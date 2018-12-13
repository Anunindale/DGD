/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.stocktakelogger;

import emc.bus.inventory.dimensions.InventoryDimensionTableLocal;
import emc.entity.inventory.InventoryStockTakeLogger;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author wikus
 */
@Stateless
public class InventoryStockTakeLoggerBean extends EMCEntityBean implements InventoryStockTakeLoggerLocal {

    @EJB
    private InventoryDimensionTableLocal dimensionBean;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object addStockTakeRecord(String journalNumber, String itemId, String dimension1, String dimension2, String dimension3, String warehouse, String location, String batch, String serial, String pallet, EMCUserData userData) throws EMCEntityBeanException {
        long diimensionId = dimensionBean.getDimRecordId(batch, dimension1, dimension2, dimension3, warehouse, location, pallet, serial, userData);

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryStockTakeLogger.class);
        query.addAnd("itemId", itemId);
        query.addAnd("dimensionId", diimensionId);
        query.addField("journalNumber");

        String existingJournal = (String) util.executeSingleResultQuery(query, userData);

        if (existingJournal == null) {
            InventoryStockTakeLogger logger = new InventoryStockTakeLogger();
            logger.setJournalNumber(journalNumber);
            logger.setItemId(itemId);
            logger.setDimensionId(diimensionId);
            logger.setStatus(0);
            insert(logger, userData);

            Object[] loggerQueries = new Object[2];

            query = new EMCQuery(enumQueryTypes.UPDATE, InventoryStockTakeLogger.class);
            query.addAnd("recordID", logger.getRecordID());
            query.addSet("status", 1);
            loggerQueries[0] = query.copyQuery();

            query = new EMCQuery(enumQueryTypes.DELETE, InventoryStockTakeLogger.class);
            query.addAnd("recordID", logger.getRecordID());
            loggerQueries[1] = query.copyQuery();

            return loggerQueries;
        } else {
            return existingJournal;
        }
    }

    @Override
    public void removeStockTakeRecord(String itemId, String dimension1, String dimension2, String dimension3, String warehouse, String location, String batch, String serial, String pallet, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryStockTakeLogger.class);
        query.addAnd("itemId", itemId);
        query.addAnd("dimensionId", dimensionBean.getDimRecordId(batch, dimension1, dimension2, dimension3, warehouse, location, pallet, serial, userData));
        InventoryStockTakeLogger logger = (InventoryStockTakeLogger) util.executeSingleResultQuery(query, userData);
        if (logger != null) {
            delete(logger, userData);
        }
    }

    @Override
    public String checkStockTakeRecord(String itemId, long dimensionId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryStockTakeLogger.class);
        query.addAnd("itemId", itemId);
        query.addAnd("dimensionId", dimensionId);
        query.addField("journalNumber");
        String existingJournal = (String) util.executeSingleResultQuery(query, userData);
        return existingJournal;
    }
}
