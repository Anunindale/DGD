/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.pop.purchasepostmasterforeignkeys;

import emc.datatypes.pop.purchasepostmaster.PostMasterId;
import emc.entity.pop.posting.POPPurchasePostMaster;

/**
 *
 * @author riaan
 */
public class PostMasterIdFK extends PostMasterId {

    /** Creates a new instance of PostMasterIdFK */
    public PostMasterIdFK() {
        this.setNumberSeqAllowed(false);
        this.setRelatedTable(POPPurchasePostMaster.class.getName());
        this.setRelatedField("postMasterId");
    }
}
