/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.batchconsolidation.datasourse;

import emc.bus.inventory.batchconsolidation.InventoryBatchConsolidationLinesLocal;
import emc.entity.inventory.InventoryBatchConsolidationLines;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.datasource.InventoryBatchConsolidationLinesDS;
import emc.entity.inventory.journals.InventoryJournalLines;
import emc.enums.enumQueryTypes;
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
public class InventoryBatchConsolidationLinesDSBean extends EMCDataSourceBean implements InventoryBatchConsolidationLinesDSLocal {

    @EJB
    private InventoryBatchConsolidationLinesLocal masterBean;

    public InventoryBatchConsolidationLinesDSBean() {
        this.setDataSourceClassName(InventoryBatchConsolidationLinesDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        InventoryBatchConsolidationLinesDS ds = (InventoryBatchConsolidationLinesDS) dataSourceInstance;

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalLines.class);
        query.addTableAnd(InventoryItemMaster.class.getName(), "itemId", InventoryJournalLines.class.getName(), "itemId");
        query.addAnd("recordID", ds.getTransferJournalLineRecordId(), InventoryJournalLines.class.getName());
        query.addField("itemReference", InventoryItemMaster.class.getName());
        query.addField("description", InventoryItemMaster.class.getName());
        query.addField("dimension1", InventoryJournalLines.class.getName());
        query.addField("dimension2", InventoryJournalLines.class.getName());
        query.addField("dimension3", InventoryJournalLines.class.getName());
        query.addField("warehouse", InventoryJournalLines.class.getName());
        query.addField("location", InventoryJournalLines.class.getName());
        query.addField("toLocation", InventoryJournalLines.class.getName());
        query.addField("batch", InventoryJournalLines.class.getName());
        query.addField("toBatch", InventoryJournalLines.class.getName());
        query.addField("itemId", InventoryJournalLines.class.getName());
        query.addField("transId", InventoryJournalLines.class.getName());
        query.addField("serial", InventoryJournalLines.class.getName());
    
        Object[] dsData = (Object[]) util.executeSingleResultQuery(query, userData);

        if (dsData != null) {
            ds.setItemReference((String) dsData[0]);
            ds.setItemDescription((String) dsData[1]);
            ds.setDimension1((String) dsData[2]);
            ds.setDimension2((String) dsData[3]);
            ds.setDimension3((String) dsData[4]);
            ds.setWarehouse((String) dsData[5]);
            ds.setFromLocation((String) dsData[6]);
            ds.setToLocation((String) dsData[7]);
            ds.setFromBatch((String) dsData[8]);
            ds.setToBatch((String) dsData[9]);
            ds.setItemId((String) dsData[10]);
            ds.setTransId((String) dsData[11]);
            ds.setSerialNo((String) dsData[12]);
        }

        return ds;
    }

    @Override
    public Object entityDelete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        return masterBean.delete(convertDataSourceToSuper(dobject, userData), userData);
    }

    @Override
    public Object entityInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        return masterBean.insert(convertDataSourceToSuper(iobject, userData), userData);
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return masterBean.update(convertDataSourceToSuper(uobject, userData), userData);
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        Object valid = masterBean.validateField(fieldNameToValidate, convertDataSourceToSuper(theRecord, userData), userData);

        if (valid instanceof InventoryBatchConsolidationLines) {
            return convertSuperToDataSource(valid, userData);
        } else {
            return valid;
        }
    }
}
