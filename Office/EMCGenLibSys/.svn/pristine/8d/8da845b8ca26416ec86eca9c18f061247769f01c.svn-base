/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.inventory.requirementsplanning;

/**
 *
 * @author rico
 */
public enum RequirementsPlanningManagedBy {

    NONE(0, "None"),
    MPS(1, "RPS"),
    MRP(2, "MRP");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of InventoryTransactionDirection */
    RequirementsPlanningManagedBy(final int id, final String label) {
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

    public static RequirementsPlanningManagedBy fromString(final String str) {
        for (RequirementsPlanningManagedBy e : RequirementsPlanningManagedBy.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static RequirementsPlanningManagedBy fromId(final int id) {
        for (RequirementsPlanningManagedBy e : RequirementsPlanningManagedBy.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
