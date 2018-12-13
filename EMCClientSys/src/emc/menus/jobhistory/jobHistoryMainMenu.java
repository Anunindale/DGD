/*
 * jobHistoryMainMenu.java
 *
 * Created on 29 November 2007, 12:36
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package emc.menus.jobhistory;

import emc.enums.modules.enumEMCModules;
import emc.framework.EMCMenu;

/**
 *
 * @author rico
 */
public class jobHistoryMainMenu extends EMCMenu {
    
    /** Creates a new instance of jobHistoryMainMenu */
    public jobHistoryMainMenu() {
        this.setEmcModule(enumEMCModules.JOBHISTORY);
        this.setMenuName(this.getEmcModule().toString());
    }
    
}
