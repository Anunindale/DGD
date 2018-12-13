/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.pop.posting;

/**
 *
 * @author wikus
 */
public enum SBType {

    JOURNAL(0, "JOURNAL"),
    PURCHASEORDER(1,"PURCHASEORDER");
    
    
    //Enum variables
    final int id;
    final String label;
    
    /** Creates a new instance of DocumentTypes */
    SBType(final int id, final String label) {
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
    
    public static SBType fromString(final String str) {
        for (SBType e : SBType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
    
    public static SBType fromId(final int id) {
        for (SBType e : SBType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
    
}
