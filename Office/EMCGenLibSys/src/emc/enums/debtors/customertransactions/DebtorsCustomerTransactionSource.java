/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.debtors.customertransactions;

/**
 *
 * @author riaan
 */
public enum DebtorsCustomerTransactionSource {

    SO(0, "Sales Order"),
    JNL(1, "Journal");

    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  DebtorsCustomerTransactionSource*/
    DebtorsCustomerTransactionSource(final int id, final String label) {
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

    public static DebtorsCustomerTransactionSource fromString(final String str) {
        for (DebtorsCustomerTransactionSource e : DebtorsCustomerTransactionSource.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static DebtorsCustomerTransactionSource fromId(final int id) {
        for (DebtorsCustomerTransactionSource e : DebtorsCustomerTransactionSource.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
