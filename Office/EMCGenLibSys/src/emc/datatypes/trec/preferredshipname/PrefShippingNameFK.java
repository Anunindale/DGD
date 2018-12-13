/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.trec.preferredshipname;

import emc.datatypes.trec.treccards.*;
import emc.datatypes.EMCString;
import emc.datatypes.trec.chemicals.ShippingName;
import emc.entity.trec.TRECChemicals;

/**
 *
 * @author wikus
 */
public class PrefShippingNameFK extends EMCString {

    public PrefShippingNameFK() {
        this.setMandatory(false);
    	this.setColumnWidth(173);
        this.setEmcLabel("Pref Ship Name");
    }
}
