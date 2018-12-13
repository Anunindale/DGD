/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.inventory.dimensions;

/**
 *
 * @author riaan
 */
public enum EnumDimensions {

    DIMENSION1("dimension1"),
    DIMENSION2("dimension2"),
    DIMENSION3("dimension3");

    //Enum variables
    final String dimension;

    /** Creates a new instance of EnumDimensions */
    EnumDimensions(final String dimension) {
        this.dimension = dimension;
    }


    @Override
    public String toString() {
        return dimension;
    }

    public static EnumDimensions fromString(final String str) {
        for (EnumDimensions e : EnumDimensions.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
}
