/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.methods.creditors;

/**
 *
 * @author riaan
 */
public enum ClientCreditorsMethods {

    GENERAL_METHOD(0,"GENERAL_METHOD");
    private final int id;
    private final String label;
 
    ClientCreditorsMethods(final int id, final String label) {
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
    public static ClientCreditorsMethods fromString(final String str) {
        for (ClientCreditorsMethods e : ClientCreditorsMethods.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
 
    public static ClientCreditorsMethods fromId(final int id) {
        for (ClientCreditorsMethods e : ClientCreditorsMethods.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
