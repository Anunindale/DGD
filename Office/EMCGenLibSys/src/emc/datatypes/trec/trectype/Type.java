/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.trec.trectype;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class Type extends EMCString{

    public Type() {
        this.setEmcLabel("Type");
        this.setMandatory(true);
    }

}
