/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.permissions;

import emc.app.components.documents.EMCStringDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJScrollPane;
import emc.app.components.emcJSplitPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcJTree;
import emc.app.components.emcMenuTreeRenderer;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.permissions.PermissionTreeObject;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.base.Usertable;
import emc.forms.base.display.permissions.resources.BasePermissionsInfoDRM;
import emc.forms.base.display.permissions.resources.PermissionInfoMouseAdapter;
import emc.framework.EMCMenuItem;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.users;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JSplitPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author wikus
 */
public class BasePermissionInfoForm extends BaseInternalFrame {

    private EMCUserData userData;
    private BasePermissionsInfoDRM dataManager;

    public BasePermissionInfoForm(EMCUserData userData) {
        super("Permissions Info", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 550);
        this.userData = userData.copyUserDataAndDataList();
        dataManager = new BasePermissionsInfoDRM(userData);
        initFrame();
    }

    private void initFrame() {
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(setupPane(), BorderLayout.NORTH);
        contentPane.add(displayPane(), BorderLayout.CENTER);
        this.setContentPane(contentPane);
    }

    private emcJPanel setupPane() {
        EMCLookup lkpUser = new EMCLookup(new users()) {

            @Override
            public void setValue(Object value) {
                super.setValue(value);
                dataManager.validateUserId();
            }
        };
        lkpUser.setPopup(new EMCLookupPopup(new Usertable(), "userId", userData));
        emcJTextField txtUser = new emcJTextField();
        txtUser.setEditable(false);
        dataManager.setUserLoookup(lkpUser, txtUser);
        Component[][] comp = {{new emcJLabel("User Id"), lkpUser, new emcJLabel("Name"), txtUser}};
        emcJButton btnOK = new emcJButton("OK") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                dataManager.getByUser();
            }
        };
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnOK);
        emcJPanel setupPane = new emcJPanel(new BorderLayout());
        setupPane.add(emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Setup"), BorderLayout.CENTER);
        setupPane.add(emcSetGridBagConstraints.createButtonPanel(buttonList), BorderLayout.EAST);
        return setupPane;
    }

    private emcJSplitPane displayPane() {
        emcJTree permissionsTree = new emcJTree(new DefaultMutableTreeNode("Permissions"));
        permissionsTree.setCellRenderer(new emcMenuTreeRenderer());
        permissionsTree.addMouseListener(new PermissionInfoMouseAdapter(permissionsTree, userData));
        permissionsTree.addTreeSelectionListener(new TreeSelectionListener() {

            @Override
            public void valueChanged(TreeSelectionEvent evt) {
                DefaultMutableTreeNode lastNode = (DefaultMutableTreeNode) evt.getPath().getLastPathComponent();
                if (lastNode.isLeaf()) {
                    if (lastNode.getUserObject() instanceof EMCMenuItem) {
                        dataManager.fetchDetailedPermissions(((EMCMenuItem) lastNode.getUserObject()).getClassPath());
                    }
                }
            }
        });
        dataManager.setPermissionsTree(permissionsTree);
        emcJScrollPane treeScroll = new emcJScrollPane(permissionsTree);

        emcJTextField txtPermission = new emcJTextField(new EMCStringDocument());
        txtPermission.setEditable(false);
        dataManager.setTxtPermissions(txtPermission);

        emcJPanel permissionPane = new emcJPanel(new BorderLayout());
        permissionPane.add(treeScroll, BorderLayout.CENTER);
        permissionPane.add(emcSetGridBagConstraints.createSimplePanel(new Component[][]{{new emcJLabel("Permissions"), txtPermission}}, GridBagConstraints.NONE, false), BorderLayout.SOUTH);

        emcJTabbedPane permissionTabbed = new emcJTabbedPane();
        permissionTabbed.add("Permissions", permissionPane);



        emcJTree detailedPermissionsTree = new emcJTree(new DefaultMutableTreeNode("Details"));
        detailedPermissionsTree.addTreeSelectionListener(new TreeSelectionListener() {

            @Override
            public void valueChanged(TreeSelectionEvent evt) {
                DefaultMutableTreeNode lastNode = (DefaultMutableTreeNode) evt.getPath().getLastPathComponent();
                if (lastNode.isLeaf() && lastNode.getUserObject() instanceof PermissionTreeObject) {
                    dataManager.setDetailedPermissions(((PermissionTreeObject) lastNode.getUserObject()).getPermission());
                } else {
                    dataManager.setDetailedPermissions(null);
                }
            }
        });
        dataManager.setDetailedPermissionsTree(detailedPermissionsTree);
        emcJScrollPane detailedTreeScroll = new emcJScrollPane(detailedPermissionsTree);

        emcJTextField txtDetailedPermission = new emcJTextField(new EMCStringDocument());
        txtDetailedPermission.setEditable(false);
        dataManager.setTxtDetailedPermissions(txtDetailedPermission);

        emcJPanel detailedPermissionPane = new emcJPanel(new BorderLayout());
        detailedPermissionPane.add(detailedTreeScroll, BorderLayout.CENTER);
        detailedPermissionPane.add(emcSetGridBagConstraints.createSimplePanel(new Component[][]{{new emcJLabel("Permissions"), txtDetailedPermission}}, GridBagConstraints.NONE, false), BorderLayout.SOUTH);

        emcJTabbedPane detailedPermissionTabbed = new emcJTabbedPane();
        detailedPermissionTabbed.add("Details", detailedPermissionPane);



        emcJSplitPane leftRightSplit = new emcJSplitPane(JSplitPane.HORIZONTAL_SPLIT, permissionTabbed, detailedPermissionTabbed);
        leftRightSplit.setDividerLocation(300);

        return leftRightSplit;
    }
}
