/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.crm.interest.foreignkey;

import emc.datatypes.crm.interest.Interest;
import emc.entity.crm.CRMInterest;

/**
 *
 * @author wikus
 */
public class InterestFKNM extends Interest {

    public InterestFKNM() {
        this.setRelatedField("interest");
        this.setRelatedTable(CRMInterest.class.getName());
        this.setMandatory(false);
    }
}
