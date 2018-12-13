/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.creditors.creditnoteinvoicemaster.datasource;

import emc.bus.creditors.creditnoteinvoicemaster.CreditorsCreditNoteInvoiceMasterLocal;
import emc.entity.creditors.CreditorsCreditNoteInvoiceMaster;
import emc.entity.creditors.datasource.CreditorsCreditNoteInvoiceMasterDS;
import emc.entity.pop.POPSuppliers;
import emc.enums.enumQueryTypes;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.lang.Boolean;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class CreditorsCreditNoteInvoiceMasterDSBean extends EMCDataSourceBean implements CreditorsCreditNoteInvoiceMasterDSLocal {

    @EJB
    private CreditorsCreditNoteInvoiceMasterLocal masterBean;

    public CreditorsCreditNoteInvoiceMasterDSBean() {
        this.setDataSourceClassName(CreditorsCreditNoteInvoiceMasterDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        CreditorsCreditNoteInvoiceMasterDS ds = (CreditorsCreditNoteInvoiceMasterDS) dataSourceInstance;

        if (!isBlank(ds.getSupplierId())) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPSuppliers.class);
            query.addAnd("supplierId", ds.getSupplierId());
            query.addField("supplierName");
            ds.setSupplierName((String) util.executeSingleResultQuery(query, userData));
        }

        return ds;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean valid = false;
        boolean returnDS = false;

        CreditorsCreditNoteInvoiceMasterDS ds = (CreditorsCreditNoteInvoiceMasterDS) theRecord;

        Object validateFieldValue = masterBean.validateField(fieldNameToValidate, convertDataSourceToSuper(ds, userData), userData);

        if (validateFieldValue instanceof CreditorsCreditNoteInvoiceMaster) {
            ds = (CreditorsCreditNoteInvoiceMasterDS) convertSuperToDataSource(validateFieldValue, userData);
            valid = true;
            returnDS = true;
        } else if ((validateFieldValue instanceof Boolean && (Boolean) validateFieldValue)) {
            valid = true;
            returnDS = false;
        }

        if (valid) {
            if (fieldNameToValidate.equals("supplierId")) {
                if (!isBlank(ds.getSupplierId())) {
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPSuppliers.class);
                    query.addAnd("supplierId", ds.getSupplierId());
                    query.addField("supplierName");
                    ds.setSupplierName((String) util.executeSingleResultQuery(query, userData));
                    returnDS = true;
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
