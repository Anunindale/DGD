/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.customerinvoice;

import emc.entity.debtors.DebtorsCreditNoteLines;
import emc.entity.debtors.DebtorsCustomerInvoiceLines;
import emc.entity.debtors.DebtorsParameters;
import emc.entity.debtors.superclasses.DebtorsInvoiceLinesSuper;
import emc.entity.debtors.superclasses.DebtorsInvoiceMasterSuper;
import emc.entity.inventory.picking.InventoryPickingListLines;
import emc.entity.sop.SOPSalesOrderLines;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCEntityBeanProtectedInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface DebtorsCustomerInvoiceLinesLocal extends EMCEntityBeanLocalInterface, EMCEntityBeanProtectedInterface {

    /**
     * Returns the invoice lines belonging to the specified invoice.
     * @param invoiceNo Invoice number.
     * @param userData User data.
     * @return A list if DebtorsCustomerInvoiceLines instances.
     */
    public java.util.List<emc.entity.debtors.DebtorsCustomerInvoiceLines> getInvoiceLines(java.lang.String invoiceNo, emc.framework.EMCUserData userData);

    /**
     * Returns the Invoice line which corresponds to the given Credit Note line.
     * @param invoiceNo Invoice number of line to fetch.
     * @param creditNoteLine Credit Note line to which Invoice line should correspond.
     * @return The specified Invoice line, or null, if it does not exist.
     */
    public DebtorsCustomerInvoiceLines getInvoiceLine(String invoiceNo, DebtorsCreditNoteLines creditNoteLine, EMCUserData userData);

    /**
     * Inserts a invoice line for sales orders
     * @param invoiceNumber the invoice master id
     * @param pickingListLine the picking list line that causes this line
     * @param salesOrderId the sales order id
     * @param parameters Debtors parameters.
     * @param userData plain old user data
     * @return The line that has been created.
     * @throws EMCEntityBeanException
     */
    public DebtorsCustomerInvoiceLines createSOInvoiceLine(String invoiceNumber, InventoryPickingListLines pickingListLine, SOPSalesOrderLines salesOrderLine, DebtorsParameters parameters, EMCUserData userData) throws EMCEntityBeanException;

    /**
     * Returns the highest line number on the specified Invoice.
     * @param invoiceNumber Invoice number.
     * @param userData User data.
     * @return The max line number on the specified Invoice.
     */
    public double getMaxLineNo(String invoiceNumber, EMCUserData userData);

    /**
     * Calculates and sets the total cost for the specified Invoice/Credit Note line.
     * @param line Invoice/Credit Note line to set total on.
     * @param master Invoice/Credit Note master record.
     * @param userData User data.
     */
    public void calculateLineTotal(DebtorsInvoiceLinesSuper line, DebtorsInvoiceMasterSuper master, EMCUserData userData) throws EMCEntityBeanException;

    public boolean cancelInvoiceLine(DebtorsCustomerInvoiceLines line, EMCUserData userData) throws EMCEntityBeanException;
}
