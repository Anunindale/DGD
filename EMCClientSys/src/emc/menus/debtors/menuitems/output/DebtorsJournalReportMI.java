/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.debtors.menuitems.output;

import emc.enums.enumMenuItems;
import emc.forms.debtors.output.DebtorsJournalReportForm;
import emc.framework.EMCMenuItem;

/**
 * @description : Menu item for the Debtors Journals report.
 *
 * @date        : 12 Aug 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class DebtorsJournalReportMI extends EMCMenuItem {

    /** Creates a new instance of DebtorsJournalReportMI */
    public DebtorsJournalReportMI() {
        this.setClassPath(DebtorsJournalReportForm.class.getName());
        this.setMenuItemType(enumMenuItems.OUTPUT);
        this.setMenuItemName("Debtors Journals");
        this.setToolTipText("Print Debtors Journals report.");
    }
}
