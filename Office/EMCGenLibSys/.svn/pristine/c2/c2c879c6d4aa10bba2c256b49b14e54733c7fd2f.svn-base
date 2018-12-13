/*
 * enumMenuTypes.java
 *
 * Created on 18 September 2007, 11:19
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package emc.enums.base.calendar;

import emc.enums.*;

/**
 *
 * @author rico
 */
public enum CalendarShiftType {

    STANDARD(0, "STANDARD"),
    EXCEPTION(1, "EXCEPTION");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of DocumentTypes */
    CalendarShiftType(final int id, final String label) {
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

    public static CalendarShiftType fromString(final String str) {
        for (CalendarShiftType e : CalendarShiftType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static CalendarShiftType fromId(final int id) {
        for (CalendarShiftType e : CalendarShiftType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
