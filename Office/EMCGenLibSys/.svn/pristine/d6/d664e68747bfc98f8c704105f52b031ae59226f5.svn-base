/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.inventory.pegging;

/**
 *
 * @author riaan
 */
public enum InventoryPeggingTypes {

    STANDARD(0, "Standard"),
    BOF(1, "BOF"),
    NO_DEMAND(2, "No Demand"),
    NO_SUPPLY(3, "No Supply");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of InventoryTransactionDirection */
    InventoryPeggingTypes(final int id, final String label) {
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

    public static InventoryPeggingTypes fromString(final String str) {
        for (InventoryPeggingTypes e : InventoryPeggingTypes.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static InventoryPeggingTypes fromId(final int id) {
        for (InventoryPeggingTypes e : InventoryPeggingTypes.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}


