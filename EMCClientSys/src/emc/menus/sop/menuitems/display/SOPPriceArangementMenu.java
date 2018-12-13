package emc.menus.sop.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.sop.display.pricearangement.SOPPriceArangementForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class SOPPriceArangementMenu extends EMCMenuItem {

    /** Creates a new instance of SOPCustomerGroupMenu */
    public SOPPriceArangementMenu() {
        this.setClassPath(SOPPriceArangementForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Price List");
        this.setToolTipText("Setup price lists");
    }
}
