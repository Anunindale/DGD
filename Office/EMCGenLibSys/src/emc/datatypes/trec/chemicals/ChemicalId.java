/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.trec.chemicals;

import emc.datatypes.EMCInt;

/**
 *
 * @author wikus
 */
public class ChemicalId extends EMCInt {

    public ChemicalId() {
        this.setEmcLabel("Chemical");
    	this.setColumnWidth(59);
        this.setMandatory(true);
    }
}
