/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.base.uom;

/**
 *
 * @author wikus
 */
public enum SystemUOM {

    HOURS(0, "Hours", UOMTypes.TIME.toString()),
    MINUTES(1, "Min", UOMTypes.TIME.toString()),
    CENTIMETERS(2, "cm", UOMTypes.LENGTH.toString()),
    KILORAMS(3, "kg", UOMTypes.MASS.toString()),
    B_MIN(4, "b/min", UOMTypes.OTHER.toString());
    //Enum variables
    final int id;
    final String label;
    final String type;

    /** Creates a new instance of DocumentTypes */
    SystemUOM(final int id, final String label, final String type) {
        this.id = id;
        this.label = label;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return label;
    }

    public static SystemUOM fromString(final String str) {
        for (SystemUOM e : SystemUOM.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static SystemUOM fromId(final int id) {
        for (SystemUOM e : SystemUOM.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
