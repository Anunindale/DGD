package emc.menus.creditors.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.creditors.display.approvalgroups.CreditorsApprovalGroupsForm;
import emc.framework.EMCMenuItem;

/** 
*
* @author claudette
*/
public class CreditorsApprovalGroupsMI extends EMCMenuItem{

    /** Creates a new instance of CreditorsApprovalGroupsMI. */
    public CreditorsApprovalGroupsMI() {
        this.setClassPath(CreditorsApprovalGroupsForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Approval Groups");
        this.setToolTipText("View and Edit Approval Groups data");
    }
}