/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.sop.discountsetup;

import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.sop.customers.SOPCustomersLocal;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.sop.SOPCustomers;
import emc.entity.sop.SOPDiscountSetup;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.sop.discountsetup.CustomerSelectionType;
import emc.enums.sop.discountsetup.ItemSelectionType;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.tables.EMCTable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class SOPDiscountSetupBean extends EMCEntityBean implements SOPDiscountSetupLocal {

    @EJB
    private SOPCustomersLocal customerBean;
    @EJB
    private InventoryItemMasterLocal itemBean;

    /** Creates a new instance of SOPDiscountSetupBean. */
    public SOPDiscountSetupBean() {
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        Boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);

        if (ret) {
            //Used by DS bean
            return theRecord;
        } else {
            return ret;
        }
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean ret = super.doInsertValidation(vobject, userData);
        if (ret) {
            SOPDiscountSetup discountSetup = (SOPDiscountSetup) vobject;
            ret = doSaveValidation(discountSetup, userData);
        }
        return ret;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doUpdateValidation(vobject, userData);
        if (ret) {
            SOPDiscountSetup discountSetup = (SOPDiscountSetup) vobject;
            ret = doSaveValidation(discountSetup, userData);
        }
        return ret;
    }

    private boolean doSaveValidation(SOPDiscountSetup discountSetup, EMCUserData userData) {
        ItemSelectionType itemSelectionType = ItemSelectionType.fromString(discountSetup.getItemSelectType());
        if (itemSelectionType == ItemSelectionType.GROUP) {
            discountSetup.setItemId(null);
        } else if (itemSelectionType == ItemSelectionType.ITEM) {
            discountSetup.setItemDiscGroup(null);
        } else {
            discountSetup.setItemId(null);
            discountSetup.setItemDiscGroup(null);
        }

        CustomerSelectionType customerSelectionType = CustomerSelectionType.fromString(discountSetup.getCustomerSelectType());
        if (customerSelectionType == CustomerSelectionType.GROUP) {
            discountSetup.setCustomerId(null);
        } else if (customerSelectionType == CustomerSelectionType.CUSTOMER) {
            discountSetup.setCustomerDiscGroup(null);
        } else {
            discountSetup.setCustomerId(null);
            discountSetup.setCustomerDiscGroup(null);
        }
        if (isBlank(discountSetup.getDimension1())) {
            discountSetup.setDimension1(null);
        }
        if (isBlank(discountSetup.getDimension2())) {
            discountSetup.setDimension2(null);
        }
        if (isBlank(discountSetup.getDimension3())) {
            discountSetup.setDimension3(null);
        }
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPDiscountSetup.class);
        query.addAnd("customerDiscGroup", discountSetup.getCustomerDiscGroup());
        query.addAnd("customerId", discountSetup.getCustomerId());
        query.addAnd("itemDiscGroup", discountSetup.getItemDiscGroup());
        query.addAnd("itemId", discountSetup.getItemId());
        query.addAnd("dimension1", discountSetup.getDimension1());
        query.addAnd("dimension2", discountSetup.getDimension2());
        query.addAnd("dimension3", discountSetup.getDimension3());
        query.openAndConditionBracket();
        query.openConditionBracket(EMCQueryBracketConditions.NONE);
        query.addAnd("fromDate", Functions.date2SQLString(discountSetup.getFromDate()), EMCQueryConditions.GREATER_THAN_EQ);
        query.addAnd("fromDate", Functions.date2SQLString(discountSetup.getToDate()), EMCQueryConditions.LESS_THAN_EQ);
        query.closeConditionBracket();
        query.openConditionBracket(EMCQueryBracketConditions.OR);
        query.addAnd("toDate", Functions.date2SQLString(discountSetup.getFromDate()), EMCQueryConditions.GREATER_THAN_EQ);
        query.addAnd("toDate", Functions.date2SQLString(discountSetup.getToDate()), EMCQueryConditions.LESS_THAN_EQ);
        query.closeConditionBracket();
        query.closeConditionBracket();
        query.addAnd("recordID", discountSetup.getRecordID(), EMCQueryConditions.NOT);
        if (util.exists(query, userData)) {
            Logger.getLogger("emc").log(Level.SEVERE, "A similar record exists in the give valid period.", userData);
            return false;
        }
        return checkCustomerAndItemValues(discountSetup, userData);
    }

    /**
     * Checks that the required fields are populated.
     * @param discountSetup Discount setup record to check.
     * @param userData User data.
     * @return A boolean indicating whether all the required fields are populated.
     */
    private boolean checkCustomerAndItemValues(SOPDiscountSetup discountSetup, EMCUserData userData) {
        boolean ret = true;

        ItemSelectionType itemSelectionType = ItemSelectionType.fromString(discountSetup.getItemSelectType());

        if ((itemSelectionType == ItemSelectionType.GROUP && isBlank(discountSetup.getItemDiscGroup())) || (itemSelectionType == ItemSelectionType.ITEM && isBlank(discountSetup.getCustomerId()))) {
            logMessage(Level.SEVERE, "Item field may not be blank.", userData);
            ret = false;
        }

        CustomerSelectionType customerSelectionType = CustomerSelectionType.fromString(discountSetup.getCustomerSelectType());

        if ((customerSelectionType == CustomerSelectionType.GROUP && isBlank(discountSetup.getCustomerDiscGroup())) || (customerSelectionType == CustomerSelectionType.CUSTOMER && isBlank(discountSetup.getCustomerId()))) {
            logMessage(Level.SEVERE, "Customer field may not be blank.", userData);
            ret = false;
        }

        return ret;
    }

    @Override
    public BigDecimal getDiscountPercentage(String customerId, String itemId, String dimension1, String dimension2, String dimension3, Date lineDate, EMCUserData userData) {
        String customerDiscGroup = null;
        String itemDiscGroup = null;
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPDiscountSetup.class);
        //Customer
        query.openConditionBracket(EMCQueryBracketConditions.NONE);
        query.openConditionBracket(EMCQueryBracketConditions.NONE);
        if (isBlank(customerId)) {
            query.addAnd("customerId", null);
            query.addAnd("customerDiscGroup", null);
            customerId = null;
        } else {
            query.openConditionBracket(EMCQueryBracketConditions.NONE);
            query.addOr("customerId", customerId);
            query.addOr("customerId", null);
            query.closeConditionBracket();
            SOPCustomers customer = customerBean.findCustomer(customerId, userData);
            customerDiscGroup = customer.getDiscountGroup();
            if (isBlank(customerDiscGroup)) {
                query.addAnd("customerDiscGroup", null);
                customerDiscGroup = null;
            } else {
                query.openConditionBracket(EMCQueryBracketConditions.AND);
                query.addOr("customerDiscGroup", customerDiscGroup);
                query.addOr("customerDiscGroup", null);
                query.closeConditionBracket();
            }
        }
        query.closeConditionBracket();
        query.addOr("customerSelectType", CustomerSelectionType.ALL.toString());
        query.closeConditionBracket();
        //Item
        query.openConditionBracket(EMCQueryBracketConditions.AND);
        query.openConditionBracket(EMCQueryBracketConditions.NONE);
        if (isBlank(itemId)) {
            query.addAnd("itemId", null);
            query.addAnd("itemDiscGroup", null);
            itemId = null;
        } else {
            query.openConditionBracket(EMCQueryBracketConditions.NONE);
            query.addOr("itemId", itemId);
            query.addOr("itemId", null);
            query.closeConditionBracket();
            InventoryItemMaster item = itemBean.findItem(itemId, userData);
            itemDiscGroup = item.getSellingDiscountGroup();
            if (isBlank(itemDiscGroup)) {
                query.addAnd("itemDiscGroup", null);
                itemDiscGroup = null;
            } else {
                query.openConditionBracket(EMCQueryBracketConditions.AND);
                query.addOr("itemDiscGroup", itemDiscGroup);
                query.addOr("itemDiscGroup", null);
                query.closeConditionBracket();
            }
        }
        query.closeConditionBracket();
        query.addOr("itemSelectType", ItemSelectionType.ALL.toString());
        query.closeConditionBracket();
        //Config
        if (isBlank(dimension1)) {
            query.addAnd("dimension1", null);
            dimension1 = null;
        } else {
            query.openConditionBracket(EMCQueryBracketConditions.AND);
            query.addOr("dimension1", dimension1);
            query.addOr("dimension1", null);
            query.closeConditionBracket();
        }
        //Size
        if (isBlank(dimension2)) {
            query.addAnd("dimension2", null);
            dimension2 = null;
        } else {
            query.openConditionBracket(EMCQueryBracketConditions.AND);
            query.addOr("dimension2", dimension2);
            query.addOr("dimension2", null);
            query.closeConditionBracket();
        }
        //Colour
        if (isBlank(dimension3)) {
            query.addAnd("dimension3", null);
            dimension3 = null;
        } else {
            query.openConditionBracket(EMCQueryBracketConditions.AND);
            query.addOr("dimension3", dimension3);
            query.addOr("dimension3", null);
            query.closeConditionBracket();
        }
        //Date
        if (isBlank(lineDate)) {
            query.addAnd("fromDate", Functions.date2SQLString(Functions.nowDate()), EMCQueryConditions.LESS_THAN_EQ);
            query.addAnd("toDate", Functions.date2SQLString(Functions.nowDate()), EMCQueryConditions.GREATER_THAN_EQ);
        } else {
            query.addAnd("fromDate", Functions.date2SQLString(lineDate), EMCQueryConditions.LESS_THAN_EQ);
            query.addAnd("toDate", Functions.date2SQLString(lineDate), EMCQueryConditions.GREATER_THAN_EQ);
        }

        List<SOPDiscountSetup> discountSetupList = util.executeGeneralSelectQuery(query, userData);
        SOPDiscountSetup bestMatch = null;
        int dimMatch = 0;
        int bestDimMatch = 0;
        for (SOPDiscountSetup setup : discountSetupList) {
            if (bestMatch == null) {
                bestMatch = setup;
                continue;
            } else {
                //Item
                if (util.checkObjectsEqual(bestMatch.getItemId(), setup.getItemId())) {
                    //Item Group
                    if (util.checkObjectsEqual(bestMatch.getItemDiscGroup(), setup.getItemDiscGroup())) {
                        //Customer
                        if (util.checkObjectsEqual(bestMatch.getCustomerId(), setup.getCustomerId())) {
                            //Customer Group
                            if (util.checkObjectsEqual(bestMatch.getCustomerDiscGroup(), setup.getCustomerDiscGroup())) {
                                //Most Dimensions
                                dimMatch = 0;
                                if (util.checkObjectsEqual(setup.getDimension1(), dimension1)) {
                                    dimMatch++;
                                }
                                if (util.checkObjectsEqual(setup.getDimension2(), dimension2)) {
                                    dimMatch++;
                                }
                                if (util.checkObjectsEqual(setup.getDimension3(), dimension3)) {
                                    dimMatch++;
                                }
                                //Better Match
                                if (dimMatch > bestDimMatch) {
                                    bestDimMatch = dimMatch;
                                    bestMatch = setup;
                                }
                                //Every thing matches
                                if (bestDimMatch == 3) {
                                    break;
                                }
                            } else if (isBlank(bestMatch.getCustomerDiscGroup())) {
                                //4 - Customer Group
                                bestMatch = setup;
                            }
                        } else if (isBlank(bestMatch.getCustomerId())) {
                            //3 - Customer
                            bestMatch = setup;
                        }
                    } else if (isBlank(bestMatch.getItemDiscGroup())) {
                        //2 - ItemGroup
                        bestMatch = setup;
                    }
                } else if (isBlank(bestMatch.getItemId())) {
                    //1 - Item
                    bestMatch = setup;
                }
            }
        }
        return bestMatch == null ? BigDecimal.ZERO : bestMatch.getDiscountPercentage();
    }
}
