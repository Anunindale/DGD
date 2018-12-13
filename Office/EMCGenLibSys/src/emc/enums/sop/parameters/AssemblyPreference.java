/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.sop.parameters;

/**
 * @author riaan
 */
public enum AssemblyPreference {

    REKIMBLE(0, "Re-Kimble"),
    ASSEMBLY(1, "Assembly");

    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of UseInvoiceTo */
    AssemblyPreference(final int id, final String label) {
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

    public static AssemblyPreference fromString(
            final String str) {
        for (AssemblyPreference e : AssemblyPreference.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }

        }
        return null;
    }

    public static AssemblyPreference fromId(
            final int id) {
        for (AssemblyPreference e : AssemblyPreference.values()) {
            if (e.id == id) {
                return e;
            }

        }
        return null;
    }
}

