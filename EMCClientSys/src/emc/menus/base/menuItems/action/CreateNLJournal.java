/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.base.menuItems.action;

import emc.enums.enumMenuItems;
import emc.framework.EMCMenuItem;

/**
 *
 * @author rico
 */
public class CreateNLJournal extends EMCMenuItem {
    public CreateNLJournal(){
        this.setClassPath(emc.forms.developertools.display.createNLonHandJournal.createNLJournalForm.class.getName());
        this.setMenuItemType(enumMenuItems.ACTION);
        this.setMenuItemName("Create N&L take on Journal");
        this.setToolTipText("Special.");
    }
}
