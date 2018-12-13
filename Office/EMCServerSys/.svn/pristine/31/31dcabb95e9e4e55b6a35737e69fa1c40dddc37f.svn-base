/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.sop.rollingsalesreport;

import emc.bus.gl.financialperiods.GLFinancialPeriodsLocal;
import emc.entity.debtors.DebtorsCreditNoteLines;
import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.entity.debtors.DebtorsCustomerInvoiceLines;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.entity.gl.GLFinancialPeriods;
import emc.entity.inventory.InventoryItemMaster;
import emc.enums.debtors.invoicestatus.DebtorsInvoiceStatus;
import emc.enums.emcquery.EMCQueryConditions;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.reporttools.EMCReportConfig;
import emc.server.datehandler.EMCDateHandlerLocal;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class SOPRollingSalesReportBean extends EMCReportBean implements SOPRollingSalesReportLocal {

    @EJB
    private GLFinancialPeriodsLocal periodBean;
    @EJB
    private EMCDateHandlerLocal dateHandlerBean;

    @Override
    public List<Object> getReportResult(List<Object> queryList, EMCReportConfig reportConfig, EMCUserData userData) {
        List reportData = new ArrayList();

        Map<String, Object> paramMap = reportConfig.getParameters();
        String fromPeriodId = (String) paramMap.get("fromPeriod");
        String toPeriodId = (String) paramMap.get("toPeriod");

        if (isBlank(fromPeriodId) || isBlank(toPeriodId)) {
            Logger.getLogger("emc").log(Level.SEVERE, "Please select both the from and to periods on the parameters tab.", userData);
            return reportData;
        }

        List<GLFinancialPeriods> periodList = periodBean.findAllPeriodsBetween(fromPeriodId, toPeriodId, userData);

        EMCQuery query = (EMCQuery) queryList.get(1);
        query.addAnd("status", DebtorsInvoiceStatus.POSTED.toString(), DebtorsCustomerInvoiceMaster.class.getName());
        query.addAnd("invoiceDate", periodList.get(0).getStartDate(), DebtorsCustomerInvoiceMaster.class.getName(), EMCQueryConditions.GREATER_THAN_EQ);
        query.addAnd("invoiceDate", periodList.get(periodList.size() - 1).getEndDate(), DebtorsCustomerInvoiceMaster.class.getName(), EMCQueryConditions.LESS_THAN_EQ);
        query.addField("invoiceDate", DebtorsCustomerInvoiceMaster.class.getName());
        query.addField("classificationClassGroup5", InventoryItemMaster.class.getName());
        String item = query.getTableAlias(InventoryItemMaster.class);
        String invoice = query.getTableAlias(DebtorsCustomerInvoiceLines.class);
        query.addCustomField("SUM(" + invoice + ".quantity * " + item + ".classificationClassGroup6)", false);
        query.addFieldAggregateFunction("totalCost", DebtorsCustomerInvoiceLines.class.getName(), "SUM");
        query.addGroupBy(DebtorsCustomerInvoiceMaster.class.getName(), "invoiceDate");
        query.addGroupBy(InventoryItemMaster.class.getName(), "classificationClassGroup5");

        List<Object[]> invoiceData = util.executeGeneralSelectQuery(query, userData);

        String period;
        SOPRollingSalesReportDS ds;
        Map<String, SOPRollingSalesReportDS> reportDataMap = new HashMap<String, SOPRollingSalesReportDS>();

        for (Object[] data : invoiceData) {
            period = findPeriodForDate((Date) data[0], periodList, userData);
            ds = reportDataMap.get(period);
            if (ds == null) {
                ds = new SOPRollingSalesReportDS();
                ds.setPeriodId(period);
                ds.setFromPeriod(periodList.get(0).getPeriodName());
                ds.setToPeriod(periodList.get(periodList.size() - 1).getPeriodName());
            }
            if ("IMP".equals(data[1])) {
                ds.setTradingUnits(ds.getTradingUnits().add((BigDecimal) data[2]));
                ds.setTradingValue(ds.getTradingValue().add((BigDecimal) data[3]));
            }
            ds.setInvoicedUnits(ds.getInvoicedUnits().add((BigDecimal) data[2]));
            ds.setInvoicedValue(ds.getInvoicedValue().add((BigDecimal) data[3]));

            reportDataMap.put(period, ds);
        }


        String creditNoteQuery = query.toString();
        creditNoteQuery = creditNoteQuery.replaceAll(DebtorsCustomerInvoiceMaster.class.getName(), DebtorsCreditNoteMaster.class.getName());
        creditNoteQuery = creditNoteQuery.replaceAll(DebtorsCustomerInvoiceLines.class.getName(), DebtorsCreditNoteLines.class.getName());

        List<Object[]> creditNoteData = util.executeGeneralSelectQuery(creditNoteQuery, userData);

        for (Object[] data : creditNoteData) {
            period = findPeriodForDate((Date) data[0], periodList, userData);
            ds = reportDataMap.get(period);
            if (ds == null) {
                ds = new SOPRollingSalesReportDS();
                ds.setPeriodId(period);
                ds.setFromPeriod(periodList.get(0).getPeriodName());
                ds.setToPeriod(periodList.get(periodList.size() - 1).getPeriodName());
            }
            if ("IMP".equals(data[1])) {
                ds.setTradingUnits(ds.getTradingUnits().add((BigDecimal)  (data[2] == null ? BigDecimal.ZERO : data[2])));
                ds.setTradingValue(ds.getTradingValue().add((BigDecimal) data[3]));
            }
            ds.setInvoicedUnits(ds.getInvoicedUnits().add((BigDecimal) (data[2] == null ? BigDecimal.ZERO : data[2])));
            ds.setInvoicedValue(ds.getInvoicedValue().add((BigDecimal) data[3]));

            reportDataMap.put(period, ds);
        }

        for (GLFinancialPeriods periodRec : periodList) {
            reportData.add(reportDataMap.get(periodRec.getPeriodName()));
        }

        return reportData;
    }

    private String findPeriodForDate(Date theDate, List<GLFinancialPeriods> periodList, EMCUserData userData) {
        for (GLFinancialPeriods period : periodList) {
            if (dateHandlerBean.compareDatesIgnoreTime(theDate, period.getStartDate(), userData) >= 0 &&
                    dateHandlerBean.compareDatesIgnoreTime(theDate, period.getEndDate(), userData) <= 0) {
                return period.getPeriodName();
            }
        }
        return null;
    }
}
