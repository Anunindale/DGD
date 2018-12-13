/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.requirementsplanning;

import emc.datatypes.EMCString;
import emc.datatypes.datasources.DSRelation;
import emc.datatypes.datasources.QueryManipulator;
import emc.entity.inventory.requirementsplanning.InventoryRequirementsPlanning;
import emc.enums.inventory.requirementsplanning.RequirementsPlanningFromType;
import emc.enums.inventory.requirementsplanning.RequirementsPlanningReferenceTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;

/**
 *
 * @author wikus
 */
public class DemandType extends EMCString {

    public DemandType() {
        this.setEmcLabel("Demand Type");
        this.setColumnWidth(31);
        DSRelation relation = new DSRelation();
        relation.setSearchCriteria(new QueryManipulator() {

            @Override
            public EMCQuery manipulateQuery(EMCQuery query, String PKField, String FKField, Object theValue, String queryCondition, EMCUserData userData) {
                int typeId;
                RequirementsPlanningReferenceTypes type = RequirementsPlanningReferenceTypes.fromShortString((String) theValue);
                if (type == null) {
                    typeId = -1;
                } else {
                    switch (type) {
                        case FORECAST:
                            typeId = RequirementsPlanningReferenceTypes.FORECAST.getId();
                            break;
                        case SALES_ORDER:
                            typeId = RequirementsPlanningReferenceTypes.SALES_ORDER.getId();
                            break;
                        case WORKS_ORDER:
                            typeId = RequirementsPlanningReferenceTypes.WORKS_ORDER.getId();
                            break;
                        case PURCHASE_ORDER:
                            typeId = RequirementsPlanningReferenceTypes.PURCHASE_ORDER.getId();
                            break;
                        case PLANNED_PURCHASE_ORDER:
                            typeId = RequirementsPlanningReferenceTypes.PLANNED_PURCHASE_ORDER.getId();
                            break;
                        case PLANNED_WORKS_ORDER:
                            typeId = RequirementsPlanningReferenceTypes.PLANNED_WORKS_ORDER.getId();
                            break;
                        default:
                            typeId = -1;
                    }
                }
                query.addAnd("referenceRecordType", typeId, InventoryRequirementsPlanning.class.getName());
                return query;
            }
        });
        this.setDsRelation(relation);
    }
}
