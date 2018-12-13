/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.inventory.pickinglist;

/**
 *
 * @author w
 */
public enum PickingListIssueTypes {

    MANUAL(0, "Manual"),
    BACKFLUSH(1, "Backflush");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of PickingListTypes */
    PickingListIssueTypes(final int id, final String label) {
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

    public static PickingListIssueTypes fromString(final String str) {
        for (PickingListIssueTypes e : PickingListIssueTypes.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static PickingListIssueTypes fromId(final int id) {
        for (PickingListIssueTypes e : PickingListIssueTypes.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
