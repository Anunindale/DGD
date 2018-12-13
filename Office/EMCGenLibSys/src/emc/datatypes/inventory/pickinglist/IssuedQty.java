/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.pickinglist;

/**
 *
 * @author wikus
 */
public class IssuedQty extends emc.datatypes.systemwide.Quantity{

    public IssuedQty() {
    	this.setColumnWidth(61);
        this.setEmcLabel("Issued");
    }
    
    
    

}
