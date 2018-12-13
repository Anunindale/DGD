/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.debtors.invoice;

/**
 *
 * @author riaan
 */
public enum DebtorsInvoicePrintType {

    TAX_INVOICE(0, "Tax Invoice"),
    TAX_INVOICE_COPY(1, "Copy Tax Invoice"),
    DELIVERY_NOTE(2, "Delivery Note"),
    CREDIT_NOTE(3, "Credit Note"),
    CREDIT_NOTE_COPY(4, "Copy Credit Note"),
    PROFORMA_INVOICE(5, "Pro Forma Invoice"),
    PROFORMA_CREDIT_NOTE(6, "Pro Forma Credit Note"),
    DIST_INVOICE(7, "Distribution Invoice"),
    DIST_INVOICE_COPY(8, "Copy Distribution Invoice"),
    CANCELLED(9,"Cancelled"),
    CANCELLED_COPY(10,"Copy Cancelled"),
    PRINT_INSTRUCTION(11, "Print Instruction");
    //Enum variables
    final int id;
    final String label;

    /**
     * Creates a new instance of  DebtorsInvoiceType
     */
    DebtorsInvoicePrintType(final int id, final String label) {
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

    public static DebtorsInvoicePrintType fromString(final String str) {
        for (DebtorsInvoicePrintType e : DebtorsInvoicePrintType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static DebtorsInvoicePrintType fromId(final int id) {
        for (DebtorsInvoicePrintType e : DebtorsInvoicePrintType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
