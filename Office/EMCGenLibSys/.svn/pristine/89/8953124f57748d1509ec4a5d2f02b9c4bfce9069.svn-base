/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.base.employees;

/**
 *
 * @author riaan
 */
public enum BaseMaritalStatus {

    SINGLE(0, "Single"),
    MARRIED(1, "Married"),
    DIVORCED(2, "Divorced");

    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of BaseMaritalStatus */
    BaseMaritalStatus(final int id, final String label) {
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

    public static BaseMaritalStatus fromString(final String str) {
        for (BaseMaritalStatus e : BaseMaritalStatus.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static BaseMaritalStatus fromId(final int id) {
        for (BaseMaritalStatus e : BaseMaritalStatus.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }

}
