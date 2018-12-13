/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.workflow.display.activities.simple;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.entity.workflow.WorkFlowActivity;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class SimpleActivitiesDRM extends emcDataRelationManagerUpdate {

    private String employeeId;
    private String sourceTable;
    private Long sourceRecordId;

    public SimpleActivitiesDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData) {
        super(tableDataSource, userData);
        employeeId = (String) userData.getUserData(2);
        sourceTable = (String) userData.getUserData(3);
        sourceRecordId = (Long) userData.getUserData(4);
    }

    @Override
    public void setFieldValueAt(int rowIndex, String columnIndex, Object aValue) {
        super.setFieldValueAt(rowIndex, columnIndex, aValue);
        if (Functions.checkBlank(getLastFieldValueAt("employeeNumber"))) {
            super.setFieldValueAt(rowIndex, "employeeNumber", employeeId);
        }
        if ((Functions.checkBlank(getLastFieldValueAt("sourceTable")) || (Long) getLastFieldValueAt("sourceRecordId") == 0) &&
                !(Functions.checkBlank(sourceTable) || Functions.checkBlank(sourceRecordId))) {
            super.setFieldValueAt(rowIndex, "sourceTable", sourceTable);
            super.setFieldValueAt(rowIndex, "sourceRecordId", sourceRecordId);
        }
    }

    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
        EMCUserData userData = super.generateRelatedFormUserData(formUserData, Index);
        switch (Index) {
            case 0:
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowActivity.class.getName());
                query.addAnd("recordID", getLastFieldValueAt("recordID"));
                List x = new ArrayList();
                x.add(query);
                userData.setUserData(x);
                break;
        }
        return userData;
    }

    @Override
    public void setUserData(EMCUserData userData) {
        super.setUserData(userData);
        employeeId = (String) userData.getUserData(2);
        sourceTable = (String) userData.getUserData(3);
        sourceRecordId = (Long) userData.getUserData(4);
    }
}
