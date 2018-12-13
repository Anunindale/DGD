/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.base.emcprojects;

/**
 *
 * @author riaan
 */
public enum EMCProjectsEnum {

    EMC_CLIENT_CORE(0, "EMCClientCore"),
    EMC_CLIENT_SYS(1, "EMCClientSys"),
    EMC_GEN_LIB_CORE(2, "EMCGenLibCore"),
    EMC_GEN_LIB_SYS(3, "EMCGenLibSys"),
    EMC_SERVER_CORE(4, "EMCServerCore"),
    EMC_SERVER_SYS(5, "EMCServerSys");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of BaseEmployeeTypeOfConcern */
    EMCProjectsEnum(final int id, final String label) {
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

    public static EMCProjectsEnum fromString(final String str) {
        for (EMCProjectsEnum e : EMCProjectsEnum.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static EMCProjectsEnum fromId(final int id) {
        for (EMCProjectsEnum e : EMCProjectsEnum.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
