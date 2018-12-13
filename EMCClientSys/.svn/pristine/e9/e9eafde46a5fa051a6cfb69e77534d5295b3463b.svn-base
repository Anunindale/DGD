/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.developertools;

import emc.framework.EMCMenu;
import emc.menus.base.menuItems.output.BaseTaskSheetsReportMI;
import emc.menus.developertools.menuitems.output.BugListReportMenu;
import emc.menus.developertools.menuitems.output.BugListReportMenuWithoutTimeSheet;
import emc.menus.developertools.menuitems.output.DevDevelopmentSummaryMI;
import emc.menus.developertools.menuitems.output.DevRequirementsSheetMI;
import emc.menus.developertools.menuitems.output.TimeSheetReportMenu;

/**
 *
 * @author wikus
 */
public class DevToolsReports extends EMCMenu {

    /**
     * Creates a new instance of DevToolsReports
     */
    public DevToolsReports() {
        this.setMenuName("Reports");        
        this.setMenuList(new DevDevelopmentSummaryMI());   
        this.setMenuList(new DevRequirementsSheetMI());  
        this.setMenuList(new BugListReportMenu());
        this.setMenuList(new BugListReportMenuWithoutTimeSheet());
        this.setMenuList(new TimeSheetReportMenu());           
        this.setMenuList(new BaseTaskSheetsReportMI());

    }

}
