/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.pop.suppliers;

/**
 *
 * @author claudette
 */
public enum SupplierStatusEnum {

    ACTIVE(0, "Active"),
    INACTIVE(1, "Inactive"),
    STOPPED(2, "Stopped"),
    CLOSED(3, "Closed");

    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of CustomerStatusEnum */
    SupplierStatusEnum(final int id, final String label) {
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

    public static SupplierStatusEnum fromString(
            final String str) {
        for (SupplierStatusEnum e : SupplierStatusEnum.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }

        }
        return null;
    }

    public static SupplierStatusEnum fromId(
            final int id) {
        for (SupplierStatusEnum e : SupplierStatusEnum.values()) {
            if (e.id == id) {
                return e;
            }

        }
        return null;
    }
}
