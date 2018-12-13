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
public class WorkHours extends EMCDouble{

    public WorkHours() {
        this.setMinValue(0);
        this.setNumberDecimalsDisplay(2);
        this.setNumberDecimalsInput(2);
        this.setEmcLabel("Work Hours");
    }
    
    

}
