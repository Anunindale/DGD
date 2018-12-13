/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.server.commandmanager.gl;

import emc.bus.gl.chartofaccounts.GLChartOfAccountsLocal;
import emc.bus.gl.currency.GLCurrencyLocal;
import emc.bus.gl.financialperiods.GLFinancialPeriodsLocal;
import emc.bus.gl.vatcodes.GLVATCodeLocal;
import emc.bus.gl.allocationrules.GLAllocationRulesLocal;
import emc.bus.gl.analysiscode1.GLAnalysisCode1Local;
import emc.bus.gl.analysiscode2.GLAnalysisCode2Local;
import emc.bus.gl.analysiscode3.GLAnalysisCode3Local;
import emc.bus.gl.analysiscode4.GLAnalysisCode4Local;
import emc.bus.gl.analysiscode5.GLAnalysisCode5Local;
import emc.bus.gl.analysiscode6.GLAnalysisCode6Local;
import emc.bus.gl.analysiscodehierarchy.GLAnalysisCodeHierarchyLocal;
import emc.bus.gl.analysiscodesuper.GLAnalysisCodeSuperLocal;
import emc.bus.gl.analysisrules.GLAnalysisRulesLocal;
import emc.bus.gl.chartofaccountsanalysisbalances.GLChartOfAccountsAnalysisBalancesLocal;
import emc.bus.gl.chartofaccountsbalances.GLChartOfAccountsBalancesLocal;
import emc.bus.gl.groupcompanyaccount.GLGroupCompanyAccountLocal;
import emc.bus.gl.grouprules.GLGroupRulesLocal;
import emc.bus.gl.journalapprovalgroupemployees.GLJournalApprovalGroupEmployeesLocal;
import emc.bus.gl.journalapprovalgroupemployees.datasourse.GLJournalApprovalGroupEmployeesDSLocal;
import emc.bus.gl.journalapprovalgroups.GLJournalApprovalGroupsLocal;
import emc.bus.gl.journallines.GLJournalLinesLocal;
import emc.bus.gl.journalmaster.GLJournalMasterLocal;
import emc.bus.gl.transactions.logic.gl.GLTransactionLogicLocal;
import emc.bus.gl.securityrules.GLSecurityRulesLocal;
import emc.bus.gl.transactionperiodsummary.GLTransactionPeriodSummaryLocal;
import emc.bus.gl.transactionsdetail.GLTransactionsDetailLocal;
import emc.bus.gl.transactionssummary.GLTransactionsSummaryLocal;
import emc.bus.gl.transactionssuper.GLTransactionsSuperBean;
import emc.bus.gl.transactionssuper.GLTransactionsSuperLocal;
import emc.commands.EMCCommands;
import emc.entity.base.journals.superclass.JournalMasterSuperClass;
import emc.entity.gl.GLChartOfAccounts;
import emc.entity.gl.GLChartOfAccountsAnalysisBalances;
import emc.entity.gl.GLChartOfAccountsBalances;
import emc.entity.gl.GLCurrency;
import emc.entity.gl.GLFinancialPeriods;
import emc.entity.gl.GLVATCode;
import emc.entity.gl.analysiscodes.GLAnalysisCode1;
import emc.entity.gl.analysiscodes.GLAnalysisCode2;
import emc.entity.gl.analysiscodes.GLAnalysisCode3;
import emc.entity.gl.analysiscodes.GLAnalysisCode4;
import emc.entity.gl.analysiscodes.GLAnalysisCode5;
import emc.entity.gl.analysiscodes.GLAnalysisCode6;
import emc.entity.gl.analysiscodes.GLAnalysisCodeSuper;
import emc.entity.gl.datasource.GLJournalApprovalGroupEmployeesDS;
import emc.entity.gl.journals.GLJournalApprovalGroupEmployees;
import emc.entity.gl.journals.GLJournalApprovalGroups;
import emc.entity.gl.journals.GLJournalLines;
import emc.entity.gl.journals.GLJournalMaster;
import emc.entity.gl.rules.GLAllocationRules;
import emc.entity.gl.rules.GLAnalysisRules;
import emc.entity.gl.rules.GLGroupCompanyAccount;
import emc.entity.gl.rules.GLGroupRules;
import emc.entity.gl.rules.GLSecurityRules;
import emc.entity.gl.transactions.GLTransactionPeriodSummary;
import emc.entity.gl.transactions.GLTransactionsDetail;
import emc.entity.gl.transactions.GLTransactionsSummary;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCUserData;
import emc.methods.gl.ClientGLMethods;
import emc.methods.gl.ServerGLMethods;
import emc.reports.gl.chartofaccounts.GLChartOfAccountsReportLocal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import emc.bus.gl.budgetmodel.GLBudgetModelLocal;
import emc.bus.gl.budgetlines.GLBudgetLinesLocal;
import emc.bus.gl.budgetlines.datasource.GLBudgetLinesDSLocal;
import emc.bus.gl.budgetperiod.GLBudgetPeriodLocal;
import emc.bus.gl.datasource.chartofaccounts.GLChartOfAccountsDSLocal;
import emc.bus.gl.datasource.journals.GLJournalLinesDSLocal;
import emc.bus.gl.transactiondaysummary.GLTransactionDaySummaryLocal;
import emc.entity.gl.GLBudgetLines;
import emc.entity.gl.GLBudgetLinesDS;
import emc.entity.gl.GLBudgetModel;
import emc.entity.gl.GLBudgetPeriod;
import emc.entity.gl.transactions.GLTransactionDaySummary;
import java.math.BigDecimal;
import emc.bus.gl.parameters.GLParametersLocal;
import emc.bus.gl.yearend.GLYearEndProcessingLocal;
import emc.entity.gl.GLParameters;
import java.util.Date;
import emc.entity.gl.analysiscodes.GLAnalysisCodeHierarchy;
import emc.entity.gl.datasource.GLChartOfAccountsDS;
import emc.entity.gl.datasource.GLJournalLinesDS;
import emc.reports.gl.journals.GLJournalReportLocal;

/**
 *
 * @author rico
 */
@Stateless
public class GLMethodMapperBean implements GLMethodMapperBeanLocal {

    @EJB
    private GLCurrencyLocal gLCurrencyBean;
    @EJB
    private GLVATCodeLocal gLVATCodeBean;
    @EJB
    private GLChartOfAccountsLocal glChartOfAccountsBean;
    @EJB
    private GLFinancialPeriodsLocal glFinancialPeriodsBean;
    @EJB
    private GLSecurityRulesLocal securityRulesBean;
    @EJB
    private GLAnalysisRulesLocal analysisRulesBean;
    @EJB
    private GLAllocationRulesLocal allocationRulesBean;
    @EJB
    private GLChartOfAccountsBalancesLocal chartOfAccountsBalancesBean;
    @EJB
    private GLChartOfAccountsAnalysisBalancesLocal chartOfAccountsAnalysisBalancesBean;
    @EJB
    private GLJournalMasterLocal journalMasterBean;
    @EJB
    private GLJournalLinesLocal journalLinesBean;
    @EJB
    private GLAnalysisCode1Local analysisCode1Bean;
    @EJB
    private GLAnalysisCode2Local analysisCode2Bean;
    @EJB
    private GLAnalysisCode3Local analysisCode3Bean;
    @EJB
    private GLAnalysisCode4Local analysisCode4Bean;
    @EJB
    private GLAnalysisCode5Local analysisCode5Bean;
    @EJB
    private GLAnalysisCode6Local analysisCode6Bean;
    @EJB
    private GLJournalApprovalGroupsLocal journalApprovalGroupsBean;
    @EJB
    private GLJournalApprovalGroupEmployeesLocal journalApprovalGroupEmployeesBean;
    @EJB
    private GLJournalApprovalGroupEmployeesDSLocal journalApprovalGroupEmployeesDSBean;
    @EJB
    private GLTransactionsSummaryLocal transactionsSummaryBean;
    @EJB
    private GLTransactionsSuperLocal transactionsSuperBean;
    @EJB
    private GLTransactionsDetailLocal transactionsDetailBean;
    @EJB
    private GLAnalysisCodeSuperLocal analysisCodeSuperBean;
    @EJB
    private GLTransactionPeriodSummaryLocal transactionPeriodSummaryBean;
    @EJB
    private GLGroupRulesLocal groupRulesBean;
    @EJB
    private GLGroupCompanyAccountLocal groupCompanyAccountBean;
    @EJB
    private GLTransactionLogicLocal glLogicBean;
    @EJB
    private GLChartOfAccountsReportLocal chartOfAccountsReportBean;
    @EJB
    private GLBudgetModelLocal budgetModelBean;
    @EJB
    private GLBudgetLinesLocal budgetLinesBean;
    @EJB
    private GLBudgetPeriodLocal budgetPeriodBean;
    @EJB
    private GLBudgetLinesDSLocal budgetLinesDSBean;
    @EJB
    private GLTransactionDaySummaryLocal daySummaryBean;
    @EJB
    private GLParametersLocal parametersBean;
    @EJB
    private GLAnalysisCodeHierarchyLocal analysisCodeHierarchyBean;
    @EJB
    private GLChartOfAccountsDSLocal chartOfAccountsDSBean;
    @EJB
    private GLJournalLinesDSLocal journalLinesDSBean;
    @EJB
    private GLYearEndProcessingLocal yearEndProcessingBean;
    @EJB
    private GLJournalReportLocal journalReportBean;

    public GLMethodMapperBean() {
    }

    public List mapMethodGL(EMCCommandClass cmd, List<Object> dataList, EMCUserData userData) throws EMCEntityBeanException {
        List<Object> theDataList = new ArrayList();
        EMCCommandClass retCmd = new EMCCommandClass();
        retCmd.setCommandId(EMCCommands.CLIENT_GENERAL_COMMAND.getId());
        retCmd.setModuleNumber(enumEMCModules.GENERAL_LEDGER.getId());
        retCmd.setMethodId(ClientGLMethods.GENERAL_METHOD.toString());

        switch (ServerGLMethods.fromString(cmd.getMethodId())) {
            //GL Currency
            case INSERT_GLCURRENCY:
                theDataList.add(gLCurrencyBean.insert((GLCurrency) dataList.get(1), userData));
                break;
            case UPDATE_GLCURRENCY:
                theDataList.add(gLCurrencyBean.update((GLCurrency) dataList.get(1), userData));
                break;
            case DELETE_GLCURRENCY:
                theDataList.add(gLCurrencyBean.delete((GLCurrency) dataList.get(1), userData));
                break;
            case GETNUMROWS_GLCURRENCY:
                theDataList.add(gLCurrencyBean.getNumRows(GLCurrency.class, userData));
                break;
            case GETDATA_GLCURRENCY:
                theDataList = (List<Object>) gLCurrencyBean.getDataInRange(GLCurrency.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_GLCURRENCY:
                theDataList.add(gLCurrencyBean.validateField(dataList.get(1).toString(), (GLCurrency) dataList.get(2), userData));
                break;
            //GL VAT Codes
            case INSERT_GLVATCODE:
                theDataList.add(gLVATCodeBean.insert((GLVATCode) dataList.get(1), userData));
                break;
            case UPDATE_GLVATCODE:
                theDataList.add(gLVATCodeBean.update((GLVATCode) dataList.get(1), userData));
                break;
            case DELETE_GLVATCODE:
                theDataList.add(gLVATCodeBean.delete((GLVATCode) dataList.get(1), userData));
                break;
            case GETNUMROWS_GLVATCODE:
                theDataList.add(gLVATCodeBean.getNumRows(GLVATCode.class, userData));
                break;
            case GETDATA_GLVATCODE:
                theDataList = (List<Object>) gLVATCodeBean.getDataInRange(GLVATCode.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_GLVATCODE:
                theDataList.add(gLVATCodeBean.validateField(dataList.get(1).toString(), (GLVATCode) dataList.get(2), userData));
                break;
            //Chart Of Accounts
            case INSERT_GLCHARTOFACCOUNTS:
                theDataList.add(glChartOfAccountsBean.insert((GLChartOfAccounts) dataList.get(1), userData));
                break;
            case UPDATE_GLCHARTOFACCOUNTS:
                theDataList.add(glChartOfAccountsBean.update((GLChartOfAccounts) dataList.get(1), userData));
                break;
            case DELETE_GLCHARTOFACCOUNTS:
                theDataList.add(glChartOfAccountsBean.delete((GLChartOfAccounts) dataList.get(1), userData));
                break;
            case GETNUMROWS_GLCHARTOFACCOUNTS:
                theDataList.add(glChartOfAccountsBean.getNumRows(GLChartOfAccounts.class, userData));
                break;
            case GETDATA_GLCHARTOFACCOUNTS:
                theDataList = (List<Object>) glChartOfAccountsBean.getDataInRange(GLChartOfAccounts.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_GLCHARTOFACCOUNTS:
                theDataList.add(glChartOfAccountsBean.validateField(dataList.get(1).toString(), (GLChartOfAccounts) dataList.get(2), userData));
                break;
            case PRINT_CHART_OF_ACCOUNTS:
                theDataList.add(theDataList = chartOfAccountsReportBean.getReportResult(dataList, userData));
                break;
            //GL Financial Periods
            case INSERT_GLFINANCIALPERIODS:
                theDataList.add(glFinancialPeriodsBean.insert((GLFinancialPeriods) dataList.get(1), userData));
                break;
            case UPDATE_GLFINANCIALPERIODS:
                theDataList.add(glFinancialPeriodsBean.update((GLFinancialPeriods) dataList.get(1), userData));
                break;
            case DELETE_GLFINANCIALPERIODS:
                theDataList.add(glFinancialPeriodsBean.delete((GLFinancialPeriods) dataList.get(1), userData));
                break;
            case GETNUMROWS_GLFINANCIALPERIODS:
                theDataList.add(glFinancialPeriodsBean.getNumRows(GLFinancialPeriods.class, userData));
                break;
            case GETDATA_GLFINANCIALPERIODS:
                theDataList = (List<Object>) glFinancialPeriodsBean.getDataInRange(GLFinancialPeriods.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_GLFINANCIALPERIODS:
                theDataList.add(glFinancialPeriodsBean.validateField(dataList.get(1).toString(), (GLFinancialPeriods) dataList.get(2), userData));
                break;
            case REFRESH_SUBLEDGERS:
                theDataList.add(glFinancialPeriodsBean.updateSubLedgersCloseDates(userData));
            case GENERATE_FINANCIAL_PERIODS:
                theDataList.add(glFinancialPeriodsBean.generateFinancialPeriods((String) dataList.get(1), (Date) dataList.get(2), (Date) dataList.get(3), userData));
                break;
            //GL Security Rules
            case INSERT_GLSECURITYRULES:
                theDataList.add(securityRulesBean.insert((GLSecurityRules) dataList.get(1), userData));
                break;
            case UPDATE_GLSECURITYRULES:
                theDataList.add(securityRulesBean.update((GLSecurityRules) dataList.get(1), userData));
                break;
            case DELETE_GLSECURITYRULES:
                theDataList.add(securityRulesBean.delete((GLSecurityRules) dataList.get(1), userData));
                break;
            case GETNUMROWS_GLSECURITYRULES:
                theDataList.add(securityRulesBean.getNumRows(GLSecurityRules.class, userData));
                break;
            case GETDATA_GLSECURITYRULES:
                theDataList = (List<Object>) securityRulesBean.getDataInRange(GLSecurityRules.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_GLSECURITYRULES:
                theDataList.add(securityRulesBean.validateField(dataList.get(1).toString(), (GLSecurityRules) dataList.get(2), userData));
                break;
            //GLAnalysisRules
            case INSERT_GLANALYSISRULES:
                theDataList.add(analysisRulesBean.insert((GLAnalysisRules) dataList.get(1), userData));
                break;
            case UPDATE_GLANALYSISRULES:
                theDataList.add(analysisRulesBean.update((GLAnalysisRules) dataList.get(1), userData));
                break;
            case DELETE_GLANALYSISRULES:
                theDataList.add(analysisRulesBean.delete((GLAnalysisRules) dataList.get(1), userData));
                break;
            case GETNUMROWS_GLANALYSISRULES:
                theDataList.add(analysisRulesBean.getNumRows(GLAnalysisRules.class, userData));
                break;
            case GETDATA_GLANALYSISRULES:
                theDataList = (List<Object>) analysisRulesBean.getDataInRange(GLAnalysisRules.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_GLANALYSISRULES:
                theDataList.add(analysisRulesBean.validateField(dataList.get(1).toString(), (GLAnalysisRules) dataList.get(2), userData));
                break;
            //GLAllocationRules
            case INSERT_GLALLOCATIONRULES:
                theDataList.add(allocationRulesBean.insert((GLAllocationRules) dataList.get(1), userData));
                break;
            case UPDATE_GLALLOCATIONRULES:
                theDataList.add(allocationRulesBean.update((GLAllocationRules) dataList.get(1), userData));
                break;
            case DELETE_GLALLOCATIONRULES:
                theDataList.add(allocationRulesBean.delete((GLAllocationRules) dataList.get(1), userData));
                break;
            case GETNUMROWS_GLALLOCATIONRULES:
                theDataList.add(allocationRulesBean.getNumRows(GLAllocationRules.class, userData));
                break;
            case GETDATA_GLALLOCATIONRULES:
                theDataList = (List<Object>) allocationRulesBean.getDataInRange(GLAllocationRules.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_GLALLOCATIONRULES:
                theDataList.add(allocationRulesBean.validateField(dataList.get(1).toString(), (GLAllocationRules) dataList.get(2), userData));
                break;
            //GLChartOfAccountsBalances
            case INSERT_GLCHARTOFACCOUNTSBALANCES:
                theDataList.add(chartOfAccountsBalancesBean.insert((GLChartOfAccountsBalances) dataList.get(1), userData));
                break;
            case UPDATE_GLCHARTOFACCOUNTSBALANCES:
                theDataList.add(chartOfAccountsBalancesBean.update((GLChartOfAccountsBalances) dataList.get(1), userData));
                break;
            case DELETE_GLCHARTOFACCOUNTSBALANCES:
                theDataList.add(chartOfAccountsBalancesBean.delete((GLChartOfAccountsBalances) dataList.get(1), userData));
                break;
            case GETNUMROWS_GLCHARTOFACCOUNTSBALANCES:
                theDataList.add(chartOfAccountsBalancesBean.getNumRows(GLChartOfAccountsBalances.class, userData));
                break;
            case GETDATA_GLCHARTOFACCOUNTSBALANCES:
                theDataList = (List<Object>) chartOfAccountsBalancesBean.getDataInRange(GLChartOfAccountsBalances.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_GLCHARTOFACCOUNTSBALANCES:
                theDataList.add(chartOfAccountsBalancesBean.validateField(dataList.get(1).toString(), (GLChartOfAccountsBalances) dataList.get(2), userData));
                break;
            //GLChartOfAccountsAnalysisBalances
            case INSERT_GLCHARTOFACCOUNTSANALYSISBALANCES:
                theDataList.add(chartOfAccountsAnalysisBalancesBean.insert((GLChartOfAccountsAnalysisBalances) dataList.get(1), userData));
                break;
            case UPDATE_GLCHARTOFACCOUNTSANALYSISBALANCES:
                theDataList.add(chartOfAccountsAnalysisBalancesBean.update((GLChartOfAccountsAnalysisBalances) dataList.get(1), userData));
                break;
            case DELETE_GLCHARTOFACCOUNTSANALYSISBALANCES:
                theDataList.add(chartOfAccountsAnalysisBalancesBean.delete((GLChartOfAccountsAnalysisBalances) dataList.get(1), userData));
                break;
            case GETNUMROWS_GLCHARTOFACCOUNTSANALYSISBALANCES:
                theDataList.add(chartOfAccountsAnalysisBalancesBean.getNumRows(GLChartOfAccountsAnalysisBalances.class, userData));
                break;
            case GETDATA_GLCHARTOFACCOUNTSANALYSISBALANCES:
                theDataList = (List<Object>) chartOfAccountsAnalysisBalancesBean.getDataInRange(GLChartOfAccountsAnalysisBalances.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_GLCHARTOFACCOUNTSANALYSISBALANCES:
                theDataList.add(chartOfAccountsAnalysisBalancesBean.validateField(dataList.get(1).toString(), (GLChartOfAccountsAnalysisBalances) dataList.get(2), userData));
                break;
            //GLJournalMaster
            case INSERT_GLJOURNALMASTER:
                theDataList.add(journalMasterBean.insert((GLJournalMaster) dataList.get(1), userData));
                break;
            case UPDATE_GLJOURNALMASTER:
                theDataList.add(journalMasterBean.update((GLJournalMaster) dataList.get(1), userData));
                break;
            case DELETE_GLJOURNALMASTER:
                theDataList.add(journalMasterBean.delete((GLJournalMaster) dataList.get(1), userData));
                break;
            case GETNUMROWS_GLJOURNALMASTER:
                theDataList.add(journalMasterBean.getNumRows(GLJournalMaster.class, userData));
                break;
            case GETDATA_GLJOURNALMASTER:
                theDataList = (List<Object>) journalMasterBean.getDataInRange(GLJournalMaster.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case PRINT_JOURNAL_REPORT:
                theDataList = journalReportBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            case VALIDATEFIELD_GLJOURNALMASTER:
                theDataList.add(journalMasterBean.validateField(dataList.get(1).toString(), (GLJournalMaster) dataList.get(2), userData));
                break;
            case APPROVE_JOURNAL:
                theDataList.add(journalMasterBean.approveJournal((String) dataList.get(1), userData));
                break;
            case UNAPPROVE_JOURNAL:
                theDataList.add(journalMasterBean.unApproveJournal((String) dataList.get(1), userData));
                break;
            case VALIDATE_JOURNAL:
                theDataList.add(journalMasterBean.validateJournal((JournalMasterSuperClass) dataList.get(1), userData));
                break;
            case POST_JOURNAL:
                theDataList.add(journalMasterBean.attemptPost((JournalMasterSuperClass) dataList.get(1), userData));
                break;
            case GET_JOURNAL_TOTALS:
                theDataList.add(journalMasterBean.getJournalTotals((String) dataList.get(1), userData));
                break;
            //GLJournalLines
            case INSERT_GLJOURNALLINES:
                theDataList.add(journalLinesBean.insert((GLJournalLines) dataList.get(1), userData));
                break;
            case UPDATE_GLJOURNALLINES:
                theDataList.add(journalLinesBean.update((GLJournalLines) dataList.get(1), userData));
                break;
            case DELETE_GLJOURNALLINES:
                theDataList.add(journalLinesBean.delete((GLJournalLines) dataList.get(1), userData));
                break;
            case GETNUMROWS_GLJOURNALLINES:
                theDataList.add(journalLinesBean.getNumRows(GLJournalLines.class, userData));
                break;
            case GETDATA_GLJOURNALLINES:
                theDataList = (List<Object>) journalLinesBean.getDataInRange(GLJournalLines.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_GLJOURNALLINES:
                theDataList.add(journalLinesBean.validateField(dataList.get(1).toString(), (GLJournalLines) dataList.get(2), userData));
                break;
            //GLAnalysisCode1
            case INSERT_GLANALYSISCODE1:
                theDataList.add(analysisCode1Bean.insert((GLAnalysisCode1) dataList.get(1), userData));
                break;
            case UPDATE_GLANALYSISCODE1:
                theDataList.add(analysisCode1Bean.update((GLAnalysisCode1) dataList.get(1), userData));
                break;
            case DELETE_GLANALYSISCODE1:
                theDataList.add(analysisCode1Bean.delete((GLAnalysisCode1) dataList.get(1), userData));
                break;
            case GETNUMROWS_GLANALYSISCODE1:
                theDataList.add(analysisCode1Bean.getNumRows(GLAnalysisCode1.class, userData));
                break;
            case GETDATA_GLANALYSISCODE1:
                theDataList = (List<Object>) analysisCode1Bean.getDataInRange(GLAnalysisCode1.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_GLANALYSISCODE1:
                theDataList.add(analysisCode1Bean.validateField(dataList.get(1).toString(), (GLAnalysisCode1) dataList.get(2), userData));
                break;
            //GLAnalysisCode2
            case INSERT_GLANALYSISCODE2:
                theDataList.add(analysisCode2Bean.insert((GLAnalysisCode2) dataList.get(1), userData));
                break;
            case UPDATE_GLANALYSISCODE2:
                theDataList.add(analysisCode2Bean.update((GLAnalysisCode2) dataList.get(1), userData));
                break;
            case DELETE_GLANALYSISCODE2:
                theDataList.add(analysisCode2Bean.delete((GLAnalysisCode2) dataList.get(1), userData));
                break;
            case GETNUMROWS_GLANALYSISCODE2:
                theDataList.add(analysisCode2Bean.getNumRows(GLAnalysisCode2.class, userData));
                break;
            case GETDATA_GLANALYSISCODE2:
                theDataList = (List<Object>) analysisCode2Bean.getDataInRange(GLAnalysisCode2.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_GLANALYSISCODE2:
                theDataList.add(analysisCode2Bean.validateField(dataList.get(1).toString(), (GLAnalysisCode2) dataList.get(2), userData));
                break;
            //GLAnalysisCode3
            case INSERT_GLANALYSISCODE3:
                theDataList.add(analysisCode3Bean.insert((GLAnalysisCode3) dataList.get(1), userData));
                break;
            case UPDATE_GLANALYSISCODE3:
                theDataList.add(analysisCode3Bean.update((GLAnalysisCode3) dataList.get(1), userData));
                break;
            case DELETE_GLANALYSISCODE3:
                theDataList.add(analysisCode3Bean.delete((GLAnalysisCode3) dataList.get(1), userData));
                break;
            case GETNUMROWS_GLANALYSISCODE3:
                theDataList.add(analysisCode3Bean.getNumRows(GLAnalysisCode3.class, userData));
                break;
            case GETDATA_GLANALYSISCODE3:
                theDataList = (List<Object>) analysisCode3Bean.getDataInRange(GLAnalysisCode3.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_GLANALYSISCODE3:
                theDataList.add(analysisCode3Bean.validateField(dataList.get(1).toString(), (GLAnalysisCode3) dataList.get(2), userData));
                break;
            //GLAnalysisCode4
            case INSERT_GLANALYSISCODE4:
                theDataList.add(analysisCode4Bean.insert((GLAnalysisCode4) dataList.get(1), userData));
                break;
            case UPDATE_GLANALYSISCODE4:
                theDataList.add(analysisCode4Bean.update((GLAnalysisCode4) dataList.get(1), userData));
                break;
            case DELETE_GLANALYSISCODE4:
                theDataList.add(analysisCode4Bean.delete((GLAnalysisCode4) dataList.get(1), userData));
                break;
            case GETNUMROWS_GLANALYSISCODE4:
                theDataList.add(analysisCode4Bean.getNumRows(GLAnalysisCode4.class, userData));
                break;
            case GETDATA_GLANALYSISCODE4:
                theDataList = (List<Object>) analysisCode4Bean.getDataInRange(GLAnalysisCode4.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_GLANALYSISCODE4:
                theDataList.add(analysisCode4Bean.validateField(dataList.get(1).toString(), (GLAnalysisCode4) dataList.get(2), userData));
                break;
            //GLAnalysisCodeHierarchy
            case INSERT_GLANALYSISCODEHIERARCHY:
                theDataList.add(analysisCodeHierarchyBean.insert((GLAnalysisCodeHierarchy) dataList.get(1), userData));
                break;
            case UPDATE_GLANALYSISCODEHIERARCHY:
                theDataList.add(analysisCodeHierarchyBean.update((GLAnalysisCodeHierarchy) dataList.get(1), userData));
                break;
            case DELETE_GLANALYSISCODEHIERARCHY:
                theDataList.add(analysisCodeHierarchyBean.delete((GLAnalysisCodeHierarchy) dataList.get(1), userData));
                break;
            case GETNUMROWS_GLANALYSISCODEHIERARCHY:
                theDataList.add(analysisCodeHierarchyBean.getNumRows(GLAnalysisCodeHierarchy.class, userData));
                break;
            case GETDATA_GLANALYSISCODEHIERARCHY:
                theDataList = (List<Object>) analysisCodeHierarchyBean.getDataInRange(GLAnalysisCodeHierarchy.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_GLANALYSISCODEHIERARCHY:
                theDataList.add(analysisCodeHierarchyBean.validateField(dataList.get(1).toString(), (GLAnalysisCodeHierarchy) dataList.get(2), userData));
                break;
            //GLJournalApprovalGroups
            case INSERT_GLJOURNALAPPROVALGROUPS:
                theDataList.add(journalApprovalGroupsBean.insert((GLJournalApprovalGroups) dataList.get(1), userData));
                break;
            case UPDATE_GLJOURNALAPPROVALGROUPS:
                theDataList.add(journalApprovalGroupsBean.update((GLJournalApprovalGroups) dataList.get(1), userData));
                break;
            case DELETE_GLJOURNALAPPROVALGROUPS:
                theDataList.add(journalApprovalGroupsBean.delete((GLJournalApprovalGroups) dataList.get(1), userData));
                break;
            case GETNUMROWS_GLJOURNALAPPROVALGROUPS:
                theDataList.add(journalApprovalGroupsBean.getNumRows(GLJournalApprovalGroups.class, userData));
                break;
            case GETDATA_GLJOURNALAPPROVALGROUPS:
                theDataList = (List<Object>) journalApprovalGroupsBean.getDataInRange(GLJournalApprovalGroups.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_GLJOURNALAPPROVALGROUPS:
                theDataList.add(journalApprovalGroupsBean.validateField(dataList.get(1).toString(), (GLJournalApprovalGroups) dataList.get(2), userData));
                break;
            //GLJournalApprovalGroupEmployees
            case INSERT_GLJOURNALAPPROVALGROUPEMPLOYEES:
                theDataList.add(journalApprovalGroupEmployeesBean.insert((GLJournalApprovalGroupEmployees) dataList.get(1), userData));
                break;
            case UPDATE_GLJOURNALAPPROVALGROUPEMPLOYEES:
                theDataList.add(journalApprovalGroupEmployeesBean.update((GLJournalApprovalGroupEmployees) dataList.get(1), userData));
                break;
            case DELETE_GLJOURNALAPPROVALGROUPEMPLOYEES:
                theDataList.add(journalApprovalGroupEmployeesBean.delete((GLJournalApprovalGroupEmployees) dataList.get(1), userData));
                break;
            case GETNUMROWS_GLJOURNALAPPROVALGROUPEMPLOYEES:
                theDataList.add(journalApprovalGroupEmployeesBean.getNumRows(GLJournalApprovalGroupEmployees.class, userData));
                break;
            case GETDATA_GLJOURNALAPPROVALGROUPEMPLOYEES:
                theDataList = (List<Object>) journalApprovalGroupEmployeesBean.getDataInRange(GLJournalApprovalGroupEmployees.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_GLJOURNALAPPROVALGROUPEMPLOYEES:
                theDataList.add(journalApprovalGroupEmployeesBean.validateField(dataList.get(1).toString(), (GLJournalApprovalGroupEmployees) dataList.get(2), userData));
                break;
            //GLJournalApprovalGroupEmployeesDS
            case INSERT_GLJOURNALAPPROVALGROUPEMPLOYEESDS:
                theDataList.add(journalApprovalGroupEmployeesDSBean.insert((GLJournalApprovalGroupEmployeesDS) dataList.get(1), userData));
                break;
            case UPDATE_GLJOURNALAPPROVALGROUPEMPLOYEESDS:
                theDataList.add(journalApprovalGroupEmployeesDSBean.update((GLJournalApprovalGroupEmployeesDS) dataList.get(1), userData));
                break;
            case DELETE_GLJOURNALAPPROVALGROUPEMPLOYEESDS:
                theDataList.add(journalApprovalGroupEmployeesDSBean.delete((GLJournalApprovalGroupEmployeesDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_GLJOURNALAPPROVALGROUPEMPLOYEESDS:
                theDataList.add(journalApprovalGroupEmployeesDSBean.getNumRows(GLJournalApprovalGroupEmployeesDS.class, userData));
                break;
            case GETDATA_GLJOURNALAPPROVALGROUPEMPLOYEESDS:
                theDataList = (List<Object>) journalApprovalGroupEmployeesDSBean.getDataInRange(GLJournalApprovalGroupEmployeesDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_GLJOURNALAPPROVALGROUPEMPLOYEESDS:
                theDataList.add(journalApprovalGroupEmployeesDSBean.validateField(dataList.get(1).toString(), (GLJournalApprovalGroupEmployeesDS) dataList.get(2), userData));
                break;

//GLAnalysisCode5
            case INSERT_GLANALYSISCODE5:
                theDataList.add(analysisCode5Bean.insert((GLAnalysisCode5) dataList.get(1), userData));
                break;
            case UPDATE_GLANALYSISCODE5:
                theDataList.add(analysisCode5Bean.update((GLAnalysisCode5) dataList.get(1), userData));
                break;
            case DELETE_GLANALYSISCODE5:
                theDataList.add(analysisCode5Bean.delete((GLAnalysisCode5) dataList.get(1), userData));
                break;
            case GETNUMROWS_GLANALYSISCODE5:
                theDataList.add(analysisCode5Bean.getNumRows(GLAnalysisCode5.class, userData));
                break;
            case GETDATA_GLANALYSISCODE5:
                theDataList = (List<Object>) analysisCode5Bean.getDataInRange(GLAnalysisCode5.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_GLANALYSISCODE5:
                theDataList.add(analysisCode5Bean.validateField(dataList.get(1).toString(), (GLAnalysisCode5) dataList.get(2), userData));
                break;
//GLAnalysisCode6
            case INSERT_GLANALYSISCODE6:
                theDataList.add(analysisCode6Bean.insert((GLAnalysisCode6) dataList.get(1), userData));
                break;
            case UPDATE_GLANALYSISCODE6:
                theDataList.add(analysisCode6Bean.update((GLAnalysisCode6) dataList.get(1), userData));
                break;
            case DELETE_GLANALYSISCODE6:
                theDataList.add(analysisCode6Bean.delete((GLAnalysisCode6) dataList.get(1), userData));
                break;
            case GETNUMROWS_GLANALYSISCODE6:
                theDataList.add(analysisCode6Bean.getNumRows(GLAnalysisCode6.class, userData));
                break;
            case GETDATA_GLANALYSISCODE6:
                theDataList = (List<Object>) analysisCode6Bean.getDataInRange(GLAnalysisCode6.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_GLANALYSISCODE6:
                theDataList.add(analysisCode6Bean.validateField(dataList.get(1).toString(), (GLAnalysisCode6) dataList.get(2), userData));
                break;

            //GLTransactionsSummary
            case INSERT_GLTRANSACTIONSSUMMARY:
                theDataList.add(transactionsSummaryBean.insert((GLTransactionsSummary) dataList.get(1), userData));
                break;
            case UPDATE_GLTRANSACTIONSSUMMARY:
                theDataList.add(transactionsSummaryBean.update((GLTransactionsSummary) dataList.get(1), userData));
                break;
            case DELETE_GLTRANSACTIONSSUMMARY:
                theDataList.add(transactionsSummaryBean.delete((GLTransactionsSummary) dataList.get(1), userData));
                break;
            case GETNUMROWS_GLTRANSACTIONSSUMMARY:
                theDataList.add(transactionsSummaryBean.getNumRows(GLTransactionsSummary.class, userData));
                break;
            case GETDATA_GLTRANSACTIONSSUMMARY:
                theDataList = (List<Object>) transactionsSummaryBean.getDataInRange(GLTransactionsSummary.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_GLTRANSACTIONSSUMMARY:
                theDataList.add(transactionsSummaryBean.validateField(dataList.get(1).toString(), (GLTransactionsSummary) dataList.get(2), userData));
                break;

            //GLTransactionsSuperBean
            case INSERT_GLTRANSACTIONSSUPER:
                theDataList.add(transactionsSuperBean.insert((GLTransactionsSuperBean) dataList.get(1), userData));
                break;
            case UPDATE_GLTRANSACTIONSSUPER:
                theDataList.add(transactionsSuperBean.update((GLTransactionsSuperBean) dataList.get(1), userData));
                break;
            case DELETE_GLTRANSACTIONSSUPER:
                theDataList.add(transactionsSuperBean.delete((GLTransactionsSuperBean) dataList.get(1), userData));
                break;
            case GETNUMROWS_GLTRANSACTIONSSUPER:
                theDataList.add(transactionsSuperBean.getNumRows(GLTransactionsSuperBean.class, userData));
                break;
            case GETDATA_GLTRANSACTIONSSUPER:
                theDataList = (List<Object>) transactionsSuperBean.getDataInRange(GLTransactionsSuperBean.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_GLTRANSACTIONSSUPER:
                theDataList.add(transactionsSuperBean.validateField(dataList.get(1).toString(), (GLTransactionsSuperBean) dataList.get(2), userData));
                break;
            //GLTransactionsDetail
            case INSERT_GLTRANSACTIONSDETAIL:
                theDataList.add(transactionsDetailBean.insert((GLTransactionsDetail) dataList.get(1), userData));
                break;
            case UPDATE_GLTRANSACTIONSDETAIL:
                theDataList.add(transactionsDetailBean.update((GLTransactionsDetail) dataList.get(1), userData));
                break;
            case DELETE_GLTRANSACTIONSDETAIL:
                theDataList.add(transactionsDetailBean.delete((GLTransactionsDetail) dataList.get(1), userData));
                break;
            case GETNUMROWS_GLTRANSACTIONSDETAIL:
                theDataList.add(transactionsDetailBean.getNumRows(GLTransactionsDetail.class, userData));
                break;
            case GETDATA_GLTRANSACTIONSDETAIL:
                theDataList = (List<Object>) transactionsDetailBean.getDataInRange(GLTransactionsDetail.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_GLTRANSACTIONSDETAIL:
                theDataList.add(transactionsDetailBean.validateField(dataList.get(1).toString(), (GLTransactionsDetail) dataList.get(2), userData));
                break;

            //GLAnalysisCodeSuper
            case INSERT_GLANALYSISCODESUPER:
                theDataList.add(analysisCodeSuperBean.insert((GLAnalysisCodeSuper) dataList.get(1), userData));
                break;
            case UPDATE_GLANALYSISCODESUPER:
                theDataList.add(analysisCodeSuperBean.update((GLAnalysisCodeSuper) dataList.get(1), userData));
                break;
            case DELETE_GLANALYSISCODESUPER:
                theDataList.add(analysisCodeSuperBean.delete((GLAnalysisCodeSuper) dataList.get(1), userData));
                break;
            case GETNUMROWS_GLANALYSISCODESUPER:
                theDataList.add(analysisCodeSuperBean.getNumRows(GLAnalysisCodeSuper.class, userData));
                break;
            case GETDATA_GLANALYSISCODESUPER:
                theDataList = (List<Object>) analysisCodeSuperBean.getDataInRange(GLAnalysisCodeSuper.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_GLANALYSISCODESUPER:
                theDataList.add(analysisCodeSuperBean.validateField(dataList.get(1).toString(), (GLAnalysisCodeSuper) dataList.get(2), userData));
                break;
            //GLTransactionPeriodSummary
            case INSERT_GLTRANSACTIONPERIODSUMMARY:
                theDataList.add(transactionPeriodSummaryBean.insert((GLTransactionPeriodSummary) dataList.get(1), userData));
                break;
            case UPDATE_GLTRANSACTIONPERIODSUMMARY:
                theDataList.add(transactionPeriodSummaryBean.update((GLTransactionPeriodSummary) dataList.get(1), userData));
                break;
            case DELETE_GLTRANSACTIONPERIODSUMMARY:
                theDataList.add(transactionPeriodSummaryBean.delete((GLTransactionPeriodSummary) dataList.get(1), userData));
                break;
            case GETNUMROWS_GLTRANSACTIONPERIODSUMMARY:
                theDataList.add(transactionPeriodSummaryBean.getNumRows(GLTransactionPeriodSummary.class, userData));
                break;
            case GETDATA_GLTRANSACTIONPERIODSUMMARY:
                theDataList = (List<Object>) transactionPeriodSummaryBean.getDataInRange(GLTransactionPeriodSummary.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_GLTRANSACTIONPERIODSUMMARY:
                theDataList.add(transactionPeriodSummaryBean.validateField(dataList.get(1).toString(), (GLTransactionPeriodSummary) dataList.get(2), userData));
                break;
            case GENERATE_OPENING_BALANCES:
                theDataList.add(transactionPeriodSummaryBean.generateOpeningBalances((String) dataList.get(1), userData));
                break;
            //GL Group Rules
            case INSERT_GLGROUPRULES:
                theDataList.add(groupRulesBean.insert((GLGroupRules) dataList.get(1), userData));
                break;
            case UPDATE_GLGROUPRULES:
                theDataList.add(groupRulesBean.update((GLGroupRules) dataList.get(1), userData));
                break;
            case DELETE_GLGROUPRULES:
                theDataList.add(groupRulesBean.delete((GLGroupRules) dataList.get(1), userData));
                break;
            case GETNUMROWS_GLGROUPRULES:
                theDataList.add(groupRulesBean.getNumRows(GLGroupRules.class, userData));
                break;
            case GETDATA_GLGROUPRULES:
                theDataList = (List<Object>) groupRulesBean.getDataInRange(GLGroupRules.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_GLGROUPRULES:
                theDataList.add(groupRulesBean.validateField(dataList.get(1).toString(), (GLGroupRules) dataList.get(2), userData));
                break;
            //GL Group Company Account
            case INSERT_GLGROUPCOMPANYACCOUNT:
                theDataList.add(groupCompanyAccountBean.insert((GLGroupCompanyAccount) dataList.get(1), userData));
                break;
            case UPDATE_GLGROUPCOMPANYACCOUNT:
                theDataList.add(groupCompanyAccountBean.update((GLGroupCompanyAccount) dataList.get(1), userData));
                break;
            case DELETE_GLGROUPCOMPANYACCOUNT:
                theDataList.add(groupCompanyAccountBean.delete((GLGroupCompanyAccount) dataList.get(1), userData));
                break;
            case GETNUMROWS_GLGROUPCOMPANYACCOUNT:
                theDataList.add(groupCompanyAccountBean.getNumRows(GLGroupCompanyAccount.class, userData));
                break;
            case GETDATA_GLGROUPCOMPANYACCOUNT:
                theDataList = (List<Object>) groupCompanyAccountBean.getDataInRange(GLGroupCompanyAccount.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_GLGROUPCOMPANYACCOUNT:
                theDataList.add(groupCompanyAccountBean.validateField(dataList.get(1).toString(), (GLGroupCompanyAccount) dataList.get(2), userData));
                break;
            //GL Consolidation
            case DO_GL_CONSOLIDATION:
                theDataList.add(glLogicBean.doGLConsolidation(userData));
                break;
            case REGENERATE_GL_SUMMARY_TABLES:
                theDataList.add(glLogicBean.regenerateSummaryTables(userData));
                break;
            //GL Budget Model
            case INSERT_GLBUDGETMODEL:
                theDataList.add(budgetModelBean.insert((GLBudgetModel) dataList.get(1), userData));
                break;
            case UPDATE_GLBUDGETMODEL:
                theDataList.add(budgetModelBean.update((GLBudgetModel) dataList.get(1), userData));
                break;
            case DELETE_GLBUDGETMODEL:
                theDataList.add(budgetModelBean.delete((GLBudgetModel) dataList.get(1), userData));
                break;
            case GETNUMROWS_GLBUDGETMODEL:
                theDataList.add(budgetModelBean.getNumRows(GLBudgetModel.class, userData));
                break;
            case GETDATA_GLBUDGETMODEL:
                theDataList = (List<Object>) budgetModelBean.getDataInRange(GLBudgetModel.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_GLBUDGETMODEL:
                theDataList.add(budgetModelBean.validateField(dataList.get(1).toString(), (GLBudgetModel) dataList.get(2), userData));
                break;
            //GL Budget Lines
            case INSERT_GLBUDGETLINES:
                theDataList.add(budgetLinesBean.insert((GLBudgetLines) dataList.get(1), userData));
                break;
            case UPDATE_GLBUDGETLINES:
                theDataList.add(budgetLinesBean.update((GLBudgetLines) dataList.get(1), userData));
                break;
            case DELETE_GLBUDGETLINES:
                theDataList.add(budgetLinesBean.delete((GLBudgetLines) dataList.get(1), userData));
                break;
            case GETNUMROWS_GLBUDGETLINES:
                theDataList.add(budgetLinesBean.getNumRows(GLBudgetLines.class, userData));
                break;
            case GETDATA_GLBUDGETLINES:
                theDataList = (List<Object>) budgetLinesBean.getDataInRange(GLBudgetLines.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_GLBUDGETLINES:
                theDataList.add(budgetLinesBean.validateField(dataList.get(1).toString(), (GLBudgetLines) dataList.get(2), userData));
                break;
            case GET_ACCOUNTS:
                theDataList.add(budgetLinesBean.getAccounts(dataList.get(1).toString(), dataList.get(2).toString(), userData));
                break;
            //GL Budget Period
            case INSERT_GLBUDGETPERIOD:
                theDataList.add(budgetPeriodBean.insert((GLBudgetPeriod) dataList.get(1), userData));
                break;
            case UPDATE_GLBUDGETPERIOD:
                theDataList.add(budgetPeriodBean.update((GLBudgetPeriod) dataList.get(1), userData));
                break;
            case DELETE_GLBUDGETPERIOD:
                theDataList.add(budgetPeriodBean.delete((GLBudgetPeriod) dataList.get(1), userData));
                break;
            case GETNUMROWS_GLBUDGETPERIOD:
                theDataList.add(budgetPeriodBean.getNumRows(GLBudgetPeriod.class, userData));
                break;
            case GETDATA_GLBUDGETPERIOD:
                theDataList = (List<Object>) budgetPeriodBean.getDataInRange(GLBudgetPeriod.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_GLBUDGETPERIOD:
                theDataList.add(budgetPeriodBean.validateField(dataList.get(1).toString(), (GLBudgetPeriod) dataList.get(2), userData));
                break;
            case SPLIT_AMOUNTS:
                theDataList.add(budgetPeriodBean.splitAmounts((Long) dataList.get(1), (BigDecimal) dataList.get(2), userData));
                break;
            //GL BudgetLines DS
            case INSERT_GLBUDGETLINESDS:
                theDataList.add(budgetLinesDSBean.insert((GLBudgetLinesDS) dataList.get(1), userData));
                break;
            case UPDATE_GLBUDGETLINESDS:
                theDataList.add(budgetLinesDSBean.update((GLBudgetLinesDS) dataList.get(1), userData));
                break;
            case DELETE_GLBUDGETLINESDS:
                theDataList.add(budgetLinesDSBean.delete((GLBudgetLinesDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_GLBUDGETLINESDS:
                theDataList.add(budgetLinesDSBean.getNumRows(GLBudgetLinesDS.class, userData));
                break;
            case GETDATA_GLBUDGETLINESDS:
                theDataList = (List<Object>) budgetLinesDSBean.getDataInRange(GLBudgetLinesDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_GLBUDGETLINESDS:
                theDataList.add(budgetLinesDSBean.validateField(dataList.get(1).toString(), (GLBudgetLinesDS) dataList.get(2), userData));
                break;
            // GL Transaction Day Summary
            case INSERT_GLTRANSACTIONDAYSUMMARY:
                theDataList.add(daySummaryBean.insert((GLTransactionDaySummary) dataList.get(1), userData));
                break;
            case UPDATE_GLTRANSACTIONDAYSUMMARY:
                theDataList.add(daySummaryBean.update((GLTransactionDaySummary) dataList.get(1), userData));
                break;
            case DELETE_GLTRANSACTIONDAYSUMMARY:
                theDataList.add(daySummaryBean.delete((GLTransactionDaySummary) dataList.get(1), userData));
                break;
            case GETNUMROWS_GLTRANSACTIONDAYSUMMARY:
                theDataList.add(daySummaryBean.getNumRows(GLTransactionDaySummary.class, userData));
                break;
            case GETDATA_GLTRANSACTIONDAYSUMMARY:
                theDataList = (List<Object>) daySummaryBean.getDataInRange(GLTransactionDaySummary.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_GLTRANSACTIONDAYSUMMARY:
                theDataList.add(daySummaryBean.validateField(dataList.get(1).toString(), (GLTransactionDaySummary) dataList.get(2), userData));
                break;

            //GL Parameters
            case INSERT_GLPARAMETERS:
                theDataList.add(parametersBean.insert((GLParameters) dataList.get(1), userData));
                break;
            case UPDATE_GLPARAMETERS:
                theDataList.add(parametersBean.update((GLParameters) dataList.get(1), userData));
                break;
            case DELETE_GLPARAMETERS:
                theDataList.add(parametersBean.delete((GLParameters) dataList.get(1), userData));
                break;
            case GETNUMROWS_GLPARAMETERS:
                theDataList.add(parametersBean.getNumRows(GLParameters.class, userData));
                break;
            case GETDATA_GLPARAMETERS:
                theDataList = (List<Object>) parametersBean.getDataInRange(GLParameters.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_GLPARAMETERS:
                theDataList.add(parametersBean.validateField(dataList.get(1).toString(), (GLParameters) dataList.get(2), userData));
                break;
            //GL Chart Of Accounts DS
            case INSERT_GLCHARTOFACCOUNTSDS:
                theDataList.add(chartOfAccountsDSBean.insert((GLChartOfAccountsDS) dataList.get(1), userData));
                break;
            case UPDATE_GLCHARTOFACCOUNTSDS:
                theDataList.add(chartOfAccountsDSBean.update((GLChartOfAccountsDS) dataList.get(1), userData));
                break;
            case DELETE_GLCHARTOFACCOUNTSDS:
                theDataList.add(chartOfAccountsDSBean.delete((GLChartOfAccountsDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_GLCHARTOFACCOUNTSDS:
                theDataList.add(chartOfAccountsDSBean.getNumRows(GLChartOfAccountsDS.class, userData));
                break;
            case GETDATA_GLCHARTOFACCOUNTSDS:
                theDataList = (List<Object>) chartOfAccountsDSBean.getDataInRange(GLChartOfAccountsDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_GLCHARTOFACCOUNTSDS:
                theDataList.add(chartOfAccountsDSBean.validateField(dataList.get(1).toString(), (GLChartOfAccountsDS) dataList.get(2), userData));
                break;
            //GL Journal Lines DS
            case INSERT_GLJOURNALLINESDS:
                theDataList.add(journalLinesDSBean.insert((GLJournalLinesDS) dataList.get(1), userData));
                break;
            case UPDATE_GLJOURNALLINESDS:
                theDataList.add(journalLinesDSBean.update((GLJournalLinesDS) dataList.get(1), userData));
                break;
            case DELETE_GLJOURNALLINESDS:
                theDataList.add(journalLinesDSBean.delete((GLJournalLinesDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_GLJOURNALLINESDS:
                theDataList.add(journalLinesDSBean.getNumRows(GLJournalLinesDS.class, userData));
                break;
            case GETDATA_GLJOURNALLINESDS:
                theDataList = (List<Object>) journalLinesDSBean.getDataInRange(GLJournalLinesDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_GLJOURNALLINESDS:
                theDataList.add(journalLinesDSBean.validateField(dataList.get(1).toString(), (GLJournalLinesDS) dataList.get(2), userData));
                break;
            //Process Year-End
            case PROCESS_YEAR_END:
                theDataList.add(yearEndProcessingBean.processYearEnd((String) dataList.get(1), userData));
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
