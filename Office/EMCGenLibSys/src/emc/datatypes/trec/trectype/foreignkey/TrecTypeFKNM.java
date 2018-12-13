/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.trec.trectype.foreignkey;

import emc.datatypes.trec.trectype.TrecType;
import emc.entity.trec.TRECTrecTypes;

/**
 *
 * @author wikus
 */
public class TrecTypeFKNM extends TrecType {

    public TrecTypeFKNM() {
        this.setRelatedTable(TRECTrecTypes.class.getName());
        this.setRelatedField("trecTypeId");
        this.setMandatory(false);
    }
}
