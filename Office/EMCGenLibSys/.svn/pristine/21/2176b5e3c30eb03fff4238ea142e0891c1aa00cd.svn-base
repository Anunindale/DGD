/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.base.batchprocess;

/**
 *
 * @author rico
 */
public enum BatchProcessStatus {

    WAITING(0, "WAITING"),
    PROCESSING(1,"PROCESSING"),
    ERROR(2,"ERROR"),
    RETRY(3,"RETRY"),
    COMPLETE(4,"COMPLETE");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of DocumentTypes */
    BatchProcessStatus(final int id, final String label) {
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

    public static BatchProcessStatus fromString(final String str) {
        for (BatchProcessStatus e : BatchProcessStatus.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static BatchProcessStatus fromId(final int id) {
        for (BatchProcessStatus e : BatchProcessStatus.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
