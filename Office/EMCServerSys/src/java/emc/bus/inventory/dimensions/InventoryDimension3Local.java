/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.dimensions;

import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface InventoryDimension3Local extends EMCEntityBeanLocalInterface {

    public String findDimensionDescription(String dimensionId, EMCUserData userData);

    public void updateSortCodes(EMCUserData userData) throws EMCEntityBeanException;

    public InventoryDimension3 getDimension3Record(String dimensionId, EMCUserData userData);
}
