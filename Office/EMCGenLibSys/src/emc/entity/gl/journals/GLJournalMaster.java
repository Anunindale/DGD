/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.gl.journals;

import emc.datatypes.EMCDataType;
import emc.datatypes.debtors.customerinvoice.ApprovedBy;
import emc.datatypes.debtors.customerinvoice.ApprovedDate;
import emc.datatypes.debtors.customerinvoice.PostedBy;
import emc.datatypes.debtors.customerinvoice.PostedDate;
import emc.datatypes.gl.analysiscode1.foreignkeys.AnalysisCode1FKNM;
import emc.datatypes.gl.analysiscode2.foreignkeys.AnalysisCode2FKNM;
import emc.datatypes.gl.analysiscode3.foreignkeys.AnalysisCode3FKNM;
import emc.datatypes.gl.analysiscode4.foreignkeys.AnalysisCode4FKNM;
import emc.datatypes.gl.analysiscode5.foreignkeys.AnalysisCode5FKNM;
import emc.datatypes.gl.analysiscode6.foreignkeys.AnalysisCode6FKNM;
import emc.datatypes.gl.chartofaccounts.foreignkeys.AccountNumberFKNM;
import emc.datatypes.gl.journalmaster.ContraType;
import emc.datatypes.gl.journalmaster.Description;
import emc.datatypes.gl.journalmaster.JournalDefinitionId;
import emc.datatypes.gl.journalmaster.JournalNumber;
import emc.datatypes.gl.journalmaster.POSTDetailSummary;
import emc.datatypes.gl.journalmaster.Status;
import emc.datatypes.gl.journalmaster.Text;
import emc.datatypes.gl.journalmaster.VATIncluded;
import emc.datatypes.gl.journalmaster.VATInputOutput;
import emc.datatypes.gl.vatcodes.foreignkeys.VATCodeFKNotMandatory;
import emc.entity.base.journals.superclass.JournalMasterSuperClass;
import emc.enums.base.journals.JournalStatus;
import emc.enums.emcquery.EMCQueryConditions;
import emc.framework.EMCQuery;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "GLJournalMaster", uniqueConstraints = {@UniqueConstraint(columnNames = {"journalNumber", "companyId"})})
public class GLJournalMaster extends JournalMasterSuperClass {

    private String text;
    private boolean vatIncluded;
    private String vatCode;
    private String vatInputOutput;
    private String postDetailSummary;
    private String analysisCode1;
    private String analysisCode2;
    private String analysisCode3;
    private String analysisCode4;
    private String analysisCode5;
    private String analysisCode6;

    /** Creates a new instance of GLJournalMaster. */
    public GLJournalMaster() {
        //Default journal date to today.
        //Don't use static method on functions class - not thread-safe if
        //called from multiple transactions on server.
        this.setJournalDate(new Date());
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = super.buildQuery();

        query.addAnd("journalStatus", JournalStatus.POSTED, EMCQueryConditions.NOT);
        query.addOrderBy("journalNumber");

        return query;
    }

    public String getAnalysisCode1() {
        return analysisCode1;
    }

    public void setAnalysisCode1(String analysisCode1) {
        this.analysisCode1 = analysisCode1;
    }

    public String getAnalysisCode2() {
        return analysisCode2;
    }

    public void setAnalysisCode2(String analysisCode2) {
        this.analysisCode2 = analysisCode2;
    }

    public String getAnalysisCode3() {
        return analysisCode3;
    }

    public void setAnalysisCode3(String analysisCode3) {
        this.analysisCode3 = analysisCode3;
    }

    public String getAnalysisCode4() {
        return analysisCode4;
    }

    public void setAnalysisCode4(String analysisCode4) {
        this.analysisCode4 = analysisCode4;
    }

    public String getPostDetailSummary() {
        return postDetailSummary;
    }

    public void setPostDetailSummary(String postDetailSummary) {
        this.postDetailSummary = postDetailSummary;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getVatCode() {
        return vatCode;
    }

    public void setVatCode(String vatCode) {
        this.vatCode = vatCode;
    }

    public boolean isVatIncluded() {
        return vatIncluded;
    }

    public void setVatIncluded(boolean vatIncluded) {
        this.vatIncluded = vatIncluded;
    }

    public String getVatInputOutput() {
        return vatInputOutput;
    }

    public void setVatInputOutput(String vatInputOutput) {
        this.vatInputOutput = vatInputOutput;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("journalNumber", new JournalNumber());
        toBuild.put("journalDescription", new Description());
        toBuild.put("journalDefinitionId", new JournalDefinitionId());
        toBuild.put("journalStatus", new Status());
        toBuild.put("text", new Text());
        toBuild.put("journalContraType", new ContraType());
        toBuild.put("journalContraAccount", new AccountNumberFKNM());
        toBuild.put("vatIncluded", new VATIncluded());
        toBuild.put("vatCode", new VATCodeFKNotMandatory());
        toBuild.put("vatInputOutput", new VATInputOutput());
        toBuild.put("postDetailSummary", new POSTDetailSummary());
        toBuild.put("analysisCode1", new AnalysisCode1FKNM());
        toBuild.put("analysisCode2", new AnalysisCode2FKNM());
        toBuild.put("analysisCode3", new AnalysisCode3FKNM());
        toBuild.put("analysisCode4", new AnalysisCode4FKNM());
        toBuild.put("analysisCode5", new AnalysisCode5FKNM());
        toBuild.put("analysisCode6", new AnalysisCode6FKNM());
        toBuild.put("journalApprovedBy", new ApprovedBy());
        toBuild.put("journalApprovedDate", new ApprovedDate());
        toBuild.put("journalPostedBy", new PostedBy());
        toBuild.put("journalPostedDate", new PostedDate());
        return toBuild;
    }
    /**
     * @return the analysisCode5
     */
    public String getAnalysisCode5() {
        return analysisCode5;
    }

    /**
     * @param analysisCode5 the analysisCode5 to set
     */
    public void setAnalysisCode5(String analysisCode5) {
        this.analysisCode5 = analysisCode5;
    }

    /**
     * @return the analysisCode6
     */
    public String getAnalysisCode6() {
        return analysisCode6;
    }

    /**
     * @param analysisCode6 the analysisCode6 to set
     */
    public void setAnalysisCode6(String analysisCode6) {
        this.analysisCode6 = analysisCode6;
    }
}
