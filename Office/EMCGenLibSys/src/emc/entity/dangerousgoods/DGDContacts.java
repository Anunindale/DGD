/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.dangerousgoods;

import emc.datatypes.EMCDataType;
import emc.datatypes.dangerousgoods.contacts.Company;
import emc.datatypes.dangerousgoods.contacts.ContactName;
import emc.datatypes.dangerousgoods.contacts.ContactNumber;
import emc.datatypes.dangerousgoods.contacts.foreignkeys.CustomerFK;
import emc.datatypes.dangerousgoods.contacts.foreignkeys.PostalCodeFK;
import emc.datatypes.dangerousgoods.contacts.Type;
import emc.datatypes.systemwide.PhysicalAddress1;
import emc.datatypes.systemwide.PhysicalAddress2;
import emc.datatypes.systemwide.PhysicalAddress3;
import emc.datatypes.systemwide.PhysicalAddress4;
import emc.datatypes.systemwide.PhysicalAddress5;
import emc.datatypes.systemwide.Telephone;
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
@Table(name="DGDContacts", uniqueConstraints={@UniqueConstraint(columnNames={"contactNumber", "companyId"})})
public class DGDContacts extends EMCEntityClass {
    
    private String contactNumber;     //NumberSequence-controlled number
    private String customer;          //From SOPCustomers
    private String type;              //ContactType enum
    private String contactName;         
    private String company;           //Company the contact works for
    private String physicalAddress1;  //Company(contact) address
    private String physicalAddress2;
    private String physicalAddress3;
    private String physicalAddress4;
    private String physicalAddress5;
    private String postalCode;
    private String telephone;         //Contact telephone number
    
    public DGDContacts()
    {
        
    }
    
    public String getContactNumber()
    {
        return contactNumber;
    }
    
    public void setContactNumber(String contactNumber)
    {
        this.contactNumber = contactNumber;
    }
    
    public String getCustomer()
    {
        return customer;
    }
    
    public void setCustomer(String customer)
    {
        this.customer = customer;
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public String getContactName()
    {
        return contactName;
    }
    
    public void setContactName(String contactName)
    {
        this.contactName = contactName;
    }
    
    public String getCompany()
    {
        return company;
    }
    
    public void setCompany(String company)
    {
        this.company = company;
    }
    
    public String getPhysicalAddress1()
    {
        return physicalAddress1;
    }
    
    public void setPhysicalAddress1(String physicalAddress1)
    {
        this.physicalAddress1 = physicalAddress1;
    }
    
    public String getPhysicalAddress2()
    {
        return physicalAddress2;
    }
    
    public void setPhysicalAddress2(String physicalAddress2)
    {
        this.physicalAddress2 = physicalAddress2;
    }
    public String getPhysicalAddress3()
    {
        return physicalAddress3;
    }
    
    public void setPhysicalAddress3(String physicalAddress3)
    {
        this.physicalAddress3 = physicalAddress3;
    }
    public String getPhysicalAddress4()
    {
        return physicalAddress4;
    }
    
    public void setPhysicalAddress4(String physicalAddress4)
    {
        this.physicalAddress4 = physicalAddress4;
    }
    
    public String getPhysicalAddress5()
    {
        return physicalAddress5;
    }
    
    public void setPhysicalAddress5(String physicalAddress5)
    {
        this.physicalAddress5 = physicalAddress5;
    }
    
    public String getPostalCode()
    {
        return postalCode;
    }
    
    public void setPostalCode(String postalCode)
    {
        this.postalCode = postalCode;
    }
    
    public String getTelephone()
    {
        return telephone;
    }
    
    public void setTelephone(String telephone)
    {
        this.telephone = telephone;
    }
    
    //In case we ever want to add anything to the query
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
        fields.add("contactNumber");
        fields.add("contactName");
        fields.add("company");
        return fields;
    }
    
    @Override
    protected HashMap<String, EMCDataType> buildFieldList()
    {
        HashMap ret = super.buildFieldList();
        ret.put("contactNumber", new ContactNumber());
        ret.put("customer", new CustomerFK());
        ret.put("type", new Type());
        ret.put("contactName", new ContactName());
        ret.put("company", new Company());
        ret.put("physicalAddress1", new PhysicalAddress1());
        ret.put("physicalAddress2", new PhysicalAddress2());
        ret.put("physicalAddress3", new PhysicalAddress3());
        ret.put("physicalAddress4", new PhysicalAddress4());
        ret.put("physicalAddress5", new PhysicalAddress5());
        ret.put("postalCode", new PostalCodeFK());
        ret.put("telephone", new Telephone());
        return ret;
    }
}
