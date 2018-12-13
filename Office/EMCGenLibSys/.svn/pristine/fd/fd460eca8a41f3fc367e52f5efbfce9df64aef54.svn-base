/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.inventory.pickinglist;

/**
 *
 * @author wikus
 */
public enum PickingListCreationTypes {

    SYSTEM(0, "System"),
    CAPTURED(1, "Captured");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of PickingListTypes */
    PickingListCreationTypes(final int id, final String label) {
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

    public static PickingListCreationTypes fromString(final String str) {
        for (PickingListCreationTypes e : PickingListCreationTypes.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static PickingListCreationTypes fromId(final int id) {
        for (PickingListCreationTypes e : PickingListCreationTypes.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
