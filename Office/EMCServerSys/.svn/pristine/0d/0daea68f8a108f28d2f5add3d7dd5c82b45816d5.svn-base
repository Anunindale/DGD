/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.developertools;

import emc.entity.developertools.bugtracking.DevBugsTable;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class BugListReportBean extends EMCReportBean implements BugListReportLocal {

    @Override
    protected EMCQuery manipulateQuery(EMCQuery query, EMCUserData userData) {
        query.addGroupBy(DevBugsTable.class.getName(), "recordID");
        return query;
    }



    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        DevBugsTable bugTable;
        BugListReportDS ds;
        List retList = new ArrayList();

        for (Object o : queryResult) {
            bugTable = (DevBugsTable) o;
            ds = new BugListReportDS();
            ds.setBugNo(bugTable.getBugNumber());
            ds.setBugDesc(bugTable.getBugId());
            ds.setAssignedTo(bugTable.getResponsible());
            ds.setRequiredDate(bugTable.getRequiredDate() == null ? "" : Functions.date2String(bugTable.getRequiredDate(), "dd/MM/yyyy"));
            ds.setPriotity(bugTable.getPriority());
            ds.setEstimatedTime(bugTable.getEstimateTime());
            ds.setCompletedDate(bugTable.getCompleted() == null ? "" : Functions.date2String(bugTable.getCompleted(), "dd/MM/yyyy"));
            ds.setCompletedTime(bugTable.getTimeTaken());
            ds.setBugSummary(bugTable.getSummary());
            ds.setQuotedHours(bugTable.getQuotedHours() == null ? BigDecimal.ZERO : bugTable.getQuotedHours());
            ds.setTaskType(bugTable.getBugType());
            retList.add(ds);
        }
        return retList;
    }
}
