/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.inventory.journals;

/**
 *
 * @author riaan
 */
public enum ContraTypes {

    GL(0, "GL"),
    DR(1, "DR"),
    CR(2, "CP"),
    P(3, "P");

    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  GLAccountStatus*/
    ContraTypes(final int id, final String label) {
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

    public static ContraTypes fromString(final String str) {
        for (ContraTypes e : ContraTypes.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static ContraTypes fromId(final int id) {
        for (ContraTypes e : ContraTypes.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
