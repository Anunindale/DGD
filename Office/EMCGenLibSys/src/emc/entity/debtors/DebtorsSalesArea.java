package emc.entity.debtors;

import emc.datatypes.debtors.salesarea.SalesArea;
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
@Table(name = "DebtorsSalesArea", uniqueConstraints = {@UniqueConstraint(columnNames = {"salesArea", "companyId"})})
public class DebtorsSalesArea extends EMCEntityClass {

    private String salesArea;
    private String description;

    public String getSalesArea() {
        return salesArea;
    }

    public void setSalesArea(String salesArea) {
        this.salesArea = salesArea;
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

        toBuild.put("salesArea", new SalesArea());
        toBuild.put("description", new Description());

        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("salesArea");
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
