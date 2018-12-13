/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.developertools;

/**
 *
 * @author claudette
 */
public enum DevInstallationProperties {

    Deploy_Version_Location(0, "Deploy_Version_Location"),
    Deploy_Client_Location(1, "Deploy_Client_Location"),
    Deploy_Server_Location(2, "Deploy_Server_Location"),
    Deploy_Glassfish_Manual_Deploy(3, "Deploy_Glassfish_Manual_Deploy"),
    Deploy_Glassfish_Location(4, "Deploy_Glassfish_Location"),
    Deploy_Glassfish_User_Name(5, "Deploy_Glassfish_User_Name"),
    Deploy_Glassfish_Password_Location(6, "Deploy_Glassfish_Password_Location"),
    Deploy_Glassfish_Domain_Name(7, "Deploy_Glassfish_Domain_Name");
    private final int id;
    private final String label;

    DevInstallationProperties(final int id, final String label) {
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

    public static DevInstallationProperties fromString(final String str) {
        for (DevInstallationProperties e : DevInstallationProperties.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static DevInstallationProperties fromId(final int id) {
        for (DevInstallationProperties e : DevInstallationProperties.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
