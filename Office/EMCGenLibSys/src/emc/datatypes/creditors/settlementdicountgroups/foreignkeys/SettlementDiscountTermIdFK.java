/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.creditors.settlementdicountgroups.foreignkeys;

import emc.datatypes.creditors.settlementdicountgroups.SettlementDiscountTermId;
import emc.entity.creditors.CreditorsSettlementDiscountTerms;

/**
 *
 * @author wikus
 */
public class SettlementDiscountTermIdFK extends SettlementDiscountTermId {

    public SettlementDiscountTermIdFK() {
        this.setRelatedTable(CreditorsSettlementDiscountTerms.class.getName());
        this.setRelatedField("settlementDiscountTermId");
    }
}
