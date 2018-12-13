/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.methods.crm;

/**
 *
 * @author wikus
 */
public enum ClientCRMMethods {

    GENERAL_METHOD(0, "GENERAL_METHOD");
    private final int id;
    private final String label;

    ClientCRMMethods(final int id, final String label) {
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

    public static ClientCRMMethods fromString(final String str) {
        for (ClientCRMMethods e : ClientCRMMethods.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
}
