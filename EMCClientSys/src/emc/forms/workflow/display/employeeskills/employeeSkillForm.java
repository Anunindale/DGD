/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.workflow.display.employeeskills;

import emc.app.components.emcHelpFile;
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
import emc.entity.workflow.WorkFlowEmployeeSkills;
import emc.entity.workflow.WorkFlowRating;
import emc.entity.workflow.WorkFlowSkill;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import emc.menus.workflow.menuitems.display.ratings;
import emc.menus.workflow.menuitems.display.skills;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rico
 */
public class employeeSkillForm extends BaseInternalFrame {

    private EMCControlLookupComponentDRM dataRelation;
    private EMCUserData userData;

    public employeeSkillForm(EMCUserData userData) {
        super("Employee Skills", true, true, true, true, userData);
        this.setHelpFile(new emcHelpFile("WorkFlow/WorkFlowEmployeeSkills.html"));
        this.setBounds(20, 20, 650, 320);
        try {
            this.userData = userData.copyUserDataAndDataList();
            dataRelation = new EMCControlLookupComponentDRM(new emcGenericDataSourceUpdate(enumEMCModules.WORKFLOW.getId(), new WorkFlowEmployeeSkills(), userData), userData);
            this.setDataManager(dataRelation);
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("employeeNumber");
            dataRelation.setFormTextId2("skill");
        } catch (Exception ex) {
        }
        initFrame();
    }

    private void initFrame() {
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(new HREmployeeControllLookupPanel(WorkFlowEmployeeSkills.class.getName(), "employeeNumber", dataRelation, userData), BorderLayout.NORTH);
        contentPane.add(tabbedPane(), BorderLayout.CENTER);
        this.setContentPane(contentPane);
    }

    private emcJTabbedPane tabbedPane() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Skills", tablePane());
        return tabbed;
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("skill");
        keys.add("rating");
        keys.add("ratingDate");
        emcTableModelUpdate model = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        setupLookups(table);
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        this.setTablePanel(tableScroll);
        return tableScroll;
    }

    private void setupLookups(emcJTableUpdate table) {
        EMCLookupJTableComponent lkpSkill = new EMCLookupJTableComponent(new skills());
        lkpSkill.setPopup(new EMCLookupPopup(new WorkFlowSkill(), "skill", userData));
        table.setLookupToColumn("skill", lkpSkill);

        EMCLookupJTableComponent lkpRating = new EMCLookupJTableComponent(new ratings());
        lkpRating.setPopup(new EMCLookupPopup(new WorkFlowRating(), "rating", userData));
        table.setLookupToColumn("rating", lkpRating);
    }
}
