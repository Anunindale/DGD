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
public class EMCEmail {

    public static final int QUERY = 0;
    public static final int ACTIVITY_GROUP = 1;
    public static final int REFERENCE_ADDRESSES = 3;
    //
    private boolean malPerRecipient = true;
    private int recipientType;
    private List<Object> reciptients;
    //
    private List<String> cc;
    private List<String> bcc;
    private String subject;
    private String message;
    private List<String> attachmentList;
    //
    private String template;
    private String sourceTable;
    private Object sourceEntity;
    private long sourceRecordID;

    public EMCEmail() {
        recipientType = -1;

        reciptients = new ArrayList();
        cc = new ArrayList();
        bcc = new ArrayList();
        attachmentList = new ArrayList();
    }

    public void addRecipient(String activityGroup) {
        if (this.recipientType != -1 && this.recipientType != ACTIVITY_GROUP) {
            throw new IllegalArgumentException("You may not add an activity group to this emails recipients");
        }

        this.recipientType = ACTIVITY_GROUP;
        this.reciptients.add(activityGroup);
    }

    public void addRecipient(EMCQuery recipientQuery) {
        if (this.recipientType != -1 && this.recipientType != QUERY) {
            throw new IllegalArgumentException("You may not add a recipient query to this emails recipients");
        }

        this.recipientType = QUERY;
        this.reciptients.add(recipientQuery);
    }

    public void addRecipient(String reference, String email) {
        if (this.recipientType != -1 && this.recipientType != REFERENCE_ADDRESSES) {
            throw new IllegalArgumentException("You may not add a reference and address to this emails recipients");
        }

        this.recipientType = REFERENCE_ADDRESSES;
        this.reciptients.add(new String[]{email, reference});
    }

    public void addRecipient(String reference, String email, Long recordID) {
        if (this.recipientType != -1 && this.recipientType != REFERENCE_ADDRESSES) {
            throw new IllegalArgumentException("You may not add a reference and address to this emails recipients");
        }

        this.recipientType = REFERENCE_ADDRESSES;
        this.reciptients.add(new Object[]{email, reference, recordID});
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void addAttachment(String attachment) {
        this.attachmentList.add(attachment);
    }

    public void addCC(String cc) {
        this.cc.add(cc);
    }

    public void addBCC(String bcc) {
        this.bcc.add(bcc);
    }

    public void setMalPerRecipient(boolean malPerRecipient) {
        this.malPerRecipient = malPerRecipient;
    }

    public boolean isMalPerRecipient() {
        return malPerRecipient;
    }

    public int getRecipientType() {
        return recipientType;
    }

    public List<Object> getReciptients() {
        return reciptients;
    }

    public List<String> getCc() {
        return cc;
    }

    public List<String> getBcc() {
        return bcc;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getAttachmentList() {
        return attachmentList;
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

    public Object getSourceEntity() {
        return sourceEntity;
    }

    public void setSourceEntity(Object sourceEntity) {
        this.sourceEntity = sourceEntity;
    }

}
