/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.base.timedoperations;

/**
 *
 * @author wikus
 */
public enum EnumTimedOperationParameters {

    COMPANY_ID(0, "COMPANY_ID"),
    OPERATION_ID(1, "OPERATION_ID");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  EnumTimedOperationStatus*/
    EnumTimedOperationParameters(final int id, final String label) {
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

    public static EnumTimedOperationParameters fromString(final String str) {
        for (EnumTimedOperationParameters e : EnumTimedOperationParameters.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static EnumTimedOperationParameters fromId(final int id) {
        for (EnumTimedOperationParameters e : EnumTimedOperationParameters.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
