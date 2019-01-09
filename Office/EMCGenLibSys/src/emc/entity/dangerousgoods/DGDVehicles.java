/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.dangerousgoods;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.companies.RegistrationNumber;
import emc.datatypes.dangerousgoods.contacts.Company;
import emc.datatypes.dangerousgoods.vehicles.ContactNumberFK;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author pj
 */
@Entity
@Table(name="DGDVehicles")
public class DGDVehicles extends EMCEntityClass {
    
    private String registrationNumber;
    private String contactNumber;
    
    public DGDVehicles()
    {
        
    }
    
    public String getRegistrationNumber()
    {
        return registrationNumber;
    }
    
    public void setRegistrationNumber(String registrationNumber)
    {
        this.registrationNumber = registrationNumber;
    }
    
    public String getContactNumber()
    {
        return contactNumber;
    }
    
    public void setContactNumber(String contactNumber)
    {
        this.contactNumber = contactNumber;
    }
    
    @Override
    public EMCQuery buildQuery()
    {
        EMCQuery query = super.buildQuery();
        //query conditions to add
        return query;
    }
    
    @Override
    public List<String> getDefaultLookupFields()
    {
        List<String> fields = new ArrayList<>();
//        fields.add("contactNumber");
        fields.add("registrationNumber");
        return fields;
    }
    
    @Override
    protected HashMap<String, EMCDataType> buildFieldList()
    {
        HashMap ret = super.buildFieldList();
        ret.put("contactNumber", new ContactNumberFK());
        ret.put("registrationNumber", new RegistrationNumber());
        return ret;
    }
    
}
