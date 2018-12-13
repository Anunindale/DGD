/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.methods.inventory;

/**
 *
 * @author riaan
 */
public enum ClientInventoryMethods {
    
    GENERAL_METHOD(0,"GENERAL_METHOD");
    private final int id;
    private final String label;
 
    ClientInventoryMethods(final int id, final String label) {
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
    public static ClientInventoryMethods fromString(final String str) {
        for (ClientInventoryMethods e : ClientInventoryMethods.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
 
    public static ClientInventoryMethods fromId(final int id) {
        for (ClientInventoryMethods e : ClientInventoryMethods.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
