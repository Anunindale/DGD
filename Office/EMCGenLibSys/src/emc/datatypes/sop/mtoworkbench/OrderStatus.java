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
public class OrderStatus extends EMCString{

    public OrderStatus() {
        this.setEmcLabel("Status");
        this.setStringSize(60);
    }
    
}
