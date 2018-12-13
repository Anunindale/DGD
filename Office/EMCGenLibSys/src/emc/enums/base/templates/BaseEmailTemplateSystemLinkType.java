/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.base.templates;

/**
 *
 * @author kapeshi
 */
public enum BaseEmailTemplateSystemLinkType {

    WELCOME_DEBIT(0, "Welcome Debit Order"),
    WELCOME_BANKDEPOSIT(1, "Welcome Bank Deposit"),
    WELCOME_FULL_PAYMENT(2, "Welcome Full Payment");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  HRDependantsTypes*/
    BaseEmailTemplateSystemLinkType(final int id, final String label) {
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

    public static BaseEmailTemplateSystemLinkType fromString(final String str) {
        for (BaseEmailTemplateSystemLinkType e : BaseEmailTemplateSystemLinkType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static BaseEmailTemplateSystemLinkType fromId(final int id) {
        for (BaseEmailTemplateSystemLinkType e : BaseEmailTemplateSystemLinkType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
