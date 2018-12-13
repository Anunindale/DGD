/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.debtors;

import emc.datatypes.EMCDataType;
import emc.datatypes.debtors.customerinvoice.InvCNNumber;
import emc.entity.debtors.superclasses.DebtorsInvoiceLinesSuper;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "DebtorsCustomerInvoiceLines", uniqueConstraints = {@UniqueConstraint(columnNames = {"invCNNumber", "lineNo", "companyId"})})
public class DebtorsCustomerInvoiceLines extends DebtorsInvoiceLinesSuper {

    /** Creates a new instance of DebtorsCustomerInvoiceLines. */
    public DebtorsCustomerInvoiceLines() {
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("invCNNumber", new InvCNNumber());

        return toBuild;
    }
}
