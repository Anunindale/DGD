/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.base.databaseconnection;

/**
 *
 * @author wikus
 */
public enum DBConnection {

    EMC(0, "EMC"),
    DRUPEL(1, "Drupel");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of Modules */
    DBConnection(final int id, final String label) {
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

    public static DBConnection fromString(final String str) {
        for (DBConnection e : DBConnection.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static DBConnection fromId(final int id) {
        for (DBConnection e : DBConnection.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
