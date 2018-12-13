/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.base.filepaths;

/**
 *
 * @author riaan
 */
public enum OperatingSystems {

    //See this webpage for a list of OS names recognised by Java: http://lopica.sourceforge.net/os.html
    WINDOWS(0, "Windows"),
    MAC(1, "Mac"),
    LINUX(2, "Linux"),
    SOLARIS(3, "Solaris"),
    HP(4, "HP"),
    OS_390(4, "OS/390"),
    FreeBSD(5, "FreeBSD"),
    IRIX(6, "Irix"),
    DIGITAL_UNIX(7, "Digital_Unix"),
    NETWARE(8, "NetWare"),
    OSF1(9, "OSF1"),
    OpenVMS(10, "OpenVMS");
    
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of OperatingSystems */
    OperatingSystems(final int id, final String label) {
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
    public static OperatingSystems fromString(final String str) {
        for (OperatingSystems e : OperatingSystems.values()) {
            if (e.toString().contains(str)) {
                return e;
            }
        }
        return null;
    }

    public static OperatingSystems fromId(final int id) {
        for (OperatingSystems e : OperatingSystems.values()) {
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
    public static OperatingSystems getOS(String osName) {
        for (OperatingSystems operatingSystem : values()) {
            if (osName.startsWith(operatingSystem.toString())) {
                return operatingSystem;
            }
        }

        return null;
    }
}
