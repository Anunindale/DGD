/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.debtors.creditheld;

/**
 *
 * @author riaan
 */
public enum DebtorsCreditHeldReason {

    CREDIT_LIMIT(0, "Credit Limit Reached"),
    CUST_NOT_FOUND(1, "Customer Not Found"),
    CUST_STOPPED(2, "Customer Stopped"),
    CUST_CLOSED(3, "Customer Closed"),
    TERMS_EXCEEDED(4, "Terms Exceeded"),
    TERMS_NOT_FOUND(5, "Terms Not Found"),
    NO_AGING_PERIOD(6, "No Aging Period");
    
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  DebtorsCreditHeldReason*/
    DebtorsCreditHeldReason(final int id, final String label) {
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

    public static DebtorsCreditHeldReason fromString(final String str) {
        for (DebtorsCreditHeldReason e : DebtorsCreditHeldReason.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static DebtorsCreditHeldReason fromId(final int id) {
        for (DebtorsCreditHeldReason e : DebtorsCreditHeldReason.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
