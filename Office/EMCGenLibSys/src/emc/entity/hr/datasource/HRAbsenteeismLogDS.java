/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.hr.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.hr.absenteeismlog.AbsentHours;
import emc.entity.hr.HRAbsenteeismLog;
import java.util.HashMap;

/**
 *
 * @author wikus
 */
public class HRAbsenteeismLogDS extends HRAbsenteeismLog {

    private int absentHourse;
    private int absentMinutes;

    public HRAbsenteeismLogDS() {
        this.setDataSource(true);
    }

    public int getAbsentHourse() {
        return absentHourse;
    }

    public void setAbsentHourse(int absentHourse) {
        this.absentHourse = absentHourse;
    }

    public int getAbsentMinutes() {
        return absentMinutes;
    }

    public void setAbsentMinutes(int absentMinutes) {
        this.absentMinutes = absentMinutes;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("absentHourse", new AbsentHours());

        return toBuild;
    }
}
