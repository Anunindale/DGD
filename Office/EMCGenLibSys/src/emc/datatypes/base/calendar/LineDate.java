/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.calendar;

import emc.datatypes.EMCDate;

/**
 *
 * @author wikus
 */
public class LineDate extends EMCDate{
    
    public LineDate() {
        this.setEmcLabel("Date");
        this.setMandatory(true);
    }

}
