/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.city;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class City extends EMCString{

    public City() {
        this.setEmcLabel("City");
        this.setMandatory(true);
    }

}
