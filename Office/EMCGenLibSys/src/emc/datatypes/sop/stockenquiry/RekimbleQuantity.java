/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.sop.stockenquiry;

import emc.datatypes.EMCBigDecimal;

/**
 *
 * @author ivan
 */
public class RekimbleQuantity extends EMCBigDecimal{
    
    public RekimbleQuantity(){
        this.setEmcLabel("Rekimble Qty");
        this.setColumnWidth(10);
    }
    
}
