/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.debtors.ageanalysisbyagent;

import emc.bus.debtors.logic.aging.DebtorsAgingLocal;
import emc.bus.debtors.parameters.DebtorsParametersLocal;
import emc.bus.sop.repgroups.SOPSalesRepGroupsLocal;
import emc.entity.base.BaseEmployeeTable;
import emc.entity.debtors.DebtorsParameters;
import emc.entity.sop.SOPCustomers;
import emc.entity.sop.SOPSalesOrderLines;
import emc.entity.sop.SOPSalesOrderMaster;
import emc.enums.debtors.customerstatement.IgnoreBalanceEnum;
import emc.enums.debtors.parameters.DebtorsAgingMode;
import emc.enums.debtors.parameters.DebtorsAgingPeriod;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.sop.salesorders.SalesOrderStatus;
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
 *
 * @author riaan
 */
@Stateless
public class DebtorsAgeAnalysisByAgentReportBean extends EMCReportBean implements DebtorsAgeAnalysisByAgentReportLocal {

    @EJB
    private SOPSalesRepGroupsLocal salesRepGroupsBean;
    @EJB
    private DebtorsParametersLocal parametersBean;
    @EJB
    private DebtorsAgingLocal ageingBean;

    /** Creates a new instance of DebtorsAgeAnalysisByAgentReportBean. */
    public DebtorsAgeAnalysisByAgentReportBean() {
    }

    @Override
    public List<Object> getReportResult(List<Object> queryList, EMCReportConfig reportConfig, EMCUserData userData) {
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
        userData.setUserData(6, IgnoreBalanceEnum.fromString((String) reportConfig.getParameters().get("ignore")));

        //Indicates the status in which orders should be selected.  May be comma separated or a range (~).
        userData.setUserData(7, reportConfig.getParameters().get("order_status"));

        //Order from date
        userData.setUserData(8, reportConfig.getParameters().get("order_from"));

        //Order to date
        userData.setUserData(9, reportConfig.getParameters().get("order_to"));

        userData.setUserData(10, reportConfig.getParameters().get("include_customers_with_no_sales_orders"));

        userData.setUserData(11, reportConfig.getParameters().get("printSODetail"));

        return super.getReportResult(queryList, reportConfig, userData);
    }

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        List<Object> ret = new ArrayList<Object>();

        Date atDate = (Date) userData.getUserData(3);
        DebtorsAgingMode mode = (DebtorsAgingMode) userData.getUserData(4);

        //Indicates whether customers with zero or credit balances should be ignored.
        IgnoreBalanceEnum ignoreBalance = (IgnoreBalanceEnum) userData.getUserData(6);

        //Indicates whether customers with no Open Sales Orders should be included on the report
        Boolean includeCustNoSalesOrder = (Boolean) userData.getUserData(10);

        String status = (String) userData.getUserData(7);

        Date from = (Date) userData.getUserData(8);
        Date to = (Date) userData.getUserData(9);

        if (from == null && to == null) {
            logMessage(Level.WARNING, ServerDebtorsMessageEnum.AGE_NO_DATE_RANGE, userData);
        }

        //Use period and mode from Debtors Parameters (if no mode is specified).
        DebtorsParameters parameters = parametersBean.getDebtorsParameters(userData);

        if (parameters == null) {
            throw new NullPointerException("No Debtors parameters found.");
        }

        if (mode == null) {
            mode = DebtorsAgingMode.fromString(parameters.getDebtorsAgingMode());
        }

        DebtorsAgingPeriod period = DebtorsAgingPeriod.fromString(parameters.getDebtorsAgingPeriod());

        boolean printSO = (Boolean) userData.getUserData(11);

        for (Object ob : queryResult) {

            BaseEmployeeTable rep = (BaseEmployeeTable) ob;

            StringBuilder fullName = new StringBuilder(rep.getForenames());
            fullName.append(" ");
            fullName.append(rep.getSurname());

            List<SOPCustomers> customers = salesRepGroupsBean.getCustomersForRep(rep.getEmployeeNumber(), true, null, userData);

            for (SOPCustomers customer : customers) {
                if (ignoreBalance != null) {
                    BigDecimal balance = ageingBean.getBalanceAtDate(atDate, customer.getInvoiceToCustomer(), false, parameters, userData);

                    if (((ignoreBalance == IgnoreBalanceEnum.CREDIT_BALANCE || ignoreBalance == IgnoreBalanceEnum.BOTH) && balance.compareTo(BigDecimal.ZERO) < 0) || ((ignoreBalance == IgnoreBalanceEnum.ZERO_BALANCE || ignoreBalance == IgnoreBalanceEnum.BOTH) && balance.compareTo(BigDecimal.ZERO) == 0)) {
                        //Ignore this customer
                        continue;
                    }
                }

                List<DebtorsAgingHelper> ageingHelpers = ageingBean.getDebtorsAging(customer.getInvoiceToCustomer(), atDate, mode, period, parameters, userData);

                DebtorsAgeAnalysisByAgentReportDS tempDS = new DebtorsAgeAnalysisByAgentReportDS();
                tempDS.setPrintSO(printSO);
                tempDS.setRep(rep.getEmployeeNumber());

                tempDS.setRepName(fullName.toString());

                tempDS.setCustomer(customer.getCustomerId());
                tempDS.setCustomerName(customer.getCustomerName());
                tempDS.setTerms(customer.getTermsOfPayment());
                tempDS.setCreditLimit(new BigDecimal(customer.getCreaditLimit()));
                tempDS.setCustomerStatus(customer.getCustomerStatus());

                if (!ageingHelpers.isEmpty()) {
                    tempDS.setCurrentBinName("Current");
                    tempDS.setCurrentBinValue(ageingHelpers.get(0).getBinAmount());
                }

                if (ageingHelpers.size() > 1) {
                    tempDS.setBin1Value(ageingHelpers.get(1).getBinAmount());
                    tempDS.setBin1Name(parameters.getAgingBin1Name());
                }

                if (ageingHelpers.size() > 2) {
                    tempDS.setBin2Value(ageingHelpers.get(2).getBinAmount());
                    tempDS.setBin2Name(parameters.getAgingBin2Name());
                }

                if (ageingHelpers.size() > 3) {
                    tempDS.setBin3Value(ageingHelpers.get(3).getBinAmount());
                    tempDS.setBin3Name(parameters.getAgingBin3Name());
                }

                //For Ninian, only go up to 90 days +
                if (ageingHelpers.size() > 4) {
                    tempDS.setBin4Value(ageingHelpers.get(4).getBinAmount());
                    tempDS.setBin4Name(parameters.getAgingBin3Name() + "+");

                    if (ageingHelpers.size() > 5) {
                        tempDS.setBin4Value(tempDS.getBin4Value().add(ageingHelpers.get(5).getBinAmount()));
                    }

                    if (ageingHelpers.size() > 6) {
                        tempDS.setBin4Value(tempDS.getBin4Value().add(ageingHelpers.get(6).getBinAmount()));
                    }
                }

//                        if (ageingHelpers.size() > 5) {
//                           tempDS.setBin5Value(ageingHelpers.get(5).getBinAmount());
//                           tempDS.setBin5Name(parameters.getAgingBin5Name());
//                        }
//
//                        if (ageingHelpers.size() > 6) {
//                            tempDS.setBin6Value(ageingHelpers.get(6).getBinAmount());
//                            tempDS.setBin6Name(parameters.getAgingBin6Name());
//                        }

                //Only get unallocated credit if mode is NONE
                if (mode == DebtorsAgingMode.NONE) {
                    tempDS.setBin7Name("Unallocated");
                    tempDS.setBin7Value(ageingBean.getTotalUnallocatedCredit(customer.getCustomerId(), atDate, userData));
                }

                tempDS.setTotalOutstanding(tempDS.getCurrentBinValue().add(tempDS.getBin1Value()).add(tempDS.getBin2Value()).add(tempDS.getBin3Value()).add(tempDS.getBin4Value()).add(tempDS.getBin5Value().add(tempDS.getBin6Value()).add(tempDS.getBin7Value())));

                tempDS.setAgeingMode(mode.toString());
                tempDS.setAtDate(Functions.date2String(atDate));

                if (from != null) {
                    tempDS.setOrdersFrom(Functions.date2String(from));
                }

                if (to != null) {
                    tempDS.setOrdersTo(Functions.date2String(to));
                }

                //Select orders for all customers associated with the specified rep.
                List<SalesOrderInfo> salesOrderInfoList = null;

                if (printSO) {
                    salesOrderInfoList = getSalesOrderInfo(salesRepGroupsBean.getCustomersForRep(rep.getEmployeeNumber(), false, customer.getCustomerId(), userData), status, rep.getEmployeeNumber(), from, to, userData);

                    DebtorsAgeAnalysisByAgentReportDS ds;
                    if (salesOrderInfoList != null && !salesOrderInfoList.isEmpty()) {

                        for (SalesOrderInfo salesOrderInfo : salesOrderInfoList) {
                            ds = (DebtorsAgeAnalysisByAgentReportDS) util.doClone(tempDS, DebtorsAgeAnalysisByAgentReportDS.class, userData);

                            ds.setOrderNo(salesOrderInfo.orderNo);
                            ds.setCustomerOrderNo(salesOrderInfo.customerOrderNo);
                            ds.setStatus(salesOrderInfo.status);
                            ds.setShipToCustomer(salesOrderInfo.shipToCustomer);
                            ds.setReference(salesOrderInfo.reference);

                            if (salesOrderInfo.requiredDate != null) {
                                ds.setRequiredDate(Functions.date2String(salesOrderInfo.requiredDate));
                            }

                            ds.setValue(salesOrderInfo.value);

                            ret.add(ds);
                        }
                    } else if (includeCustNoSalesOrder == Boolean.TRUE) { //Explicitly compare value with Boolean.TRUE.  It might be null, in which case autoboxing would throw an exception.
                        ds = (DebtorsAgeAnalysisByAgentReportDS) util.doClone(tempDS, DebtorsAgeAnalysisByAgentReportDS.class, userData);
                        ret.add(ds);
                    }
                } else {
                    ret.add(tempDS);
                }
            }
        }


        return ret;
    }

    /** Returns a list of helper classes containing Sales Order information for the
     *  specified customers.
     *
     * @param customers List of customers to select on.
     * @param status Order status to select on.
     * @param rep Sales Rep to select on.
     * @param from Order date range start.
     * @param to Order date range end.
     * @param userData User data.
     * @return A list of helper class instances.
     */
    private List<SalesOrderInfo> getSalesOrderInfo(List<SOPCustomers> customers, String status, String rep, Date from, Date to, EMCUserData userData) {
        String linesCompanyId = util.getCompanyId(SOPSalesOrderLines.class.getName(), userData);
        String masterCompanyId = util.getCompanyId(SOPSalesOrderMaster.class.getName(), userData);

        EMCQuery statusQuery = new EMCQuery(enumQueryTypes.SELECT, SOPSalesOrderMaster.class);
        statusQuery.addField("salsesOrderStatus");
        statusQuery.addAndCommaSeperated("salsesOrderStatus", status, SOPSalesOrderMaster.class.getName(), EMCQueryConditions.EQUALS);
        statusQuery.addGroupBy("salsesOrderStatus");
        List<String> statusRes = (List<String>) util.executeGeneralSelectQuery(statusQuery, userData);

        //EMCQuery is not flexible enough to allow for an efficient representation of this query.
        StringBuilder query = new StringBuilder("SELECT m.salesOrderNo, m.customerOrderNo, m.requiredDate, m.salsesOrderStatus, SUM((l.quantity * l.price) - ((l.quantity * l.price) * l.discountPerc/100)), m.customerNo, m.reference FROM SOPSalesOrderMaster m, SOPSalesOrderLines l WHERE m.salesOrderNo = l.salesOrderNo");
        if (statusRes != null && !statusRes.isEmpty()) {
            query.append(" AND m.salsesOrderStatus in (");
            for (int i = 0; i < statusRes.size(); i++) {
                query.append("'");
                query.append(statusRes.get(i));
                query.append("'");
                if (i != statusRes.size() - 1) {
                    query.append(",");
                }
            }
            query.append(") ");
        } else {
            //Include all uninvoiced Sales Orders.
            query.append(" AND NOT m.salsesOrderStatus = '");
            query.append(SalesOrderStatus.INVOICED.toString());
            query.append("'");
        }
        query.append(" AND m.salesOrderType = 'Sales Order' AND m.customerNo IN (");
        boolean firstRecordProcessed = false;
        for (SOPCustomers customer : customers) {
            if (firstRecordProcessed) {
                query.append(", ");
            } else {
                firstRecordProcessed = true;
            }

            query.append("'");
            query.append(customer.getCustomerId());
            query.append("'");
        }
        query.append(")");

        if (from != null) {
            query.append(" AND m.requiredDate >= '");
            query.append(Functions.date2SQLString(from));
            query.append("'");
        }

        if (to != null) {
            query.append(" AND m.requiredDate <= '");
            query.append(Functions.date2SQLString(to));
            query.append("'");
        }

        query.append(" AND salesRep = '");
        query.append(rep);
        query.append("' AND l.companyId = '");
        query.append(linesCompanyId);
        query.append("' AND m.companyId = '");
        query.append(masterCompanyId);
        //Order by Ship to Customer
        query.append("' GROUP BY m.salesOrderNo ORDER BY m.customerNo, m.requiredDate");

        List<Object[]> results = (List<Object[]>) util.executeGeneralSelectQuery(query.toString(), userData);

        List<SalesOrderInfo> salesOrderInfo = new ArrayList<SalesOrderInfo>();

        for (Object[] result : results) {
            salesOrderInfo.add(new SalesOrderInfo(result));
        }

        return salesOrderInfo;
    }

    /** Helper class used to store Sales Order information. */
    private class SalesOrderInfo {

        public String orderNo;
        public String customerOrderNo;
        public Date requiredDate;
        public String status;
        public BigDecimal value = BigDecimal.ZERO;   //Excludes VAT, includes discount.
        public String shipToCustomer;
        public String reference;

        /** Creates a new instance of SalesOrderInfo. */
        public SalesOrderInfo(Object[] info) {
            this.orderNo = (String) info[0];
            this.customerOrderNo = (String) info[1];
            this.requiredDate = (Date) info[2];
            this.status = (String) info[3];
            this.value = (BigDecimal) info[4];
            this.shipToCustomer = (String) info[5];
            this.reference = (String) info[6];
        }
    }
}
