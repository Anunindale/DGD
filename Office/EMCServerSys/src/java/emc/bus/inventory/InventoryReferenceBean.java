package emc.bus.inventory;

import emc.datatypes.inventory.customerreference.CustomerItemDesc;
import emc.datatypes.inventory.inventoryreference.supplier.SupplierItemDesc;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryReference;
import emc.entity.inventory.dimensions.InventoryItemDimension1Setup;
import emc.entity.inventory.dimensions.InventoryItemDimension2Setup;
import emc.entity.inventory.dimensions.InventoryItemDimension3Setup;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.entity.inventory.dimensions.superclasses.InventoryItemDimensionSetup;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.emcquery.EMCQueryOrderByDirections;
import emc.enums.enumPersistOptions;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.inventorytables.InventoryReferenceTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.inventory.ItemDimensionInterface;
import emc.inventory.ItemReferenceInterface;
import emc.messages.ServerInventoryMessageEnum;
import emc.tables.EMCTable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * 
 * @author wikus
 */
@Stateless
public class InventoryReferenceBean extends EMCEntityBean implements InventoryReferenceLocal {

    @EJB
    private InventoryItemMasterLocal itemMasterUpdate;

    public InventoryReferenceBean() {
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        InventoryReference reference = (InventoryReference) theRecord;

        InventoryReferenceTypes refType = InventoryReferenceTypes.fromString(reference.getRefType());

        //Ensure that correct emclable is used in field validation messages.
        if (refType == InventoryReferenceTypes.CUSTOMER) {
            reference.getFieldDataTypeMapper().put("alternativeDescription", new CustomerItemDesc());
        } else if (refType == InventoryReferenceTypes.SUPPLIER) {
            reference.getFieldDataTypeMapper().put("alternativeDescription", new SupplierItemDesc());
        }

        boolean back = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        if (!(isBlank(reference.getRefType()))) {
            if (fieldNameToValidate.equals("customerNo")) {
                if (reference.getRefType().equals(InventoryReferenceTypes.CUSTOMER.toString()) && (back == true)) {
                    back = true;
                } else {
                    Logger.getLogger("emc").log(Level.INFO, "Reference type needs to be set to Customer to enter customer details", userData);
                    back = false;
                }
            } else if (fieldNameToValidate.equals("supplierNo")) {
                if (reference.getRefType().equals(InventoryReferenceTypes.SUPPLIER.toString()) && (back == true)) {
                    back = true;
                } else {
                    Logger.getLogger("emc").log(Level.INFO, "Reference type needs to be set to Supplier to enter supplier details", userData);
                    back = false;
                }
            } else if (fieldNameToValidate.equals("refType")) {
                if (reference.getRecordID() != 0) {
                    InventoryReference persisted = (InventoryReference) util.findDetachedPersisted(reference, userData);
                    InventoryReferenceTypes persistedRefType = InventoryReferenceTypes.fromString(persisted.getRefType());
                    if (persistedRefType == InventoryReferenceTypes.PRIMARY || persistedRefType == InventoryReferenceTypes.DEFAULT) {
                        logMessage(Level.SEVERE, ServerInventoryMessageEnum.REF_MAY_NOT_CHANGE, userData, persistedRefType);
                        return false;
                    }
                }

                if (reference.getRefType().equals(InventoryReferenceTypes.CUSTOMER.toString())) {
                    reference.setSupplierNo(null);
                    return reference;
                } else if (reference.getRefType().equals(InventoryReferenceTypes.SUPPLIER.toString())) {
                    reference.setCustomerNo(null);
                    return reference;
                } else if (reference.getRefType().equals(InventoryReferenceTypes.PRIMARY.toString())) {
                    reference.setSupplierNo(null);
                    reference.setCustomerNo(null);
                    return reference;
                }
            }

        }
        return back;
    }

    @Override
    public Object update(Object uobject, EMCUserData userData)
            throws EMCEntityBeanException {
        InventoryReference record = (InventoryReference) uobject;
        if (record.getRefType().equals(InventoryReferenceTypes.PRIMARY.toString())) {
            if (!checkUsed(record.getItemId(), record.getReference(), userData)) {
                Logger.getLogger("emc").log(Level.SEVERE, "Item is already in use. Cannot change Reference.", userData);
                return false;
            }
        }
        Object ret = super.update(generateUnique(record), userData);
        if (record.getRefType().equals(InventoryReferenceTypes.PRIMARY.toString())) {
            itemMasterUpdate.setReference(record.getItemId(), record.getReference(), userData);
        }

        return ret;
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData)
            throws EMCEntityBeanException {
        InventoryReference record = (InventoryReference) iobject;

        if (record.getRefType().equals(InventoryReferenceTypes.PRIMARY.toString())) {
            itemMasterUpdate.setReference(record.getItemId(), record.getReference(), userData);
        }

        return super.insert(generateUnique(record), userData);
    }

    @Override
    public Object delete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        InventoryReference record = (InventoryReference) dobject;

        if (record.getRefType().equals(InventoryReferenceTypes.PRIMARY.toString())) {
            itemMasterUpdate.removeReference(record.getItemId(), userData);
        }
        return super.delete(dobject, userData);
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean back = super.doInsertValidation(vobject, userData);

        InventoryReference reference = (InventoryReference) vobject;
        String type = reference.getRefType();
        if (back) {
            if (type.equals(InventoryReferenceTypes.CUSTOMER.toString())) {
                if (isBlank(reference.getCustomerNo())) {
                    Logger.getLogger("emc").log(Level.INFO, "Customer No is a mandatory field", userData);
                    return false;
                }
            } else if (type.equals(InventoryReferenceTypes.SUPPLIER.toString())) {
                if (isBlank(reference.getSupplierNo())) {
                    Logger.getLogger("emc").log(Level.INFO, "Supplier No is a mandatory field", userData);
                    return false;
                }
            }

            back = back && validateRef(reference, userData);
        }
        return back;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        InventoryReference reference = (InventoryReference) vobject;
        String type = reference.getRefType();
        if (type.equals(InventoryReferenceTypes.CUSTOMER.toString())) {
            if (isBlank(reference.getCustomerNo())) {
                Logger.getLogger("emc").log(Level.INFO, "Customer No is a mandatory field", userData);
                return false;
            }
        } else if (type.equals(InventoryReferenceTypes.SUPPLIER.toString())) {
            if (isBlank(reference.getSupplierNo())) {
                Logger.getLogger("emc").log(Level.INFO, "Supplier No is a mandatory field", userData);
                return false;
            }
        } else if (type.equals(InventoryReferenceTypes.DEFAULT.toString())) {
            Logger.getLogger("emc").log(Level.INFO, "You cannot update the default reference", userData);
            return false;
        }

        InventoryReference persisted = (InventoryReference) util.findDetachedPersisted(reference, userData);
        if (!util.checkObjectsEqual(reference.getRefType(), persisted.getRefType())) {
            InventoryReferenceTypes persistedRefType = InventoryReferenceTypes.fromString(persisted.getRefType());

            if (persistedRefType == InventoryReferenceTypes.PRIMARY || persistedRefType == InventoryReferenceTypes.DEFAULT) {
                logMessage(Level.SEVERE, ServerInventoryMessageEnum.REF_MAY_NOT_CHANGE, userData, persistedRefType);
                return false;
            }
        }

        return super.doUpdateValidation(vobject, userData) && validateRef(reference, userData);
    }

    @Override
    public boolean doDeleteValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        InventoryReference reference = (InventoryReference) vobject;
        if (reference.getRefType().equals(InventoryReferenceTypes.DEFAULT.toString())) {
            Logger.getLogger("emc").log(Level.INFO, "You cannot update the default reference", userData);
            return false;
        }
        return super.doDeleteValidation(vobject, userData);
    }

    @Override
    public void setNewPrimaryReference(String itemId, String reference,
            EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryReference.class);
        query.addAnd("itemId", itemId);
        query.addAnd("refType", InventoryReferenceTypes.PRIMARY.toString());
        Object qRef = util.executeSingleResultQuery(query, userData);
        if (qRef != null) {
            InventoryReference refFound = (InventoryReference) qRef;
            refFound.setReference(reference);
            super.update(qRef, userData);
        } else {
            InventoryReference record = new InventoryReference();
            record.setItemId(itemId);
            record.setRefType(InventoryReferenceTypes.PRIMARY.toString());
            record.setReference(reference);
            record.setCompanyId(util.getCompanyId(InventoryReference.class.getName(), userData));
            this.insert(record, userData);
        }
    }

    @Override
    public void setDefaultReference(String itemId, EMCUserData userData)
            throws EMCEntityBeanException {
        InventoryReference record = new InventoryReference();
        record.setItemId(itemId);
        record.setRefType(InventoryReferenceTypes.DEFAULT.toString());
        record.setReference(itemId);
        record.setCompanyId(util.getCompanyId(InventoryReference.class.getName(), userData));
        this.insert(record, userData);
    }

    @Override
    public String getNumRows(Class theTable, EMCUserData userData) {
        String ret = super.getNumRows(theTable, userData);
        return ret;
    }

    private InventoryReference generateUnique(InventoryReference record) {
        String parm = record.getItemId() +
                record.getReference() +
                record.getRefType() +
                record.getCustomerNo() +
                record.getSupplierNo() +
                record.getAlternativeDescription() +
                record.getDimension1() +
                record.getDimension2() +
                record.getDimension3();

        record.setUniqueDescriptor(parm);
        return record;
    }

    /**
     * Checks if the item is alreay in use in the system.
     * @param itemId
     * @param reference
     * @param userData
     * @return boolean
     */
    @Override
    public boolean checkUsed(String itemId, String reference, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class);
        query.addAnd("itemId", itemId);
        query.addAnd("companyId", userData.getCompanyId());
        InventoryItemMaster oldRecord = (InventoryItemMaster) util.executeSingleResultQuery(query, userData);
        if (oldRecord == null) {
            return true;
        }
        if (isBlank(oldRecord.getItemReference()) && isBlank(reference)) {
            return true;
        }
        if (isBlank(oldRecord.getItemReference()) && !isBlank(reference)) {
            return true;
        }
        if (!isBlank(oldRecord.getItemReference()) && isBlank(reference)) {
            return testUsage(oldRecord, userData);
        }
        if (!reference.equals(oldRecord.getItemReference())) {
            return testUsage(oldRecord, userData);
        }
        return true;
    }

    @Override
    public boolean testUsage(EMCTable oldRecord, EMCUserData userData) {
        DefaultMutableTreeNode tree = super.testRelations(enumPersistOptions.DELETE, oldRecord, userData);
        String userObject;
        List<String> allowList = new ArrayList<String>();
        allowList.add(new InventoryReference().getEmcLabel());
        allowList.add(new InventoryItemDimension1Setup().getEmcLabel());
        allowList.add(new InventoryItemDimension2Setup().getEmcLabel());
        allowList.add(new InventoryItemDimension3Setup().getEmcLabel());
        allowList.add(new InventoryItemDimensionSetup().getEmcLabel());
        allowList.add(new InventoryItemDimensionCombinations().getEmcLabel());
        boolean ret = true;
        boolean flag;
        List<String> loggerList = new ArrayList<String>();
        DefaultMutableTreeNode parent;
        Enumeration<DefaultMutableTreeNode> e;
        for (int i = 0; i < 4; i++) {
            parent = (DefaultMutableTreeNode) tree.getChildAt(i);
            e = parent.children();
            while (e.hasMoreElements()) {
                flag = true;
                userObject = e.nextElement().getUserObject().toString();
                for (String s : allowList) {
                    if (userObject.contains(s)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    ret = false;
                    loggerList.add(userObject);
                }
            }
        }
        if (!ret) {
            Logger.getLogger("emc").log(Level.INFO, "Item usage", new Object[]{userData, "item", "used"});
            for (String table : loggerList) {
                Logger.getLogger("emc").log(Level.INFO, "Item is used on " + table, new Object[]{userData, "item", "used"});
            }
            Logger.getLogger("emc").log(Level.INFO, "Item usage", new Object[]{userData, "/item", "used"});
        }
        return ret;
    }

    /**
     * Does a check to see if the Item reference enterd is valid;
     * @param itemReference the reference entered
     * @param userData
     * @return String[] 0-itemId 1-Primary Reference or null if reference is invalid
     */
    @Override
    public String[] checkItemReference(String itemReference, EMCUserData userData) {
        return checkItemReference(itemReference, true, userData);
    }

    /**
     * Does a check to see if the Item reference enterd is valid;
     * @param itemReference the reference entered
     * @param logMessage should the method log messages
     * @param userData
     * @return String[] 0-itemId 1-Primary Reference or null if reference is invalid
     */
    @Override
    public String[] checkItemReference(String itemReference, boolean logMessage, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryReference.class);
        //query.addAnd("companyId", userData.getCompanyId());
        query.addAnd("reference", itemReference);
        query.addAnd("supplierNo", null);
        query.addAnd("customerNo", null);
        InventoryReference reference = (InventoryReference) util.executeSingleResultQuery(query, userData);
        if (reference == null) {
            if (logMessage) {
                Logger.getLogger("emc").log(Level.SEVERE, "The specified item does not exist: " + itemReference, userData);
            }
            return null;
        } else {
            query = new EMCQuery(enumQueryTypes.SELECT, InventoryReference.class);
            query.addAnd("refType", InventoryReferenceTypes.PRIMARY.toString());
            query.addAnd("itemId", reference.getItemId());
            InventoryReference referenceBack = (InventoryReference) util.executeSingleResultQuery(query, userData);
            if (referenceBack == null) {
                query = new EMCQuery(enumQueryTypes.SELECT, InventoryReference.class);
                query.addAnd("refType", InventoryReferenceTypes.DEFAULT.toString());
                query.addAnd("itemId", reference.getItemId());
                query.addAnd("companyId", userData.getCompanyId());
                referenceBack = (InventoryReference) util.executeSingleResultQuery(query, userData);
            }
            return new String[]{referenceBack.getItemId(), referenceBack.getReference()};
        }
    }

    /** Returns an Object array containing the supplier reference in position 0 and the descriprion in position 1. */
    @Override
    public Object[] getSupplierReference(String itemId, String dim1Id, String dim2Id, String dim3Id, String supplierId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryReference.class);
        query.addField("reference");
        query.addField("alternativeDescription");
        query.addAnd("itemId", itemId);
        query.addAnd("refType", InventoryReferenceTypes.SUPPLIER.toString());
        query.addAnd("supplierNo", supplierId);
        query.addAnd("companyId", userData.getCompanyId());

        List<Object[]> ret = (List<Object[]>) util.executeGeneralSelectQuery(query, userData);
        if (ret.size() == 1) {
            return ret.get(0);
        } else {
            return new Object[]{"", ""};
        }
    }

    @Override
    public Object[] getCustomerReference(String itemId, String dim1Id, String dim2Id, String dim3Id, String customerId, EMCUserData userData) {

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryReference.class);
        query.addField("reference");
        query.addField("alternativeDescription");
        query.addAnd("itemId", itemId);
        query.addAnd("customerNo", customerId);
        query.addAnd("dimension1", dim1Id);
        query.addAnd("dimension2", dim2Id);
        query.addAnd("dimension3", dim3Id);

        Object[] ret = (Object[]) util.executeSingleResultQuery(query, userData);

        if (ret == null) {
            query = new EMCQuery(enumQueryTypes.SELECT, InventoryReference.class);
            query.addField("reference");
            query.addField("alternativeDescription");
            query.addAnd("itemId", itemId);
            query.addAnd("refType", InventoryReferenceTypes.CUSTOMER.toString());
            query.addAnd("customerNo", customerId);

            ret = (Object[]) util.executeSingleResultQuery(query, userData);

            if (ret == null) {
                ret = new Object[]{"", ""};
            }
        }

        return ret;
    }

    /** Validates items and populates the item reference and description field on an entity.  */
    @Override
    public boolean doItemRefValidation(ItemReferenceInterface record, EMCUserData userData) {
        String[] itemRef = checkItemReference(record.getItemReference(), userData);

        if (itemRef != null) {
            record.setItemId(itemRef[0]);
            record.setItemReference(itemRef[1]);
            record.setItemDescription(itemMasterUpdate.findItemDescription(itemRef[0], userData));
            return true;
        } else {
            Logger.getLogger("emc").log(Level.SEVERE, "No reference found for this item on Item Reference.", userData);
            return false;
        }
    }

    @Override
    public void populateDSFields(ItemReferenceInterface record, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class.getName());
        query.addAnd("itemId", record.getItemId());
        InventoryItemMaster master = (InventoryItemMaster) util.executeSingleResultQuery(query, userData);
        if (master == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "Item does not exist.", userData);
            return;
        }
        record.setItemReference(isBlank(master.getItemReference()) ? master.getItemId() : master.getItemReference());
        record.setItemDescription(master.getDescription());
    }

    @Override
    public ArrayList<String> findReferenceAndDesc(String itemId, InventoryReferenceTypes refType, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryReference.class);
        query.addTableAnd(InventoryItemMaster.class.getName(), "itemId", InventoryReference.class.getName(), "itemId");
        query.addAnd("itemId", itemId, InventoryReference.class.getName());
        query.addAnd("refType", refType.toString(), InventoryReference.class.getName());
        query.addField("reference", InventoryReference.class.getName());
        query.addField("itemId", InventoryItemMaster.class.getName());
        query.addField("description", InventoryItemMaster.class.getName());
        Object[] itemData = (Object[]) util.executeSingleResultQuery(query, userData);
        ArrayList<String> ret = new ArrayList<String>();
        if (itemData == null) {
            query.removeAnd("refType");
            query.addAnd("refType", InventoryReferenceTypes.DEFAULT.toString());

            itemData = (Object[]) util.executeSingleResultQuery(query, userData);
            if (itemData == null) {
                return ret;
            }
        }
        ret.add((String) (isBlank(itemData[0]) ? itemData[1] : itemData[0]));
        ret.add((String) itemData[2]);
        return ret;
    }

    /**
     * Validates the item reference on the specified record and fetches and sets the
     * appropriate item id, primary reference and item description.
     *
     * If the record passed to this method implements ItemDimensionInterface, the
     * appropriate dimensions will also be set.
     *
     * @param record Record to validate.
     * @param supplier Supplier number.  This may be null.
     * @param customer Customer number.  This may be null.
     * @param userData User data.
     * @return A boolean indicating whether the item reference on the specified record is valid.
     */
    @Override
    public boolean processItemReference(ItemReferenceInterface record, String supplier, String customer, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryReference.class);
        query.addAnd("reference", record.getItemReference());
        query.openAndConditionBracket();
        query.openConditionBracket(EMCQueryBracketConditions.NONE);
        query.addAnd("customerNo", customer);
        query.addAnd("supplierNo", supplier);
        query.closeConditionBracket();
        query.addOr("refType", InventoryReferenceTypes.DEFAULT);
        query.addOr("refType", InventoryReferenceTypes.PRIMARY);
        query.closeConditionBracket();

        List<InventoryReference> references = (List<InventoryReference>) util.executeGeneralSelectQuery(query, userData);

        if (references == null || references.isEmpty()) {
            logMessage(Level.SEVERE, ServerInventoryMessageEnum.INVALID_REFERENCE, userData);
            return false;
        } else {
            InventoryReference reference = references.get(0);

            record.setItemId(reference.getItemId());

            InventoryItemMaster item = itemMasterUpdate.findItem(reference.getItemId(), userData);

            if (item != null) {
                record.setItemReference(item.getItemReference());
                record.setItemDescription(item.getDescription());
            }

            //Only populate dimensions if only one record was found.
            if (record instanceof ItemDimensionInterface && references.size() == 1) {
                ItemDimensionInterface dimensionInterface = (ItemDimensionInterface) record;
                dimensionInterface.setDimension1(reference.getDimension1());
                dimensionInterface.setDimension2(reference.getDimension2());
                dimensionInterface.setDimension3(reference.getDimension3());
            }

            return true;
        }
    }

    @Override
    public InventoryReference findSupplierReferenceRecord(String itemId, String dim1Id, String dim2Id, String dim3Id, String supplierId, boolean logMsg, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryReference.class);
        query.addAnd("itemId", itemId);
        query.addAnd("dimension1", dim1Id);
        query.addAnd("dimension2", dim2Id);
        query.addAnd("dimension3", dim3Id);
        query.addAnd("supplierNo", supplierId);
        query.addAnd("refType", InventoryReferenceTypes.SUPPLIER);

        List<InventoryReference> referenceList = util.executeGeneralSelectQuery(query, userData);

        if (referenceList.isEmpty()) {
            query = new EMCQuery(enumQueryTypes.SELECT, InventoryReference.class);
            query.addAnd("itemId", itemId);
            query.openAndConditionBracket();
            query.addOr("refType", InventoryReferenceTypes.DEFAULT);
            query.addOr("refType", InventoryReferenceTypes.PRIMARY);
            query.closeConditionBracket();
            query.addOrderBy("refType", InventoryReference.class.getName(), EMCQueryOrderByDirections.DESC);
            referenceList = util.executeGeneralSelectQuery(query, userData);
            if (referenceList.isEmpty()) {
                if (logMsg) {
                    logMessage(Level.SEVERE, ServerInventoryMessageEnum.NO_REFERENCE, userData);
                }
                return null;
            }
            return referenceList.get(0);
        } else {
            if (referenceList.size() > 1) {
                if (logMsg) {
                    logMessage(Level.WARNING, ServerInventoryMessageEnum.MULTI_SUPP_REFERENCE, userData);
                }
            }
            return referenceList.get(0);
        }
    }

    public InventoryReference findCustomerReferenceRecord(String itemId, String dim1Id, String dim2Id, String dim3Id, String customerId, EMCUserData userData) {
        InventoryReference toReturn = null;
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryReference.class);
        query.addAnd("itemId", itemId);
        //query.addAnd("dimension1", dim1Id);
        //query.addAnd("dimension2", dim2Id);
        //query.addAnd("dimension3", dim3Id);
        query.addAnd("customerNo", customerId);
        query.addAnd("refType", InventoryReferenceTypes.CUSTOMER);
        query.addOrderBy("dimension1");
        query.addOrderBy("dimension2");
        query.addOrderBy("dimension3");

        List<InventoryReference> referenceList = util.executeGeneralSelectQuery(query, userData);

        if (referenceList.isEmpty()) {
            query = new EMCQuery(enumQueryTypes.SELECT, InventoryReference.class);
            query.addAnd("itemId", itemId);
            query.openAndConditionBracket();
            query.addOr("refType", InventoryReferenceTypes.DEFAULT);
            query.addOr("refType", InventoryReferenceTypes.PRIMARY);
            query.closeConditionBracket();
            query.addOrderBy("refType", InventoryReference.class.getName(), EMCQueryOrderByDirections.DESC);
            referenceList = util.executeGeneralSelectQuery(query, userData);
            if (referenceList.isEmpty()) {
                logMessage(Level.SEVERE, ServerInventoryMessageEnum.NO_REFERENCE, userData);
                return null;
            }
            return referenceList.get(0);
        } else {
            int bestMatch = -1;
            int currentMatch = 0;
            for (InventoryReference curRef : referenceList) {
                currentMatch = 0;
                if (util.checkObjectsEqual(curRef.getDimension1(), dim1Id)) {
                    currentMatch++;
                }
                if (util.checkObjectsEqual(curRef.getDimension2(), dim2Id)) {
                    currentMatch++;
                }
                if (util.checkObjectsEqual(curRef.getDimension3(), dim3Id)) {
                    currentMatch++;
                }
                if (currentMatch > bestMatch) {
                    toReturn = curRef;
                    bestMatch = currentMatch;
                }
            }
            return toReturn;
        }
    }

    /**
     * Gets data from the InventoryCustomerReference and InventorySupplierReference tables and
     * inserts it into the InventoryReference table.
     */
    @Override
    public void importData(EMCUserData userData) throws EMCEntityBeanException {
        throw new EMCEntityBeanException("Old data can no longer be imported.  Supplier & Customer reference tables have been removed.");
    }

    private boolean validateRef(InventoryReference reference, EMCUserData userData) {
        if ("".equals(reference.getDimension1())) {
            reference.setDimension1(null);
        }
        if ("".equals(reference.getDimension2())) {
            reference.setDimension2(null);
        }
        if ("".equals(reference.getDimension3())) {
            reference.setDimension3(null);
        }
        if (reference.getDimension1() == null && reference.getDimension2() == null && reference.getDimension3() == null) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryReference.class);
            //Ignore this record, in case this method is being called from update validation.
            query.addAnd("recordID", reference.getRecordID(), EMCQueryConditions.NOT);
            query.openAndConditionBracket();
            query.addAnd("reference", reference.getReference());
            query.addOr("itemId", reference.getItemId());
            query.closeConditionBracket();
            query.addAnd("refType", reference.getRefType());
            query.addAnd("supplierNo", reference.getSupplierNo());
            query.addAnd("customerNo", reference.getCustomerNo());
            query.addAnd("dimension1", null);
            query.addAnd("dimension2", null);
            query.addAnd("dimension3", null);

            List<InventoryReference> similarReferences = (List<InventoryReference>) util.executeGeneralSelectQuery(query, userData);

            if (similarReferences != null && !similarReferences.isEmpty()) {
                InventoryReference similarRef = similarReferences.get(0);
                logMessage(Level.SEVERE, ServerInventoryMessageEnum.SIMILAR_REF_EXISTS, userData, similarRef.getItemId(), similarRef.getReference(), similarRef.getRefType(), similarRef.getSupplierNo(), similarRef.getCustomerNo());
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
}
    

