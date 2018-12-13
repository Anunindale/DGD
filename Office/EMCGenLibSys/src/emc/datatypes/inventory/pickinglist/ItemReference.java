/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.pickinglist;

import emc.datatypes.datasources.inventory.itemPrimaryReferenceDS;

/**
 * @Name         : ItemReference
 *
 * @Date         : 04 Aug 2009
 * 
 * @Description  : Datatype for item reference on Picking List lines.
 *
 * @author       : riaan
 */
public class ItemReference extends itemPrimaryReferenceDS {

    /** Creates a new instance of ItemReference. */
    public ItemReference() {
    	this.setColumnWidth(90);

    }
}
