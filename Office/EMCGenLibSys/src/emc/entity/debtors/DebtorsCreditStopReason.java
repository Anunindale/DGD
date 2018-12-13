package emc.entity.debtors;

import emc.datatypes.debtors.creditstopreason.CreditStopReason;
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
@Table(name = "DebtorsCreditStopReason", uniqueConstraints = {@UniqueConstraint(columnNames = {"reason", "companyId"})})
public class DebtorsCreditStopReason extends EMCEntityClass {

    private String reason;
    private String description;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

        toBuild.put("reason", new CreditStopReason());
        toBuild.put("description", new Description());

        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("reason");
        toBuild.add("description");
        return toBuild;
    }
}
