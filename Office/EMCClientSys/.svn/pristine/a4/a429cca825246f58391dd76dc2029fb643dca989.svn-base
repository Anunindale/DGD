/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.gl.display.analysiscodehierarchysetup;

import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJScrollPane;
import emc.app.components.emcJTree;
import emc.app.components.emctable.emcDRMViewOnly;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.gl.analysiscodes.GLAnalysisCodesDropDown;
import emc.app.config.emcicons;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.gl.analysiscodes.GLAnalysisCodeHierarchy;
import emc.entity.gl.analysiscodes.GLAnalysisCodeSuper;
import emc.enums.enumQueryTypes;
import emc.enums.gl.GLAnalysisCode;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.helpers.gl.AnalysisCodeDS;
import emc.helpers.gl.DecodeEncodeAnalysisHier;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import javax.swing.ImageIcon;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author claudette
 */
public class GLAnalysisCodeHierarchySetupForm extends BaseInternalFrame {

    private EMCUserData copyUD;
    private String hierarchy;
    private GLAnalysisCodeHierarchySetupDRM dataRelation;
    private emcJLabel lblHierarchy = new emcJLabel();
    private DefaultTreeModel hierarchyModel;
    private DefaultMutableTreeNode hierarchyRoot;
    private emcJTree hierarchyTree;
    private emcTableModelUpdate analysisModel;
    private emcJTableUpdate analysisTable;
    private emcDRMViewOnly analysisDRM;
    private GLAnalysisCodesDropDown analysisCodesDropDown;
    private emcJPanel pnlAnalysis = new emcJPanel();
    private emcJPanel pnlAnalysisSelection = new emcJPanel();
    private emcJScrollPane analysisPane;
    private emcJPanel pnlButtons = new emcJPanel();
    private emcJPanel pnlHierarchy = new emcJPanel();
    private emcJPanel pnlHierarchyInfo = new emcJPanel();
    private emcJScrollPane hierarchyPane;
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
    private emcJButton btnToTree = new emcJButton() {

        @Override
        public void doActionPerformed(ActionEvent evt) {
        }
    };
    private emcJButton btnRemove = new emcJButton() {

        @Override
        public void doActionPerformed(ActionEvent evt) {
        }
    };

    public GLAnalysisCodeHierarchySetupForm(EMCUserData userData) {
        super("Analysis Code Hierarchy Setup", true, true, true, true, userData);
        this.setBounds(20, 20, 600, 300);

        copyUD = userData.copyUserData();
        initializeUserData(copyUD);

        EMCUserData DRMUD = userData.copyUserData();

        hierarchy = (String) userData.getUserData(2);

        analysisDRM = new emcDRMViewOnly(new emcGenericDataSourceUpdate(enumEMCModules.GENERAL_LEDGER.getId(), new GLAnalysisCodeSuper(), copyUD), copyUD);

        dataRelation = new GLAnalysisCodeHierarchySetupDRM(new emcGenericDataSourceUpdate(new GLAnalysisCodeHierarchy(), DRMUD), DRMUD, this);

        this.setDataManager(dataRelation);
        createTree();
        createTable();
        initDisplay();
        dataRelation.setUserData(userData);

    }

    private void createTree() {
        //If hierarchyRoot equals null, create new empty tree
        if (hierarchyRoot == null) {
            hierarchyRoot = new DefaultMutableTreeNode(hierarchy);
        }
        this.hierarchyTree = new emcJTree();
        this.hierarchyModel = new DefaultTreeModel(this.hierarchyRoot);
        this.hierarchyTree.setModel(this.hierarchyModel);
        this.hierarchyTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    }

    private void createTable() {
        List keys = new ArrayList();
        keys.add("analysisCode");
        keys.add("description");

        analysisModel = new emcTableModelUpdate(this.analysisDRM, keys);
        analysisTable = new emcJTableUpdate(analysisModel) {

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };

        analysisCodesDropDown = new GLAnalysisCodesDropDown(analysisDRM);
        analysisDRM.setMainTableComponent(analysisTable);
    }

    /** Setup the initial user data for the form when it is opened */
    private void initializeUserData(EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLAnalysisCodeSuper.class.getName());

        userData.setUserData(0, query.toString());
        userData.setUserData(1, query.getCountQuery());
    }

    private void initDisplay() {
        Dimension panelDimension = new Dimension(150, 26);

        //Sets the icons of the buttons
        btnDown.setIcon(new ImageIcon(getClass().getResource(emcicons.getDownArrow())));
        btnUp.setIcon(new ImageIcon(getClass().getResource(emcicons.getUpArrow())));
        btnToTree.setIcon(new ImageIcon(getClass().getResource(emcicons.getNextPage())));
        btnRemove.setIcon(new ImageIcon(getClass().getResource(emcicons.getDeleteRecord())));

        btnRemove.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent evt) {
                removeFromHierarchy();
            }
        });

        btnToTree.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent evt) {
                analysisCodeToHierarchy();
            }
        });

        btnUp.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent evt) {
                //Checks which tree is selected
                if (hierarchyTree.getLastSelectedPathComponent() != null) {
                    moveNodeUp(hierarchyTree);
                } else {
                    moveNodeUp(hierarchyTree);
                }
            }
        });

        btnDown.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent evt) {
                //Checks which tree is selected
                if (hierarchyTree.getLastSelectedPathComponent() != null) {
                    moveNodeDown(hierarchyTree);
                } else {
                    moveNodeDown(hierarchyTree);
                }
            }
        });

        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        //Analysis code side
        //Setup Analysis code panel
        pnlAnalysis.setLayout(new GridBagLayout());
        pnlAnalysis.setBorder(new SoftBevelBorder(BevelBorder.RAISED));

        //Analysis code selection
        pnlAnalysisSelection.setMinimumSize(panelDimension);
        pnlAnalysisSelection.setMaximumSize(panelDimension);

        pnlAnalysisSelection.setBorder(new LineBorder(Color.BLACK));

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 2;
        gbc.weighty = 0.5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.NONE;

        pnlAnalysisSelection.add(analysisCodesDropDown, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 2;
        gbc.weighty = 0.5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;

        pnlAnalysis.add(pnlAnalysisSelection, gbc);

        //Analysis code scrollpane
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.weighty = 5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;

        analysisPane = new emcJScrollPane(analysisTable);
        analysisPane.setViewportView(analysisTable);
        pnlAnalysis.add(analysisPane, gbc);

        //Analysis code panel
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 3;
        gbc.weighty = 7;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;

        //Add this panel to the form
        this.add(pnlAnalysis, gbc);

        //Middle
        gbc = new GridBagConstraints();

        //Sets up the panel
        pnlButtons.setLayout(new GridLayout(5, 1));

        pnlButtons.add(btnUp);
        pnlButtons.add(btnDown);
        pnlButtons.add(new emcJLabel());
        pnlButtons.add(btnToTree);
        pnlButtons.add(btnRemove);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        this.add(pnlButtons, gbc);

        //Hierarchy side
        //Setup hierarchy panel
        pnlHierarchy.setLayout(new GridBagLayout());
        pnlHierarchy.setBorder(new SoftBevelBorder(BevelBorder.RAISED));

        //Create info panel
        pnlHierarchyInfo.setMinimumSize(panelDimension);
        pnlHierarchyInfo.setMaximumSize(panelDimension);

        pnlHierarchyInfo.setBorder(new LineBorder(Color.BLACK));
        pnlHierarchyInfo.setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 2;
        gbc.weighty = 0.5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.NONE;

        pnlHierarchyInfo.add(lblHierarchy, gbc);

        //Hierarchy panel
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 2;
        gbc.weighty = 0.5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;

        pnlHierarchy.add(pnlHierarchyInfo, gbc);

        //hierarchy scrollpane
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.weighty = 5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;

        hierarchyPane = new emcJScrollPane(hierarchyTree);
        hierarchyPane.setViewportView(hierarchyTree);
        pnlHierarchy.add(hierarchyPane, gbc);

        //hierarchy panel
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 3;
        gbc.weighty = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;

        //Add this panel to the form
        this.add(pnlHierarchy, gbc);
    }

    /** Remove the selected analysis code (in tree) from the hierarchy */
    private void removeFromHierarchy() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) (hierarchyTree.getSelectionPath().getLastPathComponent());

        if (node != null && node.getLevel() != 0) {
            ArrayList<TreePath> expandedNodes = getTreeExpandedNodes(hierarchyTree);
            node.removeFromParent();
            hierarchyModel.reload();
            setTreeExpandedNodes(hierarchyTree, expandedNodes);
        } else {
            java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Cannot remove analysis code. " + node == null ? "No analysis code selected." : "Root node cannot be removed", copyUD);
        }
    }

    //This method is used to save a list of all the expanded nodes in a tree
    private ArrayList<TreePath> getTreeExpandedNodes(emcJTree theTree) {
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
    private void setTreeExpandedNodes(emcJTree theTree, ArrayList<TreePath> paths) {
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

    /** This method is used to move the selected node up in a tree. */
    private void moveNodeUp(emcJTree tree) {
        //The TreePath of the selected node
        TreePath selectedPath = tree.getSelectionPath();

        //Gets the selected node
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();

        //Checks that a node is selected
        if (selectedNode != null && selectedNode.getLevel() > 0) {
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

    /** This method is used to move the selected node down in a tree. */
    private void moveNodeDown(emcJTree tree) {
        //The TreePath of the selected node
        TreePath selectedPath = tree.getSelectionPath();

        //Gets the selected node
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();

        //Checks that a node is selected
        if (selectedNode != null && selectedNode.getLevel() > 0) {
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

    /** Add the selected analysis code (in table) to the hierarchy */
    private void analysisCodeToHierarchy() {
        GLAnalysisCodeSuper analysis = (GLAnalysisCodeSuper) analysisDRM.getRowCache(analysisTable.getSelectedRow());
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) hierarchyTree.getLastSelectedPathComponent();

        String result = (String) analysisCodesDropDown.getSelectedItem();

        if (node != null && (analysis != null && analysis.getRecordID() != 0)) {
            int level = (GLAnalysisCode.fromString(result)).getId();
            if (node.getLevel() == level) {
                AnalysisCodeDS analysisCodeDS = new AnalysisCodeDS();
                analysisCodeDS.setRecordId(analysis.getRecordID());
                analysisCodeDS.setAnalysis(analysis.getAnalysisCode());
                analysisCodeDS.setDescription(analysis.getDescription());
                analysisCodeDS.setAnalysisEntityClassName(analysis.getClass().getName());

                if (!checkAnalysisCodeExists(node, analysisCodeDS)) {
                    ArrayList<TreePath> expandedNodes = getTreeExpandedNodes(hierarchyTree);
                    node.add(new DefaultMutableTreeNode(analysisCodeDS));

                    TreePath selectedPath = hierarchyTree.getSelectionPath();

                    hierarchyModel.reload();

                    setTreeExpandedNodes(hierarchyTree, expandedNodes);
                    hierarchyTree.setSelectionPath(selectedPath);
                    hierarchyTree.scrollPathToVisible(selectedPath.pathByAddingChild(node));
                } else {
                    java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Cannot add analysis code.  Analysis code already exists in tree.", copyUD);
                }
            } else {
                java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Cannot add analysis code.  Analysis codes have to be added in sequence.", copyUD);
            }
        } else {
            java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Cannot add analysis code.  Select both a analysis code and a node.", copyUD);
        }
    }

    /** This method ensures that the same analysis code cannot be added twice */
    private boolean checkAnalysisCodeExists(DefaultMutableTreeNode parentNode, AnalysisCodeDS analysis) {
        boolean ret = false;

        Enumeration<DefaultMutableTreeNode> children = parentNode.children();
        DefaultMutableTreeNode child = null;

        while (children.hasMoreElements()) {
            child = children.nextElement();

            if (child.getUserObject().toString().equals(analysis.toString())) {
                ret = true;
            }
        }

        return ret;
    }

    /**
     * Set the text above the tree
     * @param toSet
     */
    public void setLabelAboveTree(String toSet) {
        lblHierarchy.setText(toSet);
    }

    /**
     * Set the tree node from the parent form
     * @param rootNode
     */
    public void setHierarchyRoot(DefaultMutableTreeNode rootNode) {
        hierarchyModel.setRoot(rootNode);
        hierarchyModel.reload();
    }

    /**
     * Get the tree Root as XML
     * @return
     */
    public String getHierarchyRootAsXMLString() {
        DecodeEncodeAnalysisHier ed = new DecodeEncodeAnalysisHier();
        return ed.encodeTree((DefaultMutableTreeNode) hierarchyModel.getRoot());
    }
}
