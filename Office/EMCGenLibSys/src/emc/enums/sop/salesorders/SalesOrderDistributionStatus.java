/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.sop.salesorders;

/**
 *
 * @author wikus
 */
public enum SalesOrderDistributionStatus {

    NONE(0, "None"),
    READY(1, "Ready");
    //Enum variables
    final int id;
    final String label;

    /**
     * Creates a new instance of SalesOrderStatus
     */
    SalesOrderDistributionStatus(final int id, final String label) {
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

    public static SalesOrderDistributionStatus fromString(
            final String str) {
        for (SalesOrderDistributionStatus e : SalesOrderDistributionStatus.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }

        }
        return null;
    }

    public static SalesOrderDistributionStatus fromId(
            final int id) {
        for (SalesOrderDistributionStatus e : SalesOrderDistributionStatus.values()) {
            if (e.id == id) {
                return e;
            }

        }
        return null;
    }
}
