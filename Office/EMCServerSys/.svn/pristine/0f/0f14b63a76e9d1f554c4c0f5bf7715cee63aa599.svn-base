/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.inventory.pickinglistsummary.summary;

import emc.bus.inventory.InventoryItemMasterLocal;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.reports.inventory.pickinglistsummary.PickingListSummaryReportDS;
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
public class PickingListSummaryReportSummaryBean extends EMCReportBean implements PickingListSummaryReportSummaryLocal {

    @EJB
    private InventoryItemMasterLocal itemBean;

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        Map<String, Double> itemCostMap = new HashMap<String, Double>();
        Double itemCost;
        PickingListSummaryReportDS ds;
        Object[] data;
        List retList = new ArrayList();
        for (Object o : queryResult) {
            data = (Object[]) o;
            ds = new PickingListSummaryReportDS();
            ds.setPickListId((String) data[0]);
            ds.setItemId((String) (isBlank(data[2]) ? data[1] : data[2]));
            ds.setItemDesc((String) data[3]);
            ds.setDimension1((String) data[4]);
            ds.setDimension2((String) data[5]);
            ds.setDimension3((String) data[6]);
            ds.setIssuedQty((Double) data[7]);
            ds.setRequiredQty((Double) data[8]);
            itemCost = itemCostMap.get((String)data[1] + (String) data[4] + (String) data[5] + (String) data[6]);
            if (itemCost == null) {
                itemCost = itemBean.getCostPrice( (String)data[1], (String) data[4], (String) data[5], (String) data[6], userData);
                itemCostMap.put((String)data[1] + (String) data[4] + (String) data[5] + (String) data[6], itemCost);
            }
            ds.setIsseuedCost(itemCost * ds.getIssuedQty());
            retList.add(ds);
        }
        return retList;
    }
}
