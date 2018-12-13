/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.trec.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.trec.display.treccards.TRECTrecCardsForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class TRECTrecCardsMenu extends EMCMenuItem {

    public TRECTrecCardsMenu() {
        this.setClassPath(TRECTrecCardsForm.class.getName());
        this.setMenuItemName("Trec");
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setToolTipText("Setup and manage trec cards.");
    }
}
