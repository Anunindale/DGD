/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.inventory;

import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface InventoryCostingGroupLocal extends EMCEntityBeanLocalInterface {

     /** Updates the cost of items on either the Item Master or Item Dimension Combination using the cost group specified on the item.
     *  The cost is pulled through from the oldest journal line.
     *
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean updateCostRoutine(emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

     /** Updates transaction costs.
     *
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean fixTransactions(EMCUserData userData) throws EMCEntityBeanException;
   
}
