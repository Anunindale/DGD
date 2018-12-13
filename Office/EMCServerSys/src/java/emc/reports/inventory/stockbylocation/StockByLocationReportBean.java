/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.inventory.stockbylocation;

import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.dimensions.InventoryDimension3Local;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @Name         : StockByLocationReportBean
 *
 * @Date         : 06 Aug 2009
 * 
 * @Description  : Bean for the Stock By Location report.
 *
 * @author       : riaan
 */
@Stateless
public class StockByLocationReportBean extends EMCReportBean implements StockByLocationReportLocal {

    @EJB
    private InventoryDimension3Local dim3Bean;
    @EJB
    private InventoryItemMasterLocal itemMasterBean;

    /** Creates a new instance of StockByLocationReportBean. */
    public StockByLocationReportBean() {
    }

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        List<Object> ret = new ArrayList<Object>();

        Map<String, String> itemRefMap = new HashMap<String, String>();
        Map<String, String> itemDimension2GroupMap = new HashMap<String, String>();
        Map<String, String> dimension3DescMap = new HashMap<String, String>();
        //Keeps dimension group + dimension 2 as a key.
        Map<String, Integer> dimension2SortCodesMap = new HashMap<String, Integer>();

        //This List is used to sort items before adding them to the list that will be returned.
        List temp = new ArrayList();
        StockByLocationReportDSSortCodeComparator comparator = new StockByLocationReportDSSortCodeComparator();
        //Used to determine when the temporary list should be sorted and its item sent to the ret list.
        String key = null;
        String tempKey = null;

        for (Object ob : queryResult) {
            Object[] onHandInfo = (Object[]) ob;

            String itemId = (String) onHandInfo[2];

            String itemRef = itemRefMap.get(itemId);

            if (isBlank(itemRef)) {
                InventoryItemMaster item = itemMasterBean.findItem(itemId, userData);

                itemRef = item.getItemReference();

                itemRefMap.put(itemId, itemRef);

                itemDimension2GroupMap.put(itemId, item.getDimension2Group());
            }

            if (itemRef != null) {
                String dimension3 = (String) onHandInfo[3];
                String dimension3Desc = dimension3DescMap.get(dimension3);

                if (isBlank(dimension3Desc)) {
                    dimension3Desc = dim3Bean.findDimensionDescription(dimension3, userData);

                    dimension3DescMap.put(dimension3, dimension3Desc);
                }

                StockByLocationReportDS ds = new StockByLocationReportDS();

                ds.setItemRef(itemRef);
                ds.setDimension3(dimension3);
                ds.setDimension3Description(dimension3Desc);

                ds.setWarehouse((String) onHandInfo[0]);
                ds.setLocation((String) onHandInfo[1]);
                ds.setDimension2((String) onHandInfo[4]);
                ds.setBatch((String) onHandInfo[5]);
                ds.setSerial((String) onHandInfo[6]);
                ds.setQuantity(onHandInfo[7] == null ? 0 : (Double) onHandInfo[7]);

                tempKey = ds.getItemRef() + ds.getLocation() + ds.getBatch() + ds.getDimension1() + ds.getDimension3() + ds.getWarehouse() + ds.getSerial();

                //Ensure that first record is added correctly
                if (key == null) {
                    key = tempKey;
                } else if (!key.equals(tempKey)) {
                    //This is already done here to prevent the first record in a new group being added to the previous group.
                    flushTempList(temp, ret, comparator);
                    key = tempKey;
                }

                temp.add(ds);

                String dimension2Group = itemDimension2GroupMap.get(itemId);

                if (!isBlank(dimension2Group)) {
                    Integer dimension2SortCode = dimension2SortCodesMap.get(dimension2Group + ds.getDimension2());

                    if (dimension2SortCode == null) {
                        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension2.class.getName());
                        query.addAnd("dimensionId", ds.getDimension2());
                        query.addField("sortCode");

                        dimension2SortCode = (Integer) util.executeSingleResultQuery(query, userData);

                        if (dimension2SortCode == null) {
                            dimension2SortCode = 0;
                        }

                        dimension2SortCodesMap.put(dimension2Group + ds.getDimension2(), dimension2SortCode);
                    }

                    ds.setDim2SortCode(dimension2SortCode);
                }
            }
        }

        //Adds last group in temp list before returning 
        flushTempList(temp, ret, comparator);

        return ret;
    }

    /** This method sorts "tempList" using the given comparator, adds it's records to "retList" and clears "tempList". */
    private void flushTempList(List temp, List ret, Comparator comparator) {
        Collections.sort(temp, comparator);

        ret.addAll(temp);
        temp.clear();
    }

    /** This comparator is used to sort records by dimension 2 sort code. */
    private class StockByLocationReportDSSortCodeComparator implements Comparator<StockByLocationReportDS> {

        public int compare(StockByLocationReportDS ds, StockByLocationReportDS other) {
            return ds.getDim2SortCode() > other.getDim2SortCode() ? 1 : ds.getDim2SortCode() < other.getDim2SortCode() ? -1 : 0;
        }
    }
}
