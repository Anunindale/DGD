/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.sop.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.sop.display.salesrepgroups.SOPSalesRepGroupsForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class SOPSalesRepGroupsMenu extends EMCMenuItem {

    /** Creates a new instance of  SOPSalesRepGroupsMenu*/
    public SOPSalesRepGroupsMenu() {
        this.setMenuItemName("Sales Rep Groups");
        this.setClassPath(SOPSalesRepGroupsForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setToolTipText("Setup sales rep groups.");
    }
}
