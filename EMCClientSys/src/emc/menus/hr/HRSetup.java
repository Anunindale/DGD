/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.hr;

import emc.framework.EMCMenu;
import emc.menus.hr.menuitems.display.HRAbsenteeismTypeMenu;
import emc.menus.hr.menuitems.display.HRActionResultsMenu;
import emc.menus.hr.menuitems.display.HRAlternativeIdTypeMI;
import emc.menus.hr.menuitems.display.HRBranchMenu;
import emc.menus.hr.menuitems.display.HRCitizenStatusMI;
import emc.menus.hr.menuitems.display.HRCourseMenu;
import emc.menus.hr.menuitems.display.HRDisabilityTypesMenu;
import emc.menus.hr.menuitems.display.HRDisiplinaryLevelMenu;
import emc.menus.hr.menuitems.display.HREducationLevelMI;
import emc.menus.hr.menuitems.display.HREducationPriorityMenu;
import emc.menus.hr.menuitems.display.HREmploymentTypeMenu;
import emc.menus.hr.menuitems.display.HREquityCodeMI;
import emc.menus.hr.menuitems.display.HRExperienceLevelMI;
import emc.menus.hr.menuitems.display.HRGradeMenu;
import emc.menus.hr.menuitems.display.HRInstitutionMenu;
import emc.menus.hr.menuitems.display.HRJobCatagoryMenu;
import emc.menus.hr.menuitems.display.HRJobLevelMenu;
import emc.menus.hr.menuitems.display.HRJobStatusMI;
import emc.menus.hr.menuitems.display.HRLeaveTypeMenu;
import emc.menus.hr.menuitems.display.HRLevelOfExperienceMI;
import emc.menus.hr.menuitems.display.HRMedicalAidMenu;
import emc.menus.hr.menuitems.display.HRMedicalLogMenu;
import emc.menus.hr.menuitems.display.HRNationalityMenu;
import emc.menus.hr.menuitems.display.HROFOCodeMenu;
import emc.menus.hr.menuitems.display.HRQualificationMenu;
import emc.menus.hr.menuitems.display.HRRaceMenu;
import emc.menus.hr.menuitems.display.HRRemunerantionTypeMI;
import emc.menus.hr.menuitems.display.HRScarcePriorityMI;
import emc.menus.hr.menuitems.display.HRSectionMenu;
import emc.menus.hr.menuitems.display.HRSocioEcoStatusMI;
import emc.menus.hr.menuitems.display.HRTerminationTypeMenu;
import emc.menus.hr.menuitems.display.HRUnionsMenu;
import emc.menus.workflow.menuitems.display.Departments;
import emc.menus.workflow.menuitems.display.JobTitles;
import emc.menus.workflow.menuitems.display.ratings;
import emc.menus.workflow.menuitems.display.skills;

/**
 *
 * @author wikus
 */
public class HRSetup extends EMCMenu {

    /** Creates a new instance of HRSetup*/
    public HRSetup() {
        this.setMenuName("Setup");
        this.setMenuList(new HRAccessGroups());
        this.setMenuList(new HRCoursesMenu());
        this.setMenuList(new HRActionResultsMenu());
        this.setMenuList(new HRAlternativeIdTypeMI());
        this.setMenuList(new HRDisiplinaryLevelMenu());
        this.setMenuList(new HRDisabilityTypesMenu());
        this.setMenuList(new HRCitizenStatusMI());
        this.setMenuList(new HRNationalityMenu());
        this.setMenuList(new HREducationLevelMI());
        this.setMenuList(new HREmploymentTypeMenu());
        this.setMenuList(new HREquityCodeMI());
        this.setMenuList(new HRExperienceLevelMI());
        this.setMenuList(new HRRaceMenu());
        this.setMenuList(new HRGradeMenu());
        this.setMenuList(new HRInstitutionMenu());
        this.setMenuList(new HRJobStatusMI());
        this.setMenuList(new HRLeaveTypeMenu());
        this.setMenuList(new HRLevelOfExperienceMI());
        this.setMenuList(new HRTerminationTypeMenu());
        this.setMenuList(new HRSectionMenu());
        this.setMenuList(new HRBranchMenu());
        this.setMenuList(new HRCourseMenu());
        this.setMenuList(new HRQualificationMenu());
        this.setMenuList(new HRMedicalAidMenu());
        this.setMenuList(new HRMedicalLogMenu());
        this.setMenuList(new Departments());
        this.setMenuList(new skills());
        this.setMenuList(new HRSocioEcoStatusMI());
        this.setMenuList(new ratings());
        this.setMenuList(new JobTitles());
        this.setMenuList(new HRUnionsMenu());
        this.setMenuList(new HREducationPriorityMenu());
        this.setMenuList(new HRRemunerantionTypeMI());
        this.setMenuList(new HRJobLevelMenu());
        this.setMenuList(new HRJobCatagoryMenu());
        this.setMenuList(new HROFOCodeMenu());
        this.setMenuList(new HRAbsenteeismTypeMenu());
        this.setMenuList(new HRScarcePriorityMI());
    }
}
