package emc.menus.debtors.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.debtors.display.creditstopreason.DebtorsCreditStopReasonForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class DebtorsCreditStopReasonMenu extends EMCMenuItem {

    /** Creates a new instance of DebtorsCustomerGroupMenu */
    public DebtorsCreditStopReasonMenu() {
        this.setClassPath(DebtorsCreditStopReasonForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Credit Stop Reason");
        this.setToolTipText("Create and edit credit stop reasons");
    }
}
