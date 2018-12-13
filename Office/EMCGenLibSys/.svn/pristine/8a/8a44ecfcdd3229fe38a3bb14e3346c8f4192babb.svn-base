/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.crm;

import emc.datatypes.EMCDataType;
import emc.datatypes.crm.interest.Interest;
import emc.datatypes.crm.interestgroup.foreignkey.InterestGroupFKNM;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
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
@Table(name = "CRMInterest", uniqueConstraints = {@UniqueConstraint(columnNames = {"interest", "companyId"})})
public class CRMInterest extends EMCEntityClass {

    private String interest;
    private String description;
    private String interestGroup;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("interest", new Interest());
        toBuild.put("description", new Description());
        toBuild.put("interestGroup", new InterestGroupFKNM());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("interest");
        toBuild.add("description");
        return toBuild;
    }

    /**
     * @return the interestGroup
     */
    public String getInterestGroup() {
        return interestGroup;
    }

    /**
     * @param interestGroup the interestGroup to set
     */
    public void setInterestGroup(String interestGroup) {
        this.interestGroup = interestGroup;
    }
}
