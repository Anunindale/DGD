/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.crm;

import emc.framework.EMCMenu;
import emc.menus.crm.menuitems.display.CRMClassification1MI;
import emc.menus.crm.menuitems.display.CRMClassification2MI;
import emc.menus.crm.menuitems.display.CRMClassification3MI;
import emc.menus.crm.menuitems.display.CRMCorrespondenceLogMenu;
import emc.menus.crm.menuitems.display.CRMDocumentsMenu;
import emc.menus.crm.menuitems.display.CRMInterestGroupMI;
import emc.menus.crm.menuitems.display.CRMInterestMenu;
import emc.menus.crm.menuitems.display.CRMNumberSequenceMenu;
import emc.menus.crm.menuitems.display.CRMParametersMenu;
import emc.menus.crm.menuitems.display.CRMProspectCloseReasonMI;

/**
 *
 * @author wikus
 */
public class CRMSetup extends EMCMenu {

    /** Creates a new instance of CRMSetup*/
    public CRMSetup() {
        this.setMenuName("Setup");
        this.setMenuList(new CRMCorrespondenceLogMenu());
        this.setMenuList(new CRMDocumentsMenu());
        this.setMenuList(new CRMInterestMenu());
        this.setMenuList(new CRMInterestGroupMI());
        this.setMenuList(new CRMNumberSequenceMenu());
        this.setMenuList(new CRMParametersMenu());
        this.setMenuList(new CRMProspectCloseReasonMI());
        this.setMenuList(new CRMClassification1MI());
        this.setMenuList(new CRMClassification2MI());
        this.setMenuList(new CRMClassification3MI());
    }
}
