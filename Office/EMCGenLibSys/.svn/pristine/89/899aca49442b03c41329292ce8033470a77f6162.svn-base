/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.crm;

/**
 *
 * @author wikus
 */
public enum CRMTransfers {

    STUDENTS(0, "STUDENTS"),
    CUSTOMERS(1, "CUSTOMERS");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  HRDependantsTypes*/
    CRMTransfers(final int id, final String label) {
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

    public static CRMTransfers fromString(final String str) {
        for (CRMTransfers e : CRMTransfers.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static CRMTransfers fromId(final int id) {
        for (CRMTransfers e : CRMTransfers.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
