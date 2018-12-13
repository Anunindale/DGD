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
public class ResolvedBy extends EMCString {

    public ResolvedBy() {
        this.setEmcLabel("Resolved By");
        this.setEditable(false);
    }
}
