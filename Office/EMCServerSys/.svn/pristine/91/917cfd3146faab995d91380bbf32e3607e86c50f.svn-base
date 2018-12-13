/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.requirementsplanning;

import emc.entity.inventory.datasource.InventoryReqPlanAudit;
import emc.entity.inventory.requirementsplanning.InventoryRequirementsPlanning;
import emc.entity.pop.POPPurchaseOrderLines;
import emc.entity.pop.planning.POPPlannedPurchaseOrders;
import emc.entity.sop.SOPSalesOrderLines;
import emc.entity.sop.SOPSalesOrderMaster;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.requirementsplanning.ReqPlanAuditType;
import emc.enums.inventory.requirementsplanning.RequirementsPlanningReferenceTypes;
import emc.enums.sop.SalesOrderTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class ReqPlanAuditBean extends EMCEntityBean implements ReqPlanAuditLocal {

    @Override
    public String getNumRows(Class theTable, EMCUserData userData) {
        return ReqPlanAuditType.values().length + ", 0";
    }

    @Override
    public Collection getDataInRange(Class theTable, EMCUserData userData, int start, int end) {
        List<InventoryReqPlanAudit> dataList = new ArrayList<InventoryReqPlanAudit>();
        InventoryReqPlanAudit auditRec;
        EMCQuery query;

        Map<String, Object[]> auditMap = checkRequirementsPlanning(null, userData);
        Object[] audit = null;

        if (auditMap == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to execute audit queries.", userData);
        }

        for (ReqPlanAuditType type : ReqPlanAuditType.values()) {
            if (auditMap != null) {
                audit = auditMap.get(type.toString());
            }

            auditRec = new InventoryReqPlanAudit();
            auditRec.setRecordType(type.toString());

            if (audit != null) {
                if (audit[0] != null) {
                    auditRec.setSourceCount(new BigDecimal((Long) audit[0]));
                }
                if (audit[1] != null) {
                    auditRec.setSourceSum(audit[1] instanceof BigDecimal ? (BigDecimal) audit[1] : util.getBigDecimal((Double) audit[1]));
                }
                if (audit[2] != null) {
                    auditRec.setReqPlanCount(new BigDecimal((Long) audit[2]));
                }
                if (audit[3] != null) {
                    auditRec.setReqPlanSum(audit[3] instanceof BigDecimal ? (BigDecimal) audit[3] : util.getBigDecimal((Double) audit[3]));
                }
            }

            dataList.add(auditRec);
        }

        return dataList;
    }

    @Override
    public Map<String, Object[]> checkRequirementsPlanning(Connection conn, EMCUserData userData) {
        Map<String, Object[]> checkingResults = new HashMap<String, Object[]>();
        EMCQuery query;
        String requirementsAlias;
        String sourceAlias;
        List<Object[]> selectedList;
        Object[] selectedValues;

        boolean closeConn = false;

        if (conn == null) {
            try {
                conn = util.connectToDatabase(userData);
            } catch (EMCEntityBeanException ex) {
                return null;
            }

            closeConn = true;
        }

     

        //Sales Orders
        //SELECT COUNT(DISTINCT s.recordID) AS COUNT_S, SUM(s.quantity) QTY_S, COUNT(DISTINCT p.recordID) AS COUNT_R, SUM(p.demandQuantity) AS QRT_R  FROM InventoryRequirementsPlanning p LEFT OUTER JOIN SOPSalesOrderLines s ON (p.referenceRecordID = s.recordID), SOPSalesOrderMaster m WHERE s.salesOrderNo = m.salesOrderNo and p.referenceRecordType = 1 and m.salesOrderType = 'Sales Order';
        query = new EMCQuery(enumQueryTypes.SELECT, InventoryRequirementsPlanning.class);
        query.addLeftOuterJoin(InventoryRequirementsPlanning.class, "referenceRecordID", SOPSalesOrderLines.class, "recordID");
        query.addTableAnd(SOPSalesOrderMaster.class.getName(), "salesOrderNo", SOPSalesOrderLines.class.getName(), "salesOrderNo");
        query.addAnd("referenceRecordType", RequirementsPlanningReferenceTypes.SALES_ORDER.getId(), InventoryRequirementsPlanning.class.getName());
        query.addAnd("salesOrderType", SalesOrderTypes.SALES_ORDER.toString(), SOPSalesOrderMaster.class.getName());
        requirementsAlias = query.getTableAlias(InventoryRequirementsPlanning.class);
        sourceAlias = query.getTableAlias(SOPSalesOrderLines.class);
        query.addCustomField("COUNT(DISTINCT " + sourceAlias + ".recordID)", false);
        query.addCustomField("SUM(ROUND(" + sourceAlias + ".quantity, 5))", false);
        query.addCustomField("COUNT(DISTINCT " + requirementsAlias + ".recordID)", false);
        query.addCustomField("SUM(ROUND(" + requirementsAlias + ".demandQuantity, 5))", false);
        try {
            selectedList = util.exJDBCFieldReadQuery(conn, query, userData);
            if (selectedList != null && !selectedList.isEmpty()) {
                selectedValues = selectedList.get(0);
                checkingResults.put(ReqPlanAuditType.SALES_ORDERS.toString(), selectedValues);
            }
        } catch (EMCEntityBeanException ex) {
        }

        //Purchase Orders
        //SELECT COUNT(DISTINCT s.recordID) AS COUNT_S, SUM(s.quantity - s.itemsReceived) QTY_S, COUNT(DISTINCT p.recordID) AS COUNT_R, SUM(p.supplyQuantity) AS QRT_R  FROM InventoryRequirementsPlanning p LEFT OUTER JOIN POPPurchaseOrderLines s ON (p.referenceRecordID = s.recordID) WHERE p.referenceRecordType = 2;
        query = new EMCQuery(enumQueryTypes.SELECT, InventoryRequirementsPlanning.class);
        query.addLeftOuterJoin(InventoryRequirementsPlanning.class, "referenceRecordID", POPPurchaseOrderLines.class, "recordID");
        query.addAnd("referenceRecordType", RequirementsPlanningReferenceTypes.PURCHASE_ORDER.getId(), InventoryRequirementsPlanning.class.getName());
        requirementsAlias = query.getTableAlias(InventoryRequirementsPlanning.class);
        sourceAlias = query.getTableAlias(POPPurchaseOrderLines.class);
        query.addCustomField("COUNT(DISTINCT " + sourceAlias + ".recordID)", false);
        query.addCustomField("SUM(ROUND(" + sourceAlias + ".quantity - " + sourceAlias + ".itemsReceived, 5))", false);
        query.addCustomField("COUNT(DISTINCT " + requirementsAlias + ".recordID)", false);
        query.addCustomField("SUM(ROUND(" + requirementsAlias + ".supplyQuantity, 5))", false);
        try {
            selectedList = util.exJDBCFieldReadQuery(conn, query, userData);
            if (selectedList != null && !selectedList.isEmpty()) {
                selectedValues = selectedList.get(0);
                checkingResults.put(ReqPlanAuditType.PURCHASE_ORDERS.toString(), selectedValues);
            }
        } catch (EMCEntityBeanException ex) {
        }

        //Planned Purchase Orders
        //SELECT COUNT(DISTINCT s.recordID) AS COUNT_S, SUM(s.quantityRequired) QTY_S, COUNT(DISTINCT p.recordID) AS COUNT_R, SUM(p.supplyQuantity) AS QRT_R  FROM InventoryRequirementsPlanning p LEFT OUTER JOIN POPPlannedPurchaseOrders s ON (p.referenceRecordID = s.recordID) WHERE p.referenceRecordType = 3;
        query = new EMCQuery(enumQueryTypes.SELECT, InventoryRequirementsPlanning.class);
        query.addLeftOuterJoin(InventoryRequirementsPlanning.class, "referenceRecordID", POPPlannedPurchaseOrders.class, "recordID");
        query.addAnd("referenceRecordType", RequirementsPlanningReferenceTypes.PLANNED_PURCHASE_ORDER.getId(), InventoryRequirementsPlanning.class.getName());
        requirementsAlias = query.getTableAlias(InventoryRequirementsPlanning.class);
        sourceAlias = query.getTableAlias(POPPlannedPurchaseOrders.class);
        query.addCustomField("COUNT(DISTINCT " + sourceAlias + ".recordID)", false);
        query.addCustomField("SUM(ROUND(" + sourceAlias + ".quantityRequired, 5))", false);
        query.addCustomField("COUNT(DISTINCT " + requirementsAlias + ".recordID)", false);
        query.addCustomField("SUM(ROUND(" + requirementsAlias + ".supplyQuantity, 5))", false);
        try {
            selectedList = util.exJDBCFieldReadQuery(conn, query, userData);
            if (selectedList != null && !selectedList.isEmpty()) {
                selectedValues = selectedList.get(0);
                checkingResults.put(ReqPlanAuditType.PLANNED_PURCHASE_ORDERS.toString(), selectedValues);
            }
        } catch (EMCEntityBeanException ex) {
        }

       
        if (closeConn) {
            if (conn != null) {
                util.closeConnectionToDB(conn, userData);
            }
        }

        return checkingResults;
    }
}
