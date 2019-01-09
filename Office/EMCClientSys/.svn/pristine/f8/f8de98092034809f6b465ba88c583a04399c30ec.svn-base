/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.action.emcimportwizard.resources;

import emc.datatypes.EMCDataType;
import emc.datatypes.EMCDate;
import emc.framework.EMCUserData;
import java.io.BufferedReader;
import java.io.EOFException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author wikus
 */
public class ImportPreview {

    private ColumnMap columnMap;

    /**
     * Populates the table with the first 10 lines,
     * just for the user to see
     * 
     * @param table the table on the import wizard form
     * @param fileName the file to read from
     * @param seporator the seporator used in the file
     * @param userData
     * @return false if any thing fails else returns true.
     */
    public boolean preview(String emcTable, JTable table, String fileName, String seporatorForm, String delimiterForm, String dateFormat, ColumnMap map, EMCUserData userData) {
        try {
            HashMap<String, EMCDataType> DTMap = GenClass.makeDTMap(emcTable);
            this.columnMap = map;
            String colName;
            ColumnInfo info;
            String line;
            String[] lineSplit;
            String companyId = userData.getCompanyId();
            String seporator = GenClass.setSeporator(seporatorForm);
            String delimiter = GenClass.setDelimiter(delimiterForm);
            int col;
            try {
                BufferedReader reader = (BufferedReader) GenClass.setReader(fileName).get(1);
                for (int i = 0; i < 10; i++) {
                    line = reader.readLine();
                    if (line == null) {
                        throw new EOFException();
                    }
                    lineSplit = line.split(seporator);
                    for (int d = 0; d < lineSplit.length; d++) {
                        if (lineSplit[d].contains(delimiter)) {
                            lineSplit[d] = lineSplit[d].replace(delimiter, "");
                        }
                    }
                    col = 0;

                    for (int x = 0; x < table.getColumnCount(); x++) {
                        try {
                            colName = table.getColumnName(x);
                            if (colName.equals("companyId")) {
                                table.setValueAt(companyId, i, x);
                            } else {
                                if (DTMap.get(colName) instanceof EMCDate) {
                                    info = columnMap.get(colName);
                                    if (info.isOverride()) {
                                        table.setValueAt(GenClass.formatDate(info.getDefaultValue(), dateFormat), i, x);
                                    } else if (emc.functions.Functions.checkBlank(lineSplit[col])) {
                                        table.setValueAt(GenClass.formatDate(info.getDefaultValue(), dateFormat), i, x);
                                    } else {
                                        table.setValueAt(GenClass.formatDate(lineSplit[col], dateFormat), i, x);
                                    }
                                } else {
                                    info = columnMap.get(colName);
                                    try {
                                        if (info.isOverride()) {
                                            table.setValueAt(info.getDefaultValue(), i, x);
                                        } else if (emc.functions.Functions.checkBlank(lineSplit[col])) {
                                            table.setValueAt(info.getDefaultValue(), i, x);
                                        } else {
                                            table.setValueAt(lineSplit[col], i, x);
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {
                                        if (!emc.functions.Functions.checkBlank(info.getDefaultValue())) {
                                            table.setValueAt(info.getDefaultValue(), i, x);
                                        } else {
                                            continue;
                                        }
                                    }
                                }
                            }
                            col++;
                        } catch (Exception e) {
                            break;
                        }
                    }
                }
            } catch (EOFException eof) {
                return true;
            } catch (Exception e) {
                Logger.getLogger("emc").log(Level.WARNING, "The file doesn't exist or if invalid. Please reselect the file.", userData);
                return false;
            }
            return true;
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        } catch (ClassNotFoundException ex) {
        }
        return false;
    }

    /**
     * Sets the displayed columns from the HashMap with the column info.
     * 
     * @param columnMap
     * @param model
     */
    public void setColumnsFromSpec(ColumnMap columnMap, DefaultTableModel model) {
        model.setColumnCount(0);
        List headers = new ArrayList();
        Collection<ColumnInfo> infoCollec = columnMap.values();
        for (ColumnInfo info : infoCollec) {
            try {
                if (info.isActive()) {
                    headers.add(info.getColumnName());
                }
            } catch (Exception e) {
                continue;
            }
        }
        model.setColumnIdentifiers(headers.toArray());
    }
}
