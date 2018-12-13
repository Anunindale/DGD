/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.pop.posting.datasource;

import emc.bus.pop.posting.POPPurchasePostMasterLocal;
import emc.entity.pop.POPSuppliers;
import emc.entity.pop.posting.POPPurchasePostSetupTable;
import emc.entity.pop.posting.datasource.POPPurchasePostMasterDS;
import emc.enums.enumQueryTypes;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.server.utility.EMCServerUtilityLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class POPPurchasePostMasterDSBean extends EMCDataSourceBean implements POPPurchasePostMasterDSLocal {

    @EJB
    private POPPurchasePostMasterLocal postMasterBean;

    /** Creates a new instance of POPPurchasePostMasterDSBean */
    public POPPurchasePostMasterDSBean() {
        this.setDataSourceClassName(POPPurchasePostMasterDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        POPPurchasePostMasterDS instance = (POPPurchasePostMasterDS) dataSourceInstance;

        //Get the setup for the post master
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchasePostSetupTable.class.getName());
        query.addAnd("postSetupId", instance.getPostMasterId());

        POPPurchasePostSetupTable setup = (POPPurchasePostSetupTable) util.executeSingleResultQuery(query, userData);

        if (setup != null) {
            instance.setDocumentType(setup.getDocumentType());
            instance.setQuantitySelection(setup.getQuantitySelection());
            instance.setPost(setup.getPost());
            instance.setPrint(setup.getPrint());
        }

        query = new EMCQuery(enumQueryTypes.SELECT, POPSuppliers.class.getName());
        query.addAnd("supplierId", instance.getSupplierId());

        POPSuppliers supp = (POPSuppliers) util.executeSingleResultQuery(query, userData);
        if (supp != null) {
            instance.setSupplierName(supp.getSupplierName());
        }
        return instance;
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException { 
        return postMasterBean.update(convertDataSourceToSuper(uobject, userData), userData);
    }

    @Override
    public Object entityInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        return postMasterBean.insert(convertDataSourceToSuper(iobject, userData), userData);
    }

    @Override
    public Object entityDelete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        return postMasterBean.delete(convertDataSourceToSuper(dobject, userData), userData);
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
       return postMasterBean.validateField(fieldNameToValidate, convertDataSourceToSuper(theRecord, userData), userData);
    }
    
    /** Populates the supplier name field. */
//    private void doSupplier(POPPurchasePostMasterDS postMasterDS, EMCUserData userData) {
//        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPSuppliers.class.getName());
//        query.addAnd("supplierId", postMasterDS.getSupplierId());
//        query.addField("supplierName");
//
//        String supplierName = (String)util.executeSingleResultQuery(query, userData);
//
//        postMasterDS.setSupplierName(supplierName == null ? "" : supplierName); 
//    }

}
