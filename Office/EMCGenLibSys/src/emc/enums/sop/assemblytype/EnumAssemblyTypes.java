/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.sop.assemblytype;

/**
 *
 * @author riaan
 */
public enum EnumAssemblyTypes {

    AFS(0, "Assemble From Stock"),
    MTO(1, "Make To Order"),
    DIST(2, "Distribution");
    //Enum variables
    final int id;
    final String label;

    /**
     * Creates a new instance of WOType
     */
    EnumAssemblyTypes(final int id, final String label) {
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

    public static EnumAssemblyTypes fromString(final String str) {
        for (EnumAssemblyTypes e : EnumAssemblyTypes.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static EnumAssemblyTypes fromId(final int id) {
        for (EnumAssemblyTypes e : EnumAssemblyTypes.values()) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }
}