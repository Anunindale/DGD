/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.sop.salesordermaster;

import emc.datatypes.EMCDate;

/**
 *
 * @author claudette
 */
public class OriginalRequiredDate extends EMCDate {

    public OriginalRequiredDate() {
        this.setEmcLabel("Original");
    	this.setColumnWidth(77);
        this.setEditable(false);
    }
}
