/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.safetystock;

import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.logic.InventoryItemDimensionCombinationLogicLocal;
import emc.bus.inventory.safetystock.resources.SafetyStockMap;
import emc.bus.inventory.safetystockgenerationrules.InventorySafetyStockGenerationRulesLocal;
import emc.constants.systemConstants;
import emc.entity.gl.GLFinancialPeriods;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventorySafetyStock;
import emc.entity.inventory.InventorySafetyStockGenerationRules;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.entity.sop.SOPCustomers;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.costing.CostLevel;
import emc.enums.inventory.safetystock.SafetyStockCustomerType;
import emc.enums.inventory.safetystock.SafetyStockGenerationRules;
import emc.enums.inventory.safetystock.SafetyStockGranularity;
import emc.enums.inventory.safetystock.SafetyStockItemType;
import emc.enums.inventory.safetystock.SafetyStockType;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.messages.ServerInventoryMessageEnum;
import emc.tables.EMCTable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class InventorySafetyStockBean extends EMCEntityBean implements InventorySafetyStockLocal {

    @EJB
    private InventoryItemDimensionCombinationLogicLocal dimensionCombinationLogicBean;
    @EJB
    private InventoryItemMasterLocal itemBean;
    @EJB
    private InventorySafetyStockGenerationRulesLocal rulesBean;

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);

        if (ret) {
            InventorySafetyStock safetyStock = (InventorySafetyStock) theRecord;
            if (fieldNameToValidate.equals("dimension1") || fieldNameToValidate.equals("dimension2") || fieldNameToValidate.equals("dimension3")) {
                if (!isBlank(safetyStock.getItemId())) {
                    ret = dimensionCombinationLogicBean.validateDimensionValues(safetyStock.getItemId(), safetyStock.getDimension1(), safetyStock.getDimension2(), safetyStock.getDimension3(), userData);
                }
            }
        }
        return ret;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean valid = super.doUpdateValidation(vobject, userData);
        if (valid) {
            InventorySafetyStock safetyStock = (InventorySafetyStock) vobject;
            safetyStock.setGenerated(false);
            valid = doSaveValidation(safetyStock, false, userData);
        }
        return valid;
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean valid = super.doInsertValidation(vobject, userData);
        if (valid) {
            InventorySafetyStock safetyStock = (InventorySafetyStock) vobject;
            try {
                valid = doSaveValidation(safetyStock, false, userData);
            } catch (EMCEntityBeanException ex) {
                //Exception will only be thrown from Genereation
                valid = false;
            }
        }
        return valid;
    }

    private boolean doSaveValidation(InventorySafetyStock safetyStock, boolean fromGeneration, EMCUserData userData) throws EMCEntityBeanException {
        if (!fromGeneration) {
            if (safetyStock.getCustomerType().equals(SafetyStockCustomerType.ALL.toString())) {
                if (!isBlank(safetyStock.getCustomerId())) {
                    logMessage(Level.SEVERE, ServerInventoryMessageEnum.SAFETY_STOCK_CUSTOMER_NOT_REQUIRED, userData, safetyStock.getCustomerType());
                    return false;
                }
            } else {
                if (isBlank(safetyStock.getCustomerId())) {
                    logMessage(Level.SEVERE, ServerInventoryMessageEnum.SAFETY_STOCK_CUSTOMER_REQUIRED, userData, safetyStock.getCustomerType());
                    return false;
                }
            }
            if (safetyStock.getItemType().equals(SafetyStockItemType.ALL.toString())) {
                if (!isBlank(safetyStock.getItemId())) {
                    logMessage(Level.SEVERE, ServerInventoryMessageEnum.SAFETY_STOCK_ITEM_NOT_REQUIRED, userData, safetyStock.getItemType());
                    return false;
                }
            } else {
                if (isBlank(safetyStock.getItemId())) {
                    logMessage(Level.SEVERE, ServerInventoryMessageEnum.SAFETY_STOCK_ITEM_REQUIRED, userData, safetyStock.getItemType());
                    return false;
                }
            }
        }
        if (isBlank(safetyStock.getCustomerId())) {
            safetyStock.setCustomerId(null);
        }
        if (isBlank(safetyStock.getItemId())) {
            safetyStock.setItemId(null);
        }
        if (isBlank(safetyStock.getDimension1())) {
            safetyStock.setDimension1(null);
        }
        if (isBlank(safetyStock.getDimension2())) {
            safetyStock.setDimension2(null);
        }
        if (isBlank(safetyStock.getDimension3())) {
            safetyStock.setDimension3(null);
        }
        if (isBlank(safetyStock.getSerialNo())) {
            safetyStock.setSerialNo(null);
        }

        if (SafetyStockType.SS.toString().equals(safetyStock.getSafetyType())) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventorySafetyStock.class);
            query.addAnd("customerId", safetyStock.getCustomerId());
            query.addAnd("itemId", safetyStock.getItemId());
            query.addAnd("dimension1", safetyStock.getDimension1());
            query.addAnd("dimension2", safetyStock.getDimension2());
            query.addAnd("dimension3", safetyStock.getDimension3());
            query.addAnd("serialNo", safetyStock.getSerialNo());
            query.openAndConditionBracket();
            query.openConditionBracket(EMCQueryBracketConditions.NONE);
            query.addAnd("fromDate", Functions.date2SQLString(safetyStock.getFromDate()), EMCQueryConditions.GREATER_THAN_EQ);
            query.addAnd("fromDate", Functions.date2SQLString(safetyStock.getToDate()), EMCQueryConditions.LESS_THAN_EQ);
            query.closeConditionBracket();
            query.openConditionBracket(EMCQueryBracketConditions.OR);
            query.addAnd("toDate", Functions.date2SQLString(safetyStock.getFromDate()), EMCQueryConditions.GREATER_THAN_EQ);
            query.addAnd("toDate", Functions.date2SQLString(safetyStock.getToDate()), EMCQueryConditions.LESS_THAN_EQ);
            query.closeConditionBracket();
            query.openConditionBracket(EMCQueryBracketConditions.OR);
            query.addAnd("fromDate", Functions.date2SQLString(safetyStock.getFromDate()), EMCQueryConditions.LESS_THAN_EQ);
            query.addAnd("toDate", Functions.date2SQLString(safetyStock.getToDate()), EMCQueryConditions.GREATER_THAN_EQ);
            query.closeConditionBracket();
            query.closeConditionBracket();
            query.addAnd("recordID", safetyStock.getRecordID(), EMCQueryConditions.NOT);
            query.addAnd("safetyType", SafetyStockType.MPS.toString(), EMCQueryConditions.NOT);
            if (fromGeneration) {
                List<InventorySafetyStock> records = util.executeGeneralSelectQuery(query, userData);
                if (!records.isEmpty()) {
                    for (InventorySafetyStock ss : records) {
                        if (ss.isGenerated()) {
                            logMessage(Level.SEVERE, ServerInventoryMessageEnum.SAFETY_STOCK_RECORD_EXISTS, userData);
                            Logger.getLogger("emc").log(Level.SEVERE, "Customer Type: " + safetyStock.getCustomerType(), userData);
                            Logger.getLogger("emc").log(Level.SEVERE, "Customer: " + safetyStock.getCustomerId(), userData);
                            Logger.getLogger("emc").log(Level.SEVERE, "Item Type: " + safetyStock.getItemType(), userData);
                            Logger.getLogger("emc").log(Level.SEVERE, "Item: " + safetyStock.getItemId(), userData);
                            Logger.getLogger("emc").log(Level.SEVERE, "Config: " + safetyStock.getDimension1(), userData);
                            Logger.getLogger("emc").log(Level.SEVERE, "Color: " + safetyStock.getDimension3(), userData);
                            Logger.getLogger("emc").log(Level.SEVERE, "Size: " + safetyStock.getDimension2(), userData);
                            Logger.getLogger("emc").log(Level.SEVERE, "From: " + Functions.date2String(safetyStock.getFromDate()));
                            Logger.getLogger("emc").log(Level.SEVERE, "To: " + Functions.date2String(safetyStock.getToDate()));
                            Logger.getLogger("emc").log(Level.SEVERE, "Quantity: " + safetyStock.getQuantity(), userData);
                            throw new EMCEntityBeanException("Failed to insert Safety Stock Record.");
                        }
                    }
                    return false;
                }
            } else if (util.exists(query, userData)) {
                logMessage(Level.SEVERE, ServerInventoryMessageEnum.SAFETY_STOCK_RECORD_EXISTS, userData);
                Logger.getLogger("emc").log(Level.SEVERE, "Customer Type: " + safetyStock.getCustomerType(), userData);
                Logger.getLogger("emc").log(Level.SEVERE, "Customer: " + safetyStock.getCustomerId(), userData);
                Logger.getLogger("emc").log(Level.SEVERE, "Item Type: " + safetyStock.getItemType(), userData);
                Logger.getLogger("emc").log(Level.SEVERE, "Item: " + safetyStock.getItemId(), userData);
                Logger.getLogger("emc").log(Level.SEVERE, "Config: " + safetyStock.getDimension1(), userData);
                Logger.getLogger("emc").log(Level.SEVERE, "Color: " + safetyStock.getDimension3(), userData);
                Logger.getLogger("emc").log(Level.SEVERE, "Size: " + safetyStock.getDimension2(), userData);
                Logger.getLogger("emc").log(Level.SEVERE, "From: " + Functions.date2String(safetyStock.getFromDate()));
                Logger.getLogger("emc").log(Level.SEVERE, "To: " + Functions.date2String(safetyStock.getToDate()));
                Logger.getLogger("emc").log(Level.SEVERE, "Quantity: " + safetyStock.getQuantity(), userData);
                return false;
            }
        }

        StringBuilder uniqueBuilder = new StringBuilder();
        uniqueBuilder.append(safetyStock.getCustomerId());
        uniqueBuilder.append(safetyStock.getItemId());
        uniqueBuilder.append(safetyStock.getDimension1());
        uniqueBuilder.append(safetyStock.getDimension2());
        uniqueBuilder.append(safetyStock.getDimension3());
        uniqueBuilder.append(safetyStock.getSerialNo());
        uniqueBuilder.append(safetyStock.getFromDate());
        uniqueBuilder.append(safetyStock.getToDate());
        uniqueBuilder.append(safetyStock.getSafetyType());
        if (SafetyStockType.MPS.toString().equals(safetyStock.getSafetyType())) {
            uniqueBuilder.append(System.nanoTime());
        }
        safetyStock.setUniqueKey(uniqueBuilder.toString());

        return true;
    }

    @Override
    public boolean generateSafetyStock(InventorySafetyStockGenerationRules rules, EMCUserData userData) throws EMCEntityBeanException {
        rules = validateRules(rules, userData);
//        System.out.println("In Generate.");
        //Claculate From To Dates
        List<Date[]> dateList = calculateDate(rules, userData);
        if (dateList.isEmpty()) {
            logMessage(Level.SEVERE, ServerInventoryMessageEnum.SAFETY_STOCK_FAILED_TO_POPULATE_DATES, userData);
            throw new EMCEntityBeanException("Failed to populate date list of safety stock calculation.");
        }
        //Delete Current Data
        clearSafetyStockData(rules, dateList.get(0)[0], dateList.get(dateList.size() - 1)[1], false, null, SafetyStockType.SS.toString(), userData);

        //Check Safety Stock Source
        List<Object[]> safetyStockSource;

        safetyStockSource = new ArrayList<Object[]>();

        SafetyStockMap safetyMap = new SafetyStockMap();
        InventorySafetyStock safetyStock;
        EMCQuery query;
        Date[] dateRange;
        Map<String, String> kimblingMap = new HashMap<String, String>();
        for (Object[] source : safetyStockSource) {
            System.out.println("Phase 1/2 - Generating Safety Stock: " + ((new Integer(safetyStockSource.indexOf(source)).doubleValue() / new Integer(safetyStockSource.size()).doubleValue()) * 100d));
            //Check Serial Number for Customer
            String serialNo = (String) source[4];
            if (itemBean.isKimblingActive((String) source[0], userData)) {
                serialNo = kimblingMap.get((String) source[4]);
                if (isBlank(serialNo)) {
                    query = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class.getName());
                    query.addAnd("customerId", (String) source[4]);
                    query.addField("requireKimbling");
                    query.addField("useShipToKimble");
                    query.addField("customerId");
                    query.addField("invoiceToCustomer");
                    Object[] cust = (Object[]) util.executeSingleResultQuery(query, userData);
                    if (cust != null && cust.length == 4) {
                        if ((Boolean) cust[0]) {
                            if ((Boolean) cust[1]) {
                                serialNo = (String) cust[2];
                            } else {
                                serialNo = (String) cust[3];
                            }
                        }
                    }
                    kimblingMap.put((String) source[4], serialNo);
                }
            } else {
                serialNo = "";
            }
            //Find Start date of bin
            dateRange = findDateRange((Date) source[5], dateList, userData);
            if (dateRange == null) {
                throw new EMCEntityBeanException("Failed to find start date in date list for safety stock calculation.");
            }
            //Fetch Safety Stock
            safetyStock = safetyMap.get((String) source[0], (String) source[1], (String) source[2], (String) source[3], serialNo, dateRange[0]);
            //Create if not found
            if (safetyStock == null) {
                safetyStock = new InventorySafetyStock();
                if (isBlank((String) source[4])) {
                    safetyStock.setCustomerType(SafetyStockCustomerType.ALL.toString());
                } else {
                    safetyStock.setCustomerType(SafetyStockCustomerType.CUSTOMER.toString());
                    safetyStock.setCustomerId((String) source[4]);
                }
                if (isBlank((String) source[0])) {
                    safetyStock.setItemType(SafetyStockItemType.ALL.toString());
                } else {
                    safetyStock.setItemType(SafetyStockItemType.ITEM.toString());
                    safetyStock.setItemId((String) source[0]);
                }
                safetyStock.setDimension1((String) source[1]);
                safetyStock.setDimension2((String) source[2]);
                safetyStock.setDimension3((String) source[3]);
                safetyStock.setSerialNo(serialNo);
                safetyStock.setFromDate(dateRange[0]);
                safetyStock.setToDate(dateRange[1]);
                safetyStock.setGenerated(true);
                safetyMap.put((String) source[0], (String) source[1], (String) source[2], (String) source[3], serialNo, dateRange[0], safetyStock);
            }
            safetyStock.setQuantity(safetyStock.getQuantity().add(source[6] instanceof BigDecimal ? (BigDecimal) source[6] : util.getBigDecimal((Double) source[6])));
        }

        util.detachEntities(userData);

        List<List<InventorySafetyStock>> safetyMapValues = safetyMap.getValuesByDate();
        boolean logManualLineMessage = false;
        for (List<InventorySafetyStock> safteyStockList : safetyMapValues) {
            System.out.println("Phase 2/2 - Persisting Safety Stock: " + ((new Integer(safetyMapValues.indexOf(safteyStockList)).doubleValue() / new Integer(safetyMapValues.size()).doubleValue()) * 100d));
            if (!safteyStockList.isEmpty()) {
                //Validate Dates
                safteyStockList = validateSafetyStockDate(safteyStockList, dateList, userData);
                //Honer Generation Rule
                switch (SafetyStockGenerationRules.fromString(rules.getGenerationRule())) {
                    case SUM_OF_GRANULARITY:
                        honerSumOfGranularityRule(safteyStockList, rules, dateList, userData);
                        break;
                }
                //Persist
                for (InventorySafetyStock ss : safteyStockList) {
                    if (doSaveValidation(ss, true, userData)) {
                        ss.setCompanyId(userData.getCompanyId());
                        this.doInsert(ss, userData);
                    } else {
                        logManualLineMessage = true;
                    }
                }
                //Detatch
                util.flushEntity(userData);
                util.detachEntities(userData);
            }
        }
        if (logManualLineMessage) {
            logMessage(Level.WARNING, ServerInventoryMessageEnum.SAFETY_STOCK_MANUAL_LINE_FOUND, userData);
        }
        return true;
    }

    @Override
    public boolean deleteSafetyStock(InventorySafetyStockGenerationRules rules, Date deleteHistoryOlderThan, String safetyStockType, EMCUserData userData) throws EMCEntityBeanException {
        rules = validateRules(rules, userData);
//        System.out.println("In Generate.");
        //Claculate From To Dates
        List<Date[]> dateList = calculateDate(rules, userData);
        if (dateList.isEmpty()) {
            logMessage(Level.SEVERE, ServerInventoryMessageEnum.SAFETY_STOCK_FAILED_TO_POPULATE_DATES, userData);
            throw new EMCEntityBeanException("Failed to populate date list of safety stock calculation.");
        }
        //Delete Current Data
        clearSafetyStockData(rules, dateList.get(0)[0], dateList.get(dateList.size() - 1)[1], true, deleteHistoryOlderThan, safetyStockType, userData);

        return true;
    }

    private void clearSafetyStockData(InventorySafetyStockGenerationRules rules, Date from, Date to, boolean deleteManual, Date deleteHistoryOlderThan, String safetyStockType, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventorySafetyStock.class);
        query.openConditionBracket(EMCQueryBracketConditions.NONE);
        query.openConditionBracket(EMCQueryBracketConditions.NONE);
        query.addAnd("fromDate", from, EMCQueryConditions.GREATER_THAN_EQ);
        query.addAnd("toDate", to, EMCQueryConditions.LESS_THAN_EQ);
        query.closeConditionBracket();
        if (deleteHistoryOlderThan != null) {
            query.openConditionBracket(EMCQueryBracketConditions.OR);
            query.addAnd("toDate", deleteHistoryOlderThan, EMCQueryConditions.LESS_THAN_EQ);
            query.closeConditionBracket();
        }
        query.closeConditionBracket();
        if (!deleteManual) {
            query.addAnd("generated", true, InventorySafetyStock.class.getName());
        }
        //Chack Customer rule
        if (!isBlank(rules.getCustomer()) || !isBlank(rules.getCustomerGroup())) {
            query.addTableAnd(SOPCustomers.class.getName(), "customerId", InventorySafetyStock.class.getName(), "customerId");
            query.openAndConditionBracket();
            if (!isBlank(rules.getCustomer())) {
                query.addOrCommaSeperated("customerId", rules.getCustomer(), SOPCustomers.class.getName(), EMCQueryConditions.EQUALS);
            }
            if (!isBlank(rules.getCustomerGroup())) {
                query.addOrCommaSeperated("marketingGroup", rules.getCustomerGroup(), SOPCustomers.class.getName(), EMCQueryConditions.EQUALS);
            }
            query.closeConditionBracket();
        }
        //Check Item rule
        if (!isBlank(rules.getItem()) || !isBlank(rules.getItemGroup())) {
            query.addTableAnd(InventoryItemMaster.class.getName(), "itemId", InventorySafetyStock.class.getName(), "itemId");
            query.openAndConditionBracket();
            if (!isBlank(rules.getItem())) {
                query.addOrCommaSeperated("itemId", rules.getItem(), InventoryItemMaster.class.getName(), EMCQueryConditions.EQUALS);
                query.addOrCommaSeperated("itemReference", rules.getItem(), InventoryItemMaster.class.getName(), EMCQueryConditions.EQUALS);
            }
            if (!isBlank(rules.getItemGroup())) {
                query.addOrCommaSeperated("itemGroup", rules.getItemGroup(), InventoryItemMaster.class.getName(), EMCQueryConditions.EQUALS);
            }
            query.closeConditionBracket();
        }
        //Safety Stock Type
        if (!isBlank(safetyStockType)) {
            query.addAnd("safetyType", safetyStockType, InventorySafetyStock.class.getName());
        }

        List<InventorySafetyStock> safetyStockList = util.executeGeneralSelectQuery(query, userData);

        for (InventorySafetyStock ss : safetyStockList) {
            this.delete(ss, userData);
        }
    }

    private List<Date[]> calculateDate(InventorySafetyStockGenerationRules rules, EMCUserData userData) {
        List<Date[]> dateList = new ArrayList<Date[]>();
        Date[] dates;
        Calendar calcCal = dateHandler.getCalendarWithEMCSettings(userData);
        //Check Granularity Rule
        switch (SafetyStockGranularity.fromString(rules.getGranularity())) {
            case WEEK:

                for (int w = 0; w < rules.getSafetyStockHorizon(); w++) {
                    dates = new Date[2];

                    if (w == 0) {
                        //While not first day of week
                        while (calcCal.get(Calendar.DAY_OF_WEEK) != calcCal.getFirstDayOfWeek()) {
                            calcCal.add(Calendar.DATE, -1);
                        }

                        Calendar nowDate = dateHandler.getCalendarWithEMCSettings(userData);

                        if (dateHandler.compareDatesIgnoreTime(calcCal.getTime(), nowDate.getTime(), userData) <= 0) {
                            dates[0] = nowDate.getTime();
                        }
                    } else {
                        //While not first day of week
                        while (calcCal.get(Calendar.DAY_OF_WEEK) != calcCal.getFirstDayOfWeek()) {
                            calcCal.add(Calendar.DATE, 1);
                        }
                        dates[0] = calcCal.getTime();
                    }
                    //Find End of Week
                    while (calcCal.get(Calendar.DAY_OF_WEEK) != calcCal.getFirstDayOfWeek() - 1) {
                        calcCal.add(Calendar.DATE, 1);
                    }
                    dates[1] = calcCal.getTime();
                    dateList.add(dates);
                }
                break;
            case PERIOD:
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLFinancialPeriods.class);
                query.addAnd("endDate", calcCal.getTime(), EMCQueryConditions.GREATER_THAN_EQ);
                query.addOrderBy("startDate");
                query.addField("startDate");
                query.addField("endDate");
                List<Object[]> periodList = util.executeGeneralSelectQuery(query, userData);
                Object[] periodDates;
                for (int w = 0; w < rules.getSafetyStockHorizon(); w++) {
                    dates = new Date[2];
                    //Check Period Exists
                    if (periodList.isEmpty()) {
                        logMessage(Level.WARNING, ServerInventoryMessageEnum.SAFETY_STOCK_NOT_ENOUGH_FINANCIAL_PERIODS, userData);
                        break;
                    }
                    periodDates = periodList.get(w);
                    if (w == 0) {
                        //First time use current date
                        dates[0] = calcCal.getTime();
                    } else {
                        //User Period Start Date
                        dates[0] = (Date) periodDates[0];
                    }
                    //User Period End Date
                    dates[1] = (Date) periodDates[1];
                    dateList.add(dates);
                }
                break;
        }
        return dateList;
    }

    private Date[] findDateRange(Date required, List<Date[]> availableDates, EMCUserData userData) {
        for (Date[] dates : availableDates) {
            if (dateHandler.compareDatesIgnoreTime(dates[0], required, userData) <= 0 && dateHandler.compareDatesIgnoreTime(dates[1], required, userData) >= 0) {
                return dates;
            }
        }
        return null;
    }

    private void honerSumOfGranularityRule(List<InventorySafetyStock> safteyStockList, InventorySafetyStockGenerationRules rules, List<Date[]> dateRanges, EMCUserData userData) {
        InventorySafetyStock ss;
        InventorySafetyStock ssAdd;
        int datePos;
        Date uptoDate;
        for (int i = 0; i < safteyStockList.size(); i++) {
            ss = safteyStockList.get(i);
            //Check Current Week Rule
            if (!rules.isIncludeCurrentWeek()) {
                ss.setQuantity(BigDecimal.ZERO);
            }
            //Find Current Date Pos
            datePos = findDateRangePos(ss.getFromDate(), dateRanges, userData);
            //Find date to where include
            if (datePos + rules.getNumGranularityForSum() > dateRanges.size() - 1) {
                datePos = dateRanges.size() - 1;
            } else {
                datePos = datePos + rules.getNumGranularityForSum();
            }
            uptoDate = dateRanges.get(datePos)[0];
            //Loop from next element
            for (int i2 = i + 1; i2 < safteyStockList.size(); i2++) {
                ssAdd = safteyStockList.get(i2);
                //Outside rule - break
                if (ssAdd.getFromDate().compareTo(uptoDate) > 0) {
                    break;
                }
                //Update current qty
                ss.setQuantity(ss.getQuantity().add(ssAdd.getQuantity()));
            }
        }
    }

    private int findDateRangePos(Date required, List<Date[]> availableDates, EMCUserData userData) {
        Date[] dates;
        for (int i = 0; i < availableDates.size(); i++) {
            dates = availableDates.get(i);
            if (dateHandler.compareDatesIgnoreTime(dates[0], required, userData) == 0) {
                return i;
            }
        }
        return -1;
    }

    public BigDecimal getSafetyStock(Date requiredDate, String itemId, String dimension1, String dimension2, String dimension3, String serialNo, EMCUserData userData) {
        return getSafetyStock(requiredDate, itemId, dimension1, dimension2, dimension3, serialNo, null, null, null, userData).get(0);
    }

    /**
     * Returns the safty stock for the given date, item, dims. Check Safety
     * Stock Table, Combination and Item in that order. Returns a list with the
     * actual safety stock{0}, Safety Stock on item, Safety Stock on combination
     *
     * @param requiredDate
     * @param itemId
     * @param dimension1
     * @param dimension2
     * @param dimension3
     * @param serialNo
     * @param conn
     * @param item
     * @param combination
     * @param userData
     * @return Returns a list with the actual safety stock{0}, Safety Stock on
     * item, Safety Stock on combination
     */
    public List<BigDecimal> getSafetyStock(Date requiredDate, String itemId, String dimension1, String dimension2, String dimension3, String serialNo, Connection conn, BigDecimal item, BigDecimal combination, EMCUserData userData) {
        BigDecimal safetyStock = null;
        //Set Blank Values to Null
        if (isBlank(itemId)) {
            itemId = null;
        }
        if (isBlank(dimension1)) {
            dimension1 = null;
        }
        if (isBlank(dimension2)) {
            dimension2 = null;
        }
        if (isBlank(dimension3)) {
            dimension3 = null;
        }
        if (isBlank(serialNo)) {
            serialNo = null;
        }
        //Check Safety Stock Table
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventorySafetyStock.class);
        query.addAnd("itemId", itemId);
        query.addAnd("dimension1", dimension1);
        query.addAnd("dimension2", dimension2);
        query.addAnd("dimension3", dimension3);
        query.addAnd("serialNo", serialNo);
        query.addAnd("fromDate", Functions.date2SQLString(requiredDate), EMCQueryConditions.LESS_THAN_EQ);
        query.addAnd("toDate", Functions.date2SQLString(requiredDate), EMCQueryConditions.GREATER_THAN_EQ);
        query.addFieldAggregateFunction("quantity", "SUM");
        if (conn == null) {
            safetyStock = (BigDecimal) util.executeSingleResultQuery(query, userData);
        } else {
            try {
                List<Object[]> selectedList = util.exJDBCFieldReadQuery(conn, query, userData);
                if (selectedList != null && !selectedList.isEmpty()) {
                    Object[] selected = selectedList.get(0);
                    if (selected != null && selected.length >= 1) {
                        safetyStock = (BigDecimal) selected[0];
                    }
                }
            } catch (EMCEntityBeanException ex) {
                safetyStock = (BigDecimal) util.executeSingleResultQuery(query, userData);
            }
        }
        if (safetyStock == null) {
            safetyStock = BigDecimal.ZERO;
        }

        boolean checkItem = false;
        boolean checkDimensions = false;

        //Check Combinations if not found in Safety stock table
        if (safetyStock.compareTo(BigDecimal.ZERO) == 0 && checkDimensions) {
            if (combination == null || combination.compareTo(BigDecimal.ZERO) == 0) {
                query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionCombinations.class);
                query.addAnd("itemId", itemId);
                if (isBlank(dimension1)) {
                    query.addAnd("dimension1Id", systemConstants.emcNA());
                } else {
                    query.addAnd("dimension1Id", dimension1);
                }
                if (isBlank(dimension2)) {
                    query.addAnd("dimension2Id", systemConstants.emcNA());
                } else {
                    query.addAnd("dimension2Id", dimension2);
                }
                if (isBlank(dimension3)) {
                    query.addAnd("dimension3Id", systemConstants.emcNA());
                } else {
                    query.addAnd("dimension3Id", dimension3);
                }
                query.addField("safetyStock");
                if (conn == null) {
                    safetyStock = (BigDecimal) util.executeSingleResultQuery(query, userData);
                } else {
                    try {
                        List<Object[]> selectedList = util.exJDBCFieldReadQuery(conn, query, userData);
                        if (selectedList != null && !selectedList.isEmpty()) {
                            Object[] selected = selectedList.get(0);
                            if (selected != null && selected.length >= 1) {
                                safetyStock = (BigDecimal) selected[0];
                            }
                        }
                    } catch (EMCEntityBeanException ex) {
                        safetyStock = (BigDecimal) util.executeSingleResultQuery(query, userData);
                    }
                }
            } else {
                safetyStock = combination;
            }
            if (safetyStock == null) {
                safetyStock = BigDecimal.ZERO;
                checkItem = true;
            }
            combination = safetyStock;
        }
        //Check Item if not found in Combination table
        if (safetyStock.compareTo(BigDecimal.ZERO) == 0 && checkItem) {
            if (item == null || item.compareTo(BigDecimal.ZERO) == 0) {
                query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class);
                query.addAnd("itemId", itemId);
                query.addField("safetyStock");
                if (conn == null) {
                    safetyStock = (BigDecimal) util.executeSingleResultQuery(query, userData);
                } else {
                    try {
                        List<Object[]> selectedList = util.exJDBCFieldReadQuery(conn, query, userData);
                        if (selectedList != null && !selectedList.isEmpty()) {
                            Object[] selected = selectedList.get(0);
                            if (selected != null && selected.length >= 1) {
                                safetyStock = (BigDecimal) selected[0];
                            }
                        }
                    } catch (EMCEntityBeanException ex) {
                        safetyStock = (BigDecimal) util.executeSingleResultQuery(query, userData);
                    }
                }
            } else {
                safetyStock = item;
            }
            if (safetyStock == null) {
                safetyStock = BigDecimal.ZERO;
            }
            item = safetyStock;
        }
        List<BigDecimal> safetyStockList = new ArrayList<BigDecimal>();
        safetyStockList.add(safetyStock);
        safetyStockList.add(item);
        safetyStockList.add(combination);
        return safetyStockList;
    }

    private List<InventorySafetyStock> validateSafetyStockDate(List<InventorySafetyStock> safteyStockList, List<Date[]> dateList, EMCUserData userData) {
        List<InventorySafetyStock> validList = new ArrayList<InventorySafetyStock>();
        InventorySafetyStock helper = safteyStockList.get(0);
        InventorySafetyStock ss;
        int nextSS;
        for (Date[] dateRange : dateList) {
            nextSS = -1;
            for (int p = 0; p < safteyStockList.size(); p++) {
                if (dateHandler.compareDatesIgnoreTime(dateRange[0], safteyStockList.get(p).getFromDate(), userData) == 0) {
                    nextSS = p;
                    break;
                }
            }
            if (nextSS == -1) {
                ss = new InventorySafetyStock();
                ss.setCustomerType(helper.getCustomerType());
                ss.setCustomerId(helper.getCustomerId());
                ss.setItemType(helper.getItemType());
                ss.setItemId(helper.getItemId());
                ss.setDimension1(helper.getDimension1());
                ss.setDimension2(helper.getDimension2());
                ss.setDimension3(helper.getDimension3());
                ss.setSerialNo(helper.getSerialNo());
                ss.setFromDate(dateRange[0]);
                ss.setToDate(dateRange[1]);
                ss.setGenerated(helper.isGenerated());
            } else {
                ss = safteyStockList.remove(nextSS);
            }
            validList.add(ss);
        }
        return validList;
    }

    private InventorySafetyStockGenerationRules validateRules(InventorySafetyStockGenerationRules rules, EMCUserData userData) throws EMCEntityBeanException {
        InventorySafetyStockGenerationRules defaultRules = rulesBean.getRuleSet(userData);
        if (defaultRules == null) {
            throw new EMCEntityBeanException("Failed to find inventory safety stock rules.");
        }
        rules.setFromForecast(defaultRules.isFromForecast());
        rules.setSafetyStockForecastOnly(defaultRules.isSafetyStockForecastOnly());
        rules.setGranularity(defaultRules.getGranularity());
        rules.setSafetyStockHorizon(defaultRules.getSafetyStockHorizon());
        rules.setGenerationRule(defaultRules.getGenerationRule());
        rules.setRegenerateOnMPS(defaultRules.isRegenerateOnMPS());
        rules.setNumGranularityForSum(defaultRules.getNumGranularityForSum());
        rules.setIncludeCurrentWeek(defaultRules.isIncludeCurrentWeek());
        return rules;
    }
}
