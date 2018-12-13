/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.pop.pricegroups.foreignkeys;

import emc.datatypes.pop.pricegroups.PriceGroupId;
import emc.entity.pop.POPPriceGroup;

/**
 *
 * @author riaan
 */
public class PriceGroupFK extends PriceGroupId {

    /** Creates a new instance of PriceGroupFK */
    public PriceGroupFK() {
        this.setColumnWidth(15);
        this.setRelatedTable(POPPriceGroup.class.getName());
        this.setRelatedField("priceGroupId");
        this.setMandatory(true);
    }
}
