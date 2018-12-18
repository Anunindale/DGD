/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.dangerousgoods;

import emc.datatypes.EMCDataType;
import emc.datatypes.dangerousgoods.un.GrossMass;
import emc.datatypes.dangerousgoods.un.NetMass;
import emc.datatypes.dangerousgoods.un.Packaging;
import emc.datatypes.dangerousgoods.un.foreignkeys.LineNumberFK;
import emc.datatypes.trec.chemicals.PackingGroup;
import emc.datatypes.trec.chemicals.foreignkey.UNNumberFK;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author pj
 */

@Entity
@Table(name="DGDUN")
public class DGDUN extends EMCEntityClass{
    
    private String unNumber;
    private String packingGroup;
    private String packaging;
    private BigDecimal grossMass;
    private BigDecimal netMass;
    private String lineNumber;
    
    public DGDUN()
    {
        
    }
    
    public String getUNNumber()
    {
        return unNumber;
    }
    
    public void setUNNumber(String unNumber)
    {
        this.unNumber = unNumber;
    }
    public String getPackingGroup()
    {
        return packingGroup;
    }
    
    public void setPackingGroup(String packingGroup)
    {
        this.packingGroup = packingGroup;
    }
    
    public String getPackaging()
    {
        return packaging;
    }
    
    public void setPackaging(String packaging)
    {
        this.packaging = packaging;
    }
    
    public BigDecimal getGrossMass()
    {
        return grossMass;
    }
    
    public void setGrossMass(BigDecimal grossMass)
    {
        this.grossMass = grossMass;
    }
    
    public BigDecimal getNetMass()
    {
        return netMass;
    }
    
    public void setNetMass(BigDecimal netMass)
    {
        this.netMass = netMass;
    }
    
    public String getLineNumber()
    {
        return lineNumber;
    }
    
    public void setLineNumber(String lineNumber)
    {
        this.lineNumber = lineNumber;
    }
    
    //In case we want to override default query
    @Override 
    public EMCQuery buildQuery()
    {
        EMCQuery query = super.buildQuery();
        return query;
    }
    
    @Override
    public List<String> getDefaultLookupFields()
    {
        List<String> fields = new ArrayList<>();
        fields.add("unNumber");
        return fields;
    }
    
    @Override
    protected HashMap<String, EMCDataType> buildFieldList()
    {
        HashMap ret = super.buildFieldList();
        ret.put("unNumber", new UNNumberFK());
        ret.put("packingGroup", new PackingGroup());
        ret.put("packaging", new Packaging());
        ret.put("grossMass", new GrossMass());
        ret.put("netMass", new NetMass());
        ret.put("lineNumber", new LineNumberFK());
        return ret;
    }
}
