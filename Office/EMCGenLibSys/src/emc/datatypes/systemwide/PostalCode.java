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
public class PostalCode extends EMCString {

    /** Creates a new instance of PostalCode */
    public PostalCode() {
        this.setEmcLabel("Postal Code");
        this.setMandatory(true);
        this.setRelatedTable(BasePostalCodes.class.getName());
        this.setRelatedField("code");
    }
}
