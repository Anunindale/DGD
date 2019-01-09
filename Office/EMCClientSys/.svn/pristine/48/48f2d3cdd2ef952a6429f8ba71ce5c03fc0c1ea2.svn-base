/*
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package emc.menus.crm;

import emc.enums.modules.enumEMCModules;
import emc.framework.EMCMenu;
import emc.menus.crm.menuitems.display.CRMProspectMenu;

/**
 *
 * @author wikus
 */
public class CRMMainMenu extends EMCMenu {

    /** Creates a new instance of CRMMainMenu */
    public CRMMainMenu() {
        this.setEmcModule(enumEMCModules.CRM);
        this.setMenuName(this.getEmcModule().toString());
        this.setMenuList(new CRMProspectMenu());
        this.setMenuList(new CRMSetup());
        this.setMenuList(new CRMSystem());
    }
}
