/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.crm.prospect;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class ProspectId extends EMCString {

    public ProspectId() {
        this.setEmcLabel("Prospect");
        this.setMandatory(true);
        this.setNumberSeqAllowed(true);
    }


}
