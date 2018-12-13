/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.pegging;

import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

/**
 *
 * @author Owner
 */
@Local
public interface InventoryPeggingLocal extends EMCEntityBeanLocalInterface {

    /**
     * Create A Pegging Record Between the Demand And Supply
     * The method will go and fetch the Requirements Planning for the records passed in and peg with their record IDs
     * @param demandRecord The demand record to Peg
     * @param supplyRecord The supply record to Peg
     * @param pegQty The quantity to peg
     * @param userData Plain old user data
     * @return The newly created pegging record
     * @throws EMCEntityBeanException If Demand or Supply Requiremenst Planning Records were not found or if inserting of the Pegging record failed
     */
    public emc.entity.inventory.pegging.InventoryPeggingTable doPegging(emc.framework.EMCEntityClass demandRecord, emc.framework.EMCEntityClass supplyRecord, java.math.BigDecimal pegQty, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;
}
