package emc.entity.sop;

import emc.datatypes.sop.classification1.Classification1;
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
@Table(name = "SOPCustomerClassification1", uniqueConstraints = {@UniqueConstraint(columnNames = {"classification1", "companyId"})})
public class SOPCustomerClassification1 extends EMCEntityClass {

    private String classification1;
    private String description;

    public String getClassification1() {
        return classification1;
    }

    public void setClassification1(String classification1) {
        this.classification1 = classification1;
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
        toBuild.put("classification1", new Classification1());
        toBuild.put("description", new Description());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("classification1");
        toBuild.add("description");
        return toBuild;
    }
}
