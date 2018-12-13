/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.debtors;

import emc.datatypes.EMCDataType;
import emc.datatypes.debtors.PaymentDate;
import emc.datatypes.debtors.postdatedpayments.InternalRef;
import emc.datatypes.debtors.postdatedpayments.PaymentNumber;
import emc.datatypes.debtors.postdatedpayments.Text;
import emc.datatypes.sop.customers.foreignkeys.CustomerIdFK;
import emc.enums.debtors.postdatedpayment.PaymentType;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @description : Entity class for post dated payments.
 *
 * @date        : 19 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Entity
@Table(name = "DebtorsPostDatedPayment")
public class DebtorsPostDatedPayment extends EMCEntityClass {

    private String internalRef;
    private String customer;
    @Column(length = 1000)
    private String text;
    private String paymentType = PaymentType.CHEQUE.toString();
    private String paymentNumber;
    @Temporal(TemporalType.DATE)
    private Date paymentDate;
    private BigDecimal paymentAmount = BigDecimal.ZERO;
    @Temporal(TemporalType.DATE)
    private Date presentedDate;
    private boolean processed;

    /** Creates a new instance of DebtorsPostDatedPayment */
    public DebtorsPostDatedPayment() {
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("paymentDate", new PaymentDate());
        toBuild.put("internalRef", new InternalRef());
        toBuild.put("customer", new CustomerIdFK());
        toBuild.put("text", new Text());
        toBuild.put("paymentNumber", new PaymentNumber());

        return toBuild;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery ret = super.buildQuery();

        ret.addAnd("processed", false);

        return ret;
    }
    
    public String getInternalRef() {
        return internalRef;
    }

    public void setInternalRef(String internalRef) {
        this.internalRef = internalRef;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPaymentNumber() {
        return paymentNumber;
    }

    public void setPaymentNumber(String paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Date getPresentedDate() {
        return presentedDate;
    }

    public void setPresentedDate(Date presentedDate) {
        this.presentedDate = presentedDate;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }
}
