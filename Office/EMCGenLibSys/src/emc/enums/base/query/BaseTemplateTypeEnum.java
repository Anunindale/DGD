/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.base.query;

/**
 *
 * @author wikus
 */
public enum BaseTemplateTypeEnum {

    EMAIL(0, "Email"),
    SMS(1, "Sms");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of OperatingSystems */
    BaseTemplateTypeEnum(final int id, final String label) {
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

    public static BaseTemplateTypeEnum fromString(final String str) {
        for (BaseTemplateTypeEnum e : BaseTemplateTypeEnum.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static BaseTemplateTypeEnum fromId(final int id) {
        for (BaseTemplateTypeEnum e : BaseTemplateTypeEnum.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
