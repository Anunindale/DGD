package emc.menus.sop.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.sop.display.customergroup.SOPCustomerGroupForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class SOPCustomerGroupMenu extends EMCMenuItem {

    /** Creates a new instance of SOPCustomerGroupMenu */
    public SOPCustomerGroupMenu() {
        this.setClassPath(SOPCustomerGroupForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Debtor Groups");
        this.setToolTipText("Create and edit debtor groups");
    }
}
