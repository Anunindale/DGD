/*
 * usersform.java
 *
 * Created on 26 September 2007, 10:41
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package emc.forms.base.display.users;

import emc.app.components.EMCFormComboBox;
import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcHelpFile;
import emc.app.components.emcJLabel;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.components.emcJPanel;
import emc.app.components.emcJPasswordField;
import emc.app.components.emcJTextField;
import emc.app.components.emcMenuButton;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.*;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.jtablelookup.EMCLookupTableCellEditor;
import emc.app.util.utilFunctions;
import emc.entity.base.permissions.BasePermissionsTable;
import emc.enums.base.laf.ClientLAF;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.forms.base.display.users.resources.CopyPermissionsButton;
import emc.forms.base.display.users.resources.resetPasswordButton;
import emc.forms.base.display.users.resources.setPassword;
import emc.framework.EMCMenuItem;
import emc.framework.EMCQuery;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.base.menuItems.display.BaseUserFileAssociationsMI;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

/**
 *
 * @author rico
 */
public class usersform extends BaseInternalFrame {

    /**
     * Creates a new instance of usersform
     */
    private usersformDRM dataRelation;
    private emcJPanel passWordPanel = new emcJPanel();
    private emcJPasswordField passwordnew = new emcJPasswordField();
    private emcJPasswordField repeatpassword = new emcJPasswordField();
    private emcJTextField userid = new emcJTextField();
    private emcJLabel pwordnewLabel = new emcJLabel("New password");
    private emcJLabel pwordrepatLabel = new emcJLabel("Confirm password");
    private emcJLabel useridLabel = new emcJLabel("User Id");
    private emcMenuButton setPermission;
    private resetPasswordButton resetPassword;
    private EMCStringFormDocument userIdDoc;
    private setPassword setpwButton;
    private emcJLabel lblPromptUpdate = new emcJLabel("Prompt Update");
    private emcJLabel lblPromptDelete = new emcJLabel("Prompt Delete");
    private emcJLabel lblShowPersonalSpaceOnOpen = new emcJLabel("Show Personal Space On Open");
    private emcJLabel lblAllowClone = new emcJLabel("Allow Clone");
    private emcJLabel lblAllowDelete = new emcJLabel("Allow Delete");
    private emcJLabel lblPrintReportsToPDF;
    private emcYesNoComponent updateComponent;
    private emcYesNoComponent deleteComponent;
    private emcYesNoComponent personalSpaceComponent;
    private emcYesNoComponent allowClone;
    private emcYesNoComponent allowDelete;
    private emcYesNoComponent ysnPrintReportsToPDF;
    private emcYesNoComponent ysnShowImagesOnForm;
    private emcYesNoComponent ysnAllowAutoUpdate;
    EMCLookupJTableComponent lookup;
    EMCLookupTableCellEditor compIdEditor;
    private CopyPermissionsButton btnCopyPermissions;

    //Data Source
    //end data source
    public usersform(final EMCUserData userData) {
        super("Users", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 350);
        this.setHelpFile(new emcHelpFile("Base/Users.html"));
        try {
            //data source
            EMCUserData copyUD = userData.copyUserData();

            lookup = new EMCLookupJTableComponent(new emc.menus.base.menuItems.display.companies());
            //lookupComp keys
            List compkeys = new ArrayList();
            compkeys.add("companyId");
            compkeys.add("companyName");
            EMCLookupPopup compId = new EMCLookupPopup(enumEMCModules.BASE.getId(), new emc.entity.base.BaseCompanyTable(),
                    "companyId", compkeys, copyUD);
            lookup.setPopup(compId);
            compIdEditor = new EMCLookupTableCellEditor(lookup);
            //end dataSource 
            //create DataRelation
            dataRelation = new usersformDRM(new emcGenericDataSourceUpdate(
                    enumEMCModules.BASE.getId(), new emc.entity.base.Usertable(), userData), userData);
            //add data relation to the form
            this.setDataManager(dataRelation);

            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("userId");
            dataRelation.setFormTextId2("userName");

            //set Password button 
            resetPassword = new resetPasswordButton("Reset Password", this.dataRelation);
            btnCopyPermissions = new CopyPermissionsButton(this);
            setPermission = new emcMenuButton("Set Permissions", new emc.menus.base.menuItems.display.UserPermissions(), this, 0, false) {
                @Override
                public void doActionPerformed(ActionEvent e) {
                    String userCompany = (String) dataRelation.getLastFieldValueAt("userCompany");
                    String currentCompany = userData.getCompanyId();

                    if (userCompany != null && currentCompany != null && !userCompany.trim().equalsIgnoreCase(currentCompany.trim())) {
                        int result = EMCDialogFactory.createQuestionDialog(utilFunctions.findEMCDesktop(this), "Set Permission", "You are currently not in the selected users default company. \nAre you sure you want to setup permission for the user in the " + currentCompany + " company?");

                        if (result != JOptionPane.YES_OPTION) {
                            return;
                        }
                    }

                    if (Functions.checkBlank(dataRelation.getLastFieldValueAt("userId"))) {
                        Logger.getLogger("emc").log(Level.SEVERE, "Please save the user record before setting up their permissions.", dataRelation.getUserData());
                    } else {
                        EMCUserData ud = dataRelation.getUserData();
                        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BasePermissionsTable.class);
                        query.addAnd("userId", dataRelation.getLastFieldValueAt("userId"));
                        ud.setUserData(0, query);
                        dataRelation.getPermissionsDRM().setUserData(ud);
                    }

                    BaseInternalFrame permission = dataRelation.getTheForm().getDeskTop().isInDesktop(emc.forms.base.display.usermenusetup.userMenuSetup.class);
                    if (permission != null) {
                        dataRelation.getRelatedForms().remove(permission);
                        dataRelation.setDoFormRelation(true);
                        permission.dispose();
                    }
                    super.doActionPerformed(e);
                }
            };

            //create and set documents
            userIdDoc = new EMCStringFormDocument(dataRelation, "userId");
            userid.setDocument(userIdDoc);
            initLabels();
            initDisplay();
            dataRelation.setPermission(setPermission);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Initializes labels.
     */
    private void initLabels() {
        lblPrintReportsToPDF = new emcJLabel(dataRelation.getColumnName("printReportsToPDF"));
    }

    private void createPasswordPanel() {
        passWordPanel.setLayout(new GridBagLayout());
        passWordPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Set Password"));
        int y = 0;
        GridBagConstraints localg = new GridBagConstraints();

        localg = null;
        localg = new GridBagConstraints();
        y++;
        localg.gridx = 0;
        localg.gridy = y;
        localg.weightx = 0.1;
        localg.anchor = GridBagConstraints.LINE_START;
        passWordPanel.add(this.useridLabel, localg);
        localg.gridy = y;
        localg.gridx = 1;
        localg.weightx = 0.9;
        localg.anchor = GridBagConstraints.LINE_END;
        localg.fill = GridBagConstraints.HORIZONTAL;
        localg.gridwidth = GridBagConstraints.REMAINDER;
        userid.setEditable(false);
        userid.setPreferredSize(new java.awt.Dimension(150, 25));
        passWordPanel.add(this.userid, localg);

        localg = null;
        localg = new GridBagConstraints();
        y++;
        localg.gridx = 0;
        localg.gridy = y;
        localg.weightx = 0.1;
        localg.anchor = GridBagConstraints.LINE_START;
        passWordPanel.add(this.pwordnewLabel, localg);
        localg.gridy = y;
        localg.gridx = 1;
        localg.weightx = 0.9;
        localg.anchor = GridBagConstraints.LINE_END;
        localg.fill = GridBagConstraints.HORIZONTAL;
        localg.gridwidth = GridBagConstraints.REMAINDER;
        passwordnew.setPreferredSize(new java.awt.Dimension(150, 25));
        passWordPanel.add(this.passwordnew, localg);

        localg = null;
        localg = new GridBagConstraints();
        y++;
        localg.gridx = 0;
        localg.gridy = y;
        localg.weightx = 0.1;
        localg.anchor = GridBagConstraints.LINE_START;
        passWordPanel.add(this.pwordrepatLabel, localg);
        localg.gridy = y;
        localg.gridx = 1;
        localg.weightx = 0.9;
        localg.anchor = GridBagConstraints.LINE_END;
        localg.fill = GridBagConstraints.HORIZONTAL;
        localg.gridwidth = GridBagConstraints.REMAINDER;
        repeatpassword.setPreferredSize(new java.awt.Dimension(150, 25));
        passWordPanel.add(this.repeatpassword, localg);

        localg = null;
        localg = new GridBagConstraints();
        y++;
        localg.gridx = 0;
        localg.gridy = y;
        localg.weightx = 0.1;
        localg.anchor = GridBagConstraints.LINE_START;
        emcJLabel blank = new emcJLabel();
        blank.setPreferredSize(new java.awt.Dimension(150, 25));
        passWordPanel.add(blank, localg);

        localg = null;
        localg = new GridBagConstraints();
        y++;
        localg.gridx = 1;
        localg.gridy = y;
        localg.weightx = 0.1;
        localg.anchor = GridBagConstraints.LINE_START;
        setpwButton = new setPassword("Set Password", this.dataRelation, this.passwordnew, this.repeatpassword);
        setpwButton.setPreferredSize(new java.awt.Dimension(150, 25));
        passWordPanel.add(setpwButton, localg);
        y++;
        passWordPanel.add(new emcJLabel(), emcSetGridBagConstraints.endPanel(y));

    }

    private emcJPanel createPreferencesPanel() {
        updateComponent = new emcYesNoComponent(dataRelation, "promptUpdate");
        deleteComponent = new emcYesNoComponent(dataRelation, "promptDelete");
        personalSpaceComponent = new emcYesNoComponent(dataRelation, "showPersonalSpaceOnOpen");
//        allowClone = new emcYesNoComponent(dataRelation, "allowClone");
//        allowDelete = new emcYesNoComponent(dataRelation, "allowDelete");
        ysnPrintReportsToPDF = new emcYesNoComponent(dataRelation, "printReportsToPDF");
        ysnShowImagesOnForm = new emcYesNoComponent(dataRelation, "showImagesOnForm");
        ysnAllowAutoUpdate = new emcYesNoComponent(dataRelation, "allowAutoUpdate");
        EMCFormComboBox cmbLAF = new EMCFormComboBox(ClientLAF.values(), dataRelation, "clientLAF");

        Component[][] components = {
            {lblPromptUpdate, updateComponent},
            {lblPromptDelete, deleteComponent},
            {lblShowPersonalSpaceOnOpen, personalSpaceComponent},
            {lblPrintReportsToPDF, ysnPrintReportsToPDF},
            {new emcJLabel("Show Images On Form"), ysnShowImagesOnForm},
            {new emcJLabel("Allow Auto Update"), ysnAllowAutoUpdate},
            {new emcJLabel("Look And Feel"), cmbLAF}
        };

        emcJPanel CenterPane = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, false);

        components = new Component[][]{
            {new emcJLabel("<html>The Allow Clone and Allow Delete Preferences<br /> can be found on the Set Permissions form</html>", false)}
        };

        emcJPanel SouthPane = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, false);


        emcJPanel thePanel = new emcJPanel(new BorderLayout());
        thePanel.add(CenterPane, BorderLayout.CENTER);
        thePanel.add(SouthPane, BorderLayout.SOUTH);
        thePanel.setBorder(BorderFactory.createTitledBorder("User Preferences"));

        return thePanel;
    }

    private void initDisplay() {
        createPasswordPanel();
        List keys = new ArrayList();
        keys.add("userId");
        keys.add("userName");
        keys.add("userCompany");
        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation, keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        toptable.setLookupCellEditor(2, this.compIdEditor);
        emcJPanel top = new emcJPanel();
        emcJPanel topright = new emcJPanel();
        topright.setLayout(new GridBagLayout());
        topright.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        dataRelation.setMainTableComponent(toptable);
        GridBagConstraints e = new GridBagConstraints();
        e.gridx = 0;
        e.gridy = 0;
        e.weightx = 1.0;
        e.anchor = GridBagConstraints.LINE_START;
        e.fill = GridBagConstraints.HORIZONTAL;
        e.gridwidth = GridBagConstraints.REMAINDER;
        topright.add(setPermission, e);

        e.gridy = 1;
        topright.add(resetPassword, e);

        e.gridy = 2;
        topright.add(btnCopyPermissions, e);

        EMCMenuItem fileAssociationsMenu = new BaseUserFileAssociationsMI();
        fileAssociationsMenu.setDoNotOpenForm(false);
        e.gridy = 3;
        topright.add(new emcMenuButton("File Associations", fileAssociationsMenu, this, 1, false), e);

        topright.add(new emcJLabel(), emcSetGridBagConstraints.endPanel(4));
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        this.setTablePanel(topscroll);

        //tabbed pane
        JTabbedPane tabbedPanetop = new JTabbedPane();
        tabbedPanetop.addTab("Users", topscroll);
        tabbedPanetop.addTab("Set Password", passWordPanel);
        tabbedPanetop.addTab("Preferences", createPreferencesPanel());

        GridBagConstraints c = new GridBagConstraints();
        GridBagConstraints d = new GridBagConstraints();

        top.setLayout(new GridBagLayout());
        c.gridx = 0;
        c.gridy = 0;
        //c.anchor = GridBagConstraints.PAGE_START;
        // c.anchor = GridBagConstraints.PAGE_START;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        top.add(tabbedPanetop, c);
        d.gridx = 1;
        d.gridy = 0;
        d.weighty = 1.0;
        d.fill = GridBagConstraints.BOTH;
        top.add(topright, d);
        //bottom part

        this.add(top, BorderLayout.CENTER);
    }
}
