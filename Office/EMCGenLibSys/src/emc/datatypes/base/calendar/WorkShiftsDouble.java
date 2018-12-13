/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.calendar;

import emc.datatypes.EMCDouble;

/**
 *
 * @author wikus
 */
public class WorkShiftsDouble extends EMCDouble{

    public WorkShiftsDouble() {
        this.setMinValue(0);
        this.setNumberDecimalsDisplay(0);
        this.setNumberDecimalsInput(0);
        this.setEmcLabel("Num. Shifts");
    }
    
    

}
