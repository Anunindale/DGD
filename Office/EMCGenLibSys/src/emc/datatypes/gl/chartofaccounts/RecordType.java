/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.gl.chartofaccounts;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class RecordType extends EMCString {

    public RecordType() {
    	this.setColumnWidth(93);
        this.setEmcLabel("Record Type");
    }
}
