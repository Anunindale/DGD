/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.debtors.marketinggroup.foreignkeys;

import emc.datatypes.debtors.marketinggroup.MarketingGroup;
import emc.entity.debtors.DebtorsMarketingGroup;

/**
 * @description : Foreign key for marketingGroup on DebtorsMarketingGroup.
 *
 * @date        : 05 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class MarketingGroupFK extends MarketingGroup {

    /** Creates a new instance of MarketingGroupFK */
    public MarketingGroupFK() {
        this.setRelatedTable(DebtorsMarketingGroup.class.getName());
        this.setRelatedField("marketingGroup");
    }
}
