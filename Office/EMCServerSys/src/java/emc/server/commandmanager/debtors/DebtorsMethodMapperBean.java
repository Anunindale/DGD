/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.server.commandmanager.debtors;

import emc.bus.debtors.allocationimport.DebtorsAllocationImportFailLogLocal;
import emc.bus.debtors.allocationimport.DebtorsAllocationImportLocal;
import emc.bus.debtors.allocationimport.DebtorsAllocationImportSetupLinesLocal;
import emc.bus.debtors.allocationimport.DebtorsAllocationImportSetupMasterLocal;
import emc.bus.debtors.basket.DebtorsBasketLinesLocal;
import emc.bus.debtors.basket.DebtorsBasketMasterLocal;
import emc.bus.debtors.closedreason.DebtorsClosedReasonLocal;
import emc.bus.debtors.courier.DebtorsCourierLocal;
import emc.bus.debtors.creditcontroller.DebtorsCreditControllerLocal;
import emc.bus.debtors.creditheld.DebtorsCreditHeldMasterLocal;
import emc.bus.debtors.creditinsurer.DebtorsCreditInsurerLocal;
import emc.bus.debtors.creditnoteapprovalgroupemployeess.DebtorsCreditNoteApprovalGroupEmployeesLocal;
import emc.bus.debtors.creditnoteapprovalgroups.DebtorsCreditNoteApprovalGroupsLocal;
import emc.bus.debtors.creditnotereasons.DebtorsCreditNoteReasonsLocal;
import emc.bus.debtors.creditnoteregister.DebtorsCreditNoteRegisterLocal;
import emc.bus.debtors.creditnotes.DebtorsCreditNoteLinesLocal;
import emc.bus.debtors.creditnotes.DebtorsCreditNoteMasterLocal;
import emc.bus.debtors.creditrating.DebtorsCreditRatingLocal;
import emc.bus.debtors.creditstopreason.DebtorsCreditStopReasonLocal;
import emc.bus.debtors.customerinvoice.DebtorsCustomerInvoiceLinesLocal;
import emc.bus.debtors.customerinvoice.DebtorsCustomerInvoiceMasterLocal;
import emc.bus.debtors.customertransactionsummary.DebtorsCustomerTransactionsSummaryLocal;
import emc.bus.debtors.datasource.basket.DebtorsBasketLinesDSLocal;
import emc.bus.debtors.datasource.creditheldmaster.DebtorsCreditHeldMasterDSLocal;
import emc.bus.debtors.datasource.creditnoteapprovalgroupemployees.DebtorsCreditNoteApprovalGroupEmployeesDSLocal;
import emc.bus.debtors.datasource.creditnotes.DebtorsCreditNoteLinesDSLocal;
import emc.bus.debtors.datasource.creditnotes.DebtorsCreditNoteMasterDSLocal;
import emc.bus.debtors.datasource.customerinvoice.DebtorsCustomerInvoiceLinesDSLocal;
import emc.bus.debtors.datasource.customerinvoice.DebtorsCustomerInvoiceMasterDSLocal;
import emc.bus.debtors.datasource.parameters.DebtorsParametersDSLocal;
import emc.bus.debtors.datasource.transactionsettlementhistory.DebtorsTransactionSettlementHistoryDSLocal;
import emc.bus.debtors.deliverycharge.DebtorsDeliveryChargeLocal;
import emc.bus.debtors.journals.DebtorsJournalLinesLocal;
import emc.bus.debtors.journals.DebtorsJournalMasterLocal;
import emc.bus.debtors.logic.creditcheck.DebtorsCreditCheckLogicLocal;
import emc.bus.debtors.logic.customerbalance.DebtorsCustomerBalanceLocal;
import emc.bus.debtors.logic.edconstatement.DebtorsEdconStatementLocal;
import emc.bus.debtors.marketinggroup.DebtorsMarketingGroupLocal;
import emc.bus.debtors.parameters.DebtorsParametersLocal;
import emc.bus.debtors.postdatedpayment.DebtorsPostDatedPaymentLocal;
import emc.bus.debtors.rebatecodes.DebtorsRebateCodesLocal;
import emc.bus.debtors.royaltygroups.DebtorsRoyaltyGroupsLocal;
import emc.bus.debtors.royaltysetup.DebtorsRoyaltySetupLocal;
import emc.bus.debtors.salesarea.DebtorsSalesAreaLocal;
import emc.bus.debtors.salesgroup.DebtorsSalesGroupLocal;
import emc.bus.debtors.salesregion.DebtorsSalesRegionLocal;
import emc.bus.debtors.transactions.DebtorsTransactionsLocal;
import emc.bus.debtors.transactionsettlement.DebtorsSettlementDiscHistoryLocal;
import emc.bus.debtors.transactionsettlement.DebtorsTransactionSettlementHistoryLocal;
import emc.bus.debtors.transactionsettlement.DebtorsTransactionSettlementLocal;
import emc.commands.EMCCommands;
import emc.entity.base.journals.superclass.JournalMasterSuperClass;
import emc.entity.debtors.DebtorsBasketLines;
import emc.entity.debtors.DebtorsBasketMaster;
import emc.entity.debtors.DebtorsClosedReason;
import emc.entity.debtors.DebtorsCourier;
import emc.entity.debtors.DebtorsCreditController;
import emc.entity.debtors.DebtorsCreditInsurer;
import emc.entity.debtors.DebtorsCreditNoteApprovalGroupEmployees;
import emc.entity.debtors.DebtorsCreditNoteApprovalGroups;
import emc.entity.debtors.DebtorsCreditNoteLines;
import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.entity.debtors.DebtorsCreditNoteReasons;
import emc.entity.debtors.DebtorsCreditRating;
import emc.entity.debtors.DebtorsCreditStopReason;
import emc.entity.debtors.DebtorsCustomerInvoiceLines;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.entity.debtors.DebtorsCustomerTransactionsSummary;
import emc.entity.debtors.DebtorsDeliveryCharge;
import emc.entity.debtors.DebtorsMarketingGroup;
import emc.entity.debtors.DebtorsParameters;
import emc.entity.debtors.DebtorsPostDatedPayment;
import emc.entity.debtors.DebtorsRebateCodes;
import emc.entity.debtors.DebtorsRoyaltyGroups;
import emc.entity.debtors.DebtorsRoyaltySetup;
import emc.entity.debtors.DebtorsSalesArea;
import emc.entity.debtors.DebtorsSalesGroup;
import emc.entity.debtors.DebtorsSalesRegion;
import emc.entity.debtors.DebtorsTransactions;
import emc.entity.debtors.allocationimport.DebtorsAllocationImport;
import emc.entity.debtors.allocationimport.DebtorsAllocationImportFailLog;
import emc.entity.debtors.allocationimport.DebtorsAllocationImportSetupLines;
import emc.entity.debtors.allocationimport.DebtorsAllocationImportSetupMaster;
import emc.entity.debtors.creditheld.DebtorsCreditHeldMaster;
import emc.entity.debtors.creditnoteregister.DebtorsCreditNoteRegister;
import emc.entity.debtors.datasource.DebtorsBasketLinesDS;
import emc.entity.debtors.datasource.DebtorsCreditHeldMasterDS;
import emc.entity.debtors.datasource.DebtorsCreditNoteApprovalGroupEmployeesDS;
import emc.entity.debtors.datasource.DebtorsCreditNoteLinesDS;
import emc.entity.debtors.datasource.DebtorsCreditNoteMasterDS;
import emc.entity.debtors.datasource.DebtorsCustomerInvoiceLinesDS;
import emc.entity.debtors.datasource.DebtorsCustomerInvoiceMasterDS;
import emc.entity.debtors.datasource.DebtorsParametersDS;
import emc.entity.debtors.datasource.DebtorsTransactionSettlementHistoryDS;
import emc.entity.debtors.journals.DebtorsJournalLines;
import emc.entity.debtors.journals.DebtorsJournalMaster;
import emc.entity.debtors.transactionsettlement.DebtorsSettlementDiscHistory;
import emc.entity.debtors.transactionsettlement.DebtorsTransactionSettlement;
import emc.entity.sop.SOPCustomers;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCUserData;
import emc.methods.debtors.ClientDebtorsMethods;
import emc.methods.debtors.ServerDebtorsMethods;
import emc.reports.debtors.ageanalysisbyagent.DebtorsAgeAnalysisByAgentReportLocal;
import emc.reports.debtors.customeragingdetailed.DebtorsCustomerAgingDetailedReportLocal;
import emc.reports.debtors.customeragingsummary.DebtorsCustomerAgingSummaryReportLocal;
import emc.reports.debtors.customerinvoice.DebtorsCustomerInvioceReportLocal;
import emc.reports.debtors.customerstatement.DebtorsCustomerStatementReportLocal;
import emc.reports.debtors.debtorsbalance.DebtorsBalanceReportLocal;
import emc.reports.debtors.debtorscnfinishedgoodslabels.DebtorsCNFinishedGoodsLabelsLocal;
import emc.reports.debtors.invoiceregister.DebtorsInvoiceRegisterReportLocal;
import emc.reports.debtors.invoiceregisterdetail.DebtorsInvoiceRegisterReportDetailLocal;
import emc.reports.debtors.journals.DebtorsJournalsReportLocal;
import emc.reports.debtors.printinstruction.PrintInstructionReportLocal;
import emc.reports.debtors.royalty.DebtorsRoyaltyReportLocal;
import emc.reports.debtors.transactiontotals.DebtorsTransactionTotalsReportLocal;
import emc.reports.debtors.transactiontotals.journaltransactiontotals.DebtorsJournalTransactionTotalsReportLocal;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class DebtorsMethodMapperBean implements DebtorsMethodMapperBeanLocal {

    @EJB
    private DebtorsCreditRatingLocal creditRatingBean;
    @EJB
    private DebtorsCreditStopReasonLocal creditStopReasonBean;
    @EJB
    private DebtorsSalesGroupLocal salesGroupBean;
    @EJB
    private DebtorsSalesRegionLocal salesRegionBean;
    @EJB
    private DebtorsSalesAreaLocal salesAreaBean;
    @EJB
    private DebtorsTransactionsLocal transactionBean;
    @EJB
    private DebtorsCustomerInvoiceMasterLocal customerInvoiceMasterBean;
    @EJB
    private DebtorsCustomerInvoiceLinesLocal customerInvoiceLinesBean;
    @EJB
    private DebtorsCustomerInvoiceLinesDSLocal customerInvoiceLinesDSBean;
    @EJB
    private DebtorsCustomerTransactionsSummaryLocal customerTransSummaryBean;
    @EJB
    private DebtorsCustomerInvoiceMasterDSLocal customerInvoiceMasterDSBean;
    @EJB
    private DebtorsJournalMasterLocal journalMasterBean;
    @EJB
    private DebtorsJournalLinesLocal journalLinesBean;
    @EJB
    private DebtorsPostDatedPaymentLocal postDatedPaymentBean;
    @EJB
    private DebtorsParametersLocal parametersBean;
    @EJB
    private DebtorsTransactionSettlementLocal transSettlementBean;
    @EJB
    private DebtorsCustomerBalanceLocal customerBalanceBean;
    @EJB
    private DebtorsSettlementDiscHistoryLocal settlementDiscHistoryBean;
    @EJB
    private DebtorsCustomerAgingSummaryReportLocal customerAgingSummaryReportBean;
    @EJB
    private DebtorsMarketingGroupLocal marketingGroupBean;
    @EJB
    private DebtorsDeliveryChargeLocal deliveryChargeBean;
    @EJB
    private DebtorsClosedReasonLocal closedReasonBean;
    @EJB
    private DebtorsCreditInsurerLocal creditInsurerBean;
    @EJB
    private DebtorsCreditControllerLocal creditControllerBean;
    @EJB
    private DebtorsCourierLocal courierBean;
    @EJB
    private DebtorsCustomerAgingDetailedReportLocal customerAgingDetailedReportBean;
    @EJB
    private DebtorsCustomerStatementReportLocal customerStatementReportBean;
    @EJB
    private DebtorsCustomerInvioceReportLocal customerInvoiceReportBean;
    @EJB
    private DebtorsCreditNoteMasterLocal creditNoteMasterBean;
    @EJB
    private DebtorsCreditNoteLinesLocal creditNoteLinesBean;
    @EJB
    private DebtorsCreditNoteMasterDSLocal creditNoteMasterDSBean;
    @EJB
    private DebtorsCreditNoteLinesDSLocal creditNoteLinesDSBean;
    @EJB
    private DebtorsCreditNoteReasonsLocal creditNoteReasonsBean;
    @EJB
    private DebtorsCreditNoteApprovalGroupsLocal creditNoteApprovalGroupsBean;
    @EJB
    private DebtorsCreditNoteApprovalGroupEmployeesLocal creditNoteApprovalGroupEmployeesBean;
    @EJB
    private DebtorsTransactionTotalsReportLocal transactionTotalsReportBean;
    @EJB
    private DebtorsJournalTransactionTotalsReportLocal journalTransactionTotalsReportBean;
    @EJB
    private DebtorsParametersDSLocal debtorsParametersDSBean;
    @EJB
    private DebtorsTransactionSettlementHistoryDSLocal settlementHistoryDSBean;
    @EJB
    private DebtorsCreditNoteApprovalGroupEmployeesDSLocal creditNoteApprovalGroupEmployeesDSBean;
    @EJB
    private DebtorsTransactionSettlementHistoryLocal transactionSettlementHistoryBean;
    @EJB
    private DebtorsBalanceReportLocal balanceReportBean;
    @EJB
    private DebtorsCreditHeldMasterLocal creditHeldMasterBean;
    @EJB
    private DebtorsJournalsReportLocal journalReportBean;
    @EJB
    private DebtorsCreditHeldMasterDSLocal creditHeldDSBean;
    @EJB
    private DebtorsAllocationImportSetupMasterLocal allocationImportSetupMasterBean;
    @EJB
    private DebtorsAllocationImportSetupLinesLocal allocationImportSetupLinesBean;
    @EJB
    private DebtorsRebateCodesLocal rebateCodesBean;
    @EJB
    private DebtorsEdconStatementLocal edconStatementBean;
    @EJB
    private DebtorsRoyaltyReportLocal royaltyReportBean;
    @EJB
    private DebtorsRoyaltySetupLocal royaltySetupBean;
    @EJB
    private DebtorsAllocationImportLocal allocationImportBean;
    @EJB
    private DebtorsAllocationImportFailLogLocal allocationImportFailLogBean;
    @EJB
    private DebtorsCreditNoteRegisterLocal creditNoteRegisterBean;
    @EJB
    private DebtorsAgeAnalysisByAgentReportLocal ageAnalysisByAgentReportBean;
    @EJB
    private DebtorsInvoiceRegisterReportLocal invoiceRegisterReportBean;
    @EJB
    private DebtorsInvoiceRegisterReportDetailLocal invoiceRegisterDetailReportBean;
    @EJB
    private DebtorsCNFinishedGoodsLabelsLocal cnFinishedGoodsLabelsBean;
    @EJB
    private DebtorsRoyaltyGroupsLocal royaltyGroupsBean;
    @EJB
    private DebtorsBasketMasterLocal basketMasterBean;
    @EJB
    private DebtorsBasketLinesLocal basketLinesInterfaceBean;
    @EJB
    private PrintInstructionReportLocal printInstructionReportBean;
    @EJB
    private DebtorsBasketLinesDSLocal basketLinesDSInterfaceBean;
    @EJB
    private DebtorsCreditCheckLogicLocal debtorsCreditCheckBean;

    public DebtorsMethodMapperBean() {
    }

    public List mapMethodDebtors(EMCCommandClass cmd, List<Object> dataList, EMCUserData userData) throws EMCEntityBeanException {
        List<Object> theDataList = new ArrayList();
        EMCCommandClass retCmd = new EMCCommandClass();
        retCmd.setCommandId(EMCCommands.CLIENT_GENERAL_COMMAND.getId());
        retCmd.setModuleNumber(enumEMCModules.DEBTORS.getId());
        retCmd.setMethodId(ClientDebtorsMethods.GENERAL_METHOD.toString());

        switch (ServerDebtorsMethods.fromString(cmd.getMethodId())) {
            //DebtorsCreditRating
            case INSERT_DEBTORSCREDITRATING:
                theDataList.add(creditRatingBean.insert((DebtorsCreditRating) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSCREDITRATING:
                theDataList.add(creditRatingBean.update((DebtorsCreditRating) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSCREDITRATING:
                theDataList.add(creditRatingBean.delete((DebtorsCreditRating) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSCREDITRATING:
                theDataList.add(creditRatingBean.getNumRows(DebtorsCreditRating.class, userData));
                break;
            case GETDATA_DEBTORSCREDITRATING:
                theDataList = (List<Object>) creditRatingBean.getDataInRange(DebtorsCreditRating.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSCREDITRATING:
                theDataList.add(creditRatingBean.validateField(dataList.get(1).toString(), (DebtorsCreditRating) dataList.get(2), userData));
                break;
            //DebtorsCreditStopReason
            case INSERT_DEBTORSCREDITSTOPREASON:
                theDataList.add(creditStopReasonBean.insert((DebtorsCreditStopReason) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSCREDITSTOPREASON:
                theDataList.add(creditStopReasonBean.update((DebtorsCreditStopReason) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSCREDITSTOPREASON:
                theDataList.add(creditStopReasonBean.delete((DebtorsCreditStopReason) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSCREDITSTOPREASON:
                theDataList.add(creditStopReasonBean.getNumRows(DebtorsCreditStopReason.class, userData));
                break;
            case GETDATA_DEBTORSCREDITSTOPREASON:
                theDataList = (List<Object>) creditStopReasonBean.getDataInRange(DebtorsCreditStopReason.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSCREDITSTOPREASON:
                theDataList.add(creditStopReasonBean.validateField(dataList.get(1).toString(), (DebtorsCreditStopReason) dataList.get(2), userData));
                break;
            //DebtorsSalesGroup
            case INSERT_DEBTORSSALESGROUP:
                theDataList.add(salesGroupBean.insert((DebtorsSalesGroup) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSSALESGROUP:
                theDataList.add(salesGroupBean.update((DebtorsSalesGroup) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSSALESGROUP:
                theDataList.add(salesGroupBean.delete((DebtorsSalesGroup) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSSALESGROUP:
                theDataList.add(salesGroupBean.getNumRows(DebtorsSalesGroup.class, userData));
                break;
            case GETDATA_DEBTORSSALESGROUP:
                theDataList = (List<Object>) salesGroupBean.getDataInRange(DebtorsSalesGroup.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSSALESGROUP:
                theDataList.add(salesGroupBean.validateField(dataList.get(1).toString(), (DebtorsSalesGroup) dataList.get(2), userData));
                break;
            //DebtorsSalesRegion
            case INSERT_DEBTORSSALESREGION:
                theDataList.add(salesRegionBean.insert((DebtorsSalesRegion) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSSALESREGION:
                theDataList.add(salesRegionBean.update((DebtorsSalesRegion) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSSALESREGION:
                theDataList.add(salesRegionBean.delete((DebtorsSalesRegion) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSSALESREGION:
                theDataList.add(salesRegionBean.getNumRows(DebtorsSalesRegion.class, userData));
                break;
            case GETDATA_DEBTORSSALESREGION:
                theDataList = (List<Object>) salesRegionBean.getDataInRange(DebtorsSalesRegion.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSSALESREGION:
                theDataList.add(salesRegionBean.validateField(dataList.get(1).toString(), (DebtorsSalesRegion) dataList.get(2), userData));
                break;
            //DebtorsSalesArea
            case INSERT_DEBTORSSALESAREA:
                theDataList.add(salesAreaBean.insert((DebtorsSalesArea) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSSALESAREA:
                theDataList.add(salesAreaBean.update((DebtorsSalesArea) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSSALESAREA:
                theDataList.add(salesAreaBean.delete((DebtorsSalesArea) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSSALESAREA:
                theDataList.add(salesAreaBean.getNumRows(DebtorsSalesArea.class, userData));
                break;
            case GETDATA_DEBTORSSALESAREA:
                theDataList = (List<Object>) salesAreaBean.getDataInRange(DebtorsSalesArea.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSSALESAREA:
                theDataList.add(salesAreaBean.validateField(dataList.get(1).toString(), (DebtorsSalesArea) dataList.get(2), userData));
                break;
            //Debtors Transactions
            case INSERT_DEBTORSTRANSACTIONS:
                theDataList.add(transactionBean.insert((DebtorsTransactions) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSTRANSACTIONS:
                theDataList.add(transactionBean.update((DebtorsTransactions) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSTRANSACTIONS:
                theDataList.add(transactionBean.delete((DebtorsTransactions) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSTRANSACTIONS:
                theDataList.add(transactionBean.getNumRows(DebtorsTransactions.class, userData));
                break;
            case GETDATA_DEBTORSTRANSACTIONS:
                theDataList = (List<Object>) transactionBean.getDataInRange(DebtorsTransactions.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSTRANSACTIONS:
                theDataList.add(transactionBean.validateField(dataList.get(1).toString(), (DebtorsTransactions) dataList.get(2), userData));
                break;
            case CALCULATE_REFERENCE_BALANCE:
                theDataList.add(transactionBean.calculateReferenceBallance(dataList.get(1).toString(), userData));
                break;
            case CALCULATE_TOTAL_BALANCE:
                theDataList.add(transactionBean.calculateTotalBallance(userData));
                break;
            //TODO:  Remove this after running it
            case CALCULATE_DISC_AND_DUE:
                theDataList.add(transactionBean.calculateDatesAndDisc(userData));
                break;
            //Debtors Customer Invoice Master
            case INSERT_DEBTORSCUSTOMERINVOICEMASTER:
                theDataList.add(customerInvoiceMasterBean.insert((DebtorsCustomerInvoiceMaster) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSCUSTOMERINVOICEMASTER:
                theDataList.add(customerInvoiceMasterBean.update((DebtorsCustomerInvoiceMaster) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSCUSTOMERINVOICEMASTER:
                theDataList.add(customerInvoiceMasterBean.delete((DebtorsCustomerInvoiceMaster) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSCUSTOMERINVOICEMASTER:
                theDataList.add(customerInvoiceMasterBean.getNumRows(DebtorsCustomerInvoiceMaster.class, userData));
                break;
            case GETDATA_DEBTORSCUSTOMERINVOICEMASTER:
                theDataList = (List<Object>) customerInvoiceMasterBean.getDataInRange(DebtorsCustomerInvoiceMaster.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSCUSTOMERINVOICEMASTER:
                theDataList.add(customerInvoiceMasterBean.validateField(dataList.get(1).toString(), (DebtorsCustomerInvoiceMaster) dataList.get(2), userData));
                break;
            case POST_DEBTORS_CUSTOMER_INVOICE:
                theDataList.add(customerInvoiceMasterBean.postInvoice((String) dataList.get(1), userData));
                break;
            case GET_INV_CN_TOTALS:
                theDataList.add(creditNoteMasterBean.getCreditNoteTotalsHelper((String) dataList.get(1), userData));
                break;
            case GET_INV_TOTALS:
                theDataList.add(customerInvoiceMasterBean.getInvoiceOnlyTotalHelper((String) dataList.get(1), userData));
                break;
            case CANCEL_CUSTOMER_INVOICE:
                theDataList.add(customerInvoiceMasterBean.cancelInvoice(dataList.get(1).toString(), userData));
                break;
            //Debtors Customer Invoice Lines
            case INSERT_DEBTORSCUSTOMERINVOICELINES:
                theDataList.add(customerInvoiceLinesBean.insert((DebtorsCustomerInvoiceLines) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSCUSTOMERINVOICELINES:
                theDataList.add(customerInvoiceLinesBean.update((DebtorsCustomerInvoiceLines) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSCUSTOMERINVOICELINES:
                theDataList.add(customerInvoiceLinesBean.delete((DebtorsCustomerInvoiceLines) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSCUSTOMERINVOICELINES:
                theDataList.add(customerInvoiceLinesBean.getNumRows(DebtorsCustomerInvoiceLines.class, userData));
                break;
            case GETDATA_DEBTORSCUSTOMERINVOICELINES:
                theDataList = (List<Object>) customerInvoiceLinesBean.getDataInRange(DebtorsCustomerInvoiceLines.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSCUSTOMERINVOICELINES:
                theDataList.add(customerInvoiceLinesBean.validateField(dataList.get(1).toString(), (DebtorsCustomerInvoiceLines) dataList.get(2), userData));
                break;
            //DebtorsCustomerInvoiceLinesDS
            case INSERT_DEBTORSCUSTOMERINVOICELINESDS:
                theDataList.add(customerInvoiceLinesDSBean.insert((DebtorsCustomerInvoiceLinesDS) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSCUSTOMERINVOICELINESDS:
                theDataList.add(customerInvoiceLinesDSBean.update((DebtorsCustomerInvoiceLinesDS) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSCUSTOMERINVOICELINESDS:
                theDataList.add(customerInvoiceLinesDSBean.delete((DebtorsCustomerInvoiceLinesDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSCUSTOMERINVOICELINESDS:
                theDataList.add(customerInvoiceLinesDSBean.getNumRows(DebtorsCustomerInvoiceLinesDS.class, userData));
                break;
            case GETDATA_DEBTORSCUSTOMERINVOICELINESDS:
                theDataList = (List<Object>) customerInvoiceLinesDSBean.getDataInRange(DebtorsCustomerInvoiceLinesDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSCUSTOMERINVOICELINESDS:
                theDataList.add(customerInvoiceLinesDSBean.validateField(dataList.get(1).toString(), (DebtorsCustomerInvoiceLinesDS) dataList.get(2), userData));
                break;
            //Debtors Customer Transactions Summary
            case INSERT_DEBTORSCUSTOMERTRANSACTIONSSUMMARY:
                theDataList.add(customerTransSummaryBean.insert((DebtorsCustomerTransactionsSummary) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSCUSTOMERTRANSACTIONSSUMMARY:
                theDataList.add(customerTransSummaryBean.update((DebtorsCustomerTransactionsSummary) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSCUSTOMERTRANSACTIONSSUMMARY:
                theDataList.add(customerTransSummaryBean.delete((DebtorsCustomerTransactionsSummary) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSCUSTOMERTRANSACTIONSSUMMARY:
                theDataList.add(customerTransSummaryBean.getNumRows(DebtorsCustomerTransactionsSummary.class, userData));
                break;
            case GETDATA_DEBTORSCUSTOMERTRANSACTIONSSUMMARY:
                theDataList = (List<Object>) customerTransSummaryBean.getDataInRange(DebtorsCustomerTransactionsSummary.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSCUSTOMERTRANSACTIONSSUMMARY:
                theDataList.add(customerTransSummaryBean.validateField(dataList.get(1).toString(), (DebtorsCustomerTransactionsSummary) dataList.get(2), userData));
                break;
            //Debtors Customer Invoice Master DS
            case INSERT_DEBTORSCUSTOMERINVOICEMASTERDS:
                theDataList.add(customerInvoiceMasterDSBean.insert((DebtorsCustomerInvoiceMasterDS) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSCUSTOMERINVOICEMASTERDS:
                theDataList.add(customerInvoiceMasterDSBean.update((DebtorsCustomerInvoiceMasterDS) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSCUSTOMERINVOICEMASTERDS:
                theDataList.add(customerInvoiceMasterDSBean.delete((DebtorsCustomerInvoiceMasterDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSCUSTOMERINVOICEMASTERDS:
                theDataList.add(customerInvoiceMasterDSBean.getNumRows(DebtorsCustomerInvoiceMasterDS.class, userData));
                break;
            case GETDATA_DEBTORSCUSTOMERINVOICEMASTERDS:
                theDataList = (List<Object>) customerInvoiceMasterDSBean.getDataInRange(DebtorsCustomerInvoiceMasterDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSCUSTOMERINVOICEMASTERDS:
                theDataList.add(customerInvoiceMasterDSBean.validateField(dataList.get(1).toString(), (DebtorsCustomerInvoiceMasterDS) dataList.get(2), userData));
                break;
            //Debtors Journal Master
            case INSERT_DEBTORSJOURNALMASTER:
                theDataList.add(journalMasterBean.insert((DebtorsJournalMaster) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSJOURNALMASTER:
                theDataList.add(journalMasterBean.update((DebtorsJournalMaster) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSJOURNALMASTER:
                theDataList.add(journalMasterBean.delete((DebtorsJournalMaster) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSJOURNALMASTER:
                theDataList.add(journalMasterBean.getNumRows(DebtorsJournalMaster.class, userData));
                break;
            case GETDATA_DEBTORSJOURNALMASTER:
                theDataList = (List<Object>) journalMasterBean.getDataInRange(DebtorsJournalMaster.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSJOURNALMASTER:
                theDataList.add(journalMasterBean.validateField(dataList.get(1).toString(), (DebtorsJournalMaster) dataList.get(2), userData));
                break;
            case VALIDATE_DEBTORS_JOURNAL:
                theDataList.add(journalMasterBean.validateJournal((JournalMasterSuperClass) dataList.get(1), userData));
                break;
            case APPROVE_DEBTORS_JOURNAL:
                theDataList.add(journalMasterBean.approveJournal((String) dataList.get(1), userData));
                break;
            case UNAPPROVE_DEBTORS_JOURNAL:
                theDataList.add(journalMasterBean.unApproveJournal((String) dataList.get(1), userData));
                break;
            case POST_DEBTORS_JOURNAL:
                theDataList.add(journalMasterBean.attemptPost((JournalMasterSuperClass) dataList.get(1), userData));
                break;
            //Debtors Journal Lines
            case INSERT_DEBTORSJOURNALLINES:
                theDataList.add(journalLinesBean.insert((DebtorsJournalLines) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSJOURNALLINES:
                theDataList.add(journalLinesBean.update((DebtorsJournalLines) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSJOURNALLINES:
                theDataList.add(journalLinesBean.delete((DebtorsJournalLines) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSJOURNALLINES:
                theDataList.add(journalLinesBean.getNumRows(DebtorsJournalLines.class, userData));
                break;
            case GETDATA_DEBTORSJOURNALLINES:
                theDataList = (List<Object>) journalLinesBean.getDataInRange(DebtorsJournalLines.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSJOURNALLINES:
                theDataList.add(journalLinesBean.validateField(dataList.get(1).toString(), (DebtorsJournalLines) dataList.get(2), userData));
                break;
            //Debtors Post Dated Payment
            case INSERT_DEBTORSPOSTDATEDPAYMENT:
                theDataList.add(postDatedPaymentBean.insert((DebtorsPostDatedPayment) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSPOSTDATEDPAYMENT:
                theDataList.add(postDatedPaymentBean.update((DebtorsPostDatedPayment) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSPOSTDATEDPAYMENT:
                theDataList.add(postDatedPaymentBean.delete((DebtorsPostDatedPayment) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSPOSTDATEDPAYMENT:
                theDataList.add(postDatedPaymentBean.getNumRows(DebtorsPostDatedPayment.class, userData));
                break;
            case GETDATA_DEBTORSPOSTDATEDPAYMENT:
                theDataList = (List<Object>) postDatedPaymentBean.getDataInRange(DebtorsPostDatedPayment.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSPOSTDATEDPAYMENT:
                theDataList.add(postDatedPaymentBean.validateField(dataList.get(1).toString(), (DebtorsPostDatedPayment) dataList.get(2), userData));
                break;
            case PROCESS_POST_DATED_PAYMENTS:
                theDataList.add(postDatedPaymentBean.createPaymentJournal((Date) dataList.get(1), (Date) dataList.get(2), (String) dataList.get(3), userData));
                break;
            //Debtors Parameters
            case INSERT_DEBTORSPARAMETERS:
                theDataList.add(parametersBean.insert((DebtorsParameters) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSPARAMETERS:
                theDataList.add(parametersBean.update((DebtorsParameters) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSPARAMETERS:
                theDataList.add(parametersBean.delete((DebtorsParameters) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSPARAMETERS:
                theDataList.add(parametersBean.getNumRows(DebtorsParameters.class, userData));
                break;
            case GETDATA_DEBTORSPARAMETERS:
                theDataList = (List<Object>) parametersBean.getDataInRange(DebtorsParameters.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSPARAMETERS:
                theDataList.add(parametersBean.validateField(dataList.get(1).toString(), (DebtorsParameters) dataList.get(2), userData));
                break;
            case GET_DEBTORS_PARAMETERS:
                theDataList.add(parametersBean.getDebtorsParameters(userData));
                break;
            //Debtors Transaction Settlement
            case INSERT_DEBTORSTRANSACTIONSETTLEMENT:
                theDataList.add(transSettlementBean.insert((DebtorsTransactionSettlement) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSTRANSACTIONSETTLEMENT:
                theDataList.add(transSettlementBean.update((DebtorsTransactionSettlement) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSTRANSACTIONSETTLEMENT:
                theDataList.add(transSettlementBean.delete((DebtorsTransactionSettlement) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSTRANSACTIONSETTLEMENT:
                theDataList.add(transSettlementBean.getNumRows(DebtorsTransactionSettlement.class, userData));
                break;
            case GETDATA_DEBTORSTRANSACTIONSETTLEMENT:
                theDataList = (List<Object>) transSettlementBean.getDataInRange(DebtorsTransactionSettlement.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSTRANSACTIONSETTLEMENT:
                theDataList.add(transSettlementBean.validateField(dataList.get(1).toString(), (DebtorsTransactionSettlement) dataList.get(2), userData));
                break;
            case POPULATE_SETTLEMENT:
                theDataList.add(transSettlementBean.populateSettlement((String) dataList.get(1), userData));
                break;
            case ALLOCATE_SETTLEMENT:
                theDataList.add(transSettlementBean.allocateSettlement((Long) dataList.get(1), (String) dataList.get(2), userData));
                break;
            case CLEAR_SETTLEMENT:
                theDataList.add(transSettlementBean.clearSettlement((Long) dataList.get(1), userData));
                break;
            case GET_CUSTOMER_BALANCE:
                //Get aging for server date
                theDataList.add(customerBalanceBean.getCustomerBalance((String) dataList.get(1), userData));
                break;
            case GET_CUSTOMER_CREDITS_BALANCE:
                //Get aging for server date
                theDataList.add(customerBalanceBean.getCreditsCustomerBalance((String) dataList.get(1), userData));
                break;
            //Debtors Settlement Disc History
            case INSERT_DEBTORSSETTLEMENTDISCHISTORY:
                theDataList.add(settlementDiscHistoryBean.insert((DebtorsSettlementDiscHistory) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSSETTLEMENTDISCHISTORY:
                theDataList.add(settlementDiscHistoryBean.update((DebtorsSettlementDiscHistory) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSSETTLEMENTDISCHISTORY:
                theDataList.add(settlementDiscHistoryBean.delete((DebtorsSettlementDiscHistory) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSSETTLEMENTDISCHISTORY:
                theDataList.add(settlementDiscHistoryBean.getNumRows(DebtorsSettlementDiscHistory.class, userData));
                break;
            case GETDATA_DEBTORSSETTLEMENTDISCHISTORY:
                theDataList = (List<Object>) settlementDiscHistoryBean.getDataInRange(DebtorsSettlementDiscHistory.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSSETTLEMENTDISCHISTORY:
                theDataList.add(settlementDiscHistoryBean.validateField(dataList.get(1).toString(), (DebtorsSettlementDiscHistory) dataList.get(2), userData));
                break;
            //Customer Aging Summary Report
            case PRINT_AGING_SUMMARY:
                theDataList = customerAgingSummaryReportBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            //Debtors Marketing Group
            case INSERT_DEBTORSMARKETINGGROUP:
                theDataList.add(marketingGroupBean.insert((DebtorsMarketingGroup) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSMARKETINGGROUP:
                theDataList.add(marketingGroupBean.update((DebtorsMarketingGroup) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSMARKETINGGROUP:
                theDataList.add(marketingGroupBean.delete((DebtorsMarketingGroup) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSMARKETINGGROUP:
                theDataList.add(marketingGroupBean.getNumRows(DebtorsMarketingGroup.class, userData));
                break;
            case GETDATA_DEBTORSMARKETINGGROUP:
                theDataList = (List<Object>) marketingGroupBean.getDataInRange(DebtorsMarketingGroup.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSMARKETINGGROUP:
                theDataList.add(marketingGroupBean.validateField(dataList.get(1).toString(), (DebtorsMarketingGroup) dataList.get(2), userData));
                break;
            //Debtors Delivery Charge
            case INSERT_DEBTORSDELIVERYCHARGE:
                theDataList.add(deliveryChargeBean.insert((DebtorsDeliveryCharge) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSDELIVERYCHARGE:
                theDataList.add(deliveryChargeBean.update((DebtorsDeliveryCharge) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSDELIVERYCHARGE:
                theDataList.add(deliveryChargeBean.delete((DebtorsDeliveryCharge) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSDELIVERYCHARGE:
                theDataList.add(deliveryChargeBean.getNumRows(DebtorsDeliveryCharge.class, userData));
                break;
            case GETDATA_DEBTORSDELIVERYCHARGE:
                theDataList = (List<Object>) deliveryChargeBean.getDataInRange(DebtorsDeliveryCharge.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSDELIVERYCHARGE:
                theDataList.add(deliveryChargeBean.validateField(dataList.get(1).toString(), (DebtorsDeliveryCharge) dataList.get(2), userData));
                break;
            //Debtors Closed Reason
            case INSERT_DEBTORSCLOSEDREASON:
                theDataList.add(closedReasonBean.insert((DebtorsClosedReason) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSCLOSEDREASON:
                theDataList.add(closedReasonBean.update((DebtorsClosedReason) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSCLOSEDREASON:
                theDataList.add(closedReasonBean.delete((DebtorsClosedReason) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSCLOSEDREASON:
                theDataList.add(closedReasonBean.getNumRows(DebtorsClosedReason.class, userData));
                break;
            case GETDATA_DEBTORSCLOSEDREASON:
                theDataList = (List<Object>) closedReasonBean.getDataInRange(DebtorsClosedReason.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSCLOSEDREASON:
                theDataList.add(closedReasonBean.validateField(dataList.get(1).toString(), (DebtorsClosedReason) dataList.get(2), userData));
                break;
            //Debtors Credit Insurer
            case INSERT_DEBTORSCREDITINSURER:
                theDataList.add(creditInsurerBean.insert((DebtorsCreditInsurer) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSCREDITINSURER:
                theDataList.add(creditInsurerBean.update((DebtorsCreditInsurer) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSCREDITINSURER:
                theDataList.add(creditInsurerBean.delete((DebtorsCreditInsurer) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSCREDITINSURER:
                theDataList.add(creditInsurerBean.getNumRows(DebtorsCreditInsurer.class, userData));
                break;
            case GETDATA_DEBTORSCREDITINSURER:
                theDataList = (List<Object>) creditInsurerBean.getDataInRange(DebtorsCreditInsurer.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSCREDITINSURER:
                theDataList.add(creditInsurerBean.validateField(dataList.get(1).toString(), (DebtorsCreditInsurer) dataList.get(2), userData));
                break;
            //Debtors Credit Controller
            case INSERT_DEBTORSCREDITCONTROLLER:
                theDataList.add(creditControllerBean.insert((DebtorsCreditController) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSCREDITCONTROLLER:
                theDataList.add(creditControllerBean.update((DebtorsCreditController) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSCREDITCONTROLLER:
                theDataList.add(creditControllerBean.delete((DebtorsCreditController) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSCREDITCONTROLLER:
                theDataList.add(creditControllerBean.getNumRows(DebtorsCreditController.class, userData));
                break;
            case GETDATA_DEBTORSCREDITCONTROLLER:
                theDataList = (List<Object>) creditControllerBean.getDataInRange(DebtorsCreditController.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSCREDITCONTROLLER:
                theDataList.add(creditControllerBean.validateField(dataList.get(1).toString(), (DebtorsCreditController) dataList.get(2), userData));
                break;
            //Debtors Courier
            case INSERT_DEBTORSCOURIER:
                theDataList.add(courierBean.insert((DebtorsCourier) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSCOURIER:
                theDataList.add(courierBean.update((DebtorsCourier) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSCOURIER:
                theDataList.add(courierBean.delete((DebtorsCourier) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSCOURIER:
                theDataList.add(courierBean.getNumRows(DebtorsCourier.class, userData));
                break;
            case GETDATA_DEBTORSCOURIER:
                theDataList = (List<Object>) courierBean.getDataInRange(DebtorsCourier.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSCOURIER:
                theDataList.add(courierBean.validateField(dataList.get(1).toString(), (DebtorsCourier) dataList.get(2), userData));
                break;
            //Customer Aging Detailed Report
            case PRINT_AGING_DETAILED:
                theDataList = customerAgingDetailedReportBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            //Customer Statement
            case PRINT_CUSTOMER_STATEMENT:
                theDataList = customerStatementReportBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            //Customer Invoice
            case PRINT_CUSTOMER_INVOICE:
                theDataList = customerInvoiceReportBean.getReportResult(dataList, userData);
                break;
            //Age Analysis by Agent
            case PRINT_AGE_BY_AGENT:
                theDataList = ageAnalysisByAgentReportBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            //Debtors Credit Note Master
            case INSERT_DEBTORSCREDITNOTEMASTER:
                theDataList.add(creditNoteMasterBean.insert((DebtorsCreditNoteMaster) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSCREDITNOTEMASTER:
                theDataList.add(creditNoteMasterBean.update((DebtorsCreditNoteMaster) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSCREDITNOTEMASTER:
                theDataList.add(creditNoteMasterBean.delete((DebtorsCreditNoteMaster) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSCREDITNOTEMASTER:
                theDataList.add(creditNoteMasterBean.getNumRows(DebtorsCreditNoteMaster.class, userData));
                break;
            case GETDATA_DEBTORSCREDITNOTEMASTER:
                theDataList = (List<Object>) creditNoteMasterBean.getDataInRange(DebtorsCreditNoteMaster.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSCREDITNOTEMASTER:
                theDataList.add(creditNoteMasterBean.validateField(dataList.get(1).toString(), (DebtorsCreditNoteMaster) dataList.get(2), userData));
                break;
            case CREATE_CREDIT_NOTE:
                theDataList.add(creditNoteMasterBean.createCreditNote((String) dataList.get(1), (Boolean) dataList.get(2), userData));
                break;
            case APPROVE_CREDIT_NOTE:
                theDataList.add(creditNoteMasterBean.approveCreditNote((String) dataList.get(1), userData));
                break;
            case POST_CREDIT_NOTE:
                theDataList.add(creditNoteMasterBean.postCreditNote((String) dataList.get(1), (Boolean) dataList.get(2), userData));
                break;
            case POST_ALL_APPROVED_CN:
                theDataList.add(creditNoteMasterBean.postAllApproved(userData));
                break;
            case CANCEL_CREDIT_NOTE:
                theDataList.add(creditNoteMasterBean.cancelCreditNote(dataList.get(1).toString(), userData));
                break;
            //Debtors Credit Note Lines
            case INSERT_DEBTORSCREDITNOTELINES:
                theDataList.add(creditNoteLinesBean.insert((DebtorsCreditNoteLines) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSCREDITNOTELINES:
                theDataList.add(creditNoteLinesBean.update((DebtorsCreditNoteLines) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSCREDITNOTELINES:
                theDataList.add(creditNoteLinesBean.delete((DebtorsCreditNoteLines) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSCREDITNOTELINES:
                theDataList.add(creditNoteLinesBean.getNumRows(DebtorsCreditNoteLines.class, userData));
                break;
            case GETDATA_DEBTORSCREDITNOTELINES:
                theDataList = (List<Object>) creditNoteLinesBean.getDataInRange(DebtorsCreditNoteLines.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSCREDITNOTELINES:
                theDataList.add(creditNoteLinesBean.validateField(dataList.get(1).toString(), (DebtorsCreditNoteLines) dataList.get(2), userData));
                break;
            //Debtors Credit Note Master DS
            case INSERT_DEBTORSCREDITNOTEMASTERDS:
                theDataList.add(creditNoteMasterDSBean.insert((DebtorsCreditNoteMasterDS) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSCREDITNOTEMASTERDS:
                theDataList.add(creditNoteMasterDSBean.update((DebtorsCreditNoteMasterDS) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSCREDITNOTEMASTERDS:
                theDataList.add(creditNoteMasterDSBean.delete((DebtorsCreditNoteMasterDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSCREDITNOTEMASTERDS:
                theDataList.add(creditNoteMasterDSBean.getNumRows(DebtorsCreditNoteMasterDS.class, userData));
                break;
            case GETDATA_DEBTORSCREDITNOTEMASTERDS:
                theDataList = (List<Object>) creditNoteMasterDSBean.getDataInRange(DebtorsCreditNoteMasterDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSCREDITNOTEMASTERDS:
                theDataList.add(creditNoteMasterDSBean.validateField(dataList.get(1).toString(), (DebtorsCreditNoteMasterDS) dataList.get(2), userData));
                break;
            //Debtors Credit Note Lines DS
            case INSERT_DEBTORSCREDITNOTELINESDS:
                theDataList.add(creditNoteLinesDSBean.insert((DebtorsCreditNoteLinesDS) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSCREDITNOTELINESDS:
                theDataList.add(creditNoteLinesDSBean.update((DebtorsCreditNoteLinesDS) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSCREDITNOTELINESDS:
                theDataList.add(creditNoteLinesDSBean.delete((DebtorsCreditNoteLinesDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSCREDITNOTELINESDS:
                theDataList.add(creditNoteLinesDSBean.getNumRows(DebtorsCreditNoteLinesDS.class, userData));
                break;
            case GETDATA_DEBTORSCREDITNOTELINESDS:
                theDataList = (List<Object>) creditNoteLinesDSBean.getDataInRange(DebtorsCreditNoteLinesDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSCREDITNOTELINESDS:
                theDataList.add(creditNoteLinesDSBean.validateField(dataList.get(1).toString(), (DebtorsCreditNoteLinesDS) dataList.get(2), userData));
                break;
            //Debtors Credit Note Reasons
            case INSERT_DEBTORSCREDITNOTEREASONS:
                theDataList.add(creditNoteReasonsBean.insert((DebtorsCreditNoteReasons) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSCREDITNOTEREASONS:
                theDataList.add(creditNoteReasonsBean.update((DebtorsCreditNoteReasons) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSCREDITNOTEREASONS:
                theDataList.add(creditNoteReasonsBean.delete((DebtorsCreditNoteReasons) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSCREDITNOTEREASONS:
                theDataList.add(creditNoteReasonsBean.getNumRows(DebtorsCreditNoteReasons.class, userData));
                break;
            case GETDATA_DEBTORSCREDITNOTEREASONS:
                theDataList = (List<Object>) creditNoteReasonsBean.getDataInRange(DebtorsCreditNoteReasons.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSCREDITNOTEREASONS:
                theDataList.add(creditNoteReasonsBean.validateField(dataList.get(1).toString(), (DebtorsCreditNoteReasons) dataList.get(2), userData));
                break;
            //Debtors Credit Note Approval Groups
            case INSERT_DEBTORSCREDITNOTEAPPROVALGROUPS:
                theDataList.add(creditNoteApprovalGroupsBean.insert((DebtorsCreditNoteApprovalGroups) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSCREDITNOTEAPPROVALGROUPS:
                theDataList.add(creditNoteApprovalGroupsBean.update((DebtorsCreditNoteApprovalGroups) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSCREDITNOTEAPPROVALGROUPS:
                theDataList.add(creditNoteApprovalGroupsBean.delete((DebtorsCreditNoteApprovalGroups) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSCREDITNOTEAPPROVALGROUPS:
                theDataList.add(creditNoteApprovalGroupsBean.getNumRows(DebtorsCreditNoteApprovalGroups.class, userData));
                break;
            case GETDATA_DEBTORSCREDITNOTEAPPROVALGROUPS:
                theDataList = (List<Object>) creditNoteApprovalGroupsBean.getDataInRange(DebtorsCreditNoteApprovalGroups.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSCREDITNOTEAPPROVALGROUPS:
                theDataList.add(creditNoteApprovalGroupsBean.validateField(dataList.get(1).toString(), (DebtorsCreditNoteApprovalGroups) dataList.get(2), userData));
                break;
            //Debtors Credit Note Approval Group Employees
            case INSERT_DEBTORSCREDITNOTEAPPROVALGROUPEMPLOYEES:
                theDataList.add(creditNoteApprovalGroupEmployeesBean.insert((DebtorsCreditNoteApprovalGroupEmployees) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSCREDITNOTEAPPROVALGROUPEMPLOYEES:
                theDataList.add(creditNoteApprovalGroupEmployeesBean.update((DebtorsCreditNoteApprovalGroupEmployees) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSCREDITNOTEAPPROVALGROUPEMPLOYEES:
                theDataList.add(creditNoteApprovalGroupEmployeesBean.delete((DebtorsCreditNoteApprovalGroupEmployees) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSCREDITNOTEAPPROVALGROUPEMPLOYEES:
                theDataList.add(creditNoteApprovalGroupEmployeesBean.getNumRows(DebtorsCreditNoteApprovalGroupEmployees.class, userData));
                break;
            case GETDATA_DEBTORSCREDITNOTEAPPROVALGROUPEMPLOYEES:
                theDataList = (List<Object>) creditNoteApprovalGroupEmployeesBean.getDataInRange(DebtorsCreditNoteApprovalGroupEmployees.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSCREDITNOTEAPPROVALGROUPEMPLOYEES:
                theDataList.add(creditNoteApprovalGroupEmployeesBean.validateField(dataList.get(1).toString(), (DebtorsCreditNoteApprovalGroupEmployees) dataList.get(2), userData));
                break;
            //Transaction Totals
            case PRINT_TRANSACTION_TOTALS:
                theDataList = transactionTotalsReportBean.getReportResult(dataList, userData);
                break;
            case PRINT_JOURNAL_TRANSACTION_TOTALS:
                theDataList = journalTransactionTotalsReportBean.getReportResult(dataList, userData);
                break;
            //Debtors Parameters DS
            case INSERT_DEBTORSPARAMETERSDS:
                theDataList.add(debtorsParametersDSBean.insert((DebtorsParametersDS) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSPARAMETERSDS:
                theDataList.add(debtorsParametersDSBean.update((DebtorsParametersDS) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSPARAMETERSDS:
                theDataList.add(debtorsParametersDSBean.delete((DebtorsParametersDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSPARAMETERSDS:
                theDataList.add(debtorsParametersDSBean.getNumRows(DebtorsParametersDS.class, userData));
                break;
            case GETDATA_DEBTORSPARAMETERSDS:
                theDataList = (List<Object>) debtorsParametersDSBean.getDataInRange(DebtorsParametersDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSPARAMETERSDS:
                theDataList.add(debtorsParametersDSBean.validateField(dataList.get(1).toString(), (DebtorsParametersDS) dataList.get(2), userData));
                break;
            //Debtors Transaction Settlement History DS
            case INSERT_DEBTORSTRANSACTIONSETTLEMENTHISTORYDS:
                theDataList.add(settlementHistoryDSBean.insert((DebtorsTransactionSettlementHistoryDS) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSTRANSACTIONSETTLEMENTHISTORYDS:
                theDataList.add(settlementHistoryDSBean.update((DebtorsTransactionSettlementHistoryDS) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSTRANSACTIONSETTLEMENTHISTORYDS:
                theDataList.add(settlementHistoryDSBean.delete((DebtorsTransactionSettlementHistoryDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSTRANSACTIONSETTLEMENTHISTORYDS:
                theDataList.add(settlementHistoryDSBean.getNumRows(DebtorsTransactionSettlementHistoryDS.class, userData));
                break;
            case GETDATA_DEBTORSTRANSACTIONSETTLEMENTHISTORYDS:
                theDataList = (List<Object>) settlementHistoryDSBean.getDataInRange(DebtorsTransactionSettlementHistoryDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSTRANSACTIONSETTLEMENTHISTORYDS:
                theDataList.add(settlementHistoryDSBean.validateField(dataList.get(1).toString(), (DebtorsTransactionSettlementHistoryDS) dataList.get(2), userData));
                break;
            case DEALLOCATE_SETTLEMENT:
                theDataList.add(settlementHistoryDSBean.deallocateSettlement((DebtorsTransactionSettlementHistoryDS) dataList.get(1), userData));
                break;
            case DEALLOCATE_CREDIT:
                theDataList.add(transactionSettlementHistoryBean.deallocateCredit((Long) dataList.get(1), userData));
                break;
            case DEALLOCATE_DEBIT:
                theDataList.add(transactionSettlementHistoryBean.deallocateDebit((Long) dataList.get(1), userData));
                break;
            //Debtors Credit Note Approval Group Employees DS
            case INSERT_DEBTORSCREDITNOTEAPPROVALGROUPEMPLOYEESDS:
                theDataList.add(creditNoteApprovalGroupEmployeesDSBean.insert((DebtorsCreditNoteApprovalGroupEmployeesDS) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSCREDITNOTEAPPROVALGROUPEMPLOYEESDS:
                theDataList.add(creditNoteApprovalGroupEmployeesDSBean.update((DebtorsCreditNoteApprovalGroupEmployeesDS) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSCREDITNOTEAPPROVALGROUPEMPLOYEESDS:
                theDataList.add(creditNoteApprovalGroupEmployeesDSBean.delete((DebtorsCreditNoteApprovalGroupEmployeesDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSCREDITNOTEAPPROVALGROUPEMPLOYEESDS:
                theDataList.add(creditNoteApprovalGroupEmployeesDSBean.getNumRows(DebtorsCreditNoteApprovalGroupEmployeesDS.class, userData));
                break;
            case GETDATA_DEBTORSCREDITNOTEAPPROVALGROUPEMPLOYEESDS:
                theDataList = (List<Object>) creditNoteApprovalGroupEmployeesDSBean.getDataInRange(DebtorsCreditNoteApprovalGroupEmployeesDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSCREDITNOTEAPPROVALGROUPEMPLOYEESDS:
                theDataList.add(creditNoteApprovalGroupEmployeesDSBean.validateField(dataList.get(1).toString(), (DebtorsCreditNoteApprovalGroupEmployeesDS) dataList.get(2), userData));
                break;
            //Debtors Balance Report
            case PRINT_DEBTORS_BALANCE:
                theDataList = balanceReportBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            //Debtors Credit Held Master
            case INSERT_DEBTORSCREDITHELDMASTER:
                theDataList.add(creditHeldMasterBean.insert((DebtorsCreditHeldMaster) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSCREDITHELDMASTER:
                theDataList.add(creditHeldMasterBean.update((DebtorsCreditHeldMaster) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSCREDITHELDMASTER:
                theDataList.add(creditHeldMasterBean.delete((DebtorsCreditHeldMaster) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSCREDITHELDMASTER:
                theDataList.add(creditHeldMasterBean.getNumRows(DebtorsCreditHeldMaster.class, userData));
                break;
            case GETDATA_DEBTORSCREDITHELDMASTER:
                theDataList = (List<Object>) creditHeldMasterBean.getDataInRange(DebtorsCreditHeldMaster.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSCREDITHELDMASTER:
                theDataList.add(creditHeldMasterBean.validateField(dataList.get(1).toString(), (DebtorsCreditHeldMaster) dataList.get(2), userData));
                break;
            case APPROVE_CREDIT_HELD_MASTER:
                theDataList.add(creditHeldMasterBean.approveCreditHeldMaster((String) dataList.get(1), (String) dataList.get(2), userData));
                break;
            case GET_TOTAL_HELD_FOR_CUSTOMER:
                theDataList.add(creditHeldMasterBean.getTotalCreditHeldForCustomer((String) dataList.get(1), userData));
                break;
            //Debtors Journals Report
            case PRINT_DEBTORS_JOURNALS:
                theDataList = journalReportBean.getReportResult(dataList, userData);
                break;
            //Debtors Credit Held Master DS
            case INSERT_DEBTORSCREDITHELDMASTERDS:
                theDataList.add(creditHeldDSBean.insert((DebtorsCreditHeldMasterDS) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSCREDITHELDMASTERDS:
                theDataList.add(creditHeldDSBean.update((DebtorsCreditHeldMasterDS) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSCREDITHELDMASTERDS:
                theDataList.add(creditHeldDSBean.delete((DebtorsCreditHeldMasterDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSCREDITHELDMASTERDS:
                theDataList.add(creditHeldDSBean.getNumRows(DebtorsCreditHeldMasterDS.class, userData));
                break;
            case GETDATA_DEBTORSCREDITHELDMASTERDS:
                theDataList = (List<Object>) creditHeldDSBean.getDataInRange(DebtorsCreditHeldMasterDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSCREDITHELDMASTERDS:
                theDataList.add(creditHeldDSBean.validateField(dataList.get(1).toString(), (DebtorsCreditHeldMasterDS) dataList.get(2), userData));
                break;
            //Debtors Allocation Import Setup Master
            case INSERT_DEBTORSALLOCATIONIMPORTSETUPMASTER:
                theDataList.add(allocationImportSetupMasterBean.insert((DebtorsAllocationImportSetupMaster) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSALLOCATIONIMPORTSETUPMASTER:
                theDataList.add(allocationImportSetupMasterBean.update((DebtorsAllocationImportSetupMaster) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSALLOCATIONIMPORTSETUPMASTER:
                theDataList.add(allocationImportSetupMasterBean.delete((DebtorsAllocationImportSetupMaster) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSALLOCATIONIMPORTSETUPMASTER:
                theDataList.add(allocationImportSetupMasterBean.getNumRows(DebtorsAllocationImportSetupMaster.class, userData));
                break;
            case GETDATA_DEBTORSALLOCATIONIMPORTSETUPMASTER:
                theDataList = (List<Object>) allocationImportSetupMasterBean.getDataInRange(DebtorsAllocationImportSetupMaster.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSALLOCATIONIMPORTSETUPMASTER:
                theDataList.add(allocationImportSetupMasterBean.validateField(dataList.get(1).toString(), (DebtorsAllocationImportSetupMaster) dataList.get(2), userData));
                break;
            //Debtors Allocation Import Setup Lines
            case INSERT_DEBTORSALLOCATIONIMPORTSETUPLINES:
                theDataList.add(allocationImportSetupLinesBean.insert((DebtorsAllocationImportSetupLines) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSALLOCATIONIMPORTSETUPLINES:
                theDataList.add(allocationImportSetupLinesBean.update((DebtorsAllocationImportSetupLines) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSALLOCATIONIMPORTSETUPLINES:
                theDataList.add(allocationImportSetupLinesBean.delete((DebtorsAllocationImportSetupLines) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSALLOCATIONIMPORTSETUPLINES:
                theDataList.add(allocationImportSetupLinesBean.getNumRows(DebtorsAllocationImportSetupLines.class, userData));
                break;
            case GETDATA_DEBTORSALLOCATIONIMPORTSETUPLINES:
                theDataList = (List<Object>) allocationImportSetupLinesBean.getDataInRange(DebtorsAllocationImportSetupLines.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSALLOCATIONIMPORTSETUPLINES:
                theDataList.add(allocationImportSetupLinesBean.validateField(dataList.get(1).toString(), (DebtorsAllocationImportSetupLines) dataList.get(2), userData));
                break;
            //Debtors Rebate Codes
            case INSERT_DEBTORSREBATECODES:
                theDataList.add(rebateCodesBean.insert((DebtorsRebateCodes) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSREBATECODES:
                theDataList.add(rebateCodesBean.update((DebtorsRebateCodes) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSREBATECODES:
                theDataList.add(rebateCodesBean.delete((DebtorsRebateCodes) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSREBATECODES:
                theDataList.add(rebateCodesBean.getNumRows(DebtorsRebateCodes.class, userData));
                break;
            case GETDATA_DEBTORSREBATECODES:
                theDataList = (List<Object>) rebateCodesBean.getDataInRange(DebtorsRebateCodes.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSREBATECODES:
                theDataList.add(rebateCodesBean.validateField(dataList.get(1).toString(), (DebtorsRebateCodes) dataList.get(2), userData));
                break;
            //Edcon Statement
            case CREATE_EDCON_STATEMENT:
                theDataList.add(edconStatementBean.getEdconStatement((String) dataList.get(1), (Date) dataList.get(2), (Date) dataList.get(3), (String) dataList.get(4), userData));
                break;
            //Royalty Report
            case PRINT_ROYALTY_REPORT:
                theDataList = royaltyReportBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            //SOP RoyaltySetup
            case INSERT_DEBTORSROYALTYSETUP:
                theDataList.add(royaltySetupBean.insert((DebtorsRoyaltySetup) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSROYALTYSETUP:
                theDataList.add(royaltySetupBean.update((DebtorsRoyaltySetup) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSROYALTYSETUP:
                theDataList.add(royaltySetupBean.delete((DebtorsRoyaltySetup) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSROYALTYSETUP:
                theDataList.add(royaltySetupBean.getNumRows(DebtorsRoyaltySetup.class, userData));
                break;
            case GETDATA_DEBTORSROYALTYSETUP:
                theDataList = (List<Object>) royaltySetupBean.getDataInRange(DebtorsRoyaltySetup.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSROYALTYSETUP:
                theDataList.add(royaltySetupBean.validateField(dataList.get(1).toString(), (DebtorsRoyaltySetup) dataList.get(2), userData));
                break;
            case GET_ROYALTY_FIELDS:
                theDataList.add(royaltySetupBean.getRoyaltyFields(userData));
                break;
            case CHECK_ROYALTY_SETUP_EXISTS:
                theDataList.add(royaltySetupBean.checkRoyaltySetupExists(userData));
                break;
            //Debtors Allocation Import
            case INSERT_DEBTORSALLOCATIONIMPORT:
                theDataList.add(allocationImportBean.insert((DebtorsAllocationImport) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSALLOCATIONIMPORT:
                theDataList.add(allocationImportBean.update((DebtorsAllocationImport) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSALLOCATIONIMPORT:
                theDataList.add(allocationImportBean.delete((DebtorsAllocationImport) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSALLOCATIONIMPORT:
                theDataList.add(allocationImportBean.getNumRows(DebtorsAllocationImport.class, userData));
                break;
            case GETDATA_DEBTORSALLOCATIONIMPORT:
                theDataList = (List<Object>) allocationImportBean.getDataInRange(DebtorsAllocationImport.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSALLOCATIONIMPORT:
                theDataList.add(allocationImportBean.validateField(dataList.get(1).toString(), (DebtorsAllocationImport) dataList.get(2), userData));
                break;
            case IMPORT_SETTLEMENT:
                theDataList.add(allocationImportBean.importSettlement((DebtorsAllocationImport) dataList.get(1), (List<String>) dataList.get(2), false, userData));
                break;
            case VALIDATE_ALLOCATION_IMPORT:
                theDataList.add(allocationImportBean.validateImport((DebtorsAllocationImport) dataList.get(1), (List<String>) dataList.get(2), userData));
                break;
            //Debtors Allocation Import Fail Log
            case INSERT_DEBTORSALLOCATIONIMPORTFAILLOG:
                theDataList.add(allocationImportFailLogBean.insert((DebtorsAllocationImportFailLog) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSALLOCATIONIMPORTFAILLOG:
                theDataList.add(allocationImportFailLogBean.update((DebtorsAllocationImportFailLog) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSALLOCATIONIMPORTFAILLOG:
                theDataList.add(allocationImportFailLogBean.delete((DebtorsAllocationImportFailLog) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSALLOCATIONIMPORTFAILLOG:
                theDataList.add(allocationImportFailLogBean.getNumRows(DebtorsAllocationImportFailLog.class, userData));
                break;
            case GETDATA_DEBTORSALLOCATIONIMPORTFAILLOG:
                theDataList = (List<Object>) allocationImportFailLogBean.getDataInRange(DebtorsAllocationImportFailLog.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSALLOCATIONIMPORTFAILLOG:
                theDataList.add(allocationImportFailLogBean.validateField(dataList.get(1).toString(), (DebtorsAllocationImportFailLog) dataList.get(2), userData));
                break;
            case CLEAR_FAIL_LOG:
                theDataList.add(allocationImportFailLogBean.clearFailLog((String) dataList.get(1), userData));
                break;
            case IMPORT_LINE_FROM_FAIL_LOG:
                theDataList.add(allocationImportFailLogBean.importFailLogLine((DebtorsAllocationImportFailLog) dataList.get(1), userData));
                break;
            case IMPORT_ALL_FROM_FAIL_LOG:
                theDataList.add(allocationImportFailLogBean.importFailLogLines((String) dataList.get(1), userData));
                break;
            //Debtors Credit Note Register
            case INSERT_DEBTORSCREDITNOTEREGISTER:
                theDataList.add(creditNoteRegisterBean.insert((DebtorsCreditNoteRegister) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSCREDITNOTEREGISTER:
                theDataList.add(creditNoteRegisterBean.update((DebtorsCreditNoteRegister) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSCREDITNOTEREGISTER:
                theDataList.add(creditNoteRegisterBean.delete((DebtorsCreditNoteRegister) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSCREDITNOTEREGISTER:
                theDataList.add(creditNoteRegisterBean.getNumRows(DebtorsCreditNoteRegister.class, userData));
                break;
            case GETDATA_DEBTORSCREDITNOTEREGISTER:
                theDataList = (List<Object>) creditNoteRegisterBean.getDataInRange(DebtorsCreditNoteRegister.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSCREDITNOTEREGISTER:
                theDataList.add(creditNoteRegisterBean.validateField(dataList.get(1).toString(), (DebtorsCreditNoteRegister) dataList.get(2), userData));
                break;
            //Invoice Register
            case PRINT_INVOICE_REGISTER:
                theDataList = invoiceRegisterReportBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            case PRINT_INVOICE_REGISTER_DETAIL:
                theDataList = invoiceRegisterDetailReportBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            //Credit Note Finished Goods Box Labels
            case PRINT_DEBTORS_CN_FG_BOX_LABELS:
                theDataList.add(cnFinishedGoodsLabelsBean.printFinishedGoodsBoxLabels((List<String>) dataList.get(1), (List<String>) dataList.get(2), userData));
                break;
            //Debtors Royalty Groups
            case INSERT_DEBTORSROYALTYGROUPS:
                theDataList.add(royaltyGroupsBean.insert((DebtorsRoyaltyGroups) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSROYALTYGROUPS:
                theDataList.add(royaltyGroupsBean.update((DebtorsRoyaltyGroups) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSROYALTYGROUPS:
                theDataList.add(royaltyGroupsBean.delete((DebtorsRoyaltyGroups) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSROYALTYGROUPS:
                theDataList.add(royaltyGroupsBean.getNumRows(DebtorsRoyaltyGroups.class, userData));
                break;
            case GETDATA_DEBTORSROYALTYGROUPS:
                theDataList = (List<Object>) royaltyGroupsBean.getDataInRange(DebtorsRoyaltyGroups.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSROYALTYGROUPS:
                theDataList.add(royaltyGroupsBean.validateField(dataList.get(1).toString(), (DebtorsRoyaltyGroups) dataList.get(2), userData));
                break;

            //Debtors Master Basket
            case INSERT_DEBTORSBASKETMASTER:
                theDataList.add(basketMasterBean.insert((DebtorsBasketMaster) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSBASKETMASTER:
                theDataList.add(basketMasterBean.update((DebtorsBasketMaster) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSBASKETMASTER:
                theDataList.add(basketMasterBean.delete((DebtorsBasketMaster) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSBASKETMASTER:
                theDataList.add(basketMasterBean.getNumRows(DebtorsBasketMaster.class, userData));
                break;
            case GETDATA_DEBTORSBASKETMASTER:
                theDataList = (List<Object>) basketMasterBean.getDataInRange(DebtorsBasketMaster.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSBASKETMASTER:
                theDataList.add(basketMasterBean.validateField(dataList.get(1).toString(), (DebtorsBasketMaster) dataList.get(2), userData));
                break;
            case RELEASE_TREC:
                theDataList.add(basketMasterBean.ReleaseTrec( dataList.get(1).toString(), userData));
                break;
            //Debtors Basket Lines
            case INSERT_DEBTORSBASKETLINES:
                theDataList.add(basketLinesInterfaceBean.insert((DebtorsBasketLines) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSBASKETLINES:
                theDataList.add(basketLinesInterfaceBean.update((DebtorsBasketLines) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSBASKETLINES:
                theDataList.add(basketLinesInterfaceBean.delete((DebtorsBasketLines) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSBASKETLINES:
                theDataList.add(basketLinesInterfaceBean.getNumRows(DebtorsBasketLines.class, userData));
                break;
            case GETDATA_DEBTORSBASKETLINES:
                theDataList = (List<Object>) basketLinesInterfaceBean.getDataInRange(DebtorsBasketLines.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSBASKETLINES:
                theDataList.add(basketLinesInterfaceBean.validateField(dataList.get(1).toString(), (DebtorsBasketLines) dataList.get(2), userData));
                break;
            //Print Instruction
            case PRINT_PRINTINSTRUCTIONREPORT:
                theDataList = printInstructionReportBean.getReportResult(dataList, userData);
                break;
            case REPRINT_DEBTORSBASKETLINE:
                theDataList.add(basketLinesInterfaceBean.ReReleaseTrec((Long) dataList.get(1), userData));
                break;
                
            case INSERT_DEBTORSBASKETLINESDS:
                theDataList.add(basketLinesDSInterfaceBean.insert((DebtorsBasketLinesDS) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORSBASKETLINESDS:
                theDataList.add(basketLinesDSInterfaceBean.update((DebtorsBasketLinesDS) dataList.get(1), userData));
                break;
            case DELETE_DEBTORSBASKETLINESDS:
                theDataList.add(basketLinesDSInterfaceBean.delete((DebtorsBasketLinesDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEBTORSBASKETLINESDS:
                theDataList.add(basketLinesDSInterfaceBean.getNumRows(DebtorsBasketLinesDS.class, userData));
                break;
            case GETDATA_DEBTORSBASKETLINESDS:
                theDataList = (List<Object>) basketLinesDSInterfaceBean.getDataInRange(DebtorsBasketLinesDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEBTORSBASKETLINESDS:
                theDataList.add(basketLinesDSInterfaceBean.validateField(dataList.get(1).toString(), (DebtorsBasketLinesDS) dataList.get(2), userData));
                break;
            case CHECK_CUSTOMER_CREDIT_STATUS:
                theDataList.add(debtorsCreditCheckBean.allowToOrder((String)dataList.get(1), userData));
                break;

            default:
                if (EMCDebug.getDebug()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Mapper: Method not found", userData);
                }
                break;
        }

        theDataList.add(0, retCmd);
        return theDataList;
    }
}
