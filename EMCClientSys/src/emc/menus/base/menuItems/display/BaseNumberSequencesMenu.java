/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.base.menuItems.display;

import emc.enums.enumMenuItems;
import emc.forms.base.display.numbersequence.BaseNumberSequenceForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author riaan
 */
public class BaseNumberSequencesMenu extends EMCMenuItem {

    /** Creates a new instance of BaseNumberSequencesMenu */
    public BaseNumberSequencesMenu() {
        this.setClassPath(BaseNumberSequenceForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Number Sequences");
        this.setToolTipText("Edit number sequences");
    }
}
