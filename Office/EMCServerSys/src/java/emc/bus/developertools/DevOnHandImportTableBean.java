/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.developertools;

import emc.bus.gl.vatcodes.GLVATCodeLocal;
import emc.bus.inventory.dimensions.InventoryDimensionTableLocal;
import emc.bus.inventory.journals.InventoryJournalLinesLocal;
import emc.bus.inventory.journals.InventoryJournalMasterLocal;
import emc.bus.inventory.register.normanregister.InventoryRegisterLocal;
import emc.bus.sop.pricearangements.SOPPriceArangementsLocal;
import emc.entity.developertools.DevOnHandImportTable;
import emc.entity.developertools.DevProgressItemConversionTable;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryDimensionTable;
import emc.entity.inventory.journals.InventoryJournalLines;
import emc.entity.inventory.journals.InventoryJournalMaster;
import emc.entity.inventory.register.InventoryRegister;
import emc.entity.inventory.stocksettlement.InventoryStockSettlementHistory;
import emc.entity.inventory.transactions.InventorySummary;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.entity.sop.SOPCustomers;
import emc.entity.sop.SOPSalesOrderLines;
import emc.entity.sop.SOPSalesOrderMaster;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.sop.SalesOrderTypes;
import emc.enums.sop.salesorders.SalesOrderStatus;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.functions.StopWatchFactory;
import java.math.BigDecimal;
import java.util.Calendar;
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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @Name         : DevOnHandImportTableBean
 *
 * @Date         : 06 Jul 2009
 * 
 * @Description  : N & L specific bean.  Used to import on hand items.
 *
 * @author       : riaan
 */
@Stateless
public class DevOnHandImportTableBean extends EMCEntityBean implements DevOnHandImportTableLocal {

    @EJB
    private InventoryJournalLinesLocal journalLinesBean;
    @EJB
    private InventoryRegisterLocal registerBean;
    @EJB
    private DevOnHandImportTableLocal thisBean;
    @EJB
    private InventoryJournalMasterLocal journalMaster;
    @EJB
    private InventoryDimensionTableLocal dimBean;
    @EJB
    private SOPPriceArangementsLocal priceArrangementBean;
    @EJB
    private GLVATCodeLocal vatCodeBean;
    @PersistenceContext
    private EntityManager em;

    /** Creates a new instance of DevOnHandImportTableBean. */
    public DevOnHandImportTableBean() {
    }

    public void finishImport(String journalNumber, EMCUserData userData) {
        EMCQuery masterQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalMaster.class.getName());
        masterQuery.addAnd("journalNumber", journalNumber);
        InventoryJournalMaster masterRecord = (InventoryJournalMaster) util.executeSingleResultQuery(masterQuery, userData);
        String coolSelect = "SELECT new DevOnHandImportTable(u.itemId,u.config, u.colour,u.sizeDim, u.batch, u.serial, u.location, u.warehouse ,SUM(u.qty), u.importDate, u.price) FROM DevOnHandImportTable u " +
                "GROUP BY u.itemId,u.config, u.colour, u.sizeDim, u.importDate";
        List<DevOnHandImportTable> mainList = util.executeGeneralSelectQuery(coolSelect, userData);
        List<DevOnHandImportTable> serialList;

        InventoryJournalLines journalLine;
        InventoryItemMaster item;
        InventoryRegister registerRec;

        EMCQuery itemQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class.getName());
        EMCQuery importedQuery = new EMCQuery(enumQueryTypes.SELECT, DevOnHandImportTable.class.getName());

        double lineNo = 0;
        int importTableCount = 0;
        int journalCount = 0;
        String originalDesc = masterRecord.getJournalDescription();
        for (DevOnHandImportTable importedRec : mainList) {
            if (importTableCount >= 1500) {
                journalCount++;
                importTableCount = 0;
                masterRecord = (InventoryJournalMaster) this.doClone(masterRecord, userData);
                masterRecord.setJournalDescription(originalDesc + "-" + journalCount);
                masterRecord.setJournalNumber(null);
                try {
                    masterRecord = thisBean.insertJournalMaster(masterRecord, userData);
                } catch (Exception E) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to create journal master", userData);
                }

                lineNo = 0;
                journalNumber = masterRecord.getJournalNumber();
            }
            journalLine = new InventoryJournalLines();
            journalLine.setJournalNumber(journalNumber);
            journalLine.setLineDate(importedRec.getImportDate());
            journalLine.setContraAccount(masterRecord.getJournalContraAccount());
            journalLine.setContraType(masterRecord.getJournalContraType());
            //journalLine set dims
            journalLine.setDimension3(importedRec.getColour());
            journalLine.setDimension2(importedRec.getSizeDim());
            journalLine.setDimension1(importedRec.getConfig());



            journalLine.setWarehouse(importedRec.getWarehouse());
            journalLine.setQuantity(importedRec.getQty());

            itemQuery.removeAnd("itemReference");
            itemQuery.addAnd("itemReference", importedRec.getItemId());
            item = (InventoryItemMaster) util.executeSingleResultQuery(itemQuery, userData);
            journalLine.setCost(util.compareDouble(importedRec.getPrice(), 0.0) == 0 ? 0.00000001 : importedRec.getPrice());
            journalLine.setItemId(item.getItemId());
            try {
                lineNo += 10;
                journalLine.setLineNo(lineNo);
                journalLine = thisBean.insertJournalLine(journalLine, userData);
            } catch (EMCEntityBeanException ex) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to create journal line for item " + importedRec.getItemId() + " Config:" + importedRec.getConfig() + " Colour:" + importedRec.getColour() + " Size:" + importedRec.getSizeDim(), userData);
                continue;
            }
            importedQuery.removeAnd("itemId");
            importedQuery.removeAnd("colour");
            importedQuery.removeAnd("sizeDim");
            importedQuery.removeAnd("config");
            importedQuery.removeAnd("importDate");
            importedQuery.addAnd("itemId", importedRec.getItemId());
            importedQuery.addAnd("config", importedRec.getConfig());
            importedQuery.addAnd("colour", importedRec.getColour());
            importedQuery.addAnd("sizeDim", importedRec.getSizeDim());
            importedQuery.addAnd("importDate", Functions.date2String(importedRec.getImportDate(), "yyyy-MM-dd"));
            serialList = util.executeGeneralSelectQuery(importedQuery, userData);
            for (DevOnHandImportTable serialBatchRec : serialList) {
                importTableCount++;
                registerRec = new InventoryRegister();
                registerRec.setMasterId(journalNumber);
                registerRec.setTransId(journalLine.getTransId());
                registerRec.setBatch(serialBatchRec.getBatch());
                registerRec.setSerial(serialBatchRec.getSerial());
                registerRec.setWarehouse(serialBatchRec.getWarehouse());
                registerRec.setLocation(serialBatchRec.getLocation());
                registerRec.setQuantity(serialBatchRec.getQty());
                registerRec.setWidth(serialBatchRec.getWidth());
                registerRec.setUom(serialBatchRec.getWUOM());
                registerRec.setType("JRN");
                try {
                    thisBean.insertRegister(registerRec, serialBatchRec, userData);
                } catch (EMCEntityBeanException ex) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to create journal regestration for item " + journalLine.getItemId() + "!", userData);
                    continue;
                }
            }
            if (serialList.isEmpty()) {
                try {
                    super.delete(importedRec, userData);
                } catch (EMCEntityBeanException ex) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to delete temp record!", userData);
                    continue;
                }
            }
        }
    }
    /*public void finishImport(String journalNumber, EMCUserData userData) {
    EMCQuery masterQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalMaster.class.getName());
    masterQuery.addAnd("journalNumber", journalNumber);
    InventoryJournalMaster masterRecord = (InventoryJournalMaster) util.executeSingleResultQuery(masterQuery, userData);
    String coolSelect = "SELECT new DevOnHandImportTable(u.itemId, u.colourOld, u.batch, u.serial, u.location, u.warehouse ,SUM(u.qty), u.importDate, u.price) FROM DevOnHandImportTable u " +
    "GROUP BY u.itemId, u.colourOld, u.importDate";
    List<DevOnHandImportTable> mainList = util.executeGeneralSelectQuery(coolSelect, userData);
    List<DevOnHandImportTable> serialList;
    List<DevOldColourConversionTable> colourList;
    
    InventoryJournalLines journalLine;
    DevOldColourConversionTable colourRec;
    InventoryItemMaster item;
    InventoryRegister registerRec;
    
    EMCQuery colourQuery = new EMCQuery(enumQueryTypes.SELECT, DevOldColourConversionTable.class.getName());
    EMCQuery itemQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class.getName());
    EMCQuery importedQuery = new EMCQuery(enumQueryTypes.SELECT, DevOnHandImportTable.class.getName());
    
    double lineNo = 0;
    for (DevOnHandImportTable importedRec : mainList) {
    
    journalLine = new InventoryJournalLines();
    journalLine.setJournalNumber(journalNumber);
    journalLine.setLineDate(importedRec.getImportDate());
    journalLine.setContraAccount(masterRecord.getJournalContraAccount());
    journalLine.setContraType(masterRecord.getJournalContraType());
    
    colourQuery.removeAnd("oldColour");
    colourQuery.addAnd("oldColour", importedRec.getColourOld());
    colourQuery.setSelectDistinctAll(true);
    
    colourList = util.executeGeneralSelectQuery(colourQuery, userData);
    if (colourList != null && !colourList.isEmpty()) {
    colourRec = colourList.get(0);
    journalLine.setDimension3(colourRec.getNewColour());
    } else {
    Logger.getLogger("emc").log(Level.SEVERE, "Item:" + importedRec.getItemId() + "Conversion for old colour " + importedRec.getColourOld() + " is wrong!", userData);
    continue;
    }
    
    
    journalLine.setWarehouse(importedRec.getWarehouse());
    journalLine.setQuantity(importedRec.getQty());
    
    itemQuery.removeAnd("itemReference");
    itemQuery.addAnd("itemReference", importedRec.getItemId());
    item = (InventoryItemMaster) util.executeSingleResultQuery(itemQuery, userData);
    journalLine.setCost(util.compareDouble(importedRec.getPrice(),0.0) == 0 ? 5.99:importedRec.getPrice());
    journalLine.setItemId(item.getItemId());
    try {
    lineNo += 10;
    journalLine.setLineNo(lineNo);
    journalLine = thisBean.insertJournalLine(journalLine, userData);
    } catch (EMCEntityBeanException ex) {
    Logger.getLogger("emc").log(Level.SEVERE, "Failed to create journal line for item " + importedRec.getItemId() + " Colour:" + colourRec.getNewColour(), userData);
    continue;
    }
    importedQuery.removeAnd("itemId");
    importedQuery.removeAnd("colourOld");
    importedQuery.removeAnd("importDate");
    importedQuery.addAnd("itemId", importedRec.getItemId());
    importedQuery.addAnd("colourOld", importedRec.getColourOld());
    importedQuery.addAnd("importDate", Functions.date2String(importedRec.getImportDate(),"yyyy-MM-dd"));
    serialList = util.executeGeneralSelectQuery(importedQuery, userData);
    for (DevOnHandImportTable serialBatchRec : serialList) {
    registerRec = new InventoryRegister();
    registerRec.setMasterId(journalNumber);
    registerRec.setTransId(journalLine.getTransId());
    registerRec.setBatch(serialBatchRec.getBatch());
    registerRec.setSerial(serialBatchRec.getSerial());
    registerRec.setWarehouse(serialBatchRec.getWarehouse());
    registerRec.setLocation(serialBatchRec.getLocation());
    registerRec.setQuantity(serialBatchRec.getQty());
    registerRec.setWidth(serialBatchRec.getWidth());
    registerRec.setUom(serialBatchRec.getWUOM());
    registerRec.setType("JRN");
    try {
    thisBean.insertRegister(registerRec, serialBatchRec, userData);
    } catch (EMCEntityBeanException ex) {
    Logger.getLogger("emc").log(Level.SEVERE, "Failed to create journal regestration for item " + journalLine.getItemId() + "!", userData);
    continue;
    }
    }
    if (serialList.isEmpty()) {
    try {
    super.delete(importedRec, userData);
    } catch (EMCEntityBeanException ex) {
    Logger.getLogger("emc").log(Level.SEVERE, "Failed to delete temp record!", userData);
    continue;
    }
    }
    }
    }*/
    //overwritten to fix picking list

    public void finishImportSize(String journalNumber, EMCUserData userData) {

        //if(lineList.size() == 0){
        EMCQuery slinesQuery = new EMCQuery(enumQueryTypes.SELECT, SOPSalesOrderLines.class);
        slinesQuery.addAnd("salesOrderNo", journalNumber);
        List<SOPSalesOrderLines> slineList = util.executeGeneralSelectQuery(slinesQuery, userData);
        for (SOPSalesOrderLines line : slineList) {
            EMCQuery transQ = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
            transQ.addAnd("transId", line.getInventTransId());
            transQ.addAnd("closed", "CLOSEISSUE");
            transQ.addAnd("status", "RESERVED");
            List<InventoryTransactions> transList = util.executeGeneralSelectQuery(transQ, userData);
            for (InventoryTransactions trans : transList) {

                //find the summary
                EMCQuery sq = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class);
                sq.addAnd("inventoryTransRef", trans.getRecordID());
                InventorySummary sum = (InventorySummary) util.executeSingleResultQuery(sq, userData);

                //find the in transaction that closed this out
                EMCQuery inTx = new EMCQuery(enumQueryTypes.SELECT, InventoryStockSettlementHistory.class);
                inTx.addAnd("outTxUsedInSettlement", trans.getRecordID());
                inTx.addField("inTxRecordId");
                List inTxL = util.executeGeneralSelectQuery(inTx, userData);
                if (inTxL.size() > 0) {
                    InventoryTransactions inTrans = (InventoryTransactions) util.findPersisted(InventoryTransactions.class, inTxL.get(0), userData);
                    if (inTrans.getClosedFlag() == false) {
                        if (util.compareDouble(inTrans.getClosedQty(), 0) == 0) {
                            //see if there is anything to do
                            InventoryDimensionTable dim = dimBean.getDimensionRecord(inTrans.getItemDimId(), userData);
                            //select inSum
                            EMCQuery inSq = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class);
                            inSq.addAnd("inventoryTransRef", inTrans.getRecordID());
                            InventorySummary inSum = (InventorySummary) util.executeSingleResultQuery(inSq, userData);
                            //update the in transaction
                            inTrans.setClosed("CLOSEISSUE");
                            //update summaries
                            if (dim.getLocationId().equalsIgnoreCase("REC")) {
                                sum.setRecAvailable(sum.getPhysicalReserved() * -1);
                            } else {
                                sum.setPhysicalAvailable(sum.getPhysicalReserved() * -1);
                            }
                            //update out
                            //update the out trans
                            trans.setClosedQty(0d);
                            try {
                                super.update(trans, userData);
                                super.update(sum, userData);
                                super.update(inTrans, userData);
                                super.update(inSum, userData);
                            } catch (EMCEntityBeanException exp) {
                                this.logMessage(Level.SEVERE, "Failed to update Transaction:" + trans.getItemId() + "," + dim.getDimension1Id() + "," + dim.getDimension2Id() + "," + dim.getDimension3Id(), userData);
                            }
                        } else {
                            InventoryDimensionTable dim = dimBean.getDimensionRecord(inTrans.getItemDimId(), userData);
                            //select inSum
                            EMCQuery inSq = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class);
                            inSq.addAnd("inventoryTransRef", inTrans.getRecordID());
                            InventorySummary inSum = (InventorySummary) util.executeSingleResultQuery(inSq, userData);
                            //update the in transaction
                            inTrans.setClosedQty(inTrans.getClosedQty() - trans.getQuantity());
                            inTrans.setClosed("CLOSEISSUE");
                            //update summaries
                            if (dim.getLocationId().equalsIgnoreCase("REC")) {
                                inSum.setRecAvailable(inSum.getPhysicalAvailable() + trans.getQuantity());
                                sum.setRecAvailable(sum.getPhysicalReserved() * -1);
                            } else {
                                inSum.setPhysicalAvailable(inSum.getPhysicalAvailable() + trans.getQuantity());
                                sum.setPhysicalAvailable(sum.getPhysicalReserved() * -1);
                            }
                            inSum.setPhysicalTotal(inSum.getPhysicalTotal() + trans.getQuantity());
                            //update out
                            //update the out trans
                            trans.setClosedQty(0d);
                            try {
                                super.update(trans, userData);
                                super.update(sum, userData);
                                super.update(inTrans, userData);
                                super.update(inSum, userData);
                            } catch (EMCEntityBeanException exp) {
                                this.logMessage(Level.SEVERE, "Failed to update Transaction:" + trans.getItemId() + "," + dim.getDimension1Id() + "," + dim.getDimension2Id() + "," + dim.getDimension3Id(), userData);
                            }
                        }
                    } else {

                        InventoryDimensionTable dim = dimBean.getDimensionRecord(inTrans.getItemDimId(), userData);
                        InventorySummary tempSum = new InventorySummary();
                        tempSum.setItemId(inTrans.getItemId());
                        tempSum.setInventoryTransRef(inTrans.getRecordID());
                        tempSum.setItemDimId(inTrans.getItemDimId());
                        tempSum.setClosed("CLOSEISSUE");
                        tempSum.setDimension1(dim.getDimension1Id());
                        tempSum.setDimension2(dim.getDimension2Id());
                        tempSum.setDimension3(dim.getDimension3Id());
                        tempSum.setWarehouse(dim.getWarehouseId());
                        tempSum.setBatch(dim.getBatchId());
                        tempSum.setLocation(dim.getLocationId());
                        tempSum.setSerialNo(dim.getItemSerialId());
                        if (dim.getLocationId().equalsIgnoreCase("REC")) {
                            tempSum.setRecAvailable(trans.getQuantity());
                            sum.setRecAvailable(sum.getPhysicalReserved() * -1);
                        } else {
                            tempSum.setPhysicalAvailable(trans.getQuantity());
                            sum.setPhysicalAvailable(sum.getPhysicalReserved() * -1);
                        }
                        tempSum.setPhysicalTotal(inTrans.getQuantity());
                        //update InTrans
                        inTrans.setClosed("CLOSEISSUE");
                        inTrans.setClosedDate(null);
                        inTrans.setClosedFlag(false);
                        inTrans.setClosedQty(inTrans.getClosedQty() - trans.getQuantity());
                        //update out
                        //update the out trans
                        trans.setClosedQty(0d);

                        try {
                            super.update(sum, userData);
                            super.insert(tempSum, userData);
                            super.update(trans, userData);
                            super.update(inTrans, userData);
                        } catch (EMCEntityBeanException ex) {
                            this.logMessage(Level.SEVERE, "Failed to update Transaction:" + trans.getItemId() + "," + dim.getDimension1Id() + "," + dim.getDimension2Id() + "," + dim.getDimension3Id(), userData);
                        }
                    }
                }
            }
        }
        this.logMessage(Level.INFO, "Done", userData);



    }

    public void finishImportElastic(String journalNumber, EMCUserData userData) {
//        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, ProductionBundle.class);
//        query.addTableAnd(ProductionAggregateWorksOrder.class.getName(), "awoId", ProductionBundle.class.getName(), "awoId");
//        query.addAnd("status", BundleStatus.COMPLETE.toString(), ProductionBundle.class.getName());
//        query.addAnd("modifiedDate", "2012-01-17", ProductionBundle.class.getName());
//        query.addAnd("modifiedTime", "13:30:00", ProductionBundle.class.getName(), EMCQueryConditions.GREATER_THAN_EQ);
//        query.addTableAsField(ProductionBundle.class.getName());
//        query.addField("workCenterId", ProductionAggregateWorksOrder.class.getName());
//        List<Object[]> bundleList = util.executeGeneralSelectQuery(query, userData);
//
//        for (Object[] bundle : bundleList) {
//            try {
//                reportLogBean.doStatusLog((ProductionBundle) bundle[0], (String) bundle[1], userData);
//                logMessage(Level.SEVERE, "AWO: " + ((ProductionBundle) bundle[0]).getAwoId() + "  -  Bundle: " + ((ProductionBundle) bundle[0]).getBundleNo(), userData);
//            } catch (EMCEntityBeanException ex) {
//                logMessage(Level.SEVERE, "Failed to create log", userData);
//                break;
//            }
//        }





        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2012);
        cal.set(Calendar.MONTH, Calendar.MARCH);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date date = cal.getTime();
        //2012-02-29 Update prices on Sales Order lines to what is currently on price file
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesOrderLines.class);
        query.addTableAsField(SOPSalesOrderLines.class.getName());
        query.addTableAnd(SOPSalesOrderMaster.class.getName(), "salesOrderNo", SOPSalesOrderLines.class.getName(), "salesOrderNo");
        query.addTableAnd(InventoryItemMaster.class.getName(), "itemId", SOPSalesOrderLines.class.getName(), "itemId");
        query.addTableAnd(SOPCustomers.class.getName(), "customerNo", SOPSalesOrderMaster.class.getName(), "customerId");
        query.addField("customerNo", SOPSalesOrderMaster.class.getName());
        query.addAnd("salsesOrderStatus", SalesOrderStatus.BLANKET_ORDER, SOPSalesOrderMaster.class.getName(), EMCQueryConditions.NOT);
        query.addAnd("salsesOrderStatus", SalesOrderStatus.CANCELLED, SOPSalesOrderMaster.class.getName(), EMCQueryConditions.NOT);
        query.addAnd("salsesOrderStatus", SalesOrderStatus.INVOICED, SOPSalesOrderMaster.class.getName(), EMCQueryConditions.NOT);
        //Only include men's items
        query.addAnd("classificationClassGroup1", "L", InventoryItemMaster.class.getName());
        //Only include price group 1
        query.addAnd("priceGroup", "1", SOPCustomers.class.getName());
        //Exclude blanket orders
        query.addAnd("salesOrderType", SalesOrderTypes.BLANKET_ORDER, SOPSalesOrderMaster.class.getName(), EMCQueryConditions.NOT);
        //Only adjust order with a date after 2012-03-01
        query.addAnd("requiredDate", date, EMCQueryConditions.GREATER_THAN_EQ);

        System.out.println("Sales Order Query: " + query.getNativeQuery());

        List<Object[]> salesOrderLines = (List<Object[]>) util.executeGeneralSelectQuery(query, userData);
        util.detachEntities(userData);
        Map<String, Double> vatMap = new HashMap<String, Double>();

        StopWatchFactory fact = new StopWatchFactory(true);
        int counter = 0;
        for (Object[] salesOrderInfo : salesOrderLines) {
            counter++;
            System.out.println("Processing " + counter + " of " + salesOrderLines.size());
            SOPSalesOrderLines salesOrderLine = (SOPSalesOrderLines) salesOrderInfo[0];
            String customerNo = (String) salesOrderInfo[1];

            fact.start("vat");
            if (!vatMap.containsKey(salesOrderLine.getVatCode())) {
                double vatPerc = vatCodeBean.getVatPercentage(salesOrderLine.getVatCode(), userData);
                vatMap.put(salesOrderLine.getVatCode(), vatPerc);
            }
            double vatPerc = vatMap.get(salesOrderLine.getVatCode());
            System.out.println("Fetched VAT: " + fact.stop("vat"));
            if (salesOrderLine.getRequiredDate().compareTo(date) < 0) {
                //Update date to new required date.
                salesOrderLine.setRequiredDate(date);
            }
            try {
                fact.start("price");
                //Fetch new price.  Copied from Sales Order lines bean.
                BigDecimal price = priceArrangementBean.findItemPrice(customerNo, salesOrderLine.getItemId(),
                        salesOrderLine.getDimension1(), salesOrderLine.getDimension2(), salesOrderLine.getDimension3(), salesOrderLine.getRequiredDate(), salesOrderLine.getQuantity(), userData);
                System.out.println("Fetched price: " + fact.stop("price"));
                salesOrderLine.setPrice(price);

                BigDecimal grossAmount = salesOrderLine.getQuantity().multiply(salesOrderLine.getPrice());
                BigDecimal netAmount = grossAmount.subtract(grossAmount.multiply(salesOrderLine.getDiscountPerc().divide(new BigDecimal(100))));

                salesOrderLine.setVatAmount(netAmount.multiply(new BigDecimal(vatPerc / 100d)));
                BigDecimal totalBeforeDisc = (salesOrderLine.getPrice().multiply(salesOrderLine.getQuantity()));
                salesOrderLine.setLineTotal(totalBeforeDisc.subtract(totalBeforeDisc.multiply(salesOrderLine.getDiscountPerc().divide(new BigDecimal(100)))));

                fact.start("save");
                //We just set the price, so assume that everything is valid and call doUpdate.
                super.doUpdate(salesOrderLine, userData);
                System.out.println("Saved line: " + fact.stop("save"));
            } catch (EMCEntityBeanException ex) {
                ex.printStackTrace();
                logMessage(Level.SEVERE, "Error occured while updating price on " + salesOrderLine.getSalesOrderNo() + ".", userData);
            }

            if (counter % 10 == 0) {
                em.flush();
            }
        }
        em.flush();
        logMessage(Level.INFO, "Prices updated successfully.", userData);



//        EMCQuery q = new EMCQuery(enumQueryTypes.SELECT, InventoryRegister.class);
//        q.addAnd("masterId", journalNumber);
//        q.addAnd("type", "PROD_ASSY");
//        q.addOrderBy("transId");
//        List<InventoryRegister> regLines = util.executeGeneralSelectQuery(q, userData);
//        int num = 1;
//        for(InventoryRegister reg:regLines){
//            reg.setBatch("FG"+journalNumber+"-"+num);
//            num++;
//            try {
//                super.update(reg, userData);
//            } catch (EMCEntityBeanException ex) {
//                Logger.getLogger("emc").log(Level.SEVERE, "Failed to update batch for:"+reg.getTransId(), userData);
//            }
//        }
//        Logger.getLogger("emc").log(Level.INFO, "Done", userData);
//
//
//        String[] data = journalNumber.split(";");
//        String item = data[0];
//        String color = data[1];
//        EMCXMLHandler xml = new EMCXMLHandler();
//        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
//        query.addTableAnd(InventoryDimensionTable.class.getName(), "itemDimId", InventoryTransactions.class.getName(), "recordID");
//        query.addAnd("itemId", item);
//        if (!isBlank(color)) {
//            query.addAnd("dimension3Id", color, InventoryDimensionTable.class.getName());
//        }
//        query.addAnd("closedFlag", true);
//        List<InventoryTransactions> transList = util.executeGeneralSelectQuery(query, userData);
//        for (InventoryTransactions trans : transList) {
//            query = new EMCQuery(enumQueryTypes.SELECT, InventoryStockSettlementHistory.class);
//            switch (InventoryTransactionDirection.fromString(trans.getDirection())) {
//                case IN:
//                    query.addAnd("inTxRecordId", trans.getRecordID());
//                    break;
//                case OUT:
//                    query.addAnd("outTxUsedInSettlement", trans.getRecordID());
//                    break;
//            }
//            List<InventoryStockSettlementHistory> settlementList = util.executeGeneralSelectQuery(query, userData);
//            InventorySummary summary = null;
//            double summaryQty = 0;
//            for (InventoryStockSettlementHistory settlement : settlementList) {
//                if (!isBlank(settlement.getOutXmlSummary())) {
//                    summary = (InventorySummary) xml.XMLToObject(settlement.getOutXmlSummary(), userData);
//                } else if (!isBlank(settlement.getInXmlSummary())) {
//                    summary = (InventorySummary) xml.XMLToObject(settlement.getInXmlSummary(), userData);
//                }
//
//                summaryQty += Math.abs(summary.getPhysicalTotal());
//            }
//            if (util.compareDouble(summaryQty, trans.getClosedQty()) != 0) {
//                System.out.println(trans.getDirection() + " Tx: " + trans.getRecordID() + "      TxQty: " + trans.getClosedQty() + "        Summary Qty: " + summaryQty);
//            }
//        }
    }
    /*public void finishImportElastic(String journalNumber, EMCUserData userData) {
    EMCQuery masterQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalMaster.class.getName());
    masterQuery.addAnd("journalNumber", journalNumber);
    InventoryJournalMaster masterRecord = (InventoryJournalMaster) util.executeSingleResultQuery(masterQuery, userData);
    String coolSelect = "SELECT new DevOnHandImportTable(u.itemId, u.colourOld, u.batch, u.serial, u.location, u.warehouse ,SUM(u.qty), u.price) FROM DevOnHandImportTable u " +
    "GROUP BY u.itemId, u.colourOld";
    List<DevOnHandImportTable> mainList = util.executeGeneralSelectQuery(coolSelect, userData);
    List<DevOnHandImportTable> serialList;
    
    
    InventoryJournalLines journalLine;
    
    InventoryItemMaster item;
    InventoryRegister registerRec;
    
    
    EMCQuery itemQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class.getName());
    EMCQuery importedQuery = new EMCQuery(enumQueryTypes.SELECT, DevOnHandImportTable.class.getName());
    
    double lineNo = 0;
    for (DevOnHandImportTable importedRec : mainList) {
    
    journalLine = new InventoryJournalLines();
    journalLine.setJournalNumber(journalNumber);
    journalLine.setLineDate(new Date());
    journalLine.setContraAccount(masterRecord.getJournalContraAccount());
    journalLine.setContraType(masterRecord.getJournalContraType());
    
    journalLine.setDimension3(importedRec.getColourOld());
    
    journalLine.setWarehouse(importedRec.getWarehouse());
    journalLine.setQuantity(importedRec.getQty());
    
    itemQuery.removeAnd("itemReference");
    itemQuery.addAnd("itemReference", importedRec.getItemId());
    item = (InventoryItemMaster) util.executeSingleResultQuery(itemQuery, userData);
    journalLine.setCost(util.compareDouble(importedRec.getPrice(),0.0) == 0 ? 5.99 : importedRec.getPrice());
    journalLine.setItemId(item.getItemId());
    try {
    lineNo += 10;
    journalLine.setLineNo(lineNo);
    journalLine = thisBean.insertJournalLine(journalLine, userData);
    } catch (EMCEntityBeanException ex) {
    Logger.getLogger("emc").log(Level.SEVERE, "Failed to create journal line for item " + journalLine.getItemId() + "!", userData);
    continue;
    }
    importedQuery.removeAnd("itemId");
    importedQuery.removeAnd("colourOld");
    importedQuery.addAnd("itemId", importedRec.getItemId());
    importedQuery.addAnd("colourOld", importedRec.getColourOld());
    serialList = util.executeGeneralSelectQuery(importedQuery, userData);
    for (DevOnHandImportTable serialBatchRec : serialList) {
    registerRec = new InventoryRegister();
    registerRec.setMasterId(journalNumber);
    registerRec.setTransId(journalLine.getTransId());
    registerRec.setBatch(serialBatchRec.getBatch());
    registerRec.setSerial(serialBatchRec.getSerial());
    registerRec.setWarehouse(serialBatchRec.getWarehouse());
    registerRec.setLocation(serialBatchRec.getLocation());
    registerRec.setQuantity(serialBatchRec.getQty());
    registerRec.setType("JRN");
    try {
    thisBean.insertRegister(registerRec, serialBatchRec, userData);
    } catch (EMCEntityBeanException ex) {
    Logger.getLogger("emc").log(Level.SEVERE, "Failed to create journal regestration for item " + journalLine.getItemId() + "!", userData);
    continue;
    }
    }
    if (serialList.isEmpty()) {
    try {
    super.delete(importedRec, userData);
    } catch (EMCEntityBeanException ex) {
    Logger.getLogger("emc").log(Level.SEVERE, "Failed to delete temp record!", userData);
    continue;
    }
    }
    }
    }*/

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public InventoryJournalMaster insertJournalMaster(InventoryJournalMaster theRecord, EMCUserData userData) throws EMCEntityBeanException {
        journalMaster.insert(theRecord, userData);
        return theRecord;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public InventoryJournalLines insertJournalLine(InventoryJournalLines theRecord, EMCUserData userData) throws EMCEntityBeanException {
        journalLinesBean.insert(theRecord, userData);
        return theRecord;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public InventoryRegister insertRegister(InventoryRegister theRegister, DevOnHandImportTable serialBatchRec, EMCUserData userData) throws EMCEntityBeanException {
        registerBean.insert(theRegister, userData);
        super.delete(serialBatchRec, userData);
        return theRegister;
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        DevOnHandImportTable importTable = (DevOnHandImportTable) iobject;

        if (!isBlank(importTable.getOldItem())) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DevProgressItemConversionTable.class.getName());
            query.addField("emcItem");
            query.addAnd("oldItem", importTable.getOldItem());

            String itemRef = (String) util.executeSingleResultQuery(query, userData);

            if (isBlank(itemRef)) {
                Logger.getLogger("emc").log(Level.SEVERE, "No ref found for old item - " + importTable.getOldItem(), userData);
                throw new EMCEntityBeanException("No ref found for old item - " + importTable.getOldItem());
            } else {
                importTable.setItemId(itemRef);
            }
        }

        return super.insert(iobject, userData);
    }
}
