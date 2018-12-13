/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.gl.glaccount;

import emc.datatypes.EMCString;

/**
 *
 * @author Marius-Gert Coetzee
 */
public class GLAccountNum extends EMCString {
    
    /** Creates a new instance of Comments */
    public GLAccountNum() {
        this.setEmcLabel("Account Num");
        this.setColumnWidth(20);
        this.setMandatory(true);
    }

}
