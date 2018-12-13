/*
 * baseReports.java
 *
 * Created on 19 September 2007, 03:09
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package emc.menus.base;

import emc.framework.EMCMenu;
import emc.menus.base.menuItems.output.BaseDBlogGenericReportMI;
import emc.menus.base.menuItems.output.BaseTaskSheetsReportMI;
import emc.menus.base.menuItems.output.BaseUserPermissionsReportMI;
import emc.menus.base.menuItems.output.PostalCodePrintMenuItem;
import emc.menus.base.menuItems.output.userlist;

/**
 *
 * @author rico
 */
public class baseReports extends EMCMenu {

    /**
     * Creates a new instance of baseReports
     */
    public baseReports() {
        this.setMenuName("Reports");
        this.setMenuList(new PostalCodePrintMenuItem());
        this.setMenuList(new userlist());
        this.setMenuList(new BaseUserPermissionsReportMI());
        this.setMenuList(new BaseDBlogGenericReportMI());
    }

}
