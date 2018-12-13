/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.crm;

/**
 *
 * @author wikus
 */
public enum CRMDocumentTypeEnum {

    PROSPECT(0, "PROSPECT");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  HRDependantsTypes*/
    CRMDocumentTypeEnum(final int id, final String label) {
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

    public static CRMDocumentTypeEnum fromString(final String str) {
        for (CRMDocumentTypeEnum e : CRMDocumentTypeEnum.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static CRMDocumentTypeEnum fromId(final int id) {
        for (CRMDocumentTypeEnum e : CRMDocumentTypeEnum.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
