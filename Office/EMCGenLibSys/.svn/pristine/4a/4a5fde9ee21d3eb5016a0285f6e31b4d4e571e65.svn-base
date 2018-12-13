/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.base.laf;

/**
 *
 * @author asd_admin
 */
public enum ClientLAF {

    METAL(0, "Metal"),
    NIMBUS(1, "Nimbus");
    //Enum variables
    final int id;
    final String label;

    /**
     * Creates a new instance of OperatingSystems
     */
    ClientLAF(final int id, final String label) {
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

    public static ClientLAF fromString(final String str) {
        for (ClientLAF e : ClientLAF.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static ClientLAF fromId(final int id) {
        for (ClientLAF e : ClientLAF.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
