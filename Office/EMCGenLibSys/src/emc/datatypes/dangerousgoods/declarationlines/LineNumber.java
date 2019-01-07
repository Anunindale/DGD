/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.dangerousgoods.declarationlines;

import emc.datatypes.EMCString;

/**
 *
 * @author pj
 */
public class LineNumber extends EMCString{
    public LineNumber()
    {
        this.setEmcLabel("Line Number");
        this.setMandatory(true);
        this.setNumberSeqAllowed(true);
    }
}
