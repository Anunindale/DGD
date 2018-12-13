/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.inventory.costing;

/**
 *
 * @author wikus
 */
public enum DefaultDimCostCalc {

    MAX(0, "Maximum"),
    AVG(1, "Average"),
    MIN(2, "Minimum");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of ItemClassifications */
    DefaultDimCostCalc(final int id, final String label) {
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

    public static DefaultDimCostCalc fromString(final String str) {
        for (DefaultDimCostCalc e : DefaultDimCostCalc.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static DefaultDimCostCalc fromId(final int id) {
        for (DefaultDimCostCalc e : DefaultDimCostCalc.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
