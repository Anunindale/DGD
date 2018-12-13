/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.base.journals.superclass;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.availablenumbersequences.Status;
import emc.datatypes.base.journals.journaldefinition.foreignkeys.JournalDefinitionIdFK;
import emc.datatypes.base.journals.superclass.ApprovedBy;
import emc.datatypes.base.journals.superclass.ApprovedDate;
import emc.datatypes.base.journals.superclass.ContraAccount;
import emc.datatypes.base.journals.superclass.ContraType;
import emc.datatypes.base.journals.superclass.Description;
import emc.datatypes.base.journals.superclass.EnteredBy;
import emc.datatypes.base.journals.superclass.EnteredDate;
import emc.datatypes.base.journals.superclass.JournalDate;
import emc.datatypes.base.journals.superclass.JournalNumber;
import emc.datatypes.base.journals.superclass.PostedBy;
import emc.datatypes.base.journals.superclass.PostedDate;
import emc.datatypes.base.journals.superclass.Text;
import emc.enums.base.journals.JournalStatus;
import emc.framework.EMCEntityClass;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @description : Super class for journal master entity classes in EMC.
 *
 * @date        : 12 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Entity
public class JournalMasterSuperClass extends EMCEntityClass {

    private String journalDefinitionId;
    private String journalNumber;
    private String journalDescription;
    private String journalFunction;
    @Temporal(TemporalType.DATE)
    private Date journalDate;
    private String journalStatus = JournalStatus.CAPTURED.toString();
    @Column(length = 1000)
    private String journalText;
    private String journalPosting;
    private String journalContraType;
    private String journalContraAccount;
    private String journalApprovedBy;
    @Temporal(TemporalType.DATE)
    private Date journalApprovedDate;
    private String journalPostedBy;
    @Temporal(TemporalType.DATE)
    private Date journalPostedDate;

    /** Creates a new instance of JournalMasterSuperClass */
    public JournalMasterSuperClass() {

    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("journalNumber", new JournalNumber());
        toBuild.put("journalDescription", new Description());
        toBuild.put("journalStatus", new Status());
        toBuild.put("journalText", new Text());
        toBuild.put("journalContraType", new ContraType());
        toBuild.put("journalContraAccount", new ContraAccount());
        toBuild.put("createdBy", new EnteredBy());
        toBuild.put("createdDate", new EnteredDate());
        toBuild.put("journalApprovedBy", new ApprovedBy());
        toBuild.put("journalApprovedDate", new ApprovedDate());
        toBuild.put("journalPostedBy", new PostedBy());
        toBuild.put("journalPostedDate", new PostedDate());
        toBuild.put("journalDefinitionId", new JournalDefinitionIdFK());
        toBuild.put("journalDate", new JournalDate());

        return toBuild;
    }
    
    public String getJournalApprovedBy() {
        return journalApprovedBy;
    }

    public void setJournalApprovedBy(String journalApprovedBy) {
        this.journalApprovedBy = journalApprovedBy;
    }

    public Date getJournalApprovedDate() {
        return journalApprovedDate;
    }

    public void setJournalApprovedDate(Date journalApprovedDate) {
        this.journalApprovedDate = journalApprovedDate;
    }

    public String getJournalContraAccount() {
        return journalContraAccount;
    }

    public void setJournalContraAccount(String journalContraAccount) {
        this.journalContraAccount = journalContraAccount;
    }

    public String getJournalContraType() {
        return journalContraType;
    }

    public void setJournalContraType(String journalContraType) {
        this.journalContraType = journalContraType;
    }

    public Date getJournalDate() {
        return journalDate;
    }

    public void setJournalDate(Date journalDate) {
        this.journalDate = journalDate;
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

    public String getJournalPostedBy() {
        return journalPostedBy;
    }

    public void setJournalPostedBy(String journalPostedBy) {
        this.journalPostedBy = journalPostedBy;
    }

    public Date getJournalPostedDate() {
        return journalPostedDate;
    }

    public void setJournalPostedDate(Date journalPostedDate) {
        this.journalPostedDate = journalPostedDate;
    }

    public String getJournalPosting() {
        return journalPosting;
    }

    public void setJournalPosting(String journalPosting) {
        this.journalPosting = journalPosting;
    }

    public String getJournalStatus() {
        return journalStatus;
    }

    public void setJournalStatus(String journalStatus) {
        this.journalStatus = journalStatus;
    }

    public String getJournalText() {
        return journalText;
    }

    public void setJournalText(String journalText) {
        this.journalText = journalText;
    }
}
