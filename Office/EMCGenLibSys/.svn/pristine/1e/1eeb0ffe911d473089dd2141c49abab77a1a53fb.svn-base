package emc.enums.base.journals;
/**
 * 
 * @Author wikus
 */

public enum Modules {
    INVENTORY(0, "Inventory"),
    DEBTORS(1, "Debtors"),
    GL(2, "General Ledger");
    
    //Enum variables
    final int id;
    final String label;
    
    /** Creates a new instance of Modules */
    Modules(final int id, final String label) {
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
    
    public static Modules fromString(final String str) {
        for (Modules e : Modules.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
    
    public static Modules fromId(final int id) {
        for (Modules e : Modules.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}