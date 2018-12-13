/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.dangerousgoods;

/**
 *
 * @author pj
 */
public enum ContactType {
    OPERATOR(0, "Operator"),
    CONSIGNOR(1, "Consignor"),
    CONSIGNEE(2, "Consignee"),
    PRODUCT_MANUFACTURER(3, "Product manufacturer"),
    PRODUCT_OWNER(4, "Product owner"),
    PRODUCT_CUSTODIAN(5, "Product custodian"),
    PARTY_CONTRACTING_THE_OPERATOR(6, "Party contracting the operator");

    final int id;
    final String label;
    
    private ContactType(int id, String label) 
    {
        this.id = id;
        this.label = label;
    }
    
    public int getId()
    {
        return id;
    }
    
    @Override
    public String toString()
    {
        return label;
    }
    
    public static ContactType fromString(final String s)
    {
       for(ContactType i : ContactType.values())
       {
           if(i.toString().equalsIgnoreCase(s))
           {
               return i;
           }
       }
       return null;
    }
    
    public static ContactType fromId(final int id)
    {
        for(ContactType i : ContactType.values())
        {
            if(i.id == id)
            {
                return i;
            }
        }
        return null;
    }
    
}
