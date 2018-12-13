/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.debtors.allocationimport;

/**
 *
 * @author riaan
 */
public enum DebtorsAllocationImportStatus {

    CAPTURED(0, "Captured"),
    VALIDATED(1, "Validated"),
    ALLOCATED(2, "Allocated");
    
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  DebtorsAllocationImportSetupConditions*/
    DebtorsAllocationImportStatus(final int id, final String label) {
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

    public static DebtorsAllocationImportStatus fromString(final String str) {
        for (DebtorsAllocationImportStatus e : DebtorsAllocationImportStatus.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static DebtorsAllocationImportStatus fromId(final int id) {
        for (DebtorsAllocationImportStatus e : DebtorsAllocationImportStatus.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
