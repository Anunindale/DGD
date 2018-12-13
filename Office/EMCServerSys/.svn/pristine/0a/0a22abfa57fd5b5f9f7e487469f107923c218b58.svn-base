/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.pop.grnreprint;

import emc.bus.inventory.logic.InventoryItemDimensionCombinationLogicLocal;
import emc.entity.pop.grnreprint.POPGRNReprintTemp;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCUserData;
import emc.tables.EMCTable;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class POPGRNReprintTempBean extends EMCEntityBean implements POPGRNReprintTempLocal {

    @EJB
    private InventoryItemDimensionCombinationLogicLocal dimLogicBean;

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean ret = super.doInsertValidation(vobject, userData);
        if (ret) {
            POPGRNReprintTemp record = (POPGRNReprintTemp) vobject;
            ret = dimLogicBean.validateActiveDimensions(record.getItemId(), record.getDimension1(), record.getDimension2(), record.getDimension3(), null, false, userData);
        }
        return ret;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doUpdateValidation(vobject, userData);
        if (ret) {
            POPGRNReprintTemp record = (POPGRNReprintTemp) vobject;
            ret = dimLogicBean.validateActiveDimensions(record.getItemId(), record.getDimension1(), record.getDimension2(), record.getDimension3(), null, false, userData);
        }
        return ret;
    }
}
