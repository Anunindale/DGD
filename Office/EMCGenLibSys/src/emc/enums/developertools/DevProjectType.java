/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.developertools;

/**
 *
 * @author wikus
 */
public enum DevProjectType {

    CLIENT(0, "Client"),
    GENLIB(1, "Genlib"),
    SERVER(2, "Server"),
    OTHER(3, "Other");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of FinancialPeriodTypes */
    DevProjectType(final int id, final String label) {
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

    public static DevProjectType fromString(final String str) {
        for (DevProjectType e : DevProjectType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static DevProjectType fromId(final int id) {
        for (DevProjectType e : DevProjectType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}

