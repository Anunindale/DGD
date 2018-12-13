/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.pop.costchange;

import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.inventory.logic.InventoryItemDimensionCombinationLogicLocal;
import emc.bus.pop.POPPurchaseOrderLinesLocal;
import emc.bus.pop.pricearrangements.POPPriceArrangementsLocal;
import emc.entity.inventory.InventoryCostingGroup;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryReference;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.entity.pop.POPPurchaseOrderLines;
import emc.entity.pop.POPPurchaseOrderMaster;
import emc.entity.pop.costchange.CostChangeSummaryDS;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.costing.CostLevel;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.tables.EMCTable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class CostChangeSummarDSBean extends EMCDataSourceBean implements CostChangeSummaryDSLocal {

    @EJB
    private InventoryItemMasterLocal itemBean;
    @EJB
    private POPPurchaseOrderLinesLocal POLinesBean;
    @EJB
    private POPPriceArrangementsLocal priceArrangementBean;
    @EJB
    private InventoryItemDimensionCombinationLogicLocal logicBean;
    @EJB
    private emc.bus.inventory.dimensions.InventoryItemDimensionCombinationsLocal combinationsBean;
    @EJB
    private InventoryReferenceLocal refBean;

    /**
     * Creates a new instance of CostChangeSummarDSBean
     */
    public CostChangeSummarDSBean() {
        this.setDataSourceClassName(CostChangeSummaryDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        CostChangeSummaryDS ds = (CostChangeSummaryDS) dataSourceInstance;

        ds = getItemInfo(ds, userData);
        ds.setApproveCost(false);
        ds.setUpdateCost(false);
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderMaster.class.getName());
        query.addAnd("purchaseOrderId", ds.getPurchaseOrderId());
        query.addAnd("companyId", userData.getCompanyId());
        query.addField("supplier");
        ds.setSuppId(util.executeSingleResultQuery(query, userData).toString());

        return ds;
    }

    private CostChangeSummaryDS getItemInfo(CostChangeSummaryDS ds, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class.getName());
        query.addAnd("itemId", ds.getItemId());
        query.addField("itemId");
        query.addField("itemReference");
        query.addField("description");
        query.addField("purchasePrice");
        Object[] itemInfo = (Object[]) util.executeSingleResultQuery(query, userData);
        ds.setItemReference(isBlank(itemInfo[1]) ? (String) itemInfo[0] : (String) itemInfo[1]);
        ds.setItemDescription((String) itemInfo[2]);
        Double combPrice = logicBean.getCostPriceForCombination(ds.getItemId(), ds.getItemDimension1(), ds.getItemDimension2(), ds.getItemDimension3(), userData);
        ds.setItemCost(combPrice == 0 ? (Double) itemInfo[3] : combPrice);
        return ds;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        if (fieldNameToValidate.equals("updateCost")) {
            CostChangeSummaryDS ds = (CostChangeSummaryDS) theRecord;
            if (!ds.isApproveCost()) {
                Logger.getLogger("emc").log(Level.SEVERE, "You did not approve the new cost.", userData);
                return false;
            } else {
                return true;
            }
        }
        return super.validateField(fieldNameToValidate, theRecord, userData);
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        CostChangeSummaryDS ds = (CostChangeSummaryDS) uobject;
        EMCQuery query;
        if (ds.isApproveCost()) {
            if (ds.isUpdateCost()) {
                //Fetch Supplier Reference
                query = new EMCQuery(enumQueryTypes.SELECT, InventoryReference.class);
                query.addTableAnd(POPPurchaseOrderMaster.class.getName(), "supplierNo", InventoryReference.class.getName(), "supplier");
                query.addAnd("purchaseOrderId", ds.getPurchaseOrderId(), POPPurchaseOrderMaster.class.getName());
                query.addAnd("itemId", ds.getItemId(), InventoryReference.class.getName());
                query.addAnd("dimension1", ds.getItemDimension1(), InventoryReference.class.getName());
                query.addAnd("dimension2", ds.getItemDimension2(), InventoryReference.class.getName());
                query.addAnd("dimension3", ds.getItemDimension3(), InventoryReference.class.getName());
                InventoryReference supplierRef = (InventoryReference) util.executeSingleResultQuery(query, userData);
                //Set Supplier Reference Purchase Price and Date
                if (supplierRef != null) {
                    supplierRef.setPurchasePrice(ds.getItemPrice());
                    supplierRef.setPurchasePriceDate(Functions.nowDate());
                    refBean.update(supplierRef, userData);
                }

                //Fetch Item
                query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class.getName());
                query.addAnd("itemId", ds.getItemId());
                query.addAnd("companyId", userData.getCompanyId());
                InventoryItemMaster item = (InventoryItemMaster) util.executeSingleResultQuery(query, userData);
                //Set Item Purchase Price and Date
                item.setPurchasePrice(ds.getItemPrice());
                item.setPurchasePriceDate(Functions.nowDate());
                //Set Item Costing and Date
                item.setCostingPrevCost(item.getCostingCurrentCost());
                item.setCostingPrevCostDate(item.getCostingCostDate());
                item.setPrevCostLink(item.getCurrentCostLink());
                item.setCostingCostDate(Functions.nowDate());
                item.setCostingCurrentCost(ds.getItemPrice());
                item.setCurrentCostLink("Manual");
                itemBean.update(item, userData);

                //Fetch Costing group from item
                String costingGroupId = item.getCostingGroup();
                if (!isBlank(costingGroupId)) {
                    //Fetch Costing Group to check Cost Level
                    query = new EMCQuery(enumQueryTypes.SELECT, InventoryCostingGroup.class.getName());
                    query.addAnd("costingGroupId", costingGroupId);
                    InventoryCostingGroup costingGroup = (InventoryCostingGroup) util.executeSingleResultQuery(query, userData);

                    //Update Item Dimension if Cost Level is Dimension
                    if (costingGroup != null && CostLevel.DIMENSION.equals(CostLevel.fromString(costingGroup.getCostLevel()))) {
                        //Fetch active dimensions for Item
                        boolean[] activeDims = logicBean.getActiveDimensions(item.getItemId(), userData);

                        //Fetch Dimension Combination based on active dimensions
                        query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionCombinations.class.getName());
                        query.addAnd("itemId", item.getItemId());
                        if (activeDims[0]) {
                            query.addAnd("dimension1Id", ds.getItemDimension1());
                        }
                        if (activeDims[1]) {
                            query.addAnd("dimension2Id", ds.getItemDimension2());
                        }
                        if (activeDims[2]) {
                            query.addAnd("dimension3Id", ds.getItemDimension3());
                        }
                        InventoryItemDimensionCombinations combination = (InventoryItemDimensionCombinations) util.executeSingleResultQuery(query, userData);

                        if (combination != null) {
                                //Set Dimension Purchase Price and Date
                                combination.setPurchasePrice(ds.getItemPrice());
                                combination.setPurchasePriceDate(Functions.nowDate());
                                //Set Dimension Costing and Date
                                combination.setPrevCostDate(combination.getCurrentCostDate());
                                combination.setPrevCostPrice(combination.getCostPrice());
                                combination.setCostPrice(ds.getItemPrice());
                                combination.setCurrentCostDate(Functions.nowDate());
                                combinationsBean.update(combination, userData);
                        } else {
                            Logger.getLogger("emc").log(Level.WARNING, "Combination not found", userData);
                        }
                    }
                }
                BigDecimal priceA = priceArrangementBean.findItemPrice(ds.getSuppId(),ds.getItemId(), ds.getItemDimension1(), ds.getItemDimension2(),ds.getItemDimension3(),new Date(),new BigDecimal(BigInteger.ONE),userData);
                if(ds.getItemPrice() != priceA.doubleValue()){
                    Logger.getLogger("emc").log(Level.WARNING, "Item cost was not updated in the price list file.", userData);
                }
            }
            //Fetch PO Line that caused updates
            query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderLines.class.getName());
            query.addAnd("purchaseOrderId", ds.getPurchaseOrderId());
            query.addAnd("lineNo", ds.getLineNo());
            query.addAnd("companyId", userData.getCompanyId());
            POPPurchaseOrderLines POL = (POPPurchaseOrderLines) util.executeSingleResultQuery(query, userData);
            if (!isBlank(POL.getWhoApproved())) {
                Logger.getLogger("emc").log(Level.INFO, "Someone already approved the record you are trying to approve. Your data has been refreshed.", userData);
                return ds;
            }
            //Set record to approved
            POL.setCostChange(false);
            POL.setOldPrice(ds.getItemCost());
            POL.setItemPrice(ds.getItemPrice());
            POL.setWhoApproved(userData.getUserName());
            POL.setDateApproved(new GregorianCalendar().getTime());
            POL.setUpdateItem(ds.isUpdateCost());
            POLinesBean.update(POL, userData);
        }
        return ds;
    }

    @Override
    public boolean doDeleteValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        Logger.getLogger("emc").log(Level.SEVERE, "You can't delete records from this table.", userData);
        return false;
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        Logger.getLogger("emc").log(Level.SEVERE, "You can't add records to this table.", userData);
        return false;
    }
}