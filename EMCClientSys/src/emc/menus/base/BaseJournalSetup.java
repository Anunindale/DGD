/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.base;

import emc.framework.EMCMenu;
import emc.menus.base.menuItems.display.JournalAccessGroupDefinitionsMI;
import emc.menus.base.menuItems.display.JournalAccessGroupEmployeesMI;
import emc.menus.base.menuItems.display.JournalAccessGroupsMI;
import emc.menus.base.menuItems.display.JournalApprovalGroupEmployees;
import emc.menus.base.menuItems.display.JournalApprovalGroups;

/**
 *
 * @author wikus
 */
public class BaseJournalSetup extends EMCMenu {

    public BaseJournalSetup() {
        this.setMenuName("Mail");
        this.setMenuName("Journals");
        this.setMenuList(new JournalAccessGroupsMI());
        //this.setMenuList(new JournalAccessGroupDefinitionsMI());
        this.setMenuList(new JournalAccessGroupEmployeesMI());
        this.setMenuList(new JournalApprovalGroups());
        this.setMenuList(new JournalApprovalGroupEmployees());
    }
}
