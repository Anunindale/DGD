/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.datasources.base;

import emc.datatypes.EMCString;
import emc.datatypes.datasources.DSRelation;
import emc.datatypes.datasources.QueryManipulator;
import emc.entity.base.BaseEmployeeTable;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;

/**
 *
 * @author wikus
 */
public class employeeNameDS extends EMCString {

    public employeeNameDS() {
        this.setEmcLabel("Employee Name");
        this.setColumnWidth(100);
        this.setStringSize(50);
        DSRelation dsr = new DSRelation();
        dsr.setFKField("employeeId");
        dsr.setPKField("employeeNumber");
        dsr.setSearchCriteria(new QueryManipulator() {

            @Override
            public EMCQuery manipulateQuery(EMCQuery query, String PKField, String FKField, Object theValue, java.lang.String queryCondition, EMCUserData userData) {
                query.addTable(BaseEmployeeTable.class.getName());
                query.addTableAnd(query.getEntityClassName(), PKField, BaseEmployeeTable.class.getName(), FKField);
                query.openConditionBracket(EMCQueryBracketConditions.AND);
                query.addOr("forenames", theValue, BaseEmployeeTable.class.getName(), EMCQueryConditions.fromString(queryCondition));
                query.addOr("surname", theValue, BaseEmployeeTable.class.getName(), EMCQueryConditions.fromString(queryCondition));
                query.closeConditionBracket();
                return query;
            }
        });
        this.setDsRelation(dsr);
    }
}
