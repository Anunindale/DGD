/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.creditors;

import emc.datatypes.creditors.termsofpayment.TermsOfPaymentId;
import emc.datatypes.systemwide.Description;
import emc.enums.creditors.daysmonths.DaysMonths;
import emc.enums.creditors.principle.Principle;
import emc.framework.EMCEntityClass;
import java.io.Serializable;
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
@Table(name = "CreditorsTermsOfPayment", uniqueConstraints = {@UniqueConstraint(columnNames = {"termsOfPaymentId", "companyId"})})
public class CreditorsTermsOfPayment extends EMCEntityClass implements Serializable {

    private String termsOfPaymentId;
    private String description;
    private String principle = Principle.ACTUAL_DAYS.toString();
    private String daysOrMonths = DaysMonths.DAYS.toString();
    private int numberOf;

    /** Creates a new instance of CreditorsTermsOfPayment */
    public CreditorsTermsOfPayment() {
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("termsOfPaymentId", new TermsOfPaymentId());
        toBuild.put("description", new Description());

        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("termsOfPaymentId");
        toBuild.add("description");
        return toBuild;
    }
    
    public String getTermsOfPaymentId() {
        return termsOfPaymentId;
    }

    public void setTermsOfPaymentId(String termsOfPaymentId) {
        this.termsOfPaymentId = termsOfPaymentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrinciple() {
        return principle;
    }

    public void setPrinciple(String principle) {
        this.principle = principle;
    }

    public String getDaysOrMonths() {
        return daysOrMonths;
    }

    public void setDaysOrMonths(String daysOrMonths) {
        this.daysOrMonths = daysOrMonths;
    }

    public int getNumberOf() {
        return numberOf;
    }

    public void setNumberOf(int numberOf) {
        this.numberOf = numberOf;
    }
}
