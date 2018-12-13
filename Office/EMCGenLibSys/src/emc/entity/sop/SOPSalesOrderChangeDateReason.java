package emc.entity.sop;

import emc.datatypes.EMCDataType;
import emc.datatypes.sop.changedatereason.Description;
import emc.datatypes.sop.changedatereason.ReasonId;
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
@Table(name = "SOPSalesOrderChangeDateReason", uniqueConstraints = {@UniqueConstraint(columnNames = {"reasonId", "companyId"})})
public class SOPSalesOrderChangeDateReason extends EMCEntityClass {

    private String reasonId;
    private String description;

    /** Creates a new instance of SOPSalesOrderChangeDateReason. */
    public SOPSalesOrderChangeDateReason() {
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("reasonId", new ReasonId());
        toBuild.put("description", new Description());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("reasonId");
        toBuild.add("description");
        return toBuild;
    }

    public String getReasonId() {
        return this.reasonId;
    }

    public void setReasonId(String reasonId) {
        this.reasonId = reasonId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}