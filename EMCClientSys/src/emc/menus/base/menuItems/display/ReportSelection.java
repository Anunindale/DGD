/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.base.menuItems.display;

import emc.app.reporttools.selection.ReportSelectionForm;
import emc.enums.enumMenuItems;
import emc.framework.EMCMenuItem;

/**
 *
 * @author riaan
 */
public class ReportSelection extends EMCMenuItem {

    public ReportSelection() {
        this.setClassPath(ReportSelectionForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setDoNotOpenForm(true);
        this.setMenuItemName("Report Selection");
        this.setToolTipText("Setup report query");
    }
}
