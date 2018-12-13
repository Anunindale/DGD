/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.app.components.inventory.lookups;

import emc.app.components.EMCFormComboBox;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.enums.inventory.inventorytables.InventoryOrderMethods;

/**
 *
 * @author wikus
 */
public class InventoryOrderMethodDropDown extends EMCFormComboBox{

    public InventoryOrderMethodDropDown(emcDataRelationManagerUpdate dataRelationManagerUpdate, String fieldKey) {
        super(new String[] {InventoryOrderMethods.MIN_MAX.toString()}, dataRelationManagerUpdate, fieldKey);
    }
    
    

}
