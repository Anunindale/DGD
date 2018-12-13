/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.inventory.inventorywithnodemandreport;

import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryDimensionTable;
import emc.entity.inventory.requirementsplanning.InventoryRequirementsPlanning;
import emc.entity.inventory.transactions.InventorySummary;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.reporttools.EMCReportConfig;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class InventoryWithNoDemandReportBean extends EMCReportBean implements InventoryWithNoDemandReportLocal {

    @Override
    public List<Object> getReportResult(List<Object> queryList, EMCReportConfig reportConfig, EMCUserData userData) {

        List<Object> reportData = new ArrayList<Object>();
        Connection conn = null;
        try {
            conn = util.connectToDatabase(userData);
            Map<String, Object> reportParameters = reportConfig.getParameters();
            Date fromDate = (Date) reportParameters.get("fromDate");
            Date toDate = (Date) reportParameters.get("toDate");

            EMCQuery query = (EMCQuery) queryList.get(1);

            query.addField("itemGroup", InventoryItemMaster.class.getName());
            query.addField("itemReference", InventoryItemMaster.class.getName());
            query.addField("description", InventoryItemMaster.class.getName());
            query.addField("dimension1", InventorySummary.class.getName());
            query.addField("dimension2", InventorySummary.class.getName());
            query.addField("dimension3", InventorySummary.class.getName());
            query.addFieldAggregateFunction("physicalAvailable", InventorySummary.class.getName(), "SUM");
            query.addField("itemId", InventoryItemMaster.class.getName());

            query.addGroupBy(InventoryItemMaster.class.getName(), "itemId");
            query.addGroupBy(InventorySummary.class.getName(), "dimension1");
            query.addGroupBy(InventorySummary.class.getName(), "dimension2");
            query.addGroupBy(InventorySummary.class.getName(), "dimension3");

            List<Object[]> summaryData = util.exJDBCFieldReadQuery(conn, query, userData);

            InventoryWithNoDemandReportDS ds;
            List<Object[]> selection;

            for (Object[] summary : summaryData) {
                ds = new InventoryWithNoDemandReportDS();
                ds.setItemGroup((String) summary[0]);
                ds.setItemReference((String) summary[1]);
                ds.setItemDescription((String) summary[2]);
                ds.setDimension1((String) summary[3]);
                ds.setDimension2((String) summary[4]);
                ds.setDimension3((String) summary[5]);
                ds.setOnHand(util.getBigDecimal((Double) (summary[6] == null ? 0d : summary[6])));
                if (ds.getOnHand().compareTo(BigDecimal.ZERO) <= 0) {
                    continue;
                }
                //Fetch Demand
                query = new EMCQuery(enumQueryTypes.SELECT, InventoryRequirementsPlanning.class);
                query.addTableAnd(InventoryDimensionTable.class.getName(), "dimensionId", InventoryRequirementsPlanning.class.getName(), "recordID");
                query.addAnd("itemId", (String) summary[7]);
                if (!isBlank(ds.getDimension1())) {
                    query.addAnd("dimension1Id", ds.getDimension1(), InventoryDimensionTable.class.getName());
                }
                if (!isBlank(ds.getDimension2())) {
                    query.addAnd("dimension2Id", ds.getDimension2(), InventoryDimensionTable.class.getName());
                }
                if (!isBlank(ds.getDimension3())) {
                    query.addAnd("dimension3Id", ds.getDimension3(), InventoryDimensionTable.class.getName());
                }
                if (fromDate != null && toDate != null) {
                    query.addAndCommaSeperated("demandDate", Functions.date2SQLString(fromDate) + "~" + Functions.date2SQLString(toDate), InventoryRequirementsPlanning.class.getName(), EMCQueryConditions.EQUALS);
                } else {
                    if (fromDate != null) {
                        query.addAnd("demandDate", fromDate, InventoryRequirementsPlanning.class.getName(), EMCQueryConditions.EQUALS);
                    } else {
                        if (toDate != null) {
                            query.addAnd("demandDate", toDate, InventoryRequirementsPlanning.class.getName(), EMCQueryConditions.EQUALS);
                        }
                    }
                }
                query.addFieldAggregateFunction("demandQuantity", InventoryRequirementsPlanning.class.getName(), "SUM");
                selection = util.exJDBCFieldReadQuery(conn, query, userData);
                if (selection != null && !selection.isEmpty()) {
                    ds.setDemand((BigDecimal) selection.get(0)[0]);
                }
                if (ds.getDemand() == null) {
                    ds.setDemand(BigDecimal.ZERO);
                }
                if (ds.getDemand().compareTo(BigDecimal.ZERO) > 0) {
                    continue;
                }
                //Fetch Supply
                query = new EMCQuery(enumQueryTypes.SELECT, InventoryRequirementsPlanning.class);
                query.addTableAnd(InventoryDimensionTable.class.getName(), "dimensionId", InventoryRequirementsPlanning.class.getName(), "recordID");
                query.addAnd("itemId", (String) summary[7]);
                if (!isBlank(ds.getDimension1())) {
                    query.addAnd("dimension1Id", ds.getDimension1(), InventoryDimensionTable.class.getName());
                }
                if (!isBlank(ds.getDimension2())) {
                    query.addAnd("dimension2Id", ds.getDimension2(), InventoryDimensionTable.class.getName());
                }
                if (!isBlank(ds.getDimension3())) {
                    query.addAnd("dimension3Id", ds.getDimension3(), InventoryDimensionTable.class.getName());
                }
                if (fromDate != null && toDate != null) {
                    query.addAndCommaSeperated("supplyDate", Functions.date2SQLString(fromDate) + "~" + Functions.date2SQLString(toDate), InventoryRequirementsPlanning.class.getName(), EMCQueryConditions.EQUALS);
                } else {
                    if (fromDate != null) {
                        query.addAnd("supplyDate", fromDate, InventoryRequirementsPlanning.class.getName(), EMCQueryConditions.EQUALS);
                    } else {
                        if (toDate != null) {
                            query.addAnd("supplyDate", toDate, InventoryRequirementsPlanning.class.getName(), EMCQueryConditions.EQUALS);
                        }
                    }
                }
                query.addFieldAggregateFunction("supplyQuantity", InventoryRequirementsPlanning.class.getName(), "SUM");
                selection = util.exJDBCFieldReadQuery(conn, query, userData);
                if (selection != null && !selection.isEmpty()) {
                    ds.setSupply((BigDecimal) selection.get(0)[0]);
                }

                if (ds.getSupply() == null) {
                    ds.setSupply(BigDecimal.ZERO);
                }

                ds.setResult(ds.getOnHand().subtract(ds.getDemand()).add(ds.getSupply()));

                reportData.add(ds);
            }
        } catch (Exception ex) {
        } finally {
            if (conn != null) {
                util.closeConnectionToDB(conn, userData);
            }
        }
        return reportData;
    }
}
