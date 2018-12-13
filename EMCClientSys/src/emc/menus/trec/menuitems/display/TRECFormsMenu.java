/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.trec.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.trec.display.forms.TRECFormsForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class TRECFormsMenu extends EMCMenuItem {

    public TRECFormsMenu() {
        this.setClassPath(TRECFormsForm.class.getName());
        this.setMenuItemName("Forms");
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setToolTipText("Setup and manage forms.");
    }
}
