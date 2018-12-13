package emc.entity.hr;

import emc.datatypes.EMCDataType;
import emc.datatypes.hr.remunerantiontype.Description;
import emc.datatypes.hr.remunerantiontype.RemunerationType;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/** 
 *
 * @author claudette
 */
@Entity
@Table(name = "HRRemunerantionType", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "remunerationType"})})
public class HRRemunerantionType extends EMCEntityClass {

    private String remunerationType;
    private String description;

    /** Creates a new instance of HRRemunerantionType. */
    public HRRemunerantionType() {
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("remunerationType", new RemunerationType());
        toBuild.put("description", new Description());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("remunerationType");
        toBuild.add("description");
        return toBuild;
    }

    public String getRemunerationType() {
        return this.remunerationType;
    }

    public void setRemunerationType(String remunerationType) {
        this.remunerationType = remunerationType;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}