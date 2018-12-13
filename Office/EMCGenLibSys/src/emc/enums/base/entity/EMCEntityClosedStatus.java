/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.base.entity;

/**
 *
 * @author wikus
 */
public enum EMCEntityClosedStatus {

    INACTIVE(0, "Inactive"),
    ACTIVE(1, null);
    //Enum variables
    final int id;
    final String label;

    EMCEntityClosedStatus(final int id, final String label) {
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

    public static EMCEntityClosedStatus fromString(final String str) {
        for (EMCEntityClosedStatus e : EMCEntityClosedStatus.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static EMCEntityClosedStatus fromId(final int id) {
        for (EMCEntityClosedStatus e : EMCEntityClosedStatus.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
