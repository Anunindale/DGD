
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.inventory.stockusage;

import emc.constants.systemConstants;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryDimensionTable;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.entity.inventory.transactions.InventorySummary;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.inventorytables.InventoryStatus;
import emc.enums.inventory.purchasing.InventoryStopPurchasingType;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.reporttools.EMCReportConfig;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class InventoryStockUsageReportBean extends EMCReportBean implements InventoryStockUsageReportLocal {

    @Override
    public List<Object> getReportResult(List<Object> queryList, EMCReportConfig reportConfig, EMCUserData userData) {
        //Combination
        EMCQuery combinationQuery = ((EMCQuery) queryList.get(1)).copyQuery();
        combinationQuery.addField("itemId", InventoryItemDimensionCombinations.class.getName());


        //Summary
        EMCQuery summaryQuery = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class);
        summaryQuery.addAnd("itemId", combinationQuery, EMCQueryConditions.IN);
        summaryQuery.addAnd("companyId", userData.getCompanyId());
        summaryQuery.addGroupBy("itemId");
        summaryQuery.addGroupBy("dimension1");
        summaryQuery.addGroupBy("dimension2");
        summaryQuery.addGroupBy("dimension3");

        summaryQuery.addField("itemId");                                    //0
        summaryQuery.addField("dimension1");                                //1
        summaryQuery.addField("dimension2");                                //2
        summaryQuery.addField("dimension3");                                //3
        summaryQuery.addFieldAggregateFunction("physicalTotal", "SUM");     //4
        summaryQuery.addFieldAggregateFunction("physicalAvailable", "SUM"); //5
        summaryQuery.addFieldAggregateFunction("physicalReserved", "SUM");  //6


        //Transaction
        Map<String, Object> paramMap = reportConfig.getParameters();

        EMCQuery transactionQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
        transactionQuery.addTableAnd(InventoryDimensionTable.class.getName(), "itemDimId", InventoryTransactions.class.getName(), "recordID");
        transactionQuery.addAnd("itemId", combinationQuery, InventoryTransactions.class.getName(), EMCQueryConditions.IN);
        transactionQuery.addAnd("physicalDate", Functions.date2String((Date) paramMap.get("lastUsedDate"), "yyyy/MM/dd"), InventoryTransactions.class.getName(), EMCQueryConditions.GREATER_THAN_EQ);
        transactionQuery.addAnd("physicalDate", null, InventoryTransactions.class.getName(), EMCQueryConditions.NOT);

        transactionQuery.addGroupBy(InventoryTransactions.class.getName(), "itemId");
        transactionQuery.addGroupBy(InventoryDimensionTable.class.getName(), "dimension1Id");
        transactionQuery.addGroupBy(InventoryDimensionTable.class.getName(), "dimension2Id");
        transactionQuery.addGroupBy(InventoryDimensionTable.class.getName(), "dimension3Id");

        transactionQuery.addField("itemId", InventoryTransactions.class.getName());         //0
        transactionQuery.addField("dimension1Id", InventoryDimensionTable.class.getName());   //1
        transactionQuery.addField("dimension2Id", InventoryDimensionTable.class.getName());   //2
        transactionQuery.addField("dimension3Id", InventoryDimensionTable.class.getName());   //3
        transactionQuery.addField("physicalDate", InventoryTransactions.class.getName());   //4


        //Item
        EMCQuery itemQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class);
        itemQuery.addAnd("itemId", combinationQuery, EMCQueryConditions.IN);
        itemQuery.addAnd("companyId", userData.getCompanyId());


        //Send it on
        List holderList = new ArrayList();
        holderList.add(queryList.get(1));
        holderList.add(summaryQuery);
        holderList.add(transactionQuery);
        holderList.add(itemQuery);

        return this.manipulateQueryResult(holderList, null, userData);
    }

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        //Combination
        List<InventoryItemDimensionCombinations> combinationList = util.executeGeneralSelectQuery(queryResult.get(0).toString(), userData);
        Map<String, InventoryItemDimensionCombinations> combinationMap = new TreeMap<String, InventoryItemDimensionCombinations>();

        //Sumary
        List<Object[]> summaryList = util.executeGeneralSelectQuery(queryResult.get(1).toString(), userData);
        Map<String, Object[]> summaryMap = new HashMap<String, Object[]>();

        //Transaction
        List<Object[]> transactionList = util.executeGeneralSelectQuery(queryResult.get(2).toString(), userData);
        Map<String, Object[]> transactionMap = new HashMap<String, Object[]>();

        //Item
        List<InventoryItemMaster> itemList = util.executeGeneralSelectQuery(queryResult.get(3).toString(), userData);
        Map<String, InventoryItemMaster> itemMap = new HashMap<String, InventoryItemMaster>();

        //Query Result Vatiables
        InventoryItemDimensionCombinations combination;
        Object[] summary;
        Object[] transaction;
        InventoryItemMaster item;

        //Map Building Variables
        int count = 0;
        String key;
        boolean combinationLoop = false;
        boolean summaryLoop = false;
        boolean transactionLoop = false;
        boolean itemLoop = false;

        //Populate Maps
        while (true) {
            //Combinations
            if (combinationList.size() > count) {
                combination = combinationList.get(count);
                key = combination.getItemId() + (combination.getDimension1Id().equals(systemConstants.emcNA()) ? null : combination.getDimension1Id()) +
                        (combination.getDimension2Id().equals(systemConstants.emcNA()) ? null : combination.getDimension2Id()) +
                        (combination.getDimension3Id().equals(systemConstants.emcNA()) ? null : combination.getDimension3Id());
                combinationMap.put(key, combination);
            } else {
                combinationLoop = true;
            }

            //Summary
            if (summaryList.size() > count) {
                summary = summaryList.get(count);
                key = (String) summary[0] + (String) summary[1] + (String) summary[2] + (String) summary[3];
                summaryMap.put(key, summary);
            } else {
                summaryLoop = true;
            }

            //Transaction
            if (transactionList.size() > count) {
                transaction = transactionList.get(count);
                key = (String) transaction[0] + (String) transaction[1] + (String) transaction[2] + (String) transaction[3];
                transactionMap.put(key, transaction);
            } else {
                transactionLoop = true;
            }

            //Item
            if (itemList.size() > count) {
                item = itemList.get(count);
                key = item.getItemId();
                itemMap.put(key, item);
            } else {
                itemLoop = true;
            }

            //End Loop if all maps are populated
            if (combinationLoop && summaryLoop && itemLoop && transactionLoop) {
                break;
            }
            count++;
        }

        //Report Vari0ables
        InventoryStockUsageReportDS ds;
        List retList = new ArrayList();

        //Populate Report
        for (String combKey : combinationMap.keySet()) {
            if (!transactionMap.containsKey(combKey)) {
                combination = combinationMap.get(combKey);
                item = itemMap.get(combination.getItemId());
                if (item.getStopPurchase().equals(InventoryStopPurchasingType.YES.toString()) ||
                        combination.getStopPurchase().equals(InventoryStopPurchasingType.YES.toString()) ||
                        !item.getStatus().equalsIgnoreCase(InventoryStatus.ACTIVE.toString())) {
                    continue;
                } else {
                    ds = new InventoryStockUsageReportDS();
                    ds.setItem(isBlank(item.getItemReference()) ? item.getItemId() : item.getItemReference());
                    ds.setDescription(item.getDescription());
                    ds.setDim1(combination.getDimension1Id().equals(systemConstants.emcNA()) ? null : combination.getDimension1Id());
                    ds.setDim2(combination.getDimension2Id().equals(systemConstants.emcNA()) ? null : combination.getDimension2Id());
                    ds.setDim3(combination.getDimension3Id().equals(systemConstants.emcNA()) ? null : combination.getDimension3Id());
                    ds.setUom(item.getBaseUOM());
                    if (!isBlank(item.getLastPurchaseDate())) {
                        ds.setLastPurchaseDate(Functions.date2String(item.getLastPurchaseDate()));
                    }
                    if (summaryMap.containsKey(combKey)) {
                        summary = summaryMap.get(combKey);
                        ds.setOnHand((Double) summary[4]);
                        ds.setAvailable((Double) summary[5]);
                        ds.setReserved((Double) summary[6]);
                    } else {
                        ds.setOnHand(0);
                        ds.setAvailable(0);
                        ds.setReserved(0);
                    }
                    retList.add(ds);
                }
            }
        }
        return retList;
    }
}
