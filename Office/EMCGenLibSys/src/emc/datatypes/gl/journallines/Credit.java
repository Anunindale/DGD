/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.gl.journallines;

import emc.datatypes.EMCBigDecimal;

/**
 *
 * @author wikus
 */
public class Credit extends EMCBigDecimal {

    public Credit() {
    	this.setColumnWidth(56);
        this.setEmcLabel("Credit");
    }
}
