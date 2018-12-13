/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.developertools;

/**
 *
 * @author wikus
 */
public enum DevLayerType {

    CORE(0, "Core"),
    SYSTEM(1, "System"),
    CLIENT(2, "Client"),
    USER(3, "User"),
    OTHER(4, "Other");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of FinancialPeriodTypes */
    DevLayerType(final int id, final String label) {
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

    public static DevLayerType fromString(final String str) {
        for (DevLayerType e : DevLayerType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static DevLayerType fromId(final int id) {
        for (DevLayerType e : DevLayerType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}

