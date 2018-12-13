/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.hr.absenteeismtype.datasource;

import emc.datatypes.hr.absenteeismtype.AbsenteeismType;
import emc.entity.hr.HRAbsenteeismType;

/**
 *
 * @author wikus
 */
public class AbsenteeismTypeFK extends AbsenteeismType {

    public AbsenteeismTypeFK() {
        this.setRelatedTable(HRAbsenteeismType.class.getName());
        this.setRelatedField("absenteeismType");
    }

}
