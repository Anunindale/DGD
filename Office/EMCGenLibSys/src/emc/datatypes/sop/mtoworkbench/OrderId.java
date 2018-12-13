/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.sop.mtoworkbench;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class OrderId extends EMCString{

    public OrderId() {
        this.setEmcLabel("Order");
        this.setStringSize(60);
    }
    
}
