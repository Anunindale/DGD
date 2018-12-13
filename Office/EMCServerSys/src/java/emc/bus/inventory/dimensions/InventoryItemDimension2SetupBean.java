/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.dimensions;

import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.inventory.transactions.InventorySummaryLocal;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryReference;
import emc.entity.inventory.dimensions.InventoryDimension2GroupSetup;
import emc.entity.inventory.dimensions.InventoryItemDimension2Setup;
import emc.entity.inventory.transactions.InventorySummary;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.entity.pop.POPPurchaseOrderLines;
import emc.entity.pop.POPPurchaseOrderMaster;
import emc.entity.sop.SOPSalesOrderLines;
import emc.entity.sop.SOPSalesOrderMaster;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.transactions.InventoryTransactionStatus;
import emc.enums.pop.purchaseorders.PurchaseOrderStatus;
import emc.enums.pop.purchaseorders.PurchaseOrderTypes;
import emc.enums.sop.SalesOrderTypes;
import emc.enums.sop.salesorders.SalesOrderStatus;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.messages.ServerInventoryMessageEnum;
import emc.tables.EMCTable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author riaan
 */
@Stateless
public class InventoryItemDimension2SetupBean extends EMCEntityBean implements InventoryItemDimension2SetupLocal {

    @EJB
    private InventoryReferenceLocal itemRefBean;
    @PersistenceContext
    private EntityManager em;
    @EJB
    private InventorySummaryLocal summaryBean;

    /** Creates a new instance of InventoryItemDimension2SetupBean */
    public InventoryItemDimension2SetupBean() {
    }

    /** Generates new data when no data exists on the table. */
    @Override
    public String getNumRows(Class theTable, EMCUserData userData) {
        String ret = super.getNumRows(theTable, userData);
        // TODO Check that this works.
        if (ret.split(",")[0].trim().equals("0")) {
            generateItemDimensions(userData);

            ret = super.getNumRows(theTable, userData);
        }
        return ret;
    }

    /** Generates data when form is opened for the first time. */
    private List<Object> generateItemDimensions(EMCUserData userData) {
        List<Object> ret = new ArrayList<Object>();

        String query = userData.getUserData().get(0).toString();

        if (query != null && query.contains("itemId")) {
            String itemId = util.getFieldValueFromQuery("itemId", query, userData);

            EMCQuery dimGroupQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class.getName());
            dimGroupQuery.addAnd("itemId", itemId);
            dimGroupQuery.addField("dimension2Group");

            Object dim2Group = util.executeSingleResultQuery(dimGroupQuery, userData);

            if (dim2Group != null && !dim2Group.equals("")) {
                dimGroupQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension2GroupSetup.class.getName());
                dimGroupQuery.addAnd("dimensionGroupId", dim2Group.toString());
                dimGroupQuery.addField("dimensionId");

                List<String> dimGroups = util.executeGeneralSelectQuery(dimGroupQuery, userData);

                InventoryItemDimension2Setup setup = null;

                for (String ob : dimGroups) {
                    setup = new InventoryItemDimension2Setup();
                    setup.setCompanyId(userData.getCompanyId());
                    setup.setItemId(itemId);
                    setup.setDimensionId(ob);

                    try {
                        super.insert(setup, userData);
                    //ret.add(setup);
                    } catch (EMCEntityBeanException ex) {
                        if (EMCDebug.getDebug()) {
                            Logger.getLogger("emc").log(Level.SEVERE, "Failed to insert new dimension on item dimension 2 setup table.", userData);
                        }
                    }
                }
            }

            em.flush();
        }

        return ret;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        InventoryItemDimension2Setup setup = (InventoryItemDimension2Setup) theRecord;
        if (fieldNameToValidate.equals("itemId")) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryReference.class.getName());
            query.addAnd("reference", setup.getItemId());
            Object o = util.executeSingleResultQuery(query, userData);
            if (o != null) {
                InventoryReference ref = (InventoryReference) o;
                setup.setItemId(ref.getItemId());
            }
        }
        boolean ret = (Boolean) super.validateField(fieldNameToValidate, setup, userData);
        if (fieldNameToValidate.equals("dimensionId")) {
            ret = validateDimension2(setup.getDimensionId(), setup.getItemId(), userData);
        }

        if (setup.getRecordID() != 0) {
            //Only check whether the dimension has been used if the record has
            //already been saved.  Do not perform the logic for new records.
            if (fieldNameToValidate.equals("dimensionId") || (fieldNameToValidate.equals("active") && !setup.getActive())) {
                //Use persisted record to check whether it has been used.
                InventoryItemDimension2Setup persisted = (InventoryItemDimension2Setup) util.findDetachedPersisted(setup, userData);
                ret = ret && validateDimensionChangeDelete(persisted.getDimensionId(), persisted.getItemId(), userData);
            }
        }

        return ret;
    }

    @Override
    public boolean doDeleteValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean valid = super.doDeleteValidation(vobject, userData);

        if (valid) {
            InventoryItemDimension2Setup dimensionSetup = (InventoryItemDimension2Setup) vobject;
            valid = valid && validateDimensionChangeDelete(dimensionSetup.getDimensionId(), dimensionSetup.getItemId(), userData);
        }

        return valid;
    }

    /** This method is used to check whether a dimension exists on a group */
    private boolean validateDimension2(String dimension, String item, EMCUserData userData) {

        if (!isBlank(item)) {
            Object dimensionGroup = null;

            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class.getName());
            query.addAnd("itemId", item);
            query.addField("dimension2Group");

            dimensionGroup = util.executeSingleResultQuery(query, userData);

            if (isBlank(dimensionGroup)) {
                Logger.getLogger("emc").log(Level.SEVERE, "No size group on item", userData);
                return false;
            } else {
                query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension2GroupSetup.class.getName());
                query.addAnd("dimensionId", dimension);
                query.addAnd("dimensionGroupId", dimensionGroup.toString());

                if (util.executeSingleResultQuery(query, userData) == null) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Dimension not on item size group.", userData);
                    return false;
                } else {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * This method is used to check whether a dimension has been used.  It should
     * be called when an InventoryItemDimension2Setup record is deleted, the dimension
     * id is changed or the record is deactivated.
     *
     * @param dimensionId Dimension id of the record being changed.
     * @param itemId Item id of the record being changed.
     * @param userData User data.
     * @return A boolean indicating whether the record may be updated/deleted.
     */
    private boolean validateDimensionChangeDelete(String dimensionId, String itemId, EMCUserData userData) {
        String itemRef = null;
        String[] refArr = itemRefBean.checkItemReference(itemId, userData);
        if (refArr != null && refArr.length > 1 && refArr[1] != null) {
            itemRef = refArr[1];
        } else {
            itemRef = itemId;
        }

        if (checkStockOnHand(dimensionId, itemId, userData)) {
            logMessage(Level.SEVERE, ServerInventoryMessageEnum.STOCK_ON_HAND, userData, dimensionId);
            return false;
        }

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
        query.addTableAnd(InventorySummary.class.getName(), "recordID", InventoryTransactions.class.getName(), "inventoryTransRef");
        query.addAnd("itemId", itemId);
        query.addAnd("dimension2", dimensionId, InventorySummary.class.getName());
        //Only check 'open' transactions.
        query.addAnd("status", InventoryTransactionStatus.DELIVERED.toString(), EMCQueryConditions.NOT);
        query.addAnd("status", InventoryTransactionStatus.PURCHASED.toString(), EMCQueryConditions.NOT);
        query.addAnd("status", InventoryTransactionStatus.RECEIVED.toString(), EMCQueryConditions.NOT);
        query.addAnd("status", InventoryTransactionStatus.RETURNED_GOODS.toString(), EMCQueryConditions.NOT);

        List<InventoryTransactions> data = (List<InventoryTransactions>) util.executeGeneralSelectQuery(query, userData);
        if (!data.isEmpty()) {
            InventoryTransactions transaction = data.get(1);
            logMessage(Level.SEVERE, ServerInventoryMessageEnum.DIM_USED_ON_TRANS, userData, dimensionId, itemRef, transaction.getRefType(), transaction.getRefNumber());
            return false;
        }

        //Check for SOP & POP Blanket Orders
        query = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class);
        query.addFieldAggregateFunction("qtyOSBlanketOrd", "SUM");
        query.addFieldAggregateFunction("qtySOPBlanketOrder", "SUM");
        query.addAnd("itemId", itemId);
        query.addAnd("dimension2", dimensionId, InventorySummary.class.getName());
        query.openAndConditionBracket();
        query.addOr("qtyOSBlanketOrd", 0, EMCQueryConditions.NOT);
        query.addOr("qtySOPBlanketOrder", 0, EMCQueryConditions.NOT);
        query.closeConditionBracket();

        Object[] blanketOrderQuantities = (Object[]) util.executeSingleResultQuery(query, userData);
        if (blanketOrderQuantities != null) {
            if (blanketOrderQuantities[0] != null) {
                if ((Double) blanketOrderQuantities[0] != 0) {
                    //Select open Blanket Orders on which the item & dimension appears.
                    query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderMaster.class);
                    query.addTableAnd(POPPurchaseOrderLines.class.getName(), "purchaseOrderId", POPPurchaseOrderMaster.class.getName(), "purchaseOrderId");
                    query.addField("purchaseOrderId");
                    query.addAnd("purchaseOrderType", PurchaseOrderTypes.BLANKET_ORDER.toString());
                    query.addAnd("status", PurchaseOrderStatus.RECEIVED.toString(), EMCQueryConditions.NOT);
                    query.addAnd("status", PurchaseOrderStatus.RECEIVED.toString(), EMCQueryConditions.NOT);
                    query.addAnd("itemId", itemId, POPPurchaseOrderLines.class.getName());
                    query.addAnd("itemDimension2", dimensionId, POPPurchaseOrderLines.class.getName());

                    List<String> blanketOrders = (List<String>) util.executeGeneralSelectQuery(query, userData);

                    if (!blanketOrders.isEmpty()) {
                        //Only log message for first order.
                        logMessage(Level.SEVERE, ServerInventoryMessageEnum.DIM_USED_ON_BO, userData, dimensionId, "POP", blanketOrders.get(0), itemRef);
                        return false;
                    }
                }
                if (blanketOrderQuantities[1] != null && ((BigDecimal) blanketOrderQuantities[1]).compareTo(BigDecimal.ZERO) > 0) {
                    //Select open Blanket Orders on which the item & dimension appears.
                    query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesOrderMaster.class);
                    query.addTableAnd(SOPSalesOrderLines.class.getName(), "salesOrderNo", SOPSalesOrderMaster.class.getName(), "salesOrderNo");
                    query.addField("salesOrderNo");
                    query.addAnd("salesOrderType", SalesOrderTypes.BLANKET_ORDER.toString());
                    query.addAnd("salsesOrderStatus", SalesOrderStatus.INVOICED.toString(), EMCQueryConditions.NOT);
                    query.addAnd("itemId", itemId, SOPSalesOrderLines.class.getName());
                    query.addAnd("dimension2", dimensionId, SOPSalesOrderLines.class.getName());

                    List<String> blanketOrders = (List<String>) util.executeGeneralSelectQuery(query, userData);

                    if (!blanketOrders.isEmpty()) {
                        //Only log message for first order.
                        logMessage(Level.SEVERE, ServerInventoryMessageEnum.DIM_USED_ON_BO, userData, dimensionId, "SOP", blanketOrders.get(0), itemRef);
                        return false;
                    }
                }
            }
        }

    

        return true;
    }

    private boolean checkStockOnHand(String dimensionId, String itemId, EMCUserData userData) {
        if ((summaryBean.getOnHandTotal(itemId, null, dimensionId, null, null, userData)).compareTo(BigDecimal.ZERO) > 0) {
            return true;
        }
        return false;
    }
}
