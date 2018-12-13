/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.hr.terminationtype.foreignkey;

import emc.datatypes.hr.terminationtype.TerminationType;
import emc.entity.hr.HRTerminationType;

/**
 *
 * @author wikus
 */
public class TerminationTypeFK extends TerminationType {

    public TerminationTypeFK() {
        this.setRelatedField("terminationType");
        this.setRelatedTable(HRTerminationType.class.getName());
    }
}
