/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.pop.journals.datasource;

import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryReference;
import emc.entity.inventory.dimensions.InventoryDimensionTable;
import emc.entity.pop.journals.datasource.POPSupplierReceivedJournalLinesDS;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.inventorytables.InventoryReferenceTypes;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.server.utility.EMCServerUtilityLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class POPSupplierReceivedJournalLinesBeanDS extends EMCDataSourceBean implements POPSupplierReceivedJournalLinesLocalDS {

    @EJB
    private EMCServerUtilityLocal util;

    public POPSupplierReceivedJournalLinesBeanDS() {
        this.setDataSourceClassName(POPSupplierReceivedJournalLinesDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        POPSupplierReceivedJournalLinesDS ds = (POPSupplierReceivedJournalLinesDS) dataSourceInstance;

        InventoryDimensionTable dimension = (InventoryDimensionTable) util.findPersisted(InventoryDimensionTable.class, ds.getDimId(), userData);
        ds.setDimension1(dimension.getDimension1Id());
        ds.setDimension2(dimension.getDimension2Id());
        ds.setDimension3(dimension.getDimension3Id());
        
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryReference.class.getName());
        query.addAnd("companyId", userData.getCompanyId());
        query.addAnd("itemId", ds.getItemId());
        query.addAnd("refType", InventoryReferenceTypes.PRIMARY.toString());
        InventoryReference ref = (InventoryReference)util.executeSingleResultQuery(query, userData);
        if(ref == null){
            query = new EMCQuery(enumQueryTypes.SELECT, InventoryReference.class.getName());
            query.addAnd("companyId", userData.getCompanyId());
            query.addAnd("itemId", ds.getItemId());
            query.addAnd("refType", InventoryReferenceTypes.DEFAULT.toString());
            ref = (InventoryReference)util.executeSingleResultQuery(query, userData);
        }
        if(ref !=null)
        ds.setItemPrimaryReference(ref.getReference());
        
        query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class.getName());
        query.addField("description");
        query.addAnd("itemId", ds.getItemId());
        Object o = util.executeSingleResultQuery(query, userData);
        if (o != null) {
            ds.setItemDescription(o.toString());
        }

        return ds;
    }
}
