/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.dimension3;

import emc.datatypes.EMCString;
import emc.datatypes.systemwide.Dimention3Interface;

/**
 *
 * @author riaan
 */
public class Dimension3 extends EMCString implements Dimention3Interface {

    /** Creates a new instance of Dimension3 */
    public Dimension3() {
        this.setMandatory(true);
        this.setEmcLabel("Colour");
        this.setColumnWidth(100);
    }
}