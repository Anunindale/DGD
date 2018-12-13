/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.hr.union.foreignkeys;

import emc.datatypes.hr.medicalaid.MedicalAid;
import emc.entity.hr.HRUnions;

/**
 *
 * @author wikus
 */
public class UnionFK extends MedicalAid {

    public UnionFK() {
        this.setRelatedTable(HRUnions.class.getName());
        this.setRelatedField("unionId");
        this.setMandatory(false);
    }
}
