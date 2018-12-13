/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.base.sms;

/**
 *
 * @author wikus
 */
public enum SMSSendingOptions {

    FILE_SENDING(0, "File Sending"),
    EMAIL_SENDING(1, "Email Sending"),
    URL_SENDING(2,"URL Sending");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of OperatingSystems */
    SMSSendingOptions(final int id, final String label) {
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
    public static SMSSendingOptions fromString(final String str) {
        for (SMSSendingOptions e : SMSSendingOptions.values()) {
            if (e.toString().contains(str)) {
                return e;
            }
        }
        return null;
    }

    public static SMSSendingOptions fromId(final int id) {
        for (SMSSendingOptions e : SMSSendingOptions.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }

    /**
     * Returns the OS enum value for a specified OS name.  I.e. Windows 95 and Windows 2003 are both 'Windows'.
     * @param osName OS Name.
     * @return Enum value for the specified OS name.
     */
    public static SMSSendingOptions getOS(String osName) {
        for (SMSSendingOptions operatingSystem : values()) {
            if (osName.startsWith(operatingSystem.toString())) {
                return operatingSystem;
            }
        }

        return null;
    }
}
