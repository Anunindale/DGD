/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.developertools;

/**
 *
 * @author wikus
 */
public enum DevConnectionType {

    GLASSFISH(0, "Glassfish"),
    MYSQL(1, "MySQL"),
    REMOTE_SSH(2, "Remote SSH"),
    REMOTE_DESKTOP(3, "Remote Desktop"),
    SUBVERSION(3, "Subversion");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of FinancialPeriodTypes */
    DevConnectionType(final int id, final String label) {
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

    public static DevConnectionType fromString(final String str) {
        for (DevConnectionType e : DevConnectionType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static DevConnectionType fromId(final int id) {
        for (DevConnectionType e : DevConnectionType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}

