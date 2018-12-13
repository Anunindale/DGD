/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.datasource.creditnotes;

import emc.bus.debtors.creditnotes.DebtorsCreditNoteLinesLocal;
import emc.bus.debtors.creditnotes.DebtorsCreditNoteMasterLocal;
import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.inventory.logic.InventoryItemLogicLocal;
import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.entity.debtors.datasource.DebtorsCreditNoteLinesDS;
import emc.entity.debtors.datasource.DebtorsCustomerInvoiceLinesDS;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class DebtorsCreditNoteLinesDSBean extends EMCDataSourceBean implements DebtorsCreditNoteLinesDSLocal {

    @EJB
    private DebtorsCreditNoteLinesLocal linesBean;
    @EJB
    private InventoryItemLogicLocal itemLogicBean;
    @EJB
    private InventoryItemMasterLocal itemBean;
    @EJB
    private InventoryReferenceLocal refBean;
    @EJB
    private DebtorsCreditNoteMasterLocal masterBean;

    /** Creates a new instance of DebtorsCreditNoteLinesDSBean */
    public DebtorsCreditNoteLinesDSBean() {
        this.setDataSourceClassName(DebtorsCreditNoteLinesDS.class.getName());
    }

    @Override
    public Object entityDelete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        return linesBean.delete(convertDataSourceToSuper(dobject, userData), userData);
    }

    @Override
    public Object entityInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        return linesBean.insert(convertDataSourceToSuper(iobject, userData), userData);
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return linesBean.update(convertDataSourceToSuper(uobject, userData), userData);
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        DebtorsCreditNoteLinesDS ds = (DebtorsCreditNoteLinesDS) dataSourceInstance;

        String itemId = ds.getItemId();
        String[] itemRef = refBean.checkItemReference(itemId, userData);

        if (itemRef != null) {
            ds.setItemReference(itemRef[1]);
            ds.setItemDescription(itemBean.findItemDescription(itemId, userData));
        }

        return ds;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        DebtorsCreditNoteLinesDS ds = (DebtorsCreditNoteLinesDS) theRecord;

        if (fieldNameToValidate.equals("itemReference")) {

            DebtorsCreditNoteMaster master = masterBean.getCreditNote(ds.getInvCNNumber(), userData);
            if (!refBean.processItemReference(ds, null, master.getCustomerNo(), userData)) {
                return false;
            }

            itemLogicBean.setItemUOM(ds, userData);
            itemLogicBean.setItemWarehouse(ds, userData);
            itemLogicBean.setItemUnitPrice(ds, userData);
        }

        Object ret = linesBean.validateField(fieldNameToValidate, theRecord, userData);

        if (ret instanceof DebtorsCreditNoteLinesDS) {
            return ret;
        } else if (ret instanceof DebtorsCreditNoteLinesDS) {
            return populateDataSourceFields((DebtorsCustomerInvoiceLinesDS)convertSuperToDataSource(ret, userData), userData);
        } else {
            return ret;
        }
    }
}
