/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.sop.stockenquiry;

import emc.datatypes.systemwide.BigDecimalQuantity;

/**
 *
 * @author wikus
 */
public class BackOrderQty extends BigDecimalQuantity {

    public BackOrderQty() {
        this.setEmcLabel("Back Order");
        this.setColumnWidth(10);
    }
}
