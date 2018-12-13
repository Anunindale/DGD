/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.timebyday;

import emc.datatypes.EMCDate;

/**
 *
 * @author wikus
 */
public class ActualDate extends EMCDate{

    public ActualDate() {
        this.setEmcLabel("Actual Date");
        this.setMandatory(true);
    }

}
