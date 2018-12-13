/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.base.menuItems.display;

import emc.enums.enumMenuItems;
import emc.forms.base.display.language.BaseLanguageForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class BaseLanguageMenu extends EMCMenuItem {

    public BaseLanguageMenu() {
        this.setClassPath(BaseLanguageForm.class.getName());
        this.setMenuItemName("Language");
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setToolTipText("Setup and manage languages.");
    }
}
