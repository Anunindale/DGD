/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.methods.pop;

/**
 *
 * @author riaan
 */
public enum ClientPOPMethods {
    
    GENERAL_METHOD(0,"GENERAL_METHOD");
    private final int id;
    private final String label;
 
    ClientPOPMethods(final int id, final String label) {
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
    public static ClientPOPMethods fromString(final String str) {
        for (ClientPOPMethods e : ClientPOPMethods.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
 
    public static ClientPOPMethods fromId(final int id) {
        for (ClientPOPMethods e : ClientPOPMethods.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
