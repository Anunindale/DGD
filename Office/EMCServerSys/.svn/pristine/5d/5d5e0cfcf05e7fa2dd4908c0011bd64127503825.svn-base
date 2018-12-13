/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.batchconsolidation;

import emc.bus.base.journals.BaseJournalDefinitionLocal;
import emc.bus.inventory.InventoryParametersLocal;
import emc.bus.inventory.batchconsolidation.resources.ItemDimBatchHelper;
import emc.bus.inventory.journals.InventoryJournalLinesLocal;
import emc.bus.inventory.journals.InventoryJournalMasterLocal;
import emc.bus.workflow.WFActivityLocal;
import emc.entity.base.journals.BaseJournalDefinitionTable;
import emc.entity.inventory.InventoryBatchConsolidationLines;
import emc.entity.inventory.InventoryBatchConsolidationMaster;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryParameters;
import emc.entity.inventory.dimensions.InventoryItemDimensionGroup;
import emc.entity.inventory.journals.InventoryJournalLines;
import emc.entity.inventory.journals.InventoryJournalMaster;
import emc.entity.inventory.transactions.InventorySummary;
import emc.entity.workflow.WorkFlowActivity;
import emc.enums.base.journals.JournalStatus;
import emc.enums.base.journals.Modules;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.batchconsolidation.BatchConsolidationStatus;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.messages.ServerInventoryMessageEnum;
import emc.server.filehandeler.EMCFileHandlerLocal;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
public class InventoryBatchConsolidationMasterBean extends EMCEntityBean implements InventoryBatchConsolidationMasterLocal {

    @EJB
    private InventoryParametersLocal paramBean;
    @EJB
    private BaseJournalDefinitionLocal definitionBean;
    @EJB
    private InventoryJournalMasterLocal journalMasterBean;
    @EJB
    private InventoryJournalLinesLocal journalLinesBean;
    @EJB
    private InventoryBatchConsolidationLinesLocal consolidationLinesBean;
    @EJB
    private WFActivityLocal activityBean;
    @EJB
    private EMCFileHandlerLocal fileHandlerBean;

    @Override
    public boolean createBatchConsolidation(String consolidationNumber, String warehouse, String location, String productGroup, BigDecimal consolidationCrateQty, BigDecimal maxCrateQty, int maxNumberOfCrates, EMCUserData userData) throws EMCEntityBeanException {
        //Fetch Consolidation Master
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryBatchConsolidationMaster.class);
        query.addAnd("consolidationNumber", consolidationNumber);
        InventoryBatchConsolidationMaster consolidationMaster = (InventoryBatchConsolidationMaster) util.executeSingleResultQuery(query, userData);
        if (consolidationMaster == null) {
            throw new EMCEntityBeanException("Failed to find the consolidation master.");
        }

        if (!BatchConsolidationStatus.CREATED.toString().equals(consolidationMaster.getConsolidationStatus())) {
            throw new EMCEntityBeanException("The selected batch consolidation has already been created.");
        }

        consolidationMaster.setWarehouse(warehouse);
        consolidationMaster.setLocation(location);
        consolidationMaster.setProductGroup(productGroup);

        //Fetch and check Inventory Parameters
        InventoryParameters param = validateParameterSetup(userData);

        if (consolidationCrateQty.compareTo(BigDecimal.ZERO) <= 0) {
            logMessage(Level.SEVERE, ServerInventoryMessageEnum.NO_CONSOLIDATE_CRATE_QUANTITY, userData);
            throw new EMCEntityBeanException(ServerInventoryMessageEnum.NO_CONSOLIDATE_CRATE_QUANTITY.getDefaultMessage());
        }

        if (maxCrateQty.compareTo(BigDecimal.ZERO) <= 0) {
            logMessage(Level.SEVERE, ServerInventoryMessageEnum.NO_MAX_CRATE_QUANTITY, userData);
            throw new EMCEntityBeanException(ServerInventoryMessageEnum.NO_MAX_CRATE_QUANTITY.getDefaultMessage());
        }

        if (maxNumberOfCrates == 0) {
            logMessage(Level.SEVERE, ServerInventoryMessageEnum.NO_MAX_NUMBER_OF_CRATES, userData);
            throw new EMCEntityBeanException(ServerInventoryMessageEnum.NO_MAX_NUMBER_OF_CRATES.getDefaultMessage());
        }

        BaseJournalDefinitionTable journalDefinition = definitionBean.getJournalDefinition(param.getConsolidationTransferDefinition(), Modules.INVENTORY, userData);
        if (journalDefinition == null) {
            throw new EMCEntityBeanException("Failed to find the inventory journal definition.");
        }

        //Fetch Available stock
        List<Object[]> availableStock = fetchStock(warehouse, location, productGroup, userData);
        Object[] stock;

        Map<String, List<Object[]>> batchMap = new HashMap<String, List<Object[]>>();
        List<Object[]> batchList;

        Map<String, List<ItemDimBatchHelper>> itemDimhMap = new HashMap<String, List<ItemDimBatchHelper>>();
        List<ItemDimBatchHelper> itemDimList;

        BigDecimal available = BigDecimal.ZERO;
        BigDecimal reserved = BigDecimal.ZERO;

        //Group Stock By Batch
        for (int i = 0; i < availableStock.size(); i++) {
            stock = availableStock.get(i);

            batchList = batchMap.get((String) stock[6]);
            if (batchList == null) {
                batchList = new ArrayList<Object[]>();
            }

            batchList.add(stock);
            batchMap.put((String) stock[6], batchList);

            //Check Available and Reserved on Batch
            available = available.add(util.getBigDecimal((Double) stock[9]));
            reserved = reserved.add(util.getBigDecimal((Double) stock[10]));

            //Batch Completed
            if (i == availableStock.size() - 1 || !stock[6].equals(availableStock.get(i + 1)[6])) {
                if ((!param.isCheckPartialReservedCrates() && reserved.compareTo(BigDecimal.ZERO) > 0) || available.compareTo(BigDecimal.ZERO) <= 0) {
                    //Crate Available Stock greter than zero or partial reserved crated not allowed

                    batchMap.remove((String) stock[6]);
                } else {
                    //Add Batch to Item Dim Map
                    itemDimList = itemDimhMap.get((String) stock[0] + (String) stock[1] + (String) stock[2] + (String) stock[3]);
                    if (itemDimList == null) {
                        itemDimList = new ArrayList<ItemDimBatchHelper>();
                    }

                    itemDimList.add(new ItemDimBatchHelper((String) stock[6], (String) stock[5], available, reserved));
                    itemDimhMap.put((String) stock[0] + (String) stock[1] + (String) stock[2] + (String) stock[3], itemDimList);
                }
                available = BigDecimal.ZERO;
                reserved = BigDecimal.ZERO;
            }
        }

        Iterator<List<ItemDimBatchHelper>> itemDimIt = itemDimhMap.values().iterator();
        ItemDimBatchHelper outerHelper;
        ItemDimBatchHelper innerrHelper;

        InventoryJournalMaster journalMaster = null;
        InventoryJournalLines journalLine;
        int currentJournalLineCount = 0;

        boolean created = false;

        BigDecimal lineNo = BigDecimal.ONE;

        int cratesChecked = 0;

        boolean allocated;

        List<String> unallowcatedBatches = new ArrayList<String>();
//Allocate Stock
Allocate:
        while (itemDimIt.hasNext()) {
            itemDimList = itemDimIt.next();

            //Loop frm top to get rid of oldest batch first
            for (int o = 0; o != itemDimList.size(); o++) {
                outerHelper = itemDimList.get(o);

                if (outerHelper.getAvaliable().compareTo(consolidationCrateQty) < 0 && outerHelper.getAvaliable().compareTo(BigDecimal.ZERO) > 0) {
                    allocated = false;
                    //Loop from bottom to keep newest batches
                    for (int i = itemDimList.size(); i > 0; i--) {
                        if (outerHelper.getAvaliable().compareTo(BigDecimal.ZERO) == 0) {
                            break;
                        }
                        //Inner and outer is not the same thing
                        if (i - 1 != o) {
                            innerrHelper = itemDimList.get(i - 1);

                            //Outter helper has available & Inner helper has not heen moved & is not fully reserved & has enougth space left for outer stock
                            if (outerHelper.getAvaliable().compareTo(BigDecimal.ZERO) > 0
                                && isBlank(innerrHelper.getToBatch())
                                && !(innerrHelper.getReserved().compareTo(BigDecimal.ZERO) > 0 && innerrHelper.getAvaliable().compareTo(BigDecimal.ZERO) <= 0)
                                && innerrHelper.getAvaliable().add(innerrHelper.getReserved()).add(outerHelper.getAvaliable()).compareTo(maxCrateQty) <= 0) {
                                innerrHelper.setAvaliable(innerrHelper.getAvaliable().add(outerHelper.getAvaliable()));
                                outerHelper.setAvaliable(BigDecimal.ZERO);
                                outerHelper.setToBatch(innerrHelper.getBatch());
                                outerHelper.setToLocation(innerrHelper.getLocation());
                                allocated = true;

                                unallowcatedBatches.remove(innerrHelper.getBatch());
                            }
                        }
                    }

                    if (!allocated) {
                        unallowcatedBatches.add(outerHelper.getBatch());
                    }
                }
            }

//Chack all batches for item dim set again
            for (ItemDimBatchHelper helper : itemDimList) {
                //Batch has moved
                if (!isBlank(helper.getToBatch())) {
                    //Fetch all stock for batch
                    batchList = batchMap.get(helper.getBatch());
                    for (int s = 0; s < batchList.size(); s++) {
                        stock = batchList.get(s);
                        //Stock is available
                        if (BigDecimal.ZERO.compareTo(util.getBigDecimal((Double) stock[9])) < 0) {
                            //Create New Journal Master
                            if (journalMaster == null || currentJournalLineCount == param.getMaxLinesPerJournal()) {
                                journalMaster = createJournalMaster(journalDefinition, userData);
                                currentJournalLineCount = 0;
                            }
                            //Create Journal Line
                            journalLine = createJournalLine(journalMaster, currentJournalLineCount, stock, helper.getToBatch(), helper.getToLocation(), param.getConsolidationLineCost(), userData);
                            currentJournalLineCount++;
                            //Create Consolidation Line
                            createConsolidationLine(consolidationMaster, journalMaster, journalLine, lineNo, userData);
                            lineNo = lineNo.add(BigDecimal.ONE);
                            created = true;

                            //Check the number of crates to check parameter
                            cratesChecked++;
                            if (cratesChecked == maxNumberOfCrates) {
                                break Allocate;
                            }
                        }
                    }
                }
            }
        }

        if (created) {
            consolidationMaster.setConsolidationStatus(BatchConsolidationStatus.CAPTURE_SHORT.toString());
        } else {
            Logger.getLogger("emc").log(Level.WARNING, "Theere are no batches that can be grouped at the moment.", userData);
        }

        update(consolidationMaster, userData);

        if (!unallowcatedBatches.isEmpty() && !isBlank(param.getBatchConsolidationActivityUser())) {
            createLogActivity(unallowcatedBatches, batchMap, param.getBatchConsolidationActivityUser(), userData);
        }

        return true;
    }

    private List<Object[]> fetchStock(String warehouse, String location, String productGroup, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class);
        query.addTableAnd(InventoryItemMaster.class.getName(), "itemId", InventorySummary.class.getName(), "itemId");
        query.addTableAnd(InventoryItemDimensionGroup.class.getName(), "dimensionGroup", InventoryItemMaster.class.getName(), "itemDimensionGroupId");
        query.addAnd("batchNumberActive", true, InventoryItemDimensionGroup.class.getName());
        query.addAnd("warehouse", warehouse, InventorySummary.class.getName(), EMCQueryConditions.EQUALS);
        query.addAndCommaSeperated("location", location, InventorySummary.class.getName(), EMCQueryConditions.EQUALS);
        if (!isBlank(productGroup)) {
            query.addAndCommaSeperated("productGroup", productGroup, InventoryItemMaster.class.getName(), EMCQueryConditions.EQUALS);
        }

        query.addField("itemId", InventorySummary.class.getName());//0
        query.addField("dimension1", InventorySummary.class.getName());//1
        query.addField("dimension2", InventorySummary.class.getName());//2
        query.addField("dimension3", InventorySummary.class.getName());//3
        query.addField("warehouse", InventorySummary.class.getName());//4
        query.addField("location", InventorySummary.class.getName());//5
        query.addField("batch", InventorySummary.class.getName());//6
        query.addField("serialNo", InventorySummary.class.getName());//7
        query.addField("pallet", InventorySummary.class.getName());//8
        query.addFieldAggregateFunction("physicalAvailable", InventorySummary.class.getName(), "SUM");//9
        query.addFieldAggregateFunction("physicalReserved", InventorySummary.class.getName(), "SUM");//10

        query.addGroupBy(InventorySummary.class.getName(), "dimension1");
        query.addGroupBy(InventorySummary.class.getName(), "dimension2");
        query.addGroupBy(InventorySummary.class.getName(), "dimension3");
        query.addGroupBy(InventorySummary.class.getName(), "warehouse");
        query.addGroupBy(InventorySummary.class.getName(), "location");
        query.addGroupBy(InventorySummary.class.getName(), "batch");
        query.addGroupBy(InventorySummary.class.getName(), "serialNo");
        query.addGroupBy(InventorySummary.class.getName(), "pallet");

        query.addOrderBy("batch", InventorySummary.class.getName());

        query.addOrHavingAggregateFunction("SUM", InventorySummary.class.getName(), "physicalAvailable", EMCQueryConditions.GREATER_THAN, 0);

        return util.executeGeneralSelectQuery(query, userData);
    }

    private InventoryJournalMaster createJournalMaster(BaseJournalDefinitionTable definition, EMCUserData userData) throws EMCEntityBeanException {
        InventoryJournalMaster journal = new InventoryJournalMaster();
        journal.setJournalDefinitionId(definition.getJournalDefinitionId());
        journal.setJournalDescription("Crate Consolidation Transfer - " + Functions.nowDateString("yyyy/MM/dd"));
        journal.setJournalDate(Functions.nowDate());
        journal.setJournalContraAccount(definition.getJournalContraAccount());
        journal.setJournalContraType(definition.getJournalContraType());

        journalMasterBean.insert(journal, userData);

        return journal;
    }

    private InventoryJournalLines createJournalLine(InventoryJournalMaster journalMaster, int lineNo, Object[] stock, String toBatch, String toLocation, BigDecimal lineCost, EMCUserData userData) throws EMCEntityBeanException {
        InventoryJournalLines line = new InventoryJournalLines();
        line.setJournalNumber(journalMaster.getJournalNumber());
        line.setContraAccount(journalMaster.getJournalContraAccount());
        line.setContraType(journalMaster.getJournalContraType());
        line.setLineNo(Integer.valueOf(lineNo).doubleValue());
        line.setLineDate(Functions.nowDate());
        line.setItemId((String) stock[0]);
        line.setDimension1((String) stock[1]);
        line.setDimension2((String) stock[2]);
        line.setDimension3((String) stock[3]);
        line.setWarehouse((String) stock[4]);
        line.setLocation((String) stock[5]);
        line.setBatch((String) stock[6]);
        line.setSerial((String) stock[7]);
        line.setPallet((String) stock[8]);
        line.setQuantity((Double) stock[9]);
        line.setCost(lineCost.doubleValue());
        line.setToItemId((String) stock[0]);
        line.setToDimension1((String) stock[1]);
        line.setToDimension2((String) stock[2]);
        line.setToDimension3((String) stock[3]);
        line.setToWarehouse((String) stock[4]);
        line.setToLocation(toLocation);
        line.setToBatch(toBatch);
        line.setToSerial((String) stock[7]);
        line.setToPallet((String) stock[8]);

        journalLinesBean.insert(line, userData);

        return line;
    }

    private void createConsolidationLine(InventoryBatchConsolidationMaster consolidationMaster, InventoryJournalMaster journalMaster, InventoryJournalLines journalLine, BigDecimal lineNo, EMCUserData userData) throws EMCEntityBeanException {
        InventoryBatchConsolidationLines line = new InventoryBatchConsolidationLines();
        line.setConsolidationNumber(consolidationMaster.getConsolidationNumber());
        line.setLineNo(lineNo);
        line.setJournalMasterId(journalMaster.getJournalNumber());
        line.setTransferJournalLineRecordId(journalLine.getRecordID());
        line.setQuantity(util.getBigDecimal(journalLine.getQuantity()));

        consolidationLinesBean.insert(line, userData);
    }

    private InventoryParameters validateParameterSetup(EMCUserData userData) throws EMCEntityBeanException {
        InventoryParameters param = paramBean.getInventoryParameters(userData);

        if (param == null) {
            logMessage(Level.SEVERE, ServerInventoryMessageEnum.NO_PARAMS, userData);
            throw new EMCEntityBeanException(ServerInventoryMessageEnum.NO_PARAMS.getDefaultMessage());
        }

        if (isBlank(param.getConsolidationTransferDefinition())) {
            logMessage(Level.SEVERE, ServerInventoryMessageEnum.NO_TRANSFER_DEFINITION, userData);
            throw new EMCEntityBeanException(ServerInventoryMessageEnum.NO_TRANSFER_DEFINITION.getDefaultMessage());
        }

        if (isBlank(param.getConsolidationMovementDefinition())) {
            logMessage(Level.SEVERE, ServerInventoryMessageEnum.NO_MOVEMENT_DEFINITION, userData);
            throw new EMCEntityBeanException(ServerInventoryMessageEnum.NO_MOVEMENT_DEFINITION.getDefaultMessage());
        }

        if (param.getMaxLinesPerJournal() == 0) {
            logMessage(Level.SEVERE, ServerInventoryMessageEnum.NO_MOVEMENT_DEFINITION, userData);
            throw new EMCEntityBeanException(ServerInventoryMessageEnum.NO_MOVEMENT_DEFINITION.getDefaultMessage());
        }

        if (param.getConsolidationLineCost() == null || param.getConsolidationLineCost().compareTo(BigDecimal.ZERO) <= 0) {
            logMessage(Level.SEVERE, ServerInventoryMessageEnum.NO_MOVEMENT_DEFINITION, userData);
            throw new EMCEntityBeanException(ServerInventoryMessageEnum.NO_MOVEMENT_DEFINITION.getDefaultMessage());
        }

        if (isBlank(param.getConsolidationWarehouse())) {
            logMessage(Level.SEVERE, ServerInventoryMessageEnum.NO_MOVEMENT_DEFINITION, userData);
            throw new EMCEntityBeanException(ServerInventoryMessageEnum.NO_MOVEMENT_DEFINITION.getDefaultMessage());
        }

        if (isBlank(param.getBatchConsolidationActivityUser())) {
            logMessage(Level.WARNING, ServerInventoryMessageEnum.NO_CONSOLIDATION_USER, userData);
        }

        return param;
    }

    @Override
    public List<String> getMovementJournalsForConsolidationApproval(String consolidationNumber, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryBatchConsolidationLines.class);
        query.addTableAnd(InventoryJournalLines.class.getName(), "movementJournalLineRecordId", InventoryBatchConsolidationLines.class.getName(), "recordID");
        query.addTableAnd(InventoryJournalMaster.class.getName(), "journalNumber", InventoryJournalLines.class.getName(), "journalNumber");
        query.addAnd("consolidationNumber", consolidationNumber, InventoryBatchConsolidationLines.class.getName());
        query.addAnd("journalStatus", JournalStatus.CAPTURED.toString(), InventoryJournalMaster.class.getName());
        query.addField("journalNumber", InventoryJournalMaster.class.getName());
        query.addGroupBy(InventoryJournalMaster.class.getName(), "journalNumber");
        return util.executeGeneralSelectQuery(query, userData);
    }

    @Override
    public List<String> getTransferJournalsForConsolidationApproval(String consolidationNumber, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryBatchConsolidationLines.class);
        query.addTableAnd(InventoryJournalLines.class.getName(), "transferJournalLineRecordId", InventoryBatchConsolidationLines.class.getName(), "recordID");
        query.addTableAnd(InventoryJournalMaster.class.getName(), "journalNumber", InventoryJournalLines.class.getName(), "journalNumber");
        query.addAnd("consolidationNumber", consolidationNumber, InventoryBatchConsolidationLines.class.getName());
        query.addAnd("journalStatus", JournalStatus.CAPTURED.toString(), InventoryJournalMaster.class.getName());
        query.addField("journalNumber", InventoryJournalMaster.class.getName());
        query.addGroupBy(InventoryJournalMaster.class.getName(), "journalNumber");
        return util.executeGeneralSelectQuery(query, userData);
    }

    @Override
    public boolean approveConsolidation(String consolidationNumber, EMCUserData userData) throws EMCEntityBeanException {
        boolean approveConsolidation = true;

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryBatchConsolidationMaster.class);
        query.addAnd("consolidationNumber", consolidationNumber);
        InventoryBatchConsolidationMaster consolidationMaster = (InventoryBatchConsolidationMaster) util.executeSingleResultQuery(query, userData);
        if (consolidationMaster == null) {
            throw new EMCEntityBeanException("Failed to find the consolidation master.");
        }

        if (!BatchConsolidationStatus.CAPTURE_SHORT.toString().equals(consolidationMaster.getConsolidationStatus())) {
            throw new EMCEntityBeanException("The selected batch consolidation is in the " + consolidationMaster.getConsolidationStatus() + " state.");
        }

        query = new EMCQuery(enumQueryTypes.SELECT, InventoryBatchConsolidationLines.class);
        query.addTableAnd(InventoryJournalLines.class.getName(), "movementJournalLineRecordId", InventoryBatchConsolidationLines.class.getName(), "recordID");
        query.addTableAnd(InventoryJournalMaster.class.getName(), "journalNumber", InventoryJournalLines.class.getName(), "journalNumber");
        query.addAnd("consolidationNumber", consolidationNumber, InventoryBatchConsolidationLines.class.getName());
        query.addAnd("journalStatus", JournalStatus.CAPTURED.toString(), InventoryJournalMaster.class.getName());
        query.addField("journalNumber", InventoryJournalMaster.class.getName());
        query.addField("journalStatus", InventoryJournalMaster.class.getName());
        query.addGroupBy(InventoryJournalMaster.class.getName(), "journalNumber");
        List<Object[]> movementJournals = util.executeGeneralSelectQuery(query, userData);

        for (Object[] journal : movementJournals) {
            if (!JournalStatus.APPROVED.toString().equals(journal[1])) {
                Logger.getLogger("emc").log(Level.SEVERE, "Movement journal " + journal[0] + " still is in the " + journal[1] + " state.", userData);
                approveConsolidation = false;
            }
        }

        query = new EMCQuery(enumQueryTypes.SELECT, InventoryBatchConsolidationLines.class);
        query.addTableAnd(InventoryJournalLines.class.getName(), "transferJournalLineRecordId", InventoryBatchConsolidationLines.class.getName(), "recordID");
        query.addTableAnd(InventoryJournalMaster.class.getName(), "journalNumber", InventoryJournalLines.class.getName(), "journalNumber");
        query.addAnd("consolidationNumber", consolidationNumber, InventoryBatchConsolidationLines.class.getName());
        query.addAnd("journalStatus", JournalStatus.CAPTURED.toString(), InventoryJournalMaster.class.getName());
        query.addField("journalNumber", InventoryJournalMaster.class.getName());
        query.addField("journalStatus", InventoryJournalMaster.class.getName());
        query.addGroupBy(InventoryJournalMaster.class.getName(), "journalNumber");
        List<Object[]> transferJournals = util.executeGeneralSelectQuery(query, userData);

        for (Object[] journal : movementJournals) {
            if (!JournalStatus.APPROVED.toString().equals(journal[1])) {
                Logger.getLogger("emc").log(Level.SEVERE, "Transfer journal " + journal[0] + " still is in the " + journal[1] + " state.", userData);
                approveConsolidation = false;
            }
        }

        if (approveConsolidation) {
            consolidationMaster.setConsolidationStatus(BatchConsolidationStatus.APPROVED.toString());
            update(consolidationMaster, userData);
            return true;
        } else {
            throw new EMCEntityBeanException("Not all of the backing journal have been approved.");
        }
    }

    @Override
    public List<String> getMovementJournalsForConsolidationUnApproval(String consolidationNumber, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryBatchConsolidationLines.class);
        query.addTableAnd(InventoryJournalLines.class.getName(), "movementJournalLineRecordId", InventoryBatchConsolidationLines.class.getName(), "recordID");
        query.addTableAnd(InventoryJournalMaster.class.getName(), "journalNumber", InventoryJournalLines.class.getName(), "journalNumber");
        query.addAnd("consolidationNumber", consolidationNumber, InventoryBatchConsolidationLines.class.getName());
        query.addAnd("journalStatus", JournalStatus.APPROVED.toString(), InventoryJournalMaster.class.getName());
        query.addField("journalNumber", InventoryJournalMaster.class.getName());
        query.addGroupBy(InventoryJournalMaster.class.getName(), "journalNumber");
        return util.executeGeneralSelectQuery(query, userData);
    }

    @Override
    public List<String> getTransferJournalsForConsolidationUnApproval(String consolidationNumber, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryBatchConsolidationLines.class);
        query.addTableAnd(InventoryJournalLines.class.getName(), "transferJournalLineRecordId", InventoryBatchConsolidationLines.class.getName(), "recordID");
        query.addTableAnd(InventoryJournalMaster.class.getName(), "journalNumber", InventoryJournalLines.class.getName(), "journalNumber");
        query.addAnd("consolidationNumber", consolidationNumber, InventoryBatchConsolidationLines.class.getName());
        query.addAnd("journalStatus", JournalStatus.APPROVED.toString(), InventoryJournalMaster.class.getName());
        query.addField("journalNumber", InventoryJournalMaster.class.getName());
        query.addGroupBy(InventoryJournalMaster.class.getName(), "journalNumber");
        return util.executeGeneralSelectQuery(query, userData);
    }

    @Override
    public boolean unApproveConsolidation(String consolidationNumber, EMCUserData userData) throws EMCEntityBeanException {
        boolean unApproveConsolidation = true;

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryBatchConsolidationMaster.class);
        query.addAnd("consolidationNumber", consolidationNumber);
        InventoryBatchConsolidationMaster consolidationMaster = (InventoryBatchConsolidationMaster) util.executeSingleResultQuery(query, userData);
        if (consolidationMaster == null) {
            throw new EMCEntityBeanException("Failed to find the consolidation master.");
        }

        if (!BatchConsolidationStatus.APPROVED.toString().equals(consolidationMaster.getConsolidationStatus())) {
            throw new EMCEntityBeanException("The selected batch consolidation is in the " + consolidationMaster.getConsolidationStatus() + " state.");
        }

        query = new EMCQuery(enumQueryTypes.SELECT, InventoryBatchConsolidationLines.class);
        query.addTableAnd(InventoryJournalLines.class.getName(), "movementJournalLineRecordId", InventoryBatchConsolidationLines.class.getName(), "recordID");
        query.addTableAnd(InventoryJournalMaster.class.getName(), "journalNumber", InventoryJournalLines.class.getName(), "journalNumber");
        query.addAnd("consolidationNumber", consolidationNumber, InventoryBatchConsolidationLines.class.getName());
        query.addAnd("journalStatus", JournalStatus.CAPTURED.toString(), InventoryJournalMaster.class.getName());
        query.addField("journalNumber", InventoryJournalMaster.class.getName());
        query.addField("journalStatus", InventoryJournalMaster.class.getName());
        query.addGroupBy(InventoryJournalMaster.class.getName(), "journalNumber");
        List<Object[]> movementJournals = util.executeGeneralSelectQuery(query, userData);

        for (Object[] journal : movementJournals) {
            if (!JournalStatus.CAPTURED.toString().equals(journal[1])) {
                Logger.getLogger("emc").log(Level.SEVERE, "Movement journal " + journal[0] + " still is in the " + journal[1] + " state.", userData);
                unApproveConsolidation = false;
            }
        }

        query = new EMCQuery(enumQueryTypes.SELECT, InventoryBatchConsolidationLines.class);
        query.addTableAnd(InventoryJournalLines.class.getName(), "transferJournalLineRecordId", InventoryBatchConsolidationLines.class.getName(), "recordID");
        query.addTableAnd(InventoryJournalMaster.class.getName(), "journalNumber", InventoryJournalLines.class.getName(), "journalNumber");
        query.addAnd("consolidationNumber", consolidationNumber, InventoryBatchConsolidationLines.class.getName());
        query.addAnd("journalStatus", JournalStatus.CAPTURED.toString(), InventoryJournalMaster.class.getName());
        query.addField("journalNumber", InventoryJournalMaster.class.getName());
        query.addField("journalStatus", InventoryJournalMaster.class.getName());
        query.addGroupBy(InventoryJournalMaster.class.getName(), "journalNumber");
        List<Object[]> transferJournals = util.executeGeneralSelectQuery(query, userData);

        for (Object[] journal : movementJournals) {
            if (!JournalStatus.CAPTURED.toString().equals(journal[1])) {
                Logger.getLogger("emc").log(Level.SEVERE, "Transfer journal " + journal[0] + " still is in the " + journal[1] + " state.", userData);
                unApproveConsolidation = false;
            }
        }

        if (unApproveConsolidation) {
            consolidationMaster.setConsolidationStatus(BatchConsolidationStatus.CAPTURE_SHORT.toString());
            update(consolidationMaster, userData);
            return true;
        } else {
            throw new EMCEntityBeanException("Not all of the backing journal have been approved.");
        }
    }

    @Override
    public List<String> getMovementJournalsForConsolidationPosting(String consolidationNumber, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryBatchConsolidationLines.class);
        query.addTableAnd(InventoryJournalLines.class.getName(), "movementJournalLineRecordId", InventoryBatchConsolidationLines.class.getName(), "recordID");
        query.addTableAnd(InventoryJournalMaster.class.getName(), "journalNumber", InventoryJournalLines.class.getName(), "journalNumber");
        query.addAnd("consolidationNumber", consolidationNumber, InventoryBatchConsolidationLines.class.getName());
        query.addAnd("journalStatus", JournalStatus.APPROVED.toString(), InventoryJournalMaster.class.getName());
        query.addField("journalNumber", InventoryJournalMaster.class.getName());
        query.addGroupBy(InventoryJournalMaster.class.getName(), "journalNumber");
        return util.executeGeneralSelectQuery(query, userData);
    }

    @Override
    public List<String> getTransferJournalsForConsolidationPosting(String consolidationNumber, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryBatchConsolidationLines.class);
        query.addTableAnd(InventoryJournalLines.class.getName(), "transferJournalLineRecordId", InventoryBatchConsolidationLines.class.getName(), "recordID");
        query.addTableAnd(InventoryJournalMaster.class.getName(), "journalNumber", InventoryJournalLines.class.getName(), "journalNumber");
        query.addAnd("consolidationNumber", consolidationNumber, InventoryBatchConsolidationLines.class.getName());
        query.addAnd("journalStatus", JournalStatus.APPROVED.toString(), InventoryJournalMaster.class.getName());
        query.addField("journalNumber", InventoryJournalMaster.class.getName());
        query.addGroupBy(InventoryJournalMaster.class.getName(), "journalNumber");
        return util.executeGeneralSelectQuery(query, userData);
    }

    @Override
    public boolean postConsolidation(String consolidationNumber, EMCUserData userData) throws EMCEntityBeanException {
        boolean postConsolidation = true;

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryBatchConsolidationMaster.class);
        query.addAnd("consolidationNumber", consolidationNumber);
        InventoryBatchConsolidationMaster consolidationMaster = (InventoryBatchConsolidationMaster) util.executeSingleResultQuery(query, userData);
        if (consolidationMaster == null) {
            throw new EMCEntityBeanException("Failed to find the consolidation master.");
        }

        if (!BatchConsolidationStatus.APPROVED.toString().equals(consolidationMaster.getConsolidationStatus())) {
            throw new EMCEntityBeanException("The selected batch consolidation is in the " + consolidationMaster.getConsolidationStatus() + " state.");
        }

        query = new EMCQuery(enumQueryTypes.SELECT, InventoryBatchConsolidationLines.class);
        query.addTableAnd(InventoryJournalLines.class.getName(), "movementJournalLineRecordId", InventoryBatchConsolidationLines.class.getName(), "recordID");
        query.addTableAnd(InventoryJournalMaster.class.getName(), "journalNumber", InventoryJournalLines.class.getName(), "journalNumber");
        query.addAnd("consolidationNumber", consolidationNumber, InventoryBatchConsolidationLines.class.getName());
        query.addAnd("journalStatus", JournalStatus.APPROVED.toString(), InventoryJournalMaster.class.getName());
        query.addField("journalNumber", InventoryJournalMaster.class.getName());
        query.addField("journalStatus", InventoryJournalMaster.class.getName());
        query.addGroupBy(InventoryJournalMaster.class.getName(), "journalNumber");
        List<Object[]> movementJournals = util.executeGeneralSelectQuery(query, userData);

        for (Object[] journal : movementJournals) {
            if (!JournalStatus.POSTED.toString().equals(journal[1])) {
                Logger.getLogger("emc").log(Level.SEVERE, "Movement journal " + journal[0] + " still is in the " + journal[1] + " state.", userData);
                postConsolidation = false;
            }
        }

        query = new EMCQuery(enumQueryTypes.SELECT, InventoryBatchConsolidationLines.class);
        query.addTableAnd(InventoryJournalLines.class.getName(), "transferJournalLineRecordId", InventoryBatchConsolidationLines.class.getName(), "recordID");
        query.addTableAnd(InventoryJournalMaster.class.getName(), "journalNumber", InventoryJournalLines.class.getName(), "journalNumber");
        query.addAnd("consolidationNumber", consolidationNumber, InventoryBatchConsolidationLines.class.getName());
        query.addAnd("journalStatus", JournalStatus.APPROVED.toString(), InventoryJournalMaster.class.getName());
        query.addField("journalNumber", InventoryJournalMaster.class.getName());
        query.addField("journalStatus", InventoryJournalMaster.class.getName());
        query.addGroupBy(InventoryJournalMaster.class.getName(), "journalNumber");
        List<Object[]> transferJournals = util.executeGeneralSelectQuery(query, userData);

        for (Object[] journal : movementJournals) {
            if (!JournalStatus.POSTED.toString().equals(journal[1])) {
                Logger.getLogger("emc").log(Level.SEVERE, "Transfer journal " + journal[0] + " still is in the " + journal[1] + " state.", userData);
                postConsolidation = false;
            }
        }

        if (postConsolidation) {
            consolidationMaster.setConsolidationStatus(BatchConsolidationStatus.POSTED.toString());
            consolidationMaster.setPostedBy(userData.getUserName());
            consolidationMaster.setPostedDate(Functions.nowDate());
            update(consolidationMaster, userData);
            return true;
        } else {
            throw new EMCEntityBeanException("Not all of the backing journal have been posted.");
        }
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        InventoryBatchConsolidationMaster record = (InventoryBatchConsolidationMaster) iobject;

        if (isBlank(record.getWarehouse())) {
            InventoryParameters param = paramBean.getInventoryParameters(userData);
            if (param != null) {
                record.setWarehouse(param.getConsolidationWarehouse());
            }
        }

        return super.insert(record, userData);
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        InventoryBatchConsolidationMaster record = (InventoryBatchConsolidationMaster) uobject;
        InventoryBatchConsolidationMaster persistedRecord = (InventoryBatchConsolidationMaster) util.findDetachedPersisted(record, userData);

        if (BatchConsolidationStatus.POSTED.toString().equals(persistedRecord.getConsolidationStatus())) {
            throw new EMCEntityBeanException("You are not allowed to update a batch consolidation in the " + persistedRecord.getConsolidationStatus() + " state.");
        }

        return super.update(uobject, userData);
    }

    @Override
    public Object delete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        InventoryBatchConsolidationMaster record = (InventoryBatchConsolidationMaster) dobject;

        if (BatchConsolidationStatus.APPROVED.toString().equals(record.getConsolidationStatus())
            || BatchConsolidationStatus.POSTED.toString().equals(record.getConsolidationStatus())) {
            throw new EMCEntityBeanException("You are not allowed to delete a batch consolidation in the " + record.getConsolidationStatus() + " state.");
        }

        return super.delete(dobject, userData);
    }

    private void createLogActivity(List<String> unallowcatedBatches, Map<String, List<Object[]>> batchMap, String batchConsolidationActivityUser, EMCUserData userData) throws EMCEntityBeanException {
        List<String> logMessages = new ArrayList<String>();
        List<Object[]> batchStock;
        StringBuilder msg;
        for (String batch : unallowcatedBatches) {
            batchStock = batchMap.get(batch);
            if (batchStock != null && !batchStock.isEmpty()) {
                msg = new StringBuilder();
                msg.append("---------------------------------------------------").append("\n");
                msg.append("Unallocated Batch: ").append(batch).append("\n");
                msg.append("\n");
                for (Object[] stock : batchStock) {
                    msg.append("Item: ").append(stock[0]).append("\n");
                    msg.append("Config: ").append(stock[1]).append("\n");
                    msg.append("Colour: ").append(stock[3]).append("\n");
                    msg.append("Size: ").append(stock[2]).append("\n");
                    msg.append("Warehouse: ").append(stock[4]).append("\n");
                    msg.append("Location: ").append(stock[5]).append("\n");
                    msg.append("Quantity: ").append(stock[9]).append("\n");
                    msg.append("\n");
                }
                msg.append("---------------------------------------------------").append("\n");
                logMessages.add(msg.toString());
            }
        }

        if (!logMessages.isEmpty()) {
            WorkFlowActivity activity = new WorkFlowActivity();
            activity.setEmployeeNumber(batchConsolidationActivityUser);
            activity.setActivityId("Batch Consolidation - " + Functions.nowDate());
            activity.setDescription("Batch Consolidation");
            activity.setStartDate(Functions.nowDate());
            activityBean.insert(activity, userData);

            fileHandlerBean.attachFileToRecord(activity, "Batch Consolidation", logMessages, "BatchConsolidation.txt", userData);
        }
    }
}
