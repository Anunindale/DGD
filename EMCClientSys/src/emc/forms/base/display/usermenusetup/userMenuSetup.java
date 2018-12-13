/*
 * userMenuSetup.java
 *
 * Created on 24 October 2007, 05:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package emc.forms.base.display.usermenusetup;

import emc.app.components.emcHelpFile;
import emc.app.components.emcJButton;
import emc.app.components.emcJComboBox;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJScrollPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTree;
import emc.app.components.emcMenuTreeRenderer;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcYesNoComponent;
import emc.app.config.emcicons;
import emc.app.permissions.PermissionConstants;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.forms.base.display.usermenusetup.resources.DecodeUserTree;
import emc.forms.base.display.usermenusetup.resources.EncodeUserTree;
import emc.app.permissions.PermissionTreeObject;
import emc.app.util.utilFunctions;
import emc.constants.systemConstants;
import emc.entity.base.permissions.BaseUserPermissionsTable;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.forms.base.display.permissions.resources.PermissionInfoMouseAdapter;
import emc.forms.base.display.usermenusetup.resources.permissions.FormPermissionsTreeSelectionListener;
import emc.forms.base.display.usermenusetup.resources.permissions.PermissionsComboboxManager;
import emc.forms.base.display.usermenusetup.resources.permissions.PermissionsDRM;
import emc.forms.base.display.usermenusetup.resources.permissions.UserPermissionsTreeSelectionListener;
import emc.forms.base.display.usermenusetup.resources.userMenuTreeEventHandler;
import emc.forms.base.display.users.usersformDRM;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityClass;
import emc.framework.EMCMenuItem;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.xml.EMCXMLHandler;
import emc.app.permissions.ButtonPermissions;
import emc.app.permissions.FieldPermissions;
import emc.app.permissions.FormPermissions;
import emc.app.permissions.Permission;
import emc.app.permissions.PermissionTypes;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author rico
 */
public class userMenuSetup extends BaseInternalFrame {

    //Class variables
    //The trees displayed on the form
    private emcJTree adminTree;
    private emcJTree userTree;
    //User permissions tree.  Shares model with user tree
    private emcJTree userPermissionsTree;
    private emcJTree formPermissionsTree;
    //Labels used on form
    private emcJLabel lblAdmin = new emcJLabel("Admin Menu");
    private emcJLabel lblUser = new emcJLabel("Menu");
    private emcJLabel lblUserPermissions = new emcJLabel("Permissions");
    private emcJLabel lblFormPermissions = new emcJLabel("Form Permissions");
    //User id
    private String userId;
    //Tree nodes used to construct the trees on the form
    private DefaultMutableTreeNode adminRoot;
    private DefaultMutableTreeNode userRoot;
    DefaultMutableTreeNode formPermissionsRoot;
    //Panels used on the form
    private emcJPanel pnlButtons = new emcJPanel();
    private emcJPanel pnlAdmin = new emcJPanel();
    private emcJPanel pnlUser = new emcJPanel();
    private emcJPanel pnlUserPermissions = new emcJPanel();
    private emcJPanel pnlFormPermissions = new emcJPanel();
    private emcJPanel pnlPermissionSetup = new emcJPanel();
    //Buttons
    private emcJButton btnToUser = new emcJButton() {

        @Override
        public void doActionPerformed(ActionEvent evt) {
        }
    };
    private emcJButton btnToAdmin = new emcJButton() {

        @Override
        public void doActionPerformed(ActionEvent evt) {
        }
    };
    private emcJButton btnUp = new emcJButton() {

        @Override
        public void doActionPerformed(ActionEvent evt) {
        }
    };
    private emcJButton btnDown = new emcJButton() {

        @Override
        public void doActionPerformed(ActionEvent evt) {
        }
    };
    //Scrollpanes used for trees
    private emcJScrollPane adminPane;
    private emcJScrollPane userPane;
    private emcJScrollPane userPermissionsPane;
    private emcJScrollPane formPermissionsPane;
    //Tree models used by trees
    private DefaultTreeModel adminModel;
    private DefaultTreeModel userModel;
    private DefaultTreeModel formPermissionsTreeModel;
    //Used for dragging
    boolean mouseOnAdminTree = false;
    boolean mouseOnUserTree = false;
    //All nodes in the admin tree
    ArrayList<DefaultMutableTreeNode> adminNodes;
    //All nodes in the user tree
    ArrayList<DefaultMutableTreeNode> userNodes;
    userMenuSetupDRM dataRelation;
    //tree changed listner for userTreeModel
    userMenuTreeEventHandler userMenuTreeEvent = new userMenuTreeEventHandler();
    private PermissionsDRM permissionsDRM;
    private EMCUserData permissionsUserData;
    private emcJComboBox userPermissionsCombobox = new emcJComboBox(new String[]{FormPermissions.NO_ACCESS.toString(), FormPermissions.VIEW.toString(), FormPermissions.EDIT.toString(), FormPermissions.CREATE.toString()});
    private emcJComboBox formPermissionsCombobox = new emcJComboBox();
    private PermissionsComboboxManager permissionsComboboxManager;
    //We need this to pass the correct classname to the DRM when saving.
    private String lastSelectedClassName = null;
    //This is used to check whether the selection in the form combobox has changed before updating permissions
    private String lastFormPermission;

    /** Creates a new instance of userMenuSetup */
    public userMenuSetup(EMCUserData userData) {
        super("User Menu Setup - " + userData.getCompanyId(), true, true, true, true, userData);
        this.setHelpFile(new emcHelpFile("Base/SystemUserPermissions.html"));
        //construct Admin tree
        adminRoot = new DefaultMutableTreeNode("EMC");
        this.adminRoot = emc.app.util.populateMenu.getNode(adminRoot, new emc.menus.mainMenu());
        this.userRoot = null;

        Dimension userLabelSize = new Dimension(125, 25);

        lblUser.setMaximumSize(userLabelSize);
        lblUser.setMinimumSize(userLabelSize);
        lblAdmin.setMaximumSize(userLabelSize);
        lblAdmin.setMinimumSize(userLabelSize);
        lblUserPermissions.setMaximumSize(userLabelSize);
        lblUserPermissions.setMinimumSize(userLabelSize);
        lblFormPermissions.setMaximumSize(userLabelSize);
        lblFormPermissions.setMinimumSize(userLabelSize);

        permissionsUserData = userData.copyUserData();
        permissionsDRM = new PermissionsDRM(new emcGenericDataSourceUpdate(enumEMCModules.BASE.getId(), new BaseUserPermissionsTable(), permissionsUserData), permissionsUserData, this);
        permissionsDRM.setTheForm(this);
        permissionsDRM.setFormTextId1("formClassName");
        permissionsDRM.setFormTextId2("formAccess");

        createTrees();

        //Balance the trees
        balanceTree();
        dataRelation = new userMenuSetupDRM(null, userData, this);
        this.setDataManager(dataRelation);
        userMenuTreeEvent.setDRM(dataRelation);
        dataRelation.setUserData(userData);
        //Set focus on data relation
        dataRelation.setHasTheFocus(true);

        initDisplay();
    }

    private void createTrees() {
        //If userRoot equals null, create new empty menu
        if (userRoot == null) {
            this.userRoot = createNewUserRoot();
            this.userRoot.add(new DefaultMutableTreeNode(new emc.menus.mainMenu()));
        }

        this.adminTree = new emcJTree();
        this.adminModel = new DefaultTreeModel(adminRoot);
        this.adminTree.setModel(this.adminModel);
        this.adminTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        this.userTree = new emcJTree();
        this.userModel = new DefaultTreeModel(this.userRoot);
        this.userTree.setModel(this.userModel);
        this.userTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        this.userModel.addTreeModelListener(userMenuTreeEvent);
        //Selects the root of the admin tree
        adminTree.setSelectionRow(0);

        this.userPermissionsTree = new emcJTree();
        this.userPermissionsTree.setModel(userModel);
        this.userPermissionsTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        //Should not be populated initially.
        this.formPermissionsTree = new emcJTree();
        this.formPermissionsTree.setModel(null);
    }

    private void initDisplay() {
        this.setBounds(20, 20, 550, 400);
        emcMenuTreeRenderer renderer = new emcMenuTreeRenderer();

        adminTree.setCellRenderer(renderer);
        adminPane = new emcJScrollPane(adminTree);
        adminPane.setViewportView(adminTree);

        userTree.setCellRenderer(renderer);
        userPane = new emcJScrollPane(userTree);
        userPane.setViewportView(userTree);

        userPermissionsTree.setCellRenderer(renderer);
        userPermissionsPane = new emcJScrollPane(userPermissionsPane);
        userPermissionsPane.setViewportView(userPermissionsTree);

        formPermissionsTree.setCellRenderer(renderer);
        formPermissionsPane = new emcJScrollPane(formPermissionsTree);
        formPermissionsPane.setViewportView(formPermissionsTree);

        //Adds focus listeners to the trees
        userTree.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent evt) {
                //Select the root node by default if no node is selected
                if (userTree.getLastSelectedPathComponent() == null) {
                    userTree.setSelectionRow(0);
                }

                //Enables the to admin button
                btnToAdmin.setEnabled(true);

                //Disables the to user button
                btnToUser.setEnabled(false);

                //Clears the selection on the admin tree
                adminTree.setSelectionPath(null);
            }
        });

        adminTree.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent evt) {
                TreePath permissionPath = userPermissionsTree.getSelectionPath();
                if (permissionPath != null) {
                    //Focus came from other tab.  Put it on user tree
                    userTree.setSelectionPath(permissionPath);
                    //Clear selection on userPermissions tree
                    userPermissionsTree.setSelectionPath(null);
                } else {
                    //Select the root node by default if no node is selected
                    if (adminTree.getLastSelectedPathComponent() == null) {
                        adminTree.setSelectionRow(0);
                    }

                    //Disables the to admin button
                    btnToAdmin.setEnabled(false);

                    //Enables the to user button
                    btnToUser.setEnabled(true);


                    //Clears the selection on the user tree
                    userTree.setSelectionPath(null);
                }
            }
        });

        //Adds mouse listeners to the trees
        userTree.addMouseListener(new PermissionInfoMouseAdapter(userTree, permissionsUserData) {

            @Override
            public void mouseClicked(MouseEvent evt) {
                super.mouseClicked(evt);
                //Checks whether the user double clicked
                if (evt.getClickCount() == 2) {
                    userToAdmin();
                }
            }

            @Override
            public void mouseReleased(MouseEvent evt) {

                //Checks whether the mouse was released on the admin tree
                if (mouseOnAdminTree) {
                    userToAdmin();
                }
                setDragCursor(false);
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                setDragCursor(true);
            }

            @Override
            public void mouseEntered(MouseEvent evt) {
                //The mouse is over the user tree
                mouseOnUserTree = true;
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                //The mouse is not over the user tree
                mouseOnUserTree = false;
            }
        });
        adminTree.addMouseListener(new PermissionInfoMouseAdapter(adminTree, permissionsUserData) {

            @Override
            public void mouseClicked(MouseEvent evt) {
                super.mouseClicked(evt);
                //Checks whether the user double clicked
                if (evt.getClickCount() == 2) {
                    adminToUser();
                }
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                //Checks whether the mouse was released on the user tree
                if (mouseOnUserTree) {
                    adminToUser();
                }
                setDragCursor(false);
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                setDragCursor(true);
            }

            @Override
            public void mouseEntered(MouseEvent evt) {
                //The mouse is over the admin tree
                mouseOnAdminTree = true;
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                //The mouse is not over the admin tree
                mouseOnAdminTree = false;
            }
        });

        //Sets the icons of the buttons
        btnDown.setIcon(new ImageIcon(getClass().getResource(emcicons.getDownArrow())));
        btnUp.setIcon(new ImageIcon(getClass().getResource(emcicons.getUpArrow())));
        btnToUser.setIcon(new ImageIcon(getClass().getResource(emcicons.getNextPage())));
        btnToAdmin.setIcon(new ImageIcon(getClass().getResource(emcicons.getPrevPage())));

        //Disables the to admin and to user buttons

        //Adds listeners to the buttons        
        btnToAdmin.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent evt) {
                userToAdmin();
            }
        });

        btnToUser.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent evt) {
                adminToUser();
            }
        });

        btnUp.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent evt) {
                //Checks which tree is selected
                if (userTree.getLastSelectedPathComponent() != null) {
                    moveNodeUp(userTree);
                } else {
                    moveNodeUp(adminTree);
                }
            }
        });

        btnDown.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent evt) {
                //Checks which tree is selected
                if (userTree.getLastSelectedPathComponent() != null) {
                    moveNodeDown(userTree);
                } else {
                    moveNodeDown(adminTree);
                }
            }
        });

        permissionsComboboxManager = new PermissionsComboboxManager(this, userPermissionsCombobox, formPermissionsCombobox, permissionsDRM, formPermissionsTree);
        //Disable comboboxes initially
        userPermissionsCombobox.setEnabled(false);
        formPermissionsCombobox.setEnabled(false);

        userPermissionsCombobox.addActionListener(permissionsComboboxManager);
        formPermissionsCombobox.addActionListener(permissionsComboboxManager);
        //Permissions DRM must interact with combobox manager
        permissionsDRM.setPermissionsComboBoxManager(permissionsComboboxManager);

        userPermissionsTree.addTreeSelectionListener(new UserPermissionsTreeSelectionListener(this, permissionsDRM, permissionsComboboxManager));
        formPermissionsTree.addTreeSelectionListener(new FormPermissionsTreeSelectionListener(this, permissionsComboboxManager));

        emcJTabbedPane tabs = new emcJTabbedPane();
        tabs.add("User Menu Setup", createUserMenuSetupTab());
        tabs.add("Permission Setup", createPermissionsTab());
        tabs.add("User Preferences", createPreferencesTab());

        tabs.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                //Permissions tab selected.  "Copy" selection.
                if (((emcJTabbedPane) e.getSource()).getSelectedIndex() == 1) {
                    TreePath selectionPath = userTree.getSelectionPath();
                    userPermissionsTree.setSelectionPath(selectionPath);
                    userPermissionsTree.scrollPathToVisible(userPermissionsTree.getSelectionPath());

                    if (selectionPath != null) {
                        DefaultMutableTreeNode lastNode = (DefaultMutableTreeNode) selectionPath.getLastPathComponent();

                        Object userObject = lastNode.getUserObject();
                        if (userObject instanceof EMCMenuItem) {
                            setLastSelectedClassName(((EMCMenuItem) userObject).getClassPath());
                        }
                    }
                } else {
                    persistFormPermissions();
                }
            }
        });
        userPermissionsTree.addMouseListener(new PermissionInfoMouseAdapter(userPermissionsTree, permissionsUserData) {

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 3 && e.isShiftDown() && e.isAltDown() && e.isControlDown()) {
                    Logger.getLogger("emc").log(Level.INFO, "Fixing Permissions ", permissionsUserData);
//                    fixPermissions((DefaultMutableTreeNode) userPermissionsTree.getModel().getRoot());
                    Logger.getLogger("emc").log(Level.INFO, "Permissions Fixed", permissionsUserData);
                }
            }
        });

        this.add(tabs, BorderLayout.CENTER);
    }

    //This method retrieves the distance the user tree and the admin tree
    private int getDistanceBetweenTrees() {
        //Return the distance
        return (userTree.getX() - adminTree.getWidth());
    }

    //This method is used to balance the two trees
    private void balanceTree() {
        //Compares all leaves in the user tree to all nodes in the admin tree, starting at the level just below the root of the user tree
        DefaultMutableTreeNode curUserNode = (DefaultMutableTreeNode) userRoot.getNextNode();

        //Checks each user node against all admin nodes
        while (curUserNode != null) {

            //Checks if the current user node is a leaf
            if (curUserNode.isLeaf()) {
                //Starts at the level just below the root of the user tree
                DefaultMutableTreeNode curAdminNode = (DefaultMutableTreeNode) adminRoot.getFirstChild();

                //Checks each admin node
                while (curAdminNode != null) {

                    //Compares the nodes and checks if the node is a leaf in both trees
                    if (curAdminNode.getUserObject().getClass().equals(curUserNode.getUserObject().getClass())) {
                        //Check whether the node in the admin tree is a leaf
                        if (curAdminNode.isLeaf()) {
                            //Remove the admin node
                            adminModel.removeNodeFromParent(curAdminNode);
                        } else {
                            //The user leaf is a branch on the admin tree
                            userModel.removeNodeFromParent(curUserNode);
                        }
                    }

                    //Gets the next node in the admin tree
                    curAdminNode = (DefaultMutableTreeNode) curAdminNode.getNextNode();
                }
            }

            //Gets the next node in the user tree
            curUserNode = (DefaultMutableTreeNode) curUserNode.getNextNode();
        }
    }

    //Returns a new adminRoot
    private DefaultMutableTreeNode recreateAdminRoot() {
        DefaultMutableTreeNode defaultAdminRoot = new DefaultMutableTreeNode("EMC");
        return emc.app.util.populateMenu.getNode(defaultAdminRoot, new emc.menus.mainMenu());
    }

    //Creates a new DefaultMutableTreeNode containing only the "EMC" and "Main Menu" nodes
    private DefaultMutableTreeNode createNewUserRoot() {
        //The node that will be returned
        DefaultMutableTreeNode newUserRoot = new DefaultMutableTreeNode("EMC");

        //Adds the main menu item
        newUserRoot.add(new DefaultMutableTreeNode(new emc.menus.mainMenu()));

        //Returns the new user root
        return newUserRoot;
    }

    //Removes a node and all empty parents of the node
    private void removeNode(DefaultMutableTreeNode theNode) {
        //The parent node of theNode
        DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) theNode.getParent();

        //The number of children that the node has
        boolean isLast = parentNode.getChildCount() != 1 ? false : true;

        //Remove the node
        theNode.removeFromParent();

        //Checks if any siblings are left after removing the node and whether this node is at least 2 levels deep
        if (isLast && parentNode.getLevel() > 1) {
            //Removes the parent node
            removeNode(parentNode);
        }
    }

    //Gets the parent node of the specified node in the specified tree
    private DefaultMutableTreeNode findParentInTree(DefaultMutableTreeNode theNode, emcJTree theTree) {
        //The node which will be returned
        DefaultMutableTreeNode returnNode = null;

        //The parent of the given node
        DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) theNode.getParent();

        //The model of the given tree
        DefaultTreeModel theModel = (DefaultTreeModel) theTree.getModel();

        //A node in the admin tree
        DefaultMutableTreeNode adminNode = (DefaultMutableTreeNode) theModel.getRoot();

        //Checks all nodes in the admin tree
        while (adminNode != null) {
            //Check if the node is equal to the one we want
            if (adminNode.getUserObject().getClass().equals(parentNode.getUserObject().getClass())) {
                //Assigns this node to returnNode
                returnNode = adminNode;

                //Break from the loop
                break;
            }

            //Gets the next node
            adminNode = (DefaultMutableTreeNode) adminNode.getNextNode();
        }

        //Return returnNode
        return returnNode;
    }

    //Used to change the mouse cursor when dragging a node
    private void setDragCursor(boolean enabled) {
        //Checks whether the drag cursor should be enabled
        if (enabled) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }
    //Returns a list of all the children of the specified node

    public ArrayList<DefaultMutableTreeNode> listChildren(DefaultMutableTreeNode theNode) {
        //The list to return
        ArrayList<DefaultMutableTreeNode> childList = new ArrayList<DefaultMutableTreeNode>();

        //A child of the given node
        DefaultMutableTreeNode child;

        //An enumeration of this nodes' child nodes
        Enumeration children = theNode.children();

        //Iterates through all of the given node's children
        while (children.hasMoreElements()) {
            //Assign the next element to child
            child = (DefaultMutableTreeNode) children.nextElement();

            //Adds child to the list
            childList.add(child);
        }

        //Return the list
        return childList;
    }

    //Gets the a duplicate of the specified node in the specified tree
    private DefaultMutableTreeNode findDuplicateInTree(DefaultMutableTreeNode theNode, emcJTree theTree) {
        //The node which will be returned
        DefaultMutableTreeNode duplicate = new DefaultMutableTreeNode();

        //The user object of the  parent node of the given node
        Object nodeObject = theNode.getUserObject();

        //Gets the model of the specified tree
        DefaultTreeModel theModel = (DefaultTreeModel) theTree.getModel();

        //The node being checked
        DefaultMutableTreeNode curNode = (DefaultMutableTreeNode) theModel.getRoot();

        //Checks all nodes in the tree
        while (curNode != null) {
            //Checks if this is the correct node
            if (curNode.getUserObject().getClass().equals(nodeObject.getClass())) {
                //Returns the node
                return curNode;
            }

            //Gets the next node
            curNode = (DefaultMutableTreeNode) curNode.getNextNode();
        }

        //Return the node
        return duplicate;
    }

    //This method is used to save a list of all the expanded nodes in a tree
    private ArrayList<TreePath> getTreeExpandedNodes(JTree theTree) {
        //The list to return
        ArrayList<TreePath> retList = new ArrayList<TreePath>();

        //A node in the tree
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) theTree.getModel().getRoot();

        //The tree path of a node in the tree 
        TreePath nodePath = null;

        //Loops through all of the nodes in the tree
        while (node != null) {
            //Checks if the node is a branch
            if (!node.isLeaf()) {
                //Get the treepath of the node
                nodePath = new TreePath(node.getPath());

                //Checks if the node is expanded
                if (theTree.isExpanded(nodePath)) {
                    //Adds this node path to the list to return
                    retList.add(nodePath);
                }
            }

            //Gets the next node
            node = node.getNextNode();
        }

        //Return the list
        return retList;
    }

    //This method is used to expand a number of nodes on the given tree
    private void setTreeExpandedNodes(JTree theTree, ArrayList<TreePath> paths) {
        //Loops through the given list
        for (TreePath path : paths) {
            //the last node in the path
            DefaultMutableTreeNode lastPathNode = (DefaultMutableTreeNode) path.getLastPathComponent();

            //A node in the tree
            DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) theTree.getModel().getRoot();

            //Attempt to find the path in the tree
            while (treeNode != null) {
                //Checks this node against the ArrayList
                if (lastPathNode.getUserObject().getClass().equals(treeNode.getUserObject().getClass())) {
                    //Sets this node visible in the tree
                    theTree.expandPath(path);
                }

                //Gets the next node in the tree
                treeNode = treeNode.getNextNode();
            }
        }
    }

    //This method is used to move an item fom the user tree to the admin tree 
    private void userToAdmin() {
        //Gets the selected node in the user tree
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) userTree.getLastSelectedPathComponent();

        //Checks the level of the selected node
        if (selectedNode != null && selectedNode.getLevel() > 1) {

            //Gets the selected path in the tree
            TreePath userSelectedPath = userTree.getSelectionPath();

            //The node to be inserted into the admin tree
            DefaultMutableTreeNode insertNode = cloneNode(selectedNode);

            //Check whether the node is a leaf
            if (selectedNode.isLeaf()) {
                //Find the nodes' parent in the adminTree
                DefaultMutableTreeNode parentNode = findParentInTree(selectedNode, adminTree);

                //Insert the copy into its new parent
                adminModel.insertNodeInto(insertNode, parentNode, 0);


            } else {
                //Find the node in the admin tree
                insertNode = findDuplicateInTree(selectedNode, adminTree);

                //Gets all the children of the given node and inserts them into the duplicate node
                ArrayList<DefaultMutableTreeNode> childNodes = listChildren(selectedNode);

                //Gets all children of the new node
                ArrayList<DefaultMutableTreeNode> newParentChildren = listChildren(insertNode);

                //Removes all the children of the parent node
                //insertNode.setAllowsChildren(false);
                //insertNode.setAllowsChildren(true);

                //Adds all the child nodes to the new parent
                for (DefaultMutableTreeNode child : childNodes) {
                    //Checks if the node is a branch
                    if (!child.isLeaf()) {
                        //Removes the identical node in the new tree if it is a leaf
                        for (DefaultMutableTreeNode newParentChild : newParentChildren) {
                            if (child.getUserObject().toString().equals(newParentChild.getUserObject().toString())) {
                                if (newParentChild.isLeaf()) {
                                    //Remove the node
                                    newParentChild.removeFromParent();
                                    //Break from the loop
                                    break;
                                }
                            }
                        }
                    }

                    //Adds the child to it's new parent
                    insertNode.insert(child, insertNode.getChildCount());
                }
            }

            //Remove the selected node
            removeNode(selectedNode);

            //Gets the expanded nodes in the trees before reloading the models
            ArrayList<TreePath> userExpandedNodes = getTreeExpandedNodes(userTree);
            ArrayList<TreePath> adminExpandedNodes = getTreeExpandedNodes(adminTree);

            //Reload the tree models
            this.adminModel.reload();
            this.userModel.reload();

            //Expands nodes
            setTreeExpandedNodes(userTree, userExpandedNodes);
            setTreeExpandedNodes(adminTree, adminExpandedNodes);

            //Sets the selected path on the tree
            userTree.setSelectionPath(userSelectedPath);

            //Opens the path to the new node
            adminTree.scrollPathToVisible(new TreePath(insertNode.getPath()));
        }
    }

    private void adminToUser() {

        //Gets the selected node in the admin tree
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) adminTree.getLastSelectedPathComponent();

        //Checks the level of the selected node
        if (selectedNode != null && selectedNode.getLevel() > 1) {

            //Gets the selected path
            TreePath adminSelectedPath = adminTree.getSelectionPath();

            DefaultMutableTreeNode insertNode;

            List branchestoTop = new ArrayList();
            findupToTopBranch(branchestoTop, selectedNode);
            DefaultMutableTreeNode curUserNode = userRoot;
            //search userTree for position
            for (int j = 1; j < branchestoTop.size(); j++) {
                curUserNode = searchAndCreateBranch((DefaultMutableTreeNode) branchestoTop.get(j), curUserNode);
            }
            //test for leaf
            if (selectedNode.isLeaf()) {
                //test if node exists on user side
                Enumeration en = curUserNode.children();
                while (en.hasMoreElements()) {
                    DefaultMutableTreeNode child = (DefaultMutableTreeNode) en.nextElement();
                    if (child.getUserObject().toString().equals(selectedNode.getUserObject().toString())) {
                        return;
                    }
                }
                Object userObj = createFromPath(selectedNode.getUserObject().getClass().getName());
                insertNode = new DefaultMutableTreeNode(userObj);
                curUserNode.add(insertNode);
                selectedNode.removeFromParent();
            } else {
                //test if node exists on user side
                Enumeration en = curUserNode.children();
                while (en.hasMoreElements()) {
                    DefaultMutableTreeNode child = (DefaultMutableTreeNode) en.nextElement();
                    if (child.getUserObject().toString().equals(selectedNode.getUserObject().toString())) {
                        return;
                    }
                }
                //first copy the branch selected
                insertNode = new DefaultMutableTreeNode(selectedNode.getUserObject());
                curUserNode.add(insertNode);
                copyBranch(selectedNode, curUserNode.getLastLeaf());
            }

            //Gets the expanded nodes in the trees before reloading the models
            ArrayList<TreePath> userExpandedNodes = getTreeExpandedNodes(userTree);
            ArrayList<TreePath> adminExpandedNodes = getTreeExpandedNodes(adminTree);

            //Reload the tree models
            this.adminModel.reload();
            this.userModel.reload();

            //Expands nodes
            setTreeExpandedNodes(userTree, userExpandedNodes);
            setTreeExpandedNodes(adminTree, adminExpandedNodes);
            //Sets the selected path on the tree
            adminTree.setSelectionPath(adminSelectedPath);

            //Opens the path to the new node
            userTree.scrollPathToVisible(new TreePath(insertNode.getPath()));
        }
    }

    private void copyBranch(DefaultMutableTreeNode node, DefaultMutableTreeNode userNode) {
        Enumeration en = node.children();
        List leafNodes = new ArrayList();
        while (en.hasMoreElements()) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) en.nextElement();
            if (child.isLeaf()) {
                Object userObj = createFromPath(child.getUserObject().getClass().getName());
                userNode.add(new DefaultMutableTreeNode(userObj));
                leafNodes.add(child);
            } else {
                userNode.add(new DefaultMutableTreeNode(child.getUserObject()));
                copyBranch(child, userNode.getLastLeaf());
            }
        }
        for (int j = 0; j < leafNodes.size(); j++) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) leafNodes.get(j);
            child.removeFromParent();
        }

    }

    private DefaultMutableTreeNode searchAndCreateBranch(DefaultMutableTreeNode theNode, DefaultMutableTreeNode userTreeNode) {
        Enumeration en = userTreeNode.children();
        while (en.hasMoreElements()) {
            DefaultMutableTreeNode curChild = (DefaultMutableTreeNode) en.nextElement();
            if (curChild.toString().equals(theNode.toString())) {
                return curChild;
            }
        }
        userTreeNode.add(new DefaultMutableTreeNode(theNode.getUserObject()));
        return userTreeNode.getLastLeaf();
    }

    private Object createFromPath(String classPath) {
        try {
            Class cls = Class.forName(classPath);
            Constructor c = cls.getConstructor(new Class[]{});
            return c.newInstance(new Object[]{});
        } catch (Exception ex) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger(userMenuSetup.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    private void findupToTopBranch(List toTop, DefaultMutableTreeNode node) {
        while (node.getParent() != null) {
            toTop.add(0, (DefaultMutableTreeNode) node.getParent());
            node = (DefaultMutableTreeNode) node.getParent();
        }

    }

    //Creates a duplicate of a node
    private DefaultMutableTreeNode cloneNode(DefaultMutableTreeNode node) {
        //The node which will be returned
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(node.getUserObject());

        //Gets all children of this node
        Enumeration children = node.children();

        //Checks if the node has more children
        while (children.hasMoreElements()) {
            //Adds a copy of the child to this node
            newNode.add(cloneNode((DefaultMutableTreeNode) children.nextElement()));
        }

        return newNode;
    }
    //This method is used to move the selected node up in a tree

    private void moveNodeUp(JTree tree) {
        //The TreePath of the selected node
        TreePath selectedPath = tree.getSelectionPath();

        //Gets the selected node
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();

        //Checks that a node is selected
        if (selectedNode != null && selectedNode.getLevel() > 1) {
            //The parent of the selected node
            DefaultMutableTreeNode parent = (DefaultMutableTreeNode) selectedNode.getParent();

            //The index of this node in its parents' child array
            int index = parent.getIndex(selectedNode);

            //Checks if the node is the first of its parent's nodes
            if (index > 0) {
                //Insert the node at a new index
                parent.insert(selectedNode, (--index));
            }

            //Reloads the tree model
            ((DefaultTreeModel) tree.getModel()).reload();

            //Selects the node
            tree.setSelectionPath(selectedPath);
        }
    }

    //This method is used to move the selected node down in a tree
    private void moveNodeDown(JTree tree) {
        //The TreePath of the selected node
        TreePath selectedPath = tree.getSelectionPath();

        //Gets the selected node
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();

        //Checks that a node is selected
        if (selectedNode != null && selectedNode.getLevel() > 1) {
            //The parent of the selected node
            DefaultMutableTreeNode parent = (DefaultMutableTreeNode) selectedNode.getParent();

            //The index of this node in its parents' child array
            int index = parent.getIndex(selectedNode);

            //Checks if the node is the last of its parent's nodes
            if (index < parent.getChildCount() - 1) {
                //Insert the node at a new index
                parent.insert(selectedNode, (++index));
            }

            //Reloads the tree model
            ((DefaultTreeModel) tree.getModel()).reload();

            //Selects the node
            tree.setSelectionPath(selectedPath);
        }
    }

    public emcJTree getUserTree() {
        return userTree;
    }

    public void setUserTree(emcJTree userTree) {
        this.userTree = userTree;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
        this.lblUser.setText(userId + " Menu");
        this.lblUserPermissions.setText(userId + " Permissions");
    }

    public DefaultMutableTreeNode getAdminRoot() {
        return adminRoot;
    }

    public void setAdminRoot(DefaultMutableTreeNode adminRoot) {
        this.adminRoot = adminRoot;
    }

    public DefaultMutableTreeNode getUserRoot() {
        return userRoot;
    }

    public String getUserRootAsXMLString() {
        String x = EncodeUserTree.encodeUserTree(userRoot);
        DecodeUserTree.decodeTree(x);
        return x;
    }

    public void setUserRoot(DefaultMutableTreeNode userRoot) {
        userMenuTreeEvent.setBusy(true);
        this.userRoot = userRoot;
        adminRoot = new DefaultMutableTreeNode("EMC");
        adminRoot = emc.app.util.populateMenu.getNode(adminRoot, new emc.menus.mainMenu());
        if (this.userRoot == null) {
            this.userRoot = createNewUserRoot();
            this.userRoot.add(new DefaultMutableTreeNode(new emc.menus.mainMenu()));
        }

        adminModel.setRoot(adminRoot);
        userModel.setRoot(this.userRoot);
        userModel.reload();
        adminModel.reload();
        balanceTree();
        userMenuTreeEvent.setBusy(false);
    }

    DefaultTreeModel getAdminModel() {
        return adminModel;
    }

    public void setAdminModel(DefaultTreeModel adminModel) {
        this.adminModel = adminModel;
    }

    public DefaultTreeModel getUserModel() {
        return userModel;
    }

    public void setUserModel(DefaultTreeModel userModel) {
        this.userModel = userModel;
    }

    emcJTree getAdminTree() {
        return adminTree;
    }

    public void setAdminTree(emcJTree adminTree) {
        this.adminTree = adminTree;
    }

    /** Creates the User Menu Setup tab. */
    private emcJPanel createUserMenuSetupTab() {
        emcJPanel panel = new emcJPanel();

        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        //Admin side
        {
            //Setup admin panel
            {
                pnlAdmin.setLayout(new GridBagLayout());
                pnlAdmin.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
            }

            //Admin label
            {
                gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.weightx = 2;
                gbc.weighty = 0.5;
                gbc.gridwidth = 1;
                gbc.gridheight = 1;
                gbc.fill = GridBagConstraints.BOTH;

                //Sets up the label
                lblAdmin.setSize(60, 20);
                lblAdmin.setBorder(new LineBorder(Color.BLACK));
                lblAdmin.setHorizontalAlignment(emcJLabel.CENTER);

                //Add this label to the panel
                pnlAdmin.add(lblAdmin, gbc);
            }

            //Admin scrollpane
            {
                gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.weightx = 0;
                gbc.weighty = 5;
                gbc.gridwidth = 1;
                gbc.gridheight = 1;
                gbc.fill = GridBagConstraints.BOTH;

                //Adds the admin tree to this scrollpane
                adminPane.setViewportView(getAdminTree());

                //Add this scrollpane to the panel
                pnlAdmin.add(adminPane, gbc);
            }

            //Admin panel
            {
                gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.weightx = 3;
                gbc.weighty = 7;
                gbc.gridwidth = 1;
                gbc.gridheight = 1;
                gbc.fill = GridBagConstraints.BOTH;

                //Add this panel to the form
                panel.add(pnlAdmin, gbc);
            }
        }

        //Middle
        {
            gbc = new GridBagConstraints();

            //Sets up the panel
            pnlButtons.setLayout(new GridLayout(5, 1));

            pnlButtons.add(btnToUser);
            pnlButtons.add(btnToAdmin);
            pnlButtons.add(new JLabel(""));
            pnlButtons.add(btnUp);
            pnlButtons.add(btnDown);

            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.weightx = 1;
            gbc.weighty = 0;
            gbc.gridwidth = 1;
            gbc.gridheight = 1;

            //Adds the button panel
            panel.add(pnlButtons, gbc);
        }

        //User side
        {
            //Setup user panel
            {
                pnlUser.setLayout(new GridBagLayout());
                pnlUser.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
            }

            //User label
            {
                gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.weightx = 2;
                gbc.weighty = 0.5;
                gbc.gridwidth = 1;
                gbc.gridheight = 1;
                gbc.fill = GridBagConstraints.BOTH;

                //Sets up the label
                lblUser.setSize(60, 20);
                lblUser.setBorder(new LineBorder(Color.BLACK));
                lblUser.setHorizontalAlignment(emcJLabel.CENTER);

                //Add this label to the panel
                pnlUser.add(lblUser, gbc);
            }

            //User scrollpane
            {
                gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.weightx = 0;
                gbc.weighty = 5;
                gbc.gridwidth = 1;
                gbc.gridheight = 1;
                gbc.fill = GridBagConstraints.BOTH;

                //Displays the user tree to this scrollpane
                userPane.setViewportView(getUserTree());

                //Add this scrollpane to the panel
                pnlUser.add(userPane, gbc);
            }

            //User panel
            {
                gbc = new GridBagConstraints();
                gbc.gridx = 2;
                gbc.gridy = 0;
                gbc.weightx = 3;
                gbc.weighty = 0;
                gbc.gridwidth = 1;
                gbc.gridheight = 1;
                gbc.fill = GridBagConstraints.BOTH;

                //Add this panel to the form
                panel.add(pnlUser, gbc);
            }
        }

        return panel;
    }

    /** Creates the Permissions tab. */
    private emcJPanel createPermissionsTab() {
        emcJPanel panel = new emcJPanel();

        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        //User permissions side
        pnlUserPermissions.setLayout(new GridBagLayout());
        pnlUserPermissions.setBorder(new SoftBevelBorder(BevelBorder.RAISED));

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 2;
        gbc.weighty = 0.5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;

        lblUserPermissions.setSize(60, 20);
        lblUserPermissions.setBorder(new LineBorder(Color.BLACK));
        lblUserPermissions.setHorizontalAlignment(emcJLabel.CENTER);

        pnlUserPermissions.add(lblUserPermissions, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.weighty = 5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;

        userPermissionsPane.setViewportView(userPermissionsTree);

        pnlUserPermissions.add(userPermissionsPane, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 3;
        gbc.weighty = 7;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;

        panel.add(pnlUserPermissions, gbc);

        emcJPanel permissionPanel = new emcJPanel();
        permissionPanel.setBorder(BorderFactory.createTitledBorder("Permissions"));
        permissionPanel.add(userPermissionsCombobox);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.weighty = 0.3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;

        pnlUserPermissions.add(permissionPanel, gbc);

        //Middle
        gbc = new GridBagConstraints();

        pnlPermissionSetup.setLayout(new GridLayout(5, 1));

        pnlPermissionSetup.add(new emcJLabel());

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        panel.add(pnlPermissionSetup, gbc);

        //Form permissions side.
        pnlFormPermissions.setLayout(new GridBagLayout());
        pnlFormPermissions.setBorder(new SoftBevelBorder(BevelBorder.RAISED));

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 2;
        gbc.weighty = 0.5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;

        lblFormPermissions.setSize(60, 20);
        lblFormPermissions.setBorder(new LineBorder(Color.BLACK));
        lblFormPermissions.setHorizontalAlignment(emcJLabel.CENTER);

        pnlFormPermissions.add(lblFormPermissions, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.weighty = 5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;

        //Displays the user tree to this scrollpane
        formPermissionsPane.setViewportView(formPermissionsTree);

        //Add this scrollpane to the panel
        pnlFormPermissions.add(formPermissionsPane, gbc);

        permissionPanel = new emcJPanel();
        permissionPanel.setBorder(BorderFactory.createTitledBorder("Permissions"));
        permissionPanel.add(formPermissionsCombobox);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.weighty = 0.3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;

        //Add this scrollpane to the panel
        pnlFormPermissions.add(permissionPanel, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 3;
        gbc.weighty = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;

        panel.add(pnlFormPermissions, gbc);

        return panel;
    }

    /** Populates the form permissions tree with the selected value in the user permissions tree.  */
    public void populateFormPermissionsTree() {
        TreePath selectionPath = userPermissionsTree.getSelectionPath();
        if (selectionPath != null) {
            Object userObject = ((DefaultMutableTreeNode) userPermissionsTree.getSelectionPath().getLastPathComponent()).getUserObject();

            if (userObject instanceof EMCMenuItem) {
                EMCUserData copyUD = getUserData().copyUserData();
                EMCMenuItem menuItem = (EMCMenuItem) userObject;
                updateFormPermissionsTree(menuItem, copyUD);
            }
        }
    }

    /** Updates the formPermissions tree with form permissions.  This is regenerated every single time, in order to be able to assign permissions
     *  to new fields/buttons.  After constructing the tree, it checks whether the permissions data relation manager contain permissions 
     *  for fields and then uses those. 
     */
    private void updateFormPermissionsTree(EMCMenuItem menuItem, EMCUserData userData) {
        if (menuItem == null) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "null menu item passed to updateFormPermissionsTree() method", userData);
            }
            return;
        }
        try {
            //Use emc company and user
            userData = userData.copyUserData();
            userData.setCompanyId(systemConstants.defaultCompanyId());
            userData.setUserName("emc");
            userData.setUserData(2, PermissionConstants.PERMISSION_ID);

            Constructor constructor = Class.forName(menuItem.getClassPath()).getConstructor(EMCUserData.class);

            BaseInternalFrame frame = frame = (BaseInternalFrame) constructor.newInstance(userData);


            String frameTitle = frame.getFrameTitle();
            frameTitle = frameTitle.substring(frameTitle.indexOf("|") + 1).trim();

            emcJTree permissionsInDRM = refreshPermissions(menuItem.getClassPath(), frameTitle);

            Map<String, List> dataSourcesAndButtons = frame.getFormDataSourcesAndButtons(userData);

            List list = dataSourcesAndButtons.get("DATASOURCES");

            //Remove companyId from frame title
            formPermissionsRoot = new DefaultMutableTreeNode(frameTitle);
            DefaultMutableTreeNode dataSourcesNode = new DefaultMutableTreeNode("Datasources");

            for (Object ob : list) {
                addDatasourceNode((Class) ob, dataSourcesNode, permissionsInDRM, userData);
            }

            formPermissionsRoot.add(dataSourcesNode);

            DefaultMutableTreeNode buttonsNode = new DefaultMutableTreeNode("Buttons");

            list = dataSourcesAndButtons.get("BUTTONS");
            for (Object ob : list) {
                addButtonToPermissionsTree((String) ob, buttonsNode, permissionsInDRM, userData);
            }

            formPermissionsRoot.add(buttonsNode);

            formPermissionsTreeModel = new DefaultTreeModel(formPermissionsRoot);
            formPermissionsTree.setModel(formPermissionsTreeModel);
        } catch (Exception ex) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Exception occured " + ex.getMessage(), userData);
            }
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to construct form to setup permissions. (" + menuItem.getClassPath() + ").  Check with the administrator that you have permissions to edit the permissionsTree field on this form.", userData);
            ex.printStackTrace();
            return;
        }
    }

    /** Adds a button to the permissions tree. */
    private void addButtonToPermissionsTree(String buttonId, DefaultMutableTreeNode buttonsNode, emcJTree treeInDRM, EMCUserData userData) {
        //Default to "ALLOW" permission for buttons.
        Permission permission = new Permission(ButtonPermissions.ALLOW, PermissionTypes.BUTTON);
        PermissionTreeObject permissionTreeObject = new PermissionTreeObject(permission, buttonId, buttonId);
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(permissionTreeObject);
        buttonsNode.add(newNode);
        if (treeInDRM != null) {
            attempSetPermissionsFromTree(newNode, treeInDRM);
        }
    }

    /** Adds a datasource to the form permissions tree.  This method should be called when a new tree is constructed for form permissions.  
     *  If a tree with permissions is passed in, that will be used to determine permissions.  
     */
    private void addDatasourceNode(Class dataSourceClass, DefaultMutableTreeNode dataSourcesNode, emcJTree treeInDRM, EMCUserData userData) throws Exception {
        try {
            DefaultMutableTreeNode curDataSourceNode = new DefaultMutableTreeNode(dataSourceClass.getSimpleName());
            dataSourcesNode.add(curDataSourceNode);

            EMCEntityClass entity = (EMCEntityClass) dataSourceClass.newInstance();

            List<Field> fields = entity.getAllTableFields();

            for (Field field : fields) {
                Permission permission = new Permission();
                permission.setPermissionType(PermissionTypes.FIELD);
                String fieldName = field.getName();
                PermissionTreeObject permissionTreeObject = new PermissionTreeObject(permission, fieldName, entity.getDisplayLabelForField(fieldName, userData));
                DefaultMutableTreeNode permissionNode = new DefaultMutableTreeNode(permissionTreeObject);
                curDataSourceNode.add(permissionNode);

                //If the permissions DRM contains this permission, use that.  We add the node before doing this, so that we have a tree path to work with.
                if (treeInDRM != null) {
                    attempSetPermissionsFromTree(permissionNode, treeInDRM);
                }
            }

        } catch (Exception ex) {
            if (EMCDebug.getDebug()) {
                ex.printStackTrace();
                Logger.getLogger("emc").log(Level.SEVERE, "Exception occured " + ex.getMessage(), userData);
            }
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to get construct entity class.", userData);
        }
    }

    /** Sets the permission of the given permission Object to the permissions specified in the tree that was passed in.  */
    /** Returns the class name of the selected menu item in the user permissions tree, or null if no item is selected, or if the selected item is not a leaf.  */
    public String getFormClassName() {
        TreePath userPermissionPath = userPermissionsTree.getSelectionPath();

        if (userPermissionPath != null) {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) userPermissionPath.getLastPathComponent();
            if (selectedNode.isLeaf()) {
                return ((EMCMenuItem) selectedNode.getUserObject()).getClassPath();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /** Returns the data in the formPermissionsTree in an XML format. */
    public String getFormPermissionsAsXML() {
        return new EMCXMLHandler().encodeTree(formPermissionsRoot);
    }

    /** Refreshes the permissions DRM when a form is selected.
     *  Returns a the permissions contained in the DRM after refreshing it.
     *  null is returned if no permissions are found.
     */
    private emcJTree refreshPermissions(String formClassName, String frameTitle) {
        //Refresh permissions DRM
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseUserPermissionsTable.class.getName());
        query.addAnd("formClassName", formClassName);
        query.addAnd("userId", this.userId);

        permissionsUserData.setUserData(0, query);

        permissionsDRM.setUserData(permissionsUserData);

        emcJTree ret = null;
        if ((Long) permissionsDRM.getFieldValueAt(0, "recordID") == 0) {
            //Set default permission
            permissionsDRM.setFieldValueAt(0, "formAccess", FormPermissions.getDefaultFormPermission().toString());
        } else {
            ret = new emcJTree(new EMCXMLHandler().xmltoTree((String) permissionsDRM.getFieldValueAt(0, "permissionTree"), frameTitle));
        }

        //Also update user permissions combobox.
        userPermissionsCombobox.setSelectedItem(permissionsDRM.getFieldValueAt(0, "formAccess"));

        return ret;
    }

    /** This method attempts to set permissions on the given object (identified by the given tree path) using the given tree.  */
    private void attempSetPermissionsFromTree(DefaultMutableTreeNode toFind, emcJTree permissionsTreeFromDRM) {
        DefaultMutableTreeNode matchingNode = permissionsTreeFromDRM.findNode(toFind);

        if (matchingNode != null) {
            PermissionTreeObject objectFromDRMTree = (PermissionTreeObject) matchingNode.getUserObject();
            PermissionTreeObject curPermissionTreeObject = (PermissionTreeObject) toFind.getUserObject();

            curPermissionTreeObject.setPermission(objectFromDRMTree.getPermission());
        }
    }

    /** Sets the permission of the selected object in the form permissions tree.  */
    public void setCurrentFormPermission(String permissionString) {
        String formPermission = (String) formPermissionsCombobox.getSelectedItem();
        if (formPermission.equals(lastFormPermission)) {
            return;
        }
        lastFormPermission = formPermission;

        TreePath selectedPath = formPermissionsTree.getSelectionPath();

        if (selectedPath != null) {
            DefaultMutableTreeNode lastNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
            if (lastNode.getUserObject() instanceof PermissionTreeObject) {
                Permission permission = ((PermissionTreeObject) lastNode.getUserObject()).getPermission();

                PermissionTypes permissionType = PermissionTypes.fromString(permission.getPermissionType());

                switch (permissionType) {
                    case FIELD:
                        permission.setPermission(FieldPermissions.fromString(permissionString));
                        break;
                    case BUTTON:
                        permission.setPermission(ButtonPermissions.fromString(permissionString));
                        break;
                    case FORM:
                        break;

                    default:
                        break;
                }
            } else {
                return;
            }
        } else {
            return;
        }
    }

    /** Sets the model of the form permissions tree. */
    public void setFormPermissionTreeModel(DefaultTreeModel model) {
        this.formPermissionsTree.setModel(model);
    }

    /** Sets the last selected class name. */
    public void setLastSelectedClassName(String lastSelectedClassName) {
        this.lastSelectedClassName = lastSelectedClassName;
    }

    /** Returns the last selected class name. */
    public String getLastSelectedClassName() {
        return lastSelectedClassName;
    }

    /** Returns permission of the form that is currently selected in the user permissions tree. */
    public String getCurrentUserPermission() {
        return (String) userPermissionsCombobox.getSelectedItem();
    }

    /** Sets the last selected form permission. */
    public void setLastFormPermission(String lastFormPermission) {
        this.lastFormPermission = lastFormPermission;
    }

    /** Returns the last selected form permission.  */
    public String getLastSelectedFormPermission() {
        return this.lastFormPermission;
    }

    /** Persists form permissions. */
    public void persistFormPermissions() {
        //If form menu root is not null, persist.
        if (this.formPermissionsRoot != null) {
            this.permissionsDRM.updatePersist(0);
        }
    }

    /** Clears all field nodes when the selected form permission is changed.  
     *  This means that form permissions will be used unless those nodes are manually set.  
     */
    public void clearFieldPermissionNodes() {
        if (formPermissionsRoot != null) {
            List<DefaultMutableTreeNode> datasources = utilFunctions.getChildrenOfNode("Datasources", formPermissionsRoot);

            for (DefaultMutableTreeNode dsNode : datasources) {
                Enumeration en = dsNode.children();

                while (en.hasMoreElements()) {
                    DefaultMutableTreeNode permissionNode = (DefaultMutableTreeNode) en.nextElement();

                    PermissionTreeObject permissionTreeObject = (PermissionTreeObject) permissionNode.getUserObject();

                    permissionTreeObject.getPermission().setPermission(null);
                }

            }
        }
    }

    /** Returns the form permissions tree root. */
    public DefaultMutableTreeNode getFormPermissionsRoot() {
        return formPermissionsRoot;
    }

    @Override
    public void populateUserDataForPermissions(EMCUserData userData) {
        userData.setUserData(3, new usersformDRM());
        userData.setUserData(4, true);
    }

    private void fixPermissions(DefaultMutableTreeNode mainNode) {
        Enumeration e = mainNode.children();
        DefaultMutableTreeNode childNode;
        while (e.hasMoreElements()) {
            childNode = (DefaultMutableTreeNode) e.nextElement();
            if (!childNode.isLeaf()) {
                fixPermissions(childNode);
            } else {
                userPermissionsTree.setSelectionPath(new TreePath(childNode.getPath()));
                if (FormPermissions.VIEW.toString().equals(userPermissionsCombobox.getSelectedItem())) {
                    userPermissionsCombobox.setSelectedItem(FormPermissions.CREATE.toString());
                    permissionsDRM.updatePersist(permissionsDRM.getLastRowAccessed());
                }
            }
        }

    }

    private Component createPreferencesTab() {
        emcYesNoComponent allowClone = new emcYesNoComponent(dataRelation.getUserDRM().getPermissionsDRM(), "allowClone");
        emcYesNoComponent allowDelete = new emcYesNoComponent(dataRelation.getUserDRM().getPermissionsDRM(), "allowDelete");
        
        Component[][] components = {
            {new emcJLabel("Allow Clone"), allowClone},
            {new emcJLabel("Allow Delete"), allowDelete},
        };

        emcJPanel thePanel = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, false);
        thePanel.setBorder(BorderFactory.createTitledBorder("User Preferences"));

        return thePanel;
    }
}
