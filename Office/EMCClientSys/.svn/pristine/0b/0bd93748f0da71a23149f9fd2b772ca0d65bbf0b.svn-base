/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.debtors.display.transactions.resources;

import emc.app.components.emctable.emcDRMViewOnly;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.entity.debtors.journals.DebtorsJournalMaster;
import emc.entity.debtors.transactionsettlement.DebtorsTransactionSettlementHistory;
import emc.enums.debtors.transactions.DebtorsTransactionRefTypes;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.math.BigDecimal;

/**
 *
 * @author riaan
 */
public class DebtorsTransactionsDRM extends emcDRMViewOnly {

    //Passed through to the settlement history form to determine whether buttons should be displayed.
    private boolean allowAllocation;

    /** Creates a new instance of DebtorsTransactionsDRM. */
    public DebtorsTransactionsDRM(emcGenericDataSourceUpdate tableDataSource, boolean allowAllocation, EMCUserData userData) {
        super(tableDataSource, userData);
        this.allowAllocation = allowAllocation;
    }

    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
        //Special for go to main table on transactions.  This used another field on the table
        if (Index >= 1000) {
            emcJTableUpdate table = this.getMainTableComponent();
            if (table != null) {
                emcTableModelUpdate model = table.getEmcTableModel();
                String column = model.getFieldName(table.getSelectedColumn());
                DebtorsTransactionRefTypes refType = DebtorsTransactionRefTypes.fromString((String)this.getLastFieldValueAt("referenceType"));
                if ("referenceNumber".equals(column) && (refType == DebtorsTransactionRefTypes.CREDIT_JOURNAL || refType == DebtorsTransactionRefTypes.CREDIT_JOURNAL || refType == DebtorsTransactionRefTypes.PAYMENT)) {
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsJournalMaster.class);
                    query.addAnd("journalNumber", this.getLastFieldValueAt("referenceNumber"));
                    query.addAnd("companyId", formUserData.getCompanyId());

                    formUserData.setUserData(0, query);

                    return formUserData;
                }
            }
        }

        formUserData = super.generateRelatedFormUserData(formUserData, Index);

        switch (Index) {
            case 1:
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactionSettlementHistory.class);

                if (((BigDecimal)this.getLastFieldValueAt("credit")).compareTo(BigDecimal.ZERO) > 0) {
                    query.addAnd("creditTransRef", getLastFieldValueAt("recordID"));
                    //Show debit transactions on history form
                    formUserData.setUserData(2, true);
                } else {
                    query.addAnd("debitTransRef", getLastFieldValueAt("recordID"));
                    //Show credit transactions on history form.
                    formUserData.setUserData(2, false);
                }

                formUserData.setUserData(0, query);
                formUserData.setUserData(3, this.allowAllocation);

                break;
        }

        return formUserData;
    }
}
