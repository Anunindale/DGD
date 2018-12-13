/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.debtors;

import emc.datatypes.EMCDataType;
import emc.datatypes.debtors.creditclosereason.CreditCloseReason;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @description : Entity class used to store Debtors Closed Reasons.
 *
 * @date        : 06 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Entity
@Table(name = "DebtorsClosedReason", uniqueConstraints = {@UniqueConstraint(columnNames = {"closedReasonId", "companyId"})})
public class DebtorsClosedReason extends EMCEntityClass {

    private String closedReasonId;
    private String description;

    /** Creates a new instance of DebtorsClosedReason */
    public DebtorsClosedReason() {
    }

    public String getClosedReasonId() {
        return closedReasonId;
    }

    public void setClosedReasonId(String closedReasonId) {
        this.closedReasonId = closedReasonId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("closedReasonId", new CreditCloseReason());
        toBuild.put("description", new Description());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("closedReasonId");
        toBuild.add("description");
        return toBuild;
    }
}
