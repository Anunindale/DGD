/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.gl.journals;

/**
 *
 * @author riaan
 */
public enum GLJournalDirection {

    GL(0, "GL");

    int id;
    String label;

    /** Creates a new instance of DebtorsJournalDirection */
    GLJournalDirection(final int id, final String label) {
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

    public static GLJournalDirection fromString(final String str) {
        for (GLJournalDirection e : GLJournalDirection.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static GLJournalDirection fromId(final int id) {
        for (GLJournalDirection e : GLJournalDirection.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
