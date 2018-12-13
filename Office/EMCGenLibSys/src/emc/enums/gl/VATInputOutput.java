/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.gl;

/**
 *
 * @author riaan
 */
public enum VATInputOutput {

    INPUT(0, "Input"),
    OUTPUT(1, "Output");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of COAAccountTypes */
    VATInputOutput(final int id, final String label) {
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

    public static VATInputOutput fromString(final String str) {
        for (VATInputOutput e : VATInputOutput.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static VATInputOutput fromId(final int id) {
        for (VATInputOutput e : VATInputOutput.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
