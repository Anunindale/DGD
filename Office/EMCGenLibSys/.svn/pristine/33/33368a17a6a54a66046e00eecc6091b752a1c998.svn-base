/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.debtors.parameters;

/**
 * @description : This enum represents Debtors aging periods.
 *
 * @date        : 03 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public enum DebtorsAgingPeriod {

    FINANCIAL_PERIODS(0, "Financial Periods"),
    CALENDAR_MONTHS(1, "Calendar Months");

    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  AgingMode*/
    DebtorsAgingPeriod(final int id, final String label) {
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

    public static DebtorsAgingPeriod fromString(final String str) {
        for (DebtorsAgingPeriod e : DebtorsAgingPeriod.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static DebtorsAgingPeriod fromId(final int id) {
        for (DebtorsAgingPeriod e : DebtorsAgingPeriod.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
