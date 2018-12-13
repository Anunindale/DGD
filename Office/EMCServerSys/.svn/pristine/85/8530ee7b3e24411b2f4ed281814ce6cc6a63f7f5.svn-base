/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.pop.planning;

import emc.bus.inventory.dimensions.InventoryDimensionTableLocal;
import emc.bus.inventory.requirementsplanning.InventoryRequirementsPlanningLocal;
import emc.bus.pop.POPParametersLocal;
import emc.bus.pop.POPPurchaseOrderLinesLocal;
import emc.bus.pop.POPPurchaseOrderMasterLocal;
import emc.bus.pop.pricearrangements.POPPriceArrangementsLocal;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryWarehouse;
import emc.entity.inventory.requirementsplanning.InventoryRequirementsPlanning;
import emc.entity.pop.POPParameters;
import emc.entity.pop.POPPurchaseOrderLines;
import emc.entity.pop.POPPurchaseOrderMaster;
import emc.entity.pop.POPSuppliers;
import emc.entity.pop.planning.POPPlannedPurchaseOrders;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.requirementsplanning.RequirementsPlanningFromType;
import emc.enums.inventory.requirementsplanning.RequirementsPlanningManagedBy;
import emc.enums.inventory.requirementsplanning.RequirementsPlanningReferenceTypes;
import emc.enums.inventory.requirementsplanning.RequirementsPlanningType;
import emc.enums.pop.plannedpurchaseorder.PlannedPOType;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.messages.ServerPOPMessageEnum;
import emc.tables.EMCTable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
public class POPPlannedPurchaseOrdersBean extends EMCEntityBean implements POPPlannedPurchaseOrdersLocal {

    @EJB
    private POPPurchaseOrderMasterLocal purchaseMasterBean;
    @EJB
    private POPPurchaseOrderLinesLocal purchaseLinesBean;
    @EJB
    private InventoryRequirementsPlanningLocal requirementsPlanningBean;
    @EJB
    private InventoryDimensionTableLocal dimTableBean;
    @EJB
    private POPPriceArrangementsLocal priceBean;
    @EJB
    private POPParametersLocal popParamBean;

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        return super.doInsertValidation(vobject, userData);
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        return super.doUpdateValidation(vobject, userData);
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        POPPlannedPurchaseOrders ppo = (POPPlannedPurchaseOrders) super.insert(iobject, userData);
        requirementsPlanningBean.insertSupply(RequirementsPlanningFromType.PLANNED_PURCHASE_ORDER, ppo.getRecordID(), ppo.getPlannedPOId(), ppo.getDateRequired(), null, ppo.getItemId(),
                                              ppo.getDimension1(), ppo.getDimension2(), ppo.getDimension3(), null, ppo.getQuantityRequired(), ppo.getQuantityRequired(), PlannedPOType.FIRM.toString().equals(ppo.getOrderType()), 0, userData);
        return ppo;
    }

    @Override
    public void insertFromMPS(List<POPPlannedPurchaseOrders> ppoList, EMCUserData userData) throws EMCEntityBeanException {
        if (!ppoList.isEmpty()) {
            long lastRecId = util.getRecordId(ppoList.size(), userData);
            util.insertDirect(POPPlannedPurchaseOrders.class, ppoList, true, true, lastRecId, true, userData);

            InventoryRequirementsPlanning requirementsPlanning;
            List<InventoryRequirementsPlanning> requirementsList = new ArrayList<InventoryRequirementsPlanning>();

            int count = 0;
            for (POPPlannedPurchaseOrders ppo : ppoList) {
                requirementsPlanning = new InventoryRequirementsPlanning();
                requirementsPlanning.setReferenceRecordID(lastRecId - count);
                requirementsPlanning.setReferenceRecordRef(ppo.getPlannedPOId());
                requirementsPlanning.setReferenceRecordType(RequirementsPlanningReferenceTypes.PLANNED_PURCHASE_ORDER.getId());
                requirementsPlanning.setItemId(ppo.getItemId());
                requirementsPlanning.setDimensionId(dimTableBean.getDimRecordId(ppo.getDimension1(), ppo.getDimension2(), ppo.getDimension3(), null, userData));
                requirementsPlanning.setRecordType(RequirementsPlanningType.SUPPLY.getId());
                requirementsPlanning.setSupplyDate(ppo.getDateRequired());
                requirementsPlanning.setSupplyQuantity(ppo.getQuantityRequired());
                requirementsPlanning.setFirm(PlannedPOType.FIRM.toString().equals(ppo.getOrderType()));
                requirementsPlanning.setManagedBy(RequirementsPlanningManagedBy.MPS.getId());

                requirementsList.add(requirementsPlanning);
            }

            util.insertDirect(InventoryRequirementsPlanning.class, requirementsList, userData);
        }
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        POPPlannedPurchaseOrders ppo = (POPPlannedPurchaseOrders) uobject;
        RequirementsPlanningManagedBy manBy;
        switch (PlannedPOType.fromString(ppo.getOrderType())) {
            case MPS:
                manBy = RequirementsPlanningManagedBy.MPS;
                break;
            case MRP:
                manBy = RequirementsPlanningManagedBy.MRP;
                break;
            default:
                manBy = RequirementsPlanningManagedBy.NONE;
                break;
        }
        requirementsPlanningBean.updateSupply(RequirementsPlanningFromType.PLANNED_PURCHASE_ORDER, ppo.getRecordID(), ppo.getPlannedPOId(), ppo.getDateRequired(), null, ppo.getItemId(),
                                              ppo.getDimension1(), ppo.getDimension2(), ppo.getDimension3(), null, ppo.getQuantityRequired(), ppo.getQuantityRequired(), PlannedPOType.FIRM.toString().equals(ppo.getOrderType()), manBy.getId(), userData);
        return super.update(uobject, userData);
    }

    @Override
    public Object delete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        POPPlannedPurchaseOrders ppo = (POPPlannedPurchaseOrders) dobject;
        requirementsPlanningBean.deleteSupply(RequirementsPlanningFromType.PLANNED_PURCHASE_ORDER, ppo.getRecordID(), userData);
        return super.delete(dobject, userData);
    }

    @Override
    public boolean firmOrder(String plannedPOId, EMCUserData userData) throws EMCEntityBeanException {
        POPPlannedPurchaseOrders plannedLine = findPlannedPurchaseOrder(plannedPOId, userData);
        if (plannedLine == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to find planned purchase order: " + plannedPOId, userData);
            return false;
        } else {
            plannedLine.setOrderType(PlannedPOType.FIRM.toString());
            this.update(plannedLine, userData);
            return true;
        }
    }

    private POPPlannedPurchaseOrders findPlannedPurchaseOrder(String orderId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPlannedPurchaseOrders.class);
        query.addAnd("plannedPOId", orderId);
        return (POPPlannedPurchaseOrders) util.executeSingleResultQuery(query, userData);
    }

    @Override
    public String releasePlannedPO(Date requiredDate, String supplier, String warehouse, Map<Long, Long> versionMap, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPlannedPurchaseOrders.class);
        query.addAndInList("recordID", new ArrayList(versionMap.keySet()), false, false);
        List<POPPlannedPurchaseOrders> plannedOrderList = util.executeGeneralSelectQuery(query, userData);
        if (plannedOrderList.isEmpty()) {
            logMessage(Level.SEVERE, ServerPOPMessageEnum.NO_PLANNED_ORDERS, userData);
            return null;
        } else {
            POPPurchaseOrderMaster purchaseMaster = createPurchaseMaster(requiredDate, supplier, warehouse, userData);
            POPPurchaseOrderLines purchaseLine;
            double lineNumber = 0;
            Map<String, List<List<EMCEntityClass>>> purchaseLineMap = new HashMap<String, List<List<EMCEntityClass>>>();
            List<List<EMCEntityClass>> mapListHolder;
            List<EMCEntityClass> purchaseList;
            List<EMCEntityClass> plannedList;
            String purchaseLineKey;
            for (POPPlannedPurchaseOrders plannedOrder : plannedOrderList) {
                if (versionMap.get(plannedOrder.getRecordID()) != plannedOrder.getVersion()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Planned order version does not match. The record may have been updated.", userData);
                    return null;
                }
                purchaseLineKey = plannedOrder.getItemId() + plannedOrder.getDimension1() + plannedOrder.getDimension2() + plannedOrder.getDimension3();

                mapListHolder = purchaseLineMap.get(purchaseLineKey);
                if (mapListHolder == null) {
                    mapListHolder = new ArrayList<List<EMCEntityClass>>();
                    mapListHolder.add(new ArrayList<EMCEntityClass>());
                    mapListHolder.add(new ArrayList<EMCEntityClass>());
                }

                purchaseList = mapListHolder.get(0);
                plannedList = mapListHolder.get(1);

                if (purchaseList.isEmpty()) {
                    purchaseLine = null;
                } else {
                    purchaseLine = (POPPurchaseOrderLines) purchaseList.get(0);
                }

                if (purchaseLine == null) {
                    purchaseLine = new POPPurchaseOrderLines();
                    purchaseLine.setPurchaseOrderId(purchaseMaster.getPurchaseOrderId());
                    purchaseLine.setLineNo(lineNumber);
                    purchaseLine.setDeliveryDate(purchaseMaster.getRequestedDeliveryDate());
                    purchaseLine.setItemId(plannedOrder.getItemId());
                    purchaseLine.setItemDimension1(plannedOrder.getDimension1());
                    purchaseLine.setItemDimension2(plannedOrder.getDimension2());
                    purchaseLine.setItemDimension3(plannedOrder.getDimension3());
                    purchaseLine.setQuantity(plannedOrder.getQuantityToRelease().doubleValue());
                    purchaseLine.setUom(plannedOrder.getUom());
                    purchaseLine.setVatCode(plannedOrder.getVatCode());
                    purchaseLine.setItemPrice(plannedOrder.getItemPrice());
                    if (!isBlank(plannedOrder.getWarehouse())) {
                        purchaseLine.setWarehouse(plannedOrder.getWarehouse());
                    } else {
                        purchaseLine.setWarehouse(purchaseMaster.getWarehouse());
                    }
                    
                    if (util.compareDouble(purchaseLine.getItemPrice(), 0) == 0) {
                        BigDecimal itemPrice = priceBean.findItemPrice(supplier, purchaseLine.getItemId(), purchaseLine.getItemDimension1(), purchaseLine.getItemDimension2(), purchaseLine.getItemDimension3(), requiredDate, util.getBigDecimal(purchaseLine.getQuantity()), userData);
                        if (itemPrice.compareTo(BigDecimal.ZERO) == 0) {
                            Logger.getLogger("emc").log(Level.SEVERE, "The item price on PPO " + plannedOrder.getPlannedPOId() + " was zero and no price was found.", userData);
                            throw new EMCEntityBeanException("Zero price is not allowed on PPO " + plannedOrder.getPlannedPOId());
                        }
                        purchaseLine.setItemPrice(itemPrice.doubleValue());
                    }
                    lineNumber += 10;
                } else {
                    purchaseLine.setQuantity(purchaseLine.getQuantity() + plannedOrder.getQuantityToRelease().doubleValue());
                }

                purchaseList = new ArrayList<EMCEntityClass>();
                purchaseList.add(purchaseLine);

                plannedList.add(plannedOrder);

                mapListHolder.set(0, purchaseList);
                mapListHolder.set(1, plannedList);

                purchaseLineMap.put(purchaseLineKey, mapListHolder);
            }

            POPPlannedPurchaseOrders plannedOrder;
            for (List<List<EMCEntityClass>> holder : purchaseLineMap.values()) {
                purchaseList = holder.get(0);
                plannedList = holder.get(1);

                purchaseLine = (POPPurchaseOrderLines) purchaseList.get(0);

                purchaseLinesBean.insertFromRelease(purchaseLine, userData);

                for (EMCEntityClass e : plannedList) {
                    plannedOrder = (POPPlannedPurchaseOrders) e;
                    requirementsPlanningBean.populateReleaseOfPlanned(RequirementsPlanningFromType.PLANNED_PURCHASE_ORDER, plannedOrder.getRecordID(),
                                                                      purchaseLine.getRecordID(), purchaseLine.getPurchaseOrderId(), purchaseLine.getDeliveryDate(), purchaseLine.getItemId(),
                                                                      purchaseLine.getItemDimension1(), purchaseLine.getItemDimension2(), purchaseLine.getItemDimension3(), null,
                                                                      util.getBigDecimal(purchaseLine.getQuantity()), plannedOrder.getQuantityRequired().subtract(plannedOrder.getQuantityToRelease()), 0, userData);

                    if (plannedOrder.getQuantityRequired().subtract(plannedOrder.getQuantityToRelease()).compareTo(BigDecimal.ZERO) <= 0) {
                        this.delete(plannedOrder, userData);
                    } else {
                        plannedOrder.setQuantityRequired(plannedOrder.getQuantityRequired().subtract(plannedOrder.getQuantityToRelease()));
                        plannedOrder.setQuantityToRelease(plannedOrder.getQuantityRequired());
                        plannedOrder.setIncludeOnRelease(false);
                        this.update(plannedOrder, userData);
                    }
                }
            }
            return purchaseMaster.getPurchaseOrderId();
        }
    }

    private POPPurchaseOrderMaster createPurchaseMaster(Date dateRequired, String supplierId, String warehouseId, EMCUserData userData) throws EMCEntityBeanException {
        POPParameters param = popParamBean.getPOPParameters(userData);

        POPPurchaseOrderMaster purchaseMaster = new POPPurchaseOrderMaster();
        purchaseMaster.setRequestedDeliveryDate(dateRequired);
        //Supplier
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPSuppliers.class);
        query.addAnd("supplierId", supplierId);
        POPSuppliers supplier = (POPSuppliers) util.executeSingleResultQuery(query, userData);
        purchaseMaster.setSupplier(supplier.getSupplierId());
        purchaseMaster.setCurrency(supplier.getSupplierCurrency());
        purchaseMaster.setContactPerson(supplier.getContactOrders());
        purchaseMaster.setContactEmail(supplier.getContactOrdersEmail());
        purchaseMaster.setContactNo(supplier.getContactOrdersPhone());
        purchaseMaster.setPriceGroup(supplier.getPriceGroup());
        purchaseMaster.setVatApplicable(supplier.getVatApplicable());
        //Warehouse
        query = new EMCQuery(enumQueryTypes.SELECT, InventoryWarehouse.class);
        query.addAnd("warehouseId", warehouseId);
        InventoryWarehouse warehouse = (InventoryWarehouse) util.executeSingleResultQuery(query, userData);
        purchaseMaster.setWarehouse(warehouse.getWarehouseId());
        purchaseMaster.setAddressLine1(warehouse.getAddressLine1());
        purchaseMaster.setAddressLine2(warehouse.getAddressLine2());
        purchaseMaster.setAddressLine3(warehouse.getAddressLine3());
        purchaseMaster.setAddressLine4(warehouse.getAddressLine4());
        purchaseMaster.setAddressLine5(warehouse.getAddressLine5());
        purchaseMaster.setAddressPostalCode(warehouse.getPostalCode());

        if (param != null && !isBlank(param.getDefaultApprovalGroup())) {
            purchaseMaster.setApprovalGrp(param.getDefaultApprovalGroup());
        }
        purchaseMasterBean.insert(purchaseMaster, userData);
        return purchaseMaster;
    }

    @Override
    public Object[] findDefaultItemValues(String itemId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class);
        query.addAnd("itemId", itemId);
        query.addField("defaultWarehouse");
        query.addField("purchaseDefaultSupplier");
        query.addField("itemReference");
        return (Object[]) util.executeSingleResultQuery(query, userData);
    }
}
