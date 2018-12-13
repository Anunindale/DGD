/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.app.components.base;

import emc.app.components.emcJComboBox;
import emc.enums.modules.enumEMCModules;

/**
 * @name        EMCModuleDropDown
 *
 * @date        20 Apr 2009
 *
 * @desciption  A drop down list containing identifiers for EMCModules.
 *
 * @author      riaan
 */
public class EMCModuleDropDown extends emcJComboBox {

    /** Creates a new instance of EMCModuleDropDown. */
    public EMCModuleDropDown() {
        super();
        enumEMCModules[] modules = enumEMCModules.values();

        for (enumEMCModules module : modules) {
            this.addItem(module.toString());
        }
    }

}
