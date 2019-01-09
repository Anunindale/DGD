/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.developertools.display.sites;

import emc.app.components.emcJButton;
import emc.app.components.emcJComboBox;
import emc.app.components.emcJDialog;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJSplitPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.developertools.sites.DevSiteLines;
import emc.entity.developertools.sites.DevSiteMaster;
import emc.entity.sop.SOPCustomers;
import emc.enums.developertools.DevConnectionType;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.menus.debtors.menuitems.display.DebtorsCustomers;
import emc.methods.developertools.ServerDeveloperToolMethods;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.swing.JSplitPane;

/**
 *
 * @author wikus
 */
public class DevSiteForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate masterDRM;
    private EMCUserData masterUD;
    private emcDataRelationManagerUpdate linesDRM;
    private EMCUserData linesUD;

    public DevSiteForm(EMCUserData userData) {
        super("Sites", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 580);
        //Master
        masterUD = userData.copyUserDataAndDataList();
        masterDRM = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.DEVELOPERTOOLS.getId(), new DevSiteMaster(), masterUD), masterUD);
        masterDRM.setTheForm(this);
        this.setDataManager(masterDRM);
        masterDRM.setFormTextId1("masterId");
        masterDRM.setFormTextId2("customerId");
        //Lines
        linesUD = userData.copyUserData();
        linesDRM = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.DEVELOPERTOOLS.getId(), new DevSiteLines(), linesUD), linesUD);
        linesDRM.setTheForm(this);
        linesDRM.setFormTextId1("connectionDescription");
        linesDRM.setFormTextId2("IP");
        //Link
        masterDRM.setLinesTable(linesDRM);
        masterDRM.setHeaderColumnID("masterId");
        linesDRM.setHeaderTable(masterDRM);
        linesDRM.setRelationColumnToHeader("masterId");
        //Form
        initFrame();
    }

    private void initFrame() {
        emcJSplitPane topBottomSplit = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, masterPane(), linesPane());
        topBottomSplit.setDividerLocation(200);
        this.setContentPane(topBottomSplit);
    }

    private emcJPanel masterPane() {
        emcJPanel masterPanel = new emcJPanel(new BorderLayout());

        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Site", masterTablePane());

        masterPanel.add(tabbed, BorderLayout.CENTER);
        masterPanel.add(createMasterButtons(), BorderLayout.EAST);
        return masterPanel;
    }

    private emcTablePanelUpdate masterTablePane() {
        List keys = new ArrayList();
        keys.add("masterId");
        keys.add("customerId");
        keys.add("serverSystemId");
        keys.add("authorizationKey");
        emcTableModelUpdate model = new emcTableModelUpdate(masterDRM, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        //Lookups
        EMCLookupJTableComponent lkpCustomer = new EMCLookupJTableComponent(new DebtorsCustomers());
        lkpCustomer.setPopup(new EMCLookupPopup(new SOPCustomers(), "customerId", masterUD));
        table.setLookupToColumn("customerId", lkpCustomer);
        //Lookups
        masterDRM.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        masterDRM.setTablePanel(tableScroll);
        return tableScroll;
    }

    private emcJPanel createMasterButtons() {
        List<emcJButton> masterButtons = new ArrayList<emcJButton>();

        masterButtons.add(new emcJButton("Get Old Authorization Key") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                EMCCommandClass cmd = new EMCCommandClass(ServerDeveloperToolMethods.GET_OLD_AUTH_KEY);

                List toSend = new ArrayList();
                toSend.add(masterDRM.getLastFieldValueAt("serverSystemId"));

                toSend = EMCWSManager.executeGenericWS(cmd, toSend, masterUD);

                if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof String) {
                    emcJDialog oldKeyDialog = new emcJDialog(null, "Old Key for " + masterDRM.getLastFieldValueAt("customerId"));
                    oldKeyDialog.getContentPane().setLayout(new FlowLayout());
                    oldKeyDialog.add(new emcJLabel("Key for " + masterDRM.getLastFieldValueAt("customerId")));
                    emcJTextField txtKey = new emcJTextField();
                    txtKey.setText((String)toSend.get(1));
                    oldKeyDialog.add(txtKey);

                    oldKeyDialog.pack();

                    oldKeyDialog.setVisible(true);
                } else {
                    utilFunctions.logMessage(Level.SEVERE, "Failed to get old authorization key.", masterUD);
                }

            }
        });

        return emcSetGridBagConstraints.createButtonPanel(masterButtons);
    }

    private emcJTabbedPane linesPane() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Connection", linesTablePane());
        return tabbed;
    }

    private emcTablePanelUpdate linesTablePane() {
        List keys = new ArrayList();
        keys.add("connectionDescription");
        keys.add("IP");
        keys.add("port");
        keys.add("userName");
        keys.add("password");
        emcTableModelUpdate model = new emcTableModelUpdate(linesDRM, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        //Lookups
        emcJComboBox connectionType = new emcJComboBox(DevConnectionType.values());
        table.setComboBoxLookupToColumn("connectionDescription", connectionType);
        //Lookups
        linesDRM.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        return tableScroll;
    }
}
