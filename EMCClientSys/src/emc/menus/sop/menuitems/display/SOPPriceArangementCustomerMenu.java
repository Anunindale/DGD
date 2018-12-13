package emc.menus.sop.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.sop.display.pricearangement.customer.SOPPriceArangementCustomerForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class SOPPriceArangementCustomerMenu extends EMCMenuItem {

    /** Creates a new instance of SOPCustomerGroupMenu */
    public SOPPriceArangementCustomerMenu() {
        this.setClassPath(SOPPriceArangementCustomerForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Price List - Customer");
        this.setToolTipText("Setup customer price lists");
    }
}
