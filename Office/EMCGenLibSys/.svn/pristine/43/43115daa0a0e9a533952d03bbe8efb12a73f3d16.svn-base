/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base.journals;

import emc.datatypes.base.journals.journaldefinition.ApprovedBy;
import emc.datatypes.base.journals.journaldefinition.JournalAccessGroup;
import emc.datatypes.base.journals.journaldefinition.JournalApprovalRequired;
import emc.datatypes.base.journals.journaldefinition.JournalContraAccount;
import emc.datatypes.base.journals.journaldefinition.JournalContraFixed;
import emc.datatypes.base.journals.journaldefinition.JournalContraType;
import emc.datatypes.base.journals.journaldefinition.JournalDefinitionId;
import emc.datatypes.base.journals.journaldefinition.JournalEntryControl;
import emc.datatypes.base.journals.journaldefinition.JournalLineNumbering;
import emc.datatypes.base.journals.journaldefinition.JournalModule;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "BaseJournalDefinitionTable", uniqueConstraints = {@UniqueConstraint(columnNames = {"journalDefinitionId", "companyId"})})
public class BaseJournalDefinitionTable extends EMCEntityClass {

    private String journalDefinitionId;
    private String journalDescription;
    private String journalModule;
    private String journalLineNumbering;
    private String journalContraType;
    private String journalContraAccount;
    private boolean journalContraFixed;
    private boolean journalEntryControl;
    private boolean journalApprovalRequired;
    private String approvalBy;
    private String journalType;
    private String journalAccessGroup; //allows access to the journal
    //Only applies to movement journals
    private String movementDirection;

    //Only applies to financial journals
    private boolean vatApplicable;

    /** Creates a new instance of BaseJournalDefinitionTable */
    public BaseJournalDefinitionTable() {
    }

    public String getJournalDefinitionId() {
        return journalDefinitionId;
    }

    public void setJournalDefinitionId(String journalDefinitionId) {
        this.journalDefinitionId = journalDefinitionId;
    }

    public String getJournalDescription() {
        return journalDescription;
    }

    public void setJournalDescription(String journalDescription) {
        this.journalDescription = journalDescription;
    }

    public String getJournalModule() {
        return journalModule;
    }

    public void setJournalModule(String journalModule) {
        this.journalModule = journalModule;
    }

    public String getJournalLineNumbering() {
        return journalLineNumbering;
    }

    public void setJournalLineNumbering(String journalLineNumbering) {
        this.journalLineNumbering = journalLineNumbering;
    }

    public boolean getJournalEntryControl() {
        return journalEntryControl;
    }

    public void setJournalEntryControl(boolean journalEntryControl) {
        this.journalEntryControl = journalEntryControl;
    }

    public boolean getJournalApprovalRequired() {
        return journalApprovalRequired;
    }

    public void setJournalApprovalRequired(boolean journalApprovalRequired) {
        this.journalApprovalRequired = journalApprovalRequired;
    }

    public boolean getJournalContraFixed() {
        return journalContraFixed;
    }

    public void setJournalContraFixed(boolean journalContraFixed) {
        this.journalContraFixed = journalContraFixed;
    }

    public String getJournalContraType() {
        return journalContraType;
    }

    public void setJournalContraType(String journalContraType) {
        this.journalContraType = journalContraType;
    }

    public String getJournalContraAccount() {
        return journalContraAccount;
    }

    public void setJournalContraAccount(String journalContraAccount) {
        this.journalContraAccount = journalContraAccount;
    }

    public String getApprovalBy() {
        return approvalBy;
    }

    public void setApprovalBy(String approvalBy) {
        this.approvalBy = approvalBy;
    }

    public String getJournalType() {
        return journalType;
    }

    public void setJournalType(String journalType) {
        this.journalType = journalType;
    }

    public String getMovementDirection() {
        return movementDirection;
    }

    public void setMovementDirection(String movementDirection) {
        this.movementDirection = movementDirection;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("journalDefinitionId", new JournalDefinitionId());
        toBuild.put("journalDescription", new Description());
        toBuild.put("journalModule", new JournalModule());
        toBuild.put("journalLineNumbering", new JournalLineNumbering());
        toBuild.put("journalContraType", new JournalContraType());
        toBuild.put("journalContraAccount", new JournalContraAccount());
        toBuild.put("journalContraFixed", new JournalContraFixed());
        toBuild.put("journalEntryControl", new JournalEntryControl());
        toBuild.put("journalApprovalRequired", new JournalApprovalRequired());
        toBuild.put("approvalBy", new ApprovedBy());
        toBuild.put("journalAccessGroup", new JournalAccessGroup());

        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("journalDefinitionId");
        toBuild.add("journalDescription");
        return toBuild;
    }

    public boolean isVatApplicable() {
        return vatApplicable;
    }

    public void setVatApplicable(boolean vatApplicable) {
        this.vatApplicable = vatApplicable;
    }

    /**
     * @return the journalAccessGroup
     */
    public String getJournalAccessGroup() {
        return journalAccessGroup;
    }

    /**
     * @param journalAccessGroup the journalAccessGroup to set
     */
    public void setJournalAccessGroup(String journalAccessGroup) {
        this.journalAccessGroup = journalAccessGroup;
    }
}
