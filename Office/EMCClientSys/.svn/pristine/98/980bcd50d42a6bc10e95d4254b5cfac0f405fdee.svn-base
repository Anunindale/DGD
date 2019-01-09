/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.hr.display.training;

import emc.app.components.emcJComboBox;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponentDRM;
import emc.app.components.hr.HREmployeeControllLookupPanel;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.hr.HRCourses;
import emc.entity.hr.HREmployeeTraining;
import emc.entity.hr.HRInstitution;
import emc.entity.workflow.WorkFlowRating;
import emc.entity.workflow.WorkFlowSkill;
import emc.enums.hr.HRTrainingStatuses;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import emc.menus.hr.menuitems.display.HRCourseMenu;
import emc.menus.hr.menuitems.display.HRInstitutionMenu;
import emc.menus.workflow.menuitems.display.ratings;
import emc.menus.workflow.menuitems.display.skills;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class HREmployeeTrainingForm extends BaseInternalFrame {

    private EMCControlLookupComponentDRM dataRelation;
    private EMCUserData userData;

    public HREmployeeTrainingForm(EMCUserData userData) {
        super("Employee Training", true, true, true, true, userData);
        this.setBounds(20, 20, 700, 320);
        try {
            this.userData = userData.copyUserDataAndDataList();
            dataRelation = new EMCControlLookupComponentDRM(new emcGenericDataSourceUpdate(enumEMCModules.HR.getId(), new HREmployeeTraining(), userData), userData);
            this.setDataManager(dataRelation);
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("employeeNumber");
            dataRelation.setFormTextId2("institution");
        } catch (Exception ex) {
        }
        initFrame();
    }

    private void initFrame() {
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(new HREmployeeControllLookupPanel(HREmployeeTraining.class.getName(), "employeeNumber", dataRelation, userData), BorderLayout.NORTH);
        contentPane.add(tabbedPane(), BorderLayout.CENTER);
        this.setContentPane(contentPane);
    }

    private emcJTabbedPane tabbedPane() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Training", tablePane());
        return tabbed;
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("institution");
        keys.add("course");
        keys.add("skill");
        keys.add("courseCost");
        keys.add("dateStarted");
        keys.add("dateCompleted");
        keys.add("rating");
        keys.add("certificate");
        keys.add("status");
        emcTableModelUpdate model = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        setupLookups(table);
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        this.setTablePanel(tableScroll);
        return tableScroll;
    }

    private void setupLookups(emcJTableUpdate table) {
        EMCLookupJTableComponent lkpInstitution = new EMCLookupJTableComponent(new HRInstitutionMenu());
        lkpInstitution.setPopup(new EMCLookupPopup(new HRInstitution(), "institution", userData));
        table.setLookupToColumn("institution", lkpInstitution);

        EMCLookupJTableComponent lkpCourse = new EMCLookupJTableComponent(new HRCourseMenu());
        lkpCourse.setPopup(new EMCLookupPopup(new HRCourses(), "course", userData));
        table.setLookupToColumn("course", lkpCourse);

        EMCLookupJTableComponent lkpSkill = new EMCLookupJTableComponent(new skills());
        lkpSkill.setPopup(new EMCLookupPopup(new WorkFlowSkill(), "skill", userData));
        table.setLookupToColumn("skill", lkpSkill);

        EMCLookupJTableComponent lkpRating = new EMCLookupJTableComponent(new ratings());
        lkpRating.setPopup(new EMCLookupPopup(new WorkFlowRating(), "rating", userData));
        table.setLookupToColumn("rating", lkpRating);

        table.setComboBoxLookupToColumn("status", new emcJComboBox(HRTrainingStatuses.values()));
    }
}
