/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.gl;

/**
 *
 * @author riaan
 */
public enum FinancialPeriodTypes {

    OPENING(0, "Opening"),
    NORMAL(1, "Normal"),
    CLOSING(2, "Closing");
    
    //Enum variables
    final int id;
    final String label;
    
    /** Creates a new instance of FinancialPeriodTypes */
    FinancialPeriodTypes(final int id, final String label) {
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
    
    public static FinancialPeriodTypes fromString(final String str) {
        for (FinancialPeriodTypes e : FinancialPeriodTypes.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
    
    public static FinancialPeriodTypes fromId(final int id) {
        for (FinancialPeriodTypes e : FinancialPeriodTypes.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
    
}

