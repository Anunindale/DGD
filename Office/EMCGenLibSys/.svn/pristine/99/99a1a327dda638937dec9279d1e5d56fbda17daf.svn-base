/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.debtors.creditheld;

/**
 *
 * @author riaan
 */
public enum DebtorsCreditHeldRefType {

    SALES_ORDER(0, "Sales Order"),
    INVOICE(1, "Invoice");
    
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  DebtorsCreditHeldRefType*/
    DebtorsCreditHeldRefType(final int id, final String label) {
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

    public static DebtorsCreditHeldRefType fromString(final String str) {
        for (DebtorsCreditHeldRefType e : DebtorsCreditHeldRefType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static DebtorsCreditHeldRefType fromId(final int id) {
        for (DebtorsCreditHeldRefType e : DebtorsCreditHeldRefType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
