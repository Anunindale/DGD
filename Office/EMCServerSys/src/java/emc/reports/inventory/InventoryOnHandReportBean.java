/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.inventory;

import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.transactions.datasource.InventorySummaryDSLocal;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.transactions.InventorySummary;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.functions.math.EMCMath;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class InventoryOnHandReportBean extends EMCReportBean implements InventoryOnHandReportLocal {

    @EJB
    private InventorySummaryDSLocal summaryBean;
    @EJB
    private InventoryItemMasterLocal itemMasterBean;

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        InventoryOnHandReportDS ds;
        InventorySummary record;
        Object[] oArray;
        List<Object> ret = new ArrayList<Object>();
        for (Object o : queryResult) {
            oArray = (Object[]) o;
            record = (InventorySummary) oArray[0];
            ds = populateItemDSFields(record.getItemId(), userData);
            ds.setMinQty(itemMasterBean.getMinQty(record.getItemId(), record.getDimension1(), record.getDimension2(), record.getDimension3(), null, userData));
            ds.setDimension1(record.getDimension1());
            ds.setDimension2(record.getDimension2());
            ds.setDimension3(record.getDimension3());
            ds.setWarehouse(record.getWarehouse());


            ds.setOhReserved(oArray[2] == null ? 0 : (Double) oArray[2]);
            ds.setOhTotal(oArray[1] == null ? 0 : (Double) oArray[1]);
            ds.setOhAvailable(oArray[15] == null ? 0 : (Double) oArray[15]);

            ds.setOReserved(oArray[4] == null ? 0 : (Double) oArray[4]);
            ds.setOTotal(oArray[3] == null ? 0 : (Double) oArray[3]);
            ds.setOAvailable(ds.getOTotal() - ds.getOReserved());

            ds.setBlanketOrderOS(oArray[10] == null ? 0 : (Double) oArray[10]);
            ds.setOOut(oArray[13] == null ? 0 : (Double) oArray[13]);
            ds.setQc((oArray[12] == null ? 0 : (Double) oArray[12]) + (oArray[11] == null ? 0 : (Double) oArray[11]));

            ret.add(roundValues(ds));
        }
        return ret;
    }

    @Override
    public List<Object> getReportResult(List<Object> parameters, EMCUserData userData) {
        userData.setUserData(0, parameters.get(1).toString());
        userData.setUserData(11, true);
        userData.setUserData(12, true);
        userData.setUserData(13, true);
        userData.setUserData(14, true);
        List<Object> results = executeQuery(summaryBean.setQuery((EMCQuery) parameters.get(1), userData).toString(), userData);
        return manipulateQueryResult(results, null, userData);
    }

    private InventoryOnHandReportDS populateItemDSFields(String itemId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class.getName());
        query.addAnd("itemId", itemId);
        InventoryItemMaster itemMaster = (InventoryItemMaster) util.executeSingleResultQuery(query, userData);
        InventoryOnHandReportDS ds = new InventoryOnHandReportDS();
        ds.setItemDesc(itemMaster.getDescription());
        ds.setItemPrimaryReference(Functions.checkBlank(itemMaster.getItemReference()) ? itemId : itemMaster.getItemReference());
        return ds;
    }

    private InventoryOnHandReportDS roundValues(InventoryOnHandReportDS ds) {
        EMCMath rounder = new EMCMath();
        ds.setOAvailable(rounder.round(ds.getOAvailable(), 2));
        ds.setOReserved(rounder.round(ds.getOReserved(), 2));
        ds.setOTotal(rounder.round(ds.getOTotal(), 2));
        ds.setOhAvailable(rounder.round(ds.getOhAvailable(), 2));
        ds.setOhReserved(rounder.round(ds.getOhReserved(), 2));
        ds.setOhTotal(rounder.round(ds.getOhTotal(), 2));
        ds.setBlanketOrderOS(rounder.round(ds.getBlanketOrderOS(), 2));
        ds.setQc(rounder.round(ds.getQc(), 2));
        return ds;
    }
}

