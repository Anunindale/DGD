/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.methods.hr;

/**
 *
 * @author wikus
 */
public enum ClientHRMethods {

    GENERAL_METHOD(0, "GENERAL_METHOD");
    private final int id;
    private final String label;

    ClientHRMethods(final int id, final String label) {
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

    public static ClientHRMethods fromString(final String str) {
        for (ClientHRMethods e : ClientHRMethods.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
}
