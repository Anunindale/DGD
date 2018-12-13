/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.dblog.resources;

import emc.app.components.emcJPanel;
import emc.app.components.emcJScrollPane;
import emc.app.components.emcJTree;
import emc.app.components.emcpicker.EMCPickerPopup;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.framework.EMCDebug;
import emc.framework.EMCUserData;
import emc.functions.xml.EMCXMLHandler;
import java.awt.GridLayout;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author wikus
 */
public class RecordInfoPopup extends EMCPickerPopup {

    private emcJPanel thePopup;
    private emcDataRelationManagerUpdate dataRelation;
    private String fieldKey;
    private EMCUserData userData;
    private emcJTree theTree;
    private RecordInfoLookup lookup;

    public RecordInfoPopup(RecordInfoLookup lookup, emcDataRelationManagerUpdate dataRelation, String fieldKey, EMCUserData userData) {
        this.dataRelation = dataRelation;
        this.fieldKey = fieldKey;
        this.userData = userData;
        this.lookup = lookup;
        this.theTree = new emcJTree();
        this.thePopup = new emcJPanel(new GridLayout());
        thePopup.add(new emcJScrollPane(theTree));
        this.add(thePopup);
        this.setPreferredSize(new java.awt.Dimension(300, 400));
    }

    public void selectionComplete() {
        return;
    }

    public void setPopupRecord(Object theInstance) {
        DefaultMutableTreeNode curNode;
        if (theInstance != null) {
            Class theClass = theInstance.getClass();

            curNode = new DefaultMutableTreeNode(theClass.getSimpleName());

            ArrayList<Field> fields = findFieldsUpToSuperClass(theClass, Object.class);
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
        theTree.setModel(new DefaultTreeModel(curNode));
    }

    private ArrayList<Field> findFieldsUpToSuperClass(Class theClass, Class theSuperClass) {
        ArrayList<Field> ret = new ArrayList<Field>();

        while (theClass != theSuperClass) {
            ret.addAll(Arrays.asList(theClass.getDeclaredFields()));
            theClass = theClass.getSuperclass();

            if (theClass == null) {
                break;
            }
        }
        return ret;
    }

    @Override
    public void show() {
        String value  = (String) dataRelation.getFieldValueAt(dataRelation.getLastRowAccessed(), fieldKey);
        setPopupRecord(new EMCXMLHandler().XMLToObject(value == null ? "<f/>" : value, userData));
        super.show(lookup, 0, lookup.getHeight());
    }
    
}
