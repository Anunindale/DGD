/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.base.docref;

/**
 *
 * @author rico
 */
public enum DocRefSummary {

    LOGO(0, "Company Logo"),
    SIGNATURE(1, "Email Signature");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of DocumentTypes */
    DocRefSummary(final int id, final String label) {
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

    public static DocRefSummary fromString(final String str) {
        for (DocRefSummary e : DocRefSummary.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static DocRefSummary fromId(final int id) {
        for (DocRefSummary e : DocRefSummary.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
