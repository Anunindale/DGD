/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.debtors.allocationimport;

/**
 *
 * @author riaan
 */
public enum DebtorsAllocationImportSetupConditions {

    GREATER_THAN_EQ(0, ">="),
    LESS_THAN_EQ(1, "<="),
    EQUAL(2, "="),
    CONTAINS(3, "contains"),
    NONE(4, "");

    
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  DebtorsAllocationImportSetupConditions*/
    DebtorsAllocationImportSetupConditions(final int id, final String label) {
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

    public static DebtorsAllocationImportSetupConditions fromString(final String str) {
        for (DebtorsAllocationImportSetupConditions e : DebtorsAllocationImportSetupConditions.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static DebtorsAllocationImportSetupConditions fromId(final int id) {
        for (DebtorsAllocationImportSetupConditions e : DebtorsAllocationImportSetupConditions.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
