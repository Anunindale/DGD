package emc.entity.debtors;

import emc.datatypes.debtors.creditrating.CreditRating;
import emc.datatypes.debtors.creditrating.Description;
import emc.datatypes.debtors.creditrating.TermsToleranceDays;
import emc.datatypes.debtors.creditrating.TolerancePercentage;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 
 * @Author wikus
 */
@Entity
@Table(name = "DebtorsCreditRating", uniqueConstraints = {@UniqueConstraint(columnNames = {"creditRating", "companyId"})})
public class DebtorsCreditRating extends EMCEntityClass {

    private String creditRating;
    private String description;
    private double tolerancePercentage;
    private int termsToleranceDays;

    public String getCreditRating() {
        return creditRating;
    }

    public void setCreditRating(String creditRating) {
        this.creditRating = creditRating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("creditRating", new CreditRating());
        toBuild.put("description", new Description());
        toBuild.put("tolerancePercentage", new TolerancePercentage());
        toBuild.put("termsToleranceDays", new TermsToleranceDays());

        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("creditRating");
        toBuild.add("description");
        toBuild.add("tolerancePercentage");
        toBuild.add("termsToleranceDays");
        return toBuild;
    }

    public double getTolerancePercentage() {
        return tolerancePercentage;
    }

    public void setTolerancePercentage(double tolerancePercentage) {
        this.tolerancePercentage = tolerancePercentage;
    }

    public int getTermsToleranceDays() {
        return termsToleranceDays;
    }

    public void setTermsToleranceDays(int termsToleranceDays) {
        this.termsToleranceDays = termsToleranceDays;
    }
}
