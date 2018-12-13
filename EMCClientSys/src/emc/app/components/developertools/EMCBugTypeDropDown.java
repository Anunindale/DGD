/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.app.components.developertools;

import emc.app.components.emcJComboBox;
import emc.enums.developertools.DevBugType;

/**
 *
 * @author claudette
 */
public class EMCBugTypeDropDown extends emcJComboBox {

    public EMCBugTypeDropDown() {
        super();
        DevBugType[] type = DevBugType.values();

        for (DevBugType types : type) {
            this.addItem(types.toString());
        }
    }
}
