/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.debtors.creditstopreasons;

/**
 *
 * @author riaan
 */
public enum DebtorsCreditStopReasons {

    //These values should be stored on the DebtorsCreditStopReason table.
    CRL(0, "CRL"),
    TMS(1, "TMS");
    
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  DebtorsCreditHeldReason*/
    DebtorsCreditStopReasons(final int id, final String label) {
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

    public static DebtorsCreditStopReasons fromString(final String str) {
        for (DebtorsCreditStopReasons e : DebtorsCreditStopReasons.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static DebtorsCreditStopReasons fromId(final int id) {
        for (DebtorsCreditStopReasons e : DebtorsCreditStopReasons.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
