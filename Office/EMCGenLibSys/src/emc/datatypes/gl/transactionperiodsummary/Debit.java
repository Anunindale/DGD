package emc.datatypes.gl.transactionperiodsummary;

import emc.datatypes.EMCBigDecimal;

/** 
*
* @author claudette
*/
public class Debit extends EMCBigDecimal {

    /** Creates a new instance of Debit. */
    public Debit() {
        this.setEmcLabel("Debit");
        this.setEditable(false);
    }
}