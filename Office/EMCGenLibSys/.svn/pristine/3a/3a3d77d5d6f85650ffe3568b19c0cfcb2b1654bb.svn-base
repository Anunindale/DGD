/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.workflow.datasource;

import emc.datatypes.workflow.personalformds.ActiveActivitiesEmp;
import emc.datatypes.workflow.personalformds.ActiveActivitiesEmpMan;
import emc.datatypes.workflow.personalformds.ActiveActivitiesTaskMan;
import emc.datatypes.workflow.personalformds.ActivitiesNotStartedTaskMan;
import emc.datatypes.workflow.personalformds.ActivitiesToCloseTaskMan;
import emc.datatypes.workflow.personalformds.NotClosedActivitiesEmp;
import emc.datatypes.workflow.personalformds.OverdueActivitiesEmp;
import emc.datatypes.workflow.personalformds.OverdueActivitiesEmpMan;
import emc.datatypes.workflow.personalformds.OverdueActivitiesTaskMan;
import emc.framework.EMCEntityClass;
import java.util.HashMap;

/**
 *
 * @author riaan
 */
public class PersonalFormDS extends EMCEntityClass {

    private String activeActiviesForTodayEmp;
    private String activeActiviesEmp;
    private String overdueActivitiesEmp;
    private String notClosedActivitiesEmp;
    private String activeActivitiesEmpMan;
    private String overdueActivitiesEmpMan;
    private String activeActivitiesTaskMan;
    private String overdueActivitiesTaskMan;
    private String activitiesNotStartedTaskMan;
    private String activitiesToCloseTaskMan;

    /** Creates a new instance of PersonalFormDS */
    public PersonalFormDS() {
        this.setDataSource(true);
    }

    public String getActiveActiviesForTodayEmp() {
        return activeActiviesForTodayEmp;
    }

    public void setActiveActiviesForTodayEmp(String activeActiviesForTodayEmp) {
        this.activeActiviesForTodayEmp = activeActiviesForTodayEmp;
    }

    public String getActiveActiviesEmp() {
        return activeActiviesEmp;
    }

    public void setActiveActiviesEmp(String activeActiviesEmp) {
        this.activeActiviesEmp = activeActiviesEmp;
    }

    public String getOverdueActivitiesEmp() {
        return overdueActivitiesEmp;
    }

    public void setOverdueActivitiesEmp(String overdueActivitiesEmp) {
        this.overdueActivitiesEmp = overdueActivitiesEmp;
    }

    public String getNotClosedActivitiesEmp() {
        return notClosedActivitiesEmp;
    }

    public void setNotClosedActivitiesEmp(String notClosedActivitiesEmp) {
        this.notClosedActivitiesEmp = notClosedActivitiesEmp;
    }

    public String getActiveActivitiesEmpMan() {
        return activeActivitiesEmpMan;
    }

    public void setActiveActivitiesEmpMan(String activeActivitiesEmpMan) {
        this.activeActivitiesEmpMan = activeActivitiesEmpMan;
    }

    public String getOverdueActivitiesEmpMan() {
        return overdueActivitiesEmpMan;
    }

    public void setOverdueActivitiesEmpMan(String overdueActivitiesEmpMan) {
        this.overdueActivitiesEmpMan = overdueActivitiesEmpMan;
    }

    public String getActiveActivitiesTaskMan() {
        return activeActivitiesTaskMan;
    }

    public void setActiveActivitiesTaskMan(String activeActivitiesTaskMan) {
        this.activeActivitiesTaskMan = activeActivitiesTaskMan;
    }

    public String getOverdueActivitiesTaskMan() {
        return overdueActivitiesTaskMan;
    }

    public void setOverdueActivitiesTaskMan(String overdueActivitiesTaskMan) {
        this.overdueActivitiesTaskMan = overdueActivitiesTaskMan;
    }

    public String getActivitiesNotStartedTaskMan() {
        return activitiesNotStartedTaskMan;
    }

    public void setActivitiesNotStartedTaskMan(String activitiesNotStartedTaskMan) {
        this.activitiesNotStartedTaskMan = activitiesNotStartedTaskMan;
    }

    public String getActivitiesToCloseTaskMan() {
        return activitiesToCloseTaskMan;
    }

    public void setActivitiesToCloseTaskMan(String activitiesToCloseTaskMan) {
        this.activitiesToCloseTaskMan = activitiesToCloseTaskMan;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("activeActiviesEmp", new ActiveActivitiesEmp());
        toBuild.put("overdueActivitiesEmp", new OverdueActivitiesEmp());
        toBuild.put("notClosedActivitiesEmp", new NotClosedActivitiesEmp());
        toBuild.put("activeActivitiesEmpMan", new ActiveActivitiesEmpMan());
        toBuild.put("overdueActivitiesEmpMan", new OverdueActivitiesEmpMan());
        toBuild.put("activeActivitiesTaskMan", new ActiveActivitiesTaskMan());
        toBuild.put("overdueActivitiesTaskMan", new OverdueActivitiesTaskMan());
        toBuild.put("activitiesNotStartedTaskMan", new ActivitiesNotStartedTaskMan());
        toBuild.put("activitiesToCloseTaskMan", new ActivitiesToCloseTaskMan());

        return toBuild;
    }
}
