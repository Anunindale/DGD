/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.dangerousgoods.vehicles;

import emc.datatypes.dangerousgoods.contacts.ContactNumber;
import emc.entity.dangerousgoods.DGDContacts;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author pj
 */
public class ContactNumberFK extends ContactNumber{
    public ContactNumberFK()
    {
        this.setRelatedTable(DGDContacts.class.getName());
        this.setRelatedField("contactNumber");
        this.setEmcLabel("Contact Number");
        this.setNumberSeqAllowed(true);
        this.setMandatory(true);
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
        this.setDeleteAction(enumDeleteUpdateOptions.CASCADE);
    }
    
}
