/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.base.datasource;

import emc.framework.EMCEntityClass;
import java.util.Date;

/**
 *
 * @author rico
 */
public class EMCTransactions extends EMCEntityClass{
    private String transaction;
    private Long sessionNumber;
    private String userId;
    private String transactionStartDate;
    private String transactionStartTime;
    private String transactionRuntime;
    private Date   fullStartDate;
    private Object transactionId;
    private String command;
    private Date fullEndDate;

    /**
     * @return the transaction
     */
    public String getTransaction() {
        return transaction;
    }

    /**
     * @param transaction the transaction to set
     */
    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    /**
     * @return the sessionNumber
     */
    public Long getSessionNumber() {
        return sessionNumber;
    }

    /**
     * @param sessionNumber the sessionNumber to set
     */
    public void setSessionNumber(Long sessionNumber) {
        this.sessionNumber = sessionNumber;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the transactionStartDate
     */
    public String getTransactionStartDate() {
        return transactionStartDate;
    }

    /**
     * @param transactionStartDate the transactionStartDate to set
     */
    public void setTransactionStartDate(String transactionStartDate) {
        this.transactionStartDate = transactionStartDate;
    }

    /**
     * @return the transactionStartTime
     */
    public String getTransactionStartTime() {
        return transactionStartTime;
    }

    /**
     * @param transactionStartTime the transactionStartTime to set
     */
    public void setTransactionStartTime(String transactionStartTime) {
        this.transactionStartTime = transactionStartTime;
    }

    /**
     * @return the transactionRuntime
     */
    public String getTransactionRuntime() {
        return transactionRuntime;
    }

    /**
     * @param transactionRuntime the transactionRuntime to set
     */
    public void setTransactionRuntime(String transactionRuntime) {
        this.transactionRuntime = transactionRuntime;
    }

    /**
     * @return the fullStartData
     */
    public Date getFullStartDate() {
        return fullStartDate;
    }

    /**
     * @param fullStartData the fullStartData to set
     */
    public void setFullStartDate(Date fullStartDate) {
        this.fullStartDate = fullStartDate;
    }

    /**
     * @return the transactionId
     */
    public Object getTransactionId() {
        return transactionId;
    }

    /**
     * @param transactionId the transactionId to set
     */
    public void setTransactionId(Object transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * @param command the command to set
     */
    public void setCommand(String command) {
        this.command = command;
    }
    public EMCTransactions doDisplayCopy(){
        EMCTransactions ret = new EMCTransactions();
        ret.transaction = this.transaction;
        ret.sessionNumber = this.sessionNumber;
        ret.userId = this.userId;
        ret.transactionStartDate = this.transactionStartDate;
        ret.transactionStartTime = this.transactionStartTime;
        ret.fullStartDate = this.fullStartDate;
        ret.transactionId = null;
        ret.command = this.command;
        return ret;
    }
    public void calcAndSetTransactionRunTime(long currentTime){
        Long dateDiff = (currentTime - this.getFullStartDate().getTime())/1000;
        Long hours,minutes,seconds;
        String sHours,sMinutes,sSeconds;
        seconds = dateDiff%60;
        hours = (dateDiff/60)/60;
        minutes=(dateDiff-seconds)/60-hours*60;
        sHours = hours.toString();
        if(sHours.length() == 1) sHours = "0"+sHours;
        sMinutes = minutes.toString();
        if(sMinutes.length() == 1) sMinutes = "0"+sMinutes;
        sSeconds = seconds.toString();
        if(sSeconds.length() == 1) sSeconds = "0"+sSeconds;
        this.setTransactionRuntime(sHours+":"+sMinutes+":"+sSeconds);
    }

    /**
     * @return the fullEndDate
     */
    public Date getFullEndDate() {
        return fullEndDate;
    }

    /**
     * @param fullEndDate the fullEndDate to set
     */
    public void setFullEndDate(Date fullEndDate) {
        this.fullEndDate = fullEndDate;
    }

    
}
