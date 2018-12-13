/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.journals;

import emc.datatypes.EMCBigDecimal;

/**
 * @description : Data type for vatAmount on DebtorsJournalLines.
 *
 * @date        : 04 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class VATAmount extends EMCBigDecimal {

    /** Creates a new instance of VATAmount */
    public VATAmount() {
        this.setEmcLabel("VAT");
    }
}
