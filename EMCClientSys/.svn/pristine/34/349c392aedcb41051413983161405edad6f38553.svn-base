/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.developertools.display.timesheet.resources;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.scheduletable.EMCScheduleTableComponent;
import emc.app.components.emctable.scheduletable.EMCScheduleTableDRM;
import emc.app.reporttools.JasperInformation;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCReportWSManager;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.developertools.DevParameters;
import emc.entity.developertools.DevTimeSheet;
import emc.entity.developertools.bugtracking.DevBugsTable;
import emc.entity.developertools.datasources.DevTimeSheetDS;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.enumQueryTypes;
import emc.forms.developertools.display.timesheet.DevTimeSheetForm;
import emc.framework.EMCCommandClass;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.helpers.devtools.DevTimeSheetTaskHelper;
import emc.menus.developertools.Bugs;
import emc.methods.developertools.ServerDeveloperToolMethods;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author wikus
 */
public class DevTimeSheetDRM extends EMCScheduleTableDRM {

    private Date selectedDate;
    private String generalUser;

    public DevTimeSheetDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData) {
        super(tableDataSource, userData);

        //Fetch General User
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DevParameters.class);
        query.addField("generalUser");
        List toSend = new ArrayList();
        toSend.add(query);
        EMCCommandClass cmd = new EMCCommandClass(ServerDeveloperToolMethods.EXECUTE_EMCQUERY);
        toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

        if (toSend.size() > 1) {
            generalUser = (String) toSend.get(1);
        }
    }

    @Override
    public EMCScheduleTableComponent createScheduleComponent(int rowInCach, EMCEntityClass record) {
        if (record instanceof DevTimeSheetDS) {
            DevTimeSheetDS timeSheet = (DevTimeSheetDS) record;
            if (!Functions.checkBlank(timeSheet.getEmployeeId())) {

                EMCScheduleTableComponent comp = new EMCScheduleTableComponent(this);
                comp.setRowInCach(rowInCach);
                comp.setColumnCount(calculateColumn(timeSheet.getWorkDate()));
                comp.setStartRow(calculateRow(timeSheet.getWorkStartTime(), true));
                comp.setEndRow(calculateRow(timeSheet.getWorkEndTime(), false));
                comp.setCompTimeLable(timeSheet.getTaskNumber() + " - " + timeSheet.getClient() + " - " + timeSheet.getTimeTaken());
                comp.setCompLable((Functions.checkBlank(timeSheet.getTaskSummary()) ? "" : timeSheet.getTaskSummary()));
                comp.setPopup(new DevTimeSheetPopup(comp));

                if (comp.getColumnCount() >= 0) {
                    return comp;
                }
            }
        }
        return null;
    }

    @Override
    public void addSession() {
        DevTimeSheetForm theForm = (DevTimeSheetForm) getTheForm();
        String sessionId = theForm.getEmployeeId();
        if (Functions.checkBlank(sessionId)) {
            Logger.getLogger("emc").log(Level.SEVERE, "Please select an employee first.", getUserData().copyUserData());
            return;
        }
        new DevAddTimeSheetDialog(this, sessionId, utilFunctions.findEMCDesktop(getTheForm()), getUserData().copyUserData());

        refreshData();
    }

    @Override
    public void editSession(EMCEntityClass record) {
        new DevEditTimeSheetDialog(this, (DevTimeSheet) record, utilFunctions.findEMCDesktop(getTheForm()), getUserData().copyUserData());

        refreshData();
    }

    @Override
    public void removeSession(EMCEntityClass record) {
        if (EMCDialogFactory.createQuestionDialog(getTheForm(), "Remove Session", "Are you sure you want to remove the selected session?") == JOptionPane.YES_OPTION) {
            EMCCommandClass cmd = new EMCCommandClass(ServerDeveloperToolMethods.DELETE_DEVTIMESHEETDS);
            List toSend = new ArrayList();
            toSend.add(record);

            toSend = EMCWSManager.executeGenericWS(cmd, toSend, getUserData());

            refreshData();
        }
    }

    public long createTask(DevTimeSheet timeSheet) {
        EMCCommandClass cmd = new EMCCommandClass(ServerDeveloperToolMethods.FETCH_MATCHING_TASKS);

        List toSend = new ArrayList();
        toSend.add(timeSheet);

        toSend = EMCWSManager.executeGenericWS(cmd, toSend, getUserData());
        toSend.remove(0);

        DevBugsTable task;

        if (toSend.isEmpty()) {
            DevTaskCreationDialog taskDialog = new DevTaskCreationDialog(utilFunctions.findEMCDesktop(getTheForm()), getUserData().copyUserData());
            DevTimeSheetTaskHelper helper = taskDialog.getHelper();

            if (helper == null) {
                return -1;
            }

            task = new DevBugsTable();
            task.setResponsible(timeSheet.getEmployeeId());
            task.setPriority(helper.getPriority());
            task.setModuleId(helper.getModule());
            task.setClient(helper.getClient());
            task.setParentTask(helper.getParentTask());
            task.setBugType(helper.getType());
            task.setRequiredDate(helper.getRequiredDate());
            task.setSummary(timeSheet.getTaskSummary());
            task.setBugId(timeSheet.getTaskSummary() + "\n");
        } else if (toSend.size() == 1) {
            task = (DevBugsTable) toSend.get(0);
        } else {
            DevTaskSelectionDialog taskSelectionDialog = new DevTaskSelectionDialog(utilFunctions.findEMCDesktop(getTheForm()), toSend);
            if (taskSelectionDialog.getSelectedTask() == -1) {
                return -1;
            } else {
                task = (DevBugsTable) toSend.get(taskSelectionDialog.getSelectedTask());
            }

        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        task.setBugId(task.getBugId() + "\n\n" + Functions.date2String(timeSheet.getWorkDate()) + " " + (Functions.calculateTimeDiff(timeSheet.getWorkStartTime(), timeSheet.getWorkEndTime()) / 60)+ " minutes " + timeSheet.getEmployeeId() + "\n" + timeSheet.getTaskDetail());

        cmd = new EMCCommandClass(ServerDeveloperToolMethods.PERSIST_TIME_SHEET_TASK);
        toSend = new ArrayList();
        toSend.add(task);

        toSend = EMCWSManager.executeGenericWS(cmd, toSend, getUserData());

        if (toSend.size() > 1 && toSend.get(1) instanceof Long) {
            return (Long) toSend.get(1);
        }

        return -1;
    }

    public void completeTask(DevTimeSheet devTimeSheet) {
        DevTaskCompletionDialog completionDialog = new DevTaskCompletionDialog(utilFunctions.findEMCDesktop(getTheForm()), getUserData().copyUserData());

        DevTimeSheetTaskHelper helper = completionDialog.getHelper();

        if (helper != null) {
            EMCCommandClass cmd = new EMCCommandClass(ServerDeveloperToolMethods.COMPLETE_TASK);

            List toSend = new ArrayList();
            toSend.add(devTimeSheet);
            toSend.add(helper);

            toSend = EMCWSManager.executeGenericWS(cmd, toSend, getUserData());

            if (toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1)) {
                Logger.getLogger("emc").log(Level.INFO, "Task Completed", getUserData());

                cmd = new EMCCommandClass(ServerDeveloperToolMethods.PRINT_DEVELOPMENT_SUMMARY_SHEET);

                JasperInformation jasperInfo = new JasperInformation();
                jasperInfo.setJasperTemplate("/emc/reports/developertools/taskmanagement/DevDevelopmentSummary.jrxml");
                jasperInfo.setXmlNodePath("/emcmsg/emc.reports.developertools.taskmanagement.developmentsummary.DevDevelopmentSummaryDS");
                jasperInfo.setReportTitle("Development Summary");

                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DevBugsTable.class);
                query.addAnd("recordID", devTimeSheet.getTaskRecordId());

                toSend = new ArrayList();
                toSend.add(query);

                EMCReportWSManager.generateReport(cmd, jasperInfo, EnumReports.DEV_DEVELOPMENT_SUMMARY, toSend, getUserData());
            } else {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to Complete Task.", getUserData());
            }
        }
    }

    public void viewTask(DevTimeSheet devTimeSheet) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DevBugsTable.class);
        query.addAnd("recordID", devTimeSheet.getTaskRecordId());

        List udList = new ArrayList();
        udList.add(query);

        EMCUserData userData = getUserData();
        userData.setUserData(udList);

        getTheForm().getDeskTop().createAndAdd(new Bugs(), -1, -1, userData, null, 1);
    }

    @Override
    public void setFirstDate(Date firstDate) {
        super.setFirstDate(firstDate);
        setSelectedDate(firstDate);
    }

    public Date getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }

    public String getGeneralUser() {
        return generalUser;
    }

}
