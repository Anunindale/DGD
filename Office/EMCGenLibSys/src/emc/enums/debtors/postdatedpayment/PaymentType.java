/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.debtors.postdatedpayment;

/**
 * @description : This enum represents post-dated payment types.
 *
 * @date        : 03 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public enum PaymentType {

    CHEQUE(0, "Cheque");

    int id;
    String label;

    /** Creates a new instance of PaymentType */
    PaymentType(final int id, final String label) {
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

    public static PaymentType fromString(final String str) {
        for (PaymentType e : PaymentType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static PaymentType fromId(final int id) {
        for (PaymentType e : PaymentType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
