/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.debtors;

import emc.datatypes.EMCDataType;
import emc.datatypes.debtors.rebatecodes.Description;
import emc.datatypes.debtors.rebatecodes.RebateCode;
import emc.framework.EMCEntityClass;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
@Table(name = "DebtorsRebateCodes", uniqueConstraints = {@UniqueConstraint(columnNames = {"rebateCode"})})
public class DebtorsRebateCodes extends EMCEntityClass {

    private String rebateCode;
    private String description;
    private BigDecimal rebatePercentage;
    @Temporal(TemporalType.DATE)
    private Date effectivityDateFrom;
    @Temporal(TemporalType.DATE)
    private Date effectivityDateTo;

    /** Creates a new instance of DebtorsRebateCodes. */
    public DebtorsRebateCodes() {
        
    }

    public String getRebateCode() {
        return rebateCode;
    }

    public void setRebateCode(String rebateCode) {
        this.rebateCode = rebateCode;
    }

    public BigDecimal getRebatePercentage() {
        return rebatePercentage;
    }

    public void setRebatePercentage(BigDecimal rebatePercentage) {
        this.rebatePercentage = rebatePercentage;
    }

    public Date getEffectivityDateFrom() {
        return effectivityDateFrom;
    }

    public void setEffectivityDateFrom(Date effectivityDateFrom) {
        this.effectivityDateFrom = effectivityDateFrom;
    }

    public Date getEffectivityDateTo() {
        return effectivityDateTo;
    }

    public void setEffectivityDateTo(Date effectivityDateTo) {
        this.effectivityDateTo = effectivityDateTo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("rebateCode", new RebateCode());
        toBuild.put("description", new Description());

        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> ret = new ArrayList<String>();

        ret.add("rebateCode");
        ret.add("description");
        ret.add("rebatePercentage");

        return ret;
    }
}
