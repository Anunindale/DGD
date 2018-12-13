/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.debtors;

/**
 *
 * @author wikus
 */
public enum DebtorsPricingOn {

    ORDER(0, "Order"),
    INVOICE(1, "Invoice");
    
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  HRDependantsTypes*/
    DebtorsPricingOn(final int id, final String label) {
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

    public static DebtorsPricingOn fromString(final String str) {
        for (DebtorsPricingOn e : DebtorsPricingOn.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static DebtorsPricingOn fromId(final int id) {
        for (DebtorsPricingOn e : DebtorsPricingOn.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
