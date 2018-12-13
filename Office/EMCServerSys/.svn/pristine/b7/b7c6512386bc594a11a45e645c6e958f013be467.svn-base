/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.inventory.dimension1;

import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.entity.inventory.dimensions.lines.InventoryDimension1Lines;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.reporttools.EMCReportConfig;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class InventoryDimension1ReportBean extends EMCReportBean implements InventoryDimension1ReportLocal {

    @Override
    public List<Object> getReportResult(List<Object> queryList, EMCReportConfig reportConfig, EMCUserData userData) {
        EMCQuery query = (EMCQuery) queryList.get(1);
        if ((Boolean) reportConfig.getParameters().get("noLines")) {
            EMCQuery innerQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension1Lines.class);
            innerQuery.addField("dimension1Id");

            query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension1.class);
            query.addAnd("dimensionId", innerQuery, EMCQueryConditions.NOT_IN);
            query.addOrderBy("sortCode");
            query.clearFieldList();
            query.addField("dimensionId");
            query.addField("description");
            query.addField("active");
        } else {
            query.addOrderBy("sortCode", InventoryDimension1.class.getName());
            query.addOrderBy("seq", InventoryDimension1Lines.class.getName());
            query.clearFieldList();
            query.addField("dimensionId", InventoryDimension1.class.getName());
            query.addField("description", InventoryDimension1.class.getName());
            query.addField("active", InventoryDimension1.class.getName());
            query.addField("dimension3Id", InventoryDimension1Lines.class.getName());
            query.addField("description", InventoryDimension3.class.getName());
        }
        queryList.set(1, query);
        return getReportResult(queryList, userData);
    }

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        InventoryDimension1ReportDS ds;
        List<Object> reportList = new ArrayList<Object>();
        Object[] reportData;
        for (Object o : queryResult) {
            reportData = (Object[]) o;
            ds = new InventoryDimension1ReportDS();
            ds.setDimension1Id((String) reportData[0]);
            ds.setDimension1Description((String) reportData[1]);
            ds.setActive((Boolean) reportData[2]);
            if (reportData.length > 3) {
                ds.setDimension3Id((String) reportData[3]);
                ds.setDimension3Description((String) reportData[4]);
            }
            reportList.add(ds);
        }
        return reportList;
    }
}
