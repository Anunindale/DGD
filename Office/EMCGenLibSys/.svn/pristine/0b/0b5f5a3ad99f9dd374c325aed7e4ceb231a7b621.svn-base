/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.calendar;

import emc.entity.base.calendar.BaseCalendar;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author wikus
 */
public class CalendarIdFK extends CalendarId{

    public CalendarIdFK() {
        this.setRelatedTable(BaseCalendar.class.getName());
        this.setRelatedField("calendarId");
        this.setDeleteAction(enumDeleteUpdateOptions.CASCADE);
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
    }
    
    

}
