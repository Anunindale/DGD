/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.sop.customerlabels;

/**
 *
 * @author rico
 */
public enum CustomerLabelDocuments {

    EDCON(0, "Edcon");

    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of CustomerStatusEnum */
    CustomerLabelDocuments(final int id, final String label) {
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

    public static CustomerLabelDocuments fromString(
            final String str) {
        for (CustomerLabelDocuments e : CustomerLabelDocuments.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }

        }
        return null;
    }

    public static CustomerLabelDocuments fromId(
            final int id) {
        for (CustomerLabelDocuments e : CustomerLabelDocuments.values()) {
            if (e.id == id) {
                return e;
            }

        }
        return null;
    }
}