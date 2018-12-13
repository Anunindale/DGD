/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.creditors.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.creditors.display.creditnote.CreditorsCreditNoteForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class CreditorsCreditNoteMI extends EMCMenuItem {

    public CreditorsCreditNoteMI() {

        this.setClassPath(CreditorsCreditNoteForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Credit Notes");
        this.setToolTipText("Edit Creditors credit notes");

    }
}
