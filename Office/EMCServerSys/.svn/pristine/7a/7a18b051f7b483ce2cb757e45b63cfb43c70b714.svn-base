/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory;

import emc.entity.inventory.InventoryItemGroup;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.transactions.InventorySummary;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author rico
 */
@Stateless
public class InventoryItemGroupBean extends EMCEntityBean implements InventoryItemGroupLocal {

    public InventoryItemGroupBean() {

    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        if (ret) {
            InventoryItemGroup record = (InventoryItemGroup) theRecord;
            if (fieldNameToValidate.equals("quarantineReq")) {
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class.getName());
                query.addTableAnd(InventoryItemMaster.class.getName(), "itemId", InventorySummary.class.getName(), "itemId");
                query.addTableAnd(InventoryItemGroup.class.getName(), "itemGroup", InventoryItemMaster.class.getName(), "itemGroup");
                query.addAnd("itemGroup", record.getItemGroup(), InventoryItemGroup.class.getName());
                if (util.exists(query, userData)) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Item group is already in use. May not change the QC flag", userData);
                    return false;
                }
            }
        }
        return ret;
    }

    public InventoryItemGroup findItemsItemGroup(String itemId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemGroup.class);
        query.addTableAnd(InventoryItemMaster.class.getName(), "itemGroup", InventoryItemGroup.class.getName(), "itemGroup");
        query.addAnd("itemId", itemId, InventoryItemMaster.class.getName());
        return (InventoryItemGroup) util.executeSingleResultQuery(query, userData);
    }
}
