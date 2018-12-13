/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.base.setupstorage;

/**
 *
 * @author wikus
 */
public enum SetupStorageType {

    DM_ENQUIRY(0, "DM Enquiry"),
    DM_GROSS_REQUIREMENTS(1, "DM Gross Requirements"),
    DM_RESOURCE_REQUIREMENTS(2, "DM Resource Requirements"),
    DM_PRODUCTION_PLAN(3, "DM Production Plan"),
    DM_FORECAST_ENQUIRY(4, "DM Forecast Enquiry"),
    MPS_USER_VIEW(5, "MPS User View");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of Modules */
    SetupStorageType(final int id, final String label) {
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

    public static SetupStorageType fromString(final String str) {
        for (SetupStorageType e : SetupStorageType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static SetupStorageType fromId(final int id) {
        for (SetupStorageType e : SetupStorageType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
