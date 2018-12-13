/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.action.emcimportwizard.resources;

import emc.datatypes.EMCDataType;
import emc.framework.EMCEntityClass;
import emc.tables.EMCTable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author wikus
 */
public class GenClass {

    private static SimpleDateFormat systemDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static Date theDate;

    /**
     * Sets the seporator to one from the seperator on the drop down.
     * @param seporator
     * @return
     */
    public synchronized static String setSeporator(String seporator) {
        if (seporator.equals(".(Dot)")) {
            return "\\.";
        }
        if (seporator.equals(",(Comma)")) {
            return ",";
        }
        if (seporator.equals(" (Space)")) {
            return " ";
        }
        if (seporator.equals("  (Tab)")) {
            return "\t";
        }
        if (seporator.equals("\\(Back slash")) {
            return "\\";
        }
        if (seporator.equals("\'(Single Quote)")) {
            return "\'";
        }
        if (seporator.equals("\"(Double Quote)")) {
            return "\"";
        }
        return seporator;
    }

    /**
     * Sets the text delimiter to one from the delimiter on the drop down.
     * @param delimiter
     * @return
     */
    public synchronized static String setDelimiter(String delimiter) {
        if (delimiter.equals("\"(Double Quote)")) {
            return "\"";
        }
        if (delimiter.equals("|(Pipe)")) {
            return "|";
        }
        return delimiter;
    }

    /**
     * Formats the date to the system wide date format.
     * @param date
     * @return
     */
    public synchronized static String formatDate(String date, String dateString) {
        try {
            theDate = new SimpleDateFormat(dateString).parse(date);
            return systemDateFormat.format(theDate);
        } catch (ParseException ex) {
        }
        return date;
    }

    /**
     * Sets up the file reader for the import
     * @param fileName
     * @throws java.io.FileNotFoundException
     */
    public synchronized static List setReader(String fileName) throws FileNotFoundException {
        List l = new ArrayList();
        File theFile = new File(fileName);
        l.add(Long.valueOf(theFile.length()).intValue());
        l.add(new BufferedReader(new InputStreamReader(new FileInputStream(theFile))));
        return l;
    }

    /**
     * populates the HashMap with the column info
     * @param tableName
     * @return
     */
    public synchronized static ColumnMap setColumnInfo(String tableName) {
        ColumnMap columnMap = new ColumnMap(tableName);
        try {
            HashMap<String, EMCDataType> DTMap = makeDTMap(tableName);
            Class cls = Class.forName(tableName);
            Constructor c = cls.getConstructor(new Class[]{});
            EMCEntityClass entity = (EMCEntityClass) c.newInstance(new Object[]{});

            List<Field> fields = entity.getAllTableFields();
            String fieldName;
            boolean active;
            boolean mandatory;
            Object type;
            for (Field f : fields) {
                fieldName = f.getName();
                EMCDataType DT = DTMap.get(fieldName);
                if (DT != null) {
                    mandatory = DT.isMandatory();
                    active = mandatory;
                } else {
                    active = false;
                    mandatory = false;
                }
                if (f.getType().equals(double.class) || f.getType().equals(Double.class) || f.getType().equals(BigDecimal.class)) {
                    type = new Double(0);
                } else if (f.getType().equals(long.class) || f.getType().equals(Long.class)) {
                    type = new Long(0);
                } else if (f.getType().equals(boolean.class) || f.getType().equals(Boolean.class)) {
                    type = new Boolean(true);
                } else if (f.getType().equals(int.class)) {
                    type = new Integer(0);
                } else {
                    type = f.getType().newInstance();
                }
                columnMap.put(fieldName, new ColumnInfo(fieldName, active, null, false, mandatory, type));
            }
        } catch (Exception ex) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to get fields for entity. " + ex.getMessage(), ex);
        }
        return columnMap;
    }

    /**
     * Sets the displayed columns on the form
     * 
     * @param className the EMCTable
     * @param model the form table`s model
     */
    public synchronized static void setColumns(String className, DefaultTableModel model) {
        try {
            HashMap<String, EMCDataType> DTMap = makeDTMap(className);
            model.setColumnCount(0);
            Class<?> theClass = Class.forName(className);
            EMCTable table = (EMCTable) theClass.newInstance();
            DTMap = table.getFieldDataTypeMapper();
            Set display = DTMap.keySet();
            display.remove("companyId");
            model.setColumnIdentifiers(display.toArray());
        } catch (Exception e) {
        }
    }

    public synchronized static HashMap<String, EMCDataType> makeDTMap(String emcTable)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return ((EMCTable) Class.forName(emcTable).newInstance()).getFieldDataTypeMapper();
    }
}
