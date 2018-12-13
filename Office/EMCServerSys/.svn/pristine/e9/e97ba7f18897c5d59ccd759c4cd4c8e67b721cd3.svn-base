/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.onlineusers.resources;

/**
 *
 * @author wikus
 */
public enum OnlineUsersMapAccess {

    GET(0, "GET"),
    KEY_SET(1, "KEY_SET"),
    SIZE(2, "SIZE"),
    PUT(3, "PUT"),
    REMOVE(4, "REMOVE"),
    VALUES(5, "VALUES");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  OnlineUsersMapAccess*/
    OnlineUsersMapAccess(final int id, final String label) {
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

    public static OnlineUsersMapAccess fromString(final String str) {
        for (OnlineUsersMapAccess e : OnlineUsersMapAccess.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static OnlineUsersMapAccess fromId(final int id) {
        for (OnlineUsersMapAccess e : OnlineUsersMapAccess.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
