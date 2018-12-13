/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.pickinglist;

import emc.datatypes.EMCBoolean;

/**
 * @Name         : Cut
 *
 * @Date         : 09 Jul 2009
 * 
 * @Description  : Indicates whether fabric on a Picking List line should be cut.
 *
 * @author       : riaan
 */
public class Cut extends EMCBoolean {

    /** Creates a new instance of Cut. */
    public Cut() {
        this.setEmcLabel("C");
        this.setColumnWidth(20);
        this.setEditable(false);
    }
}
