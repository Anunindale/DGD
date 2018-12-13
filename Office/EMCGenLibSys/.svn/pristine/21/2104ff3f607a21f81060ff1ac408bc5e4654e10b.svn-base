/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.base.messages;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.messages.Message;
import emc.datatypes.base.messages.MessageId;
import emc.datatypes.systemwide.Description;
import emc.datatypes.systemwide.EMCModule;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @name        BaseMessages
 *
 * @date        20 Apr 2009
 *
 * @desciption  This entity holds the messages that are displayed to users.
 *
 * @author      riaan
 */
@Entity
@Table(name = "BaseMessages", uniqueConstraints = {@UniqueConstraint(columnNames = {"messageId", "companyId"})})
public class BaseMessages extends EMCEntityClass {

    private String messageId;
    private String messageModule;
    @Column(length = 1000)
    private String description;
    private String message;
            
    /** Creates a new instance of BaseMessages. */
    public BaseMessages() {
        
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageModule() {
        return messageModule;
    }

    public void setMessageModule(String messageModule) {
        this.messageModule = messageModule;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("messageId", new MessageId());
        toBuild.put("description", new Description());
        toBuild.put("messageModule", new EMCModule());
        toBuild.put("message", new Message());

        return toBuild;
    }

}
