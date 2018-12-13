/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.inventory.register;

/**
 *
 * @author wikus
 */
public enum RegisterFormTypeEnum {

    FIRST_TIME(0, "FIRST_TIME"),
    MUST_EXIST(1, "MUST_EXIST"),
    STOCK_TAKE(2, "STOCK_TAKE"),
    PRODUCTION_BOM_JOURNAL(3, "PRODUCTION_BOM_JOURNAL"),
    SOP_SO_POSTING(4, "SOP_SO_POSTING"),
    RETURN(5, "RETURN");    //Returns of stock that has been delivered (out) previously.

    //Enum variables
    final int id;
    final String label;

    RegisterFormTypeEnum(final int id, final String label) {
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

    public static RegisterFormTypeEnum fromString(final String str) {
        for (RegisterFormTypeEnum e : RegisterFormTypeEnum.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static RegisterFormTypeEnum fromId(final int id) {
        for (RegisterFormTypeEnum e : RegisterFormTypeEnum.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
