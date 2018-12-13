/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.dblog.resources;

import emc.app.components.emcJDialog;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJScrollPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcJTree;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.util.utilFunctions;
import emc.datatypes.EMCTime;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.functions.xml.EMCXMLHandler;
import emc.functions.xml.external.EMCExternalWSXMLException;
import emc.functions.xml.external.EMCExternalWSXMLHandler;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.ExpandVetoException;

/**
 *
 * @author riaan
 */
public class DBLogViewInfoDialog extends emcJDialog {

    private EMCUserData userData;
    //This can reference either the new or the old entity - it is only used to get column labels.
    private EMCEntityClass entityClassInstance;
    private EMCXMLHandler xmlHandler = new EMCXMLHandler();
    private EMCEntityClass oldEntityInstance;
    private EMCEntityClass newEntityInstance;

    /**
     * Creates a new instance of DBLogViewInfoDialog.
     */
    public DBLogViewInfoDialog(String tableLabel, String tableName, String uniqueIdentifier, String oldRecordXML, String newRecordXML, EMCUserData userData) {
        super(null, "DB Log Detail");
        this.userData = userData;

        if (!Functions.checkBlank(oldRecordXML)) {
            oldEntityInstance = (EMCEntityClass) xmlHandler.XMLToObject(oldRecordXML, userData);
            entityClassInstance = oldEntityInstance;
        }

        if (!Functions.checkBlank(newRecordXML)) {
            newEntityInstance = (EMCEntityClass) xmlHandler.XMLToObject(newRecordXML, userData);
            if (entityClassInstance == null) {
                entityClassInstance = newEntityInstance;
            }
        }

        this.setLayout(new BorderLayout());

        emcJLabel lblTable = new emcJLabel("Table");
        emcJLabel lblUID = new emcJLabel("Unique Identifier");
        emcJTextField txtTable = new emcJTextField();
        txtTable.setText(tableLabel);
        txtTable.setEditable(false);
        emcJTextField txtUID = new emcJTextField();
        txtUID.setText(uniqueIdentifier);
        txtUID.setEditable(false);

        Component[][] components = {
            {lblTable, txtTable},
            {lblUID, txtUID}
        };

        emcJPanel pnlMain = new emcJPanel(new BorderLayout(20, 20));
        pnlMain.add(emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, false), BorderLayout.NORTH);
        pnlMain.add(initTreePanel(oldRecordXML, newRecordXML), BorderLayout.CENTER);

        emcJTabbedPane tabs = new emcJTabbedPane();
        tabs.add("Detail", pnlMain);

        this.add(tabs, BorderLayout.CENTER);
        this.setSize(650, 500);
        this.setVisible(true);
    }

    /**
     * Initializes tree panels.
     *
     * @param oldRecord Old record in XML.
     * @param newRecord New record in XML.
     */
    private emcJPanel initTreePanel(String oldRecordXML, String newRecordXML) {
        emcJPanel treePanel = new emcJPanel(new GridLayout(1, 2));

        emcJTree oldTree = null;
        emcJTree newTree = null;

        emcJPanel oldTreePanel = new emcJPanel();
        oldTreePanel.setLayout(new GridLayout(1, 1));
        oldTreePanel.setBorder(BorderFactory.createTitledBorder("Old Record"));
        if (oldRecordXML != null) {
            oldTree = new emcJTree(getTreeRoot(oldRecordXML));
            oldTreePanel.add(new emcJScrollPane(oldTree));
            oldTree.addTreeWillExpandListener(new TreeWillExpandListener() {
                public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
                    //Do nothing
                }

                public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {
                    //Disallow
                    throw new ExpandVetoException(event);
                }
            });

        } else {
            oldTreePanel.add(new emcJLabel("N/A"));
        }
        treePanel.add(oldTreePanel);

        emcJPanel newTreePanel = new emcJPanel();
        newTreePanel.setLayout(new GridLayout(1, 1));
        newTreePanel.setBorder(BorderFactory.createTitledBorder("New Record"));
        if (newRecordXML != null) {
            newTree = new emcJTree(getTreeRoot(newRecordXML));
            newTreePanel.add(new emcJScrollPane(newTree));

            newTree.addTreeWillExpandListener(new TreeWillExpandListener() {
                public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
                    //Do nothing
                }

                public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {
                    //Disallow
                    throw new ExpandVetoException(event);
                }
            });
        } else {
            newTreePanel.add(new emcJLabel("N/A"));
        }
        treePanel.add(newTreePanel);

        if (oldTree != null) {
            oldTree.setCellRenderer(new DetailTreeRenderer(oldEntityInstance, newEntityInstance, userData));

        }

        if (newTree != null) {
            newTree.setCellRenderer(new DetailTreeRenderer(newEntityInstance, oldEntityInstance, userData));
        }
        return treePanel;
    }

    private DefaultMutableTreeNode getTreeRoot(String recordXML) {
        EMCEntityClass theInstance = (EMCEntityClass) xmlHandler.XMLToObject(recordXML, userData);
        DefaultMutableTreeNode curNode;
        if (theInstance != null) {
            Class theClass = theInstance.getClass();

            curNode = new DefaultMutableTreeNode(theClass.getSimpleName());

            List<Field> fields = theInstance.getAllTableFields();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    curNode.add(new DefaultMutableTreeNode(field.getName() + " : " + field.get(theInstance)));
                } catch (IllegalAccessException ex) {
                    if (EMCDebug.getDebug()) {
                        java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Could not access field: " + field.getName());
                    }
                }

            }
        } else {
            curNode = new DefaultMutableTreeNode("No Data");
        }
        return curNode;
    }
}

class DetailTreeRenderer extends DefaultTreeCellRenderer {

    private EMCEntityClass entityInstance;
    private EMCEntityClass otherEntityInstance;
    private EMCUserData userData;
    private emcJTree otherTree;

    /**
     * Creates a new instance of DetailTreeRenderer.
     */
    public DetailTreeRenderer(EMCEntityClass entityInstance, EMCEntityClass otherEntityInstance, EMCUserData userData) {
        this.entityInstance = entityInstance;
        this.otherEntityInstance = otherEntityInstance;
        this.userData = userData;
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        boolean valueChanged = false;
        String fieldName = null;
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        if (node.isLeaf() && node.getUserObject() != null) {
            String stringVal = (String) node.getUserObject();

            //Display EMC label instead of field name.
            int spaceIndex = stringVal.indexOf(" ");
            fieldName = stringVal.substring(0, spaceIndex);

            value = entityInstance.getValueForFieldInEntityObject(fieldName, entityInstance.getClass(), entityInstance);

            if (otherEntityInstance != null) {
                valueChanged = !Functions.checkObjectsEqual(value, otherEntityInstance.getValueForFieldInEntityObject(fieldName, otherEntityInstance.getClass(), otherEntityInstance));
            }

            if (value != null) {
                //Convert value to a formatted String
                if (entityInstance.getDataType(fieldName, userData) instanceof EMCTime) {
                    value = Functions.date2TimeString((Date) value);
                } else if (value instanceof Date) {
                    value = Functions.date2String((Date) value);
                } else {
                    value = String.valueOf(value);
                }
            } else {
                value = String.valueOf(value);
            }
        }
        Component comp = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        if (comp instanceof JLabel && tree.getPathForRow(row) != null && node.isLeaf()) {
            JLabel label = (JLabel) comp;
            StringBuilder sbValue = new StringBuilder();
            if (valueChanged) {
                label.setForeground(Color.RED);

                sbValue.append("<html><b>");
            }
            sbValue.append(utilFunctions.getFieldLabel(fieldName, entityInstance, userData));
            sbValue.append(": ");
            sbValue.append(value);
            if (valueChanged) {
                sbValue.append("</b></html>");
            }
            label.setText(sbValue.toString());
        }

        return comp;
    }
}
