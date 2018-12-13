/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.trec.phrasetypes;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class PhraseType extends EMCString{

    public PhraseType() {
        this.setEmcLabel("Type");
        this.setMandatory(true);
    }

}
