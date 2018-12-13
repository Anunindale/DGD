/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.debtors.menuitems.action;

import emc.enums.enumMenuItems;
import emc.forms.debtors.action.EdconStatementForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author riaan
 */
public class DebtorsEdconStatementMI extends EMCMenuItem {

    /** Creates a new instance of DebtorsEdconStatementMI. */
    public DebtorsEdconStatementMI() {
        this.setClassPath(EdconStatementForm.class.getName());
        this.setMenuItemType(enumMenuItems.ACTION);
        this.setMenuItemName("Edcon Statement Export");
        this.setToolTipText("Export an Edcon statement.");
    }
}
