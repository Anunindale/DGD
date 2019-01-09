/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.dangerousgoods.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.dangerousgoods.vehicles.datasource.CompanyName;
import emc.entity.dangerousgoods.DGDVehicles;
import java.util.HashMap;

/**
 *
 * @author pj
 */
public class VehiclesDS extends DGDVehicles {
    
    private String companyName;
    
    public VehiclesDS()
    {
        this.setDataSource(true);
    }
    
    public String getCompanyName()
    {
        return companyName;
    }
    
    public void setCompanyName(String s)
    {
        companyName = s;
    }
    
    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("companyName", new CompanyName());
        return toBuild;
    }
    
}
