/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.transactions;

import javax.ejb.Local;

/**
 *
 * @author rico
 */
@Local
public interface ProcessStockTransactionLocal {

    /**
     * This method is used to generate an InventoryTransaction record and will also update on hand 
     * May encapsulate other updates as well
     * @param recordToGenerateFor - This is the record that will be used to generate the stock transaction
     * @param additional - should the recordToGenFor need a related record 
     * @param userData
     * @return EMCTable - normally the stock transaction that was generated, null if not supported or fails
     * @throws emc.bus.inventory.transactions.EMCStockException
     */
    public emc.tables.EMCTable post(emc.tables.EMCTable recordToGenerateFor, TransactionHelper additional, emc.framework.EMCUserData userData) throws emc.bus.inventory.transactions.EMCStockException;

    /**
     * This method is used to validate the post process
     * @param recordToGenerateFor - This is the record that will be used to generate the stock transaction
     * @param relatedRecord - should the recordToGenFor need a related record for instance header of post line
     * @param userData
     * @return EMCTable - normally the stock transaction that was generated, null if not supported or fails
     * @throws emc.bus.inventory.transactions.EMCStockException
     */
    public emc.tables.EMCTable validatePost(emc.tables.EMCTable recordToGenerateFor, emc.tables.EMCTable relatedRecord, emc.framework.EMCUserData userData) throws emc.bus.inventory.transactions.EMCStockException;
}
