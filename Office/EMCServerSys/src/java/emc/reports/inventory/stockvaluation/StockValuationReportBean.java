/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.inventory.stockvaluation;

import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.logic.InventoryItemDimensionCombinationLogicLocal;
import emc.bus.inventory.transactions.datasource.InventorySummaryDSLocal;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryWarehouse;
import emc.entity.inventory.transactions.InventorySummary;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.inventorytables.InventoryLocationsEnum;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @Name         : StockValuationReportBean
 *
 * @Date         : 03 Aug 2009
 * 
 * @Description  : Bean for the Stock Valuation report.
 *
 * @author       : riaan
 */
@Stateless
public class StockValuationReportBean extends EMCReportBean implements StockValuationReportLocal {

    @EJB
    private InventorySummaryDSLocal summaryDSBean;
    @EJB
    private InventoryItemMasterLocal itemMasterBean;
    @EJB
    private InventoryItemDimensionCombinationLogicLocal dimCombinationLogicBean;

    /** Creates a new instance of StockValuationReportBean. */
    public StockValuationReportBean() {

    }

    @Override
    public List<Object> getReportResult(List<Object> parameters, EMCUserData userData) {
        EMCQuery query = (EMCQuery) parameters.get(1);
        
        userData.setUserData(12, true);
        userData.setUserData(13, true);
        
        query = summaryDSBean.setQuery(query, userData);
        
        parameters.set(1, query);

        return super.getReportResult(parameters, userData);
    }

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        List<Object> ret = new ArrayList<Object>();

        Object[] res = null;

        //Attempt to optimize code by storing selected items and colours instead of reselecting them every time that they are needed.
        Map<String, InventoryItemMaster> itemMap = new HashMap<String, InventoryItemMaster>();
        
        for (Object ob : queryResult) {
            res = (Object[]) ob;

            InventoryItemMaster item = null;

            StockValuationReportDS ds = new StockValuationReportDS();

            //Only use for item and dimensions.  Due to grouping and aggregate functions, quantities in this record may be invalid.
            InventorySummary summary = (InventorySummary) res[0];


            Double physical = res[1] == null ? 0 : (Double) res[1];
            Double rec = res[11] == null ? 0 : (Double) res[11];
            Double qc = res[12] == null ? 0 : (Double) res[12];
            Double totalQty = physical + rec + qc;
            if (util.compareDouble(totalQty, 0) <= 0) {
                continue;
            }

            item = itemMap.get(summary.getItemId());
            
            if (item == null) {
                item = itemMasterBean.findItem(summary.getItemId(), userData);
                
                if (item != null) {
                    itemMap.put(summary.getItemId(), item);
                }
            }
            
            if (item != null) {
                ds.setItemRef(item.getItemReference());
                ds.setItemDescription(item.getDescription());
                ds.setItemGroup(item.getItemGroup());
                ds.setUom(item.getBaseUOM());
                
                ds.setDimension1(summary.getDimension1());
                ds.setDimension2(summary.getDimension2());
                ds.setDimension3(summary.getDimension3());
                
                ds.setQuantityOnHand(totalQty);
                
                double price = dimCombinationLogicBean.getCostPriceForCombination(summary.getItemId(), summary.getDimension1(), summary.getDimension2(), summary.getDimension3(), userData);
                
                if (price == 0) {
                    ds.setItemCost(item.getPurchasePrice());
                } else {
                    ds.setItemCost(price);
                }
                
                ds.setValue(ds.getItemCost() * ds.getQuantityOnHand());
                
                ret.add(ds);
            }
        }

        return ret;
    }

    private boolean isAvailableInWarehouse(String warehouse, String location, EMCUserData userData) {
        if (location == null) return true;
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryWarehouse.class.getName());
        query.addAnd("warehouseId", warehouse);
        if (location.equals(InventoryLocationsEnum.RECEIVING_AREA.toString())) {
            query.addField("RECAvailable");
        } else if (location.equals(InventoryLocationsEnum.QUALITY_CHECK.toString())) {
            query.addField("QCAvailable");
        } else {
            return true;
        }
        Object o = util.executeSingleResultQuery(query, userData);
        return o == null ? false : (Boolean) o;
    }
}
