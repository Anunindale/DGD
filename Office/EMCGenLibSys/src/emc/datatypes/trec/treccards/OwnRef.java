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
public class OwnRef extends EMCString{

    public OwnRef() {
        this.setEmcLabel("Own Reference");
        this.setLowerCaseAllowed(true);
        this.setStringSize(255);
    }

}
