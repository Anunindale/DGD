/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.trec.phrases.foreignkey;

import emc.datatypes.trec.phrases.PhraseId;
import emc.entity.trec.TRECPhrases;

/**
 *
 * @author wikus
 */
public class PhraseIdFKNM extends PhraseId {

    public PhraseIdFKNM() {
        this.setRelatedTable(TRECPhrases.class.getName());
        this.setRelatedField("phraseId");
        this.setMandatory(false);
    }
}
