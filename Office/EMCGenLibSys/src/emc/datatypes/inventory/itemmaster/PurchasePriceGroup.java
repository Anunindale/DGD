/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.itemmaster;

import emc.datatypes.EMCString;
import emc.entity.pop.POPPriceGroup;

/**
 *
 * @author riaan
 */
public class PurchasePriceGroup extends EMCString { 

    /** Creates a new instance of PurchasePriceGroup */
    public PurchasePriceGroup() {
        this.setEmcLabel("Price Group");
        this.setRelatedTable(POPPriceGroup.class.getName());
        this.setRelatedField("priceGroupId");
    }
}
