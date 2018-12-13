/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.inventory.journals;

import emc.bus.inventory.InventoryReferenceLocal;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.journals.InventoryJournalLines;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.inventorytables.InventoryReferenceTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class InventoryMovementJournalSummaryBean extends EMCReportBean implements InventoryMovementJournalSummaryLocal {

    @EJB
    private InventoryReferenceLocal referenceBean;

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        InventoryMovementJournalSummaryDS ds;
        String key;
        Map<String, InventoryMovementJournalSummaryDS> groupMap = new TreeMap<String, InventoryMovementJournalSummaryDS>();
        InventoryJournalLines line;
        List<String> itemInfo;
        for (Object o : queryResult) {
            line = (InventoryJournalLines) o;
            itemInfo = referenceBean.findReferenceAndDesc(line.getItemId(), InventoryReferenceTypes.PRIMARY, userData);
            if (itemInfo == null || itemInfo.size() < 2) {
                continue;
            }
            key = itemInfo.get(0) + line.getDimension3() + getSizeSortCode(line.getDimension2(), userData);
            if (groupMap.containsKey(key)) {
                ds = groupMap.get(key);
            } else {
                ds = new InventoryMovementJournalSummaryDS();
            }
            ds.setItem(itemInfo.get(0));
            ds.setDescription(itemInfo.get(1));
            ds.setDim1(line.getDimension1());
            ds.setDim2(line.getDimension2());
            ds.setDim3(line.getDimension3());
            ds.setQuantity(ds.getQuantity() + line.getQuantity());
            groupMap.put(key, ds);
        }
        return new ArrayList<Object>(groupMap.values());
    }

    private String getSizeSortCode(String size, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension2.class.getName());
        query.addAnd("dimensionId", size);
        query.addField("sortCode");
        Integer sortCode = (Integer) util.executeSingleResultQuery(query, userData);
        if (sortCode == null) {
            return "-0";
        } else return "-" + sortCode;
    }
}
