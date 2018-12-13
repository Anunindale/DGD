/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.hr.qualification.foreignkey;

import emc.datatypes.hr.qualification.QualificationId;
import emc.entity.hr.HRQualification;

/**
 *
 * @author wikus
 */
public class QualificationIdFK extends QualificationId {

    public QualificationIdFK() {
        this.setRelatedTable(HRQualification.class.getName());
        this.setRelatedField("qualification");
        this.setMandatory(false);
    }
}
