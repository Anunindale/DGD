/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.dangerousgoods.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.dangerousgoods.contacts.Company;
import emc.entity.dangerousgoods.DGDVehicles;
import java.util.HashMap;

/**
 *
 * @author pj
 */
public class VehiclesDS extends DGDVehicles {
    
    private String company;
    
    public VehiclesDS()
    {
        this.setDataSource(true);
    }
    
    public String getCompany()
    {
        return company;
    }
    
    public void setCompany(String s)
    {
        company = s;
    }
    
    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("company", new Company());
        return toBuild;
    }
    
}
