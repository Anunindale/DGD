/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.ageing;

import emc.datatypes.EMCBoolean;

/**
 *
 * @author rico
 */
public class LastBin extends EMCBoolean{
    public LastBin(){
        this.setEmcLabel("Last Bin");
        this.setColumnWidth(5);
    }
}
