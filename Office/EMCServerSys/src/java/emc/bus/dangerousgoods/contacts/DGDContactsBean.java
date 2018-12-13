/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.dangerousgoods.contacts;

import emc.entity.dangerousgoods.DGDContacts;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import javax.ejb.Stateless;

/**
 *
 * @author pj
 */
@Stateless
public class DGDContactsBean extends EMCEntityBean implements DGDContactsLocal{
    public DGDContactsBean()
    {
        
    }
    
@Override
/**
 * Return the Company Name of the company with the given number - which corresponds to contactNumber in DGDContacts
 */
public String findCompanyByNumber(String numberSeq, EMCUserData userData)
    {
       EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DGDContacts.class);
       query.addAnd("contactNumber", numberSeq);
       
       DGDContacts result = (DGDContacts) util.executeSingleResultQuery(query, userData);
       
       return result.getCompany(); 
    }
}
