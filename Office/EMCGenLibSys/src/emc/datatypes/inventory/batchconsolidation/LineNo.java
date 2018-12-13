/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.batchconsolidation;

import emc.datatypes.EMCBigDecimal;

/**
 *
 * @author wikus
 */
public class LineNo extends EMCBigDecimal {
    
    public LineNo() {
        this.setEmcLabel("Line No");
        this.setColumnWidth(15);
    }
}
