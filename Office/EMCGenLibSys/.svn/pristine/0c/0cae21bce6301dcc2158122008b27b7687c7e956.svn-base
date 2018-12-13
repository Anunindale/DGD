/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.creditors.daysmonths;

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
public enum DaysMonths {

    DAYS(0, "Days"),
    MONTHS(1, "Months");

    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  DaysMonths*/
    DaysMonths(final int id, final String label) {
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

    public static DaysMonths fromString(final String str) {
        for (DaysMonths e : DaysMonths.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static DaysMonths fromId(final int id) {
        for (DaysMonths e : DaysMonths.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
