/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.sop.statusenquiry;

import emc.bus.base.parameters.BaseParametersLocal;
import emc.bus.gl.financialperiods.GLFinancialPeriodsLocal;
import emc.entity.base.BaseParameters;
import emc.entity.debtors.DebtorsCustomerInvoiceLines;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.entity.gl.GLFinancialPeriods;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.sop.SOPCustomers;
import emc.entity.sop.SOPSalesOrderLines;
import emc.entity.sop.SOPSalesOrderMaster;
import emc.entity.sop.SOPStatusEnquirySummary;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.sop.SalesOrderTypes;
import emc.enums.sop.salesorders.SalesOrderStatus;
import emc.enums.sop.statusenquiry.SalesStatusEnquiryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.server.datehandler.EMCDateHandlerLocal;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
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
public class SOPStatusEnquirySummaryBean extends EMCEntityBean implements SOPStatusEnquirySummaryLocal {

    @EJB
    private EMCDateHandlerLocal dateHandler;
    @EJB
    private GLFinancialPeriodsLocal periodsBean;
    @EJB
    private BaseParametersLocal baseParamBean;

    @Override
    public Object validatePeriodSelection(String period, String customerGroup, String customer, EMCUserData userData) {
        GLFinancialPeriods p = periodsBean.findFinancialPeriod(period, userData);
        if (p == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "Invalid period entered.", userData);
            return false;
        }
        Date from = p.getStartDate();
        Date to = p.getEndDate();

        Map<String, List<String>> tableMap = new HashMap<String, List<String>>();
        List<String> tableFiels = null;
        List<String> orderList = new ArrayList<String>();
        double totalDays = dateHandler.getDateDiffInDays(from, to, userData);
        if (totalDays > 35) {
            Logger.getLogger("emc").log(Level.SEVERE, "The total number of days may not be greater than 31.", userData);
            return false;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(from);
        BaseParameters param = baseParamBean.getBaseParameters(userData);
        if (param != null) {
            cal.setFirstDayOfWeek(param.getFirstDayOfWeek());
        }
        for (int i = 0; i < totalDays; i++) {
            tableFiels = tableMap.get("WK " + cal.get(Calendar.WEEK_OF_YEAR));
            if (tableFiels == null) {
                tableFiels = new ArrayList<String>();
            }
            tableFiels.add("quantity" + (i + 1));
            tableMap.put("WK " + cal.get(Calendar.WEEK_OF_YEAR), tableFiels);
            if (!orderList.contains("WK " + cal.get(Calendar.WEEK_OF_YEAR))) {
                orderList.add("WK " + cal.get(Calendar.WEEK_OF_YEAR));
            }
            cal.add(Calendar.DATE, 1);
        }
        try {
            EMCQuery query = new EMCQuery(enumQueryTypes.DELETE, SOPStatusEnquirySummary.class);
            query.addAnd("createdBy", userData.getUserName());
            util.executeUpdate(query, userData);

            populate(from, to, customerGroup, customer, userData);
        } catch (Exception ex) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to populate table", userData);
            ex.printStackTrace();
            return false;
        }
        tableMap.put("Order", orderList);
        return tableMap;
    }

    private void populate(Date from, Date to, String customerGroup, String customer, EMCUserData userData) throws Exception {
        //Fetch Sales Data
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesOrderMaster.class);
        query.addTableAnd(SOPSalesOrderLines.class.getName(), "salesOrderNo", SOPSalesOrderMaster.class.getName(), "salesOrderNo");
        query.addTableAnd(SOPCustomers.class.getName(), "invoiceToCustomerNo", SOPSalesOrderMaster.class.getName(), "customerId");
        query.addTableAnd(InventoryItemMaster.class.getName(), "itemId", SOPSalesOrderLines.class.getName(), "itemId");
        query.addAnd("salesOrderType", SalesOrderTypes.SALES_ORDER.toString(), SOPSalesOrderMaster.class.getName());
        query.addAnd("salsesOrderStatus", SalesOrderStatus.CANCELLED.toString(), SOPSalesOrderMaster.class.getName(), EMCQueryConditions.NOT);

        query.addAnd("requiredDate", from, SOPSalesOrderMaster.class.getName(), EMCQueryConditions.GREATER_THAN_EQ);
        query.addAnd("requiredDate", to, SOPSalesOrderMaster.class.getName(), EMCQueryConditions.LESS_THAN_EQ);
        if (!isBlank(customerGroup)) {
            query.addAnd("marketingGroup", customerGroup, SOPCustomers.class.getName());
        }
        if (!isBlank(customer)) {
            query.addAnd("invoiceToCustomerNo", customer, SOPSalesOrderMaster.class.getName());
        }

        String salesLineAlias = query.getTableAlias(SOPSalesOrderLines.class);

        query.addField("requiredDate", SOPSalesOrderMaster.class.getName());
        query.addField("invoiceToCustomerNo", SOPSalesOrderMaster.class.getName());
        query.addField("customerGroup", SOPCustomers.class.getName());
        query.addCustomField("SUM((" + salesLineAlias + ".quantity * " + salesLineAlias + ".price) - " +
                "((" + salesLineAlias + ".quantity * " + salesLineAlias + ".price) * (" + salesLineAlias + ".discountPerc / 100)))", false);
        query.addFieldAggregateFunction("quantity", SOPSalesOrderLines.class.getName(), "SUM");
        query.addField("classificationClassGroup6", InventoryItemMaster.class.getName());

        query.addGroupBy(SOPSalesOrderMaster.class.getName(), "requiredDate");
        query.addGroupBy(SOPSalesOrderMaster.class.getName(), "invoiceToCustomerNo");

        query.addOrderBy("requiredDate", SOPSalesOrderMaster.class.getName());

        List<Object[]> salesData = util.executeGeneralSelectQuery(query, userData);

        //Older Open Orders
        query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesOrderMaster.class);
        query.addTableAnd(SOPSalesOrderLines.class.getName(), "salesOrderNo", SOPSalesOrderMaster.class.getName(), "salesOrderNo");
        query.addTableAnd(SOPCustomers.class.getName(), "invoiceToCustomerNo", SOPSalesOrderMaster.class.getName(), "customerId");
        query.addTableAnd(InventoryItemMaster.class.getName(), "itemId", SOPSalesOrderLines.class.getName(), "itemId");
        query.addAnd("salesOrderType", SalesOrderTypes.SALES_ORDER.toString(), SOPSalesOrderMaster.class.getName());
        query.addAnd("salsesOrderStatus", SalesOrderStatus.CANCELLED.toString(), SOPSalesOrderMaster.class.getName(), EMCQueryConditions.NOT);

        query.addAnd("requiredDate", from, SOPSalesOrderMaster.class.getName(), EMCQueryConditions.LESS_THAN);
        if (!isBlank(customerGroup)) {
            query.addAnd("marketingGroup", customerGroup, SOPCustomers.class.getName());
        }
        if (!isBlank(customer)) {
            query.addAnd("invoiceToCustomerNo", customer, SOPSalesOrderMaster.class.getName());
        }

        salesLineAlias = query.getTableAlias(SOPSalesOrderLines.class);

        query.addField("invoiceToCustomerNo", SOPSalesOrderMaster.class.getName());
        query.addCustomField("SUM((" + salesLineAlias + ".quantity * " + salesLineAlias + ".price) - " +
                "((" + salesLineAlias + ".quantity * " + salesLineAlias + ".price) * (" + salesLineAlias + ".discountPerc / 100)))", false);
        query.addFieldAggregateFunction("quantity", SOPSalesOrderLines.class.getName(), "SUM");
        query.addField("classificationClassGroup6", InventoryItemMaster.class.getName());

        query.addGroupBy(SOPSalesOrderMaster.class.getName(), "invoiceToCustomerNo");

        List<Object[]> oldSalesData = util.executeGeneralSelectQuery(query, userData);

        //Fetch Invoice Data
        query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesOrderMaster.class);
        query.addTableAnd(SOPSalesOrderLines.class.getName(), "salesOrderNo", SOPSalesOrderMaster.class.getName(), "salesOrderNo");
        query.addTableAnd(SOPCustomers.class.getName(), "invoiceToCustomerNo", SOPSalesOrderMaster.class.getName(), "customerId");
        query.addTableAnd(DebtorsCustomerInvoiceLines.class.getName(), "inventTransId", SOPSalesOrderLines.class.getName(), "inventTransId");
        query.addTableAnd(DebtorsCustomerInvoiceMaster.class.getName(), "invCNNumber", DebtorsCustomerInvoiceLines.class.getName(), "invCNNumber");
        query.addTableAnd(InventoryItemMaster.class.getName(), "itemId", SOPSalesOrderLines.class.getName(), "itemId");
        query.addAnd("salesOrderType", SalesOrderTypes.SALES_ORDER.toString(), SOPSalesOrderMaster.class.getName());

        query.addAnd("postedDate", from, DebtorsCustomerInvoiceMaster.class.getName(), EMCQueryConditions.GREATER_THAN_EQ);
        query.addAnd("postedDate", to, DebtorsCustomerInvoiceMaster.class.getName(), EMCQueryConditions.LESS_THAN_EQ);
        if (!isBlank(customerGroup)) {
            query.addAnd("marketingGroup", customerGroup, SOPCustomers.class.getName());
        }
        if (!isBlank(customer)) {
            query.addAnd("invoiceToCustomerNo", customer, SOPSalesOrderMaster.class.getName());
        }

        String invoiceLineAlias = query.getTableAlias(DebtorsCustomerInvoiceLines.class);

        query.addField("postedDate", DebtorsCustomerInvoiceMaster.class.getName());
        query.addField("invoiceToCustomerNo", SOPSalesOrderMaster.class.getName());
        query.addField("customerGroup", SOPCustomers.class.getName());
        query.addCustomField("SUM((" + invoiceLineAlias + ".quantity * " + invoiceLineAlias + ".unitPrice) - " +
                "((" + invoiceLineAlias + ".quantity * " + invoiceLineAlias + ".unitPrice) * (" + invoiceLineAlias + ".discountPercentage / 100)))", false);
        query.addFieldAggregateFunction("quantity", DebtorsCustomerInvoiceLines.class.getName(), "SUM");
        query.addField("classificationClassGroup6", InventoryItemMaster.class.getName());

        query.addGroupBy(SOPSalesOrderMaster.class.getName(), "requiredDate");
        query.addGroupBy(SOPSalesOrderMaster.class.getName(), "invoiceToCustomerNo");

        List<Object[]> invoiceData = util.executeGeneralSelectQuery(query, userData);

        SOPStatusEnquirySummary invoice;
        SOPStatusEnquirySummary sales;
        SOPStatusEnquirySummary invoiceUnits;
        SOPStatusEnquirySummary salesUnits;
        List<SOPStatusEnquirySummary> summaryList;
        Map<String, List<SOPStatusEnquirySummary>> summaryMap = new HashMap<String, List<SOPStatusEnquirySummary>>();
        double totalDays;
        Field f;
        Field totalF = SOPStatusEnquirySummary.class.getDeclaredField("quantityTotal");
        totalF.setAccessible(true);
        Map<String, List<BigDecimal>> oldDataMap = new HashMap<String, List<BigDecimal>>();
        List<BigDecimal> oldDataList;
        BigDecimal quantity;
        BigDecimal quantity1;
        BigDecimal packs;
        boolean updateTotal;

        for (Object[] data : oldSalesData) {
            //Old Sales Data
            oldDataList = new ArrayList<BigDecimal>();

            quantity = data[1] == null ? BigDecimal.ZERO : (BigDecimal) data[1];
            oldDataList.add(quantity);

            quantity = data[2] == null ? BigDecimal.ZERO : (BigDecimal) data[2];
            try {
                packs = new BigDecimal(Integer.valueOf((String) data[3]));
            } catch (Exception ex) {
                packs = BigDecimal.ZERO;
            }
            oldDataList.add(quantity.multiply(packs));

            oldDataMap.put((String) data[0], oldDataList);
        }

        for (Object[] data : salesData) {
            //Sales Data
            summaryList = summaryMap.get((String) data[1]);
            if (summaryList == null) {
                oldDataList = oldDataMap.remove((String) data[1]);
                if (oldDataList == null) {
                    oldDataList = new ArrayList<BigDecimal>();
                    oldDataList.add(BigDecimal.ZERO);
                    oldDataList.add(BigDecimal.ZERO);
                }

                summaryList = new ArrayList<SOPStatusEnquirySummary>();

                sales = new SOPStatusEnquirySummary();
                sales.setRecordType(SalesStatusEnquiryTypes.OPEN.getId());
                sales.setRecordGroup("Value");
                sales.setCustomerGroup((String) data[2]);
                sales.setCustomerId((String) data[1]);
                sales.setQuantityTotal(oldDataList.get(0));
                summaryList.add(sales);

                invoice = new SOPStatusEnquirySummary();
                invoice.setRecordType(SalesStatusEnquiryTypes.INVOICED.getId());
                invoice.setRecordGroup("Value");
                invoice.setCustomerGroup((String) data[2]);
                invoice.setCustomerId((String) data[1]);
                summaryList.add(invoice);

                salesUnits = new SOPStatusEnquirySummary();
                salesUnits.setRecordType(SalesStatusEnquiryTypes.OPEN.getId());
                salesUnits.setRecordGroup("Units");
                salesUnits.setCustomerGroup((String) data[2]);
                salesUnits.setCustomerId((String) data[1]);
                sales.setQuantityTotal(oldDataList.get(1));
                summaryList.add(salesUnits);

                invoiceUnits = new SOPStatusEnquirySummary();
                invoiceUnits.setRecordType(SalesStatusEnquiryTypes.INVOICED.getId());
                invoiceUnits.setRecordGroup("Units");
                invoiceUnits.setCustomerGroup((String) data[2]);
                invoiceUnits.setCustomerId((String) data[1]);
                summaryList.add(invoiceUnits);
            }

            totalDays = dateHandler.getDateDiffInDays(from, (Date) data[0], userData);

            updateTotal = true;

            while (totalDays < 36) {
                f = SOPStatusEnquirySummary.class.getDeclaredField("quantity" + Double.valueOf(totalDays).intValue());
                f.setAccessible(true);

                sales = summaryList.get(0);

                quantity = data[3] == null ? BigDecimal.ZERO : (BigDecimal) data[3];


                if (updateTotal) {
                    f.set(sales, ((BigDecimal) totalF.get(sales)).add(quantity.add((BigDecimal) f.get(sales))));
                    totalF.set(sales, quantity.add((BigDecimal) totalF.get(sales)));
                } else {
                    f.set(sales, ((BigDecimal) totalF.get(sales)).add((BigDecimal) f.get(sales)));
                }

                salesUnits = summaryList.get(2);

                quantity = data[4] == null ? BigDecimal.ZERO : (BigDecimal) data[4];
                try {
                    packs = new BigDecimal(Integer.valueOf((String) data[5]));
                } catch (Exception ex) {
                    packs = BigDecimal.ZERO;
                }
                if (updateTotal) {
                    f.set(salesUnits, ((BigDecimal) totalF.get(salesUnits)).add(quantity.multiply(packs).add((BigDecimal) f.get(salesUnits))));
                    totalF.set(salesUnits, quantity.multiply(packs).add((BigDecimal) totalF.get(salesUnits)));
                } else {
                    f.set(salesUnits, ((BigDecimal) totalF.get(salesUnits)).add((BigDecimal) f.get(salesUnits)));
                }
                summaryMap.put((String) data[1], summaryList);

                totalDays++;
                updateTotal = false;
            }
        }

        for (Object[] data : invoiceData) {
            //Invoice
            summaryList = summaryMap.get((String) data[1]);
            if (summaryList == null) {
                summaryList = new ArrayList<SOPStatusEnquirySummary>();

                sales = new SOPStatusEnquirySummary();
                sales.setRecordType(SalesStatusEnquiryTypes.OPEN.getId());
                sales.setRecordGroup("Value");
                sales.setCustomerGroup((String) data[2]);
                sales.setCustomerId((String) data[1]);
                summaryList.add(sales);

                invoice = new SOPStatusEnquirySummary();
                invoice.setRecordType(SalesStatusEnquiryTypes.INVOICED.getId());
                invoice.setRecordGroup("Value");
                invoice.setCustomerGroup((String) data[2]);
                invoice.setCustomerId((String) data[1]);
                summaryList.add(invoice);

                salesUnits = new SOPStatusEnquirySummary();
                salesUnits.setRecordType(SalesStatusEnquiryTypes.OPEN.getId());
                salesUnits.setRecordGroup("Units");
                salesUnits.setCustomerGroup((String) data[2]);
                salesUnits.setCustomerId((String) data[1]);
                summaryList.add(salesUnits);

                invoiceUnits = new SOPStatusEnquirySummary();
                invoiceUnits.setRecordType(SalesStatusEnquiryTypes.INVOICED.getId());
                invoiceUnits.setRecordGroup("Units");
                invoiceUnits.setCustomerGroup((String) data[2]);
                invoiceUnits.setCustomerId((String) data[1]);
                summaryList.add(invoiceUnits);

            }
            totalDays = dateHandler.getDateDiffInDays(from, (Date) data[0], userData);

            f = SOPStatusEnquirySummary.class.getDeclaredField("quantity" + Double.valueOf(totalDays).intValue());
            f.setAccessible(true);

            sales = summaryList.get(0);
            invoice = summaryList.get(1);

            quantity = data[3] == null ? BigDecimal.ZERO : (BigDecimal) data[3];

            f.set(invoice, quantity.add((BigDecimal) f.get(invoice)));
            totalF.set(invoice, quantity.add((BigDecimal) totalF.get(invoice)));



            salesUnits = summaryList.get(2);
            invoiceUnits = summaryList.get(3);

            quantity1 = data[4] == null ? BigDecimal.ZERO : (BigDecimal) data[4];
            try {
                packs = new BigDecimal(Integer.valueOf((String) data[5]));
            } catch (Exception ex) {
                packs = BigDecimal.ZERO;
            }

            f.set(invoiceUnits, quantity1.multiply(packs).add((BigDecimal) f.get(invoiceUnits)));
            totalF.set(invoiceUnits, quantity1.multiply(packs).add((BigDecimal) totalF.get(invoiceUnits)));


            updateTotal = true;
            while (totalDays < 36) {
                f = SOPStatusEnquirySummary.class.getDeclaredField("quantity" + Double.valueOf(totalDays).intValue());
                f.setAccessible(true);

                f.set(sales, ((BigDecimal) f.get(sales)).subtract(quantity));
                f.set(salesUnits, ((BigDecimal) f.get(salesUnits)).subtract(quantity1.multiply(packs)));

                if (updateTotal) {
                    totalF.set(sales, ((BigDecimal) totalF.get(sales)).subtract(quantity));
                    totalF.set(salesUnits, ((BigDecimal) totalF.get(salesUnits)).subtract(quantity1.multiply(packs)));
                }

                totalDays++;
                updateTotal = false;
            }

            summaryMap.put((String) data[1], summaryList);
        }

        List insertData = new ArrayList();

        for (List<SOPStatusEnquirySummary> data : summaryMap.values()) {
            insertData.addAll(data);
        }

        util.insertDirect(SOPStatusEnquirySummary.class, insertData, true, true, util.getRecordId(insertData.size(), userData), true, userData);
    }

    @Override
    public String getNumRows(Class theTable, EMCUserData userData) {
        EMCQuery query = (EMCQuery) userData.getUserData(0);
        query.addField("recordID");
        List data = util.executeGeneralSelectQuery(query.toString(), userData);
        return data.size() + ", 0";
    }

    @Override
    public Collection getDataInRange(Class theTable, EMCUserData userData, int start, int end) {
        EMCQuery theQuery = (EMCQuery) userData.getUserData(0);
        String originalQuery = theQuery.toString();
        String query;
        if (originalQuery.contains("GROUP BY")) {
            query = "SELECT new SOPStatusEnquirySummary(recordType, recordGroup,  customerId, customerGroup, SUM(quantity1), SUM(quantity2), " +
                    "SUM(quantity3), SUM(quantity4), SUM(quantity5), SUM(quantity6), SUM(quantity7), SUM(quantity8), SUM(quantity9), SUM(quantity10), " +
                    "SUM(quantity11), SUM(quantity12), SUM(quantity13), SUM(quantity14), SUM(quantity15), SUM(quantity16), SUM(quantity17), SUM(quantity18), " +
                    "SUM(quantity19), SUM(quantity20), SUM(quantity21), SUM(quantity22), SUM(quantity23), SUM(quantity24), SUM(quantity25), SUM(quantity26), " +
                    "SUM(quantity27),  SUM(quantity28), SUM(quantity29), SUM(quantity30), SUM(quantity31), SUM(quantity32), SUM(quantity33), SUM(quantity34), " +
                    "SUM(quantity35), SUM(quantityTotal)) " + originalQuery.substring(originalQuery.indexOf("FROM"));
            userData.setUserData(0, query);
        }
        Collection data = super.getDataInRange(theTable, userData, start, end);
        return data;
    }
}
