/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.inventory.inventorytables;

/**
 *
 * @author rico
 */
public enum InventoryStatus {
    ACTIVE(0, "Active"),
    INDESIGN(1,"InDesign"),
    STOPPED(2,"Stopped"),
    REJECTED(3,"Rejected");
    
    

    //Enum variables
    final int id;
    final String label;
    
    /** Creates a new instance of InventoryWarehouseTypes */
    InventoryStatus(final int id, final String label) {
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
    
    public static InventoryStatus fromString(final String str) {
        for (InventoryStatus e : InventoryStatus.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
    
    public static InventoryStatus fromId(final int id) {
        for (InventoryStatus e : InventoryStatus.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
