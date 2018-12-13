/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.sop.salesorders;

/**
 * @description : This enum represents sales order statusses.
 *
 * @date        : 03 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public enum SalesOrderStatus {

    //Please note that the Sales Order Master bean has logic which depends on the id's
    //in this enum to be in the chronological order of their respective statusses.
    CAPTURED(0, "Captured"),
    UNAPPROVED(1, "Unapproved"),
    OPEN(2, "Open"),
    IN_PROGRESS(3, "In Progress"),
    PARTIAL(4, "Partial"),  //This should only apply to blanket orders.
    INVOICED(5, "Invoiced"),
    HELD(6, "Held"),
    CANCELLED(7, "Cancelled"),
    BLANKET_ORDER(8, "Blanket Order");

    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of SalesOrderStatus */
    SalesOrderStatus(final int id, final String label) {
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

    public static SalesOrderStatus fromString(
            final String str) {
        for (SalesOrderStatus e : SalesOrderStatus.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }

        }
        return null;
    }

    public static SalesOrderStatus fromId(
            final int id) {
        for (SalesOrderStatus e : SalesOrderStatus.values()) {
            if (e.id == id) {
                return e;
            }

        }
        return null;
    }
}

