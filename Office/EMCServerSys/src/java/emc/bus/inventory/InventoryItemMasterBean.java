/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory;

import com.google.common.io.Files;
import com.google.i18n.phonenumbers.FileUtils;
import emc.bus.base.BaseDocRefLocal;
import emc.bus.base.BaseEmployeeLocal;
import emc.bus.base.logic.BaseUOMLogicLocal;
import emc.bus.base.logic.EMCUOMConversionException;
import emc.bus.base.webfilepath.BaseWebFilePathsLocal;
import emc.bus.inventory.dimensions.InventoryDimension1Local;
import emc.bus.inventory.dimensions.InventoryDimension2Local;
import emc.bus.inventory.dimensions.InventoryDimension3Local;
import emc.bus.inventory.dimensions.InventoryDimensionTableLocal;
import emc.bus.inventory.logic.InventoryItemDimensionCombinationLogicLocal;
import emc.bus.sop.pricearangements.SOPPriceArangementsLocal;
import emc.constants.systemConstants;
import emc.entity.base.BaseDocuRefTable;
import emc.entity.base.BaseEmployeeTable;
import emc.entity.base.BaseFilePaths;
import emc.entity.base.BaseWebFilePaths;
import emc.entity.debtors.DebtorsBasketLines;
import emc.entity.debtors.DebtorsBasketMaster;
import emc.entity.inventory.InventoryCostingGroup;
import emc.entity.inventory.InventoryItemAccessGroupEmployees;
import emc.entity.inventory.InventoryItemGroup;
import emc.entity.inventory.dimensions.InventoryItemDimensionGroup;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryParameters;
import emc.entity.inventory.InventoryProductGroup;
import emc.entity.inventory.InventoryReference;
import emc.entity.inventory.classifications.InventoryClassificationHierarchy;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimensionTable;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.enums.base.webfilepaths.BaseWebFilePathProcesses;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumPersistOptions;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.costing.CostLevel;
import emc.enums.inventory.costing.DefaultDimCostCalc;
import emc.enums.inventory.inventorytables.InventoryItemTypes;
import emc.enums.inventory.inventorytables.InventoryOrderMethods;
import emc.enums.inventory.purchasing.InventoryStopPurchasingType;
import emc.enums.trec.TRECWebStoreTypeEnum;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.helpers.inventory.ItemCatalogueWebHelper;
import emc.messages.ServerInventoryMessageEnum;
import emc.tables.EMCFieldGroup;
import emc.tables.EMCTable;
import emc.utility.inventory.classifications.ClassificationsDS;
import emc.utility.inventory.classifications.DecodeEncodeClassificationHer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author riaan
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class InventoryItemMasterBean extends EMCEntityBean implements InventoryItemMasterLocal {

    @EJB
    private InventoryItemDimensionCombinationLogicLocal dimCombinationsBean;
    @EJB
    private InventoryReferenceLocal referenceUpdate;
    @EJB
    private BaseUOMLogicLocal uomBean;
    @EJB
    private BaseEmployeeLocal empBean;
    @EJB
    protected InventoryDimensionTableLocal dimIdLocal;
    @EJB
    private SOPPriceArangementsLocal priceArrangmentsBean;
    @EJB
    private BaseWebFilePathsLocal filepathsBean;
    @EJB
    InventoryDimension1Local dim1Bean;
    @EJB
    InventoryDimension2Local dim2Bean;
    @EJB
    InventoryDimension3Local dim3Bean;
    private final String[] dimensions = {"dimension1Group", "dimension2Group", "dimension3Group"};

    /**
     * Creates a new instance of InventoryItemMasterBean
     */
    public InventoryItemMasterBean() {
    }

    /**
     * Populates a new record with parameter data
     *
     * @param userData
     * @return
     */
    @Override
    public InventoryItemMaster populateDefaultItemFields(EMCUserData userData) {
        InventoryItemMaster itemMast = new InventoryItemMaster();
        EMCQuery qu = new EMCQuery(enumQueryTypes.SELECT, emc.entity.inventory.InventoryParameters.class.getName());
        InventoryParameters parm = (InventoryParameters) util.executeSingleResultQuery(qu, userData);
        if (parm != null) {
            itemMast.setBaseUOM(parm.getBaseUOM());
            itemMast.setDefaultWarehouse(parm.getDefaultWarehouse());
            itemMast.setPurchaseVatCode(parm.getPurchaseVatCode());
            itemMast.setDimensionGroup(parm.getDimensionGroup());
            itemMast.setSellingVatCode(parm.getPurchaseVatCode());
        }
        return itemMast;

    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        InventoryItemMaster item = (InventoryItemMaster) theRecord;

        boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);

        if (ret) {
            if (fieldNameToValidate.contains("dimension") && fieldNameToValidate.contains("Group")) {
                //if (ret) {
                ret = ret && validateDimension(item, fieldNameToValidate, userData);
            } else if (fieldNameToValidate.contains("SubstituteDimension")) {
                ret = ret && dimCombinationsBean.validateDimensionValues(item.getPlanningSubstituteItem(), item.getPlanningSubstituteDimension1(), item.getPlanningSubstituteDimension2(), item.getPlanningSubstituteDimension3(), userData);
            }

            if (!isBlank(item.getItemGroup())) {
                if (fieldNameToValidate.equals("itemGroup")) {
                    Object o = util.findPersisted(InventoryItemMaster.class, item.getRecordID(), userData);
                    if (o != null) {
                        InventoryItemMaster oldItem = (InventoryItemMaster) o;
                        if (!checkAccess(oldItem.getItemGroup(), userData)) {
                            Logger.getLogger("emc").log(Level.SEVERE, "You do not belong to the Item Access group for this item.", userData);
                            return false;
                        }
                    }
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemGroup.class);
                    query.addAnd("itemGroup", item.getItemGroup());
                    query.addField("mpsFlag");
                    item.setMpsFlag((String) util.executeSingleResultQuery(query, userData));
                    return item;
                }
                if (!checkAccess(item.getItemGroup(), userData)) {
                    Logger.getLogger("emc").log(Level.SEVERE, "You do not belong to the Item Access group for this item.", userData);
                    return false;
                }

            }
            if (fieldNameToValidate.contains("classification")) {
                ret = ret && isClassificationsValid(item, this.getHierRoot(item, userData), userData);
            }

            if (ret && fieldNameToValidate.equals("purchaseUOM")) {
                double qty = item.getPurchaseMinOrderQty();
                if (qty != 0) {
                    Object o = util.findPersisted(InventoryItemMaster.class, item.getRecordID(), userData);
                    if (o != null) {
                        InventoryItemMaster itemOld = (InventoryItemMaster) o;
                        String uomOld = itemOld.getPurchaseUOM();
                        String uomNew = item.getPurchaseUOM();
                        if (!uomOld.equals(uomNew)) {
                            try {
                                qty = uomBean.convertFromUOMToUOM(item.getItemId(), uomOld, uomNew, qty, userData);
                                item.setPurchaseMinOrderQty(qty);
                                return item;
                            } catch (EMCUOMConversionException e) {
                                Logger.getLogger("emc").log(Level.SEVERE, e.toString(), userData);
                                ret = false;
                            }
                        }
                    }
                }
            }
            if (ret && fieldNameToValidate.equals("purchasePrice")) {
                InventoryItemMaster itemOld = (InventoryItemMaster) util.findDetachedPersisted(item, userData);
                if (itemOld == null || itemOld.getPurchasePrice() != item.getPurchasePrice()) {
                    item.setPurchasePriceDate(Functions.nowDate());
                    return item;
                }
            }

            if (ret && fieldNameToValidate.equals("baseUOM")) {
                item.setPurchasePer(item.getBaseUOM());
                return item;
            }

            if (ret && fieldNameToValidate.equals("dimensionGroup")) {
                ret = checkGroupUsed(item, fieldNameToValidate, userData);
                if (ret == false) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Item is already in use. Cannot change Dimension Group.", userData);
                }
                if (!checkGroupValidOnItemGroup(item.getDimensionGroup(), item.getItemGroup(), userData)) {
                    Logger.getLogger("emc").log(Level.SEVERE, "The item group requires that the dimension group has location set to active.", userData);
                    return false;
                }
            }
            if (ret && fieldNameToValidate.equals("dimension1Group")) {
                ret = checkGroupUsed(item, fieldNameToValidate, userData);
                if (ret == false) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Item is already in use. Cannot change Config Group.", userData);
                }
            }
            if (ret && fieldNameToValidate.equals("dimension2Group")) {
                ret = checkGroupUsed(item, fieldNameToValidate, userData);
                if (ret == false) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Item is already in use. Cannot change Colour Group.", userData);
                }
            }
            if (ret && fieldNameToValidate.equals("dimension3Group")) {
                ret = checkGroupUsed(item, fieldNameToValidate, userData);
                if (ret == false) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Item is already in use. Cannot change Size Group.", userData);
                }
            }
            if (ret && fieldNameToValidate.equals("itemReference")) {
                ret = validateReference(item, userData);
            }

            if (ret && fieldNameToValidate.equals("minQty") && item.getMaxOrderQty() != 0) {
                if (item.getMinOrderQty() >= item.getMaxOrderQty()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "The min qty has to be less than the max qty.", userData);
                    ret = false;
                }
            }
            if (ret && fieldNameToValidate.equals("maxQty") && item.getMinOrderQty() != 0) {
                if (item.getMinOrderQty() >= item.getMaxOrderQty()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "The max qty has to be more than the min qty.", userData);
                    ret = false;
                }
            }


            if (ret && fieldNameToValidate.equals("productionRoutingId")) {
                if (!item.getItemType().equals(InventoryItemTypes.BOM.toString())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Only items of the BOM type may have a Routing", userData);
                    ret = false;
                }
            }
            if (ret && fieldNameToValidate.equals("costingGroup")) {
                EMCQuery quCostGrp = new EMCQuery(enumQueryTypes.SELECT, InventoryCostingGroup.class.getName());
                quCostGrp.addAnd("costingGroupId", item.getCostingGroup());
                InventoryCostingGroup grp = (InventoryCostingGroup) util.executeSingleResultQuery(quCostGrp, userData);
                if (grp != null && CostLevel.DIMENSION.equals(CostLevel.fromString(grp.getCostLevel()))) {
                    if (isBlank(item.getDimension1Group()) && isBlank(item.getDimension2Group()) && isBlank(item.getDimension3Group())) {
                        ret = false;
                        this.logMessage(Level.SEVERE, "Costing Group requires cost at Dimension Level. Dimensions groups are not set on the item.", userData);
                    }
                }

            }
            if (ret && fieldNameToValidate.equals("costingCurrentCost")) {
                item.setCostingCostDate(Functions.nowDate());
                if (item.getRecordID() != 0) {
                    InventoryItemMaster original = (InventoryItemMaster) this.findSQLVersionForEntity(item, userData);
                    item.setCostingPrevCost(original.getCostingCurrentCost());
                    item.setCostingPrevCostDate(original.getCostingCostDate());
                    item.setPrevCostLink(item.getCurrentCostLink());
                    doCostWarning(item, userData);
                }
                return item;
            }

            if (ret && fieldNameToValidate.equals("salesSellingPrice")) {
                if (item.getSellingPer() == 0) {
                    item.setSellingPer(1);
                    return item;
                }
            }
        }
        return ret;
    }

    public boolean checkGroupUsed(InventoryItemMaster record, String fieldToCheck, EMCUserData userData) {
        Object o = util.findPersisted(InventoryItemMaster.class, record.getRecordID(), userData);
        if (o != null) {
            InventoryItemMaster oldRecord = (InventoryItemMaster) o;
            if (fieldToCheck.equals("dimensionGroup")) {
                if (!isBlank(oldRecord.getDimensionGroup()) && isBlank(record.getDimensionGroup())) {
                    return referenceUpdate.testUsage(oldRecord, userData);
                }
                if (!record.getDimensionGroup().equals(oldRecord.getDimensionGroup())) {
                    return referenceUpdate.testUsage(oldRecord, userData);
                }
            } else if (fieldToCheck.equals("dimension1Group")) {
                if (!isBlank(oldRecord.getDimension1Group()) && isBlank(record.getDimension1Group())) {
                    return referenceUpdate.testUsage(oldRecord, userData);
                }
                if (!record.getDimension1Group().equals(oldRecord.getDimension1Group())) {
                    return referenceUpdate.testUsage(oldRecord, userData);
                }
            } else if (fieldToCheck.equals("dimension2Group")) {
                if (!isBlank(oldRecord.getDimension2Group()) && isBlank(record.getDimension2Group())) {
                    return referenceUpdate.testUsage(oldRecord, userData);
                }
                if (!record.getDimension2Group().equals(oldRecord.getDimension2Group())) {
                    return referenceUpdate.testUsage(oldRecord, userData);
                }
            } else if (fieldToCheck.equals("dimension3Group")) {
                if (!isBlank(oldRecord.getDimension3Group()) && isBlank(record.getDimension3Group())) {
                    return referenceUpdate.testUsage(oldRecord, userData);
                }
                if (!record.getDimension3Group().equals(oldRecord.getDimension3Group())) {
                    return referenceUpdate.testUsage(oldRecord, userData);
                }
            }
        }
        return true;
    }

    @Override
    public boolean doDeleteValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doDeleteValidation(vobject, userData);
        if (ret) {
            InventoryItemMaster item = (InventoryItemMaster) vobject;

            if (!isBlank(item.getItemGroup())) {
                if (!checkAccess(item.getItemGroup(), userData)) {
                    Logger.getLogger("emc").log(Level.SEVERE, "You do not belong to the Item Access group for this item.", userData);
                    ret = false;
                }
            }
        }
        return ret;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doUpdateValidation(vobject, userData);

        InventoryItemMaster item = (InventoryItemMaster) vobject;
        ret = ret && orderPolicyValidation(item, userData);

        if (!referenceUpdate.checkUsed(item.getItemId(), item.getItemReference(), userData)) {
            Logger.getLogger("emc").log(Level.SEVERE, "Item is already in use. Cannot change Reference.", userData);
            return false;
        }
        for (String dim : dimensions) {
            ret = ret && validateDimension(item, dim, userData);
        }

        if (!isBlank(item.getPlanningSubstituteItem())) {
            boolean substituteValid = dimCombinationsBean.validateDimensionValues(item.getPlanningSubstituteItem(), item.getPlanningSubstituteDimension1(), item.getPlanningSubstituteDimension2(), item.getPlanningSubstituteDimension3(), userData);
            substituteValid = substituteValid && dimCombinationsBean.validateActiveDimensions(item.getPlanningSubstituteItem(), item.getPlanningSubstituteDimension1(), item.getPlanningSubstituteDimension2(), item.getPlanningSubstituteDimension3(), item.getDefaultWarehouse(), true, userData);

            if (!substituteValid) {
                Logger.getLogger("emc").log(Level.SEVERE, "Substitute dimensions invalid.", userData);
            }

            ret = ret && substituteValid;
        }

        if (ret) {
            InventoryItemMaster master = (InventoryItemMaster) findSQLVersionForEntity(item, userData);
            if (!isBlank(item.getItemReference()) && !(isBlank(master.getItemReference()) ? "" : master.getItemReference()).equals(item.getItemReference())) {
                ret = validateReference(item, userData);
            }
        }

        ret = ret && isAllMandatoryClassificationsHonoured(item, getHierRoot(item, userData), userData);
        ret = ret && isClassificationsValid(item, getHierRoot(item, userData), userData);
        ret = ret && isPurchaseVat(item, userData);
        ret = ret && validatePurchaseStop(item, userData);

        return ret;
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean ret = super.doInsertValidation(vobject, userData);

        InventoryItemMaster item = (InventoryItemMaster) vobject;
        ret = ret && orderPolicyValidation(item, userData);

        for (String dim : dimensions) {
            ret = ret && validateDimension(item, dim, userData);
        }

        if (!isBlank(item.getPlanningSubstituteItem())) {
            boolean substituteValid = dimCombinationsBean.validateDimensionValues(item.getPlanningSubstituteItem(), item.getPlanningSubstituteDimension1(), item.getPlanningSubstituteDimension2(), item.getPlanningSubstituteDimension3(), userData);
            substituteValid = substituteValid && dimCombinationsBean.validateActiveDimensions(item.getPlanningSubstituteItem(), item.getPlanningSubstituteDimension1(), item.getPlanningSubstituteDimension2(), item.getPlanningSubstituteDimension3(), null, false, userData);

            if (!substituteValid) {
                Logger.getLogger("emc").log(Level.SEVERE, "Substitute dimensions invalid.", userData);
            }

            ret = ret && substituteValid;
        }

        ret = ret && isAllMandatoryClassificationsHonoured(item, getHierRoot(item, userData), userData);
        ret = ret && isClassificationsValid(item, getHierRoot(item, userData), userData);
        ret = ret && isPurchaseVat(item, userData);
        ret = ret && validateReference(item, userData);
        ret = ret && validatePurchaseStop(item, userData);

        //Set purchase uom if not set by user
        if (isBlank(item.getPurchaseUOM()) && ret) {
            item.setPurchaseUOM(item.getBaseUOM());
        }
        return ret;
    }

    /**
     * This method is used to validate a dimension on an item
     */
    private boolean validateDimension(InventoryItemMaster item, String dimension, EMCUserData userData) {
        boolean ret = true;
        boolean dimActive = false;

        String itemId = item.getItemId();
        //String dimensionGroup = item.getDimensionGroup();

        //if (itemId != null) {
        if (dimension.equals("dimension1Group")) {
            dimActive = isDimActive(item, "dim1Active", userData);
            if (!dimActive && !isBlank(item.getDimension1Group())) {
                Logger.getLogger("emc").log(Level.SEVERE, "Config not active on item dimension group.", userData);
                return false;
            } else if (dimActive && isBlank(item.getDimension1Group())) {
                Logger.getLogger("emc").log(Level.SEVERE, "Config active on item dimension group. Config Group must have a value.", userData);
                return false;
            }
        } else if (dimension.equals("dimension2Group")) {
            dimActive = isDimActive(item, "dim2Active", userData);
            if (!dimActive && !isBlank(item.getDimension2Group())) {
                Logger.getLogger("emc").log(Level.SEVERE, "Size not active on item dimension group.", userData);
                return false;
            } else if (dimActive && isBlank(item.getDimension2Group())) {
                Logger.getLogger("emc").log(Level.SEVERE, "Size active on item dimension group. Size Group must have a value.", userData);
                return false;
            }
        } else if (dimension.equals("dimension3Group")) {
            dimActive = isDimActive(item, "dim3Active", userData);
            if (!dimActive && !isBlank(item.getDimension3Group())) {
                Logger.getLogger("emc").log(Level.SEVERE, "Colour not active on item dimension group.", userData);
                return false;
            } else if (dimActive && isBlank(item.getDimension3Group())) {
                Logger.getLogger("emc").log(Level.SEVERE, "Colour active on item dimension group. Colour Group must have a value.", userData);
                return false;
            }
        } else if (dimension.equals("defaultWarehouse")) {
            dimActive = isDimActive(item, "defaultWarehouse", userData);
            if (!dimActive && !isBlank(item.getDefaultWarehouse())) {
                Logger.getLogger("emc").log(Level.SEVERE, "Warehouse not active on item dimension group.", userData);
                return false;
            } else if (dimActive && isBlank(item.getDefaultWarehouse())) {
                Logger.getLogger("emc").log(Level.SEVERE, "Warehouse active on item dimension group. Must have a value.", userData);
                return false;
            }
        }
        //}

        return ret;
    }

    private boolean orderPolicyValidation(InventoryItemMaster item, EMCUserData userData) {

        return true;

    }

    /**
     * This method is used to check whether a specified dimension is a active on
     * a specified item. fieldname is the name of the field on the
     * InventoryItemDimensionGroup table. (E.g. "dim1Active")
     */
    private boolean isDimActive(InventoryItemMaster item, String fieldName, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionGroup.class.getName());
        query.addAnd(fieldName, true);
        query.addAnd("itemDimensionGroupId", item.getDimensionGroup());

        Object res = util.executeSingleResultQuery(query, userData);

        return (res != null);
    }

    /**
     * Generates item dimension combinations
     */
    @Override
    public boolean generateItemDimCombinations(String itemId, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = dimCombinationsBean.calculateCombinations(itemId, userData);

        if (ret) {
            Logger.getLogger("emc").log(Level.INFO, "Combinations generated succesfully", userData);
        } else {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to generate combinations", userData);
        }

        return ret;
    }

    /**
     * This method returns the dimension group of an item.
     */
    @Override
    public String getItemDimensionGroup(String itemId, EMCUserData userData) {
        String dimGroup = null;

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class.getName());
        query.addField("dimensionGroup");
        query.addAnd("itemId", itemId);

        dimGroup = (String) util.executeSingleResultQuery(query, userData);

        return dimGroup;
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        Object ret = super.update(uobject, userData);
        if (ret instanceof EMCEntityClass) {
            InventoryItemMaster record = (InventoryItemMaster) uobject;
            if (!isBlank(record.getItemReference())) {
                referenceUpdate.setNewPrimaryReference(record.getItemId(), record.getItemReference(), userData);
            }
        }
        return ret;
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        Object ret = super.insert(iobject, userData);
        InventoryItemMaster record = (InventoryItemMaster) ret;
        if (!isBlank(record.getItemReference())) {
            referenceUpdate.setNewPrimaryReference(record.getItemId(), record.getItemReference(), userData);
        }
        referenceUpdate.setDefaultReference(record.getItemId(), userData);
        return ret;
    }

    @Override
    public void setReference(String itemId, String reference, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class.getName());
        query.addAnd("itemId", itemId);
        Object uRecord = util.executeSingleResultQuery(query, userData);
        InventoryItemMaster record = (InventoryItemMaster) uRecord;
        record.setItemReference(reference);
        super.doUpdate(record, userData);
    }

    @Override
    public void removeReference(String itemId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class.getName());
        query.addAnd("itemId", itemId);
        Object uRecord = util.executeSingleResultQuery(query, userData);
        InventoryItemMaster record = (InventoryItemMaster) uRecord;
        record.setItemReference(null);
        super.doUpdate(record, userData);
    }

    /**
     * This method takes an item id as parameter and returns the description of
     * the item
     */
    @Override
    public String findItemDescription(String itemId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class.getName());
        query.addAnd("itemId", itemId);
        query.addField("description");

        String description = (String) util.executeSingleResultQuery(query, userData);

        if (description == null) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Item not found.  Could not get item description", userData);
            }
        }

        return description;
    }

    /**
     * This method checks if the user is part of the item access group being
     * edited
     */
    private boolean checkAccess(String itemGroup, EMCUserData userData) {
        String employee = userData.getUserName();
        Object empId = util.executeSingleResultQuery(
                "SELECT a.employeeId FROM InventoryItemAccessGroupEmployees a WHERE a.itemAccessGroupId = "
                + "(SELECT g.accessGroupId FROM InventoryItemGroup g WHERE g.itemGroup = '" + itemGroup
                + "' AND g.companyId = '" + util.getCompanyId(InventoryItemGroup.class.getName(), userData) + "') AND a.employeeId = (SELECT e.employeeNumber FROM BaseEmployeeTable e WHERE e.userId = '"
                + employee + "' AND e.companyId = '" + util.getCompanyId(BaseEmployeeTable.class.getName(), userData) + "') AND a.companyId = '" + util.getCompanyId(InventoryItemAccessGroupEmployees.class.getName(), userData) + "'", userData);
        if (empId == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns a root to the Hierarchy if any is associated with the itemgroup
     *
     * @param master
     * @param userData
     * @return
     */
    private DefaultMutableTreeNode getHierRoot(InventoryItemMaster master, EMCUserData userData) {
        if (master != null && master.getItemGroup() != null) {
            EMCQuery q = new EMCQuery(enumQueryTypes.SELECT, InventoryItemGroup.class.getName());
            q.addAnd("itemGroup", master.getItemGroup());
            InventoryItemGroup iGroup = (InventoryItemGroup) util.executeSingleResultQuery(q, userData);
            if (iGroup != null && iGroup.getHierarchy() != null) {
                q = new EMCQuery(enumQueryTypes.SELECT, InventoryClassificationHierarchy.class.getName());
                q.addAnd("hierarchyId", iGroup.getHierarchy());
                InventoryClassificationHierarchy her = (InventoryClassificationHierarchy) util.executeSingleResultQuery(q, userData);
                if (her != null) {
                    DecodeEncodeClassificationHer encDec = new DecodeEncodeClassificationHer();
                    return encDec.decodeTree(her.getHierarchyTree());
                }
            }
        }
        return null;

    }

    private boolean isAllMandatoryClassificationsHonoured(InventoryItemMaster master, DefaultMutableTreeNode herRoot, EMCUserData userData) {
        boolean ret = true;
        if (herRoot != null && master != null) {
            EMCFieldGroup fGroup = master.getFieldGroupMap().get("Classifications");
            for (int j = 0; j < fGroup.size(); j++) {
                Object value = master.getValueForFieldInEntityObject(fGroup.get(j), master.getClass(), master);

                if (isBlank(value)) {
                    if (j > 0) {
                        Object temp = master.getValueForFieldInEntityObject(fGroup.get(j - 1), master.getClass(), master);
                        if (!isBlank(temp)) {
                            ret = ret && !isClassificationMandatory(temp.toString(), herRoot, userData)[0];
                            if (!ret) {
                                Logger.getLogger("emc").log(Level.SEVERE, master.getFieldDataTypeMapper().get(fGroup.get(j)).getEmcLabel() + " is required.", userData);
                                return false;
                            }
                        }
                    } else {
                        if (herRoot.getChildCount() != 0) {
                            Logger.getLogger("emc").log(Level.SEVERE, master.getFieldDataTypeMapper().get(fGroup.get(j)).getEmcLabel() + " is required.", userData);
                            return false;
                        }
                    }
                }
            }
        }
        return ret;
    }

    private boolean isClassificationsValid(InventoryItemMaster master, DefaultMutableTreeNode herRoot, EMCUserData userData) {
        boolean ret = true;
        if (herRoot != null && master != null) {
            EMCFieldGroup fGroup = master.getFieldGroupMap().get("Classifications");
            List<String> values = new ArrayList();
            //get all the classifications
            for (String fieldKey : fGroup) {
                Object temp = master.getValueForFieldInEntityObject(fieldKey, master.getClass(), master);
                if (isBlank(temp)) {
                    //break;
                } else {
                    values.add(temp.toString());
                }
            }
            //validate the classifications
            if (!values.isEmpty()) {
                ret = ret && isClassificationValid(values, herRoot, userData);
            }
        }
        return ret;
    }

    /**
     *
     * @param value
     * @param herRoot
     * @param userData
     * @return boolean [] pos 0 is the result of the method pos 1 is for
     * internal use true of value is found
     */
    private boolean[] isClassificationMandatory(String value, DefaultMutableTreeNode herRoot, EMCUserData userData) {
        boolean ret = false;
        Enumeration children = herRoot.children();
        while (children.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) children.nextElement();
            ClassificationsDS cDS = (ClassificationsDS) node.getUserObject();
            if (cDS.getClassification().equals(value)) {
                if (node.getChildCount() != 0) {
                    return new boolean[]{true, true};
                }
                return new boolean[]{false, true};
            } else {
                boolean[] temp = isClassificationMandatory(value, node, userData);
                if (temp[1]) {
                    return temp;
                }
            }
        }
        return new boolean[]{ret, false};
    }

    private boolean isClassificationValid(List<String> values, DefaultMutableTreeNode herRoot, EMCUserData userData) {
        boolean ret = false;
        String value = values.remove(0);
        if (herRoot.getChildCount() == 0) {
            return true;
        } //reached the end of the requirement
        Enumeration children = herRoot.children();
        while (children.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) children.nextElement();
            ClassificationsDS cDS = (ClassificationsDS) node.getUserObject();
            if (cDS.getClassification().equals(value)) {
                if (values.isEmpty() || values.get(0) == null) {
                    return true;
                } else {
                    return isClassificationValid(values, node, userData);
                }
            }
        }
        Logger.getLogger("emc").log(Level.SEVERE, "The value (" + value + ") for the classification is not valid at this level", userData);
        return ret;
    }

    /**
     * This method takes an item id and returns an item. null is returned when
     * an item cannot be found.
     */
    @Override
    public InventoryItemMaster findItem(String itemId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class.getName());
        query.addAnd("itemId", itemId);
        query.addAnd("companyId", userData.getCompanyId());

        InventoryItemMaster item = (InventoryItemMaster) util.executeSingleResultQuery(query, userData);

        return item;
    }

    private boolean isPurchaseVat(InventoryItemMaster item, EMCUserData userData) {
        String type = item.getItemType();
        if (!isBlank(type) && type.equals(InventoryItemTypes.PHANTOM.toString())) {
            return true;
        } else if (isBlank(item.getPurchaseVatCode())) {
            Logger.getLogger("emc").log(Level.SEVERE, "Purchase VAT code is mandatory. Please input a vat code.", userData);
            return false;
        }
        return true;
    }

    private boolean validateReference(InventoryItemMaster master, EMCUserData userData) {
        if (isBlank(master.getItemReference())) {
            return true;
        } else {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryReference.class.getName());
            query.addAnd("reference", master.getItemReference());
            query.addAnd("companyId", userData.getCompanyId());
            if (util.exists(query, userData) || (!isBlank(master.getItemId()) && master.getItemId().equalsIgnoreCase(master.getItemReference()))) {
                Logger.getLogger("emc").log(Level.SEVERE, "The item reference is already used.", userData);
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * Returns dimension group record for item.
     */
    @Override
    public InventoryItemDimensionGroup getItemDimensionGroupRecord(String itemId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionGroup.class.getName());
        query.addAnd("itemDimensionGroupId", getItemDimensionGroup(itemId, userData));

        return (InventoryItemDimensionGroup) util.executeSingleResultQuery(query, userData);
    }

    private boolean checkItemGroupForQC(String itemGroup, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemGroup.class.getName());
        query.addAnd("itemGroup", itemGroup);
        InventoryItemGroup group = (InventoryItemGroup) util.executeSingleResultQuery(query, userData);
        if (group == null) {
            return false;
        } else {
            return group.getQuarantineReq();

        }
    }

    private boolean checkGroupValidOnItemGroup(String dimensionGroup, String itemGroup, EMCUserData userData) {
        boolean itemReq = checkItemGroupForQC(itemGroup, userData);
        if (itemReq) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionGroup.class.getName());
            query.addAnd("itemDimensionGroupId", dimensionGroup);
            InventoryItemDimensionGroup group = (InventoryItemDimensionGroup) util.executeSingleResultQuery(query, userData);
            if (group == null) {
                return true;
            } else {
                return group.getLocationActive();

            }
        } else {
            return true;


        }
    }

    @Override
    public double getCostPrice(String itemId, long dimId, EMCUserData userData) {
        InventoryDimensionTable dimTable = dimIdLocal.getDimensionRecord(dimId, userData);
        return getCostPrice(itemId, dimTable.getDimension1Id(), dimTable.getDimension2Id(), dimTable.getDimension3Id(), userData);
    }

//    @Override
//    public double getCostPrice(String itemId, String dim1, String dim2, String dim3, EMCUserData userData) {
//        EMCQuery itemQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class);
//        itemQuery.addAnd("itemId", itemId);
//        InventoryItemMaster item = (InventoryItemMaster) util.executeSingleResultQuery(itemQuery, userData);
//
//        double cost = item.getCostingCurrentCost();
//
//        if (!isBlank(item.getCostingGroup())) {
//            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryCostingGroup.class);
//            query.addAnd("costingGroupId", item.getCostingGroup());
//            InventoryCostingGroup costingGroup = (InventoryCostingGroup) util.executeSingleResultQuery(query, userData);
//
//            if (costingGroup != null && CostLevel.DIMENSION.toString().equals(costingGroup.getCostLevel())) {
//
//                query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionCombinations.class);
//                query.addAnd("itemId", item.getItemId());
//
//                if (!isBlank(dim1) && !dim1.equals(systemConstants.emcNA())) {
//                    query.addAnd("dimension1Id", dim1);
//                }
//
//                if (!isBlank(dim2) && !dim2.equals(systemConstants.emcNA())) {
//                    query.addAnd("dimension2Id", dim2);
//                }
//
//                if (!isBlank(dim3) && !dim3.equals(systemConstants.emcNA())) {
//                    query.addAnd("dimension3Id", dim3);
//                }
//
//                InventoryItemDimensionCombinations combination = (InventoryItemDimensionCombinations) util.executeSingleResultQuery(query, userData);
//
//                if (combination != null) {
//                    cost = combination.getCostPrice();
//                } else {
//                    if (!isBlank(costingGroup.getDefaultDimensionCostCalc())) {
//                        query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionCombinations.class);
//                        query.addAnd("itemId", item.getItemId());
//                        switch (DefaultDimCostCalc.fromString(costingGroup.getDefaultDimensionCostCalc())) {
//                            case MAX:
//                                query.addFieldAggregateFunction("costPrice", "MAX");
//                                break;
//                            case AVG:
//                                query.addFieldAggregateFunction("costPrice", "AVG");
//                                query.addAnd("costPrice", 0, EMCQueryConditions.GREATER_THAN);
//                                break;
//                            case MIN:
//                                query.addFieldAggregateFunction("costPrice", "MIN");
//                                break;
//                        }
//                        Double defaultCost = (Double) util.executeSingleResultQuery(query, userData);
//                        if (defaultCost == null) {
//                            defaultCost = 0d;
//                        }
//                        cost = defaultCost;
//                    } else {
//                        cost = 0d;
//                    }
//                }
//            }
//        }
//        return cost;
//    }
    @Override
    public double getCostPrice(String itemId, String dim1, String dim2, String dim3, EMCUserData userData) {
        EMCQuery itemQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class);
        itemQuery.addAnd("itemId", itemId);
        InventoryItemMaster item = (InventoryItemMaster) util.executeSingleResultQuery(itemQuery, userData);

        double cost = item.getCostingCurrentCost();

        if (!isBlank(item.getCostingGroup())) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryCostingGroup.class);
            query.addAnd("costingGroupId", item.getCostingGroup());
            InventoryCostingGroup costingGroup = (InventoryCostingGroup) util.executeSingleResultQuery(query, userData);

            if (costingGroup != null && CostLevel.DIMENSION.toString().equals(costingGroup.getCostLevel())) {

                query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionCombinations.class);
                query.addAnd("itemId", item.getItemId());

                if (!isBlank(dim1) && !dim1.equals(systemConstants.emcNA())) {
                    query.addAnd("dimension1Id", dim1);
                }

                if (!isBlank(dim2) && !dim2.equals(systemConstants.emcNA())) {
                    query.addAnd("dimension2Id", dim2);
                }

                if (!isBlank(dim3) && !dim3.equals(systemConstants.emcNA())) {
                    query.addAnd("dimension3Id", dim3);
                }

                InventoryItemDimensionCombinations combination = (InventoryItemDimensionCombinations) util.executeSingleResultQuery(query, userData);

                if (combination != null) {
                    cost = combination.getCostPrice();
                } else {
                    cost = 0d;
                }

                if (cost == 0 && !isBlank(costingGroup.getDefaultDimensionCostCalc())) {
                    query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionCombinations.class);
                    query.addAnd("itemId", item.getItemId());
                    switch (DefaultDimCostCalc.fromString(costingGroup.getDefaultDimensionCostCalc())) {
                        case MAX:
                            query.addFieldAggregateFunction("costPrice", "MAX");
                            break;
                        case AVG:
                            query.addFieldAggregateFunction("costPrice", "AVG");
                            query.addAnd("costPrice", 0, EMCQueryConditions.GREATER_THAN);
                            break;
                        case MIN:
                            query.addFieldAggregateFunction("costPrice", "MIN");
                            break;
                    }
                    Double defaultCost = (Double) util.executeSingleResultQuery(query, userData);
                    if (defaultCost == null) {
                        defaultCost = 0d;
                    }
                    cost = defaultCost;
                }
            }
        }
        return cost;
    }

    /**
     * Finds the Min Qty for the given item
     *
     * @param itemId the item to check
     * @param dim1 the config to check
     * @param dim2 the size to check
     * @param dim3 the colour to check
     * @param itemMaster OPTIONAL - selects it if null
     * @param userData plain old EMCUserData
     * @return Double - You take a guess
     */
    @Override
    public double getMinQty(String itemId, String dim1, String dim2, String dim3, InventoryItemMaster itemMaster, EMCUserData userData) {
        EMCQuery query;
        if (itemMaster == null) {
            query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class.getName());
            query.addAnd("itemId", itemId);
            itemMaster = (InventoryItemMaster) util.executeSingleResultQuery(query, userData);
        }
        if (isBlank(itemMaster.getPlanningPlanning())) {
            return itemMaster.getMinOrderQty();

        }
        return 0;
    }

    private void doCostWarning(InventoryItemMaster item, EMCUserData userData) {
        if (!isBlank(item.getCostingGroup())) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryCostingGroup.class.getName());
            query.addAnd("costingGroupId", item.getCostingGroup());
            InventoryCostingGroup costGroup = (InventoryCostingGroup) util.executeSingleResultQuery(query, userData);
            if (!CostLevel.ITEM.toString().equals(costGroup.getCostLevel())) {
                logMessage(Level.WARNING, "The costing level is set to " + costGroup.getCostLevel() + ", updateing the cost here will hava no effect.", userData);
            }
        }
    }

    /**
     * Returns the type of the given item.
     *
     * @param itemId Id of item.
     * @param userData User data.
     * @return The type of the item on the specified itemId line.
     */
    @Override
    public InventoryItemTypes getItemType(String itemId, EMCUserData userData) {
        InventoryItemMaster item = findItem(itemId, userData);

        return InventoryItemTypes.fromString(item.getItemType());
    }

    @Override
    public boolean isKimblingActive(String itemId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class);
        query.addTableAnd(InventoryItemDimensionGroup.class.getName(), "dimensionGroup", InventoryItemMaster.class.getName(), "itemDimensionGroupId");
        query.addAnd("allowKimbling", true, InventoryItemMaster.class.getName());
        query.addAnd("serialNumberActive", true, InventoryItemDimensionGroup.class.getName());
        query.addAnd("itemId", itemId, InventoryItemMaster.class.getName());
        return util.exists(query, userData);
    }

    @Override
    public int getProductionLeadTime(String itemId, String dimension1, String dimension2, String dimension3, EMCUserData userData) {
        //Combination
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionCombinations.class);
        query.addAnd("itemId", itemId);
        if (!isBlank(dimension1)) {
            query.addAnd("dimension1Id", dimension1);
        }
        if (!isBlank(dimension2)) {
            query.addAnd("dimension2Id", dimension2);
        }
        if (!isBlank(dimension3)) {
            query.addAnd("dimension3Id", dimension3);
        }
        query.addField("productionLeadTime");
        BigDecimal combLeadTime = (BigDecimal) util.executeSingleResultQuery(query, userData);
        if (combLeadTime != null && combLeadTime.compareTo(BigDecimal.ZERO) > 0) {
            return new Double(Math.ceil(combLeadTime.doubleValue())).intValue();
        }
        //Item
        query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class);
        query.addAnd("itemId", itemId);
        query.addField("productionLeadTime");
        query.addField("planningPlanning");
        Object[] itemData = (Object[]) util.executeSingleResultQuery(query, userData);
        if (itemData[0] != null && util.compareDouble((Double) itemData[0], 0d) > 0) {
            return new Double(Math.ceil((Double) itemData[0])).intValue();
        }
        return 0;
    }

    @Override
    public BigDecimal getTotalProcessingTime(String itemId, String dimension1, String dimension2, String dimension3, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class);
        query.addAnd("itemId", itemId);
        query.addField("productionRoutingId");
        BigDecimal processingTime;
        processingTime = BigDecimal.ZERO;
        return processingTime;
    }

    @Override
    public int getPurchasingLeadTime(String itemId, String dimension1, String dimension2, String dimension3, EMCUserData userData) {
        //Combination
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionCombinations.class);
        query.addAnd("itemId", itemId);
        if (!isBlank(dimension1)) {
            query.addAnd("dimension1Id", dimension1);
        }
        if (!isBlank(dimension2)) {
            query.addAnd("dimension2Id", dimension2);
        }
        if (!isBlank(dimension3)) {
            query.addAnd("dimension3Id", dimension3);
        }
        query.addField("purchaseLeadTime");
        BigDecimal combLeadTime = (BigDecimal) util.executeSingleResultQuery(query, userData);
        if (combLeadTime != null && combLeadTime.compareTo(BigDecimal.ZERO) > 0) {
            return new Double(Math.ceil(combLeadTime.doubleValue())).intValue();
        }
        //Item
        query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class);
        query.addAnd("itemId", itemId);
        query.addField("purchaseLeadTime");
        query.addField("planningPlanning");
        Object[] itemData = (Object[]) util.executeSingleResultQuery(query, userData);
        if (itemData[0] != null && util.compareDouble((Double) itemData[0], 0d) > 0) {
            return new Double(Math.ceil((Double) itemData[0])).intValue();
        }
        return 0;
    }

    private boolean validatePurchaseStop(InventoryItemMaster item, EMCUserData userData) {
        if (item.getStopPurchase().equals(InventoryStopPurchasingType.OVERSEE.toString())) {
            if (isBlank(item.getOverseePurchaseGroup())) {
                Logger.getLogger("emc").log(Level.SEVERE, "Please select the overseeing purchase order approval group.", userData);
                return false;
            }
        } else {
            if (!isBlank(item.getOverseePurchaseGroup())) {
                item.setOverseePurchaseGroup(null);
            }
        }
        return true;
    }

    @Override
    public List<ItemCatalogueWebHelper> getWebItems(String WebStoreType, EMCUserData userData) {

        List<ItemCatalogueWebHelper> helperList = new ArrayList<>();

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class);
        query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "itemId", InventoryItemMaster.class.getName(), "itemId");

        if (!isBlank(WebStoreType)) {
            query.addAnd("webStoreType", WebStoreType, InventoryItemMaster.class.getName());
        }
        query.addAnd("showOnWeb", true, InventoryItemDimensionCombinations.class.getName());
        query.addField("itemId", InventoryItemMaster.class.getName());
        query.addField("itemReference", InventoryItemMaster.class.getName());
        query.addField("description", InventoryItemMaster.class.getName());
        query.addField("itemGroup", InventoryItemMaster.class.getName());
        query.addField("recordID", InventoryItemMaster.class.getName());
        query.addField("dimension1Id", InventoryItemDimensionCombinations.class.getName());
        query.addField("dimension2Id", InventoryItemDimensionCombinations.class.getName());
        query.addField("dimension3Id", InventoryItemDimensionCombinations.class.getName());
        List<Object[]> selectedData = util.executeGeneralSelectQuery(query, userData);

        ItemCatalogueWebHelper helper = null;

        Map<String, String> productMap = new HashMap<>();

        String description;

        Object[] imageData;
        //
        for (Object[] selected : selectedData) {


            helper = new ItemCatalogueWebHelper();


            helper.setItemId((String) selected[0]);
            helper.setItemReference((String) selected[1]);
            helper.setItemDescription((String) selected[2]);
            //            helper.setGender((String) selected[5]);
            helper.setProductGroup((String) selected[3]);



            if (!isBlank(helper.getProductGroup())) {
                description = productMap.get(helper.getProductGroup());

                if (isBlank(description)) {
                    query = new EMCQuery(enumQueryTypes.SELECT, InventoryProductGroup.class);
                    query.addAnd("productGroupId", helper.getProductGroup());
                    query.addField("description");

                    description = (String) util.executeSingleResultQuery(query, userData);

                    productMap.put(helper.getProductGroup(), description);
                }

                helper.setProductGroupDescription(description);
            }



            query = new EMCQuery(enumQueryTypes.SELECT, BaseDocuRefTable.class);
            query.addAnd("refTableName", InventoryItemMaster.class.getSimpleName());
            query.addAnd("refRecId", selected[4]);
            query.addAnd("summary", "ITEM IMAGE");
            query.addField("refRecId");
            query.addField("attachmentFileName");

            imageData = (Object[]) util.executeSingleResultQuery(query, userData);

            if (imageData != null) {
                helper.setImagePath(imageData[0] + "_" + (String) imageData[1]);
            }

            helper.setDimension1Description((String) selected[5] + " (" + dim1Bean.findDimensionDescription((String) selected[5], userData) + ")");
            helper.setDimension2Description((String) selected[6] + " (" + dim2Bean.findDimensionDescription((String) selected[6], userData) + ")");
            helper.setDimension3Description((String) selected[7] + " (" + dim3Bean.findDimensionDescription((String) selected[7], userData) + ")");

            helper.setDimension1((String) selected[5]);
            helper.setDimension2((String) selected[6]);
            helper.setDimension3((String) selected[7]);

            BigDecimal price = BigDecimal.ZERO;
            try {
                price = priceArrangmentsBean.findItemPrice("C01071", (String) selected[0], (String) selected[5], (String) selected[6], (String) selected[7], dateHandler.nowDate(), BigDecimal.ONE, userData);

                // BigDecimal creditsPrice = priceArrangmentsBean.findItemPrice("C01073", (String) selected[0], null, null, null, dateHandler.nowDate(), BigDecimal.ONE, userData);
            } catch (EMCEntityBeanException ex) {
                Logger.getLogger(InventoryItemMasterBean.class.getName()).log(Level.SEVERE, "cannot find price", ex);
            }
            helper.setPrice(price);
            helper.setPriceVal("R " + price);

            String webStoreType = getItemWebStoreType((String) selected[0], userData);
            if (webStoreType.equals(TRECWebStoreTypeEnum.PLACARD.toString())) {

                // do for placard
                helper.setShowAddInfo(true);
                helper.setWebstoreType(webStoreType);
                if (helper.getPrice().compareTo(BigDecimal.ZERO) > 0) {
                    helperList.add(helper);
                }
            } else if (webStoreType.equals(TRECWebStoreTypeEnum.STANDARD.toString())) {
                helper.setShowAddInfo(false);
                helper.setWebstoreType(webStoreType);
                if (helper.getPrice().compareTo(BigDecimal.ZERO) > 0) {
                    helperList.add(helper);
                }
            } else {
            }
        }
// copy files over to docroot
        for (ItemCatalogueWebHelper helperClass : helperList) {
            if (!isBlank(helperClass.getImagePath())) {

                query = new EMCQuery(enumQueryTypes.SELECT, BaseFilePaths.class);
                query.addAnd("formModule", "Inventory Control");
                query.addField("filePath");
                String sourcePath = (String) util.executeSingleResultQuery(query, userData);

                query = new EMCQuery(enumQueryTypes.SELECT, BaseWebFilePaths.class);
                query.addAnd("processId", BaseWebFilePathProcesses.TREC_IMAGES.toString());
                query.addField("relativeFilePath");
                String destinationPath = (String) util.executeSingleResultQuery(query, userData);
                InputStream is = null;
                OutputStream os = null;

                try {
                    byte[] buffer = new byte[1024];
                    is = new FileInputStream(sourcePath + "/" + helperClass.getImagePath());
                    os = new FileOutputStream(destinationPath +"/"+ helperClass.getImagePath());
                    int length;
                    try {
                        while ((length = is.read(buffer)) > 0) {
                            os.write(buffer, 0, length);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(InventoryItemMasterBean.class.getName()).log(Level.SEVERE, "could not write file", ex);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(InventoryItemMasterBean.class.getName()).log(Level.SEVERE, "could not find file", ex);
                }

            }
        }


        return helperList;

    }

    @Override
    public List<ItemCatalogueWebHelper> filterWebItems(String itemId, String WebStoreType, String material, EMCUserData userData) {

        List<ItemCatalogueWebHelper> helperList = new ArrayList<>();

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class);
        query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "itemId", InventoryItemMaster.class.getName(), "itemId");
        query.addTableAnd(InventoryDimension1.class.getName(), "dimension1Id", InventoryItemDimensionCombinations.class.getName(), "dimensionId");
        if (!isBlank(WebStoreType)) {
            query.addAnd("webStoreType", WebStoreType, InventoryItemMaster.class.getName());
        }
        if (!isBlank(material) && !material.equals("All")) {
            query.addAnd("description", material, InventoryDimension1.class.getName());
        }
        if (!isBlank(itemId)) {
            query.addAnd("itemId", itemId, InventoryItemMaster.class.getName());
        }
        query.addAnd("showOnWeb", true, InventoryItemDimensionCombinations.class.getName());
        query.addField("itemId", InventoryItemMaster.class.getName());
        query.addField("itemReference", InventoryItemMaster.class.getName());
        query.addField("description", InventoryItemMaster.class.getName());
        query.addField("itemGroup", InventoryItemMaster.class.getName());
        query.addField("recordID", InventoryItemMaster.class.getName());
        query.addField("dimension1Id", InventoryItemDimensionCombinations.class.getName());
        query.addField("dimension2Id", InventoryItemDimensionCombinations.class.getName());
        query.addField("dimension3Id", InventoryItemDimensionCombinations.class.getName());
        List<Object[]> selectedData = util.executeGeneralSelectQuery(query, userData);

        ItemCatalogueWebHelper helper = null;

        Map<String, String> productMap = new HashMap<>();

        String description;

        Object[] imageData;
        //
        for (Object[] selected : selectedData) {


            helper = new ItemCatalogueWebHelper();


            helper.setItemId((String) selected[0]);
            helper.setItemReference((String) selected[1]);
            helper.setItemDescription((String) selected[2]);
            //            helper.setGender((String) selected[5]);
            helper.setProductGroup((String) selected[3]);



            if (!isBlank(helper.getProductGroup())) {
                description = productMap.get(helper.getProductGroup());

                if (isBlank(description)) {
                    query = new EMCQuery(enumQueryTypes.SELECT, InventoryProductGroup.class);
                    query.addAnd("productGroupId", helper.getProductGroup());
                    query.addField("description");

                    description = (String) util.executeSingleResultQuery(query, userData);

                    productMap.put(helper.getProductGroup(), description);
                }

                helper.setProductGroupDescription(description);
            }



            query = new EMCQuery(enumQueryTypes.SELECT, BaseDocuRefTable.class);
            query.addAnd("refTableName", InventoryItemMaster.class.getSimpleName());
            query.addAnd("refRecId", selected[4]);
            query.addAnd("summary", "ITEM IMAGE");
            query.addField("refRecId");
            query.addField("attachmentFileName");

            imageData = (Object[]) util.executeSingleResultQuery(query, userData);

            if (imageData != null) {
                helper.setImagePath(imageData[0] + "_" + (String) imageData[1]);
            }

            helper.setDimension1Description((String) selected[5] + " (" + dim1Bean.findDimensionDescription((String) selected[5], userData) + ")");
            helper.setDimension2Description((String) selected[6] + " (" + dim2Bean.findDimensionDescription((String) selected[6], userData) + ")");
            helper.setDimension3Description((String) selected[7] + " (" + dim3Bean.findDimensionDescription((String) selected[7], userData) + ")");

            helper.setDimension1((String) selected[5]);
            helper.setDimension2((String) selected[6]);
            helper.setDimension3((String) selected[7]);

            BigDecimal price = BigDecimal.ZERO;
            try {
                price = priceArrangmentsBean.findItemPrice("C01071", (String) selected[0], (String) selected[5], (String) selected[6], (String) selected[7], dateHandler.nowDate(), BigDecimal.ONE, userData);

                // BigDecimal creditsPrice = priceArrangmentsBean.findItemPrice("C01073", (String) selected[0], null, null, null, dateHandler.nowDate(), BigDecimal.ONE, userData);
            } catch (EMCEntityBeanException ex) {
                Logger.getLogger(InventoryItemMasterBean.class.getName()).log(Level.SEVERE, "cannot find price", ex);
            }
            helper.setPrice(price);
            helper.setPriceVal("R " + price);

            String webStoreType = getItemWebStoreType((String) selected[0], userData);
            if (webStoreType.equals(TRECWebStoreTypeEnum.PLACARD.toString())) {
                // do for placard
                helper.setShowAddInfo(true);
                helper.setWebstoreType(webStoreType);
                if (helper.getPrice().compareTo(BigDecimal.ZERO) > 0) {
                    helperList.add(helper);
                }
            } else if (webStoreType.equals(TRECWebStoreTypeEnum.STANDARD.toString())) {
                helper.setShowAddInfo(false);
                helper.setWebstoreType(webStoreType);
                if (helper.getPrice().compareTo(BigDecimal.ZERO) > 0) {
                    helperList.add(helper);
                }
            } else {
            }
        }
// copy files over to docroot
        for (ItemCatalogueWebHelper helperClass : helperList) {
            if (!isBlank(helperClass.getImagePath())) {

                query = new EMCQuery(enumQueryTypes.SELECT, BaseFilePaths.class);
                query.addAnd("formModule", "Inventory Control");
                query.addField("filePath");
                String sourcePath = (String) util.executeSingleResultQuery(query, userData);

                query = new EMCQuery(enumQueryTypes.SELECT, BaseWebFilePaths.class);
                query.addAnd("processId", BaseWebFilePathProcesses.TREC_IMAGES.toString());
                query.addField("relativeFilePath");
                String destinationPath = (String) util.executeSingleResultQuery(query, userData);
                InputStream is = null;
                OutputStream os = null;

                try {
                    byte[] buffer = new byte[1024];
                    is = new FileInputStream(sourcePath + "/" + helperClass.getImagePath());
                    os = new FileOutputStream(destinationPath +"/"+ helperClass.getImagePath());
                    int length;
                    try {
                        while ((length = is.read(buffer)) > 0) {
                            os.write(buffer, 0, length);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(InventoryItemMasterBean.class.getName()).log(Level.SEVERE, "could not write file", ex);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(InventoryItemMasterBean.class.getName()).log(Level.SEVERE, "could not find file", ex);
                }

            }
        }


        return helperList;

    }

    @Override
    public String getItemWebStoreType(String itemId, EMCUserData userdata) {
        if (isBlank(itemId)) {
            Logger.getLogger(InventoryItemMasterBean.class.getName()).log(Level.SEVERE, "Item not found", userdata);
            return TRECWebStoreTypeEnum.NONE.toString();
        }

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class);
        query.addAnd("itemId", itemId);
        InventoryItemMaster item = (InventoryItemMaster) util.executeSingleResultQuery(query, userdata);

        if (isBlank(item)) {
            Logger.getLogger(InventoryItemMasterBean.class.getName()).log(Level.SEVERE, "Item not found", userdata);
            return TRECWebStoreTypeEnum.NONE.toString();
        } else {
            if (isBlank(item.getWebStoreType())) {
                return TRECWebStoreTypeEnum.NONE.toString();
            } else {
                return item.getWebStoreType();
            }
        }
    }
}
