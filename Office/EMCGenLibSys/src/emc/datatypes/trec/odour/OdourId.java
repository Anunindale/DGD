/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.trec.odour;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class OdourId extends EMCString{

    public OdourId() {
        this.setEmcLabel("Odour");
        this.setMandatory(true);
        this.setStringSize(1000);
    }

}
