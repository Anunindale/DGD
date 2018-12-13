package emc.forms.base.display.helpfiles;

import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcHelpFile;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJScrollPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcJTree;
import emc.app.components.emcMenuTreeRenderer;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.config.EMCMenuConstants;
import emc.app.config.emcserverpath;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.util.OperatingSystemManagement;
import emc.entity.base.helpfiles.BaseHelpFileMappings;
import emc.enums.enumQueryTypes;
import emc.framework.EMCMenuItem;
import emc.framework.EMCMenuSuper;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

/** 
 *
 * @author riaan
 */
public class BaseHelpFileMappingsForm extends BaseInternalFrame {

    private boolean canSave = false;
    
    private emcDataRelationManagerUpdate drm;
    private emcJTree menuTree;
    private emcJTextField txtHelpFileURL;

    /** Creates a new instance of BaseHelpFileMappingsForm. */
    public BaseHelpFileMappingsForm(EMCUserData userData) {
        super("Base Help File Mappings", true, true, true, true, userData);

        this.setBounds(20, 20, 700, 350);
        drm = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(new BaseHelpFileMappings(), userData), userData) {

            @Override
            public void setFieldValueAt(int rowIndex, String columnIndex, Object aValue) {
                super.setFieldValueAt(rowIndex, columnIndex, aValue);

                //If the form class name is changed, assume that it was done by
                //the system.  Only save if the user enters a help file URL
                if ("formClassName".equals(columnIndex)) {
                    this.setEditFlag(false);
                }
            }
        };

        drm.setTheForm(this);
        this.setDataManager(drm);
        drm.setFormTextId1("formClassName");
        drm.setFormTextId2("helpFileURL");

        this.initFrame();

        //Nothing will be selected when the form is opened.
        selectionChanged();
    }

    /**
     * This should be called when the selection on the menu tree is changed.
     */
    private void selectionChanged() {
        //Save before fetching new data.  Handle clicks on the both the same and
        //on another node.
        if (canSave && (drm.isRowUpdated() || !Functions.checkObjectsEqual(drm.getLastFieldValueAt("helpFileURL"), txtHelpFileURL.getText()))) {
            drm.setFieldValueAt(drm.getLastRowAccessed(), "helpFileURL", txtHelpFileURL.getText());
            drm.updatePersist(drm.getLastRowAccessed());
        }
        
        boolean setFormClassName = false;

        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) menuTree.getLastSelectedPathComponent();

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseHelpFileMappings.class);
        if (selectedNode == null || !(selectedNode.getUserObject() instanceof EMCMenuItem)) {
            //No form selected.  Clear data and disable URL text field.
            query.addAnd("recordID", 0);

            this.txtHelpFileURL.setEnabled(false);
            this.canSave = false;
        } else {
            EMCMenuItem menuItem = (EMCMenuItem) selectedNode.getUserObject();
            query.addAnd("formClassName", menuItem.getClassPath());

            this.txtHelpFileURL.setEnabled(true);
            this.canSave = true;
            
            setFormClassName = true;
        }

        EMCUserData userData = drm.getUserData();
        userData.setUserData(0, query);
        drm.setUserData(userData);

        //Only set form class name after data has been refreshed.
        if (setFormClassName) {
            drm.setFieldValueAt(drm.getLastRowAccessed(), "formClassName", ((EMCMenuItem) selectedNode.getUserObject()).getClassPath());
        }
    }

    /** Initializes the frame. */
    private void initFrame() {
        emcJTabbedPane tabs = new emcJTabbedPane();
        tabs.add("Mappings", createMappingsTab());
        this.add(tabs, BorderLayout.CENTER);
    }

    /** Creates the menu tree. */
    private emcJTree createMenuTree() {
        DefaultMutableTreeNode menuroot = new DefaultMutableTreeNode("EMC");

        EMCMenuSuper mainMenu = EMCMenuConstants.mainMenu;

        if (mainMenu == null) {
            throw new NullPointerException("Main Menu not set in EMCMenuConstants.");
        }

        menuroot = emc.app.util.populateMenu.getNode(menuroot, mainMenu);

        menuTree = new emcJTree(menuroot);
        menuTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        emcMenuTreeRenderer renderer = new emcMenuTreeRenderer();
        menuTree.setCellRenderer(renderer);

        menuTree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {

            public void valueChanged(TreeSelectionEvent e) {
                selectionChanged();
            }
        });

        return menuTree;
    }

    /** Creates the Mappings Tab. */
    private emcJPanel createMappingsTab() {
        emcJPanel mappingsPanel = new emcJPanel(new GridLayout(1, 2));

        emcJPanel treePanel = new emcJPanel(new GridLayout(1, 1));
        treePanel.setBorder(BorderFactory.createTitledBorder("Menu"));
        treePanel.add(new emcJScrollPane(createMenuTree()));

        mappingsPanel.add(treePanel);

        EMCStringFormDocument helpFileURLDoc = new EMCStringFormDocument(drm, "helpFileURL");
        txtHelpFileURL = new emcJTextField(helpFileURLDoc);

        emcJLabel lblHelpFileURL = new emcJLabel(drm.getColumnName("helpFileURL"));

        emcJButton testButton = new emcJButton("Test URL") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                String url = txtHelpFileURL.getText();
                if (!url.contains("://")) {
                    url = emcserverpath.getProtocol() + emcserverpath.getServerName() + emcHelpFile.HELPFILE + emcHelpFile.QUERY + url.replaceAll(" ", "%20");
                }
                OperatingSystemManagement.launchHelpBrowser(url, getUserData());
            }
        };

        Component[][] components = new Component[][]{
            {lblHelpFileURL, txtHelpFileURL},
            {new emcJLabel(), testButton},
        };

        emcJPanel componentPanel = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.HORIZONTAL, true);
        componentPanel.setBorder(BorderFactory.createTitledBorder("Help File"));

        mappingsPanel.add(componentPanel);

        return mappingsPanel;
    }
}