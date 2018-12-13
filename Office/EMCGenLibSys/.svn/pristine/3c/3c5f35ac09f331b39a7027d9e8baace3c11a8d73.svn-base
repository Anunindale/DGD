/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.debtors.journals;

/**
 * @description : This enum represents debtors journal types.
 *
 * @date        : 03 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public enum DebtorsJournalType {
    
    PAYMENT(0, "PMT"),
    JOURNAL_DEBIT(1, "JD"),
    JOURNAL_CREDIT(2, "JC"),
    SETTLEMENT_DISCOUNT(3, "SET"),
    INTEREST(4, "INT"),
    TRANSFER(5, "TRF"),
    RETURNED_PAYMENTS(6, "RD");

    int id;
    String label;
    
    /** Creates a new instance of DebtorsJournalType */
    DebtorsJournalType(final int id, final String label) {
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

    public static DebtorsJournalType fromString(final String str) {
        for (DebtorsJournalType e : DebtorsJournalType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static DebtorsJournalType fromId(final int id) {
        for (DebtorsJournalType e : DebtorsJournalType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
