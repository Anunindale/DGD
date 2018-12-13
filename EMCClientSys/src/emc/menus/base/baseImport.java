/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.base;

import emc.framework.EMCMenu;
import emc.menus.base.menuItems.action.BaseCopyTableDataMI;
import emc.menus.base.menuItems.action.ImportWizardMenuItem;

/**
 *
 * @author wikus
 */
public class baseImport extends EMCMenu {

    public baseImport() {
        this.setMenuName("Import");
        this.setMenuList(new ImportWizardMenuItem());
        this.setMenuList(new BaseCopyTableDataMI());
    }
}
