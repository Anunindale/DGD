/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.gl;

/**
 *
 * @author riaan
 */
public enum COARecordTypes {

    HEADER(0, "Header"),
    LINE(1, "Line"),
    TOTAL(2, "Total");
    
    //Enum variables
    final int id;
    final String label;
    
    /** Creates a new instance of COARecordTypes */
    COARecordTypes(final int id, final String label) {
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
    
    public static COARecordTypes fromString(final String str) {
        for (COARecordTypes e : COARecordTypes.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
    
    public static COARecordTypes fromId(final int id) {
        for (COARecordTypes e : COARecordTypes.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
    
}