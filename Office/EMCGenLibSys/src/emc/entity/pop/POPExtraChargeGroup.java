/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.pop;

import emc.datatypes.pop.extrachargegroups.ExtraChargeGroupId;
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
@Table(name = "POPExtraChargeGroup", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"extraChargeGroupId", "companyId"})})
public class POPExtraChargeGroup extends EMCEntityClass implements Serializable {

    private String extraChargeGroupId;
    private String description;

    /**
     * Creates a new instance of POPExtraChargeGroup
     */
    public POPExtraChargeGroup() {
    }

    public String getExtraChargeGroupId() {
        return extraChargeGroupId;
    }

    public void setExtraChargeGroupId(String extraChargeGroupId) {
        this.extraChargeGroupId = extraChargeGroupId;
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

        toBuild.put("extraChargeGroupId", new ExtraChargeGroupId());
        toBuild.put("description", new Description());

        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("extraChargeGroupId");
        toBuild.add("description");
        return toBuild;
    }
}
