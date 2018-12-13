/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.sop;

/**
 *
 * @author rico
 */
public enum SOPCustomerLablesType {

    SHIP_TO(0, "Ship To"),
    INVOICE_TO(1, "Invoice To"),
    BOTH(2, "Both");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of DocumentTypes */
    SOPCustomerLablesType(final int id, final String label) {
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

    public static SOPCustomerLablesType fromString(final String str) {
        for (SOPCustomerLablesType e : SOPCustomerLablesType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static SOPCustomerLablesType fromId(final int id) {
        for (SOPCustomerLablesType e : SOPCustomerLablesType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
