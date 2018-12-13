/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.logic;

import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.inventory.dimensions.InventoryItemDimension1SetupLocal;
import emc.bus.inventory.dimensions.InventoryItemDimension2SetupLocal;
import emc.bus.inventory.dimensions.InventoryItemDimension3SetupLocal;
import emc.bus.inventory.dimensions.InventoryItemDimensionCombinationsLocal;
import emc.constants.systemConstants;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.entity.inventory.dimensions.InventoryItemDimensionGroup;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.entity.inventory.dimensions.InventoryItemDimension1Setup;
import emc.entity.inventory.dimensions.InventoryItemDimension2Setup;
import emc.entity.inventory.dimensions.InventoryItemDimension3Setup;
import emc.entity.inventory.transactions.InventorySummary;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.dimensions.EnumDimensions;
import emc.framework.EMCBean;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.inventory.ItemDimensionInterface;
import emc.messages.ServerInventoryMessageEnum;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class InventoryItemDimensionCombinationLogicBean extends EMCBean implements InventoryItemDimensionCombinationLogicLocal {

    @EJB
    private InventoryReferenceLocal referenceBean;
    @EJB
    private InventoryItemDimensionCombinationsLocal dimensionCombinationsBean;
    @EJB
    private InventoryItemDimension1SetupLocal itemDim1Setup;
    @EJB
    private InventoryItemDimension2SetupLocal itemDim2Setup;
    @EJB
    private InventoryItemDimension3SetupLocal itemDim3Setup;
    @EJB
    private InventoryItemMasterLocal itemMasterBean;

    /**
     *  Calculate combinations when the dimension group of an item is updated.  Returns a boolean indicating whether the generation was succesful.
     */
    public boolean calculateCombinations(String itemId, EMCUserData userData) throws EMCEntityBeanException {
        boolean[] activeDimensions = getActiveDimensions(itemId, userData);

        boolean combinationsExist = dimensionCombinationsBean.checkCombinationsExist(itemId, userData);

        //Delete inactive dimensions on Item Dimension Setup tables.
        deleteInactiveDimensions(itemId, activeDimensions, userData);

        //Dimension 1
        //List<Object> dimension1 = util.executeGeneralSelectQuery(query.replaceAll("\\?1", "1").replaceAll("\\?2", companyId), userData);
        List<Object> dimension1 = util.executeGeneralSelectQuery(getSelectQuery(itemId, 1), userData);
        if (dimension1.size() == 0) {
            dimension1.add(systemConstants.emcNA());
        }

        //Dimension 2
        List<Object> dimension2 = util.executeGeneralSelectQuery(getSelectQuery(itemId, 2), userData);
        if (dimension2.size() == 0) {
            dimension2.add(systemConstants.emcNA());
        }

        //Dimension 3
        List<Object> dimension3 = util.executeGeneralSelectQuery(getSelectQuery(itemId, 3), userData);
        if (dimension3.size() == 0) {
            dimension3.add(systemConstants.emcNA());
        }

        if (!(activeDimensions[0] || activeDimensions[1] || activeDimensions[2])) {
            return false;
        }

        InventoryItemDimensionCombinations currentCombination = null;

        //Query to delete unmatched records.
        String dimComboclassName = InventoryItemDimensionCombinations.class.getName();
        EMCQuery deleteQuery = new EMCQuery(enumQueryTypes.DELETE, dimComboclassName);
        deleteQuery.addAnd("itemId", itemId);
        deleteQuery.addAnd("companyId", util.getCompanyId(dimComboclassName, userData));

        List recordsToIgnore = new ArrayList();

        for (Object dim1 : dimension1) {
            for (Object dim2 : dimension2) {
                for (Object dim3 : dimension3) {
                    if (!((activeDimensions[0] && dim1.toString().equals(systemConstants.emcNA()) || (activeDimensions[1] && dim2.toString().equals(systemConstants.emcNA()) || (activeDimensions[2] && dim3.toString().equals(systemConstants.emcNA())))))) {
                        try {

                            InventoryItemDimensionCombinations toInsert = new InventoryItemDimensionCombinations();
                            //toInsert.setCompanyId(companyId);
                            toInsert.setItemId(itemId);
                            toInsert.setDimension1Id(dim1.toString());
                            toInsert.setDimension2Id(dim2.toString());
                            toInsert.setDimension3Id(dim3.toString());

                            EMCQuery checkQuery = new EMCQuery(enumQueryTypes.SELECT, dimComboclassName);
                            checkQuery.addAnd("dimension1Id", dim1);
                            checkQuery.addAnd("dimension2Id", dim2);
                            checkQuery.addAnd("dimension3Id", dim3);
                            checkQuery.addAnd("itemId", itemId);
                            //checkQuery.addAnd("companyId", companyId);

                            currentCombination = (InventoryItemDimensionCombinations) util.executeSingleResultQuery(checkQuery, userData);
                            if (currentCombination == null) {
                                currentCombination = (InventoryItemDimensionCombinations) dimensionCombinationsBean.insert(toInsert, userData);
                            }

                            recordsToIgnore.add(currentCombination.getRecordID());
                        } catch (EMCEntityBeanException ex) {
                            if (EMCDebug.getDebug()) {
                                Logger.getLogger("emc").log(Level.SEVERE, "Failed to generate combinations", userData);
                            }
                            throw ex;
                        }
                    }
                }
            }
        }

        if (recordsToIgnore != null && !recordsToIgnore.isEmpty()) {
            deleteQuery.addAndInList("recordID", recordsToIgnore, false, true);
        }

        //Delete unmatched combinations
        if (EMCDebug.getDebug()) {
            System.out.println("Query - " + deleteQuery.toString());
        }
        util.executeUpdate(deleteQuery, userData);

        return true;
    }

    /** 
     *Overloaded calculateCombinations() method used to generate combinations when a dimension is added to an item
     */
    public void calculateCombinations(String itemId, String newDimension, List<Object> newValues, EMCUserData userData) {
        boolean[] activeDimensions = getActiveDimensions(itemId, userData);

        //String companyId = (this.isSystemTable() ? "emc" : userData.getCompanyId());
        String companyId = userData.getCompanyId();

        String query = "SELECT d.dimensionId FROM InventoryDimension?1 d, " +
                "InventoryItemMaster i, InventoryItemDimensionGroup g " +
                "WHERE i.dimensionGroup = g.itemDimensionGroupId " +
                "AND g.dim?1Active = true " +
                "AND d.itemId = i.itemId " +
                "AND d.companyId = '?2' AND d.itemId = '" + itemId + "' " +
                "AND g.companyId = '?2' AND i.companyId = '?2'";

        //Dimension 1
        List<Object> dimension1 = null;
        if (newDimension.equals("Dimension1")) {
            dimension1 = newValues;
        } else {
            dimension1 = util.executeGeneralSelectQuery(query.replaceAll("\\?1", "1").replace("?2", companyId), userData);
            if (dimension1.size() == 0) {
                dimension1.add(systemConstants.emcNA());
            }
        }

        //Dimension 2
        List<Object> dimension2 = null;
        if (newDimension.equals("Dimension2")) {
            dimension2 = newValues;
        } else {
            dimension2 = util.executeGeneralSelectQuery(query.replaceAll("\\?1", "2").replace("?2", companyId), userData);
            if (dimension1.size() == 0) {
                dimension1.add(systemConstants.emcNA());
            }
        }

        //Dimension 3
        List<Object> dimension3 = null;
        if (newDimension.equals("Dimension3")) {
            dimension3 = newValues;
        } else {
            dimension3 = util.executeGeneralSelectQuery(query.replaceAll("\\?1", "3").replace("?2", companyId), userData);
            if (dimension3.size() == 0) {
                dimension3.add(systemConstants.emcNA());
            }
        }

        if (!(activeDimensions[0] || activeDimensions[1] || activeDimensions[2])) {
            return;
        }

        for (Object dim1 : dimension1) {
            for (Object dim2 : dimension2) {
                for (Object dim3 : dimension3) {
                    if (!((activeDimensions[0] && dim1.toString().equals(systemConstants.emcNA()) || (activeDimensions[1] && dim2.toString().equals(systemConstants.emcNA()) || (activeDimensions[2] && dim3.toString().equals(systemConstants.emcNA())))))) {
                        try {

                            InventoryItemDimensionCombinations toInsert = new InventoryItemDimensionCombinations();
                            toInsert.setCompanyId(companyId);
                            toInsert.setItemId(itemId);
                            toInsert.setDimension1Id(dim1.toString());
                            toInsert.setDimension2Id(dim2.toString());
                            toInsert.setDimension3Id(dim3.toString());

                            dimensionCombinationsBean.insert(toInsert, userData);
                        } catch (EMCEntityBeanException ex) {
                            Logger.getLogger("emc").log(Level.SEVERE, "Failed to generate combinations", userData);
                        }
                    }
                }
            }
        }
    }

    /** Deletes Item Dimension Combinations when a dimension is deleted */
    public void deleteCombinations(String dimensionIdFieldName, String dimensionId, EMCUserData userData) {
        String companyId = userData.getCompanyId();

        String queryStr = "DELETE FROM InventoryItemDimensionCombinations c " +
                "WHERE c." + dimensionIdFieldName + " = '" + dimensionId + "' " +
                "AND c.companyId = '" + companyId + "'";

        util.executeUpdate(queryStr, userData);
    }

    /** Updates Item Dimension Combinations when a dimension is updated */
    public void updateCombinations(String dimensionIdFieldName, String oldDimensionId, String newDimensionId, EMCUserData userData) {
        String companyId = util.getCompanyId(InventoryItemDimensionCombinations.class.getName(), userData);

        String dimensionCombination = null;

        if (dimensionIdFieldName.equals("dimension1")) {
            dimensionCombination = "'" + newDimensionId + "' + c.dimension2Id + c.dimension3Id";
        } else if (dimensionIdFieldName.equals("dimension2")) {
            dimensionCombination = "c.dimension1Id + '" + newDimensionId + "' + c.dimension3Id";
        } else if (dimensionIdFieldName.equals("dimension3")) {
            dimensionCombination = "c.dimension1Id + c.dimension2Id + '" + newDimensionId + "'";
        }

        String queryStr = "UPDATE InventoryItemDimensionCombinations c " +
                "SET c." + dimensionIdFieldName + " = '" + newDimensionId + "', " +
                "c.dimensionCombination = '" + dimensionCombination + "' " +
                "WHERE c." + dimensionIdFieldName + " = '" + oldDimensionId + "' " +
                "AND c.companyId = '" + companyId + "'";

        util.executeUpdate(queryStr, userData);
    }

    /**
     * Returns active dimensions for the given item in an array.
     * @param itemId  Item to get dimensions for.
     * @param userData User data.
     * @return An array containing booleans indicating where dimensions are active.  Indexes are as follows:
     *         0: Dimension 1 
     *         1: Dimension 2 
     *         2: Dimension 3 
     *         3: Warehouse 
     */
    public boolean[] getActiveDimensions(String itemId, EMCUserData userData) {
        boolean[] ret = new boolean[8];

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class.getName());
        query.addAnd("itemId", itemId);

        InventoryItemMaster item = (InventoryItemMaster) util.executeSingleResultQuery(query, userData);

        if (item == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "No item found", userData);
        } else {
            query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionGroup.class.getName());
            query.addAnd("itemDimensionGroupId", item.getDimensionGroup());

            InventoryItemDimensionGroup dimGroup = (InventoryItemDimensionGroup) util.executeSingleResultQuery(query, userData);

            if (dimGroup == null) {
                Logger.getLogger("emc").log(Level.SEVERE, "Dimension Group not found", userData);
            } else {
                ret[0] = dimGroup.getDim1Active();
                ret[1] = dimGroup.getDim2Active();
                ret[2] = dimGroup.getDim3Active();
                ret[3] = dimGroup.getWarehouseActive();
                ret[4] = dimGroup.getLocationActive();
                ret[5] = dimGroup.getBatchNumberActive();
                ret[6] = dimGroup.getSerialNumberActive();
                ret[7] = dimGroup.getPalletIdActive();
            }
        }

        return ret;
    }

    /** This method is used to clear dimension combinations on a specified item */
    private void clearOldCombinations(String itemId, EMCUserData userData) {
        String companyId = util.getCompanyId(InventoryDimension1.class.getName(), userData);

        String query = "DELETE FROM InventoryItemDimensionCombinations c " +
                "WHERE c.itemId = '" + itemId + "' " +
                "AND c.companyId = '" + companyId + "'";

        util.executeUpdate(query, userData);

    }

    public boolean validateDimensionValues(String itemId, String dimension1, String dimension2, String dimension3, boolean logMessage, EMCUserData userData) {
        boolean ret = true;
        boolean allDimensionsInactive = isAllDimensionsInactive(itemId, userData);
        if (allDimensionsInactive && (!isBlank(dimension1) || !isBlank(dimension2) || !isBlank(dimension3))) {
            String[] refArr = referenceBean.checkItemReference(itemId, userData);

            String itemRef = refArr == null || refArr[1] == null ? itemId : refArr[1];

            logMessage(Level.SEVERE, ServerInventoryMessageEnum.NO_DIMENSIONS_ALLOWED, userData, itemRef);
            return false;
        }

        if (!isBlank(itemId)) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionCombinations.class.getName());
            query.addAnd("itemId", itemId, InventoryItemDimensionCombinations.class.getName());
            query.addAnd("active", true);

            if (!isBlank(dimension1)) {
                query.addAnd("dimension1Id", dimension1);
            }

            if (!isBlank(dimension2)) {
                query.addAnd("dimension2Id", dimension2);
            }

            if (!isBlank(dimension3)) {
                query.addAnd("dimension3Id", dimension3);
            }

            ret = util.exists(query, userData);

            if (!ret && !allDimensionsInactive) {
                if (logMessage) {
                    String[] refArr = referenceBean.checkItemReference(itemId, userData);

                    String itemRef = refArr == null || refArr[1] == null ? itemId : refArr[1];

                    logMessage(Level.SEVERE, ServerInventoryMessageEnum.INVALID_DIMENSIONS_ON_ITEM, userData, itemRef, dimension1, dimension2, dimension3);
                }
            }
        } else {
            Logger.getLogger("emc").log(Level.SEVERE, "No item specified!", userData);
        }

        //If no value found and all inactive, return true.
        return (!ret == allDimensionsInactive);
    }

    /** This method is used to validate dimensions on an item */
    public boolean validateDimensionValues(String itemId, String dimension1, String dimension2, String dimension3, EMCUserData userData) {
        return validateDimensionValues(itemId, dimension1, dimension2, dimension3, true, userData);
    }

    /** This method is used to check that all the active dimensions on an item have values */
    public boolean validateActiveDimensions(String itemId, String dimension1, String dimension2, String dimension3, String warehouse, boolean checkWarehouse, boolean logMessages, EMCUserData userData) {
        boolean[] dimensions = getActiveDimensions(itemId, userData);
        boolean ret = true;

        if (dimensions[0]) {
            if (isBlank(dimension1)) {
                if (logMessages) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Config is active on item dimension group.  Must have a value", userData);
                }
                ret = false;
            }
        }

        if (dimensions[1]) {
            if (isBlank(dimension2)) {
                if (logMessages) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Size is active on item dimension group.  Must have a value. (" + itemId + ")", userData);
                }
                ret = false;
            }
        }

        if (dimensions[2]) {
            if (isBlank(dimension3)) {
                if (logMessages) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Colour is active on item dimension group.  Must have a value. (" + itemId + ")", userData);
                }
                ret = false;
            }
        }

        if (dimensions[3] && checkWarehouse) {
            if (isBlank(warehouse)) {
                if (logMessages) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Warehouse is active on item dimension group.  Must have a value. (" + itemId + ")", userData);
                }
                ret = false;
            }
        }

        return ret;
    }

    public boolean validateActiveDimensions(String itemId, String dimension1, String dimension2, String dimension3, String warehouse, String location, String batch, String serial, String pallet, EMCUserData userData) {
        boolean[] dimensions = getActiveDimensions(itemId, userData);
        boolean ret = true;

        if (dimensions[0]) {
            if (isBlank(dimension1)) {
                Logger.getLogger("emc").log(Level.SEVERE, "Config is active on item dimension group.  Must have a value", userData);
                ret = false;
            }
        } else if (!isBlank(dimension1)) {
            Logger.getLogger("emc").log(Level.SEVERE, "Config is not active on item dimension group.  May not have a value", userData);
            ret = false;
        }

        if (dimensions[1]) {
            if (isBlank(dimension2)) {
                Logger.getLogger("emc").log(Level.SEVERE, "Size is active on item dimension group.  Must have a value. (" + itemId + ")", userData);
                ret = false;
            }
        } else if (!isBlank(dimension2)) {
            Logger.getLogger("emc").log(Level.SEVERE, "Size is not active on item dimension group.  May not have a value", userData);
            ret = false;
        }

        if (dimensions[2]) {
            if (isBlank(dimension3)) {
                Logger.getLogger("emc").log(Level.SEVERE, "Colour is active on item dimension group.  Must have a value. (" + itemId + ")", userData);
                ret = false;
            }
        } else if (!isBlank(dimension3)) {
            Logger.getLogger("emc").log(Level.SEVERE, "Colour is not active on item dimension group.  May not have a value", userData);
            ret = false;
        }

        if (dimensions[3]) {
            if (isBlank(warehouse)) {
                Logger.getLogger("emc").log(Level.SEVERE, "Warehouse is active on item dimension group.  Must have a value. (" + itemId + ")", userData);
                ret = false;
            }
        } else if (!isBlank(warehouse)) {
            Logger.getLogger("emc").log(Level.SEVERE, "Warehouse is not active on item dimension group.  May not have a value", userData);
            ret = false;
        }

        if (dimensions[4]) {
            if (isBlank(location)) {
                Logger.getLogger("emc").log(Level.SEVERE, "Location is active on item dimension group.  Must have a value. (" + itemId + ")", userData);
                ret = false;
            }
        } else if (!isBlank(location)) {
            Logger.getLogger("emc").log(Level.SEVERE, "Location is not active on item dimension group.  May not have a value", userData);
            ret = false;
        }

        if (dimensions[5]) {
            if (isBlank(batch)) {
                Logger.getLogger("emc").log(Level.SEVERE, "Batch is active on item dimension group.  Must have a value. (" + itemId + ")", userData);
                ret = false;
            }
        } else if (!isBlank(batch)) {
            Logger.getLogger("emc").log(Level.SEVERE, "Batch is not active on item dimension group.  May not have a value", userData);
            ret = false;
        }

        if (dimensions[6]) {
            if (isBlank(serial)) {
                Logger.getLogger("emc").log(Level.SEVERE, "Serial Number is active on item dimension group.  Must have a value. (" + itemId + ")", userData);
                ret = false;
            }
        } else if (!isBlank(serial)) {
            Logger.getLogger("emc").log(Level.SEVERE, "Serial Number is not active on item dimension group.  May not have a value", userData);
            ret = false;
        }

        if (dimensions[7]) {
            if (isBlank(pallet)) {
                Logger.getLogger("emc").log(Level.SEVERE, "Pallet is active on item dimension group.  Must have a value. (" + itemId + ")", userData);
                ret = false;
            }
        } else if (!isBlank(pallet)) {
            Logger.getLogger("emc").log(Level.SEVERE, "Pallet is not active on item dimension group.  May not have a value", userData);
            ret = false;
        }

        return ret;
    }

    public boolean validateActiveDimensions(String itemId, String dimension1, String dimension2, String dimension3, String warehouse, boolean checkWarehouse, EMCUserData userData) {
        return validateActiveDimensions(itemId, dimension1, dimension2, dimension3, warehouse, checkWarehouse, true, userData);
    }

    /** This method checks whether all dimensions are inactive. */
    private boolean isAllDimensionsInactive(String itemId, EMCUserData userData) {
        boolean[] activeDimensions = this.getActiveDimensions(itemId, userData);

        return (!activeDimensions[0] && !activeDimensions[1] && !activeDimensions[2]);
    }

    /** Returns the cost associated with a the given dimension combination on the given item. Returns zero if no cost is found or if the cost of a generated  combination was not changed.  */
    public double getCostPriceForCombination(String itemId, String dim1, String dim2, String dim3, EMCUserData userData) {
        boolean[] activeDims = getActiveDimensions(itemId, userData);

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionCombinations.class.getName());
        query.addField("costPrice");

        if (activeDims[0]) {
            query.addAnd("dimension1Id", dim1);
        }

        if (activeDims[1]) {
            query.addAnd("dimension2Id", dim2);
        }

        if (activeDims[2]) {
            query.addAnd("dimension3Id", dim3);
        }

        query.addAnd("itemId", itemId);

        Double costPrice = (Double) util.executeSingleResultQuery(query, userData);

        if (costPrice == null) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "No combination found for " + itemId + " [" + dim1 + "," + dim2 + "," + dim3 + "]", userData);
            }
            costPrice = 0.0;
        }

        return costPrice;
    }

    /** Validates dimension values on an item. */
    public boolean validateDimensionValues(ItemDimensionInterface record, EMCUserData userData) {
        return validateDimensionValues(record.getItemId(), record.getDimension1(), record.getDimension2(), record.getDimension3(), userData);
    }

    /** Checks that all active dimensions have values. */
    public boolean validateActiveDimensions(ItemDimensionInterface record, EMCUserData userData) {
        return validateActiveDimensions(record.getItemId(), record.getDimension1(), record.getDimension2(), record.getDimension3(), null, false, userData);
    }

    /**
     * Returns a  list of dimension id's sorted by their sort codes on the given item's applicable dimension group.
     *
     * @param dimension Dimension to search on.
     * @param itemId  Item to find dimensions for.
     * @param dimQuery  Query that could select a list of dimensions from somewhere else.  Optional.
     * @param userData User data
     * @return Sorted list of dimensions.
     */
    public List<String> getSortedDimensions(EnumDimensions dimension, String itemId, EMCQuery dimQuery, EMCUserData userData) {
        String sortClass;

        if (dimension.equals(EnumDimensions.DIMENSION1)) {
            sortClass = InventoryDimension1.class.getName();
        } else if (dimension.equals(EnumDimensions.DIMENSION2)) {
            sortClass = InventoryDimension2.class.getName();
        } else {
            sortClass = InventoryDimension3.class.getName();
        }

        List<String> dimensions = util.executeGeneralSelectQuery(dimQuery, userData);

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, sortClass);
        query.addField("dimensionId");

        if (dimQuery != null) {
            query.addAndInList("dimensionId", dimensions, true, false);
        }

        query.addOrderBy("sortCode", sortClass);

        return (List<String>) util.executeGeneralSelectQuery(query, userData);
    }

    /** Creates an EMCQuery used to select records to include when generation combinations.  Convenience method.  */
    private EMCQuery getSelectQuery(String itemId, int dimension) {
        String className = "InventoryItemDimension" + dimension + "Setup";
        String itemMasterClass = InventoryItemMaster.class.getName();
        String dimensionGroupClass = InventoryItemDimensionGroup.class.getName();

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, className);
        query.addField("dimensionId");
        query.addTableAnd(itemMasterClass, "itemId", className, "itemId");
        query.addTableAnd(dimensionGroupClass, "dimensionGroup", itemMasterClass, "itemDimensionGroupId");
        query.addAnd("dim" + dimension + "Active", true, dimensionGroupClass);
        query.addAnd("itemId", itemId, itemMasterClass);

        return query;
    }

    /**
     * This method deletes all inactive combinations on all Item Dimension Setup tables for the given item.
     * @param itemId  Item id
     * @param userData User data
     * @return A boolean indicating whether records were deleted succesfully
     * @throws EMCEntityBeanException
     */
    private boolean deleteInactiveDimensions(String itemId, boolean[] activeDimensions, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery deleteQuery = null;
        List<Object> toDelete = null;

        if (activeDimensions[0]) {
            deleteQuery = createItemDimSetupDeleteQuery(InventoryItemDimension1Setup.class.getName(), itemId);
            toDelete = (List<Object>) util.executeGeneralSelectQuery(deleteQuery, userData);

            for (Object ob : toDelete) {
                itemDim1Setup.delete(ob, userData);
            }
        }

        if (activeDimensions[1]) {
            deleteQuery = createItemDimSetupDeleteQuery(InventoryItemDimension2Setup.class.getName(), itemId);
            toDelete = (List<Object>) util.executeGeneralSelectQuery(deleteQuery, userData);

            for (Object ob : toDelete) {
                itemDim2Setup.delete(ob, userData);
            }
        }

        if (activeDimensions[2]) {
            deleteQuery = createItemDimSetupDeleteQuery(InventoryItemDimension3Setup.class.getName(), itemId);
            toDelete = (List<Object>) util.executeGeneralSelectQuery(deleteQuery, userData);

            for (Object ob : toDelete) {
                itemDim3Setup.delete(ob, userData);
            }
        }

        return true;
    }

    /** This convenience method creates an EMCQuery used to delete inactive dimensions from one of the Item Dimension Setup tables. */
    private EMCQuery createItemDimSetupDeleteQuery(String dimSetupClass, String itemId) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, dimSetupClass);

        query.addAnd("active", false);
        query.addAnd("itemId", itemId);

        return query;
    }

    /**
     * This method should check all 'open' orders and return a boolean indicating whether the specified dimension is in use on the specified item. ('Orders' refer to Sales Orders, Purchase Orders, Works Orders, etc.)
     * @param dimensionId Id of dimension to check.
     * @param itemId Id of the item to check.
     * @param dimension Indicates which dimension is to be checked.  (Dimension 1, 2 or 3)
     * @param userData User data.
     * @return A boolean indicating whether the specified dimension is in use.
     */
    public boolean isDimensionInUse(String dimensionId, String itemId, EnumDimensions dimension, EMCUserData userData) {
        String transClass = InventoryTransactions.class.getName();
        String summClass = InventorySummary.class.getName();

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, transClass);
        query.addTableAnd(summClass, "recordID", transClass, "inventoryTransRef");
        query.addAnd("physicalDate", null, EMCQueryConditions.IS_NULL);

        switch (dimension) {
            case DIMENSION1:
                query.addAnd("dimension1", dimensionId, summClass);
                break;
            case DIMENSION2:
                query.addAnd("dimension2", dimensionId, summClass);
                break;
            case DIMENSION3:
                query.addAnd("dimension3", dimensionId, summClass);
                break;
        }

        return util.exists(query, userData);
    }
}
