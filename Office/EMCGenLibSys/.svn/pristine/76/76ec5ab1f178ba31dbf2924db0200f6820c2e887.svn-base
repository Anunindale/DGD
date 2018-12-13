/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.gl;

import emc.framework.EMCEntityClass;
import java.io.Serializable;
import emc.datatypes.gl.currency.Currency;
import emc.datatypes.gl.currency.DescriptiveName;
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
@Table(name = "GLCurrency", uniqueConstraints = {@UniqueConstraint(columnNames = {"currency", "companyId"})})
public class GLCurrency extends EMCEntityClass implements Serializable {

    private String currency;
    private String descriptiveName;

    /** Creates a new instance of GLCurrency */
    public GLCurrency() {
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescriptiveName() {
        return descriptiveName;
    }

    public void setDescriptiveName(String descriptiveName) {
        this.descriptiveName = descriptiveName;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("currency", new Currency());
        toBuild.put("descriptiveName", new DescriptiveName());

        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("currency");
        toBuild.add("descriptiveName");
        return toBuild;
    }
}
