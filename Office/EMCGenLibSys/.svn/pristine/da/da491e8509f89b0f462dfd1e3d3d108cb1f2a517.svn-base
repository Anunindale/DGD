/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.itemmaster.foreignkeys;

import emc.datatypes.datasources.DSRelation;
import emc.datatypes.datasources.QueryManipulator;
import emc.datatypes.inventory.itemreference.ItemReferenceId;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryReference;
import emc.enums.emcquery.EMCQueryConditions;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;

/**
 *
 * @author riaan
 */
public class ItemReferenceFK extends ItemReferenceId {

    /** Creates a new instance of ItemReferenceFK. */
    public ItemReferenceFK() {
        this.setRelatedField("itemReference");
        this.setRelatedTable(InventoryItemMaster.class.getName());
        this.setMandatory(false);

        DSRelation dsr = new DSRelation();
        dsr.setFKField("itemId");
        dsr.setPKField("itemId");
        //Special for item reference
        dsr.setRelatedTable(InventoryItemMaster.class.getName());
        dsr.setRelatedField("itemReference");
        dsr.setSearchCriteria(new QueryManipulator() {

            @Override
            public EMCQuery manipulateQuery(EMCQuery query, String PKField, String FKField, Object theValue, java.lang.String queryCondition, EMCUserData userData) {
                query.addTable(InventoryReference.class.getName()); // Not sure why this is here.  Commented out so that it can be restored easily if needed.
                query.addTableAnd(query.getEntityClassName(), PKField, InventoryReference.class.getName(), FKField);
                query.addAnd("reference", theValue, InventoryReference.class.getName(), EMCQueryConditions.fromString(queryCondition));

                return query;
            }
        });
        this.setDsRelation(dsr);
        this.setColumnWidth(50);
    }
}
