package emc.datatypes.gl.budgetmodel;

import emc.datatypes.gl.financialperiod.foreignkeys.PeriodIdFK;

/** 
 *
 * @author claudette
 */
public class FromPeriod extends PeriodIdFK {

    /** Creates a new instance of FromPeriod. */
    public FromPeriod() {
        this.setEmcLabel("From Period");
    }
}