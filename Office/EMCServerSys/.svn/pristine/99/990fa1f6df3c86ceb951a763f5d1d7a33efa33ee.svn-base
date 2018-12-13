/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.pop;

import emc.entity.pop.POPSuppliers;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.server.utility.EMCServerUtilityLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
 
/**
 *
 * @author riaan
 */
@Stateless
public class POPSupplierBean extends EMCEntityBean implements POPSupplierLocal {
/** Creates a new instance of POPSupplierBean */
    public POPSupplierBean() {
        
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        Boolean valid =(Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        
        POPSuppliers supplier = (POPSuppliers)theRecord;
        
        if (fieldNameToValidate.equals("vatRegistrationNo")) {
            if (supplier.getVatApplicable()) {
                if (isBlank(supplier.getVatRegistrationNo())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "VAT Registration No is required when VAT is applicable on supplier.");
                    valid = false;
                }
            }
        }
        
        Object ret = null;
        
        ret = valid;
        
        return ret;
    }
    
    public String getSupplierName(String suppId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPSuppliers.class.getName());
        query.addField("supplierName");
        query.addAnd("supplierId", suppId);
        Object o = util.executeSingleResultQuery(query, userData);
        if (o != null) {
            return o.toString();
        }
        return null;
    }

    /**
    * Returns the specified supplier, or null, if the supplier is not found.
    * @param supplierId Supplier id.
    * @param userData User data.
    * @return The specified supplier, or null, iof the supplier is not found.
    */
    public POPSuppliers getSupplier(String supplierId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPSuppliers.class);
        query.addAnd("supplierId", supplierId);

        return (POPSuppliers)util.executeSingleResultQuery(query, userData);
    }

}
