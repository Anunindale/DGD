/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.inventory.pickinglist;

/**
 *
 * @author riaan
 */
public enum PickingListStatusses {

    OPEN(0, "OPEN"),
    POSTED(1, "POSTED"),
    CANCELLED(2, "CANCELLED");
    
    
    //Enum variables
    final int id;
    final String label;
    
    /** Creates a new instance of PickingListStatusses */
    PickingListStatusses(final int id, final String label) {
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
    
    public static PickingListStatusses fromString(final String str) {
        for (PickingListStatusses e : PickingListStatusses.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
    
    public static PickingListStatusses fromId(final int id) {
        for (PickingListStatusses e : PickingListStatusses.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
