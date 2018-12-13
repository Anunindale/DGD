/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.developertools.sites;

import emc.datatypes.EMCDataType;
import emc.datatypes.developertools.sites.ConnectionDescription;
import emc.datatypes.developertools.sites.IP;
import emc.datatypes.developertools.sites.MasterIdFK;
import emc.datatypes.developertools.sites.Password;
import emc.datatypes.developertools.sites.Port;
import emc.datatypes.developertools.sites.UserName;
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
@Table(name = "DevSiteLines", uniqueConstraints = {@UniqueConstraint(columnNames = {"masterId", "connectionDescription", "companyId"})})
public class DevSiteLines extends EMCEntityClass {

    private String masterId;
    private String connectionDescription;
    private String IP;
    private String port;
    private String userName;
    private String password;

    public String getMasterId() {
        return masterId;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getConnectionDescription() {
        return connectionDescription;
    }

    public void setConnectionDescription(String connectionDescription) {
        this.connectionDescription = connectionDescription;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("masterId", new MasterIdFK());
        toBuild.put("connectionDescription", new ConnectionDescription());
        toBuild.put("IP", new IP());
        toBuild.put("port", new Port());
        toBuild.put("userName", new UserName());
        toBuild.put("password", new Password());
        return toBuild;
    }
}
