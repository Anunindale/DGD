/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.dangerousgoods.datasource.declarationmaster;

import emc.bus.dangerousgoods.declarationmaster.DGDeclarationMasterLocal;
import emc.entity.dangerousgoods.DGDContacts;
import emc.entity.dangerousgoods.datasource.DGDeclarationMasterDS;
import emc.entity.sop.SOPCustomers;
import emc.enums.enumQueryTypes;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author asd_admin
 */
@Stateless
public class DGDeclarationMasterDSBean extends EMCDataSourceBean implements DGDeclarationMasterDSLocal{
    @EJB
    private DGDeclarationMasterLocal masterBean;
    
    public DGDeclarationMasterDSBean()
    {
        this.setDataSourceClassName(DGDeclarationMasterDS.class.getName());
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        Object ret = masterBean.validateField(fieldNameToValidate, (convertDataSourceToSuper(theRecord, userData)), userData);

        return ret instanceof Boolean ? ret : convertSuperToDataSource(ret, userData);    
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return masterBean.update(convertDataSourceToSuper(uobject, userData), userData);
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
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) 
    {
        DGDeclarationMasterDS ds = (DGDeclarationMasterDS) dataSourceInstance;
        
        //ConsignorName
        if (!Functions.checkBlank(ds.getDefConsignor())) 
        {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DGDContacts.class);
             query.addAnd("contactNumber", ds.getDefConsignor());
            DGDContacts contact = (DGDContacts) util.executeSingleResultQuery(query, userData);
            if (!Functions.checkBlank(contact)) {
                ds.setConsignorName(contact.getContactName());
            }

        }
       
        //OperatorName
        if (!Functions.checkBlank(ds.getDefOperator())) 
        {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DGDContacts.class);
             query.addAnd("contactNumber", ds.getDefOperator());
            DGDContacts contact = (DGDContacts) util.executeSingleResultQuery(query, userData);
            if (!Functions.checkBlank(contact)) {
                ds.setOperatorName(contact.getContactName());
            }

        }
        
        //CustomerName
        if (!Functions.checkBlank(ds.getCustomer())) 
        {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class);
             query.addAnd("customerId", ds.getCustomer());
            SOPCustomers contact = (SOPCustomers) util.executeSingleResultQuery(query, userData);
            if (!Functions.checkBlank(contact)) {
                ds.setCustomerName(contact.getCustomerName());
            }

        }
        
        return ds;
    }
    
}
