/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.server.commandmanager.hr;

import emc.bus.hr.absenteeismlog.HRAbsenteeismLogLocal;
import emc.bus.hr.absenteeismlog.datasource.HRAbsenteeismLogDSLocal;
import emc.bus.hr.absenteeismtype.HRAbsenteeismTypeLocal;
import emc.bus.hr.absscarcity.HRAbsScarcityLocal;
import emc.bus.hr.actionresults.HRActionResultsLocal;
import emc.bus.hr.alternativeidtype.HRAlternativeIdTypeLocal;
import emc.bus.hr.branch.HRBranchLocal;
import emc.bus.hr.citizenstatus.HRCitizenStatusLocal;
import emc.bus.hr.course.HRCourseLocal;
import emc.bus.hr.dependants.HREmployeeDependantsLocal;
import emc.bus.hr.disabilitytypes.HRDisabilityTypesLocal;
import emc.bus.hr.disiplaneryaction.HRDisiplaneryActionLocal;
import emc.bus.hr.disiplinaryleve.HRDisiplinaryLevelLocal;
import emc.bus.hr.education.HREmployeeEducationLocal;
import emc.bus.hr.educationpriority.HREducationPriorityLocal;
import emc.bus.hr.edulevel.HREducationLevelLocal;
import emc.bus.hr.employmenttype.HREmploymentTypeLocal;
import emc.bus.hr.equitycode.HREquityCodeLocal;
import emc.bus.hr.explevel.HRExperienceLevelLocal;
import emc.bus.hr.grade.HRGradeLocal;
import emc.bus.hr.grievences.HRGrievencesLocal;
import emc.bus.hr.institution.HRInstitutionLocal;
import emc.bus.hr.internalemploymenthistory.HRInternalEmploymentHistoryLocal;
import emc.bus.hr.jobcatagory.HRJobCatagoryLocal;
import emc.bus.hr.joblevel.HRJobLevelLocal;
import emc.bus.hr.jobstatus.HRJobStatusLocal;
import emc.bus.hr.learningprogram.HRLearningProgramLocal;
import emc.bus.hr.leavelog.HRLeaveLogLocal;
import emc.bus.hr.leavetype.HRLeaveTypeLocal;
import emc.bus.hr.levelofexperience.HRLevelOfExperienceLocal;
import emc.bus.hr.medicalaid.HRMedicalAidLocal;
import emc.bus.hr.medicallog.HRMedicalLogLocal;
import emc.bus.hr.nationality.HRNationalityLocal;
import emc.bus.hr.ofocode.HROfOCodeLocal;
import emc.bus.hr.pivotalinst.HRPivotalInstLocal;
import emc.bus.hr.pivotalprogram.HRPivotalProgramLocal;
import emc.bus.hr.pivotalstudyfield.HRPivotalStudyFieldLocal;
import emc.bus.hr.previousemploymenthistory.HRPreviousEmploymentHistoryLocal;
import emc.bus.hr.qualification.HRQualificationLocal;
import emc.bus.hr.qualtype.HRQualTypeLocal;
import emc.bus.hr.race.HRRaceLocal;
import emc.bus.hr.remunerantiontype.HRRemunerantionTypeLocal;
import emc.bus.hr.scarcecritskills.HRScarceCritSkillsLocal;
import emc.bus.hr.scarcepriority.HRScarcePriorityLocal;
import emc.bus.hr.section.HRSectionLocal;
import emc.bus.hr.socioecostatus.HRSocioEcoStatusLocal;
import emc.bus.hr.terminationlog.HRTerminationLogLocal;
import emc.bus.hr.terminationlog.datasource.HRTerminationLogDSLocal;
import emc.bus.hr.terminationtype.HRTerminationTypeLocal;
import emc.bus.hr.training.HREmployeeTrainingLocal;
import emc.bus.hr.trainingreason.HRTrainingReasonLocal;
import emc.bus.hr.traininglevel.HRTrainingLevelLocal;
import emc.bus.hr.trainingtype.HRTrainingTypeLocal;
import emc.bus.hr.unions.HRUnionsLocal;
import emc.commands.EMCCommands;
import emc.entity.hr.HRAbsScarcity;
import emc.entity.hr.HRAbsenteeismLog;
import emc.entity.hr.HRAbsenteeismType;
import emc.entity.hr.HRActionResults;
import emc.entity.hr.HRAlternativeIdType;
import emc.entity.hr.HRBranch;
import emc.entity.hr.HRCitizenStatus;
import emc.entity.hr.HRCourses;
import emc.entity.hr.HRDiciplaneryActions;
import emc.entity.hr.HRDisabilityTypes;
import emc.entity.hr.HRDisciplinaryLevel;
import emc.entity.hr.HREducationLevel;
import emc.entity.hr.HREducationPriority;
import emc.entity.hr.HREmployeeDependants;
import emc.entity.hr.HREmployeeEducation;
import emc.entity.hr.HREmployeeTraining;
import emc.entity.hr.HREmploymentType;
import emc.entity.hr.HREquityCode;
import emc.entity.hr.HRExperienceLevel;
import emc.entity.hr.HRGrade;
import emc.entity.hr.HRGrievences;
import emc.entity.hr.HRInstitution;
import emc.entity.hr.HRInternalEmploymentHistory;
import emc.entity.hr.HRJobCatagory;
import emc.entity.hr.HRJobLevel;
import emc.entity.hr.HRJobStatus;
import emc.entity.hr.HRLearningProgram;
import emc.entity.hr.HRLeaveLog;
import emc.entity.hr.HRLeaveType;
import emc.entity.hr.HRLevelOfExperience;
import emc.entity.hr.HRMedicalAid;
import emc.entity.hr.HRMedicalLog;
import emc.entity.hr.HRNationality;
import emc.entity.hr.HROFOCodes;
import emc.entity.hr.HRPivotalInst;
import emc.entity.hr.HRPivotalProgram;
import emc.entity.hr.HRPivotalStudyField;
import emc.entity.hr.HRPreviousEmploymentHistory;
import emc.entity.hr.HRQualType;
import emc.entity.hr.HRQualification;
import emc.entity.hr.HRRace;
import emc.entity.hr.HRRemunerantionType;
import emc.entity.hr.HRScarceCritSkills;
import emc.entity.hr.HRScarcePriority;
import emc.entity.hr.HRSection;
import emc.entity.hr.HRSocioEcoStatus;
import emc.entity.hr.HRTerminationLog;
import emc.entity.hr.HRTerminationType;
import emc.entity.hr.HRTrainingLevel;
import emc.entity.hr.HRTrainingReason;
import emc.entity.hr.HRTrainingType;
import emc.entity.hr.HRUnions;
import emc.entity.hr.datasource.HRAbsenteeismLogDS;
import emc.entity.hr.datasource.HRTerminationLogDS;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCUserData;
import emc.methods.hr.ClientHRMethods;
import emc.methods.hr.ServerHRMethods;
import emc.reports.hr.disciplinaryaction.HRDisciplinaryActionReportLocal;
import emc.reports.hr.employeesummary.HREmployeeSummaryLocal;
import java.util.ArrayList;
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
public class HRMethodMapperBean implements HRMethodMapperBeanLocal {

    @EJB
    private HRNationalityLocal nationalityBean;
    @EJB
    private HREmploymentTypeLocal employmentTypeBean;
    @EJB
    private HRRaceLocal raceBean;
    @EJB
    private HRGradeLocal gradeBean;
    @EJB
    private HRInstitutionLocal institutionBean;
    @EJB
    private HRLeaveTypeLocal leaveTypeBean;
    @EJB
    private HRTerminationTypeLocal terminationTypeBean;
    @EJB
    private HRSectionLocal sectionBean;
    @EJB
    private HRBranchLocal branchBean;
    @EJB
    private HRTerminationLogLocal terminationLogBean;
    @EJB
    private HRTerminationLogDSLocal terminationLogDSBean;
    @EJB
    private HRLeaveLogLocal leaveLogBean;
    @EJB
    private HREmployeeDependantsLocal employeeDependantsBean;
    @EJB
    private HREmployeeEducationLocal employeeEducationBean;
    @EJB
    private HREmployeeTrainingLocal employeeTrainingBean;
    @EJB
    private HRDisiplaneryActionLocal disiplaneryActionBean;
    @EJB
    private HRGrievencesLocal grievencesBean;
    @EJB
    private HRPreviousEmploymentHistoryLocal previousEmploymentHistoryBean;
    @EJB
    private HRInternalEmploymentHistoryLocal internalEmploymentHistoryBean;
    @EJB
    private HRActionResultsLocal actionResultsBean;
    @EJB
    private HRMedicalAidLocal medicalAidBean;
    @EJB
    private HRUnionsLocal unionsBean;
    @EJB
    private HRCourseLocal courseBean;
    @EJB
    private HRQualificationLocal qualificationBean;
    @EJB
    private HRDisiplinaryLevelLocal disiplinaryLevelBean;
    @EJB
    private HRMedicalLogLocal medicalLogBean;
    @EJB
    private HRDisabilityTypesLocal disabilityTypesBean;
    @EJB
    private HREducationPriorityLocal educationPriorityBean;
    @EJB
    private HRJobLevelLocal jobLevelBean;
    @EJB
    private HRJobCatagoryLocal jobCatagoryBean;
    @EJB
    private HROfOCodeLocal ofoCodeBean;
    @EJB
    private HRAbsenteeismTypeLocal absenteeismTypeBean;
    @EJB
    private HRAbsenteeismLogLocal absenteeismLogBean;
    @EJB
    private HRAbsenteeismLogDSLocal absenteeismLogDSBean;
    @EJB
    private HRDisciplinaryActionReportLocal disciplinaryActionReportBean;
    @EJB
    private HRCitizenStatusLocal citizenStatusBean;
    @EJB
    private HREducationLevelLocal eduLevelBean;
    @EJB
    private HREquityCodeLocal equityCodeBean;
    @EJB
    private HRExperienceLevelLocal experienceLevelBean;
    @EJB
    private HRJobStatusLocal jobStatusBean;
    @EJB
    private HRSocioEcoStatusLocal socioEcoStausBean;
    @EJB
    private HRAlternativeIdTypeLocal alternativeIdTypeBean;
    @EJB
    private HRRemunerantionTypeLocal remunerationBean;
    @EJB
    private HRLevelOfExperienceLocal levelOfExperienceBean;
    @EJB
    private HRAbsScarcityLocal absScarcityBean;
    @EJB
    private HRLearningProgramLocal learningProgramBean;
    @EJB
    private HRPivotalInstLocal pivotalInstBean;
    @EJB
    private HRPivotalProgramLocal pivotalProgramBean;
    @EJB
    private HRPivotalStudyFieldLocal pivotalStudyFieldBean;
    @EJB
    private HRQualTypeLocal qualTypeBean;
    @EJB
    private HRScarceCritSkillsLocal scarceCritSkillsBean;
    @EJB
    private HRTrainingLevelLocal trainingLevelBean;
    @EJB
    private HRTrainingReasonLocal trainingReasonBean;
    @EJB
    private HRTrainingTypeLocal trainingTypeBean;
    @EJB
    private HRScarcePriorityLocal scarcePriorityBean;
    @EJB
    private HREmployeeSummaryLocal employmentSummaryReportBean;

    public HRMethodMapperBean() {
    }

    public List mapMethodHR(EMCCommandClass cmd, List<Object> dataList, EMCUserData userData) throws EMCEntityBeanException {
        List<Object> theDataList = new ArrayList();
        EMCCommandClass retCmd = new EMCCommandClass();
        retCmd.setCommandId(EMCCommands.CLIENT_GENERAL_COMMAND.getId());
        retCmd.setModuleNumber(enumEMCModules.HR.getId());
        retCmd.setMethodId(ClientHRMethods.GENERAL_METHOD.toString());

        switch (ServerHRMethods.fromString(cmd.getMethodId())) {
            //HRNationality
            case INSERT_HRNATIONALITY:
                theDataList.add(nationalityBean.insert((HRNationality) dataList.get(1), userData));
                break;
            case UPDATE_HRNATIONALITY:
                theDataList.add(nationalityBean.update((HRNationality) dataList.get(1), userData));
                break;
            case DELETE_HRNATIONALITY:
                theDataList.add(nationalityBean.delete((HRNationality) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRNATIONALITY:
                theDataList.add(nationalityBean.getNumRows(HRNationality.class, userData));
                break;
            case GETDATA_HRNATIONALITY:
                theDataList = (List<Object>) nationalityBean.getDataInRange(HRNationality.class, userData,
                                                                            Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRNATIONALITY:
                theDataList.add(nationalityBean.validateField(dataList.get(1).toString(), (HRNationality) dataList.get(2), userData));
                break;
            //HREmploymentType
            case INSERT_HREMPLOYMENTTYPE:
                theDataList.add(employmentTypeBean.insert((HREmploymentType) dataList.get(1), userData));
                break;
            case UPDATE_HREMPLOYMENTTYPE:
                theDataList.add(employmentTypeBean.update((HREmploymentType) dataList.get(1), userData));
                break;
            case DELETE_HREMPLOYMENTTYPE:
                theDataList.add(employmentTypeBean.delete((HREmploymentType) dataList.get(1), userData));
                break;
            case GETNUMROWS_HREMPLOYMENTTYPE:
                theDataList.add(employmentTypeBean.getNumRows(HREmploymentType.class, userData));
                break;
            case GETDATA_HREMPLOYMENTTYPE:
                theDataList = (List<Object>) employmentTypeBean.getDataInRange(HREmploymentType.class, userData,
                                                                               Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HREMPLOYMENTTYPE:
                theDataList.add(employmentTypeBean.validateField(dataList.get(1).toString(), (HREmploymentType) dataList.get(2), userData));
                break;
            //HRRace
            case INSERT_HRRACE:
                theDataList.add(raceBean.insert((HRRace) dataList.get(1), userData));
                break;
            case UPDATE_HRRACE:
                theDataList.add(raceBean.update((HRRace) dataList.get(1), userData));
                break;
            case DELETE_HRRACE:
                theDataList.add(raceBean.delete((HRRace) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRRACE:
                theDataList.add(raceBean.getNumRows(HRRace.class, userData));
                break;
            case GETDATA_HRRACE:
                theDataList = (List<Object>) raceBean.getDataInRange(HRRace.class, userData,
                                                                     Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRRACE:
                theDataList.add(raceBean.validateField(dataList.get(1).toString(), (HRRace) dataList.get(2), userData));
                break;
            //HRGrade
            case INSERT_HRGRADE:
                theDataList.add(gradeBean.insert((HRGrade) dataList.get(1), userData));
                break;
            case UPDATE_HRGRADE:
                theDataList.add(gradeBean.update((HRGrade) dataList.get(1), userData));
                break;
            case DELETE_HRGRADE:
                theDataList.add(gradeBean.delete((HRGrade) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRGRADE:
                theDataList.add(gradeBean.getNumRows(HRGrade.class, userData));
                break;
            case GETDATA_HRGRADE:
                theDataList = (List<Object>) gradeBean.getDataInRange(HRGrade.class, userData,
                                                                      Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRGRADE:
                theDataList.add(gradeBean.validateField(dataList.get(1).toString(), (HRGrade) dataList.get(2), userData));
                break;
            //HRInstitution
            case INSERT_HRINSTITUTION:
                theDataList.add(institutionBean.insert((HRInstitution) dataList.get(1), userData));
                break;
            case UPDATE_HRINSTITUTION:
                theDataList.add(institutionBean.update((HRInstitution) dataList.get(1), userData));
                break;
            case DELETE_HRINSTITUTION:
                theDataList.add(institutionBean.delete((HRInstitution) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRINSTITUTION:
                theDataList.add(institutionBean.getNumRows(HRInstitution.class, userData));
                break;
            case GETDATA_HRINSTITUTION:
                theDataList = (List<Object>) institutionBean.getDataInRange(HRInstitution.class, userData,
                                                                            Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRINSTITUTION:
                theDataList.add(institutionBean.validateField(dataList.get(1).toString(), (HRInstitution) dataList.get(2), userData));
                break;
            //HRLeaveType
            case INSERT_HRLEAVETYPE:
                theDataList.add(leaveTypeBean.insert((HRLeaveType) dataList.get(1), userData));
                break;
            case UPDATE_HRLEAVETYPE:
                theDataList.add(leaveTypeBean.update((HRLeaveType) dataList.get(1), userData));
                break;
            case DELETE_HRLEAVETYPE:
                theDataList.add(leaveTypeBean.delete((HRLeaveType) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRLEAVETYPE:
                theDataList.add(leaveTypeBean.getNumRows(HRLeaveType.class, userData));
                break;
            case GETDATA_HRLEAVETYPE:
                theDataList = (List<Object>) leaveTypeBean.getDataInRange(HRLeaveType.class, userData,
                                                                          Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRLEAVETYPE:
                theDataList.add(leaveTypeBean.validateField(dataList.get(1).toString(), (HRLeaveType) dataList.get(2), userData));
                break;
            //HRTerminationType
            case INSERT_HRTERMINATIONTYPE:
                theDataList.add(terminationTypeBean.insert((HRTerminationType) dataList.get(1), userData));
                break;
            case UPDATE_HRTERMINATIONTYPE:
                theDataList.add(terminationTypeBean.update((HRTerminationType) dataList.get(1), userData));
                break;
            case DELETE_HRTERMINATIONTYPE:
                theDataList.add(terminationTypeBean.delete((HRTerminationType) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRTERMINATIONTYPE:
                theDataList.add(terminationTypeBean.getNumRows(HRTerminationType.class, userData));
                break;
            case GETDATA_HRTERMINATIONTYPE:
                theDataList = (List<Object>) terminationTypeBean.getDataInRange(HRTerminationType.class, userData,
                                                                                Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRTERMINATIONTYPE:
                theDataList.add(terminationTypeBean.validateField(dataList.get(1).toString(), (HRTerminationType) dataList.get(2), userData));
                break;
            //HRSection
            case INSERT_HRSECTION:
                theDataList.add(sectionBean.insert((HRSection) dataList.get(1), userData));
                break;
            case UPDATE_HRSECTION:
                theDataList.add(sectionBean.update((HRSection) dataList.get(1), userData));
                break;
            case DELETE_HRSECTION:
                theDataList.add(sectionBean.delete((HRSection) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRSECTION:
                theDataList.add(sectionBean.getNumRows(HRSection.class, userData));
                break;
            case GETDATA_HRSECTION:
                theDataList = (List<Object>) sectionBean.getDataInRange(HRSection.class, userData,
                                                                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRSECTION:
                theDataList.add(sectionBean.validateField(dataList.get(1).toString(), (HRSection) dataList.get(2), userData));
                break;
            //HRBranch
            case INSERT_HRBRANCH:
                theDataList.add(branchBean.insert((HRBranch) dataList.get(1), userData));
                break;
            case UPDATE_HRBRANCH:
                theDataList.add(branchBean.update((HRBranch) dataList.get(1), userData));
                break;
            case DELETE_HRBRANCH:
                theDataList.add(branchBean.delete((HRBranch) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRBRANCH:
                theDataList.add(branchBean.getNumRows(HRBranch.class, userData));
                break;
            case GETDATA_HRBRANCH:
                theDataList = (List<Object>) branchBean.getDataInRange(HRBranch.class, userData,
                                                                       Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRBRANCH:
                theDataList.add(branchBean.validateField(dataList.get(1).toString(), (HRBranch) dataList.get(2), userData));
                break;
            //HRTerminationLog
            case INSERT_HRTERMINATIONLOG:
                theDataList.add(terminationLogBean.insert((HRTerminationLog) dataList.get(1), userData));
                break;
            case UPDATE_HRTERMINATIONLOG:
                theDataList.add(terminationLogBean.update((HRTerminationLog) dataList.get(1), userData));
                break;
            case DELETE_HRTERMINATIONLOG:
                theDataList.add(terminationLogBean.delete((HRTerminationLog) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRTERMINATIONLOG:
                theDataList.add(terminationLogBean.getNumRows(HRTerminationLog.class, userData));
                break;
            case GETDATA_HRTERMINATIONLOG:
                theDataList = (List<Object>) terminationLogBean.getDataInRange(HRTerminationLog.class, userData,
                                                                               Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRTERMINATIONLOG:
                theDataList.add(terminationLogBean.validateField(dataList.get(1).toString(), (HRTerminationLog) dataList.get(2), userData));
                break;
            //HRTerminationLogDS
            case INSERT_HRTERMINATIONLOGDS:
                theDataList.add(terminationLogDSBean.insert((HRTerminationLogDS) dataList.get(1), userData));
                break;
            case UPDATE_HRTERMINATIONLOGDS:
                theDataList.add(terminationLogDSBean.update((HRTerminationLogDS) dataList.get(1), userData));
                break;
            case DELETE_HRTERMINATIONLOGDS:
                theDataList.add(terminationLogDSBean.delete((HRTerminationLogDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRTERMINATIONLOGDS:
                theDataList.add(terminationLogDSBean.getNumRows(HRTerminationLogDS.class, userData));
                break;
            case GETDATA_HRTERMINATIONLOGDS:
                theDataList = (List<Object>) terminationLogDSBean.getDataInRange(HRTerminationLogDS.class, userData,
                                                                                 Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRTERMINATIONLOGDS:
                theDataList.add(terminationLogDSBean.validateField(dataList.get(1).toString(), (HRTerminationLogDS) dataList.get(2), userData));
                break;
            //HRLeaveLog
            case INSERT_HRLEAVELOG:
                theDataList.add(leaveLogBean.insert((HRLeaveLog) dataList.get(1), userData));
                break;
            case UPDATE_HRLEAVELOG:
                theDataList.add(leaveLogBean.update((HRLeaveLog) dataList.get(1), userData));
                break;
            case DELETE_HRLEAVELOG:
                theDataList.add(leaveLogBean.delete((HRLeaveLog) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRLEAVELOG:
                theDataList.add(leaveLogBean.getNumRows(HRLeaveLog.class, userData));
                break;
            case GETDATA_HRLEAVELOG:
                theDataList = (List<Object>) leaveLogBean.getDataInRange(HRLeaveLog.class, userData,
                                                                         Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRLEAVELOG:
                theDataList.add(leaveLogBean.validateField(dataList.get(1).toString(), (HRLeaveLog) dataList.get(2), userData));
                break;
            //HREmployeeDependants
            case INSERT_HREMPLOYEEDEPENDANTS:
                theDataList.add(employeeDependantsBean.insert((HREmployeeDependants) dataList.get(1), userData));
                break;
            case UPDATE_HREMPLOYEEDEPENDANTS:
                theDataList.add(employeeDependantsBean.update((HREmployeeDependants) dataList.get(1), userData));
                break;
            case DELETE_HREMPLOYEEDEPENDANTS:
                theDataList.add(employeeDependantsBean.delete((HREmployeeDependants) dataList.get(1), userData));
                break;
            case GETNUMROWS_HREMPLOYEEDEPENDANTS:
                theDataList.add(employeeDependantsBean.getNumRows(HREmployeeDependants.class, userData));
                break;
            case GETDATA_HREMPLOYEEDEPENDANTS:
                theDataList = (List<Object>) employeeDependantsBean.getDataInRange(HREmployeeDependants.class, userData,
                                                                                   Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HREMPLOYEEDEPENDANTS:
                theDataList.add(employeeDependantsBean.validateField(dataList.get(1).toString(), (HREmployeeDependants) dataList.get(2), userData));
                break;
            //HREmployeeEducation
            case INSERT_HREMPLOYEEEDUCATION:
                theDataList.add(employeeEducationBean.insert((HREmployeeEducation) dataList.get(1), userData));
                break;
            case UPDATE_HREMPLOYEEEDUCATION:
                theDataList.add(employeeEducationBean.update((HREmployeeEducation) dataList.get(1), userData));
                break;
            case DELETE_HREMPLOYEEEDUCATION:
                theDataList.add(employeeEducationBean.delete((HREmployeeEducation) dataList.get(1), userData));
                break;
            case GETNUMROWS_HREMPLOYEEEDUCATION:
                theDataList.add(employeeEducationBean.getNumRows(HREmployeeEducation.class, userData));
                break;
            case GETDATA_HREMPLOYEEEDUCATION:
                theDataList = (List<Object>) employeeEducationBean.getDataInRange(HREmployeeEducation.class, userData,
                                                                                  Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HREMPLOYEEEDUCATION:
                theDataList.add(employeeEducationBean.validateField(dataList.get(1).toString(), (HREmployeeEducation) dataList.get(2), userData));
                break;
            //HREmployeeTraining
            case INSERT_HREMPLOYEETRAINING:
                theDataList.add(employeeTrainingBean.insert((HREmployeeTraining) dataList.get(1), userData));
                break;
            case UPDATE_HREMPLOYEETRAINING:
                theDataList.add(employeeTrainingBean.update((HREmployeeTraining) dataList.get(1), userData));
                break;
            case DELETE_HREMPLOYEETRAINING:
                theDataList.add(employeeTrainingBean.delete((HREmployeeTraining) dataList.get(1), userData));
                break;
            case GETNUMROWS_HREMPLOYEETRAINING:
                theDataList.add(employeeTrainingBean.getNumRows(HREmployeeTraining.class, userData));
                break;
            case GETDATA_HREMPLOYEETRAINING:
                theDataList = (List<Object>) employeeTrainingBean.getDataInRange(HREmployeeTraining.class, userData,
                                                                                 Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HREMPLOYEETRAINING:
                theDataList.add(employeeTrainingBean.validateField(dataList.get(1).toString(), (HREmployeeTraining) dataList.get(2), userData));
                break;
            //HRDiciplaneryActions
            case INSERT_HRDICIPLANERYACTIONS:
                theDataList.add(disiplaneryActionBean.insert((HRDiciplaneryActions) dataList.get(1), userData));
                break;
            case UPDATE_HRDICIPLANERYACTIONS:
                theDataList.add(disiplaneryActionBean.update((HRDiciplaneryActions) dataList.get(1), userData));
                break;
            case DELETE_HRDICIPLANERYACTIONS:
                theDataList.add(disiplaneryActionBean.delete((HRDiciplaneryActions) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRDICIPLANERYACTIONS:
                theDataList.add(disiplaneryActionBean.getNumRows(HRDiciplaneryActions.class, userData));
                break;
            case GETDATA_HRDICIPLANERYACTIONS:
                theDataList = (List<Object>) disiplaneryActionBean.getDataInRange(HRDiciplaneryActions.class, userData,
                                                                                  Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRDICIPLANERYACTIONS:
                theDataList.add(disiplaneryActionBean.validateField(dataList.get(1).toString(), (HRDiciplaneryActions) dataList.get(2), userData));
                break;
            //HRGrievences
            case INSERT_HRGRIEVENCES:
                theDataList.add(grievencesBean.insert((HRGrievences) dataList.get(1), userData));
                break;
            case UPDATE_HRGRIEVENCES:
                theDataList.add(grievencesBean.update((HRGrievences) dataList.get(1), userData));
                break;
            case DELETE_HRGRIEVENCES:
                theDataList.add(grievencesBean.delete((HRGrievences) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRGRIEVENCES:
                theDataList.add(grievencesBean.getNumRows(HRGrievences.class, userData));
                break;
            case GETDATA_HRGRIEVENCES:
                theDataList = (List<Object>) grievencesBean.getDataInRange(HRGrievences.class, userData,
                                                                           Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRGRIEVENCES:
                theDataList.add(grievencesBean.validateField(dataList.get(1).toString(), (HRGrievences) dataList.get(2), userData));
                break;
            //HRPreviousEmploymentHistory
            case INSERT_HRPREVIOUSEMPLOYMENTHISTORY:
                theDataList.add(previousEmploymentHistoryBean.insert((HRPreviousEmploymentHistory) dataList.get(1), userData));
                break;
            case UPDATE_HRPREVIOUSEMPLOYMENTHISTORY:
                theDataList.add(previousEmploymentHistoryBean.update((HRPreviousEmploymentHistory) dataList.get(1), userData));
                break;
            case DELETE_HRPREVIOUSEMPLOYMENTHISTORY:
                theDataList.add(previousEmploymentHistoryBean.delete((HRPreviousEmploymentHistory) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRPREVIOUSEMPLOYMENTHISTORY:
                theDataList.add(previousEmploymentHistoryBean.getNumRows(HRPreviousEmploymentHistory.class, userData));
                break;
            case GETDATA_HRPREVIOUSEMPLOYMENTHISTORY:
                theDataList = (List<Object>) previousEmploymentHistoryBean.getDataInRange(HRPreviousEmploymentHistory.class, userData,
                                                                                          Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRPREVIOUSEMPLOYMENTHISTORY:
                theDataList.add(previousEmploymentHistoryBean.validateField(dataList.get(1).toString(), (HRPreviousEmploymentHistory) dataList.get(2), userData));
                break;
            //HRInternalEmploymentHistory
            case INSERT_HRINTERNALEMPLOYMENTHISTORY:
                theDataList.add(internalEmploymentHistoryBean.insert((HRInternalEmploymentHistory) dataList.get(1), userData));
                break;
            case UPDATE_HRINTERNALEMPLOYMENTHISTORY:
                theDataList.add(internalEmploymentHistoryBean.update((HRInternalEmploymentHistory) dataList.get(1), userData));
                break;
            case DELETE_HRINTERNALEMPLOYMENTHISTORY:
                theDataList.add(internalEmploymentHistoryBean.delete((HRInternalEmploymentHistory) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRINTERNALEMPLOYMENTHISTORY:
                theDataList.add(internalEmploymentHistoryBean.getNumRows(HRInternalEmploymentHistory.class, userData));
                break;
            case GETDATA_HRINTERNALEMPLOYMENTHISTORY:
                theDataList = (List<Object>) internalEmploymentHistoryBean.getDataInRange(HRInternalEmploymentHistory.class, userData,
                                                                                          Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRINTERNALEMPLOYMENTHISTORY:
                theDataList.add(internalEmploymentHistoryBean.validateField(dataList.get(1).toString(), (HRInternalEmploymentHistory) dataList.get(2), userData));
                break;
            //HRActionResults
            case INSERT_HRACTIONRESULTS:
                theDataList.add(actionResultsBean.insert((HRActionResults) dataList.get(1), userData));
                break;
            case UPDATE_HRACTIONRESULTS:
                theDataList.add(actionResultsBean.update((HRActionResults) dataList.get(1), userData));
                break;
            case DELETE_HRACTIONRESULTS:
                theDataList.add(actionResultsBean.delete((HRActionResults) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRACTIONRESULTS:
                theDataList.add(actionResultsBean.getNumRows(HRActionResults.class, userData));
                break;
            case GETDATA_HRACTIONRESULTS:
                theDataList = (List<Object>) actionResultsBean.getDataInRange(HRActionResults.class, userData,
                                                                              Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRACTIONRESULTS:
                theDataList.add(actionResultsBean.validateField(dataList.get(1).toString(), (HRActionResults) dataList.get(2), userData));
                break;
            //HRMedicalAid
            case INSERT_HRMEDICALAID:
                theDataList.add(medicalAidBean.insert((HRMedicalAid) dataList.get(1), userData));
                break;
            case UPDATE_HRMEDICALAID:
                theDataList.add(medicalAidBean.update((HRMedicalAid) dataList.get(1), userData));
                break;
            case DELETE_HRMEDICALAID:
                theDataList.add(medicalAidBean.delete((HRMedicalAid) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRMEDICALAID:
                theDataList.add(medicalAidBean.getNumRows(HRMedicalAid.class, userData));
                break;
            case GETDATA_HRMEDICALAID:
                theDataList = (List<Object>) medicalAidBean.getDataInRange(HRMedicalAid.class, userData,
                                                                           Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRMEDICALAID:
                theDataList.add(medicalAidBean.validateField(dataList.get(1).toString(), (HRMedicalAid) dataList.get(2), userData));
                break;
            //HRUnions
            case INSERT_HRUNIONS:
                theDataList.add(unionsBean.insert((HRUnions) dataList.get(1), userData));
                break;
            case UPDATE_HRUNIONS:
                theDataList.add(unionsBean.update((HRUnions) dataList.get(1), userData));
                break;
            case DELETE_HRUNIONS:
                theDataList.add(unionsBean.delete((HRUnions) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRUNIONS:
                theDataList.add(unionsBean.getNumRows(HRUnions.class, userData));
                break;
            case GETDATA_HRUNIONS:
                theDataList = (List<Object>) unionsBean.getDataInRange(HRUnions.class, userData,
                                                                       Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRUNIONS:
                theDataList.add(unionsBean.validateField(dataList.get(1).toString(), (HRUnions) dataList.get(2), userData));
                break;
            //HRCourses
            case INSERT_HRCOURSES:
                theDataList.add(courseBean.insert((HRCourses) dataList.get(1), userData));
                break;
            case UPDATE_HRCOURSES:
                theDataList.add(courseBean.update((HRCourses) dataList.get(1), userData));
                break;
            case DELETE_HRCOURSES:
                theDataList.add(courseBean.delete((HRCourses) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRCOURSES:
                theDataList.add(courseBean.getNumRows(HRCourses.class, userData));
                break;
            case GETDATA_HRCOURSES:
                theDataList = (List<Object>) courseBean.getDataInRange(HRCourses.class, userData,
                                                                       Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRCOURSES:
                theDataList.add(courseBean.validateField(dataList.get(1).toString(), (HRCourses) dataList.get(2), userData));
                break;
            //HRQualification
            case INSERT_HRQUALIFICATION:
                theDataList.add(qualificationBean.insert((HRQualification) dataList.get(1), userData));
                break;
            case UPDATE_HRQUALIFICATION:
                theDataList.add(qualificationBean.update((HRQualification) dataList.get(1), userData));
                break;
            case DELETE_HRQUALIFICATION:
                theDataList.add(qualificationBean.delete((HRQualification) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRQUALIFICATION:
                theDataList.add(qualificationBean.getNumRows(HRQualification.class, userData));
                break;
            case GETDATA_HRQUALIFICATION:
                theDataList = (List<Object>) qualificationBean.getDataInRange(HRQualification.class, userData,
                                                                              Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRQUALIFICATION:
                theDataList.add(qualificationBean.validateField(dataList.get(1).toString(), (HRQualification) dataList.get(2), userData));
                break;
            //HRDisciplinaryLevel
            case INSERT_HRDISCIPLINARYLEVEL:
                theDataList.add(disiplinaryLevelBean.insert((HRDisciplinaryLevel) dataList.get(1), userData));
                break;
            case UPDATE_HRDISCIPLINARYLEVEL:
                theDataList.add(disiplinaryLevelBean.update((HRDisciplinaryLevel) dataList.get(1), userData));
                break;
            case DELETE_HRDISCIPLINARYLEVEL:
                theDataList.add(disiplinaryLevelBean.delete((HRDisciplinaryLevel) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRDISCIPLINARYLEVEL:
                theDataList.add(disiplinaryLevelBean.getNumRows(HRDisciplinaryLevel.class, userData));
                break;
            case GETDATA_HRDISCIPLINARYLEVEL:
                theDataList = (List<Object>) disiplinaryLevelBean.getDataInRange(HRDisciplinaryLevel.class, userData,
                                                                                 Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRDISCIPLINARYLEVEL:
                theDataList.add(disiplinaryLevelBean.validateField(dataList.get(1).toString(), (HRDisciplinaryLevel) dataList.get(2), userData));
                break;
            //HRMedicalLog
            case INSERT_HRMEDICALLOG:
                theDataList.add(medicalLogBean.insert((HRMedicalLog) dataList.get(1), userData));
                break;
            case UPDATE_HRMEDICALLOG:
                theDataList.add(medicalLogBean.update((HRMedicalLog) dataList.get(1), userData));
                break;
            case DELETE_HRMEDICALLOG:
                theDataList.add(medicalLogBean.delete((HRMedicalLog) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRMEDICALLOG:
                theDataList.add(medicalLogBean.getNumRows(HRMedicalLog.class, userData));
                break;
            case GETDATA_HRMEDICALLOG:
                theDataList = (List<Object>) medicalLogBean.getDataInRange(HRMedicalLog.class, userData,
                                                                           Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRMEDICALLOG:
                theDataList.add(medicalLogBean.validateField(dataList.get(1).toString(), (HRMedicalLog) dataList.get(2), userData));
                break;
            //HRDisabilityTypes
            case INSERT_HRDISABILITYTYPES:
                theDataList.add(disabilityTypesBean.insert((HRDisabilityTypes) dataList.get(1), userData));
                break;
            case UPDATE_HRDISABILITYTYPES:
                theDataList.add(disabilityTypesBean.update((HRDisabilityTypes) dataList.get(1), userData));
                break;
            case DELETE_HRDISABILITYTYPES:
                theDataList.add(disabilityTypesBean.delete((HRDisabilityTypes) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRDISABILITYTYPES:
                theDataList.add(disabilityTypesBean.getNumRows(HRDisabilityTypes.class, userData));
                break;
            case GETDATA_HRDISABILITYTYPES:
                theDataList = (List<Object>) disabilityTypesBean.getDataInRange(HRDisabilityTypes.class, userData,
                                                                                Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRDISABILITYTYPES:
                theDataList.add(disabilityTypesBean.validateField(dataList.get(1).toString(), (HRDisabilityTypes) dataList.get(2), userData));
                break;
            //HREducationPriority
            case INSERT_HREDUCATIONPRIORITY:
                theDataList.add(educationPriorityBean.insert((HREducationPriority) dataList.get(1), userData));
                break;
            case UPDATE_HREDUCATIONPRIORITY:
                theDataList.add(educationPriorityBean.update((HREducationPriority) dataList.get(1), userData));
                break;
            case DELETE_HREDUCATIONPRIORITY:
                theDataList.add(educationPriorityBean.delete((HREducationPriority) dataList.get(1), userData));
                break;
            case GETNUMROWS_HREDUCATIONPRIORITY:
                theDataList.add(educationPriorityBean.getNumRows(HREducationPriority.class, userData));
                break;
            case GETDATA_HREDUCATIONPRIORITY:
                theDataList = (List<Object>) educationPriorityBean.getDataInRange(HREducationPriority.class, userData,
                                                                                  Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HREDUCATIONPRIORITY:
                theDataList.add(educationPriorityBean.validateField(dataList.get(1).toString(), (HREducationPriority) dataList.get(2), userData));
                break;
            //HRJobLevel
            case INSERT_HRJOBLEVEL:
                theDataList.add(jobLevelBean.insert((HRJobLevel) dataList.get(1), userData));
                break;
            case UPDATE_HRJOBLEVEL:
                theDataList.add(jobLevelBean.update((HRJobLevel) dataList.get(1), userData));
                break;
            case DELETE_HRJOBLEVEL:
                theDataList.add(jobLevelBean.delete((HRJobLevel) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRJOBLEVEL:
                theDataList.add(jobLevelBean.getNumRows(HRJobLevel.class, userData));
                break;
            case GETDATA_HRJOBLEVEL:
                theDataList = (List<Object>) jobLevelBean.getDataInRange(HRJobLevel.class, userData,
                                                                         Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRJOBLEVEL:
                theDataList.add(jobLevelBean.validateField(dataList.get(1).toString(), (HRJobLevel) dataList.get(2), userData));
                break;
            //HRJobCatagory
            case INSERT_HRJOBCATAGORY:
                theDataList.add(jobCatagoryBean.insert((HRJobCatagory) dataList.get(1), userData));
                break;
            case UPDATE_HRJOBCATAGORY:
                theDataList.add(jobCatagoryBean.update((HRJobCatagory) dataList.get(1), userData));
                break;
            case DELETE_HRJOBCATAGORY:
                theDataList.add(jobCatagoryBean.delete((HRJobCatagory) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRJOBCATAGORY:
                theDataList.add(jobCatagoryBean.getNumRows(HRJobCatagory.class, userData));
                break;
            case GETDATA_HRJOBCATAGORY:
                theDataList = (List<Object>) jobCatagoryBean.getDataInRange(HRJobCatagory.class, userData,
                                                                            Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRJOBCATAGORY:
                theDataList.add(jobCatagoryBean.validateField(dataList.get(1).toString(), (HRJobCatagory) dataList.get(2), userData));
                break;
            //HROFOCodes
            case INSERT_HROFOCODES:
                theDataList.add(ofoCodeBean.insert((HROFOCodes) dataList.get(1), userData));
                break;
            case UPDATE_HROFOCODES:
                theDataList.add(ofoCodeBean.update((HROFOCodes) dataList.get(1), userData));
                break;
            case DELETE_HROFOCODES:
                theDataList.add(ofoCodeBean.delete((HROFOCodes) dataList.get(1), userData));
                break;
            case GETNUMROWS_HROFOCODES:
                theDataList.add(ofoCodeBean.getNumRows(HROFOCodes.class, userData));
                break;
            case GETDATA_HROFOCODES:
                theDataList = (List<Object>) ofoCodeBean.getDataInRange(HROFOCodes.class, userData,
                                                                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HROFOCODES:
                theDataList.add(ofoCodeBean.validateField(dataList.get(1).toString(), (HROFOCodes) dataList.get(2), userData));
                break;
            //HRAbsenteeismType
            case INSERT_HRABSENTEEISMTYPE:
                theDataList.add(absenteeismTypeBean.insert((HRAbsenteeismType) dataList.get(1), userData));
                break;
            case UPDATE_HRABSENTEEISMTYPE:
                theDataList.add(absenteeismTypeBean.update((HRAbsenteeismType) dataList.get(1), userData));
                break;
            case DELETE_HRABSENTEEISMTYPE:
                theDataList.add(absenteeismTypeBean.delete((HRAbsenteeismType) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRABSENTEEISMTYPE:
                theDataList.add(absenteeismTypeBean.getNumRows(HRAbsenteeismType.class, userData));
                break;
            case GETDATA_HRABSENTEEISMTYPE:
                theDataList = (List<Object>) absenteeismTypeBean.getDataInRange(HRAbsenteeismType.class, userData,
                                                                                Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRABSENTEEISMTYPE:
                theDataList.add(absenteeismTypeBean.validateField(dataList.get(1).toString(), (HRAbsenteeismType) dataList.get(2), userData));
                break;
            //HRAbsenteeismLog
            case INSERT_HRABSENTEEISMLOG:
                theDataList.add(absenteeismLogBean.insert((HRAbsenteeismLog) dataList.get(1), userData));
                break;
            case UPDATE_HRABSENTEEISMLOG:
                theDataList.add(absenteeismLogBean.update((HRAbsenteeismLog) dataList.get(1), userData));
                break;
            case DELETE_HRABSENTEEISMLOG:
                theDataList.add(absenteeismLogBean.delete((HRAbsenteeismLog) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRABSENTEEISMLOG:
                theDataList.add(absenteeismLogBean.getNumRows(HRAbsenteeismLog.class, userData));
                break;
            case GETDATA_HRABSENTEEISMLOG:
                theDataList = (List<Object>) absenteeismLogBean.getDataInRange(HRAbsenteeismLog.class, userData,
                                                                               Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRABSENTEEISMLOG:
                theDataList.add(absenteeismLogBean.validateField(dataList.get(1).toString(), (HRAbsenteeismLog) dataList.get(2), userData));
                break;
            //HRAbsenteeismLogDS
            case INSERT_HRABSENTEEISMLOGDS:
                theDataList.add(absenteeismLogDSBean.insert((HRAbsenteeismLogDS) dataList.get(1), userData));
                break;
            case UPDATE_HRABSENTEEISMLOGDS:
                theDataList.add(absenteeismLogDSBean.update((HRAbsenteeismLogDS) dataList.get(1), userData));
                break;
            case DELETE_HRABSENTEEISMLOGDS:
                theDataList.add(absenteeismLogDSBean.delete((HRAbsenteeismLogDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRABSENTEEISMLOGDS:
                theDataList.add(absenteeismLogDSBean.getNumRows(HRAbsenteeismLogDS.class, userData));
                break;
            case GETDATA_HRABSENTEEISMLOGDS:
                theDataList = (List<Object>) absenteeismLogDSBean.getDataInRange(HRAbsenteeismLogDS.class, userData,
                                                                                 Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRABSENTEEISMLOGDS:
                theDataList.add(absenteeismLogDSBean.validateField(dataList.get(1).toString(), (HRAbsenteeismLogDS) dataList.get(2), userData));
                break;
            //Print Disciplinary Action Report
            case PRINT_DISCIPLINARY_ACTION:
                theDataList = disciplinaryActionReportBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            //HRCitizenStatus
            case INSERT_HRCITIZENSTATUS:
                theDataList.add(citizenStatusBean.insert((HRCitizenStatus) dataList.get(1), userData));
                break;
            case UPDATE_HRCITIZENSTATUS:
                theDataList.add(citizenStatusBean.update((HRCitizenStatus) dataList.get(1), userData));
                break;
            case DELETE_HRCITIZENSTATUS:
                theDataList.add(citizenStatusBean.delete((HRCitizenStatus) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRCITIZENSTATUS:
                theDataList.add(citizenStatusBean.getNumRows(HRCitizenStatus.class, userData));
                break;
            case GETDATA_HRCITIZENSTATUS:
                theDataList = (List<Object>) citizenStatusBean.getDataInRange(HRCitizenStatus.class, userData,
                                                                              Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRCITIZENSTATUS:
                theDataList.add(citizenStatusBean.validateField(dataList.get(1).toString(), (HRCitizenStatus) dataList.get(2), userData));
                break;
//HREducationLevel
            case INSERT_HREDUCATIONLEVEL:
                theDataList.add(eduLevelBean.insert((HREducationLevel) dataList.get(1), userData));
                break;
            case UPDATE_HREDUCATIONLEVEL:
                theDataList.add(eduLevelBean.update((HREducationLevel) dataList.get(1), userData));
                break;
            case DELETE_HREDUCATIONLEVEL:
                theDataList.add(eduLevelBean.delete((HREducationLevel) dataList.get(1), userData));
                break;
            case GETNUMROWS_HREDUCATIONLEVEL:
                theDataList.add(eduLevelBean.getNumRows(HREducationLevel.class, userData));
                break;
            case GETDATA_HREDUCATIONLEVEL:
                theDataList = (List<Object>) eduLevelBean.getDataInRange(HREducationLevel.class, userData,
                                                                         Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HREDUCATIONLEVEL:
                theDataList.add(eduLevelBean.validateField(dataList.get(1).toString(), (HREducationLevel) dataList.get(2), userData));
                break;
//HREquityCode
            case INSERT_HREQUITYCODE:
                theDataList.add(equityCodeBean.insert((HREquityCode) dataList.get(1), userData));
                break;
            case UPDATE_HREQUITYCODE:
                theDataList.add(equityCodeBean.update((HREquityCode) dataList.get(1), userData));
                break;
            case DELETE_HREQUITYCODE:
                theDataList.add(equityCodeBean.delete((HREquityCode) dataList.get(1), userData));
                break;
            case GETNUMROWS_HREQUITYCODE:
                theDataList.add(equityCodeBean.getNumRows(HREquityCode.class, userData));
                break;
            case GETDATA_HREQUITYCODE:
                theDataList = (List<Object>) equityCodeBean.getDataInRange(HREquityCode.class, userData,
                                                                           Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HREQUITYCODE:
                theDataList.add(equityCodeBean.validateField(dataList.get(1).toString(), (HREquityCode) dataList.get(2), userData));
                break;
//HRExperienceLevel
            case INSERT_HREXPERIENCELEVEL:
                theDataList.add(experienceLevelBean.insert((HRExperienceLevel) dataList.get(1), userData));
                break;
            case UPDATE_HREXPERIENCELEVEL:
                theDataList.add(experienceLevelBean.update((HRExperienceLevel) dataList.get(1), userData));
                break;
            case DELETE_HREXPERIENCELEVEL:
                theDataList.add(experienceLevelBean.delete((HRExperienceLevel) dataList.get(1), userData));
                break;
            case GETNUMROWS_HREXPERIENCELEVEL:
                theDataList.add(experienceLevelBean.getNumRows(HRExperienceLevel.class, userData));
                break;
            case GETDATA_HREXPERIENCELEVEL:
                theDataList = (List<Object>) experienceLevelBean.getDataInRange(HRExperienceLevel.class, userData,
                                                                                Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HREXPERIENCELEVEL:
                theDataList.add(experienceLevelBean.validateField(dataList.get(1).toString(), (HRExperienceLevel) dataList.get(2), userData));
                break;
//HRJobStatus
            case INSERT_HRJOBSTATUS:
                theDataList.add(jobStatusBean.insert((HRJobStatus) dataList.get(1), userData));
                break;
            case UPDATE_HRJOBSTATUS:
                theDataList.add(jobStatusBean.update((HRJobStatus) dataList.get(1), userData));
                break;
            case DELETE_HRJOBSTATUS:
                theDataList.add(jobStatusBean.delete((HRJobStatus) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRJOBSTATUS:
                theDataList.add(jobStatusBean.getNumRows(HRJobStatus.class, userData));
                break;
            case GETDATA_HRJOBSTATUS:
                theDataList = (List<Object>) jobStatusBean.getDataInRange(HRJobStatus.class, userData,
                                                                          Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRJOBSTATUS:
                theDataList.add(jobStatusBean.validateField(dataList.get(1).toString(), (HRJobStatus) dataList.get(2), userData));
                break;
//HRSocioEcoStatus
            case INSERT_HRSOCIOECOSTATUS:
                theDataList.add(socioEcoStausBean.insert((HRSocioEcoStatus) dataList.get(1), userData));
                break;
            case UPDATE_HRSOCIOECOSTATUS:
                theDataList.add(socioEcoStausBean.update((HRSocioEcoStatus) dataList.get(1), userData));
                break;
            case DELETE_HRSOCIOECOSTATUS:
                theDataList.add(socioEcoStausBean.delete((HRSocioEcoStatus) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRSOCIOECOSTATUS:
                theDataList.add(socioEcoStausBean.getNumRows(HRSocioEcoStatus.class, userData));
                break;
            case GETDATA_HRSOCIOECOSTATUS:
                theDataList = (List<Object>) socioEcoStausBean.getDataInRange(HRSocioEcoStatus.class, userData,
                                                                              Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRSOCIOECOSTATUS:
                theDataList.add(socioEcoStausBean.validateField(dataList.get(1).toString(), (HRSocioEcoStatus) dataList.get(2), userData));
                break;
//HRAlternativeIdType
            case INSERT_HRALTERNATIVEIDTYPE:
                theDataList.add(alternativeIdTypeBean.insert((HRAlternativeIdType) dataList.get(1), userData));
                break;
            case UPDATE_HRALTERNATIVEIDTYPE:
                theDataList.add(alternativeIdTypeBean.update((HRAlternativeIdType) dataList.get(1), userData));
                break;
            case DELETE_HRALTERNATIVEIDTYPE:
                theDataList.add(alternativeIdTypeBean.delete((HRAlternativeIdType) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRALTERNATIVEIDTYPE:
                theDataList.add(alternativeIdTypeBean.getNumRows(HRAlternativeIdType.class, userData));
                break;
            case GETDATA_HRALTERNATIVEIDTYPE:
                theDataList = (List<Object>) alternativeIdTypeBean.getDataInRange(HRAlternativeIdType.class, userData,
                                                                                  Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRALTERNATIVEIDTYPE:
                theDataList.add(alternativeIdTypeBean.validateField(dataList.get(1).toString(), (HRAlternativeIdType) dataList.get(2), userData));
                break;
            //HRRemunerantionType
            case INSERT_HRREMUNERANTIONTYPE:
                theDataList.add(remunerationBean.insert((HRRemunerantionType) dataList.get(1), userData));
                break;
            case UPDATE_HRREMUNERANTIONTYPE:
                theDataList.add(remunerationBean.update((HRRemunerantionType) dataList.get(1), userData));
                break;
            case DELETE_HRREMUNERANTIONTYPE:
                theDataList.add(remunerationBean.delete((HRRemunerantionType) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRREMUNERANTIONTYPE:
                theDataList.add(remunerationBean.getNumRows(HRRemunerantionType.class, userData));
                break;
            case GETDATA_HRREMUNERANTIONTYPE:
                theDataList = (List<Object>) remunerationBean.getDataInRange(HRRemunerantionType.class, userData,
                                                                             Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRREMUNERANTIONTYPE:
                theDataList.add(remunerationBean.validateField(dataList.get(1).toString(), (HRRemunerantionType) dataList.get(2), userData));
                break;
//HRLevelOfExperience
            case INSERT_HRLEVELOFEXPERIENCE:
                theDataList.add(levelOfExperienceBean.insert((HRLevelOfExperience) dataList.get(1), userData));
                break;
            case UPDATE_HRLEVELOFEXPERIENCE:
                theDataList.add(levelOfExperienceBean.update((HRLevelOfExperience) dataList.get(1), userData));
                break;
            case DELETE_HRLEVELOFEXPERIENCE:
                theDataList.add(levelOfExperienceBean.delete((HRLevelOfExperience) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRLEVELOFEXPERIENCE:
                theDataList.add(levelOfExperienceBean.getNumRows(HRLevelOfExperience.class, userData));
                break;
            case GETDATA_HRLEVELOFEXPERIENCE:
                theDataList = (List<Object>) levelOfExperienceBean.getDataInRange(HRLevelOfExperience.class, userData,
                                                                                  Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRLEVELOFEXPERIENCE:
                theDataList.add(levelOfExperienceBean.validateField(dataList.get(1).toString(), (HRLevelOfExperience) dataList.get(2), userData));
                break;
//HRAbsScarcity
            case INSERT_HRABSSCARCITY:
                theDataList.add(absScarcityBean.insert((HRAbsScarcity) dataList.get(1), userData));
                break;
            case UPDATE_HRABSSCARCITY:
                theDataList.add(absScarcityBean.update((HRAbsScarcity) dataList.get(1), userData));
                break;
            case DELETE_HRABSSCARCITY:
                theDataList.add(absScarcityBean.delete((HRAbsScarcity) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRABSSCARCITY:
                theDataList.add(absScarcityBean.getNumRows(HRAbsScarcity.class, userData));
                break;
            case GETDATA_HRABSSCARCITY:
                theDataList = (List<Object>) absScarcityBean.getDataInRange(HRAbsScarcity.class, userData,
                                                                            Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRABSSCARCITY:
                theDataList.add(absScarcityBean.validateField(dataList.get(1).toString(), (HRAbsScarcity) dataList.get(2), userData));
                break;
//HRLearningProgram
            case INSERT_HRLEARNINGPROGRAM:
                theDataList.add(learningProgramBean.insert((HRLearningProgram) dataList.get(1), userData));
                break;
            case UPDATE_HRLEARNINGPROGRAM:
                theDataList.add(learningProgramBean.update((HRLearningProgram) dataList.get(1), userData));
                break;
            case DELETE_HRLEARNINGPROGRAM:
                theDataList.add(learningProgramBean.delete((HRLearningProgram) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRLEARNINGPROGRAM:
                theDataList.add(learningProgramBean.getNumRows(HRLearningProgram.class, userData));
                break;
            case GETDATA_HRLEARNINGPROGRAM:
                theDataList = (List<Object>) learningProgramBean.getDataInRange(HRLearningProgram.class, userData,
                                                                                Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRLEARNINGPROGRAM:
                theDataList.add(learningProgramBean.validateField(dataList.get(1).toString(), (HRLearningProgram) dataList.get(2), userData));
                break;
//HRPivotalInst
            case INSERT_HRPIVOTALINST:
                theDataList.add(pivotalInstBean.insert((HRPivotalInst) dataList.get(1), userData));
                break;
            case UPDATE_HRPIVOTALINST:
                theDataList.add(pivotalInstBean.update((HRPivotalInst) dataList.get(1), userData));
                break;
            case DELETE_HRPIVOTALINST:
                theDataList.add(pivotalInstBean.delete((HRPivotalInst) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRPIVOTALINST:
                theDataList.add(pivotalInstBean.getNumRows(HRPivotalInst.class, userData));
                break;
            case GETDATA_HRPIVOTALINST:
                theDataList = (List<Object>) pivotalInstBean.getDataInRange(HRPivotalInst.class, userData,
                                                                            Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRPIVOTALINST:
                theDataList.add(pivotalInstBean.validateField(dataList.get(1).toString(), (HRPivotalInst) dataList.get(2), userData));
                break;
//HRPivotalProgram
            case INSERT_HRPIVOTALPROGRAM:
                theDataList.add(pivotalProgramBean.insert((HRPivotalProgram) dataList.get(1), userData));
                break;
            case UPDATE_HRPIVOTALPROGRAM:
                theDataList.add(pivotalProgramBean.update((HRPivotalProgram) dataList.get(1), userData));
                break;
            case DELETE_HRPIVOTALPROGRAM:
                theDataList.add(pivotalProgramBean.delete((HRPivotalProgram) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRPIVOTALPROGRAM:
                theDataList.add(pivotalProgramBean.getNumRows(HRPivotalProgram.class, userData));
                break;
            case GETDATA_HRPIVOTALPROGRAM:
                theDataList = (List<Object>) pivotalProgramBean.getDataInRange(HRPivotalProgram.class, userData,
                                                                               Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRPIVOTALPROGRAM:
                theDataList.add(pivotalProgramBean.validateField(dataList.get(1).toString(), (HRPivotalProgram) dataList.get(2), userData));
                break;
//HRPivotalStudyField
            case INSERT_HRPIVOTALSTUDYFIELD:
                theDataList.add(pivotalStudyFieldBean.insert((HRPivotalStudyField) dataList.get(1), userData));
                break;
            case UPDATE_HRPIVOTALSTUDYFIELD:
                theDataList.add(pivotalStudyFieldBean.update((HRPivotalStudyField) dataList.get(1), userData));
                break;
            case DELETE_HRPIVOTALSTUDYFIELD:
                theDataList.add(pivotalStudyFieldBean.delete((HRPivotalStudyField) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRPIVOTALSTUDYFIELD:
                theDataList.add(pivotalStudyFieldBean.getNumRows(HRPivotalStudyField.class, userData));
                break;
            case GETDATA_HRPIVOTALSTUDYFIELD:
                theDataList = (List<Object>) pivotalStudyFieldBean.getDataInRange(HRPivotalStudyField.class, userData,
                                                                                  Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRPIVOTALSTUDYFIELD:
                theDataList.add(pivotalStudyFieldBean.validateField(dataList.get(1).toString(), (HRPivotalStudyField) dataList.get(2), userData));
                break;
//HRQualType
            case INSERT_HRQUALTYPE:
                theDataList.add(qualTypeBean.insert((HRQualType) dataList.get(1), userData));
                break;
            case UPDATE_HRQUALTYPE:
                theDataList.add(qualTypeBean.update((HRQualType) dataList.get(1), userData));
                break;
            case DELETE_HRQUALTYPE:
                theDataList.add(qualTypeBean.delete((HRQualType) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRQUALTYPE:
                theDataList.add(qualTypeBean.getNumRows(HRQualType.class, userData));
                break;
            case GETDATA_HRQUALTYPE:
                theDataList = (List<Object>) qualTypeBean.getDataInRange(HRQualType.class, userData,
                                                                         Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRQUALTYPE:
                theDataList.add(qualTypeBean.validateField(dataList.get(1).toString(), (HRQualType) dataList.get(2), userData));
                break;
//HRScarceCritSkills
            case INSERT_HRSCARCECRITSKILLS:
                theDataList.add(scarceCritSkillsBean.insert((HRScarceCritSkills) dataList.get(1), userData));
                break;
            case UPDATE_HRSCARCECRITSKILLS:
                theDataList.add(scarceCritSkillsBean.update((HRScarceCritSkills) dataList.get(1), userData));
                break;
            case DELETE_HRSCARCECRITSKILLS:
                theDataList.add(scarceCritSkillsBean.delete((HRScarceCritSkills) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRSCARCECRITSKILLS:
                theDataList.add(scarceCritSkillsBean.getNumRows(HRScarceCritSkills.class, userData));
                break;
            case GETDATA_HRSCARCECRITSKILLS:
                theDataList = (List<Object>) scarceCritSkillsBean.getDataInRange(HRScarceCritSkills.class, userData,
                                                                                 Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRSCARCECRITSKILLS:
                theDataList.add(scarceCritSkillsBean.validateField(dataList.get(1).toString(), (HRScarceCritSkills) dataList.get(2), userData));
                break;
//HRTrainingLevel
            case INSERT_HRTRAININGLEVEL:
                theDataList.add(trainingLevelBean.insert((HRTrainingLevel) dataList.get(1), userData));
                break;
            case UPDATE_HRTRAININGLEVEL:
                theDataList.add(trainingLevelBean.update((HRTrainingLevel) dataList.get(1), userData));
                break;
            case DELETE_HRTRAININGLEVEL:
                theDataList.add(trainingLevelBean.delete((HRTrainingLevel) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRTRAININGLEVEL:
                theDataList.add(trainingLevelBean.getNumRows(HRTrainingLevel.class, userData));
                break;
            case GETDATA_HRTRAININGLEVEL:
                theDataList = (List<Object>) trainingLevelBean.getDataInRange(HRTrainingLevel.class, userData,
                                                                              Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRTRAININGLEVEL:
                theDataList.add(trainingLevelBean.validateField(dataList.get(1).toString(), (HRTrainingLevel) dataList.get(2), userData));
                break;
//HRTrainingReason
            case INSERT_HRTRAININGREASON:
                theDataList.add(trainingReasonBean.insert((HRTrainingReason) dataList.get(1), userData));
                break;
            case UPDATE_HRTRAININGREASON:
                theDataList.add(trainingReasonBean.update((HRTrainingReason) dataList.get(1), userData));
                break;
            case DELETE_HRTRAININGREASON:
                theDataList.add(trainingReasonBean.delete((HRTrainingReason) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRTRAININGREASON:
                theDataList.add(trainingReasonBean.getNumRows(HRTrainingReason.class, userData));
                break;
            case GETDATA_HRTRAININGREASON:
                theDataList = (List<Object>) trainingReasonBean.getDataInRange(HRTrainingReason.class, userData,
                                                                               Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRTRAININGREASON:
                theDataList.add(trainingReasonBean.validateField(dataList.get(1).toString(), (HRTrainingReason) dataList.get(2), userData));
                break;
//HRTrainingType
            case INSERT_HRTRAININGTYPE:
                theDataList.add(trainingTypeBean.insert((HRTrainingType) dataList.get(1), userData));
                break;
            case UPDATE_HRTRAININGTYPE:
                theDataList.add(trainingTypeBean.update((HRTrainingType) dataList.get(1), userData));
                break;
            case DELETE_HRTRAININGTYPE:
                theDataList.add(trainingTypeBean.delete((HRTrainingType) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRTRAININGTYPE:
                theDataList.add(trainingTypeBean.getNumRows(HRTrainingType.class, userData));
                break;
            case GETDATA_HRTRAININGTYPE:
                theDataList = (List<Object>) trainingTypeBean.getDataInRange(HRTrainingType.class, userData,
                                                                             Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRTRAININGTYPE:
                theDataList.add(trainingTypeBean.validateField(dataList.get(1).toString(), (HRTrainingType) dataList.get(2), userData));
                break;
            //HRScarcePriority
            case INSERT_HRSCARCEPRIORITY:
                theDataList.add(scarcePriorityBean.insert((HRScarcePriority) dataList.get(1), userData));
                break;
            case UPDATE_HRSCARCEPRIORITY:
                theDataList.add(scarcePriorityBean.update((HRScarcePriority) dataList.get(1), userData));
                break;
            case DELETE_HRSCARCEPRIORITY:
                theDataList.add(scarcePriorityBean.delete((HRScarcePriority) dataList.get(1), userData));
                break;
            case GETNUMROWS_HRSCARCEPRIORITY:
                theDataList.add(scarcePriorityBean.getNumRows(HRScarcePriority.class, userData));
                break;
            case GETDATA_HRSCARCEPRIORITY:
                theDataList = (List<Object>) scarcePriorityBean.getDataInRange(HRScarcePriority.class, userData,
                                                                               Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_HRSCARCEPRIORITY:
                theDataList.add(scarcePriorityBean.validateField(dataList.get(1).toString(), (HRScarcePriority) dataList.get(2), userData));
                break;
            //Employment Summary
            case PRINT_EMPLOYMENT_SUMMARY:
                theDataList = employmentSummaryReportBean.getReportResult(dataList, cmd.getReportConfig(), userData);
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
