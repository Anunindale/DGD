/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.inventorystocktakeunreserved.datasource;

import emc.bus.inventory.inventorystocktakeunreserved.InventoryStocktakeUnreservedLocal;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.datasource.InventoryStocktakeUnreservedDS;
import emc.entity.inventory.dimensions.InventoryDimensionTable;
import emc.entity.inventory.journals.InventoryJournalMaster;
import emc.entity.sop.SOPCustomers;
import emc.entity.sop.SOPSalesOrderMaster;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.transactions.InventoryTransactionsRefType;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class InventoryStocktakeUnreservedDSBean extends EMCDataSourceBean implements InventoryStocktakeUnreservedDSLocal {

    @EJB
    InventoryStocktakeUnreservedLocal masterBean;

    public InventoryStocktakeUnreservedDSBean() {
        this.setDataSourceClassName(InventoryStocktakeUnreservedDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        InventoryStocktakeUnreservedDS ds = (InventoryStocktakeUnreservedDS) dataSourceInstance;

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class);
        query.addAnd("itemId", ds.getItemId());
        query.addField("itemReference");
        query.addField("description");
        Object[] itemFields = (Object[]) util.executeSingleResultQuery(query, userData);

        ds.setItemReference((String) (isBlank(itemFields[0]) ? ds.getItemId() : itemFields[0]));
        ds.setItemDescription((String) itemFields[1]);

        query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimensionTable.class);
        query.addAnd("recordID", ds.getDimensionId());
        InventoryDimensionTable dimensionRecord = (InventoryDimensionTable) util.executeSingleResultQuery(query, userData);

        ds.setDimension1(dimensionRecord.getDimension1Id());
        ds.setDimension2(dimensionRecord.getDimension2Id());
        ds.setDimension3(dimensionRecord.getDimension3Id());
        ds.setWarehouse(dimensionRecord.getWarehouseId());
        ds.setLocation(dimensionRecord.getLocationId());
        ds.setBatch(dimensionRecord.getBatchId());
        ds.setSerial(dimensionRecord.getItemSerialId());
        ds.setPallet(dimensionRecord.getPalletId());

        if (!isBlank(ds.getReferenceType()) && !isBlank(ds.getReferenceId())) {
            switch (InventoryTransactionsRefType.fromString(ds.getReferenceType())) {
                case Journal_Line:
                    query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalMaster.class);
                    query.addAnd("journalNumber", ds.getReferenceId());
                    query.addField("journalStatus");
                    query.addField("journalDefinitionId");
                    break;
                case Sales_Order:
                    query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesOrderMaster.class);
                    query.addTableAnd(SOPCustomers.class.getName(), "customerNo", SOPSalesOrderMaster.class.getName(), "customerId");
                    query.addAnd("salesOrderNo", ds.getReferenceId(), SOPSalesOrderMaster.class.getName());
                    query.addField("salsesOrderStatus", SOPSalesOrderMaster.class.getName());
                    query.addField("deliveryRules", SOPCustomers.class.getName());
                    break;
               
                default:
                    query = null;
            }

            if (query != null) {
                Object[] referenceRec = (Object[]) util.executeSingleResultQuery(query, userData);
                if (referenceRec != null && referenceRec.length > 0 && referenceRec[0] != null) {
                    ds.setReferenceRecStatus((String) referenceRec[0]);
                }
                if (referenceRec != null && referenceRec.length > 1 && referenceRec[1] != null) {
                    ds.setReferenceRecType((String) referenceRec[1]);
                }
            }
        }
        return ds;
    }

    @Override
    public Object entityDelete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        return convertSuperToDataSource(masterBean.insert(convertDataSourceToSuper(dobject, userData), userData), userData);
    }

    @Override
    public Object entityInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        return convertSuperToDataSource(masterBean.insert(convertDataSourceToSuper(iobject, userData), userData), userData);
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return convertSuperToDataSource(masterBean.update(convertDataSourceToSuper(uobject, userData), userData), userData);
    }
}
