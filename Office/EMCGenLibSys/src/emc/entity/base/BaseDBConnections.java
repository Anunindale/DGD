/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.dbconnection.ConnectionId;
import emc.datatypes.base.dbconnection.ConnectionType;
import emc.datatypes.base.dbconnection.DataBaseVander;
import emc.datatypes.base.dbconnection.Database;
import emc.datatypes.base.dbconnection.DriverClass;
import emc.datatypes.base.dbconnection.Password;
import emc.datatypes.base.dbconnection.Port;
import emc.datatypes.base.dbconnection.Server;
import emc.datatypes.base.dbconnection.UserName;
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
@Table(name = "BaseDBConnections", uniqueConstraints = {@UniqueConstraint(columnNames = {"connectionId", "companyId"})})
public class BaseDBConnections extends EMCEntityClass {

    private String connectionId;
    private String server;
    private String port;
    private String databaseName;
    private String userName;
    private String dbPassword;
    private String driver;
    private String databaseVender;
    private String connectionType;

    public String getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getDatabaseVender() {
        return databaseVender;
    }

    public void setDatabaseVender(String databaseVender) {
        this.databaseVender = databaseVender;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
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
        toBuild.put("connectionId", new ConnectionId());
        toBuild.put("connectionType", new ConnectionType());
        toBuild.put("databaseVender", new DataBaseVander());
        toBuild.put("driver", new DriverClass());
        toBuild.put("server", new Server());
        toBuild.put("port", new Port());
        toBuild.put("databaseName", new Database());
        toBuild.put("userName", new UserName());
        toBuild.put("dbPassword", new Password());
        return toBuild;
    }
}
