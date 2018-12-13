/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.trec.treccards;

import emc.datatypes.EMCString;
import emc.datatypes.trec.chemicals.ShippingName;
import emc.entity.trec.TRECChemicals;
import emc.entity.trec.TRECChemicalsSuper;
import emc.entity.trec.TRECPreferredShipName;

/**
 *
 * @author wikus
 */
public class ShippingNameFKM extends ShippingName {

    public ShippingNameFKM() {
        this.setMandatory(true);
        this.setRelatedTable(TRECChemicals.class.getName());
    	this.setColumnWidth(173);
        this.setRelatedField("shippingName");
    }
}
