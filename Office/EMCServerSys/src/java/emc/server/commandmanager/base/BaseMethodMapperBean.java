
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.server.commandmanager.base;

import emc.bus.base.BaseCompanyLocal;
import emc.bus.base.BaseCountriesLocal;
import emc.bus.base.BaseDocRefLocal;
import emc.bus.base.BaseEmployeeLocal;
import emc.bus.base.BaseFilePathsLocal;
import emc.bus.base.BaseLicenceLocal;
import emc.bus.base.BasePostalCodesLocal;
import emc.bus.base.BaseSystemTableLocal;
import emc.bus.base.BaseUOMConversionTableLocal;
import emc.bus.base.BaseUnitsOfMeasureLocal;
import emc.bus.base.SessionsLocal;
import emc.bus.base.UsersLocal;
import emc.bus.base.action.BaseCloneLocal;
import emc.bus.base.action.BaseImportLocal;
import emc.bus.base.activetransactions.ActiveTransactionsLocal;
import emc.bus.base.autoemailsms.BaseAutoEmailSMSLocal;
import emc.bus.base.batchprocess.BaseBatchProcessLocal;
import emc.bus.base.calendar.BaseCalendarExceptionTypesLocal;
import emc.bus.base.calendar.BaseCalendarExceptionsLocal;
import emc.bus.base.calendar.BaseCalendarLocal;
import emc.bus.base.calendar.BaseCalendarShiftsLocal;
import emc.bus.base.city.BaseCityLocal;
import emc.bus.base.copytabledata.BaseCopyTableDataLocal;
import emc.bus.base.databaseconnection.BaseDataBaseConnectionLocal;
import emc.bus.base.dblog.DBLogLocal;
import emc.bus.base.dblog.DBLogSetupLocal;
import emc.bus.base.employeeaccessgroup.BaseEmployeeAccessGroupLocal;
import emc.bus.base.employeeaccessgroupemployees.BaseEmployeeAccessGroupEmployeesLocal;
import emc.bus.base.employeeaccessgroupemployees.datasourse.BaseEmployeeAccessGroupEmployeesDSLocal;
import emc.bus.base.employeecategoryhistory.BaseEmployeeCategoryHistoryLocal;
import emc.bus.base.employeecategoryhistory.datasource.BaseEmployeeCategoryHistoryDSLocal;
import emc.bus.base.entityrelationdiagram.BaseEntityRelationDiagramLocal;
import emc.bus.base.fileassociations.BaseFileAssociationsLocal;
import emc.bus.base.fileassociations.BaseUserFileAssociationsLocal;
import emc.bus.base.genericreport.BaseGenericReportLocal;
import emc.bus.base.helpfiles.BaseHelpFileMappingsLocal;
import emc.bus.base.index.BaseIndexLocal;
import emc.bus.base.journals.BaseJournalApprovalGroupsLocal;
import emc.bus.base.journals.BaseJournalDefinitionLocal;
import emc.bus.base.journals.accessgroups.BaseJournalAccessGroupDefinitionsLocal;
import emc.bus.base.journals.accessgroups.BaseJournalAccessGroupEmployeesDSLocal;
import emc.bus.base.journals.accessgroups.BaseJournalAccessGroupEmployeesLocal;
import emc.bus.base.journals.accessgroups.BaseJournalAccessGroupsLocal;
import emc.bus.base.journals.datasources.BaseJournalApprovalGroupEmployeesDSLocal;
import emc.bus.base.language.BaseLanguageLocal;
import emc.bus.base.mailsetup.BaseMailReturnAddressSetupDSLocal;
import emc.bus.base.mailsetup.BaseMailReturnAddressSetupLocal;
import emc.bus.base.mailsetup.BaseMailSetupLocal;
import emc.bus.base.messages.BaseMessagesLocal;
import emc.bus.base.numbersequences.BaseAvailableSequenceNumbersLocal;
import emc.bus.base.numbersequences.BaseNumberSequenceLocal;
import emc.bus.base.onlineusers.OnlineUsersLocal;
import emc.bus.base.parameters.BaseParametersLocal;
import emc.bus.base.permissions.BasePermissionsTableLocal;
import emc.bus.base.permissions.BaseUserPermissionsTableLocal;
import emc.bus.base.permissions.info.BasePermissionsInfoLocal;
import emc.bus.base.provence.BaseProvenceLocal;
import emc.bus.base.query.BaseQueryActionTableLocal;
import emc.bus.base.query.BaseQueryTableLocal;
import emc.bus.base.reporttools.BaseReportOrderTableLocal;
import emc.bus.base.reporttools.BaseReportPrintOptionsLocal;
import emc.bus.base.reporttools.BaseReportSchedulingLocal;
import emc.bus.base.reporttools.BaseReportTextLocal;
import emc.bus.base.reporttools.BaseReportUserQueryTableLocal;
import emc.bus.base.reporttools.BaseReportWhereTableLocal;
import emc.bus.base.reporttools.DefaultReportLocal;
import emc.bus.base.servertransactionlog.BaseServerTransactionLogLocal;
import emc.bus.base.servertransactionlog.datasource.BaseServerTransactionsLogDSLocal;
import emc.bus.base.setupstoratetable.BaseSetupStorageTableLocal;
import emc.bus.base.signature.BaseEmailSignatureLocal;
import emc.bus.base.smstemplate.BaseSMSTemplateLocal;
import emc.bus.base.suburb.BaseSuburbLocal;
import emc.bus.base.systemtables.SystemTableLogicLocal;
import emc.bus.base.tables.TablesLocal;
import emc.bus.base.template.BaseEmailTemplateLocal;
import emc.bus.base.templatedocuments.BaseTemplateDocumentsLocal;
import emc.bus.base.timebyday.BaseTimeByDayLocal;
import emc.bus.base.timedoperations.BaseTimedOperationLocal;
import emc.bus.base.uomdetailconversiontable.BaseUOMDetailedConversionTableLocal;
import emc.bus.base.uomdetailconversiontable.datasource.BaseUOMDetailedConversionTableDSLocal;
import emc.bus.base.webfilepath.BaseWebFilePathsLocal;
import emc.bus.base.webportal.BaseWebPortalUsersLocal;
import emc.bus.base.webportal.datasource.BaseWebPortalUsersDSLocal;
import emc.commands.EMCCommands;
import emc.entity.base.BaseCity;
import emc.entity.base.BaseCompanyTable;
import emc.entity.base.BaseCountries;
import emc.entity.base.BaseDBConnections;
import emc.entity.base.BaseDocuRefTable;
import emc.entity.base.BaseEmailSignatures;
import emc.entity.base.BaseEmailTemplates;
import emc.entity.base.BaseEmployeeCategoryHistory;
import emc.entity.base.BaseEmployeeTable;
import emc.entity.base.BaseFilePaths;
import emc.entity.base.BaseIndex;
import emc.entity.base.BaseLicenceTable;
import emc.entity.base.BaseMailReturnAddressSetup;
import emc.entity.base.BaseMailSetup;
import emc.entity.base.BaseParameters;
import emc.entity.base.BasePostalCodes;
import emc.entity.base.BaseProvence;
import emc.entity.base.BaseSMSTemplate;
import emc.entity.base.BaseSuburb;
import emc.entity.base.BaseSystemTable;
import emc.entity.base.BaseTimeByDay;
import emc.entity.base.BaseUOMConversionTable;
import emc.entity.base.BaseUOMDetailedConversionTable;
import emc.entity.base.BaseUnitsOfMeasure;
import emc.entity.base.BaseWebFilePaths;
import emc.entity.base.Usertable;
import emc.entity.base.batchprocess.BaseBatchProcess;
import emc.entity.base.calendar.BaseCalendar;
import emc.entity.base.calendar.BaseCalendarExceptionTypes;
import emc.entity.base.calendar.BaseCalendarExceptions;
import emc.entity.base.calendar.BaseCalendarShifts;
import emc.entity.base.copytabledata.BaseCopyTableData;
import emc.entity.base.datasource.BaseEmployeeCategoryHistoryDS;
import emc.entity.base.datasource.BaseMailReturnAddressSetupDS;
import emc.entity.base.datasource.BaseUOMDetailedConversionTableDS;
import emc.entity.base.datasource.BaseWebPortalUsersDS;
import emc.entity.base.datasource.EMCSession;
import emc.entity.base.datasource.EMCTransactions;
import emc.entity.base.dblog.BaseDBLog;
import emc.entity.base.dblog.BaseDBLogSetup;
import emc.entity.base.employeeaccessgroup.BaseEmployeeAccessGroup;
import emc.entity.base.employeeaccessgroupemployees.BaseEmployeeAccessGroupEmployees;
import emc.entity.base.employeeaccessgroupemployees.datasourse.BaseEmployeeAccessGroupEmployeesDS;
import emc.entity.base.fileassociations.BaseFileAssociations;
import emc.entity.base.fileassociations.BaseUserFileAssociations;
import emc.entity.base.helpfiles.BaseHelpFileMappings;
import emc.entity.base.journals.BaseJournalApprovalGroupEmployees;
import emc.entity.base.journals.BaseJournalApprovalGroups;
import emc.entity.base.journals.BaseJournalDefinitionTable;
import emc.entity.base.journals.accessgroups.BaseJournalAccessGroupDefinitions;
import emc.entity.base.journals.accessgroups.BaseJournalAccessGroupEmployees;
import emc.entity.base.journals.accessgroups.BaseJournalAccessGroupEmployeesDS;
import emc.entity.base.journals.accessgroups.BaseJournalAccessGroups;
import emc.entity.base.language.BaseLanguage;
import emc.entity.base.messages.BaseMessages;
import emc.entity.base.numbersequences.BaseAvailableSequenceNumbers;
import emc.entity.base.numbersequences.BaseNumberSequence;
import emc.entity.base.permissions.BasePermissionsTable;
import emc.entity.base.permissions.BaseUserPermissionsTable;
import emc.entity.base.query.BaseQueryActionTable;
import emc.entity.base.query.BaseQueryTable;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportPrintOptions;
import emc.entity.base.reporttools.BaseReportScheduling;
import emc.entity.base.reporttools.BaseReportText;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.base.servertransactions.BaseServerTransactionsLog;
import emc.entity.base.servertransactions.datasource.BaseServerTransactionsLogDS;
import emc.entity.base.templatedocuments.BaseTemplateDocuments;
import emc.entity.base.timedoperations.BaseTimedOperations;
import emc.entity.base.webportal.BaseWebPortalUsers;
import emc.enums.enumLogTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCMultiProcessingLocal;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.helpers.base.EMCEmail;
import emc.helpers.base.EMCSms;
import emc.methods.base.ServerBaseMethods;
import emc.methods.workflow.ClientWorkFlowMethods;
import emc.reports.base.BasePermissionsInfoReportLocal;
import emc.reports.base.dbloggenericreport.BaseDBLogGenericReportLocal;
import emc.reports.base.permissions.BaseUserPermissionsReportLocal;
import emc.reports.developertools.tasksheets.TaskSheetsReportsDSLocal;
import emc.server.mailmanager.EMCMailManagerLocal;
import emc.server.smsmanager.EMCSMSManagerLocal;
import emc.server.utility.EMCServerUtilityLocal;
import emc.server.utility.support.SupportLogicLocal;
import emc.um.helper.FileUploadHelper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author rico
 */
@Stateless
public class BaseMethodMapperBean implements BaseMethodMapperBeanLocal {

    @EJB
    private BaseCompanyLocal baseCompanyBean;
    @EJB
    private BaseEmployeeLocal baseEmployeeBean;
    @EJB
    private BaseDocRefLocal baseDocuRefBean;
    @EJB
    private BaseLicenceLocal baseLicenceBean;
    @EJB
    private UsersLocal usersBean;
    @EJB
    private BasePostalCodesLocal postalCodesBean;
    @EJB
    private BaseUnitsOfMeasureLocal unitsOfMeasureBean;
    @EJB
    private BaseSystemTableLocal systemTablesBean;
    @EJB
    private SystemTableLogicLocal systemTableLogicBean;
    @EJB
    private BaseFilePathsLocal filePathsBean;
    @EJB
    private OnlineUsersLocal onlineUsersBean;
    @EJB
    private SessionsLocal EMCSessionBean;
    @EJB
    private BaseNumberSequenceLocal numberSequenceBean;
    @EJB
    private BaseAvailableSequenceNumbersLocal availableSequenceNumbersBean;
    @EJB
    private BaseUOMConversionTableLocal uomConversionBean;
    @EJB
    private TablesLocal tablesBean;
    @EJB
    private EMCServerUtilityLocal util;
    @EJB
    private BaseCountriesLocal baseCountriesBean;
    @EJB
    private BaseReportUserQueryTableLocal userQueryTableBean;
    @EJB
    private BaseReportOrderTableLocal reportOrderTableBean;
    @EJB
    private BaseReportWhereTableLocal reportWhereTableBean;
    @EJB
    private DefaultReportLocal defaultReportBean;
    @EJB
    private BaseImportLocal importBean;
    @EJB
    private BaseReportPrintOptionsLocal printOptionsBean;
    @EJB
    private BaseCalendarLocal calendarBean;
    @EJB
    private BaseCalendarExceptionsLocal calendarExceptionBean;
    @EJB
    private BaseCalendarShiftsLocal calendarShiftBean;
    @EJB
    private DBLogLocal dbLogBean;
    @EJB
    private DBLogSetupLocal dbLogSetupBean;
    @EJB
    private BaseMessagesLocal messagesBean;
    @EJB
    private BaseCloneLocal cloneBean;
    @EJB
    private BaseUserPermissionsTableLocal userPermissionsBean;
    @EJB
    private BaseTimedOperationLocal timedBean;
    @EJB
    private BaseMailSetupLocal mailSetupBean;
    @EJB
    private BaseEmailTemplateLocal templateBean;
    @EJB
    private BaseEmailSignatureLocal signatureBean;
    @EJB
    private BaseWebPortalUsersLocal webPortalUsersBean;
    @EJB
    private BaseParametersLocal paramBean;
    @EJB
    private BaseProvenceLocal provenceBean;
    @EJB
    private BaseCityLocal cityBean;
    @EJB
    private BaseSuburbLocal suburbBean;
    @EJB
    private BaseEmployeeAccessGroupLocal empAccessGroupBean;
    @EJB
    private BaseEmployeeAccessGroupEmployeesLocal empAccessGroupEmpBean;
    @EJB
    private BaseEmployeeAccessGroupEmployeesDSLocal empAccessGroupEmpDSBean;
    @EJB
    private BasePermissionsInfoLocal permissionInfoBean;
    @EJB
    private BaseTimeByDayLocal timeByDayBean;
    @EJB
    private BasePermissionsTableLocal permissionsBean;
    @EJB
    private BaseJournalDefinitionLocal journalDefinitionBean;
    @EJB
    private BaseJournalApprovalGroupsLocal journalApprovalGroupsBean;
    @EJB
    private BaseJournalApprovalGroupEmployeesDSLocal journalGroupEmployeesBean;
    @EJB
    private BaseCalendarExceptionTypesLocal calendarExceptionTypesBean;
    @EJB
    private EMCMailManagerLocal mailManagerBean;
    @EJB
    private BaseMailReturnAddressSetupLocal mailReturnAddressSetupBean;
    @EJB
    private BaseMailReturnAddressSetupDSLocal mailReturnAddressSetupDSBean;
    @EJB
    private BaseSetupStorageTableLocal setupStorageTableBean;
    @EJB
    private BaseDataBaseConnectionLocal dataBaseConnectionBean;
    @EJB
    private BaseFileAssociationsLocal fileAssociationsBean;
    @EJB
    private BaseUserFileAssociationsLocal userFileAssociationsBean;
    @EJB
    private EMCMultiProcessingLocal multiProcessingBean;
    @EJB
    private BaseJournalAccessGroupsLocal journalAccessGroupsBean;
    @EJB
    private BaseJournalAccessGroupEmployeesLocal journalAccessGroupEmployeesBean;
    @EJB
    private BaseJournalAccessGroupDefinitionsLocal journalAccessGroupDefinitionsBean;
    @EJB
    private BaseJournalAccessGroupEmployeesDSLocal journalAccessGroupEmployeesDSBean;
    @EJB
    private ActiveTransactionsLocal activeTransBean;
    @EJB
    private BasePermissionsInfoReportLocal permissionInfoReportBean;
    @EJB
    private TaskSheetsReportsDSLocal taskSheetReportBean;
    @EJB
    private BaseBatchProcessLocal batchProcessingBean;
    @EJB
    private BaseUserPermissionsReportLocal userPermissionsReportBean;
    @EJB
    private BaseHelpFileMappingsLocal helpFileMappingBean;
    @EJB
    private BaseEntityRelationDiagramLocal entityRelationDiagramBean;
    @EJB
    private BaseGenericReportLocal genericReportBean;
    @EJB
    private BaseServerTransactionLogLocal baseServerTransactionsLogBean;
    @EJB
    private BaseServerTransactionsLogDSLocal baseServerTransactionsLogBeanDS;
    @EJB
    private BaseIndexLocal indexBean;
    @EJB
    private BaseWebFilePathsLocal webFilePaths;
    @EJB
    private BaseCopyTableDataLocal copyTableDataBean;
    @EJB
    private BaseReportTextLocal reportTextBean;
    @EJB
    private BaseEmployeeCategoryHistoryLocal employeeCategoryHistoryBean;
    @EJB
    private BaseEmployeeCategoryHistoryDSLocal employeeCategoryHistoryDSBean;
    @EJB
    private BaseUOMDetailedConversionTableLocal uomDetailedConversionBean;
    @EJB
    private BaseUOMDetailedConversionTableDSLocal uomDetailedConversionDSBean;
    @EJB
    private BaseLanguageLocal languageBean;
    @EJB
    private BaseDBLogGenericReportLocal dbLogGenericReportBean;
    @EJB
    private BaseWebPortalUsersDSLocal webPortalUsersDSBean;
    @EJB
    private EMCSMSManagerLocal smsManagerBean;
    @EJB
    private BaseSMSTemplateLocal smsTemplateBean;
    @EJB
    private BaseQueryTableLocal queryBean;
    @EJB
    private BaseAutoEmailSMSLocal autoEmailSMSBean;
    @EJB
    private BaseQueryActionTableLocal queryActionBean;
    @EJB
    private BaseReportSchedulingLocal reportSchedulingBean;
    @EJB
    private BaseTemplateDocumentsLocal templateDocumentsBean;
    @EJB
    private SupportLogicLocal supportLogicBean;

    public BaseMethodMapperBean() {
    }

    @Override
    public List mapMethodBase(EMCCommandClass cmd, List<Object> dataList, EMCUserData userData) throws EMCEntityBeanException {
        List<Object> theDataList = new ArrayList();
        EMCCommandClass retCmd = new EMCCommandClass();
        retCmd.setCommandId(EMCCommands.CLIENT_GENERAL_COMMAND.getId());
        retCmd.setModuleNumber(enumEMCModules.BASE.getId());
        retCmd.setMethodId(ClientWorkFlowMethods.GENERAL_METHOD.toString());

        switch (ServerBaseMethods.fromString(cmd.getMethodId())) {
            //Base users
            case INSERT_USERTABLE:
                theDataList.add(usersBean.insert((Usertable) dataList.get(1), userData));
                break;
            case UPDATE_USERTABLE:
                theDataList.add(usersBean.update((Usertable) dataList.get(1), userData));
                break;
            case DELETE_USERTABLE:
                theDataList.add(usersBean.delete((Usertable) dataList.get(1), userData));
                break;
            case GETNUMROWS_USERTABLE:
                theDataList.add(usersBean.getNumRows(Usertable.class, userData));
                break;
            case GETDATA_USERTABLE:
                theDataList = (List<Object>) usersBean.getDataInRange(Usertable.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_USERTABLE:
                theDataList.add(usersBean.validateField(dataList.get(1).toString(), (Usertable) dataList.get(2), userData));
                break;
            case TESTLOGIN:
                theDataList = usersBean.testLogin(dataList.get(1).toString(), dataList.get(2).toString(), userData);
                break;
            case INITDATA:
                theDataList.add(usersBean.initData());
                break;
            case USER_COMPANY_CHANGED:
                theDataList = usersBean.userCompanyChanged(dataList.get(1).toString(), dataList.get(2).toString(), userData);
                break;
            case TESTADMINLOGIN:
                theDataList.add(usersBean.testAdminLogin((String) dataList.get(1), (String) dataList.get(2), userData));
                break;
            case GET_REPORT_HEADER:
                theDataList.add(baseDocuRefBean.getReportHeader((EMCQuery) dataList.get(1), (String) dataList.get(2), (String) dataList.get(3), userData));
                break;
            case ADD_LETTER_HEAD:
                theDataList.add(baseCompanyBean.createLetterHead((Long) dataList.get(1), userData));
                break;
            //Base company
            case INSERT_BASECOMPANYTABLE:
                theDataList.add(baseCompanyBean.insert((BaseCompanyTable) dataList.get(1), userData));
                break;
            case UPDATE_BASECOMPANYTABLE:
                theDataList.add(baseCompanyBean.update((BaseCompanyTable) dataList.get(1), userData));
                break;
            case DELETE_BASECOMPANYTABLE:
                theDataList.add(baseCompanyBean.delete((BaseCompanyTable) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASECOMPANYTABLE:
                theDataList.add(baseCompanyBean.getNumRows(BaseCompanyTable.class, userData));
                break;
            case GETDATA_BASECOMPANYTABLE:
                theDataList = (List<Object>) baseCompanyBean.getDataInRange(BaseCompanyTable.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASECOMPANYTABLE:
                theDataList.add(baseCompanyBean.validateField(dataList.get(1).toString(), (BaseCompanyTable) dataList.get(2), userData));
                break;
            //Base employee
            case INSERT_BASEEMPLOYEETABLE:
                theDataList.add(baseEmployeeBean.insert((BaseEmployeeTable) dataList.get(1), userData));
                break;
            case UPDATE_BASEEMPLOYEETABLE:
                theDataList.add(baseEmployeeBean.update((BaseEmployeeTable) dataList.get(1), userData));
                break;
            case DELETE_BASEEMPLOYEETABLE:
                theDataList.add(baseEmployeeBean.delete((BaseEmployeeTable) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEEMPLOYEETABLE:
                theDataList.add(baseEmployeeBean.getNumRows(BaseEmployeeTable.class, userData));
                break;
            case GETDATA_BASEEMPLOYEETABLE:
                theDataList = (List<Object>) baseEmployeeBean.getDataInRange(BaseEmployeeTable.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEEMPLOYEETABLE:
                theDataList.add(baseEmployeeBean.validateField(dataList.get(1).toString(), (BaseEmployeeTable) dataList.get(2), userData));
                break;
            case FIND_EMPLOYEE:
                theDataList.add(baseEmployeeBean.findEmployee(dataList.get(1).toString(), userData));
                break;
            case FIND_BIRTHDAYS:
                theDataList = baseEmployeeBean.findBirthdays(userData);
                break;
            case FIND_EMPLOYEENAMEANDSURNAME:
                theDataList.add(baseEmployeeBean.findEmployeeNameAndSurname(dataList.get(1).toString(), userData));
                break;
            case FIND_EMPLOYEESOFMANAGER:
                theDataList = baseEmployeeBean.findEmployeesOfManager(userData);
                break;
            case FIX_EMP_ID:
                baseEmployeeBean.fixEmployeeIds(userData);
                break;
            case CHECK_ACCESS_GROUP:
                theDataList.add(baseEmployeeBean.checkAccessGroup(userData));
                break;
            case CREATE_WEB_USER_FOR_EMP:
                theDataList.add(baseEmployeeBean.createWebPortalUser((BaseEmployeeTable) dataList.get(1), userData));
                break;
            case CREATE_SETA_EXPORT_FILE:
                theDataList.add(baseEmployeeBean.createSETAExportFile((EMCQuery) dataList.get(1), userData));
                break;
            //Base docuref
            case INSERT_BASEDOCUREFTABLE:
                theDataList.add(baseDocuRefBean.insert((BaseDocuRefTable) dataList.get(1), userData));
                break;
            case UPDATE_BASEDOCUREFTABLE:
                theDataList.add(baseDocuRefBean.update((BaseDocuRefTable) dataList.get(1), userData));
                break;
            case DELETE_BASEDOCUREFTABLE:
                theDataList.add(baseDocuRefBean.delete((BaseDocuRefTable) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEDOCUREFTABLE:
                theDataList.add(baseDocuRefBean.getNumRows(BaseDocuRefTable.class, userData));
                break;
            case GETDATA_BASEDOCUREFTABLE:
                theDataList = (List<Object>) baseDocuRefBean.getDataInRange(BaseDocuRefTable.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEDOCUREFTABLE:
                theDataList.add(baseDocuRefBean.validateField(dataList.get(1).toString(), (BaseDocuRefTable) dataList.get(2), userData));
                break;
            case ISDOCUMENTATTACHED:
                theDataList.add(baseDocuRefBean.isDocumentAttached(dataList.get(1).toString(), dataList.get(2).toString(), userData));
                break;
            case ISDOCUMENTOFTYPEATTACHED:
                theDataList.add(baseDocuRefBean.isDocumentOfTypeAttached(dataList.get(1).toString(), dataList.get(2).toString(), dataList.get(3).toString(), userData));
                break;
            case FIX_DATA:
                baseDocuRefBean.fixData(userData);
                break;
            case CREATE_SPECIFIC_ATT:
                theDataList.add(baseDocuRefBean.createSpecificAttachment((Long) dataList.get(1), dataList.get(2).toString(), dataList.get(3).toString(), userData));
                break;
            case ADD_COMPANY_LOGO_PATH:
                theDataList.add(baseCompanyBean.createCompanyLogoRef((Long) dataList.get(1), userData));
                break;
            case CREATE_WEB_ATT:
                theDataList.add(baseDocuRefBean.createWebAttachment((FileUploadHelper) dataList.get(1), userData));
                break;
            //Base license
            case INSERT_BASELICENCETABLE:
                theDataList.add(baseLicenceBean.insert((BaseLicenceTable) dataList.get(1), userData));
                break;
            case UPDATE_BASELICENCETABLE:
                theDataList.add(baseLicenceBean.update((BaseLicenceTable) dataList.get(1), userData));
                break;
            case DELETE_BASELICENCETABLE:
                theDataList.add(baseLicenceBean.delete((BaseLicenceTable) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASELICENCETABLE:
                theDataList.add(baseLicenceBean.getNumRows(BaseLicenceTable.class, userData));
                break;
            case GETDATA_BASELICENCETABLE:
                theDataList = (List<Object>) baseLicenceBean.getDataInRange(BaseLicenceTable.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASELICENCETABLE:
                theDataList.add(baseLicenceBean.validateField(dataList.get(1).toString(), (BaseLicenceTable) dataList.get(2), userData));
                break;
            case GET_SYSTEM_ID:
                theDataList.add(baseLicenceBean.getSystemID(userData));
                break;
            case VALIDATE_AUTH_KEY:
                theDataList.add(baseLicenceBean.validateAuthKey((String) dataList.get(1), userData));
                break;
            //Base postal codes 
            case INSERT_BASEPOSTALCODES:
                theDataList.add(postalCodesBean.insert((BasePostalCodes) dataList.get(1), userData));
                break;
            case UPDATE_BASEPOSTALCODES:
                theDataList.add(postalCodesBean.update((BasePostalCodes) dataList.get(1), userData));
                break;
            case DELETE_BASEPOSTALCODES:
                theDataList.add(postalCodesBean.delete((BasePostalCodes) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEPOSTALCODES:
                theDataList.add(postalCodesBean.getNumRows(BasePostalCodes.class, userData));
                break;
            case GETDATA_BASEPOSTALCODES:
                theDataList = (List<Object>) postalCodesBean.getDataInRange(BasePostalCodes.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEPOSTALCODES:
                theDataList.add(postalCodesBean.validateField(dataList.get(1).toString(), (BasePostalCodes) dataList.get(2), userData));
                break;
            //Base units of measure
            case INSERT_BASEUNITSOFMEASURE:
                theDataList.add(unitsOfMeasureBean.insert((BaseUnitsOfMeasure) dataList.get(1), userData));
                break;
            case UPDATE_BASEUNITSOFMEASURE:
                theDataList.add(unitsOfMeasureBean.update((BaseUnitsOfMeasure) dataList.get(1), userData));
                break;
            case DELETE_BASEUNITSOFMEASURE:
                theDataList.add(unitsOfMeasureBean.delete((BaseUnitsOfMeasure) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEUNITSOFMEASURE:
                theDataList.add(unitsOfMeasureBean.getNumRows(BaseUnitsOfMeasure.class, userData));
                break;
            case GETDATA_BASEUNITSOFMEASURE:
                theDataList = (List<Object>) unitsOfMeasureBean.getDataInRange(BaseUnitsOfMeasure.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEUNITSOFMEASURE:
                theDataList.add(unitsOfMeasureBean.validateField(dataList.get(1).toString(), (BaseUnitsOfMeasure) dataList.get(2), userData));
                break;
            //Base system tables
            case INSERT_BASESYSTEMTABLE:
                theDataList.add(systemTablesBean.insert((BaseSystemTable) dataList.get(1), userData));
                break;
            case UPDATE_BASESYSTEMTABLE:
                theDataList.add(systemTablesBean.update((BaseSystemTable) dataList.get(1), userData));
                break;
            case DELETE_BASESYSTEMTABLE:
                theDataList.add(systemTablesBean.delete((BaseSystemTable) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASESYSTEMTABLE:
                theDataList.add(systemTablesBean.getNumRows(BaseSystemTable.class, userData));
                break;
            case GETDATA_BASESYSTEMTABLE:
                theDataList = (List<Object>) systemTablesBean.getDataInRange(BaseSystemTable.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASESYSTEMTABLE:
                theDataList.add(systemTablesBean.validateField(dataList.get(1).toString(), (BaseSystemTable) dataList.get(2), userData));
                break;
            case REFRESS_BASESYSTEMTABLE:
                theDataList.add(systemTableLogicBean.refresh());
                break;
            //Base file paths
            case INSERT_BASEFILEPATHS:
                theDataList.add(filePathsBean.insert((BaseFilePaths) dataList.get(1), userData));
                break;
            case UPDATE_BASEFILEPATHS:
                theDataList.add(filePathsBean.update((BaseFilePaths) dataList.get(1), userData));
                break;
            case DELETE_BASEFILEPATHS:
                theDataList.add(filePathsBean.delete((BaseFilePaths) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEFILEPATHS:
                theDataList.add(filePathsBean.getNumRows(BaseFilePaths.class, userData));
                break;
            case GETDATA_BASEFILEPATHS:
                theDataList = (List<Object>) filePathsBean.getDataInRange(BaseFilePaths.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEFILEPATHS:
                theDataList.add(filePathsBean.validateField(dataList.get(1).toString(), (BaseFilePaths) dataList.get(2), userData));
                break;
            //Online users
            case UPDATE_NO_OF_USERS:
                onlineUsersBean.updateNumberOfUsers(userData);
                break;
            case CLOSE_SESSION:
                onlineUsersBean.closeSession((Long) dataList.get(1));
                break;
            case CHECKIN:
                EMCCommandClass clientCmd = onlineUsersBean.checkIn(dataList.get(1).toString(), userData);
                if (clientCmd != null) {
                    retCmd = clientCmd;
                }
                break;
            case ADD_MESSAGE:
                onlineUsersBean.addMessage((enumLogTypes) dataList.get(1), dataList.get(2).toString(), userData);
                break;
            case GET_SESSIONS:
                theDataList = util.convertToObjectList(onlineUsersBean.getSessions());
                break;
            case GET_SESSION:
                theDataList.add(onlineUsersBean.getSession((Long) dataList.get(1)));
                break;
            case GET_MESSAGES:
                theDataList = util.convertToObjectList(onlineUsersBean.getMessages(userData));
                break;
            case GET_EMCSESSION_NUMBER:
                theDataList.add(onlineUsersBean.getEMCSessionNumber(dataList.get(1).toString(), dataList.get(2).toString(), userData));
                break;
            case ADMIN_MESSAGE_TO_USER:
                onlineUsersBean.setAdminMessageForUser(Long.parseLong(dataList.get(1).toString()), dataList.get(2).toString(), userData);
                break;
            case ADMIN_MESSAGE_TO_ALL_USERS:
                onlineUsersBean.setAdminMessageForAllUsers(dataList.get(1).toString(), userData);
                break;
            case KILL_USER_SESSION:
                onlineUsersBean.killSession(Long.parseLong(dataList.get(1).toString()), userData);
                break;
            case KILL_ALL_SESSIONS:
                onlineUsersBean.killAllSessions(userData);
                break;
            case ADDWEBUSERSESSION:
                onlineUsersBean.addWebsiteUserMessageList(userData);
                break;
            case ENDWEBUSERSESSION:
                onlineUsersBean.removeWebsiteUserMessageList(userData);
                break;
            //Session
            case INSERT_EMCSESSION:
                theDataList.add(EMCSessionBean.insert((EMCSession) dataList.get(1), userData));
                break;
            case UPDATE_EMCSESSION:
                theDataList.add(EMCSessionBean.update((EMCSession) dataList.get(1), userData));
                break;
            case DELETE_EMCSESSION:
                theDataList.add(EMCSessionBean.delete((EMCSession) dataList.get(1), userData));
                break;
            case GETNUMROWS_EMCSESSION:
                theDataList.add(EMCSessionBean.getNumRows(EMCSession.class, userData));
                break;
            case GETDATA_EMCSESSION:
                theDataList = (List<Object>) EMCSessionBean.getDataInRange(EMCSession.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_EMCSESSION:
                theDataList.add(EMCSessionBean.validateField(dataList.get(1).toString(), (EMCSession) dataList.get(2), userData));
                break;
            //Base Number Sequence
            case INSERT_BASENUMBERSEQUENCE:
                theDataList.add(numberSequenceBean.insert((BaseNumberSequence) dataList.get(1), userData));
                break;
            case UPDATE_BASENUMBERSEQUENCE:
                theDataList.add(numberSequenceBean.update((BaseNumberSequence) dataList.get(1), userData));
                break;
            case DELETE_BASENUMBERSEQUENCE:
                theDataList.add(numberSequenceBean.delete((BaseNumberSequence) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASENUMBERSEQUENCE:
                theDataList.add(numberSequenceBean.getNumRows(BaseNumberSequence.class, userData));
                break;
            case GETDATA_BASENUMBERSEQUENCE:
                theDataList = (List<Object>) numberSequenceBean.getDataInRange(BaseNumberSequence.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASENUMBERSEQUENCE:
                theDataList.add(numberSequenceBean.validateField(dataList.get(1).toString(), (BaseNumberSequence) dataList.get(2), userData));
                break;
            case POPULATE_MODULE_ID:
                numberSequenceBean.populateModuleId(userData);
                break;
            case REGENERATE_RANDOM_NUMBERS:
                theDataList.add(numberSequenceBean.regenerateRandomNumbers((BaseNumberSequence) dataList.get(1), userData));
                break;
            //BaseAvailableSequenceNumbers
            case INSERT_BASEAVAILABLESEQUENCENUMBERS:
                theDataList.add(availableSequenceNumbersBean.insert((BaseAvailableSequenceNumbers) dataList.get(1), userData));
                break;
            case UPDATE_BASEAVAILABLESEQUENCENUMBERS:
                theDataList.add(availableSequenceNumbersBean.update((BaseAvailableSequenceNumbers) dataList.get(1), userData));
                break;
            case DELETE_BASEAVAILABLESEQUENCENUMBERS:
                theDataList.add(availableSequenceNumbersBean.delete((BaseAvailableSequenceNumbers) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEAVAILABLESEQUENCENUMBERS:
                theDataList.add(availableSequenceNumbersBean.getNumRows(BaseAvailableSequenceNumbers.class, userData));
                break;
            case GETDATA_BASEAVAILABLESEQUENCENUMBERS:
                theDataList = (List<Object>) availableSequenceNumbersBean.getDataInRange(BaseAvailableSequenceNumbers.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEAVAILABLESEQUENCENUMBERS:
                theDataList.add(availableSequenceNumbersBean.validateField(dataList.get(1).toString(), (BaseAvailableSequenceNumbers) dataList.get(2), userData));
                break;
            //Tables
            case GET_EMCTABLES:
                theDataList = tablesBean.getEMCTables(userData);
                break;
            case TEST_TABLE_RELATIONS:
                tablesBean.testRelations(userData);
                break;
            case GET_EMC_TABLES_AND_LABELS:
                theDataList.add(tablesBean.getEMCTablesAndLabels(userData));
                break;
            case GET_EMC_MODULE_TABLES_AND_LABELS:
                theDataList.add(tablesBean.getEMCTablesAndLabels(dataList.get(1).toString(), userData));
                break;
            case GET_EMC_NUM_SEQ_TABLES:
                theDataList.add(tablesBean.getNumberSequenceTables(userData));
                break;
            case GET_EMC_MODULE_NUM_SEQ_TABLES:
                theDataList.add(tablesBean.getNumberSequenceTables(dataList.get(1).toString(), userData));
                break;
            case SEARCH_DB_FOR_VALUE:
                theDataList.add(tablesBean.findAllTablesWithValue(dataList.get(1).toString(), (Boolean) dataList.get(2), (String) dataList.get(3), userData));
                break;
            //Base UOM Conversion Table
            case INSERT_BASEUOMCONVERSIONTABLE:
                theDataList.add(uomConversionBean.insert((BaseUOMConversionTable) dataList.get(1), userData));
                break;
            case UPDATE_BASEUOMCONVERSIONTABLE:
                theDataList.add(uomConversionBean.update((BaseUOMConversionTable) dataList.get(1), userData));
                break;
            case DELETE_BASEUOMCONVERSIONTABLE:
                theDataList.add(uomConversionBean.delete((BaseUOMConversionTable) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEUOMCONVERSIONTABLE:
                theDataList.add(uomConversionBean.getNumRows(BaseUOMConversionTable.class, userData));
                break;
            case GETDATA_BASEUOMCONVERSIONTABLE:
                theDataList = (List<Object>) uomConversionBean.getDataInRange(BaseUOMConversionTable.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEUOMCONVERSIONTABLE:
                theDataList.add(uomConversionBean.validateField(dataList.get(1).toString(), (BaseUOMConversionTable) dataList.get(2), userData));
                break;
            //BaseCountries
            case INSERT_BASECOUNTRIES:
                theDataList.add(baseCountriesBean.insert((BaseCountries) dataList.get(1), userData));
                break;
            case UPDATE_BASECOUNTRIES:
                theDataList.add(baseCountriesBean.update((BaseCountries) dataList.get(1), userData));
                break;
            case DELETE_BASECOUNTRIES:
                theDataList.add(baseCountriesBean.delete((BaseCountries) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASECOUNTRIES:
                theDataList.add(baseCountriesBean.getNumRows(BaseCountries.class, userData));
                break;
            case GETDATA_BASECOUNTRIES:
                theDataList = (List<Object>) baseCountriesBean.getDataInRange(BaseCountries.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASECOUNTRIES:
                theDataList.add(baseCountriesBean.validateField(dataList.get(1).toString(), (BaseCountries) dataList.get(2), userData));
                break;
            //Base Report User Query Table
            case INSERT_BASEREPORTUSERQUERYTABLE:
                theDataList.add(userQueryTableBean.insert((BaseReportUserQueryTable) dataList.get(1), userData));
                break;
            case UPDATE_BASEREPORTUSERQUERYTABLE:
                theDataList.add(userQueryTableBean.update((BaseReportUserQueryTable) dataList.get(1), userData));
                break;
            case DELETE_BASEREPORTUSERQUERYTABLE:
                theDataList.add(userQueryTableBean.delete((BaseReportUserQueryTable) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEREPORTUSERQUERYTABLE:
                theDataList.add(userQueryTableBean.getNumRows(BaseReportUserQueryTable.class, userData));
                break;
            case GETDATA_BASEREPORTUSERQUERYTABLE:
                theDataList = (List<Object>) userQueryTableBean.getDataInRange(BaseReportUserQueryTable.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEREPORTUSERQUERYTABLE:
                theDataList.add(userQueryTableBean.validateField(dataList.get(1).toString(), (BaseReportUserQueryTable) dataList.get(2), userData));
                break;
            case SAVE_USER_DEFAULT_QUERY:
                theDataList.add(userQueryTableBean.saveDefaultQueryForUser(dataList.subList(1, dataList.size()), userData));
                break;
            case GET_USER_SAVED_QUERIES:
                theDataList = userQueryTableBean.getUserSavedQueries((String) dataList.get(1), userData);
                break;
            case SAVE_USER_QUERY:
                theDataList.add(userQueryTableBean.saveUserQuery((String) dataList.get(1), (String) dataList.get(2), (String) dataList.get(3), userData));
                break;
            case GET_LAST_QUERY_FOR_USER:
                theDataList.add(userQueryTableBean.getLastQueryForUser((String) dataList.get(1), userData));
                break;
            case DELETE_QUERY:
                theDataList.add(userQueryTableBean.deleteQuery((String) dataList.get(1), (String) dataList.get(2), userData));
                break;
            case CHECK_QUERY_EXISTENCE:
                theDataList.add(userQueryTableBean.checkQueryExistance((String) dataList.get(1), (String) dataList.get(2), userData));
                break;
            case CONSTRUCT_AND_RETURN_QUERY:
                theDataList.add(userQueryTableBean.constructAndReturnQuery((String) dataList.get(1), (String) dataList.get(2), userData));
                break;
            //Base Report Order Table
            case INSERT_BASEREPORTORDERTABLE:
                theDataList.add(reportOrderTableBean.insert((BaseReportOrderTable) dataList.get(1), userData));
                break;
            case UPDATE_BASEREPORTORDERTABLE:
                theDataList.add(reportOrderTableBean.update((BaseReportOrderTable) dataList.get(1), userData));
                break;
            case DELETE_BASEREPORTORDERTABLE:
                theDataList.add(reportOrderTableBean.delete((BaseReportOrderTable) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEREPORTORDERTABLE:
                theDataList.add(reportOrderTableBean.getNumRows(BaseReportOrderTable.class, userData));
                break;
            case GETDATA_BASEREPORTORDERTABLE:
                theDataList = (List<Object>) reportOrderTableBean.getDataInRange(BaseReportOrderTable.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEREPORTORDERTABLE:
                theDataList.add(reportOrderTableBean.validateField(dataList.get(1).toString(), (BaseReportOrderTable) dataList.get(2), userData));
                break;
            //Base Report Where Table
            case INSERT_BASEREPORTWHERETABLE:
                theDataList.add(reportWhereTableBean.insert((BaseReportWhereTable) dataList.get(1), userData));
                break;
            case UPDATE_BASEREPORTWHERETABLE:
                theDataList.add(reportWhereTableBean.update((BaseReportWhereTable) dataList.get(1), userData));
                break;
            case DELETE_BASEREPORTWHERETABLE:
                theDataList.add(reportWhereTableBean.delete((BaseReportWhereTable) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEREPORTWHERETABLE:
                theDataList.add(reportWhereTableBean.getNumRows(BaseReportWhereTable.class, userData));
                break;
            case GETDATA_BASEREPORTWHERETABLE:
                theDataList = (List<Object>) reportWhereTableBean.getDataInRange(BaseReportWhereTable.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEREPORTWHERETABLE:
                theDataList.add(reportWhereTableBean.validateField(dataList.get(1).toString(), (BaseReportWhereTable) dataList.get(2), userData));
                break;

            //Default report bean    
            case GET_DEFAULTREPORT:
                theDataList = defaultReportBean.getReportResult(dataList, userData);
                break;
            case GET_COMPANY_REPORT_INFO:
                theDataList.add(defaultReportBean.getCompanyReportInformation(userData));
                break;
            //Import Tool
            case IMPORT_RECORD:
                theDataList.add(importBean.importRecord(dataList, userData));
                break;
            //BaseReportPrintOptions
            case INSERT_BASEREPORTPRINTOPTIONS:
                theDataList.add(printOptionsBean.insert((BaseReportPrintOptions) dataList.get(1), userData));
                break;
            case UPDATE_BASEREPORTPRINTOPTIONS:
                theDataList.add(printOptionsBean.update((BaseReportPrintOptions) dataList.get(1), userData));
                break;
            case DELETE_BASEREPORTPRINTOPTIONS:
                theDataList.add(printOptionsBean.delete((BaseReportPrintOptions) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEREPORTPRINTOPTIONS:
                theDataList.add(printOptionsBean.getNumRows(BaseReportPrintOptions.class, userData));
                break;
            case GETDATA_BASEREPORTPRINTOPTIONS:
                theDataList = (List<Object>) printOptionsBean.getDataInRange(BaseReportPrintOptions.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEREPORTPRINTOPTIONS:
                theDataList.add(printOptionsBean.validateField(dataList.get(1).toString(), (BaseReportPrintOptions) dataList.get(2), userData));
                break;
            case FIND_PRINT_OPTIONS:
                theDataList.add(printOptionsBean.findPrinterOptions(dataList.get(1).toString(), dataList.get(2).toString(), userData));
                break;
            //BaseCalendar
            case INSERT_BASECALENDAR:
                theDataList.add(calendarBean.insert((BaseCalendar) dataList.get(1), userData));
                break;
            case UPDATE_BASECALENDAR:
                theDataList.add(calendarBean.update((BaseCalendar) dataList.get(1), userData));
                break;
            case DELETE_BASECALENDAR:
                theDataList.add(calendarBean.delete((BaseCalendar) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASECALENDAR:
                theDataList.add(calendarBean.getNumRows(BaseCalendar.class, userData));
                break;
            case GETDATA_BASECALENDAR:
                theDataList = (List<Object>) calendarBean.getDataInRange(BaseCalendar.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASECALENDAR:
                theDataList.add(calendarBean.validateField(dataList.get(1).toString(), (BaseCalendar) dataList.get(2), userData));
                break;
            case COPY_CALENDAR:
                calendarBean.copyCalendar(dataList.get(1).toString(), dataList.get(2).toString(), userData);
                break;
            case TEST_CALENDAR:
                calendarBean.testCalendar(dataList.get(1).toString(), (Date) dataList.get(2), (Integer) dataList.get(3), userData);
                break;
            //BaseCalendarExceptions
            case INSERT_BASECALENDAREXCEPTIONS:
                theDataList.add(calendarExceptionBean.insert((BaseCalendarExceptions) dataList.get(1), userData));
                break;
            case UPDATE_BASECALENDAREXCEPTIONS:
                theDataList.add(calendarExceptionBean.update((BaseCalendarExceptions) dataList.get(1), userData));
                break;
            case DELETE_BASECALENDAREXCEPTIONS:
                theDataList.add(calendarExceptionBean.delete((BaseCalendarExceptions) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASECALENDAREXCEPTIONS:
                theDataList.add(calendarExceptionBean.getNumRows(BaseCalendarExceptions.class, userData));
                break;
            case GETDATA_BASECALENDAREXCEPTIONS:
                theDataList = (List<Object>) calendarExceptionBean.getDataInRange(BaseCalendarExceptions.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASECALENDAREXCEPTIONS:
                theDataList.add(calendarExceptionBean.validateField(dataList.get(1).toString(), (BaseCalendarExceptions) dataList.get(2), userData));
                break;
            case GET_MASS_UPDATE_DATA:
                theDataList.add(calendarExceptionBean.getMassData(userData));
                break;
            case DO_MASS_UPDATE:
                calendarExceptionBean.odMassUpdate((Long) dataList.get(1), (List) dataList.get(2), userData);
                break;
            //BaseCalendarShifts
            case INSERT_BASECALENDARSHIFTS:
                theDataList.add(calendarShiftBean.insert((BaseCalendarShifts) dataList.get(1), userData));
                break;
            case UPDATE_BASECALENDARSHIFTS:
                theDataList.add(calendarShiftBean.update((BaseCalendarShifts) dataList.get(1), userData));
                break;
            case DELETE_BASECALENDARSHIFTS:
                theDataList.add(calendarShiftBean.delete((BaseCalendarShifts) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASECALENDARSHIFTS:
                theDataList.add(calendarShiftBean.getNumRows(BaseCalendarShifts.class, userData));
                break;
            case GETDATA_BASECALENDARSHIFTS:
                theDataList = (List<Object>) calendarShiftBean.getDataInRange(BaseCalendarShifts.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASECALENDARSHIFTS:
                theDataList.add(calendarShiftBean.validateField(dataList.get(1).toString(), (BaseCalendarShifts) dataList.get(2), userData));
                break;
            //BaseDBLog
            case INSERT_BASEDBLOG:
                theDataList.add(dbLogBean.insert((BaseDBLog) dataList.get(1), userData));
                break;
            case UPDATE_BASEDBLOG:
                theDataList.add(dbLogBean.update((BaseDBLog) dataList.get(1), userData));
                break;
            case DELETE_BASEDBLOG:
                theDataList.add(dbLogBean.delete((BaseDBLog) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEDBLOG:
                theDataList.add(dbLogBean.getNumRows(BaseDBLog.class, userData));
                break;
            case GETDATA_BASEDBLOG:
                theDataList = (List<Object>) dbLogBean.getDataInRange(BaseDBLog.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEDBLOG:
                theDataList.add(dbLogBean.validateField(dataList.get(1).toString(), (BaseDBLog) dataList.get(2), userData));
                break;
            case CLEAR_DB_LOG:
                theDataList.add(dbLogBean.clearDBLog((Date) dataList.get(1), userData));
                break;
            case AUTO_EMAIL_SMS:
                theDataList.add(autoEmailSMSBean.fireAutoSend(userData));
                break;
            //BaseDBLogSetup
            case INSERT_BASEDBLOGSETUP:
                theDataList.add(dbLogSetupBean.insert((BaseDBLogSetup) dataList.get(1), userData));
                break;
            case UPDATE_BASEDBLOGSETUP:
                theDataList.add(dbLogSetupBean.update((BaseDBLogSetup) dataList.get(1), userData));
                break;
            case DELETE_BASEDBLOGSETUP:
                theDataList.add(dbLogSetupBean.delete((BaseDBLogSetup) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEDBLOGSETUP:
                theDataList.add(dbLogSetupBean.getNumRows(BaseDBLogSetup.class, userData));
                break;
            case GETDATA_BASEDBLOGSETUP:
                theDataList = (List<Object>) dbLogSetupBean.getDataInRange(BaseDBLogSetup.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEDBLOGSETUP:
                theDataList.add(dbLogSetupBean.validateField(dataList.get(1).toString(), (BaseDBLogSetup) dataList.get(2), userData));
                break;
            case REFRESS:
                theDataList.add(dbLogSetupBean.refress(userData));
                break;
            //Base Messages
            case INSERT_BASEMESSAGES:
                theDataList.add(messagesBean.insert((BaseMessages) dataList.get(1), userData));
                break;
            case UPDATE_BASEMESSAGES:
                theDataList.add(messagesBean.update((BaseMessages) dataList.get(1), userData));
                break;
            case DELETE_BASEMESSAGES:
                theDataList.add(messagesBean.delete((BaseMessages) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEMESSAGES:
                theDataList.add(messagesBean.getNumRows(BaseMessages.class, userData));
                break;
            case GETDATA_BASEMESSAGES:
                theDataList = (List<Object>) messagesBean.getDataInRange(BaseMessages.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEMESSAGES:
                theDataList.add(messagesBean.validateField(dataList.get(1).toString(), (BaseMessages) dataList.get(2), userData));
                break;
            case POPULATE_BASE_MESSAGES:
                theDataList.add(messagesBean.populateServerMessages((String) dataList.get(1), userData));
                break;
            case CLONE_RECORD:
                theDataList.add(cloneBean.cloneRecord((EMCEntityClass) dataList.get(1), dataList.size() > 2 ? dataList.get(2) : null, userData));
                break;

            //Base User Permissions Table
            case INSERT_BASEUSERPERMISSIONSTABLE:
                theDataList.add(userPermissionsBean.insert((BaseUserPermissionsTable) dataList.get(1), userData));
                break;
            case UPDATE_BASEUSERPERMISSIONSTABLE:
                theDataList.add(userPermissionsBean.update((BaseUserPermissionsTable) dataList.get(1), userData));
                break;
            case DELETE_BASEUSERPERMISSIONSTABLE:
                theDataList.add(userPermissionsBean.delete((BaseUserPermissionsTable) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEUSERPERMISSIONSTABLE:
                theDataList.add(userPermissionsBean.getNumRows(BaseUserPermissionsTable.class, userData));
                break;
            case GETDATA_BASEUSERPERMISSIONSTABLE:
                theDataList = (List<Object>) userPermissionsBean.getDataInRange(BaseUserPermissionsTable.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEUSERPERMISSIONSTABLE:
                theDataList.add(userPermissionsBean.validateField(dataList.get(1).toString(), (BaseUserPermissionsTable) dataList.get(2), userData));
                break;
            case GET_PERMISSIONS_FOR_USER:
                theDataList.addAll(userPermissionsBean.getPermissionsForUser((String) dataList.get(1), userData));
                break;
            case COPY_USER_PERMISSIONS:
                theDataList.add(userPermissionsBean.copyPermissions((String) dataList.get(1), (String) dataList.get(2), userData));
                break;
            case VALIDATE_USER_ID:
                theDataList.add(permissionInfoBean.validateUserId(dataList.get(1).toString(), userData));
                break;
            case GET_PERMISSION_TREE_INFO:
                theDataList.add(permissionInfoBean.getPermissionInfoByUser(dataList.get(1).toString(), userData));
                break;
            case GET_DETAILED_PERMISSION_TREE_INFO:
                theDataList.add(permissionInfoBean.getDetailedPermissionInfoByUser(dataList.get(1).toString(), dataList.get(2).toString(), userData));
                break;
            case PRINT_PERMISSION_INFO:
                theDataList = permissionInfoReportBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            //BaseTimedOperations
            case INSERT_BASETIMEDOPERATIONS:
                theDataList.add(timedBean.insert((BaseTimedOperations) dataList.get(1), userData));
                break;
            case UPDATE_BASETIMEDOPERATIONS:
                theDataList.add(timedBean.update((BaseTimedOperations) dataList.get(1), userData));
                break;
            case DELETE_BASETIMEDOPERATIONS:
                theDataList.add(timedBean.delete((BaseTimedOperations) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASETIMEDOPERATIONS:
                theDataList.add(timedBean.getNumRows(BaseTimedOperations.class, userData));
                break;
            case GETDATA_BASETIMEDOPERATIONS:
                theDataList = (List<Object>) timedBean.getDataInRange(BaseTimedOperations.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASETIMEDOPERATIONS:
                theDataList.add(timedBean.validateField(dataList.get(1).toString(), (BaseTimedOperations) dataList.get(2), userData));
                break;
            case START_TIMED_OPERATION:
                timedBean.startTimedOperation(dataList.get(1).toString(), userData);
                break;
            case STOP_TIMED_OPERATION:
                timedBean.stopTimedOperation(dataList.get(1).toString(), userData);
                break;
            //Version Info
            case GET_SERVER_VERSION_INFO:
                theDataList.add(util.getServerVersionInfo());
                break;
            //BaseMailSetup
            case INSERT_BASEMAILSETUP:
                theDataList.add(mailSetupBean.insert((BaseMailSetup) dataList.get(1), userData));
                break;
            case UPDATE_BASEMAILSETUP:
                theDataList.add(mailSetupBean.update((BaseMailSetup) dataList.get(1), userData));
                break;
            case DELETE_BASEMAILSETUP:
                theDataList.add(mailSetupBean.delete((BaseMailSetup) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEMAILSETUP:
                theDataList.add(mailSetupBean.getNumRows(BaseMailSetup.class, userData));
                break;
            case GETDATA_BASEMAILSETUP:
                theDataList = (List<Object>) mailSetupBean.getDataInRange(BaseMailSetup.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEMAILSETUP:
                theDataList.add(mailSetupBean.validateField(dataList.get(1).toString(), (BaseMailSetup) dataList.get(2), userData));
                break;
            //BaseEmailTemplates
            case INSERT_BASEEMAILTEMPLATES:
                theDataList.add(templateBean.insert((BaseEmailTemplates) dataList.get(1), userData));
                break;
            case UPDATE_BASEEMAILTEMPLATES:
                theDataList.add(templateBean.update((BaseEmailTemplates) dataList.get(1), userData));
                break;
            case DELETE_BASEEMAILTEMPLATES:
                theDataList.add(templateBean.delete((BaseEmailTemplates) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEEMAILTEMPLATES:
                theDataList.add(templateBean.getNumRows(BaseEmailTemplates.class, userData));
                break;
            case GETDATA_BASEEMAILTEMPLATES:
                theDataList = (List<Object>) templateBean.getDataInRange(BaseEmailTemplates.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEEMAILTEMPLATES:
                theDataList.add(templateBean.validateField(dataList.get(1).toString(), (BaseEmailTemplates) dataList.get(2), userData));
                break;
            case CHECK_TEMPLATE:
                theDataList.add(templateBean.checkTemplate(dataList.get(1).toString(), userData));
                break;
            case FETCH_TEMPLATE:
                theDataList.add(templateBean.fetchTemplate(dataList.get(1).toString(), userData));
                break;
            //BaseEmailSignatures
            case INSERT_BASEEMAILSIGNATURES:
                theDataList.add(signatureBean.insert((BaseEmailSignatures) dataList.get(1), userData));
                break;
            case UPDATE_BASEEMAILSIGNATURES:
                theDataList.add(signatureBean.update((BaseEmailSignatures) dataList.get(1), userData));
                break;
            case DELETE_BASEEMAILSIGNATURES:
                theDataList.add(signatureBean.delete((BaseEmailSignatures) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEEMAILSIGNATURES:
                theDataList.add(signatureBean.getNumRows(BaseEmailSignatures.class, userData));
                break;
            case GETDATA_BASEEMAILSIGNATURES:
                theDataList = (List<Object>) signatureBean.getDataInRange(BaseEmailSignatures.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEEMAILSIGNATURES:
                theDataList.add(signatureBean.validateField(dataList.get(1).toString(), (BaseEmailSignatures) dataList.get(2), userData));
                break;

            //BaseWebPortalUsers
            case INSERT_BASEWEBPORTALUSERS:
                theDataList.add(webPortalUsersBean.insert((BaseWebPortalUsers) dataList.get(1), userData));
                break;
            case UPDATE_BASEWEBPORTALUSERS:
                theDataList.add(webPortalUsersBean.update((BaseWebPortalUsers) dataList.get(1), userData));
                break;
            case DELETE_BASEWEBPORTALUSERS:
                theDataList.add(webPortalUsersBean.delete((BaseWebPortalUsers) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEWEBPORTALUSERS:
                theDataList.add(webPortalUsersBean.getNumRows(BaseWebPortalUsers.class, userData));
                break;
            case GETDATA_BASEWEBPORTALUSERS:
                theDataList = (List<Object>) webPortalUsersBean.getDataInRange(BaseWebPortalUsers.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEWEBPORTALUSERS:
                theDataList.add(webPortalUsersBean.validateField(dataList.get(1).toString(), (BaseWebPortalUsers) dataList.get(2), userData));
                break;
            case LOGIN_WEBPORTALUSER:
                theDataList.add(webPortalUsersBean.loginWebPortalUser(String.valueOf(dataList.get(1)), String.valueOf(dataList.get(2)), userData));
                break;
            case ACTIVATE_WEB_USER:
                theDataList.add(webPortalUsersBean.activateWebUser((Long) dataList.get(1), userData));
                break;
            case DEACTIVATE_WEB_USER:
                theDataList.add(webPortalUsersBean.deactivateWebUser((Long) dataList.get(1), userData));
                break;
            case DEACTIVATE_ALL_WEB_USERS:
                theDataList.add(webPortalUsersBean.deactivateAllWebUsers(userData));
                break;
            case FIND_STUDENT_ID:
                theDataList.add(webPortalUsersBean.getStudentIdNumber(String.valueOf(dataList.get(1)), userData));
                break;
            //BaseParameters
            case INSERT_BASEPARAMETERS:
                theDataList.add(paramBean.insert((BaseParameters) dataList.get(1), userData));
                break;
            case UPDATE_BASEPARAMETERS:
                theDataList.add(paramBean.update((BaseParameters) dataList.get(1), userData));
                break;
            case DELETE_BASEPARAMETERS:
                theDataList.add(paramBean.delete((BaseParameters) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEPARAMETERS:
                theDataList.add(paramBean.getNumRows(BaseParameters.class, userData));
                break;
            case GETDATA_BASEPARAMETERS:
                theDataList = (List<Object>) paramBean.getDataInRange(BaseParameters.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case GET_BASE_PARAMETERS:
                theDataList.add(paramBean.getBaseParameters(userData));
                break;
            case VALIDATEFIELD_BASEPARAMETERS:
                theDataList.add(paramBean.validateField(dataList.get(1).toString(), (BaseParameters) dataList.get(2), userData));
                break;
            //BaseProvence
            case INSERT_BASEPROVENCE:
                theDataList.add(provenceBean.insert((BaseProvence) dataList.get(1), userData));
                break;
            case UPDATE_BASEPROVENCE:
                theDataList.add(provenceBean.update((BaseProvence) dataList.get(1), userData));
                break;
            case DELETE_BASEPROVENCE:
                theDataList.add(provenceBean.delete((BaseProvence) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEPROVENCE:
                theDataList.add(provenceBean.getNumRows(BaseProvence.class, userData));
                break;
            case GETDATA_BASEPROVENCE:
                theDataList = (List<Object>) provenceBean.getDataInRange(BaseProvence.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEPROVENCE:
                theDataList.add(provenceBean.validateField(dataList.get(1).toString(), (BaseProvence) dataList.get(2), userData));
                break;
            //BaseCity
            case INSERT_BASECITY:
                theDataList.add(cityBean.insert((BaseCity) dataList.get(1), userData));
                break;
            case UPDATE_BASECITY:
                theDataList.add(cityBean.update((BaseCity) dataList.get(1), userData));
                break;
            case DELETE_BASECITY:
                theDataList.add(cityBean.delete((BaseCity) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASECITY:
                theDataList.add(cityBean.getNumRows(BaseCity.class, userData));
                break;
            case GETDATA_BASECITY:
                theDataList = (List<Object>) cityBean.getDataInRange(BaseCity.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASECITY:
                theDataList.add(cityBean.validateField(dataList.get(1).toString(), (BaseCity) dataList.get(2), userData));
                break;
            //BaseSuburb
            case INSERT_BASESUBURB:
                theDataList.add(suburbBean.insert((BaseSuburb) dataList.get(1), userData));
                break;
            case UPDATE_BASESUBURB:
                theDataList.add(suburbBean.update((BaseSuburb) dataList.get(1), userData));
                break;
            case DELETE_BASESUBURB:
                theDataList.add(suburbBean.delete((BaseSuburb) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASESUBURB:
                theDataList.add(suburbBean.getNumRows(BaseSuburb.class, userData));
                break;
            case GETDATA_BASESUBURB:
                theDataList = (List<Object>) suburbBean.getDataInRange(BaseSuburb.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASESUBURB:
                theDataList.add(suburbBean.validateField(dataList.get(1).toString(), (BaseSuburb) dataList.get(2), userData));
                break;
            //BaseEmployeeAccessGroup
            case INSERT_BASEEMPLOYEEACCESSGROUP:
                theDataList.add(empAccessGroupBean.insert((BaseEmployeeAccessGroup) dataList.get(1), userData));
                break;
            case UPDATE_BASEEMPLOYEEACCESSGROUP:
                theDataList.add(empAccessGroupBean.update((BaseEmployeeAccessGroup) dataList.get(1), userData));
                break;
            case DELETE_BASEEMPLOYEEACCESSGROUP:
                theDataList.add(empAccessGroupBean.delete((BaseEmployeeAccessGroup) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEEMPLOYEEACCESSGROUP:
                theDataList.add(empAccessGroupBean.getNumRows(BaseEmployeeAccessGroup.class, userData));
                break;
            case GETDATA_BASEEMPLOYEEACCESSGROUP:
                theDataList = (List<Object>) empAccessGroupBean.getDataInRange(BaseEmployeeAccessGroup.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEEMPLOYEEACCESSGROUP:
                theDataList.add(empAccessGroupBean.validateField(dataList.get(1).toString(), (BaseEmployeeAccessGroup) dataList.get(2), userData));
                break;
            //BaseEmployeeAccessGroupEmployees
            case INSERT_BASEEMPLOYEEACCESSGROUPEMPLOYEES:
                theDataList.add(empAccessGroupEmpBean.insert((BaseEmployeeAccessGroupEmployees) dataList.get(1), userData));
                break;
            case UPDATE_BASEEMPLOYEEACCESSGROUPEMPLOYEES:
                theDataList.add(empAccessGroupEmpBean.update((BaseEmployeeAccessGroupEmployees) dataList.get(1), userData));
                break;
            case DELETE_BASEEMPLOYEEACCESSGROUPEMPLOYEES:
                theDataList.add(empAccessGroupEmpBean.delete((BaseEmployeeAccessGroupEmployees) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEEMPLOYEEACCESSGROUPEMPLOYEES:
                theDataList.add(empAccessGroupEmpBean.getNumRows(BaseEmployeeAccessGroupEmployees.class, userData));
                break;
            case GETDATA_BASEEMPLOYEEACCESSGROUPEMPLOYEES:
                theDataList = (List<Object>) empAccessGroupEmpBean.getDataInRange(BaseEmployeeAccessGroupEmployees.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEEMPLOYEEACCESSGROUPEMPLOYEES:
                theDataList.add(empAccessGroupEmpBean.validateField(dataList.get(1).toString(), (BaseEmployeeAccessGroupEmployees) dataList.get(2), userData));
                break;
            //BaseEmployeeAccessGroupEmployeesDS
            case INSERT_BASEEMPLOYEEACCESSGROUPEMPLOYEESDS:
                theDataList.add(empAccessGroupEmpDSBean.insert((BaseEmployeeAccessGroupEmployeesDS) dataList.get(1), userData));
                break;
            case UPDATE_BASEEMPLOYEEACCESSGROUPEMPLOYEESDS:
                theDataList.add(empAccessGroupEmpDSBean.update((BaseEmployeeAccessGroupEmployeesDS) dataList.get(1), userData));
                break;
            case DELETE_BASEEMPLOYEEACCESSGROUPEMPLOYEESDS:
                theDataList.add(empAccessGroupEmpDSBean.delete((BaseEmployeeAccessGroupEmployeesDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEEMPLOYEEACCESSGROUPEMPLOYEESDS:
                theDataList.add(empAccessGroupEmpDSBean.getNumRows(BaseEmployeeAccessGroupEmployeesDS.class, userData));
                break;
            case GETDATA_BASEEMPLOYEEACCESSGROUPEMPLOYEESDS:
                theDataList = (List<Object>) empAccessGroupEmpDSBean.getDataInRange(BaseEmployeeAccessGroupEmployeesDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEEMPLOYEEACCESSGROUPEMPLOYEESDS:
                theDataList.add(empAccessGroupEmpDSBean.validateField(dataList.get(1).toString(), (BaseEmployeeAccessGroupEmployeesDS) dataList.get(2), userData));
                break;
            //BaseTimeByDay
            case INSERT_BASETIMEBYDAY:
                theDataList.add(timeByDayBean.insert((BaseTimeByDay) dataList.get(1), userData));
                break;
            case UPDATE_BASETIMEBYDAY:
                theDataList.add(timeByDayBean.update((BaseTimeByDay) dataList.get(1), userData));
                break;
            case DELETE_BASETIMEBYDAY:
                theDataList.add(timeByDayBean.delete((BaseTimeByDay) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASETIMEBYDAY:
                theDataList.add(timeByDayBean.getNumRows(BaseTimeByDay.class, userData));
                break;
            case GETDATA_BASETIMEBYDAY:
                theDataList = (List<Object>) timeByDayBean.getDataInRange(BaseTimeByDay.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASETIMEBYDAY:
                theDataList.add(timeByDayBean.validateField(dataList.get(1).toString(), (BaseTimeByDay) dataList.get(2), userData));
                break;
            case GENERATE_TIMES:
                timeByDayBean.populateTimeByDays((Date) dataList.get(1), (Date) dataList.get(2), userData);
                break;
            //BasePermissionsTable
            case INSERT_BASEPERMISSIONSTABLE:
                theDataList.add(permissionsBean.insert((BasePermissionsTable) dataList.get(1), userData));
                break;
            case UPDATE_BASEPERMISSIONSTABLE:
                theDataList.add(permissionsBean.update((BasePermissionsTable) dataList.get(1), userData));
                break;
            case DELETE_BASEPERMISSIONSTABLE:
                theDataList.add(permissionsBean.delete((BasePermissionsTable) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEPERMISSIONSTABLE:
                theDataList.add(permissionsBean.getNumRows(BasePermissionsTable.class, userData));
                break;
            case GETDATA_BASEPERMISSIONSTABLE:
                theDataList = (List<Object>) permissionsBean.getDataInRange(BasePermissionsTable.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEPERMISSIONSTABLE:
                theDataList.add(permissionsBean.validateField(dataList.get(1).toString(), (BasePermissionsTable) dataList.get(2), userData));
                break;

            //Base Journal Definition Table
            case INSERT_BASEJOURNALDEFINITIONTABLE:
                theDataList.add(journalDefinitionBean.insert((BaseJournalDefinitionTable) dataList.get(1), userData));
                break;
            case UPDATE_BASEJOURNALDEFINITIONTABLE:
                theDataList.add(journalDefinitionBean.update((BaseJournalDefinitionTable) dataList.get(1), userData));
                break;
            case DELETE_BASEJOURNALDEFINITIONTABLE:
                theDataList.add(journalDefinitionBean.delete((BaseJournalDefinitionTable) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEJOURNALDEFINITIONTABLE:
                theDataList.add(journalDefinitionBean.getNumRows(BaseJournalDefinitionTable.class, userData));
                break;
            case GETDATA_BASEJOURNALDEFINITIONTABLE:
                theDataList = (List<Object>) journalDefinitionBean.getDataInRange(BaseJournalDefinitionTable.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEJOURNALDEFINITIONTABLE:
                theDataList.add(journalDefinitionBean.validateField(dataList.get(1).toString(), (BaseJournalDefinitionTable) dataList.get(2), userData));
                break;
            //InventoryJournalApprovalTable
            case INSERT_BASEJOURNALAPPROVALGROUPS:
                theDataList.add(journalApprovalGroupsBean.insert((BaseJournalApprovalGroups) dataList.get(1), userData));
                break;
            case UPDATE_BASEJOURNALAPPROVALGROUPS:
                theDataList.add(journalApprovalGroupsBean.update((BaseJournalApprovalGroups) dataList.get(1), userData));
                break;
            case DELETE_BASEJOURNALAPPROVALGROUPS:
                theDataList.add(journalApprovalGroupsBean.delete((BaseJournalApprovalGroups) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEJOURNALAPPROVALGROUPS:
                theDataList.add(journalApprovalGroupsBean.getNumRows(BaseJournalApprovalGroups.class, userData));
                break;
            case GETDATA_BASEJOURNALAPPROVALGROUPS:
                theDataList = (List<Object>) journalApprovalGroupsBean.getDataInRange(BaseJournalApprovalGroups.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEJOURNALAPPROVALGROUPS:
                theDataList.add(journalApprovalGroupsBean.validateField(dataList.get(1).toString(), (BaseJournalApprovalGroups) dataList.get(2), userData));
                break;
            //BaseJournalApprovalGroupEmployees
            case INSERT_BASEJOURNALAPPROVALGROUPEMPLOYEESDS:
                theDataList.add(journalGroupEmployeesBean.insert((BaseJournalApprovalGroupEmployees) dataList.get(1), userData));
                break;
            case UPDATE_BASEJOURNALAPPROVALGROUPEMPLOYEESDS:
                theDataList.add(journalGroupEmployeesBean.update((BaseJournalApprovalGroupEmployees) dataList.get(1), userData));
                break;
            case DELETE_BASEJOURNALAPPROVALGROUPEMPLOYEESDS:
                theDataList.add(journalGroupEmployeesBean.delete((BaseJournalApprovalGroupEmployees) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEJOURNALAPPROVALGROUPEMPLOYEESDS:
                theDataList.add(journalGroupEmployeesBean.getNumRows(BaseJournalApprovalGroupEmployees.class, userData));
                break;
            case GETDATA_BASEJOURNALAPPROVALGROUPEMPLOYEESDS:
                theDataList = (List<Object>) journalGroupEmployeesBean.getDataInRange(BaseJournalApprovalGroupEmployees.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEJOURNALAPPROVALGROUPEMPLOYEESDS:
                theDataList.add(journalGroupEmployeesBean.validateField(dataList.get(1).toString(), (BaseJournalApprovalGroupEmployees) dataList.get(2), userData));
                break;
            //BaseCalendarExceptionTypes
            case INSERT_BASECALENDAREXCEPTIONTYPES:
                theDataList.add(calendarExceptionTypesBean.insert((BaseCalendarExceptionTypes) dataList.get(1), userData));
                break;
            case UPDATE_BASECALENDAREXCEPTIONTYPES:
                theDataList.add(calendarExceptionTypesBean.update((BaseCalendarExceptionTypes) dataList.get(1), userData));
                break;
            case DELETE_BASECALENDAREXCEPTIONTYPES:
                theDataList.add(calendarExceptionTypesBean.delete((BaseCalendarExceptionTypes) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASECALENDAREXCEPTIONTYPES:
                theDataList.add(calendarExceptionTypesBean.getNumRows(BaseCalendarExceptionTypes.class, userData));
                break;
            case GETDATA_BASECALENDAREXCEPTIONTYPES:
                theDataList = (List<Object>) calendarExceptionTypesBean.getDataInRange(BaseCalendarExceptionTypes.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASECALENDAREXCEPTIONTYPES:
                theDataList.add(calendarExceptionTypesBean.validateField(dataList.get(1).toString(), (BaseCalendarExceptionTypes) dataList.get(2), userData));
                break;
            //EMAIL

            case SEND_BATCH_EMAILS:
            case SEND_SINGLE_EMAILS:
                theDataList.add(mailManagerBean.sendEmail((EMCEmail) dataList.get(1), userData));
                break;
            case PREVIEW_BATCH_EMAILS:
            case PREVIEW_SINGLE_EMAILS:
                theDataList.add(mailManagerBean.previewEmail((EMCEmail) dataList.get(1), userData));
                break;
            case VALIDATE_EMAILS:
                theDataList.add(mailManagerBean.validateEmail((EMCEmail) dataList.get(1), userData));
                break;
            //BaseMailReturnAddressSetup
            case INSERT_BASEMAILRETURNADDRESSSETUP:
                theDataList.add(mailReturnAddressSetupBean.insert((BaseMailReturnAddressSetup) dataList.get(1), userData));
                break;
            case UPDATE_BASEMAILRETURNADDRESSSETUP:
                theDataList.add(mailReturnAddressSetupBean.update((BaseMailReturnAddressSetup) dataList.get(1), userData));
                break;
            case DELETE_BASEMAILRETURNADDRESSSETUP:
                theDataList.add(mailReturnAddressSetupBean.delete((BaseMailReturnAddressSetup) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEMAILRETURNADDRESSSETUP:
                theDataList.add(mailReturnAddressSetupBean.getNumRows(BaseMailReturnAddressSetup.class, userData));
                break;
            case GETDATA_BASEMAILRETURNADDRESSSETUP:
                theDataList = (List<Object>) mailReturnAddressSetupBean.getDataInRange(BaseMailReturnAddressSetup.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEMAILRETURNADDRESSSETUP:
                theDataList.add(mailReturnAddressSetupBean.validateField(dataList.get(1).toString(), (BaseMailReturnAddressSetup) dataList.get(2), userData));
                break;
            //BaseMailReturnAddressSetupDS
            case INSERT_BASEMAILRETURNADDRESSSETUPDS:
                theDataList.add(mailReturnAddressSetupDSBean.insert((BaseMailReturnAddressSetupDS) dataList.get(1), userData));
                break;
            case UPDATE_BASEMAILRETURNADDRESSSETUPDS:
                theDataList.add(mailReturnAddressSetupDSBean.update((BaseMailReturnAddressSetupDS) dataList.get(1), userData));
                break;
            case DELETE_BASEMAILRETURNADDRESSSETUPDS:
                theDataList.add(mailReturnAddressSetupDSBean.delete((BaseMailReturnAddressSetupDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEMAILRETURNADDRESSSETUPDS:
                theDataList.add(mailReturnAddressSetupDSBean.getNumRows(BaseMailReturnAddressSetupDS.class, userData));
                break;
            case GETDATA_BASEMAILRETURNADDRESSSETUPDS:
                theDataList = (List<Object>) mailReturnAddressSetupDSBean.getDataInRange(BaseMailReturnAddressSetupDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEMAILRETURNADDRESSSETUPDS:
                theDataList.add(mailReturnAddressSetupDSBean.validateField(dataList.get(1).toString(), (BaseMailReturnAddressSetupDS) dataList.get(2), userData));
                break;
            //BaseSetupStorageTable
            case SAVE_MULTIPLE_SETUP_STORAGE:
                setupStorageTableBean.saveSetup(dataList, userData);
                break;
            case SAVE_SINGLE_SETUP_STORAGE:
                int size = dataList.size();
                setupStorageTableBean.saveSetup((String) dataList.get(1), (String) dataList.get(2),
                        size > 3 ? dataList.get(3) : null, size > 4 ? dataList.get(4) : null, size > 5 ? dataList.get(5) : null,
                        size > 6 ? dataList.get(6) : null, size > 4 ? dataList.get(7) : null, userData);
                break;
            case FIND_SETUP_STORAGE_ID:
                theDataList = setupStorageTableBean.findSetupId((String) dataList.get(1), userData);
                break;
            case LOAD_SETUP_STORAGE:
                theDataList = setupStorageTableBean.findSetup((String) dataList.get(1), (String) dataList.get(2), userData);
                break;
            case DELETE_SETUP_STORAGE:
                setupStorageTableBean.deleteSetup((String) dataList.get(1), (String) dataList.get(2), userData);
                break;
            //BaseDataBaseConnections
            case INSERT_BASEDBCONNECTIONS:
                theDataList.add(dataBaseConnectionBean.insert((BaseDBConnections) dataList.get(1), userData));
                break;
            case UPDATE_BASEDBCONNECTIONS:
                theDataList.add(dataBaseConnectionBean.update((BaseDBConnections) dataList.get(1), userData));
                break;
            case DELETE_BASEDBCONNECTIONS:
                theDataList.add(dataBaseConnectionBean.delete((BaseDBConnections) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEDBCONNECTIONS:
                theDataList.add(dataBaseConnectionBean.getNumRows(BaseDBConnections.class, userData));
                break;
            case GETDATA_BASEDBCONNECTIONS:
                theDataList = (List<Object>) dataBaseConnectionBean.getDataInRange(BaseDBConnections.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEDBCONNECTIONS:
                theDataList.add(dataBaseConnectionBean.validateField(dataList.get(1).toString(), (BaseDBConnections) dataList.get(2), userData));
                break;
            //Base File Associations
            case INSERT_BASEFILEASSOCIATIONS:
                theDataList.add(fileAssociationsBean.insert((BaseFileAssociations) dataList.get(1), userData));
                break;
            case UPDATE_BASEFILEASSOCIATIONS:
                theDataList.add(fileAssociationsBean.update((BaseFileAssociations) dataList.get(1), userData));
                break;
            case DELETE_BASEFILEASSOCIATIONS:
                theDataList.add(fileAssociationsBean.delete((BaseFileAssociations) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEFILEASSOCIATIONS:
                theDataList.add(fileAssociationsBean.getNumRows(BaseFileAssociations.class, userData));
                break;
            case GETDATA_BASEFILEASSOCIATIONS:
                theDataList = (List<Object>) fileAssociationsBean.getDataInRange(BaseFileAssociations.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case GET_FILE_ASSOCIATIONS:
                theDataList.add(fileAssociationsBean.getFileAssociations((String) dataList.get(1), (String) dataList.get(2), userData));
                break;
            case VALIDATEFIELD_BASEFILEASSOCIATIONS:
                theDataList.add(fileAssociationsBean.validateField(dataList.get(1).toString(), (BaseFileAssociations) dataList.get(2), userData));
                break;
            //Base User File Associations
            case INSERT_BASEUSERFILEASSOCIATIONS:
                theDataList.add(userFileAssociationsBean.insert((BaseUserFileAssociations) dataList.get(1), userData));
                break;
            case UPDATE_BASEUSERFILEASSOCIATIONS:
                theDataList.add(userFileAssociationsBean.update((BaseUserFileAssociations) dataList.get(1), userData));
                break;
            case DELETE_BASEUSERFILEASSOCIATIONS:
                theDataList.add(userFileAssociationsBean.delete((BaseUserFileAssociations) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEUSERFILEASSOCIATIONS:
                theDataList.add(userFileAssociationsBean.getNumRows(BaseUserFileAssociations.class, userData));
                break;
            case GETDATA_BASEUSERFILEASSOCIATIONS:
                theDataList = (List<Object>) userFileAssociationsBean.getDataInRange(BaseUserFileAssociations.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEUSERFILEASSOCIATIONS:
                theDataList.add(userFileAssociationsBean.validateField(dataList.get(1).toString(), (BaseUserFileAssociations) dataList.get(2), userData));
                break;
            //Multi Processing
            case RESERVE_MULTI_PROCESSING_THREADS:
                theDataList.add(multiProcessingBean.reserveThreads((Integer) dataList.get(1), userData));
                break;
            case UNRESERVE_MULTI_PROCESSING_THREADS:
                multiProcessingBean.unreserveThreads((Integer) dataList.get(1), userData);
                break;
            case RESET_MULTI_PROCESSING_VARIABLES:
                multiProcessingBean.setThreadVariables(userData);
                break;
            //Base Journal Access Groups
            case INSERT_BASEJOURNALACCESSGROUPS:
                theDataList.add(journalAccessGroupsBean.insert((BaseJournalAccessGroups) dataList.get(1), userData));
                break;
            case UPDATE_BASEJOURNALACCESSGROUPS:
                theDataList.add(journalAccessGroupsBean.update((BaseJournalAccessGroups) dataList.get(1), userData));
                break;
            case DELETE_BASEJOURNALACCESSGROUPS:
                theDataList.add(journalAccessGroupsBean.delete((BaseJournalAccessGroups) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEJOURNALACCESSGROUPS:
                theDataList.add(journalAccessGroupsBean.getNumRows(BaseJournalAccessGroups.class, userData));
                break;
            case GETDATA_BASEJOURNALACCESSGROUPS:
                theDataList = (List<Object>) journalAccessGroupsBean.getDataInRange(BaseJournalAccessGroups.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEJOURNALACCESSGROUPS:
                theDataList.add(journalAccessGroupsBean.validateField(dataList.get(1).toString(), (BaseJournalAccessGroups) dataList.get(2), userData));
                break;
            case GET_ACCESS_DEFS:
                theDataList.add(journalAccessGroupsBean.getDefinitionList(userData));
                break;
            //Base Journal Access Group Employees
            case INSERT_BASEJOURNALACCESSGROUPEMPLOYEES:
                theDataList.add(journalAccessGroupEmployeesBean.insert((BaseJournalAccessGroupEmployees) dataList.get(1), userData));
                break;
            case UPDATE_BASEJOURNALACCESSGROUPEMPLOYEES:
                theDataList.add(journalAccessGroupEmployeesBean.update((BaseJournalAccessGroupEmployees) dataList.get(1), userData));
                break;
            case DELETE_BASEJOURNALACCESSGROUPEMPLOYEES:
                theDataList.add(journalAccessGroupEmployeesBean.delete((BaseJournalAccessGroupEmployees) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEJOURNALACCESSGROUPEMPLOYEES:
                theDataList.add(journalAccessGroupEmployeesBean.getNumRows(BaseJournalAccessGroupEmployees.class, userData));
                break;
            case GETDATA_BASEJOURNALACCESSGROUPEMPLOYEES:
                theDataList = (List<Object>) journalAccessGroupEmployeesBean.getDataInRange(BaseJournalAccessGroupEmployees.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEJOURNALACCESSGROUPEMPLOYEES:
                theDataList.add(journalAccessGroupEmployeesBean.validateField(dataList.get(1).toString(), (BaseJournalAccessGroupEmployees) dataList.get(2), userData));
                break;
            //Base Journal Access Group Definitions
            case INSERT_BASEJOURNALACCESSGROUPDEFINITIONS:
                theDataList.add(journalAccessGroupDefinitionsBean.insert((BaseJournalAccessGroupDefinitions) dataList.get(1), userData));
                break;
            case UPDATE_BASEJOURNALACCESSGROUPDEFINITIONS:
                theDataList.add(journalAccessGroupDefinitionsBean.update((BaseJournalAccessGroupDefinitions) dataList.get(1), userData));
                break;
            case DELETE_BASEJOURNALACCESSGROUPDEFINITIONS:
                theDataList.add(journalAccessGroupDefinitionsBean.delete((BaseJournalAccessGroupDefinitions) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEJOURNALACCESSGROUPDEFINITIONS:
                theDataList.add(journalAccessGroupDefinitionsBean.getNumRows(BaseJournalAccessGroupDefinitions.class, userData));
                break;
            case GETDATA_BASEJOURNALACCESSGROUPDEFINITIONS:
                theDataList = (List<Object>) journalAccessGroupDefinitionsBean.getDataInRange(BaseJournalAccessGroupDefinitions.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEJOURNALACCESSGROUPDEFINITIONS:
                theDataList.add(journalAccessGroupDefinitionsBean.validateField(dataList.get(1).toString(), (BaseJournalAccessGroupDefinitions) dataList.get(2), userData));
                break;
            //BaseJournalAccessGroupEmployeesDS
            case INSERT_BASEJOURNALACCESSGROUPEMPLOYEESDS:
                theDataList.add(journalAccessGroupEmployeesDSBean.insert((BaseJournalAccessGroupEmployeesDS) dataList.get(1), userData));
                break;
            case UPDATE_BASEJOURNALACCESSGROUPEMPLOYEESDS:
                theDataList.add(journalAccessGroupEmployeesDSBean.update((BaseJournalAccessGroupEmployeesDS) dataList.get(1), userData));
                break;
            case DELETE_BASEJOURNALACCESSGROUPEMPLOYEESDS:
                theDataList.add(journalAccessGroupEmployeesDSBean.delete((BaseJournalAccessGroupEmployeesDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEJOURNALACCESSGROUPEMPLOYEESDS:
                theDataList.add(journalAccessGroupEmployeesDSBean.getNumRows(BaseJournalAccessGroupEmployeesDS.class, userData));
                break;
            case GETDATA_BASEJOURNALACCESSGROUPEMPLOYEESDS:
                theDataList = (List<Object>) journalAccessGroupEmployeesDSBean.getDataInRange(BaseJournalAccessGroupEmployeesDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEJOURNALACCESSGROUPEMPLOYEESDS:
                theDataList.add(journalAccessGroupEmployeesDSBean.validateField(dataList.get(1).toString(), (BaseJournalAccessGroupEmployeesDS) dataList.get(2), userData));
                break;

            //Active Transactions
            case INSERT_EMCTRANSACTIONS:
                theDataList.add(activeTransBean.insert((EMCTransactions) dataList.get(1), userData));
                break;
            case UPDATE_EMCTRANSACTIONS:
                theDataList.add(activeTransBean.update((EMCTransactions) dataList.get(1), userData));
                break;
            case DELETE_EMCTRANSACTIONS:
                theDataList.add(activeTransBean.delete((EMCTransactions) dataList.get(1), userData));
                break;
            case GETNUMROWS_EMCTRANSACTIONS:
                theDataList.add(activeTransBean.getNumRows(EMCTransactions.class, userData));
                break;
            case GETDATA_EMCTRANSACTIONS:
                theDataList = (List<Object>) activeTransBean.getDataInRange(EMCTransactions.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_EMCTRANSACTIONS:
                theDataList.add(activeTransBean.validateField(dataList.get(1).toString(), (EMCTransactions) dataList.get(2), userData));
                break;
            case ROLL_BACK_TRANSACTION:
                theDataList.add(activeTransBean.rollBackTransaction((String) dataList.get(1), userData));
                break;
            //BaseBatchProcess
            case INSERT_BASEBATCHPROCESS:
                theDataList.add(batchProcessingBean.insert((BaseBatchProcess) dataList.get(1), userData));
                break;
            case UPDATE_BASEBATCHPROCESS:
                theDataList.add(batchProcessingBean.update((BaseBatchProcess) dataList.get(1), userData));
                break;
            case DELETE_BASEBATCHPROCESS:
                theDataList.add(batchProcessingBean.delete((BaseBatchProcess) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEBATCHPROCESS:
                theDataList.add(batchProcessingBean.getNumRows(BaseBatchProcess.class, userData));
                break;
            case GETDATA_BASEBATCHPROCESS:
                theDataList = (List<Object>) batchProcessingBean.getDataInRange(BaseBatchProcess.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEBATCHPROCESS:
                theDataList.add(batchProcessingBean.validateField(dataList.get(1).toString(), (BaseBatchProcess) dataList.get(2), userData));
                break;
            case LOG_BASEBATCHPROCESS_MESSAGES:
                batchProcessingBean.logBatchRecordMessages((Long) dataList.get(1), userData);
                break;
            //User Permissions Report
            case PRINT_USER_PERMISSIONS_REPORT:
                theDataList = userPermissionsReportBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            //BaseHelpFileMappings
            case INSERT_BASEHELPFILEMAPPINGS:
                theDataList.add(helpFileMappingBean.insert((BaseHelpFileMappings) dataList.get(1), userData));
                break;
            case UPDATE_BASEHELPFILEMAPPINGS:
                theDataList.add(helpFileMappingBean.update((BaseHelpFileMappings) dataList.get(1), userData));
                break;
            case DELETE_BASEHELPFILEMAPPINGS:
                theDataList.add(helpFileMappingBean.delete((BaseHelpFileMappings) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEHELPFILEMAPPINGS:
                theDataList.add(helpFileMappingBean.getNumRows(BaseHelpFileMappings.class, userData));
                break;
            case GETDATA_BASEHELPFILEMAPPINGS:
                theDataList = (List<Object>) helpFileMappingBean.getDataInRange(BaseHelpFileMappings.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEHELPFILEMAPPINGS:
                theDataList.add(helpFileMappingBean.validateField(dataList.get(1).toString(), (BaseHelpFileMappings) dataList.get(2), userData));
                break;
            case GET_HELP_FILE_URL:
                theDataList.add(helpFileMappingBean.getHelpFileURL((String) dataList.get(1), userData));
                break;
            //Entity Relation Diagram
            case GET_ENTITY_RELATION:
                theDataList.add(entityRelationDiagramBean.getRelationsForTable(dataList.get(1).toString(), userData));
                break;
            case FETCHEXCEL_EXPORT_DATA:
                theDataList = genericReportBean.fetchReportData((String) dataList.get(1), (EMCCommandClass) dataList.get(2), (Integer) dataList.get(3), (Integer) dataList.get(4), userData);
                break;
            case FETCH_GENERIC_REPORT_DATA:
                theDataList = genericReportBean.fetchReportData((List<String>) dataList.get(1), (String) dataList.get(2), (EMCCommandClass) dataList.get(3), (Integer) dataList.get(4), (Integer) dataList.get(5), userData);
                break;

            //BaseTransactionsLog
            case INSERT_BASESERVERTRANSACTIONSLOG:
                theDataList.add(baseServerTransactionsLogBean.insert((BaseServerTransactionsLog) dataList.get(1), userData));
                break;
            case UPDATE_BASESERVERTRANSACTIONSLOG:
                theDataList.add(baseServerTransactionsLogBean.update((BaseServerTransactionsLog) dataList.get(1), userData));
                break;
            case DELETE_BASESERVERTRANSACTIONSLOG:
                theDataList.add(baseServerTransactionsLogBean.delete((BaseServerTransactionsLog) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASESERVERTRANSACTIONSLOG:
                theDataList.add(baseServerTransactionsLogBean.getNumRows(BaseServerTransactionsLog.class, userData));
                break;
            case GETDATA_BASESERVERTRANSACTIONSLOG:
                theDataList = (List<Object>) baseServerTransactionsLogBean.getDataInRange(BaseServerTransactionsLog.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASESERVERTRANSACTIONSLOG:
                theDataList.add(baseServerTransactionsLogBean.validateField(dataList.get(1).toString(), (BaseServerTransactionsLog) dataList.get(2), userData));
                break;
            //BaseTransactionsLog
            case INSERT_BASESERVERTRANSACTIONSLOGDS:
                theDataList.add(baseServerTransactionsLogBeanDS.insert((BaseServerTransactionsLogDS) dataList.get(1), userData));
                break;
            case UPDATE_BASESERVERTRANSACTIONSLOGDS:
                theDataList.add(baseServerTransactionsLogBeanDS.update((BaseServerTransactionsLogDS) dataList.get(1), userData));
                break;
            case DELETE_BASESERVERTRANSACTIONSLOGDS:
                theDataList.add(baseServerTransactionsLogBeanDS.delete((BaseServerTransactionsLogDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASESERVERTRANSACTIONSLOGDS:
                theDataList.add(baseServerTransactionsLogBeanDS.getNumRows(BaseServerTransactionsLogDS.class, userData));
                break;
            case GETDATA_BASESERVERTRANSACTIONSLOGDS:
                theDataList = (List<Object>) baseServerTransactionsLogBeanDS.getDataInRange(BaseServerTransactionsLogDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASESERVERTRANSACTIONSLOGDS:
                theDataList.add(baseServerTransactionsLogBeanDS.validateField(dataList.get(1).toString(), (BaseServerTransactionsLogDS) dataList.get(2), userData));
                break;
//BaseIndex
            case INSERT_BASEINDEX:
                theDataList.add(indexBean.insert((BaseIndex) dataList.get(1), userData));
                break;
            case UPDATE_BASEINDEX:
                theDataList.add(indexBean.update((BaseIndex) dataList.get(1), userData));
                break;
            case DELETE_BASEINDEX:
                theDataList.add(indexBean.delete((BaseIndex) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEINDEX:
                theDataList.add(indexBean.getNumRows(BaseIndex.class, userData));
                break;
            case GETDATA_BASEINDEX:
                theDataList = (List<Object>) indexBean.getDataInRange(BaseIndex.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEINDEX:
                theDataList.add(indexBean.validateField(dataList.get(1).toString(), (BaseIndex) dataList.get(2), userData));
                break;
            //BaseWebFilePaths
            case INSERT_BASEWEBFILEPATHS:
                theDataList.add(webFilePaths.insert((BaseWebFilePaths) dataList.get(1), userData));
                break;
            case UPDATE_BASEWEBFILEPATHS:
                theDataList.add(webFilePaths.update((BaseWebFilePaths) dataList.get(1), userData));
                break;
            case DELETE_BASEWEBFILEPATHS:
                theDataList.add(webFilePaths.delete((BaseWebFilePaths) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEWEBFILEPATHS:
                theDataList.add(webFilePaths.getNumRows(BaseWebFilePaths.class, userData));
                break;
            case GETDATA_BASEWEBFILEPATHS:
                theDataList = (List<Object>) webFilePaths.getDataInRange(BaseWebFilePaths.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEWEBFILEPATHS:
                theDataList.add(webFilePaths.validateField(dataList.get(1).toString(), (BaseWebFilePaths) dataList.get(2), userData));
                break;
            //BaseCopyTableData
            case INSERT_BASECOPYTABLEDATA:
                theDataList.add(copyTableDataBean.insert((BaseCopyTableData) dataList.get(1), userData));
                break;
            case UPDATE_BASECOPYTABLEDATA:
                theDataList.add(copyTableDataBean.update((BaseCopyTableData) dataList.get(1), userData));
                break;
            case DELETE_BASECOPYTABLEDATA:
                theDataList.add(copyTableDataBean.delete((BaseCopyTableData) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASECOPYTABLEDATA:
                theDataList.add(copyTableDataBean.getNumRows(BaseCopyTableData.class, userData));
                break;
            case GETDATA_BASECOPYTABLEDATA:
                theDataList = (List<Object>) copyTableDataBean.getDataInRange(BaseCopyTableData.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASECOPYTABLEDATA:
                theDataList.add(copyTableDataBean.validateField(dataList.get(1).toString(), (BaseCopyTableData) dataList.get(2), userData));
                break;
            case COPY_TABLE_DATA:
                theDataList.add(copyTableDataBean.copyTableData(userData.getCompanyId(), dataList.get(1).toString(), userData));
                break;
            case ADD_RELATED_TABLES:
                theDataList.add(copyTableDataBean.addRelatedTables(userData));
                break;
            case BACKUP_TABLE_DATA:
                theDataList.add(copyTableDataBean.createBackupFile(userData));
                break;
            case IMPORT_TABLE_DATA:
                theDataList.add(copyTableDataBean.importBackupFile((String) dataList.get(1), userData));
                break;
            case DELETE_BACKUP_FILE:
                copyTableDataBean.deleteBackupFile(userData);
                break;
            case CLEAR_TABLE_DATA:
                theDataList.add(copyTableDataBean.deleteTableData((String) dataList.get(1), userData));
                break;
            //BaseReportText
            case INSERT_BASEREPORTTEXT:
                theDataList.add(reportTextBean.insert((BaseReportText) dataList.get(1), userData));
                break;
            case UPDATE_BASEREPORTTEXT:
                theDataList.add(reportTextBean.update((BaseReportText) dataList.get(1), userData));
                break;
            case DELETE_BASEREPORTTEXT:
                theDataList.add(reportTextBean.delete((BaseReportText) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEREPORTTEXT:
                theDataList.add(reportTextBean.getNumRows(BaseReportText.class, userData));
                break;
            case GETDATA_BASEREPORTTEXT:
                theDataList = (List<Object>) reportTextBean.getDataInRange(BaseReportText.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEREPORTTEXT:
                theDataList.add(reportTextBean.validateField(dataList.get(1).toString(), (BaseReportText) dataList.get(2), userData));
                break;
            //BaseEmployeeCategoryHistory
            case INSERT_BASEEMPLOYEECATEGORYHISTORY:
                theDataList.add(employeeCategoryHistoryBean.insert((BaseEmployeeCategoryHistory) dataList.get(1), userData));
                break;
            case UPDATE_BASEEMPLOYEECATEGORYHISTORY:
                theDataList.add(employeeCategoryHistoryBean.update((BaseEmployeeCategoryHistory) dataList.get(1), userData));
                break;
            case DELETE_BASEEMPLOYEECATEGORYHISTORY:
                theDataList.add(employeeCategoryHistoryBean.delete((BaseEmployeeCategoryHistory) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEEMPLOYEECATEGORYHISTORY:
                theDataList.add(employeeCategoryHistoryBean.getNumRows(BaseEmployeeCategoryHistory.class, userData));
                break;
            case GETDATA_BASEEMPLOYEECATEGORYHISTORY:
                theDataList = (List<Object>) employeeCategoryHistoryBean.getDataInRange(BaseEmployeeCategoryHistory.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEEMPLOYEECATEGORYHISTORY:
                theDataList.add(employeeCategoryHistoryBean.validateField(dataList.get(1).toString(), (BaseEmployeeCategoryHistory) dataList.get(2), userData));
                break;
            //BaseEmployeeCategoryHistoryDS
            case INSERT_BASEEMPLOYEECATEGORYHISTORYDS:
                theDataList.add(employeeCategoryHistoryDSBean.insert((BaseEmployeeCategoryHistoryDS) dataList.get(1), userData));
                break;
            case UPDATE_BASEEMPLOYEECATEGORYHISTORYDS:
                theDataList.add(employeeCategoryHistoryDSBean.update((BaseEmployeeCategoryHistoryDS) dataList.get(1), userData));
                break;
            case DELETE_BASEEMPLOYEECATEGORYHISTORYDS:
                theDataList.add(employeeCategoryHistoryDSBean.delete((BaseEmployeeCategoryHistoryDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEEMPLOYEECATEGORYHISTORYDS:
                theDataList.add(employeeCategoryHistoryDSBean.getNumRows(BaseEmployeeCategoryHistoryDS.class, userData));
                break;
            case GETDATA_BASEEMPLOYEECATEGORYHISTORYDS:
                theDataList = (List<Object>) employeeCategoryHistoryDSBean.getDataInRange(BaseEmployeeCategoryHistoryDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEEMPLOYEECATEGORYHISTORYDS:
                theDataList.add(employeeCategoryHistoryDSBean.validateField(dataList.get(1).toString(), (BaseEmployeeCategoryHistoryDS) dataList.get(2), userData));
                break;
            //BaseUOMDetailedConversionTable
            case INSERT_BASEUOMDETAILEDCONVERSIONTABLE:
                theDataList.add(uomDetailedConversionBean.insert((BaseUOMDetailedConversionTable) dataList.get(1), userData));
                break;
            case UPDATE_BASEUOMDETAILEDCONVERSIONTABLE:
                theDataList.add(uomDetailedConversionBean.update((BaseUOMDetailedConversionTable) dataList.get(1), userData));
                break;
            case DELETE_BASEUOMDETAILEDCONVERSIONTABLE:
                theDataList.add(uomDetailedConversionBean.delete((BaseUOMDetailedConversionTable) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEUOMDETAILEDCONVERSIONTABLE:
                theDataList.add(uomDetailedConversionBean.getNumRows(BaseUOMDetailedConversionTable.class, userData));
                break;
            case GETDATA_BASEUOMDETAILEDCONVERSIONTABLE:
                theDataList = (List<Object>) uomDetailedConversionBean.getDataInRange(BaseUOMDetailedConversionTable.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEUOMDETAILEDCONVERSIONTABLE:
                theDataList.add(uomDetailedConversionBean.validateField(dataList.get(1).toString(), (BaseUOMDetailedConversionTable) dataList.get(2), userData));
                break;
            //BaseUOMDetailedConversionTableDS
            case INSERT_BASEUOMDETAILEDCONVERSIONTABLEDS:
                theDataList.add(uomDetailedConversionDSBean.insert((BaseUOMDetailedConversionTableDS) dataList.get(1), userData));
                break;
            case UPDATE_BASEUOMDETAILEDCONVERSIONTABLEDS:
                theDataList.add(uomDetailedConversionDSBean.update((BaseUOMDetailedConversionTableDS) dataList.get(1), userData));
                break;
            case DELETE_BASEUOMDETAILEDCONVERSIONTABLEDS:
                theDataList.add(uomDetailedConversionDSBean.delete((BaseUOMDetailedConversionTableDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEUOMDETAILEDCONVERSIONTABLEDS:
                theDataList.add(uomDetailedConversionDSBean.getNumRows(BaseUOMDetailedConversionTableDS.class, userData));
                break;
            case GETDATA_BASEUOMDETAILEDCONVERSIONTABLEDS:
                theDataList = (List<Object>) uomDetailedConversionDSBean.getDataInRange(BaseUOMDetailedConversionTableDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEUOMDETAILEDCONVERSIONTABLEDS:
                theDataList.add(uomDetailedConversionDSBean.validateField(dataList.get(1).toString(), (BaseUOMDetailedConversionTableDS) dataList.get(2), userData));
                break;
//BaseLanguage
            case INSERT_BASELANGUAGE:
                theDataList.add(languageBean.insert((BaseLanguage) dataList.get(1), userData));
                break;
            case UPDATE_BASELANGUAGE:
                theDataList.add(languageBean.update((BaseLanguage) dataList.get(1), userData));
                break;
            case DELETE_BASELANGUAGE:
                theDataList.add(languageBean.delete((BaseLanguage) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASELANGUAGE:
                theDataList.add(languageBean.getNumRows(BaseLanguage.class, userData));
                break;
            case GETDATA_BASELANGUAGE:
                theDataList = (List<Object>) languageBean.getDataInRange(BaseLanguage.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASELANGUAGE:
                theDataList.add(languageBean.validateField(dataList.get(1).toString(), (BaseLanguage) dataList.get(2), userData));
                break;
            //DB Log generic Report
            case FETCH_DB_LOG_GENERIC_REPORT_DATA:
                theDataList.add(dbLogGenericReportBean.getReportData((EMCQuery) dataList.get(1), (Integer) dataList.get(2), userData));
                break;
            //BaseWebPortalUsers
            case INSERT_BASEWEBPORTALUSERSDS:
                theDataList.add(webPortalUsersDSBean.insert((BaseWebPortalUsersDS) dataList.get(1), userData));
                break;
            case UPDATE_BASEWEBPORTALUSERSDS:
                theDataList.add(webPortalUsersDSBean.update((BaseWebPortalUsersDS) dataList.get(1), userData));
                break;
            case DELETE_BASEWEBPORTALUSERSDS:
                theDataList.add(webPortalUsersDSBean.delete((BaseWebPortalUsersDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEWEBPORTALUSERSDS:
                theDataList.add(webPortalUsersDSBean.getNumRows(BaseWebPortalUsersDS.class, userData));
                break;
            case GETDATA_BASEWEBPORTALUSERSDS:
                theDataList = (List<Object>) webPortalUsersDSBean.getDataInRange(BaseWebPortalUsersDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEWEBPORTALUSERSDS:
                theDataList.add(webPortalUsersDSBean.validateField(dataList.get(1).toString(), (BaseWebPortalUsersDS) dataList.get(2), userData));
                break;
            //SMS
            case SEND_BATCH_SMS:
            case SEND_SINGLE_SMS:
                theDataList.add(smsManagerBean.sendSMS((EMCSms) dataList.get(1), userData));
                break;
            case PREVIEW_BATCH_SMS:
            case PREVIEW_SINGLE_SMS:
                theDataList.add(smsManagerBean.previewSMS((EMCSms) dataList.get(1), userData));
                break;
            case VALIDATE_SMS:
                theDataList.add(smsManagerBean.validateSMS((EMCSms) dataList.get(1), userData));
                break;
            case GET_MAX_SMS_LENGTH:
                theDataList.add(smsManagerBean.getMaxSMSLength(userData));
                break;
            //BaseSMSTemplate
            case INSERT_BASESMSTEMPLATE:
                theDataList.add(smsTemplateBean.insert((BaseSMSTemplate) dataList.get(1), userData));
                break;
            case UPDATE_BASESMSTEMPLATE:
                theDataList.add(smsTemplateBean.update((BaseSMSTemplate) dataList.get(1), userData));
                break;
            case DELETE_BASESMSTEMPLATE:
                theDataList.add(smsTemplateBean.delete((BaseSMSTemplate) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASESMSTEMPLATE:
                theDataList.add(smsTemplateBean.getNumRows(BaseSMSTemplate.class, userData));
                break;
            case GETDATA_BASESMSTEMPLATE:
                theDataList = (List<Object>) smsTemplateBean.getDataInRange(BaseSMSTemplate.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASESMSTEMPLATE:
                theDataList.add(smsTemplateBean.validateField(dataList.get(1).toString(), (BaseSMSTemplate) dataList.get(2), userData));
                break;
            //BaseQueryTable
            case INSERT_BASEQUERYTABLE:
                theDataList.add(queryBean.insert((BaseQueryTable) dataList.get(1), userData));
                break;
            case UPDATE_BASEQUERYTABLE:
                theDataList.add(queryBean.update((BaseQueryTable) dataList.get(1), userData));
                break;
            case DELETE_BASEQUERYTABLE:
                theDataList.add(queryBean.delete((BaseQueryTable) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEQUERYTABLE:
                theDataList.add(queryBean.getNumRows(BaseQueryTable.class, userData));
                break;
            case GETDATA_BASEQUERYTABLE:
                theDataList = (List<Object>) queryBean.getDataInRange(BaseQueryTable.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEQUERYTABLE:
                theDataList.add(queryBean.validateField(dataList.get(1).toString(), (BaseQueryTable) dataList.get(2), userData));
                break;
            case LINK_QUERY_AND_TEMPLATE:
                theDataList.add(queryBean.mergeEMCQueryAndTemplate(dataList.get(1).toString(), dataList.get(2).toString(), dataList.get(3).toString(), userData));
                break;
            //BaseQueryActionTable
            case INSERT_BASEQUERYACTIONTABLE:
                theDataList.add(queryActionBean.insert((BaseQueryActionTable) dataList.get(1), userData));
                break;
            case UPDATE_BASEQUERYACTIONTABLE:
                theDataList.add(queryActionBean.update((BaseQueryActionTable) dataList.get(1), userData));
                break;
            case DELETE_BASEQUERYACTIONTABLE:
                theDataList.add(queryActionBean.delete((BaseQueryActionTable) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEQUERYACTIONTABLE:
                theDataList.add(queryActionBean.getNumRows(BaseQueryActionTable.class, userData));
                break;
            case GETDATA_BASEQUERYACTIONTABLE:
                theDataList = (List<Object>) queryActionBean.getDataInRange(BaseQueryActionTable.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEQUERYACTIONTABLE:
                theDataList.add(queryActionBean.validateField(dataList.get(1).toString(), (BaseQueryActionTable) dataList.get(2), userData));
                break;
            case EXTRACT_TEMPLATE_FIELDS:
                theDataList.add(queryActionBean.getTemplateFieldsToMap(dataList.get(1).toString(), dataList.get(2).toString(), userData));
                break;
            //BaseReportScheduling
            case INSERT_BASEREPORTSCHEDULING:
                theDataList.add(reportSchedulingBean.insert((BaseReportScheduling) dataList.get(1), userData));
                break;
            case UPDATE_BASEREPORTSCHEDULING:
                theDataList.add(reportSchedulingBean.update((BaseReportScheduling) dataList.get(1), userData));
                break;
            case DELETE_BASEREPORTSCHEDULING:
                theDataList.add(reportSchedulingBean.delete((BaseReportScheduling) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASEREPORTSCHEDULING:
                theDataList.add(reportSchedulingBean.getNumRows(BaseReportScheduling.class, userData));
                break;
            case GETDATA_BASEREPORTSCHEDULING:
                theDataList = (List<Object>) reportSchedulingBean.getDataInRange(BaseReportScheduling.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASEREPORTSCHEDULING:
                theDataList.add(reportSchedulingBean.validateField(dataList.get(1).toString(), (BaseReportScheduling) dataList.get(2), userData));
                break;
            case CREATE_REPORT_SCHEDULE:
                theDataList.add(reportSchedulingBean.addScheduledReport((String) dataList.get(1), (String) dataList.get(2), (Date) dataList.get(3),
                        (Date) dataList.get(4), (String) dataList.get(5), (String) dataList.get(6), userData));
                break;
            case CHECK_REPORT_SCHEDULE:
                theDataList.add(reportSchedulingBean.checkReportSchedule(userData));
                break;
            case BUILD_REPORT_SCHEDULE_QUERY:
                theDataList.add(reportSchedulingBean.buildScheduleSelectionQuery((String) dataList.get(1), (String) dataList.get(2), (String) dataList.get(3), userData));
                break;
            case UPDATE_REPORT_SCHEDULE:
                theDataList.add(reportSchedulingBean.updateReportSchedule((BaseReportScheduling) dataList.get(1), (Boolean) dataList.get(2), userData));
                break;
            case IS_REPORT_SCHEDULED_ID:
                theDataList.add(reportSchedulingBean.logForScheduledReports((Long) dataList.get(1), userData));
                break;
            case IS_REPORT_SCHEDULED_NAME:
                theDataList.add(reportSchedulingBean.logForScheduledReports((String) dataList.get(1), (String) dataList.get(2), userData));
                break;
            //BaseEmailTemplates
            case INSERT_BASETEMPLATEDOCUMENTS:
                theDataList.add(templateDocumentsBean.insert((BaseTemplateDocuments) dataList.get(1), userData));
                break;
            case UPDATE_BASETEMPLATEDOCUMENTS:
                theDataList.add(templateDocumentsBean.update((BaseTemplateDocuments) dataList.get(1), userData));
                break;
            case DELETE_BASETEMPLATEDOCUMENTS:
                theDataList.add(templateDocumentsBean.delete((BaseTemplateDocuments) dataList.get(1), userData));
                break;
            case GETNUMROWS_BASETEMPLATEDOCUMENTS:
                theDataList.add(templateDocumentsBean.getNumRows(BaseTemplateDocuments.class, userData));
                break;
            case GETDATA_BASETEMPLATEDOCUMENTS:
                theDataList = (List<Object>) templateDocumentsBean.getDataInRange(BaseTemplateDocuments.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_BASETEMPLATEDOCUMENTS:
                theDataList.add(templateDocumentsBean.validateField(dataList.get(1).toString(), (BaseTemplateDocuments) dataList.get(2), userData));
                break;

            //Validation
            case VALIDATE_TELEPHONE_NUMBER:
                theDataList.add(supportLogicBean.validateTelephone((String) dataList.get(1), userData));
                break;

//            case LINK_DOCUMENTS:
//                theDataList.add(templateDocumentsBean.linkDocuments(dataList.get(1).toString(), userData));
//                break;
//            case GET_DOCUMENTS:
//                theDataList.add(templateDocumentsBean.getDocuments(dataList.get(1).toString(), userData));
//                break;

            case GET_ACTIVE_MODULELIST:
                theDataList = baseLicenceBean.getActiveModuleList(userData);
                break;
                
            case PRINT_TASK_SHEET_REPORT:
                theDataList = taskSheetReportBean.getReportResult(dataList, cmd.getReportConfig(), userData);
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