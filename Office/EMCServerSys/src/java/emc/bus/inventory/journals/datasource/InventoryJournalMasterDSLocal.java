/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.inventory.journals.datasource;

import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface InventoryJournalMasterDSLocal extends EMCEntityBeanLocalInterface {

    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData);
            
}
