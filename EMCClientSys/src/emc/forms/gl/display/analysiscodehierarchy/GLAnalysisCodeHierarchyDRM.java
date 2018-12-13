/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.gl.display.analysiscodehierarchy;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.framework.EMCUserData;

/**
 *
 * @author claudette
 */
public class GLAnalysisCodeHierarchyDRM extends emcDataRelationManagerUpdate {

    private Boolean doFormRelation = true;

    public GLAnalysisCodeHierarchyDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData) {
        super(tableDataSource, userData);
    }

    public void setDoFormRelation(Boolean doFormRelation) {
        this.doFormRelation = doFormRelation;
    }

    public Boolean getDoFormRelation() {
        return doFormRelation;
    }

    @Override
    public void tableRowChanged(int rowIndex) {
        setDoFormRelation((Boolean) true);
        super.tableRowChanged(rowIndex);
    }

    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
        formUserData = super.generateRelatedFormUserData(formUserData, Index);
        String tree;
        String hierarchyId;
        String description;

        switch (Index) {
            case 0:
                hierarchyId = (String) super.getFieldValueAt(this.getLastRowAccessed(), "hierarchyId");
                description = (String) super.getFieldValueAt(this.getLastRowAccessed(), "description");
                tree = (String) super.getFieldValueAt(this.getLastRowAccessed(), "hierarchyTree");
                if (hierarchyId != null) {
                    //Tree in postition 2, this drm in 3, hierarchy in 4, description in 5
                    formUserData.setUserData(2, tree);
                    formUserData.setUserData(3, this);
                    formUserData.setUserData(4, hierarchyId);
                    formUserData.setUserData(5, description);
                    formUserData.setUserData(6, doFormRelation);
                    if (getDoFormRelation()) {
                        setDoFormRelation((Boolean) false);
                    }
                }
                break;

            default:
                break;
        }
        return formUserData;
    }
}
