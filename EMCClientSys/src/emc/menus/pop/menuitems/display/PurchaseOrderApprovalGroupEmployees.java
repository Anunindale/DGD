/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.pop.menuitems.display;
import emc.enums.enumMenuItems;
import emc.forms.pop.display.purchaseorders.approvalgroupsemployees.PurchaseOrderApprovalGroupEmployeesForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class PurchaseOrderApprovalGroupEmployees extends EMCMenuItem {
    
    public PurchaseOrderApprovalGroupEmployees() {
        this.setClassPath(PurchaseOrderApprovalGroupEmployeesForm.class.getName());
        this.setMenuItemName("Purchase Order Approval Groups Employees");
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setToolTipText("Set the employees for approval groups");
    }

}
