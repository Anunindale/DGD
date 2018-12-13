/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.inventory.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.inventory.display.requirementsplanning.InventoryRequirementsPlanningHistoryForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class InventoryRequirementsPlanningHistoryMenu extends EMCMenuItem {

    public InventoryRequirementsPlanningHistoryMenu() {
        this.setClassPath(InventoryRequirementsPlanningHistoryForm.class.getName());
        this.setMenuItemName("Requirements Planning History");
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setToolTipText("View the requirements planning history data");
    }
}
