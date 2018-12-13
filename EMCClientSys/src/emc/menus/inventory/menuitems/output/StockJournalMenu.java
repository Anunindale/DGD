/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.inventory.menuitems.output;

import emc.enums.enumMenuItems;
import emc.forms.inventory.output.stockjournals.StockJournalReportForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class StockJournalMenu extends EMCMenuItem {

    /** Creates a new instance of StockJournalMenu */
    public StockJournalMenu() {
       this.setClassPath(StockJournalReportForm.class.getName());
       this.setMenuItemType(enumMenuItems.OUTPUT);
       this.setMenuItemName("Stock Journals");
       this.setToolTipText("Prints a list of stock journals");
    }
}
