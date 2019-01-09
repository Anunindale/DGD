/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.crm.display.prospect;

import emc.app.components.base.emailing.EMCEmailButton;
import emc.app.components.base.sms.EMCSMSButton;
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
import emc.app.components.emcpicker.emcdatepicker.EMCDatePickerFormComponent;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.queryswitchbutton.EMCQuerySwitchButton;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.base.BaseEmployeeTable;
import emc.entity.base.BasePostalCodes;
import emc.entity.base.BaseProvence;
import emc.entity.base.language.BaseLanguage;
import emc.entity.crm.CRMClassification1;
import emc.entity.crm.CRMClassification2;
import emc.entity.crm.CRMClassification3;
import emc.entity.crm.CRMInterest;
import emc.entity.crm.CRMProspect;
import emc.entity.crm.CRMProspectCloseReason;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.forms.crm.display.prospect.resources.CRMProspectsDRM;
import emc.forms.crm.display.prospect.resources.CRMTransferProspectButtonList;
import emc.forms.crm.display.prospect.resources.ProspectCloseButton;
import emc.forms.crm.display.prospect.resources.ProspectRestoreButton;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.BaseLanguageMenu;
import emc.menus.base.menuItems.display.BaseProvenceMenu;
import emc.menus.base.menuItems.display.PostalCodes;
import emc.menus.base.menuItems.display.employees;
import emc.menus.crm.menuitems.display.CRMClassification1MI;
import emc.menus.crm.menuitems.display.CRMClassification2MI;
import emc.menus.crm.menuitems.display.CRMClassification3MI;
import emc.menus.crm.menuitems.display.CRMDocumentSelectionMenu;
import emc.menus.crm.menuitems.display.CRMInterestMenu;
import emc.menus.crm.menuitems.display.CRMProspectCloseReasonMI;
import emc.menus.workflow.menuitems.display.WorkFlowSimpleActivitiesMenu;
import emc.methods.crm.ServerCRMMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

/**
 *
 * @author wikus
 */
public class CRMProspectForm extends BaseInternalFrame {

    private CRMProspectsDRM dataManager;
    private EMCUserData userData;
    private emcJTextField txtPostalAddress1;

    public CRMProspectForm(EMCUserData userData) {
        super("Prospect", true, true, true, true, userData);
        this.setBounds(20, 20, 850, 600);
        try {
            this.userData = userData.copyUserDataAndDataList();
            dataManager = new CRMProspectsDRM(new emcGenericDataSourceUpdate(enumEMCModules.CRM.getId(), new CRMProspect(), userData), userData);
            dataManager.setTheForm(this);
            this.setDataManager(dataManager);
            dataManager.setFormTextId1("prospectId");
            dataManager.setFormTextId2("surname");
        } catch (Exception ex) {
        }
        initFrame();
    }

    private void initFrame() {
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        emcJSplitPane topBottomSplit = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, tablePane(), detailPane());
        topBottomSplit.setDividerLocation(190);
        contentPane.add(topBottomSplit, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);
        this.setContentPane(contentPane);
    }

    private JTabbedPane tablePane() {
        List keys = new ArrayList();
        keys.add("prospectId");
        keys.add("company");
        keys.add("name");
        keys.add("surname");
        keys.add("telNum");
        keys.add("cellNum");
        keys.add("email");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        final emcJTableUpdate table = new emcJTableUpdate(model);
        AbstractAction focusAction = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing();
                }
                int col = table.getSelectedColumn();
                int maxCol = table.getTableModel().getColumnCount();
                col++;
                if (col == maxCol) {
                    txtPostalAddress1.requestFocusInWindow();
                }

                table.changeSelection(table.getSelectedRow(), col, false, false);

            }
        };
        table.getActionMap().put("pressed ENTER", focusAction);
        table.getActionMap().put("pressed TAB", focusAction);
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        this.setTablePanel(tableScroll);
        JTabbedPane tabbed = new JTabbedPane();
        tabbed.add("Prospect", tableScroll);
        return tabbed;
    }

    private emcJPanel statusPanel() {
        EMCLookupFormComponent lkpCloseReason = new EMCLookupFormComponent(new CRMProspectCloseReasonMI(), dataManager, "reasonCode");
        lkpCloseReason.setPopup(new EMCLookupPopup(new CRMProspectCloseReason(), "reasonId", userData));
        lkpCloseReason.setEnabled(false);
        emcJTextField txtTransferTo = new emcJTextField(new EMCStringFormDocument(dataManager, "transferedTo"));
        txtTransferTo.setEditable(false);
        emcJTextField txtStatus = new emcJTextField(new EMCStringFormDocument(dataManager, "prosStatus"));
        txtStatus.setEditable(false);


        Component[][] comp = {
            {new emcJLabel("Status"), txtStatus},
            {new emcJLabel("Close Reason"), lkpCloseReason},
            {new emcJLabel("Transfered to"), txtTransferTo}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Status");
    }

    private emcJPanel addressPanel() {
        txtPostalAddress1 = new emcJTextField(new EMCStringFormDocument(dataManager, "postalAddress1"));
        emcJTextField txtPostalAddress2 = new emcJTextField(new EMCStringFormDocument(dataManager, "postalAddress2"));
        emcJTextField txtPostalAddress3 = new emcJTextField(new EMCStringFormDocument(dataManager, "postalAddress3"));
        emcJTextField txtPostalAddress4 = new emcJTextField(new EMCStringFormDocument(dataManager, "postalAddress4"));
        emcJTextField txtPostalAddress5 = new emcJTextField(new EMCStringFormDocument(dataManager, "postalAddress5"));
        EMCLookupFormComponent lkpPostalCode = new EMCLookupFormComponent(new PostalCodes(), dataManager, "postalPostalCode");
        lkpPostalCode.setPopup(new EMCLookupPopup(new BasePostalCodes(), "code", userData));
        Component[][] comp = {{new emcJLabel("Postal Address 1"), txtPostalAddress1},
            {new emcJLabel("Postal Address 2"), txtPostalAddress2},
            {new emcJLabel("Postal Address 3"), txtPostalAddress3},
            {new emcJLabel("Postal Address 4"), txtPostalAddress4},
            {new emcJLabel("Postal Address 5"), txtPostalAddress5},
            {new emcJLabel("Postal Code"), lkpPostalCode}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Address");
    }

    private emcJTabbedPane detailPane() {
        EMCLookupFormComponent lkpProvince = new EMCLookupFormComponent(new BaseProvenceMenu(), dataManager, "province");
        lkpProvince.setPopup(new EMCLookupPopup(new BaseProvence(), "provence", userData));
       
        EMCLookupFormComponent lkpInterest = new EMCLookupFormComponent(new CRMInterestMenu(), dataManager, "interest");
        lkpInterest.setPopup(new EMCLookupPopup(new CRMInterest(), "interest", userData));
       
        EMCLookupFormComponent lkpLang = new EMCLookupFormComponent(new BaseLanguageMenu(), dataManager, "prefLanguage");
        lkpLang.setPopup(new EMCLookupPopup(new BaseLanguage(), "languageId", userData));
        EMCLookupFormComponent lkpSalesRep = new EMCLookupFormComponent(new employees(), dataManager, "employeeId");
        lkpSalesRep.setPopup(new EMCLookupPopup(new BaseEmployeeTable(), "employeeNumber", userData));
        EMCDatePickerFormComponent dpCreated = new EMCDatePickerFormComponent(dataManager, "createdDate");
        dpCreated.setEnabled(false);
        EMCDatePickerFormComponent dpPresentationDate = new EMCDatePickerFormComponent(dataManager, "presentationDate");
        
        EMCLookupFormComponent lkpClassification1 = new EMCLookupFormComponent(new CRMClassification1MI(), dataManager, "classification1");
        lkpClassification1.setPopup(new EMCLookupPopup(new CRMClassification1(), "classification", userData));
        EMCLookupFormComponent lkpClassification2 = new EMCLookupFormComponent(new CRMClassification2MI(), dataManager, "classification2");
        lkpClassification2.setPopup(new EMCLookupPopup(new CRMClassification2(), "classification", userData));
        EMCLookupFormComponent lkpClassification3 = new EMCLookupFormComponent(new CRMClassification3MI(), dataManager, "classification3");
        lkpClassification3.setPopup(new EMCLookupPopup(new CRMClassification3(), "classification", userData));
        Component[][] leftComp = {
            {new emcJLabel("Language"), lkpLang},
            {new emcJLabel("Sales Rep"), lkpSalesRep},
            {new emcJLabel("Province"), lkpProvince},
            {new emcJLabel("Captured"), dpCreated},
            {new emcJLabel("Presentation Date"), dpPresentationDate}};

        Component[][] rightComp = {
            {new emcJLabel("Prospect For"), lkpInterest},
            {new emcJLabel("Classification 1"), lkpClassification1},
            {new emcJLabel("Classification 2"), lkpClassification2},
            {new emcJLabel("Classification 3"), lkpClassification3}};
        emcJTextArea txaRequest = new emcJTextArea(new EMCStringFormDocument(dataManager, "request"));
        emcJPanel bottomPane = new emcJPanel(new BorderLayout());
        bottomPane.add(new emcJScrollPane(txaRequest), BorderLayout.CENTER);
        bottomPane.setBorder(BorderFactory.createTitledBorder("Request"));
        bottomPane.setPreferredSize(new Dimension(100, 140));
        emcJPanel detailPanel = new emcJPanel(new BorderLayout());
        detailPanel.add(emcSetGridBagConstraints.createSimplePanel(leftComp, GridBagConstraints.NONE, true), BorderLayout.WEST);
        detailPanel.add(emcSetGridBagConstraints.createSimplePanel(rightComp, GridBagConstraints.NONE, true), BorderLayout.CENTER);
        detailPanel.add(bottomPane, BorderLayout.SOUTH);
        detailPanel.setBorder(BorderFactory.createTitledBorder("Details"));
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Details", detailPanel);
        tabbed.add("Address", addressPanel());
        tabbed.add("Status", statusPanel());
        return tabbed;
    }

    private emcJPanel buttonPane() {
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        CRMTransferProspectButtonList btnTransfer = new CRMTransferProspectButtonList(this);

        EMCEmailButton btnEmail = new EMCEmailButton(dataManager, "email", "prospectId");
        dataManager.setBtnEmail(btnEmail);

        EMCSMSButton btnSMS = new EMCSMSButton(dataManager, "cellNum", "postalPostalCode");
        dataManager.setBtnEmail(btnEmail);



        final CRMDocumentSelectionMenu docMenu = new CRMDocumentSelectionMenu();
        docMenu.setDoNotOpenForm(false);

        emcJButton btnJob = new emcJButton("Create Task") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.CRM.getId(), ServerCRMMethods.CREATE_PROSPECT_JOB.toString());
                List toSend = new ArrayList();
                toSend.add(dataManager.getRowCache(dataManager.getLastRowAccessed()));
                EMCWSManager.executeGenericWS(cmd, toSend, userData);
            }
        };
        EMCQuerySwitchButton btnQuery = new EMCQuerySwitchButton("Prospects", dataManager);
        btnQuery.addQuery("Prospects", new CRMProspect().buildQuery());
        btnQuery.addQuery("All", new EMCQuery(enumQueryTypes.SELECT, CRMProspect.class));

        emcJButton btnClose = new ProspectCloseButton(dataManager);
        emcJButton btnRestore = new ProspectRestoreButton(dataManager);
        dataManager.setBtnClose(btnClose);
        dataManager.setBtnRestore(btnRestore);

        //Activities
        emcMenuButton btnActivities = new emcMenuButton("Activities", new WorkFlowSimpleActivitiesMenu(), this, 13, false);
        buttonList.add(btnActivities);

        buttonList.add(btnTransfer);
        buttonList.add(btnEmail);
        buttonList.add(btnSMS);
        buttonList.add(btnJob);
        buttonList.add(btnClose);
        buttonList.add(btnRestore);
        buttonList.add(btnQuery);

        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}


