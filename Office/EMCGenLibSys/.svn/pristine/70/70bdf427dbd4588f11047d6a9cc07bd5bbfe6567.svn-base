/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.sop.customers;

/**
 * @description : This enum represents customer statusses.
 *
 * @date        : 03 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public enum CustomerStatusEnum {

    ACTIVE(0, "Active"),
    INACTIVE(1, "Inactive"),
    STOPPED(2, "Stopped"),
    CLOSED(3, "Closed");

    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of CustomerStatusEnum */
    CustomerStatusEnum(final int id, final String label) {
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

    public static CustomerStatusEnum fromString(
            final String str) {
        for (CustomerStatusEnum e : CustomerStatusEnum.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }

        }
        return null;
    }

    public static CustomerStatusEnum fromId(
            final int id) {
        for (CustomerStatusEnum e : CustomerStatusEnum.values()) {
            if (e.id == id) {
                return e;
            }

        }
        return null;
    }
}

