/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.royaltygroups.foreignkeys;

import emc.datatypes.debtors.royaltygroups.RoyaltyGroupId;
import emc.entity.debtors.DebtorsRoyaltyGroups;

/**
 *
 * @author riaan
 */
public class RoyaltyGroupIDFK extends RoyaltyGroupId {

    /** Creates a new instance of RoyaltyGroupIDFK. */
    public RoyaltyGroupIDFK() {
        this.setRelatedTable(DebtorsRoyaltyGroups.class.getName());
        this.setRelatedField("royaltyGroupId");
    }
}
