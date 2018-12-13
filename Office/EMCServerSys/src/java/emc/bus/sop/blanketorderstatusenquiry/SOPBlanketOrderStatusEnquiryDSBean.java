/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.sop.blanketorderstatusenquiry;

import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.InventoryReferenceLocal;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.entity.sop.SOPSalesOrderLines;
import emc.entity.sop.datasource.blanketorderstatusenquiry.SOPBlanketOrderStatusEnquiryDS;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.transactions.InventoryTransactionDirection;
import emc.enums.inventory.transactions.InventoryTransactionStatus;
import emc.enums.inventory.transactions.InventoryTransactionsRefType;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class SOPBlanketOrderStatusEnquiryDSBean extends EMCDataSourceBean implements SOPBlanketOrderStatusEnquiryDSLocal {

    @EJB
    private InventoryReferenceLocal refBean;
    @EJB
    private InventoryItemMasterLocal itemBean;

    /** Creates a new instance of SOPBlanketOrderStatusEnquiryDS. */
    public SOPBlanketOrderStatusEnquiryDSBean() {
        this.setDataSourceClassName(SOPBlanketOrderStatusEnquiryDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        SOPBlanketOrderStatusEnquiryDS ds = (SOPBlanketOrderStatusEnquiryDS) dataSourceInstance;

        String itemId = ds.getItemId();
        String[] itemRef = refBean.checkItemReference(itemId, userData);
        if (itemRef != null) {
            ds.setItemReference(itemRef[1]);
            ds.setItemDescription(itemBean.findItemDescription(itemId, userData));
        } else {
            ds.setItemReference(itemId);
        }

        ds.setPackedQuantity(new BigDecimal(getPackedQuantity(ds, userData)));
        ds.setCalledOffQuantity(ds.getBlanketOrderQtyReleased());
        ds.setBalance(ds.getQuantity().subtract(ds.getCalledOffQuantity()));
        ds.setQuantityToPack(ds.getQuantity().subtract(ds.getPackedQuantity().add(ds.getCalledOffQuantity())));

        return ds;
    }

    /** Returns the number of items packed for the specified line.  (Completed on Assembly Orders) */
    private Double getPackedQuantity(SOPSalesOrderLines line, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
        query.addAnd("direction", InventoryTransactionDirection.OUT.toString());
        query.addAnd("status", InventoryTransactionStatus.RESERVED.toString());
        query.addAnd("refType", InventoryTransactionsRefType.Sales_Order.toString());
        query.addAnd("transId", line.getInventTransId());
        query.addAnd("refNumber", line.getSalesOrderNo());
        query.addFieldAggregateFunction("quantity", "SUM");

        Double total = (Double)util.executeSingleResultQuery(query, userData);

        return total == null ? 0 : total;
    }

}
