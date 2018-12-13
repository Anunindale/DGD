/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.debtors.customeragingsummary;

import emc.bus.debtors.logic.aging.DebtorsAgingLocal;
import emc.bus.debtors.logic.aging.DebtorsInternalAgingLocal;
import emc.bus.debtors.parameters.DebtorsParametersLocal;
import emc.entity.debtors.DebtorsParameters;
import emc.entity.sop.SOPCustomers;
import emc.enums.debtors.parameters.DebtorsAgingMode;
import emc.enums.debtors.parameters.DebtorsAgingPeriod;
import emc.enums.emcquery.EMCQueryConditions;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.helpers.debtors.DebtorsAgingHelper;
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
 * @description : Report bean for the Debtors customer aging summary report.
 *
 * @date : 01 Jul 2010
 *
 * @author : Riaan Nel
 *
 * @version : 1.0
 */
@Stateless
public class DebtorsCustomerAgingSummaryReportBean extends EMCReportBean implements DebtorsCustomerAgingSummaryReportLocal {

    @EJB
    private DebtorsAgingLocal agingBean;
    @EJB
    private DebtorsParametersLocal parametersBean;
    @EJB
    private DebtorsInternalAgingLocal internalAgeingBean;

    /**
     * Creates a new instance of DebtorsCustomerAgingSummaryReportBean
     */
    public DebtorsCustomerAgingSummaryReportBean() {
    }

    @Override
    public List<Object> getReportResult(List<Object> queryList, EMCReportConfig reportConfig, EMCUserData userData) {
        EMCQuery query = (EMCQuery) queryList.get(1);
        query.addAnd("customerId", EMCQueryConditions.EQUALS, "invoiceToCustomer");

        //Set atDate in userData.
        Date atDate = (Date) reportConfig.getParameters().get("atDate");

        DebtorsAgingMode agingMode = DebtorsAgingMode.fromString((String) reportConfig.getParameters().get("unallocatedCreditAgingMode"));

        if (atDate == null) {
            logMessage(Level.WARNING, ServerDebtorsMessageEnum.NO_AT_DATE, userData);
            atDate = Functions.nowDate();
        }

        if (agingMode == null) {
            logMessage(Level.WARNING, ServerDebtorsMessageEnum.NO_MODE, userData);
        }

        userData.setUserData(3, atDate);
        userData.setUserData(4, agingMode);

        //Indicates whether certain customers should be ignored.
        userData.setUserData(7, reportConfig.getParameters().get("ignore_zero"));

        //Indicates whether this is an internal ageing report.  If so,
        //transactions allocated in the current period should be shown,
        //regardless of which period the transaction falls in.
        userData.setUserData(8, reportConfig.getParameters().get("internalAgeing") instanceof Boolean ? (Boolean) reportConfig.getParameters().get("internalAgeing") : false);

        return super.getReportResult(queryList, reportConfig, userData);
    }

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        boolean ignoreZero = userData.getUserData(7) instanceof Boolean ? (Boolean) userData.getUserData(7) : false;
        boolean internalAgeing = userData.getUserData(8) == Boolean.TRUE;

        List<Object> ret = new ArrayList<Object>();

        //Set in getReportResult() method.
        Date atDate = (Date) userData.getUserData(3);
        DebtorsAgingMode mode = (DebtorsAgingMode) userData.getUserData(4);

        //Use period and mode from Debtors Parameters (if no mode is specified).
        DebtorsParameters parameters = parametersBean.getDebtorsParameters(userData);

        if (parameters == null) {
            throw new NullPointerException("No Debtors parameters found.");
        }

        if (mode == null) {
            mode = DebtorsAgingMode.fromString(parameters.getDebtorsAgingMode());
        }

        DebtorsAgingPeriod period = DebtorsAgingPeriod.fromString(parameters.getDebtorsAgingPeriod());

        for (Object ob : queryResult) {
            SOPCustomers customer = (SOPCustomers) ob;

            DebtorsCustomerAgingSummaryReportDS ds = new DebtorsCustomerAgingSummaryReportDS();

            ds.setCustomerId(customer.getCustomerId());
            ds.setCustomerName(customer.getCustomerName());
            ds.setPaymentTerms(customer.getTermsOfPayment());
            ds.setCreditLimit(new BigDecimal(customer.getCreaditLimit()));
            ds.setAtDate(Functions.date2String(atDate));
            ds.setAgingMode(String.valueOf(mode));

            List<DebtorsAgingHelper> agingHelpers = null;

            if (internalAgeing) {
                agingHelpers = internalAgeingBean.getDebtorsInternalAging(customer.getCustomerId(), atDate, mode, period, userData);
            } else {
                agingHelpers = agingBean.getDebtorsAging(customer.getCustomerId(), atDate, mode, period, parameters, userData);
            }

            if (!agingHelpers.isEmpty()) {
                ds.setCurrentBinAmount(agingHelpers.get(0).getBinAmount());
            }

            if (agingHelpers.size() > 1) {
                ds.setBin1Amount(agingHelpers.get(1).getBinAmount());
            }

            if (agingHelpers.size() > 2) {
                ds.setBin2Amount(agingHelpers.get(2).getBinAmount());
            }

            if (agingHelpers.size() > 3) {
                ds.setBin3Amount(agingHelpers.get(3).getBinAmount());
            }

            if (agingHelpers.size() > 4) {
                ds.setBin4Amount(agingHelpers.get(4).getBinAmount());
            }

            if (agingHelpers.size() > 5) {
                ds.setBin5Amount(agingHelpers.get(5).getBinAmount());
            }

            if (agingHelpers.size() > 6) {
                ds.setBin6Amount(agingHelpers.get(6).getBinAmount());
            }

            ds.setTotalOutstanding(ds.getCurrentBinAmount().add(ds.getBin1Amount()).add(ds.getBin2Amount()).add(ds.getBin3Amount()).add(ds.getBin4Amount()).add(ds.getBin5Amount()).add(ds.getBin6Amount()));

            if (mode == DebtorsAgingMode.NONE) {
                if (internalAgeing) {
                    ds.setUnallocatedCredit(internalAgeingBean.getTotalUnallocatedCredit(customer.getCustomerId(), atDate, userData).multiply(new BigDecimal(-1)));
                } else {//For modes other than NONE, credit will already have been allocated in aging.
                    ds.setUnallocatedCredit(agingBean.getTotalUnallocatedCredit(customer.getCustomerId(), atDate, userData).multiply(new BigDecimal(-1)));
                }
                ds.setShowUnallocatedCredits(true);
                ds.setTotalOutstanding(ds.getTotalOutstanding().add(ds.getUnallocatedCredit()));
            }

            //If all bins are zero, ignore it.
            if (ignoreZero && (util.compareDouble(ds.getCurrentBinAmount().doubleValue(), 0) == 0
                               && util.compareDouble(ds.getBin1Amount().doubleValue(), 0) == 0
                               && util.compareDouble(ds.getBin2Amount().doubleValue(), 0) == 0
                               && util.compareDouble(ds.getBin3Amount().doubleValue(), 0) == 0
                               && util.compareDouble(ds.getBin4Amount().doubleValue(), 0) == 0
                               && util.compareDouble(ds.getBin5Amount().doubleValue(), 0) == 0
                               && util.compareDouble(ds.getBin6Amount().doubleValue(), 0) == 0)
                && util.compareDouble(ds.getUnallocatedCredit().doubleValue(), 0) == 0) {
                continue;
            }

            ret.add(ds);
        }

        return ret;
    }
}
