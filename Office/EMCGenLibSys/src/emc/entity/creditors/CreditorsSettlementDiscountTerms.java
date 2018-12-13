/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.creditors;

import emc.datatypes.creditors.settlementdicountgroups.SettlementDiscountTermId;
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
@Table(name = "CreditorsSettlementDiscount", uniqueConstraints = {@UniqueConstraint(columnNames = {"settlementDiscountTermId", "companyId"})})
public class CreditorsSettlementDiscountTerms extends EMCEntityClass implements Serializable {

    private String settlementDiscountTermId;
    private String description;
    private String principle = Principle.ACTUAL_DAYS.toString();
    private String daysOrMonths = DaysMonths.DAYS.toString();
    private int numberOf;
    private double discountPercentage;

    /** Creates a new instance of CreditorsSettlementDiscount */
    public CreditorsSettlementDiscountTerms() {
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("settlementDiscountTermId", new SettlementDiscountTermId());
        toBuild.put("description", new Description());

        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("settlementDiscountTermId");
        toBuild.add("description");
        return toBuild;
    }
    
    public String getSettlementDiscountTermId() {
        return settlementDiscountTermId;
    }

    public void setSettlementDiscountTermId(String settlementDiscountTermId) {
        this.settlementDiscountTermId = settlementDiscountTermId;
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

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
