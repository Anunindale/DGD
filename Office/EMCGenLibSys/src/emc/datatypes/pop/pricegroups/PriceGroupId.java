/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.pop.pricegroups;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class PriceGroupId extends EMCString {

    /** Creates a new instance of PriceGroupId */
    public PriceGroupId() {
        this.setEmcLabel("Price Group");
        this.setColumnWidth(15);
        this.setMandatory(true);
        this.setEditable(true);
    }
}
