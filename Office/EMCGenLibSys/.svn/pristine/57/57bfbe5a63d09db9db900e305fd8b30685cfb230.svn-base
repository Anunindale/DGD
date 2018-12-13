/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base.servertransactions;

import emc.entity.base.datasource.EMCTransactions;
import emc.framework.EMCEntityClass;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author rico
 */
@Entity
@Table(name = "BaseServerTransactionsLog")
public class BaseServerTransactionsLog extends EMCEntityClass {

    private long transStartTime;
    private long transEndTime;
    private String timeTaken;
    @Temporal(TemporalType.DATE)
    private Date transStartDate;
    private String command;
    private String cmdUserId;

    public BaseServerTransactionsLog() {
    }

    public BaseServerTransactionsLog(EMCTransactions trans, String status) {
        this.transStartDate = trans.getFullStartDate();
        this.cmdUserId = trans.getUserId();
        this.command = trans.getCommand();
        this.timeTaken = trans.getTransactionRuntime();
        this.transStartTime = trans.getFullStartDate().getTime();
        this.transEndTime = trans.getFullEndDate().getTime();
        this.setClosed(status);
    }

    /**
     * @return the transStartTime
     */
    public long getTransStartTime() {
        return transStartTime;
    }

    /**
     * @param transStartTime the transStartTime to set
     */
    public void setTransStartTime(long transStartTime) {
        this.transStartTime = transStartTime;
    }

    /**
     * @return the transEndTime
     */
    public long getTransEndTime() {
        return transEndTime;
    }

    /**
     * @param transEndTime the transEndTime to set
     */
    public void setTransEndTime(long transEndTime) {
        this.transEndTime = transEndTime;
    }

    /**
     * @return the timeTaken
     */
    public String getTimeTaken() {
        return timeTaken;
    }

    /**
     * @param timeTaken the timeTaken to set
     */
    public void setTimeTaken(String timeTaken) {
        this.timeTaken = timeTaken;
    }

    /**
     * @return the transStartDate
     */
    public Date getTransStartDate() {
        return transStartDate;
    }

    /**
     * @param transStartDate the transStartDate to set
     */
    public void setTransStartDate(Date transStartDate) {
        this.transStartDate = transStartDate;
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

    /**
     * @return the cmdUserId
     */
    public String getCmdUserId() {
        return cmdUserId;
    }

    /**
     * @param cmdUserId the cmdUserId to set
     */
    public void setCmdUserId(String cmdUserId) {
        this.cmdUserId = cmdUserId;
    }
}
