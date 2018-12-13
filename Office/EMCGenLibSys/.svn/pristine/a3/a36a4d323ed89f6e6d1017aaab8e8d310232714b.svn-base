/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.inventory.transactions;

/**
 *
 * @author rico
 */
public enum InventoryTransactionsRefType {

    Purchase_Order(0, "POL"),
    Receipt(1, "RC"),
    Journal(2, "JR"),
    Journal_Line(3, "JRL"),
    PlannedWO(4, "PWO"),
    Return(5, "RTND"),
    Works_Order(6, "WO"),
    Simple_Movement(7, "SM"),
    Picking_List_Lines(8, "PLL"),
    Sales_Order(9, "SOL"),
    Invoice(10, "INV"),
    Credit_Note(11, "CRN"),
    Works_Order_Return(12, "WOR");

    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of InventoryTransactionTypes */
    InventoryTransactionsRefType(final int id, final String label) {
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

    public static InventoryTransactionsRefType fromString(final String str) {
        for (InventoryTransactionsRefType e : InventoryTransactionsRefType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static InventoryTransactionsRefType fromId(final int id) {
        for (InventoryTransactionsRefType e : InventoryTransactionsRefType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
