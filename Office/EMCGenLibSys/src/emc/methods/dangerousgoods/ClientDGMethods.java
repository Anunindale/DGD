/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.methods.dangerousgoods;

/**
 *
 * @author rico
 */
public enum ClientDGMethods {

    GENERAL_METHOD(0, "GENERAL_METHOD");
    private final int id;
    private final String label;

    ClientDGMethods(final int id, final String label) {
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

    public static ClientDGMethods fromString(final String str) {
        for (ClientDGMethods e : ClientDGMethods.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
}

