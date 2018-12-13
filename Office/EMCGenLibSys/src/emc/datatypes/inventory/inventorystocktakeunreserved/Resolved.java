/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.inventorystocktakeunreserved;

import emc.datatypes.EMCBoolean;

/**
 *
 * @author claudette
 */
public class Resolved extends EMCBoolean {

    public Resolved() {
    	this.setColumnWidth(29);
        this.setEmcLabel("R");
    }
}
