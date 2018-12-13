/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.itemmaster;

import emc.datatypes.base.unitsofmeasure.foreignkeys.UnitOfMeasureFKNotMandatory;


/**
 *
 * @author rico
 */
public class SalesUOM extends UnitOfMeasureFKNotMandatory {
    public SalesUOM(){
        this.setEmcLabel("Sales UOM");
    }
}
