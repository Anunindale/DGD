/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.developertools.poimport;

import emc.bus.pop.POPPurchaseOrderMasterLocal;
import emc.entity.developertools.poimport.DevNLPOImportMaster;
import emc.entity.inventory.InventoryWarehouse;
import emc.enums.enumQueryTypes;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @Name         : DevNLPOImportMasterBean
 *
 * @Date         : 13 Jul 2009
 * 
 * @Description  : Entity bean used to import old N & L PO Master recordss.
 *
 * @author       : riaan
 */
@Stateless
public class DevNLPOImportMasterBean extends EMCDataSourceBean implements DevNLPOImportMasterLocal {

    @EJB
    private POPPurchaseOrderMasterLocal poMasterBean;

    /** Creates a new instance of DevNLPOImportMasterBean. */
    public DevNLPOImportMasterBean() {
        this.setDataSourceClassName(DevNLPOImportMaster.class.getName());
    }

    @Override
    public Object entityInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        DevNLPOImportMaster master = (DevNLPOImportMaster)iobject;
        
        //Help us identify imported lines.
        master.setPostVersion(-10);
        
        //If Master has already been imported, return.  Better than showing a bunch of useless exceptions.
        if (poMasterBean.findPurchaseOrder(master.getPurchaseOrderId(), userData) != null) {
            return master;
        }
        
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryWarehouse.class.getName());
        query.addAnd("warehouseId", master.getWarehouse());
                
        InventoryWarehouse warehouse = (InventoryWarehouse)util.executeSingleResultQuery(query, userData);

        if (warehouse != null) {
            master.setAddressLine1(warehouse.getAddressLine1());
            master.setAddressLine2(warehouse.getAddressLine2());
            master.setAddressLine3(warehouse.getAddressLine3());
            master.setAddressLine4(warehouse.getAddressLine4());
            master.setAddressLine5(warehouse.getAddressLine5());
            master.setAddressPostalCode(warehouse.getPostalCode());
        }
        
        return poMasterBean.insert(convertDataSourceToSuper(iobject, userData), userData);
    }

    @Override
    public Object entityDelete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        return poMasterBean.delete(convertDataSourceToSuper(dobject, userData), userData);
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return poMasterBean.update(convertDataSourceToSuper(uobject, userData), userData);
    }
}
