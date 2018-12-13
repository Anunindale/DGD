/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.inventory.pickinglist;

/**
 *
 * @author riaan
 */
public enum PickingListHeldStatusses {

    NA(0, "N/A"),
    ON_HOLD(1, "On Hold"),
    RELEASED(2, "Released");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of PickingListStatusses */
    PickingListHeldStatusses(final int id, final String label) {
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

    public static PickingListHeldStatusses fromString(final String str) {
        for (PickingListHeldStatusses e : PickingListHeldStatusses.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static PickingListHeldStatusses fromId(final int id) {
        for (PickingListHeldStatusses e : PickingListHeldStatusses.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
