/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.helpers.base;

import emc.framework.EMCQuery;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class EMCSms {

    public static final int QUERY = 0;
    public static final int ACTIVITY_GROUP = 1;
    public static final int REFERENCE_NUMBER = 3;
    //
    private int recipientType;
    private List<Object> reciptients;
    //
    private String message;
    //
    private String template;
    private String sourceTable;
    private Object sourceEntity;
    private long sourceRecordID;

    public EMCSms() {
        recipientType = -1;
        reciptients = new ArrayList();
    }

    public void addRecipient(String activityGroup) {
        if (this.recipientType != -1 && this.recipientType != ACTIVITY_GROUP) {
            throw new IllegalArgumentException("You may not add an activity group to this sms recipients");
        }

        this.recipientType = ACTIVITY_GROUP;
        this.reciptients.add(activityGroup);
    }

    public void addRecipient(EMCQuery recipientQuery) {
        if (this.recipientType != -1 && this.recipientType != QUERY) {
            throw new IllegalArgumentException("You may not add a recipient query to this sms recipients");
        }

        this.recipientType = QUERY;
        this.reciptients.add(recipientQuery);
    }

    public void addRecipient(String postalCode, String number, String reference, Long recordID) {
        if (this.recipientType != -1 && this.recipientType != REFERENCE_NUMBER) {
            throw new IllegalArgumentException("You may not add a reference and address to this sms recipients");
        }

        this.recipientType = REFERENCE_NUMBER;
        this.reciptients.add(new Object[]{postalCode, number, reference, recordID});
    }

    public int getRecipientType() {
        return recipientType;
    }

    public List<Object> getReciptients() {
        return reciptients;
    }

    public void setReciptients(List<Object> reciptients) {
        this.reciptients = reciptients;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getSourceTable() {
        return sourceTable;
    }

    public void setSourceTable(String sourceTable) {
        this.sourceTable = sourceTable;
    }

    public long getSourceRecordID() {
        return sourceRecordID;
    }

    public void setSourceRecordID(long sourceRecordID) {
        this.sourceRecordID = sourceRecordID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getSourceEntity() {
        return sourceEntity;
    }

    public void setSourceEntity(Object sourceEntity) {
        this.sourceEntity = sourceEntity;
    }

}
