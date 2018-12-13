/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.inventory.journals;

/**
 *
 * @author wikus
 */
public enum GenerateStockCountValidationType {

    ITEM_GROUP(0, "ItemGroup"),
    ITEM_ID(1, "ItemId"),
    WAREHOUSE(2, "Warehouse"),
    LOCATION(3, "Location"),
    JOURNAL_DEFINITION(4, "JournalDefinition");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of GenerateStockCountValidationType */
    GenerateStockCountValidationType(final int id, final String label) {
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

    public static GenerateStockCountValidationType fromString(final String str) {
        for (GenerateStockCountValidationType e : GenerateStockCountValidationType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static GenerateStockCountValidationType fromId(final int id) {
        for (GenerateStockCountValidationType e : GenerateStockCountValidationType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
