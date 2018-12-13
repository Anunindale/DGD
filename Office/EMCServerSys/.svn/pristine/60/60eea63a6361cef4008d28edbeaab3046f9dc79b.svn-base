/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor. 
 */
package emc.server.commandmanager.workflow;

import emc.bus.workflow.WFActCatLocal;
import emc.bus.workflow.WFActGroupEmpDSLocal;
import emc.bus.workflow.WFActGroupEmpLocal;
import emc.bus.workflow.WFActGroupsLocal;
import emc.bus.workflow.WFActPriorLocal;
import emc.bus.workflow.WFActStatusLocal;
import emc.bus.workflow.WFActTypeLocal;
import emc.bus.workflow.WFActivityDSLocal;
import emc.bus.workflow.WFActivityLocal;
import emc.bus.workflow.WFDepartmentsLocal;
import emc.bus.workflow.WFEmpSkillLocal;
import emc.bus.workflow.WFEmployeeTypesLocal;
import emc.bus.workflow.WFJobLinesLocal;
import emc.bus.workflow.WFJobMasterLocal;
import emc.bus.workflow.WFJobTitlesLocal;
import emc.bus.workflow.WFLinesLocal;
import emc.bus.workflow.WFMasterLocal;
import emc.bus.workflow.WFRatingsLocal;
import emc.bus.workflow.WFSkillsLocal;
import emc.bus.workflow.activitiesmanager.WFMyActivitiesDataManagerLocal;
import emc.bus.workflow.activityalerts.WFActivityAlertsLocal;
import emc.bus.workflow.skillsenquiry.WorkFlowSkillEnquiryLocal;
import emc.commands.EMCCommands;
import emc.entity.workflow.WFActivityAlerts;
import emc.entity.workflow.WorkFlowActivity;
import emc.entity.workflow.WorkFlowActivityCate;
import emc.entity.workflow.WorkFlowActivityGroupEmp;
import emc.entity.workflow.WorkFlowActivityGroups;
import emc.entity.workflow.WorkFlowActivityPriority;
import emc.entity.workflow.WorkFlowActivityStatus;
import emc.entity.workflow.WorkFlowActivityTypes;
import emc.entity.workflow.WorkFlowDepartment;
import emc.entity.workflow.WorkFlowEmployeeSkills;
import emc.entity.workflow.WorkFlowEmployeeType;
import emc.entity.workflow.WorkFlowJobLines;
import emc.entity.workflow.WorkFlowJobMaster;
import emc.entity.workflow.WorkFlowJobTitles;
import emc.entity.workflow.WorkFlowLines;
import emc.entity.workflow.WorkFlowMaster;
import emc.entity.workflow.WorkFlowRating;
import emc.entity.workflow.WorkFlowSkill;
import emc.entity.workflow.WorkFlowSkillsEnquiry;
import emc.entity.workflow.datasource.WorkFlowActivityDS;
import emc.entity.workflow.datasource.WorkFlowActivityGroupEmpDS;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCUserData;
import emc.methods.workflow.ClientWorkFlowMethods;
import emc.methods.workflow.ServerWorkFlowMethods;
import java.util.ArrayList;
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
public class WorkFlowMethodMapperBean implements WorkFlowMethodMapperBeanLocal {

    @EJB
    private WFLinesLocal wFLinesBean;
    @EJB
    private WFMasterLocal wFMasterBean;
    @EJB
    private WFJobMasterLocal wFJobMasterBean;
    @EJB
    private WFJobLinesLocal wFJobLinesBean;
    @EJB
    private WFActivityLocal wFActivityBean;
    @EJB
    private WFActGroupEmpLocal wFActGroupEmpLocalBean;
    @EJB
    private WFActTypeLocal wFAcTypeLocalBean;
    @EJB
    private WFDepartmentsLocal wFDepartmentsBean;
    @EJB
    private WFActStatusLocal wFActStatusBean;
    @EJB
    private WFActPriorLocal wFActPriorBean;
    @EJB
    private WFActGroupsLocal wFActGroupsBean;
    @EJB
    private WFActCatLocal wFActCateBean;
    @EJB
    private WFEmpSkillLocal wFEmployeeSkillBean;
    @EJB
    private WFEmployeeTypesLocal wFEmployeeTypesBean;
    @EJB
    private WFJobTitlesLocal wFJobTitlesBean;
    @EJB
    private WFRatingsLocal wFRatingsBean;
    @EJB
    private WFSkillsLocal wFSkillsBean;
    @EJB
    private WFActivityDSLocal wfActivityDSBean;
    @EJB
    private WFActGroupEmpDSLocal wfActGroupEmpDSBean;
    @EJB
    private WFMyActivitiesDataManagerLocal myActivitiesDataManagerBean;
    @EJB
    private WFActivityAlertsLocal activityAlertBean;
    @EJB
    private WorkFlowSkillEnquiryLocal skillsEnquiryBean;

    public WorkFlowMethodMapperBean() {
    }

    public List mapMethodWorkFlow(EMCCommandClass cmd, List<Object> dataList, EMCUserData userData) throws EMCEntityBeanException {
        List<Object> theDataList = new ArrayList();
        EMCCommandClass retCmd = new EMCCommandClass();
        retCmd.setCommandId(EMCCommands.CLIENT_GENERAL_COMMAND.getId());
        retCmd.setModuleNumber(enumEMCModules.WORKFLOW.getId());
        retCmd.setMethodId(ClientWorkFlowMethods.GENERAL_METHOD.toString());

        switch (ServerWorkFlowMethods.fromString(cmd.getMethodId())) {
            //WF skill
            case INSERT_WORKFLOWSKILL:
                theDataList.add(wFSkillsBean.insert((WorkFlowSkill) dataList.get(1), userData));
                break;
            case UPDATE_WORKFLOWSKILL:
                theDataList.add(wFSkillsBean.update((WorkFlowSkill) dataList.get(1), userData));
                break;
            case DELETE_WORKFLOWSKILL:
                theDataList.add(wFSkillsBean.delete((WorkFlowSkill) dataList.get(1), userData));
                break;
            case GETNUMROWS_WORKFLOWSKILL:
                theDataList.add(wFSkillsBean.getNumRows(WorkFlowSkill.class, userData));
                break;
            case GETDATA_WORKFLOWSKILL:
                theDataList = (List<Object>) wFSkillsBean.getDataInRange(WorkFlowSkill.class, userData,
                                                                         Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_WORKFLOWSKILL:
                theDataList.add(wFSkillsBean.validateField(dataList.get(1).toString(), (WorkFlowSkill) dataList.get(2), userData));
                break;
            //WF Rating
            case INSERT_WORKFLOWRATING:
                theDataList.add(wFRatingsBean.insert((WorkFlowRating) dataList.get(1), userData));
                break;
            case UPDATE_WORKFLOWRATING:
                theDataList.add(wFRatingsBean.update((WorkFlowRating) dataList.get(1), userData));
                break;
            case DELETE_WORKFLOWRATING:
                theDataList.add(wFRatingsBean.delete((WorkFlowRating) dataList.get(1), userData));
                break;
            case GETNUMROWS_WORKFLOWRATING:
                theDataList.add(wFRatingsBean.getNumRows(WorkFlowRating.class, userData));
                break;
            case GETDATA_WORKFLOWRATING:
                theDataList = (List<Object>) wFRatingsBean.getDataInRange(WorkFlowRating.class, userData,
                                                                          Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_WORKFLOWRATING:
                theDataList.add(wFRatingsBean.validateField(dataList.get(1).toString(), (WorkFlowRating) dataList.get(2), userData));
                break;
            //WF JobTitles
            case INSERT_WORKFLOWJOBTITLES:
                theDataList.add(wFJobTitlesBean.insert((WorkFlowJobTitles) dataList.get(1), userData));
                break;
            case UPDATE_WORKFLOWJOBTITLES:
                theDataList.add(wFJobTitlesBean.update((WorkFlowJobTitles) dataList.get(1), userData));
                break;
            case DELETE_WORKFLOWJOBTITLES:
                theDataList.add(wFJobTitlesBean.delete((WorkFlowJobTitles) dataList.get(1), userData));
                break;
            case GETNUMROWS_WORKFLOWJOBTITLES:
                theDataList.add(wFJobTitlesBean.getNumRows(WorkFlowJobTitles.class, userData));
                break;
            case GETDATA_WORKFLOWJOBTITLES:
                theDataList = (List<Object>) wFJobTitlesBean.getDataInRange(WorkFlowJobTitles.class, userData,
                                                                            Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_WORKFLOWJOBTITLES:
                theDataList.add(wFJobTitlesBean.validateField(dataList.get(1).toString(), (WorkFlowJobTitles) dataList.get(2), userData));
                break;
            //WF Employee Types
            case INSERT_WORKFLOWEMPLOYEETYPE:
                theDataList.add(wFEmployeeTypesBean.insert((WorkFlowEmployeeType) dataList.get(1), userData));
                break;
            case UPDATE_WORKFLOWEMPLOYEETYPE:
                theDataList.add(wFEmployeeTypesBean.update((WorkFlowEmployeeType) dataList.get(1), userData));
                break;
            case DELETE_WORKFLOWEMPLOYEETYPE:
                theDataList.add(wFEmployeeTypesBean.delete((WorkFlowEmployeeType) dataList.get(1), userData));
                break;
            case GETNUMROWS_WORKFLOWEMPLOYEETYPE:
                theDataList.add(wFEmployeeTypesBean.getNumRows(WorkFlowEmployeeType.class, userData));
                break;
            case GETDATA_WORKFLOWEMPLOYEETYPE:
                theDataList = (List<Object>) wFEmployeeTypesBean.getDataInRange(WorkFlowEmployeeType.class, userData,
                                                                                Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_WORKFLOWEMPLOYEETYPE:
                theDataList.add(wFEmployeeTypesBean.validateField(dataList.get(1).toString(), (WorkFlowEmployeeType) dataList.get(2), userData));
                break;
            //WF EmployeeSkills
            case INSERT_WORKFLOWEMPLOYEESKILLS:
                theDataList.add(wFEmployeeSkillBean.insert((WorkFlowEmployeeSkills) dataList.get(1), userData));
                break;
            case UPDATE_WORKFLOWEMPLOYEESKILLS:
                theDataList.add(wFEmployeeSkillBean.update((WorkFlowEmployeeSkills) dataList.get(1), userData));
                break;
            case DELETE_WORKFLOWEMPLOYEESKILLS:
                theDataList.add(wFEmployeeSkillBean.delete((WorkFlowEmployeeSkills) dataList.get(1), userData));
                break;
            case GETNUMROWS_WORKFLOWEMPLOYEESKILLS:
                theDataList.add(wFEmployeeSkillBean.getNumRows(WorkFlowEmployeeSkills.class, userData));
                break;
            case GETDATA_WORKFLOWEMPLOYEESKILLS:
                theDataList = (List<Object>) wFEmployeeSkillBean.getDataInRange(WorkFlowEmployeeSkills.class, userData,
                                                                                Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_WORKFLOWEMPLOYEESKILLS:
                theDataList.add(wFEmployeeSkillBean.validateField(dataList.get(1).toString(), (WorkFlowEmployeeSkills) dataList.get(2), userData));
                break;
            //WF Activity categories
            case INSERT_WORKFLOWACTIVITYCATE:
                theDataList.add(wFActCateBean.insert((WorkFlowActivityCate) dataList.get(1), userData));
                break;
            case UPDATE_WORKFLOWACTIVITYCATE:
                theDataList.add(wFActCateBean.update((WorkFlowActivityCate) dataList.get(1), userData));
                break;
            case DELETE_WORKFLOWACTIVITYCATE:
                theDataList.add(wFActCateBean.delete((WorkFlowActivityCate) dataList.get(1), userData));
                break;
            case GETNUMROWS_WORKFLOWACTIVITYCATE:
                theDataList.add(wFActCateBean.getNumRows(WorkFlowActivityCate.class, userData));
                break;
            case GETDATA_WORKFLOWACTIVITYCATE:
                theDataList = (List<Object>) wFActCateBean.getDataInRange(WorkFlowActivityCate.class, userData,
                                                                          Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_WORKFLOWACTIVITYCATE:
                theDataList.add(wFActCateBean.validateField(dataList.get(1).toString(), (WorkFlowActivityCate) dataList.get(2), userData));
                break;
            //WF Activity groups
            case INSERT_WORKFLOWACTIVITYGROUPS:
                theDataList.add(wFActGroupsBean.insert((WorkFlowActivityGroups) dataList.get(1), userData));
                break;
            case UPDATE_WORKFLOWACTIVITYGROUPS:
                theDataList.add(wFActGroupsBean.update((WorkFlowActivityGroups) dataList.get(1), userData));
                break;
            case DELETE_WORKFLOWACTIVITYGROUPS:
                theDataList.add(wFActGroupsBean.delete((WorkFlowActivityGroups) dataList.get(1), userData));
                break;
            case GETNUMROWS_WORKFLOWACTIVITYGROUPS:
                theDataList.add(wFActGroupsBean.getNumRows(WorkFlowActivityGroups.class, userData));
                break;
            case GETDATA_WORKFLOWACTIVITYGROUPS:
                theDataList = (List<Object>) wFActGroupsBean.getDataInRange(WorkFlowActivityGroups.class, userData,
                                                                            Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_WORKFLOWACTIVITYGROUPS:
                theDataList.add(wFActGroupsBean.validateField(dataList.get(1).toString(), (WorkFlowActivityGroups) dataList.get(2), userData));
                break;
            //WF Activity priority
            case INSERT_WORKFLOWACTIVITYPRIORITY:
                theDataList.add(wFActPriorBean.insert((WorkFlowActivityPriority) dataList.get(1), userData));
                break;
            case UPDATE_WORKFLOWACTIVITYPRIORITY:
                theDataList.add(wFActPriorBean.update((WorkFlowActivityPriority) dataList.get(1), userData));
                break;
            case DELETE_WORKFLOWACTIVITYPRIORITY:
                theDataList.add(wFActPriorBean.delete((WorkFlowActivityPriority) dataList.get(1), userData));
                break;
            case GETNUMROWS_WORKFLOWACTIVITYPRIORITY:
                theDataList.add(wFActPriorBean.getNumRows(WorkFlowActivityPriority.class, userData));
                break;
            case GETDATA_WORKFLOWACTIVITYPRIORITY:
                theDataList = (List<Object>) wFActPriorBean.getDataInRange(WorkFlowActivityPriority.class, userData,
                                                                           Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_WORKFLOWACTIVITYPRIORITY:
                theDataList.add(wFActPriorBean.validateField(dataList.get(1).toString(), (WorkFlowActivityPriority) dataList.get(2), userData));
                break;
            //WF Activity Status
            case INSERT_WORKFLOWACTIVITYSTATUS:
                theDataList.add(wFActStatusBean.insert((WorkFlowActivityStatus) dataList.get(1), userData));
                break;
            case UPDATE_WORKFLOWACTIVITYSTATUS:
                theDataList.add(wFActStatusBean.update((WorkFlowActivityStatus) dataList.get(1), userData));
                break;
            case DELETE_WORKFLOWACTIVITYSTATUS:
                theDataList.add(wFActStatusBean.delete((WorkFlowActivityStatus) dataList.get(1), userData));
                break;
            case GETNUMROWS_WORKFLOWACTIVITYSTATUS:
                theDataList.add(wFActStatusBean.getNumRows(WorkFlowActivityStatus.class, userData));
                break;
            case GETDATA_WORKFLOWACTIVITYSTATUS:
                theDataList = (List<Object>) wFActStatusBean.getDataInRange(WorkFlowActivityStatus.class, userData,
                                                                            Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_WORKFLOWACTIVITYSTATUS:
                theDataList.add(wFActStatusBean.validateField(dataList.get(1).toString(), (WorkFlowActivityStatus) dataList.get(2), userData));
                break;
            //WF Departments
            case INSERT_WORKFLOWDEPARTMENT:
                theDataList.add(wFDepartmentsBean.insert((WorkFlowDepartment) dataList.get(1), userData));
                break;
            case UPDATE_WORKFLOWDEPARTMENT:
                theDataList.add(wFDepartmentsBean.update((WorkFlowDepartment) dataList.get(1), userData));
                break;
            case DELETE_WORKFLOWDEPARTMENT:
                theDataList.add(wFDepartmentsBean.delete((WorkFlowDepartment) dataList.get(1), userData));
                break;
            case GETNUMROWS_WORKFLOWDEPARTMENT:
                theDataList.add(wFDepartmentsBean.getNumRows(WorkFlowDepartment.class, userData));
                break;
            case GETDATA_WORKFLOWDEPARTMENT:
                theDataList = (List<Object>) wFDepartmentsBean.getDataInRange(WorkFlowDepartment.class, userData,
                                                                              Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_WORKFLOWDEPARTMENT:
                theDataList.add(wFDepartmentsBean.validateField(dataList.get(1).toString(), (WorkFlowDepartment) dataList.get(2), userData));
                break;
            //WF Activity Types
            case INSERT_WORKFLOWACTIVITYTYPES:
                theDataList.add(wFAcTypeLocalBean.insert((WorkFlowActivityTypes) dataList.get(1), userData));
                break;
            case UPDATE_WORKFLOWACTIVITYTYPES:
                theDataList.add(wFAcTypeLocalBean.update((WorkFlowActivityTypes) dataList.get(1), userData));
                break;
            case DELETE_WORKFLOWACTIVITYTYPES:
                theDataList.add(wFAcTypeLocalBean.delete((WorkFlowActivityTypes) dataList.get(1), userData));
                break;
            case GETNUMROWS_WORKFLOWACTIVITYTYPES:
                theDataList.add(wFAcTypeLocalBean.getNumRows(WorkFlowActivityTypes.class, userData));
                break;
            case GETDATA_WORKFLOWACTIVITYTYPES:
                theDataList = (List<Object>) wFAcTypeLocalBean.getDataInRange(WorkFlowActivityTypes.class, userData,
                                                                              Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_WORKFLOWACTIVITYTYPES:
                theDataList.add(wFAcTypeLocalBean.validateField(dataList.get(1).toString(), (WorkFlowActivityTypes) dataList.get(2), userData));
                break;
            //WF Activity Group Employees
            case INSERT_WORKFLOWACTIVITYGROUPEMP:
                theDataList.add(wFActGroupEmpLocalBean.insert((WorkFlowActivityGroupEmp) dataList.get(1), userData));
                break;
            case UPDATE_WORKFLOWACTIVITYGROUPEMP:
                theDataList.add(wFActGroupEmpLocalBean.update((WorkFlowActivityGroupEmp) dataList.get(1), userData));
                break;
            case DELETE_WORKFLOWACTIVITYGROUPEMP:
                theDataList.add(wFActGroupEmpLocalBean.delete((WorkFlowActivityGroupEmp) dataList.get(1), userData));
                break;
            case GETNUMROWS_WORKFLOWACTIVITYGROUPEMP:
                theDataList.add(wFActGroupEmpLocalBean.getNumRows(WorkFlowActivityGroupEmp.class, userData));
                break;
            case GETDATA_WORKFLOWACTIVITYGROUPEMP:
                theDataList = (List<Object>) wFActGroupEmpLocalBean.getDataInRange(WorkFlowActivityGroupEmp.class, userData,
                                                                                   Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_WORKFLOWACTIVITYGROUPEMP:
                theDataList.add(wFActGroupEmpLocalBean.validateField(dataList.get(1).toString(), (WorkFlowActivityGroupEmp) dataList.get(2), userData));
                break;
            //WF Activities
            case INSERT_WORKFLOWACTIVITY:
                theDataList.add(wFActivityBean.insert((WorkFlowActivity) dataList.get(1), userData));
                break;
            case UPDATE_WORKFLOWACTIVITY:
                theDataList.add(wFActivityBean.update((WorkFlowActivity) dataList.get(1), userData));
                break;
            case DELETE_WORKFLOWACTIVITY:
                theDataList.add(wFActivityBean.delete((WorkFlowActivity) dataList.get(1), userData));
                break;
            case GETNUMROWS_WORKFLOWACTIVITY:
                theDataList.add(wFActivityBean.getNumRows(WorkFlowActivity.class, userData));
                break;
            case GETDATA_WORKFLOWACTIVITY:
                theDataList = (List<Object>) wFActivityBean.getDataInRange(WorkFlowActivity.class, userData,
                                                                           Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_WORKFLOWACTIVITY:
                theDataList.add(wFActivityBean.validateField(dataList.get(1).toString(), (WorkFlowActivity) dataList.get(2), userData));
                break;
            case COPY_WFJOBLINE_ACTIVITY_PERSIST:
                wFActivityBean.copyJobLineToActivityPersist("", (WorkFlowJobLines) dataList.get(1), userData);
                break;
            //WF Job Lines
            case INSERT_WORKFLOWJOBLINES:
                theDataList.add(wFJobLinesBean.insert((WorkFlowJobLines) dataList.get(1), userData));
                break;
            case UPDATE_WORKFLOWJOBLINES:
                theDataList.add(wFJobLinesBean.update((WorkFlowJobLines) dataList.get(1), userData));
                break;
            case DELETE_WORKFLOWJOBLINES:
                theDataList.add(wFJobLinesBean.delete((WorkFlowJobLines) dataList.get(1), userData));
                break;
            case GETNUMROWS_WORKFLOWJOBLINES:
                theDataList.add(wFJobLinesBean.getNumRows(WorkFlowJobLines.class, userData));
                break;
            case GETDATA_WORKFLOWJOBLINES:
                theDataList = (List<Object>) wFJobLinesBean.getDataInRange(WorkFlowJobLines.class, userData,
                                                                           Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_WORKFLOWJOBLINES:
                theDataList.add(wFJobLinesBean.validateField(dataList.get(1).toString(), (WorkFlowJobLines) dataList.get(2), userData));
                break;
            case REDO_WFJOBLINES:
                wFJobLinesBean.redoJobLines(dataList.get(1).toString(), dataList.get(2).toString(), dataList.get(3).toString(), userData);
                break;
            //WF Job Master
            case INSERT_WORKFLOWJOBMASTER:
                theDataList.add(wFJobMasterBean.insert((WorkFlowJobMaster) dataList.get(1), userData));
                break;
            case UPDATE_WORKFLOWJOBMASTER:
                theDataList.add(wFJobMasterBean.update((WorkFlowJobMaster) dataList.get(1), userData));
                break;
            case DELETE_WORKFLOWJOBMASTER:
                theDataList.add(wFJobMasterBean.delete((WorkFlowJobMaster) dataList.get(1), userData));
                break;
            case GETNUMROWS_WORKFLOWJOBMASTER:
                theDataList.add(wFJobMasterBean.getNumRows(WorkFlowJobMaster.class, userData));
                break;
            case GETDATA_WORKFLOWJOBMASTER:
                theDataList = (List<Object>) wFJobMasterBean.getDataInRange(WorkFlowJobMaster.class, userData,
                                                                            Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_WORKFLOWJOBMASTER:
                theDataList.add(wFJobMasterBean.validateField(dataList.get(1).toString(), (WorkFlowJobMaster) dataList.get(2), userData));
                break;
            case ALL_DOCUMENTS_ATTATCHED:
                wFJobMasterBean.allDocumentsAttached((WorkFlowJobMaster) dataList.get(1), userData);
                break;
            case EVALUATE_WFJOBLINES:
                theDataList.add(wFJobMasterBean.evaluateWFJobLines((WorkFlowJobMaster) dataList.get(1), userData));
                break;
            //WF Lines
            case INSERT_WORKFLOWLINES:
                theDataList.add(wFLinesBean.insert((WorkFlowLines) dataList.get(1), userData));
                break;
            case UPDATE_WORKFLOWLINES:
                theDataList.add(wFLinesBean.update((WorkFlowLines) dataList.get(1), userData));
                break;
            case DELETE_WORKFLOWLINES:
                theDataList.add(wFLinesBean.delete((WorkFlowLines) dataList.get(1), userData));
                break;
            case GETNUMROWS_WORKFLOWLINES:
                theDataList.add(wFLinesBean.getNumRows(WorkFlowLines.class, userData));
                break;
            case GETDATA_WORKFLOWLINES:
                theDataList = (List<Object>) wFLinesBean.getDataInRange(WorkFlowLines.class, userData,
                                                                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_WORKFLOWLINES:
                theDataList.add(wFLinesBean.validateField(dataList.get(1).toString(), (WorkFlowLines) dataList.get(2), userData));
                break;
            //WF Master
            case INSERT_WORKFLOWMASTER:
                theDataList.add(wFMasterBean.insert((WorkFlowMaster) dataList.get(1), userData));
                break;
            case UPDATE_WORKFLOWMASTER:
                theDataList.add(wFMasterBean.update((WorkFlowMaster) dataList.get(1), userData));
                break;
            case DELETE_WORKFLOWMASTER:
                theDataList.add(wFMasterBean.delete((WorkFlowMaster) dataList.get(1), userData));
                break;
            case GETNUMROWS_WORKFLOWMASTER:
                theDataList.add(wFMasterBean.getNumRows(WorkFlowMaster.class, userData));
                break;
            case GETDATA_WORKFLOWMASTER:
                theDataList = (List<Object>) wFMasterBean.getDataInRange(WorkFlowMaster.class, userData,
                                                                         Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_WORKFLOWMASTER:
                theDataList.add(wFMasterBean.validateField(dataList.get(1).toString(), (WorkFlowMaster) dataList.get(2), userData));
                break;
            case ALL_DOCUMENTS_ATTATCHED_WFMASTER:
                wFMasterBean.allDocumentsAttached((WorkFlowMaster) dataList.get(1), userData);
                break;
            case COPY_WORKFLOW:
                wFMasterBean.copyWF((WorkFlowMaster) dataList.get(1), dataList.get(2).toString(), userData);
                break;
            case CREATE_TASK:
                wFMasterBean.createJob((WorkFlowMaster) dataList.get(1), dataList.get(2).toString(), userData);
                break;
            case EVALUATE_WFMASTER_LINES:
                theDataList.add(wFMasterBean.evaluateWFLines((WorkFlowMaster) dataList.get(1), userData));
                break;
            //WorkFlowActivityDS
            case INSERT_WORKFLOWACTIVITYDS:
                theDataList.add(wfActivityDSBean.insert((WorkFlowActivityDS) dataList.get(1), userData));
                break;
            case UPDATE_WORKFLOWACTIVITYDS:
                theDataList.add(wfActivityDSBean.update((WorkFlowActivityDS) dataList.get(1), userData));
                break;
            case DELETE_WORKFLOWACTIVITYDS:
                theDataList.add(wfActivityDSBean.delete((WorkFlowActivityDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_WORKFLOWACTIVITYDS:
                theDataList.add(wfActivityDSBean.getNumRows(WorkFlowActivityDS.class, userData));
                break;
            case GETDATA_WORKFLOWACTIVITYDS:
                theDataList = (List<Object>) wfActivityDSBean.getDataInRange(WorkFlowActivityDS.class, userData,
                                                                             Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_WORKFLOWACTIVITYDS:
                theDataList.add(wfActivityDSBean.validateField(dataList.get(1).toString(), (WorkFlowActivityDS) dataList.get(2), userData));
                break;
            //WorkFlowActivityGroupEmpDS
            case INSERT_WORKFLOWACTIVITYGROUPEMPDS:
                theDataList.add(wfActGroupEmpDSBean.insert((WorkFlowActivityGroupEmpDS) dataList.get(1), userData));
                break;
            case UPDATE_WORKFLOWACTIVITYGROUPEMPDS:
                theDataList.add(wfActGroupEmpDSBean.update((WorkFlowActivityGroupEmpDS) dataList.get(1), userData));
                break;
            case DELETE_WORKFLOWACTIVITYGROUPEMPDS:
                theDataList.add(wfActGroupEmpDSBean.delete((WorkFlowActivityGroupEmpDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_WORKFLOWACTIVITYGROUPEMPDS:
                theDataList.add(wfActGroupEmpDSBean.getNumRows(WorkFlowActivityGroupEmpDS.class, userData));
                break;
            case GETDATA_WORKFLOWACTIVITYGROUPEMPDS:
                theDataList = (List<Object>) wfActGroupEmpDSBean.getDataInRange(WorkFlowActivityGroupEmpDS.class, userData,
                                                                                Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_WORKFLOWACTIVITYGROUPEMPDS:
                theDataList.add(wfActGroupEmpDSBean.validateField(dataList.get(1).toString(), (WorkFlowActivityGroupEmpDS) dataList.get(2), userData));
                break;
            case GET_MY_ACTIVITIES_INFO:
                theDataList.add(myActivitiesDataManagerBean.getNumberOfActivities(userData));
                break;
            //WFActivityAlerts
            case INSERT_WFACTIVITYALERTS:
                theDataList.add(activityAlertBean.insert((WFActivityAlerts) dataList.get(1), userData));
                break;
            case UPDATE_WFACTIVITYALERTS:
                theDataList.add(activityAlertBean.update((WFActivityAlerts) dataList.get(1), userData));
                break;
            case DELETE_WFACTIVITYALERTS:
                theDataList.add(activityAlertBean.delete((WFActivityAlerts) dataList.get(1), userData));
                break;
            case GETNUMROWS_WFACTIVITYALERTS:
                theDataList.add(activityAlertBean.getNumRows(WFActivityAlerts.class, userData));
                break;
            case GETDATA_WFACTIVITYALERTS:
                theDataList = (List<Object>) activityAlertBean.getDataInRange(WFActivityAlerts.class, userData,
                                                                              Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_WFACTIVITYALERTS:
                theDataList.add(activityAlertBean.validateField(dataList.get(1).toString(), (WFActivityAlerts) dataList.get(2), userData));
                break;
            case SEND_ACTIVITY_REMINDER:
                activityAlertBean.sendActivityAlert(userData);
                break;
            //WorkFlowSkillsEnquiry
            case INSERT_WORKFLOWSKILLSENQUIRY:
                theDataList.add(skillsEnquiryBean.insert((WorkFlowSkillsEnquiry) dataList.get(1), userData));
                break;
            case UPDATE_WORKFLOWSKILLSENQUIRY:
                theDataList.add(skillsEnquiryBean.update((WorkFlowSkillsEnquiry) dataList.get(1), userData));
                break;
            case DELETE_WORKFLOWSKILLSENQUIRY:
                theDataList.add(skillsEnquiryBean.delete((WorkFlowSkillsEnquiry) dataList.get(1), userData));
                break;
            case GETNUMROWS_WORKFLOWSKILLSENQUIRY:
                theDataList.add(skillsEnquiryBean.getNumRows(WorkFlowSkillsEnquiry.class, userData));
                break;
            case GETDATA_WORKFLOWSKILLSENQUIRY:
                theDataList = (List<Object>) skillsEnquiryBean.getDataInRange(WorkFlowSkillsEnquiry.class, userData,
                                                                              Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_WORKFLOWSKILLSENQUIRY:
                theDataList.add(skillsEnquiryBean.validateField(dataList.get(1).toString(), (WorkFlowSkillsEnquiry) dataList.get(2), userData));
                break;

            case CREATE_NEW_ACTIVITY:
                theDataList.add(wFActivityBean.createNewActivity((WorkFlowActivity) dataList.get(1), userData));
                break;
            case LAUNCH_WORK_FLOW:
                theDataList.add(wFActivityBean.launchWorkFlow((String) dataList.get(1), (String) dataList.get(2), (String) dataList.get(3), (Long) dataList.get(4), (String) dataList.get(5), userData));
                break;
            case REASSIGN_ACTIVITY:
                theDataList.add(wFActivityBean.reassignActivity((Long) dataList.get(1), (String) dataList.get(2), userData));
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
