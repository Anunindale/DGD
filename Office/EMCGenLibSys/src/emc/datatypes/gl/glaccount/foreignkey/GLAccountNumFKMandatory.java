/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.gl.glaccount.foreignkey;

import emc.datatypes.gl.glaccount.GLAccountNum;

/**
 *
 * @author rico
 */
public class GLAccountNumFKMandatory extends GLAccountNum {
    public GLAccountNumFKMandatory(){
        this.setRelatedField("accountNumber");
        this.setRelatedTable(emc.entity.gl.GLChartOfAccounts.class.getName());
    }
}
