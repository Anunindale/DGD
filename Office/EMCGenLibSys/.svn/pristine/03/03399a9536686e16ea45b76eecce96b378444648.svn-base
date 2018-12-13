/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.gl;

/**
 *
 * @author riaan
 */
public enum COAAccountTypes {

    ASSET(0, "Asset"),
    LIABILITY(1, "Liability"),
    INCOME(2, "Income"),
    EXPENSE(3, "Expense");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of COAAccountTypes */
    COAAccountTypes(final int id, final String label) {
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

    public static COAAccountTypes fromString(final String str) {
        for (COAAccountTypes e : COAAccountTypes.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static COAAccountTypes fromId(final int id) {
        for (COAAccountTypes e : COAAccountTypes.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
