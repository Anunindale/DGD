/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.unitsofmeasure.foreignkeys;

import emc.datatypes.base.unitsofmeasure.UnitOfMeasureNoFK;
import emc.entity.base.BaseUnitsOfMeasure;

/**
 *
 * @author riaan
 */
public class UnitOfMeasureFK extends UnitOfMeasureNoFK{

    public UnitOfMeasureFK(){
        this.setEmcLabel("UoM");
        this.setMandatory(true);
        this.setRelatedTable(BaseUnitsOfMeasure.class.getName());
        this.setRelatedField("unit");
        this.setColumnWidth(36);
    }

}
