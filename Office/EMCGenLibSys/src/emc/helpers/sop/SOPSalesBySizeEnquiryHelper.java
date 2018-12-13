/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.helpers.sop;

import java.util.Date;

/**
 *
 * @author wikus
 */
public class SOPSalesBySizeEnquiryHelper {

    private String userDataKey;
    private Date fromDate;
    private Date toDate;
    private String customerMarketingGroup;
    private String customer;
    private String itemProductGroup;
    private String itemGender;
    private String itemPlanningGroup;
    private String item;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCustomerMarketingGroup() {
        return customerMarketingGroup;
    }

    public void setCustomerMarketingGroup(String customerMarketingGroup) {
        this.customerMarketingGroup = customerMarketingGroup;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getItemGender() {
        return itemGender;
    }

    public void setItemGender(String itemGender) {
        this.itemGender = itemGender;
    }

    public String getItemPlanningGroup() {
        return itemPlanningGroup;
    }

    public void setItemPlanningGroup(String itemPlanningGroup) {
        this.itemPlanningGroup = itemPlanningGroup;
    }

    public String getItemProductGroup() {
        return itemProductGroup;
    }

    public void setItemProductGroup(String itemProductGroup) {
        this.itemProductGroup = itemProductGroup;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getUserDataKey() {
        return userDataKey;
    }

    public void setUserDataKey(String userDataKey) {
        this.userDataKey = userDataKey;
    }
}
