/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.trec.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.trec.display.odours.TRECOdoursForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class TRECOdoursMenu extends EMCMenuItem {

    public TRECOdoursMenu() {
        this.setClassPath(TRECOdoursForm.class.getName());
        this.setMenuItemName("Odours");
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setToolTipText("Setup and manage odours.");
    }
}
