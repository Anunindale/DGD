/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.debtors.journals;

import emc.datatypes.EMCDataType;
import emc.datatypes.debtors.customerinvoice.VATAmount;
import emc.datatypes.base.journals.superclass.ContraAccount;
import emc.datatypes.base.journals.superclass.ContraType;
import emc.datatypes.debtors.journals.LineAmount;
import emc.datatypes.debtors.journals.LineDescription;
import emc.datatypes.debtors.journals.LineNo;
import emc.datatypes.debtors.journals.LineReference;
import emc.datatypes.debtors.journals.LineTotal;
import emc.datatypes.base.journals.superclass.VATCode;
import emc.datatypes.debtors.journals.LineDate;
import emc.datatypes.debtors.journals.foreignkeys.JournalNumberFK;
import emc.datatypes.sop.customers.foreignkeys.CustomerIdFK;
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
 * @description : Entity class used to represent Debtors Journal Lines records.
 *
 * @date        : 04 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Table(name = "DebtorsJournalLines", uniqueConstraints = {@UniqueConstraint(columnNames = {"journalNumber", "lineNo", "companyId"}), @UniqueConstraint(columnNames = {"journalNumber", "lineRef"})})
@Entity
public class DebtorsJournalLines extends EMCEntityClass {

    private String journalNumber;
    private double lineNo;
    private String customerId;
    private String lineRef;
    private String lineDescription;
    private BigDecimal lineAmount = new BigDecimal(0);
    private String vatCode;
    private BigDecimal vatAmount = new BigDecimal(0);
    private BigDecimal lineTotal = new BigDecimal(0);
    private String contraType;
    private String contraAccount;
    private String transId;
    @Temporal(TemporalType.DATE)
    private Date lineDate;

    /** Creates a new instance of DebtorsJournalLines */
    public DebtorsJournalLines() {

    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("journalNumber", new JournalNumberFK());
        toBuild.put("lineNo", new LineNo());
        toBuild.put("customerId", new CustomerIdFK());
        toBuild.put("lineRef", new LineReference());
        toBuild.put("lineDescription", new LineDescription());
        toBuild.put("lineAmount", new LineAmount());
        toBuild.put("lineDate", new LineDate());
        toBuild.put("vatCode", new VATCode());
        toBuild.put("vatAmount", new VATAmount());
        toBuild.put("lineTotal", new LineTotal());
        toBuild.put("contraAccount", new ContraAccount());
        toBuild.put("contraType", new ContraType());

        return toBuild;
    }

    public String getJournalNumber() {
        return journalNumber;
    }

    public void setJournalNumber(String journalNumber) {
        this.journalNumber = journalNumber;
    }

    public double getLineNo() {
        return lineNo;
    }

    public void setLineNo(double lineNo) {
        this.lineNo = lineNo;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getLineRef() {
        return lineRef;
    }

    public void setLineRef(String lineRef) {
        this.lineRef = lineRef;
    }

    public String getLineDescription() {
        return lineDescription;
    }

    public void setLineDescription(String lineDescription) {
        this.lineDescription = lineDescription;
    }

    public BigDecimal getLineAmount() {
        return lineAmount;
    }

    public void setLineAmount(BigDecimal lineAmount) {
        this.lineAmount = lineAmount;
    }

    public String getVatCode() {
        return vatCode;
    }

    public void setVatCode(String vatCode) {
        this.vatCode = vatCode;
    }

    public BigDecimal getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(BigDecimal vatAmount) {
        this.vatAmount = vatAmount;
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

    public BigDecimal getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(BigDecimal lineTotal) {
        this.lineTotal = lineTotal;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public Date getLineDate() {
        return lineDate;
    }

    public void setLineDate(Date lineDate) {
        this.lineDate = lineDate;
    }
}
