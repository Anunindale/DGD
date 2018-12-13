/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.debtors.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.debtors.display.marketinggroup.MarketingGroupForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author riaan
 */
public class DebtorsMarketingGroup extends EMCMenuItem {

    /** Creates a new instance of DebtorsMarketingGroup */
    public DebtorsMarketingGroup() {
        this.setClassPath(MarketingGroupForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Marketing Groups");
        this.setToolTipText("Create and edit marketing groups.");
    }
}
