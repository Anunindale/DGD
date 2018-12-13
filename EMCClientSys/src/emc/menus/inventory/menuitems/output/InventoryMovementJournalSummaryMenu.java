/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.inventory.menuitems.output;

import emc.enums.enumMenuItems;
import emc.forms.inventory.output.journal.InventoryJournalMovementSummaryReportFrame;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class InventoryMovementJournalSummaryMenu extends EMCMenuItem {

    public InventoryMovementJournalSummaryMenu() {
        this.setClassPath(InventoryJournalMovementSummaryReportFrame.class.getName());
        this.setMenuItemType(enumMenuItems.OUTPUT);
        this.setMenuItemName("Movement Journal - Summary");
        this.setToolTipText("Movement Journal - Summary");
    }
}
