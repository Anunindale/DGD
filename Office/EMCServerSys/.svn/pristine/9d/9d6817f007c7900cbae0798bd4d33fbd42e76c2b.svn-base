/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.copytabledata;

import emc.datatypes.EMCDataType;
import emc.entity.base.BaseCompanyTable;
import emc.entity.base.BaseDocuRefTable;
import emc.entity.base.BaseFilePaths;
import emc.entity.base.copytabledata.BaseCopyTableData;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.tables.EMCTable;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author wikus
 */
@Stateless
public class BaseCopyTableDataBean extends EMCEntityBean implements BaseCopyTableDataLocal {

    @EJB
    private BaseCopyTableDataLocal thisBean;

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean valid = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        if (valid) {
            BaseCopyTableData record = (BaseCopyTableData) theRecord;
            if (fieldNameToValidate.equals("tableName")) {
                if (isSystemTable(record.getTableName()) && !record.getTableName().equals(BaseCompanyTable.class.getName())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "The selected table is a system table.", userData);
                    return false;
                }
            }
        }
        return valid;
    }

    @Override
    public boolean copyTableData(String fromCompanyId, String toCompanyId, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseCopyTableData.class);
        List<BaseCopyTableData> tableToCopyList = util.executeGeneralSelectQuery(query, userData);
        util.detachEntities(userData);


        Class tableToCopyClass;
        Map<enumEMCModules, BaseFilePaths> fromFilePathMap = new HashMap<enumEMCModules, BaseFilePaths>();
        BaseFilePaths fromFilePath;
        Map<enumEMCModules, BaseFilePaths> toFilePathMap = new HashMap<enumEMCModules, BaseFilePaths>();
        BaseFilePaths toFilePath;
        enumEMCModules module;


        Map<String, Integer> entityPositionMap = new HashMap<String, Integer>();
        int listLoopCount = 0;
        List<String> tablesAlreadyAdded = new ArrayList<String>();

        for (BaseCopyTableData tableToCopy : tableToCopyList) {
            entityPositionMap.put(tableToCopy.getTableName(), listLoopCount);
            listLoopCount++;

            if (!isSystemTable(tableToCopy.getTableName()) && !tableToCopy.getTableName().equals(BaseCompanyTable.class.getName())) {
                if (tableToCopy.isCopyAttachments()) {
                    try {
                        tableToCopyClass = Class.forName(tableToCopy.getTableName());
                    } catch (ClassNotFoundException ex) {
                        throw new EMCEntityBeanException(ex);
                    }

                    module = Functions.getEMCModule(tableToCopyClass);

                    userData.setCompanyId(toCompanyId);
                    toFilePath = toFilePathMap.get(module);
                    if (toFilePath == null) {
                        query = new EMCQuery(enumQueryTypes.SELECT, BaseFilePaths.class);
                        query.addAnd("formModule", module.toString());
                        toFilePath = (BaseFilePaths) util.executeSingleResultQuery(query, userData);
                        if (toFilePath == null) {
                            Logger.getLogger("emc").log(Level.SEVERE, "Failed to find a file path for the " + module.toString() + " module in the " + toCompanyId + " company.", userData);
                            throw new EMCEntityBeanException("Failed to find a file path for the " + module.toString() + " module in the " + toCompanyId + " company.");
                        }
                        toFilePathMap.put(module, toFilePath);
                    }

                    userData.setCompanyId(fromCompanyId);
                    fromFilePath = fromFilePathMap.get(module);
                    if (fromFilePath == null) {
                        query = new EMCQuery(enumQueryTypes.SELECT, BaseFilePaths.class);
                        query.addAnd("formModule", module.toString());
                        fromFilePath = (BaseFilePaths) util.executeSingleResultQuery(query, userData);
                        if (fromFilePath == null) {
                            Logger.getLogger("emc").log(Level.SEVERE, "Failed to find a file path for the " + module.toString() + " module in the " + fromCompanyId + " company.", userData);
                            throw new EMCEntityBeanException("Failed to find a file path for the " + module.toString() + " module in the " + fromCompanyId + " company.");
                        }
                        fromFilePathMap.put(module, fromFilePath);
                    }
                }
            }
        }

        for (BaseCopyTableData tableToCopy : tableToCopyList) {
            copyTableData(tableToCopy, tableToCopyList, fromCompanyId, toCompanyId, fromFilePathMap, toFilePathMap, entityPositionMap, tablesAlreadyAdded, userData);
        }
        return true;
    }

    private void copyTableData(BaseCopyTableData tableToCopy, List<BaseCopyTableData> tableToCopyList, String fromCompanyId, String toCompanyId, Map<enumEMCModules, BaseFilePaths> fromFilePathMap, Map<enumEMCModules, BaseFilePaths> toFilePathMap, Map<String, Integer> entityPositionMap, List<String> tablesAlreadyAdded, EMCUserData userData) throws EMCEntityBeanException {
        if (tablesAlreadyAdded.contains(tableToCopy.getTableName())) {
            return;
        }
        Class tableToCopyClass;
        EMCEntityClass copyRecord;
        BaseDocuRefTable copyDocument;
        List<EMCEntityClass> recordToCopyList;
        List<BaseDocuRefTable> recordToCopyDocumentList;
        enumEMCModules module;
        BufferedOutputStream fileWriter = null;
        BufferedInputStream fileReader = null;
        byte[] readBytes = new byte[1024];
        BaseFilePaths fromFilePath;
        BaseFilePaths toFilePath;
        EMCEntityClass entityClass;
        HashMap<String, EMCDataType> dataTypeMap;
        if (!isSystemTable(tableToCopy.getTableName()) && !tableToCopy.getTableName().equals(BaseCompanyTable.class.getName())) {
            try {
                tableToCopyClass = Class.forName(tableToCopy.getTableName());
                entityClass = (EMCEntityClass) tableToCopyClass.newInstance();
            } catch (Exception ex) {
                throw new EMCEntityBeanException(ex);
            }

            dataTypeMap = entityClass.getFieldDataTypeMapper();

            for (EMCDataType dataType : dataTypeMap.values()) {
                if (!isBlank(dataType.getRelatedTable()) && !tablesAlreadyAdded.contains(dataType.getRelatedTable()) && entityPositionMap.containsKey(dataType.getRelatedTable())) {
                    copyTableData(tableToCopyList.get(entityPositionMap.get(dataType.getRelatedTable())), tableToCopyList, fromCompanyId, toCompanyId, fromFilePathMap, toFilePathMap, entityPositionMap, tablesAlreadyAdded, userData);
                }
            }

            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, tableToCopyClass);
            userData.setCompanyId(fromCompanyId);
            recordToCopyList = util.executeGeneralSelectQuery(query, userData);
            util.detachEntities(userData);

            for (EMCEntityClass recordToCopy : recordToCopyList) {
                System.out.println("Table: " + (tableToCopyList.indexOf(tableToCopy) + 1) + " of " + tableToCopyList.size() +
                        "    -   Record: " + (recordToCopyList.indexOf(recordToCopy) + 1) + " of " + recordToCopyList.size());
                copyRecord = (EMCEntityClass) doClone(recordToCopy, userData);
                copyRecord.setCompanyId(toCompanyId);
                userData.setCompanyId(toCompanyId);
                thisBean.insertInNewTx(copyRecord, userData);
                util.flushEntity(userData);

                if (tableToCopy.isCopyAttachments() && recordToCopy.getHasAttachment()) {
                    query = new EMCQuery(enumQueryTypes.SELECT, BaseDocuRefTable.class);
                    query.addAnd("refTableName", tableToCopyClass.getSimpleName());
                    query.addAnd("refRecId", recordToCopy.getRecordID());
                    userData.setCompanyId(fromCompanyId);
                    recordToCopyDocumentList = util.executeGeneralSelectQuery(query, userData);
                    util.detachEntities(userData);

                    for (BaseDocuRefTable recordToCopyDocument : recordToCopyDocumentList) {
                        System.out.println("Table: " + (tableToCopyList.indexOf(tableToCopy) + 1) + " of " + tableToCopyList.size() +
                                "    -   Record: " + (recordToCopyList.indexOf(recordToCopy) + 1) + " of " + recordToCopyList.size() +
                                "    -   Document: " + (recordToCopyDocumentList.indexOf(recordToCopyDocument) + 1) + " of " + recordToCopyDocumentList.size());
                        copyDocument = (BaseDocuRefTable) doClone(recordToCopyDocument, userData);
                        copyDocument.setCompanyId(toCompanyId);
                        copyDocument.setRefRecId(String.valueOf(copyRecord.getRecordID()));

                        userData.setCompanyId(toCompanyId);
                        thisBean.insertInNewTx(copyDocument, userData);
                        util.flushEntity(userData);

                        if (!isBlank(recordToCopyDocument.getAttachmentFileName())) {
                            module = Functions.getEMCModule(recordToCopy.getClass());

                            fromFilePath = fromFilePathMap.get(module);
                            toFilePath = toFilePathMap.get(module);

                            fileWriter = null;
                            fileReader = null;
                            readBytes = new byte[1024];

                            try {
                                fileReader = new BufferedInputStream(new FileInputStream(new File(fromFilePath.getFilePath() + File.separator + recordToCopy.getRecordID() + "_" + recordToCopyDocument.getAttachmentFileName())));
                                fileWriter = new BufferedOutputStream(new FileOutputStream(new File(toFilePath.getFilePath() + File.separator + copyRecord.getRecordID() + "_" + recordToCopyDocument.getAttachmentFileName())));

                                while (fileReader.read(readBytes) != -1) {
                                    fileWriter.write(readBytes);
                                }
                            } catch (IOException ex) {
                                Logger.getLogger("emc").log(Level.SEVERE, "Failed to attachment document.", userData);
                            } finally {
                                if (fileWriter != null) {
                                    try {
                                        fileWriter.flush();
                                        fileWriter.close();
                                    } catch (IOException ex) {
                                    }
                                }
                                try {
                                    if (fileReader != null) {
                                        fileReader.close();
                                    }
                                } catch (IOException ex) {
                                }
                            }
                        }
                    }
                }
            }
            tablesAlreadyAdded.add(tableToCopy.getTableName());
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void insertInNewTx(EMCEntityClass recordToCopy, EMCUserData userData) throws EMCEntityBeanException {
        super.insert(recordToCopy, userData);
    }

    @Override
    public boolean deleteTableData(String companyId, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseCopyTableData.class);
        List<BaseCopyTableData> tableToCopyList = util.executeGeneralSelectQuery(query, userData);
        Class tableToClear;
        for (BaseCopyTableData tableToCopy : tableToCopyList) {
            if (!isSystemTable(tableToCopy.getTableName()) && !tableToCopy.getTableName().equals(BaseCompanyTable.class.getName())) {
                try {
                    tableToClear = Class.forName(tableToCopy.getTableName());
                } catch (ClassNotFoundException ex) {
                    throw new EMCEntityBeanException(ex);
                }


                query = new EMCQuery(enumQueryTypes.DELETE, tableToClear);
                query.addAnd("companyId", companyId);
                if (!util.executeUpdate(query.toString(), userData)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean addRelatedTables(EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseCopyTableData.class);
        List<BaseCopyTableData> tableToCopyList = util.executeGeneralSelectQuery(query, userData);

        EMCEntityClass entityToCopy;
        HashMap<String, EMCDataType> dataTypeMap;
        List<String> tablesAlreadyAdded = new ArrayList<String>();

        for (BaseCopyTableData tableToCopy : tableToCopyList) {
            tablesAlreadyAdded.add(tableToCopy.getTableName());
        }

        for (BaseCopyTableData tableToCopy : tableToCopyList) {
            try {
                entityToCopy = (EMCEntityClass) Class.forName(tableToCopy.getTableName()).newInstance();
            } catch (Exception ex) {
                throw new EMCEntityBeanException(ex);
            }
            dataTypeMap = entityToCopy.getFieldDataTypeMapper();

            resolveRelations(dataTypeMap, tablesAlreadyAdded, tableToCopy.isCopyAttachments(), userData);
        }
        return true;
    }

    private void resolveRelations(HashMap<String, EMCDataType> dataTypeMap, List<String> tablesAlreadyAdded, boolean copyAttachments, EMCUserData userData) throws EMCEntityBeanException {
        BaseCopyTableData relatedtableToCopy;
        EMCEntityClass entityToCopy;
        HashMap<String, EMCDataType> relatedDataTypeMap;
        for (EMCDataType dataType : dataTypeMap.values()) {
            if (!isBlank(dataType.getRelatedTable()) && !tablesAlreadyAdded.contains(dataType.getRelatedTable())) {
                relatedtableToCopy = new BaseCopyTableData();
                relatedtableToCopy.setTableName(dataType.getRelatedTable());
                relatedtableToCopy.setCopyAttachments(copyAttachments);
                this.insert(relatedtableToCopy, userData);

                tablesAlreadyAdded.add(dataType.getRelatedTable());

                try {
                    entityToCopy = (EMCEntityClass) Class.forName(dataType.getRelatedTable()).newInstance();
                } catch (Exception ex) {
                    throw new EMCEntityBeanException(ex);
                }

                relatedDataTypeMap = entityToCopy.getFieldDataTypeMapper();

                resolveRelations(relatedDataTypeMap, tablesAlreadyAdded, copyAttachments, userData);
            }
        }
    }

    @Override
    public String createBackupFile(EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseCopyTableData.class);
        List<BaseCopyTableData> tableToCopyList = util.executeGeneralSelectQuery(query, userData);

        Class recordToBackUp;
        List recordsToBackUp;
        StringBuilder insertString = null;
        String insertTable;
        List<Field> tableFields;
        Object value;
        boolean addComma = false;
        boolean addFieldsComma;
        boolean createBuilder = true;
        int count = 0;
        int insertSize = 10;

        query = new EMCQuery(enumQueryTypes.SELECT, BaseFilePaths.class);
        query.addAnd("formModule", enumEMCModules.BASE.toString());
        BaseFilePaths filePath = (BaseFilePaths) util.executeSingleResultQuery(query, userData);
        if (filePath == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to find a file path for the " + enumEMCModules.BASE.toString() + " module.", userData);
            throw new EMCEntityBeanException("Failed to find a file path for the " + enumEMCModules.BASE.toString() + " module.");
        }
        File theFile = new File(filePath.getFilePath() + File.separator + "EMCBackup_" + Functions.nowDateString("yyyy-MM-dd") + ".emc");
        if (theFile.exists()) {
            Logger.getLogger("emc").log(Level.SEVERE, "A Backup is already in progress.", userData);
            throw new EMCEntityBeanException("A Backup is already in progress.");
        }

        for (BaseCopyTableData tableToCopy : tableToCopyList) {
            try {
                recordToBackUp = Class.forName(tableToCopy.getTableName());
            } catch (ClassNotFoundException ex) {
                throw new EMCEntityBeanException(ex);
            }


            query = new EMCQuery(enumQueryTypes.SELECT, recordToBackUp);
            recordsToBackUp = util.executeGeneralSelectQuery(query, userData);

            insertString = null;
            insertTable = recordToBackUp.getSimpleName();
            tableFields = new ArrayList<Field>();
            do {
                tableFields.addAll(Arrays.asList(recordToBackUp.getDeclaredFields()));
                recordToBackUp = recordToBackUp.getSuperclass();
            } while (!recordToBackUp.getName().equals(EMCTable.class.getName()));

            addComma = false;
            createBuilder = true;
            count = 0;
            insertSize = 10;

            for (Object record : recordsToBackUp) {
                if (createBuilder) {
                    insertString = new StringBuilder("INSERT INTO ");
                    insertString.append(insertTable);
                    insertString.append("(");
                    for (Field f : tableFields) {
                        insertString.append(f.getName());
                        if (tableFields.indexOf(f) != tableFields.size() - 1) {
                            insertString.append(", ");
                        }
                    }
                    insertString.append(") VALUES ");
                    addComma = false;
                    addFieldsComma = false;
                    createBuilder = false;
                }

                if (addComma) {
                    insertString.append(", ");
                    insertString.append(System.getProperty("line.separator"));
                }

                insertString.append("(");
                addFieldsComma = false;

                for (Field f : tableFields) {
                    if (addFieldsComma) {
                        insertString.append(", ");
                    }
                    try {
                        f.setAccessible(true);
                        value = f.get(record);
                        if (value instanceof Number) {
                            if (value == null) {
                                insertString.append(0);
                            } else {
                                insertString.append(value);
                            }
                        } else {
                            if (value instanceof Boolean) {
                                if (value == null) {
                                    insertString.append(false);
                                } else {
                                    insertString.append(value);
                                }
                            } else {
                                if (value == null) {
                                    insertString.append("null");
                                } else {
                                    if (value instanceof java.sql.Time) {
                                        insertString.append("\'");
                                        insertString.append(Functions.date2String((Date) value, "HH:mm:ss"));
                                        insertString.append("\'");
                                    } else if (value instanceof java.sql.Date) {
                                        insertString.append("\'");
                                        insertString.append(Functions.date2SQLString((Date) value));
                                        insertString.append("\'");
                                    } else {
                                        value = value.toString().replaceAll("'", " ");
                                        insertString.append("\'");
                                        insertString.append(value);
                                        insertString.append("\'");
                                    }
                                }
                            }
                        }
                    } catch (Exception ex) {
                        insertString.append("null");
                    }
                    addFieldsComma = true;
                }
                insertString.append(")");
                addComma = true;
            }
            count++;
            if (count == insertSize) {
                insertString.append(";");
                insertString.append(System.getProperty("line.separator"));

                writeBackupFile(theFile, insertString, userData);

                insertString = null;
                createBuilder = true;
                count = 0;
            }
            if (insertString != null && insertString.length() != 0) {
                insertString.append(";");
                writeBackupFile(theFile, insertString, userData);
            }
        }

        return "EMCBackup_" + Functions.nowDateString("yyyy-MM-dd") + ".emc";
    }

    private void writeBackupFile(File backupFile, StringBuilder fileContent, EMCUserData userData) throws EMCEntityBeanException {
        PrintWriter out = null;
        try {
            out = new PrintWriter(new FileOutputStream(backupFile, true));
            out.println(fileContent.toString());
        } catch (IOException ex) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to create backup file: " + ex.getMessage(), userData);
            throw new EMCEntityBeanException("Failed to create backup file: " + ex.getMessage());
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    public void deleteBackupFile(EMCUserData userData) {
        String module = (String) userData.getUserData(0);
        String fileName = (String) userData.getUserData(1);
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseFilePaths.class);
        query.addAnd("formModule", module);
        BaseFilePaths filePath = (BaseFilePaths) util.executeSingleResultQuery(query, userData);
        if (filePath == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to find a file path for the " + module + " module.", userData);
            return;
        }
        File theFile = new File(filePath.getFilePath() + File.separator + fileName);
        if (!theFile.exists()) {
            Logger.getLogger("emc").log(Level.SEVERE, "The backup file no longer exists.", userData);
            return;
        }
        theFile.delete();
    }

    @Override
    public boolean importBackupFile(String fileName, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseFilePaths.class);
        query.addAnd("formModule", enumEMCModules.BASE.toString());
        BaseFilePaths filePath = (BaseFilePaths) util.executeSingleResultQuery(query, userData);
        if (filePath == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to find a file path for the " + enumEMCModules.BASE.toString() + " module.", userData);
            throw new EMCEntityBeanException("Failed to find a file path for the " + enumEMCModules.BASE.toString() + " module.");
        }

        File theFile = new File(filePath.getFilePath() + File.separator + fileName);
        if (!theFile.exists()) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to find the uploaded backup file.", userData);
            throw new EMCEntityBeanException("Failed to find the uploaded backup file.");
        }

        BufferedReader in = null;
        String readLine;
        StringBuilder queryBuilder = null;

        try {
            in = new BufferedReader(new FileReader(theFile));
            while ((readLine = in.readLine()) != null) {
                if (queryBuilder == null) {
                    queryBuilder = new StringBuilder();
                }

                queryBuilder.append(readLine);

                if (readLine.endsWith(";")) {
                    int rowsUpdated = util.executeNativeUpdate(queryBuilder.toString(), userData);
                    if (rowsUpdated == -1) {
                        System.out.println("Insert Fail");
                    }
                    queryBuilder = null;
                }
            }

            if (queryBuilder != null) {
                int rowsUpdated = util.executeNativeUpdate(queryBuilder.toString(), userData);
                if (rowsUpdated == -1) {
                    System.out.println("Insert Fail");
                }
                queryBuilder = null;
            }
        } catch (IOException ex) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to import EMC Backup.", userData);
            return false;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                if (EMCDebug.getDebug()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to close all streams: " + ex.getMessage(), userData);
                }
            }
            theFile.delete();
        }

        return true;
    }
}



