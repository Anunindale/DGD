/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.itemmaster;

import emc.datatypes.base.unitsofmeasure.foreignkeys.UnitOfMeasureFK;
import emc.datatypes.base.unitsofmeasure.foreignkeys.UnitOfMeasureFKNotMandatory;



/**
 *
 * @author rico
 */
public class LengthUOM extends UnitOfMeasureFKNotMandatory {
    public LengthUOM(){
        this.setEmcLabel("Length UOM");
    }
}