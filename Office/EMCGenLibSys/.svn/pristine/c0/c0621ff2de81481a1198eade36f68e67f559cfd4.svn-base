/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.journals.superclass;

import emc.datatypes.gl.chartofaccounts.AccountNumber;
import emc.entity.gl.GLChartOfAccounts;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 * @description : Data type for ContraAccount on DebtorsJournalMaster.
 *
 * @date        : 03 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class ContraAccount extends AccountNumber {

    /** Creates a new instance of ContraAccount */
    public ContraAccount() {
        this.setRelatedField("accountNumber");
        this.setRelatedTable(GLChartOfAccounts.class.getName());
        this.setUpdateAction(enumDeleteUpdateOptions.RESTRICT);
        this.setDeleteAction(enumDeleteUpdateOptions.RESTRICT);
        this.setNumberSeqAllowed(false);
    }
}
