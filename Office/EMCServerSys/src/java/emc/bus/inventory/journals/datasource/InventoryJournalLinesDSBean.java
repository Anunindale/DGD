/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.journals.datasource;

import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.inventory.journals.InventoryJournalLinesLocal;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.journals.InventoryJournalLines;
import emc.entity.inventory.journals.datasource.InventoryJournalLinesDS;
import emc.enums.inventory.inventorytables.InventoryItemTypes;
import emc.enums.inventory.journals.InventoryJournalTypes;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class InventoryJournalLinesDSBean extends EMCDataSourceBean implements InventoryJournalLinesDSLocal {

    @EJB
    private InventoryJournalLinesLocal linesBean;
    @EJB
    private InventoryItemMasterLocal itemMasterBean;
    @EJB
    private InventoryReferenceLocal referenceBean;

    /** Creates a new instance of InventoryJournalLinesDSBean */
    public InventoryJournalLinesDSBean() {
        this.setDataSourceClassName(InventoryJournalLinesDS.class.getName());
    }

    @Override
    public Object entityDelete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        return linesBean.delete(convertDataSourceToSuper(dobject, userData), userData);
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return linesBean.update(convertDataSourceToSuper(uobject, userData), userData);
    }

    @Override
    public Object entityInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        return linesBean.insert(convertDataSourceToSuper(iobject, userData), userData);
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        Object ret = linesBean.validateField(fieldNameToValidate, theRecord, userData);

        if (ret instanceof Boolean && (Boolean) ret || ret instanceof InventoryJournalLines) {

            InventoryJournalLinesDS record = (InventoryJournalLinesDS) theRecord;

            if (fieldNameToValidate.equals("itemPrimaryReference")) {
                String[] itemReference = referenceBean.checkItemReference(record.getItemPrimaryReference(), userData);
                if (!isBlank(itemReference)) {
                    record.setItemId(itemReference[0]);
                    record.setItemPrimaryReference(itemReference[1]);
                    if (!doItem(record, true, userData)) return false;
                    if (InventoryJournalTypes.TRANSFER.equals(linesBean.getJournalType(record, userData))) {
                        record.setToItemId(itemReference[0]);
                        record.setItemToPrimaryReference(itemReference[1]);
                        if (!doToItem(record, userData)) return false; //also set cost
                    }
                } else {
                    return false;
                }
            } else if (fieldNameToValidate.equals("itemToPrimaryReference")) {
                String[] itemReference = referenceBean.checkItemReference(record.getItemToPrimaryReference(), userData);
                if (!isBlank(itemReference)) {
                    record.setToItemId(itemReference[0]);
                    record.setItemToPrimaryReference(itemReference[1]);
                    if (!doToItem(record, userData)) return false;
                } else {
                    return false;
                }
            }
            return record;
        } else {
            return ret;
        }
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        InventoryJournalLinesDS ds = (InventoryJournalLinesDS) dataSourceInstance;
        if (!isBlank(ds.getItemId())) ds.setItemPrimaryReference(referenceBean.checkItemReference(ds.getItemId(), userData)[1]);
        if (!isBlank(ds.getToItemId())) ds.setItemToPrimaryReference(referenceBean.checkItemReference(ds.getToItemId(), userData)[1]);
        doItem(ds, false, userData);
        doToItem(ds, userData);
        return ds;
    }

    /** This method assigns a value to the item description and uom fields */
    private boolean doItem(InventoryJournalLinesDS ds, boolean setCost, EMCUserData userData) {
        if (!isBlank(ds.getItemId())) {
            InventoryItemMaster item = itemMasterBean.findItem(ds.getItemId(), userData);
            if (InventoryItemTypes.SERVICE.toString().equals(item.getItemType())) {
                Logger.getLogger("emc").log(Level.SEVERE, "Service items are not allowed on Journals", userData);
                return false;
            }
            if (item != null) {
                ds.setDescription(item.getDescription());
                ds.setUom(item.getBaseUOM());
                if (setCost) ds.setCost(item.getCostingCurrentCost());
            }
        }
        return true;
    }

    /** This method assigns a value to the to item description and uom fields */
    private boolean doToItem(InventoryJournalLinesDS ds, EMCUserData userData) {
        if (!isBlank(ds.getToItemId())) {
            InventoryItemMaster item = itemMasterBean.findItem(ds.getToItemId(), userData);
            if (InventoryItemTypes.SERVICE.toString().equals(item.getItemType())) {
                Logger.getLogger("emc").log(Level.SEVERE, "Service items are not allowed on Journals", userData);
                return false;
            }
            if (item != null) {
                ds.setToDescription(item.getDescription());
                ds.setToUom(item.getBaseUOM());
            }
        }
        return true;
    }
}
