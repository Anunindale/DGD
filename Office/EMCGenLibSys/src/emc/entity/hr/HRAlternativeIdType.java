package emc.entity.hr;

import emc.datatypes.EMCDataType;
import emc.datatypes.hr.alternativeidtype.AltIdType;
import emc.datatypes.hr.alternativeidtype.Description;
import emc.framework.EMCEntityClass;
import java.lang.Override;
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
@Table(name = "HRAlternativeIdType", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "altIdType"})})
public class HRAlternativeIdType extends EMCEntityClass {

    private String altIdType;
    private String description;

    /** Creates a new instance of HRAlternativeIdType. */
    public HRAlternativeIdType() {
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("altIdType", new AltIdType());
        toBuild.put("description", new Description());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("altIdType");
        toBuild.add("description");
        return toBuild;
    }

    public String getAltIdType() {
        return this.altIdType;
    }

    public void setAltIdType(String altIdType) {
        this.altIdType = altIdType;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}