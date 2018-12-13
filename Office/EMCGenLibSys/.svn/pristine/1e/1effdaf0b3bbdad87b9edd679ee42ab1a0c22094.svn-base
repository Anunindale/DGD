/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.base.employees;

/**
 *
 * @author riaan
 */
public enum BaseWorkTimeTypes {

    FIXED(0, "Fixed"),
    FLEXI(1, "FLEXI");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of BaseMaritalStatus */
    BaseWorkTimeTypes(final int id, final String label) {
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

    public static BaseWorkTimeTypes fromString(final String str) {
        for (BaseWorkTimeTypes e : BaseWorkTimeTypes.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static BaseWorkTimeTypes fromId(final int id) {
        for (BaseWorkTimeTypes e : BaseWorkTimeTypes.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
