/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.base.menuItems.display;

import emc.enums.enumMenuItems;
import emc.forms.base.display.journalaccessgroups.JournalAccessGroupDefinitionsForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author riaan
 */
public class JournalAccessGroupDefinitionsMI extends EMCMenuItem {
    
    /** Creates a new instance of JournalAccessGroupDefinitionsMI. */
    public JournalAccessGroupDefinitionsMI() {
       this.setClassPath(JournalAccessGroupDefinitionsForm.class.getName());
       this.setMenuItemType(enumMenuItems.DISPLAY);
       this.setMenuItemName("Journal Access Group Definitions");
       this.setToolTipText("View and edit journal access group definitions.");
    }
}
