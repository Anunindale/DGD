/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.base.journals.accessgroups;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.journals.accessgroup.AccessDescription;
import emc.datatypes.base.journals.accessgroup.AccessGroupId;
import emc.datatypes.inventory.journals.approvalgroup.GroupModule;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "BaseJournalAccessGroups", uniqueConstraints = {@UniqueConstraint(columnNames = {"accessGroupId", "companyId"})})
public class BaseJournalAccessGroups extends EMCEntityClass {

    private String accessGroupId;
    private String accessGroupDescription;
    private String groupModule;

    /** Creates a new instance of BaseJournalAccessGroups. */
    public BaseJournalAccessGroups() {
        
    }

    public String getAccessGroupId() {
        return accessGroupId;
    }

    public void setAccessGroupId(String accessGroupId) {
        this.accessGroupId = accessGroupId;
    }

    public String getAccessGroupDescription() {
        return accessGroupDescription;
    }

    public void setAccessGroupDescription(String accessGroupDescription) {
        this.accessGroupDescription = accessGroupDescription;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap toBuild =  super.buildFieldList();
        toBuild.put("accessGroupId", new AccessGroupId());
        toBuild.put("groupModule", new GroupModule());
        toBuild.put("accessGroupDescription", new AccessDescription());
        return toBuild;
    }


    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List ret = super.buildDefaultLookupFieldList();
        ret.add("accessGroupId");
        ret.add("accessGroupDescription");
        return ret;
    }
    /**
     * @return the groupModule
     */
    public String getGroupModule() {
        return groupModule;
    }

    /**
     * @param groupModule the groupModule to set
     */
    public void setGroupModule(String groupModule) {
        this.groupModule = groupModule;
    }

}
