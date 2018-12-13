/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.base.journals.accessgroups;

import emc.framework.EMCEntityClass;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "BaseJournalAccessGroupDefinitions", uniqueConstraints = {@UniqueConstraint(columnNames = {"accessGroupId", "definitionId"})})
public class BaseJournalAccessGroupDefinitions extends EMCEntityClass {

    private String accessGroupId;
    private String definitionId;
    
    /** Creates a new instance of BaseJournalAccessGroupDefinitions. */
    public BaseJournalAccessGroupDefinitions() {
        
    }

    public String getAccessGroupId() {
        return accessGroupId;
    }

    public void setAccessGroupId(String accessGroupId) {
        this.accessGroupId = accessGroupId;
    }

    public String getDefinitionId() {
        return definitionId;
    }

    public void setDefinitionId(String definitionId) {
        this.definitionId = definitionId;
    }
}
