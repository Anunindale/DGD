/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.developertools;

import emc.enums.enumMenuItems;
import emc.framework.EMCMenuItem;

/**
 *
 * @author riaan
 */
public class DataTypeWizard extends EMCMenuItem {
    
/** Creates a new instance of DataTypeWizard */
    public DataTypeWizard() {
        this.setClassPath("emc.forms.developertools.display.emcdatatypewizard.DataTypeWizardForm");
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Data Type Wizard");
        this.setToolTipText("Display the EMC Data Type Wizard form");
    }
}
