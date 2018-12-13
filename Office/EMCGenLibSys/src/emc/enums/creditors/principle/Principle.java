/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.creditors.principle;

/**
 * @description : This enum is used on the CreditorsSettlementDiscountTerms and
 *                CreditorsTermsOfPayment tables.
 *
 * @date        : 03 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public enum Principle {

    ACTUAL_DAYS(0, "Actual Days"),
    STATEMENT_DATE(1, "Statement Date");

    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  Principle*/
    Principle(final int id, final String label) {
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

    public static Principle fromString(final String str) {
        for (Principle e : Principle.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static Principle fromId(final int id) {
        for (Principle e : Principle.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
