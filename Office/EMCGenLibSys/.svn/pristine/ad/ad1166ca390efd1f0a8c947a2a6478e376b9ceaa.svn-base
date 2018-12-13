/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.debtors;

import emc.datatypes.EMCDataType;
import emc.datatypes.debtors.royaltygroups.Description;
import emc.datatypes.debtors.royaltygroups.RoyaltyGroupId;
import emc.datatypes.debtors.royaltygroups.RoyaltyPercentage;
import emc.framework.EMCEntityClass;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "DebtorsRoyaltyGroups", uniqueConstraints = {@UniqueConstraint(columnNames = {"royaltyGroupId", "companyId"})})
public class DebtorsRoyaltyGroups extends EMCEntityClass {

    private String royaltyGroupId;
    private String description;
    private BigDecimal royaltyPercentage;

    /** Creates a new instance of DebtorsRoyaltyGroups. */
    public DebtorsRoyaltyGroups() {

    }


    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("royaltyGroupId", new RoyaltyGroupId());
        toBuild.put("description", new Description());
        toBuild.put("royaltyPercentage", new RoyaltyPercentage());

        return toBuild;
    }

    @Override
    public List<String> getDefaultLookupFields() {
        List<String> ret = super.getDefaultLookupFields();

        ret.add("royaltyGroupId");
        ret.add("description");
        ret.add("royaltyPercentage");

        return ret;
    }

    public String getRoyaltyGroupId() {
        return royaltyGroupId;
    }

    public void setRoyaltyGroupId(String royaltyGroupId) {
        this.royaltyGroupId = royaltyGroupId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getRoyaltyPercentage() {
        return royaltyPercentage;
    }

    public void setRoyaltyPercentage(BigDecimal royaltyPercentage) {
        this.royaltyPercentage = royaltyPercentage;
    }
}
