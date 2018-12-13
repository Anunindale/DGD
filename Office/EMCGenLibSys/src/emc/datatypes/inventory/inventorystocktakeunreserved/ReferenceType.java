/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.inventorystocktakeunreserved;

import emc.datatypes.EMCString;

/**
 *
 * @author claudette
 */
public class ReferenceType extends EMCString {

    public ReferenceType() {
        this.setEmcLabel("Ref Type");
        this.setMandatory(true);
    	this.setColumnWidth(60);
        this.setEditable(false);
    }
}
