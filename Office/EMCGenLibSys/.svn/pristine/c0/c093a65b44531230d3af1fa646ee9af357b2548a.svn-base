package emc.entity.debtors;

import emc.datatypes.debtors.salesregion.SalesRegion;
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
 * @Author wikus
 */
@Entity
@Table(name = "DebtorsSalesRegion", uniqueConstraints = {@UniqueConstraint(columnNames = {"salesRegion", "companyId"})})
public class DebtorsSalesRegion extends EMCEntityClass {

    private String salesRegion;
    private String description;

    public String getSalesRegion() {
        return salesRegion;
    }

    public void setSalesRegion(String salesRegion) {
        this.salesRegion = salesRegion;
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

        toBuild.put("salesRegion", new SalesRegion());
        toBuild.put("description", new Description());

        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("salesRegion");
        toBuild.add("description");
        return toBuild;
    }

    @Override
    public EMCQuery getQuery() {
        EMCQuery query = super.getQuery();
        query.addOrderBy("description");

        return query;
    }
}
