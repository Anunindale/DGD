/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.developertools;

/**
 *
 * @author claudette
 */
public enum DevBugType {

    BUG(1, "Bug"),
    DEVELOPMENT(2, "Development"),
    MODIFICATION(3, "Modification"),
    SUPPORT_CALL(0, "Support Call"),
    ADMIN(4, "Admin"),
    NA(5, "Not Applicable"),
    TECH(6, "Tech");
    private final int id;
    private final String label;

    DevBugType(final int id, final String label) {
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

    public static DevBugType fromString(final String str) {
        for (DevBugType e : DevBugType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static DevBugType fromId(final int id) {
        for (DevBugType e : DevBugType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
