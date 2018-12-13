/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.inventory.journals;

/**
 *
 * @author riaan
 */
public enum MovementDirections {
    IN(0, "In"),
    OUT(1, "Out");
    
    //Enum variables
    final int id;
    final String label;
    
    /** Creates a new instance of MovementDirections */
    MovementDirections(final int id, final String label) {
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
    
    public static MovementDirections fromString(final String str) {
        for (MovementDirections e : MovementDirections.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
    
    public static MovementDirections fromId(final int id) {
        for (MovementDirections e : MovementDirections.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
