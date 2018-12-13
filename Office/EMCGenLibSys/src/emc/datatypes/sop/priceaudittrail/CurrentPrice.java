/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.sop.priceaudittrail;

import emc.datatypes.EMCBigDecimal;

/**
 *
 * @author wikus
 */
public class CurrentPrice extends EMCBigDecimal {

    public CurrentPrice() {
    	this.setColumnWidth(50);
        this.setEmcLabel("Current List");
    }
}
