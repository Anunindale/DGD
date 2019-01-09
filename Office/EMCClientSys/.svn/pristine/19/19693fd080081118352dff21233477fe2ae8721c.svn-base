/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.workflow.display.skillsenquiry;

import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponent;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponentDRM;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.workflow.WorkFlowEmployeeSkills;
import emc.entity.workflow.WorkFlowSkill;
import emc.entity.workflow.WorkFlowSkillsEnquiry;
import emc.framework.EMCUserData;
import emc.menus.workflow.menuitems.display.skills;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wikus
 */
public class WorkFlowSkillEnquiryForm extends BaseInternalFrame {

    private EMCControlLookupComponentDRM dataManager;
    private EMCUserData userData;

    public WorkFlowSkillEnquiryForm(EMCUserData userData) {
        super("Skills Enquiry", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        this.userData = userData.copyUserDataAndDataList();
        this.dataManager = new EMCControlLookupComponentDRM(new emcGenericDataSourceUpdate(new WorkFlowSkillsEnquiry(), userData), userData) {

            @Override
            public void insertRowCache(int rowIndex, boolean addRowAfter) {
                Logger.getLogger("emc").log(Level.SEVERE, "You are not allowed to add rows to this table.", WorkFlowSkillEnquiryForm.this.userData);
            }

            @Override
            public void deleteRowCache(int rowIndex) {
                Logger.getLogger("emc").log(Level.SEVERE, "You are not allowed to remove rows from this table.", WorkFlowSkillEnquiryForm.this.userData);
            }
        };
        dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        dataManager.setFormTextId1("skill");
        dataManager.setFormTextId2("employeeName");
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Employees", tablePane());

        emcJPanel mainPanel = new emcJPanel(new BorderLayout());
        mainPanel.add(selectionPane(), BorderLayout.NORTH);
        mainPanel.add(tabbed, BorderLayout.CENTER);

        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(mainPanel, BorderLayout.CENTER);
        this.setContentPane(contentPane);
    }

    private emcJPanel selectionPane() {
        emcJTextField txtSkillsDescription = new emcJTextField();
        txtSkillsDescription.setEditable(false);
        EMCControlLookupComponent lkpSkill = new EMCControlLookupComponent(new skills(), dataManager, "skill", txtSkillsDescription, "description", WorkFlowEmployeeSkills.class.getName());
        lkpSkill.setPopup(new EMCLookupPopup(new WorkFlowSkill(), "skill", userData));
        dataManager.setLookup(lkpSkill);
        Component[][] comp = {{new emcJLabel("Skill"), lkpSkill, new emcJLabel("Description"), txtSkillsDescription}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Skills");
    }

    private emcTablePanelUpdate tablePane() {
        List<String> keys = new ArrayList<String>();
        keys.add("employeeNumber");
        keys.add("employeeName");
        keys.add("rating");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataManager.setTablePanel(tableScroll);
        return tableScroll;
    }
}
