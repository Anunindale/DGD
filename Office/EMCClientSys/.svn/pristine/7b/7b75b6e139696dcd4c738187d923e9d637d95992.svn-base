/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.gl.display.transactionperiodsummary.resources;

import emc.app.components.emctable.emcDRMViewOnly;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.entity.gl.transactions.GLTransactionsDetail;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;

/**
 *
 * @author riaan
 */
public class GLTransactionPeriodSummaryDRM extends emcDRMViewOnly {

    /**
     * Creates a new instance of GLTransactionPeriodSummaryDRM.
     * @param tableDataSource Data source.
     * @param userData User data.
     */
    public GLTransactionPeriodSummaryDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData) {
        super(tableDataSource, userData);
    }

    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
        formUserData = super.generateRelatedFormUserData(formUserData, Index);

        switch (Index) {
            case 0:
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLTransactionsDetail.class);
                query.addAnd("accountNumber", this.getLastFieldValueAt("accountNumber"));
                if (!Functions.checkBlank(getLastFieldValueAt("analysisCode1"))) {
                    query.addAnd("analysisCode1", getLastFieldValueAt("analysisCode1"));
                }
                if (!Functions.checkBlank(getLastFieldValueAt("analysisCode2"))) {
                    query.addAnd("analysisCode2", getLastFieldValueAt("analysisCode2"));
                }
                if (!Functions.checkBlank(getLastFieldValueAt("analysisCode3"))) {
                    query.addAnd("analysisCode3", getLastFieldValueAt("analysisCode3"));
                }
                if (!Functions.checkBlank(getLastFieldValueAt("analysisCode4"))) {
                    query.addAnd("analysisCode4", getLastFieldValueAt("analysisCode4"));
                }
                if (!Functions.checkBlank(getLastFieldValueAt("analysisCode5"))) {
                    query.addAnd("analysisCode5", getLastFieldValueAt("analysisCode5"));
                }
                if (!Functions.checkBlank(getLastFieldValueAt("analysisCode6"))) {
                    query.addAnd("analysisCode6", getLastFieldValueAt("analysisCode6"));
                }
                formUserData.setUserData(0, query);
                break;
        }
        
        return formUserData;
    }

}
