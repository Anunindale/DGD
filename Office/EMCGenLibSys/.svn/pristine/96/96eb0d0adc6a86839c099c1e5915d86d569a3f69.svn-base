/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.sop.parameters;

/**
 * @author riaan
 */
public enum UseInvoiceTo {

    ALWAYS(0, "Always"),
    NEVER(1, "Never"),
    ASK(2, "Ask");

    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of UseInvoiceTo */
    UseInvoiceTo(final int id, final String label) {
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

    public static UseInvoiceTo fromString(
            final String str) {
        for (UseInvoiceTo e : UseInvoiceTo.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }

        }
        return null;
    }

    public static UseInvoiceTo fromId(
            final int id) {
        for (UseInvoiceTo e : UseInvoiceTo.values()) {
            if (e.id == id) {
                return e;
            }

        }
        return null;
    }
}

