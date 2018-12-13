/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.inventory.requirementsplanning;

/**
 *
 * @author rico
 */
public enum RequirementsPlanningFromType {

    FORECAST(0, "Forecast"),
    SALES_ORDER(1, "Sales Order"),
    PURCHASE_ORDER(2, "Purchase Order"),
    WORKS_ORDER(3, "Works Order"),
    PLANNED_PURCHASE_ORDER(4, "Planned Purchase Order"),
    PLANNED_WORKS_ORDER(5, "Planned Works Order"),
    MPS_DEMAND(6, "MPS Demand"),
    MPS_SUPPLY(7, "MPS Supply"),
    RELEASE_PLANNED_PURCHASE_ORDER(8, "Release Planned Purchase Order"),
    RELEASE_PLANNED_WORKS_ORDER(9, "Release Planned Works Order"),
    SAFETY_STOCK(10, "Safety Stock"),
    WORKS_ORDER_BOM(11, "Works Order Bom");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of InventoryTransactionDirection */
    RequirementsPlanningFromType(final int id, final String label) {
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

    public static RequirementsPlanningFromType fromString(final String str) {
        for (RequirementsPlanningFromType e : RequirementsPlanningFromType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static RequirementsPlanningFromType fromId(final int id) {
        for (RequirementsPlanningFromType e : RequirementsPlanningFromType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
