/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.warehousestockenquiry;

import emc.bus.inventory.InventoryReferenceLocal;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.dimensions.InventoryDimension2GroupSetup;
import emc.entity.inventory.transactions.InventorySummary;
import emc.entity.inventory.warehousestockenquiry.InventoryWarehouseStockEnquiryBySize;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.helpers.inventory.WarehouseEnquiryHelperClass;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
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
public class InventoryWarehouseStockEnquiryBySizeBean extends EMCEntityBean implements InventoryWarehouseStockEnquiryBySizeLocal {

    @EJB
    private InventoryWarehouseStockEnquiryBySizeLocal thisBean;
    @EJB
    private InventoryReferenceLocal referenceBean;

    @Override
    public Object populateEnquiry(WarehouseEnquiryHelperClass helper, EMCUserData userData) {
        //Clear Users Data
        thisBean.clearUserData(helper.getRecordOwner(), userData);
        //Select Summaries
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class);
        query.addTableAnd(InventoryItemMaster.class.getName(), "itemId", InventorySummary.class.getName(), "itemId");
        query.addLeftOuterJoin(InventorySummary.class, "dimension2", InventoryDimension2.class, "dimensionId");
        query.addAnd("warehouse", helper.getWarehouse());
        if (!isBlank(helper.getLocation())) {
            query.addAnd("location", helper.getLocation(), InventorySummary.class.getName());
        }
        String[] itemReference = null;
        if (!isBlank(helper.getItem())) {
            itemReference = referenceBean.checkItemReference(helper.getItem(), userData);
            if (itemReference == null) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to find item reference.", userData);
                return false;
            }
            query.addAnd("itemId", itemReference[0]);
        }
        if (!isBlank(helper.getProductGroup())) {
            query.addAnd("productGroup", helper.getProductGroup(), InventoryItemMaster.class.getName());
        }
        if (!isBlank(helper.getPlanningGroup())) {
            query.addAnd("planningPlanning", helper.getPlanningGroup(), InventoryItemMaster.class.getName());
        }
        if (!isBlank(helper.getClassification1())) {
            query.addAnd("classificationClassGroup1", helper.getClassification1(), InventoryItemMaster.class.getName());
        }
        if (!isBlank(helper.getClassification5())) {
            query.addAnd("classificationClassGroup5", helper.getClassification5(), InventoryItemMaster.class.getName());
        }
        if (!isBlank(helper.getBrandGroup())) {
            query.addAnd("brandGroup", helper.getBrandGroup(), InventoryItemMaster.class.getName());
        }
        if (!isBlank(helper.getDimension1())) {
            query.addAnd("dimension1", helper.getDimension1());
        }
        if (!isBlank(helper.getDimension3())) {
            query.addAnd("dimension3", helper.getDimension3());
        }
        query.addField("itemId", InventoryItemMaster.class.getName());//0
        query.addField("itemReference", InventoryItemMaster.class.getName());//1
        query.addField("description", InventoryItemMaster.class.getName());//2
        query.addField("dimension1", InventorySummary.class.getName());//3
        query.addField("dimension3", InventorySummary.class.getName());//4
        query.addField("productGroup", InventoryItemMaster.class.getName());//5
        query.addField("planningPlanning", InventoryItemMaster.class.getName());//6
        query.addField("classificationClassGroup1", InventoryItemMaster.class.getName());//7
        query.addField("classificationClassGroup5", InventoryItemMaster.class.getName());//8
        query.addField("brandGroup", InventoryItemMaster.class.getName());//9
        query.addFieldAggregateFunction("physicalTotal", InventorySummary.class.getName(), "SUM");//10
        query.addFieldAggregateFunction("physicalReserved", InventorySummary.class.getName(), "SUM");//11
        query.addField("dimension2", InventorySummary.class.getName());//12
        query.addField("location", InventorySummary.class.getName());//13

        query.addGroupBy(InventoryItemMaster.class.getName(), "itemId");
        query.addGroupBy(InventorySummary.class.getName(), "dimension1");
        query.addGroupBy(InventorySummary.class.getName(), "dimension2");
        query.addGroupBy(InventorySummary.class.getName(), "dimension3");
        query.addGroupBy(InventorySummary.class.getName(), "location");

        String summaryAlias = query.getTableAlias(InventorySummary.class);
        if (helper.isTotalQuantity()) {
            query.addAndCustomHavingValue("SUM(" + summaryAlias + ".physicalTotal) > 0");
        } else {
            query.addAndCustomHavingValue("SUM(" + summaryAlias + ".physicalTotal - " + summaryAlias + ".physicalReserved) > 0");
        }

        if (helper.isCrates()) {
            query.addStringNotBlank("batch", InventorySummary.class.getName(), EMCQueryBracketConditions.AND);
            query.addGroupBy(InventorySummary.class.getName(), "batch");
        }

        List<Object[]> dataList = util.executeNativeQuery(query, userData);
        if (dataList.isEmpty()) {
            Logger.getLogger("emc").log(Level.SEVERE, "No Stock Found.", userData);
            return false;
        }
        //Select Needed size groups on item
        query = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class);
        query.addTableAnd(InventoryItemMaster.class.getName(), "itemId", InventorySummary.class.getName(), "itemId");
        query.openAndConditionBracket();
        query.addOr("physicalTotal", 0, InventorySummary.class.getName(), EMCQueryConditions.NOT);
        query.addOr("physicalReserved", 0, InventorySummary.class.getName(), EMCQueryConditions.NOT);
        query.closeConditionBracket();
        query.addAnd("warehouse", helper.getWarehouse());
        if (!isBlank(helper.getLocation())) {
            query.addAnd("location", helper.getLocation());
        }
        if (!isBlank(helper.getItem())) {
            query.addAnd("itemId", itemReference[0]);
        }
        if (!isBlank(helper.getProductGroup())) {
            query.addAnd("productGroup", helper.getProductGroup(), InventoryItemMaster.class.getName());
        }
        if (!isBlank(helper.getPlanningGroup())) {
            query.addAnd("planningPlanning", helper.getPlanningGroup(), InventoryItemMaster.class.getName());
        }
        if (!isBlank(helper.getClassification1())) {
            query.addAnd("classificationClassGroup1", helper.getClassification1(), InventoryItemMaster.class.getName());
        }
        if (!isBlank(helper.getClassification5())) {
            query.addAnd("classificationClassGroup5", helper.getClassification5(), InventoryItemMaster.class.getName());
        }
        if (!isBlank(helper.getBrandGroup())) {
            query.addAnd("brandGroup", helper.getBrandGroup(), InventoryItemMaster.class.getName());
        }
        if (!isBlank(helper.getDimension1())) {
            query.addAnd("dimension1", helper.getDimension1());
        }
        if (!isBlank(helper.getDimension3())) {
            query.addAnd("dimension3", helper.getDimension3());
        }
        query.addField("dimension2Group", InventoryItemMaster.class.getName());
        query.addGroupBy(InventoryItemMaster.class.getName(), "dimension2Group");
        query.addOrderBy("dimension2Group", InventoryItemMaster.class.getName());
        List<String> dimension2Groups = util.executeGeneralSelectQuery(query, userData);
        Map<String, String> sizeColumnMapper = new HashMap<String, String>();
        List<String> sizeColumns = new ArrayList<String>();
        List<String> sizeColumnNames = new ArrayList<String>();
        int quantityColumn = 1;
        if (dimension2Groups.isEmpty()) {
            sizeColumnMapper.put(null, "quantity" + quantityColumn);
            sizeColumns.add("quantity" + quantityColumn);
            sizeColumnNames.add("No Sizes");
            quantityColumn++;
        } else {
            if (dimension2Groups.contains(null)) {
                sizeColumnMapper.put(null, "quantity" + quantityColumn);
                sizeColumns.add("quantity" + quantityColumn);
                sizeColumnNames.add("No Sizes");
                quantityColumn++;
            }
            query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension2GroupSetup.class);
            query.addTableAnd(InventoryDimension2.class.getName(), "dimensionId", InventoryDimension2GroupSetup.class.getName(), "dimensionId");
            query.addAndInList("dimensionGroupId", InventoryDimension2GroupSetup.class.getName(), dimension2Groups, true, false);
            query.addField("dimensionId", InventoryDimension2.class.getName());
            query.addOrderBy("dimensionGroupId", InventoryDimension2GroupSetup.class.getName());
            query.addOrderBy("sortCode", InventoryDimension2.class.getName());
            query.addGroupBy(InventoryDimension2.class.getName(), "dimensionId");
            List<String> dimension2List = util.executeGeneralSelectQuery(query, userData);
            for (String dimension2 : dimension2List) {
                sizeColumnMapper.put(dimension2, "quantity" + quantityColumn);
                sizeColumns.add("quantity" + quantityColumn);
                sizeColumnNames.add(dimension2);
                quantityColumn++;
            }
        }

        List<InventoryWarehouseStockEnquiryBySize> enquiryList = new ArrayList<InventoryWarehouseStockEnquiryBySize>();
        InventoryWarehouseStockEnquiryBySize enquiry;
        BigDecimal quantity;
        Field f;
        //Create Enquiry Records
        for (Object[] data : dataList) {
            if (enquiryList.size() == 1000) {
                util.insertDirect(InventoryWarehouseStockEnquiryBySize.class, enquiryList, userData);
                enquiryList.clear();
            }
            enquiry = new InventoryWarehouseStockEnquiryBySize();
            enquiry.setRecordOwner(helper.getRecordOwner());
            enquiry.setItemId((String) data[0]);
            enquiry.setItemReference((String) data[1]);
            enquiry.setItemDescription((String) data[2]);
            enquiry.setDimension1((String) data[3]);
            enquiry.setDimension3((String) data[4]);
            enquiry.setLocation((String) data[13]);
            enquiry.setProductGroup((String) data[5]);
            enquiry.setPlanningGroup((String) data[6]);
            enquiry.setClassification1((String) data[7]);
            enquiry.setClassification5((String) data[8]);
            enquiry.setBrandGroup((String) data[9]);
            if (helper.isCrates()) {
                quantity = BigDecimal.ONE;
            } else {
                if (helper.isTotalQuantity()) {
                    quantity = util.getBigDecimal((Double) (isBlank(data[10]) ? 0 : data[10]));
                } else {
                    quantity = util.getBigDecimal((Double) (isBlank(data[10]) ? 0 : data[10]) - (Double) (isBlank(data[11]) ? 0 : data[11]));
                }
            }
            try {
                f = InventoryWarehouseStockEnquiryBySize.class.getDeclaredField(sizeColumnMapper.get((String) (isBlank(data[12]) ? null : data[12])));
                f.setAccessible(true);
                f.set(enquiry, quantity.add((BigDecimal) f.get(enquiry)));
            } catch (Exception ex) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to set quantity column: " + ex.getMessage(), userData);
            }
            enquiry.setQuantityTotal(enquiry.getQuantityTotal().add(quantity));

            enquiryList.add(enquiry);
        }
        if (!enquiryList.isEmpty()) {
            util.insertDirect(InventoryWarehouseStockEnquiryBySize.class, enquiryList, userData);
        }

        List retList = new ArrayList();
        retList.add(sizeColumns);
        retList.add(sizeColumnNames);

        return retList;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void clearUserData(String recordOwner, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.DELETE, InventoryWarehouseStockEnquiryBySize.class);
        query.addAnd("recordOwner", recordOwner);
        util.executeUpdate(query, userData);
    }

    @Override
    public String getNumRows(Class theTable, EMCUserData userData) {
        EMCQuery query = (EMCQuery) userData.getUserData(0);
        query.addGroupBy("recordOwner");
        query.addField("recordID");
        List data = util.executeGeneralSelectQuery(query.toString(), userData);
        return data.size() + ", 0";
    }

    @Override
    public Collection getDataInRange(Class theTable, EMCUserData userData, int start, int end) {
        EMCQuery theQuery = (EMCQuery) userData.getUserData(0);
        theQuery.addGroupBy("recordOwner");
        String originalQuery = theQuery.toString();
        String query = "SELECT new InventoryWarehouseStockEnquiryBySize(recordOwner, itemId, itemReference, itemDescription, productGroup, planningGroup, " +
                "classification1, classification5, brandGroup, dimension1, dimension3, location, SUM(quantity1), SUM(quantity2), SUM(quantity3), SUM(quantity4), " +
                "SUM(quantity5), SUM(quantity6), SUM(quantity7), SUM(quantity8), SUM(quantity9), SUM(quantity10), SUM(quantity11), SUM(quantity12), " +
                "SUM(quantity13), SUM(quantity14), SUM(quantity15), SUM(quantity16), SUM(quantity17), SUM(quantity18), SUM(quantity19), SUM(quantity20), " +
                "SUM(quantity21), SUM(quantity22), SUM(quantity23), SUM(quantity24), SUM(quantity25), SUM(quantity26), SUM(quantity27), SUM(quantity28), " +
                "SUM(quantity29), SUM(quantity30), SUM(quantity31), SUM(quantity32), SUM(quantity33), SUM(quantity34), SUM(quantity35), SUM(quantity36), " +
                "SUM(quantity37), SUM(quantity38), SUM(quantity39), SUM(quantity40), SUM(quantity41), SUM(quantity42), SUM(quantity43), SUM(quantity44), " +
                "SUM(quantity45), SUM(quantity46), SUM(quantity47), SUM(quantity48), SUM(quantity49), SUM(quantity50), SUM(quantity51), SUM(quantity52), " +
                "SUM(quantity53), SUM(quantity54), SUM(quantity55), SUM(quantity56), SUM(quantity57), SUM(quantity58), SUM(quantity59), SUM(quantity60), " +
                "SUM(quantityTotal)) " + originalQuery.substring(originalQuery.indexOf("FROM"));
        userData.setUserData(0, query);

        Collection data = super.getDataInRange(theTable, userData, start, end);
        return data;
    }
}
