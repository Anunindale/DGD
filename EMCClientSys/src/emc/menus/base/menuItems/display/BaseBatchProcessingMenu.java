/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.base.menuItems.display;

import emc.enums.enumMenuItems;
import emc.forms.base.display.batchprocessing.BaseBatchProcessingForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author riaan
 */
public class BaseBatchProcessingMenu extends EMCMenuItem {

    /** Creates a new instance of AdminUsers */
    public BaseBatchProcessingMenu() {
        this.setClassPath(BaseBatchProcessingForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Batch Processing");
        this.setToolTipText("Monitor Batch Processing");
    }
}
