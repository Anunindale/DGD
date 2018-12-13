/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.sop.discountsetup;

/**
 *
 * @author riaan
 */
public enum ItemSelectionType {

    ALL(0, "All"),
    GROUP(1, "Group"),
    ITEM(2, "Item");

    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  CustomerSelectionType.  */
    ItemSelectionType(final int id, final String label) {
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

    public static ItemSelectionType fromString(final String str) {
        for (ItemSelectionType e : ItemSelectionType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static ItemSelectionType fromId(final int id) {
        for (ItemSelectionType e : ItemSelectionType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
