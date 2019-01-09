/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.workflow.display.workflow;

import emc.app.components.documents.EMCDoubleFormDocument;
import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJScrollPane;
import emc.app.components.emcJSplitPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextArea;
import emc.app.components.emcJTextField;
import emc.app.components.emcMenuButton;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.emctable.emcYesNoComponent;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.tables.EMCFormTablesMapComboBox;
import emc.app.components.workflow.workflowPITableLookup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.base.BaseEmailTemplates;
import emc.entity.base.BaseEmployeeTable;
import emc.entity.base.BaseSMSTemplate;
import emc.entity.base.query.BaseQueryTable;
import emc.entity.workflow.WorkFlowActivityCate;
import emc.entity.workflow.WorkFlowActivityGroups;
import emc.entity.workflow.WorkFlowActivityTypes;
import emc.entity.workflow.WorkFlowSkill;
import emc.enums.modules.enumEMCModules;
import emc.forms.workflow.display.workflow.resources.allDocumentsButton;
import emc.forms.workflow.display.workflow.resources.copyWFButton;
import emc.forms.workflow.display.workflow.resources.createJobButton;
import emc.forms.workflow.display.workflow.resources.evaluateButton;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.DBLogSetupMenu;
import emc.menus.base.menuItems.display.employees;
import emc.menus.workflow.menuitems.display.activityCategory;
import emc.menus.workflow.menuitems.display.activityGroup;
import emc.menus.workflow.menuitems.display.activityTypes;
import emc.menus.workflow.menuitems.display.jobsMaster;
import emc.menus.workflow.menuitems.display.skills;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JSplitPane;

/**
 *
 * @author rico
 */
public class wfMasterForm extends BaseInternalFrame {

    private wfMasterDRM masterDRM;
    private WFLinesDRM linesDRM;
    private EMCUserData masterUD;
    private EMCUserData linesUD;

    public wfMasterForm(EMCUserData userData) {
        super("Work Flow", true, true, true, true, userData);
        this.setBounds(20, 20, 700, 550);
        try {
            masterUD = userData.copyUserDataAndDataList();
            linesUD = userData.copyUserData();
            masterDRM = new wfMasterDRM(new emcGenericDataSourceUpdate(
                    enumEMCModules.WORKFLOW.getId(), new emc.entity.workflow.WorkFlowMaster(), masterUD), masterUD);
            masterDRM.setTheForm(this);
            this.setDataManager(masterDRM);
            masterDRM.setFormTextId1("workFlowId");
            masterDRM.setFormTextId2("workFlowDescription");

            linesDRM = new WFLinesDRM(new emcGenericDataSourceUpdate(
                    enumEMCModules.WORKFLOW.getId(), new emc.entity.workflow.WorkFlowLines(), linesUD), linesUD);
            linesDRM.setTheForm(this);
            linesDRM.setFormTextId1("LineNo");
            linesDRM.setFormTextId2("descriptionOfActivity");

            masterDRM.setLinesTable(linesDRM);
            masterDRM.setHeaderColumnID("workFlowId");
            linesDRM.setHeaderTable(masterDRM);
            linesDRM.setRelationColumnToHeader("workFlowId");

        } catch (Exception ex) {
        }
        initFrame();
    }

    private void initFrame() {
        emcJSplitPane topBottomSplit = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, masterPane(), linesPane());
        topBottomSplit.setDividerLocation(220);
        this.setContentPane(topBottomSplit);
    }

    private emcJPanel masterPane() {
        emcJPanel masterPanel = new emcJPanel(new BorderLayout());
        emcJTabbedPane masterTabbed = new emcJTabbedPane();
        masterTabbed.add("Work Flow Master", masterTablePane());
        masterTabbed.add("Usage", sourcePane());
        masterPanel.add(masterTabbed, BorderLayout.CENTER);
        masterPanel.add(masterButtons(), BorderLayout.EAST);
        return masterPanel;
    }

    private emcTablePanelUpdate masterTablePane() {
        List keys = new ArrayList();
        keys.add("workFlowId");
        keys.add("workFlowDescription");
        keys.add("approvedBy");
        keys.add("approvedDate");
        emcTableModelUpdate model = new emcTableModelUpdate(masterDRM, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        //Lookups
        EMCLookupJTableComponent lookupEmp = new EMCLookupJTableComponent(new employees());
        lookupEmp.setPopup(new EMCLookupPopup(new BaseEmployeeTable(), "employeeNumber", masterUD));
        table.setLookupToColumn("approvedBy", lookupEmp);
        //Lookups
        masterDRM.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        masterDRM.setTablePanel(tableScroll);
        return tableScroll;
    }

    private emcJPanel sourcePane() {
        EMCFormTablesMapComboBox cbSource = new EMCFormTablesMapComboBox(masterDRM, "sourceTable");

        Component[][] comp = {{new emcJLabel("May be used with"), cbSource}};

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Usage");
    }

    private emcJPanel masterButtons() {
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(new copyWFButton("Copy Work Flow", null, this, 0, masterDRM));
        buttonList.add(new createJobButton("Create Task", new jobsMaster(), this, 0, masterDRM));
        buttonList.add(new evaluateButton("Evaluate Lines", null, this, 0, masterDRM));
        buttonList.add(new allDocumentsButton("All Documents WF", masterDRM));
        buttonList.add(new emcMenuButton("Action", new DBLogSetupMenu(), this, 1, false, true));
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    private emcJPanel linesPane() {
        emcJPanel linesPanel = new emcJPanel(new BorderLayout());
        emcJTabbedPane linesTabbed = new emcJTabbedPane();
        linesTabbed.add("Work Flow Lines", linesTablePane());
        linesTabbed.add("Notes", notesPane());
        linesTabbed.add("Work Allocation", allocationPane());
        linesTabbed.add("Costing", costingPane());
        linesPanel.add(linesTabbed, BorderLayout.CENTER);
        return linesPanel;
    }

    private emcTablePanelUpdate linesTablePane() {
        List keys = new ArrayList();
        keys.add("LineNo");
        keys.add("descriptionOfActivity");
        keys.add("primaryIndicator");
        keys.add("activityType");
        keys.add("activityCategory");
        keys.add("skill");
        keys.add("NextLineNo");
        emcTableModelUpdate model = new emcTableModelUpdate(linesDRM, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        linesDRM.setMainTableComponent(table);
        //Lookups
        table.setComboBoxLookupToColumn("primaryIndicator", new workflowPITableLookup());
        EMCLookupJTableComponent lkpTypes = new EMCLookupJTableComponent(new activityTypes());
        lkpTypes.setPopup(new EMCLookupPopup(new WorkFlowActivityTypes(), "activityType", linesUD));
        table.setLookupToColumn("activityType", lkpTypes);
        EMCLookupJTableComponent lkpCategorie = new EMCLookupJTableComponent(new activityCategory());
        lkpCategorie.setPopup(new EMCLookupPopup(new WorkFlowActivityCate(), "activityCategory", linesUD));
        table.setLookupToColumn("activityCategory", lkpCategorie);
        EMCLookupJTableComponent lkpSkills = new EMCLookupJTableComponent(new skills());
        lkpSkills.setPopup(new EMCLookupPopup(new WorkFlowSkill(), "skill", linesUD));
        table.setLookupToColumn("skill", lkpSkills);
        //Lookups
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        return tableScroll;
    }

    private emcJPanel notesPane() {
        //Internal
        emcJPanel intNotes = new emcJPanel();
        intNotes.setBorder(javax.swing.BorderFactory.createTitledBorder("Internal Notes"));
        intNotes.setLayout(new GridLayout(1, 1));
        emcJTextArea internalNotes = new emcJTextArea(new EMCStringFormDocument(linesDRM, "internalNotes"));
        emcJScrollPane internalNotesScroll = new emcJScrollPane(internalNotes);
        intNotes.add(internalNotesScroll);
        //External
        emcJPanel extNotes = new emcJPanel();
        extNotes.setBorder(javax.swing.BorderFactory.createTitledBorder("External Notes"));
        extNotes.setLayout(new GridLayout(1, 1));
        emcJTextArea externalNotes = new emcJTextArea(new EMCStringFormDocument(linesDRM, "externalNotes"));
        emcJScrollPane externalNoteScroll = new emcJScrollPane(externalNotes);
        extNotes.add(externalNoteScroll);
        //Notes
        emcJPanel notes = new emcJPanel(new GridLayout(2, 1));
        notes.setBorder(javax.swing.BorderFactory.createTitledBorder("Notes"));
        notes.add(intNotes);
        notes.add(extNotes);
        return notes;
    }

    private emcJPanel allocationPane() {
        EMCLookupFormComponent lkpManager = new EMCLookupFormComponent(new employees(), linesDRM, "managerResponsible");
        lkpManager.setPopup(new EMCLookupPopup(new BaseEmployeeTable(), "employeeNumber", linesUD));

        EMCLookupFormComponent lkpEmployee = new EMCLookupFormComponent(new employees(), linesDRM, "employeeId");
        lkpEmployee.setPopup(new EMCLookupPopup(new BaseEmployeeTable(), "employeeNumber", linesUD));

        EMCLookupFormComponent lkpActGroup = new EMCLookupFormComponent(new activityGroup(), linesDRM, "activityGroup");
        lkpActGroup.setPopup(new EMCLookupPopup(new WorkFlowActivityGroups(), "activityGroup", linesUD));

        emcJTextField txtSource = new emcJTextField(new EMCStringFormDocument(linesDRM, "referenceSource"));
        txtSource.setEditable(false);

        emcYesNoComponent managerClosesAct = new emcYesNoComponent(linesDRM, "closedByManager");

        Component[][] comp = {{new emcJLabel("Manager Responsible"), lkpManager},
                              {new emcJLabel("Employee Responsible"), lkpEmployee},
                              {new emcJLabel("Activity Group"), lkpActGroup},
                              {new emcJLabel("Reference Source"), txtSource},
                              {new emcJLabel("Manager Closes Activity"), managerClosesAct}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Work Allocation");
    }

    private emcJPanel costingPane() {
        emcJTextField txtLeadTime = new emcJTextField(new EMCDoubleFormDocument(linesDRM, "leadTime"));

        emcJTextField txtDuration = new emcJTextField(new EMCDoubleFormDocument(linesDRM, "duration"));

        emcJTextField txtEstimated = new emcJTextField(new EMCDoubleFormDocument(linesDRM, "HoursWorkEstimated"));

        emcYesNoComponent billable = new emcYesNoComponent(linesDRM, "billable");

        emcJTextField txtCompletionRules = new emcJTextField(new EMCStringFormDocument(linesDRM, "completionRules"));

        emcYesNoComponent outputFile = new emcYesNoComponent(linesDRM, "outputFileRequired");

        Component[][] comp = {{new emcJLabel("Lead Time (Days)"), txtLeadTime},
                              {new emcJLabel("Duration (Days)"), txtDuration},
                              {new emcJLabel("Estimated Hours"), txtEstimated},
                              {new emcJLabel("Billable"), billable},
                              {new emcJLabel("Completion Rules"), txtCompletionRules},
                              {new emcJLabel("Output File Req."), outputFile}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Costing");
    }

    
}
