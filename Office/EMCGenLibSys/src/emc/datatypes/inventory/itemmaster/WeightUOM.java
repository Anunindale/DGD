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
public class WeightUOM extends UnitOfMeasureFKNotMandatory {
    public WeightUOM(){
        this.setEmcLabel("Weight UOM");
    }
}