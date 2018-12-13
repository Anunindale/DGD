/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.debtors.transactions;

/**
 * @description : This enum represents debtors transactions types.
 *
 * @date        : 03 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public enum DebtorsTransactionRefTypes {

    PAYMENT(0, "PMT"),
    INVOICE(1, "INV"),
    CREDIT_NOTE(2, "CRN"),
    TRANSFER(3, "TRF"),
    SETTLEMENT_DISC(4, "DISC"),
    INTEREST(5, "INT"),
    DEBIT_JOURNAL(6, "DBJ"),
    CREDIT_JOURNAL(7, "CRJ"),
    RETURNED_PAYMENT(8, "RD PMT"),
    REBATE(9, "RB");

    int id;
    String label;

    /** Creates a new instance of DebtorsTransactionRefTypes */
    DebtorsTransactionRefTypes(final int id, final String label) {
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

    public static DebtorsTransactionRefTypes fromString(final String str) {
        for (DebtorsTransactionRefTypes e : DebtorsTransactionRefTypes.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static DebtorsTransactionRefTypes fromId(final int id) {
        for (DebtorsTransactionRefTypes e : DebtorsTransactionRefTypes.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
