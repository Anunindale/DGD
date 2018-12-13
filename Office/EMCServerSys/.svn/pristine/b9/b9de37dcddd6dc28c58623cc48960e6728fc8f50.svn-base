/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.server.utility;

import emc.entity.base.BaseDBConnections;
import emc.enums.base.databaseconnection.DBConnection;
import emc.enums.enumQueryTypes;
import emc.framework.EMCBusinessBean;
import emc.framework.EMCDebug;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class EMCRemoteDataBaseBean extends EMCBusinessBean implements EMCRemoteDataBaseLocal {

    @Override
    public Connection connectToDatabase(BaseDBConnections connInfo, EMCUserData userData) {
        Connection conn = null;
        try {
            if (connInfo == null) {
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseDBConnections.class);
                query.addAnd("connectionId", DBConnection.EMC.toString());
                connInfo = (BaseDBConnections) util.executeSingleResultQuery(query, userData);
                if (connInfo == null) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Connection Info not set up.", userData);
                    return null;
                }
            }
            Class.forName(connInfo.getDriver());
            String url = connInfo.getConnectionType() + ":" + connInfo.getDatabaseVender() + "://" + connInfo.getServer() + ":" +
                    connInfo.getPort() + "/" + connInfo.getDatabaseName();
            conn = DriverManager.getConnection(url, connInfo.getUserName(), connInfo.getDbPassword());
        } catch (Exception e) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Direct JDBC connection." + e.getMessage(), userData);
            }
            e.printStackTrace();
        }
        return conn;
    }

    @Override
    public void closeConnectionToDB(Connection conn, EMCUserData userData) {
        try {
            conn.close();
        } catch (Exception close) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to close JDBC Connection." + close.getMessage(), userData);
            }
            close.printStackTrace();
        }
    }

    @Override
    public List<Object[]> exJDBCFieldReadQuery(Connection conn, EMCQuery theQuery, EMCUserData userData) {
        List<Object[]> ret = new ArrayList<Object[]>();
        try {
            Statement st = conn.createStatement(ResultSet.FETCH_FORWARD, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(theQuery.getNativeQuery());
            ResultSetMetaData md = rs.getMetaData();
            Object[] selectedData;
            while (rs.next()) {
                selectedData = new Object[md.getColumnCount()];
                for (int col = 0; col < md.getColumnCount(); col++) {
                    selectedData[col] = rs.getObject(col + 1);
                }
                ret.add(selectedData);
            }
            rs.close();
            st.close();
        } catch (SQLException se) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Direct JDBC select threw a SQLException." + se.getMessage(), userData);
            }
            se.printStackTrace();
        }
        return ret;
    }

    @Override
    public int exJDBCUpdateQuery(Connection conn, EMCQuery theQuery, EMCUserData userData) {
        int updated = 0;
        try {
            Statement st = conn.createStatement(ResultSet.FETCH_FORWARD, ResultSet.CONCUR_READ_ONLY);
            updated = st.executeUpdate(theQuery.toString());
            st.close();
        } catch (SQLException se) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Direct JDBC update threw a SQLException." + se.getMessage(), userData);
            }
            se.printStackTrace();
        }
        return updated;
    }
}
