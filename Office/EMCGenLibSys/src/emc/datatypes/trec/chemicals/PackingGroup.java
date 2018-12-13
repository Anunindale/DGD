/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.trec.chemicals;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class PackingGroup extends EMCString {

    public PackingGroup() {
    	this.setColumnWidth(84);
        this.setEmcLabel("Packing Group");
    }
}
