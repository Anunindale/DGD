/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.dimensions.foreignkeys;

import emc.datatypes.systemwide.Dimention3Interface;

/**
 *
 * @author riaan
 */
public class Dimension3FKNotMandatory extends Dimension3FK implements Dimention3Interface{

    /** Creates a new instance of Dimension3FKNotMandatory */
    public Dimension3FKNotMandatory() {
        this.setMandatory(false);
        this.setColumnWidth(30);
    }
}
