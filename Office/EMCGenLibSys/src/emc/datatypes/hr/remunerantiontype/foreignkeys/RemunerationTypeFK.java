/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.hr.remunerantiontype.foreignkeys;

import emc.datatypes.hr.remunerantiontype.RemunerationType;
import emc.entity.hr.HRRemunerantionType;

/**
 *
 * @author claudette
 */
public class RemunerationTypeFK extends RemunerationType {

    public RemunerationTypeFK() {
        this.setRelatedTable(HRRemunerantionType.class.getName());
        this.setRelatedField("remunerationType");
        this.setMandatory(false);
    }
}
