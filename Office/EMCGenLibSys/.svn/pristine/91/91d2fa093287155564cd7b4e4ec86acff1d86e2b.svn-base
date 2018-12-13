/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.gl;

/**
 *
 * @author claudette
 */
public enum TransactionSourceTypes {

    PRODUCTION_PICKLIST(0, "Production Picking List"),
    DEBTORS(1, "Debtors"),
    INVOICE(2, "Invoice"),
    CRN(3, "CRN"),
    GL_JOURNAL(4, "GL Journal");
    
    final int id;
    final String label;

    private TransactionSourceTypes(final int id, final String label) {
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

    public static TransactionSourceTypes fromString(final String str) {
        for (TransactionSourceTypes e : TransactionSourceTypes.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static TransactionSourceTypes fromId(final int id) {
        for (TransactionSourceTypes e : TransactionSourceTypes.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
