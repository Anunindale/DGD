/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.base.webfilepaths;

/**
 *
 * @author wikus
 */
public enum BaseWebFilePathProcesses {

    STUDY_MATERIAL(0, "Study Material"),
    COMPLETED_ASSIGNMENTS(1, "Completed Assignments"),
    MARKED_ASSIGNMENTS(2, "Marked Assignments"),
    RESULT_STATEMENTS(3, "Result Statement"),
    TREC_IMAGES(4, "Trec Images");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of DocumentTypes */
    BaseWebFilePathProcesses(final int id, final String label) {
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

    public static BaseWebFilePathProcesses fromString(final String str) {
        for (BaseWebFilePathProcesses e : BaseWebFilePathProcesses.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static BaseWebFilePathProcesses fromId(final int id) {
        for (BaseWebFilePathProcesses e : BaseWebFilePathProcesses.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
