/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.inventory.factoryshop;

import emc.entity.debtors.DebtorsCreditNoteLines;
import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.entity.debtors.DebtorsCustomerInvoiceLines;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.journals.InventoryJournalLines;
import emc.entity.inventory.journals.InventoryJournalMaster;
import emc.enums.debtors.creditnotes.DebtorsCreditNoteReturnOptions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.reporttools.EMCReportConfig;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class InventoryFactoryShopReportBean extends EMCReportBean implements InventoryFactoryShopReportLocal {

    @Override
    public List<Object> getReportResult(List<Object> queryList, EMCReportConfig reportConfig, EMCUserData userData) {
        Map<String, Object> paramMap = reportConfig.getParameters();
        Date fromDate = (Date) paramMap.get("fromDate");
        Date toDate = (Date) paramMap.get("toDate");
        String cageDef = (String) paramMap.get("cageDef");
        String finDef = (String) paramMap.get("finDef");
        String fsCust = (String) paramMap.get("fsCust");

        EMCQuery itemQuery = (EMCQuery) queryList.get(1);

        Map<String, InventoryFactoryShopReportDS> dsMap = new TreeMap<String, InventoryFactoryShopReportDS>();
        InventoryFactoryShopReportDS ds;
        //Populate Issues-Cage, Units Made and Issues-Fin Goods
        List<Object[]> journalData = fetchJournal(itemQuery.copyQuery(), fromDate, toDate, cageDef, finDef, userData);
        for (Object[] journal : journalData) {
            ds = dsMap.get((String) journal[0] + (String) journal[1]);
            if (ds == null) {
                ds = new InventoryFactoryShopReportDS();
                ds.setFromDate(fromDate == null ? "-" : Functions.date2String(fromDate));
                ds.setToDate(toDate == null ? "-" : Functions.date2String(toDate));
                ds.setClassification1((String) journal[0]);
                ds.setProductGroup((String) journal[1]);
            }
            if (!isBlank(cageDef) && cageDef.equals(journal[2])) {
                if (journal[3].toString().substring(1, 2).equalsIgnoreCase("x")) {
                    if (!isBlank(journal[5])) {
                        ds.setUnitsMade(ds.getUnitsMade().add(util.getBigDecimal((Double) journal[4], 2).abs().multiply(new BigDecimal((String) journal[5]))));
                    } else {
                        ds.setUnitsMade(ds.getUnitsMade().add(util.getBigDecimal((Double) journal[4], 2).abs()));
                    }
                } else {
                    if (!isBlank(journal[5])) {
                        ds.setIssuesCage(ds.getIssuesCage().add(util.getBigDecimal((Double) journal[4], 2).abs().multiply(new BigDecimal((String) journal[5]))));
                    } else {
                        ds.setIssuesCage(ds.getIssuesCage().add(util.getBigDecimal((Double) journal[4], 2).abs()));
                    }
                }
            } else if (!isBlank(finDef) && finDef.equals(journal[2])) {
                if (!isBlank(journal[5])) {
                    ds.setIssuesFinGoods(ds.getIssuesFinGoods().add(util.getBigDecimal((Double) journal[4], 2).abs().multiply(new BigDecimal((String) journal[5]))));
                } else {
                    ds.setIssuesFinGoods(ds.getIssuesFinGoods().add(util.getBigDecimal((Double) journal[4], 2).abs()));
                }
            }
            dsMap.put((String) journal[0] + (String) journal[1], ds);
        }
        //Populate Sales and Returns
        List<Object[]> creditInvoiceData = fetchInvoiceCreditNodeData(itemQuery, fromDate, toDate, fsCust, userData);
        for (Object[] invoiceCredit : creditInvoiceData) {
            ds = dsMap.get((String) invoiceCredit[0] + (String) invoiceCredit[1]);
            if (ds == null) {
                ds = new InventoryFactoryShopReportDS();
                ds.setFromDate(fromDate == null ? "-" : Functions.date2String(fromDate));
                ds.setToDate(toDate == null ? "-" : Functions.date2String(toDate));
                ds.setClassification1((String) invoiceCredit[0]);
                ds.setProductGroup((String) invoiceCredit[1]);
            }
            if (invoiceCredit[3].toString().equals(fsCust)) {
                if (!isBlank(invoiceCredit[5])) {
                    ds.setSales(ds.getSales().add(((BigDecimal) invoiceCredit[4]).multiply(new BigDecimal((String) invoiceCredit[5]))));
                } else {
                    ds.setSales(ds.getSales().add((BigDecimal) invoiceCredit[4]));
                }
            }
            if (DebtorsCreditNoteReturnOptions.FSTR.toString().equals((String) invoiceCredit[2])) {
                if (!isBlank(invoiceCredit[5])) {
                    ds.setReturns(ds.getReturns().add(((BigDecimal) invoiceCredit[4]).abs().multiply(new BigDecimal((String) invoiceCredit[5]))));
                } else {
                    ds.setReturns(ds.getReturns().add(((BigDecimal) invoiceCredit[4]).abs()));
                }
            }
            dsMap.put((String) invoiceCredit[0] + (String) invoiceCredit[1], ds);

        }
        return new ArrayList<Object>(dsMap.values());
    }

   

    private List<Object[]> fetchJournal(EMCQuery query, Date fromDate, Date toDate, String cageDef, String finDef, EMCUserData userData) {
        query.addTableAnd(InventoryJournalLines.class.getName(), "itemId", InventoryItemMaster.class.getName(), "itemId");
        query.addTableAnd(InventoryJournalMaster.class.getName(), "journalNumber", InventoryJournalLines.class.getName(), "journalNumber");
        if (!isBlank(cageDef) && !isBlank(finDef)) {
            query.openAndConditionBracket();
            query.addOr("journalDefinitionId", cageDef, InventoryJournalMaster.class.getName());
            query.addOr("journalDefinitionId", finDef, InventoryJournalMaster.class.getName());
            query.closeConditionBracket();
        } else if (!isBlank(cageDef)) {
            query.addAnd("journalDefinitionId", cageDef, InventoryJournalMaster.class.getName());
        } else if (!isBlank(finDef)) {
            query.addAnd("journalDefinitionId", cageDef, InventoryJournalMaster.class.getName());
        }
        if (fromDate == null && toDate == null) {
            query.addAnd("journalPostedDate", null, InventoryJournalMaster.class.getName(), EMCQueryConditions.NOT);
        }
        if (fromDate != null) {
            query.addAnd("journalPostedDate", fromDate, InventoryJournalMaster.class.getName(), EMCQueryConditions.GREATER_THAN_EQ);
        }
        if (toDate != null) {
            query.addAnd("journalPostedDate", toDate, InventoryJournalMaster.class.getName(), EMCQueryConditions.LESS_THAN_EQ);
        }
        query.addField("classificationClassGroup1", InventoryItemMaster.class.getName());
        query.addField("productGroup", InventoryItemMaster.class.getName());
        query.addField("journalDefinitionId", InventoryJournalMaster.class.getName());
        query.addField("itemReference", InventoryItemMaster.class.getName());
        query.addFieldAggregateFunction("quantity", InventoryJournalLines.class.getName(), "SUM");
        query.addField("classificationClassGroup6", InventoryItemMaster.class.getName());
        query.addGroupBy(InventoryJournalMaster.class.getName(), "journalDefinitionId");
        query.addGroupBy(InventoryItemMaster.class.getName(), "itemId");
        return util.executeGeneralSelectQuery(query, userData);
    }

    private List<Object[]> fetchInvoiceCreditNodeData(EMCQuery query, Date fromDate, Date toDate, String fsCust, EMCUserData userData) {
        EMCQuery invoiceQuery = query.copyQuery();
        invoiceQuery.addTableAnd(DebtorsCustomerInvoiceLines.class.getName(), "itemId", InventoryItemMaster.class.getName(), "itemId");
        invoiceQuery.addTableAnd(DebtorsCustomerInvoiceMaster.class.getName(), "invCNNumber", DebtorsCustomerInvoiceLines.class.getName(), "invCNNumber");
        if (!isBlank(fsCust)) {
            invoiceQuery.addAndCommaSeperated("customerNo", fsCust, DebtorsCustomerInvoiceMaster.class.getName(), EMCQueryConditions.EQUALS);
        }
        if (fromDate == null && toDate == null) {
            invoiceQuery.addAnd("postedDate", null, DebtorsCustomerInvoiceMaster.class.getName(), EMCQueryConditions.NOT);
        }
        if (fromDate != null) {
            invoiceQuery.addAnd("postedDate", fromDate, DebtorsCustomerInvoiceMaster.class.getName(), EMCQueryConditions.GREATER_THAN_EQ);
        }
        if (toDate != null) {
            invoiceQuery.addAnd("postedDate", toDate, DebtorsCustomerInvoiceMaster.class.getName(), EMCQueryConditions.LESS_THAN_EQ);
        }
        invoiceQuery.addField("classificationClassGroup1", InventoryItemMaster.class.getName());
        invoiceQuery.addField("productGroup", InventoryItemMaster.class.getName());
        invoiceQuery.addCustomField(" ", true, "Custom_1");
        invoiceQuery.addField("customerNo", DebtorsCustomerInvoiceMaster.class.getName());
        invoiceQuery.addFieldAggregateFunction("quantity", DebtorsCustomerInvoiceLines.class.getName(), "SUM");
        invoiceQuery.addField("classificationClassGroup6", InventoryItemMaster.class.getName());
        invoiceQuery.addGroupBy(DebtorsCustomerInvoiceMaster.class.getName(), "customerNo");
        invoiceQuery.addGroupBy(DebtorsCustomerInvoiceMaster.class.getName(), "returnOption");
        invoiceQuery.addGroupBy(InventoryItemMaster.class.getName(), "itemId");
        List<Object[]> returnData = util.executeGeneralSelectQuery(invoiceQuery, userData);

        EMCQuery creditNoteQuery = query.copyQuery();
        creditNoteQuery.addTableAnd(DebtorsCreditNoteLines.class.getName(), "itemId", InventoryItemMaster.class.getName(), "itemId");
        creditNoteQuery.addTableAnd(DebtorsCreditNoteMaster.class.getName(), "invCNNumber", DebtorsCreditNoteLines.class.getName(), "invCNNumber");
        creditNoteQuery.openAndConditionBracket();
        if (!isBlank(fsCust)) {
            creditNoteQuery.addAndCommaSeperated("customerNo", fsCust, DebtorsCreditNoteMaster.class.getName(), EMCQueryConditions.EQUALS);
        }
        creditNoteQuery.addOr("returnOption", DebtorsCreditNoteReturnOptions.FSTR.toString(), DebtorsCreditNoteMaster.class.getName());
        creditNoteQuery.closeConditionBracket();
        if (fromDate == null && toDate == null) {
            creditNoteQuery.addAnd("postedDate", null, DebtorsCreditNoteMaster.class.getName(), EMCQueryConditions.NOT);
        }
        if (fromDate != null) {
            creditNoteQuery.addAnd("postedDate", fromDate, DebtorsCreditNoteMaster.class.getName(), EMCQueryConditions.GREATER_THAN_EQ);
        }
        if (toDate != null) {
            creditNoteQuery.addAnd("postedDate", toDate, DebtorsCreditNoteMaster.class.getName(), EMCQueryConditions.LESS_THAN_EQ);
        }
        creditNoteQuery.addField("classificationClassGroup1", InventoryItemMaster.class.getName());
        creditNoteQuery.addField("productGroup", InventoryItemMaster.class.getName());
        creditNoteQuery.addField("returnOption", DebtorsCreditNoteMaster.class.getName());
        creditNoteQuery.addField("customerNo", DebtorsCreditNoteMaster.class.getName());
        creditNoteQuery.addFieldAggregateFunction("quantity", DebtorsCreditNoteLines.class.getName(), "SUM");
        creditNoteQuery.addField("classificationClassGroup6", InventoryItemMaster.class.getName());
        creditNoteQuery.addGroupBy(DebtorsCreditNoteMaster.class.getName(), "customerNo");
        creditNoteQuery.addGroupBy(DebtorsCreditNoteMaster.class.getName(), "returnOption");
        creditNoteQuery.addGroupBy(InventoryItemMaster.class.getName(), "itemId");
        returnData.addAll(util.executeGeneralSelectQuery(creditNoteQuery, userData));

        return returnData;
    }
}
