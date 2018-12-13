/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.gl;

/**
 *
 * @author riaan
 */
public enum COADebitCredit {

    DEBIT(0, "Debit"),
    CREDIT(1, "Credit");
    
    //Enum variables
    final int id;
    final String label;
    
    /** Creates a new instance of COADebitCredit */
    COADebitCredit(final int id, final String label) {
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
    
    public static COADebitCredit fromString(final String str) {
        for (COADebitCredit e : COADebitCredit.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
    
    public static COADebitCredit fromId(final int id) {
        for (COADebitCredit e : COADebitCredit.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
    
}
