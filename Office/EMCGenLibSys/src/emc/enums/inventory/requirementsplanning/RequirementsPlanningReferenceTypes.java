/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.inventory.requirementsplanning;

/**
 *
 * @author riaan
 */
public enum RequirementsPlanningReferenceTypes {

    FORECAST(0, "Forecast", "DM"),
    SALES_ORDER(1, "Sales Order", "SO"),
    PURCHASE_ORDER(2, "Purchase Order", "PO"),
    PLANNED_PURCHASE_ORDER(3, "Planned Purchase Order", "PPO"),
    WORKS_ORDER(4, "Works Order", "WO"),
    PLANNED_WORKS_ORDER(6, "Planned Works Order", "PWO"),
    SAFETY_STOCK(7, "Safety Stock", "SS"),
    WORKS_ORDER_BOM(8, "Works Order BOM", "WO BOM");
    //Enum variables
    final int id;
    final String label;
    final String shortLabel;

    /** Creates a new instance of  RequirementsPlanningReferenceTypes. */
    RequirementsPlanningReferenceTypes(final int id, final String label, final String shortLabel) {
        this.id = id;
        this.label = label;
        this.shortLabel = shortLabel;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return label;
    }

    public String toShortString() {
        return shortLabel;
    }

    public static RequirementsPlanningReferenceTypes fromString(final String str) {
        for (RequirementsPlanningReferenceTypes e : RequirementsPlanningReferenceTypes.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static RequirementsPlanningReferenceTypes fromShortString(final String str) {
        for (RequirementsPlanningReferenceTypes e : RequirementsPlanningReferenceTypes.values()) {
            if (e.toShortString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static RequirementsPlanningReferenceTypes fromId(final int id) {
        for (RequirementsPlanningReferenceTypes e : RequirementsPlanningReferenceTypes.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
