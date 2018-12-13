/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.sop.garmentrequirementspersales;

/**
 *
 * @author wikus
 */
public enum SOPGarmentRequirementsSalesOrderType {

    BOTH(0, "Both"),
    SALES_ORDERS(1, "Sales Orders"),
    BLANKET_ORDERS(2, "Blanket Orders");
    //Enum variables
    final int id;
    final String label;

    /**
     * Creates a new instance of SOPGarmentRequirementsViewType
     */
    SOPGarmentRequirementsSalesOrderType(final int id, final String label) {
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

    public static SOPGarmentRequirementsSalesOrderType fromString(final String str) {
        for (SOPGarmentRequirementsSalesOrderType e : SOPGarmentRequirementsSalesOrderType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static SOPGarmentRequirementsSalesOrderType fromId(final int id) {
        for (SOPGarmentRequirementsSalesOrderType e : SOPGarmentRequirementsSalesOrderType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
