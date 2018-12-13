/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.methods.developertools;

/**
 *
 * @author riaan
 */
public enum ClientDeveloperToolMethods {
    GENERAL_METHOD(0,"GENERAL_METHOD");
    private final int id;
    private final String label;
 
    ClientDeveloperToolMethods(final int id, final String label) {
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
    public static ClientDeveloperToolMethods fromString(final String str) {
        for (ClientDeveloperToolMethods e : ClientDeveloperToolMethods.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
}
