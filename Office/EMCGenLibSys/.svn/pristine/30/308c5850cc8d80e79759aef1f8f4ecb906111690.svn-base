/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.developertools;

/**
 *
 * @author wikus
 */
public enum DevQueryType {

    QUERY(0, "Query"),
    STORED_PROCEDURE(1, "Stored Procedure"),
    VIEW(2, "View");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of FinancialPeriodTypes */
    DevQueryType(final int id, final String label) {
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

    public static DevQueryType fromString(final String str) {
        for (DevQueryType e : DevQueryType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static DevQueryType fromId(final int id) {
        for (DevQueryType e : DevQueryType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}

