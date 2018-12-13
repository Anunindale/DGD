/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.sop.salesordermaster;

import emc.datatypes.systemwide.BigDecimalQuantity;

/**
 *
 * @author wikus
 */
public class UsedQuantity extends BigDecimalQuantity {

    public UsedQuantity() {
        this.setEmcLabel("Used");
        this.setColumnWidth(10);
    }
}
