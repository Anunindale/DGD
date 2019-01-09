/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.gl.display.chartofaccounts.resources;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.entity.gl.transactions.GLTransactionDaySummary;
import emc.entity.gl.transactions.GLTransactionPeriodSummary;
import emc.entity.gl.transactions.GLTransactionsDetail;
import emc.entity.gl.transactions.GLTransactionsSummary;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;

/**
 *
 * @author riaan
 */
public class GLChartOfAccountsDRM extends emcDataRelationManagerUpdate {

    /**
     * Creates a new instance of GLChartOfAccountsDRM.
     * @param tableDataSource Data source.
     * @param userData User data
     */
    public GLChartOfAccountsDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData) {
        super(tableDataSource, userData);
    }

    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
        formUserData = super.generateRelatedFormUserData(formUserData, Index);
        String account = (String)getLastFieldValueAt("accountNumber");
        switch (Index) {
            case 0:
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLTransactionsDetail.class);
                query.addAnd("accountNumber", account);
                formUserData.setUserData(0, query);
                break;
            case 1:
                query = new EMCQuery(enumQueryTypes.SELECT, GLTransactionsSummary.class);
                query.addAnd("accountNumber", account);
                formUserData.setUserData(0, query);
                break;
            case 2:
                query = new EMCQuery(enumQueryTypes.SELECT, GLTransactionDaySummary.class);
                query.addAnd("accountNumber", account);
                formUserData.setUserData(0, query);
                break;
            case 3:
                query = new EMCQuery(enumQueryTypes.SELECT, GLTransactionPeriodSummary.class);
                query.addAnd("accountNumber", account);
                formUserData.setUserData(0, query);
                break;
        }
        
        return formUserData;
    }
}
