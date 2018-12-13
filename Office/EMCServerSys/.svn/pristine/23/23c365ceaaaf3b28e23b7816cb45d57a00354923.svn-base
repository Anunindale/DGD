/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.sop.controlsheet;

import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.dimensions.InventoryDimension1Local;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.picking.InventoryPickingListLines;
import emc.entity.inventory.picking.InventoryPickingListMaster;
import emc.entity.sop.SOPSalesOrderMaster;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class SOPControlSheetReportBean extends EMCReportBean implements SOPControlSheetReportLocal {

    @EJB
    private InventoryItemMasterLocal itemMasterBean;
    @EJB
    private InventoryDimension1Local dimension1Bean;

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        SOPSalesOrderMaster salesOrderMaster;
        SOPControlSheetReportDS ds;
        List<InventoryPickingListLines> pickingList;
        Map<String, Integer> duplicationMap = new HashMap<String, Integer>();
        String duplicationMapKey;
        Integer duplicationValue;
        Map<String, InventoryItemMaster> itemMap = new HashMap<String, InventoryItemMaster>();
        InventoryItemMaster item;
        Map<String, InventoryDimension1> configMap = new HashMap<String, InventoryDimension1>();
        InventoryDimension1 config;
        List reportDataList = new ArrayList();
        for (Object o : queryResult) {
            salesOrderMaster = (SOPSalesOrderMaster) o;

            pickingList = findPickingList(salesOrderMaster.getSalesOrderNo(), userData);

            for (InventoryPickingListLines pick : pickingList) {
                duplicationMapKey = pick.getItemId() + pick.getDimension1() + pick.getDimension2() + pick.getDimension3();
                duplicationValue = duplicationMap.get(duplicationMapKey);
                if (duplicationValue == null) {
                    ds = createDataSource(salesOrderMaster);
                    ds.setPickingList(pick.getPickingListId());
                    item = itemMap.get(pick.getItemId());
                    if (item == null) {
                        item = itemMasterBean.findItem(pick.getItemId(), userData);
                        itemMap.put(pick.getItemId(), item);
                    }
                    ds.setItemReference(item.getItemReference());
                    ds.setItemDescription(item.getDescription());
                    ds.setDimension1(pick.getDimension1());
                    if (!isBlank(pick.getDimension1())) {
                        config = configMap.get(pick.getDimension1());
                        if (config == null) {
                            config = dimension1Bean.getDimension1(pick.getDimension1(), userData);
                            configMap.put(pick.getDimension1(), config);
                        }
                        ds.setDimension1Description(config.getDescription());
                    }
                    ds.setDimension2(pick.getDimension2());
                    ds.setDimension3(pick.getDimension3());
                    ds.setQuantityToPick(new BigDecimal(pick.getOrderQty() - pick.getIssueQty()));
                    reportDataList.add(ds);
                    duplicationMap.put(duplicationMapKey, reportDataList.size() - 1);
                } else {
                    ds = (SOPControlSheetReportDS) reportDataList.get(duplicationValue);
                    ds.setPickingList(pick.getPickingListId());
                    ds.setQuantityToPick(ds.getQuantityToPick().add(new BigDecimal(pick.getOrderQty() - pick.getIssueQty())));
                    reportDataList.set(duplicationValue, ds);
                }
            }

          
        }
        return reportDataList;
    }

    private List<InventoryPickingListLines> findPickingList(String salesOrderNo, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryPickingListLines.class);
        query.addTableAnd(InventoryPickingListMaster.class.getName(), "pickingListId", InventoryPickingListLines.class.getName(), "pickingListId");
        query.addAnd("orderId", salesOrderNo, InventoryPickingListMaster.class.getName());
        return util.executeQuerySortedByDimensions(query, InventoryPickingListLines.class, InventoryPickingListLines.class, "dimension1", InventoryPickingListLines.class, "dimension2",
                InventoryPickingListLines.class, "dimension3", true, null, userData);
    }

   

    private SOPControlSheetReportDS createDataSource(SOPSalesOrderMaster salesOrderMaster) {
        SOPControlSheetReportDS ds = new SOPControlSheetReportDS();
        ds.setSalesOrder(salesOrderMaster.getSalesOrderNo());
        ds.setRequiredDate(Functions.date2String(salesOrderMaster.getRequiredDate()));
        ds.setPrintedDate(Functions.date2String(Functions.nowDate()));
        ds.setInvoiceAddress1(salesOrderMaster.getInvoiceAddress1());
        ds.setInvoiceAddress2(salesOrderMaster.getInvoiceAddress2());
        ds.setInvoiceAddress3(salesOrderMaster.getInvoiceAddress3());
        ds.setInvoiceAddress4(salesOrderMaster.getInvoiceAddress4());
        ds.setInvoiceAddress5(salesOrderMaster.getInvoiceAddress5());
        ds.setInvoiceAddressCode(salesOrderMaster.getInvoiceAddressCode());
        ds.setDeliveryAddress1(salesOrderMaster.getDeliveryAddress1());
        ds.setDeliveryAddress2(salesOrderMaster.getDeliveryAddress2());
        ds.setDeliveryAddress3(salesOrderMaster.getDeliveryAddress3());
        ds.setDeliveryAddress4(salesOrderMaster.getDeliveryAddress4());
        ds.setDeliveryAddress5(salesOrderMaster.getDeliveryAddress5());
        ds.setDeliveryAddressCode(salesOrderMaster.getDeliveryAddressCode());
        return ds;
    }

    @Override
    protected EMCQuery manipulateQuery(EMCQuery query, EMCUserData userData) {
        query.setSelectDistinctAll(true);
        return query;
    }
}
