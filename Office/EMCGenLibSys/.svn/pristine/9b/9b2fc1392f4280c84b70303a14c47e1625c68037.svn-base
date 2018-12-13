/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.base.query;

/**
 *
 * @author wikus
 */
public enum BaseQueryTypeEnum {

    MERGE_TEMPLATE(0, "Merge Template"),
    RECIPIENT(1, "Recipient");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of OperatingSystems */
    BaseQueryTypeEnum(final int id, final String label) {
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

    public static BaseQueryTypeEnum fromString(final String str) {
        for (BaseQueryTypeEnum e : BaseQueryTypeEnum.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static BaseQueryTypeEnum fromId(final int id) {
        for (BaseQueryTypeEnum e : BaseQueryTypeEnum.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
