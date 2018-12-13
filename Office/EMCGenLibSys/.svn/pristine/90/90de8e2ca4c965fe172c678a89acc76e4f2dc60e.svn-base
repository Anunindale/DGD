/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.systemwide;

import emc.datatypes.EMCString;
import emc.entity.base.BasePostalCodes;

/**
 *
 * @author riaan
 */
public class PhysicalPostalCode extends EMCString {

    /** Creates a new instance of PostalCode */
    public PhysicalPostalCode() {
        this.setEmcLabel("Physical Address Postal Code");
        this.setMandatory(true);
        this.setRelatedTable(BasePostalCodes.class.getName());
        this.setRelatedField("code");
    }
}