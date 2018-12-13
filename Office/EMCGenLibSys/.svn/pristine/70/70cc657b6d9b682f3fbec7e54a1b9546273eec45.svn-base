/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.sop.discountsetup;

/**
 *
 * @author riaan
 */
public enum CustomerSelectionType {

    ALL(0, "All"),
    GROUP(1, "Group"),
    CUSTOMER(2, "Customer");

    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  CustomerSelectionType.  */
    CustomerSelectionType(final int id, final String label) {
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

    public static CustomerSelectionType fromString(final String str) {
        for (CustomerSelectionType e : CustomerSelectionType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static CustomerSelectionType fromId(final int id) {
        for (CustomerSelectionType e : CustomerSelectionType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
