/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.pop.costchange;

import emc.entity.pop.costchange.POPCostChangeReason;

/**
 *
 * @author wikus
 */
public class ReasonIdFKNotManditory extends ReasonId{

    public ReasonIdFKNotManditory() {
        this.setEmcLabel("Reason");
        this.setMandatory(false);
        this.setRelatedTable(POPCostChangeReason.class.getName());
        this.setColumnWidth(50);
        this.setRelatedField("reasonId");
    }

    
    
}
