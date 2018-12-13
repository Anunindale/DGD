/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.inventory.stockjournalreport;

import emc.bus.base.journals.BaseJournalDefinitionLocal;
import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.inventory.journals.datasource.InventoryJournalLinesDSLocal;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryReference;
import emc.entity.inventory.journals.InventoryJournalLines;
import emc.entity.inventory.journals.InventoryJournalMaster;
import emc.entity.inventory.journals.datasource.InventoryJournalLinesDS;
import emc.entity.inventory.register.InventoryRegister;
import emc.entity.inventory.register.InventoryRegisterSuper;
import emc.entity.inventory.register.InventoryRemoveRegister;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.journals.InventoryJournalTypes;
import emc.enums.inventory.register.RegisterFromTypeEnum;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class StockJournalReportBean extends EMCReportBean implements StockJournalReportLocal {

    @EJB
    private InventoryJournalLinesDSLocal linesDSBean;
    @EJB
    private InventoryReferenceLocal referenceBean;
    @EJB
    private BaseJournalDefinitionLocal definitionBean;

    @Override
    public List<Object> getReportResult(List<Object> parameters, EMCUserData userData) {
        List<Object> results = executeQuery(parameters.get(1).toString(), userData);
        return manipulateQueryResult(results, null, userData);
    }

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        InventoryJournalMaster master;
        List<Object> ret = new ArrayList<Object>();

        for (Object o : queryResult) {
            master = (InventoryJournalMaster) o;
            ret.addAll(populateDS(master, userData));
        }
        return ret;
    }

    private List<Object> populateDS(InventoryJournalMaster master, EMCUserData userData) {
        double totalCost = 0;
        StockJournalReportDS ds;
        InventoryJournalLinesDS linesDS;

        //Map supplier references using item id and dimensions.
        Map<String, String> supplierReferences = new HashMap<String, String>();
        //Map item id's to default suppliers
        Map<String, String> defaultSuppliers = new HashMap<String, String>();

        List<Object> ret = new ArrayList<Object>();
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalLines.class.getName());
        query.addAnd("journalNumber", master.getJournalNumber());
        Object oList = util.executeGeneralSelectQuery(query, userData);

        List regList;
        InventoryRegisterSuper reg;
        if (oList != null) {
            List tempLines = (ArrayList) oList;
            for (Object o : tempLines) {
                ds = new StockJournalReportDS();
                linesDS = (InventoryJournalLinesDS) linesDSBean.populateDataSourceFields((InventoryJournalLinesDS) util.convertEntityToDatasource(
                        (InventoryJournalLines) o, InventoryJournalLinesDS.class, userData), userData);
                ds.setJournalNumber(master.getJournalNumber());
                ds.setJournalDesc(master.getJournalDescription());
                ds.setContraType(master.getJournalContraType());
                ds.setContraAccount(master.getJournalContraAccount());
                ds.setJournalDefinition(master.getJournalDefinitionId());
                ds.setEnteredBy(master.getCreatedBy());
                ds.setEnteredDate(Functions.date2String(master.getCreatedDate()));
                ds.setStatus(master.getJournalStatus());
                ds.setApprovedBy(master.getJournalApprovedBy());
                ds.setLineDate(linesDS.getLineDate() == null ? "" : linesDS.getLineDate().toString());
                ds.setItemPrimaryReference(linesDS.getItemPrimaryReference());
                ds.setItemDesc(linesDS.getDescription());
                ds.setDimension1(linesDS.getDimension1());
                ds.setDimension2(linesDS.getDimension2());
                ds.setDimension3(linesDS.getDimension3());
                ds.setWarehouse(linesDS.getWarehouse());
                ds.setLocation(linesDS.getLocation());
                ds.setPallet(linesDS.getPallet());
                ds.setUom(linesDS.getUom());

                StringBuilder key = new StringBuilder(linesDS.getItemId());
                key.append(linesDS.getDimension1());
                key.append(linesDS.getDimension2());
                key.append(linesDS.getDimension3());

                String supplierRef = supplierReferences.get(key.toString());
                //If value is null, but key exists, assume that no record exists - do not select again.
                if (supplierRef == null && !supplierReferences.containsKey(key)) {
                    String supplier = defaultSuppliers.get(linesDS.getItemId());
                    if (supplier == null && !defaultSuppliers.containsKey(linesDS.getItemId())) {
                        //For speed gains, select supplier field only.
                        EMCQuery itemQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class);
                        itemQuery.addField("purchaseDefaultSupplier");
                        itemQuery.addAnd("itemId", linesDS.getItemId());

                        supplier = (String) util.executeSingleResultQuery(itemQuery, userData);
                        defaultSuppliers.put(linesDS.getItemId(), supplier);
                    }

                    InventoryReference supplierReferenceRecord = referenceBean.findSupplierReferenceRecord(linesDS.getItemId(), linesDS.getDimension1(), linesDS.getDimension2(), linesDS.getDimension3(), supplier, true, userData);
                    if (supplierReferenceRecord == null) {
                        supplierRef = null;
                    } else {
                        supplierRef = supplierReferenceRecord.getReference();
                    }

                    supplierReferences.put(key.toString(), supplierRef);
                }

                //Do not print supplier ref if it is the same as item reference.
                if (supplierRef != null && !util.checkObjectsEqual(supplierRef, ds.getItemPrimaryReference())) {
                    ds.setSupplierRef(supplierRef);
                }
                if (definitionBean.getJournalDefinition(master, userData).getJournalType().equals(InventoryJournalTypes.TRANSFER.toString())) {
                    ds.setBatch(linesDS.getBatch());
                    ds.setLocation(linesDS.getLocation());
                    ds.setSerial(linesDS.getSerial());
                    ds.setQuantity(linesDS.getQuantity());
                    ds.setCost(linesDS.getCost());
                    ds.setAmount(util.round(ds.getCost() * ds.getQuantity(), 2));
                    totalCost += (util.round(ds.getCost() * ds.getQuantity(), 2));
                    ds.setTotalCost(totalCost);
                    ret.add(ds);
                } else {
                    if (linesDS.getQuantity() > 0) {
                        query = new EMCQuery(enumQueryTypes.SELECT, InventoryRegister.class.getName());
                    } else {
                        query = new EMCQuery(enumQueryTypes.SELECT, InventoryRemoveRegister.class.getName());
                    }

                    query.addAnd("type", RegisterFromTypeEnum.JRN.toString());
                    query.addAnd("masterId", master.getJournalNumber());
                    query.addAnd("transId", linesDS.getTransId());
                    regList = util.executeGeneralSelectQuery(query, userData);
                    if (regList != null && regList.size() != 0) {
                        for (Object osb : regList) {
                            reg = (InventoryRegisterSuper) osb;
                            ds.setBatch(reg.getBatch());
                            ds.setLocation(reg.getLocation());
                            ds.setSerial(reg.getSerial());
                            ds.setQuantity(reg.getQuantity());
                            ds.setCost((linesDS.getCost() / linesDS.getQuantity()) * reg.getQuantity());
                            ds.setAmount(util.round(ds.getCost() * ds.getQuantity(), 2));
                            totalCost += (util.round(ds.getCost() * ds.getQuantity(), 2));
                            ds.setTotalCost(totalCost);
                            ret.add(util.doClone(ds, StockJournalReportDS.class, userData));
                        }
                    } else {
                        ds.setQuantity(linesDS.getQuantity());
                        ds.setCost(linesDS.getCost());
                        ds.setAmount(util.round(ds.getCost() * ds.getQuantity(), 2));
                        totalCost += (util.round(ds.getCost() * ds.getQuantity(), 2));
                        ds.setTotalCost(totalCost);
                        ret.add(ds);
                    }
                }
            }
        }
        return ret;
    }
}
