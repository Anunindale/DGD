/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.gl.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.gl.journallines.datasource.AccountDescription;
import emc.datatypes.gl.journallines.datasource.ContraAccountDescription;
import emc.entity.gl.journals.GLJournalLines;
import java.util.HashMap;

/**
 *
 * @author riaan
 */
public class GLJournalLinesDS extends GLJournalLines {

    private String accountDescription;
    private String contraAccountDescription;
    
    /**
     * Creates a new instance of GLJournalLinesDS.
     */
    public GLJournalLinesDS() {
        this.setDataSource(true);
    }

    public String getAccountDescription() {
        return accountDescription;
    }

    public void setAccountDescription(String accountDescription) {
        this.accountDescription = accountDescription;
    }

    public String getContraAccountDescription() {
        return contraAccountDescription;
    }

    public void setContraAccountDescription(String contraAccountDescription) {
        this.contraAccountDescription = contraAccountDescription;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> ret = super.buildFieldList();

        ret.put("accountDescription", new AccountDescription());
        ret.put("contraAccountDescription", new ContraAccountDescription());

        return ret;
    }
}
