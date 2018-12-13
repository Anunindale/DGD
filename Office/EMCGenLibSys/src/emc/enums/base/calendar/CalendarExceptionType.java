/*
 * enumMenuTypes.java
 *
 * Created on 18 September 2007, 11:19
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package emc.enums.base.calendar;

/**
 *
 * @author rico
 */
public enum CalendarExceptionType {

    HOLIDAY(0, "Holiday"),
    OVER_TIME(1, "Over Time"),
    MAINTENANCE(2, "Maintenance"),
    CLOSURE(3, "Closure"),
    SHORT_TIME(4, "Short Time"),
    NIGHT_SHIFT(5, "Night Shift"),
    TARGET_PERCENTAGE(6, "Target Percentage");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of DocumentTypes */
    CalendarExceptionType(final int id, final String label) {
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

    public static CalendarExceptionType fromString(final String str) {
        for (CalendarExceptionType e : CalendarExceptionType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static CalendarExceptionType fromId(final int id) {
        for (CalendarExceptionType e : CalendarExceptionType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
