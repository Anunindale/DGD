package emc.entity.sop;

import emc.datatypes.sop.classification5.Classification5;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
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
@Table(name = "SOPCustomerClassification5", uniqueConstraints = {@UniqueConstraint(columnNames = {"classification5", "companyId"})})
public class SOPCustomerClassification5 extends EMCEntityClass {

    private String classification5;
    private String description;

    public String getClassification5() {
        return classification5;
    }

    public void setClassification5(String classification5) {
        this.classification5 = classification5;
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
        toBuild.put("classification5", new Classification5());
        toBuild.put("description", new Description());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("classification5");
        toBuild.add("description");
        return toBuild;
    }
}
