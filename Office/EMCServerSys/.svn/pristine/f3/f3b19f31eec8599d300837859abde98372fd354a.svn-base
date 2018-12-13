/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.developertools.tasksheets;

import emc.entity.developertools.bugtracking.DevBugsTable;
import emc.enums.developertools.DevBugType;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;

/**
 *
 * @author Brenden
 */
@Stateless
public class TaskSheetsReportsDSBean extends EMCReportBean implements TaskSheetsReportsDSLocal {

    @Override
    protected EMCQuery manipulateQuery(EMCQuery query, EMCUserData userData) {
        return super.manipulateQuery(query, userData);
    }

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, Map<String, Object> paramMap, EMCUserData userData) {

        List reportData = new ArrayList();

        for (Object o : queryResult) {
            DevBugsTable task = (DevBugsTable) o;
            TaskSheetsReportDS ds = new TaskSheetsReportDS();

            ds.setBugNumber(task.getBugNumber());
            ds.setType(convertBugTypes(task.getBugType()));
            ds.setSummary(task.getSummary());
            ds.setAssigned(task.getResponsible());
            ds.setTime(task.getTimeTaken());
            ds.setDate(Functions.date2String(task.getCreatedDate()));
            ds.setClient(task.getClient());

            reportData.add(ds);
        }

        return reportData;
    }

    public String convertBugTypes(String type) {
        String bugtype = null;
        switch (type) {
            case "Admin":
                bugtype = "ADMIN";
                break;
            case "Bug":
                type = "BUG";
                break;
            case  "Development":
                type = "DEV";
                break;
            case "Modification":
                type = "MOD";
                break;
            case "Not Applicable":
                type = "N/A";
                break;
            case "Support Call":
                type = "SUPP";
                break;
            case "Tech":
                type = "TECH";
                break;
        }
        
        
        return type;
    }
}
