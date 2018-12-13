/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.journals.journallines;

import emc.datatypes.EMCString;

/**
 *
 * @author rico
 */
public class JRLineText extends EMCString {
    public JRLineText(){
        this.setEmcLabel("Journal Line Text");
        this.setStringSize(1000);
    }
}
