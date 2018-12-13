/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.trec.treccards.foreignkey;

import emc.datatypes.trec.treccards.MasterId;
import emc.entity.trec.TRECTrecCardsMaster;

/**
 *
 * @author wikus
 */
public class MasterIdFK extends MasterId {

    public MasterIdFK() {
        this.setRelatedField("masterId");
        this.setRelatedTable(TRECTrecCardsMaster.class.getName());
        this.setNumberSeqAllowed(false);
    }
}
