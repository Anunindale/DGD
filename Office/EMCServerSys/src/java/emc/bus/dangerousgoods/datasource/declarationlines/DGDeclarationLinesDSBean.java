/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.dangerousgoods.datasource.declarationlines;

import emc.bus.dangerousgoods.declarationlines.DGDeclarationLinesLocal;
import emc.entity.dangerousgoods.DGDContacts;
import emc.entity.dangerousgoods.datasource.DGDeclarationLinesDS;
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
public class DGDeclarationLinesDSBean extends EMCDataSourceBean implements DGDeclarationLinesDSLocal
{
    
    @EJB
    private DGDeclarationLinesLocal linesBean;
    
    public DGDeclarationLinesDSBean()
    {
        this.setDataSourceClassName(DGDeclarationLinesDS.class.getName());
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        Object ret = linesBean.validateField(fieldNameToValidate, (convertDataSourceToSuper(theRecord, userData)), userData);

        return ret instanceof Boolean ? ret : convertSuperToDataSource(ret, userData);    
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return linesBean.update(convertDataSourceToSuper(uobject, userData), userData);
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
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) 
    {
        DGDeclarationLinesDS ds = (DGDeclarationLinesDS) dataSourceInstance;
        
        //ConsignorName
        if (!Functions.checkBlank(ds.getConsignor())) 
        {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DGDContacts.class);
             query.addAnd("contactNumber", ds.getConsignor());
            DGDContacts contact = (DGDContacts) util.executeSingleResultQuery(query, userData);
            if (!Functions.checkBlank(contact)) {
                ds.setConsignorName(contact.getContactName());
            }

        }
       
        //ConsigneeName
        if (!Functions.checkBlank(ds.getConsignee())) 
        {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DGDContacts.class);
             query.addAnd("contactNumber", ds.getConsignee());
            DGDContacts contact = (DGDContacts) util.executeSingleResultQuery(query, userData);
            if (!Functions.checkBlank(contact)) {
                ds.setConsigneeName(contact.getContactName());
            }

        }
        
        //OperatorName
        if (!Functions.checkBlank(ds.getOperator())) 
        {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DGDContacts.class);
             query.addAnd("contactNumber", ds.getOperator());
            DGDContacts contact = (DGDContacts) util.executeSingleResultQuery(query, userData);
            if (!Functions.checkBlank(contact)) {
                ds.setOperatorName(contact.getContactName());
            }

        }
        
        //productManufacturerName
        if (!Functions.checkBlank(ds.getProductManufacturer())) 
        {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DGDContacts.class);
             query.addAnd("contactNumber", ds.getProductManufacturer());
            DGDContacts contact = (DGDContacts) util.executeSingleResultQuery(query, userData);
            if (!Functions.checkBlank(contact)) {
                ds.setProductManufacturerName(contact.getContactName());
            }

        }
        
        //productOwnerName
        if (!Functions.checkBlank(ds.getProductOwner())) 
        {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DGDContacts.class);
             query.addAnd("contactNumber", ds.getProductOwner());
            DGDContacts contact = (DGDContacts) util.executeSingleResultQuery(query, userData);
            if (!Functions.checkBlank(contact)) {
                ds.setProductOwnerName(contact.getContactName());
            }

        }
        
        //contractingPartyName
        if (!Functions.checkBlank(ds.getContractingParty())) 
        {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DGDContacts.class);
             query.addAnd("contactNumber", ds.getContractingParty());
            DGDContacts contact = (DGDContacts) util.executeSingleResultQuery(query, userData);
            if (!Functions.checkBlank(contact)) {
                ds.setContractingPartyName(contact.getContactName());
            }

        }
        
        //productCustodianName
        if (!Functions.checkBlank(ds.getProductCustodian())) 
        {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DGDContacts.class);
             query.addAnd("contactNumber", ds.getProductCustodian());
            DGDContacts contact = (DGDContacts) util.executeSingleResultQuery(query, userData);
            if (!Functions.checkBlank(contact)) {
                ds.setProductCustodianName(contact.getContactName());
            }

        }
        
        return ds;
    }
    
}
