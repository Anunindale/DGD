/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.debtors;

/**
 *
 * @author riaan
 */
public enum DebtorsInvoiceCreditNoteType {

    MANUAL_INVOICE(0, "Manual Invoice"),
    MANUAL_CREDIT_NOTE(1, "Manual Credit Note"),
    SALES_INVOICE(2, "Sales Invoice"),
    SALES_CREDIT_NOTE(3, "Sales Credit Note"),
    DISTRIBUTION_INVOICE(4, "Distribution Invoice");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  DebtorsInvoiceType*/
    DebtorsInvoiceCreditNoteType(final int id, final String label) {
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

    public static DebtorsInvoiceCreditNoteType fromString(final String str) {
        for (DebtorsInvoiceCreditNoteType e : DebtorsInvoiceCreditNoteType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static DebtorsInvoiceCreditNoteType fromId(final int id) {
        for (DebtorsInvoiceCreditNoteType e : DebtorsInvoiceCreditNoteType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
