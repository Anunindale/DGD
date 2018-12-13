/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.pop.posting.datasource;

import emc.bus.pop.posting.POPPurchasePostLinesLocal;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryReference;
import emc.entity.inventory.dimensions.InventoryDimensionTable;
import emc.entity.pop.posting.datasource.POPPurchasePostLinesDS;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.inventorytables.InventoryReferenceTypes;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.server.utility.EMCServerUtilityLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class POPPurchasePostLinesDSBean extends EMCDataSourceBean implements POPPurchasePostLinesDSLocal {

    @EJB
    private POPPurchasePostLinesLocal postLinesBean;

    /** Creates a new instance of POPPurchasePostLinesDSBean. */
    public POPPurchasePostLinesDSBean() {
        this.setDataSourceClassName(POPPurchasePostLinesDS.class.getName());
    }

    @Override
    public Object entityInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        return postLinesBean.insert(convertDataSourceToSuper(iobject, userData), userData);
    }

    @Override
    public Object entityDelete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        return postLinesBean.delete(convertDataSourceToSuper(dobject, userData), userData);
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return postLinesBean.update(convertDataSourceToSuper(uobject, userData), userData);
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        POPPurchasePostLinesDS ds = (POPPurchasePostLinesDS) dataSourceInstance;

        InventoryDimensionTable dimensionTable = (InventoryDimensionTable) util.findPersisted(InventoryDimensionTable.class, ds.getDimId(), userData);
        if (dimensionTable != null) {
            ds.setDimension1Id(dimensionTable.getDimension1Id());
            ds.setDimension2Id(dimensionTable.getDimension2Id());
            ds.setDimension3Id(dimensionTable.getDimension3Id());
            ds.setWarehouse(dimensionTable.getWarehouseId());
        }

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryReference.class.getName());
        query.addAnd("companyId", userData.getCompanyId());
        query.addAnd("itemId", ds.getItemId());
        query.addAnd("refType", InventoryReferenceTypes.PRIMARY.toString());
        InventoryReference ref = (InventoryReference) util.executeSingleResultQuery(query, userData);
        if (ref == null) {
            query = new EMCQuery(enumQueryTypes.SELECT, InventoryReference.class.getName());
            query.addAnd("companyId", userData.getCompanyId());
            query.addAnd("itemId", ds.getItemId());
            query.addAnd("refType", InventoryReferenceTypes.DEFAULT.toString());
            ref = (InventoryReference) util.executeSingleResultQuery(query, userData);
        }
        if (ref != null) {
            ds.setItemPrimaryReference(ref.getReference());
        }

        query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class.getName());
        query.addAnd("itemId", ds.getItemId());
        query.addAnd("companyId", userData.getCompanyId());
        InventoryItemMaster master = (InventoryItemMaster) util.executeSingleResultQuery(query, userData);
        ds.setItemDesc(master.getDescription());

        return ds;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        return postLinesBean.validateField(fieldNameToValidate, convertDataSourceToSuper(theRecord, userData), userData);
    }
}
