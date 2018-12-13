/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.pop.menuitems.output;

import emc.enums.enumMenuItems;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class GoodsReturned extends EMCMenuItem {

    /** Creates a new instance of GoodsReturned */
    public GoodsReturned() {
        this.setClassPath(emc.forms.pop.output.goodsreturned.GoodsReturnedPrintForm.class.getName());
        this.setMenuItemType(enumMenuItems.OUTPUT);
        this.setMenuItemName("Goods Returned");
        this.setToolTipText("Goods Returned");
    }

}
