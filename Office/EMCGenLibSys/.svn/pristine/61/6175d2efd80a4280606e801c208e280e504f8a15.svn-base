/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.pop.plannedpurchaseorder;

/**
 *
 * @author riaan
 */
public enum PlannedPOType {

    MAN(0, "MAN"),
    SYS(1, "SYS"),
    MPS(2, "MPS"),
    FIRM(3, "FIRM"),
    MRP(4, "MRP");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of PlannedWOType */
    PlannedPOType(final int id, final String label) {
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

    public static PlannedPOType fromString(final String str) {
        for (PlannedPOType e : PlannedPOType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static PlannedPOType fromId(final int id) {
        for (PlannedPOType e : PlannedPOType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
