/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.base.journals;

/**
 *
 * @author rico
 */
public enum JournalStatus {
    CAPTURED(0, "CAPTURED"),
    APPROVED(0, "APPROVED"),
    POSTED(0, "POSTED");
    
    
    //Enum variables
    final int id;
    final String label;
    
    /** Creates a new instance of Modules */
    JournalStatus(final int id, final String label) {
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
    
    public static JournalStatus fromString(final String str) {
        for (JournalStatus e : JournalStatus.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
    
    public static JournalStatus fromId(final int id) {
        for (JournalStatus e : JournalStatus.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }

}
