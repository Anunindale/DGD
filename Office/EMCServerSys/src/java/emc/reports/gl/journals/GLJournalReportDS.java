/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.gl.journals;

import java.math.BigDecimal;

/**
 *
 * @author claudette
 */
public class GLJournalReportDS {

    private String journalNumber;
    private String journalDescription;
    private String journalStatus;
    private String journalDate;
    private String approvedDate;
    private String approvedBy;
    private String postedDate;
    private String postedBy;
    private String extRef;
    private String type;
    private String account;
    private String description;
    private BigDecimal debit;
    private BigDecimal credit;
    private String contraType;
    private String contraAccount;
    private BigDecimal contraDebit;
    private BigDecimal contraCredit;

    public String getJournalNumber() {
        return journalNumber;
    }

    public void setJournalNumber(String journalNumber) {
        this.journalNumber = journalNumber;
    }

    public String getJournalDescription() {
        return journalDescription;
    }

    public void setJournalDescription(String journalDescription) {
        this.journalDescription = journalDescription;
    }

    public String getJournalStatus() {
        return journalStatus;
    }

    public void setJournalStatus(String journalStatus) {
        this.journalStatus = journalStatus;
    }

    public String getJournalDate() {
        return journalDate;
    }

    public void setJournalDate(String journalDate) {
        this.journalDate = journalDate;
    }

    public String getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getExtRef() {
        return extRef;
    }

    public void setExtRef(String extRef) {
        this.extRef = extRef;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public String getContraType() {
        return contraType;
    }

    public void setContraType(String contraType) {
        this.contraType = contraType;
    }

    public String getContraAccount() {
        return contraAccount;
    }

    public void setContraAccount(String contraAccount) {
        this.contraAccount = contraAccount;
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
