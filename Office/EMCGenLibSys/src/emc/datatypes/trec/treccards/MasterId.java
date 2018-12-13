/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.trec.treccards;

import emc.datatypes.EMCString;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author wikus
 */
public class MasterId extends EMCString {

    public MasterId() {
        this.setEmcLabel("Id");
        this.setMandatory(true);
    	this.setColumnWidth(69);
        this.setNumberSeqAllowed(true);
        this.setStringSize(255);
        this.setDeleteAction(enumDeleteUpdateOptions.CASCADE);
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
    }

}
