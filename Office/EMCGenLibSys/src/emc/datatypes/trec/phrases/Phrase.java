/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.trec.phrases;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class Phrase extends EMCString {

    public Phrase() {
        this.setEmcLabel("Phrase");
        this.setStringSize(1000);
        this.setLowerCaseAllowed(true);
        this.setMandatory(true);
        this.setColumnWidth(70);
    }

}
