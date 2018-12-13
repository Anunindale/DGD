/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.gl.datasource;

import emc.entity.gl.GLChartOfAccounts;

/**
 *
 * @author riaan
 */
public class GLChartOfAccountsDS extends GLChartOfAccounts {

    private String contraAccountName;

    /** 
     * Creates a new instance of GLChartOfAccountsDS.
     */
    public GLChartOfAccountsDS() {
        this.setDataSource(true);
    }

    public String getContraAccountName() {
        return contraAccountName;
    }

    public void setContraAccountName(String contraAccountName) {
        this.contraAccountName = contraAccountName;
    }
}
