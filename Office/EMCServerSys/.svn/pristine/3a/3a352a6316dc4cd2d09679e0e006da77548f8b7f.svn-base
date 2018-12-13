/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.inventory.belowminimum;

import emc.bus.inventory.InventoryItemMasterLocal;
import emc.constants.systemConstants;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryDimensionTable;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.entity.inventory.transactions.InventorySummary;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.emcquery.EMCQueryOrderByDirections;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.inventorytables.InventoryStatus;
import emc.enums.inventory.purchasing.InventoryStopPurchasingType;
import emc.enums.inventory.transactions.InventoryTransactionDirection;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
public class InventoryBelowMinimumreportBean extends EMCReportBean implements InventoryBelowMinimumReportLocal {

    @EJB
    private InventoryItemMasterLocal itemMasterBean;

    @Override
    public List<Object> getReportResult(List<Object> parameters, EMCUserData userData) {
        EMCQuery combinationQuery = ((EMCQuery) parameters.get(1)).copyQuery();
        combinationQuery.addField("itemId", InventoryItemDimensionCombinations.class.getName());

        EMCQuery summaryQuery = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class.getName());
        summaryQuery.addAnd("itemId", combinationQuery, EMCQueryConditions.IN);
        summaryQuery.addAnd("companyId", userData.getCompanyId());
        summaryQuery.addGroupBy("itemId");
        summaryQuery.addGroupBy("dimension1");
        summaryQuery.addGroupBy("dimension2");
        summaryQuery.addGroupBy("dimension3");

        summaryQuery.addField("itemId");        //0
        summaryQuery.addField("dimension1");    //1
        summaryQuery.addField("dimension2");    //2
        summaryQuery.addField("dimension3");    //3
        summaryQuery.addFieldAggregateFunction("physicalTotal", "SUM");     //4
        summaryQuery.addFieldAggregateFunction("physicalAvailable", "SUM"); //5
        summaryQuery.addFieldAggregateFunction("physicalReserved", "SUM");  //6

        EMCQuery itemQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class.getName());
        itemQuery.addAnd("itemId", combinationQuery, EMCQueryConditions.IN);
        itemQuery.addAnd("companyId", userData.getCompanyId());

        List queryList = new ArrayList();
        queryList.add(parameters.get(1));
        queryList.add(summaryQuery);
        queryList.add(itemQuery);

        return this.manipulateQueryResult(queryList, null, userData);
    }

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        List<InventoryItemDimensionCombinations> combinationList = util.executeGeneralSelectQuery(queryResult.get(0).toString(), userData);
        Map<String, InventoryItemDimensionCombinations> combinationMap = new TreeMap<String, InventoryItemDimensionCombinations>();

        List<Object[]> summaryList = util.executeGeneralSelectQuery(queryResult.get(1).toString(), userData);
        Map<String, Object[]> summaryMap = new HashMap<String, Object[]>();

        List<InventoryItemMaster> itemList = util.executeGeneralSelectQuery(queryResult.get(2).toString(), userData);
        Map<String, InventoryItemMaster> itemMap = new HashMap<String, InventoryItemMaster>();

        InventoryItemDimensionCombinations combination;
        Object[] summary;
        InventoryItemMaster item;

        int count = 0;
        String key;
        boolean combinationLoop = false;
        boolean summaryLoop = false;
        boolean itemLoop = false;

        while (true) {
            if (combinationList.size() > count) {
                combination = combinationList.get(count);
                key = combination.getItemId() + (combination.getDimension1Id().equals(systemConstants.emcNA()) ? null : combination.getDimension1Id()) +
                        (combination.getDimension2Id().equals(systemConstants.emcNA()) ? null : combination.getDimension2Id()) +
                        (combination.getDimension3Id().equals(systemConstants.emcNA()) ? null : combination.getDimension3Id());
                combinationMap.put(key, combination);
            } else {
                combinationLoop = true;
            }

            if (summaryList.size() > count) {
                summary = summaryList.get(count);
                key = (String) summary[0] + (String) summary[1] + (String) summary[2] + (String) summary[3];
                summaryMap.put(key, summary);
            } else {
                summaryLoop = true;
            }

            if (itemList.size() > count) {
                item = itemList.get(count);
                key = item.getItemId();
                itemMap.put(key, item);
            } else {
                itemLoop = true;
            }

            if (combinationLoop && summaryLoop && itemLoop) {
                break;
            }
            count++;
        }


        InventoryBelowMinimumReportDS ds;
        List retList = new ArrayList();
        double minQty = 0;

        for (String combKey : combinationMap.keySet()) {
            combination = combinationMap.get(combKey);
            item = itemMap.get(combination.getItemId());
            if (item.getStopPurchase().equals(InventoryStopPurchasingType.YES.toString()) || 
                    combination.getStopPurchase().equals(InventoryStopPurchasingType.YES.toString()) ||
                    !item.getStatus().equalsIgnoreCase(InventoryStatus.ACTIVE.toString())) {
                continue;
            } else {
                minQty = itemMasterBean.getMinQty(item.getItemId(), combination.getDimension1Id(), combination.getDimension2Id(), combination.getDimension3Id(), item, userData);
                if (minQty == 0) {
                    continue;
                } else {
                    if (summaryMap.containsKey(combKey)) {
                        summary = summaryMap.get(combKey);
                        if ((Double) summary[5] >= minQty) {
                            continue;
                        } else {
                            ds = new InventoryBelowMinimumReportDS();
                            ds.setItem(isBlank(item.getItemReference()) ? item.getItemId() : item.getItemReference());
                            ds.setDescription(item.getDescription());
                            ds.setDim1(combination.getDimension1Id().equals(systemConstants.emcNA()) ? null : combination.getDimension1Id());
                            ds.setDim2(combination.getDimension2Id().equals(systemConstants.emcNA()) ? null : combination.getDimension2Id());
                            ds.setDim3(combination.getDimension3Id().equals(systemConstants.emcNA()) ? null : combination.getDimension3Id());
                            ds.setUom(item.getBaseUOM());
                            ds.setOnHand((Double) summary[4]);
                            ds.setAvailable((Double) summary[5]);
                            ds.setReserved((Double) summary[6]);
                            ds.setMinStock(minQty);
                            ds.setQtyUnder(ds.getMinStock() - ds.getAvailable());
                            if (!isBlank(item.getLastPurchaseDate())) {
                                ds.setLastPurchaseDate(Functions.date2String(item.getLastPurchaseDate()));
                            }
                        }
                    } else {
                        ds = new InventoryBelowMinimumReportDS();
                        ds.setItem(isBlank(item.getItemReference()) ? item.getItemId() : item.getItemReference());
                        ds.setDescription(item.getDescription());
                        ds.setDim1(combination.getDimension1Id().equals(systemConstants.emcNA()) ? null : combination.getDimension1Id());
                        ds.setDim2(combination.getDimension2Id().equals(systemConstants.emcNA()) ? null : combination.getDimension2Id());
                        ds.setDim3(combination.getDimension3Id().equals(systemConstants.emcNA()) ? null : combination.getDimension3Id());
                        ds.setUom(item.getBaseUOM());
                        ds.setOnHand(0);
                        ds.setAvailable(0);
                        ds.setReserved(0);
                        ds.setMinStock(minQty);
                        ds.setQtyUnder(ds.getMinStock() - ds.getAvailable());
                        if (!isBlank(item.getLastPurchaseDate())) {
                            ds.setLastPurchaseDate(Functions.date2String(item.getLastPurchaseDate()));
                        }
                    }
                    setLastUsedDate(ds, item, userData);
                    retList.add(ds);
                }
            }
        }
        return retList;
    }

    private void setLastUsedDate(InventoryBelowMinimumReportDS ds, InventoryItemMaster item, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
        query.addTableAnd(InventoryDimensionTable.class.getName(), "itemDimId", InventoryTransactions.class.getName(), "recordID");
        query.addAnd("itemId", item.getItemId(), InventoryTransactions.class.getName());
        if (!isBlank(ds.getDim1())) {
            query.addAnd("dimension1Id", ds.getDim1(), InventoryDimensionTable.class.getName());
        }
        if (!isBlank(ds.getDim2())) {
            query.addAnd("dimension2Id", ds.getDim2(), InventoryDimensionTable.class.getName());
        }
        if (!isBlank(ds.getDim3())) {
            query.addAnd("dimension3Id", ds.getDim3(), InventoryDimensionTable.class.getName());
        }
        query.addAnd("direction", InventoryTransactionDirection.OUT.toString(), InventoryTransactions.class.getName());
        query.addAnd("physicalDate", null, InventoryTransactions.class.getName(), EMCQueryConditions.NOT);
        query.addField("physicalDate", InventoryTransactions.class.getName());
        query.addOrderBy("physicalDate", InventoryTransactions.class.getName(), EMCQueryOrderByDirections.DESC);
        List<Date> resultList = util.executeLimitedResultGeneralSelectQuery(query, 0, 1, userData);
        if (resultList != null && !resultList.isEmpty()) {
            Date d = resultList.get(0);
            if (d != null) {
                ds.setLastUsedDate(Functions.date2String(d));
            }
        }
    }
}
