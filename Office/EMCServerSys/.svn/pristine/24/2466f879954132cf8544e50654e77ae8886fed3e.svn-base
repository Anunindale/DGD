/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.transactions.pop;

import emc.bus.inventory.transactions.EMCStockException;
import emc.entity.pop.POPPurchaseOrderLines;
import emc.entity.pop.posting.POPPurchasePostMaster;
import emc.framework.EMCUserData;
import emc.tables.EMCTable;
import javax.ejb.Local;

/**
 *
 * @author rico
 */
@Local
public interface POPTransactionsLogicBeanLocal {

    public emc.tables.EMCTable postPOLine(emc.entity.pop.POPPurchaseOrderLines theLine, boolean setQtyToZero, emc.framework.EMCUserData userData) throws emc.bus.inventory.transactions.EMCStockException;

    /**
     * Returns stock to the supplier
     * @param postMaster
     * @param receiveLater
     * @param userData
     * @return
     * @throws emc.bus.inventory.transactions.EMCStockException
     */
    public EMCTable postReturnToSupplier(POPPurchasePostMaster postMaster, boolean receiveLater, EMCUserData userData) throws EMCStockException;

    /** 
     * Does the posting for Purchase Order Post Line
     * @param theLine
     * @param theHeader
     * @param userData
     * @return
     * @throws emc.bus.inventory.transactions.EMCStockException
     */
    public EMCTable postPOPostMaster(POPPurchasePostMaster thePostMaster, EMCUserData userData) throws EMCStockException;

    /**
     * Updates a blanket orders outstanding quantity
     * @param theLine
     * @param qty
     * @param userData
     * @return
     * @throws emc.bus.inventory.transactions.EMCStockException
     */
    public EMCTable updateBlanketOrderOutStanding(POPPurchaseOrderLines theLine, double qty, EMCUserData userData) throws EMCStockException;

}
