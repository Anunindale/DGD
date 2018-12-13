/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.app.components.pop.purchaseorders;

import emc.enums.pop.purchaseorders.PurchaseOrderStatus;
import javax.swing.JComboBox;

/** 
 *
 * @author riaan
 */
public class PurchaseOrderStatusDropdown extends JComboBox {

    /** Creates a new instance of PurchaseOrderStatusDropdown */
    public PurchaseOrderStatusDropdown() {
        super(new String[] {PurchaseOrderStatus.APPROVED.toString(), PurchaseOrderStatus.REQUISITION.toString(), 
                            PurchaseOrderStatus.PARTIALLY_RECEIVED.toString(), PurchaseOrderStatus.RECEIVED.toString(), 
                            PurchaseOrderStatus.INVOICED.toString()});
    }
}
