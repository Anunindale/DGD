/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.sop.commission;

/**
 *
 * @author rico
 */
public enum SOPCommissionTypes {

    SINGLE(0, "Single"),
    MULTIPLE(1, "Multiple");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of DocumentTypes */
    SOPCommissionTypes(final int id, final String label) {
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

    public static SOPCommissionTypes fromString(final String str) {
        for (SOPCommissionTypes e : SOPCommissionTypes.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static SOPCommissionTypes fromId(final int id) {
        for (SOPCommissionTypes e : SOPCommissionTypes.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
