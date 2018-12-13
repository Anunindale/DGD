/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.journals;

import emc.datatypes.gl.glaccount.foreignkey.GLAccountNumFKMandatory;
import emc.datatypes.base.journals.journaldefinition.foreignkeys.JournalDefinitionIdFK;
import emc.datatypes.inventory.journals.journalmaster.JournalCreatedDate;
import emc.datatypes.inventory.journals.journalmaster.JournalDescription;
import emc.datatypes.inventory.journals.journalmaster.JournalNumber;
import emc.datatypes.inventory.journals.journalmaster.JournalPostedDate;
import emc.datatypes.systemwide.DetailedDescription;
import emc.enums.base.journals.JournalStatus;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "InventoryJournalMaster", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "journalNumber"})})
public class InventoryJournalMaster extends EMCEntityClass {
    
    private String journalDefinitionId;
    private String journalNumber;
    private String journalDescription;
    private String journalFunction;
    @Temporal(TemporalType.DATE)
    private Date journalDate;
    private String journalStatus = JournalStatus.CAPTURED.toString();
    private String journalText;
    private String journalPosting;
    private String journalContraType;
    private String journalContraAccount;
    //private String journalEnteredBy;  Using createdBy and createdDate
    private String journalApprovedBy;
    @Temporal(TemporalType.DATE)
    private Date journalApprovedDate;
    private String journalPostedBy;
    @Temporal(TemporalType.DATE)
    private Date journalPostedDate;
    
    /** Creates a new instance of InventoryJournalMaster */
    public InventoryJournalMaster() {
        
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

    public String getJournalFunction() {
        return journalFunction;
    }

    public void setJournalFunction(String journalFunction) {
        this.journalFunction = journalFunction;
    }

    public String getJournalNumber() {
        return journalNumber;
    }

    public void setJournalNumber(String journalNumber) {
        this.journalNumber = journalNumber;
    }

    public String getJournalStatus() {
        return journalStatus;
    }

    public void setJournalStatus(String journalStatus) {
        this.journalStatus = journalStatus;
    }

    public Date getJournalDate() {
        return journalDate;
    }

    public void setJournalDate(Date journalDate) {
        this.journalDate = journalDate;
    }

    public String getJournalText() {
        return journalText;
    }

    public void setJournalText(String journalText) {
        this.journalText = journalText;
    }

    public String getJournalPosting() {
        return journalPosting;
    }

    public void setJournalPosting(String journalPosting) {
        this.journalPosting = journalPosting;
    }
    
    public String getJournalApprovedBy() {
        return journalApprovedBy;
    }

    public void setJournalApprovedBy(String journalApprovedBy) {
        this.journalApprovedBy = journalApprovedBy;
    }

    public String getJournalPostedBy() {
        return journalPostedBy;
    }

    public void setJournalPostedBy(String journalPostedBy) {
        this.journalPostedBy = journalPostedBy;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        
        toBuild.put("journalDefinitionId", new JournalDefinitionIdFK());
        toBuild.put("journalNumber", new JournalNumber());
        toBuild.put("journalText", new DetailedDescription());
        toBuild.put("journalContraAccount", new GLAccountNumFKMandatory());
        toBuild.put("journalDescription", new JournalDescription());
        toBuild.put("journalPostedDate", new JournalPostedDate());
        toBuild.put("journalStatus",new emc.datatypes.inventory.journals.journalmaster.JournalStatus());
        toBuild.put("createdDate",new JournalCreatedDate());

        return toBuild;
    }

    public String getJournalContraType() {
        return journalContraType;
    }

    public void setJournalContraType(String journalContraType) {
        this.journalContraType = journalContraType;
    }

    public Date getJournalApprovedDate() {
        return journalApprovedDate;
    }

    public void setJournalApprovedDate(Date journalApprovedDate) {
        this.journalApprovedDate = journalApprovedDate;
    }

    public Date getJournalPostedDate() {
        return journalPostedDate;
    }

    public void setJournalPostedDate(Date journalPostedDate) {
        this.journalPostedDate = journalPostedDate;
    }

    public String getJournalContraAccount() {
        return journalContraAccount;
    }

    public void setJournalContraAccount(String journalContraAccount) {
        this.journalContraAccount = journalContraAccount;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = super.buildQuery();
        
        query.addAnd("journalStatus", emc.enums.base.journals.JournalStatus.POSTED, EMCQueryConditions.NOT);
        query.addOrderBy("journalNumber");
        
        return query;
    }

    @Override
    public EMCQuery buildLookupQuery() {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalMaster.class.getName());
        
        query.addOrderBy("journalNumber");
        
        return query;
    }

}
