/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.mailsetup.FromMailAddress;
import emc.datatypes.base.mailsetup.LogToEmailAddress;
import emc.datatypes.base.mailsetup.SmtpAddress;
import emc.datatypes.base.mailsetup.SmtpAuthenticationReq;
import emc.datatypes.base.mailsetup.SmtpPassword;
import emc.datatypes.base.mailsetup.SmtpPort;
import emc.datatypes.base.mailsetup.SmtpUserName;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "BaseMailSetup", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId"})})
public class BaseMailSetup extends EMCEntityClass {

    private boolean smtpAuthenticationRequired;
    private int smtpPort;
    private String smtpAddress;
    private String smtpUserName;
    private String smtpPassword;
    private String fromEmailAddress;
    private String logToEmailAddress;

    public String getSmtpAddress() {
        return smtpAddress;
    }

    public void setSmtpAddress(String smtpAddress) {
        this.smtpAddress = smtpAddress;
    }

    public boolean isSmtpAuthenticationRequired() {
        return smtpAuthenticationRequired;
    }

    public void setSmtpAuthenticationRequired(boolean smtpAuthenticationRequired) {
        this.smtpAuthenticationRequired = smtpAuthenticationRequired;
    }

    public String getSmtpPassword() {
        return smtpPassword;
    }

    public void setSmtpPassword(String smtpPassword) {
        this.smtpPassword = smtpPassword;
    }

    public int getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
    }

    public String getSmtpUserName() {
        return smtpUserName;
    }

    public void setSmtpUserName(String smtpUserName) {
        this.smtpUserName = smtpUserName;
    }

    public String getFromEmailAddress() {
        return fromEmailAddress;
    }

    public void setFromEmailAddress(String fromEmailAddress) {
        this.fromEmailAddress = fromEmailAddress;
    }

    public String getLogToEmailAddress() {
        return logToEmailAddress;
    }

    public void setLogToEmailAddress(String logToEmailAddress) {
        this.logToEmailAddress = logToEmailAddress;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("smtpAddress", new SmtpAddress());
        toBuild.put("smtpUserName", new SmtpUserName());
        toBuild.put("smtpPassword", new SmtpPassword());
        toBuild.put("smtpAuthenticationRequired", new SmtpAuthenticationReq());
        toBuild.put("fromEmailAddress", new FromMailAddress());
        toBuild.put("smtpPort", new SmtpPort());
        toBuild.put("logToEmailAddress", new LogToEmailAddress());
        return toBuild;
    }
}
