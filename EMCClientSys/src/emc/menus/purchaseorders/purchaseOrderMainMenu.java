/*
 * purchaseOrderMainMenu.java
 *
 * Created on 29 November 2007, 11:50
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package emc.menus.purchaseorders;

import emc.enums.modules.enumEMCModules;
import emc.framework.EMCMenu;


/**
 *
 * @author rico
 */
public class purchaseOrderMainMenu extends EMCMenu {
    
    /** Creates a new instance of purchaseOrderMainMenu */
    public purchaseOrderMainMenu()  {
        this.setEmcModule(enumEMCModules.POP);
        this.setMenuName(this.getEmcModule().toString());
    }
    
}
