/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.debtors.parameters;

/**
 * @description : This enum represents Debtors aging modes.
 *
 * @date        : 03 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public enum DebtorsAgingMode {

    OLDEST(0, "Oldest"),
    NONE(1, "None"),
    DATE(2, "Date");

    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  DebtorsAgingMode*/
    DebtorsAgingMode(final int id, final String label) {
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

    public static DebtorsAgingMode fromString(final String str) {
        for (DebtorsAgingMode e : DebtorsAgingMode.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static DebtorsAgingMode fromId(final int id) {
        for (DebtorsAgingMode e : DebtorsAgingMode.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
