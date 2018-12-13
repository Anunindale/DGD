/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.workflow.activityalerts;

import emc.bus.base.BaseEmployeeLocal;
import emc.bus.workflow.activitiesmanager.WFMyActivitiesDataManagerLocal;
import emc.bus.workflow.activitiesmanager.resources.WFMyActivitiesMessage;
import emc.entity.base.BaseEmployeeTable;
import emc.entity.workflow.WFActivityAlerts;
import emc.entity.workflow.WorkFlowActivity;
import emc.enums.enumQueryTypes;
import emc.enums.workflow.WFMyActivitiesEnum;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.server.mailmanager.EMCMailManagerLocal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.MessagingException;

/**
 *
 * @author wikus
 */
@Stateless
public class WFAcrivityAlertsBean extends EMCEntityBean implements WFActivityAlertsLocal {

    @EJB
    private BaseEmployeeLocal employeeBean;
    @EJB
    private EMCMailManagerLocal mailBean;
    @EJB
    private WFMyActivitiesDataManagerLocal myActivityDataManagerBean;

    @Override
    public void sendActivityAlert(EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, WFActivityAlerts.class);
        List<WFActivityAlerts> alertList = util.executeGeneralSelectQuery(query, userData);
        List<WorkFlowActivity> activityList = null;
        Map<String, Map<String, List<WFMyActivitiesMessage>>> messageMap = new HashMap<String, Map<String, List<WFMyActivitiesMessage>>>();
        String mapKey;
        for (WFActivityAlerts alert : alertList) {
            switch (WFMyActivitiesEnum.fromString(alert.getActivityCategory())) {
                case EMPLOYEE_ACTIVITIES_ALL:
                case TASK_ACTIVITIES_ALL:
                case MANAGER_ACTIVITIES_ALL:
                    activityList = myActivityDataManagerBean.getActivitiesAll(alert.getActivityGroup(), alert.getActivityType(), userData);
                    break;
                case EMPLOYEE_ACTIVITIES_OVERDUE:
                case MANAGER_ACTIVITIES_OVERDUE:
                case TASK_ACTIVITIES_OVERDUE:
                    activityList = myActivityDataManagerBean.getActivitiesOverdue(alert.getActivityGroup(), alert.getActivityType(), userData);
                    break;
                case EMPLOYEE_ACTIVITIES_TODAY:
                    activityList = myActivityDataManagerBean.getActivitiesToday(alert.getActivityGroup(), alert.getActivityType(), userData);
                    break;
                case TASK_ACTIVITIES_NOT_STARTED:
                    activityList = myActivityDataManagerBean.getActivitiesNotStarted(alert.getActivityGroup(), alert.getActivityType(), userData);
                    break;
                case EMPLOYEE_ACTIVITIES_NOT_CLOSED:
                    activityList = myActivityDataManagerBean.getActivitiesNotClosed(alert.getActivityGroup(), alert.getActivityType(), userData);
                    break;
                case TASK_ACTIVITIES_TO_CLOSE:
                    activityList = myActivityDataManagerBean.getActivitiesToClose(alert.getActivityGroup(), alert.getActivityType(), userData);
                    break;
            }
            for (WorkFlowActivity activity : activityList) {
                if (alert.isManager()) {
                    mapKey = activity.getManagerResponsible();
                    messageMap.put(mapKey, createMessage(activity, messageMap.get(mapKey), alert.getActivityCategory()));
                }
                if (alert.isTaskManager()) {
                    mapKey = activity.getManagerResponsible();
                    messageMap.put(mapKey, createMessage(activity, messageMap.get(mapKey), alert.getActivityCategory()));
                }
                if (alert.isEmployee()) {
                    mapKey = activity.getEmployeeNumber();
                    messageMap.put(mapKey, createMessage(activity, messageMap.get(mapKey), alert.getActivityCategory()));
                }
            }
        }
        sendEmails(messageMap, userData);
    }

    private Map<String, List<WFMyActivitiesMessage>> createMessage(WorkFlowActivity activity, Map<String, List<WFMyActivitiesMessage>> messageMap, String category) {
        if (messageMap == null) {
            messageMap = new HashMap<String, List<WFMyActivitiesMessage>>();
        }
        List<WFMyActivitiesMessage> messageList = messageMap.get(category);
        if (messageList == null) {
            messageList = new ArrayList<WFMyActivitiesMessage>();
        }
        messageList.add(new WFMyActivitiesMessage(activity.getActivityId(), activity.getDescription(), activity.getRequiredCompletionDate(),
                activity.getManagerResponsible(), activity.getManagerResponsible(), activity.getEmployeeNumber()));
        messageMap.put(category, messageList);
        return messageMap;
    }

    private void sendEmails(Map<String, Map<String, List<WFMyActivitiesMessage>>> messageMap, EMCUserData userData) {
        Map<String, BaseEmployeeTable> employeeMap = new HashMap<String, BaseEmployeeTable>();
        BaseEmployeeTable employee;
        StringBuilder messageBuilder = null;
        for (Entry<String, Map<String, List<WFMyActivitiesMessage>>> messageMapEntry : messageMap.entrySet()) {
            employee = employeeMap.get(messageMapEntry.getKey());
            if (employee == null) {
                employee = employeeBean.getEmployeeRecord(messageMapEntry.getKey(), userData);
                employeeMap.put(messageMapEntry.getKey(), employee);
            }
            if (isBlank(employee.getEmailAddress())) {
                continue;
            }

            messageBuilder = new StringBuilder();
            messageBuilder.append(employee.getForenames() + " " + employee.getSurname() + "\n");
            messageBuilder.append("\n");
            messageBuilder.append("\n");
            for (Entry<String, List<WFMyActivitiesMessage>> messageEntry : messageMapEntry.getValue().entrySet()) {
                messageBuilder.append(messageEntry.getKey() + "\n");
                messageBuilder.append("\n");
                for (WFMyActivitiesMessage message : messageEntry.getValue()) {
                    messageBuilder.append("   Activity Id         : " + message.getActivityNum() + "\n");
                    messageBuilder.append("   Description         : " + message.getDescription() + "\n");
                    messageBuilder.append("   Due Date            : " + message.getDueDate() + "\n");
                    messageBuilder.append("   Manager Responsible : " + message.getManager() + "\n");
                    messageBuilder.append("   Task Manager        : " + message.getManager() + "\n");
                    messageBuilder.append("   Employee responsible: " + message.getEmployee() + "\n");
                    messageBuilder.append("\n");
                }
                messageBuilder.append("\n");
                messageBuilder.append("\n");
            }
            messageBuilder.append("EMC by ASD" + "\n");
            try {
                mailBean.postMail(new String[]{employee.getEmailAddress()}, null, null, "Activity Alert", messageBuilder.toString(), null, "wsb@asd.co.za", null, userData);
            } catch (MessagingException ex) {
                System.out.println("Failed to send email to " + employee.getForenames() + " " + employee.getSurname());
            }

        }
    }
}
