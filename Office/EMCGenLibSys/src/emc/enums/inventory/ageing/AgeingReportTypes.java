/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.inventory.ageing;

/**
 *
 * @author rico
 */
public enum AgeingReportTypes {

    SUMMARY(0, "SUMMARY"),
    DETAIL(1, "DETAIL"),
    FULL_DETAIL(2, "FULL_DETAIL");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of ItemClassifications */
    AgeingReportTypes(final int id, final String label) {
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

    public static AgeingReportTypes fromString(final String str) {
        for (AgeingReportTypes e : AgeingReportTypes.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static AgeingReportTypes fromId(final int id) {
        for (AgeingReportTypes e : AgeingReportTypes.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
