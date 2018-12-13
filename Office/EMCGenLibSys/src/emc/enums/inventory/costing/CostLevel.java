/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.inventory.costing;

/**
 *
 * @author rico
 */
public enum CostLevel {

    ITEM(0, "Item"),
    DIMENSION(1,"Dimension");


    //Enum variables
    final int id;
    final String label;


    /** Creates a new instance of ItemClassifications */
    CostLevel(final int id, final String label) {
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



    public static CostLevel fromString(final String str) {
        for (CostLevel e : CostLevel.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static CostLevel fromId(final int id) {
        for (CostLevel e : CostLevel.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }

}