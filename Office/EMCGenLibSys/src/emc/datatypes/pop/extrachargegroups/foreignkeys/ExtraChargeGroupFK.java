/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.pop.extrachargegroups.foreignkeys;

import emc.datatypes.EMCString;
import emc.datatypes.pop.extrachargegroups.ExtraChargeGroupId;
import emc.entity.pop.POPExtraChargeGroup;

/**
 *
 * @author riaan
 */
public class ExtraChargeGroupFK extends ExtraChargeGroupId {

    /** Creates a new instance of ExtraChargeGroupFK */
    public ExtraChargeGroupFK() {
        this.setRelatedTable(POPExtraChargeGroup.class.getName());
        this.setRelatedField("extraChargeGroupId");
    }
}
