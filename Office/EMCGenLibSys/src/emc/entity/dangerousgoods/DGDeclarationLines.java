/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.dangerousgoods;

import emc.datatypes.EMCDataType;
import emc.datatypes.dangerousgoods.declarationlines.Consignee;
import emc.datatypes.dangerousgoods.declarationlines.Consignor;
import emc.datatypes.dangerousgoods.declarationlines.ContractingParty;
import emc.datatypes.dangerousgoods.declarationlines.LineNumber;
import emc.datatypes.dangerousgoods.declarationlines.Operator;
import emc.datatypes.dangerousgoods.declarationlines.ProductCustodian;
import emc.datatypes.dangerousgoods.declarationlines.ProductManufacturer;
import emc.datatypes.dangerousgoods.declarationlines.ProductOwner;
import emc.datatypes.dangerousgoods.declarationlines.foreignkeys.CustomerFK;
import emc.datatypes.dangerousgoods.declarationlines.foreignkeys.DecNumberFK;
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
@Table(name="DGDeclarationLines", uniqueConstraints={@UniqueConstraint(columnNames={"lineNumber"})})
public class DGDeclarationLines extends EMCEntityClass{
    
    private String lineNumber;  //NumberSequence-controlled
    private String consignor;   
    private String consignee;
    private String operator;
    private String productManufacturer;
    private String productOwner;
    private String contractingParty;
    private String productCustodian;
    private String decNumber;
    private String customer;
    
    public DGDeclarationLines()
    {
        
    }
    
    public String getLineNumber()
    {
        return lineNumber;
    }
    
    public void setLineNumber(String lineNumber)
    {
        this.lineNumber = lineNumber;
    }
    
    public String getConsignor()
    {
        return consignor;
    }
    
    public void setConisgnor(String consignor)
    {
        this.consignor = consignor;
    }
    
    public String getConsignee()
    {
        return consignee;
    }
    
    public void setConsignee(String consignee)
    {
        this.consignee = consignee;
    }
    
    public String getOperator()
    {
        return operator;
    }

    public void setOperator(String operator)
    {
        this.operator = operator;
    }
    
    public String getProductManufacturer()
    {
        return productManufacturer;
    }
    
    public void setProductManufacturer(String productManufacturer)
    {
        this.productManufacturer = productManufacturer;
    }
    
    public String getProductOwner()
    {
        return productOwner;
    }
    
    public void setProductOwner(String productOwner)
    {
        this.productOwner = productOwner;
    }
    
    public String getContractingParty()
    {
        return contractingParty;
    }
    
    public void setContractingParty(String contractingParty)
    {
        this.contractingParty = contractingParty;
    }
    
    public String getProductCustodian()
    {
        return productCustodian;
    }
    
    public void setProductCustodian(String productCustodian)
    {
        this.productCustodian = productCustodian;
    }
    
    public String getCustomer()
    {
        return customer;
    }
    
    public void setCustomer(String customer)
    {
        this.customer = customer;
    }
    
    //In case we want to add to the query
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
        fields.add("lineNumber");
        fields.add("consignor");
        fields.add("consignee");
        fields.add("operator");
        fields.add("productManufacturer");
        fields.add("productOwner");
        fields.add("contractingParty");
        fields.add("productCustodian");
        return fields;
    }
    
    @Override
    protected HashMap<String, EMCDataType> buildFieldList()
    {
        HashMap ret = super.buildFieldList();
        ret.put("lineNumber", new LineNumber());
        ret.put("consignor", new Consignor());
        ret.put("consignee", new Consignee());
        ret.put("operator", new Operator());
        ret.put("productMaufacturer", new ProductManufacturer());
        ret.put("productOwner", new ProductOwner());
        ret.put("contractingParty", new ContractingParty());
        ret.put("productCustodian", new ProductCustodian());
        ret.put("customer", new CustomerFK());
        ret.put("decNumber", new DecNumberFK());
        return ret;
    }
}
