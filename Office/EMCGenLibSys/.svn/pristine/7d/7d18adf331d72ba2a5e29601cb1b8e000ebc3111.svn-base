/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.sop.garmentrequirementspersales;

/**
 *
 * @author wikus
 */
public enum SOPGarmentRequirementsViewType {

    REQUIREMENTS(0, "Required"),
    SHORTS(1, "Short");
    //Enum variables
    final int id;
    final String label;

    /**
     * Creates a new instance of SOPGarmentRequirementsViewType
     */
    SOPGarmentRequirementsViewType(final int id, final String label) {
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

    public static SOPGarmentRequirementsViewType fromString(final String str) {
        for (SOPGarmentRequirementsViewType e : SOPGarmentRequirementsViewType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static SOPGarmentRequirementsViewType fromId(final int id) {
        for (SOPGarmentRequirementsViewType e : SOPGarmentRequirementsViewType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
