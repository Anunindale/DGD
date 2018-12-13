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
public class PriceListPrice extends EMCBigDecimal{

    public PriceListPrice() {
    	this.setColumnWidth(66);
        this.setEmcLabel("Price List");
    }

}
