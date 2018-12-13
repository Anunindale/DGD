/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.pop.purchaseorders;

import emc.bus.inventory.InventoryReferenceLocal;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryDimensionTable;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.transactions.InventoryTransactionDirection;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class POPGoodsReturnedReportBean extends EMCReportBean implements POPGoodsReturnedReportLocal {

    @EJB
    private InventoryReferenceLocal refBean;

    @Override
    public List<Object> getReportResult(List<Object> parameters, EMCUserData userData) {
        return manipulateQueryResult(util.executeGeneralSelectQuery(parameters.get(1).toString(), userData), null, userData);
    }

    @Override
    public List<Object> manipulateQueryResult(List<Object> transactions, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {

        List<Object> ret = new ArrayList<Object>();

        InventoryTransactions transaction;
        InventoryItemMaster item = null;
        InventoryDimensionTable dimensions = null;

        EMCQuery query = null;

        POPGoodsReceivedDS ds = null;

        for (Object ob : transactions) {
            transaction = (InventoryTransactions) ob;
            if (isBlank(transaction.getDocumentNo()) ||
                    InventoryTransactionDirection.IN.toString().equals(transaction.getDirection())) {
                continue;
            }

            ds = new POPGoodsReceivedDS();

            query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class.getName());
            query.addAnd("itemId", transaction.getItemId());

            item = (InventoryItemMaster) util.executeSingleResultQuery(query, userData);

            query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimensionTable.class.getName());
            query.addAnd("recordID", transaction.getItemDimId());

            dimensions = (InventoryDimensionTable) util.executeSingleResultQuery(query, userData);

            ds.setSupplier(transaction.getSupplierId());
            ds.setPurchaseOrder(transaction.getRefNumber());

            ds.setDeliveryDate(transaction.getPhysicalDate() == null ? "" : Functions.date2String(transaction.getPhysicalDate()));

            ds.setUom(item.getBaseUOM());

            String itemRef = refBean.checkItemReference(item.getItemId(), userData)[1];

            ds.setItemId(itemRef);
            ds.setItemDescription(item.getDescription());
            ds.setDimension1(dimensions.getDimension1Id());
            ds.setDimension2(dimensions.getDimension2Id());
            ds.setDimension3(dimensions.getDimension3Id());

            ds.setItemPrice(transaction.getCost() / transaction.getQuantity());

            ds.setDeliveryNote(transaction.getDocumentNo());
            ds.setQtyReceived(transaction.getQuantity() * -1);

            ds.setValueReceived(ds.getQtyReceived() * ds.getItemPrice());

            ret.add(ds);
        }

        return ret;
    }
}
