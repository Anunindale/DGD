package emc.datatypes.gl.transactionperiodsummary;

import emc.datatypes.EMCString;

/** 
 *
 * @author claudette
 */
public class FinancialPeriod extends EMCString {

    /** Creates a new instance of FinancialPeriod. */
    public FinancialPeriod() {
        this.setEmcLabel("Financial Period");
        this.setEditable(false);
    }
}