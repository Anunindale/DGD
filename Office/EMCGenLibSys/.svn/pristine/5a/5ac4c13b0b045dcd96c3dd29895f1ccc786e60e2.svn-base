/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.base.postalcodes;

import emc.datatypes.EMCString;
import emc.entity.base.BaseCountries;

/**
 *
 * @author wikus
 */
public class CountryFK extends EMCString { 

/** Creates a new instance of CountryFK */
public CountryFK() {
this.setEmcLabel("Country");
this.setColumnWidth(50);
this.setRelatedTable(BaseCountries.class.getName());
this.setRelatedField("Code");
this.setMandatory(true);
this.setEditable(true);
this.setStringSize(100);
}
}
