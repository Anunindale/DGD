/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.crm.interestgroup.foreignkey;

import emc.datatypes.crm.interestgroup.InterestGroup;
import emc.entity.crm.CRMInterestGroup;

/**
 *
 * @author rico
 */
public class InterestGroupFK extends InterestGroup{
    public InterestGroupFK(){
        this.setRelatedTable(CRMInterestGroup.class.getName());
        this.setRelatedField("interestGroupId");
    }
}
