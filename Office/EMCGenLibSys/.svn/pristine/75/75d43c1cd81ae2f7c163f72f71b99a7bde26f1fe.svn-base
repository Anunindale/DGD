/*
 * enumMenuTypes.java
 *
 * Created on 18 September 2007, 11:19
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package emc.enums.base.calendar;

import java.util.Calendar;

/**
 *
 * @author rico
 */
public enum WeekDays {

    MONDAY(Calendar.MONDAY, "Mon"),
    TUESDAY(Calendar.TUESDAY, "Tue"),
    WEDNESDAY(Calendar.WEDNESDAY, "Wed"),
    THURSDAY(Calendar.THURSDAY, "Thu"),
    FRIDAY(Calendar.FRIDAY, "Fri"),
    SATURDAY(Calendar.SATURDAY, "Sat"),
    SUNDAY(Calendar.SUNDAY, "Sun");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of DocumentTypes */
    WeekDays(final int id, final String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return label;
    }

    public static WeekDays fromString(final String str) {
        for (WeekDays e : WeekDays.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static WeekDays fromId(final int id) {
        for (WeekDays e : WeekDays.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
