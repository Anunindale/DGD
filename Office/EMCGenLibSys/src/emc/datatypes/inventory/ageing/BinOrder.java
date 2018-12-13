/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.ageing;

import emc.datatypes.EMCInt;

/**
 *
 * @author rico
 */
public class BinOrder extends EMCInt {
    public BinOrder(){
        this.setMandatory(true);
        this.setEmcLabel("Order");
        this.setColumnWidth(5);
    }
}
