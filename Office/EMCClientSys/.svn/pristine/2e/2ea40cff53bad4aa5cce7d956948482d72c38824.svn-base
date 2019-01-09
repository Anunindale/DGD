/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.action.emcimportwizard.resources;

import emc.app.wsmanager.EMCMultiProcessingThreadManager;
import emc.app.wsmanager.EMCWSManager;
import emc.app.wsmanager.MultiProcessingHelper;
import emc.commands.EMCCommands;
import emc.datatypes.EMCBigDecimal;
import emc.datatypes.EMCBoolean;
import emc.datatypes.EMCDataType;
import emc.datatypes.EMCDate;
import emc.datatypes.EMCDouble;
import emc.datatypes.EMCInt;
import emc.datatypes.EMCLong;
import emc.datatypes.EMCString;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.methods.base.ServerBaseMethods;
import emc.tables.EMCTable;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoundedRangeModel;
import javax.swing.SwingWorker;

/**
 *
 * @author wikus
 */
public class ImportThread extends SwingWorker {

    private EMCUserData userData;
    private int importMethod; //0 - Normal || 1 - Multi Processing || 2 - Direct Insert 
    private String[] tableColumns;
    private boolean validateField;
    private BoundedRangeModel progressModel;
    private int fileFize;
    private BufferedReader reader;
    private int moduleNumber;
    private String methodNumber;
    private EMCTable theTable;
    private String seporator;
    private String delimiter;
    private Class<?> theClass;
    private String dateFormat;
    private PrintWriter writer;
    private String companyId;
    private EMCDataType DT;
    private HashMap<String, EMCDataType> DTMap;
    private ColumnMap columnMap;
    private EMCCommandClass importCMD;
    private EMCMultiProcessingThreadManager threadWSM;

    /**
     * Makes a new instance of the thread
     * sets up the thread that will be used during importing.
     * 
     * @param table
     * @param progressModel the progressbars model
     * @param userData
     */
    public ImportThread(String fileName, String emcTable, String seporator, String delimiter, String dateFormat, int importMethod,
            String[] tableColumns, boolean validateField, BoundedRangeModel progressModel, ColumnMap columnMap, EMCUserData userData) {
        try {
            List l = GenClass.setReader(fileName);
            this.fileFize = (Integer) l.get(0);
            this.reader = (BufferedReader) l.get(1);


            String[] tableSplit = emcTable.split("\\.");
            this.methodNumber = "INSERT_" + tableSplit[tableSplit.length - 1].toUpperCase();

            this.theClass = Class.forName(emcTable);
            this.moduleNumber = Functions.getEMCModule(theClass).getId();

            this.DTMap = GenClass.makeDTMap(emcTable);

            this.seporator = GenClass.setSeporator(seporator);
            this.delimiter = GenClass.setDelimiter(delimiter);

            this.dateFormat = dateFormat;
            this.importMethod = importMethod;
            this.tableColumns = tableColumns;
            this.validateField = validateField;
            this.progressModel = progressModel;

            this.companyId = userData.getCompanyId();

            this.columnMap = columnMap;

            this.importCMD = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.BASE.getId(), ServerBaseMethods.IMPORT_RECORD.toString());

            this.userData = userData.copyUserDataAndDataList();
        } catch (FileNotFoundException e) {
            Logger.getLogger("emc").log(Level.WARNING, "The file you are trying to read is incorrect. Please reselect and try again.", userData);
        } catch (Exception ex) {
        }
    }

    /**
     * Checks that a mandetory field has data in it.
     * @param field
     * @param value
     * @return A DataType if the field is valid or null if not.
     */
    private EMCDataType doContinue(String field, String value) {
        if (field.equals("companyId")) {
            theTable.setValueForFieldInEntityObject(field, theClass, theTable, companyId);
            return null;
        }
        DT = DTMap.get(field);
        try {
            if (DT.isMandatory() && emc.functions.Functions.checkBlank(value) && !DT.isNumberSeqAllowed()) {
                Logger.getLogger("emc").log(Level.WARNING, field + " is mandatory and the record has no value.", new Object[]{userData, "import", "hi"});
                return null;
            }
            return DT;
        } catch (NullPointerException e) {
            Object type = columnMap.get(field).getType();
            if (type instanceof String) {
                return new EMCString();
            } else if (type instanceof Integer) {
                return new EMCInt();
            } else if (type instanceof Boolean) {
                return new EMCBoolean();
            } else if (type instanceof Double) {
                return new EMCDouble();
            } else if (type instanceof Long) {
                return new EMCLong();
            } else if (type instanceof Date) {
                return new EMCDate();
            } else if (type instanceof BigDecimal) {
                return new EMCBigDecimal();
            } else {
                return null;
            }
        }
    }

    /**
     * Writes a log file of all the lines that fail to import.
     * @param line
     */
    private void writeLog(String line) {
        try {
            if (writer == null) {
                writer = new PrintWriter(new BufferedOutputStream(new FileOutputStream(
                        new File(System.getProperty("user.home") + System.getProperty("file.separator") + "FailedImportLines.log"))), true);
            }
            writer.println(line);
        } catch (IOException ex) {
        }
    }

    /**
     * The method that does the actual import.
     */
    @Override
    protected Void doInBackground() throws Exception {
        Logger.getLogger("emc").log(Level.INFO, "Importing Started", new Object[]{userData, "import", "hi"});

        //Prepare Record Insert CMD
        EMCCommandClass insertCMD = new EMCCommandClass();
        insertCMD.setCommandId(EMCCommands.SERVER_GENERAL_COMMAND.getId());
        insertCMD.setModuleNumber(moduleNumber);
        insertCMD.setMethodId(methodNumber);

        List<String> linesInProcess = new ArrayList();
        List<List<Object>> multToSend = null;
        List<Object> directToSend = null;
        switch (importMethod) {
            case 1://Multi Thread
                threadWSM = new EMCMultiProcessingThreadManager();
                multToSend = new ArrayList();
                break;
            case 2://Direct Insert
                directToSend = new ArrayList();
                break;
        }


        String line;
        Boolean errors = false;

        int barValue = 0;
        int sleep = 0;
        boolean contLoop = true;

        progressModel.setMinimum(0);
        progressModel.setMaximum(fileFize);
        progressModel.setValue(0);

        List theList;

        try {
            while (contLoop) {
                line = reader.readLine();
                if (line == null) {
                    switch (importMethod) {
                        case 0://Normal
                            break;
                        case 1://Multi Thread
                            if (!multToSend.isEmpty()) {
                                errors = doMultiThread(multToSend, linesInProcess);
                            }
                            break;
                        case 2://Direct Insert
                            if (!directToSend.isEmpty()) {
                                errors = doDirectInsert(directToSend, linesInProcess, insertCMD);
                            }
                            break;
                    }
                    progressModel.setValue(fileFize);
                    break;
                } else {
                    progressModel.setValue((barValue += line.length()));

                    if (populateEntityObject(line)) {
                        try {
                            writeLog(line);
                        } catch (Exception e) {
                        }
                    } else {
                        switch (importMethod) {
                            case 0://Normal
                                theList = new ArrayList();
                                theList.add(0);//Normal Insert
                                theList.add(validateField);
                                theList.add(theTable);
                                theList.add(theClass);
                                theList.add(insertCMD);

                                errors = doNormal(directToSend, line);
                                break;
                            case 1://Multi Thread
                                theList = new ArrayList();
                                theList.add(0);//Normal Insert
                                theList.add(validateField);
                                theList.add(theTable);
                                theList.add(theClass);
                                theList.add(insertCMD);

                                multToSend.add(theList);
                                linesInProcess.add(line);

                                //just add till we get to 2000 then process
                                if (multToSend.size() >= 2000) {
                                    errors = doMultiThread(multToSend, linesInProcess);
                                }
                                break;
                            case 2://Direct Insert
                                directToSend.add(theTable);
                                linesInProcess.add(line);

                                if (directToSend.size() >= 500) {
                                    errors = doDirectInsert(directToSend, linesInProcess, insertCMD);
                                }
                                break;
                        }
                    }
                }

                sleep++;
                if (sleep == 50) {
                    Thread.sleep(1);
                    sleep = 0;
                }
            }
        } catch (InterruptedException ex) {
        } catch (Exception e) {
            Logger.getLogger("emc").log(Level.WARNING, "There is something wrong with the file you are trying to read. Please Insure its the correct file.", new Object[]{userData, "/import", "hi"});
        }

        if (this.isCancelled()) {
            Logger.getLogger("emc").log(Level.INFO, "Importing Canceled", new Object[]{userData, "/import", "hi"});
        } else if (errors) {
            Logger.getLogger("emc").log(Level.INFO, "Importing Completed. There were errors while importing. See the Log file for more information.", new Object[]{userData, "/import", "hi"});
        } else {
            Logger.getLogger("emc").log(Level.INFO, "Importing Completed Succesfully", new Object[]{userData, "/import", "hi"});
        }
        return null;
    }

    private boolean doNormal(List<Object> toSend, String line) {
        boolean failed = false;

        toSend = EMCWSManager.executeImportWS(importCMD, toSend, userData);

        if (toSend.size() > 1 && toSend.get(1) instanceof Boolean && !(Boolean) toSend.get(1)) {
            failed = true;
            try {
                writeLog(line);
            } catch (Exception e) {
            }
        }

        return failed;
    }

    private boolean doMultiThread(List<List<Object>> toSend, List<String> linesInProcess) throws Exception {
        boolean failed = false;

        List<MultiProcessingHelper> res;

        try {
            res = threadWSM.executeMultiProcessingWS(importCMD, toSend, userData);
            EMCWSManager.getImportMessages(userData);
        } catch (IllegalThreadStateException e) {
            Logger.getLogger("emc").log(Level.SEVERE, "No Multi Processor Threads available", new Object[]{userData, "/import", "hi"});

            toSend.clear();
            linesInProcess.clear();

            throw new Exception("No Multi Processor Threads available");
        }

        for (MultiProcessingHelper curHelper : res) {
            if (curHelper.getReturnList().size() < 2 || !(Boolean) (curHelper.getReturnList().get(1))) {
                failed = true;
                try {
                    writeLog(linesInProcess.get(curHelper.getWorkNumber()));
                } catch (Exception e) {
                }
            }
        }

        toSend.clear();
        linesInProcess.clear();

        return failed;
    }

    private boolean doDirectInsert(List<Object> directRecords, List<String> linesInProcess, EMCCommandClass insertDMC) throws Exception {
        boolean failed = false;
        List toSend = new ArrayList();
        toSend.add(1);//Direct Insert
        toSend.add(validateField);
        toSend.add(directRecords);
        toSend.add(theClass.getName());
        toSend.add(insertDMC);

        toSend = EMCWSManager.executeImportWS(importCMD, toSend, userData);
        if (toSend.size() > 2 && toSend.get(1) instanceof List) {
            toSend = (List) toSend.get(1);
            if (!toSend.isEmpty()) {
                failed = true;
                for (Object index : toSend) {
                    try {
                        writeLog(linesInProcess.get((Integer) index));
                    } catch (Exception e) {
                    }
                }
            }
        }

        directRecords.clear();
        linesInProcess.clear();

        return failed;
    }

    private boolean populateEntityObject(String line) {
        boolean failed = false;

        try {
            String field;
            ColumnInfo info;
            String[] lineSplit;
            String value;
            EMCDataType dataType;
            Object insertValue = null;

            lineSplit = line.split(seporator);
            //text delimiter
            for (int d = 0; d < lineSplit.length; d++) {
                if (lineSplit[d].contains(delimiter)) {
                    lineSplit[d] = lineSplit[d].replace(delimiter, "");
                }
            }

            this.theTable = (EMCTable) theClass.newInstance();

            for (int i = 0; i < tableColumns.length; i++) {
                field = tableColumns[i];
                info = columnMap.get(field);
                try {
                    if (info.isOverride()) {
                        value = info.getDefaultValue();
                    } else if (emc.functions.Functions.checkBlank(lineSplit[i])) {
                        value = info.getDefaultValue();
                    } else {
                        value = lineSplit[i];
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    if (!emc.functions.Functions.checkBlank(info.getDefaultValue())) {
                        value = info.getDefaultValue();
                    } else {
                        continue;
                    }
                }
                dataType = doContinue(field, value);
                if (dataType != null) {
                    if (dataType instanceof EMCString) {
                        insertValue = value;
                    } else if (dataType instanceof EMCInt) {
                        if (Functions.checkBlank(value)) {
                            insertValue = 0;
                        } else {
                            insertValue = Integer.parseInt(value);
                        }
                    } else if (dataType instanceof EMCBoolean) {
                        insertValue = Boolean.parseBoolean(value);
                    } else if (dataType instanceof EMCDouble) {
                        if (Functions.checkBlank(value)) {
                            insertValue = 0;
                        } else {
                            insertValue = Double.parseDouble(value);
                        }
                    } else if (dataType instanceof EMCLong) {
                        if (Functions.checkBlank(value)) {
                            insertValue = 0;
                        } else {
                            insertValue = Long.parseLong(value);
                        }
                    } else if (dataType instanceof EMCDate) {
                        insertValue = emc.functions.Functions.string2Date(GenClass.formatDate(value, dateFormat), "dd/MM/yyyy");
                    } else if (dataType instanceof EMCBigDecimal) {
                        if (Functions.checkBlank(value)) {
                            insertValue = BigDecimal.ZERO;
                        } else {
                            insertValue = new BigDecimal(value);
                        }
                    }
                    theTable.setValueForFieldInEntityObject(field, theClass, theTable, insertValue);
                } else if (!field.equals("companyId")) {
                    failed = true;
                    break;
                }
            }
        } catch (Exception e) {
            failed = true;

            try {
                Logger.getLogger("emc").log(Level.WARNING, "Something went wrong while poulating the record." + e.getMessage(), new Object[]{userData, "import", "hi"});
                writeLog(line);
            } catch (Exception ex) {
            }
        }

        return failed;
    }
}
