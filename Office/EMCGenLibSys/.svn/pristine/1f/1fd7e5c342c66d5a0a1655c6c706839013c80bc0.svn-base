/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.gl.journals;

/**
 *
 * @author riaan
 */
public enum JournalLineType {

    GL(0, "GL"),
    DR(1, "DR"),
    CR(2, "CP"),
    P(3, "P");

    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  GLAccountStatus*/
    JournalLineType(final int id, final String label) {
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

    public static JournalLineType fromString(final String str) {
        for (JournalLineType e : JournalLineType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static JournalLineType fromId(final int id) {
        for (JournalLineType e : JournalLineType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
