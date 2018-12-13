/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.inventory.inventorytables;

/**
 *
 * @author riaan
 */
public enum InventoryLocationsEnum {

    RECEIVING_AREA(0, "REC"),
    QUALITY_CHECK(1, "QC"),
    DESPATCH(2, "DESP");

    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of InventoryWarehouseTypes */
    InventoryLocationsEnum(final int id, final String label) {
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

    public static InventoryLocationsEnum fromString(final String str) {
        for (InventoryLocationsEnum e : InventoryLocationsEnum.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static InventoryLocationsEnum fromId(final int id) {
        for (InventoryLocationsEnum e : InventoryLocationsEnum.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
