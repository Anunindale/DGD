/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.requirementsplanning;

import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.dimensions.InventoryDimensionTableLocal;
import emc.bus.inventory.pegging.InventoryPeggingLocal;
import emc.entity.inventory.pegging.InventoryPeggingTable;
import emc.entity.inventory.requirementsplanning.InventoryRequirementsPlanning;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.requirementsplanning.RequirementsPlanningFromType;
import emc.enums.inventory.requirementsplanning.RequirementsPlanningManagedBy;
import emc.enums.inventory.requirementsplanning.RequirementsPlanningReferenceTypes;
import emc.enums.inventory.requirementsplanning.RequirementsPlanningType;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class InventoryRequirementsPlanningBean extends EMCEntityBean implements InventoryRequirementsPlanningLocal {

    @EJB
    private InventoryDimensionTableLocal dimensionBean;
    @EJB
    private InventoryItemMasterLocal itemBean;
    @EJB
    private InventoryPeggingLocal peggingBean;

    @Override
    public void insertDemand(RequirementsPlanningFromType type, long recordID, String reference, Date demandDate, String itemId, String dimension1, String dimension2,
            String dimension3, String serialNo, BigDecimal quantity, int managedBy, EMCUserData userData) throws EMCEntityBeanException {
        InventoryRequirementsPlanning planning = new InventoryRequirementsPlanning();
        switch (type) {
            case SALES_ORDER:
                planning.setReferenceRecordType(RequirementsPlanningReferenceTypes.SALES_ORDER.getId());
                break;
            case FORECAST:
                planning.setReferenceRecordType(RequirementsPlanningReferenceTypes.FORECAST.getId());
                break;
            case WORKS_ORDER_BOM:
                planning.setReferenceRecordType(RequirementsPlanningReferenceTypes.WORKS_ORDER_BOM.getId());
                break;
        }
        planning.setItemId(itemId);
        planning.setDimensionId(dimensionBean.getDimRecordId(dimension1, dimension2, dimension3, null, userData));

        planning.setDemandDate(demandDate);
        planning.setDemandQuantity(quantity);

        planning.setRecordType(RequirementsPlanningType.DEMAND.getId());
        planning.setReferenceRecordID(recordID);
        planning.setReferenceRecordRef(reference);
        planning.setRecordLevel(0);
        planning.setManagedBy(managedBy);
        insert(planning, userData);
    }

    @Override
    public void updateDemand(RequirementsPlanningFromType type, long recordID, String reference, Date demandDate, String itemId, String dimension1, String dimension2,
            String dimension3, String serialNo, BigDecimal quantity, int managedBy, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryRequirementsPlanning.class);
        query.addAnd("referenceRecordID", recordID);
        switch (type) {
            case SALES_ORDER:
                query.addAnd("referenceRecordType", RequirementsPlanningReferenceTypes.SALES_ORDER.getId());
                break;
            case FORECAST:
                query.addAnd("referenceRecordType", RequirementsPlanningReferenceTypes.FORECAST.getId());
                break;
            case WORKS_ORDER_BOM:
                query.addAnd("referenceRecordType", RequirementsPlanningReferenceTypes.WORKS_ORDER_BOM.getId());
                break;
        }
        InventoryRequirementsPlanning planning = (InventoryRequirementsPlanning) util.executeSingleResultQuery(query, userData);
        if (planning == null) {
            insertDemand(type, recordID, reference, demandDate, itemId, dimension1, dimension2, dimension3, serialNo, quantity, managedBy, userData);
        } else {
            boolean updatePegging = false;
            if (quantity.compareTo(planning.getDemandQuantity()) < 0) {
                updatePegging = true;
            }
            switch (type) {
                case SALES_ORDER:
                    planning.setReferenceRecordType(RequirementsPlanningReferenceTypes.SALES_ORDER.getId());
                    break;
                case FORECAST:
                    planning.setReferenceRecordType(RequirementsPlanningReferenceTypes.FORECAST.getId());
                    break;
                case WORKS_ORDER_BOM:
                    planning.setReferenceRecordType(RequirementsPlanningReferenceTypes.WORKS_ORDER_BOM.getId());
                    break;
            }
            planning.setItemId(itemId);
            planning.setDimensionId(dimensionBean.getDimRecordId(dimension1, dimension2, dimension3, null, userData));

            planning.setDemandDate(demandDate);
            planning.setDemandQuantity(quantity);

            planning.setRecordType(RequirementsPlanningType.DEMAND.getId());
            planning.setReferenceRecordID(recordID);
            planning.setReferenceRecordRef(reference);
            planning.setRecordLevel(0);
            planning.setManagedBy(managedBy);

            update(planning, userData);

            if (updatePegging) {
                query = new EMCQuery(enumQueryTypes.SELECT, InventoryPeggingTable.class);
                query.addAnd("demandRef", planning.getRecordID());
                List<InventoryPeggingTable> peggingList = util.executeGeneralSelectQuery(query, userData);

                boolean fullyPegged = false;

                for (InventoryPeggingTable pegging : peggingList) {
                    if (BigDecimal.ZERO.compareTo(quantity) >= 0) {
                        fullyPegged = true;
                    }

                    if (fullyPegged) {
                        peggingBean.delete(pegging, userData);
                    } else if (pegging.getPeggingQuantity().compareTo(quantity) > 0) {
                        pegging.setPeggingQuantity(quantity);
                        peggingBean.update(pegging, userData);
                    }

                    quantity = quantity.subtract(pegging.getPeggingQuantity());
                }
            }
        }
    }

    @Override
    public void deleteDemand(RequirementsPlanningFromType type, long recordID, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryRequirementsPlanning.class);
        query.addAnd("referenceRecordID", recordID);
        switch (type) {
            case SALES_ORDER:
                query.addAnd("referenceRecordType", RequirementsPlanningReferenceTypes.SALES_ORDER.getId());
                break;
            case FORECAST:
                query.addAnd("referenceRecordType", RequirementsPlanningReferenceTypes.FORECAST.getId());
                break;
            case WORKS_ORDER_BOM:
                query.addAnd("referenceRecordType", RequirementsPlanningReferenceTypes.WORKS_ORDER_BOM.getId());
                break;
        }
        InventoryRequirementsPlanning planning = (InventoryRequirementsPlanning) util.executeSingleResultQuery(query, userData);
        if (planning != null) {
            query = new EMCQuery(enumQueryTypes.SELECT, InventoryPeggingTable.class);
            query.addAnd("demandRef", planning.getRecordID());
            List<InventoryPeggingTable> peggingList = util.executeGeneralSelectQuery(query, userData);

            for (InventoryPeggingTable pegging : peggingList) {
                peggingBean.delete(pegging, userData);
            }

            delete(planning, userData);
        }
    }

    @Override
    public void closeDemand(RequirementsPlanningFromType type, long recordID, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryRequirementsPlanning.class);
        query.addAnd("referenceRecordID", recordID);
        switch (type) {
            case SALES_ORDER:
                query.addAnd("referenceRecordType", RequirementsPlanningReferenceTypes.SALES_ORDER.getId());
                break;
            case FORECAST:
                query.addAnd("referenceRecordType", RequirementsPlanningReferenceTypes.FORECAST.getId());
                break;
            case WORKS_ORDER_BOM:
                query.addAnd("referenceRecordType", RequirementsPlanningReferenceTypes.WORKS_ORDER_BOM.getId());
                break;
        }
        InventoryRequirementsPlanning planning = (InventoryRequirementsPlanning) util.executeSingleResultQuery(query, userData);
        if (planning != null) {
            planning.setDemandClosed(true);
            update(planning, userData);
        }
    }

    @Override
    public void insertSupply(RequirementsPlanningFromType type, long recordID, String reference, Date supplyDate, Date supplyCompletionDate, String itemId, String dimension1, String dimension2,
            String dimension3, String serialNo, BigDecimal supplyQuantity, BigDecimal totalQuantity, boolean firm, int managedBy, EMCUserData userData) throws EMCEntityBeanException {

        if (supplyQuantity.compareTo(BigDecimal.ZERO) > 1) {
            supplyQuantity = BigDecimal.ZERO;
        }

        InventoryRequirementsPlanning planning = new InventoryRequirementsPlanning();
        switch (type) {
            case PLANNED_PURCHASE_ORDER:
                planning.setReferenceRecordType(RequirementsPlanningReferenceTypes.PLANNED_PURCHASE_ORDER.getId());
                break;
            case PURCHASE_ORDER:
                planning.setReferenceRecordType(RequirementsPlanningReferenceTypes.PURCHASE_ORDER.getId());
                break;
            case PLANNED_WORKS_ORDER:
                planning.setReferenceRecordType(RequirementsPlanningReferenceTypes.PLANNED_WORKS_ORDER.getId());
                break;
            case WORKS_ORDER:
                planning.setReferenceRecordType(RequirementsPlanningReferenceTypes.WORKS_ORDER.getId());
                break;
        }

        planning.setItemId(itemId);
        planning.setDimensionId(dimensionBean.getDimRecordId(dimension1, dimension2, dimension3, null, userData));

        planning.setSupplyDate(supplyDate);
        planning.setSupplyCompletionDate(supplyCompletionDate);
        planning.setSupplyQuantity(supplyQuantity);
        planning.setTotalQuantity(totalQuantity);

        planning.setRecordType(RequirementsPlanningType.SUPPLY.getId());
        planning.setReferenceRecordID(recordID);
        planning.setReferenceRecordRef(reference);
        planning.setRecordLevel(0);
        planning.setManagedBy(managedBy);
        planning.setFirm(firm);

        if (planning.getSupplyQuantity().compareTo(BigDecimal.ZERO) == 0) {
            planning.setSupplyClosed(true);
        }

        insert(planning, userData);
    }

    @Override
    public void updateSupply(RequirementsPlanningFromType type, long recordID, String reference, Date supplyDate, Date supplyCompletionDate, String itemId, String dimension1, String dimension2,
            String dimension3, String serialNo, BigDecimal supplyQuantity, BigDecimal totalQuantity, boolean firm, int managedBy, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryRequirementsPlanning.class);
        query.addAnd("referenceRecordID", recordID);
        switch (type) {
            case PLANNED_PURCHASE_ORDER:
                query.addAnd("referenceRecordType", RequirementsPlanningReferenceTypes.PLANNED_PURCHASE_ORDER.getId());
                break;
            case PURCHASE_ORDER:
                query.addAnd("referenceRecordType", RequirementsPlanningReferenceTypes.PURCHASE_ORDER.getId());
                break;
            case PLANNED_WORKS_ORDER:
                query.addAnd("referenceRecordType", RequirementsPlanningReferenceTypes.PLANNED_WORKS_ORDER.getId());
                break;
            case WORKS_ORDER:
                query.addAnd("referenceRecordType", RequirementsPlanningReferenceTypes.WORKS_ORDER.getId());
                break;
        }
        InventoryRequirementsPlanning planning = (InventoryRequirementsPlanning) util.executeSingleResultQuery(query, userData);
        if (planning == null) {
            insertSupply(type, recordID, reference, supplyDate, supplyCompletionDate, itemId, dimension1, dimension2, dimension3, serialNo, supplyQuantity, totalQuantity, firm, managedBy, userData);
        } else {
            if (supplyQuantity.compareTo(BigDecimal.ZERO) > 1) {
                supplyQuantity = BigDecimal.ZERO;
            }
            boolean updatePegging = false;
            if (supplyQuantity.compareTo(planning.getDemandQuantity()) < 0) {
                updatePegging = true;
            }
            switch (type) {
                case PLANNED_PURCHASE_ORDER:
                    planning.setReferenceRecordType(RequirementsPlanningReferenceTypes.PLANNED_PURCHASE_ORDER.getId());
                    break;
                case PURCHASE_ORDER:
                    planning.setReferenceRecordType(RequirementsPlanningReferenceTypes.PURCHASE_ORDER.getId());
                    break;
                case PLANNED_WORKS_ORDER:
                    planning.setReferenceRecordType(RequirementsPlanningReferenceTypes.PLANNED_WORKS_ORDER.getId());
                    break;
                case WORKS_ORDER:
                    planning.setReferenceRecordType(RequirementsPlanningReferenceTypes.WORKS_ORDER.getId());
                    break;
            }

            planning.setItemId(itemId);
            planning.setDimensionId(dimensionBean.getDimRecordId(dimension1, dimension2, dimension3, null, userData));

            planning.setSupplyDate(supplyDate);
            planning.setSupplyCompletionDate(supplyCompletionDate);
            planning.setSupplyQuantity(supplyQuantity);
            planning.setTotalQuantity(totalQuantity);

            planning.setRecordType(RequirementsPlanningType.SUPPLY.getId());
            planning.setReferenceRecordID(recordID);
            planning.setReferenceRecordRef(reference);
            planning.setRecordLevel(0);
            planning.setManagedBy(managedBy);
            planning.setFirm(firm);

            if (planning.getSupplyQuantity().compareTo(BigDecimal.ZERO) == 0) {
                planning.setSupplyClosed(true);
            }

            update(planning, userData);

            if (updatePegging) {
                query = new EMCQuery(enumQueryTypes.SELECT, InventoryPeggingTable.class);
                query.addAnd("supplyRef", planning.getRecordID());
                List<InventoryPeggingTable> peggingList = util.executeGeneralSelectQuery(query, userData);

                boolean fullyPegged = false;

                for (InventoryPeggingTable pegging : peggingList) {
                    if (BigDecimal.ZERO.compareTo(supplyQuantity) >= 0) {
                        fullyPegged = true;
                    }

                    if (fullyPegged) {
                        peggingBean.delete(pegging, userData);
                    } else if (pegging.getPeggingQuantity().compareTo(supplyQuantity) > 0) {
                        pegging.setPeggingQuantity(supplyQuantity);
                        peggingBean.update(pegging, userData);
                    }

                    supplyQuantity = supplyQuantity.subtract(pegging.getPeggingQuantity());
                }
            }
        }
    }

    @Override
    public void deleteSupply(RequirementsPlanningFromType type, long recordID, EMCUserData userData) throws EMCEntityBeanException {
        //Delete existing supply
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryRequirementsPlanning.class);
        query.addAnd("referenceRecordID", recordID);
        switch (type) {
            case PLANNED_PURCHASE_ORDER:
                query.addAnd("referenceRecordType", RequirementsPlanningReferenceTypes.PLANNED_PURCHASE_ORDER.getId());
                break;
            case PURCHASE_ORDER:
                query.addAnd("referenceRecordType", RequirementsPlanningReferenceTypes.PURCHASE_ORDER.getId());
                break;
            case PLANNED_WORKS_ORDER:
                query.addAnd("referenceRecordType", RequirementsPlanningReferenceTypes.PLANNED_WORKS_ORDER.getId());
                break;
            case WORKS_ORDER:
                query.addAnd("referenceRecordType", RequirementsPlanningReferenceTypes.WORKS_ORDER.getId());
                break;
        }
        InventoryRequirementsPlanning planning = (InventoryRequirementsPlanning) util.executeSingleResultQuery(query, userData);
        if (planning != null) {
            query = new EMCQuery(enumQueryTypes.SELECT, InventoryPeggingTable.class);
            query.addAnd("supplyRef", planning.getRecordID());
            List<InventoryPeggingTable> peggingList = util.executeGeneralSelectQuery(query, userData);

            for (InventoryPeggingTable pegging : peggingList) {
                peggingBean.delete(pegging, userData);
            }

            delete(planning, userData);
        }
    }

    @Override
    public void closeSupply(RequirementsPlanningFromType type, long recordID, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryRequirementsPlanning.class);
        query.addAnd("referenceRecordID", recordID);
        switch (type) {
            case PLANNED_PURCHASE_ORDER:
                query.addAnd("referenceRecordType", RequirementsPlanningReferenceTypes.PLANNED_PURCHASE_ORDER.getId());
                break;
            case PURCHASE_ORDER:
                query.addAnd("referenceRecordType", RequirementsPlanningReferenceTypes.PURCHASE_ORDER.getId());
                break;
            case PLANNED_WORKS_ORDER:
                query.addAnd("referenceRecordType", RequirementsPlanningReferenceTypes.PLANNED_WORKS_ORDER.getId());
                break;
            case WORKS_ORDER:
                query.addAnd("referenceRecordType", RequirementsPlanningReferenceTypes.WORKS_ORDER.getId());
                break;
        }
        InventoryRequirementsPlanning planning = (InventoryRequirementsPlanning) util.executeSingleResultQuery(query, userData);
        if (planning != null) {
            planning.setSupplyClosed(true);
            update(planning, userData);
        }
    }

    @Override
    public void populateReleaseOfPlanned(RequirementsPlanningFromType type, long fromRecordID, long toRecordID, String reference, Date supplyDate, String itemId, String dimension1, String dimension2,
            String dimension3, String serialNo, BigDecimal quantity, BigDecimal quantityRemaining, int managedBy, EMCUserData userData) throws EMCEntityBeanException {

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryRequirementsPlanning.class);
        query.addAnd("referenceRecordID", fromRecordID);
        switch (type) {
            case PLANNED_PURCHASE_ORDER:
                query.addAnd("referenceRecordType", RequirementsPlanningReferenceTypes.PLANNED_PURCHASE_ORDER.getId());
                break;
            case PLANNED_WORKS_ORDER:
                query.addAnd("referenceRecordType", RequirementsPlanningReferenceTypes.PLANNED_WORKS_ORDER.getId());
                break;
        }
        InventoryRequirementsPlanning planning = (InventoryRequirementsPlanning) util.executeSingleResultQuery(query, userData);

        InventoryRequirementsPlanning plannedPlanning = null;
        boolean updatePegging = false;

        if (planning != null) {
            if (quantityRemaining.compareTo(BigDecimal.ZERO) != 0) {
                plannedPlanning = new InventoryRequirementsPlanning();
                util.copyFields(planning, plannedPlanning, userData);
                plannedPlanning.setSupplyQuantity(quantityRemaining);
                plannedPlanning.setTotalQuantity(quantityRemaining);
                insert(plannedPlanning, userData);
                updatePegging = true;
            }

            query = new EMCQuery(enumQueryTypes.SELECT, InventoryRequirementsPlanning.class);
            query.addAnd("referenceRecordID", toRecordID);
            switch (type) {
                case PLANNED_PURCHASE_ORDER:
                    query.addAnd("referenceRecordType", RequirementsPlanningReferenceTypes.PURCHASE_ORDER.getId());
                    break;
                case PLANNED_WORKS_ORDER:
                    query.addAnd("referenceRecordType", RequirementsPlanningReferenceTypes.WORKS_ORDER.getId());
                    break;
            }
            InventoryRequirementsPlanning existingPlanning = (InventoryRequirementsPlanning) util.executeSingleResultQuery(query, userData);

            if (existingPlanning == null) {
                switch (type) {
                    case PLANNED_PURCHASE_ORDER:
                        planning.setReferenceRecordType(RequirementsPlanningReferenceTypes.PURCHASE_ORDER.getId());
                        break;
                    case PLANNED_WORKS_ORDER:
                        planning.setReferenceRecordType(RequirementsPlanningReferenceTypes.WORKS_ORDER.getId());
                        break;
                }
                planning.setItemId(itemId);
                planning.setDimensionId(dimensionBean.getDimRecordId(dimension1, dimension2, dimension3, null, userData));

                planning.setSupplyDate(supplyDate);
                planning.setSupplyQuantity(quantity);
                planning.setTotalQuantity(quantity);

                planning.setRecordType(RequirementsPlanningType.SUPPLY.getId());
                planning.setReferenceRecordID(toRecordID);
                planning.setReferenceRecordRef(reference);
                planning.setRecordLevel(0);
                planning.setManagedBy(managedBy);
                planning.setFirm(false);

                update(planning, userData);
            } else {
                existingPlanning.setSupplyQuantity(quantity);
                existingPlanning.setTotalQuantity(quantity);
                update(existingPlanning, userData);
                delete(planning, userData);
                updatePegging = true;
            }

            if (updatePegging) {
                query = new EMCQuery(enumQueryTypes.SELECT, InventoryPeggingTable.class);
                query.addAnd("supplyRef", planning.getRecordID());
                List<InventoryPeggingTable> peggingList = util.executeGeneralSelectQuery(query, userData);

                for (InventoryPeggingTable pegging : peggingList) {
                    if (quantity.compareTo(BigDecimal.ZERO) == 0) {
                        if (plannedPlanning != null) {
                            pegging.setSupplyRef(plannedPlanning.getRecordID());
                            peggingBean.update(pegging, userData);
                        } else {
                            peggingBean.delete(pegging, userData);
                        }
                    } else {
                        if (pegging.getPeggingQuantity().compareTo(quantity) > 0) {
                            if (plannedPlanning != null) {
                                InventoryPeggingTable plannedPegging = new InventoryPeggingTable();
                                util.copyFields(pegging, plannedPegging, userData);
                                plannedPegging.setPeggingQuantity(pegging.getPeggingQuantity().subtract(quantity));
                                plannedPegging.setSupplyRef(plannedPlanning.getRecordID());
                                peggingBean.insert(plannedPegging, userData);
                            }
                            pegging.setPeggingQuantity(quantity);
                            peggingBean.update(pegging, userData);
                        }
                        if (existingPlanning != null) {
                            pegging.setSupplyRef(existingPlanning.getReferenceRecordID());
                            peggingBean.update(pegging, userData);
                        }
                        quantity = quantity.subtract(pegging.getPeggingQuantity());
                    }
                }
            }
        } else {
            switch (type) {
                case PLANNED_PURCHASE_ORDER:
                    insertSupply(RequirementsPlanningFromType.PURCHASE_ORDER, toRecordID, reference, supplyDate, null, itemId, dimension1, dimension2, dimension3,
                            serialNo, quantity, quantity, false, managedBy, userData);
                    break;
                case PLANNED_WORKS_ORDER:
                    insertSupply(RequirementsPlanningFromType.WORKS_ORDER, toRecordID, reference, supplyDate, null, itemId, dimension1, dimension2, dimension3,
                            serialNo, quantity, quantity, false, managedBy, userData);
                    break;
            }
        }
    }

    @Override
    public InventoryRequirementsPlanning generateSafetyStockRecord(final Date demandDate, final String itemId, final String dimension1, final String dimension2, final String dimension3, final String serialNo, final BigDecimal quantity, final RequirementsPlanningManagedBy managedBy, final int level, final EMCUserData userData) throws EMCEntityBeanException {
        InventoryRequirementsPlanning planning = generateSafetyStockRecord(itemId, dimensionBean.getDimRecordId(dimension1, dimension2, dimension3, null, userData), demandDate, quantity, managedBy, userData);
        planning.setRecordLevel(level);

        return planning;
    }

    /**
     * Generates a safety stock requirements planning record using the specified
     * values. This method will not set the serial number field on the
     * requirements planning record. The requirements planning record created by
     * this method will not have a record ID or be persisted. Persisting the
     * record is up to the calling code.
     *
     * @param itemId Item ID.
     * @param itemDimId Item dimension ID.
     * @param demandDate Demand date.
     * @param quantity Demand quantity.
     * @param managedBy Managed by (MRP or MPS).
     * @param userData User data.
     * @return The safety stock requirements planning record that has been
     * generated.
     */
    @Override
    public InventoryRequirementsPlanning generateSafetyStockRecord(final String itemId, final long itemDimId, final Date demandDate, final BigDecimal quantity, final RequirementsPlanningManagedBy managedBy, final EMCUserData userData) {
        InventoryRequirementsPlanning planning = new InventoryRequirementsPlanning();
        planning.setReferenceRecordType(RequirementsPlanningReferenceTypes.SAFETY_STOCK.getId());
        planning.setItemId(itemId);
        planning.setDimensionId(itemDimId);

        planning.setDemandDate(demandDate);
        planning.setDemandQuantity(quantity);

        planning.setRecordType(RequirementsPlanningType.DEMAND.getId());
        planning.setReferenceRecordID(0);
        planning.setReferenceRecordRef("Safety Stock");
        planning.setManagedBy(managedBy.getId());

        return planning;
    }

    @Override
    public boolean cleanupRequirementsPlanningAndPegging(EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryRequirementsPlanning.class);
        query.openConditionBracket(EMCQueryBracketConditions.NONE);
        query.addOr("demandClosed", true);
        query.addOr("supplyClosed", true);
        query.closeConditionBracket();

        List<InventoryRequirementsPlanning> reqPlanList = util.executeGeneralSelectQuery(query, userData);
        List<InventoryRequirementsPlanning> recordsToRemove;
        List<InventoryPeggingTable> peggingToRemove;
        List<Long> removedRecords = new ArrayList<Long>();

        for (InventoryRequirementsPlanning reqPlan : reqPlanList) {
            if (!removedRecords.contains(reqPlan.getRecordID())) {
                recordsToRemove = new ArrayList<InventoryRequirementsPlanning>();
                recordsToRemove.add(reqPlan);
                recordsToRemove = checkPeggedRecord(reqPlan, 0, recordsToRemove, userData);
                for (InventoryRequirementsPlanning toRemove : recordsToRemove) {
                    if (!removedRecords.contains(toRemove.getRecordID())) {
                        removedRecords.add(toRemove.getRecordID());

                        query = new EMCQuery(enumQueryTypes.SELECT, InventoryPeggingTable.class);
                        query.addOr("supplyRef", toRemove.getRecordID());
                        query.addOr("demandRef", toRemove.getRecordID());
                        peggingToRemove = util.executeGeneralSelectQuery(query, userData);
                        for (InventoryPeggingTable pegging : peggingToRemove) {
                            peggingBean.delete(pegging, userData);
                        }

                        this.delete(toRemove, userData);
                    }
                }
            }
        }
        return true;
    }

    private List<InventoryRequirementsPlanning> checkPeggedRecord(InventoryRequirementsPlanning planning, long previousPlanning, List<InventoryRequirementsPlanning> recordsToRemove, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryRequirementsPlanning.class);
        query.addTableAnd(InventoryPeggingTable.class.getName(), "recordID", InventoryRequirementsPlanning.class.getName(), "demandRef");
        query.addTableAnd(InventoryRequirementsPlanning.class.getSimpleName(), "supplyRef", InventoryPeggingTable.class.getName(), "recordID");
        query.addAnd("recordID", planning.getRecordID(), InventoryRequirementsPlanning.class.getName());
        query.addAnd("recordID", planning.getRecordID(), InventoryRequirementsPlanning.class.getSimpleName(), EMCQueryConditions.NOT);
        query.addAnd("recordID", previousPlanning, InventoryRequirementsPlanning.class.getSimpleName(), EMCQueryConditions.NOT);
        query.addTableAsField(InventoryRequirementsPlanning.class.getSimpleName());
        List<InventoryRequirementsPlanning> peggedPlanningList = util.executeGeneralSelectQuery(query, userData);
        for (InventoryRequirementsPlanning peggedPlanning : peggedPlanningList) {
            if (peggedPlanning.isDemandClosed() || peggedPlanning.isSupplyClosed()) {
                recordsToRemove.add(planning);
                recordsToRemove = checkPeggedRecord(peggedPlanning, planning.getRecordID(), recordsToRemove, userData);
            } else {
                recordsToRemove.clear();
            }
            if (recordsToRemove.isEmpty()) {
                return recordsToRemove;
            }
        }

        query = new EMCQuery(enumQueryTypes.SELECT, InventoryRequirementsPlanning.class);
        query.addTableAnd(InventoryPeggingTable.class.getName(), "recordID", InventoryRequirementsPlanning.class.getName(), "supplyRef");
        query.addTableAnd(InventoryRequirementsPlanning.class.getSimpleName(), "demandRef", InventoryPeggingTable.class.getName(), "recordID");
        query.addAnd("recordID", planning.getRecordID(), InventoryRequirementsPlanning.class.getName());
        query.addAnd("recordID", planning.getRecordID(), InventoryRequirementsPlanning.class.getSimpleName(), EMCQueryConditions.NOT);
        query.addAnd("recordID", previousPlanning, InventoryRequirementsPlanning.class.getSimpleName(), EMCQueryConditions.NOT);
        query.addTableAsField(InventoryRequirementsPlanning.class.getSimpleName());
        peggedPlanningList = util.executeGeneralSelectQuery(query, userData);
        for (InventoryRequirementsPlanning peggedPlanning : peggedPlanningList) {
            if (peggedPlanning.isDemandClosed() || peggedPlanning.isSupplyClosed()) {
                recordsToRemove.add(planning);
                recordsToRemove = checkPeggedRecord(peggedPlanning, planning.getRecordID(), recordsToRemove, userData);
            } else {
                recordsToRemove.clear();
            }
            if (recordsToRemove.isEmpty()) {
                return recordsToRemove;
            }
        }
        return recordsToRemove;
    }
}
