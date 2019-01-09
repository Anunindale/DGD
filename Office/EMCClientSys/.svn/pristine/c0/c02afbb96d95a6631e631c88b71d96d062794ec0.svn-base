/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.workflow;

import emc.framework.EMCMenu;
import emc.menus.workflow.menuitems.display.activityCategory;
import emc.menus.workflow.menuitems.display.activityGroup;
import emc.menus.workflow.menuitems.display.activityGroupEmployee;
import emc.menus.workflow.menuitems.display.activityPriority;
import emc.menus.workflow.menuitems.display.activityStatus;
import emc.menus.workflow.menuitems.display.activityTypes;
import emc.menus.workflow.menuitems.display.employeeSkills;
import emc.menus.workflow.menuitems.display.ratings;
import emc.menus.workflow.menuitems.display.skills;
import emc.menus.workflow.menuitems.display.Departments;
import emc.menus.workflow.menuitems.display.EmployeeTypes;
import emc.menus.workflow.menuitems.display.JobTitles;
import emc.menus.workflow.menuitems.display.WorkFlowActivityAlertsMenu;
import emc.menus.workflow.menuitems.display.WorkFlowNumberSequenceMenu;

/**
 *
 * @author rico
 */
public class workFlowSetup extends EMCMenu {

    private skills skill = new skills();
    private ratings rating = new ratings();
    private activityGroup agroup = new activityGroup();
    private activityTypes atypes = new activityTypes();
    private activityPriority aprior = new activityPriority();
    private activityCategory acat = new activityCategory();
    private activityStatus astatus = new activityStatus();
    private employeeSkills empSkill = new employeeSkills();
    private activityGroupEmployee empActGrp = new activityGroupEmployee();
    private Departments departments = new Departments();
    private EmployeeTypes employeeTypes = new EmployeeTypes();
    private JobTitles jobTitles = new JobTitles();

    public workFlowSetup() {
        this.setMenuName("Setup");
        this.setMenuList(new WorkFlowActivityAlertsMenu());
        this.setMenuList(acat);
        this.setMenuList(agroup);
        this.setMenuList(empActGrp);
        this.setMenuList(aprior);
        this.setMenuList(astatus);
        this.setMenuList(atypes);
        this.setMenuList(departments);
        this.setMenuList(empSkill);
        this.setMenuList(employeeTypes);
        this.setMenuList(jobTitles);
        this.setMenuList(rating);
        this.setMenuList(skill);

        this.setMenuList(new WorkFlowNumberSequenceMenu());
    }
}
