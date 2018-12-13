/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.inventory.requirementsplanning;

/**
 *
 * @author rico
 */
public enum RequirementsPlanningType {

    DEMAND(0, "Demand"),
    SUPPLY(1, "Supply"),
    MPS(2, "RPS"),
    MRP(3, "MRP");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of InventoryTransactionDirection */
    RequirementsPlanningType(final int id, final String label) {
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

    public static RequirementsPlanningType fromString(final String str) {
        for (RequirementsPlanningType e : RequirementsPlanningType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static RequirementsPlanningType fromId(final int id) {
        for (RequirementsPlanningType e : RequirementsPlanningType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
