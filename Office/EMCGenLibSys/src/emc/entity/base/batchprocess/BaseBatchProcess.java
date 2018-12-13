/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base.batchprocess;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.batchprocess.EndProcessing;
import emc.datatypes.base.batchprocess.StartProcessing;
import emc.enums.base.batchprocess.BatchProcessStatus;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author rico
 */
@Entity
@Table(name = "BaseBatchProcess")
public class BaseBatchProcess extends EMCEntityClass {

    @Column(length = 100000)
    private String toDo;
    private String command;
    private String status;
    private int retry;
    @Column(length = 100000)
    private String messages;
    private String sourceTable;
    private String sourceField;
    private String source;
    private long sourceVersion;
    private int priority;
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date startProcessing;
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date endProcessing;

    /**
     * @return the toDo
     */
    public String getToDo() {
        return toDo;
    }

    /**
     * @param toDo the toDo to set
     */
    public void setToDo(String toDo) {
        this.toDo = toDo;
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
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the retry
     */
    public int getRetry() {
        return retry;
    }

    /**
     * @param retry the retry to set
     */
    public void setRetry(int retry) {
        this.retry = retry;
    }

    /**
     * @return the messages
     */
    public String getMessages() {
        return messages;
    }

    /**
     * @param messages the messages to set
     */
    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public long getSourceVersion() {
        return sourceVersion;
    }

    public void setSourceVersion(long sourceVersion) {
        this.sourceVersion = sourceVersion;
    }

    public String getSourceTable() {
        return sourceTable;
    }

    public void setSourceTable(String sourceTable) {
        this.sourceTable = sourceTable;
    }

    public String getSourceField() {
        return sourceField;
    }

    public void setSourceField(String sourceField) {
        this.sourceField = sourceField;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap ret = super.buildFieldList();
        ret.put("startProcessing",new StartProcessing());
        ret.put("endProcessing",new EndProcessing());
        return ret;
    }
    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = super.buildQuery();

        DecimalFormat timeFormat = new DecimalFormat("00");

        Calendar currentCalendar = Calendar.getInstance();
        Date currentDate = currentCalendar.getTime();
        String currentTime = timeFormat.format(currentCalendar.get(Calendar.HOUR_OF_DAY)) + ":" + timeFormat.format(currentCalendar.get(Calendar.MINUTE)) + ":" + timeFormat.format(currentCalendar.get(Calendar.SECOND));

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.add(Calendar.HOUR, -12);
        Date startDate = startCalendar.getTime();
        String startTime = timeFormat.format(startCalendar.get(Calendar.HOUR)) + ":" + timeFormat.format(startCalendar.get(Calendar.MINUTE)) + ":00";

        //If dates differ, check for records captured today and yesterday.
        if (startCalendar.get(Calendar.DAY_OF_YEAR) != currentCalendar.get(Calendar.DAY_OF_YEAR)) {
            query.openAndConditionBracket();
            query.openConditionBracket(EMCQueryBracketConditions.NONE);
            query.addAnd("createdDate", startDate, EMCQueryConditions.EQUALS);
            query.addAnd("createdTime", startTime, EMCQueryConditions.GREATER_THAN_EQ);
            query.closeConditionBracket();
            query.openConditionBracket(EMCQueryBracketConditions.OR);
            query.addAnd("createdDate", currentDate, EMCQueryConditions.EQUALS);
            query.addAnd("createdTime", currentTime, EMCQueryConditions.LESS_THAN_EQ);
            query.closeConditionBracket();
            query.closeConditionBracket();
        } else {
            query.addAnd("createdDate", currentDate);
            query.addAnd("createdTime", currentTime, EMCQueryConditions.LESS_THAN_EQ);
            query.addAnd("createdTime", startTime, EMCQueryConditions.GREATER_THAN_EQ);
        }

        return query;
    }

    /**
     * @return the startProcessing
     */
    public Date getStartProcessing() {
        return startProcessing;
    }

    /**
     * @param startProcessing the startProcessing to set
     */
    public void setStartProcessing(Date startProcessing) {
        this.startProcessing = startProcessing;
    }

    /**
     * @return the endProcessing
     */
    public Date getEndProcessing() {
        return endProcessing;
    }

    /**
     * @param endProcessing the endProcessing to set
     */
    public void setEndProcessing(Date endProcessing) {
        this.endProcessing = endProcessing;
    }
}
