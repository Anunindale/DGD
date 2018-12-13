/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.trec.chemicals.foreignkey;

import emc.datatypes.trec.chemicals.UNNumber;
import emc.entity.trec.TRECChemicals;

/**
 *
 * @author wikus
 */
public class UNNumberFKNM extends UNNumber {

    public UNNumberFKNM() {
        this.setRelatedTable(TRECChemicals.class.getName());
    	this.setColumnWidth(39);
        this.setRelatedField("unNumber");
        this.setMandatory(false);
    }
}
