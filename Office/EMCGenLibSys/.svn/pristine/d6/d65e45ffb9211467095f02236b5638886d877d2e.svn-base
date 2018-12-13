/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.requirementsplanning;

import emc.datatypes.EMCString;
import emc.datatypes.datasources.DSRelation;
import emc.datatypes.datasources.QueryManipulator;
import emc.entity.inventory.requirementsplanning.InventoryRequirementsPlanning;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;

/**
 *
 * @author wikus
 */
public class DemandId extends EMCString {

    public DemandId() {
        this.setEmcLabel("Demand");
        this.setColumnWidth(70);
        DSRelation relation = new DSRelation();
        relation.setSearchCriteria(new QueryManipulator() {

            @Override
            public EMCQuery manipulateQuery(EMCQuery query, String PKField, String FKField, Object theValue, String queryCondition, EMCUserData userData) {
                query.addAnd("referenceRecordRef", theValue, InventoryRequirementsPlanning.class.getName());
                return query;
            }
        });
        this.setDsRelation(relation);
    }
}
