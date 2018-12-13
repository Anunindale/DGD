/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.inventory.transactions;

/**
 *
 * @author wikus
 */
public enum TransEnum {
    
     ADD_EXTRA(0, "ADD_EXTRA");
    //Enum variables
    final int id;
    final String label;
    
    /** Creates a new instance of InventoryTransactionTypes */
    TransEnum(final int id, final String label) {
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
    
    public static TransEnum fromString(final String str) {
        for (TransEnum e : TransEnum.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
    
    public static TransEnum fromId(final int id) {
        for (TransEnum e : TransEnum.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }

}
