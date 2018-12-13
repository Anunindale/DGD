/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.gl.chartofaccounts.foreignkeys;

import emc.datatypes.gl.chartofaccounts.AccountNumber;
import emc.entity.gl.GLChartOfAccounts;

/**
 *
 * @author wikus
 */
public class AccountNumberFK extends AccountNumber {

    public AccountNumberFK() {
        this.setRelatedTable(GLChartOfAccounts.class.getName());
        this.setRelatedField("accountNumber");
        this.setNumberSeqAllowed(false);

    }
}
