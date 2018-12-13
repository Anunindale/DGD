/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.classifications;

import emc.datatypes.inventory.itemclassification.hierarchies.HierarchyId;
import emc.datatypes.inventory.itemclassification.hierarchies.HierarchyTree;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "InventoryClassificationHierarchy", uniqueConstraints = {@UniqueConstraint(columnNames = {"hierarchyId", "companyId"})})
public class InventoryClassificationHierarchy extends EMCEntityClass {
 
    private String hierarchyId;
    private String description;
    @Column(length = 10000)
    private String hierarchyTree;
    
    /** Creates a new instance of InventoryClassificationHierarchy. */
    public InventoryClassificationHierarchy() {
        
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
