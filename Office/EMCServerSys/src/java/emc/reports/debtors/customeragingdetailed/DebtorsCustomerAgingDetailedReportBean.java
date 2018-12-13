/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.debtors.customeragingdetailed;

import emc.bus.base.BaseCompanyLocal;
import emc.bus.debtors.logic.aging.DebtorsAgingLocal;
import emc.bus.debtors.logic.aging.DebtorsInternalAgingLocal;
import emc.bus.debtors.parameters.DebtorsParametersLocal;
import emc.entity.base.BaseCompanyTable;
import emc.entity.debtors.DebtorsParameters;
import emc.entity.debtors.DebtorsTransactions;
import emc.entity.debtors.transactionsettlement.DebtorsTransactionSettlementHistory;
import emc.entity.sop.SOPCustomers;
import emc.enums.debtors.customerstatement.IgnoreBalanceEnum;
import emc.enums.debtors.parameters.DebtorsAgingMode;
import emc.enums.debtors.parameters.DebtorsAgingPeriod;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.emcquery.EMCQueryOrderByDirections;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.functions.StopWatchFactory;
import emc.helpers.debtors.DebtorsAgingHelper;
import emc.helpers.debtors.DebtorsDetailedAgingHelper;
import emc.helpers.debtors.DebtorsDetailedAgingLineDS;
import emc.messages.ServerDebtorsMessageEnum;
import emc.reports.debtors.customerstatement.DebtorsCustomerStatementReportDS;
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
 * @author wikus
 */
@Stateless
public class DebtorsCustomerAgingDetailedReportBean extends EMCReportBean implements DebtorsCustomerAgingDetailedReportLocal {
    
    @EJB
    private DebtorsParametersLocal parametersBean;
    @EJB
    private DebtorsAgingLocal agingBean;
    @EJB
    private DebtorsInternalAgingLocal internalAgingBean;
    @EJB
    private BaseCompanyLocal companyBean;

    /**
     * Creates a new instance of DebtorsCustomerAgingDetailedReportBean.
     */
    public DebtorsCustomerAgingDetailedReportBean() {
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
        userData.setUserData(6, IgnoreBalanceEnum.fromString((String) reportConfig.getParameters().get("ignore")));

        //Indicates whether this is an internal ageing report.  If so,
        //transactions allocated in the current period should be shown,
        //regardless of which period the transaction falls in.
        userData.setUserData(7, reportConfig.getParameters().get("internalAgeing") instanceof Boolean ? (Boolean) reportConfig.getParameters().get("internalAgeing") : false);
        
        return super.getReportResult(queryList, reportConfig, userData);
    }
    
    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        List<Object> ret = new ArrayList<Object>();

        //Set in getReportResult() method.
        Date atDate = (Date) userData.getUserData(3);
        DebtorsAgingMode mode = (DebtorsAgingMode) userData.getUserData(4);
        
        BaseCompanyTable company = companyBean.getUserCompany(userData);

        //Indicates whether the returned DS instances should include customer information.
        //The DS class is determined based on this value.  This report bean is used
        //for both the Customer Statement and Detailed Aging reports in order to prevent
        //duplication of logic.
        Boolean populateCustomerStatement = userData.getUserData(5) instanceof Boolean ? (Boolean) userData.getUserData(5) : false;

        //Indicates whether customers with zero or credit balances should be ignored.
        IgnoreBalanceEnum ignoreBalance = (IgnoreBalanceEnum) userData.getUserData(6);

        //Indicates whether this is an internal ageing report.  If so,
        //transactions allocated in the current period should be shown,
        //regardless of which period the transaction falls in.
        boolean internalAgeingReport = userData.getUserData(7) instanceof Boolean ? (Boolean) userData.getUserData(7) : false;
        
        if (populateCustomerStatement == null) {
            populateCustomerStatement = false;
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
        
        for (Object ob : queryResult) {
            SOPCustomers customer = (SOPCustomers) ob;
            
            if (ignoreBalance != null) {
                BigDecimal balance = null;
                
                if (internalAgeingReport) {
                    balance = internalAgingBean.getBalanceAtDate(atDate, customer.getCustomerId(), false, userData);
                } else {
                    balance = agingBean.getBalanceAtDate(atDate, customer.getCustomerId(), false, parameters, userData);
                }
                
                if (((ignoreBalance == IgnoreBalanceEnum.CREDIT_BALANCE || ignoreBalance == IgnoreBalanceEnum.BOTH) && util.compareDouble(balance.doubleValue(), 0) < 0)
                    || ((ignoreBalance == IgnoreBalanceEnum.ZERO_BALANCE || ignoreBalance == IgnoreBalanceEnum.BOTH) && util.compareDouble(balance.doubleValue(), 0) == 0)) {
                    //Ignore this customer
                    continue;
                }
            }

            //Get bin totals for customer
            List<DebtorsAgingHelper> agingBins = null;
            
            if (internalAgeingReport) {
                agingBins = internalAgingBean.getDebtorsInternalAging(customer.getCustomerId(), atDate, mode, period, userData);
            } else {
                agingBins = agingBean.getDebtorsAging(customer.getCustomerId(), atDate, mode, period, parameters, userData);
            }
            
            BigDecimal unallocatedCredit = null;

            //Only get unallocated credit if mode is NONE
            if (mode == DebtorsAgingMode.NONE) {
                if (internalAgeingReport) {
                    unallocatedCredit = internalAgingBean.getTotalUnallocatedCredit(customer.getCustomerId(), atDate, userData);
                } else {
                    unallocatedCredit = agingBean.getTotalUnallocatedCredit(customer.getCustomerId(), atDate, userData);
                }
            }
            
            //Include settled records for Statement and for internal Ageing Report.
            List<DebtorsDetailedAgingHelper> agingHelpers = agingBean.getDetailedDebtorsAging(customer.getCustomerId(), atDate, mode, period, populateCustomerStatement || internalAgeingReport, userData);

            //If anything was found, add bins only at end, otherwise bin totals are added multiple times
            boolean addedRecord = false;

            //For loop conditions.  Ordering varies between detailed aging and statement reports.
            int loopStart;
            int increment;
            int stopValue;
            
            if (populateCustomerStatement) {
                loopStart = agingHelpers.size() - 1;
                increment = -1;
                stopValue = -1;
            } else {
                loopStart = 0;
                increment = 1;
                stopValue = agingHelpers.size();
            }
            
            boolean populatedStatementInfo = false;
            
            for (int i = loopStart; i != stopValue; i += increment) {
                DebtorsDetailedAgingHelper agingHelper = agingHelpers.get(i);
                String binName = agingHelper.getBinName();
                
                for (DebtorsDetailedAgingLineDS agingLine : agingHelper.getAgingLines()) {
                    CustomerStatementDSInterface ds = null;
                    
                    if (populateCustomerStatement) {
                        ds = new DebtorsCustomerStatementReportDS();

                        //Only set this info on first record for customer
                        if (!populatedStatementInfo) {
                            this.populateCustomerStatementInfo((DebtorsCustomerStatementReportDS) ds, customer, company, userData);
                            populatedStatementInfo = true;
                        }
                    } else {
                        ds = new DebtorsCustomerAgingDetailedReportDS();
                    }
                    
                    ds.setCustomrtId(customer.getCustomerId());
                    ds.setCustomerName(customer.getCustomerName());
                    ds.setAgingGroup(binName);
                    ds.setAmount(agingLine.getAmount());
                    ds.setBalance(agingLine.getBalance());
                    ds.setStatementDate(Functions.date2String(atDate));
                    ds.setAtDate(Functions.date2String(atDate));
                    ds.setAgingMode(String.valueOf(mode));
                    
                    if (util.compareDouble(agingLine.getAmount().doubleValue(), 0) > 0) {
                        ds.setDebitTransRecordID(agingLine.getTransRecordID());
                    }
                    
                    if (populateCustomerStatement) {
                        DebtorsCustomerStatementReportDS statementDS = (DebtorsCustomerStatementReportDS) ds;
                        
                        statementDS.setActualTransactionDate(agingLine.getTransactionDate());
                    }

                    //This field is used for calculations on the report.
                    //For aging MODE none, it should ignore credits.
                    if (mode == DebtorsAgingMode.NONE && util.compareDouble(ds.getBalance().doubleValue(), 0) < 0) {
                        ds.setCalculatedBalance(BigDecimal.ZERO);
                    } else {
                        ds.setCalculatedBalance(ds.getBalance());
                    }
                    
                    ds.setTransactionDate(Functions.date2String(agingLine.getTransactionDate()));
                    ds.setTransactionReference(agingLine.getTransactionRef());
                    ds.setTransactionType(agingLine.getTransactionType());
                    
                    if (util.compareDouble(agingLine.getAmount().doubleValue(), 0) > 0) {
                        //Debit
                        ds.setDebit(agingLine.getAmount());
                    } else {
                        //Credit
                        ds.setCredit(agingLine.getAmount().abs());
                    }

                    //Get due dates for customer statement.
                    if (populateCustomerStatement) {
                        if (agingLine.getPaymentDueDate() != null) {
                            ds.setDueDate(Functions.date2String(agingLine.getPaymentDueDate()));
                        }
                        ds.setCustomerOrderNumber(agingLine.getCustomerOrderNumber());
                    }
                    
                    ret.add(ds);
                    
                    addedRecord = true;
                }
            }

            //Show payment history for customer - N & L specific
            if (populateCustomerStatement) {
                Date currentPeriodStartDate = agingBins.get(0).getBinStartDate();
                
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactionSettlementHistory.class);
                query.addAnd("createdDate", currentPeriodStartDate, EMCQueryConditions.GREATER_THAN_EQ);
                query.addAnd("createdDate", atDate, EMCQueryConditions.LESS_THAN_EQ);
                query.addAnd("customerId", customer.getCustomerId());
                //Only show transactions that fall on or before atDate
                query.addAnd("creditTransactionDate", atDate, EMCQueryConditions.LESS_THAN_EQ);
                query.addTableAnd(DebtorsTransactions.class.getName(), "creditTransRef", DebtorsTransactionSettlementHistory.class.getName(), "recordID");
                query.addOrderBy("creditTransactionDate", DebtorsTransactionSettlementHistory.class.getName(), EMCQueryOrderByDirections.DESC);
                query.addOrderBy("debitTransRef", DebtorsTransactionSettlementHistory.class.getName(), EMCQueryOrderByDirections.DESC);
                query.addTableAsField(DebtorsTransactionSettlementHistory.class.getName());
                query.addTableAsField(DebtorsTransactions.class.getName());
                
                List<Object[]> historyRecords = (List<Object[]>) util.executeGeneralSelectQuery(query, userData);
                
                for (int i = 0; i < ret.size(); i++) {
                    DebtorsCustomerStatementReportDS ds = (DebtorsCustomerStatementReportDS) ret.get(i);

                    //Only use debits
                    if (ds.getDebitTransRecordID() != 0) {
                        for (int j = 0; j < historyRecords.size(); j++) {
                            DebtorsTransactionSettlementHistory settlementHistory = (DebtorsTransactionSettlementHistory) historyRecords.get(j)[0];
                            DebtorsTransactions creditTrans = (DebtorsTransactions) historyRecords.get(j)[1];
                            
                            if (settlementHistory.getDebitTransRef() == ds.getDebitTransRecordID()) {
                                historyRecords.remove(j--);
                                
                                DebtorsCustomerStatementReportDS newDS = new DebtorsCustomerStatementReportDS();
                                
                                newDS.setCustomrtId(customer.getCustomerId());
                                newDS.setCustomerName(customer.getCustomerName());

                                //Indent credit transaction ref
                                newDS.setTransactionReference("    " + creditTrans.getReferenceNumber());
                                newDS.setTransactionType(creditTrans.getReferenceType());
                                newDS.setCredit(settlementHistory.getCreditSettled());
                                newDS.setTransactionDate(Functions.date2String(creditTrans.getTransactionDate()));
                                newDS.setActualTransactionDate(creditTrans.getTransactionDate());
                                newDS.setAgingGroup(ds.getAgingGroup());
                                newDS.setCustomerName(customer.getCustomerName());
                                newDS.setAgingMode(String.valueOf(mode));
                                newDS.setAtDate(Functions.date2String(atDate));

                                //Add new record after invoice.
                                ret.add(++i, newDS);
                            }
                        }
                    }
                }
            } else if (internalAgeingReport) {
                Date settlementPeriodStartDate = agingBins.get(0).getBinStartDate();
                
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactionSettlementHistory.class);
                query.addAnd("createdDate", settlementPeriodStartDate, EMCQueryConditions.GREATER_THAN_EQ);
                query.addAnd("customerId", customer.getCustomerId());
                query.addTableAnd(DebtorsTransactions.class.getName(), "creditTransRef", DebtorsTransactionSettlementHistory.class.getName(), "recordID");
                query.addOrderBy("creditTransactionDate", DebtorsTransactionSettlementHistory.class.getName(), EMCQueryOrderByDirections.DESC);
                query.addOrderBy("debitTransRef", DebtorsTransactionSettlementHistory.class.getName(), EMCQueryOrderByDirections.DESC);
                query.addTableAsField(DebtorsTransactionSettlementHistory.class.getName());
                query.addTableAsField(DebtorsTransactions.class.getName());
                
                List<Object[]> historyRecords = (List<Object[]>) util.executeGeneralSelectQuery(query, userData);
                
                for (int i = 0; i < ret.size(); i++) {
                    DebtorsCustomerAgingDetailedReportDS ds = (DebtorsCustomerAgingDetailedReportDS) ret.get(i);

                    //Only use debits
                    if (ds.getDebitTransRecordID() != 0) {
                        for (int j = 0; j < historyRecords.size(); j++) {
                            DebtorsTransactionSettlementHistory settlementHistory = (DebtorsTransactionSettlementHistory) historyRecords.get(j)[0];
                            DebtorsTransactions creditTrans = (DebtorsTransactions) historyRecords.get(j)[1];
                            
                            if (settlementHistory.getDebitTransRef() == ds.getDebitTransRecordID()) {
                                historyRecords.remove(j--);
                                
                                DebtorsCustomerAgingDetailedReportDS newDS = new DebtorsCustomerAgingDetailedReportDS();
                                
                                newDS.setCustomrtId(customer.getCustomerId());
                                newDS.setCustomerName(customer.getCustomerName());

                                //Indent credit transaction ref
                                newDS.setTransactionReference("    " + creditTrans.getReferenceNumber());
                                newDS.setTransactionType(creditTrans.getReferenceType());
                                newDS.setCredit(settlementHistory.getCreditSettled());
                                newDS.setBalance(settlementHistory.getCreditSettled());
                                newDS.setAmount(settlementHistory.getCreditSettled());
                                newDS.setTransactionDate(Functions.date2String(creditTrans.getTransactionDate()));
                                newDS.setActualTransactionDate(creditTrans.getTransactionDate());
                                newDS.setAgingGroup(ds.getAgingGroup());
                                newDS.setCustomerName(customer.getCustomerName());
                                newDS.setAgingMode(String.valueOf(mode));
                                newDS.setAtDate(Functions.date2String(atDate));

                                //Add new record after invoice.
                                ret.add(++i, newDS);
                            }
                        }
                    }
                }
            }
            
            if (addedRecord) {
                CustomerStatementDSInterface ds = (CustomerStatementDSInterface) ret.get(ret.size() - 1);
                
                if (agingBins.size() > 0) {
                    ds.setCurrentBinName(parameters.getAgingCurrentBinName());
                    ds.setCurrentBinValue(agingBins.get(0).getBinAmount());
                }
                
                if (agingBins.size() > 1) {
                    ds.setBin1Name(parameters.getAgingBin1Name());
                    ds.setBin1Value(agingBins.get(1).getBinAmount());
                }
                
                if (agingBins.size() > 2) {
                    ds.setBin2Name(parameters.getAgingBin2Name());
                    ds.setBin2Value(agingBins.get(2).getBinAmount());
                }
                
                if (agingBins.size() > 3) {
                    ds.setBin3Name(parameters.getAgingBin3Name());
                    ds.setBin3Value(agingBins.get(3).getBinAmount());
                }
                
                if (agingBins.size() > 4) {
                    ds.setBin4Name(parameters.getAgingBin4Name());
                    ds.setBin4Value(agingBins.get(4).getBinAmount());
                }
                
                if (agingBins.size() > 5) {
                    ds.setBin5Name(parameters.getAgingBin5Name());
                    ds.setBin5Value(agingBins.get(5).getBinAmount());
                }
                
                if (agingBins.size() > 6) {
                    ds.setBin6Name(parameters.getAgingBin6Name());
                    ds.setBin6Value(agingBins.get(6).getBinAmount());
                }

                //Bin 7 is used for unallocated credits
                if (mode == DebtorsAgingMode.NONE) {
                    ds.setBin7Name("Unallocated Credit");
                    ds.setBin7Value(unallocatedCredit.multiply(new BigDecimal(-1)));
                }
            }

            //Add page footer to last customer record only.
            if (!ret.isEmpty()) {
                ((CustomerStatementDSInterface) ret.get(ret.size() - 1)).setPageFooterCaption(parameters.getReportFooterCaption());
                
                if (populateCustomerStatement) {
                    DebtorsCustomerStatementReportDS statementDS = (DebtorsCustomerStatementReportDS) ret.get(ret.size() - 1);

                    //Always populate for last record in order to get banking details.
                    populateBankingInfo(company, statementDS);
                }
            }

            //Detach transactions and settlement history records.
            util.detachEntities(userData);
        }
        
        return ret;
    }

    /**
     * This method populates customer information.
     */
    private void populateCustomerStatementInfo(DebtorsCustomerStatementReportDS ds, SOPCustomers customer, BaseCompanyTable company, EMCUserData userData) {
        //Get fax number
        ds.setCompanyFax(companyBean.getFaxNumber(userData.getUserName(), enumEMCModules.DEBTORS, userData));
        
        ds.setCompanyPhone(company.getCoPhoneNr());
        
        List<String> addressFields = new ArrayList<String>();
        
        ds.setCompanyRegistration(company.getCoRegNr());
        ds.setCompanyVATNumber(company.getCoVatRegNr());
        
        if (!isBlank(company.getCoPhysAdrs1())) {
            addressFields.add(company.getCoPhysAdrs1());
        }
        
        if (!isBlank(company.getCoPhysAdrs2())) {
            addressFields.add(company.getCoPhysAdrs2());
        }
        
        if (!isBlank(company.getCoPhysAdrs3())) {
            addressFields.add(company.getCoPhysAdrs3());
        }
        
        if (!isBlank(company.getCoPhysAdrs4())) {
            addressFields.add(company.getCoPhysAdrs4());
        }
        
        if (!isBlank(company.getCoPhysAdrs5())) {
            addressFields.add(company.getCoPhysAdrs5());
        }
        
        if (!isBlank(company.getCoPhysPostCode())) {
            addressFields.add(company.getCoPhysPostCode());
        }
        
        doCompanyPhysicalAddress(ds, addressFields);
        
        addressFields.clear();
        if (!isBlank(company.getCoPhysAdrs1())) {
            addressFields.add(company.getCoPhysAdrs1());
        }
        
        if (!isBlank(company.getCoPhysAdrs2())) {
            addressFields.add(company.getCoPhysAdrs2());
        }
        
        if (!isBlank(company.getCoPhysAdrs3())) {
            addressFields.add(company.getCoPhysAdrs3());
        }
        
        if (!isBlank(company.getCoPhysAdrs4())) {
            addressFields.add(company.getCoPhysAdrs4());
        }
        
        if (!isBlank(company.getCoPhysAdrs5())) {
            addressFields.add(company.getCoPhysAdrs5());
        }
        
        if (!isBlank(company.getCoPhysPostCode())) {
            addressFields.add(company.getCoPhysPostCode());
        }
        
        doCompanyPostalAddress(ds, addressFields);
        
        addressFields.clear();
        
        if (!isBlank(customer.getPostalAddresLine1())) {
            addressFields.add(customer.getPostalAddresLine1());
        }
        
        if (!isBlank(customer.getPostalAddresLine2())) {
            addressFields.add(customer.getPostalAddresLine2());
        }
        
        if (!isBlank(customer.getPostalAddresLine3())) {
            addressFields.add(customer.getPostalAddresLine3());
        }
        
        if (!isBlank(customer.getPostalAddresLine4())) {
            addressFields.add(customer.getPostalAddresLine4());
        }
        
        if (!isBlank(customer.getPostalAddresLine5())) {
            addressFields.add(customer.getPostalAddresLine5());
        }
        
        if (!isBlank(customer.getPostalPostalCode())) {
            addressFields.add(customer.getPostalPostalCode());
        }
        
        doCustomerAddress(ds, addressFields);
        
        populateBankingInfo(company, ds);
    }

    /**
     * This method formats the customer address
     */
    private void doCustomerAddress(DebtorsCustomerStatementReportDS ds, List<String> addressFields) {
        if (addressFields.size() > 0) {
            ds.setCustomerAddress1(addressFields.get(0));
        }
        
        if (addressFields.size() > 1) {
            ds.setCustomerAddress2(addressFields.get(1));
        }
        
        if (addressFields.size() > 2) {
            ds.setCustomerAddress3(addressFields.get(2));
        }
        
        if (addressFields.size() > 3) {
            ds.setCustomerAddress4(addressFields.get(3));
        }
        
        if (addressFields.size() > 4) {
            ds.setCustomerAddress5(addressFields.get(4));
        }
        
        if (addressFields.size() > 5) {
            ds.setCustomerAddressCode(addressFields.get(5));
        }
        
    }

    /**
     * This method formats the company physical address
     */
    private void doCompanyPhysicalAddress(DebtorsCustomerStatementReportDS ds, List<String> addressFields) {
        if (addressFields.size() > 0) {
            ds.setCompanyPhysicalAddress1(addressFields.get(0));
        }
        
        if (addressFields.size() > 1) {
            ds.setCompanyPhysicalAddress2(addressFields.get(1));
        }
        
        if (addressFields.size() > 2) {
            ds.setCompanyPhysicalAddress3(addressFields.get(2));
        }
        
        if (addressFields.size() > 3) {
            ds.setCompanyPhysicalAddress4(addressFields.get(3));
        }
        
        if (addressFields.size() > 4) {
            ds.setCompanyPhysicalAddress5(addressFields.get(4));
        }
        
        if (addressFields.size() > 5) {
            ds.setCompanyPhysicalAddressCode(addressFields.get(5));
        }
        
    }

    /**
     * This method formats the company postal address
     */
    private void doCompanyPostalAddress(DebtorsCustomerStatementReportDS ds, List<String> addressFields) {
        if (addressFields.size() > 0) {
            ds.setCompanyPostalAddress1(addressFields.get(0));
        }
        
        if (addressFields.size() > 1) {
            ds.setCompanyPostalAddress2(addressFields.get(1));
        }
        
        if (addressFields.size() > 2) {
            ds.setCompanyPostalAddress3(addressFields.get(2));
        }
        
        if (addressFields.size() > 3) {
            ds.setCompanyPostalAddress4(addressFields.get(3));
        }
        
        if (addressFields.size() > 4) {
            ds.setCompanyPostalAddress5(addressFields.get(4));
        }
        
        if (addressFields.size() > 5) {
            ds.setCompanyPostalAddressCode(addressFields.get(5));
        }
    }

    /**
     * Populates banking details.
     */
    private void populateBankingInfo(BaseCompanyTable company, DebtorsCustomerStatementReportDS ds) {
        //This field uses HTML markup to ensure that headings are displayed in bold, but still utilize space as effectively as possible.
        StringBuilder bankingInfo = new StringBuilder(company.getCoBank());
        bankingInfo.append("&nbsp;&nbsp;<b>Branch:</b>&nbsp;&nbsp;");
        bankingInfo.append(company.getCoBankBranch());
        bankingInfo.append("&nbsp;&nbsp;<b>Account:</b>&nbsp;&nbsp;");
        bankingInfo.append(company.getCoBankAccNo());
        
        ds.setBankingInfo(bankingInfo.toString());
    }
}
