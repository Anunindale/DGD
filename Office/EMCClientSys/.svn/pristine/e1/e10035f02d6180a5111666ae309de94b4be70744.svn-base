/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.itemclassification.hierarchysetup;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.utility.inventory.classifications.DecodeEncodeClassificationHer;
import emc.forms.inventory.display.itemclassificationhierachies.ClassificationHierarchyDRM;
import emc.framework.EMCUserData;
import emc.utility.inventory.classifications.ClassificationsDS;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author riaan 
 */
public class ItemGroupClassificationHierarchySetupDRM extends emcDataRelationManagerUpdate {

    private ClassificationHierarchyDRM classHerDRM;
    private ItemGroupClassificationHierarchySetupForm theForm;

    public ItemGroupClassificationHierarchySetupDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData, ItemGroupClassificationHierarchySetupForm theForm) {
        super(tableDataSource, userData);
        this.theForm = theForm;

    }

    @Override
    public void setUserData(EMCUserData userData) {
        //2 = Tree String
        //3 = DRM
        //4 = ID
        //5 = description
        //6 = boolean form relation
        Boolean res = (Boolean) userData.getUserData(6);
        if (res) {
            if (classHerDRM == null) {
                classHerDRM = (ClassificationHierarchyDRM) (userData.getUserData().remove(3));
            }
            String herId = "";
            String herDesc = "";
            if (userData.getUserData(3) != null) {
                herId = userData.getUserData(3).toString();
            }
            if (userData.getUserData(4) != null) {
                herDesc = userData.getUserData(4).toString();
            }
            theForm.repaintFrameTitle(herId, herDesc);
            theForm.setLabelAboveTree(herId);
            String treeString = (String) userData.getUserData(2);
            if (treeString != null) {
                DecodeEncodeClassificationHer ed = new DecodeEncodeClassificationHer();
                DefaultMutableTreeNode tree = ed.decodeTree(treeString);
                theForm.setHierarchyRoot(tree);
            } else {
                ClassificationsDS ds = new ClassificationsDS();
                ds.setClassification("Item Group");
                ds.setDescription("Hierarchy Root");
                ds.setClassificationEntityClassName("");
                DefaultMutableTreeNode tree = new DefaultMutableTreeNode(ds);
                theForm.setHierarchyRoot(tree);
            }
        }
    }

    public void setDataOnHerarchyForm() {
        if (classHerDRM != null) {
            classHerDRM.setFieldValueAt(classHerDRM.getLastRowAccessed(), "hierarchyTree", theForm.getHierarchyRootAsXMLString());
        }
    }

    @Override
    public boolean persistOnClosing() {
        setDataOnHerarchyForm();
        if (classHerDRM != null) {
            classHerDRM.updatePersist(classHerDRM.getLastRowAccessed());
            classHerDRM.setDoFormRelation(true);
        }

        return true;

    }

    @Override
    public void updatePersist(int rowIndex) {
        setDataOnHerarchyForm();
        if (classHerDRM != null) {
            classHerDRM.updatePersist(classHerDRM.getLastRowAccessed());
        }
    }

    @Override
    public void insertRowCache(int rowIndex, boolean addRowAfter) {
    }

    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
        return null;
    }

    public ClassificationHierarchyDRM getClassHerDRM() {
        return classHerDRM;
    }

    public void setClassHerDRM(ClassificationHierarchyDRM classHerDRM) {
        this.classHerDRM = classHerDRM;
    }
}
