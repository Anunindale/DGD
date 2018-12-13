/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.gl.journals;

import emc.datatypes.EMCDataType;
import emc.datatypes.gl.analysiscode1.foreignkeys.AnalysisCode1FKNM;
import emc.datatypes.gl.analysiscode2.foreignkeys.AnalysisCode2FKNM;
import emc.datatypes.gl.analysiscode3.foreignkeys.AnalysisCode3FKNM;
import emc.datatypes.gl.analysiscode4.foreignkeys.AnalysisCode4FKNM;
import emc.datatypes.gl.analysiscode5.foreignkeys.AnalysisCode5FKNM;
import emc.datatypes.gl.analysiscode6.foreignkeys.AnalysisCode6FKNM;
import emc.datatypes.gl.journallines.Account;
import emc.datatypes.gl.journallines.ContraAccount;
import emc.datatypes.gl.journallines.ContraCredit;
import emc.datatypes.gl.journallines.ContraDebit;
import emc.datatypes.gl.journallines.Credit;
import emc.datatypes.gl.journallines.Debit;
import emc.datatypes.gl.journallines.ExtReference;
import emc.datatypes.gl.journallines.LineDate;
import emc.datatypes.gl.journallines.LineType;
import emc.datatypes.gl.journallines.TransactionId;
import emc.datatypes.gl.journallines.VATAmount;
import emc.datatypes.gl.journalmaster.ContraType;
import emc.datatypes.gl.journalmaster.Text;
import emc.datatypes.gl.journalmaster.VATIncluded;
import emc.datatypes.gl.journalmaster.VATInputOutput;
import emc.datatypes.gl.journalmaster.foreignkey.JournalNumberFK;
import emc.datatypes.gl.vatcodes.foreignkeys.VATCodeFKNotMandatory;
import emc.datatypes.systemwide.Description;
import emc.datatypes.systemwide.LineNo;
import emc.enums.gl.journals.JournalLineType;
import emc.framework.EMCEntityClass;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "GLJournalLines")
public class GLJournalLines extends EMCEntityClass {

    private String journalNumber;
    private BigDecimal lineNumber;
    private String lineType = JournalLineType.GL.toString();
    @Temporal(TemporalType.DATE)
    private Date lineDate;
    private String journalTransId;
    private String extReference;
    private String description;
    private String account;
    private BigDecimal debit = new BigDecimal(0);
    private BigDecimal credit = new BigDecimal(0);
    private String contraType;
    private String contraAccount;
    private BigDecimal contraDebit = new BigDecimal(0);
    private BigDecimal contraCredit = new BigDecimal(0);
    private boolean vatIncluded;
    private String vatCode;
    private String vatInputOutput;
    private BigDecimal vatAmount = new BigDecimal(0);
    private String text;
    private String analysisCode1;
    private String analysisCode2;
    private String analysisCode3;
    private String analysisCode4;
    private String analysisCode5;
    private String analysisCode6;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public String getContraAccount() {
        return contraAccount;
    }

    public void setContraAccount(String contraAccount) {
        this.contraAccount = contraAccount;
    }

    public String getContraType() {
        return contraType;
    }

    public void setContraType(String contraType) {
        this.contraType = contraType;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExtReference() {
        return extReference;
    }

    public void setExtReference(String extReference) {
        this.extReference = extReference;
    }

    public String getJournalNumber() {
        return journalNumber;
    }

    public void setJournalNumber(String journalNumber) {
        this.journalNumber = journalNumber;
    }

    public String getJournalTransId() {
        return journalTransId;
    }

    public void setJournalTransId(String journalTransId) {
        this.journalTransId = journalTransId;
    }

    public Date getLineDate() {
        return lineDate;
    }

    public void setLineDate(Date lineDate) {
        this.lineDate = lineDate;
    }

    public BigDecimal getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(BigDecimal lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public BigDecimal getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(BigDecimal vatAmount) {
        this.vatAmount = vatAmount;
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
        toBuild.put("journalNumber", new JournalNumberFK());
        toBuild.put("lineNumber", new LineNo());
        toBuild.put("lineDate", new LineDate());
        toBuild.put("journalTransId", new TransactionId());
        toBuild.put("extReference", new ExtReference());
        toBuild.put("description", new Description());
        toBuild.put("account", new Account());
        toBuild.put("debit", new Debit());
        toBuild.put("credit", new Credit());
        toBuild.put("contraType", new ContraType());
        toBuild.put("contraAccount", new ContraAccount());
        toBuild.put("contraDebit", new ContraDebit());
        toBuild.put("contraCredit", new ContraCredit());
        toBuild.put("vatIncluded", new VATIncluded());
        toBuild.put("vatCode", new VATCodeFKNotMandatory());
        toBuild.put("vatInputOutput", new VATInputOutput());
        toBuild.put("vatAmount", new VATAmount());
        toBuild.put("text", new Text());
        toBuild.put("analysisCode1", new AnalysisCode1FKNM());
        toBuild.put("analysisCode2", new AnalysisCode2FKNM());
        toBuild.put("analysisCode3", new AnalysisCode3FKNM());
        toBuild.put("analysisCode4", new AnalysisCode4FKNM());
        toBuild.put("analysisCode5", new AnalysisCode5FKNM());
        toBuild.put("analysisCode6", new AnalysisCode6FKNM());

        toBuild.put("lineType", new LineType());
        return toBuild;
    }

    public String getLineType() {
        return lineType;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }

    public String getAnalysisCode5() {
        return analysisCode5;
    }

    public void setAnalysisCode5(String analysisCode5) {
        this.analysisCode5 = analysisCode5;
    }

    public String getAnalysisCode6() {
        return analysisCode6;
    }

    public void setAnalysisCode6(String analysisCode6) {
        this.analysisCode6 = analysisCode6;
    }

    public BigDecimal getContraDebit() {
        return contraDebit;
    }

    public void setContraDebit(BigDecimal contraDebit) {
        this.contraDebit = contraDebit;
    }

    public BigDecimal getContraCredit() {
        return contraCredit;
    }

    public void setContraCredit(BigDecimal contraCredit) {
        this.contraCredit = contraCredit;
    }
}
