/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.dangerousgoods;

import emc.datatypes.EMCDataType;
import emc.datatypes.dangerousgoods.contacts.foreignkeys.CustomerFK;
import emc.datatypes.dangerousgoods.declarationmaster.DecNumber;
import emc.datatypes.dangerousgoods.declarationmaster.DefConsignor;
import emc.datatypes.dangerousgoods.declarationmaster.DefOperator;
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
@Table(name="DGDeclarationMaster", uniqueConstraints={@UniqueConstraint(columnNames={"companyId", "customer", "decNumber"})})
public class DGDeclarationMaster extends EMCEntityClass
{
    private String decNumber;          //NumberSequence-controlled declaration master number
    private String customer;        //From SOPCustomers
    private String defConsignor;     //Default Consignor
    private String defOperator;     //Default Operator
    
    public DGDeclarationMaster()
    {
        
    }
    
    public String getNumber()
    {
        return decNumber;
    }
    
    public void setNumber(String decNumber)
    {
        this.decNumber = decNumber;
    }
    
    public String getCustomer()
    {
        return customer;
    }
    
    public void setCustomer(String customer)
    {
        this.customer = customer;
    }
    
    public String getDefConsignor()
    {
        return defConsignor;
    }
            
    public void setDefConsignor(String defConsignor)
    {
        this.defConsignor = defConsignor;
    }
    
    public String getDefOperator()
    {
        return defOperator;
    }
    
    public void setDefOperator(String defOperator)
    {
        this.defOperator = defOperator;
    }
    
    //In case we ever want to add something to the query
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
        fields.add("decNumber");
        fields.add("customer");
        fields.add("defConsignor");
        fields.add("defOperator");
        return fields;
    }
    
    @Override
    protected HashMap<String, EMCDataType> buildFieldList()
    {
        HashMap ret = super.buildFieldList();
        ret.put("decNumber", new DecNumber());
        ret.put("customer", new CustomerFK());
        ret.put("defConsignor", new DefConsignor());
        ret.put("defOperator", new DefOperator());
        return ret;
    }
}
