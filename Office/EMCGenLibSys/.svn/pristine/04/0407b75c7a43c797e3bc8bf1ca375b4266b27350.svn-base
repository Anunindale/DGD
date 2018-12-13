/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.debtors;

import emc.datatypes.EMCDataType;
import emc.datatypes.debtors.creditinsurer.CreditInsurerID;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @description : Entity class used to store Credit Insurer records.
 *
 * @date        : 06 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Entity
@Table(name = "DebtorsCreditInsurer", uniqueConstraints = {@UniqueConstraint(columnNames = {"creditInsurerId", "companyId"})})
public class DebtorsCreditInsurer extends EMCEntityClass {

    private String creditInsurerId;
    private String description;
    
    /** Creates a new instance of DebtorsCreditInsurer */
    public DebtorsCreditInsurer() {

    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("creditInsurerId", new CreditInsurerID());
        toBuild.put("description", new Description());

        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> ret = super.buildDefaultLookupFieldList();

        ret.add("creditInsurerId");
        ret.add("description");

        return ret;
    }

    public String getCreditInsurerId() {
        return creditInsurerId;
    }

    public void setCreditInsurerId(String creditInsurerId) {
        this.creditInsurerId = creditInsurerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
