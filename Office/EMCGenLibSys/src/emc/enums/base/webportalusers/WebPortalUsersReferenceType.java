/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.base.webportalusers;

/**
 *
 * @author wikus
 */
public enum WebPortalUsersReferenceType {

    CAPTURED(0, "Captured"),
    STUDENTS(1, "Student"),
    LECTURER(2, "Lecturer"),
    CUSTOMER(3, "Customer");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of DocumentTypes */
    WebPortalUsersReferenceType(final int id, final String label) {
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

    public static WebPortalUsersReferenceType fromString(final String str) {
        for (WebPortalUsersReferenceType e : WebPortalUsersReferenceType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static WebPortalUsersReferenceType fromId(final int id) {
        for (WebPortalUsersReferenceType e : WebPortalUsersReferenceType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
