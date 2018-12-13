/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.warehousestockenquiry;

import emc.bus.inventory.InventoryReferenceLocal;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.agebins.InventoryAgeBins;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.transactions.InventorySummary;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.entity.inventory.warehousestockenquiry.InventoryWarehouseStockEnquiry;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.transactions.InventoryTransactionDirection;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author wikus
 */
@Stateless
public class InventoryWarehouseStockEnquiryBean extends EMCEntityBean implements InventoryWarehouseStockEnquiryLocal {

    @EJB
    private InventoryWarehouseStockEnquiryLocal thisBean;
    @EJB
    private InventoryReferenceLocal referenceBean;

    @Override
    public boolean populateEnquiry(String warehouse, String location, String item, String productGroup, String planningGroup, String classification1, String classification5,
            String dimension1, String dimension2, String dimension3, String batch, String serial, int boxLevel, boolean doAging, EMCUserData userData) {
        //Clear Users Data
        thisBean.clearUserData(userData);
        //Select Summaries
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class);
        query.addTableAnd(InventoryItemMaster.class.getName(), "itemId", InventorySummary.class.getName(), "itemId");
        query.openAndConditionBracket();
        query.addOr("physicalTotal", 0, InventorySummary.class.getName(), EMCQueryConditions.NOT);
        query.addOr("physicalReserved", 0, InventorySummary.class.getName(), EMCQueryConditions.NOT);
        query.closeConditionBracket();
        query.addAnd("warehouse", warehouse);
        if (!isBlank(item)) {
            String[] itemReference = referenceBean.checkItemReference(item, userData);
            if (itemReference == null) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to find item reference.", userData);
                return false;
            }
            query.addAnd("itemId", itemReference[0]);
        }
        if (!isBlank(location)) {
            query.addAnd("location", location);
        }
        if (!isBlank(productGroup)) {
            query.addAnd("productGroup", productGroup, InventoryItemMaster.class.getName());
        }
        if (!isBlank(planningGroup)) {
            query.addAnd("planningPlanning", planningGroup, InventoryItemMaster.class.getName());
        }
        if (!isBlank(classification1)) {
            query.addAnd("classificationClassGroup1", classification1, InventoryItemMaster.class.getName());
        }
        if (!isBlank(classification5)) {
            query.addAnd("classificationClassGroup5", classification5, InventoryItemMaster.class.getName());
        }
        if (!isBlank(dimension1)) {
            query.addAnd("dimension1", dimension1);
        }
        if (!isBlank(dimension2)) {
            query.addAnd("dimension2", dimension2);
        }
        if (!isBlank(dimension3)) {
            query.addAnd("dimension3", dimension3);
        }
        if (!isBlank(batch)) {
            query.addAnd("batch", batch);
        }
        if (!isBlank(serial)) {
            query.addAnd("serialNo", serial);
        }

        query.addField("itemId", InventoryItemMaster.class.getName());//0
        query.addField("itemReference", InventoryItemMaster.class.getName());//1
        query.addField("description", InventoryItemMaster.class.getName());//2
        query.addField("dimension1", InventorySummary.class.getName());//3
        query.addField("dimension2", InventorySummary.class.getName());//4
        query.addCustomField("", true, "Custom_1");
        query.addField("dimension3", InventorySummary.class.getName());//6
        query.addField("batch", InventorySummary.class.getName());//7
        query.addField("serialNo", InventorySummary.class.getName());//8
        query.addFieldAggregateFunction("physicalReserved", InventorySummary.class.getName(), "SUM");//9
        query.addFieldAggregateFunction("physicalTotal", InventorySummary.class.getName(), "SUM");//10
        query.addField("productGroup", InventoryItemMaster.class.getName());//11
        query.addField("planningPlanning", InventoryItemMaster.class.getName());//12
        query.addField("classificationClassGroup1", InventoryItemMaster.class.getName());//13
        query.addField("classificationClassGroup5", InventoryItemMaster.class.getName());//14
        query.addField("location", InventorySummary.class.getName());//15
        query.addField("itemDimId", InventorySummary.class.getName());//16

        query.addGroupBy(InventoryItemMaster.class.getName(), "itemId");
        query.addGroupBy(InventorySummary.class.getName(), "dimension1");
        query.addGroupBy(InventorySummary.class.getName(), "dimension2");
        query.addGroupBy(InventorySummary.class.getName(), "dimension3");
        query.addGroupBy(InventorySummary.class.getName(), "batch");
        query.addGroupBy(InventorySummary.class.getName(), "serialNo");
        query.addGroupBy(InventorySummary.class.getName(), "location");

        if (boxLevel > 0) {
            EMCQuery nestedQuery = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class);

            nestedQuery.addAnd("warehouse", warehouse);

            if (!isBlank(location)) {
                nestedQuery.addAnd("location", location);
            }

            if (!isBlank(item)) {
                String[] itemReference = referenceBean.checkItemReference(item, userData);
                if (itemReference == null) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to find item reference.", userData);
                    return false;
                }
                nestedQuery.addAnd("itemId", itemReference[0]);
            }
            if (!isBlank(dimension1)) {
                nestedQuery.addAnd("dimension1", dimension1);
            }
            if (!isBlank(dimension2)) {
                nestedQuery.addAnd("dimension2", dimension2);
            }
            if (!isBlank(dimension3)) {
                nestedQuery.addAnd("dimension3", dimension3);
            }
            if (!isBlank(batch)) {
                nestedQuery.addAnd("batch", batch);
            }
            if (!isBlank(serial)) {
                nestedQuery.addAnd("serialNo", serial);
            }

            nestedQuery.addGroupBy(InventorySummary.class.getName(), "itemId");
            nestedQuery.addGroupBy(InventorySummary.class.getName(), "dimension1");
            nestedQuery.addGroupBy(InventorySummary.class.getName(), "dimension2");
            nestedQuery.addGroupBy(InventorySummary.class.getName(), "dimension3");
            nestedQuery.addGroupBy(InventorySummary.class.getName(), "batch");

            String summaryAlias = nestedQuery.getTableAlias(InventorySummary.class);
            String itemField = "IFNULL(" + summaryAlias + ".itemId, \'null\'), ";
            String dim1Field = "IFNULL(" + summaryAlias + ".dimension1, \'null\'), ";
            String dim2Field = "IFNULL(" + summaryAlias + ".dimension2, \'null\'), ";
            String dim3Field = "IFNULL(" + summaryAlias + ".dimension3, \'null\'), ";
            String batchField = "IFNULL(" + summaryAlias + ".batch, \'null\')";

            nestedQuery.addCustomField("CONCAT(" + itemField + dim1Field + dim2Field + dim3Field + batchField + ")", false);

            nestedQuery.addAndCustomHavingValue("SUM(" + summaryAlias + ".physicalTotal) < " + boxLevel + " AND SUM(" + summaryAlias + ".physicalTotal) > 0");

            List<String> nestedValues = util.executeGeneralSelectQuery(nestedQuery, userData);

            if (nestedValues.isEmpty()) {
                Logger.getLogger("emc").log(Level.SEVERE, "No Stock Found.", userData);
                return false;
            }

            summaryAlias = query.getTableAlias(InventorySummary.class);
            itemField = "IFNULL(" + summaryAlias + ".itemId, \'null\'), ";
            dim1Field = "IFNULL(" + summaryAlias + ".dimension1, \'null\'), ";
            dim2Field = "IFNULL(" + summaryAlias + ".dimension2, \'null\'), ";
            dim3Field = "IFNULL(" + summaryAlias + ".dimension3, \'null\'), ";
            batchField = "IFNULL(" + summaryAlias + ".batch, \'null\')";

            query.addCustomAndInList("CONCAT(" + itemField + dim1Field + dim2Field + dim3Field + batchField + ")", nestedValues, true, false);
        }

        List<Object[]> dataList = util.executeNativeQuery(query, userData);
        if (dataList.isEmpty()) {
            Logger.getLogger("emc").log(Level.SEVERE, "No Stock Found.", userData);
            return false;
        }

        Date physicalDate = Functions.nowDate();
        EMCQuery binQ = new EMCQuery(enumQueryTypes.SELECT, InventoryAgeBins.class);
        binQ.addOrderBy("binOrder");
        binQ.addField("numberOfDaysInBin");
        List<Object> bins = util.executeGeneralSelectQuery(binQ, userData);
        List<Integer> ageingBinRanges = new ArrayList();
        //Build the bin size List.
        if (bins.size() > 6) {
            Logger.getLogger("emc").log(Level.SEVERE, "Only six age bins allowed", userData);
        }
        for (int j = 0; j < bins.size(); j++) {
            Integer value = (Integer) bins.get(j);
            Integer maxSize = (Integer) value;
            for (int k = 0; k < j; k++) {
                Integer curValue = (Integer) bins.get(k);
                maxSize += curValue;
            }
            ageingBinRanges.add(maxSize);
        }

        List<InventoryWarehouseStockEnquiry> enquiryList = new ArrayList<InventoryWarehouseStockEnquiry>();
        InventoryWarehouseStockEnquiry enquiry;

        Map<String, Integer> sizeMap = new HashMap<String, Integer>();
        Integer sortCode;

        for (Object[] data : dataList) {
            if (enquiryList.size() == 1000) {
                util.insertDirect(InventoryWarehouseStockEnquiry.class, enquiryList, userData);
                enquiryList.clear();
                try {
                    util.flushEntity(userData);
                } catch (Exception ex) {
                }
            }

            enquiry = new InventoryWarehouseStockEnquiry();
            enquiry.setRecordOwner(userData.getUserName());
            enquiry.setItemId((String) data[0]);
            enquiry.setItemReference((String) data[1]);
            enquiry.setItemDescription((String) data[2]);
            enquiry.setDimension1((String) data[3]);
            enquiry.setDimension2((String) data[4]);
            if (!isBlank(enquiry.getDimension2())) {
                sortCode = sizeMap.get(enquiry.getDimension2());
                if (sortCode == null) {
                    query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension2.class);
                    query.addAnd("dimensionId", enquiry.getDimension2());
                    query.addField("sortCode");
                    sortCode = (Integer) util.executeSingleResultQuery(query, userData);
                    if (sortCode == null) {
                        sortCode = 0;
                    }
                    sizeMap.put(enquiry.getDimension2(), sortCode);
                }
                enquiry.setDimension2SortCode(sortCode);
            }
            enquiry.setDimension3((String) data[6]);
            enquiry.setBatch((String) data[7]);
            enquiry.setSerial((String) data[8]);
            enquiry.setReservedQuantity(util.getBigDecimal((Double) data[9]));
            enquiry.setTotalQuantity(util.getBigDecimal((Double) data[10]));
            enquiry.setAvailableQuantity(enquiry.getTotalQuantity().subtract(enquiry.getReservedQuantity()));
            enquiry.setProductGroup((String) data[11]);
            enquiry.setPlanningGroup((String) data[12]);
            enquiry.setClassification1((String) data[13]);
            enquiry.setClassification5((String) data[14]);
            enquiry.setLocation((String) data[15]);

            if (doAging) {
                EMCQuery txQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class.getName());
                txQuery.addAnd("itemId", data[0]); //Get item id from query selection
                txQuery.addAnd("itemDimId", data[16]); //Get dimension id from query selection.
                txQuery.addAnd("physicalDate", null, EMCQueryConditions.IS_NOT_NULL);
                txQuery.addAnd("closedFlag", false);
                txQuery.addAnd("direction", InventoryTransactionDirection.IN.toString());

                List<InventoryTransactions> inTx = (List<InventoryTransactions>) util.executeGeneralSelectQuery(txQuery, userData);

                txQuery.removeAnd("direction");
                txQuery.addAnd("direction", InventoryTransactionDirection.OUT.toString());

                List<InventoryTransactions> outTx = (List<InventoryTransactions>) util.executeGeneralSelectQuery(txQuery, userData);

                doAgeing(physicalDate, enquiry, inTx, outTx, ageingBinRanges, userData);
            }
            enquiryList.add(enquiry);
        }
        if (!enquiryList.isEmpty()) {
            util.insertDirect(InventoryWarehouseStockEnquiry.class, enquiryList, userData);
        }
        return true;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void clearUserData(EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.DELETE, InventoryWarehouseStockEnquiry.class);
        query.addAnd("recordOwner", userData.getUserName());
        util.executeUpdate(query, userData);
    }

    @Override
    public String getNumRows(Class theTable, EMCUserData userData) {
        EMCQuery query = (EMCQuery) userData.getUserData(0);
        query.addGroupBy("recordOwner");
        query.addField("recordID");
        query.addAndCustomHavingValue("SUM(totalQuantity) > 0");
        List data = util.executeGeneralSelectQuery(query.toString(), userData);
        return data.size() + ", 0";
    }

    @Override
    public Collection getDataInRange(Class theTable, EMCUserData userData, int start, int end) {
        EMCQuery theQuery = (EMCQuery) userData.getUserData(0);
        theQuery.addGroupBy("recordOwner");
        String originalQuery = theQuery.toString();
        String query;
        if (originalQuery.contains("GROUP BY")) {
            query = "SELECT new InventoryWarehouseStockEnquiry(recordOwner, itemId, itemReference, itemDescription, productGroup, planningGroup, " +
                    "classification1, classification5, dimension1, dimension2, dimension2SortCode, dimension3, batch, serial, location, " +
                    "SUM(reservedQuantity), SUM(bin1Quantity), SUM(bin2Quantity), SUM(bin3Quantity), SUM(bin4Quantity), " +
                    "SUM(bin5Quantity), SUM(totalQuantity), SUM(availableQuantity)) " +
                    (originalQuery.indexOf("ORDER BY") == -1 ? originalQuery.substring(originalQuery.indexOf("FROM")) : originalQuery.substring(originalQuery.indexOf("FROM"), originalQuery.indexOf("ORDER BY"))) +
                    " HAVING SUM(totalQuantity) > 0 " + (originalQuery.indexOf("ORDER BY") == -1 ? "" : originalQuery.substring(originalQuery.indexOf("ORDER BY")));
            userData.setUserData(0, query);
        }
        Collection data = super.getDataInRange(theTable, userData, start, end);
        return data;
    }

    /**
     * Poulates ageing bins on the specified InventoryWarehouseStockEnquiry instance.
     * @param physicalDate Date at which ageing is done.  This should always be the
     * date on which the enquiry was opened.  (This is to cater for a scenario where
     * the enquiry is populated at midnight.)
     * @param enquiryRecord  InventoryWarehouseStockEnquiry instance to populate.
     * @param inTx In transactions to use when populating ageing bins.
     * @param outTx Out transactions to use when populating ageing bins.
     * @param ageingBinRanges Ageing bin ranges (days).
     * @param userData User data
     */
    private void doAgeing(Date physicalDate, InventoryWarehouseStockEnquiry enquiryRecord, List<InventoryTransactions> inTx, List<InventoryTransactions> outTx, List<Integer> ageingBinRanges, EMCUserData userData) {
        //Do not update transactions.
        util.detachEntities(userData);
        for (InventoryTransactions out : outTx) {
            for (InventoryTransactions in : inTx) {
                //inavailable >= out
                if (util.compareDouble(in.getQuantity() - in.getClosedQty(), out.getQuantity()) >= 0) {
                    in.setClosedQty(in.getClosedQty() + out.getQuantity());
                    break; //consumed the out
                } else {
                    out.setQuantity(out.getQuantity() - (in.getQuantity() - in.getClosedQty()));
                    in.setClosedQty(in.getQuantity()); //close this transaction
                    in.setClosedFlag(true);
                    //continue still need to consume the rest of this out
                }
            }
        }
        //now place in bins
        for (InventoryTransactions in : inTx) {
            if (!in.getClosedFlag()) {
                long days = dateHandler.getDateDiffInDays(in.getPhysicalDate(), physicalDate, userData);
                int binNumber = 0;
                for (int j = 0; j < ageingBinRanges.size(); j++) {
                    binNumber = j;
                    if (ageingBinRanges.get(j) >= days) {
                        break;
                    }

                }
                switch (binNumber) {
                    case 0:
                        enquiryRecord.setBin1Quantity(enquiryRecord.getBin1Quantity().add(new BigDecimal(in.getQuantity() - in.getClosedQty())));
                        break;
                    case 1:
                        enquiryRecord.setBin2Quantity(enquiryRecord.getBin2Quantity().add(new BigDecimal(in.getQuantity() - in.getClosedQty())));
                        break;
                    case 2:
                        enquiryRecord.setBin3Quantity(enquiryRecord.getBin3Quantity().add(new BigDecimal(in.getQuantity() - in.getClosedQty())));
                        break;
                    case 3: //Fall through.  Cater for 4 bins only.
                    case 4:
                    case 5:
                        enquiryRecord.setBin4Quantity(enquiryRecord.getBin4Quantity().add(new BigDecimal(in.getQuantity() - in.getClosedQty())));
                        break;
                    default:
                        break;
                }

            }
        }
    }
}
