/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.inventory.requirementsplanning;

/**
 *
 * @author wikus
 */
public enum ReqPlanAuditType {

    FORECAST(0, "Forecast"),
    SALES_ORDERS(1, "Sales Orders"),
    PURCHASE_ORDERS(2, "Purchase Orders"),
    PLANNED_PURCHASE_ORDERS(3, "Planned Purchase Orders"),
    WORKS_ORDERS(4, "Works Orders"),
    PLANNED_WORKS_ORDERS(5, "Planned Works Orders");
    //Enum variables
    final int id;
    final String label;

    /**
     * Creates a new instance of InventoryTransactionDirection
     */
    ReqPlanAuditType(final int id, final String label) {
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

    public static ReqPlanAuditType fromString(final String str) {
        for (ReqPlanAuditType e : ReqPlanAuditType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static ReqPlanAuditType fromId(final int id) {
        for (ReqPlanAuditType e : ReqPlanAuditType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
