/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.methods.trec;

/**
 *
 * @author wikus
 */
public enum ClientTRECMethods {

    GENERAL_METHOD(0, "GENERAL_METHOD");
    private final int id;
    private final String label;

    ClientTRECMethods(final int id, final String label) {
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

    public static ClientTRECMethods fromString(final String str) {
        for (ClientTRECMethods e : ClientTRECMethods.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
}
