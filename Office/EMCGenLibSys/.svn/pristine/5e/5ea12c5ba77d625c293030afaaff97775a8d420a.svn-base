/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.methods.debtors;


/**
 *
 * @author wikus
 */
public enum ClientDebtorsMethods {
    GENERAL_METHOD(0,"GENERAL_METHOD");
    private final int id;
    private final String label;
 
    ClientDebtorsMethods(final int id, final String label) {
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
    public static ClientDebtorsMethods fromString(final String str) {
        for (ClientDebtorsMethods e : ClientDebtorsMethods.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
}
