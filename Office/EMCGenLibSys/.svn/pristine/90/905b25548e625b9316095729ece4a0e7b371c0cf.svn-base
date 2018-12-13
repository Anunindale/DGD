/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.hr;

/**
 *
 * @author wikus
 */
public enum HRStatuses {

    CAPTURED(0, "Captured"),
    PENDING(1, "Pending"),
    COMPLETED(2, "Completed");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  HRDependantsTypes*/
    HRStatuses(final int id, final String label) {
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

    public static HRStatuses fromString(final String str) {
        for (HRStatuses e : HRStatuses.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static HRStatuses fromId(final int id) {
        for (HRStatuses e : HRStatuses.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
