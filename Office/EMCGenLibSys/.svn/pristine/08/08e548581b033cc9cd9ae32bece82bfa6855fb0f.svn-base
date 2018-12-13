/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.base.sms;

/**
 *
 * @author wikus
 */
public enum SMSFileOutputFields {

    NUMBER(0, "Number"),
    MESSAGE(1, "Message"),
    ROW_COUNT(2, "Row Count");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of OperatingSystems */
    SMSFileOutputFields(final int id, final String label) {
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

    /**
     * This method does not check for an exact match.  A partial match is sufficient,
     * as Windows 95, Windows 98, Windows Vista, etc are all seen as 'Windows' by the system.
     * Please note that the partial check is case sensitive.
     *
     * @param str String to check.
     * @return An instance of OperatingSystems.
     */
    public static SMSFileOutputFields fromString(final String str) {
        for (SMSFileOutputFields e : SMSFileOutputFields.values()) {
            if (e.toString().contains(str)) {
                return e;
            }
        }
        return null;
    }

    public static SMSFileOutputFields fromId(final int id) {
        for (SMSFileOutputFields e : SMSFileOutputFields.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }

    
}
