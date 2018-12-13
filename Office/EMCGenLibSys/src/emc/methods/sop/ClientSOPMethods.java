/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.methods.sop;

/**
 *
 * @author wikus
 */
public enum ClientSOPMethods {

    GENERAL_METHOD(0, "GENERAL_METHOD");
    private final int id;
    private final String label;

    ClientSOPMethods(final int id, final String label) {
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

    public static ClientSOPMethods fromString(final String str) {
        for (ClientSOPMethods e : ClientSOPMethods.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static ClientSOPMethods fromId(final int id) {
        for (ClientSOPMethods e : ClientSOPMethods.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
