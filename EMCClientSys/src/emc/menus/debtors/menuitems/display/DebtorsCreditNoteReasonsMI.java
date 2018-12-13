/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.debtors.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.debtors.display.creditnotereasons.CreditNoteReasonsForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author riaan
 */
public class DebtorsCreditNoteReasonsMI extends EMCMenuItem {

    /** Creates a new instance of DebtorsCreditNoteReasonsMI */
    public DebtorsCreditNoteReasonsMI() {
        this.setClassPath(CreditNoteReasonsForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Credit Note Reasons");
        this.setToolTipText("Edit Credit Note Reasons");
    }
}
