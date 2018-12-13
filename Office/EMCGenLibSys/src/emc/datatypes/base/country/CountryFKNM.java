/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.country;

import emc.entity.base.BaseCountries;

/**
 *
 * @author wikus
 */
public class CountryFKNM extends Country{

    public CountryFKNM() {
        this.setMandatory(false);
        this.setRelatedTable(BaseCountries.class.getName());
        this.setRelatedField("Code");
    }
    

}
