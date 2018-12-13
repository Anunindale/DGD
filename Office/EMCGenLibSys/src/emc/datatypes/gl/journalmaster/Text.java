/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.gl.journalmaster;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class Text extends EMCString{

    public Text() {
        this.setEmcLabel("Text");
        this.setLowerCaseAllowed(true);
        this.setStringSize(1000);
    }

}
