/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.basetables.docuref;

/**
 *
 * @author riaan
 */
public enum DocurefTypes {
    FILE(0, "F"),
    TEMPLATE(1,"T"),
    NOTE(2,"N");
    
    //Enum variables
    final int id;
    final String label;
    
    /** Creates a new instance of DocurefTypes */
    DocurefTypes(final int id, final String label) {
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
    
    public static DocurefTypes fromString(final String str) {
        for (DocurefTypes e : DocurefTypes.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
    
    public static DocurefTypes fromId(final int id) {
        for (DocurefTypes e : DocurefTypes.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
