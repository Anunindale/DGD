/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.workflow;

/**
 *
 * @author wikus
 */
public enum enumActivitySMSEmailState {

    //My Activities
    NONE(0, "None"),
    TO_SEND(0, "To Send"),
    SENT(0, "Sent");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  HRDependantsTypes*/
    enumActivitySMSEmailState(final int id, final String label) {
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

    public static enumActivitySMSEmailState fromString(final String str) {
        for (enumActivitySMSEmailState e : enumActivitySMSEmailState.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static enumActivitySMSEmailState fromId(final int id) {
        for (enumActivitySMSEmailState e : enumActivitySMSEmailState.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
