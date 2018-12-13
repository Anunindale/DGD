/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.debtors.allocationimport;

/**
 *
 * @author riaan
 */
public enum DebtorsAllocationImportPaymentOrder {

    AMOUNT(0, "Amount"),
    DATE(1, "Date");

    
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  DebtorsAllocationImportPaymentOrder*/
    DebtorsAllocationImportPaymentOrder(final int id, final String label) {
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

    public static DebtorsAllocationImportPaymentOrder fromString(final String str) {
        for (DebtorsAllocationImportPaymentOrder e : DebtorsAllocationImportPaymentOrder.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static DebtorsAllocationImportPaymentOrder fromId(final int id) {
        for (DebtorsAllocationImportPaymentOrder e : DebtorsAllocationImportPaymentOrder.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
