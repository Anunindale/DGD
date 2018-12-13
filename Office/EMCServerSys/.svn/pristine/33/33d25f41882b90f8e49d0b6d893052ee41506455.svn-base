/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.dimensions;

import emc.bus.inventory.transactions.InventorySummaryLocal;
import emc.constants.systemConstants;
import emc.entity.inventory.InventoryCostingGroup;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.costing.CostLevel;
import emc.enums.inventory.purchasing.InventoryStopPurchasingType;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.messages.ServerInventoryMessageEnum;
import emc.tables.EMCTable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class InventoryItemDimensionCombinationsBean extends EMCEntityBean implements InventoryItemDimensionCombinationsLocal {

    @EJB
    private InventorySummaryLocal summaryBean;

    /** Creates a new instance of InventoryItemDimensionCombinationsBean */
    public InventoryItemDimensionCombinationsBean() {
    }

    @Override
    public boolean doDeleteValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doDeleteValidation(vobject, userData);
        if (ret) {
            InventoryItemDimensionCombinations combo = (InventoryItemDimensionCombinations) vobject;
            ret = ret && validateCombinationActive(combo.getDimension1Id(), combo.getDimension2Id(), combo.getDimension3Id(), combo.getItemId(), userData);
        }
        return ret;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);

        if (ret) {
            InventoryItemDimensionCombinations comb = (InventoryItemDimensionCombinations) theRecord;
            if (fieldNameToValidate.equals("costPrice")) {
                comb.setCurrentCostDate(Functions.nowDate());
                if (comb.getRecordID() != 0) {
                    InventoryItemDimensionCombinations orig = (InventoryItemDimensionCombinations) this.findSQLVersionForEntity(comb, userData);
                    comb.setPrevCostDate(orig.getCurrentCostDate());
                    comb.setPrevCostPrice(orig.getCostPrice());
                    doCostWarning(comb, userData);
                }
                return comb;
            }

            if (ret && fieldNameToValidate.equals("minQty") && (comb.getMaxOrderQty() != null && comb.getMaxOrderQty().compareTo(BigDecimal.ZERO) != 0)) {
                if (comb.getMinOrderQty() != null && comb.getMinOrderQty().compareTo(comb.getMaxOrderQty()) > 0) {
                    Logger.getLogger("emc").log(Level.SEVERE, "The min qty has to be less than the max qty.", userData);
                    ret = false;
                }
            }
            if (ret && fieldNameToValidate.equals("maxQty") && (comb.getMaxOrderQty() != null && comb.getMaxOrderQty().compareTo(BigDecimal.ZERO) != 0)) {
                if (comb.getMinOrderQty() != null && comb.getMinOrderQty().compareTo(comb.getMaxOrderQty()) > 0) {
                    Logger.getLogger("emc").log(Level.SEVERE, "The max qty has to be more than the min qty.", userData);
                    ret = false;
                }
            }

        }


        return ret;
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        InventoryItemDimensionCombinations toBeTested = (InventoryItemDimensionCombinations) iobject;

        //Concats given dimensions
        String dimension1 = toBeTested.getDimension1Id();
        String dimension2 = toBeTested.getDimension2Id();
        String dimension3 = toBeTested.getDimension3Id();

        toBeTested.setDimensionCombination((dimension1 == null ? "" : dimension1) + (dimension2 == null ? "" : dimension2) + (dimension3 == null ? "" : dimension3));

        return super.insert(iobject, userData);
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        InventoryItemDimensionCombinations toBeTested = (InventoryItemDimensionCombinations) uobject;

        //Concats given dimensions
        String dimension1 = toBeTested.getDimension1Id();
        String dimension2 = toBeTested.getDimension2Id();
        String dimension3 = toBeTested.getDimension3Id();

        toBeTested.setDimensionCombination((dimension1 == null ? "" : dimension1) + (dimension2 == null ? "" : dimension2) + (dimension3 == null ? "" : dimension3));

        return super.update(uobject, userData);
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean valid = super.doInsertValidation(vobject, userData);

        if (valid) {
            InventoryItemDimensionCombinations record = (InventoryItemDimensionCombinations) vobject;

            if (record.getActive()) {
                valid = valid && checkConfigActive(record, userData);
            }
            valid = valid && validatePurchaseStop(record, userData);
        }

        return valid;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean valid = super.doUpdateValidation(vobject, userData);

        if (valid) {
            InventoryItemDimensionCombinations record = (InventoryItemDimensionCombinations) vobject;
            InventoryItemDimensionCombinations persistedRecord = (InventoryItemDimensionCombinations) util.findDetachedPersisted(record, userData);

            if (record.getActive() && !persistedRecord.getActive()) {
                valid = valid && checkConfigActive(record, userData);
            } else if (persistedRecord.getActive() && !record.getActive()) {
                String dimension1Id = util.checkObjectsEqual(record.getDimension1Id(), systemConstants.emcNA()) ? null : record.getDimension1Id();
                String dimension2Id = util.checkObjectsEqual(record.getDimension2Id(), systemConstants.emcNA()) ? null : record.getDimension2Id();
                String dimension3Id = util.checkObjectsEqual(record.getDimension3Id(), systemConstants.emcNA()) ? null : record.getDimension3Id();
                valid = valid && validateCombinationActive(dimension1Id, dimension2Id, dimension3Id, record.getItemId(), userData);
            }
            valid = valid && validatePurchaseStop(record, userData);
        }


        return valid;
    }

    /**
     * Checks whether combinations for the given item already exist.
     * @param itemId Item to check combinations for.
     * @param userData User data.
     * @return a boolean indicating whether combinations exist.
     */
    public boolean checkCombinationsExist(String itemId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionCombinations.class.getName());
        query.addAnd("itemId", itemId);

        return util.exists(query, userData);
    }

    private void doCostWarning(InventoryItemDimensionCombinations comb, EMCUserData userData) {
        if (!isBlank(comb.getItemId())) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryCostingGroup.class.getName());
            query.addTableAnd(InventoryItemMaster.class.getName(), "costingGroupId", InventoryCostingGroup.class.getName(), "costingGroup");
            query.addAnd("itemId", comb.getItemId(), InventoryItemMaster.class.getName());
            InventoryCostingGroup costGroup = (InventoryCostingGroup) util.executeSingleResultQuery(query, userData);
            if (!CostLevel.DIMENSION.toString().equals(costGroup.getCostLevel())) {
                logMessage(Level.WARNING, "The costing level is set to " + costGroup.getCostLevel() + ", updateing the cost here will hava no affect.", userData);
            }
        }
    }

    private boolean checkConfigActive(InventoryItemDimensionCombinations record, EMCUserData userData) {
        if (!(isBlank(record.getDimension1Id()) || "*EMC/NA*".equals(record.getDimension1Id()))) {
            if (!(userData.getUserData().size() > 5 && (Boolean) userData.getUserData(5))) {
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension1.class.getName());
                query.addAnd("dimensionId", record.getDimension1Id());
                query.addAnd("active", true);
                if (!util.exists(query, userData)) {
                    Logger.getLogger("emc").log(Level.SEVERE, "The config on the selected record has not been activated.", userData);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Collection getDataInRange(Class theTable, EMCUserData userData, int start, int end) {
        if (userData.getUserData(0) != null && userData.getUserData(0) instanceof EMCQuery) {
            EMCQuery query = (EMCQuery) userData.getUserData(0);
            query.addLeftOuterJoin(InventoryItemDimensionCombinations.class, "dimension1Id", InventoryDimension1.class, "dimensionId");
            query.addLeftOuterJoin(InventoryItemDimensionCombinations.class, "dimension2Id", InventoryDimension2.class, "dimensionId");
            query.addLeftOuterJoin(InventoryItemDimensionCombinations.class, "dimension3Id", InventoryDimension3.class, "dimensionId");
            query.addOrderBy("sortCode", InventoryDimension1.class.getName());
            query.addOrderBy("sortCode", InventoryDimension3.class.getName());
            query.addOrderBy("sortCode", InventoryDimension2.class.getName());
            super.checkCompanyId(query, userData);
            return util.executeLimitedNativeQuery(query, theTable, start, end, userData);
        }
        return super.getDataInRange(theTable, userData, start, end);
    }

    private boolean validateCombinationActive(String dimension1Id, String dimension2Id, String dimension3Id, String itemId, EMCUserData userData) {
        if ((summaryBean.getOnHandTotal(itemId, dimension1Id, dimension2Id, dimension3Id, null, userData)).compareTo(BigDecimal.ZERO) > 0) {
            logMessage(Level.SEVERE, ServerInventoryMessageEnum.COMBINATIONS_ACTIVE, userData, dimension1Id, dimension2Id, dimension3Id);
            return false;
        } else {
            return true;
        }
    }

    private boolean validatePurchaseStop(InventoryItemDimensionCombinations com, EMCUserData userData) {
        if (InventoryStopPurchasingType.OVERSEE.toString().equals(com.getStopPurchase())) {
            if (isBlank(com.getOverseePurchaseGroup())) {
                Logger.getLogger("emc").log(Level.SEVERE, "Please select the overseeing purchase order approval group.", userData);
                return false;
            }
        } else {
            if (!isBlank(com.getOverseePurchaseGroup())) {
                com.setOverseePurchaseGroup(null);
            }
        }
        return true;
    }
}
