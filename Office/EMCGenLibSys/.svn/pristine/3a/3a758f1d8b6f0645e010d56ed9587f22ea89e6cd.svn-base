/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.debtors.invoicestatus;

/**
 *
 * @author riaan
 */
public enum DebtorsInvoiceStatus {

    CAPTURED(0, "Captured"),
    APPROVED(1, "Approved"),
    POSTED(2, "Posted"),
    HELD(3, "Held"),
    CANCELED(4, "Cancelled"),
    DISTRIBUTION(5, "Distribution");
    //Enum variables
    final int id;
    final String label;

    /**
     * Creates a new instance of  DebtorsInvoiceStatus
     */
    DebtorsInvoiceStatus(final int id, final String label) {
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

    public static DebtorsInvoiceStatus fromString(final String str) {
        for (DebtorsInvoiceStatus e : DebtorsInvoiceStatus.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static DebtorsInvoiceStatus fromId(final int id) {
        for (DebtorsInvoiceStatus e : DebtorsInvoiceStatus.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
