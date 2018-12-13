package emc.entity.hr;

import emc.datatypes.EMCDataType;
import emc.datatypes.hr.qualtype.Description;
import emc.datatypes.hr.qualtype.QualTypeId;
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
@Table(name = "HRQualType", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "qualTypeId"})})
public class HRQualType extends EMCEntityClass {

    private String qualTypeId;
    private String description;

    /** Creates a new instance of HRQualType. */
    public HRQualType() {
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("qualTypeId", new QualTypeId());
        toBuild.put("description", new Description());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("qualTypeId");
        toBuild.add("description");
        return toBuild;
    }

    public String getQualTypeId() {
        return this.qualTypeId;
    }

    public void setQualTypeId(String qualTypeId) {
        this.qualTypeId = qualTypeId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}