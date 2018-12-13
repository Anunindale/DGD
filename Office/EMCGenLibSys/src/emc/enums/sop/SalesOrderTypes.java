/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.sop;

/**
 *
 * @author rico
 */
public enum SalesOrderTypes {

    SALES_ORDER(0, "Sales Order"),
    IMPORTED(1, "Imported"),
    BLANKET_ORDER(2, "Blanket Order");

    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of DocumentTypes */
    SalesOrderTypes(final int id, final String label) {
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

    public static SalesOrderTypes fromString(final String str) {
        for (SalesOrderTypes e : SalesOrderTypes.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static SalesOrderTypes fromId(final int id) {
        for (SalesOrderTypes e : SalesOrderTypes.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
