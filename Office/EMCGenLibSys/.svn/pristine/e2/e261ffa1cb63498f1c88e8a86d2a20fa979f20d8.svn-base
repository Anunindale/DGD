/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.sop;

import emc.datatypes.EMCDataType;
import emc.datatypes.sop.repgroups.RepGroupId;
import emc.datatypes.sop.repgroups.ManagerCommission;
import emc.datatypes.sop.repgroups.foreignkeys.RepGroupIdFkNM;
import emc.datatypes.sop.repgroups.RepGroupManager;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "SOPSalesRepGroups", uniqueConstraints = {@UniqueConstraint(columnNames = {"repGroupId", "companyId"})})
public class SOPSalesRepGroups extends EMCEntityClass {

    private String repGroupId;
    private String description;
    private String groupManager;
    private BigDecimal groupManagerCommissionPerc = new BigDecimal(0);
    private String parentRepGroup;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroupManager() {
        return groupManager;
    }

    public void setGroupManager(String groupManager) {
        this.groupManager = groupManager;
    }

    public BigDecimal getGroupManagerCommissionPerc() {
        return groupManagerCommissionPerc;
    }

    public void setGroupManagerCommissionPerc(BigDecimal groupManagerCommissionPerc) {
        this.groupManagerCommissionPerc = groupManagerCommissionPerc;
    }

    public String getParentRepGroup() {
        return parentRepGroup;
    }

    public void setParentRepGroup(String parentRepGroup) {
        this.parentRepGroup = parentRepGroup;
    }

    public String getRepGroupId() {
        return repGroupId;
    }

    public void setRepGroupId(String repGroupId) {
        this.repGroupId = repGroupId;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("repGroupId", new RepGroupId());
        toBuild.put("description", new Description());
        toBuild.put("groupManager", new RepGroupManager());
        toBuild.put("groupManagerCommissionPerc", new ManagerCommission());
        toBuild.put("parentRepGroup", new RepGroupIdFkNM());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("groupManager");
        //toBuild.add("repGroupId");
        toBuild.add("description");
        return toBuild;
    }
}
