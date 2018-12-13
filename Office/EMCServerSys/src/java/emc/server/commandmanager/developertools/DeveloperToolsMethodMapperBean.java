/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.server.commandmanager.developertools;

import emc.bus.developertools.DevNLPriceImportTableLocal;
import emc.bus.developertools.DevOldColourConversionTableLocal;
import emc.bus.developertools.DevOnHandImportTableLocal;
import emc.bus.developertools.DevProgressItemConversionTableLocal;
import emc.bus.developertools.DeveloperToolsLocal;
import emc.bus.developertools.bugtracking.DevBugsTableLocal;
import emc.bus.developertools.bugtracking.DevTimeSheetDSLocal;
import emc.bus.developertools.deploymentupdatelog.DevDeploymentUpdateLogLinesLocal;
import emc.bus.developertools.deploymentupdatelog.DevDeploymentUpdateLogLocal;
import emc.bus.developertools.deploymentupdatelog.datasource.DevDeploymentUpdateLogDSLocal;
import emc.bus.developertools.entitylog.DevEntityLogLocal;
import emc.bus.developertools.installation.DevInstallationLinesLocal;
import emc.bus.developertools.installation.DevInstallationMasterLocal;
import emc.bus.developertools.parameters.DevParametersLocal;
import emc.bus.developertools.poimport.DevNLPOImportLinesLocal;
import emc.bus.developertools.poimport.DevNLPOImportMasterLocal;
import emc.bus.developertools.projects.DevProjectsLinesLocal;
import emc.bus.developertools.projects.DevProjectsMasterLocal;
import emc.bus.developertools.sites.DevSiteLinesLocal;
import emc.bus.developertools.sites.DevSiteMasterLocal;
import emc.bus.developertools.timesheet.DevTimeSheetLocal;
import emc.bus.developertools.versioncontrol.DevVersioningControlLocal;
import emc.commands.EMCCommands;
import emc.entity.developertools.DevDeploymentUpdateLog;
import emc.entity.developertools.DevDeploymentUpdateLogLines;
import emc.entity.developertools.DevNLPriceImportTable;
import emc.entity.developertools.DevOldColourConversionTable;
import emc.entity.developertools.DevOnHandImportTable;
import emc.entity.developertools.DevParameters;
import emc.entity.developertools.DevProgressItemConversionTable;
import emc.entity.developertools.DevProjectLines;
import emc.entity.developertools.DevProjectMaster;
import emc.entity.developertools.DevTimeSheet;
import emc.entity.developertools.bugtracking.DevBugsTable;
import emc.entity.developertools.datasources.DevDeploymentUpdateLogDS;
import emc.entity.developertools.datasources.DevTimeSheetDS;
import emc.entity.developertools.installations.DevInstallationLines;
import emc.entity.developertools.installations.DevInstallationMaster;
import emc.entity.developertools.poimport.DevNLPOImportLines;
import emc.entity.developertools.poimport.DevNLPOImportMaster;
import emc.entity.developertools.sites.DevSiteLines;
import emc.entity.developertools.sites.DevSiteMaster;
import emc.entity.developertools.versioningcontrol.DevVersioningControl;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.helpers.devtools.DevTimeSheetTaskHelper;
import emc.methods.developertools.ClientDeveloperToolMethods;
import emc.methods.developertools.ServerDeveloperToolMethods;
import emc.reports.developertools.BugListReportLocal;
import emc.reports.developertools.taskmanagement.developmentsummary.DevDevelopmentSummaryLocal;
import emc.reports.developertools.taskmanagement.requirementssheet.DevRequirementsSheetLocal;
import emc.reports.developertools.timesheet.TimeSheetReportLocal;
import emc.server.AuthorizationKeyManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class DeveloperToolsMethodMapperBean implements DeveloperToolsMethodMapperBeanLocal {

    @EJB
    private DeveloperToolsLocal devToolsBean;
    @EJB
    private DevOnHandImportTableLocal devOnHandImportTableBean;
    @EJB
    private DevOldColourConversionTableLocal devOldColourConversionTableBean;
    @EJB
    private DevProgressItemConversionTableLocal itemConversionTableBean;
    @EJB
    private DevNLPOImportLinesLocal poImportLinesBean;
    @EJB
    private DevNLPOImportMasterLocal poImportMasterBean;
    @EJB
    private DevNLPriceImportTableLocal priceImportBean;
    @EJB
    private DevBugsTableLocal bugsTableBean;
    @EJB
    private BugListReportLocal bugsListReportBean;
    @EJB
    private DevParametersLocal paramBean;
    @EJB
    private DevSiteMasterLocal siteMasterBean;
    @EJB
    private DevSiteLinesLocal siteLinesBean;
    @EJB
    private TimeSheetReportLocal timeSheetReport;
    @EJB
    private DevEntityLogLocal entityLogBean;
    @EJB
    private DevProjectsMasterLocal projectMasterBean;
    @EJB
    private DevProjectsLinesLocal projectLinesBean;
    @EJB
    private DevDeploymentUpdateLogLocal deploymentUpdateLogBean;
    @EJB
    private DevDeploymentUpdateLogDSLocal deploymentUpdateLogDSBean;
    @EJB
    private DevInstallationMasterLocal installationMasterBean;
    @EJB
    private DevInstallationLinesLocal installationLinesBean;
    @EJB
    private DevVersioningControlLocal versioningControlBean;
    @EJB
    private DevDeploymentUpdateLogLinesLocal deploymentUpdateLogLinesBean;
    @EJB
    private DevTimeSheetLocal timeSheetBean;
    @EJB
    private DevTimeSheetDSLocal timeSheetDSBean;
    @EJB
    private DevRequirementsSheetLocal requirementsSheetBean;
    @EJB
    private DevDevelopmentSummaryLocal developmentSummaryBean;

    public List mapMethodDeveloperTools(EMCCommandClass cmd, List<Object> dataList, EMCUserData userData) throws EMCEntityBeanException {
        List<Object> theDataList = new ArrayList();
        EMCCommandClass retCmd = new EMCCommandClass();
        retCmd.setCommandId(EMCCommands.CLIENT_GENERAL_COMMAND.getId());
        retCmd.setModuleNumber(enumEMCModules.DEVELOPERTOOLS.getId());
        retCmd.setMethodId(ClientDeveloperToolMethods.GENERAL_METHOD.toString());

        switch (ServerDeveloperToolMethods.fromString(cmd.getMethodId())) {
            case TESTQUERY:
                theDataList = devToolsBean.testQuery(dataList.get(1).toString(), userData);
                break;
            case EXECUTE_EMCQUERY:
                theDataList = devToolsBean.executeQuery((EMCQuery) dataList.get(1), userData);
                break;
            case EXECUTE_NATIVE_QUERY:
                theDataList.add(devToolsBean.executeNativeQuery((String) dataList.get(1)));
                break;
            case EXECUTE_NATIVE_SELECT_QUERY:
                theDataList.add(devToolsBean.executeNativeQuery((EMCQuery) dataList.get(1), userData));
                break;
            //DevOnHandImportTable
            case INSERT_DEVONHANDIMPORTTABLE:
                theDataList.add(devOnHandImportTableBean.insert((DevOnHandImportTable) dataList.get(1), userData));
                break;
            case UPDATE_DEVONHANDIMPORTTABLE:
                theDataList.add(devOnHandImportTableBean.update((DevOnHandImportTable) dataList.get(1), userData));
                break;
            case DELETE_DEVONHANDIMPORTTABLE:
                theDataList.add(devOnHandImportTableBean.delete((DevOnHandImportTable) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEVONHANDIMPORTTABLE:
                theDataList.add(devOnHandImportTableBean.getNumRows(DevOnHandImportTable.class, userData));
                break;
            case GETDATA_DEVONHANDIMPORTTABLE:
                theDataList = (List<Object>) devOnHandImportTableBean.getDataInRange(DevOnHandImportTable.class, userData,
                                                                                     Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEVONHANDIMPORTTABLE:
                theDataList.add(devOnHandImportTableBean.validateField(dataList.get(1).toString(), (DevOnHandImportTable) dataList.get(2), userData));
                break;
            case FINISH_IMPORT:
                devOnHandImportTableBean.finishImport((String) dataList.get(1), userData);
                break;
            case FINISH_IMPORT_SIZE:
                devOnHandImportTableBean.finishImportSize((String) dataList.get(1), userData);
                break;
            case FINISH_IMPORT_ELASTIC:
                devOnHandImportTableBean.finishImportElastic((String) dataList.get(1), userData);
                break;
            //DevOldColourConversionTable
            case INSERT_DEVOLDCOLOURCONVERSIONTABLE:
                theDataList.add(devOldColourConversionTableBean.insert((DevOldColourConversionTable) dataList.get(1), userData));
                break;
            case UPDATE_DEVOLDCOLOURCONVERSIONTABLE:
                theDataList.add(devOldColourConversionTableBean.update((DevOldColourConversionTable) dataList.get(1), userData));
                break;
            case DELETE_DEVOLDCOLOURCONVERSIONTABLE:
                theDataList.add(devOldColourConversionTableBean.delete((DevOldColourConversionTable) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEVOLDCOLOURCONVERSIONTABLE:
                theDataList.add(devOldColourConversionTableBean.getNumRows(DevOldColourConversionTable.class, userData));
                break;
            case GETDATA_DEVOLDCOLOURCONVERSIONTABLE:
                theDataList = (List<Object>) devOldColourConversionTableBean.getDataInRange(DevOldColourConversionTable.class, userData,
                                                                                            Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEVOLDCOLOURCONVERSIONTABLE:
                theDataList.add(devOldColourConversionTableBean.validateField(dataList.get(1).toString(), (DevOldColourConversionTable) dataList.get(2), userData));
                break;
            //DevProgressItemConversionTable
            case INSERT_DEVPROGRESSITEMCONVERSIONTABLE:
                theDataList.add(itemConversionTableBean.insert((DevProgressItemConversionTable) dataList.get(1), userData));
                break;
            case UPDATE_DEVPROGRESSITEMCONVERSIONTABLE:
                theDataList.add(itemConversionTableBean.update((DevProgressItemConversionTable) dataList.get(1), userData));
                break;
            case DELETE_DEVPROGRESSITEMCONVERSIONTABLE:
                theDataList.add(itemConversionTableBean.delete((DevProgressItemConversionTable) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEVPROGRESSITEMCONVERSIONTABLE:
                theDataList.add(itemConversionTableBean.getNumRows(DevProgressItemConversionTable.class, userData));
                break;
            case GETDATA_DEVPROGRESSITEMCONVERSIONTABLE:
                theDataList = (List<Object>) itemConversionTableBean.getDataInRange(DevProgressItemConversionTable.class, userData,
                                                                                    Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEVPROGRESSITEMCONVERSIONTABLE:
                theDataList.add(itemConversionTableBean.validateField(dataList.get(1).toString(), (DevProgressItemConversionTable) dataList.get(2), userData));
                break;
            //DevNLPOImportMaster
            case INSERT_DEVNLPOIMPORTMASTER:
                theDataList.add(poImportMasterBean.insert((DevNLPOImportMaster) dataList.get(1), userData));
                break;
            case UPDATE_DEVNLPOIMPORTMASTER:
                theDataList.add(poImportMasterBean.update((DevNLPOImportMaster) dataList.get(1), userData));
                break;
            case DELETE_DEVNLPOIMPORTMASTER:
                theDataList.add(poImportMasterBean.delete((DevNLPOImportMaster) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEVNLPOIMPORTMASTER:
                theDataList.add(poImportMasterBean.getNumRows(DevNLPOImportMaster.class, userData));
                break;
            case GETDATA_DEVNLPOIMPORTMASTER:
                theDataList = (List<Object>) poImportMasterBean.getDataInRange(DevNLPOImportMaster.class, userData,
                                                                               Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEVNLPOIMPORTMASTER:
                theDataList.add(poImportMasterBean.validateField(dataList.get(1).toString(), (DevNLPOImportMaster) dataList.get(2), userData));
                break;
            //DevNLPOImportLines
            case INSERT_DEVNLPOIMPORTLINES:
                theDataList.add(poImportLinesBean.insert((DevNLPOImportLines) dataList.get(1), userData));
                break;
            case UPDATE_DEVNLPOIMPORTLINES:
                theDataList.add(poImportLinesBean.update((DevNLPOImportLines) dataList.get(1), userData));
                break;
            case DELETE_DEVNLPOIMPORTLINES:
                theDataList.add(poImportLinesBean.delete((DevNLPOImportLines) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEVNLPOIMPORTLINES:
                theDataList.add(poImportLinesBean.getNumRows(DevNLPOImportLines.class, userData));
                break;
            case GETDATA_DEVNLPOIMPORTLINES:
                theDataList = (List<Object>) poImportLinesBean.getDataInRange(DevNLPOImportLines.class, userData,
                                                                              Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEVNLPOIMPORTLINES:
                theDataList.add(poImportLinesBean.validateField(dataList.get(1).toString(), (DevNLPOImportLines) dataList.get(2), userData));
                break;
            //DevNLPriceImportTable
            case INSERT_DEVNLPRICEIMPORTTABLE:
                theDataList.add(priceImportBean.insert((DevNLPriceImportTable) dataList.get(1), userData));
                break;
            case UPDATE_DEVNLPRICEIMPORTTABLE:
                theDataList.add(priceImportBean.update((DevNLPriceImportTable) dataList.get(1), userData));
                break;
            case DELETE_DEVNLPRICEIMPORTTABLE:
                theDataList.add(priceImportBean.delete((DevNLPriceImportTable) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEVNLPRICEIMPORTTABLE:
                theDataList.add(priceImportBean.getNumRows(DevNLPriceImportTable.class, userData));
                break;
            case GETDATA_DEVNLPRICEIMPORTTABLE:
                theDataList = (List<Object>) priceImportBean.getDataInRange(DevNLPriceImportTable.class, userData,
                                                                            Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEVNLPRICEIMPORTTABLE:
                theDataList.add(priceImportBean.validateField(dataList.get(1).toString(), (DevNLPriceImportTable) dataList.get(2), userData));
                break;
            case UPDATE_ITEM_PRICES:
                theDataList.add(priceImportBean.updateItemPrices(userData));
                break;
            //Dev Bugs Table
            case INSERT_DEVBUGSTABLE:
                theDataList.add(bugsTableBean.insert((DevBugsTable) dataList.get(1), userData));
                break;
            case UPDATE_DEVBUGSTABLE:
                theDataList.add(bugsTableBean.update((DevBugsTable) dataList.get(1), userData));
                break;
            case DELETE_DEVBUGSTABLE:
                theDataList.add(bugsTableBean.delete((DevBugsTable) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEVBUGSTABLE:
                theDataList.add(bugsTableBean.getNumRows(DevBugsTable.class, userData));
                break;
            case GETDATA_DEVBUGSTABLE:
                theDataList = (List<Object>) bugsTableBean.getDataInRange(DevBugsTable.class, userData,
                                                                          Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEVBUGSTABLE:
                theDataList.add(bugsTableBean.validateField(dataList.get(1).toString(), (DevBugsTable) dataList.get(2), userData));
                break;
            case PRINT_BUG_LIST:
                theDataList = bugsListReportBean.getReportResult(dataList, userData);
                break;
            case CLOSE_TASKS_BUG_LIST_FROM_DATE:
                theDataList.add(bugsTableBean.closeTasksFromDate(dataList.get(1).toString(),(Date)dataList.get(2), (Date)dataList.get(3),userData));
                break;
            case CLOSE_TASKS_BUG_LIST_FROM_NUMBER:
                theDataList.add(bugsTableBean.closeTasksFromNumber(dataList.get(1).toString(), dataList.get(2).toString(), userData));
                break;
            //Generate Report XML
            case GENERATE_REPORT_XML:
                theDataList.add(devToolsBean.generateReportFieldsXML((String) dataList.get(1), userData));
                break;
            //DevParameters
            case INSERT_DEVPARAMETERS:
                theDataList.add(paramBean.insert((DevParameters) dataList.get(1), userData));
                break;
            case UPDATE_DEVPARAMETERS:
                theDataList.add(paramBean.update((DevParameters) dataList.get(1), userData));
                break;
            case DELETE_DEVPARAMETERS:
                theDataList.add(paramBean.delete((DevParameters) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEVPARAMETERS:
                theDataList.add(paramBean.getNumRows(DevParameters.class, userData));
                break;
            case GETDATA_DEVPARAMETERS:
                theDataList = (List<Object>) paramBean.getDataInRange(DevParameters.class, userData,
                                                                      Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEVPARAMETERS:
                theDataList.add(paramBean.validateField(dataList.get(1).toString(), (DevParameters) dataList.get(2), userData));
                break;
            //DevSiteMaster
            case INSERT_DEVSITEMASTER:
                theDataList.add(siteMasterBean.insert((DevSiteMaster) dataList.get(1), userData));
                break;
            case UPDATE_DEVSITEMASTER:
                theDataList.add(siteMasterBean.update((DevSiteMaster) dataList.get(1), userData));
                break;
            case DELETE_DEVSITEMASTER:
                theDataList.add(siteMasterBean.delete((DevSiteMaster) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEVSITEMASTER:
                theDataList.add(siteMasterBean.getNumRows(DevSiteMaster.class, userData));
                break;
            case GETDATA_DEVSITEMASTER:
                theDataList = (List<Object>) siteMasterBean.getDataInRange(DevSiteMaster.class, userData,
                                                                           Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEVSITEMASTER:
                theDataList.add(siteMasterBean.validateField(dataList.get(1).toString(), (DevSiteMaster) dataList.get(2), userData));
                break;
            case GET_OLD_AUTH_KEY:
                theDataList.add(AuthorizationKeyManager.getOldAuthKey((String) dataList.get(1)));
                break;
            //DevSiteLines
            case INSERT_DEVSITELINES:
                theDataList.add(siteLinesBean.insert((DevSiteLines) dataList.get(1), userData));
                break;
            case UPDATE_DEVSITELINES:
                theDataList.add(siteLinesBean.update((DevSiteLines) dataList.get(1), userData));
                break;
            case DELETE_DEVSITELINES:
                theDataList.add(siteLinesBean.delete((DevSiteLines) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEVSITELINES:
                theDataList.add(siteLinesBean.getNumRows(DevSiteLines.class, userData));
                break;
            case GETDATA_DEVSITELINES:
                theDataList = (List<Object>) siteLinesBean.getDataInRange(DevSiteLines.class, userData,
                                                                          Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEVSITELINES:
                theDataList.add(siteLinesBean.validateField(dataList.get(1).toString(), (DevSiteLines) dataList.get(2), userData));
                break;
            //Time Sheet
            case PRINT_TIME_SHEET:
                theDataList = timeSheetReport.getReportResult(dataList, userData);
                break;
            //DevEntityLog
            case INSERT_DEVENTITYLOG:
                theDataList.add(entityLogBean.insert((emc.entity.developertools.DevEntityLog) dataList.get(1), userData));
                break;
            case UPDATE_DEVENTITYLOG:
                theDataList.add(entityLogBean.update((emc.entity.developertools.DevEntityLog) dataList.get(1), userData));
                break;
            case DELETE_DEVENTITYLOG:
                theDataList.add(entityLogBean.delete((emc.entity.developertools.DevEntityLog) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEVENTITYLOG:
                theDataList.add(entityLogBean.getNumRows(emc.entity.developertools.DevEntityLog.class, userData));
                break;
            case GETDATA_DEVENTITYLOG:
                theDataList = (List<Object>) entityLogBean.getDataInRange(emc.entity.developertools.DevEntityLog.class, userData,
                                                                          Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEVENTITYLOG:
                theDataList.add(entityLogBean.validateField(dataList.get(1).toString(), (emc.entity.developertools.DevEntityLog) dataList.get(2), userData));
                break;
            case GET_ENTITY_LOGS_FOR_CUSTOMER:
                theDataList.add(entityLogBean.getEntityLogQueries(dataList.get(1).toString(), userData));
                break;
            case EXECUTE_ENTITY_LOG:
                entityLogBean.executeEntityLogQueries((List<String>) dataList.get(1), userData);
                break;
            //DevProjectMaster
            case INSERT_DEVPROJECTMASTER:
                theDataList.add(projectMasterBean.insert((DevProjectMaster) dataList.get(1), userData));
                break;
            case UPDATE_DEVPROJECTMASTER:
                theDataList.add(projectMasterBean.update((DevProjectMaster) dataList.get(1), userData));
                break;
            case DELETE_DEVPROJECTMASTER:
                theDataList.add(projectMasterBean.delete((DevProjectMaster) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEVPROJECTMASTER:
                theDataList.add(projectMasterBean.getNumRows(DevProjectMaster.class, userData));
                break;
            case GETDATA_DEVPROJECTMASTER:
                theDataList = (List<Object>) projectMasterBean.getDataInRange(DevProjectMaster.class, userData,
                                                                              Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEVPROJECTMASTER:
                theDataList.add(projectMasterBean.validateField(dataList.get(1).toString(), (DevProjectMaster) dataList.get(2), userData));
                break;
            case ADD_PROJECT_FROM_ADMIN_TOOL:
                theDataList.add(projectMasterBean.createProject(dataList.get(1).toString(), dataList.get(2).toString(), dataList.get(3).toString(), dataList.get(4).toString(), (List<String>) dataList.get(5), userData));
                break;
            case GET_ADMIN_TOOL_PROJECTS:
                theDataList.add(projectMasterBean.getEMCProjectForAdminTool(userData));
                break;
            case GET_ADMIN_TOOL_PROJECTS_LINES:
                theDataList.add(projectMasterBean.getEMCProjectLinesForAdminTool(dataList.get(1).toString(), userData));
                break;
            case GET_ADMIN_TOOL_PROJECT_DESC:
                theDataList.add(projectMasterBean.getEMCProjectDescriptionForAdminTool(dataList.get(1).toString(), userData));
                break;
            //DevProjectLines
            case INSERT_DEVPROJECTLINES:
                theDataList.add(projectLinesBean.insert((DevProjectLines) dataList.get(1), userData));
                break;
            case UPDATE_DEVPROJECTLINES:
                theDataList.add(projectLinesBean.update((DevProjectLines) dataList.get(1), userData));
                break;
            case DELETE_DEVPROJECTLINES:
                theDataList.add(projectLinesBean.delete((DevProjectLines) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEVPROJECTLINES:
                theDataList.add(projectLinesBean.getNumRows(DevProjectLines.class, userData));
                break;
            case GETDATA_DEVPROJECTLINES:
                theDataList = (List<Object>) projectLinesBean.getDataInRange(DevProjectLines.class, userData,
                                                                             Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEVPROJECTLINES:
                theDataList.add(projectLinesBean.validateField(dataList.get(1).toString(), (DevProjectLines) dataList.get(2), userData));
                break;
            //DevDeploymentUpdateLog
            case INSERT_DEVDEPLOYMENTUPDATELOG:
                theDataList.add(deploymentUpdateLogBean.insert((DevDeploymentUpdateLog) dataList.get(1), userData));
                break;
            case UPDATE_DEVDEPLOYMENTUPDATELOG:
                theDataList.add(deploymentUpdateLogBean.update((DevDeploymentUpdateLog) dataList.get(1), userData));
                break;
            case DELETE_DEVDEPLOYMENTUPDATELOG:
                theDataList.add(deploymentUpdateLogBean.delete((DevDeploymentUpdateLog) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEVDEPLOYMENTUPDATELOG:
                theDataList.add(deploymentUpdateLogBean.getNumRows(DevDeploymentUpdateLog.class, userData));
                break;
            case GETDATA_DEVDEPLOYMENTUPDATELOG:
                theDataList = (List<Object>) deploymentUpdateLogBean.getDataInRange(DevDeploymentUpdateLog.class, userData,
                                                                                    Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEVDEPLOYMENTUPDATELOG:
                theDataList.add(deploymentUpdateLogBean.validateField(dataList.get(1).toString(), (DevDeploymentUpdateLog) dataList.get(2), userData));
                break;
            case UPDATE_DEPLOYMENT_LOG:
                deploymentUpdateLogBean.logUpdate(dataList.get(1).toString(), (Integer) dataList.get(2), (List<Object[]>) dataList.get(3), userData);
                break;
            //DevDeploymentUpdateLogDS
            case INSERT_DEVDEPLOYMENTUPDATELOGDS:
                theDataList.add(deploymentUpdateLogDSBean.insert((DevDeploymentUpdateLogDS) dataList.get(1), userData));
                break;
            case UPDATE_DEVDEPLOYMENTUPDATELOGDS:
                theDataList.add(deploymentUpdateLogDSBean.update((DevDeploymentUpdateLogDS) dataList.get(1), userData));
                break;
            case DELETE_DEVDEPLOYMENTUPDATELOGDS:
                theDataList.add(deploymentUpdateLogDSBean.delete((DevDeploymentUpdateLogDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEVDEPLOYMENTUPDATELOGDS:
                theDataList.add(deploymentUpdateLogDSBean.getNumRows(DevDeploymentUpdateLogDS.class, userData));
                break;
            case GETDATA_DEVDEPLOYMENTUPDATELOGDS:
                theDataList = (List<Object>) deploymentUpdateLogDSBean.getDataInRange(DevDeploymentUpdateLogDS.class, userData,
                                                                                      Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEVDEPLOYMENTUPDATELOGDS:
                theDataList.add(deploymentUpdateLogDSBean.validateField(dataList.get(1).toString(), (DevDeploymentUpdateLogDS) dataList.get(2), userData));
                break;
            //DevInstallationMaster
            case INSERT_DEVINSTALLATIONMASTER:
                theDataList.add(installationMasterBean.insert((DevInstallationMaster) dataList.get(1), userData));
                break;
            case UPDATE_DEVINSTALLATIONMASTER:
                theDataList.add(installationMasterBean.update((DevInstallationMaster) dataList.get(1), userData));
                break;
            case DELETE_DEVINSTALLATIONMASTER:
                theDataList.add(installationMasterBean.delete((DevInstallationMaster) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEVINSTALLATIONMASTER:
                theDataList.add(installationMasterBean.getNumRows(DevInstallationMaster.class, userData));
                break;
            case GETDATA_DEVINSTALLATIONMASTER:
                theDataList = (List<Object>) installationMasterBean.getDataInRange(DevInstallationMaster.class, userData,
                                                                                   Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEVINSTALLATIONMASTER:
                theDataList.add(installationMasterBean.validateField(dataList.get(1).toString(), (DevInstallationMaster) dataList.get(2), userData));
                break;
            case GET_INSTALLATION_NAMES:
                theDataList.add(installationMasterBean.getInstallationNames(userData));
                break;
            case GET_INSTALLATION_PROPERTIES:
                theDataList.add(installationMasterBean.getInstallationProperties(dataList.get(1).toString(), userData));
                break;
            case SAVE_INSTALLATION_PROPERTIES:
                installationMasterBean.saveInstallationProperties(dataList.get(1).toString(), (Map<String, String>) dataList.get(2), userData);
                break;
            //DevInstallationLines
            case INSERT_DEVINSTALLATIONLINES:
                theDataList.add(installationLinesBean.insert((DevInstallationLines) dataList.get(1), userData));
                break;
            case UPDATE_DEVINSTALLATIONLINES:
                theDataList.add(installationLinesBean.update((DevInstallationLines) dataList.get(1), userData));
                break;
            case DELETE_DEVINSTALLATIONLINES:
                theDataList.add(installationLinesBean.delete((DevInstallationLines) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEVINSTALLATIONLINES:
                theDataList.add(installationLinesBean.getNumRows(DevInstallationLines.class, userData));
                break;
            case GETDATA_DEVINSTALLATIONLINES:
                theDataList = (List<Object>) installationLinesBean.getDataInRange(DevInstallationLines.class, userData,
                                                                                  Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEVINSTALLATIONLINES:
                theDataList.add(installationLinesBean.validateField(dataList.get(1).toString(), (DevInstallationLines) dataList.get(2), userData));
                break;
            //DevVersioningControl
            case INSERT_DEVVERSIONINGCONTROL:
                theDataList.add(versioningControlBean.insert((DevVersioningControl) dataList.get(1), userData));
                break;
            case UPDATE_DEVVERSIONINGCONTROL:
                theDataList.add(versioningControlBean.update((DevVersioningControl) dataList.get(1), userData));
                break;
            case DELETE_DEVVERSIONINGCONTROL:
                theDataList.add(versioningControlBean.delete((DevVersioningControl) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEVVERSIONINGCONTROL:
                theDataList.add(versioningControlBean.getNumRows(DevVersioningControl.class, userData));
                break;
            case GETDATA_DEVVERSIONINGCONTROL:
                theDataList = (List<Object>) versioningControlBean.getDataInRange(DevVersioningControl.class, userData,
                                                                                  Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEVVERSIONINGCONTROL:
                theDataList.add(versioningControlBean.validateField(dataList.get(1).toString(), (DevVersioningControl) dataList.get(2), userData));
                break;
            case GET_VERSIONING_CUSTOMERS:
                theDataList.add(versioningControlBean.getVersionedCustomers(userData));
                break;
            case GET_VERSIONING_CUSTOMER_URL:
                theDataList.add(versioningControlBean.getRepositoryURL(dataList.get(1).toString(), userData));
                break;
            //DevDeploymentUpdateLogLines
            case INSERT_DEVDEPLOYMENTUPDATELOGLINES:
                theDataList.add(deploymentUpdateLogLinesBean.insert((DevDeploymentUpdateLogLines) dataList.get(1), userData));
                break;
            case UPDATE_DEVDEPLOYMENTUPDATELOGLINES:
                theDataList.add(deploymentUpdateLogLinesBean.update((DevDeploymentUpdateLogLines) dataList.get(1), userData));
                break;
            case DELETE_DEVDEPLOYMENTUPDATELOGLINES:
                theDataList.add(deploymentUpdateLogLinesBean.delete((DevDeploymentUpdateLogLines) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEVDEPLOYMENTUPDATELOGLINES:
                theDataList.add(deploymentUpdateLogLinesBean.getNumRows(DevDeploymentUpdateLogLines.class, userData));
                break;
            case GETDATA_DEVDEPLOYMENTUPDATELOGLINES:
                theDataList = (List<Object>) deploymentUpdateLogLinesBean.getDataInRange(DevDeploymentUpdateLogLines.class, userData,
                                                                                         Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEVDEPLOYMENTUPDATELOGLINES:
                theDataList.add(deploymentUpdateLogLinesBean.validateField(dataList.get(1).toString(), (DevDeploymentUpdateLogLines) dataList.get(2), userData));
                break;
            //DevTimeSheet
            case INSERT_DEVTIMESHEET:
                theDataList.add(timeSheetBean.insert((DevTimeSheet) dataList.get(1), userData));
                break;
            case UPDATE_DEVTIMESHEET:
                theDataList.add(timeSheetBean.update((DevTimeSheet) dataList.get(1), userData));
                break;
            case DELETE_DEVTIMESHEET:
                theDataList.add(timeSheetBean.delete((DevTimeSheet) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEVTIMESHEET:
                theDataList.add(timeSheetBean.getNumRows(DevTimeSheet.class, userData));
                break;
            case GETDATA_DEVTIMESHEET:
                theDataList = (List<Object>) timeSheetBean.getDataInRange(DevTimeSheet.class, userData,
                                                                          Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEVTIMESHEET:
                theDataList.add(timeSheetBean.validateField(dataList.get(1).toString(), (DevTimeSheet) dataList.get(2), userData));
                break;
            case FETCH_MATCHING_TASKS:
                theDataList = (List) timeSheetBean.fetchPosibleMatchingTasks((DevTimeSheet) dataList.get(1), userData);
                break;
            case PERSIST_TIME_SHEET_TASK:
                theDataList.add(timeSheetBean.saveTask((DevBugsTable) dataList.get(1), userData));
                break;
            case COMPLETE_TASK:
                theDataList.add(timeSheetBean.completeTask((DevTimeSheet) dataList.get(1), (DevTimeSheetTaskHelper) dataList.get(2), userData));
                break;
            case GET_LAST_TASK_END_TIME:
                theDataList.add(timeSheetBean.getLastTaskEndTime((Date) dataList.get(1), userData));
                break;
            //DevTimeSheetDS
            case INSERT_DEVTIMESHEETDS:
                theDataList.add(timeSheetDSBean.insert((DevTimeSheetDS) dataList.get(1), userData));
                break;
            case UPDATE_DEVTIMESHEETDS:
                theDataList.add(timeSheetDSBean.update((DevTimeSheetDS) dataList.get(1), userData));
                break;
            case DELETE_DEVTIMESHEETDS:
                theDataList.add(timeSheetDSBean.delete((DevTimeSheetDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_DEVTIMESHEETDS:
                theDataList.add(timeSheetDSBean.getNumRows(DevTimeSheetDS.class, userData));
                break;
            case GETDATA_DEVTIMESHEETDS:
                theDataList = (List<Object>) timeSheetDSBean.getDataInRange(DevTimeSheetDS.class, userData,
                                                                            Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DEVTIMESHEETDS:
                theDataList.add(timeSheetDSBean.validateField(dataList.get(1).toString(), (DevTimeSheetDS) dataList.get(2), userData));
                break;
            case PRINT_REQUIREMENTS_SHEET:
                theDataList = requirementsSheetBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            case PRINT_DEVELOPMENT_SUMMARY_SHEET:
                theDataList = developmentSummaryBean.getReportResult(dataList, cmd.getReportConfig(), userData);
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
