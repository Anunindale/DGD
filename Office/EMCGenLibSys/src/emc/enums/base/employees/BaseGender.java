/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.base.employees;

/**
 *
 * @author riaan
 */
public enum BaseGender {

    M(0, "Male"),
    F(1, "Female");

    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of BaseMaritalStatus */
    BaseGender(final int id, final String label) {
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

    public static BaseGender fromString(final String str) {
        for (BaseGender e : BaseGender.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static BaseGender fromId(final int id) {
        for (BaseGender e : BaseGender.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }

}
