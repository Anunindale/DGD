/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.developertools.taskmanagement.requirementssheet;

import emc.entity.developertools.bugtracking.DevBugsTable;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class DevRequirementsSheetBean extends EMCReportBean  implements DevRequirementsSheetLocal {

    @Override
    protected EMCQuery manipulateQuery(EMCQuery query, EMCUserData userData) {
        query.addField("client", DevBugsTable.class.getName());
        query.addField("requiredDate", DevBugsTable.class.getName());
        query.addField("estimateTime", DevBugsTable.class.getName());
        query.addField("bugId", DevBugsTable.class.getName());
        query.addField("possibleAffected", DevBugsTable.class.getName());
        query.addField("possibleAffectedData", DevBugsTable.class.getName());
        query.addField("responsible", DevBugsTable.class.getName());
        query.addField("bugNumber", DevBugsTable.class.getName());
        query.addField("billable", DevBugsTable.class.getName());

        return query;
    }

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, Map<String, Object> paramMap, EMCUserData userData) {
        List reportData = new ArrayList();

        for (Iterator i$ = queryResult.iterator(); i$.hasNext();) {
            Object o = i$.next();
            Object[] selectedData = (Object[]) o;

            DevRequirementsSheetDS ds = new DevRequirementsSheetDS();
            ds.setCurrentDate(Functions.nowDateString("yyyy-MM-dd"));
            ds.setClient((String) selectedData[0]);
            ds.setRequiredDate(selectedData[1] == null ? "" : Functions.date2String((Date) selectedData[1], "yyyy-MM-dd"));
            ds.setEstimatedHours(((Double) selectedData[2]).doubleValue());
            ds.setRequirement((String) selectedData[3]);
            ds.setAffectedAreas((String) selectedData[4]);
            ds.setAffectedData((String) selectedData[5]);
            ds.setDeveloper((String) selectedData[6]);
            ds.setTaskNo((String) selectedData[7]);
            if (((Boolean) selectedData[8]).booleanValue()) {
                ds.setBillable("BILLABLE");
            }

            reportData.add(ds);
        }

        return reportData;
    }

}
