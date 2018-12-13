/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.gl;

import emc.datatypes.gl.vatcodes.Percentage;
import emc.datatypes.gl.vatcodes.VatId;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import java.io.Serializable;
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
@Table(name = "GLVATCode", uniqueConstraints = {@UniqueConstraint(columnNames = {"vatId", "companyId"})})
public class GLVATCode extends EMCEntityClass implements Serializable {
    
    private String vatId;
    private String description;
    private double percentage;
    @Temporal(TemporalType.DATE)
    private Date fromDate;
    @Temporal(TemporalType.DATE)
    private Date toDate;
    
    /** Creates a new instance of GLVATCode */
    public GLVATCode() {
        
    }

    public String getVatId() {
        return vatId;
    }

    public void setVatId(String vatId) {
        this.vatId = vatId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
    
     @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        
        toBuild.put("vatId", new VatId());
        toBuild.put("description", new Description());
        toBuild.put("percentage", new Percentage());
        
        return toBuild;
     }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("vatId");
        toBuild.add("description");
        return toBuild;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
    
}
