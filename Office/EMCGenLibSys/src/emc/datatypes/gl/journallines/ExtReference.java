/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.gl.journallines;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class ExtReference extends EMCString {

    public ExtReference() {
    	this.setColumnWidth(116);
        this.setEmcLabel("Ext. Reference");
    }
}
