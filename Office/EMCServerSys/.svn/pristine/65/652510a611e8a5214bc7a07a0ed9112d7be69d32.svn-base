/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.pop.planning;

import emc.entity.pop.planning.POPPlannedPurchaseOrders;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import emc.tables.EMCTable;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface POPPlannedPurchaseOrdersLocal extends EMCEntityBeanLocalInterface {

    public boolean firmOrder(java.lang.String plannedPOId, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public String releasePlannedPO(java.util.Date requiredDate, java.lang.String supplier, java.lang.String warehouse, java.util.Map<java.lang.Long, java.lang.Long> versionMap, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public void insertFromMPS(List<POPPlannedPurchaseOrders> ppoList, EMCUserData userData) throws EMCEntityBeanException;

    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData);

    public Object[] findDefaultItemValues(String itemId, EMCUserData userData);
}
