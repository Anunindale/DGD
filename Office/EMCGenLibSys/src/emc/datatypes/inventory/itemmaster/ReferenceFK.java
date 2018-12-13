/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.itemmaster;

import emc.datatypes.inventory.itemreference.ItemReferenceId;

/**
 *
 * @author rico
 */
public class ReferenceFK extends ItemReferenceId {
    public ReferenceFK(){
        this.setMandatory(false);
        this.setEmcLabel("Ref");
        this.setMandatory(false);
    }
}
