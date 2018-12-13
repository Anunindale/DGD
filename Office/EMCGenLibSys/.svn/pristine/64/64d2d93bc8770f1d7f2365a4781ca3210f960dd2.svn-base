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
public enum Months {

    JANUARY(Calendar.JANUARY, "Jan"),
    FEBRUARY(Calendar.FEBRUARY, "Feb"),
    MARCH(Calendar.MARCH, "Mar"),
    APRIL(Calendar.APRIL, "Apr"),
    MAY(Calendar.MAY, "May"),
    JUNE(Calendar.JUNE, "Jun"),
    JULY(Calendar.JULY, "Jul"),
    AUGUST(Calendar.AUGUST, "Aug"),
    SEPTEMBER(Calendar.SEPTEMBER, "Sep"),
    OCTOBER(Calendar.OCTOBER, "Oct"),
    NOVEMBER(Calendar.NOVEMBER, "Nov"),
    DECEMBER(Calendar.DECEMBER, "Dec");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of DocumentTypes */
    Months(final int id, final String label) {
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

    public static Months fromString(final String str) {
        for (Months e : Months.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static Months fromId(final int id) {
        for (Months e : Months.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
