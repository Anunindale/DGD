/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.crm.interestgroup;

import emc.datatypes.EMCString;

/**
 *
 * @author rico
 */
public class InterestGroup extends EMCString{
    public InterestGroup(){
        this.setEmcLabel("Group Code");
        this.setMandatory(true);
    }
}
