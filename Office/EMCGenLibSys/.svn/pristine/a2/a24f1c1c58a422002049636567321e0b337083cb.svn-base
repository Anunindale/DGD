/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.gl.journals;

/**
 *
 * @author riaan
 */
public enum GLJournalType {
    
    GL(0, "GL");

    int id;
    String label;
    
    /** Creates a new instance of DebtorsJournalType */
    GLJournalType(final int id, final String label) {
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

    public static GLJournalType fromString(final String str) {
        for (GLJournalType e : GLJournalType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static GLJournalType fromId(final int id) {
        for (GLJournalType e : GLJournalType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
