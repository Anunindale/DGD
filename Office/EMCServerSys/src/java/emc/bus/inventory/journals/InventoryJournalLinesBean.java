/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.journals;

import emc.bus.base.journals.BaseJournalDefinitionLocal;
import emc.bus.base.numbersequences.BaseNumberSequenceLogicLocal;
import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.InventoryWarehouseLocal;
import emc.bus.inventory.dimensions.InventoryDimensionTableLocal;
import emc.bus.inventory.journals.movement.MovementJournalPostCases;
import emc.bus.inventory.logic.InventoryItemDimensionCombinationLogicLocal;
import emc.bus.inventory.register.normanregister.InventoryRegisterLocal;
import emc.bus.inventory.register.removeregister.InventoryRemoveRegisterLocal;
import emc.bus.inventory.register.stocktakeregister.InventoryStockTakeRegisterLocal;
import emc.bus.inventory.stocktakelogger.InventoryStockTakeLoggerLocal;
import emc.bus.inventory.transactions.EMCStockException;
import emc.bus.inventory.transactions.ProcessStockTransactionLocal;
import emc.bus.inventory.transactions.TransactionHelper;
import emc.bus.inventory.transactions.TransactionType;
import emc.bus.inventory.transactions.datasource.InventorySummaryDSLocal;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryLocation;
import emc.entity.inventory.InventoryWarehouse;
import emc.entity.inventory.dimensions.InventoryItemDimensionGroup;
import emc.entity.base.journals.BaseJournalDefinitionTable;
import emc.entity.base.numbersequences.BaseAvailableSequenceNumbers;
import emc.entity.inventory.journals.InventoryJournalLines;
import emc.entity.inventory.journals.InventoryJournalMaster;
import emc.entity.inventory.register.InventoryRegister;
import emc.entity.inventory.register.InventoryRemoveRegister;
import emc.entity.inventory.register.InventoryStocktakeRegister;
import emc.entity.inventory.transactions.InventorySummary;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.inventorytables.InventoryLocationsEnum;
import emc.enums.inventory.journals.InventoryJournalTypes;
import emc.enums.base.journals.JournalStatus;
import emc.enums.inventory.journals.MovementDirections;
import emc.enums.inventory.register.RegisterFromTypeEnum;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.messages.ServerInventoryMessageEnum;
import emc.server.commandmanager.EMCCommandManagerLocal;
import emc.tables.EMCTable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.TransactionSynchronizationRegistry;

/**
 *
 * @author riaan
 */
@Stateless
public class InventoryJournalLinesBean extends EMCEntityBean implements InventoryJournalLinesLocal {

    @EJB
    private BaseJournalDefinitionLocal definitionBean;
    @EJB
    private InventoryItemDimensionCombinationLogicLocal dimCombinationBean;
    @EJB
    private ProcessStockTransactionLocal stockTransaction;
    @EJB
    private InventorySummaryDSLocal summaryDSBean;
    @EJB
    private InventoryItemMasterLocal itemMasterBean;
    @EJB
    private InventoryDimensionTableLocal dimIdBean;
    @EJB
    private InventoryRegisterLocal registerBean;
    @EJB
    private InventoryRemoveRegisterLocal removeRegisterBean;
    @EJB
    private InventoryStockTakeRegisterLocal stockTakeRegisterBean;
    @EJB
    private InventoryWarehouseLocal warehouseBean;
    @EJB
    private BaseNumberSequenceLogicLocal numberSequenceBean;
    @Resource
    private TransactionSynchronizationRegistry registry;
    @EJB
    private EMCCommandManagerLocal commandManager;
    @EJB
    private InventoryStockTakeLoggerLocal stockTakeLoggerBean;

    /** Creates a new instance of InventoryJournalLinesBean */
    public InventoryJournalLinesBean() {
    }

    @Override
    public boolean doDeleteValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        InventoryJournalLines line = (InventoryJournalLines) vobject;
        switch (this.checkStatus(line, userData)) {
            case APPROVED:
                Logger.getLogger("emc").log(Level.SEVERE, "Journal Approved, may not delete line", userData);
                return false;
            case POSTED:
                Logger.getLogger("emc").log(Level.SEVERE, "Journal Posted, may not delete line", userData);
                return false;
        }
        return super.doDeleteValidation(vobject, userData);
    }

    @Override
    public Object delete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        try {
            InventoryJournalLines line = (InventoryJournalLines) dobject;

            TransactionHelper txHelper = new TransactionHelper(TransactionType.IVENT_POST_JRLINE);

            switch (getJournalType(line, userData)) {
                case MOVEMENT:
                    txHelper.setPostCase(MovementJournalPostCases.JOURNAL_LINE_DELETED);
                    stockTransaction.post(line, txHelper, userData);

                //Post any other type of journal
                default:
                    deleteRegistration(line, false, userData);
                    line.setQuantity(0);
                    line.setCountQOH(0);
                    stockTransaction.post(line, txHelper, userData);
            }

            return super.delete(dobject, userData);
        } catch (EMCStockException ex) {
            throw new EMCEntityBeanException(ex.getMessage());
        }
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        try {

            InventoryJournalLines line = (InventoryJournalLines) uobject;
            line = checkDimForBlank(line);
            calcTotalCost(line, userData);
            line.setJournalType(definitionBean.getJournalDefinition(line.getJournalNumber(), userData).getJournalType());

            TransactionHelper helper = new TransactionHelper(TransactionType.IVENT_POST_JRLINE);

            switch (getJournalType(line, userData)) {
                case MOVEMENT:
                    InventoryJournalLines persistedLine = (InventoryJournalLines) util.findDetachedPersisted(line, userData);

                    if (!util.checkObjectsEqual(persistedLine.getItemId(), line.getItemId()) || !util.checkObjectsEqual(persistedLine.getDimension1(), line.getDimension1()) || !util.checkObjectsEqual(persistedLine.getDimension2(), line.getDimension2()) || !util.checkObjectsEqual(persistedLine.getDimension3(), line.getDimension3()) || !util.checkObjectsEqual(persistedLine.getWarehouse(), line.getWarehouse())) {
                        helper.setPostCase(MovementJournalPostCases.JOURNAL_ITEM_CHANGED);
                        stockTransaction.post(line, helper, userData);
                    } else {
                        if (!util.checkObjectsEqual(persistedLine.getQuantity(), line.getQuantity())) {

                            if (line.getQuantity() < persistedLine.getQuantity()) {
                                helper.setPostCase(MovementJournalPostCases.JOURNAL_QTY_UP);
                            } else {
                                helper.setPostCase(MovementJournalPostCases.JOURNAL_QTY_DOWN);
                            }

                            stockTransaction.post(line, helper, userData);
                        }
                    }
                    break;

                //Post any other type of journal
                default:
                    stockTransaction.post(line, helper, userData);
            }

            Object ret = super.update(uobject, userData);
            return ret;
        } catch (EMCStockException ex) {
            Logger.getLogger("emc").log(Level.SEVERE, "Could not update line " + ex, userData);
            throw new EMCEntityBeanException(ex.getMessage());
        }
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        try {
            InventoryJournalLines line = (InventoryJournalLines) iobject;
            line = checkDimForBlank(line);
            //Version might have been misused as flag for making quantities negative.   Check seems pointless.  2 simple statements versus 1
            line.setVersion(0);
            calcTotalCost(line, userData);
            line.setJournalType(definitionBean.getJournalDefinition(line.getJournalNumber(), userData).getJournalType());
            Object ret = super.insert(iobject, userData);
//            if (InventoryJournalTypes.STOCKTAKE.equals(getJournalType(line, userData))) createStockTakeRegisterLines(line, userData);
            //If this is a movement journal line, a transaction will be returned.
            TransactionHelper txHelper = new TransactionHelper(TransactionType.IVENT_POST_JRLINE);
            txHelper.setPostCase(MovementJournalPostCases.JOURNAL_LINE_CAPTURED);
            stockTransaction.post(line, txHelper, userData);

            return ret;
        } catch (EMCStockException ex) {
            ex.printStackTrace();
            throw new EMCEntityBeanException(ex.getMessage());
        }
    }

    @Override
    public boolean doUpdateValidation(EMCTable uobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doUpdateValidation(uobject, userData);

        InventoryJournalLines line = (InventoryJournalLines) uobject;
        switch (checkStatus(line, userData)) {
            case APPROVED:
                ret = ret && false;
                Logger.getLogger("emc").log(Level.SEVERE, "Journal is Approved, may not update line", userData);
                break;
            case POSTED:
                ret = ret && false;
                Logger.getLogger("emc").log(Level.SEVERE, "Journal is Posted, may not update line", userData);
                break;
        }
        switch (getJournalType(line, userData)) {
            case MOVEMENT:
                break;
            case STOCKTAKE:
                break;
            case TRANSFER:
                if (isBlank(line.getToItemId())) {
                    ret = false;
                    Logger.getLogger("emc").log(Level.SEVERE, "To Item may not be blank.", userData);
                }
                break;

        }
        ret = ret && dimCombinationBean.validateDimensionValues(line.getItemId(), line.getDimension1(), line.getDimension2(), line.getDimension3(), userData);
        ret = ret && dimCombinationBean.validateActiveDimensions(line.getItemId(), line.getDimension1(), line.getDimension2(), line.getDimension3(), line.getWarehouse(), true, userData);
        ret = ret && validateStockAvailableInLocation(line, userData);

        if (!isBlank(line.getToItemId())) {
            boolean toValid = dimCombinationBean.validateDimensionValues(line.getToItemId(), line.getToDimension1(), line.getToDimension2(), line.getToDimension3(), userData);
            toValid = toValid && dimCombinationBean.validateActiveDimensions(line.getToItemId(), line.getToDimension1(), line.getToDimension2(), line.getToDimension3(), line.getWarehouse(), true, userData);

            if (!toValid) {
                Logger.getLogger("emc").log(Level.SEVERE, "To item dimensions invalid.", userData);
            }

            ret = ret && toValid;
        }

        if (line.getQuantity() != 0 && line.getCost() == 0.0) {
            ret = false;
            Logger.getLogger("emc").log(Level.SEVERE, "Zero cost not allowed.", userData);
        }

        return ret;
    }

    /**
     * 
     * @param theLine
     * @param userData
     * @return
     */
    private JournalStatus checkStatus(InventoryJournalLines theLine, EMCUserData userData) {

        EMCQuery theQ = new EMCQuery(enumQueryTypes.SELECT, emc.entity.inventory.journals.InventoryJournalMaster.class.getName());
        theQ.addAnd("journalNumber", theLine.getJournalNumber());
        InventoryJournalMaster master = (InventoryJournalMaster) util.executeSingleResultQuery(theQ, userData);
        if (master != null && master.getJournalStatus() != null) {
            return JournalStatus.fromString(master.getJournalStatus());
        } else {
            return JournalStatus.CAPTURED;
        }

    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean ret = super.doInsertValidation(vobject, userData);

        InventoryJournalLines line = (InventoryJournalLines) vobject;
        switch (checkStatus(line, userData)) {
            case APPROVED:
                ret = ret && false;
                Logger.getLogger("emc").log(Level.SEVERE, "Journal is Approved, may not insert new line, un-approve first.", userData);
                break;
            case POSTED:
                ret = ret && false;
                Logger.getLogger("emc").log(Level.SEVERE, "Journal is Posted, may not insert new line.", userData);
                break;
        }
        switch (getJournalType(line, userData)) {
            case MOVEMENT:
                break;
            case STOCKTAKE:
                break;
            case TRANSFER:
                if (isBlank(line.getToItemId())) {
                    ret = false;
                    Logger.getLogger("emc").log(Level.SEVERE, "To Item may not be blank.", userData);
                }
                break;

        }
        ret = ret && dimCombinationBean.validateDimensionValues(line.getItemId(), line.getDimension1(), line.getDimension2(), line.getDimension3(), userData);
        ret = ret && dimCombinationBean.validateActiveDimensions(line.getItemId(), line.getDimension1(), line.getDimension2(), line.getDimension3(), line.getWarehouse(), true, userData);

        ret = ret && validateStockAvailableInLocation(line, userData);

        if (!isBlank(line.getToItemId())) {
            boolean toValid = dimCombinationBean.validateDimensionValues(line.getToItemId(), line.getToDimension1(), line.getToDimension2(), line.getToDimension3(), userData);
            toValid = toValid && dimCombinationBean.validateActiveDimensions(line.getToItemId(), line.getToDimension1(), line.getToDimension2(), line.getToDimension3(), line.getToWarehouse(), true, userData);

            if (!toValid) {
                Logger.getLogger("emc").log(Level.SEVERE, "To item dimensions invalid.", userData);
            }

            ret = ret && toValid;
        }
        if (line.getQuantity() != 0 && line.getCost() == 0.0) {
            ret = false;
            Logger.getLogger("emc").log(Level.SEVERE, "Zero cost not allowed.", userData);
        }
        return ret;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        Boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);

        InventoryJournalLines line = (InventoryJournalLines) theRecord;

        //Get type
        BaseJournalDefinitionTable definition = definitionBean.getJournalDefinition(line.getJournalNumber(), userData);

        //Movement journal special.
        if (definition != null && InventoryJournalTypes.MOVEMENT.equals(InventoryJournalTypes.fromString(definition.getJournalType()))) {
            if (MovementDirections.OUT.equals(MovementDirections.fromString(definition.getMovementDirection()))) {

                if (fieldNameToValidate.equals("quantity")) {
                    if (line.getRecordID() == 0) {
                        doMovementJournalLineQuantity(line, definition, userData);
                    }
                    if (line.getQuantity() > 0) {
                        Logger.getLogger("emc").log(Level.WARNING, "Quantity is not negative and this is a movement issue out Journal.", userData);
                    }
                }
            }
        }

        if (fieldNameToValidate.equals("contraType") || fieldNameToValidate.equals("contraAccount")) {
            ret = ret && checkContraFixed(fieldNameToValidate, line, userData);
        }
        if (fieldNameToValidate.contains("dimension")) {
            //Dimensions
            ret = ret && dimCombinationBean.validateDimensionValues(line.getItemId(), line.getDimension1(), line.getDimension2(), line.getDimension3(), userData);

            if (ret) {
                doItemCost(line, userData);
                if (InventoryJournalTypes.TRANSFER.equals(getJournalType(line, userData))) {
                    line.setToDimension1(line.getDimension1());
                    line.setToDimension2(line.getDimension2());
                    line.setToDimension3(line.getDimension3());
                }
            }
        }
        if (fieldNameToValidate.contains("toDimension")) {
            //To dimensions
            ret = ret && dimCombinationBean.validateDimensionValues(line.getToItemId(), line.getToDimension1(), line.getToDimension2(), line.getToDimension3(), userData);
        } else if (fieldNameToValidate.equals("cost") && line.getQuantity() > 0 && line.getCost() == 0.0) {
            ret = false;
            Logger.getLogger("emc").log(Level.SEVERE, "Zero cost not allowed.", userData);
        }
        if (fieldNameToValidate.equals("location")) {
            ret = ret && validateLocation(line.getItemId(), line.getLocation(), line.getWarehouse(), userData);
            //Check that stock is available in the specified location
            ret = ret && validateStockAvailableInLocation(line, userData);
            if (ret && InventoryJournalTypes.TRANSFER.equals(getJournalType(line, userData))) {
                line.setToLocation(line.getLocation());
            }
        }
        if (fieldNameToValidate.equals("toLocation")) {
            if (line.getToLocation().equals("QC")) {
                logMessage(Level.SEVERE, "Stock may not be moved into QC.", userData);
                return false;
            }
            ret = ret && validateLocation(line.getItemId(), line.getToLocation(), line.getToWarehouse(), userData);
        }
        if (fieldNameToValidate.equals("quantity")) {
            //Code that was here moved to oMovementJournalLineQuantity() method.
        }
        if (fieldNameToValidate.equals("warehouse")) {
            if (ret && InventoryJournalTypes.TRANSFER.equals(getJournalType(line, userData))) {
                line.setToWarehouse(line.getWarehouse());
            }
        }
        if (fieldNameToValidate.equals("batch")) {
            if (ret && InventoryJournalTypes.TRANSFER.equals(getJournalType(line, userData))) {
                line.setToBatch(line.getBatch());
            }
        }
        if (fieldNameToValidate.equals("serial")) {
            if (ret && InventoryJournalTypes.TRANSFER.equals(getJournalType(line, userData))) {
                line.setToSerial(line.getSerial());
            }
        }
        if (fieldNameToValidate.equals("pallet")) {
            if (ret && InventoryJournalTypes.TRANSFER.equals(getJournalType(line, userData))) {
                line.setToPallet(line.getPallet());
            }
        }
        if (fieldNameToValidate.equals("location")) {
            if (line.getLocation().equals("QC")) {
                logMessage(Level.SEVERE, "Stock may not be moved/received into or out of QC.", userData);
                return false;
            }
        }

        if (ret) {
            return line;
        } else {
            return ret;
        }
    }

    /**
     * returns the journal type
     * @param line
     * @param userData
     * @return
     */
    public InventoryJournalTypes getJournalType(InventoryJournalLines line, EMCUserData userData) {
        BaseJournalDefinitionTable definition = definitionBean.getJournalDefinition(line.getJournalNumber(), userData);

        if (definition != null) {
            return InventoryJournalTypes.fromString(definition.getJournalType());
        } else {
            return null;
        }
    }

    /** This method checks whether contra is fixed on the definition and checks input against the values on the jornal def */
    private boolean checkContraFixed(String fieldNameToValidate, InventoryJournalLines line, EMCUserData userData) {
        boolean ret = true;

        BaseJournalDefinitionTable definition = null;
        if (line != null && line.getJournalNumber() != null) {
            definitionBean.getJournalDefinition(line.getJournalNumber(), userData);
        }

        if (definition != null) {
            if (definition.getJournalContraFixed()) {
                if (fieldNameToValidate.equals("contraType") && line.getContraType() != null && !line.getContraType().toString().equals(definition.getJournalContraType())) {
                    ret = false;
                    Logger.getLogger("emc").log(Level.SEVERE, "Contra Account is fixed on Journal definition.", userData);
                } else {
                    if (fieldNameToValidate.equals("contraAccount") && line.getContraAccount() != null && !line.getContraAccount().toString().equals(definition.getJournalContraAccount())) {
                        ret = false;
                        Logger.getLogger("emc").log(Level.SEVERE, "Contra Account is fixed on Journal definition.", userData);
                    }
                }
            }
        }

        return ret;
    }

    /** Calculate the total cost of a line */
    private void calcTotalCost(InventoryJournalLines line, EMCUserData userData) {
        if (line.getCost() == 0 && InventoryJournalTypes.STOCKTAKE.equals(getJournalType(line, userData))) {
            line.setCost(itemMasterBean.getCostPrice(line.getItemId(), line.getDimension1(), line.getDimension2(), line.getDimension3(), userData));
            if (line.getCost() == 0) {
                line.setCost(0.01d);
            }
        }
        line.setTotalCost(line.getCost() * line.getQuantity());
    }

    private boolean validateLocation(String item, String location, String warehouse, EMCUserData userData) {
        if (isBlank(location)) {
            return true;
        }
        if (isBlank(warehouse)) {
            Logger.getLogger("emc").log(Level.SEVERE, "No warehouse specified.", userData);
            return false;
        }
        if (isBlank(item)) {
            Logger.getLogger("emc").log(Level.SEVERE, "No item specified.", userData);
            return false;
        }
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionGroup.class.getName());
        query.addTableAnd(InventoryItemMaster.class.getName(), "itemDimensionGroupId", InventoryItemDimensionGroup.class.getName(), "dimensionGroup");
        query.addAnd("locationActive", true, InventoryItemDimensionGroup.class.getName());
        query.addAnd("itemId", item, InventoryItemMaster.class.getName());
        if (!util.exists(query, userData)) {
            Logger.getLogger("emc").log(Level.SEVERE, "The location is not active on the given item.", userData);
        }
        query = new EMCQuery(enumQueryTypes.SELECT, InventoryLocation.class.getName());
        query.addAnd("warehouseId", warehouse);
        query.addAnd("locationId", location);
        boolean ret = util.exists(query, userData);
        if (!ret) {
            Logger.getLogger("emc").log(Level.SEVERE, "The location is not found in the given warehouse", userData);
        }
        return ret;
    }

    /**
     * method checks lines for blank dims
     * @param theLine
     * @return
     */
    private InventoryJournalLines checkDimForBlank(InventoryJournalLines theLine) {
        if (isBlank(theLine.getDimension1())) {
            theLine.setDimension1(null);
        }
        if (isBlank(theLine.getDimension2())) {
            theLine.setDimension2(null);
        }
        if (isBlank(theLine.getDimension3())) {
            theLine.setDimension3(null);
        }
        return theLine;

    }

    /** Updates quantity for a Movement Journal lines.  */
    private void doMovementJournalLineQuantity(InventoryJournalLines line, BaseJournalDefinitionTable definition, EMCUserData userData) {
        if (line.getQuantity() > 0) {
            line.setQuantity(line.getQuantity() * -1);
        }
    }

    /** Sets the quantity on a movement journal based on what is in stock.  */
    private void setMoveQuantityFromStock(InventoryJournalLines line, EMCUserData userData) {
        String itemId = line.getItemId();
        String warehouse = line.getWarehouse();
        String dimension1 = line.getDimension1();
        String dimension2 = line.getDimension2();
        String dimension3 = line.getDimension3();

        //Get active dims
        boolean[] activeDims = dimCombinationBean.getActiveDimensions(itemId, userData);

        if (!(activeDims[0] ^ !isBlank(dimension1)) &&
                !(activeDims[1] ^ !isBlank(dimension2)) &&
                !(activeDims[2] ^ !isBlank(dimension3)) &&
                !(activeDims[2] ^ !isBlank(warehouse))) {

            //Reset
            line.setQuantity(0);

            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class.getName());
            query.addAnd("itemId", itemId);
            query.addAnd("dimension1", dimension1);
            query.addAnd("dimension2", dimension2);
            query.addAnd("dimension3", dimension3);
            query.addAnd("warehouse", warehouse);

            query = summaryDSBean.setQuery(query, userData);

            List<Object[]> onHand = (List<Object[]>) util.executeGeneralSelectQuery(query, userData);

            for (Object[] summary : onHand) {
                Double availableQuantity = (Double) summary[1];

                if (availableQuantity == null) {
                    availableQuantity = 0d;
                }
                //There should only be one record.  Loop just to be safe.
                line.setQuantity(line.getQuantity() + availableQuantity);
            }
        }
    }

    private void doItemCost(InventoryJournalLines line, EMCUserData userData) {
        boolean[] dims = dimCombinationBean.getActiveDimensions(line.getItemId(), userData);
        boolean setCost = true;
        if (setCost && dims[0]) {
            setCost = !isBlank(line.getDimension1());
        }
        if (setCost && dims[1]) {
            setCost = !isBlank(line.getDimension2());
        }
        if (setCost && dims[2]) {
            setCost = !isBlank(line.getDimension3());
        }
        if (setCost) {
            double itemCost = itemMasterBean.getCostPrice(line.getItemId(), line.getDimension1(), line.getDimension2(), line.getDimension3(), userData);
            line.setCost(itemCost);
        }
    }

    private void deleteRegistration(InventoryJournalLines line, boolean orderAgain, EMCUserData userData) throws EMCEntityBeanException {
        //Indicate that data is being deleted from this method.  Used for stock reservation on movement journals.
        userData.setUserData(5, orderAgain);

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryRegister.class.getName());
        query.addAnd("masterId", line.getJournalNumber());
        query.addAnd("transId", line.getTransId());
        List<InventoryRegister> regList = util.executeGeneralSelectQuery(query, userData);
        for (InventoryRegister reg : regList) {
            registerBean.delete(reg, userData);
        }
        query = new EMCQuery(enumQueryTypes.SELECT, InventoryRemoveRegister.class.getName());
        query.addAnd("masterId", line.getJournalNumber());
        query.addAnd("transId", line.getTransId());
        List<InventoryRemoveRegister> removeRegList = util.executeGeneralSelectQuery(query, userData);
        for (InventoryRemoveRegister reg : removeRegList) {
            removeRegisterBean.delete(reg, userData);
        }
        query = new EMCQuery(enumQueryTypes.SELECT, InventoryStocktakeRegister.class.getName());
        query.addAnd("masterId", line.getJournalNumber());
        query.addAnd("transId", line.getTransId());
        List<InventoryStocktakeRegister> stockTakeRegList = util.executeGeneralSelectQuery(query, userData);
        for (InventoryStocktakeRegister curReg : stockTakeRegList) {
            //Delete Stock Take Log Record
            try {
                stockTakeLoggerBean.removeStockTakeRecord(line.getItemId(), line.getDimension1(), line.getDimension2(), line.getDimension3(), curReg.getWarehouse(), curReg.getLocation(), curReg.getBatch(), curReg.getSerial(), curReg.getPallet(), userData);
            } catch (EMCEntityBeanException ex) {
                throw new EMCEntityBeanException("Failed to delete stock take record: " + ex.getMessage());
            }
            stockTakeRegisterBean.delete(curReg, userData);
        }
    }

    private void createStockTakeRegisterLines(InventoryJournalLines line, EMCUserData userData) throws EMCEntityBeanException {
        InventoryStocktakeRegister register = new InventoryStocktakeRegister();
        register.setMasterId(line.getJournalNumber());
        register.setTransId(line.getTransId());
        register.setWarehouse(line.getWarehouse());
        register.setLocation(line.getLocation());
        register.setQuantity(line.getQuantity());
        register.setTotalQty(line.getQuantity());
        register.setRegisteredQty(line.getQuantity());
        stockTakeRegisterBean.insert(register, userData);
    }

    /**
     * Returns a Journal Line matching the specified parameters, or null, if no Journal Line is found.
     * @param journalNumber Journal number.
     * @param transId Transaction id.
     * @param userData User data.
     * @return A Journal Line matching the specified parameters, or null, if no Journal Line is found.
     */
    public InventoryJournalLines findJournalLine(String journalNumber, String transId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalLines.class.getName());
        query.addAnd("journalNumber", journalNumber);
        query.addAnd("transId", transId);

        return (InventoryJournalLines) util.executeSingleResultQuery(query, userData);
    }

    public boolean massUpdateLineDate(String journalId, Date theDate, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalLines.class);
        query.addAnd("journalNumber", journalId);
        List<InventoryJournalLines> linesList = util.executeGeneralSelectQuery(query, userData);
        for (InventoryJournalLines line : linesList) {
            line.setLineDate(theDate);
            this.update(line, userData);
        }
        return true;
    }

    /**
     * Ensures that the location on the given line is valid.
     * @param line Line to validate.
     * @param userData User data.
     * @return A boolean indicating whether the given line has a valid location.
     */
    private boolean validateStockAvailableInLocation(InventoryJournalLines line, EMCUserData userData) {
        InventoryLocationsEnum location = InventoryLocationsEnum.fromString(line.getLocation());
        InventoryWarehouse warehouse = warehouseBean.getWarehouse(line.getWarehouse(), userData);

        if (isBlank(warehouse) || isBlank(location)) {
            //No warehouse found, or location not special.
            return true;
        }

        boolean ret = true;

        switch (location) {
            case QUALITY_CHECK:
                ret = warehouse.getQCAvailable();
            case RECEIVING_AREA:
                ret = warehouse.getRECAvailable();
        }

        if (!ret) {
            logMessage(Level.SEVERE, ServerInventoryMessageEnum.STOCK_NOT_AVAILABLE, userData, location, warehouse.getWarehouseId());
        }

        return ret;
    }

    @Override
    public boolean generateRegistration(String masterId, String transId, String batchPrefix, String batchNo, String batchSuffix, String serialNo,
            String location, BigDecimal quantity, String batchGroupId, EMCUserData userData) throws EMCEntityBeanException {
        InventoryJournalLines journalLine = findJournalLine(masterId, transId, userData);

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryRegister.class);
        query.addAnd("masterId", masterId);
        query.addAnd("transId", transId);
        query.addFieldAggregateFunction("quantity", "SUM");
        Double registeredQty = (Double) util.executeSingleResultQuery(query, userData);
        if (registeredQty == null) {
            registeredQty = 0d;
        }

        double quantityRequired = journalLine.getQuantity() - registeredQty;

        if (util.compareDouble(quantityRequired, 0) <= 0) {
            Logger.getLogger("emc").log(Level.SEVERE, "Total quantity has already been registered.", userData);
            return false;
        }

        if (journalLine == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to find journal line.", userData);
            return false;
        }

        query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionGroup.class);
        query.addTableAnd(InventoryItemMaster.class.getName(), "itemDimensionGroupId", InventoryItemDimensionGroup.class.getName(), "dimensionGroup");
        query.addAnd("itemId", journalLine.getItemId(), InventoryItemMaster.class.getName());
        InventoryItemDimensionGroup dimGroup = (InventoryItemDimensionGroup) util.executeSingleResultQuery(query, userData);
        if (dimGroup == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to find dimension group.");
            return false;
        }
        if (!dimGroup.getBatchNumberActive()) {
            Logger.getLogger("emc").log(Level.SEVERE, "Batch is not active on the selected item.", userData);
            return false;
        } else {

            if (isBlank(batchNo)) {
                Logger.getLogger("emc").log(Level.SEVERE, "Batch is active on the selected item.", userData);
                return false;
            }

        }

        if (dimGroup.getSerialNumberActive() && isBlank(serialNo)) {
            Logger.getLogger("emc").log(Level.SEVERE, "Serial No is active on the selected item.", userData);
            return false;
        } else {
            if (!dimGroup.getSerialNumberActive() && !isBlank(serialNo)) {
                Logger.getLogger("emc").log(Level.WARNING, "Serial No is not active on the selected item.", userData);
            }
        }

        if (dimGroup.getLocationActive() && isBlank(location)) {
            Logger.getLogger("emc").log(Level.SEVERE, "Location is active on the selected item.", userData);
            return false;
        } else {
            if (!dimGroup.getLocationActive() && !isBlank(location)) {
                Logger.getLogger("emc").log(Level.WARNING, "Location is not active on the selected item.", userData);
            }
        }
        if (!isBlank(location)) {
            query = new EMCQuery(enumQueryTypes.SELECT, InventoryLocation.class);
            query.addAnd("warehouseId", journalLine.getWarehouse());
            query.addAnd("locationId", location);
            if (!util.exists(query, userData)) {
                Logger.getLogger("emc").log(Level.SEVERE, "The selected location is not found in the journal line's warehouse.", userData);
                return false;
            }
        }

        int batch = 0;
        int batchZero = 0;
        int batchLength = 0;

        double numBoxes = Math.ceil(quantityRequired / quantity.doubleValue());
        if (numBoxes > 0) {
            List<String> numberSeq = null;
            List<Long> numberSeqRecId = null;

            InventoryRegister register;
            while (quantityRequired > 0) {
                register = new InventoryRegister();
                register.setMasterId(masterId);
                register.setTransId(transId);
                register.setType(RegisterFromTypeEnum.JRN.toString());
                register.setRegisteredQty(journalLine.getQuantity());
                register.setTotalQty(journalLine.getQuantity());
                if (dimGroup.getBatchNumberActive()) {

                    register.setBatch(numberSeq.remove(0));

                    EMCQuery numQuery = new EMCQuery(enumQueryTypes.UPDATE, BaseAvailableSequenceNumbers.class);
                    numQuery.addAnd("recordID", numberSeqRecId.get(0));
                    numQuery.addSet("status", 2);//set it to used
                    commandManager.setTransactionSucceedQuery(registry.getTransactionKey(), numQuery.toString());
                    //load query to use on fail
                    EMCQuery fQuery = new EMCQuery(enumQueryTypes.UPDATE, BaseAvailableSequenceNumbers.class);
                    fQuery.addAnd("recordID", numberSeqRecId.remove(0));
                    fQuery.addSet("status", 0);//set it to available
                    commandManager.setTransactionFailQuery(registry.getTransactionKey(), fQuery.toString());

                }
                if (dimGroup.getSerialNumberActive()) {
                    register.setSerial(serialNo);
                }
                if (dimGroup.getWarehouseActive()) {
                    register.setWarehouse(journalLine.getWarehouse());
                }
                if (dimGroup.getLocationActive()) {
                    register.setLocation(location);
                }
                if (quantity.compareTo(util.getBigDecimal(quantityRequired)) > 0) {
                    register.setQuantity(quantityRequired);
                } else {
                    register.setQuantity(quantity.doubleValue());
                }
                quantityRequired -= quantity.doubleValue();
                registerBean.insert(register, userData);


            }
        }
        Logger.getLogger("emc").log(Level.INFO, "Registration Generated.", userData);
        return true;
    }
}
