/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.workflow.activitiesmanager;

import emc.bus.base.BaseEmployeeLocal;
import emc.entity.base.BaseEmployeeTable;
import emc.entity.workflow.WorkFlowActivity;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.workflow.WFMyActivitiesEnum;
import emc.framework.EMCBusinessBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class WFMyActivitiesDataManagerBean extends EMCBusinessBean implements WFMyActivitiesDataManagerLocal {

    @EJB
    private BaseEmployeeLocal employeeBean;

    public Map<String, String> getNumberOfActivities(EMCUserData userData) {
        Map<String, String> retMap = new HashMap<String, String>();
        retMap.put(WFMyActivitiesEnum.EMPLOYEE_ACTIVITIES_TODAY.toString(), getNumEmployeeActivitiesToday(userData));
        retMap.put(WFMyActivitiesEnum.EMPLOYEE_ACTIVITIES_ALL.toString(), getNumEmployeeActivitiesAll(userData));
        retMap.put(WFMyActivitiesEnum.EMPLOYEE_ACTIVITIES_OVERDUE.toString(), getNumEmployeeActivitiesOverdue(userData));
        retMap.put(WFMyActivitiesEnum.EMPLOYEE_ACTIVITIES_NOT_CLOSED.toString(), getNumEmployeeActivitiesNotClosed(userData));
        retMap.put(WFMyActivitiesEnum.TASK_ACTIVITIES_ALL.toString(), getNumTaskActivitiesAll(userData));
        retMap.put(WFMyActivitiesEnum.TASK_ACTIVITIES_OVERDUE.toString(), getNumTaskActivitiesOverdue(userData));
        retMap.put(WFMyActivitiesEnum.TASK_ACTIVITIES_NOT_STARTED.toString(), getNumTaskActivitiesNotStarted(userData));
        retMap.put(WFMyActivitiesEnum.TASK_ACTIVITIES_TO_CLOSE.toString(), getNumTaskActivitiesToClose(userData));
        retMap.put(WFMyActivitiesEnum.MANAGER_ACTIVITIES_ALL.toString(), getNumManagerActivitiesAll(userData));
        retMap.put(WFMyActivitiesEnum.MANAGER_ACTIVITIES_OVERDUE.toString(), getNumManagerActivitiesOverdue(userData));
        retMap.put("birthdays", getBirthdays(userData));
        return retMap;
    }

    private String getNumEmployeeActivitiesToday(EMCUserData userData) {
        String employee = employeeBean.findEmployee(userData.getUserName(), userData);
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowActivity.class);
        query.addAnd("employeeNumber", employee);
        query.addAnd("requiredCompletionDate", Functions.nowDateString("yyyy/MM/dd"));
        query.addAnd("closedDate", null);
        query.addAnd("completionDate", null);
        return String.valueOf(util.executeSingleResultQuery(query.getCountQuery(true), userData));
    }

    private String getNumEmployeeActivitiesAll(EMCUserData userData) {
        String employee = employeeBean.findEmployee(userData.getUserName(), userData);
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowActivity.class);
        query.addAnd("employeeNumber", employee);
        query.addAnd("closedDate", null);
        query.addAnd("completionDate", null);
        return String.valueOf(util.executeSingleResultQuery(query.getCountQuery(true), userData));
    }

    private String getNumEmployeeActivitiesOverdue(EMCUserData userData) {
        String employee = employeeBean.findEmployee(userData.getUserName(), userData);
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowActivity.class);
        query.addAnd("employeeNumber", employee);
        query.addAnd("requiredCompletionDate", Functions.nowDateString("yyyy/MM/dd"), EMCQueryConditions.LESS_THAN);
        query.addAnd("closedDate", null);
        query.addAnd("completionDate", null);
        return String.valueOf(util.executeSingleResultQuery(query.getCountQuery(true), userData));
    }

    private String getNumEmployeeActivitiesNotClosed(EMCUserData userData) {
        String employee = employeeBean.findEmployee(userData.getUserName(), userData);
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowActivity.class);
        query.addAnd("employeeNumber", employee);
        query.addAnd("closedDate", null, EMCQueryConditions.NOT);
        query.addAnd("completionDate", null);
        return String.valueOf(util.executeSingleResultQuery(query.getCountQuery(true), userData));
    }

    private String getNumTaskActivitiesAll(EMCUserData userData) {
        String employee = employeeBean.findEmployee(userData.getUserName(), userData);
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowActivity.class);
        query.addAnd("managerResponsible", employee);
        query.addAnd("closedDate", null);
        query.addAnd("completionDate", null);
        return String.valueOf(util.executeSingleResultQuery(query.getCountQuery(true), userData));
    }

    private String getNumTaskActivitiesOverdue(EMCUserData userData) {
        String employee = employeeBean.findEmployee(userData.getUserName(), userData);
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowActivity.class);
        query.addAnd("managerResponsible", employee);
        query.addAnd("requiredCompletionDate", Functions.nowDateString("yyyy/MM/dd"), EMCQueryConditions.LESS_THAN);
        query.addAnd("closedDate", null);
        query.addAnd("completionDate", null);
        return String.valueOf(util.executeSingleResultQuery(query.getCountQuery(true), userData));
    }

    private String getNumTaskActivitiesNotStarted(EMCUserData userData) {
        String employee = employeeBean.findEmployee(userData.getUserName(), userData);
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowActivity.class);
        query.addAnd("managerResponsible", employee);
        query.addAnd("closedDate", null);
        query.addAnd("completionDate", null);
        query.addAnd("startDate", null);
        return String.valueOf(util.executeSingleResultQuery(query.getCountQuery(true), userData));
    }

    private String getNumTaskActivitiesToClose(EMCUserData userData) {
        String employee = employeeBean.findEmployee(userData.getUserName(), userData);
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowActivity.class);
        query.addAnd("managerResponsible", employee);
        query.addAnd("closedDate", null);
        query.addAnd("completionDate", null, EMCQueryConditions.NOT);
        return String.valueOf(util.executeSingleResultQuery(query.getCountQuery(true), userData));
    }

    private String getNumManagerActivitiesAll(EMCUserData userData) {
        String manager = employeeBean.findEmployee(userData.getUserName(), userData);
        EMCQuery managerQuery = new EMCQuery(enumQueryTypes.SELECT, BaseEmployeeTable.class);
        managerQuery.addAnd("manager", manager);
        managerQuery.addField("employeeNumber");

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowActivity.class);
        query.addAnd("managerResponsible", managerQuery, EMCQueryConditions.IN);
        query.addAnd("closedDate", null);
        query.addAnd("completionDate", null);
        return String.valueOf(util.executeSingleResultQuery(query.getCountQuery(true), userData));
    }

    private String getNumManagerActivitiesOverdue(EMCUserData userData) {
        String manager = employeeBean.findEmployee(userData.getUserName(), userData);
        EMCQuery managerQuery = new EMCQuery(enumQueryTypes.SELECT, BaseEmployeeTable.class);
        managerQuery.addAnd("manager", manager);
        managerQuery.addField("employeeNumber");

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowActivity.class);
        query.addAnd("managerResponsible", managerQuery, EMCQueryConditions.IN);
        query.addAnd("requiredCompletionDate", Functions.nowDateString("yyyy/MM/dd"), EMCQueryConditions.LESS_THAN);
        query.addAnd("closedDate", null);
        query.addAnd("completionDate", null);
        return String.valueOf(util.executeSingleResultQuery(query.getCountQuery(true), userData));
    }

    private String getBirthdays(EMCUserData userData) {
        List birthdayList = employeeBean.findBirthdays(userData);
        String birthdays = "";
        for (Object o : birthdayList) {
            birthdays += o.toString() + "~";
        }
        return birthdays;
    }

    public List<WorkFlowActivity> getActivitiesAll(String group, String type, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowActivity.class);
        query.addAnd("closedDate", null);
        query.addAnd("completionDate", null);
        query.addAnd("activityGroup", group);
        query.addAnd("type", type);
        return util.executeGeneralSelectQuery(query, userData);
    }

    public List<WorkFlowActivity> getActivitiesToday(String group, String type, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowActivity.class);
        query.addAnd("requiredCompletionDate", Functions.nowDateString("yyyy/MM/dd"));
        query.addAnd("closedDate", null);
        query.addAnd("completionDate", null);
        query.addAnd("activityGroup", group);
        query.addAnd("type", type);
        return util.executeGeneralSelectQuery(query, userData);
    }

    public List<WorkFlowActivity> getActivitiesOverdue(String group, String type, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowActivity.class);
        query.addAnd("requiredCompletionDate", Functions.nowDateString("yyyy/MM/dd"), EMCQueryConditions.LESS_THAN);
        query.addAnd("closedDate", null);
        query.addAnd("completionDate", null);
        query.addAnd("activityGroup", group);
        query.addAnd("type", type);
        return util.executeGeneralSelectQuery(query, userData);
    }

    public List<WorkFlowActivity> getActivitiesNotStarted(String group, String type, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowActivity.class);
        query.addAnd("closedDate", null);
        query.addAnd("completionDate", null);
        query.addAnd("startDate", null);
        query.addAnd("activityGroup", group);
        query.addAnd("type", type);
        return util.executeGeneralSelectQuery(query, userData);
    }

    public List<WorkFlowActivity> getActivitiesNotClosed(String group, String type, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowActivity.class);
        query.addAnd("closedDate", null, EMCQueryConditions.NOT);
        query.addAnd("completionDate", null);
        query.addAnd("activityGroup", group);
        query.addAnd("type", type);
        return util.executeGeneralSelectQuery(query, userData);
    }

    public List<WorkFlowActivity> getActivitiesToClose(String group, String type, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowActivity.class);
        query.addAnd("closedDate", null);
        query.addAnd("completionDate", null, EMCQueryConditions.NOT);
        query.addAnd("activityGroup", group);
        query.addAnd("type", type);
        return util.executeGeneralSelectQuery(query, userData);
    }
}
