package emc.entity.sop;

import emc.datatypes.sop.classification4.Classification4;
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
@Table(name = "SOPCustomerClassification4", uniqueConstraints = {@UniqueConstraint(columnNames = {"classification4", "companyId"})})
public class SOPCustomerClassification4 extends EMCEntityClass {

    private String classification4;
    private String description;

    public String getClassification4() {
        return classification4;
    }

    public void setClassification4(String classification4) {
        this.classification4 = classification4;
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
        toBuild.put("classification4", new Classification4());
        toBuild.put("description", new Description());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("classification4");
        toBuild.add("description");
        return toBuild;
    }
}
