/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.systemwide;

import emc.datatypes.EMCBigDecimal;

/**
 *
 * @author Wikus
 */
public class BigDecimalQuantity extends EMCBigDecimal {

    /** Creates a new instance of Quantity. */
    public BigDecimalQuantity() {
        this.setEmcLabel("Qty");
        this.setColumnWidth(10);
    }
}
