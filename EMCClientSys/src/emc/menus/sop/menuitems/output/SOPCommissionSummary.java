/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.sop.menuitems.output;

import emc.enums.enumMenuItems;
import emc.forms.sop.output.SOPCommissionSummaryReport;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class SOPCommissionSummary extends EMCMenuItem {

    /** Creates a new instance of CustomerActivityMenu */
    public SOPCommissionSummary() {
        this.setMenuItemName("Commission By Rep");
        this.setClassPath(SOPCommissionSummaryReport.class.getName());
        this.setMenuItemType(enumMenuItems.OUTPUT);
        this.setToolTipText("Print out a commission summary by rep report.");
    }
}
