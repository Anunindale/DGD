/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.dimensions;

import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.inventory.transactions.InventorySummaryLocal;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryParameters;
import emc.entity.inventory.InventoryReference;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimension1GroupSetup;
import emc.entity.inventory.dimensions.InventoryItemDimension1Setup;
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
import emc.messages.ServerInventoryMessageEnum;
import emc.tables.EMCTable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
public class InventoryItemDimension1SetupBean extends EMCEntityBean implements InventoryItemDimension1SetupLocal {

    @EJB
    private InventoryDimension1Local dimension1Bean;
    @EJB
    private InventoryReferenceLocal itemRefBean;
    @PersistenceContext
    private EntityManager em;
    @EJB
    private InventorySummaryLocal summaryBean;

    /** Creates a new instance of InventoryItemDimension1SetupBean */
    public InventoryItemDimension1SetupBean() {
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
            dimGroupQuery.addField("dimension1Group");

            Object dim1Group = util.executeSingleResultQuery(dimGroupQuery, userData);

            if (dim1Group != null && !dim1Group.equals("")) {
                dimGroupQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension1GroupSetup.class.getName());
                dimGroupQuery.addAnd("dimensionGroupId", dim1Group.toString());
                dimGroupQuery.addField("dimensionId");

                List<String> dimGroups = util.executeGeneralSelectQuery(dimGroupQuery, userData);

                InventoryItemDimension1Setup setup = null;

                for (String ob : dimGroups) {
                    setup = new InventoryItemDimension1Setup();
                    setup.setCompanyId(userData.getCompanyId());
                    setup.setItemId(itemId);
                    setup.setDimensionId(ob);

                    try {
                        super.insert(setup, userData);
                        //ret.add(setup);
                    } catch (EMCEntityBeanException ex) {
                        if (EMCDebug.getDebug()) {
                            Logger.getLogger("emc").log(Level.SEVERE, "Failed to insert new dimension on item dimension 1 setup table.", userData);
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
        InventoryItemDimension1Setup setup = (InventoryItemDimension1Setup) theRecord;
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
            ret = validateDimension1(setup.getDimensionId(), setup.getItemId(), userData);
        }

        if (setup.getRecordID() != 0) {
            //Only check whether the dimension has been used if the record has
            //already been saved.  Do not perform the logic for new records.
            if (fieldNameToValidate.equals("dimensionId") || (fieldNameToValidate.equals("active") && !setup.isActive())) {
                //Use persisted record to check whether it has been used.
                InventoryItemDimension1Setup persisted = (InventoryItemDimension1Setup) util.findDetachedPersisted(setup, userData);
                ret = ret && validateDimensionChangeDelete(persisted.getDimensionId(), persisted.getItemId(), userData);
            }
        }

        return ret;
    }

    /** This method is used to validate an item dimension.  */
    private boolean validateDimension1(String dimension, String item, EMCUserData userData) {
        //If dimensions which are not in group are allowed, do not validate.
        if (!isBlank(item) && !allowDim1NotInGroup(userData)) {
            Object dimensionGroup = null;

            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class.getName());
            query.addAnd("itemId", item);
            query.addField("dimension1Group");

            dimensionGroup = util.executeSingleResultQuery(query, userData);

            if (isBlank(dimensionGroup)) {
                Logger.getLogger("emc").log(Level.SEVERE, "No config group on item", userData);
                return false;
            } else {
                query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension1GroupSetup.class.getName());
                query.addAnd("dimensionId", dimension);
                query.addAnd("dimensionGroupId", dimensionGroup.toString());

                if (util.executeSingleResultQuery(query, userData) == null) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Dimension not on item config group.", userData);
                    return false;
                } else {
                    return true;
                }
            }
        }

        InventoryDimension1 dim1 = dimension1Bean.getDimension1(dimension, userData);

        if (!dim1.isActive()) {
            logMessage(Level.SEVERE, ServerInventoryMessageEnum.DIM1_NOT_ACTIVE, userData, dimension);
            return false;
        }

        return true;
    }

    /**
     * Returns a boolean which indicates whether dimensions which do not belong to the dimension 1 group specified on the item may be used.
     * @param userData
     * @return
     */
    private boolean allowDim1NotInGroup(EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryParameters.class);
        query.addAnd("companyId", userData.getCompanyId());
        InventoryParameters param = (InventoryParameters) util.executeSingleResultQuery(query, userData);
        return param == null ? false : param.isAllowDim1NotOnGroup();
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doUpdateValidation(vobject, userData);

        if (ret) {
            InventoryItemDimension1Setup dimension1Setup = (InventoryItemDimension1Setup) vobject;
            ret = ret && validateDimension1(dimension1Setup.getDimensionId(), dimension1Setup.getItemId(), userData);
        }

        return ret;
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean ret = super.doInsertValidation(vobject, userData);

        if (ret) {
            InventoryItemDimension1Setup dimension1Setup = (InventoryItemDimension1Setup) vobject;
            ret = ret && validateDimension1(dimension1Setup.getDimensionId(), dimension1Setup.getItemId(), userData);
        }

        return ret;
    }

    @Override
    public boolean doDeleteValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean valid = super.doDeleteValidation(vobject, userData);

        if (valid) {
            InventoryItemDimension1Setup dimensionSetup = (InventoryItemDimension1Setup) vobject;
            valid = valid && validateDimensionChangeDelete(dimensionSetup.getDimensionId(), dimensionSetup.getItemId(), userData);
        }

        return valid;
    }

    /**
     * This method is used to check whether a dimension has been used.  It should
     * be called when an InventoryItemDimension1Setup record is deleted, the dimension
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
        checkStockOnHand(dimensionId, itemId, userData);

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class);
        query.addTableAnd(InventorySummary.class.getName(), "recordID", InventoryTransactions.class.getName(), "inventoryTransRef");
        query.addAnd("itemId", itemId);
        query.addAnd("dimension1", dimensionId, InventorySummary.class.getName());
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
        query.addAnd("dimension1", dimensionId, InventorySummary.class.getName());
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
                    query.addAnd("itemDimension1", dimensionId, POPPurchaseOrderLines.class.getName());

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
                    query.addAnd("dimension1", dimensionId, SOPSalesOrderLines.class.getName());

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
        if ((summaryBean.getOnHandTotal(itemId, dimensionId, null, null, null, userData)).compareTo(BigDecimal.ZERO) > 0) {
            return true;
        } else {
            logMessage(Level.SEVERE, ServerInventoryMessageEnum.STOCK_ON_HAND, userData, dimensionId);
        }
        return false;
    }
}
