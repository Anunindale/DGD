/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.pop.posting;

/**
 *
 * @author riaan
 */
public enum PostingQuantities {

    ALL(0, "ALL"),
    RECEIVED(1,"RECEIVE NOW"),
    DELIVERY_NOTE(2,"DELIVERY_NOTE");
    
    //Enum variables
    final int id;
    final String label;
    
    /** Creates a new instance of DocurefTypes */
    PostingQuantities(final int id, final String label) {
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
    
    public static PostingQuantities fromString(final String str) {
        for (PostingQuantities e : PostingQuantities.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
    
    public static PostingQuantities fromId(final int id) {
        for (PostingQuantities e : PostingQuantities.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
    
}
