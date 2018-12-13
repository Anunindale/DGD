/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.um;

/**
 *
 * @author wikus
 */
public enum UMElearningEventType {

    WEB_LOGIN(0, "WEB_Login"),
    WEB_MARKS(1, "WEB_Marks"),
    WEB_RESULTSTATEMENT(2, "WEB_ResultStmt"),
    WEB_DOWLOADBOOK(3, "WEB_DowloadBook"),
    WEB_UPDATEDETAILS(4,"WEB_UpdateDetails"),
    WEB_UPLOAD(5,"WEB_Upload"),
    APP_LOGIN(6,"APP_Login"),
    APP_MARKS(7,"APP_Marks"),
    APP_SRO(8,"APP_Sro"),
    APP_FINANCE(9,"APP_Finance");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  UMElearningEventType*/
    UMElearningEventType(final int id, final String label) {
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

    public static UMElearningEventType fromString(final String str) {
        for (UMElearningEventType e : UMElearningEventType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static UMElearningEventType fromId(final int id) {
        for (UMElearningEventType e : UMElearningEventType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
