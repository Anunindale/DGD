/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.sop.menuitems.output;

import emc.enums.enumMenuItems;
import emc.forms.sop.output.SOPControlSheetReportForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class SOPControlSheet extends EMCMenuItem {

    /** Creates a new instance of CustomerActivityMenu */
    public SOPControlSheet() {
        this.setMenuItemName("Control Sheet");
        this.setClassPath(SOPControlSheetReportForm.class.getName());
        this.setMenuItemType(enumMenuItems.OUTPUT);
        this.setToolTipText("Print out the sales order control sheet.");
    }
}
