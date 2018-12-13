/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.inventory.pickinglist;

/**
 *
 * @author w
 */
public enum PickingListPickFrom {

    INVENTORY(0, "Inventory"),
    ASSEMBLY(1, "Assembly");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of PickingListTypes */
    PickingListPickFrom(final int id, final String label) {
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

    public static PickingListPickFrom fromString(final String str) {
        for (PickingListPickFrom e : PickingListPickFrom.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static PickingListPickFrom fromId(final int id) {
        for (PickingListPickFrom e : PickingListPickFrom.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
