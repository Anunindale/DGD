/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.base.menuItems.action;

import emc.forms.base.action.emcimportwizard.ColumnForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class ColumnsFormMenu extends EMCMenuItem {

    public ColumnsFormMenu() {
        this.setClassPath(ColumnForm.class.getName());
    }

    
}
