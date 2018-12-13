package emc.menus.pop.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.pop.display.pricearrangements.POPPriceArrangementsForm;
import emc.framework.EMCMenuItem;

/** 
*
* @author riaan
*/
public class POPPriceArrangementsMI extends EMCMenuItem{

    /** Creates a new instance of POPPriceArrangementsMI. */
    public POPPriceArrangementsMI() {
        this.setClassPath(POPPriceArrangementsForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Price List");
        this.setToolTipText("View and Edit Price Lists");
    }
}