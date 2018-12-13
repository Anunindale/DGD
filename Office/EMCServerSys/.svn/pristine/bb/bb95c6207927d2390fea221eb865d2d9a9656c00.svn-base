/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.transactions.datasource;

import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryItemDimensionGroup;
import emc.entity.inventory.transactions.InventorySummary;
import emc.entity.inventory.transactions.datasource.InventorySummaryDS;
import emc.enums.emcquery.EMCQueryOrderByDirections;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.transactions.TransEnum;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author rico
 */
@Stateless
public class InventorySummaryDSBean extends EMCDataSourceBean implements InventorySummaryDSLocal {

    @PersistenceContext
    private EntityManager entityManager;

    public InventorySummaryDSBean() {
        this.setDataSourceClassName(emc.entity.inventory.transactions.datasource.InventorySummaryDS.class.getName());
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        return super.validateField(fieldNameToValidate, theRecord, userData);
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        InventorySummaryDS instance = (InventorySummaryDS) dataSourceInstance;
        EMCQuery itemq = new EMCQuery(enumQueryTypes.SELECT, emc.entity.inventory.InventoryItemMaster.class.getName());
        itemq.addAnd("itemId", instance.getItemId());
        itemq.addField("itemId");
        itemq.addField("itemReference");
        itemq.addField("description");
        itemq.addField("baseUOM");
        Object[] obj = (Object[]) util.executeSingleResultQuery(itemq, userData);
        if (obj != null) {
            instance.setItemPrimaryReference((String) (isBlank(obj[1]) ? obj[0] : obj[1]));
            instance.setDescription((String) obj[2]);
            instance.setUnitOfMeasure((String) obj[3]);
        }
        if (userData.getUserData().size() > 2 && userData.getUserData(2).equals(TransEnum.ADD_EXTRA.toString()) && !isBlank(instance.getItemId())) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionGroup.class.getName());
            query.addTableAnd(InventoryItemMaster.class.getName(), "itemDimensionGroupId", InventoryItemDimensionGroup.class.getName(), "dimensionGroup");
            query.addAnd("itemId", instance.getItemId(), InventoryItemMaster.class.getName());
            InventoryItemDimensionGroup dimGroup = (InventoryItemDimensionGroup) util.executeSingleResultQuery(query, userData);

            String toAdd = "";
            if (dimGroup.getDim1Active()) toAdd += "dimension1~";
            if (dimGroup.getDim2Active()) toAdd += "dimension2~";
            if (dimGroup.getDim3Active()) toAdd += "dimension3~";
            if (dimGroup.getWarehouseActive()) toAdd += "warehouse~";
            if (dimGroup.getBatchNumberActive()) toAdd += "batch~";
            if (dimGroup.getSerialNumberActive()) toAdd += "serialNo~";
            if (dimGroup.getLocationActive()) toAdd += "location~";
            if (dimGroup.getPalletIdActive()) toAdd += "pallet~";

            if (toAdd.substring(toAdd.length() - 1, toAdd.length()).equals("~")) {
                toAdd = toAdd.substring(0, toAdd.length() - 1);
            }
            instance.setDisplayColumns(toAdd);
        }
        return instance;
    }

//Next three methods are for the grouping of the data according to the columns that are active
    @Override
    public Collection getDataInRange(Class theTable, EMCUserData userData, int start, int end) {
        EMCQuery query = (EMCQuery) userData.getUserData(0);
        String alias = query.getTableAlias(InventorySummary.class);
        String fieldValue = "ROUND(SUM(" + alias + ".physicalTotal), 1) > 0 OR ROUND(SUM(" + alias + ".physicalReserved), 1) > 0 OR " +
                "ROUND(SUM(" + alias + ".physicalAvailable), 1) > 0 OR ROUND(SUM(" + alias + ".orderedTotal), 1) > 0 OR " +
                "ROUND(SUM(" + alias + ".orderedReserved), 1) > 0 OR ROUND(SUM(" + alias + ".orderedAvailable), 1) > 0 OR " +
                "ROUND(SUM(" + alias + ".qtyOSBlanketOrd), 1) > 0 OR ROUND(SUM(" + alias + ".recAvailable), 1) > 0 OR " +
                "ROUND(SUM(" + alias + ".qcAvailable), 1) > 0 OR ROUND(SUM(" + alias + ".orderedOut), 1) > 0";
        query.addAndCustomHavingValue(fieldValue);
        super.checkCompanyId(query, userData);
        Query qr = entityManager.createQuery(setQuery(query, userData).toString());
        qr.setFirstResult(start);
        qr.setMaxResults(end - start);
        List<Object[]> l = qr.getResultList();
        List ret = new ArrayList();

        InventorySummary summary, tempSummary;
        for (Object[] o : l) {
            summary = (InventorySummary) o[0];
            tempSummary = new InventorySummaryDS();

            tempSummary.setItemId(summary.getItemId());
            //so client does not show new record
            tempSummary.setRecordID(1);
            tempSummary.setDimension1(summary.getDimension1());
            tempSummary.setDimension2(summary.getDimension2());
            tempSummary.setDimension3(summary.getDimension3());
            tempSummary.setWarehouse(summary.getWarehouse());
            tempSummary.setBatch(summary.getBatch());
            tempSummary.setLocation(summary.getLocation());
            tempSummary.setPallet(summary.getPallet());
            tempSummary.setSerialNo(summary.getSerialNo());
            tempSummary.setItemDimId(summary.getItemDimId());
            tempSummary.setQCStatus(summary.getQCStatus());

            tempSummary.setPhysicalTotal(o[1] == null ? 0 : (Double) o[1]);
            tempSummary.setPhysicalReserved(o[2] == null ? 0 : (Double) o[2]);
            tempSummary.setPhysicalAvailable(o[15] == null ? 0 : (Double) o[15]);
            tempSummary.setOrderedTotal(o[3] == null ? 0 : (Double) o[3]);
            tempSummary.setOrderedReserved(o[4] == null ? 0 : (Double) o[4]);
            tempSummary.setOrderedAvailable(tempSummary.getOrderedTotal() - tempSummary.getOrderedReserved());
            tempSummary.setPicked(o[5] == null ? 0 : (Double) o[5]);
            tempSummary.setReceived(o[6] == null ? 0 : (Double) o[6]);
            tempSummary.setPosted(o[7] == null ? 0 : (Double) o[7]);
            tempSummary.setDeducted(o[8] == null ? 0 : (Double) o[8]);
            tempSummary.setRegistered(o[9] == null ? 0 : (Double) o[9]);
            tempSummary.setQtyOSBlanketOrd(o[10] == null ? 0 : (Double) o[10]);
            tempSummary.setRecAvailable(o[11] == null ? 0 : (Double) o[11]);
            tempSummary.setQcAvailable(o[12] == null ? 0 : (Double) o[12]);
            tempSummary.setOrderedOut(o[13] == null ? 0 : (Double) o[13]);
            tempSummary.setQtySOPBlanketOrder(o[14] == null ? BigDecimal.ZERO : (BigDecimal) o[14]);

            ret.add(populateDataSourceFields(tempSummary, userData));
        }
        return ret;
    }

    @Override
    public String getNumRows(Class theTable, EMCUserData userData) {
        EMCQuery query = (EMCQuery) userData.getUserData(0);
        String alias = query.getTableAlias(InventorySummary.class);
        String fieldValue = "ROUND(SUM(" + alias + ".physicalTotal), 1) > 0 OR ROUND(SUM(" + alias + ".physicalReserved), 1) > 0 OR " +
                "ROUND(SUM(" + alias + ".physicalAvailable), 1) > 0 OR ROUND(SUM(" + alias + ".orderedTotal), 1) > 0 OR " +
                "ROUND(SUM(" + alias + ".orderedReserved), 1) > 0 OR ROUND(SUM(" + alias + ".orderedAvailable), 1) > 0 OR " +
                "ROUND(SUM(" + alias + ".qtyOSBlanketOrd), 1) > 0 OR ROUND(SUM(" + alias + ".recAvailable), 1) > 0 OR " +
                "ROUND(SUM(" + alias + ".qcAvailable), 1) > 0 OR ROUND(SUM(" + alias + ".orderedOut), 1) > 0";
        query.addAndCustomHavingValue(fieldValue);
        super.checkCompanyId(query, userData);
        Query qr = entityManager.createQuery(setQuery(query, userData).toString());
        List<Object[]> qtyList = qr.getResultList();
        return String.valueOf(String.valueOf(qtyList.size())) + ", " + String.valueOf(0);
    }

    private EMCUserData populateUD(EMCQuery sQuery, EMCUserData userData) {
        String itemId = EMCQuery.getFieldValueFromQuery("itemId", sQuery.toString());
        if (userData.getUserData().size() > 2 && userData.getUserData(2).equals(TransEnum.ADD_EXTRA.toString()) && !isBlank(itemId)) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionGroup.class.getName());
            query.addTableAnd(InventoryItemMaster.class.getName(), "itemDimensionGroupId", InventoryItemDimensionGroup.class.getName(), "dimensionGroup");
            query.addAnd("itemId", itemId, InventoryItemMaster.class.getName());
            InventoryItemDimensionGroup dimGroup = (InventoryItemDimensionGroup) util.executeSingleResultQuery(query, userData);

            if (dimGroup.getDim1Active()) userData.setUserData(11, true);
            if (dimGroup.getDim2Active()) userData.setUserData(12, true);
            if (dimGroup.getDim3Active()) userData.setUserData(13, true);
            if (dimGroup.getWarehouseActive()) userData.setUserData(14, true);
            if (dimGroup.getBatchNumberActive()) userData.setUserData(15, true);
            if (dimGroup.getSerialNumberActive())
                userData.setUserData(16, true);
            if (dimGroup.getLocationActive()) userData.setUserData(17, true);
            if (dimGroup.getPalletIdActive()) userData.setUserData(18, true);
        }
        return userData;
    }

    @Override
    public EMCQuery setQuery(EMCQuery query, EMCUserData userData) {
        userData = populateUD(query, userData);

        query.addTableAsField(InventorySummary.class.getName());//0
        query.addFieldAggregateFunction("physicalTotal", InventorySummary.class.getName(), "SUM");//1
        query.addFieldAggregateFunction("physicalReserved", InventorySummary.class.getName(), "SUM");//2
        query.addFieldAggregateFunction("orderedTotal", InventorySummary.class.getName(), "SUM");//3
        query.addFieldAggregateFunction("orderedReserved", InventorySummary.class.getName(), "SUM");//4
        query.addFieldAggregateFunction("picked", InventorySummary.class.getName(), "SUM");//5
        query.addFieldAggregateFunction("received", InventorySummary.class.getName(), "SUM");//6
        query.addFieldAggregateFunction("posted", InventorySummary.class.getName(), "SUM");//7
        query.addFieldAggregateFunction("deducted", InventorySummary.class.getName(), "SUM");//8
        query.addFieldAggregateFunction("registered", InventorySummary.class.getName(), "SUM");//9
        query.addFieldAggregateFunction("qtyOSBlanketOrd", InventorySummary.class.getName(), "SUM");//10
        query.addFieldAggregateFunction("recAvailable", InventorySummary.class.getName(), "SUM");//11
        query.addFieldAggregateFunction("qcAvailable", InventorySummary.class.getName(), "SUM");//12
        query.addFieldAggregateFunction("orderedOut", InventorySummary.class.getName(), "SUM");//13
        query.addFieldAggregateFunction("qtySOPBlanketOrder", InventorySummary.class.getName(), "SUM");//14
        query.addFieldAggregateFunction("physicalAvailable", InventorySummary.class.getName(), "SUM");//15

        query.addGroupBySingle(InventorySummary.class.getName(), "itemId");
        query.addOrderBySingle("itemId", InventorySummary.class.getName(), EMCQueryOrderByDirections.ASC);

        if (isActive(11, userData)) {
            query.addGroupBySingle(InventorySummary.class.getName(), "dimension1");
            query.addOrderBySingle("dimension1", InventorySummary.class.getName(), EMCQueryOrderByDirections.ASC);
        }
        if (isActive(13, userData)) {
            query.addGroupBySingle(InventorySummary.class.getName(), "dimension3");
            query.addOrderBySingle("dimension3", InventorySummary.class.getName(), EMCQueryOrderByDirections.ASC);
        }
        if (isActive(12, userData)) {
            query.addGroupBySingle(InventorySummary.class.getName(), "dimension2");
            query.addOrderBySingle("dimension2", InventorySummary.class.getName(), EMCQueryOrderByDirections.ASC);
        }
        if (isActive(14, userData)) {
            query.addGroupBySingle(InventorySummary.class.getName(), "warehouse");
            query.addOrderBySingle("warehouse", InventorySummary.class.getName(), EMCQueryOrderByDirections.ASC);
        }
        if (isActive(15, userData)) {
            query.addGroupBySingle(InventorySummary.class.getName(), "batch");
            query.addOrderBySingle("batch", InventorySummary.class.getName(), EMCQueryOrderByDirections.ASC);
        }
        if (isActive(16, userData)) {
            query.addGroupBySingle(InventorySummary.class.getName(), "serialNo");
            query.addOrderBySingle("serialNo", InventorySummary.class.getName(), EMCQueryOrderByDirections.ASC);
        }
        if (isActive(17, userData)) {
            query.addGroupBySingle(InventorySummary.class.getName(), "location");
            query.addOrderBySingle("location", InventorySummary.class.getName(), EMCQueryOrderByDirections.ASC);
        }
        if (isActive(18, userData)) {
            query.addGroupBySingle(InventorySummary.class.getName(), "pallet");
            query.addOrderBySingle("pallet", InventorySummary.class.getName(), EMCQueryOrderByDirections.ASC);
        }
        return query;
    }

    private boolean isActive(int flagPos, EMCUserData userData) {
        try {
            return !isBlank(userData.getUserData().get(flagPos));
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }
}
