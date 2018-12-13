/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.debtors.creditheld;

import emc.datatypes.EMCDataType;
import emc.datatypes.debtors.creditheld.ApprovedBy;
import emc.datatypes.debtors.creditheld.ApprovedDate;
import emc.datatypes.debtors.creditheld.CreditHeldStatus;
import emc.datatypes.debtors.creditheld.CustomerId;
import emc.datatypes.debtors.creditheld.Reference;
import emc.enums.debtors.creditheld.DebtorsCreditHeldStatus;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @description : Entity class for Debtors Credit Held Master records.
 *                Lines for this entity class will be dynamic, referencing
 *                either SOP Sales Order lines or Debtors Customer Invoice lines.
 *
 * @date        : 29 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Entity
@Table(name = "DebtorsCreditHeldMaster")
public class DebtorsCreditHeldMaster extends EMCEntityClass {

    private String reference;
    private String referenceType;
    private String customerId;
    @Temporal(TemporalType.DATE)
    private Date heldDate;
    private String creditHeldStatus = DebtorsCreditHeldStatus.HELD.toString();
    private String approvedBy;
    @Temporal(TemporalType.DATE)
    private Date approvedDate;
    private String creditHeldReason;
    //Previous Sales Order status.  Used to revert sales order back to the status that it was in before being held.
    private String previousSOStatus;

    /** Creates a new instance of DebtorsCreditHeldMaster */
    public DebtorsCreditHeldMaster() {
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("creditHeldStatus", new CreditHeldStatus());
        toBuild.put("approvedBy", new ApprovedBy());
        toBuild.put("approvedDate", new ApprovedDate());
        toBuild.put("customerId", new CustomerId());
        toBuild.put("reference",  new Reference());

        return toBuild;
    }

    @Override
    public EMCQuery getQuery() {
        EMCQuery query = super.getQuery();

        query.addAnd("creditHeldStatus", DebtorsCreditHeldStatus.HELD.toString());

        return query;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Date getHeldDate() {
        return heldDate;
    }

    public void setHeldDate(Date heldDate) {
        this.heldDate = heldDate;
    }

    public String getCreditHeldStatus() {
        return creditHeldStatus;
    }

    public void setCreditHeldStatus(String creditHeldStatus) {
        this.creditHeldStatus = creditHeldStatus;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getCreditHeldReason() {
        return creditHeldReason;
    }

    public void setCreditHeldReason(String creditHeldReason) {
        this.creditHeldReason = creditHeldReason;
    }

    public void setPreviousSOStatus(String previousSOStatus) {
        this.previousSOStatus = previousSOStatus;
    }

    public String getPreviousSOStatus() {
        return this.previousSOStatus;
    }
}
