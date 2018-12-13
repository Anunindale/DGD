/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.inventory.pegging;

/**
 *
 * @author riaan
 */
public enum InventoryPeggingResolverTypes {

    DEMAND(0, "Demand"),
    SUPPLY(1, "Supply"),
    SOURCE(2, "Source"),
    ALL(3, "All");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of InventoryTransactionDirection */
    InventoryPeggingResolverTypes(final int id, final String label) {
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

    public static InventoryPeggingResolverTypes fromString(final String str) {
        for (InventoryPeggingResolverTypes e : InventoryPeggingResolverTypes.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static InventoryPeggingResolverTypes fromId(final int id) {
        for (InventoryPeggingResolverTypes e : InventoryPeggingResolverTypes.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}


