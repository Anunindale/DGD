/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.inventory.register;

/**
 *
 * @author wikus
 */
public enum RegisterFromTypeEnum {

    PO(0, "PO"),
    JRN(1, "JRN"),
    PROD_JRN(2, "PROD_JRN"),
    SO(3, "SO"),
    PROD_ASSY(3, "PROD_ASSY"),
    RETURN(4, "RETURN"),
    DEBTORS_RECEIVE(5, "DEBTORS_RECEIVE");    //Returns of stock that has been delivered (out) previously.

    //Enum variables
    final int id;
    final String label;

    RegisterFromTypeEnum(final int id, final String label) {
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

    public static RegisterFromTypeEnum fromString(final String str) {
        for (RegisterFromTypeEnum e : RegisterFromTypeEnum.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static RegisterFromTypeEnum fromId(final int id) {
        for (RegisterFromTypeEnum e : RegisterFromTypeEnum.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
