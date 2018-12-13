/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.gl.display.transactionssummary.resources;

import emc.app.components.emctable.emcDRMViewOnly;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.entity.gl.transactions.GLTransactionsDetail;
import emc.enums.enumQueryTypes;
import emc.enums.gl.GroupGranularityTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;

/**
 *
 * @author riaan
 */
public class GLTransactionsSummaryDRM extends emcDRMViewOnly {

    /** Creates a new instance of GLTransactionsSummaryDRM. */
    public GLTransactionsSummaryDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData) {
        super(tableDataSource, userData);
    }

    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
        formUserData = super.generateRelatedFormUserData(formUserData, Index);
        switch (Index) {
            case 0:
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLTransactionsDetail.class);
                query.addAnd("accountNumber", getLastFieldValueAt("accountNumber"));

                GroupGranularityTypes granularity = GroupGranularityTypes.fromString((String) getLastFieldValueAt("groupGranularity"));
                switch (granularity) {
                    case NONE:
                    //Fall through
                    case DAY:
                        query.addAnd("transactionDate", getLastFieldValueAt("groupDate"));
                        break;
                    case WEEK:
                        query.addAnd("groupWeek", getLastFieldValueAt("groupWeek"));
                        break;
                    case PERIOD:
                        query.addAnd("groupPeriod", getLastFieldValueAt("groupPeriod"));
                        break;
                }

                formUserData.setUserData(0, query);

                break;
        }
        return formUserData;
    }
}
