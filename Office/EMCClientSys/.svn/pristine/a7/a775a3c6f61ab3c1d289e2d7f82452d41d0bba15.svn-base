package emc.forms.developertools.display.bugtracking;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import emc.app.components.base.EMCModuleDropDown;
import emc.app.components.developertools.EMCBugTypeDropDown;
import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJScrollPane;
import emc.app.components.emcJSplitPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextArea;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.queryswitchbutton.EMCQuerySwitchButton;
import emc.app.reporttools.JasperInformation;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCReportWSManager;
import emc.entity.base.Usertable;
import emc.entity.developertools.bugtracking.DevBugsTable;
import emc.entity.sop.SOPCustomers;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.users;
import emc.menus.debtors.menuitems.display.DebtorsCustomers;
import emc.menus.sop.menuitems.display.SOPCustomersMenu;
import emc.methods.developertools.ServerDeveloperToolMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JSplitPane;

/**
 * @Name         : BugsForm
 *
 * @Date         : 14 Aug 2009
 * 
 * @Description  : This form is used to log bugs.
 *
 * @author       : riaan
 */
public class BugsForm extends BaseInternalFrame {

    private emcJTextArea txaBug = new emcJTextArea();
    private emcDataRelationManagerUpdate drm;
    private EMCUserData userData;

    /** Creates a new instance of BugsForm. */
    public BugsForm(EMCUserData userData) {
        super("Tasks", true, true, true, true, userData);
        this.setBounds(20, 20, 1200, 600);

        drm = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.DEVELOPERTOOLS.getId(), new DevBugsTable(), userData), userData) {

            @Override
            public void updatePersist(int rowIndex) {
                super.updatePersist(rowIndex);

                if (getLastFieldValueAt("completed") != null && getLastUpdateStatus()) {
                    EMCCommandClass cmd = new EMCCommandClass(ServerDeveloperToolMethods.PRINT_DEVELOPMENT_SUMMARY_SHEET);

                    JasperInformation jasperInfo = new JasperInformation();
                    jasperInfo.setJasperTemplate("/emc/reports/developertools/taskmanagement/DevDevelopmentSummary.jrxml");
                    jasperInfo.setXmlNodePath("/emcmsg/emc.reports.developertools.taskmanagement.developmentsummary.DevDevelopmentSummaryDS");
                    jasperInfo.setReportTitle("Development Summary");

                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DevBugsTable.class);
                    query.addAnd("bugNumber", getLastFieldValueAt("bugNumber"));

                    List toSend = new ArrayList();
                    toSend.add(query);

                    EMCReportWSManager.generateReport(cmd, jasperInfo, EnumReports.DEV_DEVELOPMENT_SUMMARY, toSend, getUserData());
                }
            }
        };

        this.setDataManager(drm);
        this.userData = userData.copyUserDataAndDataList();

        drm.setTheForm(this);
        drm.setFormTextId1("bugNumber");
        drm.setFormTextId2("bugId");


        setDocuments();
        initFrame();
    }

    /** Sets documents.  */
    private void setDocuments() {
        this.txaBug.setDocument(new EMCStringFormDocument(drm, "bugId"));
    }

    /** Initializes the frame. */
    private void initFrame() {
        emcJTabbedPane tabs = new emcJTabbedPane();
        tabs.add("Bugs", createOverviewPanel());
        tabs.add("Possible Affected", createPossibleAffectedTab());
        tabs.add("Test", createTestTab());
        tabs.add("Entity Log", createEntityLogTab());
        tabs.add("Documentation", createDocumentationTab());
        tabs.add("Parent Task", createParentTab());

        emcJSplitPane topBottomSplit = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, tabs, createBugPanel());
        topBottomSplit.setDividerSize(8);
        topBottomSplit.setContinuousLayout(true);
        topBottomSplit.setOneTouchExpandable(false);
        topBottomSplit.setDividerLocation(300);

        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(topBottomSplit, BorderLayout.CENTER);
        contentPane.add(createButtonsPanel(), BorderLayout.EAST);
        this.setContentPane(contentPane);
    }

    /** Creates the Overview panel. */
    private emcJPanel createOverviewPanel() {
        emcJPanel panel = new emcJPanel();

        List keys = new ArrayList();
        keys.add("bugNumber");
        keys.add("moduleId");
        keys.add("bugType");
        keys.add("summary");
        keys.add("responsible");
        keys.add("requiredDate");
        keys.add("priority");
        keys.add("estimateTime");
        keys.add("quotedHours");
        keys.add("completed");
        keys.add("startTime");
        keys.add("timeTaken");
        keys.add("client");
        keys.add("testedBy");
        keys.add("tested");
        keys.add("billable");
        keys.add("pending");

        emcTableModelUpdate m = new emcTableModelUpdate(drm, keys);

        emcJTableUpdate toptable = new emcJTableUpdate(m);
        toptable.setComboBoxLookupToColumn("moduleId", new EMCModuleDropDown());

        toptable.setComboBoxLookupToColumn("bugType", new EMCBugTypeDropDown());

        drm.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        panel.setLayout(new GridLayout(1, 1));

        EMCLookupJTableComponent lkpAssigned = new EMCLookupJTableComponent(new users());
        lkpAssigned.setPopup(new EMCLookupPopup(new Usertable(), "userId", userData));
        toptable.setLookupToColumn("responsible", lkpAssigned);

        EMCLookupJTableComponent lkpClient = new EMCLookupJTableComponent(new SOPCustomersMenu());
        lkpClient.setPopup(new EMCLookupPopup(new SOPCustomers(), "customerId", userData));
        toptable.setLookupToColumn("client", lkpClient);

        EMCLookupJTableComponent lkpTestedBy = new EMCLookupJTableComponent(new users());
        lkpTestedBy.setPopup(new EMCLookupPopup(new Usertable(), "userId", userData));
        toptable.setLookupToColumn("testedBy", lkpTestedBy);

        this.setTablePanel(topscroll);

        panel.add(topscroll);

        return panel;
    }

    /** Creates the bug panel.  */
    private emcJPanel createBugPanel() {
        emcJPanel bugPanel = new emcJPanel(new BorderLayout());

        bugPanel.setBorder(BorderFactory.createTitledBorder(drm.getColumnName("bugId")));
        bugPanel.add(new emcJScrollPane(txaBug));

        return bugPanel;
    }

    /** Creates the buttons panel. */
    private emcJPanel createButtonsPanel() {
        List<emcJButton> buttons = new ArrayList<emcJButton>();

        EMCQuerySwitchButton querySwitchButton = new EMCQuerySwitchButton("Open", drm);
        querySwitchButton.addQuery("Open", DevBugsTable.class.getName(), "completed", EMCQueryConditions.IS_BLANK, null);
        querySwitchButton.addQuery("All", DevBugsTable.class.getName(), "completed", null, null);

        emcJButton btnRequirements = new emcJButton("Requirements") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                EMCCommandClass cmd = new EMCCommandClass(ServerDeveloperToolMethods.PRINT_REQUIREMENTS_SHEET);

                JasperInformation jasperInfo = new JasperInformation();
                jasperInfo.setJasperTemplate("/emc/reports/developertools/taskmanagement/DevRequirementsSheet.jrxml");
                jasperInfo.setXmlNodePath("/emcmsg/emc.reports.developertools.taskmanagement.requirementssheet.DevRequirementsSheetDS");
                jasperInfo.setReportTitle("Requirements Sheet");

                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DevBugsTable.class);
                query.addAnd("bugNumber", drm.getLastFieldValueAt("bugNumber"));

                List toSend = new ArrayList();
                toSend.add(query);

                EMCReportWSManager.generateReport(cmd, jasperInfo, EnumReports.DEV_REQUIREMENTS_SHEET, toSend, userData);
            }
        };
        emcJButton btnDevelopment = new emcJButton("Development") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                EMCCommandClass cmd = new EMCCommandClass(ServerDeveloperToolMethods.PRINT_DEVELOPMENT_SUMMARY_SHEET);

                JasperInformation jasperInfo = new JasperInformation();
                jasperInfo.setJasperTemplate("/emc/reports/developertools/taskmanagement/DevDevelopmentSummary.jrxml");
                jasperInfo.setXmlNodePath("/emcmsg/emc.reports.developertools.taskmanagement.developmentsummary.DevDevelopmentSummaryDS");
                jasperInfo.setReportTitle("Development Summary");

                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DevBugsTable.class);
                query.addAnd("bugNumber", drm.getLastFieldValueAt("bugNumber"));

                List toSend = new ArrayList();
                toSend.add(query);

                EMCReportWSManager.generateReport(cmd, jasperInfo, EnumReports.DEV_DEVELOPMENT_SUMMARY, toSend, userData);
            }
        };




        buttons.add(querySwitchButton);
        buttons.add(btnRequirements);
        buttons.add(btnDevelopment);

        return emcSetGridBagConstraints.createButtonPanel(buttons);
    }

    private emcJPanel createTestTab() {
        emcJTextArea taTest = new emcJTextArea(new EMCStringFormDocument(drm, "testDescription"));

        EMCLookupFormComponent lkpTestedBy = new EMCLookupFormComponent(new users(), drm, "testedBy");
        lkpTestedBy.setPopup(new EMCLookupPopup(new Usertable(), "userId", userData));


        Component[][] comp = {{new emcJLabel("Tested By"), lkpTestedBy},
            {taTest, new emcJLabel("Test Description")}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
    }

    private emcJPanel createPossibleAffectedTab() {
        emcJTextArea taPossibleAffected = new emcJTextArea(new EMCStringFormDocument(drm, "possibleAffected"));
        emcJTextArea taPossibleAffectedData = new emcJTextArea(new EMCStringFormDocument(drm, "possibleAffectedData"));
        emcJTextArea taCoreUpdates = new emcJTextArea(new EMCStringFormDocument(drm, "coreUpdates"));
        Component[][] comp = {{taPossibleAffected, new emcJLabel("Possible Affected")},
            {taPossibleAffectedData, new emcJLabel("Possible Affected Data")},
            {taCoreUpdates, new emcJLabel("Core Updates")}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
    }

    private emcJPanel createEntityLogTab() {
        emcJTextArea taEntityLog = new emcJTextArea(new EMCStringFormDocument(drm, "entityLog"));
        Component[][] comp = {{taEntityLog, new emcJLabel("Entity Log")},};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
    }

    private emcJPanel createDocumentationTab() {
        emcJTextArea taDocumentation = new emcJTextArea(new EMCStringFormDocument(drm, "documentation"));
        Component[][] comp = {{taDocumentation, new emcJLabel("Documentation")},};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
    }
    
    private emcJPanel createParentTab(){
        EMCLookupFormComponent lkpTestedBy = new EMCLookupFormComponent(new emc.menus.developertools.Bugs(), drm, "parentTask");
        lkpTestedBy.setPopup(new EMCLookupPopup(new DevBugsTable(), "bugNumber", userData));
        
        Component[][] comp = {{new emcJLabel("Parent Task"), lkpTestedBy},};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
    }
}
