/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.datasources.inventory;

import emc.datatypes.datasources.DSRelation;
import emc.datatypes.datasources.QueryManipulator;
import emc.datatypes.inventory.itemreference.ItemReferenceId;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryReference;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.inventory.inventorytables.InventoryReferenceTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;

/**
 *
 * @author wikus
 */
public class itemPrimaryReferenceDS extends ItemReferenceId {

    public itemPrimaryReferenceDS() {
        DSRelation dsr = new DSRelation();
        dsr.setFKField("itemId");
        dsr.setPKField("itemId");
        //Special for item reference
        dsr.setRelatedTable(InventoryItemMaster.class.getName());
        dsr.setRelatedField("itemReference");
        dsr.setSearchCriteria(new QueryManipulator() {

            @Override
            public EMCQuery manipulateQuery(EMCQuery query, String PKField, String FKField, Object theValue, java.lang.String queryCondition, EMCUserData userData) {
                query.addTable(InventoryReference.class.getName());
                query.addTableAnd(query.getEntityClassName(), PKField, InventoryReference.class.getName(), FKField);
                query.addAndCommaSeperated("reference", theValue.toString(), InventoryReference.class.getName(), EMCQueryConditions.fromString(queryCondition));
                query.openAndConditionBracket();
                query.addOr("refType", InventoryReferenceTypes.DEFAULT.toString(), InventoryReference.class.getName());
                query.addOr("refType", InventoryReferenceTypes.PRIMARY.toString(), InventoryReference.class.getName());
                query.closeConditionBracket();
                return query;
            }
        });
        this.setDsRelation(dsr);
        this.setColumnWidth(62);
    }
}
