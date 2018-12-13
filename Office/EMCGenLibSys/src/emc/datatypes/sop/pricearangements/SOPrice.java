/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.sop.pricearangements;

import emc.datatypes.EMCBigDecimal;

/**
 *
 * @author wikus
 */
public class SOPrice extends EMCBigDecimal{

    public SOPrice() {
    	this.setColumnWidth(64);
        this.setEmcLabel("SO Price");
    }

}
