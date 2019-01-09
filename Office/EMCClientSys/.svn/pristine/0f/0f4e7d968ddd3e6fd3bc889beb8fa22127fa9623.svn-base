/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.app.components.base;

import emc.app.components.emcJComboBox;
import emc.enums.base.timedoperations.EnumTimedOperations;

/**
 *
 * @author wikus
 */
public class EMCTimedOperationEnumDropDown extends emcJComboBox {

    /** Creates a new instance of EMCTimedOperationEnumDropDown. */
    public EMCTimedOperationEnumDropDown() {
        super();
        EnumTimedOperations[] operations = EnumTimedOperations.values();

        for (EnumTimedOperations operation : operations) {
            this.addItem(operation.toString());
        }
    }
}
