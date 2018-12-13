/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.calendar;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class CalendarId extends EMCString{

    public CalendarId() {
        this.setColumnWidth(60);
        this.setEmcLabel("Calendar");
        this.setMandatory(true);
    }

    
    
}
