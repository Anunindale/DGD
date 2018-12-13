/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.hr;

/**
 *
 * @author wikus
 */
public enum HRDependantsTypes {

    DEPENDANT(0, "Dependant"),
    NOK(1, "Next Of Kin"),
    OTHER(2, "Other");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  HRDependantsTypes*/
    HRDependantsTypes(final int id, final String label) {
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

    public static HRDependantsTypes fromString(final String str) {
        for (HRDependantsTypes e : HRDependantsTypes.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static HRDependantsTypes fromId(final int id) {
        for (HRDependantsTypes e : HRDependantsTypes.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
