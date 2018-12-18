/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.dangerousgoods.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.systemwide.Description;
import emc.entity.dangerousgoods.DGDUN;
import java.util.HashMap;

/**
 *
 * @author pj
 */
public class UNDS extends DGDUN{
    private String description;
    
    public UNDS()
    {
        this.setDataSource(true);
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("description", new Description());
        return toBuild;
    }
}
