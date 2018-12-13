/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.hr.leavetype;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class LeaveType extends EMCString{

    public LeaveType() {
        this.setEmcLabel("Leave Type");
        this.setMandatory(true);
    }

}
