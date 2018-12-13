/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.debtors;

import emc.datatypes.EMCDataType;
import emc.datatypes.debtors.creditnoteapprovalgroupemployees.ApprovalGroupId;
import emc.datatypes.debtors.parameters.HandlingChargePercentage;
import emc.datatypes.debtors.creditnotereasons.ReasonCode;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @description : This entity class represents Credit Note reasons.
 *
 * @date        : 16 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Entity
@Table(name = "DebtorsCreditNoteReasons", uniqueConstraints = {@UniqueConstraint(columnNames = {"reasonCode", "companyId"})})
public class DebtorsCreditNoteReasons extends EMCEntityClass {

    private String reasonCode;
    private String description;
    private String approvalGroupId;

    //Indicates whether a handling charge should be applied.
    private boolean handlingCharge;

    /** Creates a new instance of DebtorsCreditNoteReasons */
    public DebtorsCreditNoteReasons() {

    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("reasonCode", new ReasonCode());
        toBuild.put("description", new Description());
        toBuild.put("approvalGroupId", new ApprovalGroupId());

        return toBuild;
    }

    @Override
    public List<String> getDefaultLookupFields() {
        List<String> ret = super.getDefaultLookupFields();

        ret.add("reasonCode");
        ret.add("description");
        ret.add("approvalGroupId");

        return ret;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getApprovalGroupId() {
        return approvalGroupId;
    }

    public void setApprovalGroupId(String approvalGroupId) {
        this.approvalGroupId = approvalGroupId;
    }

    public boolean isHandlingCharge() {
        return handlingCharge;
    }

    public void setHandlingCharge(boolean handlingCharge) {
        this.handlingCharge = handlingCharge;
    }
}
