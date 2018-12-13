package emc.bus.pop.pricearrangements;

import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.inventory.dimensions.InventoryDimension1Local;
import emc.bus.inventory.dimensions.InventoryDimension2Local;
import emc.bus.inventory.dimensions.InventoryDimension3Local;
import emc.bus.inventory.logic.InventoryItemDimensionCombinationLogicLocal;
import emc.bus.pop.POPParametersLocal;
import emc.bus.pop.POPSupplierLocal;
import emc.entity.inventory.InventoryCostingGroup;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.entity.pop.POPParameters;
import emc.entity.pop.POPSuppliers;
import emc.entity.pop.pricearrangements.POPPriceArrangements;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.emcquery.EMCQueryOrderByDirections;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.costing.CostLevel;
import emc.enums.pop.pricearrangement.PricingSupplierType;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.messages.ServerPOPMessageEnum;
import emc.tables.EMCTable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/** 
 * 
 * @author riaan
 */
@Stateless
public class POPPriceArrangementsBean extends EMCEntityBean implements POPPriceArrangementsLocal {

    @EJB
    private POPParametersLocal parametersBean;
    @EJB
    private POPSupplierLocal supplierBean;
    @EJB
    private InventoryReferenceLocal itemReferenceBean;
    @EJB
    private InventoryItemMasterLocal itemMasterBean;
    @EJB
    private InventoryItemDimensionCombinationLogicLocal dimensionCombinationLogicBean;
    @EJB
    private InventoryDimension1Local dimension1Bean;
    @EJB
    private InventoryDimension2Local dimension2Bean;
    @EJB
    private InventoryDimension3Local dimension3Bean;

    /** Creates a new instance of POPPriceArrangementsBean. */
    public POPPriceArrangementsBean() {
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean ret = super.doInsertValidation(vobject, userData);

        if (ret) {
            POPPriceArrangements priceArrangement = (POPPriceArrangements) vobject;

            ret = ret && doSaveValidation(priceArrangement, userData);
        }

        return ret;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doUpdateValidation(vobject, userData);

        if (ret) {
            POPPriceArrangements priceArrangement = (POPPriceArrangements) vobject;

            ret = ret && doSaveValidation(priceArrangement, userData);
        }

        return ret;
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        POPPriceArrangements priceArrangement = (POPPriceArrangements) iobject;

        populateDimensionSortCodes(priceArrangement, userData);

        return super.insert(iobject, userData);
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        POPPriceArrangements priceArrangement = (POPPriceArrangements) uobject;

        //Dimensions may have changed - repopulate sort codes.
        populateDimensionSortCodes(priceArrangement, userData);

        return super.update(uobject, userData);
    }

    /**
     * Checks that the specified Price Arrangement record may be saved.  During
     * this validation values stored as blank Strings may be set to null.
     * @param arrangement Record to validate.
     * @param userData User data.
     * @return A boolean indicating whether the record may be saved.
     */
    private boolean doSaveValidation(POPPriceArrangements arrangement, EMCUserData userData) {
        if (isBlank(arrangement.getPriceGroup())) {
            arrangement.setPriceGroup(null);
        }
        if (isBlank(arrangement.getSupplierId())) {
            arrangement.setSupplierId(null);
        }
        if (isBlank(arrangement.getItemId())) {
            arrangement.setItemId(null);
        }
        if (isBlank(arrangement.getDimension1())) {
            arrangement.setDimension1(null);
        }
        if (isBlank(arrangement.getDimension2())) {
            arrangement.setDimension2(null);
        }
        if (isBlank(arrangement.getDimension3())) {
            arrangement.setDimension3(null);
        }
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPriceArrangements.class);
        query.addAnd("priceGroup", arrangement.getPriceGroup());
        query.addAnd("supplierId", arrangement.getSupplierId());
        query.addAnd("itemId", arrangement.getItemId());
        query.addAnd("dimension1", arrangement.getDimension1());
        query.addAnd("dimension2", arrangement.getDimension2());
        query.addAnd("dimension3", arrangement.getDimension3());
        query.openAndConditionBracket();
        query.openConditionBracket(EMCQueryBracketConditions.NONE);
        query.addAnd("fromDate", Functions.date2SQLString(arrangement.getFromDate()), EMCQueryConditions.GREATER_THAN_EQ);
        query.addAnd("fromDate", Functions.date2SQLString(arrangement.getToDate()), EMCQueryConditions.LESS_THAN_EQ);
        query.closeConditionBracket();
        query.openConditionBracket(EMCQueryBracketConditions.OR);
        query.addAnd("toDate", Functions.date2SQLString(arrangement.getFromDate()), EMCQueryConditions.GREATER_THAN_EQ);
        query.addAnd("toDate", Functions.date2SQLString(arrangement.getToDate()), EMCQueryConditions.LESS_THAN_EQ);
        query.closeConditionBracket();
        query.openConditionBracket(EMCQueryBracketConditions.OR);
        query.addAnd("fromDate", Functions.date2SQLString(arrangement.getFromDate()), EMCQueryConditions.LESS_THAN_EQ);
        query.addAnd("toDate", Functions.date2SQLString(arrangement.getToDate()), EMCQueryConditions.GREATER_THAN_EQ);
        query.closeConditionBracket();
        query.closeConditionBracket();
        query.addAnd("quantity", arrangement.getQuantity());
        query.addAnd("recordID", arrangement.getRecordID(), EMCQueryConditions.NOT);
        if (util.exists(query, userData)) {
            logMessage(Level.SEVERE, ServerPOPMessageEnum.ARRANGEMENT_EXISTS, userData);
            return false;
        }
        return true;

    }

    /**
     * Populates the sort code fields on the specified record.
     * @param record Record to populate.
     * @param userData User data.
     */
    private void populateDimensionSortCodes(POPPriceArrangements record, EMCUserData userData) {
        if (!isBlank(record.getDimension1())) {
            InventoryDimension1 dim1 = dimension1Bean.getDimension1(record.getDimension1(), userData);
            if (dim1 != null) {
                record.setDimension1SortCode(dim1.getSortCode());
            } else {
                record.setDimension1SortCode(0);
            }
        } else {
            record.setDimension1SortCode(0);
        }

        if (!isBlank(record.getDimension2())) {
            InventoryDimension2 dim2 = dimension2Bean.findDimension2(record.getDimension2(), userData);
            if (dim2 != null) {
                record.setDimension2SortCode(dim2.getSortCode());
            } else {
                record.setDimension2SortCode(0);
            }
        } else {
            record.setDimension2SortCode(0);
        }

        if (!isBlank(record.getDimension3())) {
            InventoryDimension3 dim3 = dimension3Bean.getDimension3Record(record.getDimension3(), userData);
            if (dim3 != null) {
                record.setDimension3SortCode(dim3.getSortCode());
            } else {
                record.setDimension3SortCode(0);
            }
        } else {
            record.setDimension3SortCode(0);
        }
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);

        if (ret) {
            POPPriceArrangements priceArrangement = (POPPriceArrangements) theRecord;
            if (fieldNameToValidate.equals("dimension1") || fieldNameToValidate.equals("dimension2") || fieldNameToValidate.equals("dimension3")) {
                if (!isBlank(priceArrangement.getItemId())) {
                    dimensionCombinationLogicBean.validateDimensionValues(priceArrangement, userData);
                }
            }
        }

        return ret;
    }

    @Override
    public BigDecimal findItemPrice(String itemId, String dimension1, String dimension2, String dimension3, Date lineDate,
            BigDecimal quantity, EMCUserData userData) throws EMCEntityBeanException {
        return findItemPrice(null, null, null, null, null, null, null, null, itemId, dimension1, dimension2, dimension3, lineDate, quantity, userData);
    }

    @Override
    public BigDecimal findItemPrice(String supplierId, String itemId, String dimension1, String dimension2, String dimension3, Date lineDate,
            BigDecimal quantity, EMCUserData userData) throws EMCEntityBeanException {
        return findItemPrice(null, null, null, null, null, null, null, supplierId, itemId, dimension1, dimension2, dimension3, lineDate, quantity, userData);
    }

    @Override
    public BigDecimal findItemPrice(Connection conn, InventoryItemMaster item, InventoryItemDimensionCombinations combination, InventoryCostingGroup costingGroup,
            POPSuppliers supplier, POPParameters param, String priceGroup, String supplierId, String itemId, String dimension1, String dimension2, String dimension3,
            Date lineDate, BigDecimal quantity, EMCUserData userData) throws EMCEntityBeanException {

        if (param == null) {
            param = parametersBean.getPOPParameters(userData);

            if (param == null) {
                logMessage(Level.SEVERE, "No POP Parameters found.", userData);
                return BigDecimal.ZERO;
            }
        }
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPriceArrangements.class);
        //Customer
        query.openConditionBracket(EMCQueryBracketConditions.NONE);
        query.openConditionBracket(EMCQueryBracketConditions.NONE);
        if (!isBlank(priceGroup)) {
            query.addAnd("priceGroup", priceGroup);
            query.addAnd("supplierId", null);
        } else {
            if (isBlank(supplierId)) {
                query.addAnd("supplierId", null);
                query.addAnd("priceGroup", null);
                supplierId = null;
            } else {
                if (supplier == null) {
                    supplier = supplierBean.getSupplier(supplierId, userData);
                }

                if (supplier == null) {
                    logMessage(Level.SEVERE, "Customer not found.", userData);
                    return BigDecimal.ZERO;
                }

                query.openConditionBracket(EMCQueryBracketConditions.NONE);
                query.addOr("supplierId", supplierId);
                query.addOr("supplierId", null);
                query.closeConditionBracket();
                priceGroup = supplier.getPriceGroup();
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
        query.addOr("supplierType", PricingSupplierType.ALL.toString());
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

        List<POPPriceArrangements> priceArangementList;
        POPPriceArrangements bestMatch = null;
        if (param.isUseLowestPrice()) {
            //Use lowest price
            query.addOrderBy("price");
            if (conn == null) {
                priceArangementList = util.executeGeneralSelectQuery(query, userData);
            } else {
                priceArangementList = util.exJDBCReadQuery(conn, query, userData);
            }
            if (priceArangementList != null && !priceArangementList.isEmpty()) {
                if (item == null) {
                    String[] i = itemReferenceBean.checkItemReference(itemId, userData);
                    if (i == null) {
                        return BigDecimal.ZERO;
                    }
                    item = itemMasterBean.findItem(i[0], userData);
                }
                if (priceArangementList.get(0).getPrice().compareTo(new BigDecimal(item.getPurchasePrice())) < 0) {
                    bestMatch = priceArangementList.get(0);
                } else {
                    //If item price is lower than lowest price on price file, use item price.
                    return new BigDecimal(item.getPurchasePrice());
                }
            }
        } else {
            //Use best match
            query.addOrderBy("itemId", POPPriceArrangements.class.getName(), EMCQueryOrderByDirections.DESC);
            query.addOrderBy("supplierId", POPPriceArrangements.class.getName(), EMCQueryOrderByDirections.DESC);
            query.addOrderBy("priceGroup", POPPriceArrangements.class.getName(), EMCQueryOrderByDirections.DESC);
            query.addOrderBy("dimension1", POPPriceArrangements.class.getName(), EMCQueryOrderByDirections.DESC);
            query.addOrderBy("dimension3", POPPriceArrangements.class.getName(), EMCQueryOrderByDirections.DESC);
            query.addOrderBy("dimension2", POPPriceArrangements.class.getName(), EMCQueryOrderByDirections.DESC);

            if (conn == null) {
                priceArangementList = util.executeGeneralSelectQuery(query, userData);
            } else {
                priceArangementList = util.exJDBCReadQuery(conn, query, userData);
            }
            int dimMatch = 0;
            int bestDimMatch = 0;
            for (POPPriceArrangements arrangement : priceArangementList) {
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
                        if (util.checkObjectsEqual(bestMatch.getSupplierId(), arrangement.getSupplierId()) || (!util.checkObjectsEqual(bestMatch.getSupplierId(), supplierId)) && util.checkObjectsEqual(arrangement.getSupplierId(), supplierId)) {
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
                                    //Everything matches
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
                            if (isBlank(bestMatch.getSupplierId())) {
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

        //No Arrangement Found
        if (bestMatch == null) {
            //logMessage(Level.WARNING, "No price arrangement record found.", userData);
            if (param == null) {
                param = parametersBean.getPOPParameters(userData);
            }
            BigDecimal standardPrice;
            if (param == null) {
                logMessage(Level.SEVERE, ServerPOPMessageEnum.NO_POP_PARAMS, userData);
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
                                standardPrice = util.getBigDecimal(combination.getPurchasePrice());
                            }
                        } else {
                            standardPrice = util.getBigDecimal(item.getPurchasePrice());
                        }
                    } else {
                        standardPrice = util.getBigDecimal(item.getPurchasePrice());
                    }
                }

            }
            return standardPrice;
        } else {
            return bestMatch.getPrice();
        }
    }
}