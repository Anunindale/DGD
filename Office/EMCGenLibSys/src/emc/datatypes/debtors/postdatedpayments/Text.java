/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.postdatedpayments;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class Text extends EMCString {

    /** Creates a new instance of Text. */
    public Text() {
        this.setEmcLabel("Text");
        this.setStringSize(1000);
    }
}
