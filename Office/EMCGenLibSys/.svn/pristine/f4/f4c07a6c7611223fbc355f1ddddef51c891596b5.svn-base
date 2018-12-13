/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.inventory.printtypes;

/**
 *
 * @author wikus
 */
public enum StockTakePrintType {

    PORTRAIT(0, "Portrait"),
    LANDSCAPE(1, "Landscape");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of PickingListTypes */
    StockTakePrintType(final int id, final String label) {
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

    public static StockTakePrintType fromString(final String str) {
        for (StockTakePrintType e : StockTakePrintType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static StockTakePrintType fromId(final int id) {
        for (StockTakePrintType e : StockTakePrintType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
