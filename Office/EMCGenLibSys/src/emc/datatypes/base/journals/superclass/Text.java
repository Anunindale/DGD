/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.journals.superclass;

import emc.datatypes.EMCString;

/**
 * @description : Data type for text on DebtorsJournalMaster.
 *
 * @date        : 03 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class Text extends EMCString {

    /** Creates a new instance of Text */
    public Text() {
        this.setEmcLabel("Text");
        this.setStringSize(1000);
    }
}
