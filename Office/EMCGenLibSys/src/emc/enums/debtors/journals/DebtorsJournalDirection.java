/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.debtors.journals;

/**
 * @description : This enum represents debtors journal directions.
 *
 * @date        : 03 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public enum DebtorsJournalDirection {

    DEBIT(0, "Debit"),
    CREDIT(1, "Credit");

    int id;
    String label;

    /** Creates a new instance of DebtorsJournalDirection */
    DebtorsJournalDirection(final int id, final String label) {
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

    public static DebtorsJournalDirection fromString(final String str) {
        for (DebtorsJournalDirection e : DebtorsJournalDirection.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static DebtorsJournalDirection fromId(final int id) {
        for (DebtorsJournalDirection e : DebtorsJournalDirection.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
