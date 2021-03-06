/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.pop;

import emc.datatypes.pop.pricegroups.PriceGroupId;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import java.io.Serializable;
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
@Table(name = "POPPriceGroup", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"priceGroupId", "companyId"})})
public class POPPriceGroup extends EMCEntityClass implements Serializable {

    private String priceGroupId;
    private String description;

    /**
     * Creates a new instance of POPPriceGroup
     */
    public POPPriceGroup() {
    }

    public String getPriceGroupId() {
        return priceGroupId;
    }

    public void setPriceGroupId(String priceGroupId) {
        this.priceGroupId = priceGroupId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("priceGroupId", new PriceGroupId());
        toBuild.put("description", new Description());

        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("priceGroupId");
        toBuild.add("description");
        return toBuild;
    }
}
