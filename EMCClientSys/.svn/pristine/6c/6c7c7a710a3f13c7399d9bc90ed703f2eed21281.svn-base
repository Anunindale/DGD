/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.app.components.hr;

import emc.app.components.emcJComboBox;
import emc.enums.hr.HRStatuses;

/**
 *
 * @author wikus
 */
public class HRStatusDropDown extends emcJComboBox {

    public HRStatusDropDown() {
        super();
        HRStatuses[] types = HRStatuses.values();

        for (HRStatuses type : types) {
            this.addItem(type.toString());
        }
    }
}
