/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.trec.chemicals;

import emc.datatypes.EMCBoolean;

/**
 *
 * @author wikus
 */
public class Ready extends EMCBoolean {

    public Ready() {
    	this.setColumnWidth(51);
        this.setEmcLabel("Ready");
    }
}
