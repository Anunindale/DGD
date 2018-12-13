/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.pop.grnlabels;

import emc.bus.inventory.dimensions.InventoryDimension3Local;
import emc.bus.inventory.dimensions.InventoryDimensionTableLocal;
import emc.bus.pop.POPSupplierLocal;
import emc.bus.pop.grnreprint.POPGRNReprintTempLocal;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.entity.inventory.dimensions.InventoryDimensionTable;
import emc.entity.inventory.dimensions.InventoryItemDimensionGroup;
import emc.entity.inventory.serialbatch.InventorySerialBatch;
import emc.entity.pop.POPPurchaseOrderMaster;
import emc.entity.pop.POPSuppliers;
import emc.entity.pop.grnreprint.POPGRNReprintTemp;
import emc.entity.pop.journals.POPSupplierReceivedJournalLines;
import emc.entity.pop.journals.POPSupplierReceivedJournalMaster;
import emc.entity.pop.posting.POPPurchasePostLines;
import emc.entity.pop.posting.POPPurchasePostMaster;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.pop.posting.SBType;
import emc.framework.EMCBusinessBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.server.utility.EMCServerUtilityLocal;
import java.util.ArrayList;
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
public class LabelsBean extends EMCBusinessBean implements LabelsLocal {

    @EJB
    private POPSupplierLocal suppBean;
    @EJB
    private InventoryDimension3Local dim3Local;
    @EJB
    private POPGRNReprintTempLocal grnBean;
    @EJB
    private InventoryDimensionTableLocal dimBean;

    @Override
    public String getReportResult(List<Object> parameters, EMCUserData userData) {
        List<Object> results = util.executeGeneralSelectQuery(parameters.get(1).toString(), userData);
        return printGRNLables(manipulateQueryResult(results, userData));
    }

    private List<Object> manipulateQueryResult(List<Object> queryResult, EMCUserData userData) {
        POPPurchasePostLines line;
        LabelsDS ds;
        MergerList ret = new MergerList(util, userData);
        List<InventorySerialBatch> serialBatch;
        String purchaseOrderId = null;
        int count = 0;
        EMCQuery query;
        POPPurchasePostMaster master = null;
        for (Object o : queryResult) {
            count++;
            line = (POPPurchasePostLines) o;
            if (purchaseOrderId == null) {
                query = new EMCQuery(enumQueryTypes.SELECT, POPPurchasePostMaster.class.getName());
                query.addAnd("postMasterId", line.getPostMasterId());
                master = (POPPurchasePostMaster) util.executeSingleResultQuery(query, userData);
                purchaseOrderId = master.getPurchaseOrderId();
            }
            query = new EMCQuery(enumQueryTypes.SELECT, POPSupplierReceivedJournalLines.class.getName());
            query.addTableAnd(POPSupplierReceivedJournalMaster.class.getName(), "receivedId", POPSupplierReceivedJournalLines.class.getName(), "receivedId");
            query.addAnd("documentNumber", master.getDocumentNumber(), POPSupplierReceivedJournalMaster.class.getName());
            query.addAnd("transactionNumber", line.getTransactionNumber());
            query.addAnd("quantity", 0, EMCQueryConditions.GREATER_THAN);
            POPSupplierReceivedJournalLines srg = (POPSupplierReceivedJournalLines) util.executeSingleResultQuery(query, userData);
            ds = new LabelsDS();
            ds.setGrnNumber(srg.getReceivedId());
            ds.setDateReceived(isBlank(srg.getReceivedDate()) ? "" : Functions.date2String(srg.getReceivedDate()));
            ds = getItemInfo(line, ds, userData);
            ds = getSuppInfo(purchaseOrderId, ds, userData);
            ds.setStandardLocation(line.getStandardLocation());
            ds.setDocumentNumber(master.getDocumentNumber());

            if (serialBatchActive(line.getItemId(), userData)) {
                serialBatch = poplateSerialBatch(line.getPostMasterId(), line.getTransactionNumber(), SBType.PURCHASEORDER.toString(), userData);
                for (InventorySerialBatch rec : serialBatch) {
                    ds.setSerial(rec.getSerial());
                    ds.setBatch(rec.getBatch());
                    double width = dimBean.findWidth(dimBean.getWidthDimRecordID(
                            isBlank(ds.getDimension1()) ? null : ds.getDimension1(), isBlank(ds.getDimension2()) ? null : ds.getDimension2(),
                            isBlank(ds.getDimension3()) ? null : ds.getDimension3(), rec.getBatch(), rec.getSerial(), userData), userData);
                    ds.setItemWidth(String.valueOf(width == 0 ? "" : width));
                    if (line.getNumberLabels() == 1) {
                        ds.setQuantity(rec.getQuantity());
                    }
                    for (int i = 0; i < line.getNumberLabels(); i++) {
                        ret.add((LabelsDS) util.doClone(ds, LabelsDS.class, userData));
                    }
                }
            } else {
                for (int i = 0; i < line.getNumberLabels(); i++) {
                    ret.add((LabelsDS) util.doClone(ds, LabelsDS.class, userData));
                }
            }
        }
        return ret.done();
    }

    private LabelsDS getItemInfo(POPPurchasePostLines line, LabelsDS ds, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class.getName());
        query.addAnd("itemId", line.getItemId());
        InventoryItemMaster item = (InventoryItemMaster) util.executeSingleResultQuery(query, userData);
        ds.setItemReference(isBlank(item.getItemReference()) ? item.getItemId() : item.getItemReference());
        ds.setItemDesc(item.getDescription());
        ds.setItemPrice(String.valueOf(item.getPurchasePrice()));
        ds.setItemUOM(item.getBaseUOM());
        query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimensionTable.class.getName());
        query.addAnd("recordID", line.getDimId());
        query.addField("dimension1Id");
        query.addField("dimension2Id");
        query.addField("dimension3Id");
        Object dims = util.executeSingleResultQuery(query, userData);
        if (dims != null) {
            Object[] dimArray = (Object[]) dims;
            ds.setDimension1((String) dimArray[0]);
            ds.setDimension2((String) dimArray[1]);
            ds.setDimension3((String) dimArray[2]);
            if (!isBlank(ds.getDimension3())) {
                query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension3.class.getName());
                query.addAnd("dimensionId", ds.getDimension3());
                query.addField("description");
                ds.setDim3Desc((String) util.executeSingleResultQuery(query, userData));
            }
        }
        return ds;
    }

    private LabelsDS getSuppInfo(String purchaseId, LabelsDS ds, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPSuppliers.class.getName());
        query.addTableAnd(POPPurchaseOrderMaster.class.getName(), "supplierId", POPSuppliers.class.getName(), "supplier");
        query.addAnd("purchaseOrderId", purchaseId, POPPurchaseOrderMaster.class.getName());
        POPSuppliers supp = (POPSuppliers) util.executeSingleResultQuery(query, userData);
        ds.setSuppCode(supp.getSupplierId());
        ds.setSuppDesc(supp.getSupplierName());
        return ds;
    }

    private List<InventorySerialBatch> poplateSerialBatch(String postId, String transId, String type, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventorySerialBatch.class.getName());
        query.addAnd("masterId", postId);
        query.addAnd("transId", transId);
        query.addAnd("type", type);
        return util.executeGeneralSelectQuery(query, userData);
    }

    private boolean serialBatchActive(String itemId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionGroup.class.getName());
        query.addTableAnd(InventoryItemMaster.class.getName(), "itemDimensionGroupId", InventoryItemDimensionGroup.class.getName(), "dimensionGroup");
        query.addAnd("itemId", itemId, InventoryItemMaster.class.getName());
        InventoryItemDimensionGroup grp = (InventoryItemDimensionGroup) util.executeSingleResultQuery(query, userData);
        return grp.getSerialNumberActive() || grp.getBatchNumberActive();
    }

    public Boolean checkNumLabels(String postMasterId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchasePostLines.class.getName());
        query.addAnd("postMasterId", postMasterId);
        query.addAnd("numberLabels", null);
        if (util.executeGeneralSelectQuery(query, userData).size() != 0) {
            return false;
        } else return true;
    }

    /**
     * Reprints the lables
     * @param theData
     * @param userData
     * @return
     */
    @Override
    public String reprintLables(String query, EMCUserData userData) {
        MergerList ret = new MergerList(util, userData);
        List<POPGRNReprintTemp> results = util.executeGeneralSelectQuery(query, userData);
        LabelsDS ds;

        EMCQuery itemQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class.getName());
        itemQuery.addAnd("companyId", userData.getCompanyId());
        InventoryItemMaster itemMaster;

        for (POPGRNReprintTemp grn : results) {
            if (grn.getNumLables() == 0) {
                continue;
            }
            itemQuery.removeAnd("itemId");
            itemQuery.addAnd("itemId", grn.getItemId());
            itemMaster = (InventoryItemMaster) util.executeSingleResultQuery(itemQuery, userData);
            ds = new LabelsDS();
            ds.setGrnNumber(grn.getGrnumber());
            ds.setItemReference(isBlank(itemMaster.getItemReference()) ? itemMaster.getItemId() : itemMaster.getItemReference());
            ds.setItemDesc(itemMaster.getDescription());
            ds.setItemWidth(String.valueOf(itemMaster.getSpecWidth()));
            ds.setItemPrice(String.valueOf(itemMaster.getPurchasePrice()));
            ds.setSuppCode(grn.getSupplierCode());
            ds.setSuppDesc(suppBean.getSupplierName(grn.getSupplierCode(), userData));
            ds.setDateReceived(Functions.date2String(grn.getDateRecieved()));
            ds.setDimension1(grn.getDimension1());
            ds.setDimension2(grn.getDimension2());
            ds.setDimension3(grn.getDimension3());
            ds.setDim3Desc(dim3Local.findDimensionDescription(grn.getDimension3(), userData));
            ds.setItemUOM(itemMaster.getBaseUOM());
            ds.setSerial(grn.getSerial());
            ds.setBatch(grn.getBatch());
            ds.setItemWidth(String.valueOf(grn.getWidth() == 0 ? "" : grn.getWidth()));
            ds.setStandardLocation(grn.getStandardLocation());
            ds.setDocumentNumber(grn.getDocumentNumber());
            
            for (int i = 0; i < grn.getNumLables(); i++) {
                ret.add(ds);
            }
            try {
                grnBean.delete(grn, userData);
            } catch (EMCEntityBeanException ex) {
                Logger.getLogger("emc").log(Level.SEVERE, ex.getMessage(), userData);
            }
        }
        return printGRNLables(ret.done());
    }

    public String printGRNLables(List theList) {
        StringBuffer buff = new StringBuffer("");
        LabelsDS ds;
        for (Object o : theList) {
            ds = (LabelsDS) o;
            buff.append("~BARCODESTART~L");
            buff.append("H20");
            buff.append("~BARCODERETURN~");
            buff.append("PG");
            buff.append("~BARCODERETURN~");
            buff.append("D11");
            buff.append("~BARCODERETURN~");
            buff.append("222100004500210" + doSpacing("GRN NUMBER", ds.getGrnNumber() + "  (" + ds.getDocumentNumber() + ")"));
            buff.append("222100004500200" + doSpacing("ITEM", ds.getItemReference()));
            buff.append("222100004500190" + doSpacing("", ds.getItemDesc()));
            buff.append("222100004500180" + doSpacing("CONFIG", ds.getDimension1()));
            buff.append("222100004500170" + doSpacing("COLOUR", ds.getDimension3()));
            buff.append("222100004500160" + doSpacing("", ds.getDim3Desc()));
            buff.append("222100004500150" + doSpacing("SIZE", ds.getDimension2()));
            buff.append("222100004500140" + doSpacing("ITEM WIDTH", ds.getItemWidth()));
            buff.append("222100004500130" + doSpacing("SERIAL NO", ds.getSerial()));
            buff.append("222100004500120" + doSpacing("BATCH NO", ds.getBatch()));
            buff.append("222100004500110" + doSpacing("SUPPLIER", ds.getSuppCode() + "  " + ds.getSuppDesc()));
            buff.append("222100004500100" + doSpacing("DATE RECVD", ds.getDateReceived()));
            buff.append("222100004500090" + doSpacing("QUANTITY", ds.getQuantity() == null ? "" : ds.getQuantity().toString()));
            buff.append("222100004500080" + doSpacing("LOCATION", ds.getStandardLocation()));
//            buff.append("222100004500080" + "Printed with EMC by ASD");
            buff.append("E");
            buff.append("~BARCODERETURN~");
        }
        return buff.toString();
    }

    private String doSpacing(String title, String value) {
        //Change to ajust spacing
        int totalSpace = 200;
        int longestTitle = 10;
        String seperator = ":  ";
        //Change to ajust spacing
        int titleSpace = longestTitle + seperator.length();
        int valueSpace = totalSpace - titleSpace;
        int spacesToAdd = longestTitle - title.length();
        StringBuilder theString = new StringBuilder();
        for (int i = 0; i < spacesToAdd; i++) {
            theString.append(" ");
        }
        theString.append(title);
        theString.append(seperator);
        if (!isBlank(value)) {
            theString.append(value.substring(0, valueSpace > value.length() ? value.length() : valueSpace));
        }
        theString.append("~BARCODERETURN~");
        return theString.toString();
    }
}

class MergerList {

    private EMCServerUtilityLocal util;
    private EMCUserData userData;
    private List<LabelsDS> tempList;
    private List<LabelsDS> retList;

    public MergerList(EMCServerUtilityLocal util, EMCUserData userData) {
        tempList = new ArrayList<LabelsDS>();
        retList = new ArrayList<LabelsDS>();
        this.util = util;
        this.userData = userData;
    }

    public void add(LabelsDS ds) {
//        tempList.add(ds);
//        if (tempList.size() == 2) {
//            retList.add(mergeDSRecords(tempList));
//            tempList.clear();
//        }
        retList.add(ds);
    }

    public List done() {
//        if (tempList.size() != 0) {
//            retList.add(tempList.get(0));
//        }
        return retList;
    }

    private LabelsDS mergeDSRecords(List<LabelsDS> tempList) {
        LabelsDS ds = tempList.get(0);
        LabelsDS tempDS = tempList.get(1);
        ds.setBatch_2(tempDS.getBatch());
        ds.setDateReceived_2(tempDS.getDateReceived());
        ds.setDimension1_2(tempDS.getDimension1());
        ds.setDimension2_2(tempDS.getDimension2());
        ds.setDimension3_2(tempDS.getDimension3());
        ds.setGrnNumber_2(tempDS.getGrnNumber());
        ds.setItemDesc_2(tempDS.getItemDesc());
        ds.setItemPrice_2(tempDS.getItemPrice());
        ds.setItemReference_2(tempDS.getItemReference());
        ds.setItemUOM_2(tempDS.getItemUOM());
        ds.setItemWidth_2(tempDS.getItemWidth());
        ds.setSerial_2(tempDS.getSerial());
        ds.setSuppCode_2(tempDS.getSuppCode());
        ds.setSuppDesc_2(tempDS.getSuppDesc());
        ds.setDim3Desc_2(tempDS.getDim3Desc());
        ds.setQuantity_2(tempDS.getQuantity());
        return (LabelsDS) util.doClone(ds, LabelsDS.class, userData);
    }
}
