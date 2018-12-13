/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.gl.transactions.logic.production;

import emc.bus.gl.transactions.logic.GLTransactionException;
import emc.bus.gl.transactions.logic.posthelpers.production.ProductionPickingListPostHelper;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface ProductionGLTransactionLogicLocal {

    /**
     * Posts GL transactions for production picking lists.
     * @param postHelper Post helper containing picking list information.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws GLTransactionException
     */
    public boolean postProductionPickingList(ProductionPickingListPostHelper postHelper, EMCUserData userData) throws GLTransactionException;
}
