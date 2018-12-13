package emc.entity.hr;

import emc.datatypes.EMCDataType;
import emc.datatypes.hr.absscarcity.AbsScarcityId;
import emc.datatypes.hr.absscarcity.Description;
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
@Table(name = "HRAbsScarcity", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "absScarcityId"})})
public class HRAbsScarcity extends EMCEntityClass {

    private String absScarcityId;
    private String description;

    /** Creates a new instance of HRAbsScarcity. */
    public HRAbsScarcity() {
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("absScarcityId", new AbsScarcityId());
        toBuild.put("description", new Description());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("absScarcityId");
        toBuild.add("description");
        return toBuild;
    }

    public String getAbsScarcityId() {
        return this.absScarcityId;
    }

    public void setAbsScarcityId(String absScarcityId) {
        this.absScarcityId = absScarcityId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}