/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.sop.salesorderenquiry;

/**
 *
 * @author wikus
 */
public enum SalesOrderEnquiryTypes {

    SALES_ORDER(0, "Sales Order"),
    BLANKET_ORDER(1, "Blanket Order"),
    BOTH(2, "Both");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of DocumentTypes */
    SalesOrderEnquiryTypes(final int id, final String label) {
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

    public static SalesOrderEnquiryTypes fromString(final String str) {
        for (SalesOrderEnquiryTypes e : SalesOrderEnquiryTypes.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static SalesOrderEnquiryTypes fromId(final int id) {
        for (SalesOrderEnquiryTypes e : SalesOrderEnquiryTypes.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
