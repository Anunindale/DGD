/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.gl.financialperiods;

/**
 *
 * @author riaan
 */
public enum GLFinancialPeriodGenerationTypes {

    MONTHLY(0, "Monthly");

    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  GLFinancialPeriodGenerationTypes. */
    GLFinancialPeriodGenerationTypes(final int id, final String label) {
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

    public static GLFinancialPeriodGenerationTypes fromString(final String str) {
        for (GLFinancialPeriodGenerationTypes e : GLFinancialPeriodGenerationTypes.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static GLFinancialPeriodGenerationTypes fromId(final int id) {
        for (GLFinancialPeriodGenerationTypes e : GLFinancialPeriodGenerationTypes.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
