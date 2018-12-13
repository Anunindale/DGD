/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.gl;

/**
 *
 * @author riaan
 */
public enum FinancialPeriodStatus {

    OPEN(0, "Open"),
    CLOSED(1, "Closed");

    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  GLAccountStatus*/
    FinancialPeriodStatus(final int id, final String label) {
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

    public static FinancialPeriodStatus fromString(final String str) {
        for (FinancialPeriodStatus e : FinancialPeriodStatus.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static FinancialPeriodStatus fromId(final int id) {
        for (FinancialPeriodStatus e : FinancialPeriodStatus.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
