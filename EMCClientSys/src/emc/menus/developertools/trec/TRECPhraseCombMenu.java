/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.developertools.trec;

import emc.enums.enumMenuItems;
import emc.forms.trec.display.phrasecomb.TrecPhraseCombForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author rico
 */
public class TRECPhraseCombMenu extends EMCMenuItem {

    public TRECPhraseCombMenu() {
        this.setClassPath(TrecPhraseCombForm.class.getName());
        this.setMenuItemName("Phrase Combinations");
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setToolTipText("Setup and manage Phrase Combinations.");
    }
}