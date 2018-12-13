/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.inventory.journals;

/**
 *
 * @author wikus
 */
public enum StockTakeVarianceType {

    ALL(0, "All"),
    VARIANCE_ONLY(1, "Variance Only"),
    NO_VARIANCE_ONLY(2, "No Variance Only");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of GenerateStockCountValidationType */
    StockTakeVarianceType(final int id, final String label) {
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

    public static StockTakeVarianceType fromString(final String str) {
        for (StockTakeVarianceType e : StockTakeVarianceType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static StockTakeVarianceType fromId(final int id) {
        for (StockTakeVarianceType e : StockTakeVarianceType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
