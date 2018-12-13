package emc.entity.hr;

import emc.datatypes.EMCDataType;
import emc.datatypes.hr.equitycode.Description;
import emc.datatypes.hr.equitycode.EquityCode;
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
@Table(name = "HREquiteyCode", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "equityCode"})})
public class HREquityCode extends EMCEntityClass {

    private String equityCode;
    private String description;

    /** Creates a new instance of HREquityCode. */
    public HREquityCode() {
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("equityCode", new EquityCode());
        toBuild.put("description", new Description());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("equityCode");
        toBuild.add("description");
        return toBuild;
    }

    public String getEquityCode() {
        return this.equityCode;
    }

    public void setEquityCode(String equityCode) {
        this.equityCode = equityCode;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
