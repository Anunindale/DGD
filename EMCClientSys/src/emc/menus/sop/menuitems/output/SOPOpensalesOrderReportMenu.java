/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.sop.menuitems.output;

import emc.enums.enumMenuItems;
import emc.forms.sop.output.SOPOpenSalesOrdersForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class SOPOpensalesOrderReportMenu extends EMCMenuItem {

    /** Creates a new instance of CustomerActivityMenu */
    public SOPOpensalesOrderReportMenu() {
        this.setMenuItemName("Open Sales Orders");
        this.setClassPath(SOPOpenSalesOrdersForm.class.getName());
        this.setMenuItemType(enumMenuItems.OUTPUT);
        this.setToolTipText("Print the open sales orders.");
    }
}
