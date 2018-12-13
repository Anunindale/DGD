/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.sop.postsetup.foreignkeys;

import emc.datatypes.sop.postsetup.PostSetupId;
import emc.entity.sop.SOPSalesOrderPostSetup;

/**
 *
 * @author wikus
 */
public class PostSetupIdFK extends PostSetupId {

    public PostSetupIdFK() {
        this.setRelatedTable(SOPSalesOrderPostSetup.class.getName());
        this.setRelatedField("postSetupId");
        this.setNumberSeqAllowed(false);
    }
}
