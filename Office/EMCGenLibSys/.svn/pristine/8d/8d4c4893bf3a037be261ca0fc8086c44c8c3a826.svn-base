package emc.entity.hr;

import emc.datatypes.EMCDataType;
import emc.datatypes.hr.socioecostatus.Description;
import emc.datatypes.hr.socioecostatus.SocioEcoStatus;
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
@Table(name = "HRSocioEcoStatus", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "socioEcoStatus"})})
public class HRSocioEcoStatus extends EMCEntityClass {

    private String socioEcoStatus;
    private String description;

    /** Creates a new instance of HRSocioEcoStatus. */
    public HRSocioEcoStatus() {
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("socioEcoStatus", new SocioEcoStatus());
        toBuild.put("description", new Description());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("socioEcoStatus");
        toBuild.add("description");
        return toBuild;
    }

    public String getSocioEcoStatus() {
        return this.socioEcoStatus;
    }

    public void setSocioEcoStatus(String socioEcoStatus) {
        this.socioEcoStatus = socioEcoStatus;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}