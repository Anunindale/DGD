package emc.entity.hr;

import emc.datatypes.EMCDataType;
import emc.datatypes.hr.pivotalinst.Description;
import emc.datatypes.hr.pivotalinst.PivotalInst;
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
@Table(name = "HRPivotalInst", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "pivotalInst"})})
public class HRPivotalInst extends EMCEntityClass {

    private String pivotalInst;
    private String description;

    /** Creates a new instance of HRPivotalInst. */
    public HRPivotalInst() {
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("pivotalInst", new PivotalInst());
        toBuild.put("description", new Description());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("pivotalInst");
        toBuild.add("description");
        return toBuild;
    }

    public String getPivotalInst() {
        return this.pivotalInst;
    }

    public void setPivotalInst(String pivotalInst) {
        this.pivotalInst = pivotalInst;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}