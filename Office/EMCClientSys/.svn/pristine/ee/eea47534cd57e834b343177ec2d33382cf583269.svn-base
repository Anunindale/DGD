/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.output.bdloggenericreport.resources;

import emc.app.components.emctable.EMCJTableSuper;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.jasper.genericreport.EMCGenericReportPrintDataInterface;
import emc.app.jasper.genericreport.EMCGenericReportPrintThread;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.util.utilFunctions;
import emc.datatypes.EMCDataType;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author wikus
 */
public class BaseDBLogGenericReportDataManager implements EMCGenericReportPrintDataInterface {

    private Class entityClass;
    private List<EMCEntityClass> reportData;
    private EMCUserData userData;
    private EMCEntityClass entityClassInstance;
    private Map<String, EMCDataType> dataTypeMap;

    public BaseDBLogGenericReportDataManager(Class entityClass, List<EMCEntityClass> reportData, EMCUserData userData) throws InstantiationException, IllegalAccessException {
        this.entityClass = entityClass;
        this.reportData = reportData;
        this.userData = userData;
        this.entityClassInstance = (EMCEntityClass) entityClass.newInstance();
        this.dataTypeMap = entityClassInstance.getFieldDataTypeMapper();
    }

    public int getRowCount() {
        return reportData.size();
    }

    public EMCJTableSuper getMainTableComponent() {
        return null;
    }

    public Class getEntityClass() {
        return entityClass;
    }

    public String getColumnName(String columnId) {
        EMCDataType dt = getDataType(columnId);
        if (dt != null) {
            return dt.getEmcLabel();
        } else {
            return utilFunctions.convertName(columnId);
        }
    }

    public BaseInternalFrame getTheForm() {
        return null;
    }

    public EMCUserData getUserData() {
        return userData;
    }

    public EMCDataType getDataType(String columnId) {
        return dataTypeMap.get(columnId);
    }

    public List<Object> fetchGenericReportData(Class entityClassName, int from, boolean useTableValues, emcTableModelUpdate model, List<String> currentColumns, EMCUserData userData) {
        List<Object> toPrint = new ArrayList<Object>();
        Object[] printRow;
        String column;
        Field f;
        Map<String, Field> columnFieldMap = new HashMap<String, Field>();
        List<Field> fieldList = entityClassInstance.getAllTableFields();
        for (int r = 0; r < reportData.size(); r++) {
            printRow = new Object[currentColumns.size()];

            for (int c = 0; c < currentColumns.size(); c++) {
                column = currentColumns.get(c);
                f = columnFieldMap.get(column);

                if (f == null) {
                    for (Field tf : fieldList) {
                        if (tf.getName().equals(column)) {
                            f = tf;
                            columnFieldMap.put(column, tf);
                            break;
                        }
                    }
                }

                try {
                    f.setAccessible(true);
                    printRow[c] = f.get(reportData.get(r));
                } catch (Exception ex) {
                    //
                }
            }

            toPrint.add(printRow);
        }
        return toPrint;
    }

    public void printTableData() {
        new EMCGenericReportPrintThread(this);
    }
}
