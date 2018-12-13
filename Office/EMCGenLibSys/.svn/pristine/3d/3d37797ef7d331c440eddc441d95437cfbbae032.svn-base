/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.sop.priceaudittrail;

/**
 *
 * @author wikus
 */
public enum SOPPriceAuditTrailType {

    PRICE_APPROVAL(0, "Price"),
    DISCOUNT_APPROVAL(1, "Discount"),
    PRICE_LIST_UPDATE(2, "Update"),
    PRICE_LIST_INSERT(3, "Insert"),
    PRICE_LIST_DELETE(4, "Delete");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of ProductionWorkTypes */
    SOPPriceAuditTrailType(final int id, final String label) {
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

    public static SOPPriceAuditTrailType fromString(final String str) {
        for (SOPPriceAuditTrailType e : SOPPriceAuditTrailType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static SOPPriceAuditTrailType fromId(final int id) {
        for (SOPPriceAuditTrailType e : SOPPriceAuditTrailType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
