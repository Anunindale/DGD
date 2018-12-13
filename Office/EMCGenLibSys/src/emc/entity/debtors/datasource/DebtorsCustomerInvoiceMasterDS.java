/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.debtors.datasource;

import emc.datatypes.EMCDataType;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import java.util.HashMap;

/**
 *
 * @author riaan
 */
public class DebtorsCustomerInvoiceMasterDS extends DebtorsCustomerInvoiceMaster {

    /** Creates a new instance of DebtorsCustomerInvoiceMasterDS */
    public DebtorsCustomerInvoiceMasterDS() {
        this.setDataSource(true);
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        return toBuild;
    }
}
