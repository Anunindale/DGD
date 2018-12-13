/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.crm;

/**
 *
 * @author rico
 */
public enum CRMProspectActRule {

    UMSCHOOL(0, "UMSCHOOL");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  HRDependantsTypes*/
    CRMProspectActRule(final int id, final String label) {
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

    public static CRMProspectActRule fromString(final String str) {
        for (CRMProspectActRule e : CRMProspectActRule.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static CRMProspectActRule fromId(final int id) {
        for (CRMProspectActRule e : CRMProspectActRule.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
