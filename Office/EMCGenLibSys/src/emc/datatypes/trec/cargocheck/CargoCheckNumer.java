/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.trec.cargocheck;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class CargoCheckNumer extends EMCString{

    public CargoCheckNumer() {
        this.setEmcLabel("Cargo Number");
        this.setMandatory(true);
        this.setNumberSeqAllowed(true);
    }

}
