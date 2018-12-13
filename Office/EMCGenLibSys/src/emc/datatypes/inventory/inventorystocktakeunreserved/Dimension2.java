/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.inventorystocktakeunreserved;

import emc.datatypes.inventory.dimensions.foreignkeys.Dimension2FKNotMandatory;

/**
 *
 * @author wikus
 */
public class Dimension2 extends Dimension2FKNotMandatory {

    public Dimension2() {
    	this.setColumnWidth(73);
        this.setEditable(false);
    }
}
