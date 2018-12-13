/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.creditors;

/**
 *
 * @author wikus
 */
public enum CreditorsCreditNoteInvoiceType {

    MANUAL_INVOICE(0, "Manual Invoice"),
    MANUAL_CREDIT_NOTE(1, "Manual Credit Note"),
    PURCHASING_INVOICE(2, "Purchasing Invoice"),
    PURCHASING_CREDIT_NOTE(3, "Purchasing Credit Note");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  DebtorsInvoiceType*/
    CreditorsCreditNoteInvoiceType(final int id, final String label) {
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

    public static CreditorsCreditNoteInvoiceType fromString(final String str) {
        for (CreditorsCreditNoteInvoiceType e : CreditorsCreditNoteInvoiceType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static CreditorsCreditNoteInvoiceType fromId(final int id) {
        for (CreditorsCreditNoteInvoiceType e : CreditorsCreditNoteInvoiceType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
