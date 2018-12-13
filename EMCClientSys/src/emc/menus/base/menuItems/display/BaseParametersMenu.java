/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.base.menuItems.display;

import emc.enums.enumMenuItems;
import emc.forms.base.display.parameters.BaseParametersForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class BaseParametersMenu extends EMCMenuItem {

    /** Creates a new instance of BaseParametersMenu */
    public BaseParametersMenu() {
        this.setClassPath(BaseParametersForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Parameters");
        this.setToolTipText("Setup the base parameters");
    }
}
