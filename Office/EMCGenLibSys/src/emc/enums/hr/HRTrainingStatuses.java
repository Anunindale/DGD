/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.hr;

/**
 *
 * @author wikus
 */
public enum HRTrainingStatuses {

    PROPOSED(0, "Proposed"),
    BUSY(1, "Busy"),
    COMPLETED(2, "Completed");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  HRDependantsTypes*/
    HRTrainingStatuses(final int id, final String label) {
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

    public static HRTrainingStatuses fromString(final String str) {
        for (HRTrainingStatuses e : HRTrainingStatuses.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static HRTrainingStatuses fromId(final int id) {
        for (HRTrainingStatuses e : HRTrainingStatuses.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
