package emc.entity.hr;

import emc.datatypes.EMCDataType;
import emc.datatypes.hr.citizenstatus.CitizenStatus;
import emc.datatypes.hr.citizenstatus.Description;
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
@Table(name = "HRCitizenStatus", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "citizenStatus"})})
public class HRCitizenStatus extends EMCEntityClass {

    private String citizenStatus;
    private String description;

    /** Creates a new instance of HRCitizenStatus. */
    public HRCitizenStatus() {
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("citizenStatus", new CitizenStatus());
        toBuild.put("description", new Description());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("citizenStatus");
        toBuild.add("description");
        return toBuild;
    }

    public String getCitizenStatus() {
        return this.citizenStatus;
    }

    public void setCitizenStatus(String citizenStatus) {
        this.citizenStatus = citizenStatus;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
