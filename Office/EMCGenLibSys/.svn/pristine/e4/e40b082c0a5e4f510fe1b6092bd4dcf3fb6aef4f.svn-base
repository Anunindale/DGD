/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.base.uom;

/**
 *
 * @author wikus
 */
public enum UOMTypes {

    QUANTITY(0, "Quantity"),
    TIME(1, "Time"),
    VOLUME(2, "Volume"),
    MASS(3, "Mass"),
    LENGTH(4, "Length"),
    AREA(5, "Area"),
    TEMP(6, "Temp"),
    OTHER(7, "Other");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of DocumentTypes */
    UOMTypes(final int id, final String label) {
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

    public static UOMTypes fromString(final String str) {
        for (UOMTypes e : UOMTypes.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static UOMTypes fromId(final int id) {
        for (UOMTypes e : UOMTypes.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
