/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.allocationimport;

import emc.bus.debtors.creditnotes.DebtorsCreditNoteMasterLocal;
import emc.bus.debtors.transactionsettlement.DebtorsTransactionSettlementLocal;
import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.entity.debtors.DebtorsOpenTransactions;
import emc.entity.debtors.DebtorsTransactions;
import emc.entity.debtors.allocationimport.DebtorsAllocationImport;
import emc.entity.debtors.allocationimport.DebtorsAllocationImportFailLog;
import emc.entity.debtors.allocationimport.DebtorsAllocationImportSetupLines;
import emc.entity.debtors.transactionsettlement.DebtorsTransactionSettlement;
import emc.enums.debtors.allocationimport.DebtorsAllocationImportPaymentOrder;
import emc.enums.debtors.allocationimport.DebtorsAllocationImportSetupConditions;
import emc.enums.debtors.allocationimport.DebtorsAllocationImportStatus;
import emc.enums.debtors.transactions.DebtorsTransactionRefTypes;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.emcquery.EMCQueryOrderByDirections;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.messages.ServerDebtorsMessageEnum;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author riaan
 */
@Stateless
public class DebtorsAllocationImportBean extends EMCEntityBean implements DebtorsAllocationImportLocal {

    @EJB
    private DebtorsAllocationImportSetupMasterLocal importSetupMasterBean;
    @EJB
    private DebtorsCreditNoteMasterLocal creditNoteMasterBean;
    @EJB
    private DebtorsAllocationImportFailLogLocal allocationImportFailLogBean;
    @EJB
    private DebtorsTransactionSettlementLocal transactionSettlementBean;
    @EJB
    private DebtorsAllocationImportLocal thisBean;

    /** Creates a new instance of DebtorsAllocationImportBean. */
    public DebtorsAllocationImportBean() {
    }

    /**
     * Imports data in the specified list to the specified allocation session.
     * @param allocationImport Allocation import record.
     * @param importData Data to import.  The first line should contain column headings.
     * @param userData User data.
     * @param fromFailLog Indicates whether this import is being called from the fail log bean.  If so, records should not be written to the fail log.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean importSettlement(DebtorsAllocationImport allocationImport, List<String> importData, boolean fromFailLog, EMCUserData userData) throws EMCEntityBeanException {
        if (!fromFailLog && DebtorsAllocationImportStatus.fromString(allocationImport.getImportStatus()) == DebtorsAllocationImportStatus.ALLOCATED) {
            logMessage(Level.SEVERE, ServerDebtorsMessageEnum.IMPORT_ALREADY_PROCESSED, userData);
            return false;
        }

        if (!fromFailLog) {
            allocationImportFailLogBean.clearFailLog(allocationImport.getImportCode(), userData);
        }



        logMessage(Level.INFO, ServerDebtorsMessageEnum.STARTING_IMPORT, "import", userData);

        if (importData == null || importData.isEmpty()) {
            return false;
        }

        List<String> columnNames = Arrays.asList(importData.remove(0).split(","));

        List<DebtorsAllocationImportHelper> creditList = new ArrayList<DebtorsAllocationImportHelper>();
        List<DebtorsAllocationImportHelper> debitList = new ArrayList<DebtorsAllocationImportHelper>();
        List<DebtorsAllocationImportHelper> otherList = new ArrayList<DebtorsAllocationImportHelper>();

        this.mapImportData(allocationImport.getCustomerId(), importData, creditList, debitList, otherList, columnNames, userData);

        processCredits(allocationImport, creditList, fromFailLog, userData);
        processDebits(allocationImport, debitList, fromFailLog, userData);
        processOther(allocationImport, otherList, fromFailLog, userData);

        allocationImport.setImportStatus(DebtorsAllocationImportStatus.ALLOCATED.toString());

        this.update(allocationImport, userData);

        logMessage(Level.INFO, ServerDebtorsMessageEnum.IMPORT_COMPLETE, "/import", userData);

        return true;
    }

    /**
     * Processes credit lines from the import file.
     * @param allocationImport Allocation import record.
     * @param creditList List of credit helpers to be processed.
     * @param fromFailLog Indicates whether this import is being called from the fail log bean.  If so, records should not be written to the fail log.
     * @param userData User data.
     * @throws EMCEntityBeanException
     */
    private void processCredits(DebtorsAllocationImport allocationImport, List<DebtorsAllocationImportHelper> creditList, boolean fromFailLog, EMCUserData userData) throws EMCEntityBeanException {
        for (DebtorsAllocationImportHelper creditHelper : creditList) {

            DebtorsCreditNoteMaster creditNote = creditNoteMasterBean.getCreditNote(creditHelper.getTransactionReference(), userData);

            if (creditNote == null) {
                logMessage(Level.SEVERE, ServerDebtorsMessageEnum.CRN_NOT_FOUND, "import", userData, creditHelper.getTransactionReference());

                if (!fromFailLog) {
                    writeFailLog(allocationImport, creditHelper, userData);
                }
            } else {
                long sessionId = 0;

                try {
                    sessionId = thisBean.createSession(allocationImport.getCustomerId(), userData);
                } catch (EMCEntityBeanException ex) {
                    logMessage(Level.SEVERE, "Failed to create allocation.", userData);
                    throw ex;
                }
                //Get invoice in allocation
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactionSettlement.class.getName());
                query.addAnd("sessionId", sessionId);
                query.addAnd("referenceNumber", creditNote.getRefInvoiceNo());

                DebtorsTransactionSettlement invoiceSettlement = (DebtorsTransactionSettlement) util.executeSingleResultQuery(query, userData);

                if (invoiceSettlement == null) {
                    logMessage(Level.SEVERE, ServerDebtorsMessageEnum.INV_NOT_FOUND, "import", userData, creditNote.getRefInvoiceNo());

                    if (!fromFailLog) {
                        writeFailLog(allocationImport, creditHelper, userData);
                    }
                } else {
                    invoiceSettlement.setDebitAmountSettled(creditHelper.getCreditAmountAllocated());
                    invoiceSettlement.setTick(true);
                    //Do not let any logic change this amount.
                    invoiceSettlement.setUserChangedAmount(true);

                    transactionSettlementBean.update(invoiceSettlement, userData);

                    query.removeAnd("referenceNumber");
                    query.addAnd("referenceNumber", creditHelper.getTransactionReference());

                    DebtorsTransactionSettlement creditNoteSettlement = (DebtorsTransactionSettlement) util.executeSingleResultQuery(query, userData);

                    if (creditNoteSettlement == null) {
                        logMessage(Level.SEVERE, ServerDebtorsMessageEnum.CRN_NOT_FOUND, "import", userData, creditNote.getRefInvoiceNo());

                        if (!fromFailLog) {
                            writeFailLog(allocationImport, creditHelper, userData);
                        }
                    } else {
                        creditNoteSettlement.setDebitAmountSettled(creditHelper.getCreditAmountAllocated());
                        creditNoteSettlement.setTick(true);
                        //Do not let any logic change this amount.
                        creditNoteSettlement.setUserChangedAmount(true);

                        try {
                            transactionSettlementBean.update(creditNoteSettlement, userData);

                            transactionSettlementBean.allocateSettlement(sessionId, allocationImport.getCustomerId(), userData);
                        } catch (EMCEntityBeanException ex) {
                            logMessage(Level.SEVERE, "Failed to allocate.", userData);
                            throw ex;
                        }
                    }
                }
            }
        }
    }

    /**
     * Processes debit lines from the import file.
     * @param allocationImport Allocation import record.
     * @param debitList List of debit helpers to be processed.
     * @param fromFailLog Indicates whether this import is being called from the fail log bean.  If so, records should not be written to the fail log.
     * @param userData User data.
     * @throws EMCEntityBeanException
     */
    private void processDebits(DebtorsAllocationImport allocationImport, List<DebtorsAllocationImportHelper> debitList, boolean fromFailLog, EMCUserData userData) throws EMCEntityBeanException {
        DebtorsAllocationImportPaymentOrder paymentOrder = DebtorsAllocationImportPaymentOrder.fromString(allocationImport.getAutoAllocationPaymentOrder());
        boolean allowPartialAllocation = allocationImport.isAllowPartialAllocation();

        BigDecimal debitTotal = BigDecimal.ZERO;

        //Sum over records in the file
        for (DebtorsAllocationImportHelper debitHelper : debitList) {
            debitTotal = debitTotal.add(debitHelper.getDebitAmountAllocated() == null ? BigDecimal.ZERO : debitHelper.getDebitAmountAllocated());
        }

        EMCQuery totalCreditQuery = new EMCQuery(enumQueryTypes.SELECT, DebtorsOpenTransactions.class);
        totalCreditQuery.addFieldAggregateFunction("balance", "SUM");
        totalCreditQuery.addAnd("balance", 0, EMCQueryConditions.LESS_THAN);
        totalCreditQuery.addAnd("customerId", allocationImport.getCustomerId());

        BigDecimal totalCreditAvailable = (BigDecimal) util.executeSingleResultQuery(totalCreditQuery, userData);

        //Assign absolute value to credit available, otherwise it'll be negative.
        if (totalCreditAvailable == null || (totalCreditAvailable = totalCreditAvailable.abs()).compareTo(debitTotal) < 0) {
            logMessage(Level.SEVERE, ServerDebtorsMessageEnum.INSUF_CREDIT, userData);

            if (!fromFailLog) {
                for (DebtorsAllocationImportHelper debitHelper : debitList) {
                    writeFailLog(allocationImport, debitHelper, userData);
                }
            }

            return;
        }

        long sessionId = transactionSettlementBean.populateSettlement(allocationImport.getCustomerId(), userData);

        //Incase a payment is only allocated partially and may not be used again.  This helps prevent an infinite loop trying to allocate the same payment over and over.
        List<String> processedPaymentList = new ArrayList<String>();

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactionSettlement.class);
        query.addAnd("sessionId", sessionId);
        query.addAnd("debit", 0, EMCQueryConditions.GREATER_THAN);
        query.addOrderBy("transactionDate");

        List<DebtorsTransactionSettlement> debitSettlements = (List<DebtorsTransactionSettlement>) util.executeGeneralSelectQuery(query, userData);

        for (int outer = 0; outer < debitSettlements.size(); outer++) {
            query = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactionSettlement.class);
            query.addAnd("sessionId", sessionId);
            query.addAnd("credit", 0, EMCQueryConditions.GREATER_THAN);

            if (!processedPaymentList.isEmpty()) {
                query.addAndInList("referenceNumber", processedPaymentList, true, true);
            }

            if (paymentOrder == DebtorsAllocationImportPaymentOrder.AMOUNT) {
                query.addOrderBy("credit", DebtorsTransactionSettlement.class.getName(), EMCQueryOrderByDirections.DESC);
            } else if (paymentOrder == DebtorsAllocationImportPaymentOrder.DATE) {
                query.addOrderBy("transactionDate");
            }

            //Payments are selected for each session, therefore we can't execute this only once.
            List<DebtorsTransactionSettlement> creditSettlements = (List<DebtorsTransactionSettlement>) util.executeGeneralSelectQuery(query, userData);

            if (creditSettlements.isEmpty()) {
                if (!fromFailLog) {
                    for (DebtorsAllocationImportHelper debitHelper : debitList) {
                        writeFailLog(allocationImport, debitHelper, userData);
                    }
                }
                return;
            }

            DebtorsTransactionSettlement creditSettlement = creditSettlements.get(0);
            BigDecimal creditAmount = creditSettlement.getCredit();

            for (int inner = 0; inner < debitSettlements.size(); inner++) {
                DebtorsTransactionSettlement debitSettlement = debitSettlements.get(inner);
                DebtorsAllocationImportHelper debitHelper = getImportHelper(debitSettlement.getReferenceNumber(), debitList);

                if (debitHelper == null) {
                    //Not a record in the file
                    continue;
                } else {
                    if (debitHelper.getRebate() != null) {
                        debitSettlement.setRebate(debitHelper.getRebate());
                        //If partially allocated, do not use this again later.
                        debitHelper.setRebate(BigDecimal.ZERO);
                    }

                    if (debitHelper.getSettlementDiscount() != null) {
                        debitSettlement.setDiscTaken(debitHelper.getSettlementDiscount());
                        //If partially allocated, do not use this again later.
                        debitHelper.setSettlementDiscount(BigDecimal.ZERO);
                    }

                    if (debitHelper.getDebitAmountAllocated() != null) {
                        if (debitHelper.getDebitAmountAllocated().compareTo(creditAmount) <= 0) {
                            debitSettlement.setDebitAmountSettled(debitHelper.getDebitAmountAllocated());
                            debitList.remove(debitHelper);
                        } else if (allowPartialAllocation) {
                            debitSettlement.setDebitAmountSettled(creditAmount);
                            debitHelper.setDebitAmountAllocated(creditAmount);
                        }
                    }

                    creditAmount = creditAmount.subtract(debitSettlement.getDebitAmountSettled());

                    debitSettlement.setTick(true);
                    //Do not allow the system to change this amount.
                    debitSettlement.setUserChangedAmount(true);

                    try {
                        transactionSettlementBean.update(debitSettlement, userData);
                    } catch (EMCEntityBeanException ex) {
                        if (!fromFailLog) {
                            writeFailLog(allocationImport, debitHelper, userData);
                        }
                        //Add back failed amount
                        creditAmount = creditAmount.add(debitTotal);
                        debitSettlements.remove(inner--);
                    }

                    if (creditAmount.compareTo(BigDecimal.ZERO) == 0) {
                        break;
                    }
                }
            }

            creditSettlement.setTick(true);
            creditSettlement.setUserChangedAmount(true);
            creditSettlement.setCreditAmountSettled(creditSettlement.getCredit().subtract(creditAmount));

            transactionSettlementBean.update(creditSettlement, userData);

            processedPaymentList.add(creditSettlement.getReferenceNumber());

            try {
                transactionSettlementBean.allocateSettlement(sessionId, allocationImport.getCustomerId(), userData);
            } catch (EMCEntityBeanException ex) {
                logMessage(Level.SEVERE, "Failed to allocate.", userData);
                throw ex;
            }

            sessionId = thisBean.createSession(allocationImport.getCustomerId(), userData);

            if (debitList.isEmpty()) {
                break;
            }
        }
    }

    /**
     * Creates a settlement session in a new transaction.  Without this, update method on settlement bean falls over.
     * @param customerId Customer for which a session should be generated.
     * @param userData User data.
     * @return A session id.
     * @throws EMCEntityBeanException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public long createSession(String customerId, EMCUserData userData) throws EMCEntityBeanException {
        return transactionSettlementBean.populateSettlement(customerId, userData);
    }

    /**
     * Processes 'other' lines from the import file.
     * @param allocationImport Allocation import record.
     * @param otherList List of 'other' helpers to be processed.
     * @param fromFailLog Indicates whether this import is being called from the fail log bean.  If so, records should not be written to the fail log.
     * @param userData User data.
     * @throws EMCEntityBeanException
     */
    private void processOther(DebtorsAllocationImport allocationImport, List<DebtorsAllocationImportHelper> otherList, boolean fromFailLog, EMCUserData userData) throws EMCEntityBeanException {
        if (!fromFailLog) {
            for (DebtorsAllocationImportHelper otherHelper : otherList) {
                writeFailLog(allocationImport, otherHelper, userData);
            }
        }
    }

    /**
     * Maps import data to various lists.
     *
     * @param customerId Customer for whom data is being imported.
     * @param importData Data from import file.
     * @param creditList List containing helper class instances for credit records.
     * @param debitList List containing helper class instances for debit records.
     * @param otherList List containing helper class instances for other (unmapped) records.
     * @param columnNames List containing column names in import file.  (First line)
     * @param userData User data.
     */
    private void mapImportData(String customerId, final List<String> importData, final List<DebtorsAllocationImportHelper> creditList, final List<DebtorsAllocationImportHelper> debitList, final List<DebtorsAllocationImportHelper> otherList, final List<String> columnNames, final EMCUserData userData) {
        Map<String, List<MappingHelper>> mappingHelpers = getMappingHelpers(customerId, columnNames, userData);

        for (String line : importData) {
            DebtorsAllocationImportHelper helper = mapImportLine(line, columnNames, mappingHelpers, userData);

            //Check whether transaction exists
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactions.class);

            if (!isBlank(helper.getTransactionReference())) {
                query.addAnd("transactionSource", helper.getTransactionReference());
            }

            if (!isBlank(helper.getCustomerOrderNumber())) {
                query.addAnd("customerOrderNumber", helper.getCustomerOrderNumber());
            }

            DebtorsTransactions transaction = (DebtorsTransactions) util.executeSingleResultQuery(query, userData);

            if (transaction == null || transaction.getBalance().equals(BigDecimal.ZERO) || transaction.getBalance().compareTo(helper.getDebitAmountAllocated()) < -1) {
                DebtorsAllocationImportHelper otherHelper = getImportHelper(helper.getTransactionReference(), otherList);

                if (otherHelper != null) {
                    //Update existing helper
                    if (helper.getDebitAmountAllocated() != BigDecimal.ZERO) {
                        otherHelper.setDebitAmountAllocated(helper.getDebitAmountAllocated());
                    }

                    if (helper.getCreditAmountAllocated() != BigDecimal.ZERO) {
                        otherHelper.setCreditAmountAllocated(helper.getCreditAmountAllocated());
                    }

                    if (helper.getRebate() != BigDecimal.ZERO) {
                        otherHelper.setRebate(helper.getRebate());
                    }

                    if (helper.getSettlementDiscount() != BigDecimal.ZERO) {
                        otherHelper.setSettlementDiscount(helper.getSettlementDiscount());
                    }
                }

                if (otherHelper == null) {
                    //Not found or already settled
                    otherList.add(helper);
                }
            } else if (transaction.getDebit().compareTo(BigDecimal.ZERO) > 0) {
                DebtorsAllocationImportHelper otherHelper = getImportHelper(helper.getTransactionReference(), debitList);

                if (otherHelper != null) {
                    //Update existing helper
                    if (helper.getDebitAmountAllocated() != BigDecimal.ZERO) {
                        otherHelper.setDebitAmountAllocated(helper.getDebitAmountAllocated());
                    }

                    if (helper.getCreditAmountAllocated() != BigDecimal.ZERO) {
                        otherHelper.setCreditAmountAllocated(helper.getCreditAmountAllocated());
                    }

                    if (helper.getRebate() != BigDecimal.ZERO) {
                        otherHelper.setRebate(helper.getRebate());
                    }

                    if (helper.getSettlementDiscount() != BigDecimal.ZERO) {
                        otherHelper.setSettlementDiscount(helper.getSettlementDiscount());
                    }
                }

                if (otherHelper == null) {
                    //Debit
                    debitList.add(helper);
                }
            } else {
                //Credit
                if (DebtorsTransactionRefTypes.CREDIT_NOTE.toString().equalsIgnoreCase(transaction.getReferenceType())) {
                    DebtorsAllocationImportHelper otherHelper = getImportHelper(helper.getTransactionReference(), creditList);

                    if (otherHelper != null) {
                        //Update existing helper
                        if (helper.getDebitAmountAllocated() != BigDecimal.ZERO) {
                            otherHelper.setDebitAmountAllocated(helper.getDebitAmountAllocated());
                        }

                        if (helper.getCreditAmountAllocated() != BigDecimal.ZERO) {
                            otherHelper.setCreditAmountAllocated(helper.getCreditAmountAllocated());
                        }

                        if (helper.getRebate() != BigDecimal.ZERO) {
                            otherHelper.setRebate(helper.getRebate());
                        }

                        if (helper.getSettlementDiscount() != BigDecimal.ZERO) {
                            otherHelper.setSettlementDiscount(helper.getSettlementDiscount());
                        }
                    }

                    if (otherHelper == null) {
                        //Credit note
                        creditList.add(helper);
                    }
                } else {
                    DebtorsAllocationImportHelper otherHelper = getImportHelper(helper.getTransactionReference(), otherList);

                    if (otherHelper != null) {
                        //Update existing helper
                        if (helper.getDebitAmountAllocated() != BigDecimal.ZERO) {
                            otherHelper.setDebitAmountAllocated(helper.getDebitAmountAllocated());
                        }

                        if (helper.getCreditAmountAllocated() != BigDecimal.ZERO) {
                            otherHelper.setCreditAmountAllocated(helper.getCreditAmountAllocated());
                        }

                        if (helper.getRebate() != BigDecimal.ZERO) {
                            otherHelper.setRebate(helper.getRebate());
                        }

                        if (helper.getSettlementDiscount() != BigDecimal.ZERO) {
                            otherHelper.setSettlementDiscount(helper.getSettlementDiscount());
                        }
                    }

                    if (otherHelper == null) {
                        //We cannot map this credit
                        otherList.add(helper);
                    }
                }
            }
        }
    }

    /**
     * Returns A debit allocation import helper for the specified transaction.
     * @param transactionReference Reference of transaction to search for.
     * @param debitList List of DebitAllocationImportHelper instances.
     * @return A debit DebtorsAllocationImportHelper for the specified transaction, or null, if no helper is found.
     */
    private DebtorsAllocationImportHelper getImportHelper(String transactionReference, List<DebtorsAllocationImportHelper> debitList) {
        if (transactionReference != null) {
            for (DebtorsAllocationImportHelper debitHelper : debitList) {
                if (transactionReference.equals(debitHelper.getTransactionReference())) {
                    return debitHelper;
                }
            }
        }

        return null;
    }

    /**
     * Writes a fail log record.
     * @param allocationImport Allocation import record for current allocation.
     * @param helper DebtorsAllocationImportHelper representing the line being logged.
     * @param userData User data.
     * @throws emc.framework.EMCEntityBeanException
     */
    private void writeFailLog(DebtorsAllocationImport allocationImport, DebtorsAllocationImportHelper helper, EMCUserData userData) throws EMCEntityBeanException {
        BigDecimal minusOne = new BigDecimal(-1);

        DebtorsAllocationImportFailLog failLog = new DebtorsAllocationImportFailLog();

        if (helper.getCreditAmountAllocated() != BigDecimal.ZERO) {
            failLog.setCredit(helper.getCreditAmountAllocated());
            failLog.setBalance(failLog.getCredit().multiply(minusOne));
        } else if (helper.getDebitAmountAllocated() != BigDecimal.ZERO) {
            failLog.setDebit(helper.getDebitAmountAllocated());
            failLog.setBalance(failLog.getDebit());
        }

        failLog.setCustomerOrderNumber(helper.getCustomerOrderNumber());
        failLog.setTransReference(helper.getTransactionReference());
        failLog.setLine(helper.getLine());

        failLog.setImportCode(allocationImport.getImportCode());

        allocationImportFailLogBean.insert(failLog, userData);
    }

    /**
     * Validates the specified import.  All unmappable lines will be written to the fail log.
     * @param allocationImport Import to validate.
     * @param List<String> importData Data to validate.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws emc.framework.EMCEntityBeanException
     */
    public boolean validateImport(DebtorsAllocationImport allocationImport, List<String> importData, EMCUserData userData) throws EMCEntityBeanException {
        if (DebtorsAllocationImportStatus.fromString(allocationImport.getImportStatus()) == DebtorsAllocationImportStatus.ALLOCATED) {
            throw new EMCEntityBeanException(getMessage(ServerDebtorsMessageEnum.MAY_NOT_VALIDATE, userData));
        }

        //Clear previous fail log records
        allocationImportFailLogBean.clearFailLog(allocationImport.getImportCode(), userData);

        List<String> columnNames = Arrays.asList(importData.remove(0).split(","));

        List<DebtorsAllocationImportHelper> creditList = new ArrayList<DebtorsAllocationImportHelper>();
        List<DebtorsAllocationImportHelper> debitList = new ArrayList<DebtorsAllocationImportHelper>();
        List<DebtorsAllocationImportHelper> otherList = new ArrayList<DebtorsAllocationImportHelper>();

        this.mapImportData(allocationImport.getCustomerId(), importData, creditList, debitList, otherList, columnNames, userData);

        processOther(allocationImport, otherList, false, userData);

        allocationImport.setImportStatus(DebtorsAllocationImportStatus.VALIDATED.toString());

        this.update(allocationImport, userData);

        return true;
    }

    /**
     * Maps the specified line to a DebtorsAllocationImportHelper instance.
     * @param line Line to map.
     * @param columnNames Column names in the import file.
     * @param mappingHelpers Mapping helpers used to map values.
     * @param userData User data.
     * @return A DebtorsAllocationImportHelper representing the specified line.
     */
    private DebtorsAllocationImportHelper mapImportLine(final String line, final List<String> columnNames, final Map<String, List<MappingHelper>> mappingHelpers, final EMCUserData userData) {
        String[] lineData = line.split(",");
        DebtorsAllocationImportHelper helper = new DebtorsAllocationImportHelper(line);

        for (int i = 0; i < columnNames.size(); i++) {
            if (mappingHelpers.containsKey(columnNames.get(i))) {
                for (MappingHelper mappingHelper : mappingHelpers.get(columnNames.get(i))) {
                    if (mappingHelper.checkCondition(columnNames, lineData, userData)) {
                        try {
                            String fieldName = mappingHelper.allocationField;
                            Object value = null;

                            Field field = DebtorsTransactionSettlement.class.getDeclaredField(fieldName);

                            if (field.getType() == BigDecimal.class) {
                                //Always use positive values.
                                value = new BigDecimal(lineData[i]).abs();
                            } else {
                                value = lineData[i];
                            }

                            helper.mapField(mappingHelper.allocationField, value);

                            break;
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        //Continue loop.
                        }
                    }
                }
            }
        }

        return helper;
    }

    /**
     * Returns a Map of fields and the mapping helpers bound to those fields.
     * Mapping helpers are generated from the Allocation Import setup tables in EMC.
     *
     * @param customerId Customer to get mapping for.
     * @param columnNames Columns to which data is being imported.
     * @param userData User data.
     * @return A Map of fields and mapping helpers.
     */
    private Map<String, List<MappingHelper>> getMappingHelpers(String customerId, List<String> columnNames, EMCUserData userData) {
        Map<String, List<MappingHelper>> mappingHelpers = new HashMap<String, List<MappingHelper>>();

        Map<String, List<DebtorsAllocationImportSetupLines>> mapping = importSetupMasterBean.getCustomerMapping(customerId, userData);

        for (String importField : columnNames) {
            if (!isBlank(importField)) {
                mappingHelpers.put(importField, new ArrayList<MappingHelper>());
            }
        }

        for (String field : mapping.keySet()) {
            for (DebtorsAllocationImportSetupLines setupLine : mapping.get(field)) {
                if (mappingHelpers.containsKey(setupLine.getSpreadsheetMapping())) {
                    mappingHelpers.get(setupLine.getSpreadsheetMapping()).add(new MappingHelper(setupLine, field));
                }
            }
        }

        return mappingHelpers;
    }

    /**
     * Returns the specified import allocation line.
     * @param importCode Import code of line to fetch.
     * @param userData User data.
     * @return The specified allocation import line, or null, if it does not exists.
     */
    public DebtorsAllocationImport getAllocationImport(String importCode, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsAllocationImport.class);
        query.addAnd("importCode", importCode);

        return (DebtorsAllocationImport) util.executeSingleResultQuery(query, userData);
    }

    /**
     * This class assists with mapping field values.
     */
    private class MappingHelper {

        private DebtorsAllocationImportSetupLines setupLine;
        protected String allocationField;

        /** Creates a new instance of MappingHelper. */
        public MappingHelper(DebtorsAllocationImportSetupLines setupLine, String allocationField) {
            this.setupLine = setupLine;
            this.allocationField = allocationField;
        }

        /**
         * Returns a boolean indicating whether the specified field matches the
         * condition on the DebtorsAllocationImportSetupLines instance belonging
         * to this MappingHelper instance.
         *
         * @param value Value to check.
         * @return A boolean indicating whether the condition is met.
         */
        public boolean checkCondition(List<String> columnNames, String[] lineData, EMCUserData userData) {
            try {
                DebtorsAllocationImportSetupConditions condition = DebtorsAllocationImportSetupConditions.fromString(setupLine.getMapCondition());

                String conditionField = isBlank(this.setupLine.getMapConditionField()) ? this.setupLine.getSpreadsheetMapping() : this.setupLine.getMapConditionField();
                String conditionValue = this.setupLine.getMapConditionValue();
                int conditionFieldIndex = columnNames.indexOf(conditionField);

                //Do not do a check for the 'NONE' condition.
                if (condition != null && condition != DebtorsAllocationImportSetupConditions.NONE) {
                    switch (condition) {
                        case CONTAINS:
                            //Only applies to String values
                            return lineData[conditionFieldIndex].contains(conditionValue);
                        //These conditions only apply to decimal fields.
                        case GREATER_THAN_EQ:
                            return new BigDecimal(lineData[conditionFieldIndex]).compareTo(new BigDecimal(conditionValue)) >= 0;
                        case LESS_THAN_EQ:
                            return new BigDecimal(lineData[conditionFieldIndex]).compareTo(new BigDecimal(conditionValue)) <= 0;
                        case EQUAL:
                            return new BigDecimal(lineData[conditionFieldIndex]).compareTo(new BigDecimal(conditionValue)) == 0;
                    }
                }

                return true;
            } catch (Exception ex) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to map field - " + this.setupLine.getSpreadsheetMapping() + "." + ex.getMessage(), userData);
                return false;
            }
        }
    }
}
