/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.debtors.creditheld;

/**
 *
 * @author riaan
 */
public enum DebtorsCreditHeldStatus {

    HELD(0, "Held"),
    APPROVED(1, "Approved");

    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  DebtorsInvoiceStatus*/
    DebtorsCreditHeldStatus(final int id, final String label) {
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

    public static DebtorsCreditHeldStatus fromString(final String str) {
        for (DebtorsCreditHeldStatus e : DebtorsCreditHeldStatus.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static DebtorsCreditHeldStatus fromId(final int id) {
        for (DebtorsCreditHeldStatus e : DebtorsCreditHeldStatus.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
