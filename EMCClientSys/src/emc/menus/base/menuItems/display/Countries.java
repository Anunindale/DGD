package emc.menus.base.menuItems.display;

import emc.enums.enumMenuItems;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */

public class Countries extends EMCMenuItem {
    
    /** Creates a new instance of PostalCodes */
    public Countries() {
        this.setClassPath("emc.forms.base.display.countries.countriesForm");
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Countries");
        this.setToolTipText("Edit countries");
    }
}