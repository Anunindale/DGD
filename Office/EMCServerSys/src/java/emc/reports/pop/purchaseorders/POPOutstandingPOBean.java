/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.pop.purchaseorders;

import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.inventory.dimensions.InventoryDimensionTableLocal;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.pop.POPPurchaseOrderLines;
import emc.entity.pop.POPPurchaseOrderMaster;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
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
 * @author riaan
 */
@Stateless
public class POPOutstandingPOBean extends EMCReportBean implements POPOutstandingPOLocal {

    @EJB
    private InventoryReferenceLocal refBean;
    @EJB
    private InventoryDimensionTableLocal dimensionBean;

    /**
     * Creates a new instance of POPOutstandingPOBean.
     */
    public POPOutstandingPOBean() {
    }

    @Override
    public List<Object> manipulateQueryResult(List<Object> purchaseOrders, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        //Save classname to prevent unnecesary overhead
        String linesClassName = POPPurchaseOrderLines.class.getName();

        List<Object> ret = new ArrayList<Object>();

        List<POPPurchaseOrderLines> purchaseOrderLines = null;
        POPPurchaseOrderMaster purchaseOrderMaster = null;
        InventoryItemMaster item = null;

        EMCQuery query = null;

        POPOutstandingPODS ds = null;
        double total = 0;

        boolean noDemand = (Boolean) paramMap.get("noDemand");

        for (Object ob : purchaseOrders) {
            purchaseOrderMaster = (POPPurchaseOrderMaster) ob;

            query = new EMCQuery(enumQueryTypes.SELECT, linesClassName);
            query.addAnd("purchaseOrderId", purchaseOrderMaster.getPurchaseOrderId());
            query.addAnd("itemsReceived", EMCQueryConditions.LESS_THAN, "quantity");

            purchaseOrderLines = (List<POPPurchaseOrderLines>) util.executeGeneralSelectQuery(query, userData);

            for (POPPurchaseOrderLines purchaseOrderLine : purchaseOrderLines) {

                query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class.getName());
                query.addAnd("itemId", purchaseOrderLine.getItemId());

                item = (InventoryItemMaster) util.executeSingleResultQuery(query, userData);

                ds = new POPOutstandingPODS();

                //Master fields
                ds.setSupplier(purchaseOrderMaster.getSupplier());
                ds.setPurchaseOrder(purchaseOrderMaster.getPurchaseOrderId());
                ds.setStatus(purchaseOrderMaster.getStatus());

                //Lines fields
                String itemRef = refBean.checkItemReference(item.getItemId(), userData)[1];

                ds.setItemId(itemRef);

                ds.setDeliveryDate(isBlank(purchaseOrderLine.getDeliveryDate()) ? "" : Functions.date2String(purchaseOrderLine.getDeliveryDate()));
                ds.setItemDescription(item.getDescription());
                ds.setDimension1(purchaseOrderLine.getItemDimension1());
                ds.setDimension2(purchaseOrderLine.getItemDimension2());
                ds.setDimension3(purchaseOrderLine.getItemDimension3());
                ds.setUom(purchaseOrderLine.getUom());
                ds.setQtyOrdered(purchaseOrderLine.getQuantity());
                ds.setQtyOutstanding(purchaseOrderLine.getQuantity() - purchaseOrderLine.getItemsReceived());
                total += ds.getQtyOutstanding();
                ds.setOsTotal(total);
                ds.setItemPrice(purchaseOrderLine.getItemPrice());

                //doValueOutstanding(ds);

                ds.setValueOutstanding(ds.getQtyOutstanding() * ds.getItemPrice());

                ds.setNoDemand(noDemand);
                ret.add(ds);
            }
        }

        return ret;
    }

    @Override
    public List<Object> getReportResult(List<Object> parameters, EMCUserData userData) {
        List<Object> results = executeQuery(parameters.get(1).toString(), userData);

        return manipulateQueryResult(results, null, userData);
    }
}
