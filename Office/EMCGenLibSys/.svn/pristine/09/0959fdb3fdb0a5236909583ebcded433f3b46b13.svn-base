/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.debtors;

import emc.datatypes.EMCDataType;
import emc.datatypes.debtors.marketinggroup.MarketingGroup;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @description : Entity class used to store Debtors Marketing Groups.
 *
 * @date        : 06 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Entity
@Table(name = "DebtorsMarketingGroup", uniqueConstraints = {@UniqueConstraint(columnNames = {"marketingGroup", "companyId"})})
public class DebtorsMarketingGroup extends EMCEntityClass {

    private String marketingGroup;
    private String description;

    /** Creates a new instance of DebtorsMarketingGroup */
    public DebtorsMarketingGroup() {

    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("marketingGroup", new MarketingGroup());
        toBuild.put("description", new Description());

        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("marketingGroup");
        toBuild.add("description");
        return toBuild;
    }



    public String getMarketingGroup() {
        return marketingGroup;
    }

    public void setMarketingGroup(String marketingGroup) {
        this.marketingGroup = marketingGroup;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
