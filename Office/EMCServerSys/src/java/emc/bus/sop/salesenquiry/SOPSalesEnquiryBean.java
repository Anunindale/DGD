/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.sop.salesenquiry;

import emc.bus.gl.financialperiods.GLFinancialPeriodsLocal;
import emc.bus.sop.parameters.SOPParametersLocal;
import emc.entity.debtors.DebtorsCreditNoteLines;
import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.entity.debtors.DebtorsCustomerInvoiceLines;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.entity.gl.GLFinancialPeriods;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.sop.SOPCustomers;
import emc.entity.sop.SOPParameters;
import emc.entity.sop.SOPSalesEnquiry;
import emc.enums.debtors.invoicestatus.DebtorsInvoiceStatus;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.sop.salesorderenquiry.SalesEnquiryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.helpers.sop.SalesEnquiryHelper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class SOPSalesEnquiryBean extends EMCEntityBean implements SOPSalesEnquiryLocal {

    @EJB
    private GLFinancialPeriodsLocal periodBean;
    @EJB
    private SOPParametersLocal paramBean;

    @Override
    public List<GLFinancialPeriods> validatePeriodSelection(SalesEnquiryHelper helper, EMCUserData userData) {
        try {
            cleanupSalesEnquiry(helper.getUserId(), false, userData);
        } catch (EMCEntityBeanException ex) {
            //Will Never Be Thrown from this call
        }

        //Validate Period Selection
        if (periodBean.findFinancialPeriod(helper.getFromPeriod(), userData) == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "The from period is invalid.", userData);
            return null;
        }
        if (periodBean.findFinancialPeriod(helper.getToPeriod(), userData) == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "The to period is invalid.", userData);
            return null;
        }
        //Fetch All Periods
        List<GLFinancialPeriods> periodList = periodBean.findAllPeriodsBetween(helper.getFromPeriod(), helper.getToPeriod(), userData);

        if (periodList.isEmpty()) {
            Logger.getLogger("emc").log(Level.SEVERE, "No periods found.", userData);
            return null;
        } else {
            if (periodList.size() > 12) {
                Logger.getLogger("emc").log(Level.SEVERE, "Enquiry only display a maximum of 12 months.", userData);
                return null;
            }
        }
        return periodList;
    }

    @Override
    public void populateSalesEnquiry(GLFinancialPeriods period, int columnCount, SalesEnquiryHelper helper, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCustomerInvoiceMaster.class);
        query.addTableAnd(DebtorsCustomerInvoiceLines.class.getName(), "invCNNumber", DebtorsCustomerInvoiceMaster.class.getName(), "invCNNumber");
        query.addTableAnd(SOPCustomers.class.getName(), "customerNo", DebtorsCustomerInvoiceMaster.class.getName(), "customerId");
        query.addTableAnd(InventoryItemMaster.class.getName(), "itemId", DebtorsCustomerInvoiceLines.class.getName(), "itemId");
        query.addAnd("invoiceDate", period.getStartDate(), DebtorsCustomerInvoiceMaster.class.getName(), EMCQueryConditions.GREATER_THAN_EQ);
        query.addAnd("invoiceDate", period.getEndDate(), DebtorsCustomerInvoiceMaster.class.getName(), EMCQueryConditions.LESS_THAN_EQ);

        if (!isBlank(helper.getCustomer())) {
            query.addAndCommaSeperated("customerId", helper.getCustomer(), SOPCustomers.class.getName(), helper.isNotCustomer() ? EMCQueryConditions.NOT : EMCQueryConditions.EQUALS);

//            query.openAndConditionBracket();
//            query.addOrCommaSeperated("customerId", helper.getCustomer(), SOPCustomers.class.getName(), helper.isNotCustomer() ? EMCQueryConditions.NOT : EMCQueryConditions.EQUALS);
//            query.addAndCommaSeperated("invoiceToCustomer", helper.getCustomer(), SOPCustomers.class.getName(), helper.isNotCustomer() ? EMCQueryConditions.NOT : EMCQueryConditions.EQUALS);
//            query.closeConditionBracket();
        }
        if (!isBlank(helper.getInvoiceCustomer())) {
            query.addAndCommaSeperated("invoiceToCustomer", helper.getInvoiceCustomer(), SOPCustomers.class.getName(), helper.isNotInvoiceCustomer() ? EMCQueryConditions.NOT : EMCQueryConditions.EQUALS);
        }
        if (!isBlank(helper.getSalesRep())) {
            query.addAndCommaSeperated("salesRep", helper.getSalesRep(), DebtorsCustomerInvoiceMaster.class.getName(), helper.isNotSalesRep() ? EMCQueryConditions.NOT : EMCQueryConditions.EQUALS);
        }
        if (!isBlank(helper.getMarketingGroup())) {
            query.addAndCommaSeperated("marketingGroup", helper.getMarketingGroup(), SOPCustomers.class.getName(), helper.isNotMarketingGroup() ? EMCQueryConditions.NOT : EMCQueryConditions.EQUALS);
        }
        if (!isBlank(helper.getItem())) {
            query.openAndConditionBracket();
            if (helper.isNotItem()) {
                query.addAndCommaSeperated("itemId", helper.getItem(), InventoryItemMaster.class.getName(), EMCQueryConditions.NOT);
                query.addAndCommaSeperated("itemReference", helper.getItem(), InventoryItemMaster.class.getName(), EMCQueryConditions.NOT);
            } else {
                query.addOrCommaSeperated("itemId", helper.getItem(), InventoryItemMaster.class.getName(), EMCQueryConditions.EQUALS);
                query.addOrCommaSeperated("itemReference", helper.getItem(), InventoryItemMaster.class.getName(), EMCQueryConditions.EQUALS);
            }
            query.closeConditionBracket();
        }
        if (!isBlank(helper.getDimension1())) {
            query.addAndCommaSeperated("dimension1", helper.getDimension1(), DebtorsCustomerInvoiceLines.class.getName(), helper.isNotDimension1() ? EMCQueryConditions.NOT : EMCQueryConditions.EQUALS);
        }
        if (!isBlank(helper.getDimension2())) {
            query.addAndCommaSeperated("dimension2", helper.getDimension2(), DebtorsCustomerInvoiceLines.class.getName(), helper.isNotDimension2() ? EMCQueryConditions.NOT : EMCQueryConditions.EQUALS);
        }
        if (!isBlank(helper.getDimension3())) {
            query.addAndCommaSeperated("dimension3", helper.getDimension3(), DebtorsCustomerInvoiceLines.class.getName(), helper.isNotDimension3() ? EMCQueryConditions.NOT : EMCQueryConditions.EQUALS);
        }
        if (!isBlank(helper.getClassification1())) {
            query.addAndCommaSeperated("classificationClassGroup1", helper.getClassification1(), InventoryItemMaster.class.getName(), helper.isNotClassification1() ? EMCQueryConditions.NOT : EMCQueryConditions.EQUALS);
        }
        if (!isBlank(helper.getClassification5())) {
            query.addAndCommaSeperated("classificationClassGroup5", helper.getClassification5(), InventoryItemMaster.class.getName(), helper.isNotClassification5() ? EMCQueryConditions.NOT : EMCQueryConditions.EQUALS);
        }
        if (!isBlank(helper.getBrandGroup())) {
            query.addAndCommaSeperated("brandGroup", helper.getBrandGroup(), InventoryItemMaster.class.getName(), helper.isNotBrandGroup() ? EMCQueryConditions.NOT : EMCQueryConditions.EQUALS);
        }

        query.addField("customerId", SOPCustomers.class.getName());//0
        query.addField("customerName", SOPCustomers.class.getName());//1
        query.addField("marketingGroup", SOPCustomers.class.getName());//2
        query.addField("itemId", InventoryItemMaster.class.getName());//3
        query.addField("itemReference", InventoryItemMaster.class.getName());//4
        query.addField("description", InventoryItemMaster.class.getName());//5
        query.addField("classificationClassGroup1", InventoryItemMaster.class.getName());//6
        query.addField("classificationClassGroup5", InventoryItemMaster.class.getName());//7
        query.addField("dimension1", DebtorsCustomerInvoiceLines.class.getName());//8
        query.addField("dimension2", DebtorsCustomerInvoiceLines.class.getName());//9
        query.addField("dimension3", DebtorsCustomerInvoiceLines.class.getName());//10
        if (!isBlank(helper.getCustomField1()) && !isBlank(helper.getCustomTable1())) {
            query.addField(helper.getCustomField1(), helper.getCustomTable1());//11
        } else {
            query.addCustomField("", true, "Custom_1");//11
        }
        if (!isBlank(helper.getCustomField2()) && !isBlank(helper.getCustomTable2())) {
            query.addField(helper.getCustomField2(), helper.getCustomTable2());//12
        } else {
            query.addCustomField("", true, "Custom_2");//12
        }
        if (!isBlank(helper.getCustomField3()) && !isBlank(helper.getCustomTable3())) {
            query.addField(helper.getCustomField3(), helper.getCustomTable3());//13
        } else {
            query.addCustomField("", true, "Custom_3");//13
        }
        if (!isBlank(helper.getCustomField4()) && !isBlank(helper.getCustomTable4())) {
            query.addField(helper.getCustomField4(), helper.getCustomTable4());//14
        } else {
            query.addCustomField("", true, "Custom_4");//14
        }
        if (!isBlank(helper.getCustomField5()) && !isBlank(helper.getCustomTable5())) {
            query.addField(helper.getCustomField5(), helper.getCustomTable5());//15
        } else {
            query.addCustomField("", true, "Custom_5");//15
        }

        String invoice = query.getTableAlias(DebtorsCustomerInvoiceLines.class);
//        if (helper.isSalesValue()) {
        if (helper.isGrossValue()) {
            query.addCustomField("SUM(" + invoice + ".totalCost + " + invoice + ".discountAmount)", false);//16
        } else {
            query.addFieldAggregateFunction("totalCost", DebtorsCustomerInvoiceLines.class.getName(), "SUM");//16
        }
//        } else {
        if (helper.isUnitQuantity()) {
            String item = query.getTableAlias(InventoryItemMaster.class);
            query.addCustomField("SUM(" + invoice + ".quantity * " + item + ".classificationClassGroup6)", false);//17
        } else {
            query.addFieldAggregateFunction("quantity", DebtorsCustomerInvoiceLines.class.getName(), "SUM");//17
        }
//        }
        query.addField("brandGroup", InventoryItemMaster.class.getName());//18
        query.addField("salesRep", DebtorsCustomerInvoiceMaster.class.getName());//19

        query.addGroupBy(SOPCustomers.class.getName(), "customerId");
        query.addGroupBy(InventoryItemMaster.class.getName(), "itemId");
        query.addGroupBy(DebtorsCustomerInvoiceLines.class.getName(), "dimension1");
        query.addGroupBy(DebtorsCustomerInvoiceLines.class.getName(), "dimension2");
        query.addGroupBy(DebtorsCustomerInvoiceLines.class.getName(), "dimension3");
        query.addGroupBy(DebtorsCustomerInvoiceMaster.class.getName(), "salesRep");

        EMCQuery creditQuery = query.copyQuery();
        
        query.openAndConditionBracket();
        query.openConditionBracket(EMCQueryBracketConditions.NONE);
        query.addAnd("status", DebtorsInvoiceStatus.POSTED.toString(), DebtorsCustomerInvoiceMaster.class.getName(), EMCQueryConditions.EQUALS);
        query.addAnd("distributionNumber", null, DebtorsCustomerInvoiceMaster.class.getName(), EMCQueryConditions.IS_BLANK);
        query.closeConditionBracket();
        query.openConditionBracket(EMCQueryBracketConditions.OR);
        query.addAnd("status", DebtorsInvoiceStatus.DISTRIBUTION.toString(), DebtorsCustomerInvoiceMaster.class.getName(), EMCQueryConditions.EQUALS);
        query.closeConditionBracket();
        query.closeConditionBracket();

        List<Object[]> dataList = util.executeNativeQuery(query, userData);

        creditQuery.addAnd("status", DebtorsInvoiceStatus.POSTED.toString(), DebtorsCustomerInvoiceMaster.class.getName(), EMCQueryConditions.EQUALS);
        super.checkCompanyId(creditQuery, userData);
        
        String creditNoteQuery = creditQuery.getNativeQuery();
        creditNoteQuery = creditNoteQuery.replaceAll(DebtorsCustomerInvoiceMaster.class.getSimpleName(), DebtorsCreditNoteMaster.class.getSimpleName());
        creditNoteQuery = creditNoteQuery.replaceAll(DebtorsCustomerInvoiceLines.class.getSimpleName(), DebtorsCreditNoteLines.class.getSimpleName());

        dataList.addAll(util.executeNativeQuery(creditNoteQuery, userData));

        List<SOPSalesEnquiry> enquiryList = new ArrayList<SOPSalesEnquiry>();

        SOPSalesEnquiry tempRec;
        SOPSalesEnquiry quantityRec;
        SOPSalesEnquiry valueRec;

        for (Object[] data : dataList) {
            tempRec = new SOPSalesEnquiry();
            tempRec.setUserId(helper.getUserId());
            tempRec.setCustomerId((String) data[0]);
            tempRec.setCustomerName((String) data[1]);
            tempRec.setMarketingGroup((String) data[2]);
            tempRec.setItemId((String) data[3]);
            tempRec.setItemReference((String) data[4]);
            tempRec.setItemDescription((String) data[5]);
            tempRec.setClassification1((String) data[6]);
            tempRec.setClassification5((String) data[7]);
            tempRec.setDimension1((String) data[8]);
            tempRec.setDimension2((String) data[9]);
            tempRec.setDimension3((String) data[10]);
            tempRec.setCustomField1((String) data[11]);
            tempRec.setCustomField2((String) data[12]);
            tempRec.setCustomField3((String) data[13]);
            tempRec.setCustomField4((String) data[14]);
            tempRec.setCustomField5((String) data[15]);
            tempRec.setBrandGroup((String) data[18]);
            tempRec.setSalesRep((String) data[19]);

            quantityRec = (SOPSalesEnquiry) util.doClone(tempRec, SOPSalesEnquiry.class, userData);
            quantityRec.setRecordType(SalesEnquiryTypes.QUANTITY.toString());

            valueRec = (SOPSalesEnquiry) util.doClone(tempRec, SOPSalesEnquiry.class, userData);
            valueRec.setRecordType(SalesEnquiryTypes.VALUE.toString());

            BigDecimal quantity = isBlank(data[17]) ? BigDecimal.ZERO : data[17] instanceof Double ? util.getBigDecimal((Double) data[17]) : (BigDecimal) data[17];
            BigDecimal value = isBlank(data[16]) ? BigDecimal.ZERO : data[16] instanceof Double ? util.getBigDecimal((Double) data[16]) : (BigDecimal) data[16];

            switch (columnCount) {
                case 1:
                    quantityRec.setQuantity1(quantity);
                    valueRec.setQuantity1(value);
                    break;
                case 2:
                    quantityRec.setQuantity2(quantity);
                    valueRec.setQuantity2(value);
                    break;
                case 3:
                    quantityRec.setQuantity3(quantity);
                    valueRec.setQuantity3(value);
                    break;
                case 4:
                    quantityRec.setQuantity4(quantity);
                    valueRec.setQuantity4(value);
                    break;
                case 5:
                    quantityRec.setQuantity5(quantity);
                    valueRec.setQuantity5(value);
                    break;
                case 6:
                    quantityRec.setQuantity6(quantity);
                    valueRec.setQuantity6(value);
                    break;
                case 7:
                    quantityRec.setQuantity7(quantity);
                    valueRec.setQuantity7(value);
                    break;
                case 8:
                    quantityRec.setQuantity8(quantity);
                    valueRec.setQuantity8(value);
                    break;
                case 9:
                    quantityRec.setQuantity9(quantity);
                    valueRec.setQuantity9(value);
                    break;
                case 10:
                    quantityRec.setQuantity10(quantity);
                    valueRec.setQuantity10(value);
                    break;
                case 11:
                    quantityRec.setQuantity11(quantity);
                    valueRec.setQuantity11(value);
                    break;
                case 12:
                    quantityRec.setQuantity12(quantity);
                    valueRec.setQuantity12(value);
                    break;
            }
            quantityRec.setQuantityTotal(quantity);
            valueRec.setQuantityTotal(value);

            enquiryList.add(quantityRec);
            enquiryList.add(valueRec);
        }
        if (!enquiryList.isEmpty()) {
            util.insertDirect(SOPSalesEnquiry.class, enquiryList, userData);
        }
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
            query = "SELECT new SOPSalesEnquiry(userId, customerId, customerName, marketingGroup, itemId, itemReference, itemDescription, classification1, " +
                    "classification5, brandGroup, dimension1,  dimension2, dimension3, customField1, customField2, customField3, customField4, customField5, salesRep, recordType, " +
                    "SUM(quantity1), SUM(quantity2), SUM(quantity3), SUM(quantity4), SUM(quantity5), SUM(quantity6), SUM(quantity7), SUM(quantity8), " +
                    "SUM(quantity9), SUM(quantity10), SUM(quantity11), SUM(quantity12), SUM(quantityTotal)) " +
                    originalQuery.substring(originalQuery.indexOf("FROM"));
            userData.setUserData(0, query);
        }
        Collection data = super.getDataInRange(theTable, userData, start, end);
        return data;
    }

    @Override
    public void cleanupSalesEnquiry(String userId, boolean clearAll, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.DELETE, SOPSalesEnquiry.class);
        query.addAnd("userId", userId);

        util.executeUpdate(query, userData);

        if (clearAll) {
            SOPParameters param = paramBean.getParameterRecord(userData);
            if (param != null) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DATE, -7);

                if (param.getSalesEnquiryLastClearedDate() == null || param.getSalesEnquiryLastClearedDate().compareTo(cal.getTime()) >= 0) {
                    query = new EMCQuery(enumQueryTypes.DELETE, SOPSalesEnquiry.class);
                    query.addAnd("createdDate", cal.getTime(), EMCQueryConditions.LESS_THAN_EQ);

                    util.executeUpdate(query, userData);

                    param.setSalesEnquiryLastClearedDate(cal.getTime());
                    paramBean.update(param, userData);
                }
            }
        }
    }
}
