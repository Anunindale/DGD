/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.inventory.costing;

/**
 *
 * @author rico
 */
public enum CostType {

    AVERAGE(0, "Average"),
    FIFO(1,"FIFO"),
    STANDARD(1,"Standard");


    //Enum variables
    final int id;
    final String label;


    /** Creates a new instance of ItemClassifications */
    CostType(final int id, final String label) {
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



    public static CostType fromString(final String str) {
        for (CostType e : CostType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static CostType fromId(final int id) {
        for (CostType e : CostType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }

}
