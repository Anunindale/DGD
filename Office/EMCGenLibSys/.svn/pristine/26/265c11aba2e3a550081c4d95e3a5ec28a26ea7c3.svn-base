/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.inventory.dimensions;

/**
 *
 * @author wikus
 */
public enum DimensionsEnum {

    DIMENSION1(0, "dimension1"),
    DIMENSION3(1, "dimension3"),
    DIMENSION2(2, "dimension2"),
    BATCH(3, "batch"),
    SERIAL(4, "serial"),
    WAREHOUSE(5, "warehouse"),
    LOCATION(6, "location"),
    PALLET(7, "pallet");

    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of DimensionsEnum */
    DimensionsEnum(final int id, final String label) {
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

    public static DimensionsEnum fromString(final String str) {
        for (DimensionsEnum e : DimensionsEnum.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static DimensionsEnum fromId(final int id) {
        for (DimensionsEnum e : DimensionsEnum.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
