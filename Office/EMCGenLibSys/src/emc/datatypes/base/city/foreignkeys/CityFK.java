/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.base.city.foreignkeys;

import emc.datatypes.base.city.City;
import emc.entity.base.BaseCity;

/**
 *
 * @author wikus
 */
public class CityFK extends City {

    public CityFK() {
        this.setRelatedTable(BaseCity.class.getName());
        this.setRelatedField("city");
    }
}
