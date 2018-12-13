/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.inventory.settlement;

/**
 *
 * @author rico
 */
public enum SettlementStatus {
    CAPTURED(0, "Captured"),
    PARTIAL(1,"Partial Complete"),
    COMPLETED(2, "Completed"),
    ROLLBACK(3,"Rolled Back");

    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of InventoryTransactionDirection */
    SettlementStatus(final int id, final String label) {
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

    public static SettlementStatus fromString(final String str) {
        for (SettlementStatus e : SettlementStatus.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static SettlementStatus fromId(final int id) {
        for (SettlementStatus e : SettlementStatus.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
