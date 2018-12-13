/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.pop.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.pop.display.qualitytest.QualityTestTypeForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class QualityTestTypes extends EMCMenuItem {
    
    public QualityTestTypes() {
        this.setClassPath(QualityTestTypeForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Quality Test Types");
        this.setToolTipText("Setup and change Quality Test Types");
    }

}
