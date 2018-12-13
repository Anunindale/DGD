package emc.datatypes.gl.transactiondaysummary;

import emc.datatypes.EMCBigDecimal;

/** 
*
* @author claudette
*/
public class Credit extends EMCBigDecimal {

    /** Creates a new instance of Credit. */
    public Credit() {
        this.setEmcLabel("Credit");
        this.setEditable(false);
    }
}