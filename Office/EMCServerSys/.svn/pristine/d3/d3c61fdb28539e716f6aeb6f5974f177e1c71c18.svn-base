/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.stocktake;

import emc.bus.inventory.stocktakelogger.InventoryStockTakeLoggerLocal;
import emc.bus.inventory.transactions.EMCStockException;
import emc.bus.inventory.transactions.ProcessStockTransactionLocal;
import emc.bus.inventory.transactions.TransactionHelper;
import emc.bus.inventory.transactions.TransactionType;
import emc.entity.inventory.InventoryItemGroup;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryLocation;
import emc.entity.inventory.InventoryReference;
import emc.entity.inventory.InventoryWarehouse;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.entity.base.journals.BaseJournalDefinitionTable;
import emc.entity.inventory.journals.InventoryJournalLines;
import emc.entity.inventory.journals.InventoryJournalMaster;
import emc.entity.inventory.register.InventoryStocktakeRegister;
import emc.entity.inventory.transactions.InventorySummary;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.journals.InventoryJournalTypes;
import emc.enums.base.journals.JournalStatus;
import emc.enums.inventory.printtypes.StockTakePrintType;
import emc.enums.inventory.register.RegisterFromTypeEnum;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.inventory.helper.GenerateStockTake;
import emc.server.commandmanager.EMCCommandManagerLocal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.TransactionSynchronizationRegistry;

/**
 *
 * @author rico
 */
@Stateless
public class InventoryStockTakeBean extends EMCEntityBean implements InventoryStockTakeLocal {

    @EJB
    private ProcessStockTransactionLocal stockTransaction;
    @EJB
    private InventoryStockTakeLoggerLocal stockTakeLoggerBean;
    @EJB
    private EMCCommandManagerLocal cmdBean;
    @Resource
    protected TransactionSynchronizationRegistry registry;

    /**
     *
     * @param stHelper
     * @param userData
     * @throws EMCEntityBeanException
     */
    @Override
    public List<String> generateStockTakeJournals(GenerateStockTake stHelper, EMCUserData userData) throws EMCEntityBeanException {
        stHelper = validateGenerateStockCountParams(stHelper, userData);
        //build onHandQuery and order as needed
        EMCQuery onHand = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class.getName());
        onHand.addTableAnd(InventoryItemMaster.class.getName(), "itemId", InventorySummary.class.getName(), "itemId");
        onHand.addFieldAggregateFunction("physicalTotal", "SUM"); //0
        //fields to select
        onHand.addField("dimension1"); //1
        onHand.addField("dimension2"); //2
        onHand.addField("dimension3"); //3
        onHand.addField("batch");      //4
        onHand.addField("serialNo");   //5
        onHand.addField("warehouse");  //6
        onHand.addField("location");   //7
        onHand.addField("pallet");     //8
        onHand.addField("itemId");     //9
        //Later Update
        onHand.addFieldAggregateFunction("qcAvailable", "SUM"); //10
        onHand.addFieldAggregateFunction("recAvailable", "SUM"); //11
        //group by
        onHand.addGroupBy("itemId");
        onHand.addGroupBy("dimension1");
        onHand.addGroupBy("dimension2");
        onHand.addGroupBy("dimension3");
        onHand.addGroupBy("batch");
        onHand.addGroupBy("serialNo");
        onHand.addGroupBy("warehouse");
        onHand.addGroupBy("location");
        onHand.addGroupBy("pallet");
        //order By this should be changed with care also updating split methods
        if (stHelper.isSplitPerWarehouse()) {
            onHand.addOrderBy("warehouse");
        }
        if (stHelper.isSplitPerLocation()) {
            onHand.addOrderBy("location");
        }
        onHand.addOrderBy("itemReference", InventoryItemMaster.class.getName());
        onHand.addLeftOuterJoin(InventorySummary.class, "dimension1", InventoryDimension1.class, "dimensionId");
        onHand.addOrderBy("sortCode", InventoryDimension1.class.getName());
        onHand.addLeftOuterJoin(InventorySummary.class, "dimension3", InventoryDimension3.class, "dimensionId");
        onHand.addOrderBy("sortCode", InventoryDimension3.class.getName());
        onHand.addLeftOuterJoin(InventorySummary.class, "dimension2", InventoryDimension2.class, "dimensionId");
        onHand.addOrderBy("sortCode", InventoryDimension2.class.getName());
        if (!stHelper.isSplitPerWarehouse()) {
            onHand.addOrderBy("warehouse");
        }
        if (!stHelper.isSplitPerLocation()) {
            onHand.addOrderBy("location");
        }
        onHand.addOrderBy("batch");
        onHand.addOrderBy("serialNo");
        //where add user selection in here from stock take helper
        if (stHelper.getItemGroups() != null && stHelper.getItemGroups().size() > 0) {
            onHand.addAndInList("itemGroup", InventoryItemMaster.class.getName(), stHelper.getItemGroups(), true, false);
        }
        //Product Group
        if (stHelper.getProductGroups() != null && stHelper.getProductGroups().size() > 0) {
            onHand.addAndInList("productGroup", InventoryItemMaster.class.getName(), stHelper.getProductGroups(), true, false);
        }
        //item
        if (stHelper.getItemIds() != null && stHelper.getItemIds().size() > 0) {
            onHand.addAndInList("itemId", stHelper.getItemIds(), true, false);
        }
        //dim1
        if (stHelper.getDimension1() != null && stHelper.getDimension1().size() > 0) {
            onHand.addAndInList("dimension1", stHelper.getDimension1(), true, false);
        }
        //dim2
        if (stHelper.getDimension2() != null && stHelper.getDimension2().size() > 0) {
            onHand.addAndInList("dimension2", stHelper.getDimension2(), true, false);
        }
        //dim3
        if (stHelper.getDimension3() != null && stHelper.getDimension3().size() > 0) {
            onHand.addAndInList("dimension3", stHelper.getDimension3(), true, false);
        }
        //location
        if (stHelper.getLocations() != null && stHelper.getLocations().size() > 0) {
            onHand.addAndInList("location", stHelper.getLocations(), true, false);
        }
        //warehouse
        if (stHelper.getWarehouses() != null && stHelper.getWarehouses().size() > 0) {
            onHand.addAndInList("warehouse", stHelper.getWarehouses(), true, false);
        }
        if (stHelper.getNoMovementSince() != null || stHelper.getMovementSince() != null) {
            onHand.addTableAnd(InventoryTransactions.class.getName(), "inventoryTransRef", InventorySummary.class.getName(), "recordID");
        }
        //No Movement Since
        if (stHelper.getNoMovementSince() != null) {
            String transAlias = onHand.getTableAlias(InventoryTransactions.class);
            onHand.addAndCustomHavingValue("MAX(" + transAlias + ".physicalDate) < \'" + Functions.date2SQLString(stHelper.getNoMovementSince()) + "\'");
        }
        //Movement Since
        if (stHelper.getMovementSince() != null) {
            String transAlias = onHand.getTableAlias(InventoryTransactions.class);
            onHand.addAndCustomHavingValue("MAX(" + transAlias + ".physicalDate) >= \'" + Functions.date2SQLString(stHelper.getMovementSince()) + "\'");
        }
        String summaryAlias = onHand.getTableAlias(InventorySummary.class);
        onHand.addAndCustomHavingValue("SUM(" + summaryAlias + ".physicalTotal) > 0");

        //Select Data
        List<InventoryJournalLines> jLinesToInsert = new ArrayList();
        List<Object[]> data = util.executeNativeQuery(onHand, userData);
        int totalCount = data.size();
        if (totalCount == 0) {
            this.logMessage(Level.INFO, "No data found matching your selection. Update selection.", userData);
        }

        int count = 1;
        int processCount = 0;
        double lineNumber = 10;
        int detachFlushCount = 0;
        String company = util.getCompanyId(InventoryJournalMaster.class.getName(), userData);
        InventoryJournalMaster mast = null;
        InventoryJournalLines line = null;
        EMCQuery getDefQ = new EMCQuery(enumQueryTypes.SELECT, BaseJournalDefinitionTable.class.getName());
        getDefQ.addAnd("journalDefinitionId", stHelper.getJournalDefinition());
        BaseJournalDefinitionTable definition = (BaseJournalDefinitionTable) util.executeSingleResultQuery(getDefQ, userData);
        if (definition == null) {
            throw new EMCEntityBeanException("Failed to find journal definition");
        }
        List<String> journalNumberList = new ArrayList<String>();
        double qtyOnHand = 0;
        String previousLocation = "";

        InventoryWarehouse warehouse = null;
        int printRowCount = 0;
        int printPageCount = 0;
        Object[] toProcess;
        Object[] toPeek;
        Object loggerObject;
        for (int i = 0; i < data.size(); i++) {
            toProcess = data.get(i);
            qtyOnHand = (Double) toProcess[0];
            if (util.compareDouble(qtyOnHand, 0) == 0) {
                processCount++;
                continue; //don't want 0 lines
            }
            if (splitJournal(toProcess, line, stHelper, previousLocation, userData)) {
                mast = new InventoryJournalMaster();
                mast.setCompanyId(company);
                mast.setJournalDefinitionId(stHelper.getJournalDefinition());
                mast.setJournalDescription("Stock Take: " + Functions.nowDateString(emc.constants.systemConstants.systemDateFormat()) + " " + count + (isBlank(stHelper.getNoMovementSince()) ? "" : " * COUNT"));
                mast.setJournalContraAccount(definition.getJournalContraAccount());
                mast.setJournalContraType(definition.getJournalContraType());
                mast.setJournalStatus(JournalStatus.CAPTURED.toString());
                insert(mast, userData);
                if (stHelper.isPrintCountSheet()) {
                    journalNumberList.add(mast.getJournalNumber());
                }
                count++;
                lineNumber = 10; //reset line number

                if (warehouse == null || !warehouse.getWarehouseId().equals(toProcess[6])) {
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryWarehouse.class);
                    query.addAnd("warehouseId", toProcess[6]);
                    warehouse = (InventoryWarehouse) util.executeSingleResultQuery(query, userData);
                }
                //Reset Page Number
                printRowCount = 1;
                printPageCount = 1;
            }

            //Log Stock Take Item
            try {
                loggerObject = stockTakeLoggerBean.addStockTakeRecord(mast.getJournalNumber(), (String) toProcess[9], (String) toProcess[1], (String) toProcess[2], (String) toProcess[3],
                        (String) toProcess[6], (String) toProcess[7], (String) toProcess[4], (String) toProcess[5], (String) toProcess[8], userData);
            } catch (EMCEntityBeanException ex) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to create stock take logger record for Item - " + toProcess[9] +
                        "   Config - " + (String) toProcess[1] +
                        "   Colour - " + (String) toProcess[3] +
                        "   Size - " + (String) toProcess[2] +
                        "   Warehouse - " + (String) toProcess[6] +
                        "   Location - " + (String) toProcess[7] +
                        "   Batch - " + (String) toProcess[4] +
                        "   Serial - " + (String) toProcess[5] +
                        "   Pallet - " + (String) toProcess[8], userData);
                throw new EMCEntityBeanException("Failed to create stock take logger record: " + ex.getMessage());
            }

            if (loggerObject instanceof String) {
                Logger.getLogger("emc").log(Level.SEVERE, "Stock take already in progress for Item - " + toProcess[9] +
                        "   Config - " + (String) toProcess[1] +
                        "   Colour - " + (String) toProcess[3] +
                        "   Size - " + (String) toProcess[2] +
                        "   Warehouse - " + (String) toProcess[6] +
                        "   Location - " + (String) toProcess[7] +
                        "   Batch - " + (String) toProcess[4] +
                        "   Serial - " + (String) toProcess[5] +
                        "   Pallet - " + (String) toProcess[8] + " on the journal " + loggerObject, userData);
                throw new EMCEntityBeanException("Stock Take already in progress.");
            } else {
                Object[] loggerQueries = (Object[]) loggerObject;
                cmdBean.setTransactionSucceedQuery(registry.getTransactionKey(), ((EMCQuery) loggerQueries[0]).toString());
                cmdBean.setTransactionFailQuery(registry.getTransactionKey(), ((EMCQuery) loggerQueries[1]).toString());
            }

            processCount++;
            detachFlushCount++;
            printRowCount++;
            previousLocation = (String) toProcess[7];
            if (splitLines(toProcess, line, stHelper, userData)) {
                // Flush and detatch
                if (detachFlushCount > 10) {
                    util.flushEntity(userData);
                    util.detachEntities(userData);
                    detachFlushCount = 0;
                }

                line = new InventoryJournalLines();
                line.setLineNo(lineNumber);
                line.setCompanyId(company);
                line.setJournalNumber(mast.getJournalNumber());
                line.setLineDate(Functions.nowDate());
                line.setItemId(toProcess[9].toString());
                line.setDimension1(toProcess[1] == null ? null : toProcess[1].toString());
                line.setDimension2(toProcess[2] == null ? null : toProcess[2].toString());
                line.setDimension3(toProcess[3] == null ? null : toProcess[3].toString());
                line.setWarehouse(toProcess[6] == null ? null : toProcess[6].toString());
                line.setContraAccount(mast.getJournalContraAccount());
                line.setContraType(mast.getJournalContraType());
                line.setGenerated(true);
                line.setJournalType(definition.getJournalType());
                for (int i2 = i; i2 < data.size(); i2++) {
                    toPeek = data.get(i2);
                    if (splitJournal(toPeek, line, stHelper, previousLocation, userData) || splitLines(toPeek, line, stHelper, userData)) {
                        stHelper.setForceLineSplit(false);
                        break;
                    } else {
                        line.setCountQOH(line.getCountQOH() + (Double) toPeek[0]);
                    }
                }
                insert(line, userData);
                jLinesToInsert.add(line);
                //also create register line
                createRegisterLine(toProcess, qtyOnHand, line, company, printPageCount, userData);
                lineNumber += 10;
            } else { //only create register line
                createRegisterLine(toProcess, qtyOnHand, line, company, printPageCount, userData);
            }
            if (StockTakePrintType.LANDSCAPE.toString().equals(warehouse.getStockTakePrintType())) {
                if (printRowCount > 22) {
                    printRowCount = 1;
                    printPageCount++;
                }
            } else if (StockTakePrintType.PORTRAIT.toString().equals(warehouse.getStockTakePrintType())) {
                if (printRowCount > 34) {
                    printRowCount = 1;
                    printPageCount++;
                }
            }
        }
        //generate transactions
        TransactionHelper txH = new TransactionHelper(TransactionType.IVENT_POST_JRLINE);
        txH.setStockTakeJournal(true);
        for (InventoryJournalLines curLine : jLinesToInsert) {
            //do transaction
            try {
                stockTransaction.post(curLine, txH, userData);
            } catch (EMCStockException ex) {
                throw new EMCEntityBeanException("Failed to post transaction for stock take journal line." + ex);
            }
        }
        return journalNumberList;
    }

    /**
     * Creates a registration line
     * @param current
     * @param line
     * @param userData
     */
    private void createRegisterLine(Object[] current, double theQty, InventoryJournalLines line, String company, int pageNumber, EMCUserData userData) throws EMCEntityBeanException {
        InventoryStocktakeRegister reg = new InventoryStocktakeRegister();
        reg.setOnHandQty(theQty);
        reg.setCompanyId(company);
        reg.setType(RegisterFromTypeEnum.JRN.toString());
        reg.setTransId(line.getTransId());
        reg.setMasterId(line.getJournalNumber());
        reg.setWarehouse(current[6] == null ? null : current[6].toString());
        reg.setBatch(current[4] == null ? null : current[4].toString());
        reg.setSerial(current[5] == null ? null : current[5].toString());
        reg.setLocation(current[7] == null ? null : current[7].toString());
        reg.setPallet(current[8] == null ? null : current[8].toString());
        reg.setPageNumber(pageNumber);
        insert(reg, userData);
    }

    /**
     * Used to determine if a new Journal line is needed
     * @param previous
     * @param current
     * @param stHelper
     * @param userData
     * @return
     */
    private boolean splitLines(Object[] current, InventoryJournalLines line, GenerateStockTake stHelper, EMCUserData userData) {
        if (line == null || stHelper.isForceLineSplit()) {
            if (stHelper.isForceLineSplit()) {
                stHelper.setForceLineSplit(false);
            }
            return true;
        }
        //item, dim1,dim2,dim3,warehouse
        String itemP = line.getItemId() == null ? emc.constants.systemConstants.emcNA() : line.getItemId();
        String dim1P = line.getDimension1() == null ? emc.constants.systemConstants.emcNA() : line.getDimension1();
        String dim2P = line.getDimension2() == null ? emc.constants.systemConstants.emcNA() : line.getDimension2();
        String dim3P = line.getDimension3() == null ? emc.constants.systemConstants.emcNA() : line.getDimension3();
        String itemC = current[9] == null ? emc.constants.systemConstants.emcNA() : current[9].toString();
        String dim1C = current[1] == null ? emc.constants.systemConstants.emcNA() : current[1].toString();
        String dim2C = current[2] == null ? emc.constants.systemConstants.emcNA() : current[2].toString();
        String dim3C = current[3] == null ? emc.constants.systemConstants.emcNA() : current[3].toString();
        if (itemP.equals(itemC) && dim1P.equals(dim1C) && dim2P.equals(dim2C) && dim3P.equals(dim3C)) {
            return false;
        }
        return true;
    }

    /**
     * Used to determine if new Journal is needed
     * @param previous
     * @param current
     * @param stHelper
     * @param userData
     * @return
     */
    private boolean splitJournal(Object[] current, InventoryJournalLines line, GenerateStockTake stHelper, String previousLocation, EMCUserData userData) {
        boolean ret = false;
        if (line == null) {
            ret = true;
        }
        if (line != null) {
            if (stHelper.isSplitPerWarehouse()) {
                if (!line.getWarehouse().equals(current[6].toString())) {
                    ret = true;
                }
            }
            if (stHelper.isSplitPerLocation()) {
                if (!(isBlank(previousLocation) ? "" : previousLocation).equals((isBlank(current[7]) ? "" : current[7].toString()))) {
                    ret = true;
                }
            }
        }
        if (ret) {
            stHelper.setForceLineSplit(true);
        }
        return ret;
    }

    /**
     * Validates the values in the stock take generation helper
     * @param generate
     * @param userData
     * @return
     * @throws EMCEntityBeanException
     */
    private GenerateStockTake validateGenerateStockCountParams(GenerateStockTake generate, EMCUserData userData) throws EMCEntityBeanException {
        //Item Groups
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemGroup.class.getName());
        List<String> tempList = new ArrayList<String>();
        if (generate.getItemGroups() != null) {
            query.addField("itemGroup");
            query.addAndCommaSeperated("itemGroup", generate.getItemGroups().get(0), InventoryItemGroup.class.getName(), EMCQueryConditions.EQUALS);
            tempList = util.executeGeneralSelectQuery(query, userData);
            if (tempList.size() == 0) {
                throw new EMCEntityBeanException("All the Item Groups selected are invalid. Please reselect.");
            }
        }
        generate.setItemGroups(tempList);
        //Item Id
        query = new EMCQuery(enumQueryTypes.SELECT, InventoryReference.class.getName());
        tempList = new ArrayList<String>();
        if (generate.getItemIds() != null) {
            query.addField("itemId");
            query.addAndCommaSeperated("reference", generate.getItemIds().get(0), InventoryReference.class.getName(), EMCQueryConditions.EQUALS);
            tempList = util.executeGeneralSelectQuery(query, userData);
            if (tempList.size() == 0) {
                throw new EMCEntityBeanException("All the items selected are invalid. Please reselect.");
            }
        }
        generate.setItemIds(tempList);
        //Warehouse
        query = new EMCQuery(enumQueryTypes.SELECT, InventoryWarehouse.class.getName());
        tempList = new ArrayList<String>();
        if (generate.getWarehouses() != null) {
            query.addField("warehouseId");
            query.addAndCommaSeperated("warehouseId", generate.getWarehouses().get(0), InventoryWarehouse.class.getName(), EMCQueryConditions.EQUALS);
            tempList = util.executeGeneralSelectQuery(query, userData);
            if (tempList.size() == 0) {
                throw new EMCEntityBeanException("All the Warehouses selected are invalid. Please reselect.");
            }
        }
        generate.setWarehouses(tempList);
        //Location
        query = new EMCQuery(enumQueryTypes.SELECT, InventoryLocation.class.getName());
        tempList = new ArrayList<String>();
        if (generate.getLocations() != null && generate.getLocations().size() > 0) {
            query.addField("locationId");
            query.addAndCommaSeperated("locationId", generate.getLocations().get(0), InventoryLocation.class.getName(), EMCQueryConditions.EQUALS);
            tempList = util.executeGeneralSelectQuery(query, userData);
            if (tempList.size() == 0) {
                throw new EMCEntityBeanException("All the locations selected are invalid. Please reselect.");
            }
        }
        generate.setLocations(tempList);
        //Journal Definition
        query = new EMCQuery(enumQueryTypes.SELECT, BaseJournalDefinitionTable.class);
        query.addAnd("journalDefinitionId", generate.getJournalDefinition());
        query.addAnd("journalType", InventoryJournalTypes.STOCKTAKE.toString());
        if (!util.exists(query, userData)) {
            throw new EMCEntityBeanException("The Journal definition entered is not valid. Please reselect.");
        }
        return generate;
    }
}
