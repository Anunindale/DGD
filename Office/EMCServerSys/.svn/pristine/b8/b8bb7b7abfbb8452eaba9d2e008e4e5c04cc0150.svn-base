/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.creditnotes;

import emc.bus.base.BaseEmployeeLocal;
import emc.bus.debtors.creditnoteapprovalgroupemployeess.DebtorsCreditNoteApprovalGroupEmployeesLocal;
import emc.bus.debtors.creditnoteapprovalgroups.DebtorsCreditNoteApprovalGroupsLocal;
import emc.bus.debtors.creditnotereasons.DebtorsCreditNoteReasonsLocal;
import emc.bus.debtors.creditnoteregister.DebtorsCreditNoteRegisterLocal;
import emc.bus.debtors.customerinvoice.DebtorsCustomerInvoiceLinesLocal;
import emc.bus.debtors.customerinvoice.DebtorsCustomerInvoiceMasterLocal;
import emc.bus.debtors.parameters.DebtorsParametersLocal;
import emc.bus.debtors.transactions.logic.DebtorsTransactionLogicLocal;
import emc.bus.inventory.InventoryLocationLocal;
import emc.bus.inventory.dimensions.InventoryDimensionTableLocal;
import emc.bus.inventory.transactions.InventoryTransactionsLocal;
import emc.bus.sop.customers.SOPCustomersLocal;
import emc.bus.sop.salesrepcommission.SOPSalesRepCommissionLocal;
import emc.entity.base.BaseCompanyTable;
import emc.entity.debtors.DebtorsCreditNoteApprovalGroups;
import emc.entity.debtors.DebtorsCreditNoteLines;
import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.entity.debtors.DebtorsCreditNoteReasons;
import emc.entity.debtors.DebtorsCustomerInvoiceLines;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.entity.debtors.DebtorsParameters;
import emc.entity.debtors.creditnoteregister.DebtorsCreditNoteRegister;
import emc.entity.inventory.dimensions.InventoryDimensionTable;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.entity.sop.SOPCustomers;
import emc.enums.debtors.DebtorsInvoiceCreditNoteType;
import emc.enums.debtors.invoicestatus.DebtorsInvoiceStatus;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.register.RegisterFromTypeEnum;
import emc.enums.inventory.transactions.InventoryTransactionDirection;
import emc.enums.inventory.transactions.InventoryTransactionStatus;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.helpers.debtors.DebtorsInvCNTotalsHelper;
import emc.messages.ServerDebtorsMessageEnum;
import emc.tables.EMCTable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @description : Entity bean for DebtorsCreditNoteMaster.
 *
 * @date : 14 Jul 2010
 *
 * @author : Riaan Nel
 *
 * @version : 1.0
 */
@Stateless
public class DebtorsCreditNoteMasterBean extends EMCEntityBean implements DebtorsCreditNoteMasterLocal {

    @EJB
    private DebtorsCustomerInvoiceMasterLocal invoiceMasterBean;
    @EJB
    private DebtorsCustomerInvoiceLinesLocal invoiceLinesBean;
    @EJB
    private DebtorsCreditNoteLinesLocal creditNoteLinesBean;
    @EJB
    private DebtorsCreditNoteReasonsLocal reasonsBean;
    @EJB
    private BaseEmployeeLocal employeeBean;
    @EJB
    private DebtorsCreditNoteApprovalGroupEmployeesLocal approvalGroupEmployeesBean;
    @EJB
    private DebtorsTransactionLogicLocal transactionLogicBean;
    @EJB
    private SOPCustomersLocal customerBean;
    @EJB
    private DebtorsCreditNoteApprovalGroupsLocal approvalGroupsBean;
    @EJB
    private InventoryTransactionsLocal transactionsBean;
    @EJB
    private InventoryDimensionTableLocal dimTableBean;
    @EJB
    private DebtorsCreditNoteRegisterLocal registerBean;
    @EJB
    private DebtorsParametersLocal parameterBean;
    @EJB
    private InventoryLocationLocal locationsBean;
    @EJB
    private SOPSalesRepCommissionLocal commissionBean;

    /**
     * Creates a new instance of DebtorsCreditNoteMasterBean
     */
    public DebtorsCreditNoteMasterBean() {
    }

    /**
     * Creates a Credit Note from the specified Invoice.
     *
     * @param invoiceNo Invoice number of Invoice to use when creating a Credit
     * Note.
     * @param useStock Indicates whether the new Credit Note should.
     * @param userData User data.
     * @return The Credit Note master record that was created.
     * @throws EMCEntityBeanException
     */
    public DebtorsCreditNoteMaster createCreditNote(String invoiceNo, boolean useStock, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCreditNoteMaster.class);
        query.addAnd("refInvoiceNo", invoiceNo);
        query.addAnd("status", DebtorsInvoiceStatus.POSTED, EMCQueryConditions.NOT);
        query.addAnd("status", DebtorsInvoiceStatus.CANCELED, EMCQueryConditions.NOT);
        query.addAnd("status", DebtorsInvoiceStatus.DISTRIBUTION, EMCQueryConditions.NOT);
        if (util.exists(query, userData)) {
            throw new EMCEntityBeanException("An open credit note already exists for the given invoice.");
        }

        DebtorsCustomerInvoiceMaster invoiceMaster = invoiceMasterBean.getInvoiceMaster(invoiceNo, userData);

        if (!DebtorsInvoiceStatus.POSTED.equals(DebtorsInvoiceStatus.fromString(invoiceMaster.getStatus())) && !DebtorsInvoiceStatus.DISTRIBUTION.equals(DebtorsInvoiceStatus.fromString(invoiceMaster.getStatus()))) {
            throw new EMCEntityBeanException("An invoice must be in the " + DebtorsInvoiceStatus.POSTED.toString() + " or " + DebtorsInvoiceStatus.DISTRIBUTION.toString() + " status before a Credit Note can be created.");
        } else {
            if (checkCreditNoteExists(invoiceNo, userData)) {
                throw new EMCEntityBeanException("An open Credit Note already exists for the specified Invoice.");
            }

            if (!DebtorsInvoiceStatus.DISTRIBUTION.equals(DebtorsInvoiceStatus.fromString(invoiceMaster.getStatus())) && !isBlank(invoiceMaster.getDistributionNumber())) {
                throw new EMCEntityBeanException("You may not credit the consolidated distribution invoice. Rather credit the individual distribution invoices.");
            }
        }

        DebtorsParameters params = parameterBean.getDebtorsParameters(userData);

        if (params == null) {
            throw new EMCEntityBeanException(getMessage(ServerDebtorsMessageEnum.PARAM_NOT_FOUND, userData));
        } else {
            if (isBlank(params.getDefaultReturnWarehouse())) {
                throw new EMCEntityBeanException(getMessage(ServerDebtorsMessageEnum.NO_DEF_RET_WHSE, userData));
            } else {
                if (isBlank(params.getDefaultReturnLocation())) {
                    throw new EMCEntityBeanException(getMessage(ServerDebtorsMessageEnum.NO_DEF_RET_LOC, userData));
                } else {
                    if (!locationsBean.checkLocationInWarehouse(params.getDefaultReturnWarehouse(), params.getDefaultReturnLocation(), userData)) {
                        throw new EMCEntityBeanException(getMessage(ServerDebtorsMessageEnum.DEF_RET_LOC_NOT_IN_WHSE, userData));
                    }
                }
            }
        }

        DebtorsCreditNoteMaster creditNoteMaster = new DebtorsCreditNoteMaster();
        util.copyFields(invoiceMaster, creditNoteMaster, userData);
        //Get own number sequence.
        creditNoteMaster.setInvCNNumber(null);
        creditNoteMaster.setInvCNType(DebtorsInvoiceCreditNoteType.SALES_CREDIT_NOTE.toString());
        creditNoteMaster.setStatus(DebtorsInvoiceStatus.CAPTURED.toString());
        creditNoteMaster.setInvoiceStock(useStock);

        if (DebtorsInvoiceStatus.DISTRIBUTION.equals(DebtorsInvoiceStatus.fromString(invoiceMaster.getStatus()))) {
            query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCustomerInvoiceMaster.class);
            query.addAnd("distributionNumber", invoiceMaster.getDistributionNumber());
            query.addAnd("invCNType", DebtorsInvoiceCreditNoteType.MANUAL_INVOICE.toString());
            query.openAndConditionBracket();
            query.addOr("status", DebtorsInvoiceStatus.CAPTURED.toString());
            query.addOr("status", DebtorsInvoiceStatus.POSTED.toString());
            query.closeConditionBracket();
            query.addField("invCNNumber");
            
            String consolidatedInvNo = (String) util.executeSingleResultQuery(query, userData);
            
            if (isBlank(consolidatedInvNo)) {
                throw new EMCEntityBeanException("Failed to find the consolidated distribution invoice for the distribution number " + invoiceMaster.getDistributionNumber());
            } else {
                creditNoteMaster.setRefInvoiceNo(consolidatedInvNo);
            }
        } else {
            creditNoteMaster.setRefInvoiceNo(invoiceMaster.getInvCNNumber());
        }
        creditNoteMaster.setInvCNType(DebtorsInvoiceCreditNoteType.SALES_CREDIT_NOTE.toString());
        creditNoteMaster.setInvoiceDate(Functions.nowDate());
        creditNoteMaster.setPostedBy(null);
        creditNoteMaster.setPostedDate(null);
        creditNoteMaster.setPostedTime(null);
        creditNoteMaster.setPrintedBy(null);
        creditNoteMaster.setPrintedDate(null);
        creditNoteMaster.setPrintedTime(null);
        creditNoteMaster.setLastPrintedBy(null);
        creditNoteMaster.setLastPrintedDate(null);
        creditNoteMaster.setLastPrintedTime(null);
        creditNoteMaster.setTermsCode(null);
        creditNoteMaster.setPaymentDueDate(null);
        creditNoteMaster.setSettlementDiscDate(null);
        creditNoteMaster.setSettlementDiscCode(null);

        this.insert(creditNoteMaster, userData);

        List<DebtorsCustomerInvoiceLines> invoiceLines = invoiceLinesBean.getInvoiceLines(invoiceNo, userData);

        boolean createdLines = false;

        for (DebtorsCustomerInvoiceLines invoiceLine : invoiceLines) {
            if (invoiceLine.getQuantity().add(invoiceLine.getQuantityReturned()).compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            } else {
                createdLines = true;
            }

            DebtorsCreditNoteLines creditNoteLine = new DebtorsCreditNoteLines();
            util.copyFields(invoiceLine, creditNoteLine, userData);

            creditNoteLine.setInvCNNumber(creditNoteMaster.getInvCNNumber());
            creditNoteLine.setInventTransId(null);
            creditNoteLine.setQuantity((invoiceLine.getQuantity().add(invoiceLine.getQuantityReturned())).multiply(new BigDecimal(-1)));
            creditNoteLine.setMaxQuantity(creditNoteLine.getQuantity());
            creditNoteLine.setVatAmount(BigDecimal.ZERO);
            //Use default return warehouse
            creditNoteLine.setWarehouse(params.getDefaultReturnWarehouse());
            //Clear location, batch, pallet and serial.  This should be a 'high-level' in transaction.
            creditNoteLine.setBatch(null);
            creditNoteLine.setLocation(null);
            creditNoteLine.setPallet(null);
            creditNoteLine.setSerial(null);

            creditNoteLinesBean.insert(creditNoteLine, userData);

            if (creditNoteMaster.isInvoiceStock()) {
                //Post transaction.
                creditNoteLinesBean.postStockTransaction(creditNoteLine, userData);

                //Indicates that Credit Note is being created.  Used on register lines bean insert method.
                userData.setUserData(6, true);

                //Get all delivered transactions against invoice.  Do not specify type, as transactions may be invoice transactions or SO transactions.
                List<InventoryTransactions> transactions = transactionsBean.findTransactions(null, null, null, InventoryTransactionStatus.DELIVERED, InventoryTransactionDirection.OUT, invoiceLine.getInventTransId(), userData);

                for (InventoryTransactions transaction : transactions) {
                    query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
                    query.addAnd("refTransId", transaction.getRecordID());
                    query.addFieldAggregateFunction("quantity", "SUM");
                    Double returned = (Double) util.executeSingleResultQuery(query, userData);
                    if (returned == null) {
                        returned = 0d;
                    }
                    if (util.compareDouble(transaction.getQuantity(), returned) <= 0) {
                        continue;
                    }


                    InventoryDimensionTable dimTable = dimTableBean.getDimensionRecord(transaction.getItemDimId(), userData);

                    DebtorsCreditNoteRegister register = new DebtorsCreditNoteRegister();
                    register.setBatch(dimTable.getBatchId());
                    register.setMasterId(creditNoteMaster.getInvCNNumber());
                    register.setPallet(dimTable.getPalletId());
                    //Use negative quantities, same as on Credit Note lines.
                    register.setQuantity((transaction.getQuantity() - returned) * -1);
                    register.setRegisteredQty(transaction.getQuantity());
                    register.setSerial(dimTable.getItemSerialId());
                    register.setTotalQty(creditNoteLine.getQuantity().doubleValue());
                    register.setTransId(creditNoteLine.getInventTransId());
                    register.setType(RegisterFromTypeEnum.RETURN.toString());
                    //Use warehouse and location from Debtors Parameters
                    register.setWarehouse(params.getDefaultReturnWarehouse());
                    register.setLocation(params.getDefaultReturnLocation());

                    register.setOriginalQuantity(transaction.getQuantity() - returned);

                    registerBean.insert(register, userData);
                }
            }
        }

        if (!createdLines) {
            throw new EMCEntityBeanException("Invoice already fully returned.");
        }

        return creditNoteMaster;
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean valid = super.doInsertValidation(vobject, userData);

        if (valid) {
            DebtorsCreditNoteMaster master = (DebtorsCreditNoteMaster) vobject;
            valid = valid && validateRep(master, userData);
        }
        return valid;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean valid = super.doUpdateValidation(vobject, userData);

        if (valid) {
            DebtorsCreditNoteMaster master = (DebtorsCreditNoteMaster) vobject;
            DebtorsCreditNoteMaster persisted = (DebtorsCreditNoteMaster) util.findDetachedPersisted(master, userData);

            if (DebtorsInvoiceStatus.fromString(persisted.getStatus()) == DebtorsInvoiceStatus.POSTED) {
                logMessage(Level.SEVERE, ServerDebtorsMessageEnum.ALREADY_POSTED, userData);
                return false;
            } else if (DebtorsInvoiceStatus.fromString(persisted.getStatus()) == DebtorsInvoiceStatus.CANCELED || DebtorsInvoiceStatus.fromString(persisted.getStatus()) == DebtorsInvoiceStatus.DISTRIBUTION) {
                logMessage(Level.SEVERE, ServerDebtorsMessageEnum.ALREADY_CANCELED, userData);
                return false;
            }

            valid = valid && validateRep(master, userData);
        }
        return valid;
    }

    /**
     * Checks whether an open credit note exists for the specified Invoice.
     *
     * @param invoiceNo
     * @param userData
     * @return
     */
    private boolean checkCreditNoteExists(String invoiceNo, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCreditNoteMaster.class);
        query.addAnd("invCNNumber", invoiceNo);
        query.addAnd("status", DebtorsInvoiceStatus.POSTED.toString(), EMCQueryConditions.NOT);
        query.addAnd("status", DebtorsInvoiceStatus.CANCELED.toString(), EMCQueryConditions.NOT);
        query.addAnd("status", DebtorsInvoiceStatus.DISTRIBUTION.toString(), EMCQueryConditions.NOT);

        return util.exists(query, userData);
    }

    /**
     * Returns the specified Credit Note Master.
     *
     * @param creditNoteNo Credit Note No to search for.
     * @param userData User data.
     * @return The specified Credit Note Master, or null, if nothing is found.
     */
    public DebtorsCreditNoteMaster getCreditNote(String creditNoteNo, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCreditNoteMaster.class);
        query.addAnd("invCNNumber", creditNoteNo);

        return (DebtorsCreditNoteMaster) util.executeSingleResultQuery(query, userData);

    }

    /**
     * Approves the specified Credit Note.
     *
     * @param creditNoteId Id of Credit Note to approve.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean approveCreditNote(String creditNoteId, EMCUserData userData) throws EMCEntityBeanException {
        DebtorsCreditNoteMaster creditNoteMaster = getCreditNote(creditNoteId, userData);

        if (creditNoteMaster == null) {
            throw new EMCEntityBeanException("Credit Note not found.");
        } else {
            if (isBlank(creditNoteMaster.getReasonCode())) {
                logMessage(Level.SEVERE, ServerDebtorsMessageEnum.NO_REASON, userData);
                return false;
            } else {
                if (isBlank(creditNoteMaster.getApprovalGroup())) {
                    logMessage(Level.SEVERE, ServerDebtorsMessageEnum.NO_GROUP, userData);
                    return false;
                } else {
                    DebtorsInvoiceStatus status = DebtorsInvoiceStatus.fromString(creditNoteMaster.getStatus());

                    if (DebtorsInvoiceStatus.APPROVED.equals(status)) {
                        logMessage(Level.SEVERE, ServerDebtorsMessageEnum.ALREADY_APPROVED, userData);
                        return false;
                    } else {
                        if (DebtorsInvoiceStatus.POSTED.equals(status)) {
                            logMessage(Level.SEVERE, ServerDebtorsMessageEnum.ALREADY_APPROVED, userData);
                            return false;
                        } else {
                            if (DebtorsInvoiceStatus.CANCELED.equals(status) || DebtorsInvoiceStatus.DISTRIBUTION.equals(status)) {
                                logMessage(Level.SEVERE, ServerDebtorsMessageEnum.ALREADY_CANCELED, userData);
                                return false;
                            }
                        }
                    }
                }
            }
        }

        String employeeId = employeeBean.findEmployee(userData.getUserName(), userData);

        if (isBlank(employeeId)) {
            logMessage(Level.SEVERE, ServerDebtorsMessageEnum.NO_EMPLOYEE, userData);
            return false;
        } else {
            if (!approvalGroupEmployeesBean.isEmployeeInGroup(creditNoteMaster.getApprovalGroup(), employeeId, userData)) {
                logMessage(Level.SEVERE, ServerDebtorsMessageEnum.APPROVAL_DISALLOWED, userData);
                return false;
            } else {
                //Employee is in approval group.
                DebtorsCreditNoteApprovalGroups approvalGroup = approvalGroupsBean.getApprovalGroup(creditNoteMaster.getApprovalGroup(), userData);

                if (!isBlank(approvalGroup.getHigherLevelApprovalGroupId())) {
                    creditNoteMaster.setApprovalGroup(approvalGroup.getHigherLevelApprovalGroupId());
                    logMessage(Level.WARNING, ServerDebtorsMessageEnum.APP_HIGHER_LEVEL, userData);
                } else {
                    creditNoteMaster.setStatus(DebtorsInvoiceStatus.APPROVED.toString());
                    creditNoteMaster.setApprovedBy(userData.getUserName());
                    Date now = Functions.nowDate();
                    creditNoteMaster.setApprovedDate(now);
                    creditNoteMaster.setApprovedTime(now);
                    if (creditNoteMaster.getInvoiceDate().compareTo(now) < 0) {
                        //Use now date as invoice date.
                        //This ensures that the approved Credit Note will fall in the current period when being posted.
                        creditNoteMaster.setInvoiceDate(now);
                    }
                }

                update(creditNoteMaster, userData);
            }
        }

        return true;
    }

    /**
     * Returns the total of the lines on the specified Credit Note.
     *
     * @param creditNoteNo Credit Note number to sum on.
     * @param userData User data.
     * @return The total of the lines on the specified Credit Note.
     */
    public BigDecimal getCreditNoteTotal(String creditNoteNo, EMCUserData userData) {
        //Get sum of lines
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCreditNoteLines.class);
        query.addFieldAggregateFunction("totalCost", "SUM");
        query.addAnd("invCNNumber", creditNoteNo);

        BigDecimal total = (BigDecimal) util.executeSingleResultQuery(query, userData);

        return total == null ? new BigDecimal(0) : total;
    }

    /**
     * Returns the total of the lines on the specified Credit Note.
     *
     * @param creditNoteNo Credit Note number to sum on.
     * @param userData User data.
     * @return The total of the lines on the specified Credit Note.
     */
    public BigDecimal getCreditNoteTotalVAT(String creditNoteNo, EMCUserData userData) {
        //Get sum of lines
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCreditNoteLines.class);
        query.addFieldAggregateFunction("vatAmount", "SUM");
        query.addAnd("invCNNumber", creditNoteNo);

        BigDecimal total = (BigDecimal) util.executeSingleResultQuery(query, userData);

        return total == null ? new BigDecimal(0) : total;
    }

    /**
     * Posts the specified Credit Note.
     *
     * @param creditNoteNo Credit Note number of Credit Note to post.
     * @param generateNewSTKNumbers Indicates whether new batch numbers should
     * be generated if the credit note is returning goods into stock.
     * @param userData User data.
     * @return A boolean indicating whether the CreditNote was posted
     * succesfully.
     * @throws EMCEntityBeanException
     */
    public boolean postCreditNote(String CreditNote, boolean generateNewSTKNumbers, EMCUserData userData) throws EMCEntityBeanException {
        DebtorsCreditNoteMaster creditNoteMaster = getCreditNote(CreditNote, userData);
        return postCreditNote(creditNoteMaster, generateNewSTKNumbers, userData);
    }

    /**
     * Posts the specified Credit Note.
     *
     * @param creditNoteNo Credit Note record to post.
     * @param generateNewSTKNumbers Indicates whether new batch numbers should
     * be generated if the credit note is returning goods into stock.
     * @param userData User data.
     * @return A boolean indicating whether the CreditNote was posted
     * succesfully.
     * @throws EMCEntityBeanException
     */
    private boolean postCreditNote(DebtorsCreditNoteMaster creditNote, boolean generateNewSTKNumbers, EMCUserData userData) throws EMCEntityBeanException {
        DebtorsInvoiceStatus status = DebtorsInvoiceStatus.fromString(creditNote.getStatus());

        if (status.equals(DebtorsInvoiceStatus.POSTED)) {
            throw new EMCEntityBeanException("Credit Note already posted.");
        } else if (status.equals(DebtorsInvoiceStatus.CANCELED) || status.equals(DebtorsInvoiceStatus.DISTRIBUTION)) {
            throw new EMCEntityBeanException("Credit Note already cancelled.");
        } else {
            if (status.equals(DebtorsInvoiceStatus.CAPTURED)) {
                throw new EMCEntityBeanException("Credit Note may not be posted before being approved.");
            } else {
                if (isBlank(creditNote.getReturnOption()) && creditNote.isInvoiceStock()) {
                    throw new EMCEntityBeanException(getMessage(ServerDebtorsMessageEnum.NO_STOCK_RET_OPTION, userData, creditNote.getDisplayLabelForField("returnOption", userData)));
                } else {
                    if (!isBlank(creditNote.getReturnOption()) && !creditNote.isInvoiceStock()) {
                        throw new EMCEntityBeanException(getMessage(ServerDebtorsMessageEnum.RET_OPTION_NO_STOCK, userData, creditNote.getDisplayLabelForField("returnOption", userData)));
                    }
                }
            }
        }

        return transactionLogicBean.postDebtorsCreditNote(creditNote, generateNewSTKNumbers, userData);
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);

        DebtorsCreditNoteMaster master = (DebtorsCreditNoteMaster) theRecord;
        DebtorsCreditNoteMaster persisted = (DebtorsCreditNoteMaster) util.findDetachedPersisted(master, userData);

        if (ret) {
            //These fields can be changed on posted Invoices and Sales Invoices
            List<String> editableFields = new ArrayList<String>();
            editableFields.add("customerOrderNumber");
            editableFields.add("reference");
            editableFields.add("comments");
            if (DebtorsInvoiceStatus.fromString(master.getStatus()) == DebtorsInvoiceStatus.POSTED && !editableFields.contains(fieldNameToValidate)) {
                logMessage(Level.SEVERE, ServerDebtorsMessageEnum.ALREADY_POSTED, userData);
                return false;
            } else if ((DebtorsInvoiceStatus.fromString(master.getStatus()) == DebtorsInvoiceStatus.CANCELED
                    || DebtorsInvoiceStatus.fromString(master.getStatus()) == DebtorsInvoiceStatus.CANCELED)
                    && !editableFields.contains(fieldNameToValidate)) {
                logMessage(Level.SEVERE, ServerDebtorsMessageEnum.ALREADY_CANCELED, userData);
                return false;
            }

            editableFields.add("reasonCode");
            editableFields.add("approvalGroup");
            editableFields.add("authorizationNo");
            editableFields.add("claimNo");
            editableFields.add("returnOption");
            editableFields.add("invoiceDate");
            editableFields.add("comments");

            //All super class fields are editable
            editableFields.add("companyId");
            editableFields.add("createdBy");
            editableFields.add("createdDate");
            editableFields.add("modifiedBy");
            editableFields.add("modifiedTime");
            editableFields.add("modifiedDate");
            editableFields.add("closed");
            editableFields.add("version");
            editableFields.add("createdTime");
            editableFields.add("hasAttachment");

            if (!isBlank(master.getRefInvoiceNo())) {
                if (!editableFields.contains(fieldNameToValidate)) {
                    logMessage(Level.SEVERE, ServerDebtorsMessageEnum.ONLY_QTY_CHANGE, userData);
                    return false;
                }
            }

            if (fieldNameToValidate.equals("salesRep") && master.getRecordID() != 0 && !util.checkObjectsEqual(master.getSalesRep(), persisted.getSalesRep()) && !isBlank(master.getSalesRep())) {
                List<DebtorsCustomerInvoiceLines> lines = invoiceLinesBean.getInvoiceLines(master.getInvCNNumber(), userData);

                //Only validate rep when commission is applicable
                if (master.isCommissionApplicable()) {
                    for (DebtorsCustomerInvoiceLines line : lines) {
                        if (!commissionBean.validateSalesRep(master.getSalesRep(), master.getCustomerNo(), null, line.getItemId(), null, userData)) {
                            return false;
                        }
                    }
                }
            }

            if (fieldNameToValidate.equals("returnOption")) {
                if (!master.isInvoiceStock() && !isBlank(master.getReturnOption())) {
                    logMessage(Level.SEVERE, ServerDebtorsMessageEnum.RET_OPTION_NO_STOCK, userData, master.getDisplayLabelForField("returnOption", userData));
                    return false;
                }
            }

            if (fieldNameToValidate.equals("approvalGroup")) {
                if (isBlank(master.getReasonCode())) {
                    logMessage(Level.SEVERE, ServerDebtorsMessageEnum.APP_GRP_NO_CHANGE, userData);
                    return false;
                } else {
                    String employeeId = employeeBean.findEmployee(userData.getUserName(), userData);

                    if (isBlank(employeeId)) {
                        logMessage(Level.SEVERE, ServerDebtorsMessageEnum.NO_EMPLOYEE, userData);
                        return false;
                    } else {
                        if (approvalGroupEmployeesBean.isEmployeeInGroup(master.getApprovalGroup(), employeeId, userData)) {
                            logMessage(Level.SEVERE, ServerDebtorsMessageEnum.APPROVAL_DISALLOWED_EMP, userData);
                            return false;
                        }
                    }
                }
            } else {
                if (fieldNameToValidate.equals("reasonCode")) {
                    DebtorsCreditNoteReasons reason = reasonsBean.getReason(master.getReasonCode(), userData);
                    //If super class validation succeeded, reason should always exist
                    if (reason != null) {
                        master.setApprovalGroup(reason.getApprovalGroupId());
                        return master;
                    }
                } else {
                    if (fieldNameToValidate.equals("invoiceStock")) {
                        //Check whether lines exist
                        if (!creditNoteLinesBean.getCreditNoteLines(master.getInvCNNumber(), userData).isEmpty()) {
                            logMessage(Level.SEVERE, ServerDebtorsMessageEnum.CN_LINES_EXIST, userData, master.getDisplayLabelForField(fieldNameToValidate, userData));
                            return false;
                        }

                        if (!master.isInvoiceStock()) {
                            master.setReturnOption(null);
                        }
                    } else {
                        if (fieldNameToValidate.equals("customerNo")) {
                            populateCustomerDetails(master, true, true, userData);

                            populateInvoiceCustomerDetails(master, true, userData);

                            return master;
                        }
                    }
                }
            }
        }
        return ret;
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        DebtorsCreditNoteMaster master = (DebtorsCreditNoteMaster) iobject;

        populateCustomerDetails(master, false, false, userData);
        populateInvoiceCustomerDetails(master, false, userData);
        master = populateBillingCurrency(master, userData);
        return super.insert(iobject, userData);
    }

    private DebtorsCreditNoteMaster populateBillingCurrency(DebtorsCreditNoteMaster master, EMCUserData userData) {
        EMCQuery compQuery = new EMCQuery(enumQueryTypes.SELECT, BaseCompanyTable.class);
        compQuery.addAnd("companyId", userData.getCompanyId());
        BaseCompanyTable comp = (BaseCompanyTable) util.executeSingleResultQuery(compQuery, userData);
        if (comp != null) {
            master.setBillingCurrency(comp.getCurrency());
        }
        return master;
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        DebtorsCreditNoteMaster master = (DebtorsCreditNoteMaster) uobject;
        DebtorsCreditNoteMaster persisted = (DebtorsCreditNoteMaster) util.findDetachedPersisted(master, userData);

        if (!util.checkObjectsEqual(master.getCustomerNo(), persisted.getCustomerNo())) {
            populateCustomerDetails(master, true, false, userData);
        }

        if (!util.checkObjectsEqual(master.getInvoiceToCustNo(), persisted.getInvoiceToCustNo())) {
            populateInvoiceCustomerDetails(master, true, userData);
        }

        //Get this before persisting master record, otherwise check on lines bean will not compare the line VAT code with the correct VAT code on the unpersisted master.
        boolean vatCodeChanged = !util.checkObjectsEqual(persisted.getVatCode(), master.getVatCode());

        super.update(uobject, userData);

        //If VAT code changed, VAT should be recalculated and set on lines.  Only calculate this on
        if (vatCodeChanged) {
            List<DebtorsCreditNoteLines> lines = creditNoteLinesBean.getCreditNoteLines(master.getInvCNNumber(), userData);
            for (DebtorsCreditNoteLines line : lines) {
                line.setVatCode(master.getVatCode());

                creditNoteLinesBean.update(line, userData);
            }
        }

        return master;
    }

    /**
     * Populates customer details on the specified DebtorsCustomerInvoiceMaster
     * instance.
     *
     * @param master DebtorsCreditNoteMaster instance to populate.
     * @param overwrite Specifies whether existing values should be overwritten.
     * @param setInvoiceToCustomer Indicates whether the code should set the
     * invoice to customer on the specified Credit Note to the invoice to
     * customer on the Item Master. If an invoice to customer has already been
     * set on the Credit Note, the overwrite parameter will be used to determine
     * whether it should be overwritten.
     * @param userData User data.
     */
    private void populateCustomerDetails(DebtorsCreditNoteMaster master, boolean overwrite, boolean setInvoiceTo, EMCUserData userData) {
        if (isBlank(master.getCustomerNo())) {
            return;
        }

        SOPCustomers customer = customerBean.findCustomer(master.getCustomerNo(), userData);

        if (isBlank(master.getCustomerTradingAs()) || overwrite) {
            master.setCustomerTradingAs(customer.getTrandingAs());
        }

        if (isBlank(master.getSalesArea()) || overwrite) {
            master.setSalesArea(customer.getSalesArea());
        }

        if (isBlank(master.getSalesGroup()) || overwrite) {
            master.setSalesGroup(customer.getSalesGroup());
        }

        if (isBlank(master.getSalesRegion()) || overwrite) {
            master.setSalesRegion(customer.getSalesRegion());
        }

        if (isBlank(master.getDeliveryAddress1()) || overwrite) {
            master.setDeliveryAddress1(customer.getPhysicalAddresLine1());
        }

        if (isBlank(master.getDeliveryAddress2()) || overwrite) {
            master.setDeliveryAddress2(customer.getPhysicalAddresLine2());
        }

        if (isBlank(master.getDeliveryAddress3()) || overwrite) {
            master.setDeliveryAddress3(customer.getPhysicalAddresLine3());
        }

        if (isBlank(master.getDeliveryAddress4()) || overwrite) {
            master.setDeliveryAddress4(customer.getPhysicalAddresLine4());
        }

        if (isBlank(master.getDeliveryAddress5()) || overwrite) {
            master.setDeliveryAddress5(customer.getPhysicalAddresLine5());
        }

        if (isBlank(master.getDeliveryAddressPostalCode()) || overwrite) {
            master.setDeliveryAddressPostalCode(customer.getPhysicalPostalCode());
        }

        if (isBlank(master.getOrderWarehouse()) || overwrite) {
            master.setOrderWarehouse(customer.getOrderWarehouse());
        }

        if (!isBlank(master.getSettlementDiscCode()) || overwrite) {
            master.setSettlementDiscCode(customer.getSettlementDiscount());
        }

        if (setInvoiceTo) {
            if (isBlank(master.getInvoiceToCustNo()) || overwrite) {
                master.setInvoiceToCustNo(customer.getInvoiceToCustomer());
            }
        }
    }

    /**
     * Populates invoice customer details on the specified
     * DebtorsCustomerInvoiceMaster instance.
     *
     * @param master DebtorsCreditNoteMaster instance to populate.
     * @param overwrite Specifies whether existing values should be overwritten.
     * @param userData User data.
     */
    private void populateInvoiceCustomerDetails(DebtorsCreditNoteMaster master, boolean overwrite, EMCUserData userData) {
        if (isBlank(master.getInvoiceToCustNo())) {
            return;
        }

        SOPCustomers customer = customerBean.findCustomer(master.getInvoiceToCustNo(), userData);

        if (isBlank(master.getInvoiceToCustomerName()) || overwrite) {
            master.setInvoiceToCustomerName(customer.getCustomerName());
        }

        if (isBlank(master.getInvoiceToCustomerExportCurrency()) || overwrite) {
            master.setInvoiceToCustomerExportCurrency(customer.getExportCurrency());
        }

        if (isBlank(master.getInvoiceToCustomerCountryOfDestination()) || overwrite) {
            master.setInvoiceToCustomerCountryOfDestination(customer.getCountryOfDestination());
        }

        if (isBlank(master.getInvoiceToCustomerDeliveryInstructions()) || overwrite) {
            master.setInvoiceToCustomerDeliveryInstructions(customer.getDeliveryInstructions());
        }

        if (isBlank(master.getInvoiceToCustomerVatRegistration()) || overwrite) {
            master.setInvoiceToCustomerVatRegistration(customer.getVatRegistration());
        }

        if (isBlank(master.getInvoiceAddress1()) || overwrite) {
            master.setInvoiceAddress1(customer.getPostalAddresLine1());
        }

        if (isBlank(master.getInvoiceAddress2()) || overwrite) {
            master.setInvoiceAddress2(customer.getPostalAddresLine2());
        }

        if (isBlank(master.getInvoiceAddress3()) || overwrite) {
            master.setInvoiceAddress3(customer.getPostalAddresLine3());
        }

        if (isBlank(master.getInvoiceAddress4()) || overwrite) {
            master.setInvoiceAddress4(customer.getPostalAddresLine4());
        }

        if (isBlank(master.getInvoiceAddress5()) || overwrite) {
            master.setInvoiceAddress5(customer.getPostalAddresLine5());
        }

        if (isBlank(master.getInvoiceAddressPostalCode()) || overwrite) {
            master.setInvoiceAddressPostalCode(customer.getPostalPostalCode());
        }

        if (isBlank(master.getVatNo()) || overwrite) {
            master.setVatNo(customer.getVatRegistration());
        }

        if (isBlank(master.getVatCode()) || overwrite) {
            master.setVatCode(customer.getVatCode());
        }
    }

    /**
     * This method posts all approved Credit Notes.
     *
     * @param userData User data.
     * @return A list containing the numbers and Return Options of all posted
     * Credit Notes.
     */
    public List<String[]> postAllApproved(EMCUserData userData) {
        boolean success = true;

        logMessage(Level.INFO, ServerDebtorsMessageEnum.STARTING_CN_POSTING, "post", userData);

        List<String[]> postedCreditNotes = new ArrayList<String[]>();

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCreditNoteMaster.class);
        query.addAnd("status", DebtorsInvoiceStatus.APPROVED.toString());

        List<DebtorsCreditNoteMaster> creditNotes = (List<DebtorsCreditNoteMaster>) util.executeGeneralSelectQuery(query, userData);

        for (DebtorsCreditNoteMaster creditNoteMaster : creditNotes) {
            try {
                success = success && this.postCreditNote(creditNoteMaster, false, userData);
                postedCreditNotes.add(new String[]{creditNoteMaster.getInvCNNumber(), creditNoteMaster.getReturnOption()});
            } catch (EMCEntityBeanException ex) {
                success = false;
                logMessage(Level.SEVERE, ServerDebtorsMessageEnum.FAILED_TO_POST_CN, "post", userData, creditNoteMaster.getInvCNNumber(), ex.getMessage());
                continue;
            }
        }

        if (!success) {
            logMessage(Level.WARNING, ServerDebtorsMessageEnum.NOT_ALL_CREDIT_NOTES_POSTED, "post", userData);
        }

        logMessage(Level.INFO, ServerDebtorsMessageEnum.COMPLETED_CN_POSTING, "/post", userData);
        return postedCreditNotes;
    }

    @Override
    public boolean doDeleteValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doDeleteValidation(vobject, userData);

        if (ret) {
            DebtorsCreditNoteMaster master = (DebtorsCreditNoteMaster) vobject;

            if (master != null && DebtorsInvoiceStatus.fromString(master.getStatus()) == DebtorsInvoiceStatus.POSTED) {
                logMessage(Level.SEVERE, ServerDebtorsMessageEnum.POSTED_CR_NO_DELETE, userData);
                return false;
            } else if (master != null
                    && (DebtorsInvoiceStatus.fromString(master.getStatus()) == DebtorsInvoiceStatus.CANCELED
                    || DebtorsInvoiceStatus.fromString(master.getStatus()) == DebtorsInvoiceStatus.DISTRIBUTION)) {
                logMessage(Level.SEVERE, ServerDebtorsMessageEnum.CANCELED_CR_NO_DELETE, userData);
                return false;
            }
        }

        return ret;
    }

    /**
     * Returns totals for the specified Credit Note.
     *
     * @param creditNoteNumber Credit Note to select on.
     * @param userData User data.
     * @return Totals for the specified Credit Note.
     */
    @Override
    public DebtorsInvCNTotalsHelper getCreditNoteTotalsHelper(String creditNoteNumber, EMCUserData userData) {
        DebtorsInvCNTotalsHelper helper = new DebtorsInvCNTotalsHelper();

        helper.setDiscountTotal(getDiscountTotal(creditNoteNumber, userData));
        helper.setSalesTotal(getCreditNoteTotal(creditNoteNumber, userData));
        helper.setVatTotal(getTotalVAT(creditNoteNumber, userData));
        helper.setInvoiceTotal(helper.getSalesTotal().add(helper.getVatTotal()));

        return helper;
    }

    /**
     * Gets total discount on an Credit Note.
     *
     * @param creditNoteNo Credit Note number.
     * @param userData User data.
     * @return Total discount on the specified Credit Note.
     */
    public BigDecimal getDiscountTotal(String creditNoteNo, EMCUserData userData) {
        //Get sum of line discounts
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCreditNoteLines.class);
        query.addField("quantity");
        query.addField("unitPrice");
        query.addField("discountPercentage");
        query.addAnd("invCNNumber", creditNoteNo);

        BigDecimal totalDisc = BigDecimal.ZERO;

        List<Object[]> values = (List<Object[]>) util.executeGeneralSelectQuery(query, userData);

        for (Object[] value : values) {
            BigDecimal quantity = (BigDecimal) value[0];
            BigDecimal unitPrice = (BigDecimal) value[1];
            BigDecimal discountPercentage = (BigDecimal) value[2];

            BigDecimal totalBeforeDisc = ((unitPrice.multiply(quantity))).multiply(discountPercentage.divide(new BigDecimal(100)));
            totalDisc = totalDisc.add(totalBeforeDisc);
        }

        return totalDisc;
    }

    /**
     * Returns the total VAT of the lines on the specified Credit Note.
     *
     * @param creditNoteNo Credit Note number to sum on.
     * @param userData User data.
     * @return The total of the lines on the specified Invoice.
     */
    private BigDecimal getTotalVAT(String creditNoteNo, EMCUserData userData) {
        //Get sum of lines
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCreditNoteLines.class);
        query.addFieldAggregateFunction("vatAmount", "SUM");
        query.addAnd("invCNNumber", creditNoteNo);

        BigDecimal total = (BigDecimal) util.executeSingleResultQuery(query, userData);

        return total == null ? new BigDecimal(0) : total;
    }

    /**
     * Validates the sales rep field on the specified invoice.
     *
     * @param invoiceMaster Invoice to validate.
     * @param userData User data.
     * @return A boolean indicating whether the sales rep field on the specified
     * invoice has a valid value.
     */
    private boolean validateRep(DebtorsCreditNoteMaster creditNoteMaster, EMCUserData userData) {
        //Only validate rep when commission is applicable
        if (creditNoteMaster.isCommissionApplicable()) {
            return commissionBean.validateSalesRep(creditNoteMaster.getSalesRep(), creditNoteMaster.getCustomerNo(), null, null, null, userData);
        } else {
            return true;
        }
    }

    @Override
    public boolean cancelCreditNote(String invCNNumber, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCreditNoteMaster.class);
        query.addAnd("invCNNumber", invCNNumber);
        DebtorsCreditNoteMaster master = (DebtorsCreditNoteMaster) util.executeSingleResultQuery(query, userData);

        if (master == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "Could not find the credit note " + invCNNumber, userData);
            return false;
        }

        switch (DebtorsInvoiceStatus.fromString(master.getStatus())) {
            case CANCELED:
            case DISTRIBUTION:
                Logger.getLogger("emc").log(Level.SEVERE, "The selected credit note has already been cancelled.", userData);
                return false;
            case POSTED:
                Logger.getLogger("emc").log(Level.SEVERE, "The selected credit note has already been posted.", userData);
                return false;
            case APPROVED:
            case HELD:
            case CAPTURED:
                if (master.getInvCNType().equals(DebtorsInvoiceCreditNoteType.SALES_CREDIT_NOTE.toString())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Can not cancel credit notes of type " + DebtorsInvoiceCreditNoteType.SALES_CREDIT_NOTE.toString() + ".", userData);
                    return false;
                }
                if (master.isInvoiceStock()) {
                    query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCreditNoteLines.class);
                    query.addAnd("invCNNumber", master.getInvCNNumber());
                    List<DebtorsCreditNoteLines> linesList = util.executeGeneralSelectQuery(query, userData);
                    for (DebtorsCreditNoteLines line : linesList) {
                        creditNoteLinesBean.cancelCreditNoteLine(line, userData);
                    }
                }
                master.setStatus(DebtorsInvoiceStatus.CANCELED.toString());
                update(master, userData);
                break;
        }
        return true;
    }
}
