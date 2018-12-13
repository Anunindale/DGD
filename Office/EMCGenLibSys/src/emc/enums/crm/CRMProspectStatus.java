/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.crm;

/**
 *
 * @author rico
 */
public enum CRMProspectStatus {

    ACTIVE(0, "ACTIVE"),
    CLOSED(1, "CLOSED");//inactive
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  HRDependantsTypes*/
    CRMProspectStatus(final int id, final String label) {
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

    public static CRMProspectStatus fromString(final String str) {
        for (CRMProspectStatus e : CRMProspectStatus.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static CRMProspectStatus fromId(final int id) {
        for (CRMProspectStatus e : CRMProspectStatus.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
