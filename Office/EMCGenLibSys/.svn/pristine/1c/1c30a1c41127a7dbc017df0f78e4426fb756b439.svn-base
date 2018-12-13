package emc.entity.sop;

import emc.datatypes.sop.classification6.Classification6;
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
@Table(name = "SOPCustomerClassification6", uniqueConstraints = {@UniqueConstraint(columnNames = {"classification6", "companyId"})})
public class SOPCustomerClassification6 extends EMCEntityClass {

    private String classification6;
    private String description;

    public String getClassification6() {
        return classification6;
    }

    public void setClassification6(String classification6) {
        this.classification6 = classification6;
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
        toBuild.put("classification6", new Classification6());
        toBuild.put("description", new Description());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("classification6");
        toBuild.add("description");
        return toBuild;
    }
}
