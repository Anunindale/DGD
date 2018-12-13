/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base.dblog;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.dbshowlog.DataField;
import emc.datatypes.base.dbshowlog.DateChanged;
import emc.datatypes.base.dbshowlog.NewValue; 
import emc.datatypes.base.dbshowlog.OldValue;
import emc.datatypes.base.dbshowlog.SessionId;
import emc.datatypes.base.dbshowlog.UniqueIdentifier;
import emc.datatypes.base.dbshowlog.UserId;
import emc.datatypes.base.dbshowlog.Status;
import emc.datatypes.base.dbshowlog.TimeChanged;
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
 * @author asd_admin
 */
@Entity
@Table(name = "BaseDBLogChanges")
public class BaseDBLogChanges extends EMCEntityClass {
    
    private String dataField;
    @Column(length=100000)
    private String oldValue;
    @Column(length=100000)
    private String newValue;
    private String uniqueIdentifier;
    @Temporal(TemporalType.TIME)
    private Date timeChanged;
    @Temporal(TemporalType.DATE)
    private Date dateChanged;
    private String userId;
    private String status; 
    private String sessionId;

    public String getDataField() {
        return dataField;
    }

    public void setDataField(String dataField) {
        this.dataField = dataField;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }

   

    public Date getTimeChanged() {
        return timeChanged;
    }

    public void setTimeChanged(Date timeChanged) {
        this.timeChanged = timeChanged;
    }

    public Date getDateChanged() {
        return dateChanged;
    }

    public void setDateChanged(Date dateChanged) {
        this.dateChanged = dateChanged;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    
    
    
     @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("dataField", new DataField());
        toBuild.put("oldValue", new OldValue());
        toBuild.put("newValue", new NewValue());
        toBuild.put("timeChanged", new TimeChanged());
        toBuild.put("uniqueIdentifier", new UniqueIdentifier());
        toBuild.put("dateChanged", new DateChanged());
        toBuild.put("userId", new UserId());
        toBuild.put("status", new Status());
        toBuild.put("sessionId", new SessionId());
     

        return toBuild;
    }
    
}
