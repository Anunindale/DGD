/*
 * workflowMainMenu.java
 *
 * Created on 29 November 2007, 12:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package emc.menus.workflow;

import emc.enums.modules.enumEMCModules;
import emc.framework.EMCMenu;
import emc.menus.workflow.menuitems.display.activityMain;
import emc.menus.workflow.menuitems.display.jobsMaster;
import emc.menus.workflow.menuitems.display.workflowMaster;

/**
 *
 * @author rico
 */
public class workflowMainMenu extends EMCMenu {

    public workflowMainMenu() {
        this.setEmcModule(enumEMCModules.WORKFLOW);
        this.setMenuName(this.getEmcModule().toString());
        this.setMenuList(new activityMain());
        this.setMenuList(new jobsMaster());
        this.setMenuList(new workflowMaster());
        this.setMenuList(new workFlowEnquiry());
        this.setMenuList(new workFlowSetup());
        this.setMenuList(new workFlowSystem());
    }
}
