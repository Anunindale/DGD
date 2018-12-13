/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.inventory.inventorytables;

/**
 *
 * @author rico
 */
public enum InventorySummarySuspense {

    SUSPENSE(0, "Suspense");

    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of InventoryItemSubstituteRules */
    InventorySummarySuspense(final int id, final String label) {
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

    public static InventorySummarySuspense fromString(final String str) {
        for (InventorySummarySuspense e : InventorySummarySuspense.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static InventorySummarySuspense fromId(final int id) {
        for (InventorySummarySuspense e : InventorySummarySuspense.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
