/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.hr;

/**
 *
 * @author wikus
 */
public enum HREducationTypes {

    PRIMARY(0, "Primary"),
    SECONDARY(1, "Secondary"),
    TERTIARY(2, "Tertiary"),
    JOB_RELATED(3, "Job Related"),
    OTHER(4, "Other");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  HRDependantsTypes*/
    HREducationTypes(final int id, final String label) {
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

    public static HREducationTypes fromString(final String str) {
        for (HREducationTypes e : HREducationTypes.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static HREducationTypes fromId(final int id) {
        for (HREducationTypes e : HREducationTypes.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
