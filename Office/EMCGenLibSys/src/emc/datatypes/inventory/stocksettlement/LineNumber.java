/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.stocksettlement;

import emc.datatypes.EMCLong;

/**
 *
 * @author rico
 */
public class LineNumber extends EMCLong {
    public LineNumber(){
        this.setMandatory(true);
        this.setEmcLabel("Nr");
        this.setColumnWidth(7);
    }
}
