/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.journals.datasource;

import emc.bus.inventory.InventoryParametersLocal;
import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.inventory.journals.InventoryJournalLinesLocal;
import emc.bus.inventory.logic.InventoryItemDimensionCombinationLogicLocal;
import emc.bus.inventory.register.stocktakeregister.InventoryStockTakeRegisterLocal;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryLocation;
import emc.entity.inventory.InventoryParameters;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.entity.inventory.dimensions.InventoryItemDimensionGroup;
import emc.entity.inventory.journals.InventoryJournalLines;
import emc.entity.inventory.journals.InventoryJournalMaster;
import emc.entity.inventory.journals.datasource.InventoryStocktakeCaptureDS;
import emc.entity.inventory.register.InventoryStocktakeRegister;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.inventorytables.InventoryReferenceTypes;
import emc.enums.inventory.journals.StockTakeVarianceType;
import emc.enums.inventory.register.RegisterFromTypeEnum;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
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

/**
 *
 * @author wikus
 */
@Stateless
public class InventoryStocktakeCaptureDSBean extends EMCEntityBean implements InventoryStockTakeCaptureDSLocal {

    @EJB
    private InventoryReferenceLocal referenceBean;
    @EJB
    private InventoryStockTakeRegisterLocal registerBean;
    @EJB
    private InventoryParametersLocal paramBean;
    @EJB
    private InventoryJournalLinesLocal journalLinesBean;
    @EJB
    private InventoryItemDimensionCombinationLogicLocal dimensionBean;

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        InventoryStocktakeCaptureDS ds = (InventoryStocktakeCaptureDS) uobject;

        if (!doSaveValidation(ds, userData)) {
            throw new EMCEntityBeanException("Save validation failed");
        }

        if (checkGeneratedFlag(ds.getJournalNumber(), ds.getJournalLineNo(), userData)) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryStocktakeRegister.class.getName());
            query.addAnd("recordID", ds.getRegisterLineRecordId());
            InventoryStocktakeRegister register = (InventoryStocktakeRegister) util.executeSingleResultQuery(query, userData);
            if (register == null) {
                throw new EMCEntityBeanException("Failed to get journal registration line.");
            }
            register.setQuantity(ds.getQuantityCounted());
            register.setOriginalCountedQty(register.getQuantity());
            registerBean.update(register, userData);
        } else {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalLines.class.getName());
            query.addAnd("journalNumber", ds.getJournalNumber());
            query.addAnd("lineNo", ds.getJournalLineNo());
            InventoryJournalLines journalLine = (InventoryJournalLines) util.executeSingleResultQuery(query, userData);
            if (journalLine == null) {
                throw new EMCEntityBeanException("Failed to get journalLine.");
            }
            journalLine.setItemId(ds.getItemId());
            journalLine.setDimension1(ds.getDimension1());
            journalLine.setDimension2(ds.getDimension2());
            journalLine.setDimension3(ds.getDimension3());
            journalLine.setWarehouse(ds.getWarehouse());
            journalLinesBean.update(journalLine, userData);

            query = new EMCQuery(enumQueryTypes.SELECT, InventoryStocktakeRegister.class.getName());
            query.addAnd("recordID", ds.getRegisterLineRecordId());
            InventoryStocktakeRegister register = (InventoryStocktakeRegister) util.executeSingleResultQuery(query, userData);
            if (register == null) {
                throw new EMCEntityBeanException("Failed to get journal registration line.");
            }
            register.setTransId(journalLine.getTransId());
            register.setBatch(ds.getBatch());
            register.setSerial(ds.getSerial());
            register.setWarehouse(ds.getWarehouse());
            register.setLocation(ds.getLocation());
            register.setPallet(ds.getPallet());
            register.setQuantity(ds.getQuantityCounted());
            register.setOriginalCountedQty(register.getQuantity());
            registerBean.update(register, userData);
        }
        return ds;
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        InventoryStocktakeCaptureDS ds = (InventoryStocktakeCaptureDS) iobject;

        if (!doSaveValidation(ds, userData)) {
            throw new EMCEntityBeanException("Save validation failed");
        }

        InventoryJournalMaster master = findJournalMaster(ds.getJournalNumber(), userData);

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalLines.class);
        query.addAnd("journalNumber", ds.getJournalNumber());
        query.addAnd("itemId", ds.getItemId());
        query.addAnd("dimension1", ds.getDimension1());
        query.addAnd("dimension2", ds.getDimension2());
        query.addAnd("dimension3", ds.getDimension3());
        query.addAnd("warehouse", ds.getWarehouse());
        InventoryJournalLines journalLine = (InventoryJournalLines) util.executeSingleResultQuery(query, userData);

        if (journalLine == null) {
            query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalLines.class);
            query.addAnd("journalNumber", ds.getJournalNumber());
            query.addFieldAggregateFunction("lineNo", "MAX");
            Double lineNo = (Double) util.executeSingleResultQuery(query, userData);

            journalLine = new InventoryJournalLines();
            journalLine.setJournalNumber(ds.getJournalNumber());
            journalLine.setLineNo(lineNo == null ? 10 : lineNo + 10);
            journalLine.setLineDate(Functions.nowDate());
            journalLine.setContraType(master.getJournalContraType());
            journalLine.setContraAccount(master.getJournalContraAccount());
            journalLine.setItemId(ds.getItemId());
            journalLine.setDimension1(ds.getDimension1());
            journalLine.setDimension2(ds.getDimension2());
            journalLine.setDimension3(ds.getDimension3());
            journalLine.setWarehouse(ds.getWarehouse());
            journalLinesBean.insert(journalLine, userData);
        }
        InventoryStocktakeRegister register = new InventoryStocktakeRegister();
        register.setMasterId(ds.getJournalNumber());
        register.setTransId(journalLine.getTransId());
        register.setBatch(ds.getBatch());
        register.setSerial(ds.getSerial());
        register.setWarehouse(ds.getWarehouse());
        register.setLocation(ds.getLocation());
        register.setPallet(ds.getPallet());
        register.setType(RegisterFromTypeEnum.JRN.toString());
        register.setQuantity(ds.getQuantityCounted());
        register.setOriginalCountedQty(register.getQuantity());
        register.setTotalQty(0);
        register.setRegisteredQty(ds.getQuantityCounted());
        register.setOnHandQty(ds.getQuantityOnHand());
        registerBean.insert(register, userData);

        ds.setJournalLineNo(journalLine.getLineNo());
        ds.setRegisterLineRecordId(register.getRecordID());
        ds.setRecordID(-1);

        return ds;
    }

    private boolean doSaveValidation(InventoryStocktakeCaptureDS ds, EMCUserData userData) {
        boolean valid = true;
        valid = valid && dimensionBean.validateActiveDimensions(ds.getItemId(), ds.getDimension1(), ds.getDimension2(), ds.getDimension3(), ds.getWarehouse(), ds.getLocation(), ds.getBatch(), ds.getSerial(), ds.getPallet(), userData);
        valid = valid && dimensionBean.validateDimensionValues(ds.getItemId(), ds.getDimension1(), ds.getDimension2(), ds.getDimension3(), userData);
        return valid;
    }

    @Override
    public Object delete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        InventoryStocktakeCaptureDS ds = (InventoryStocktakeCaptureDS) dobject;
        if (checkGeneratedFlag(ds.getJournalNumber(), ds.getJournalLineNo(), userData)) {
            logMessage(Level.SEVERE, "You are not allowed to delete generated lines.", userData);
            throw new EMCEntityBeanException("You are not allowed to delete generated lines.");
        }

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryStocktakeRegister.class.getName());
        query.addAnd("recordID", ds.getRegisterLineRecordId());
        InventoryStocktakeRegister registerLine = (InventoryStocktakeRegister) util.executeSingleResultQuery(query, userData);
        if (registerLine == null) {
            throw new EMCEntityBeanException("Failed to get journal registration line.");
        }
        registerBean.delete(registerLine, userData);

        query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalLines.class.getName());
        query.addAnd("journalNumber", ds.getJournalNumber());
        query.addAnd("recordID", ds.getJournalLineNo());
        InventoryJournalLines journalLine = (InventoryJournalLines) util.executeSingleResultQuery(query, userData);
        if (journalLine == null) {
            throw new EMCEntityBeanException("Failed to get Journal Line");
        }
        journalLinesBean.delete(journalLine, userData);

        return true;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean valid = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        InventoryStocktakeCaptureDS ds = (InventoryStocktakeCaptureDS) theRecord;
        if (valid) {
            try {
                if (ds.getJournalLineNo() != 0 && checkGeneratedFlag(ds.getJournalNumber(), ds.getJournalLineNo(), userData) && !fieldNameToValidate.equals("quantityCounted")) {
                    Logger.getLogger("emc").log(Level.SEVERE, "You are not allowed to update generated lines", userData);
                    return false;
                }
            } catch (EMCEntityBeanException ex) {
                Logger.getLogger("emc").log(Level.SEVERE, ex.getMessage(), userData);
                return false;
            }
            if (fieldNameToValidate.equals("itemReference")) {
                valid = referenceBean.doItemRefValidation(ds, userData);
            } else if (fieldNameToValidate.equals("warehouse")) {
                if (!isBlank(ds.getItemId())) {
                    boolean active = dimensionActive(ds.getItemId(), "warehouseActive", userData);
                    if (!active && !isBlank(ds.getWarehouse())) {
                        Logger.getLogger("emc").log(Level.SEVERE, "Warehouse is not active on the selected item.", userData);
                        valid = false;
                    }
                }
                if (!isBlank(ds.getLocation())) {
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryLocation.class);
                    query.addAnd("locationId", ds.getLocation());
                    query.addAnd("warehouseId", ds.getWarehouse());
                    if (!util.exists(query, userData)) {
                        Logger.getLogger("emc").log(Level.SEVERE, "The selected location is not found in the selected warhouse.", userData);
                        valid = false;
                    }
                }
            } else if (fieldNameToValidate.equals("location")) {
                if (!isBlank(ds.getItemId())) {
                    boolean active = dimensionActive(ds.getItemId(), "locationActive", userData);
                    if (!active && !isBlank(ds.getLocation())) {
                        Logger.getLogger("emc").log(Level.SEVERE, "Location is not active on the selected item.", userData);
                        valid = false;
                    }
                }
                if (!isBlank(ds.getWarehouse())) {
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryLocation.class);
                    query.addAnd("locationId", ds.getLocation());
                    query.addAnd("warehouseId", ds.getWarehouse());
                    if (!util.exists(query, userData)) {
                        Logger.getLogger("emc").log(Level.SEVERE, "The selected location is not found in the selected warhouse.", userData);
                        valid = false;
                    }
                }
            } else if (fieldNameToValidate.equals("dimension1")) {
                if (!isBlank(ds.getItemId())) {
                    boolean active = dimensionActive(ds.getItemId(), "dim1Active", userData);
                    if (!active && !isBlank(ds.getDimension1())) {
                        Logger.getLogger("emc").log(Level.SEVERE, "Config is not active on the selected item.", userData);
                        valid = false;
                    }
                }
            } else if (fieldNameToValidate.equals("dimension2")) {
                if (!isBlank(ds.getItemId())) {
                    boolean active = dimensionActive(ds.getItemId(), "dim2Active", userData);
                    if (!active && !isBlank(ds.getDimension2())) {
                        Logger.getLogger("emc").log(Level.SEVERE, "Size is not active on the selected item.", userData);
                        valid = false;
                    }
                }
            } else if (fieldNameToValidate.equals("dimension3")) {
                if (!isBlank(ds.getItemId())) {
                    boolean active = dimensionActive(ds.getItemId(), "dim3Active", userData);
                    if (!active && !isBlank(ds.getDimension3())) {
                        Logger.getLogger("emc").log(Level.SEVERE, "Color is not active on the selected item.", userData);
                        valid = false;
                    }
                }
            } else if (fieldNameToValidate.equals("batch")) {
                if (!isBlank(ds.getItemId())) {
                    boolean active = dimensionActive(ds.getItemId(), "batchNumberActive", userData);
                    if (!active && !isBlank(ds.getBatch())) {
                        Logger.getLogger("emc").log(Level.SEVERE, "Batch is not active on the selected item.", userData);
                        valid = false;
                    }
                }
            } else if (fieldNameToValidate.equals("serial")) {
                if (!isBlank(ds.getItemId())) {
                    boolean active = dimensionActive(ds.getItemId(), "serialNumberActive", userData);
                    if (!active && !isBlank(ds.getSerial())) {
                        Logger.getLogger("emc").log(Level.SEVERE, "Serial Number is not active on the selected item.", userData);
                        valid = false;
                    }
                }
            } else if (fieldNameToValidate.equals("pallet")) {
                if (!isBlank(ds.getItemId())) {
                    boolean active = dimensionActive(ds.getItemId(), "palletIdActive", userData);
                    if (!active && !isBlank(ds.getPallet())) {
                        Logger.getLogger("emc").log(Level.SEVERE, "Pallet is not active on the selected item.", userData);
                        valid = false;
                    }
                }
            }
        }
        if (valid) {
            return ds;
        }
        return valid;
    }

    private boolean dimensionActive(String itemId, String dimension, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionGroup.class);
        query.addTableAnd(InventoryItemMaster.class.getName(), "itemDimensionGroupId", InventoryItemDimensionGroup.class.getName(), "dimensionGroup");
        query.addAnd("itemId", itemId, InventoryItemMaster.class.getName());
        query.addField(dimension);
        Boolean active = (Boolean) util.executeSingleResultQuery(query, userData);
        if (active == null) {
            active = false;
        }
        return active;
    }

    @Override
    public Collection getDataInRange(Class theTable, EMCUserData userData, int start, int end) {
        EMCQuery query = (EMCQuery) userData.getUserData(0);
        query.addOrderBy("warehouse", InventoryJournalLines.class.getName());
        query.addOrderBy("location", InventoryStocktakeRegister.class.getName());
        query.addLeftOuterJoin(InventoryJournalLines.class, "itemId", InventoryItemMaster.class, "itemId");
        query.addOrderBy("itemReference", InventoryItemMaster.class.getName());
        query.addLeftOuterJoin(InventoryJournalLines.class, "dimension1", InventoryDimension1.class, "dimensionId");
        query.addOrderBy("sortCode", InventoryDimension1.class.getName());
        query.addLeftOuterJoin(InventoryJournalLines.class, "dimension3", InventoryDimension3.class, "dimensionId");
        query.addOrderBy("sortCode", InventoryDimension3.class.getName());
        query.addLeftOuterJoin(InventoryJournalLines.class, "dimension2", InventoryDimension2.class, "dimensionId");
        query.addOrderBy("sortCode", InventoryDimension2.class.getName());
        query.addOrderBy("batch", InventoryStocktakeRegister.class.getName());
        query.addOrderBy("serial", InventoryStocktakeRegister.class.getName());
        List data = util.executeNativeQuery(query, userData);
        return populateDataSourceFields(data.subList(start, end > data.size() ? data.size() : end), userData);
    }

    @Override
    public String getNumRows(Class theTable, EMCUserData userData) {
        if (userData.getUserData() != null && userData.getUserData().size() > 2 && userData.getUserData(2).equals(true)) {
            // TODO Check that this works.
            return String.valueOf(populateDataSourceFields(util.executeGeneralSelectQuery(((EMCQuery) userData.getUserData(0)).toString(), userData), userData).size()) + ", " + Long.MAX_VALUE;
        } else {
            return super.getNumRows(theTable, userData);
        }
    }

    @Override
    public List<InventoryStocktakeCaptureDS> populateDataSourceFields(List dataList, EMCUserData userData) {
        Object[] oArray;
        InventoryStocktakeCaptureDS ds;
        Double journalQty;
        Double registerQty;
        Double countedQty;
        List retList = new ArrayList();
        List<String> itemList;
        InventoryParameters param = null;
        try {
            param = paramBean.getInventoryParameters(userData);
        } catch (EMCEntityBeanException ex) {
            logMessage(Level.SEVERE, "Failed to get inventory parametes", userData);
            return new ArrayList();
        }
        Map<String, List<String>> itemMap = new HashMap<String, List<String>>();
        Map<String, InventoryJournalMaster> journalMap = new HashMap<String, InventoryJournalMaster>();
        boolean recount = false;
        if (userData.getUserData() != null && userData.getUserData().size() > 2 && userData.getUserData(2).equals(true)) {
            recount = true;
        }
        boolean variance = false;
        StockTakeVarianceType varianceType = StockTakeVarianceType.VARIANCE_ONLY;
        if (userData.getUserData() != null && userData.getUserData().size() > 3 && userData.getUserData(3).equals(true)) {
            variance = true;
            if (userData.getUserData().size() > 4 && userData.getUserData(4) instanceof String) {
                varianceType = StockTakeVarianceType.fromString((String) userData.getUserData(4));
            }
        }
        boolean splitPerLocation = false;
        if (userData.getUserData() != null && userData.getUserData().size() > 5 && userData.getUserData(5).equals(true)) {
            splitPerLocation = true;
        }

        //Variance Report Field
        double grandTotalOnhand = 0d;
        for (Object o : dataList) {
            oArray = (Object[]) o;
            ds = new InventoryStocktakeCaptureDS();
            ds.setRecordID(-1);
            ds.setJournalNumber((String) oArray[0]);
            if (journalMap.containsKey(ds.getJournalNumber())) {
                ds.setJournalDescription(journalMap.get(ds.getJournalNumber()).getJournalDescription());
                ds.setJournalCreatedDate(Functions.date2String(journalMap.get(ds.getJournalNumber()).getCreatedDate()));
            } else {
                journalMap.put(ds.getJournalNumber(), findJournal(ds.getJournalNumber(), userData));
                ds.setJournalDescription(journalMap.get(ds.getJournalNumber()).getJournalDescription());
                ds.setJournalCreatedDate(Functions.date2String(journalMap.get(ds.getJournalNumber()).getCreatedDate()));
            }
            ds.setTransId((String) oArray[1]);
            ds.setItemId((String) oArray[2]);
            if (itemMap.containsKey(ds.getItemId())) {
                itemList = itemMap.get(ds.getItemId());
            } else {
                itemList = referenceBean.findReferenceAndDesc(ds.getItemId(), InventoryReferenceTypes.PRIMARY, userData);
                itemMap.put(ds.getItemId(), itemList);
            }
            ds.setItemReference(itemList.get(0));
            ds.setItemDescription(itemList.get(1));
            ds.setDimension1((String) oArray[3]);
            ds.setDimension2((String) oArray[4]);
            ds.setDimension3((String) oArray[5]);
            ds.setWarehouse((String) oArray[6]);
            ds.setLocation((String) oArray[7]);
            if (splitPerLocation) {
                ds.setLocationSplit(ds.getLocation());
            } else {
                ds.setLocationSplit("No Split");
            }
            ds.setBatch((String) oArray[8]);
            ds.setSerial((String) oArray[9]);
            ds.setPallet((String) oArray[10]);
            journalQty = (Double) oArray[11];
            registerQty = (Double) oArray[12];
            ds.setQuantityOnHand(registerQty);
            grandTotalOnhand += ds.getQuantityOnHand();
            countedQty = (Double) oArray[17];
            ds.setQuantityCounted(countedQty);
            ds.setVariance(ds.getQuantityCounted() - ds.getQuantityOnHand());
            ds.setItemCost((Double) oArray[14]);
            ds.setCostVariance((ds.getQuantityCounted() * ds.getItemCost()) - (ds.getQuantityOnHand() * ds.getItemCost()));
            ds.setPrintOnHand(param.getShowOnHanndQty());
            if (variance) {
                ds.setPrintOnHand(true);
            }
            ds.setJournalLineNo(Double.valueOf(oArray[15].toString()));
            ds.setRegisterLineRecordId(Long.valueOf(oArray[16].toString()));
            ds.setPageNumber((Integer) oArray[20]);
            if (recount) {
                if (needsRecount(ds, (BigDecimal) oArray[18], (BigDecimal) oArray[19], param)) {
                    ds.setQuantityOnHand(param.getShowOnHanndQty() ? (registerQty == null ? journalQty : registerQty) : 0);
                    retList.add(ds);
                }
            } else {
                if (variance) {
                    switch (varianceType) {
                        case ALL:
                            retList.add(ds);
                            break;
                        case VARIANCE_ONLY:
                            //only print values that are not 0
                            if (!(util.compareDouble(ds.getQuantityCounted() - ds.getQuantityOnHand(), 0d) == 0)) {
                                retList.add(ds);
                            }
                            break;
                        case NO_VARIANCE_ONLY:
                            //only print values that are not 0
                            if ((util.compareDouble(ds.getQuantityCounted() - ds.getQuantityOnHand(), 0d) == 0)) {
                                retList.add(ds);
                            }
                            break;
                    }
                } else {
                    ds.setQuantityOnHand(param.getShowOnHanndQty() ? (registerQty == null ? journalQty : registerQty) : 0);
                    retList.add(ds);
                }
            }
        }
        if (!retList.isEmpty()) {
            ds = (InventoryStocktakeCaptureDS) retList.remove(retList.size() - 1);
            ds.setGrandTotalHonHand(grandTotalOnhand);
            retList.add(ds);
        }
        return retList;
    }

    private boolean needsRecount(InventoryStocktakeCaptureDS ds, BigDecimal warehouseQtyDiff, BigDecimal warehouseValueDiff, InventoryParameters param) {
        if (Math.abs(ds.getQuantityCounted() - ds.getQuantityOnHand()) >
                (ds.getQuantityOnHand() * ((warehouseQtyDiff.compareTo(BigDecimal.ZERO) == 0 ? param.getStockTakeQuantityDiff() : warehouseQtyDiff.doubleValue()) /
                100))) {
            return true;
        }
        if (Math.abs((ds.getQuantityCounted() * ds.getItemCost()) - (ds.getQuantityOnHand() * ds.getItemCost())) >
                Math.abs((warehouseValueDiff.compareTo(BigDecimal.ZERO) == 0 ? param.getStockTakeValueDiff() : warehouseValueDiff.doubleValue()))) {
            return true;
        }
        return false;
    }

    private InventoryJournalMaster findJournal(String journalNumber, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalMaster.class.getName());
        query.addAnd("journalNumber", journalNumber);
        return (InventoryJournalMaster) util.executeSingleResultQuery(query, userData);
    }

    private boolean checkGeneratedFlag(String journalNum, double joutnalLineRecId, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalLines.class.getName());
        query.addAnd("journalNumber", journalNum);
        query.addAnd("lineNo", joutnalLineRecId);
        InventoryJournalLines line = (InventoryJournalLines) util.executeSingleResultQuery(query, userData);
        if (line == null) {
            throw new EMCEntityBeanException("Failed to get journal line.");
        }
        return line.getGenerated();
    }

    private InventoryJournalMaster findJournalMaster(String journalNumber, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalMaster.class.getName());
        query.addAnd("journalNumber", journalNumber);
        return (InventoryJournalMaster) util.executeSingleResultQuery(query, userData);
    }
}
