/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.hr.disciplinaryaction;

import emc.entity.base.BaseEmployeeTable;
import emc.entity.hr.HRActionResults;
import emc.entity.hr.HRDiciplaneryActions;
import emc.entity.hr.HRDisciplinaryLevel;
import emc.entity.hr.HRRace;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class HRDisciplinaryActionReportBean extends EMCReportBean implements HRDisciplinaryActionReportLocal {

    @Override
    protected EMCQuery manipulateQuery(EMCQuery query, EMCUserData userData) {
        query.addField("employeeNumber", HRDiciplaneryActions.class.getName());//0
        query.addField("forenames", BaseEmployeeTable.class.getName());//1
        query.addField("surname", BaseEmployeeTable.class.getName());//2
        query.addField("gender", BaseEmployeeTable.class.getName());//3
        query.addField("race", BaseEmployeeTable.class.getName());//4
        query.addField("offenceDate", HRDiciplaneryActions.class.getName());//5
        query.addField("offence", HRDiciplaneryActions.class.getName());//6
        query.addField("resultLevel", HRDiciplaneryActions.class.getName());//7
        query.addField("diciplaneryResult", HRDiciplaneryActions.class.getName());//8
        return query;
    }

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        List reportData = new ArrayList();

        HRDisciplinaryActionReportDS ds;

        Object[] selectedData;

        Map<String, String> raceMap = new HashMap<String, String>();
        String race;
        Map<String, String> levelMap = new HashMap<String, String>();
        String level;
        Map<String, String> outcomeMap = new HashMap<String, String>();
        String outcome;

        EMCQuery query;

        for (Object o : queryResult) {
            selectedData = (Object[]) o;
            ds = new HRDisciplinaryActionReportDS();
            ds.setClockNo((String) selectedData[0]);
            ds.setName((String) selectedData[1] + " " + (String) selectedData[2]);
            ds.setGender((String) (isBlank(selectedData[3]) ? "-" : selectedData[3]));
            race = raceMap.get((String) (isBlank(selectedData[4]) ? "-" : selectedData[4]));
            if (race == null) {
                query = new EMCQuery(enumQueryTypes.SELECT, HRRace.class);
                query.addAnd("race", selectedData[4]);
                query.addField("description");
                race = (String) util.executeSingleResultQuery(query, userData);
                if (isBlank(race)) {
                    race = (String) (isBlank(selectedData[4]) ? "-" : selectedData[4]);
                }
                raceMap.put((String) selectedData[4], race);
            }
            ds.setRace(race);
            ds.setActionDate(Functions.date2String((Date) selectedData[5]));
            ds.setType((String) selectedData[6]);
            level = levelMap.get((String) selectedData[7]);
            if (level == null) {
                query = new EMCQuery(enumQueryTypes.SELECT, HRDisciplinaryLevel.class);
                query.addAnd("levelId", selectedData[7]);
                query.addField("description");
                level = (String) util.executeSingleResultQuery(query, userData);
                if (isBlank(level)) {
                    level = (String) selectedData[7];
                }
                levelMap.put((String) selectedData[7], level);
            }
            ds.setLevel(level);
            outcome = outcomeMap.get((String) selectedData[8]);
            if (outcome == null) {
                query = new EMCQuery(enumQueryTypes.SELECT, HRActionResults.class);
                query.addAnd("resultId", selectedData[8]);
                query.addField("description");
                outcome = (String) util.executeSingleResultQuery(query, userData);
                if (isBlank(outcome)) {
                    outcome = (String) selectedData[8];
                }
                outcomeMap.put((String) selectedData[8], outcome);
            }
            ds.setOutcome(outcome);
            reportData.add(ds);
        }
        return reportData;
    }
}
