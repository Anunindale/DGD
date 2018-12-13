/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.gl.analysiscodes;

import emc.datatypes.gl.analysiscodeshierarchy.Description;
import emc.datatypes.gl.analysiscodeshierarchy.HierarchyId;
import emc.datatypes.gl.analysiscodeshierarchy.HierarchyTree;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author claudette
 */
@Entity
@Table(name = "GLAnalysisCodeHierarchy", uniqueConstraints = {@UniqueConstraint(columnNames = {"hierarchyId", "companyId"})})
public class GLAnalysisCodeHierarchy extends EMCEntityClass {

    private String hierarchyId;
    private String description;
    @Column(length = 10000)
    private String hierarchyTree;

    /** Creates a new instance of InventoryClassificationHierarchy. */
    public GLAnalysisCodeHierarchy() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHierarchyId() {
        return hierarchyId;
    }

    public void setHierarchyId(String hierarchyId) {
        this.hierarchyId = hierarchyId;
    }

    public String getHierarchyTree() {
        return hierarchyTree;
    }

    public void setHierarchyTree(String hierarchyTree) {
        this.hierarchyTree = hierarchyTree;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("hierarchyId", new HierarchyId());
        toBuild.put("description", new Description());
        toBuild.put("hierarchyTree", new HierarchyTree());
        return toBuild;
    }
}
