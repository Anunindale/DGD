/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.debtors.debtorsbalance;

import emc.bus.debtors.logic.aging.DebtorsAgingLocal;
import emc.entity.debtors.DebtorsTransactions;
import emc.enums.debtors.transactions.DebtorsTransactionRefTypes;
import emc.enums.emcquery.EMCQueryFieldTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.messages.ServerDebtorsMessageEnum;
import emc.reporttools.EMCReportConfig;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @description : Report bean for the Debtors Balance report.
 *
 * @date        : 28 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Stateless
public class DebtorsBalanceReportBean extends EMCReportBean implements DebtorsBalanceReportLocal {

    @EJB
    private DebtorsAgingLocal agingBean;

    /** Creates a new instance of DebtorsBalanceReportBean */
    public DebtorsBalanceReportBean() {
    }

    @Override
    public List<Object> getReportResult(List<Object> queryList, EMCReportConfig reportConfig, EMCUserData userData) {
        //Add dates to user data
        userData.setUserData(3, reportConfig.getParameters().get("fromDate"));
        userData.setUserData(4, reportConfig.getParameters().get("toDate"));
        //Add customer id to user data.
        userData.setUserData(5, ((EMCQuery) queryList.get(1)).getFieldValue(DebtorsTransactions.class.getName(), "customerId", EMCQueryFieldTypes.STRING, false));

        if (reportConfig.getParameters().get("fromDate") == null || reportConfig.getParameters().get("toDate") == null) {
            logMessage(Level.SEVERE, ServerDebtorsMessageEnum.NO_DATE_RANGE, userData);
            return new ArrayList();
        }

        return super.getReportResult(queryList, reportConfig, userData);
    }

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        List<Object> ret = new ArrayList<Object>();

        DebtorsBalanceReportDS ds = new DebtorsBalanceReportDS();

        BigDecimal startDateBalance = BigDecimal.ZERO;
        List<String> customers = (List<String>) userData.getUserData(5);
        
        //Use ageing to get opening balance at date.
        if (customers != null && !customers.isEmpty()) {
            for (String customer : customers) {
                startDateBalance = startDateBalance.add(agingBean.getBalanceAtDate((Date) userData.getUserData(3), customer, true, null, userData));
            }
        } else {
            startDateBalance = agingBean.getBalanceAtDate((Date) userData.getUserData(3), null, true, null, userData);
        }
        
        ds.setStartDateBalance(startDateBalance);

        BigDecimal endDateBalance = startDateBalance;

        ret.add(ds);

        for (Object result : queryResult) {
            Object[] totalArr = (Object[]) result;

            String type = (String) totalArr[0];
            BigDecimal debit = (BigDecimal) totalArr[1];
            BigDecimal credit = (BigDecimal) totalArr[2];

            BigDecimal total = BigDecimal.ZERO;

            if (credit.compareTo(BigDecimal.ZERO) > 0) {
                //Credit
                total = total.subtract(credit);
            }

            if (debit.compareTo(BigDecimal.ZERO) > 0) {
                //Debit
                total = total.add(debit);
            }

            DebtorsTransactionRefTypes refType = DebtorsTransactionRefTypes.fromString(type);

            //Check for each type individually.  This allows us to display zero amounts.
            switch (refType) {
                case CREDIT_NOTE:
                    ds.setCreditNoteTotal(total);
                    break;
                case INTEREST:
                    ds.setInterestTotal(total);
                    break;
                case INVOICE:
                    ds.setInvoiceTotal(total);
                    break;
                case DEBIT_JOURNAL:
                    ds.setDebitJournalTotal(total);
                    break;
                case CREDIT_JOURNAL:
                    ds.setCreditJournalTotal(total);
                    break;
                case PAYMENT:
                    ds.setPaymentTotal(total);
                    break;
                case RETURNED_PAYMENT:
                    break;
                case SETTLEMENT_DISC:
                    ds.setSettlementDiscountTotal(total);
                    break;
                case TRANSFER:
                    break;
                case REBATE:
                    ds.setRebateTotal(total);

                default:
                    break;
            }

            endDateBalance = endDateBalance.add(total);
        }

        ds.setEndDateBalance(endDateBalance);

        return ret;
    }
}
