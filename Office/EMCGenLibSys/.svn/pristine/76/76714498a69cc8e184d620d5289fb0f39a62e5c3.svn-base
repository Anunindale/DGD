/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.base.timedoperations;

/**
 *
 * @author wikus
 */
public enum EnumTimedOperationStatus {

    CREATED(0, "Created"),
    STARTED(1, "Started"),
    STOPED(2, "Stopped");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  EnumTimedOperationStatus*/
    EnumTimedOperationStatus(final int id, final String label) {
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

    public static EnumTimedOperationStatus fromString(final String str) {
        for (EnumTimedOperationStatus e : EnumTimedOperationStatus.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static EnumTimedOperationStatus fromId(final int id) {
        for (EnumTimedOperationStatus e : EnumTimedOperationStatus.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
