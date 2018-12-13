/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.creditors.creditnoteinvoicelines.datasource;

import emc.bus.creditors.creditnoteinvoicelines.CreditorsCreditNoteInvoiceLinesLocal;
import emc.bus.inventory.InventoryReferenceLocal;
import emc.entity.creditors.CreditorsCreditNoteInvoiceLines;
import emc.entity.creditors.datasource.CreditorsCreditNoteInvoiceLinesDS;
import emc.entity.inventory.InventoryItemMaster;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class CreditorsCreditNoteInvoiceLinesDSBean extends EMCDataSourceBean implements CreditorsCreditNoteInvoiceLinesDSLocal {

    @EJB
    private CreditorsCreditNoteInvoiceLinesLocal masterBean;
    @EJB
    private InventoryReferenceLocal itemReferenceBean;

    public CreditorsCreditNoteInvoiceLinesDSBean() {
        this.setDataSourceClassName(CreditorsCreditNoteInvoiceLinesDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        CreditorsCreditNoteInvoiceLinesDS ds = (CreditorsCreditNoteInvoiceLinesDS) dataSourceInstance;

        if (!isBlank(ds.getItemId())) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class);
            query.addAnd("itemId", ds.getItemId());
            query.addField("itemReference");
            query.addField("description");
            Object[] itemInfo = (Object[]) util.executeSingleResultQuery(query, userData);
            if (itemInfo != null) {
                ds.setItemReference((String) itemInfo[0]);
                ds.setItemDescription((String) itemInfo[1]);
            }
        }

        return ds;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean valid = false;
        boolean returnDS = false;

        CreditorsCreditNoteInvoiceLinesDS ds = (CreditorsCreditNoteInvoiceLinesDS) theRecord;

        Object validateFieldValue = masterBean.validateField(fieldNameToValidate, convertDataSourceToSuper(ds, userData), userData);

        if (validateFieldValue instanceof CreditorsCreditNoteInvoiceLines) {
            ds = (CreditorsCreditNoteInvoiceLinesDS) convertSuperToDataSource(validateFieldValue, userData);
            populateDataSourceFields(ds, userData);
            valid = true;
            returnDS = true;
        } else if ((validateFieldValue instanceof Boolean && (Boolean) validateFieldValue)) {
            valid = true;
            returnDS = false;
        }

        if (valid) {
            if (fieldNameToValidate.equals("itemReference")) {
                if (!isBlank(ds.getItemReference())) {
                    if (itemReferenceBean.doItemRefValidation(ds, userData)) {
                        returnDS = true;
                    }
                }
            }

            if (returnDS) {
                return ds;
            }
        }

        return valid;
    }

    @Override
    public Object entityDelete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        return masterBean.delete(convertDataSourceToSuper(dobject, userData), userData);
    }

    @Override
    public Object entityInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        return masterBean.insert(convertDataSourceToSuper(iobject, userData), userData);
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return masterBean.update(convertDataSourceToSuper(uobject, userData), userData);
    }
}
