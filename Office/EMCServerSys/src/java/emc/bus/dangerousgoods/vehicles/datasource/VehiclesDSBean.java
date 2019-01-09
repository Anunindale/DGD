/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.dangerousgoods.vehicles.datasource;

import emc.bus.dangerousgoods.contacts.DGDContactsLocal;
import emc.entity.dangerousgoods.DGDContacts;
import emc.entity.dangerousgoods.datasource.VehiclesDS;
import emc.enums.enumQueryTypes;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author pj
 */
@Stateless
public class VehiclesDSBean extends EMCDataSourceBean implements VehiclesDSLocal {
    
    @EJB
    private DGDContactsLocal contactsBean;
    
    public VehiclesDSBean()
    {
        this.setDataSourceClassName(VehiclesDS.class.getName());
    }
    
    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) 
    {
     VehiclesDS ds = (VehiclesDS) dataSourceInstance;
     
     if (!isBlank(ds.getContactNumber())) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DGDContacts.class);
                query.addAnd("contactNumber", ds.getContactNumber());
            DGDContacts contact = (DGDContacts) util.executeSingleResultQuery(query, userData);
            if (!Functions.checkBlank(contact)) {
                ds.setCompanyName(contact.getCompany());
            }
        }

        return ds;
    }
    
}
