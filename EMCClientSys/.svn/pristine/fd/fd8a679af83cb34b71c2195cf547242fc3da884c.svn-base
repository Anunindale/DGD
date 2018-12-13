/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.gl.display.analysiscodehierarchysetup;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.forms.gl.display.analysiscodehierarchy.GLAnalysisCodeHierarchyDRM;
import emc.framework.EMCUserData;
import emc.helpers.gl.AnalysisCodeDS;
import emc.helpers.gl.DecodeEncodeAnalysisHier;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author claudette
 */
public class GLAnalysisCodeHierarchySetupDRM extends emcDataRelationManagerUpdate {

    private GLAnalysisCodeHierarchyDRM classHierDRM;
    private GLAnalysisCodeHierarchySetupForm theForm;

    public GLAnalysisCodeHierarchySetupDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData, GLAnalysisCodeHierarchySetupForm theForm) {
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
            if (classHierDRM == null) {
                classHierDRM = (GLAnalysisCodeHierarchyDRM) (userData.getUserData().remove(3));
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
                DecodeEncodeAnalysisHier ed = new DecodeEncodeAnalysisHier();
                DefaultMutableTreeNode tree = ed.decodeTree(treeString);
                theForm.setHierarchyRoot(tree);
            } else {
                AnalysisCodeDS ds = new AnalysisCodeDS();
                ds.setAnalysis("AnalysisCodes");
                ds.setDescription("Description");
                ds.setAnalysisEntityClassName("");
                DefaultMutableTreeNode tree = new DefaultMutableTreeNode(ds);
                theForm.setHierarchyRoot(tree);
            }
        }
    }

    public void setDataOnHerarchyForm() {
        if (classHierDRM != null) {
            classHierDRM.setFieldValueAt(classHierDRM.getLastRowAccessed(), "hierarchyTree", theForm.getHierarchyRootAsXMLString());
        }
    }

    @Override
    public boolean persistOnClosing() {
        setDataOnHerarchyForm();
        if (classHierDRM != null) {
            classHierDRM.updatePersist(classHierDRM.getLastRowAccessed());
            classHierDRM.setDoFormRelation(true);
        }
        return true;
    }

    @Override
    public void updatePersist(int rowIndex) {
        setDataOnHerarchyForm();
        if (classHierDRM != null) {
            classHierDRM.updatePersist(classHierDRM.getLastRowAccessed());
        }
    }

    @Override
    public void insertRowCache(int rowIndex, boolean addRowAfter) {
    }

    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
        return null;
    }

    public GLAnalysisCodeHierarchyDRM getClassHerDRM() {
        return classHierDRM;
    }

    public void setClassHierDRM(GLAnalysisCodeHierarchyDRM classHierDRM) {
        this.classHierDRM = classHierDRM;
    }
}
