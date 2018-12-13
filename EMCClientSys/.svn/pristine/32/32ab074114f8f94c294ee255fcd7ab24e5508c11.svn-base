/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.webportalusers;

import emc.app.components.documents.EMCStringDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJPasswordField;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.editors.EMCGoToMainTableEditor;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.menulistbutton.emcMenuButtonList;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.datatypes.EMCDataType;
import emc.entity.base.BaseEmployeeTable;
import emc.entity.base.datasource.BaseWebPortalUsersDS;
import emc.enums.base.webportalusers.WebPortalUsersReferenceType;
import emc.enums.modules.enumEMCModules;
import emc.forms.base.display.webportalusers.resources.ChangePasswordButton;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.base.menuItems.display.employees;
import emc.methods.base.ServerBaseMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;

/**
 * @description Form used to set up web portal users.
 *
 * @version     1.0 6 April 2010
 *
 * @author      Riaan Nel
 */
public class WebPortalUsersForm extends BaseInternalFrame {

    private emcJLabel lblPassword;
    private emcJLabel lblConfirmPassword;
    private emcJPasswordField pwPassword = new emcJPasswordField();
    private emcJPasswordField pwConfirmPassword = new emcJPasswordField();
    private emcDataRelationManagerUpdate drm;

    /** Creates a new instance of WebPortalUsersForm. */
    public WebPortalUsersForm(EMCUserData userData) {
        super("Web Portal Users", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 290);

        drm = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                enumEMCModules.BASE.getId(), new BaseWebPortalUsersDS(), userData), userData);

        this.setDataManager(drm);

        drm.setTheForm(this);
        drm.setFormTextId1("userId");
        drm.setFormTextId2("linkToSourceType");

        initLabels();

        initFrame();
    }

    /** Initializes labels. */
    private void initLabels() {
        this.lblPassword = new emcJLabel(drm.getColumnName("password"));
        this.lblConfirmPassword = new emcJLabel("Confirm Password");
    }

    /** Initializes the frame. */
    private void initFrame() {
        this.setLayout(new BorderLayout());
        emcJTabbedPane tabs = new emcJTabbedPane();
        tabs.add("Web Portal Users", createWebPortalUsersPanel());
        tabs.add("Password", createPasswordPanel());

        this.add(tabs, BorderLayout.CENTER);
        this.add(createButtonsPanel(), BorderLayout.EAST);
    }

    /** Creates the Web Portal Users panel. */
    private emcJPanel createWebPortalUsersPanel() {
        emcJPanel pnlWebPortalUsers = new emcJPanel();

        List keys = new ArrayList();
        keys.add("userId");
        keys.add("linkToSourceType");
        keys.add("sourceRef");
        keys.add("sourceRefDesc");
        keys.add("active");

        emcTableModelUpdate m = new emcTableModelUpdate(drm, keys);
        emcJTableUpdate table = new emcJTableUpdate(m);
        table.setColumnEditable("linkToSourceType", false);
        table.setColumnEditable("sourceRef", false);
        table.setColumnEditable("sourceRefDesc", false);
        table.setColumnEditable("active", false);
        table.setColumnCellEditor("sourceRef", new EMCGoToMainTableEditor(new EMCStringDocument(), null) {

            @Override
            public Component getTableCellEditorComponent(JTable arg0, Object arg1, boolean arg2, int arg3, int arg4) {
                EMCDataType dt = drm.getDataType("sourceRef");
                if (!Functions.checkBlank(drm.getLastFieldValueAt("linkToSourceType"))) {
                    switch (WebPortalUsersReferenceType.fromString((String) drm.getLastFieldValueAt("linkToSourceType"))) {
                        case CAPTURED:
                            super.changeMenuItem(null);
                            break;
                        case LECTURER:
                            super.changeMenuItem(new employees());
                            dt.setRelatedTable(BaseEmployeeTable.class.getName());
                            dt.setRelatedField("employeeNumber");
                            break;

                    }
                }
                return super.getTableCellEditorComponent(arg0, arg1, arg2, arg3, arg4);
            }
        });


        drm.setMainTableComponent(table);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(table);
        pnlWebPortalUsers.setLayout(new GridLayout(1, 1));
        pnlWebPortalUsers.add(topscroll);

        drm.setTablePanel(topscroll);

        return pnlWebPortalUsers;
    }

    /**
     * Creates the password panel.
     */
    private emcJPanel createPasswordPanel() {
        Component[][] components = new Component[][]{
            {lblPassword, pwPassword},
            {lblConfirmPassword, pwConfirmPassword}
        };

        return emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true, "Password");
    }

    /**
     * Create buttons panel.
     */
    private emcJPanel createButtonsPanel() {
        List<emcJButton> buttons = new ArrayList<emcJButton>();
        buttons.add(new ChangePasswordButton(drm, pwPassword, pwConfirmPassword));
        emcMenuButtonList btnActivation = new emcMenuButtonList("Activation", this) {

            @Override
            public void executeCmd(String theCmd) {
                long recordID = (Long) drm.getLastFieldValueAt("recordID");
                if (recordID != 0) {
                    if (theCmd.equals("Activate")) {
                        EMCCommandClass cmd = new EMCCommandClass(ServerBaseMethods.ACTIVATE_WEB_USER);
                        List toSend = new ArrayList();
                        toSend.add(recordID);
                        toSend = EMCWSManager.executeGenericWS(cmd, toSend, drm.getUserData());
                        if (toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1)) {
                            Logger.getLogger("emc").log(Level.INFO, "Web user activated.", drm.getUserData());
                            drm.refreshRecord(drm.getLastRowAccessed());
                        } else {
                            Logger.getLogger("emc").log(Level.SEVERE, "Failed to activate web user.", drm.getUserData());
                        }
                    } else if (theCmd.equals("Deactivate")) {
                        EMCCommandClass cmd = new EMCCommandClass(ServerBaseMethods.DEACTIVATE_WEB_USER);
                        List toSend = new ArrayList();
                        toSend.add(recordID);
                        toSend = EMCWSManager.executeGenericWS(cmd, toSend, drm.getUserData());
                        if (toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1)) {
                            Logger.getLogger("emc").log(Level.INFO, "Web user deactivated.", drm.getUserData());
                            drm.refreshRecord(drm.getLastRowAccessed());
                        } else {
                            Logger.getLogger("emc").log(Level.SEVERE, "Failed to deactivate web user.", drm.getUserData());
                        }
                    } else if (theCmd.equals("Deactivate All")) {
                        EMCCommandClass cmd = new EMCCommandClass(ServerBaseMethods.DEACTIVATE_ALL_WEB_USERS);
                        List toSend = new ArrayList();
                        toSend = EMCWSManager.executeGenericWS(cmd, toSend, drm.getUserData());
                        if (toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1)) {
                            Logger.getLogger("emc").log(Level.INFO, "All web users deactivated.", drm.getUserData());
                            drm.refreshData();
                        } else {
                            Logger.getLogger("emc").log(Level.SEVERE, "Failed to deactivate all web users.", drm.getUserData());
                        }
                    }
                } else {
                    Logger.getLogger("emc").log(Level.SEVERE, "The selected record has not been saved yet.", drm.getUserData());
                }
            }
        };
        btnActivation.addMenuItem("Activate", null, 0, false);
        btnActivation.addMenuItem("Deactivate", null, 0, false);
        btnActivation.addMenuItem("Deactivate All", null, 0, false);
        buttons.add(btnActivation);
        return emcSetGridBagConstraints.createButtonPanel(buttons);
    }
}
