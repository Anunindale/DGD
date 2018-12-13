/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.pop.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.pop.display.returnreason.POPGoodsReturnReasonForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class POPGoodsReturnReasonMenu extends EMCMenuItem {

    public POPGoodsReturnReasonMenu() {
        this.setClassPath(POPGoodsReturnReasonForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Goods Return Reason");
        this.setToolTipText("Setup and change the goods returned reasons");
    }
}
