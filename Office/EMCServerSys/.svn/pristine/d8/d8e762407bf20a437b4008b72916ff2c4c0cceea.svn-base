/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.sop.pricearangements;

import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.inventory.dimensions.InventoryDimension1Local;
import emc.bus.inventory.dimensions.InventoryDimension2Local;
import emc.bus.inventory.dimensions.InventoryDimension3Local;
import emc.bus.sop.customers.SOPCustomersLocal;
import emc.bus.sop.parameters.SOPParametersLocal;
import emc.bus.sop.priceaudittrail.SOPPriceAuditTrailLocal;
import emc.entity.inventory.InventoryCostingGroup;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.entity.sop.SOPCustomers;
import emc.entity.sop.SOPParameters;
import emc.entity.sop.SOPPriceArangements;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.emcquery.EMCQueryOrderByDirections;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.costing.CostLevel;
import emc.enums.sop.pricearangement.PricingCustomerType;
import emc.enums.sop.priceaudittrail.SOPPriceAuditTrailType;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.tables.EMCTable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class SOPPriceArangementsBean extends EMCEntityBean implements SOPPriceArangementsLocal {

    @EJB
    private InventoryItemMasterLocal itemMasterBean;
    @EJB
    private InventoryReferenceLocal itemReferenceBean;
    @EJB
    private SOPParametersLocal parametersBean;
    @EJB
    private SOPCustomersLocal customerBean;
    @EJB
    private InventoryDimension1Local dim1Bean;
    @EJB
    private InventoryDimension2Local dim2Bean;
    @EJB
    private InventoryDimension3Local dim3Bean;
    @EJB
    private SOPPriceAuditTrailLocal auditTrailBean;

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean valid = super.doUpdateValidation(vobject, userData);
        if (valid) {
            valid = doSaveValidation((SOPPriceArangements) vobject, userData);
        }
        return valid;
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean valid = super.doInsertValidation(vobject, userData);
        if (valid) {
            valid = doSaveValidation((SOPPriceArangements) vobject, userData);
        }
        return valid;
    }

    private boolean doSaveValidation(SOPPriceArangements arangement, EMCUserData userData) {
        if (isBlank(arangement.getPriceGroup())) {
            arangement.setPriceGroup(null);
        }
        if (isBlank(arangement.getCustomerId())) {
            arangement.setCustomerId(null);
        }
        if (isBlank(arangement.getItemId())) {
            arangement.setItemId(null);
        }
        if (isBlank(arangement.getDimension1())) {
            arangement.setDimension1(null);
        }
        if (isBlank(arangement.getDimension2())) {
            arangement.setDimension2(null);
        }
        if (isBlank(arangement.getDimension3())) {
            arangement.setDimension3(null);
        }

        switch (PricingCustomerType.fromString(arangement.getCustomerType())) {
            case CUSTOMER:
                if (isBlank(arangement.getCustomerId())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Price List Type is Customer. Please select a valid customer.", userData);
                    return false;
                }
                break;
            case GROUP:
                if (isBlank(arangement.getPriceGroup())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Price List Type is Group. Please select a valid price group.", userData);
                    return false;
                }
        }

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPPriceArangements.class);
        query.addAnd("priceGroup", arangement.getPriceGroup());
        query.addAnd("customerId", arangement.getCustomerId());
        query.addAnd("itemId", arangement.getItemId());
        query.addAnd("dimension1", arangement.getDimension1());
        query.addAnd("dimension2", arangement.getDimension2());
        query.addAnd("dimension3", arangement.getDimension3());
        query.openAndConditionBracket();
        query.openConditionBracket(EMCQueryBracketConditions.NONE);
        query.addAnd("fromDate", Functions.date2SQLString(arangement.getFromDate()), EMCQueryConditions.GREATER_THAN_EQ);
        query.addAnd("fromDate", Functions.date2SQLString(arangement.getToDate()), EMCQueryConditions.LESS_THAN_EQ);
        query.closeConditionBracket();
        query.openConditionBracket(EMCQueryBracketConditions.OR);
        query.addAnd("toDate", Functions.date2SQLString(arangement.getFromDate()), EMCQueryConditions.GREATER_THAN_EQ);
        query.addAnd("toDate", Functions.date2SQLString(arangement.getToDate()), EMCQueryConditions.LESS_THAN_EQ);
        query.closeConditionBracket();
        query.openConditionBracket(EMCQueryBracketConditions.OR);
        query.addAnd("fromDate", Functions.date2SQLString(arangement.getFromDate()), EMCQueryConditions.LESS_THAN_EQ);
        query.addAnd("toDate", Functions.date2SQLString(arangement.getToDate()), EMCQueryConditions.GREATER_THAN_EQ);
        query.closeConditionBracket();
        query.closeConditionBracket();
        query.addAnd("quantity", arangement.getQuantity());
        query.addAnd("recordID", arangement.getRecordID(), EMCQueryConditions.NOT);
        if (util.exists(query, userData)) {
            Logger.getLogger("emc").log(Level.SEVERE, "A similar record exists in the given period.", userData);
            return false;
        }
        return true;
    }

    @Override
    public BigDecimal findItemPrice(Connection conn, InventoryItemMaster item, InventoryItemDimensionCombinations combination, InventoryCostingGroup costingGroup,
            SOPCustomers customer, SOPParameters param, String priceGroup, String customerId, String itemId, String dimension1, String dimension2, String dimension3,
            Date lineDate, BigDecimal quantity, EMCUserData userData) throws EMCEntityBeanException {

        if (param == null) {
            param = parametersBean.getParameterRecord(userData);

            if (param == null) {
                logMessage(Level.SEVERE, "No SOP Parameters found.", userData);
                return BigDecimal.ZERO;
            }
        }
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPPriceArangements.class);
        //Customer
        query.openConditionBracket(EMCQueryBracketConditions.NONE);
        query.openConditionBracket(EMCQueryBracketConditions.NONE);
        if (!isBlank(priceGroup)) {
            query.addAnd("priceGroup", priceGroup);
            query.addAnd("customerId", null);
        } else {
            if (isBlank(customerId)) {
                query.addAnd("customerId", null);
                query.addAnd("priceGroup", null);
                customerId = null;
            } else {
                if (customer == null) {
                    customer = customerBean.findCustomer(customerId, userData);
                }

                if (customer == null) {
                    logMessage(Level.SEVERE, "Customer not found.", userData);
                    return BigDecimal.ZERO;
                }

                query.openConditionBracket(EMCQueryBracketConditions.NONE);
                query.addOr("customerId", customerId);
                query.addOr("customerId", customer.getInvoiceToCustomer());
                query.addOr("customerId", null);
                query.closeConditionBracket();
                priceGroup = customer.getPriceGroup();
                if (isBlank(priceGroup)) {
                    query.addAnd("priceGroup", null);
                    priceGroup = null;
                } else {
                    query.openConditionBracket(EMCQueryBracketConditions.AND);
                    query.addOr("priceGroup", priceGroup);
                    query.addOr("priceGroup", null);
                    query.closeConditionBracket();
                }
            }

        }
        query.closeConditionBracket();
        query.addOr("customerType", PricingCustomerType.ALL.toString());
        query.closeConditionBracket();
        //Item
        if (isBlank(itemId)) {
            query.addAnd("itemId", null);
            itemId = null;
        } else {
            query.openConditionBracket(EMCQueryBracketConditions.AND);
            query.addOr("itemId", itemId);
            query.addOr("itemId", null);
            query.closeConditionBracket();
        }
        //Config
        if (isBlank(dimension1)) {
            query.addAnd("dimension1", null);
            dimension1 = null;
        } else {
            query.openConditionBracket(EMCQueryBracketConditions.AND);
            query.addOr("dimension1", dimension1);
            query.addOr("dimension1", null);
            query.closeConditionBracket();
        }
        //Size
        if (isBlank(dimension2)) {
            query.addAnd("dimension2", null);
            dimension2 = null;
        } else {
            query.openConditionBracket(EMCQueryBracketConditions.AND);
            query.addOr("dimension2", dimension2);
            query.addOr("dimension2", null);
            query.closeConditionBracket();
        }
        //Colour
        if (isBlank(dimension3)) {
            query.addAnd("dimension3", null);
            dimension3 = null;
        } else {
            query.openConditionBracket(EMCQueryBracketConditions.AND);
            query.addOr("dimension3", dimension3);
            query.addOr("dimension3", null);
            query.closeConditionBracket();
        }
        //Date
        if (isBlank(lineDate)) {
            query.addAnd("fromDate", Functions.date2SQLString(Functions.nowDate()), EMCQueryConditions.LESS_THAN_EQ);
            query.addAnd("toDate", Functions.date2SQLString(Functions.nowDate()), EMCQueryConditions.GREATER_THAN_EQ);
        } else {
            query.addAnd("fromDate", Functions.date2SQLString(lineDate), EMCQueryConditions.LESS_THAN_EQ);
            query.addAnd("toDate", Functions.date2SQLString(lineDate), EMCQueryConditions.GREATER_THAN_EQ);
        }
        //Quantity
        query.addAnd("quantity", quantity, EMCQueryConditions.LESS_THAN_EQ);

        List<SOPPriceArangements> priceArangementList;
        SOPPriceArangements bestMatch = null;
        if (param.isUseLowestPrice()) {
            //Use lowest price
            query.addOrderBy("price");
            if (conn == null) {
                priceArangementList = util.executeGeneralSelectQuery(query, userData);
            } else {
                priceArangementList = util.exJDBCReadQuery(conn, query, userData);
            }
            if (priceArangementList != null && !priceArangementList.isEmpty()) {
                bestMatch = priceArangementList.get(0);
            }
        } else {
            //Use best match
            query.addOrderBy("itemId", SOPPriceArangements.class.getName(), EMCQueryOrderByDirections.DESC);
            query.addOrderBy("customerId", SOPPriceArangements.class.getName(), EMCQueryOrderByDirections.DESC);
            query.addOrderBy("priceGroup", SOPPriceArangements.class.getName(), EMCQueryOrderByDirections.DESC);
            query.addOrderBy("dimension1", SOPPriceArangements.class.getName(), EMCQueryOrderByDirections.DESC);
            query.addOrderBy("dimension3", SOPPriceArangements.class.getName(), EMCQueryOrderByDirections.DESC);
            query.addOrderBy("dimension2", SOPPriceArangements.class.getName(), EMCQueryOrderByDirections.DESC);

            if (conn == null) {
                priceArangementList = util.executeGeneralSelectQuery(query, userData);
            } else {
                priceArangementList = util.exJDBCReadQuery(conn, query, userData);
            }
            int dimMatch = 0;
            int bestDimMatch = 0;
            for (SOPPriceArangements arrangement : priceArangementList) {
                if (bestMatch == null) {
                    //This should only happen for the first iteration
                    bestMatch = arrangement;

                    //Also get dimension match on first record.
                    if (util.checkObjectsEqual(arrangement.getDimension1(), dimension1)) {
                        bestDimMatch++;
                    }
                    if (util.checkObjectsEqual(arrangement.getDimension2(), dimension2)) {
                        bestDimMatch++;
                    }
                    if (util.checkObjectsEqual(arrangement.getDimension3(), dimension3)) {
                        bestDimMatch++;
                    }

                    continue;
                } else {
                    //Item
                    if (util.checkObjectsEqual(bestMatch.getItemId(), arrangement.getItemId())) {
                        //Customer.  Ship to customer takes preference over invoice customer.
                        if (util.checkObjectsEqual(bestMatch.getCustomerId(), arrangement.getCustomerId()) || (!util.checkObjectsEqual(bestMatch.getCustomerId(), customerId)) && util.checkObjectsEqual(arrangement.getCustomerId(), customerId)) {
                            //Customer Group
                            if (util.checkObjectsEqual(bestMatch.getPriceGroup(), arrangement.getPriceGroup())) {
                                //Most Dimensions
                                dimMatch = 0;
                                if (util.checkObjectsEqual(arrangement.getDimension1(), dimension1)) {
                                    dimMatch++;
                                }
                                if (util.checkObjectsEqual(arrangement.getDimension2(), dimension2)) {
                                    dimMatch++;
                                }
                                if (util.checkObjectsEqual(arrangement.getDimension3(), dimension3)) {
                                    dimMatch++;
                                }
                                //Better Match
                                if (dimMatch > bestDimMatch) {
                                    bestDimMatch = dimMatch;
                                    bestMatch = arrangement;
                                } else {
                                    if (dimMatch == bestDimMatch) {
                                        //Lasty check qty
                                        if (arrangement.getQuantity().compareTo(bestMatch.getQuantity()) > 0) {
                                            bestMatch = arrangement;
                                        }
                                    }
                                    //Every thing matches

                                }
                                if (bestDimMatch == 3 && bestMatch.getQuantity().compareTo(quantity) == 0) {
                                    break;
                                }
                            } else {
                                if (isBlank(bestMatch.getPriceGroup())) {
                                    //3 - Customer Group
                                    bestMatch = arrangement;
                                }

                            }
                        } else {
                            if (isBlank(bestMatch.getCustomerId())) {
                                //2 - Customer
                                bestMatch = arrangement;
                            }

                        }
                    } else {
                        if (isBlank(bestMatch.getItemId())) {
                            //1 - Item
                            bestMatch = arrangement;
                        }

                    }
                }
            }
        }

        //No Arangement Found
        if (bestMatch == null) {
            //logMessage(Level.WARNING, "No price arrangement record found.", userData);
            if (param == null) {
                param = parametersBean.getParameterRecord(userData);
            }
            BigDecimal standardPrice;
            if (param == null) {
                Logger.getLogger("emc").log(Level.SEVERE, "No SOP Parameters set up.", userData);
                standardPrice = BigDecimal.ZERO;
            } else {
                if (!param.isAllowStandardPrice()) {
                    standardPrice = BigDecimal.ZERO;
                } else {
                    if (item == null) {
                        String[] i = itemReferenceBean.checkItemReference(itemId, userData);
                        if (i == null) {
                            return BigDecimal.ZERO;
                        }
                        item = itemMasterBean.findItem(i[0], userData);
                    }
                    if (!isBlank(item.getCostingGroup())) {
                        if (costingGroup == null) {
                            query = new EMCQuery(enumQueryTypes.SELECT, InventoryCostingGroup.class);
                            query.addAnd("costingGroupId", item.getCostingGroup());
                            costingGroup = (InventoryCostingGroup) util.executeSingleResultQuery(query, userData);
                        }
                        if (CostLevel.DIMENSION.equals(CostLevel.fromString(costingGroup.getCostLevel()))) {
                            if (combination == null) {
                                query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionCombinations.class);
                                query.addAnd("itemId", item.getItemId());
                                query.addAnd("active", true);
                                if (!isBlank(dimension1)) {
                                    query.addAnd("dimension1Id", dimension1);
                                }
                                if (!isBlank(dimension2)) {
                                    query.addAnd("dimension2Id", dimension2);
                                }
                                if (!isBlank(dimension3)) {
                                    query.addAnd("dimension3Id", dimension3);
                                }

                                combination = (InventoryItemDimensionCombinations) util.executeSingleResultQuery(query, userData);
                            }

                            //If multiple results are found for single result query, do not return a price.
                            if (combination == null) {
                                standardPrice = BigDecimal.ZERO;
                            } else {
                                standardPrice = util.getBigDecimal(combination.getSellingPrice());
                            }
                        } else {
                            standardPrice = util.getBigDecimal(item.getSalesSellingPrice());
                        }
                    } else {
                        standardPrice = util.getBigDecimal(item.getSalesSellingPrice());
                    }
                }

            }
            return standardPrice;
        } else {
            return bestMatch.getPrice();
        }
    }

    @Override
    public BigDecimal findItemPrice(String customerId, String itemId, String dimension1, String dimension2, String dimension3, Date lineDate, BigDecimal quantity, EMCUserData userData) throws EMCEntityBeanException {
        return findItemPrice(null, null, null, null, null, null, null, customerId, itemId, dimension1, dimension2, dimension3, lineDate, quantity, userData);
    }

    @Override
    public boolean importPriceArangements(List<Object> theData, EMCUserData userData) throws EMCEntityBeanException {
        //remove CMD
        theData.remove(0);
        String theLine;
        String[] theLineArray;
        EMCQuery query;
        SOPPriceArangements arangement;
        SOPPriceArangements sqlArangement;
        for (Object o : theData) {
            theLine = (String) o;
            theLine = theLine.replaceAll("\"", "");
            theLineArray = theLine.split(",");
            arangement = new SOPPriceArangements();
            arangement.setCustomerType(theLineArray[0]);
            if (arangement.getCustomerType().equals(PricingCustomerType.ALL.toString())) {
                //Set nothing
            } else {
                if (arangement.getCustomerType().equals(PricingCustomerType.CUSTOMER.toString())) {
                    arangement.setCustomerId(theLineArray[1]);
                } else {
                    if (arangement.getCustomerType().equals(PricingCustomerType.GROUP.toString())) {
                        arangement.setPriceGroup(theLineArray[1]);
                    }

                }

            }
            arangement.setItemId(theLineArray[2]);
            arangement.setDimension1(theLineArray[3]);
            arangement.setDimension3(theLineArray[4]);
            arangement.setDimension2(theLineArray[5]);
            arangement.setFromDate(Functions.string2Date(theLineArray[6], "yyyy/MM/dd"));
            arangement.setToDate(Functions.string2Date(theLineArray[7], "yyyy/MM/dd"));
            arangement.setQuantity(new BigDecimal(theLineArray[8]));
            arangement.setPrice(new BigDecimal(theLineArray[9]));
            //Check if similar exists
            query = new EMCQuery(enumQueryTypes.SELECT, SOPPriceArangements.class);
            query.addAnd("customerType", arangement.getCustomerType());
            query.addAnd("priceGroup", isBlank(arangement.getPriceGroup()) ? null : arangement.getPriceGroup());
            query.addAnd("customerId", isBlank(arangement.getCustomerId()) ? null : arangement.getCustomerId());
            query.addAnd("itemId", isBlank(arangement.getItemId()) ? null : arangement.getItemId());
            query.addAnd("dimension1", isBlank(arangement.getDimension1()) ? null : arangement.getDimension1());
            query.addAnd("dimension2", isBlank(arangement.getDimension2()) ? null : arangement.getDimension2());
            query.addAnd("dimension3", isBlank(arangement.getDimension3()) ? null : arangement.getDimension3());
            query.addAnd("quantity", arangement.getQuantity());
            sqlArangement = (SOPPriceArangements) util.executeSingleResultQuery(query, userData);
            if (sqlArangement != null) {
                //Update existing
                util.copyFields(arangement, sqlArangement, userData);
                this.update(sqlArangement, userData);
            } else {
                //Inser New
                this.insert(arangement, userData);
            }
        }
        return true;
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        SOPPriceArangements record = (SOPPriceArangements) iobject;

        populateDimensionSortCodes(record, userData);
        auditTrailBean.logChanges(SOPPriceAuditTrailType.PRICE_LIST_INSERT, record, null, null, null, userData);

        //Set up unique constraint
        String priceGroup = record.getPriceGroup();
        String customerId = record.getCustomerId();
        String itemId = record.getItemId();
        String dimension1 = record.getDimension1();
        String dimension2 = record.getDimension2();
        String dimension3 = record.getDimension3();
        Date fromDate = record.getFromDate();
        Date toDate = record.getToDate();
        BigDecimal quantity = record.getQuantity();

        record.setUniqueFields((priceGroup == null ? "" : priceGroup) + (customerId == null ? "" : customerId) + (itemId == null ? "" : itemId) + (dimension1 == null ? "" : dimension1) + (dimension2 == null ? "" : dimension2) + (dimension3 == null ? "" : dimension3) + (fromDate == null ? "" : fromDate) + (toDate == null ? "" : toDate) + (quantity == null ? "" : quantity));
        return super.insert(iobject, userData);
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        SOPPriceArangements record = (SOPPriceArangements) uobject;
        populateDimensionSortCodes(record, userData);


        //Set up unique constraint
        String priceGroup = record.getPriceGroup();
        String customerId = record.getCustomerId();
        String itemId = record.getItemId();
        String dimension1 = record.getDimension1();
        String dimension2 = record.getDimension2();
        String dimension3 = record.getDimension3();
        Date fromDate = record.getFromDate();
        Date toDate = record.getToDate();
        BigDecimal quantity = record.getQuantity();

        record.setUniqueFields((priceGroup == null ? "" : priceGroup) + (customerId == null ? "" : customerId) + (itemId == null ? "" : itemId) + (dimension1 == null ? "" : dimension1) + (dimension2 == null ? "" : dimension2) + (dimension3 == null ? "" : dimension3) + (fromDate == null ? "" : fromDate) + (toDate == null ? "" : toDate) + (quantity == null ? "" : quantity));


        SOPPriceArangements persisted = (SOPPriceArangements) util.findDetachedPersisted(record, userData);
        if (persisted.getPrice().compareTo(record.getPrice()) != 0) {
            auditTrailBean.logChanges(SOPPriceAuditTrailType.PRICE_LIST_UPDATE, record, persisted, null, null, userData);
        }

        return super.update(uobject, userData);
    }

    @Override
    public Object delete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        SOPPriceArangements record = (SOPPriceArangements) dobject;
        auditTrailBean.logChanges(SOPPriceAuditTrailType.PRICE_LIST_DELETE, record, null, null, null, userData);
        return super.delete(dobject, userData);
    }

    private void populateDimensionSortCodes(SOPPriceArangements record, EMCUserData userData) {
        if (!isBlank(record.getDimension1())) {
            InventoryDimension1 dim1 = dim1Bean.getDimension1(record.getDimension1(), userData);
            if (dim1 != null) {
                record.setDimension1SortCode(dim1.getSortCode());
            } else {
                record.setDimension1SortCode(0);
            }
        } else {
            record.setDimension1SortCode(0);
        }

        if (!isBlank(record.getDimension2())) {
            InventoryDimension2 dim2 = dim2Bean.findDimension2(record.getDimension2(), userData);
            if (dim2 != null) {
                record.setDimension2SortCode(dim2.getSortCode());
            } else {
                record.setDimension2SortCode(0);
            }
        } else {
            record.setDimension2SortCode(0);
        }

        if (!isBlank(record.getDimension3())) {
            InventoryDimension3 dim3 = dim3Bean.getDimension3Record(record.getDimension3(), userData);
            if (dim3 != null) {
                record.setDimension3SortCode(dim3.getSortCode());
            } else {
                record.setDimension3SortCode(0);
            }
        } else {
            record.setDimension3SortCode(0);
        }
    }
}
