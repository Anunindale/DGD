/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.workflow.display.jobs;

import emc.app.components.documents.EMCDoubleFormDocument;
import emc.app.components.emcHelpFile;
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
import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.emctable.emcYesNoComponent;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.jtablelookup.EMCLookupTableCellEditor;
import emc.app.components.queryswitchbutton.EMCQuerySwitchButton;
import emc.app.components.workflow.workflowPITableLookup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.base.BaseEmailTemplates;
import emc.entity.base.BaseSMSTemplate;
import emc.entity.base.query.BaseQueryTable;
import emc.entity.workflow.WorkFlowJobMaster;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.modules.enumEMCModules;
import emc.forms.workflow.display.jobs.resources.allDocumentsButton;
import emc.forms.workflow.display.jobs.resources.createActivityButton;
import emc.forms.workflow.display.jobs.resources.evaluateButton;
import emc.forms.workflow.display.jobs.resources.redoRangeButton;
import emc.framework.EMCMenuItem;
import emc.framework.EMCUserData;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JSplitPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

/**
 *
 * @author rico
 */
public class JobsMasterForm extends BaseInternalFrame {

    private emcJPanel jobMasterPanel = new emcJPanel();
    private emcJPanel jobLinesPanel = new emcJPanel();
    private emcJTableUpdate toptable;
    private emcJPanel notes = new emcJPanel();
    private emcJPanel costingPanel = new emcJPanel();
    private emcJPanel repPanel = new emcJPanel();
    //notes tab
    private emcJTextArea internalNotes = new emcJTextArea();
    private emcJTextArea externalNotes = new emcJTextArea();
    //work allocation tab
    private emcJTextField referenceSource = new emcJTextField();
    private emcYesNoComponent managerClosesAct;
    //costing tab
    private emcJTextField duration = new emcJTextField();
    private emcJTextField extimatedHr = new emcJTextField();
    private emcYesNoComponent billable;
    private emcJTextField completionRules = new emcJTextField();
    private emcYesNoComponent outputFile;
    //buttons
    private emcJPanel buttonsPanel = new emcJPanel();
    private emcJPanel buttonsPanelBot = new emcJPanel();
    //DataSource
    private jobsMasterDRM dataRelation;
    //lookup
    private EMCLookupJTableComponent managerResp;
    private EMCLookupTableCellEditor manRespEditor;
    private EMCLookupJTableComponent lookupStatus;
    private EMCLookupJTableComponent lookupStatusLines;
    private EMCLookupTableCellEditor statusEditor;
    private EMCLookupFormComponent linesEmployeeId;
    private EMCLookupFormComponent linesManagerResp;
    private EMCLookupFormComponent lookupskill;
    private EMCLookupFormComponent lookupTypes;
    private EMCLookupFormComponent lookupCat;
    private EMCLookupFormComponent linesActGrp;
    private jobsLinesDRM linesDataRelation;

    public JobsMasterForm(EMCUserData userData) {
        super("Task Master", true, true, true, true, userData);
        this.setBounds(20, 20, 750, 550);
        this.setHelpFile(new emcHelpFile("WorkFlow/WorkFlowDesignJobs.html"));
        try {
            EMCUserData copyUD = userData.copyUserDataAndDataList();
            userData = userData.copyUserData();

            //JobsMaster
            dataRelation = new jobsMasterDRM(new emcGenericDataSourceUpdate(
                    enumEMCModules.WORKFLOW.getId(), new emc.entity.workflow.WorkFlowJobMaster(), copyUD), copyUD);
            this.setDataManager(dataRelation);

            //lines
            linesDataRelation = new jobsLinesDRM(new emcGenericDataSourceUpdate(
                    enumEMCModules.WORKFLOW.getId(), new emc.entity.workflow.WorkFlowJobLines(), userData), userData);

            //status
            List statusKeys = new ArrayList();
            statusKeys.add("activityStatus");
            statusKeys.add("description");

            EMCLookupPopup statusPopup = new EMCLookupPopup(enumEMCModules.WORKFLOW.getId(), new emc.entity.workflow.WorkFlowActivityStatus(),
                                                            "activityStatus", statusKeys, copyUD);
            EMCMenuItem statusItem = new emc.menus.workflow.menuitems.display.activityStatus();
            lookupStatus = new EMCLookupJTableComponent(statusItem);
            lookupStatusLines = new EMCLookupJTableComponent(statusItem);
            lookupStatus.setPopup(statusPopup);
            lookupStatusLines.setPopup(statusPopup);
            statusEditor = new EMCLookupTableCellEditor(lookupStatus);
            //emp
            List employeeKeys = new ArrayList();
            employeeKeys.add("employeeNumber");
            employeeKeys.add("surname");
            employeeKeys.add("forenames");
            employeeKeys.add("emailAddress");
            employeeKeys.add("internalPhoneNumber");

            EMCLookupPopup empPopup = new EMCLookupPopup(enumEMCModules.BASE.getId(), new emc.entity.base.BaseEmployeeTable(),
                                                         "employeeNumber", employeeKeys, copyUD);
            EMCMenuItem empItem = new emc.menus.base.menuItems.display.employees();
            managerResp = new EMCLookupJTableComponent(empItem);
            managerResp.setPopup(empPopup);
            manRespEditor = new EMCLookupTableCellEditor(managerResp);
            linesManagerResp = new EMCLookupFormComponent(empItem,
                                                          linesDataRelation, "managerResponsible");
            linesManagerResp.setPopup(empPopup);
            linesDataRelation.setDocument(linesManagerResp.getDocument());
            linesEmployeeId = new EMCLookupFormComponent(empItem,
                                                         linesDataRelation, "employeeId");
            linesEmployeeId.setPopup(empPopup);
            linesDataRelation.setDocument(linesEmployeeId.getDocument());
            //skill
            List skillkeys = new ArrayList();
            skillkeys.add("skill");
            skillkeys.add("description");
            lookupskill = new EMCLookupFormComponent(new emc.menus.workflow.menuitems.display.skills(),
                                                     linesDataRelation, "skill");
            EMCLookupPopup skillPopup = new EMCLookupPopup(enumEMCModules.WORKFLOW.getId(), new emc.entity.workflow.WorkFlowSkill(),
                                                           "skill", skillkeys, copyUD);
            lookupskill.setPopup(skillPopup);
            linesDataRelation.setDocument(lookupskill.getDocument());
            //type
            List typeKeys = new ArrayList();
            typeKeys.add("activityType");
            typeKeys.add("description");
            lookupTypes = new EMCLookupFormComponent(new emc.menus.workflow.menuitems.display.activityTypes(),
                                                     linesDataRelation, "activityType");
            EMCLookupPopup typePopup = new EMCLookupPopup(enumEMCModules.WORKFLOW.getId(), new emc.entity.workflow.WorkFlowActivityTypes(),
                                                          "activityType", typeKeys, copyUD);
            lookupTypes.setPopup(typePopup);
            linesDataRelation.setDocument(lookupTypes.getDocument());
            //categories
            List catKeys = new ArrayList();
            catKeys.add("activityCategory");
            catKeys.add("description");
            lookupCat = new EMCLookupFormComponent(new emc.menus.workflow.menuitems.display.activityCategory(),
                                                   linesDataRelation, "activityCategory");
            EMCLookupPopup catPopup = new EMCLookupPopup(enumEMCModules.WORKFLOW.getId(), new emc.entity.workflow.WorkFlowActivityCate(),
                                                         "activityCategory", catKeys, copyUD);
            lookupCat.setPopup(catPopup);
            linesDataRelation.setDocument(lookupCat.getDocument());
            //group
            List groupkeys = new ArrayList();
            groupkeys.add("activityGroup");
            groupkeys.add("description");
            linesActGrp = new EMCLookupFormComponent(new emc.menus.workflow.menuitems.display.activityGroup(),
                                                     linesDataRelation, "activityGroup");
            EMCLookupPopup groupPopup = new EMCLookupPopup(enumEMCModules.WORKFLOW.getId(), new emc.entity.workflow.WorkFlowActivityGroups(),
                                                           "activityGroup", groupkeys, copyUD);
            linesActGrp.setPopup(groupPopup);
            linesDataRelation.setDocument(linesActGrp.getDocument());

            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("designNo");
            dataRelation.setFormTextId2("workFlowId");
            dataRelation.setLinesTable(linesDataRelation);
            linesDataRelation.setTheForm(this);
            linesDataRelation.setFormTextId1("lineNo");
            linesDataRelation.setFormTextId2("descriptionOfActivity");
        } catch (Exception e) {
            e.printStackTrace();
        }
        dataRelationsMaster();
        relationSetup();
        initFrame();
    }

    private void relationSetup() {
        this.internalNotes.setDocument(new EMCStringFormDocument(linesDataRelation, "internalNotes"));
        this.externalNotes.setDocument(new EMCStringFormDocument(linesDataRelation, "externalNotes"));
        duration.setDocument(new EMCDoubleFormDocument(linesDataRelation, "duration"));
        this.extimatedHr.setDocument(new EMCDoubleFormDocument(linesDataRelation, "hoursWorkEstimated"));
        this.billable = new emcYesNoComponent(linesDataRelation, "billable");
        this.managerClosesAct = new emcYesNoComponent(linesDataRelation, "closedByManager");
        this.completionRules.setDocument(new EMCStringFormDocument(linesDataRelation, "completionRules"));
        this.outputFile = new emcYesNoComponent(linesDataRelation, "outputFileRequired");
        this.referenceSource.setDocument(new EMCStringFormDocument(linesDataRelation, "referenceSource"));
        referenceSource.setEditable(false);
    }

    private void notesTab() {
        this.notes.setLayout(new GridLayout(2, 1));
        notes.setBorder(javax.swing.BorderFactory.createTitledBorder("Notes"));
        emcJPanel intNotes = new emcJPanel();
        intNotes.setBorder(javax.swing.BorderFactory.createTitledBorder("Internal Notes"));
        intNotes.setLayout(new GridLayout(1, 1));
        emcJScrollPane internalNotesScroll = new emcJScrollPane(this.internalNotes);
        intNotes.add(internalNotesScroll);
        //
        emcJPanel extNotes = new emcJPanel();
        extNotes.setBorder(javax.swing.BorderFactory.createTitledBorder("External Notes"));
        extNotes.setLayout(new GridLayout(1, 1));
        emcJScrollPane externalNoteScroll = new emcJScrollPane(this.externalNotes);
        extNotes.add(externalNoteScroll);
        //
        notes.add(intNotes);
        notes.add(extNotes);
    }

    private void responsibleTab() {
        repPanel.setLayout(new GridBagLayout());
        repPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Work Allocation"));
        int y = 0;
        GridBagConstraints localg;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        repPanel.add(new emcJLabel("Manager Responsible"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.1);
        repPanel.add(this.linesManagerResp, localg);
        localg = emcSetGridBagConstraints.createStandard(2, y, 0.1, GridBagConstraints.LINE_START);
        repPanel.add(new emcJLabel("Manager Closes Activity"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 3, y, 0.9);
        repPanel.add(this.managerClosesAct, localg);
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        repPanel.add(new emcJLabel("Employee Responsible"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        repPanel.add(this.linesEmployeeId, localg);
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        repPanel.add(new emcJLabel("Activity Group"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        repPanel.add(this.linesActGrp, localg);
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        repPanel.add(new emcJLabel("Reference Source"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        repPanel.add(this.referenceSource, localg);
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        repPanel.add(new emcJLabel("Required Skill"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        repPanel.add(this.lookupskill, localg);
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        repPanel.add(new emcJLabel("Type"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        repPanel.add(this.lookupTypes, localg);
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        repPanel.add(new emcJLabel("Category"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        repPanel.add(this.lookupCat, localg);
        //end of panel
        y++;
        repPanel.add(new emcJLabel(), emcSetGridBagConstraints.endPanel(y));

    }

    private void costingTab() {
        costingPanel.setLayout(new GridBagLayout());
        costingPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Costing"));
        int y = 0;
        GridBagConstraints localg;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        costingPanel.add(new emcJLabel("Lead Time"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        costingPanel.add(new emcJTextField(new EMCDoubleFormDocument(linesDataRelation, "leadTime")), localg);
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        costingPanel.add(new emcJLabel("Duration"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        costingPanel.add(duration, localg);
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        costingPanel.add(new emcJLabel("Estimated Hours"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        costingPanel.add(this.extimatedHr, localg);
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        costingPanel.add(new emcJLabel("Billable"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        costingPanel.add(this.billable, localg);
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        costingPanel.add(new emcJLabel("Completion Rules"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        costingPanel.add(this.completionRules, localg);
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        costingPanel.add(new emcJLabel("Output File Req."), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        costingPanel.add(this.outputFile, localg);
        y++;
        costingPanel.add(new emcJLabel(), emcSetGridBagConstraints.endPanel(y));
    }

    private void tabJobLines() {
        List keys = new ArrayList();
        keys.add("overdueFlag");
        keys.add("cFlag");
        keys.add("lineNo");
        keys.add("descriptionOfActivity");
        keys.add("primaryIndicator");
        keys.add("status");
        keys.add("requiredCompletionDate");
        keys.add("nextLineNo");
        emcTableModelUpdate m = new emcTableModelUpdate(this.linesDataRelation, keys) {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                if (columnIndex == 0) {
                    return false;
                }
                if (columnIndex == 1) {
                    return false;
                }
                if (columnIndex == 8) {
                    return false;
                }
                return true;
            }

            @Override
            public Class getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Boolean.class;
                }
                if (columnIndex == 1) {
                    return Boolean.class;
                }
                if (columnIndex == 8) {
                    return Boolean.class;
                }
                return super.getColumnClass(columnIndex);
            }

            @Override
            public String getColumnName(int columnIndex) {
                if (columnIndex == 0) {
                    return "O";
                }
                if (columnIndex == 1) {
                    return "C";
                }
                if (columnIndex == 8) {
                    return "D";
                }
                return super.getColumnName(columnIndex);
            }

            @Override
            public int getColumnWidth(int columnIndex) {
                if (columnIndex == 0) {
                    return 5;
                }
                if (columnIndex == 1) {
                    return 5;
                }
                if (columnIndex == 8) {
                    return 5;
                }
                return super.getColumnWidth(columnIndex);
            }
        };

        emcJTableUpdate bottomtable = new emcJTableUpdate(m);

        bottomtable.setLookupCellEditor(5, statusEditor);
        linesDataRelation.setHeaderTable(dataRelation);
        linesDataRelation.setRelationColumnToHeader("designNo");
        bottomtable.setComboBoxLookupToColumn(4, new workflowPITableLookup());
        emcTablePanelUpdate bottomscroll = new emcTablePanelUpdate(bottomtable);
        linesDataRelation.setMainTableComponent(bottomtable);
        jobLinesPanel.setLayout(new GridLayout(1, 1));
        jobLinesPanel.add(bottomscroll);

    }

    private void dataRelationsMaster() {
        dataRelation.setHeaderColumnID("designNo");
    }

    private void tabJobMaster() {
        List keys = new ArrayList();
        keys.add("designNo");
        keys.add("workFlowId");
        keys.add("description");
        keys.add("startedBy");
        keys.add("managerResponsible");
        keys.add("startedDate");
        keys.add("targetCompletionDate");
        keys.add("status");
        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation, keys) {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                if (columnIndex == 3) {
                    return false;
                }
                if (columnIndex == 1) {
                    return false;
                }
                return true;
            }
        };
        toptable = new emcJTableUpdate(m);
        toptable.setLookupCellEditor(4, manRespEditor);
        toptable.setLookupCellEditor(7, statusEditor);
        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        jobMasterPanel.setLayout(new GridLayout(1, 1));
        jobMasterPanel.add(topscroll);
        this.setTablePanel(topscroll);
    }

    private void initButtonsTop() {
        buttonsPanel.setLayout(new GridBagLayout());
        buttonsPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        int y = 0;
        GridBagConstraints localg;
        localg = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.LINE_START);
        localg.fill = GridBagConstraints.HORIZONTAL;
        localg.gridwidth = GridBagConstraints.REMAINDER;
        buttonsPanel.add(new allDocumentsButton("All Documents Task", this.dataRelation), localg);
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.LINE_START);
        localg.fill = GridBagConstraints.HORIZONTAL;
        localg.gridwidth = GridBagConstraints.REMAINDER;
        buttonsPanel.add(new evaluateButton("Evaluate Lines", null, this, 0, dataRelation), localg);
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.LINE_START);
        localg.fill = GridBagConstraints.HORIZONTAL;
        localg.gridwidth = GridBagConstraints.REMAINDER;
        buttonsPanel.add(new emcMenuButton("View Task Activity", new emc.menus.workflow.menuitems.display.activityMain(), this, 0, false), localg);
        y++;

        localg = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.LINE_START);
        localg.fill = GridBagConstraints.HORIZONTAL;
        localg.gridwidth = GridBagConstraints.REMAINDER;
        EMCQuerySwitchButton querySwitchButton = new EMCQuerySwitchButton("Open", dataRelation);
        querySwitchButton.addQuery("Open", WorkFlowJobMaster.class.getName(), "completed", EMCQueryConditions.IS_NULL, null);
        querySwitchButton.addQuery("All", WorkFlowJobMaster.class.getName(), "completed", null, null);
        buttonsPanel.add(querySwitchButton, localg);
        y++;

        buttonsPanel.add(new emcJLabel(), emcSetGridBagConstraints.endPanel(y));
    }

    private void initButtonsBottom() {
        buttonsPanelBot.setLayout(new GridBagLayout());
        buttonsPanelBot.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        int y = 0;
        GridBagConstraints localg;
        localg = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.LINE_START);
        localg.fill = GridBagConstraints.HORIZONTAL;
        localg.gridwidth = GridBagConstraints.REMAINDER;
        buttonsPanelBot.add(new redoRangeButton("Redo Range", this.linesDataRelation, this.dataRelation), localg);
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.LINE_START);
        localg.fill = GridBagConstraints.HORIZONTAL;
        localg.gridwidth = GridBagConstraints.REMAINDER;
        buttonsPanelBot.add(new emcMenuButton("Reallocate Range", null, this, 0, true), localg);
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.LINE_START);
        localg.fill = GridBagConstraints.HORIZONTAL;
        localg.gridwidth = GridBagConstraints.REMAINDER;
        buttonsPanelBot.add(new emcMenuButton("Go to Activity", new emc.menus.workflow.menuitems.display.activityMain(), this, 0, true), localg);
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.LINE_START);
        localg.fill = GridBagConstraints.HORIZONTAL;
        localg.gridwidth = GridBagConstraints.REMAINDER;
        buttonsPanelBot.add(new createActivityButton("Create Activity", this.linesDataRelation, this.dataRelation), localg);
        y++;
        buttonsPanelBot.add(new emcJLabel(), emcSetGridBagConstraints.endPanel(y));
    }

    private void initFrame() {
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabbedPanetop.setRelationManager(dataRelation);
        emcJTabbedPane tabbedPaneBottom = new emcJTabbedPane();
        tabbedPaneBottom.setRelationManager(linesDataRelation);
        emcJPanel topPanel = new emcJPanel();
        topPanel.setLayout(new GridBagLayout());
        int y = 0;
        GridBagConstraints localg;

        emcJPanel bottomPanel = new emcJPanel();
        bottomPanel.setLayout(new GridBagLayout());
        //setup top panel
        tabJobMaster();
        initButtonsTop();
        tabbedPanetop.add("Task Master", this.jobMasterPanel);
        //setup for buttons and panel
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.99, GridBagConstraints.FIRST_LINE_START);
        localg.weighty = 1.0;
        localg.fill = GridBagConstraints.BOTH;
        topPanel.add(tabbedPanetop, localg);
        localg = emcSetGridBagConstraints.createStandard(1, y, 0.01, GridBagConstraints.FIRST_LINE_START);
        localg.weighty = 1.0;
        localg.fill = GridBagConstraints.BOTH;
        topPanel.add(buttonsPanel, localg);
        //setup bottom panel
        tabJobLines();
        notesTab();
        costingTab();
        responsibleTab();
        tabbedPaneBottom.add("Task Lines", this.jobLinesPanel);
        tabbedPaneBottom.add("Notes", notes);
        tabbedPaneBottom.add("Work Allocation", repPanel);
        tabbedPaneBottom.add("Costing", costingPanel);
        initButtonsBottom();
        //setup for buttons and panel
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.99, GridBagConstraints.FIRST_LINE_START);
        localg.weighty = 1.0;
        localg.fill = GridBagConstraints.BOTH;
        bottomPanel.add(tabbedPaneBottom, localg);
        localg = emcSetGridBagConstraints.createStandard(1, y, 0.01, GridBagConstraints.FIRST_LINE_START);
        localg.weighty = 1.0;
        localg.fill = GridBagConstraints.BOTH;
        bottomPanel.add(buttonsPanelBot, localg);

        emcJSplitPane topBottomSplit;
        topBottomSplit = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, topPanel, bottomPanel);
        topBottomSplit.setDividerSize(8);
        topBottomSplit.setContinuousLayout(true);
        topBottomSplit.setOneTouchExpandable(false);
        topBottomSplit.setDividerLocation(160);
        this.add(topBottomSplit);

    }
}
