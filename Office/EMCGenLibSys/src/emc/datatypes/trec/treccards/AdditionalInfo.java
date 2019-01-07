/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.trec.treccards;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class AdditionalInfo extends EMCString {

    public AdditionalInfo() {
        this.setEmcLabel("Additional Info");
        this.setStringSize(2000);
        this.setLowerCaseAllowed(true);
    }
}
