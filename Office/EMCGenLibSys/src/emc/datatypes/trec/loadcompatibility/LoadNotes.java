/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.trec.loadcompatibility;

import emc.datatypes.EMCString;

/**
 *
 * @author rico
 */
public class LoadNotes extends EMCString{
    public LoadNotes(){
        this.setEmcLabel("Notes");
        this.setLowerCaseAllowed(true);
        this.setStringSize(1000);
    }
}
