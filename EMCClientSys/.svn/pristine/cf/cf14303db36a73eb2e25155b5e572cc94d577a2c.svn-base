/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.workflow.display.activities;

import emc.app.components.documents.EMCDoubleFormDocument;
import emc.app.components.emcHelpFile;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJScrollPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextArea;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.documents.EMCTimeFormDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcpicker.emcdatepicker.EMCDatePickerFormComponent;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.emctable.emcYesNoComponent;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.jtablelookup.EMCLookupTableCellEditor;
import emc.app.components.workflow.WorkFlowSourceButton;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.datatypes.EMCString;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCDebug;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author rico
 */
public class activitiesForm extends BaseInternalFrame {

    private emcJPanel activityPanel = new emcJPanel();
    private emcJPanel generalPanel = new emcJPanel();
    private emcJPanel detailsPanel = new emcJPanel();
    private emcJPanel planningPanel = new emcJPanel();
    private emcJPanel referencePanel = new emcJPanel();
    private emcJPanel scratchPanel = new emcJPanel();
    private emcJPanel completionPanel = new emcJPanel();
    private emcJLabel actNoLabel = new emcJLabel("Activity Number");
    private emcJTextField actNo = new emcJTextField();
    private emcJTextField emplResp = new emcJTextField();
    private emcJLabel descriptLabel = new emcJLabel("Description");
    private emcJTextField descript = new emcJTextField();
    private emcJTextField createdBy = new emcJTextField();
    private emcJTextArea detailDec = new emcJTextArea();
    private emcJTextArea scratchPad = new emcJTextArea();
    //planing
    private emcJTextField reqBy = new emcJTextField();
    private EMCDatePickerFormComponent startDate;
    private emcJTextField startTime = new emcJTextField();
    private EMCDatePickerFormComponent expectedCompl;
    private EMCDatePickerFormComponent createdOn;
    private emcJTextField durationHours = new emcJTextField();
    private EMCDatePickerFormComponent complDate;
    private emcJTextField complTime = new emcJTextField();
    private emcJTextField estHours = new emcJTextField();
    private emcJTextField actHours = new emcJTextField();
    private emcYesNoComponent billable;
    private emcYesNoComponent closedByMng;
    //reference
    private emcJTextField referenceSource = new emcJTextField();
    private emcJTextField reference = new emcJTextField();
    private emcJTextField customer = new emcJTextField();
    private emcJTextField customerName = new emcJTextField();
    //completion
    private EMCDatePickerFormComponent completionDate;
    private emcJTextField completionTime = new emcJTextField();
    private emcJTextField completionRules = new emcJTextField();
    private emcJTextField closedBy = new emcJTextField();
    private EMCDatePickerFormComponent closedOn;
    private emcJTextField resultAct = new emcJTextField();
    private emcYesNoComponent outputFileReq;
    private emcJTextField outputFilePath = new emcJTextField();
    //lookups
    private EMCLookupJTableComponent lookupEmp;
    private EMCLookupTableCellEditor empEditor;
    private EMCLookupJTableComponent lookupStatus;
    private EMCLookupTableCellEditor statusEditor;
    private EMCLookupJTableComponent lookupPrior;
    private EMCLookupTableCellEditor priorEditor;
    private EMCLookupFormComponent formLookupStatus;
    private EMCLookupFormComponent lookupManager;
    private EMCLookupFormComponent lookupCat;
    private EMCLookupFormComponent lookupType;
    private EMCLookupFormComponent lookupActGrp;
    private EMCLookupFormComponent formLookupPrior;
    private EMCLookupFormComponent lookupskill;
    //DataSource
    private emcDataRelationManagerUpdate dataRelation;
    //Email and SMS pan
    private EMCUserData copyUD;

    public activitiesForm(EMCUserData userData) {
        super("Activities", true, true, true, true, userData);
        this.setBounds(20, 20, 750, 350);
        this.setHelpFile(new emcHelpFile("WorkFlow/WorkFlowActivities.html"));
        try {

            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                    enumEMCModules.WORKFLOW.getId(), new emc.entity.workflow.datasource.WorkFlowActivityDS(), userData), userData);
            this.setDataManager(dataRelation);

            copyUD = userData.copyUserData();
            copyUD.setUserData(null);

            //emp
            List employeeKeys = new ArrayList();
            employeeKeys.add("employeeNumber");
            employeeKeys.add("surname");
            employeeKeys.add("forenames");
            employeeKeys.add("emailAddress");
            employeeKeys.add("internalPhoneNumber");
            lookupEmp = new EMCLookupJTableComponent(new emc.menus.base.menuItems.display.employees());
            EMCLookupPopup empPopup = new EMCLookupPopup(enumEMCModules.BASE.getId(), new emc.entity.base.BaseEmployeeTable(),
                    "employeeNumber", employeeKeys, copyUD);
            lookupEmp.setPopup(empPopup);
            empEditor = new EMCLookupTableCellEditor(lookupEmp);

            lookupManager = new EMCLookupFormComponent(new emc.menus.base.menuItems.display.employees(),
                    dataRelation, "managerResponsible");
            lookupManager.setPopup(empPopup);
            dataRelation.setDocument(lookupManager.getDocument());

            //status
            List statusKeys = new ArrayList();
            statusKeys.add("activityStatus");
            statusKeys.add("description");

            formLookupStatus = new EMCLookupFormComponent(new emc.menus.workflow.menuitems.display.activityStatus(),
                    dataRelation, "status");
            lookupStatus = new EMCLookupJTableComponent(new emc.menus.workflow.menuitems.display.activityStatus());
            EMCLookupPopup statusPopup = new EMCLookupPopup(enumEMCModules.WORKFLOW.getId(), new emc.entity.workflow.WorkFlowActivityStatus(),
                    "activityStatus", statusKeys, copyUD);
            lookupStatus.setPopup(statusPopup);
            statusEditor = new EMCLookupTableCellEditor(lookupStatus);
            formLookupStatus.setPopup(statusPopup);

            //categories
            List catKeys = new ArrayList();
            catKeys.add("activityCategory");
            catKeys.add("description");

            lookupCat = new EMCLookupFormComponent(new emc.menus.workflow.menuitems.display.activityCategory(),
                    dataRelation, "category");
            EMCLookupPopup catPopup = new EMCLookupPopup(enumEMCModules.WORKFLOW.getId(), new emc.entity.workflow.WorkFlowActivityCate(),
                    "activityCategory", catKeys, copyUD);
            lookupCat.setPopup(catPopup);

            //prior
            List catPrior = new ArrayList();
            catPrior.add("activityPriority");
            catPrior.add("description");
            formLookupPrior = new EMCLookupFormComponent(new emc.menus.workflow.menuitems.display.activityPriority(),
                    dataRelation, "priority");
            EMCLookupPopup priorPopup = new EMCLookupPopup(enumEMCModules.WORKFLOW.getId(), new emc.entity.workflow.WorkFlowActivityPriority(),
                    "activityPriority", catPrior, copyUD);
            lookupPrior = new EMCLookupJTableComponent(new emc.menus.workflow.menuitems.display.activityPriority());
            lookupPrior.setPopup(priorPopup);
            priorEditor = new EMCLookupTableCellEditor(lookupPrior);
            formLookupPrior.setPopup(priorPopup);

            //skill
            List skillkeys = new ArrayList();
            skillkeys.add("skill");
            skillkeys.add("description");
            lookupskill = new EMCLookupFormComponent(new emc.menus.workflow.menuitems.display.skills(),
                    dataRelation, "skillRequired");
            EMCLookupPopup skillPopup = new EMCLookupPopup(enumEMCModules.WORKFLOW.getId(), new emc.entity.workflow.WorkFlowSkill(),
                    "skill", skillkeys, copyUD);
            lookupskill.setPopup(skillPopup);

            //group
            List groupkeys = new ArrayList();
            groupkeys.add("activityGroup");
            groupkeys.add("description");
            lookupActGrp = new EMCLookupFormComponent(new emc.menus.workflow.menuitems.display.activityGroup(),
                    dataRelation, "activityGroup");
            EMCLookupPopup actGrpPopup = new EMCLookupPopup(enumEMCModules.WORKFLOW.getId(), new emc.entity.workflow.WorkFlowActivityGroups(),
                    "activityGroup", groupkeys, userData);
            lookupActGrp.setPopup(actGrpPopup);

            //type
            List typeKeys = new ArrayList();
            typeKeys.add("activityType");
            typeKeys.add("description");

            lookupType = new EMCLookupFormComponent(new emc.menus.workflow.menuitems.display.activityTypes(),
                    dataRelation, "type");
            EMCLookupPopup typePopup = new EMCLookupPopup(enumEMCModules.WORKFLOW.getId(), new emc.entity.workflow.WorkFlowActivityTypes(),
                    "activityType", typeKeys, userData);
            lookupType.setPopup(typePopup);

            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("employeeNumber");
            dataRelation.setFormTextId2("description");

        } catch (Exception e) {
            if (EMCDebug.getDebug()) {
                System.out.println(e.getMessage());
            }
        }
        setDocuments();
        initFrame();
    }

    private void setDocuments() {
        createdBy.setEditable(false);

        this.descript.setDocument(new EMCStringFormDocument(dataRelation, "description"));
        this.actNo.setDocument(new EMCStringFormDocument(dataRelation, "activityId"));
        actNo.setEditable(false);
        this.createdBy.setDocument(new EMCStringFormDocument(dataRelation, "createdBy"));
        this.detailDec.setDocument(new EMCStringFormDocument(dataRelation, "detailedDescription"));
        this.scratchPad.setDocument(new EMCStringFormDocument(dataRelation, "scratchPad"));
        EMCStringFormDocument text = (EMCStringFormDocument) scratchPad.getDocument();
        EMCString scratchPadDT = new EMCString();
        scratchPadDT.setStringSize(2000);
        text.setDataType(scratchPadDT);
        this.startTime.setDocument(new EMCTimeFormDocument(startTime, dataRelation, "startTime"));
        startTime.setEditable(false);
        this.durationHours.setDocument(new EMCDoubleFormDocument(dataRelation, "duration"));
        durationHours.setEditable(false);
        this.complTime.setDocument(new EMCTimeFormDocument(complTime, dataRelation, "completionTime"));
        complTime.setEditable(false);

        this.completionTime.setDocument(new EMCTimeFormDocument(completionTime, dataRelation, "completionTime"));
        completionTime.setEditable(false);
        this.actHours.setDocument(new EMCDoubleFormDocument(dataRelation, "hoursWorkActual"));
        this.estHours.setDocument(new EMCDoubleFormDocument(dataRelation, "hoursWorkEstimated"));
        this.resultAct.setDocument(new EMCStringFormDocument(dataRelation, "activityResult"));
        this.outputFilePath.setDocument(new EMCStringFormDocument(dataRelation, "outputFilePath"));

        startDate = new EMCDatePickerFormComponent(dataRelation, "startDate");
        this.createdOn = new EMCDatePickerFormComponent(dataRelation, "createdDate");
        createdOn.setEnabled(false);
        this.complDate = new EMCDatePickerFormComponent(dataRelation, "completionDate");
        complDate.setEnabled(false);
        this.completionDate = new EMCDatePickerFormComponent(dataRelation, "completionDate");

        this.expectedCompl = new EMCDatePickerFormComponent(dataRelation, "expectedCompletionDate");

        this.closedBy.setDocument(new EMCStringFormDocument(dataRelation, "closedBy"));
        dataRelation.setDocument(closedBy.getDocument());
        closedBy.setEditable(false);

        closedOn = new EMCDatePickerFormComponent(dataRelation, "closedDate");
        this.outputFileReq = new emcYesNoComponent(dataRelation, "outputFileRequired");
        this.billable = new emcYesNoComponent(dataRelation, "billable");
        this.closedByMng = new emcYesNoComponent(dataRelation, "closedByManager");

        emplResp.setDocument(new EMCStringFormDocument(dataRelation, "employeeNumber"));
        emplResp.setEditable(false);

        this.referenceSource.setDocument(new EMCStringFormDocument(dataRelation, "referenceSource"));
        this.reference.setDocument(new EMCStringFormDocument(dataRelation, "reference"));
    }

    private void generalTab() {
        generalPanel.setLayout(new GridBagLayout());
        generalPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("General"));
        int y = 0;
        GridBagConstraints localg;
        //Act No
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        generalPanel.add(this.actNoLabel, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.1);
        generalPanel.add(this.actNo, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 2, y, 0.1);
        generalPanel.add(new emcJLabel("Created By"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 3, y, 0.1);
        generalPanel.add(this.createdBy, localg);
        //Description
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        generalPanel.add(this.descriptLabel, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.1);
        generalPanel.add(this.descript, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 2, y, 0.1);
        generalPanel.add(new emcJLabel("Employee Responsible"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 3, y, 0.1);
        generalPanel.add(this.emplResp, localg);
        //type
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        generalPanel.add(new emcJLabel("Type"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.1);
        generalPanel.add(this.lookupType, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 2, y, 0.1);
        generalPanel.add(new emcJLabel("Task Manager"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 3, y, 0.1);
        generalPanel.add(this.lookupManager, localg);
        //category
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        generalPanel.add(new emcJLabel("Category"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.1);
        generalPanel.add(this.lookupCat, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 2, y, 0.1);
        generalPanel.add(new emcJLabel("Activity Group"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 3, y, 0.1);
        generalPanel.add(this.lookupActGrp, localg);
        //Status
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        generalPanel.add(new emcJLabel("Status"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        generalPanel.add(this.formLookupStatus, localg);
        //priority
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        generalPanel.add(new emcJLabel("Priority"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        generalPanel.add(this.formLookupPrior, localg);
        //skill req
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        generalPanel.add(new emcJLabel("Skill Required"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        generalPanel.add(this.lookupskill, localg);
        //end of panel
        y++;
        generalPanel.add(new emcJLabel(), emcSetGridBagConstraints.endPanel(y));
    }

    private void detailsTab() {
        detailsPanel.setLayout(new GridLayout(1, 1));
        detailsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Details"));
        emcJScrollPane detailDecScroll = new emcJScrollPane(this.detailDec);
        detailsPanel.add(detailDecScroll);

    }

    private void referenceTab() {
        referencePanel.setLayout(new GridBagLayout());
        referencePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Reference"));
        referenceSource.setEditable(false);
        reference.setEditable(false);
        customer.setEditable(false);
        customerName.setEditable(false);
        int y = 0;
        GridBagConstraints localg;
        //reference source
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        referencePanel.add(new emcJLabel("Reference Source"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        referencePanel.add(this.referenceSource, localg);
        //reference 
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        referencePanel.add(new emcJLabel("Reference"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        referencePanel.add(this.reference, localg);
        //customer
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        referencePanel.add(new emcJLabel("Customer"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.1);
        referencePanel.add(this.customer, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 2, y, 0.9);
        referencePanel.add(this.customerName, localg);
        //end of panel
        y++;
        referencePanel.add(new emcJLabel(), emcSetGridBagConstraints.endPanel(y));
    }

    private void scratchTab() {
        scratchPanel.setLayout(new GridLayout(1, 1));
        scratchPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Scratch Pad"));
        emcJScrollPane scratchScroll = new emcJScrollPane(this.scratchPad);
        scratchPanel.add(scratchScroll);
    }

    private void completionTab() {
        completionPanel.setLayout(new GridBagLayout());
        completionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Completion"));
        int y = 0;
        GridBagConstraints localg;
        //completion date
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        completionPanel.add(new emcJLabel("Completion Date"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.1);
        completionPanel.add(this.completionDate, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 2, y, 0.1);
        completionPanel.add(new emcJLabel("Closed By"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 3, y, 0.1);
        completionPanel.add(this.closedBy, localg);
        y++;
        //completion time
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        completionPanel.add(new emcJLabel("Completion Time"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.1);
        completionPanel.add(this.completionTime, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 2, y, 0.1);
        completionPanel.add(new emcJLabel("Closed On"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 3, y, 0.1);
        completionPanel.add(this.closedOn, localg);
        y++;
        //completion rules
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        completionPanel.add(new emcJLabel("Completion Rules"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.1);
        completionPanel.add(this.completionRules, localg);
        y++;
        //result
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        completionPanel.add(new emcJLabel("Result"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.1);
        completionPanel.add(this.resultAct, localg);
        y++;
        //result
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        completionPanel.add(new emcJLabel("Output File Required"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.1);
        completionPanel.add(this.outputFileReq, localg);
        y++;
        //result
        /*localg = emcSetGridBagConstraints.createStandard(0,y,0.1,GridBagConstraints.LINE_START);
        completionPanel.add(new emcJLabel("Output File Path"),localg);
        localg = emcSetGridBagConstraints.changePosPlusFill(localg,1,y,0.9,GridBagConstraints.LINE_END,
        GridBagConstraints.HORIZONTAL,GridBagConstraints.REMAINDER);
        completionPanel.add(this.outputFilePath,localg);
        //end of panel*/
        y++;
        completionPanel.add(new emcJLabel(), emcSetGridBagConstraints.endPanel(y));
    }

    private void planningTab() {
        planningPanel.setLayout(new GridBagLayout());
        planningPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Planning"));

        this.complDate.setEnabled(false);
        this.complTime.setEditable(false);
        int y = 0;
        GridBagConstraints localg;
        //Req by
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        planningPanel.add(new emcJLabel("Required By"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.1);
        planningPanel.add(this.reqBy, localg);
        reqBy.setEditable(false);
        localg = emcSetGridBagConstraints.changePosition(localg, 2, y, 0.1);
        planningPanel.add(new emcJLabel("Start Date"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 3, y, 0.1);
        planningPanel.add(this.startDate, localg);
        //Expected comp
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        planningPanel.add(new emcJLabel("Expected Completion"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.1);
        planningPanel.add(expectedCompl, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 2, y, 0.1);
        planningPanel.add(new emcJLabel("Start Time"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 3, y, 0.1);
        planningPanel.add(this.startTime, localg);
        //created on
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        planningPanel.add(new emcJLabel("Created On"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.1);
        planningPanel.add(this.createdOn, localg);
        //duration
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        planningPanel.add(new emcJLabel("Duration (Days)"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.1);
        planningPanel.add(this.durationHours, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 2, y, 0.1);
        planningPanel.add(new emcJLabel("Completed Date"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 3, y, 0.1);
        planningPanel.add(this.complDate, localg);
        //completed time
        y++;
        localg = emcSetGridBagConstraints.createStandard(2, y, 0.1, GridBagConstraints.LINE_START);
        planningPanel.add(new emcJLabel("Completed Time"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 3, y, 0.1);
        planningPanel.add(this.complTime, localg);
        //estimated hours
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        planningPanel.add(new emcJLabel("Estimated Hours"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.1);
        planningPanel.add(this.estHours, localg);
        //act hours
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        planningPanel.add(new emcJLabel("Actual Hours"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.1);
        planningPanel.add(this.actHours, localg);
        //billable
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        planningPanel.add(new emcJLabel("Billable"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.1);
        planningPanel.add(this.billable, localg);
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        planningPanel.add(new emcJLabel("Manager closes activity"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.1);
        planningPanel.add(this.closedByMng, localg);
        //end of panel
        y++;
        planningPanel.add(new emcJLabel(), emcSetGridBagConstraints.endPanel(y));
    }

    private void activityTab() {
        List keys = new ArrayList();
        keys.add("overdue");
        keys.add("complete");
        keys.add("employeeNumber");
        keys.add("employeeName");
        keys.add("requiredCompletionDate");
        keys.add("priority");
        keys.add("managerResponsible");
        keys.add("managerName");
        keys.add("description");
        keys.add("status");
        keys.add("documentAttached");

        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation, keys) {

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                if (columnIndex == 0) {
                    return false;
                }
                if (columnIndex == 1) {
                    return false;
                }
                if (columnIndex == 3) {
                    return false;
                }
                if (columnIndex == 7) {
                    return false;
                }
                if (columnIndex == 10) {
                    return false;
                }
                return true;
            }

            @Override
            public String getColumnName(int columnIndex) {
                if (columnIndex == 0) {
                    return "O";
                }
                if (columnIndex == 1) {
                    return "C";
                }
                if (columnIndex == 3) {
                    return "Emp";
                }
                if (columnIndex == 7) {
                    return "Man";
                }
                if (columnIndex == 10) {
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
                if (columnIndex == 3) {
                    return 75;
                }
                if (columnIndex == 7) {
                    return 75;
                }
                if (columnIndex == 10) {
                    return 5;
                }
                return super.getColumnWidth(columnIndex);
            }
        };
        try {
            emcJTableUpdate toptable = new emcJTableUpdate(m);
            dataRelation.setMainTableComponent(toptable);
            //toptable.setLookupToColumn(2, lookupEmp);
            //toptable.setLookupToColumn(6, lookupManager);
            toptable.setLookupCellEditor(2, empEditor);
            toptable.setLookupCellEditor(6, empEditor);
            toptable.setLookupCellEditor(9, statusEditor);
            toptable.setLookupCellEditor(5, priorEditor);
            //toptable.setLookupToColumn(9, lookupStatus);
            //toptable.setLookupToColumn(9, lookupCat);
            //toptable.setLookupToColumn(8, lookupType);
            //toptable.setLookupToColumn(6, lookupActGrp);
            //toptable.setLookupToColumn(5, lookupPrior);

            emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
            activityPanel.setLayout(new GridLayout(1, 1));
            activityPanel.add(topscroll);
            this.setTablePanel(topscroll);
        } catch (Exception e) {
            if (EMCDebug.getDebug()) {
                java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Failed to create JTable", this.getUserData());
            }
        }

    }

    private void initFrame() {
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        activityTab();
        generalTab();
        detailsTab();
        planningTab();
        referenceTab();
        scratchTab();
        completionTab();
        tabbedPanetop.add("Activities", this.activityPanel);
        tabbedPanetop.add("General", generalPanel);
        tabbedPanetop.add("Details", detailsPanel);
        tabbedPanetop.add("Planning", planningPanel);
        tabbedPanetop.add("Reference", referencePanel);
        tabbedPanetop.add("Scratch Pad", scratchPanel);
        tabbedPanetop.add("Completion", completionPanel);
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbedPanetop, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);
        this.add(contentPane);
    }

    private emcJPanel buttonPane() {
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(new WorkFlowSourceButton(this, dataRelation, "sourceTable", "sourceRecordId"));
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}
