/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.base.employees;

/**
 *
 * @author riaan
 */
public enum BaseEmployeeTypeOfConcern {

    SOLE_PROP(0, "Sole Prop."),
    PARTNERSHIP(1, "Partners"),
    CC(2, "CC"),
    PTY(3, "Pty (Ltd)");

    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of BaseEmployeeTypeOfConcern */
    BaseEmployeeTypeOfConcern(final int id, final String label) {
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

    public static BaseEmployeeTypeOfConcern fromString(final String str) {
        for (BaseEmployeeTypeOfConcern e : BaseEmployeeTypeOfConcern.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static BaseEmployeeTypeOfConcern fromId(final int id) {
        for (BaseEmployeeTypeOfConcern e : BaseEmployeeTypeOfConcern.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }

}
