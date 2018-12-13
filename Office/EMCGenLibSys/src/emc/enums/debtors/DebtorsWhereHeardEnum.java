/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.debtors;

/**
 *
 * @author kapeshi
 */
public enum DebtorsWhereHeardEnum {

    GOOGLE(0, "Google"),
    REFERRAL(1, "Referral"),
    MOUTH(2, "Word of Mouth"),
    EMAIL(3, "Email"),
    OTHER(4, "Other");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  DebtorsInvoiceType*/
    DebtorsWhereHeardEnum(final int id, final String label) {
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

    public static DebtorsWhereHeardEnum fromString(final String str) {
        for (DebtorsWhereHeardEnum e : DebtorsWhereHeardEnum.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static DebtorsWhereHeardEnum fromId(final int id) {
        for (DebtorsWhereHeardEnum e : DebtorsWhereHeardEnum.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
