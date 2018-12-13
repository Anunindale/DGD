/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.base.query;

/**
 *
 * @author wikus
 */
public enum BaseQueryRecipientTypeEnum {

    SMS(0, "SMS - Number"),
    SMSPC(1,"SMS - Postal Code"),
    SMS_CRMLOG(2,"SMS - Log Field"),
    EMAIL(3, "Email"),
    EMAIL_CC(4, "Email - CC"),
    EMAIL_BCC(5, "Email - BCC"),
    EMAIL_CRMLOG(6,"Email - Log Field");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of OperatingSystems */
    BaseQueryRecipientTypeEnum(final int id, final String label) {
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

    public static BaseQueryRecipientTypeEnum fromString(final String str) {
        for (BaseQueryRecipientTypeEnum e : BaseQueryRecipientTypeEnum.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static BaseQueryRecipientTypeEnum fromId(final int id) {
        for (BaseQueryRecipientTypeEnum e : BaseQueryRecipientTypeEnum.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
