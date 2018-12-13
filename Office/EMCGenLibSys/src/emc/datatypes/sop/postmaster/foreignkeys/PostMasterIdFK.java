/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.sop.postmaster.foreignkeys;

import emc.datatypes.sop.postmaster.PostMasterId;
import emc.entity.sop.SOPSalesOrderPostMaster;

/**
 *
 * @author wikus
 */
public class PostMasterIdFK extends PostMasterId{

    public PostMasterIdFK() {
        this.setRelatedTable(SOPSalesOrderPostMaster.class.getName());
        this.setRelatedField("postMasterId");
        this.setNumberSeqAllowed(false);
    }

}
