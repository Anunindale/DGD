/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.base.reporttools;

/**
 *
 * @author claudette
 */
public enum BaseReportSectionEnum {

    FOOTER(0, "Footer"),
    HEADER(1, "Header"),
    BODY(1, "Body");

    //Enum variables
    final int id;
    final String label;

    private BaseReportSectionEnum(final int id, final String label) {
        /** Creates a new instance of BaseEmployeeTypeOfConcern */
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

    public static BaseReportSectionEnum fromString(final String str) {
        for (BaseReportSectionEnum e : BaseReportSectionEnum.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static BaseReportSectionEnum fromId(final int id) {
        for (BaseReportSectionEnum e : BaseReportSectionEnum.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
