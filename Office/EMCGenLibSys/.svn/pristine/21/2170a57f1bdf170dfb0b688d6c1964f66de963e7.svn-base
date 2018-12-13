/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.gl;

import emc.datatypes.EMCDataType;
import emc.datatypes.gl.parameters.BudgetFreezePeriods;
import emc.datatypes.gl.parameters.VATInputAccount;
import emc.datatypes.gl.parameters.VATOutputAccount;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "GLParameters", uniqueConstraints = {@UniqueConstraint(columnNames = "companyId")})
public class GLParameters extends EMCEntityClass {

    private int budgetFreezePeriods;
    private String vatInputAccount;
    private String vatOutputAccount;

    /** Creates a new instance of GLParameters. */
    public GLParameters() {
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("budgetFreezePeriods", new BudgetFreezePeriods());
        toBuild.put("vatInputAccount", new VATInputAccount());
        toBuild.put("vatOutputAccount", new VATOutputAccount());
        return toBuild;
    }

    public int getBudgetFreezePeriods() {
        return budgetFreezePeriods;
    }

    public void setBudgetFreezePeriods(int budgetFreezePeriods) {
        this.budgetFreezePeriods = budgetFreezePeriods;
    }

    public String getVatInputAccount() {
        return vatInputAccount;
    }

    public void setVatInputAccount(String vatInputAccount) {
        this.vatInputAccount = vatInputAccount;
    }

    public String getVatOutputAccount() {
        return vatOutputAccount;
    }

    public void setVatOutputAccount(String vatOutputAccount) {
        this.vatOutputAccount = vatOutputAccount;
    }
}
