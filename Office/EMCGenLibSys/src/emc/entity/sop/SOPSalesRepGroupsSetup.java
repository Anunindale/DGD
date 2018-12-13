/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.sop;

import emc.datatypes.EMCDataType;
import emc.datatypes.sop.repgroups.ManagerCommission;
import emc.datatypes.sop.repgroups.foreignkeys.RepGroupIdFK;
import emc.datatypes.sop.repgroupsetup.DefaultCommission;
import emc.datatypes.sop.repgroupsetup.RepId;
import emc.framework.EMCEntityClass;
import java.math.BigDecimal;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "SOPSalesRepGroupsSetup", uniqueConstraints = {@UniqueConstraint(columnNames = {"repGroupId", "repId", "companyId"})})
public class SOPSalesRepGroupsSetup extends EMCEntityClass {

    private String repGroupId;
    private String repId;
    private BigDecimal groupManagerCommissionPerc = new BigDecimal(0);
    private BigDecimal defaultCommissionPerc = new BigDecimal(0);

    public BigDecimal getDefaultCommissionPerc() {
        return defaultCommissionPerc;
    }

    public void setDefaultCommissionPerc(BigDecimal defaultCommissionPerc) {
        this.defaultCommissionPerc = defaultCommissionPerc;
    }

    public BigDecimal getGroupManagerCommissionPerc() {
        return groupManagerCommissionPerc;
    }

    public void setGroupManagerCommissionPerc(BigDecimal groupManagerCommissionPerc) {
        this.groupManagerCommissionPerc = groupManagerCommissionPerc;
    }

    public String getRepGroupId() {
        return repGroupId;
    }

    public void setRepGroupId(String repGroupId) {
        this.repGroupId = repGroupId;
    }

    public String getRepId() {
        return repId;
    }

    public void setRepId(String repId) {
        this.repId = repId;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("repGroupId", new RepGroupIdFK());
        toBuild.put("repId", new RepId());
        toBuild.put("groupManagerCommissionPerc", new ManagerCommission());
        toBuild.put("defaultCommissionPerc", new DefaultCommission());
        return toBuild;
    }
}
