/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.sop.salesbysizeenquiry;

import emc.bus.debtors.parameters.DebtorsParametersLocal;
import emc.entity.debtors.DebtorsCreditNoteLines;
import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.entity.debtors.DebtorsCustomerInvoiceLines;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.entity.debtors.DebtorsParameters;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.dimensions.InventoryDimension2GroupSetup;
import emc.entity.sop.SOPCustomers;
import emc.entity.sop.SOPSalesBySizeEnquiry;
import emc.enums.debtors.invoicestatus.DebtorsInvoiceStatus;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.helpers.sop.SOPSalesBySizeEnquiryHelper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
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
public class SOPSalesBySizeEnquiryBean extends EMCEntityBean implements SOPSalesBySizeEnquiryLocal {

    @EJB
    private DebtorsParametersLocal debtorsParamBean;

    @Override
    public Map populateSalesBySizeEnquiry(SOPSalesBySizeEnquiryHelper helper, EMCUserData userData) {
        List<String> sizeList = populateTempTable(helper, userData);
        if (sizeList == null) {
            return null;
        } else {
            Map quantityColumnsToDisplay = new HashMap();
            List<String> orderList = new ArrayList<String>();

            for (int column = 0; column < sizeList.size(); column++) {
                ArrayList<String> columns = new ArrayList<String>();
                columns.add("percentage" + (column + 1));
                quantityColumnsToDisplay.put(sizeList.get(column), columns);
                orderList.add("percentage" + (column + 1));
            }
            quantityColumnsToDisplay.put("Order", orderList);

            return quantityColumnsToDisplay;
        }
    }

    private List<String> populateTempTable(SOPSalesBySizeEnquiryHelper helper, EMCUserData userData) {
        clearSalesBySize(helper.getUserDataKey(), userData);

        util.executeNativeUpdateInNewTransaction("CREATE TABLE SOP_SBS_Helper_" + helper.getUserDataKey() +
                "(customerId VARCHAR(255), itemId VARCHAR(255), dimension1 VARCHAR(255), dimension2 VARCHAR(255), dimension3 VARCHAR(255), quantity DECIMAL(19, 2), companyId VARCHAR(255))", userData);

        DebtorsParameters debtorsParam = debtorsParamBean.getDebtorsParameters(userData);

        EMCQuery invoiceQuery = new EMCQuery(enumQueryTypes.SELECT, DebtorsCustomerInvoiceLines.class);
        invoiceQuery.addTableAnd(DebtorsCustomerInvoiceMaster.class.getName(), "invCNNumber", DebtorsCustomerInvoiceLines.class.getName(), "invCNNumber");
        invoiceQuery.addAnd("invoiceDate", helper.getFromDate(), DebtorsCustomerInvoiceMaster.class.getName(), EMCQueryConditions.GREATER_THAN_EQ);
        invoiceQuery.addAnd("invoiceDate", helper.getToDate(), DebtorsCustomerInvoiceMaster.class.getName(), EMCQueryConditions.LESS_THAN_EQ);
        invoiceQuery.addAnd("status", DebtorsInvoiceStatus.POSTED.toString(), DebtorsCustomerInvoiceMaster.class.getName(), EMCQueryConditions.EQUALS);
        invoiceQuery.addStringNotBlank("dimension2", DebtorsCustomerInvoiceLines.class.getName(), EMCQueryBracketConditions.AND);
        if (debtorsParam != null && !isBlank(debtorsParam.getDeliveryChargeItem())) {
            invoiceQuery.addAnd("itemId", debtorsParam.getDeliveryChargeItem(), DebtorsCustomerInvoiceLines.class.getName(), EMCQueryConditions.NOT);
        }
        if (!isBlank(helper.getCustomer()) || !isBlank(helper.getCustomerMarketingGroup())) {
            invoiceQuery.addTableAnd(SOPCustomers.class.getName(), "customerNo", DebtorsCustomerInvoiceMaster.class.getName(), "customerId");
            if (!isBlank(helper.getCustomer())) {
                invoiceQuery.addAndCommaSeperated("customerId", helper.getCustomer(), SOPCustomers.class.getName(), EMCQueryConditions.EQUALS);
            }
            if (!isBlank(helper.getCustomerMarketingGroup())) {
                invoiceQuery.addAndCommaSeperated("marketingGroup", helper.getCustomerMarketingGroup(), SOPCustomers.class.getName(), EMCQueryConditions.EQUALS);
            }
        }
        if (!isBlank(helper.getItem()) || !isBlank(helper.getItemGender()) || !isBlank(helper.getItemPlanningGroup()) || !isBlank(helper.getItemProductGroup())) {
            invoiceQuery.addTableAnd(InventoryItemMaster.class.getName(), "itemId", DebtorsCustomerInvoiceLines.class.getName(), "itemId");
            if (!isBlank(helper.getItem())) {
                invoiceQuery.openAndConditionBracket();
                invoiceQuery.addOrCommaSeperated("itemId", helper.getItem(), InventoryItemMaster.class.getName(), EMCQueryConditions.EQUALS);
                invoiceQuery.addOrCommaSeperated("itemReference", helper.getItem(), InventoryItemMaster.class.getName(), EMCQueryConditions.EQUALS);
                invoiceQuery.closeConditionBracket();
            }
            if (!isBlank(helper.getItemGender())) {
                invoiceQuery.addAndCommaSeperated("classificationClassGroup1", helper.getItemGender(), InventoryItemMaster.class.getName(), EMCQueryConditions.EQUALS);
            }
            if (!isBlank(helper.getItemPlanningGroup())) {
                invoiceQuery.addAndCommaSeperated("planningPlanning", helper.getItemPlanningGroup(), InventoryItemMaster.class.getName(), EMCQueryConditions.EQUALS);
            }
            if (!isBlank(helper.getItemProductGroup())) {
                invoiceQuery.addAndCommaSeperated("productGroup", helper.getItemProductGroup(), InventoryItemMaster.class.getName(), EMCQueryConditions.EQUALS);
            }
        }

        invoiceQuery.addField("customerNo", DebtorsCustomerInvoiceMaster.class.getName());
        invoiceQuery.addField("itemId", DebtorsCustomerInvoiceLines.class.getName());
        invoiceQuery.addField("dimension1", DebtorsCustomerInvoiceLines.class.getName());
        invoiceQuery.addField("dimension2", DebtorsCustomerInvoiceLines.class.getName());
        invoiceQuery.addField("dimension3", DebtorsCustomerInvoiceLines.class.getName());
        invoiceQuery.addFieldAggregateFunction("quantity", DebtorsCustomerInvoiceLines.class.getName(), "SUM");
        invoiceQuery.addField("companyId", DebtorsCustomerInvoiceLines.class.getName());

        invoiceQuery.addGroupBy(DebtorsCustomerInvoiceMaster.class.getName(), "customerNo");
        invoiceQuery.addGroupBy(DebtorsCustomerInvoiceLines.class.getName(), "itemId");
        invoiceQuery.addGroupBy(DebtorsCustomerInvoiceLines.class.getName(), "dimension1");
        invoiceQuery.addGroupBy(DebtorsCustomerInvoiceLines.class.getName(), "dimension2");
        invoiceQuery.addGroupBy(DebtorsCustomerInvoiceLines.class.getName(), "dimension3");

        util.executeNativeUpdateInNewTransaction("CREATE VIEW SOP_SBS_Invoices_" + helper.getUserDataKey() + " AS " + invoiceQuery.getNativeQuery(), userData);
        util.executeNativeUpdateInNewTransaction("INSERT INTO SOP_SBS_Helper_" + helper.getUserDataKey() +
                "(customerId, itemId, dimension1, dimension2, dimension3, quantity, companyId) " +
                "SELECT * FROM SOP_SBS_Invoices_" + helper.getUserDataKey(), userData);

        EMCQuery creditNoteQuery = new EMCQuery(enumQueryTypes.SELECT, DebtorsCreditNoteLines.class);
        creditNoteQuery.addTableAnd(DebtorsCreditNoteMaster.class.getName(), "invCNNumber", DebtorsCreditNoteLines.class.getName(), "invCNNumber");
        creditNoteQuery.addAnd("invoiceDate", helper.getFromDate(), DebtorsCreditNoteMaster.class.getName(), EMCQueryConditions.GREATER_THAN_EQ);
        creditNoteQuery.addAnd("invoiceDate", helper.getToDate(), DebtorsCreditNoteMaster.class.getName(), EMCQueryConditions.LESS_THAN_EQ);
        creditNoteQuery.addAnd("status", DebtorsInvoiceStatus.POSTED.toString(), DebtorsCreditNoteMaster.class.getName(), EMCQueryConditions.EQUALS);
        creditNoteQuery.addStringNotBlank("dimension2", DebtorsCreditNoteLines.class.getName(), EMCQueryBracketConditions.AND);
         if (debtorsParam != null && !isBlank(debtorsParam.getDeliveryChargeItem())) {
            creditNoteQuery.addAnd("itemId", debtorsParam.getDeliveryChargeItem(), DebtorsCreditNoteLines.class.getName(), EMCQueryConditions.NOT);
        }
        if (!isBlank(helper.getCustomer()) || !isBlank(helper.getCustomerMarketingGroup())) {
            creditNoteQuery.addTableAnd(SOPCustomers.class.getName(), "customerNo", DebtorsCreditNoteMaster.class.getName(), "customerId");
            if (!isBlank(helper.getCustomer())) {
                creditNoteQuery.addAndCommaSeperated("customerId", helper.getCustomer(), SOPCustomers.class.getName(), EMCQueryConditions.EQUALS);
            }
            if (!isBlank(helper.getCustomerMarketingGroup())) {
                creditNoteQuery.addAndCommaSeperated("marketingGroup", helper.getCustomerMarketingGroup(), SOPCustomers.class.getName(), EMCQueryConditions.EQUALS);
            }
        }
        if (!isBlank(helper.getItem()) || !isBlank(helper.getItemGender()) || !isBlank(helper.getItemPlanningGroup()) || !isBlank(helper.getItemProductGroup())) {
            creditNoteQuery.addTableAnd(InventoryItemMaster.class.getName(), "itemId", DebtorsCreditNoteLines.class.getName(), "itemId");
            if (!isBlank(helper.getItem())) {
                creditNoteQuery.openAndConditionBracket();
                creditNoteQuery.addOrCommaSeperated("itemId", helper.getItem(), InventoryItemMaster.class.getName(), EMCQueryConditions.EQUALS);
                creditNoteQuery.addOrCommaSeperated("itemReference", helper.getItem(), InventoryItemMaster.class.getName(), EMCQueryConditions.EQUALS);
                creditNoteQuery.closeConditionBracket();
            }
            if (!isBlank(helper.getItemGender())) {
                creditNoteQuery.addAndCommaSeperated("classificationClassGroup1", helper.getItemGender(), InventoryItemMaster.class.getName(), EMCQueryConditions.EQUALS);
            }
            if (!isBlank(helper.getItemPlanningGroup())) {
                creditNoteQuery.addAndCommaSeperated("planningPlanning", helper.getItemPlanningGroup(), InventoryItemMaster.class.getName(), EMCQueryConditions.EQUALS);
            }
            if (!isBlank(helper.getItemProductGroup())) {
                creditNoteQuery.addAndCommaSeperated("productGroup", helper.getItemProductGroup(), InventoryItemMaster.class.getName(), EMCQueryConditions.EQUALS);
            }
        }

        creditNoteQuery.addField("customerNo", DebtorsCreditNoteMaster.class.getName());
        creditNoteQuery.addField("itemId", DebtorsCreditNoteLines.class.getName());
        creditNoteQuery.addField("dimension1", DebtorsCreditNoteLines.class.getName());
        creditNoteQuery.addField("dimension2", DebtorsCreditNoteLines.class.getName());
        creditNoteQuery.addField("dimension3", DebtorsCreditNoteLines.class.getName());
        creditNoteQuery.addFieldAggregateFunction("quantity", DebtorsCreditNoteLines.class.getName(), "SUM");
        creditNoteQuery.addField("companyId", DebtorsCreditNoteLines.class.getName());

        creditNoteQuery.addGroupBy(DebtorsCreditNoteMaster.class.getName(), "customerNo");
        creditNoteQuery.addGroupBy(DebtorsCreditNoteLines.class.getName(), "itemId");
        creditNoteQuery.addGroupBy(DebtorsCreditNoteLines.class.getName(), "dimension1");
        creditNoteQuery.addGroupBy(DebtorsCreditNoteLines.class.getName(), "dimension2");
        creditNoteQuery.addGroupBy(DebtorsCreditNoteLines.class.getName(), "dimension3");

        util.executeNativeUpdateInNewTransaction("CREATE VIEW SOP_SBS_CreditNotes_" + helper.getUserDataKey() + " AS " + creditNoteQuery.getNativeQuery(), userData);
        util.executeNativeUpdateInNewTransaction("INSERT INTO SOP_SBS_Helper_" + helper.getUserDataKey() +
                "(customerId, itemId, dimension1, dimension2, dimension3, quantity, companyId) " +
                "SELECT * FROM SOP_SBS_CreditNotes_" + helper.getUserDataKey(), userData);

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, "SOP_SBS_Helper_" + helper.getUserDataKey());
        query.addField("customerId");
        query.addField("itemId");
        query.addField("dimension1");
        query.addField("dimension2");
        query.addField("dimension3");
        query.addFieldAggregateFunction("quantity", "SUM");
        query.addGroupBy("customerId");
        query.addGroupBy("itemId");
        query.addGroupBy("dimension1");
        query.addGroupBy("dimension2");
        query.addGroupBy("dimension3");

        List<Object[]> selectedData = (List<Object[]>) util.executeNativeSelectInNewTx(query, userData);

        if (selectedData.isEmpty()) {
            Logger.getLogger("emc").log(Level.SEVERE, "No data was found for your selection.", userData);
            return null;
        }

        query = new EMCQuery(enumQueryTypes.SELECT, "SOP_SBS_Helper_" + helper.getUserDataKey());
        query.addField("dimension2");
        query.addGroupBy("dimension2");

        List<String> sizeList = (List<String>) util.executeNativeSelectInNewTx(query, userData);

        query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension2.class);
        query.addTableAnd(InventoryDimension2GroupSetup.class.getName(), "dimensionId", InventoryDimension2.class.getName(), "dimensionId");
        query.addAndInList("dimensionId", InventoryDimension2.class.getName(), sizeList, true, false);
        query.addField("dimensionId", InventoryDimension2.class.getName());
        query.addOrderBy("dimensionGroupId", InventoryDimension2GroupSetup.class.getName());
        query.addOrderBy("sortCode", InventoryDimension2.class.getName());
        sizeList = util.executeGeneralSelectQuery(query, userData);

        Map<String, Object[]> customerMap = new HashMap<String, Object[]>();
        Object[] customerInfo;
        Map<String, Object[]> itemInfoMap = new HashMap<String, Object[]>();
        Object[] itemInfo;
        SOPSalesBySizeEnquiry enquiry;
        List<SOPSalesBySizeEnquiry> enquiryList = new ArrayList<SOPSalesBySizeEnquiry>();

        for (Object[] sold : selectedData) {
            if (BigDecimal.ZERO.compareTo(util.roundBigDecimal((BigDecimal) sold[5], 2)) >= 0 || isBlank(sold[3])) {
                continue;
            }

            enquiry = new SOPSalesBySizeEnquiry();
            enquiry.setUserDateId(helper.getUserDataKey());
            enquiry.setCustomerId((String) sold[0]);
            customerInfo = customerMap.get(enquiry.getCustomerId());
            if (customerInfo == null) {
                query = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class);
                query.addAnd("customerId", enquiry.getCustomerId());
                query.addField("customerName");
                query.addField("marketingGroup");
                customerInfo = (Object[]) util.executeSingleResultQuery(query, userData);
                customerMap.put(enquiry.getCustomerId(), customerInfo);
            }
            enquiry.setCustomerName((String) customerInfo[0]);
            enquiry.setCustomerGroup((String) customerInfo[1]);
            enquiry.setItemId((String) sold[1]);
            itemInfo = itemInfoMap.get(enquiry.getItemId());
            if (itemInfo == null) {
                query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class);
                query.addAnd("itemId", enquiry.getItemId());
                query.addField("itemReference");
                query.addField("description");
                query.addField("classificationClassGroup1");
                query.addField("planningPlanning");
                query.addField("productGroup");

                itemInfo = (Object[]) util.executeSingleResultQuery(query, userData);
                itemInfoMap.put(enquiry.getItemId(), itemInfo);
            }
            enquiry.setItemReference((String) itemInfo[0]);
            enquiry.setItemDescription((String) itemInfo[1]);
            enquiry.setItemGender((String) itemInfo[2]);
            enquiry.setItemGroup((String) itemInfo[3]);
            enquiry.setItemProductGroup((String) itemInfo[4]);
            enquiry.setDimension1((String) sold[2]);
            enquiry.setDimension2((String) sold[3]);
            enquiry.setDimension3((String) sold[4]);

            switch (sizeList.indexOf(enquiry.getDimension2())) {
                case 0:
                    enquiry.setQuantity1(enquiry.getQuantity1().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 1:
                    enquiry.setQuantity2(enquiry.getQuantity2().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 2:
                    enquiry.setQuantity3(enquiry.getQuantity3().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 3:
                    enquiry.setQuantity4(enquiry.getQuantity4().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 4:
                    enquiry.setQuantity5(enquiry.getQuantity5().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 5:
                    enquiry.setQuantity6(enquiry.getQuantity6().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 6:
                    enquiry.setQuantity7(enquiry.getQuantity7().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 7:
                    enquiry.setQuantity8(enquiry.getQuantity8().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 8:
                    enquiry.setQuantity9(enquiry.getQuantity9().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 9:
                    enquiry.setQuantity10(enquiry.getQuantity10().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 10:
                    enquiry.setQuantity11(enquiry.getQuantity11().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 11:
                    enquiry.setQuantity12(enquiry.getQuantity12().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 12:
                    enquiry.setQuantity13(enquiry.getQuantity13().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 13:
                    enquiry.setQuantity14(enquiry.getQuantity14().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 14:
                    enquiry.setQuantity15(enquiry.getQuantity15().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 15:
                    enquiry.setQuantity16(enquiry.getQuantity16().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 16:
                    enquiry.setQuantity17(enquiry.getQuantity17().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 17:
                    enquiry.setQuantity18(enquiry.getQuantity18().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 18:
                    enquiry.setQuantity19(enquiry.getQuantity19().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 19:
                    enquiry.setQuantity20(enquiry.getQuantity20().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 20:
                    enquiry.setQuantity21(enquiry.getQuantity21().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 21:
                    enquiry.setQuantity22(enquiry.getQuantity22().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 22:
                    enquiry.setQuantity23(enquiry.getQuantity23().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 23:
                    enquiry.setQuantity24(enquiry.getQuantity24().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 24:
                    enquiry.setQuantity25(enquiry.getQuantity25().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 25:
                    enquiry.setQuantity26(enquiry.getQuantity26().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 26:
                    enquiry.setQuantity27(enquiry.getQuantity27().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 27:
                    enquiry.setQuantity28(enquiry.getQuantity28().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 28:
                    enquiry.setQuantity29(enquiry.getQuantity29().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 29:
                    enquiry.setQuantity30(enquiry.getQuantity30().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 30:
                    enquiry.setQuantity31(enquiry.getQuantity31().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 31:
                    enquiry.setQuantity32(enquiry.getQuantity32().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 32:
                    enquiry.setQuantity33(enquiry.getQuantity33().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 33:
                    enquiry.setQuantity34(enquiry.getQuantity34().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 34:
                    enquiry.setQuantity35(enquiry.getQuantity35().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 35:
                    enquiry.setQuantity36(enquiry.getQuantity36().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 36:
                    enquiry.setQuantity37(enquiry.getQuantity37().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 37:
                    enquiry.setQuantity38(enquiry.getQuantity38().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 38:
                    enquiry.setQuantity39(enquiry.getQuantity39().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 39:
                    enquiry.setQuantity40(enquiry.getQuantity40().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 40:
                    enquiry.setQuantity41(enquiry.getQuantity41().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 41:
                    enquiry.setQuantity42(enquiry.getQuantity42().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 42:
                    enquiry.setQuantity43(enquiry.getQuantity43().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 43:
                    enquiry.setQuantity44(enquiry.getQuantity44().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 44:
                    enquiry.setQuantity45(enquiry.getQuantity45().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 45:
                    enquiry.setQuantity46(enquiry.getQuantity46().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 46:
                    enquiry.setQuantity47(enquiry.getQuantity47().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 47:
                    enquiry.setQuantity48(enquiry.getQuantity48().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 48:
                    enquiry.setQuantity49(enquiry.getQuantity49().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
                case 49:
                    enquiry.setQuantity50(enquiry.getQuantity50().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));
                    break;
            }
            enquiry.setTotalQuantity(enquiry.getTotalQuantity().add(util.roundBigDecimal((BigDecimal) sold[5], 2)));

            enquiryList.add(enquiry);
        }
        util.insertDirect(SOPSalesBySizeEnquiry.class, enquiryList, userData);

        util.executeNativeUpdateInNewTransaction("DROP TABLE SOP_SBS_Helper_" + helper.getUserDataKey(), userData);
        util.executeNativeUpdateInNewTransaction("DROP VIEW SOP_SBS_Invoices_" + helper.getUserDataKey(), userData);
        util.executeNativeUpdateInNewTransaction("DROP VIEW SOP_SBS_CreditNotes_" + helper.getUserDataKey(), userData);

        return sizeList;
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

        String query = "SELECT new SOPSalesBySizeEnquiry(userDateId, customerGroup, customerId, customerName, itemGender, itemGroup, itemProductGroup, itemId, itemReference, itemDescription, dimension1, " +
                "dimension2, dimension3, SUM(totalQuantity), SUM(quantity1), SUM(quantity2), SUM(quantity3), SUM(quantity4), SUM(quantity5), " +
                "SUM(quantity6), SUM(quantity7), SUM(quantity8), SUM(quantity9), SUM(quantity10), SUM(quantity11), SUM(quantity12), SUM(quantity13), " +
                "SUM(quantity14), SUM(quantity15), SUM(quantity16), SUM(quantity17), SUM(quantity18), SUM(quantity19), SUM(quantity20), SUM(quantity21), " +
                "SUM(quantity22), SUM(quantity23), SUM(quantity24), SUM(quantity25), SUM(quantity26), SUM(quantity27), SUM(quantity28), SUM(quantity29), " +
                "SUM(quantity30), SUM(quantity31), SUM(quantity32), SUM(quantity33), SUM(quantity34), SUM(quantity35), SUM(quantity36), SUM(quantity37), " +
                "SUM(quantity38), SUM(quantity39), SUM(quantity40), SUM(quantity41), SUM(quantity42), SUM(quantity43), SUM(quantity44), SUM(quantity45), " +
                "SUM(quantity46), SUM(quantity47), SUM(quantity48), SUM(quantity49), SUM(quantity50)) " +
                originalQuery.substring(originalQuery.indexOf("FROM"));

        userData.setUserData(0, query);

        Collection data = super.getDataInRange(theTable, userData, start, end);
        return data;
    }

    public void clearSalesBySize(String userDataId, EMCUserData userData) {
        EMCQuery deleteQuery = new EMCQuery(enumQueryTypes.DELETE, SOPSalesBySizeEnquiry.class);
        deleteQuery.addAnd("userDateId", userDataId);
        util.executeUpdateInNewTransaction(deleteQuery, userData);
    }
}
