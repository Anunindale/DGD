/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.debtors.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.debtors.creditheld.Rep;
import emc.entity.debtors.creditheld.DebtorsCreditHeldMaster;
import java.math.BigDecimal;
import java.util.HashMap;

/**
 *
 * @author riaan
 */
public class DebtorsCreditHeldMasterDS extends DebtorsCreditHeldMaster {

    private String customerName;
    private BigDecimal totalCreditHeld;
    private String rep;

    /** Creates a new instance of DebtorsCreditHeldMasterDS. */
    public DebtorsCreditHeldMasterDS() {
        this.setDataSource(true);
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public BigDecimal getTotalCreditHeld() {
        return totalCreditHeld;
    }

    public void setTotalCreditHeld(BigDecimal totalCreditHeld) {
        this.totalCreditHeld = totalCreditHeld;
    }

    public String getRep() {
        return rep;
    }

    public void setRep(String rep) {
        this.rep = rep;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("rep", new Rep());

        return toBuild;
    }
}
