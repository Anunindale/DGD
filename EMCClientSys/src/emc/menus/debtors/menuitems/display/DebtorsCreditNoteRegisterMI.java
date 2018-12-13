/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.debtors.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.debtors.display.creditnoteregister.DebtorsCreditNoteRegisterForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author riaan
 */
public class DebtorsCreditNoteRegisterMI extends EMCMenuItem {

    /** Creates a new instance of DebtorsCreditNotesMI */
    public DebtorsCreditNoteRegisterMI() {
        this.setClassPath(DebtorsCreditNoteRegisterForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Credit Note Register");
        this.setToolTipText("Register items against a Credit Note line.");
        this.setDoNotOpenForm(true);
    }
}
