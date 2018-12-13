/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.debtors.customerstatement;

/**
 *
 * @author riaan
 */
public enum IgnoreBalanceEnum {

    ZERO_BALANCE(0, "Zero Balance"),
    CREDIT_BALANCE(1, "Credit Balance"),
    BOTH(2, "Both");
    
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  IgnoreBalanceEnum. */
    IgnoreBalanceEnum(final int id, final String label) {
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

    public static IgnoreBalanceEnum fromString(final String str) {
        for (IgnoreBalanceEnum e : IgnoreBalanceEnum.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static IgnoreBalanceEnum fromId(final int id) {
        for (IgnoreBalanceEnum e : IgnoreBalanceEnum.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
