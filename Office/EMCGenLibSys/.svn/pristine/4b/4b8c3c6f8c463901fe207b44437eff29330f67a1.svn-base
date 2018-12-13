/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.suburb.Suburb;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
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
@Table(name = "BaseSuburb", uniqueConstraints = {@UniqueConstraint(columnNames = {"suburb", "companyId"})})
public class BaseSuburb extends EMCEntityClass {

    private String suburb;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("suburb", new Suburb());
        toBuild.put("description", new Description());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("suburb");
        toBuild.add("description");
        return toBuild;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = super.buildQuery();
        query.addOrderBy("suburb");
        return query;
    }
}
