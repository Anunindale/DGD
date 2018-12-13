/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.inventorystocktakeunreserved;

import emc.datatypes.inventory.dimensions.foreignkeys.Dimension3FKNotMandatory;

/**
 *
 * @author wikus
 */
public class Dimension3 extends Dimension3FKNotMandatory {

    public Dimension3() {
    	this.setColumnWidth(114);
        this.setEditable(false);
    }
}
