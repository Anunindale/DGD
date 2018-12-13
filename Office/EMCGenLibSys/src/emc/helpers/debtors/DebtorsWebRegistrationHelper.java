/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.helpers.debtors;

import emc.entity.sop.SOPCustomers;

/**
 * @description : Helper class used to store Debtors web registration
 *
 * @date        : 13 August 2015
 *
 * @author      : Kapeshi Kongolo
 *
 * @version     : 1.0
 */
public class DebtorsWebRegistrationHelper {

    private SOPCustomers customer;
    private String tempPassword;
    private String mailToEmail;
    private boolean mailEmail;
   
    /** Creates a new instance of DebtorsWebRegistrationHelper */
    public DebtorsWebRegistrationHelper() {

    }

    public SOPCustomers getCustomer() {
        return customer;
    }

    public void setCustomer(SOPCustomers customer) {
        this.customer = customer;
    }

    public String getTempPassword() {
        return tempPassword;
    }

    public void setTempPassword(String tempPassword) {
        this.tempPassword = tempPassword;
    }

    public String getMailToEmail() {
        return mailToEmail;
    }

    public void setMailToEmail(String mailToEmail) {
        this.mailToEmail = mailToEmail;
    }

    public boolean isMailEmail() {
        return mailEmail;
    }

    public void setMailEmail(boolean mailEmail) {
        this.mailEmail = mailEmail;
    }
}
