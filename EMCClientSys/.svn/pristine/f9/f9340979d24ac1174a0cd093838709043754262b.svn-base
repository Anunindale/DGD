/*
 * mainMenu.java
 *
 * Created on 17 September 2007, 02:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package emc.menus;

import emc.framework.EMCMenu;
import emc.framework.EMCMenuReference;
import emc.menus.crm.CRMMainMenu;
import emc.menus.dangerousgoods.menuitems.DGMainMenu;
import emc.menus.developertools.DeveloperToolsMainMenu;
import emc.menus.hr.HRMainMenu;
import emc.menus.trec.TRECMainMenu;

/**
 *
 * @author rico
 */
public class mainMenu extends EMCMenu {

    private EMCMenuReference globalmenu = new EMCMenuReference("emc.menus.base.baseMainMenu");
    private EMCMenuReference personalMenu = new EMCMenuReference("emc.menus.personal.personalMainMenu");
    private EMCMenuReference glMainMenu = new EMCMenuReference("emc.menus.gl.glMainMenu");
    private EMCMenuReference debtorsMainMenu = new EMCMenuReference("emc.menus.debtors.debtorsMainMenu");
    private EMCMenuReference creditorsMainMenu = new EMCMenuReference("emc.menus.creditors.creditorsMainMenu");
    private EMCMenuReference salesOrderMainMenu = new EMCMenuReference(emc.menus.sop.SOPMainMenu.class.getName());
    private EMCMenuReference purchaseOrderMainMenu = new EMCMenuReference("emc.menus.pop.POPMainMenu");
    private EMCMenuReference inventorycontrolMainMenu = new EMCMenuReference("emc.menus.inventory.InventoryControlMainMenu");
    private EMCMenuReference workflowMM = new EMCMenuReference("emc.menus.workflow.workflowMainMenu");
    private EMCMenuReference developerToolsMainMenu = new EMCMenuReference(DeveloperToolsMainMenu.class.getName());
    private EMCMenuReference HRMainMenu = new EMCMenuReference(HRMainMenu.class.getName());
    private EMCMenuReference CRMMainMenu = new EMCMenuReference(CRMMainMenu.class.getName());
    private EMCMenuReference TRECMainMenu = new EMCMenuReference(TRECMainMenu.class.getName());
    private EMCMenuReference dgMainMenu = new EMCMenuReference(DGMainMenu.class.getName());

    public mainMenu() {
        this.setMenuList(globalmenu);
        this.setMenuList(creditorsMainMenu);
        this.setMenuList(CRMMainMenu);
        this.setMenuList(dgMainMenu);
        this.setMenuList(debtorsMainMenu);
        this.setMenuList(developerToolsMainMenu);
        this.setMenuList(glMainMenu);
        this.setMenuList(HRMainMenu);
        this.setMenuList(inventorycontrolMainMenu);
        this.setMenuList(personalMenu);
        this.setMenuList(purchaseOrderMainMenu);
        this.setMenuList(salesOrderMainMenu);
        this.setMenuList(TRECMainMenu);
        this.setMenuList(workflowMM);
    }
}
