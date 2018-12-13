/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.dangerousgoods.vehicles.datasource;

import emc.bus.dangerousgoods.contacts.DGDContactsLocal;
import emc.entity.dangerousgoods.datasource.VehiclesDS;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
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
     
     if (!isBlank(ds.getCompany())) {
            ds.setCompany(contactsBean.findCompanyByNumber(ds.getContactNumber(), userData));
        }

        return ds;
    }
    
}
