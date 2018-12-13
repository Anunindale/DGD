/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.developertools.trec;

import emc.enums.enumMenuItems;
import emc.forms.trec.display.trecPhrases.TrecPhrasesForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author rico
 */
public class TRECPhrasesMenu extends EMCMenuItem {

    public TRECPhrasesMenu() {
        this.setClassPath(TrecPhrasesForm.class.getName());
        this.setMenuItemName("Phrases");
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setToolTipText("Setup and manage phrases.");
    }
}