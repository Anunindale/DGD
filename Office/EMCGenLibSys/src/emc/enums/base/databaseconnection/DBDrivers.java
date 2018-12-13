/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.base.databaseconnection;

/**
 *
 * @author wikus
 */
public enum DBDrivers {

    MY_SQL_DATA_SOURCE(0, "com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of Modules */
    DBDrivers(final int id, final String label) {
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

    public static DBDrivers fromString(final String str) {
        for (DBDrivers e : DBDrivers.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static DBDrivers fromId(final int id) {
        for (DBDrivers e : DBDrivers.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
