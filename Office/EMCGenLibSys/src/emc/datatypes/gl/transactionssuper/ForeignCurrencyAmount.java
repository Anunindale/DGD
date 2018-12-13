/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.gl.transactionssuper;

import emc.datatypes.EMCBigDecimal;

/**
 *
 * @author wikus
 */
public class ForeignCurrencyAmount extends EMCBigDecimal {

    public ForeignCurrencyAmount() {
        this.setEmcLabel("Foreign Amount");
        this.setEditable(false);
    }
}
