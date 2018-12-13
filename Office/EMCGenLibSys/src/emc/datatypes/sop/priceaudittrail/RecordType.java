/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.sop.priceaudittrail;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class RecordType extends EMCString {

    public RecordType() {
        this.setEmcLabel("Type");
    	this.setColumnWidth(87);
        this.setMandatory(true);
    }

}
