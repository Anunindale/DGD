/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.transactions.sop;

import emc.bus.inventory.transactions.EMCStockException;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.entity.sop.SOPSalesOrderLines;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import emc.tables.EMCTable;
import java.math.BigDecimal;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface SOPTransactionLogicLocal {

    /**
     * Manages the ordered out transactions for sales order lines.
     * @param salesOrderLine The Sales Order Line to reference.
     * @param userData Plain Old User Data.
     * @return The transacation in the form of a EMCTable Object.
     * @throws EMCStockException If anything goes wrong.
     */
    public emc.tables.EMCTable orderSOLineStock(emc.entity.sop.SOPSalesOrderLines salesOrderLine, emc.framework.EMCUserData userData) throws emc.bus.inventory.transactions.EMCStockException;

    /**
     * Updates the Inventory Summary table to indicate the outstanding items on a Blanket Order.
     * @param salesOrderLine Blanket Order line.
     * @param quantity Quantity to add to summary.  Negative quantities will be deducted.
     * @param userData User data.
     * @return The given Blanket Order line.
     * @throws EMCStockExceptionm, EMCEntityBeanException
     */
    public EMCTable orderBOLine(SOPSalesOrderLines salesOrderLine, BigDecimal quantity, EMCUserData userData) throws EMCStockException, EMCEntityBeanException;

    /**
     * Reallocates stock.
     * @param fromTransaction Transaction on which reserved quantity should be decreased.
     * @param toSalesOrderLine Sales order line to which stock should be reallocated.  May be null if stock should only be unreserved.
     * @param quantity Quantity to reallocate.  This should always be in the item base UoM.
     * @param toCustomer Customer id of customer to whom stock is being reallocated.
     * @param unreserve Indicates whether stock should only be unreserved and not reallocated.
     * @param salesOrderType Indicates Blanket Order or Sales Order.
     * @param userData User data.
     * @return fromTransaction
     * @throws emc.framework.EMCEntityBeanException
     * @throws emc.bus.inventory.transactions.EMCStockException
     */
    public emc.framework.EMCEntityClass reallocateSOLineStock(emc.entity.inventory.transactions.InventoryTransactions fromTransaction, emc.entity.sop.SOPSalesOrderLines toSalesOrderLine, java.math.BigDecimal quantity, java.lang.String toCustomer, boolean unreserve, java.lang.String salesOrderType, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException, emc.bus.inventory.transactions.EMCStockException;

    public emc.tables.EMCTable reserveSOLineStock(emc.entity.sop.SOPSalesOrderPostLines salesOrderPostLine, java.lang.String salesOrderNo, java.lang.String salesOrderType, boolean checkPickList, emc.framework.EMCUserData userData) throws emc.bus.inventory.transactions.EMCStockException;
}
