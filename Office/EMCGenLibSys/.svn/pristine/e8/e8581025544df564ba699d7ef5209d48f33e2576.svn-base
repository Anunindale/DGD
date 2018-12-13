/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.pop.supplierreceivedjournals;

/**
 *
 * @author riaan
 */
public enum ReceivedJournalType {
    
    RECEIVED_NOTE(0, "GRN"),
    RETURN_GOODS(1,"RN");
    
    //Enum variables
    final int id;
    final String label;
    
    /** Creates a new instance of ReceivedJournalType */
    ReceivedJournalType(final int id, final String label) {
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
    
    public static ReceivedJournalType fromString(final String str) {
        for (ReceivedJournalType e : ReceivedJournalType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
    
    public static ReceivedJournalType fromId(final int id) {
        for (ReceivedJournalType e : ReceivedJournalType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
    