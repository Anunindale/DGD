/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base.servertransactions.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.servertransactionslog.ServerTransactionsLogTimeTakenDT;
import emc.entity.base.servertransactions.BaseServerTransactionsLog;
import java.util.HashMap;

/**
 *
 * @author Owner
 */
public class BaseServerTransactionsLogDS extends BaseServerTransactionsLog {

    public BaseServerTransactionsLogDS() {
        this.setDataSource(true);
    }
    private long timeTakenMiliseconds;

    public long getTimeTakenMiliseconds() {
        return timeTakenMiliseconds;
    }

    public void setTimeTakenMiliseconds(long timeTakenMiliseconds) {
        this.timeTakenMiliseconds = timeTakenMiliseconds;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("timeTakenMiliseconds", new ServerTransactionsLogTimeTakenDT());
        return toBuild;
    }
}
