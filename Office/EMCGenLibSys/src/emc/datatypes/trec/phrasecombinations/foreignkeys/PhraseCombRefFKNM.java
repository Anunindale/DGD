/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.trec.phrasecombinations.foreignkeys;

import emc.datatypes.trec.phrasecombinations.PhraseCombRef;
import emc.entity.trec.TRECPhraseCombinations;

/**
 *
 * @author rico
 */
public class PhraseCombRefFKNM extends PhraseCombRef{
    public PhraseCombRefFKNM(){
        this.setRelatedField("phraseRefNum");
        this.setRelatedTable(TRECPhraseCombinations.class.getName());
        this.setMandatory(false);
    }

}
