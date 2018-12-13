/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.methods.um;

/**
 *
 * @author wikus
 */
public enum ClientUMMethods {

    GENERAL_METHOD(0, "GENERAL_METHOD");
    private final int id;
    private final String label;

    ClientUMMethods(final int id, final String label) {
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

    public static ClientUMMethods fromString(final String str) {
        for (ClientUMMethods e : ClientUMMethods.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
}
