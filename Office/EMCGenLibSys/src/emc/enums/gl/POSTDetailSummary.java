/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.gl;

/**
 *
 * @author riaan
 */
public enum POSTDetailSummary {

    DETAIL(0, "Detail"),
    SUMMARY(1, "Summary");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of COAAccountTypes */
    POSTDetailSummary(final int id, final String label) {
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

    public static POSTDetailSummary fromString(final String str) {
        for (POSTDetailSummary e : POSTDetailSummary.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static POSTDetailSummary fromId(final int id) {
        for (POSTDetailSummary e : POSTDetailSummary.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
