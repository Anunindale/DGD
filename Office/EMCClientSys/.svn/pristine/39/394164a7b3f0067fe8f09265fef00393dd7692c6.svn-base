/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.personal.display.personal;

import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTextArea;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.panelswitcher.EMCPanelSwitcher;
import emc.app.components.panelswitcher.EMCPanelSwitcherItem;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.enums.modules.enumEMCModules;
import emc.forms.personal.display.personal.resources.ActivitiesLinkAction;
import emc.entity.base.BaseEmployeeTable;
import emc.entity.workflow.WorkFlowActivity;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.workflow.WFMyActivitiesEnum;
import emc.forms.personal.display.personal.resources.PersonalDRM;
import emc.forms.personal.display.todo.resources.todoDocument;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.base.menuItems.display.employees;
import emc.menus.workflow.menuitems.display.WorkFlowSimpleActivitiesMenu;
import emc.menus.workflow.menuitems.display.activityMain;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.Timer;

/**
 *
 * @author riaan
 */
public class MyActivitiesForm extends BaseInternalFrame {

    private EMCUserData userData;
    private emcDataRelationManagerUpdate dataRelation;
    private String employeeNumber;
    private EMCCommandClass cmd;
    private Timer refrehT;
    private emcJPanel pnlBirthdays;
    private ArrayList<ActivitiesLinkAction> refreshList = new ArrayList<ActivitiesLinkAction>();

    /** Creates a new instance of PersonalForm */
    public MyActivitiesForm(EMCUserData userData) {
        super("My Activities", true, true, true, true, userData);
        this.setBounds(20, 20, 355, 490);
        this.userData = userData.copyUserDataAndDataList();
        this.setLayout(new BorderLayout());

        List<EMCPanelSwitcherItem> items = new ArrayList<EMCPanelSwitcherItem>();

        this.getEmployeeNumber();

        dataRelation = new PersonalDRM();
        dataRelation.setTheForm(this);
        this.setDataManager(dataRelation);

        items.add(setupEmployeeActivities());
        items.add(setupManagetActivities());
        items.add(setupTaskActivities());
        items.add(setupBirthdays());

        emcJPanel pnlPostIt = new emcJPanel();
        pnlPostIt.setBorder(BorderFactory.createTitledBorder("Post It"));
        pnlPostIt.setLayout(new GridLayout(1, 1));
        emcJTextArea textArea = new emcJTextArea(10, 20);
        textArea.setDocument(new todoDocument());
        pnlPostIt.add(new JScrollPane(textArea));

        refresh();
        createRefreshTimer(30000, this);

        this.add(pnlPostIt, BorderLayout.SOUTH);
        this.add(new EMCPanelSwitcher(items), BorderLayout.CENTER);
    }

    public String getEmployeeNumber() {
        if (Functions.checkBlank(employeeNumber)) {
            employeeNumber = "";

            EMCUserData copyUD = userData.copyUserData();

            cmd = new EMCCommandClass();
            cmd.setCommandId(emc.commands.EMCCommands.SERVER_GENERAL_COMMAND.getId());
            cmd.setModuleNumber(enumEMCModules.BASE.getId());
            cmd.setMethodId(emc.methods.base.ServerBaseMethods.FIND_EMPLOYEE.toString());
            List toSend = new ArrayList();
            toSend.add(copyUD.getUserName());

            List retData = EMCWSManager.executeGenericWS(cmd, toSend, copyUD);
            if (retData.size() > 0) {
                this.employeeNumber = retData.get(1).toString();
            }
        }
        return employeeNumber;
    }

    //This method is used to create a timer, which refreshes the form
    private void createRefreshTimer(int interval, MyActivitiesForm theForm) {

        //The form
        final MyActivitiesForm thisForm = theForm;

        refrehT = new javax.swing.Timer(interval, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                thisForm.refresh();
            }
        });
        refrehT.start();
    }

    public void stopRefreshTimer() {
        refrehT.stop();
    }

    private EMCPanelSwitcherItem setupEmployeeActivities() {
        WorkFlowSimpleActivitiesMenu theMenu = new WorkFlowSimpleActivitiesMenu();

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowActivity.class);
        query.addAnd("employeeNumber", employeeNumber);
        query.addAnd("requiredCompletionDate", Functions.nowDateString("yyyy/MM/dd"));
        query.addAnd("closedDate", null);
        query.addAnd("completionDate", null);
        ActivitiesLinkAction allMyActiveActivitiesForToday = new ActivitiesLinkAction(WFMyActivitiesEnum.EMPLOYEE_ACTIVITIES_TODAY, query, theMenu, this, userData);
        this.refreshList.add(allMyActiveActivitiesForToday);

        query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowActivity.class);
        query.addAnd("employeeNumber", employeeNumber);
        query.addAnd("closedDate", null);
        query.addAnd("completionDate", null);
        ActivitiesLinkAction allMyActiveActivities = new ActivitiesLinkAction(WFMyActivitiesEnum.EMPLOYEE_ACTIVITIES_ALL, query, theMenu, this, userData);
        this.refreshList.add(allMyActiveActivities);

        query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowActivity.class);
        query.addAnd("employeeNumber", employeeNumber);
        query.addAnd("requiredCompletionDate", Functions.nowDateString("yyyy/MM/dd"), EMCQueryConditions.LESS_THAN);
        query.addAnd("closedDate", null);
        query.addAnd("completionDate", null);
        ActivitiesLinkAction allMyOverdueActivities = new ActivitiesLinkAction(WFMyActivitiesEnum.EMPLOYEE_ACTIVITIES_OVERDUE, query, theMenu, this, userData);
        this.refreshList.add(allMyOverdueActivities);

        query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowActivity.class);
        query.addAnd("employeeNumber", employeeNumber);
        query.addAnd("closedDate", null, EMCQueryConditions.NOT);
        query.addAnd("completionDate", null);
        ActivitiesLinkAction allMyActivitiesNotClosed = new ActivitiesLinkAction(WFMyActivitiesEnum.EMPLOYEE_ACTIVITIES_NOT_CLOSED, query, theMenu, this, userData);
        this.refreshList.add(allMyActivitiesNotClosed);

        Component[][] components = new Component[][]{
            {allMyActiveActivitiesForToday},
            {allMyActiveActivities},
            {allMyOverdueActivities},
            {allMyActivitiesNotClosed}
        };

        emcJPanel pnlMyActivities = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.HORIZONTAL, true);

        return new EMCPanelSwitcherItem("My Activities", pnlMyActivities);
    }

    private EMCPanelSwitcherItem setupManagetActivities() {
        activityMain theMenu = new activityMain();

        EMCQuery managerQuery = new EMCQuery(enumQueryTypes.SELECT, BaseEmployeeTable.class);
        managerQuery.addAnd("manager", employeeNumber);
        managerQuery.addField("employeeNumber");

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowActivity.class);
        query.addAnd("managerResponsible", managerQuery, EMCQueryConditions.IN);
        query.addAnd("closedDate", null);
        query.addAnd("completionDate", null);
        ActivitiesLinkAction allActivitiesIAmTheManager = new ActivitiesLinkAction(WFMyActivitiesEnum.MANAGER_ACTIVITIES_ALL, query, theMenu, this, userData);
        this.refreshList.add(allActivitiesIAmTheManager);

        managerQuery = new EMCQuery(enumQueryTypes.SELECT, BaseEmployeeTable.class);
        managerQuery.addAnd("manager", employeeNumber);
        managerQuery.addField("employeeNumber");

        query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowActivity.class);
        query.addAnd("managerResponsible", managerQuery, EMCQueryConditions.IN);
        query.addAnd("requiredCompletionDate", Functions.nowDateString("yyyy/MM/dd"), EMCQueryConditions.LESS_THAN);
        query.addAnd("closedDate", null);
        query.addAnd("completionDate", null);
        ActivitiesLinkAction overdueActivitiesIAmTheManager = new ActivitiesLinkAction(WFMyActivitiesEnum.MANAGER_ACTIVITIES_OVERDUE, query, theMenu, this, userData);
        this.refreshList.add(overdueActivitiesIAmTheManager);

        Component[][] components = new Component[][]{
            {allActivitiesIAmTheManager},
            {overdueActivitiesIAmTheManager},};

        emcJPanel pnlActivitiesIAmTheManager = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.HORIZONTAL, true);

        return new EMCPanelSwitcherItem("Activities: I am the Employee Manager", pnlActivitiesIAmTheManager);
    }

    private EMCPanelSwitcherItem setupTaskActivities() {
        activityMain theMenu = new activityMain();

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowActivity.class);
        query.addAnd("managerResponsible", employeeNumber);
        query.addAnd("closedDate", null);
        query.addAnd("completionDate", null);
        ActivitiesLinkAction allActivitiesIAmTheTaskManager = new ActivitiesLinkAction(WFMyActivitiesEnum.TASK_ACTIVITIES_ALL, query, theMenu, this, userData);
        this.refreshList.add(allActivitiesIAmTheTaskManager);

        query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowActivity.class);
        query.addAnd("managerResponsible", employeeNumber);
        query.addAnd("requiredCompletionDate", Functions.nowDateString("yyyy/MM/dd"), EMCQueryConditions.LESS_THAN);
        query.addAnd("closedDate", null);
        query.addAnd("completionDate", null);
        ActivitiesLinkAction overdueActivitiesIAmTheTaskManager = new ActivitiesLinkAction(WFMyActivitiesEnum.TASK_ACTIVITIES_OVERDUE, query, theMenu, this, userData);
        this.refreshList.add(overdueActivitiesIAmTheTaskManager);

        query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowActivity.class);
        query.addAnd("managerResponsible", employeeNumber);
        query.addAnd("closedDate", null);
        query.addAnd("completionDate", null);
        query.addAnd("startDate", null);
        ActivitiesLinkAction activitiesNotStarted = new ActivitiesLinkAction(WFMyActivitiesEnum.TASK_ACTIVITIES_NOT_STARTED, query, theMenu, this, userData);
        this.refreshList.add(activitiesNotStarted);

        query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowActivity.class);
        query.addAnd("managerResponsible", employeeNumber);
        query.addAnd("closedDate", null);
        query.addAnd("completionDate", null, EMCQueryConditions.NOT);
        ActivitiesLinkAction activitiesIHaveToClose = new ActivitiesLinkAction(WFMyActivitiesEnum.TASK_ACTIVITIES_TO_CLOSE, query, theMenu, this, userData);
        this.refreshList.add(activitiesIHaveToClose);

        Component[][] components = new Component[][]{
            {allActivitiesIAmTheTaskManager},
            {overdueActivitiesIAmTheTaskManager},
            {activitiesNotStarted},
            {activitiesIHaveToClose},};

        emcJPanel pnlActivitiesIAmTheTaskManager = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.HORIZONTAL, true);

        return new EMCPanelSwitcherItem("Activities: I am the Manager of the activity", pnlActivitiesIAmTheTaskManager);
    }

    private EMCPanelSwitcherItem setupBirthdays() {
        this.pnlBirthdays = new emcJPanel(new GridBagLayout());
        return new EMCPanelSwitcherItem("Today's Birthdays", pnlBirthdays);
    }

    public void refresh() {
        EMCCommandClass refreshCmd = new EMCCommandClass();
        refreshCmd.setCommandId(emc.commands.EMCCommands.SERVER_GENERAL_COMMAND.getId());
        refreshCmd.setModuleNumber(enumEMCModules.WORKFLOW.getId());
        refreshCmd.setMethodId(emc.methods.workflow.ServerWorkFlowMethods.GET_MY_ACTIVITIES_INFO.toString());
        Map<String, String> infoMap = (Map<String, String>) EMCWSManager.executeGenericWS(refreshCmd, new ArrayList(), userData).get(1);

        for (ActivitiesLinkAction link : refreshList) {
            link.refresh(infoMap);
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlBirthdays.removeAll();
        String birthdays = infoMap.get("birthdays");
        if (!Functions.checkBlank(birthdays)) {
            employees menu = new employees();
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseEmployeeTable.class);
            for (String s : birthdays.split("~")) {
                String[] details = s.split("\\|");

                //Should have name in position 0 and record ID in position 1
                if (details.length == 2) {
                    try {
                        String name = details[0];
                        long recordID = Long.valueOf(details[1]);

                        EMCQuery empQuery = query.copyQuery();
                        empQuery.addAnd("recordID", recordID);
                        this.pnlBirthdays.add(new ActivitiesLinkAction(name, empQuery, menu, this, userData), gbc);
                        gbc.gridy = gbc.gridy + 1;
                    } catch (Exception ex) {
                        //Do nothing
                    }
                }
            }
        }
        gbc = emcSetGridBagConstraints.endPanel(gbc.gridy);
        pnlBirthdays.add(new emcJLabel(), gbc);
        pnlBirthdays.repaint();
        pnlBirthdays.revalidate();
    }
}
