/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.sop.statusenquiry;

/**
 *
 * @author riaan
 */
public enum SalesStatusEnquiryTypes {

    INVOICED(1, "Invoiced"),
    OPEN(0, "Open");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of ProductionWorkTypes */
    SalesStatusEnquiryTypes(final int id, final String label) {
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

    public static SalesStatusEnquiryTypes fromString(final String str) {
        for (SalesStatusEnquiryTypes e : SalesStatusEnquiryTypes.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static SalesStatusEnquiryTypes fromId(final int id) {
        for (SalesStatusEnquiryTypes e : SalesStatusEnquiryTypes.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
