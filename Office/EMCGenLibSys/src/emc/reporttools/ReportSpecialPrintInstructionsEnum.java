/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reporttools;

/**
 *
 * @author rico
 */
public enum ReportSpecialPrintInstructionsEnum {

    NONE(0, "NONE"), //no special print instructions
    PICKLIST_DIRECT(1, "PICKLIST_DIRECT");
    //Enum variables
    final int id;
    final String label;

    /**
     * Creates a new instance of DocumentTypes
     */
    ReportSpecialPrintInstructionsEnum(final int id, final String label) {
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

    public static ReportSpecialPrintInstructionsEnum fromString(final String str) {
        for (ReportSpecialPrintInstructionsEnum e : ReportSpecialPrintInstructionsEnum.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static ReportSpecialPrintInstructionsEnum fromId(final int id) {
        for (ReportSpecialPrintInstructionsEnum e : ReportSpecialPrintInstructionsEnum.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
