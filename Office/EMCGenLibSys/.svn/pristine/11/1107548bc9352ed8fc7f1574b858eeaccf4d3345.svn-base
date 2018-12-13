/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.trec.treccards.foreignkey;

import emc.datatypes.trec.treccards.TrecCompanyName;
import emc.entity.trec.TRECTrecCardsMaster;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author wikus
 */
public class TrecCompanyNameFK extends TrecCompanyName {

    public TrecCompanyNameFK() {
        this.setRelatedTable(TRECTrecCardsMaster.class.getName());
        this.setRelatedField("trecCompanyName");
        this.setDeleteAction(enumDeleteUpdateOptions.CASCADE);
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
    }
}
