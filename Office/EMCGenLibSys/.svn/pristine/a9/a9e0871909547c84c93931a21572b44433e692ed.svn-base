/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.crm;

import emc.datatypes.EMCDataType;
import emc.framework.EMCEntityClass;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "CRMCorrespondenceLog")
public class CRMCorrespondenceLog extends EMCEntityClass {

    @Column(length = 2000)
    private String eventDescription;
    @Temporal(TemporalType.DATE)
    private Date eventDate;
    private String eventType;
    private String referenceNumber;
    private String referenceType;
    private String trackNumber;
    private String resipient;

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public String getResipient() {
        return resipient;
    }

    public void setResipient(String resipient) {
        this.resipient = resipient;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        return toBuild;
    }

    /**
     * @return the trackNumber
     */
    public String getTrackNumber() {
        return trackNumber;
    }

    /**
     * @param trackNumber the trackNumber to set
     */
    public void setTrackNumber(String trackNumber) {
        this.trackNumber = trackNumber;
    }
}
